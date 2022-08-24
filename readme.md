bazel-multiversion
==================

Transitive Maven artifact resolution that supports multiple versions of
the same 3rdparty dependency (an alternative to bazelbuild/rules_jvm_external).

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

## Usage

Download GraalVM native image from https://github.com/twitter/bazel-multiversion/releases,
and put `multiversion` in your `PATH`.

There are 3 ways to configure the dependencies.

### BUILDish DSL

BUILDish DSL allows the configuration of dependencies using `BUILD` files
similar to what is currently done using Pants.
See `multiversion-example/3rdparty/jvm/` for the examples.

To regenerate the `3rdparty/jvm_deps.bzl` file,
run the following inside the `multiversion-example/` directory:

```sh
$ multiversion import-build --output-path=3rdparty/jvm_deps.bzl
```

#### multiversion_config

Place this at `3rdparty/jvm/BUILD`:

```python
multiversion_config(
  scala_versions = ["2.12.12"],
)
```

Currently only one Scala version can be specified.

#### jar_library

Place `BUILD` files under `3rdparty/jvm/com/google/guava/BUILD` where `com/google/guava` matches the groupid of the external library:

```python
jar_library(
  name = "guava",
  jars = [
    jar(
      org = "com.google.guava",
      name = "guava",
      rev = "29.0-jre",
    )
  ],
)

jar_library(
  name = "guava-24",
  jars = [
    jar(
      org = "com.google.guava",
      name = "guava",
      rev = "24.1.1-jre",
    ),
    jar(
      org = "org.checkerframework",
      name = "checker-compat-qual",
      rev = "2.0.0",
    )
  ],
)
```

For Scala libraries:

```python
jar_library(
  name = "scalameta",
  jars = [
    scala_jar(
      org = "org.scalameta",
      name = "scalameta",
      rev = "4.0.0",
    )
  ],
)
```

Next, reference the artifacts in the `BUILD` file with their label
(location of the Guava `BUILD` + `/` + name of the "target"):

```python
scala_library(
    name="hello",
    srcs=["Hello.scala"],
    deps=[
        "@maven//:3rdparty/jvm/com/google/guava/guava",
    ],
)
```

### Workspace setup

```python
load("//3rdparty:jvm_deps.bzl", "jvm_deps")
jvm_deps()
load("@maven//:jvm_deps.bzl", "load_jvm_deps")
load_jvm_deps()
```

### YAML

If you prefer to configure the dependencies in one file, create `3rdparty.yaml`
at the root of monorepo instead
To regenerate the `3rdparty/jvm_deps.bzl` file, 
runt the following inside the `multiversion-example/` directory:

```sh
$ multiversion export --output-path=3rdparty/jvm_deps.bzl
```

### Pants

```sh
$ multiversion pants-export --output-path=3rdparty/jvm_deps.bzl
```

## Publishing to external repositories

See [rules_jvm_export](rules_jvm_export/).

## Building from source

See [DEVELOP](DEVELOP.md).

## License

Copyright Twitter, Inc.

Licensed under the Apache License, Version 2.0
