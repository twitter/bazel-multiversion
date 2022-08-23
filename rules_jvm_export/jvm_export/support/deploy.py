#!$PYTHON_PATH

# Originally from https://github.com/vaticle/bazel-distribution/blob/master/maven/templates/deploy.py
# SPDX-License-Identifier: Apache-2.0

from __future__ import print_function
from xml.etree import ElementTree

import argparse
import hashlib
import os
import re
import shutil
import subprocess as sp
import sys
import tempfile
from pathlib import Path
from posixpath import join as urljoin
from urllib.parse import urlparse

USAGE_STR = """
bazel run <target>.publish -- release
"""

MAVEN_REPOS = {
    "snapshot": "{snapshot}",
    "release": "{release}",
    "local": "file:~/.m2/repository/",
}
JAR_PATH = "$JAR_PATH"
POM_FILE_PATH = "$POM_PATH"
SRCJAR_PATH = "$SRCJAR_PATH"


def main():
    args = _parse_args()
    repo_type = "local" if args.local else args.command
    maven_url = MAVEN_REPOS[repo_type]
    pom = ElementTree.parse(POM_FILE_PATH).getroot()
    group_id = _parse_pom(pom, "ns0:groupId")
    artifact_id = _parse_pom(pom, "ns0:artifactId")
    version = _parse_pom(pom, "ns0:version")
    version_snapshot_regex = """^[0-9|a-f|A-F]{40}$|.*-SNAPSHOT$"""
    version_release_regex = """^[0-9]+.[0-9]+.[0-9]+(-[a-zA-Z0-9]+)*$"""
    if (
        repo_type == "snapshot"
        and len(re.findall(version_snapshot_regex, version)) == 0
    ):
        raise ValueError(
            "Invalid version: {}. An artifact uploaded to a {} repository "
            "must have a version which complies to this regex: {}".format(
                version, repo_type, version_snapshot_regex
            )
        )
    if repo_type == "release" and len(re.findall(version_release_regex, version)) == 0:
        raise ValueError(
            "Invalid version: {}. An artifact uploaded to a {} repository "
            "must have a version which complies to this regex: {}".format(
                version, repo_type, version_release_regex
            )
        )
    curl_opts = _curl_options(maven_url, args.netrc)

    filename_base = "{coordinates}/{artifact}/{version}/{artifact}-{version}".format(
        coordinates=group_id.replace(".", "/"), version=version, artifact=artifact_id
    )

    upload(maven_url, curl_opts, JAR_PATH, filename_base + ".jar")
    if args.gpg:
        upload(maven_url, curl_opts, sign(JAR_PATH), filename_base + ".jar.asc")
    upload(maven_url, curl_opts, POM_FILE_PATH, filename_base + ".pom")
    if args.gpg:
        upload(maven_url, curl_opts, sign(POM_FILE_PATH), filename_base + ".pom.asc")
    if os.path.exists(SRCJAR_PATH):
        upload(maven_url, curl_opts, SRCJAR_PATH, filename_base + "-sources.jar")
        if args.gpg:
            upload(
                maven_url,
                curl_opts,
                sign(SRCJAR_PATH),
                filename_base + "-sources.jar.asc",
            )
        # TODO(vmax): use real Javadoc instead of srcjar
        upload(maven_url, curl_opts, SRCJAR_PATH, filename_base + "-javadoc.jar")
        if args.gpg:
            upload(
                maven_url,
                curl_opts,
                sign(SRCJAR_PATH),
                filename_base + "-javadoc.jar.asc",
            )

    with tempfile.NamedTemporaryFile(mode="wt", delete=True) as pom_md5:
        pom_md5.write(md5(POM_FILE_PATH))
        pom_md5.flush()
        upload(maven_url, curl_opts, pom_md5.name, filename_base + ".pom.md5")

    with tempfile.NamedTemporaryFile(mode="wt", delete=True) as pom_sha1:
        pom_sha1.write(sha1(POM_FILE_PATH))
        pom_sha1.flush()
        upload(maven_url, curl_opts, pom_sha1.name, filename_base + ".pom.sha1")

    with tempfile.NamedTemporaryFile(mode="wt", delete=True) as jar_md5:
        jar_md5.write(md5(JAR_PATH))
        jar_md5.flush()
        upload(maven_url, curl_opts, jar_md5.name, filename_base + ".jar.md5")

    with tempfile.NamedTemporaryFile(mode="wt", delete=True) as jar_sha1:
        jar_sha1.write(sha1(JAR_PATH))
        jar_sha1.flush()
        upload(maven_url, curl_opts, jar_sha1.name, filename_base + ".jar.sha1")

    if os.path.exists(SRCJAR_PATH):
        with tempfile.NamedTemporaryFile(mode="wt", delete=True) as srcjar_md5:
            srcjar_md5.write(md5(SRCJAR_PATH))
            srcjar_md5.flush()
            upload(
                maven_url,
                curl_opts,
                srcjar_md5.name,
                filename_base + "-sources.jar.md5",
            )
            # TODO(vmax): use checksum of real Javadoc instead of srcjar
            upload(
                maven_url,
                curl_opts,
                srcjar_md5.name,
                filename_base + "-javadoc.jar.md5",
            )

        with tempfile.NamedTemporaryFile(mode="wt", delete=True) as srcjar_sha1:
            srcjar_sha1.write(sha1(SRCJAR_PATH))
            srcjar_sha1.flush()
            upload(
                maven_url,
                curl_opts,
                srcjar_sha1.name,
                filename_base + "-sources.jar.sha1",
            )
            # TODO(vmax): use checksum of real Javadoc instead of srcjar
            upload(
                maven_url,
                curl_opts,
                srcjar_sha1.name,
                filename_base + "-javadoc.jar.sha1",
            )


def _parse_pom(pom, elem_name):
    namespaces = {"ns0": "http://maven.apache.org/POM/4.0.0"}
    elem = pom.find(elem_name, namespaces)
    if elem is None or len(elem.text) == 0:
        raise Exception(f"Could not get {elem_name} from pom.xml")
    return elem.text


def sha1(fn):
    with open(fn, "rb") as f:
      return hashlib.sha1(f.read()).hexdigest()


def md5(fn):
    with open(fn, "rb") as f:
        return hashlib.md5(f.read()).hexdigest()


def upload(url, curl_opts, local_fn, remote_fn):
    print(f"  publishing {remote_fn}")
    u = urlparse(url)
    if u.scheme == "file":
        destination = Path(u.path).expanduser() / remote_fn
        destination.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(src=local_fn, dst=destination)
    else:
        failed = False
        try:
            upload_status_code = (
                sp.check_output(
                    [
                        "curl",
                        "--silent",
                        "--output",
                        "/dev/stderr",
                        "--write-out",
                        "%{http_code}",
                        *curl_opts,
                        "--upload-file",
                        local_fn,
                        urljoin(url, remote_fn),
                    ]
                )
                .decode()
                .strip()
            )
        except Exception:
            # Catch the exception to avoid credential getting logged
            failed = True
        if failed:
            raise Exception("upload of {} to {} failed".format(local_fn, urljoin(url, remote_fn)))
        if upload_status_code not in {"200", "201"}:
            raise Exception(
                "upload of {} failed, got HTTP status code {}".format(
                    local_fn, upload_status_code
                )
            )


def sign(fn):
    # TODO(vmax): current limitation of this functionality
    # is that gpg key should already be present in keyring
    # and should not require passphrase
    asc_file = tempfile.mktemp()
    sp.check_call(["gpg", "--detach-sign", "--armor", "--output", asc_file, fn])
    return asc_file


def _curl_options(maven_url, netrc):
    if urlparse(maven_url).scheme == "file":
        return None
    elif netrc:
        return ["--netrc"]
    else:
        username, password = os.getenv("SONATYPE_USERNAME"), os.getenv(
            "SONATYPE_PASSWORD"
        )
        if not username:
            raise ValueError(
                "Error: username should be passed via $SONATYPE_USERNAME env variable"
            )
        if not password:
            raise ValueError(
                "Error: password should be passed via $SONATYPE_PASSWORD env variable"
            )
        return ["-u", f"{username}:{password}"]


def _parse_args():
    class Formatter(
        argparse.ArgumentDefaultsHelpFormatter, argparse.RawTextHelpFormatter
    ):
        pass

    parser = argparse.ArgumentParser(description=USAGE_STR, formatter_class=Formatter)
    parser.add_argument("command", choices=["release", "snapshot"])
    parser.add_argument(
        "--gpg", action="store_true", default=False, help="Sign artifact using gpg"
    )
    parser.add_argument(
        "--netrc",
        action="store_true",
        default=False,
        help="Use .netrc for authentication",
    )
    parser.add_argument(
        "--local",
        action="store_true",
        default=False,
        help="Publish to local M2 repo (~/.m2/repository/).",
    )
    return parser.parse_args()


if __name__ == "__main__":
    main()
