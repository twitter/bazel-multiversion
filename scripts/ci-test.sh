#!/usr/bin/bash

# Exit on error. Append "|| true" if you expect an error.
set -o errexit
# Exit on error inside any functions or subshells.
set -o errtrace
# Do not allow use of undefined vars. Use ${VAR:-} to use an undefined VAR
set -o nounset
# Catch the error in case mysqldump fails (but gzip succeeds) in `mysqldump |gzip`
set -o pipefail
# Turn on traces, useful while debugging but commented out by default
# set -o xtrace

cd multiversion-example
bazel build tricky/...

rm -rf $HOME/.m2/repository/com/twitter/dpb/
bazel run export-example:io1.publish --@twitter_rules_jvm_export//jvm_export:version=0.1.0-alpha1 -- release --local
ls $HOME/.m2/repository/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1.pom
cat $HOME/.m2/repository/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1.pom | grep "29\.0"

ls $HOME/.m2/repository/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1.jar
ls $HOME/.m2/repository/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1-sources.jar
ls $HOME/.m2/repository/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1-javadoc.jar
