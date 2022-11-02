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

rm -rf /tmp/repo/
bazel run export-example:io1.publish --@twitter_rules_jvm_export//jvm_export:version=0.1.0-alpha1 -- release --publish_to=/tmp/repo
ls /tmp/repo/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1.pom
cat /tmp/repo/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1.pom | grep "29\.0"
dep_count=$(cat /tmp/repo/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1.pom | grep '<dependency>' | wc -l | tr -d ' ')
[[ $dep_count == "1" ]] || (echo "expected only one dependency" && cat /tmp/repo/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1.pom && exit 1)

ls /tmp/repo/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1.jar
ls /tmp/repo/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1-sources.jar
ls /tmp/repo/com/twitter/dpb/io1/0.1.0-alpha1/io1-0.1.0-alpha1-javadoc.jar

bazel run export-example:io2.publish --@twitter_rules_jvm_export//jvm_export:version=0.1.0-alpha1 -- release --publish_to=/tmp/repo
cat /tmp/repo/com/twitter/dpb/io2/0.1.0-alpha1/io2-0.1.0-alpha1.pom | tr -d ' ' | tr -d '\n' | grep '<dependency><groupId>com.twitter.dpb</groupId><artifactId>io1</artifactId><version>0.1.0-alpha1</version></dependency>'
bazel run export-example:io3.publish --@twitter_rules_jvm_export//jvm_export:version=0.1.0-alpha1 -- release --publish_to=/tmp/repo
cat /tmp/repo/com/twitter/dpb/io3/0.1.0-alpha1/io3-0.1.0-alpha1.pom | tr -d ' ' | tr -d '\n' | grep '<dependency><groupId>com.twitter.dpb</groupId><artifactId>io2</artifactId><classifier>abc</classifier><version>0.1.0-alpha1</version></dependency>'
bazel run export-example:io5.publish --@twitter_rules_jvm_export//jvm_export:version=0.1.0-alpha1 -- release --publish_to=/tmp/repo
cat /tmp/repo/com/twitter/dpb/io5/0.1.0-alpha1/io5-0.1.0-alpha1.pom | tr -d ' ' | tr -d '\n' | grep '<dependency><groupId>com.twitter.dpb</groupId><artifactId>io4</artifactId><scope>provided</scope><version>0.1.0-alpha1</version></dependency>'
