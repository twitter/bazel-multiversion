#!$PYTHON_PATH

import argparse
import xml.etree.ElementTree as ET
from xml.dom import minidom

USAGE_STR = """This program generates POM file for publishing to Maven.
"""


MAVEN_SCOPES = ["compile", "provided", "runtime", "test"]


def main():
    args = _parse_args()
    version = args.version
    ET.register_namespace("", "http://maven.apache.org/POM/4.0.0")
    ET.register_namespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")
    pom = ET.Element(
        "{http://maven.apache.org/POM/4.0.0}project",
        attrib={
            "xmlns:xsi": "http://www.w3.org/2001/XMLSchema-instance",
            "xsi:schemaLocation": "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd",
        },
    )
    pom.append(_elem_text("modelVersion", "4.0.0"))
    pom.append(_elem_text("name", args.project_name))
    pom.append(_elem_text("description", args.project_description))
    pom.append(_elem_text("url", args.project_url))
    pom.append(_licenses(args))
    pom.append(_scm(args))
    pom.append(_elem_text("groupId", args.group_id))
    pom.append(_elem_text("artifactId", args.artifact_id))
    pom.append(_elem_text("version", version))
    pom.append(_dependencies(args, version))
    with open(args.output_file, "w") as f:
        f.write(_pretty_print(pom))


def _dependencies(args, version):
    dependencies = ET.Element("dependencies")
    coordinates = (
        args.target_deps_coordinates.split(";") if args.target_deps_coordinates else []
    )
    for dep in coordinates:
        dep_coord = _parse_maven_coordinates(dep)
        dep_version = _dependency_version(dep_coord["version"], version)
        dependency = ET.Element("dependency")
        dependency.append(_elem_text("groupId", dep_coord["group_id"]))
        dependency.append(_elem_text("artifactId", dep_coord["artifact_id"]))
        if "classifier" in dep_coord:
            dep_classifier = dep_coord["classifier"]
            if dep_classifier in MAVEN_SCOPES:
                dependency.append(_elem_text("scope", dep_classifier))
            else:
                dependency.append(_elem_text("classifier", dep_classifier))

        dependency.append(_elem_text("version", dep_version))
        dependencies.append(dependency)
    return dependencies


def _dependency_version(original_version, project_version):
    if original_version == "{pom_version}":
        return project_version
    return original_version


def _parse_maven_coordinates(coordinates_string):
    """
    Given a string containing a standard Maven coordinate (g:a:[p:[c:]]v),
    returns a Maven artifact map (see above).
    See also https://github.com/bazelbuild/rules_jvm_external/blob/4.3/specs.bzl
    """
    parts = coordinates_string.split(":")
    group_id, artifact_id = parts[0:2]
    if len(parts) == 3:
        version = parts[2]
        result = dict(group_id=group_id, artifact_id=artifact_id, version=version)
    elif len(parts) == 4:
        packaging = parts[2]
        version = parts[3]
        result = dict(group_id=group_id, artifact_id=artifact_id, packaging=packaging, version=version)
    elif len(parts) == 5:
        packaging = parts[2]
        classifier = parts[3]
        version = parts[4]
        result = dict(group_id=group_id, artifact_id=artifact_id, packaging=packaging, classifier=classifier, version=version)
    else:
        raise ValueError("failed to parse '{}'".format(coordinates_string))
    return result


def _licenses(args):
    licenses = ET.Element("licenses")
    license = ET.Element("license")
    info = _get_license_info(args.license)
    license.append(_elem_text("name", info["name"]))
    license.append(_elem_text("url", info["url"]))
    licenses.append(license)
    return licenses


def _get_license_info(license):
    if license == "apache" or license == "Apache-2.0":
        return {
            "name": "Apache-2.0",
            "url": "https://www.apache.org/licenses/LICENSE-2.0.txt",
        }
    if license == "mit" or license == "MIT":
        return {
            "name": "MIT",
            "url": "https://opensource.org/licenses/MIT",
        }
    raise ValueError("expected Apache-2.0 or MIT")


def _scm(args):
    scm = ET.Element("scm")
    scm.append(_elem_text("connection", args.scm_url))
    scm.append(_elem_text("url", args.scm_url))
    return scm


def _elem_text(name, text):
    child = ET.Element(name)
    child.text = text
    return child


def _pretty_print(elem):
    return minidom.parseString(ET.tostring(elem)).toprettyxml(indent="  ")


def _parse_args():
    class Formatter(
        argparse.ArgumentDefaultsHelpFormatter, argparse.RawTextHelpFormatter
    ):
        pass

    parser = argparse.ArgumentParser(description=USAGE_STR, formatter_class=Formatter)
    parser.add_argument("--group_id", type=str, required=True)
    parser.add_argument("--artifact_id", type=str, required=True)
    parser.add_argument("--version", type=str, required=True)
    parser.add_argument("--project_name", type=str)
    parser.add_argument("--project_description", type=str)
    parser.add_argument("--project_url", type=str)
    parser.add_argument("--license", type=str)
    parser.add_argument("--scm_url", type=str)
    parser.add_argument("--target_deps_coordinates", type=str, default="")
    parser.add_argument("--output_file", type=str)
    return parser.parse_args()


if __name__ == "__main__":
    main()
