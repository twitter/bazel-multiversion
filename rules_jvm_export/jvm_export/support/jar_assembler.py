#!$PYTHON_PATH

import argparse
from pathlib import Path
import zipfile
from zipfile import ZipFile

USAGE_STR = """This program aggregates JAR files for publishing to Maven.
"""


def main():
    args = _parse_args()
    print(str(args.jars))
    with ZipFile(args.output, "w") as output:
        if args.pom_file:
            pom_path = f"META-INF/maven/{args.group_id}/{args.artifact_id}/pom.xml"
            output.write(args.pom_file, pom_path)
        for jar_file in args.jars:
            with ZipFile(jar_file, "r") as jar:
                for entry in jar.infolist():
                    if entry.is_dir():
                        pass
                    elif entry.filename.startswith("META-INF/MANIFEST.MF"):
                        pass
                    elif entry.filename.startswith("META-INF/maven/"):
                        pass
                    else:
                        output.writestr(entry, entry.filename)


def _parse_args():
    class Formatter(
        argparse.ArgumentDefaultsHelpFormatter, argparse.RawTextHelpFormatter
    ):
        pass

    parser = argparse.ArgumentParser(description=USAGE_STR, formatter_class=Formatter)
    parser.add_argument("--output", required=True, type=str)
    parser.add_argument("--group_id", type=str)
    parser.add_argument("--artifact_id", type=str)
    parser.add_argument("--pom_file", type=Path)
    parser.add_argument("jars", nargs="+", type=Path)
    return parser.parse_args()


if __name__ == "__main__":
    main()
