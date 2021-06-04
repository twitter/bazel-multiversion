bazel-multiversion
==================

Alternative to bazelbuild/rules_jvm_external that supports multiple versions of
the same 3rdparty dependency.

## Getting started

```sh
# Step 1: Clone repo
git clone https://github.com/twitter/bazel-multiversion
cd bazel-multiversion

# Step 2: Build and run code with different versions of Guava
cd multiversion-example/
bazel build tricky/...
bazel run tricky/user/src/main/scala/bincompat:bin-needs-guava24
bazel run tricky/user/src/main/scala/bincompat:bin-needs-guava29
```

### Install bazel-multiversion

Download GraalVM native image from https://github.com/twitter/bazel-multiversion/releases,
and put `multiversion` in your `PATH`.

Make changes to `3rdparty.yaml`.
Next, regenerate the `3rdparty/jvm_deps.bzl` file inside the example:

```sh
$ multiversion export  --output-path=3rdparty/jvm_deps.bzl
```

## Building from source

See [DEVELOP](DEVELOP.md).

## License

Apache v2
