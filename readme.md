# bazel-multideps

Alternative to bazelbuild/rules_jvm_external that supports multiple versions of
the same 3rdparty dependency.

## Getting started

```sh
bazel build tricky/...
# Run binary with Guava version 24
bazel run tricky/user/src/main/scala/bincompat:NeedsGuava24
# Run binary with Guava version 29
bazel run tricky/user/src/main/scala/bincompat:NeedsGuava29

# (optional) regenerate 3rdparty/jvm_deps.bzl file after changing 3rdparty.yaml
./sbt multideps/run export
```
