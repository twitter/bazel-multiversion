rules_jvm_export
================

## Publishing to external repositories

```python
load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library")
load("@twitter_rules_jvm_export//jvm_export:jvm_export.bzl", "jvm_export")

scala_library(
    name = "io1",
    srcs = ["IO1.scala"],
    tags = [
        "maven_coordinates=com.example:io1:{pom_version}",
    ],
    deps = [
        "@maven//:3rdparty/jvm/com/google/guava/guava",
    ],
)

jvm_export(
    name = "io1.publish",
    artifacts = [":io1"],
    project_name = "IO 1",
    project_description = "IO library",
    project_url = "http://example.com/",
    license = "Apache-2.0",
    scm_url = "http://github.com/",
    release_repo = "https://localhost/",
    snapshot_repo = "https://localhost/",
    python_path = "/usr/bin/env python",
)
```

To publish using `.netrc` authentication:

```bash
$ bazel run //:io1.publish --@twitter_rules_jvm_export//jvm_export:version=0.1.0-alpha1 -- release --netrc
```

To publish with environment variable authentication, set `SONATYPE_USERNAME` and `SONATYPE_PASSWORD`, then run:

```bash
$ bazel run //:io1.publish --@twitter_rules_jvm_export//jvm_export:version=0.1.0-alpha1 -- release
```

To publish to local `~/.m2/repository/`:

```bash
$ bazel run //:io1.publish --@twitter_rules_jvm_export//jvm_export:version=0.1.0-alpha1 -- release --local
```

## Credits

- rules_jvm_export was inspired by [bazel-distribution][bazel-distribution] developed by Vaticle.
  In fact, the rules implementation is based off of bazel-distribution for the most part.
  Instead of using Kotlin, we have reimplemented the helper programs to use Python instead.
  Also instead of assembling JAR files, rules_jvm_export uses the normal `build` artifacts by default.
- [rules_jvm_external][rules_jvm_external] was also an inspiration for its simplicity.

  [bazel-distribution]: https://github.com/vaticle/bazel-distribution
  [rules_jvm_external]: https://github.com/bazelbuild/rules_jvm_external
