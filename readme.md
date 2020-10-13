# bazel-multideps

Alternative to bazelbuild/rules_jvm_external that supports multiple versions of
the same 3rdparty dependency.

## Getting started

```sh
# Step 1: Clone repo
git clone https://github.com/olafurpg/bazel-multideps
cd bazel-multideps

# Step 2: Build and run code with different versions of Guava
bazel build tricky/...
bazel run tricky/user/src/main/scala/bincompat:NeedsGuava24
bazel run tricky/user/src/main/scala/bincompat:NeedsGuava29

# (optional) Step 3: make changes to 3rdparty.yaml and regenerate 3rdparty/jvm_deps.bzl
# NOTE: This step can become a native-imagine binary with instant startup.
./sbt multideps/run export
```
