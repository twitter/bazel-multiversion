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
bazel run tricky/user/src/main/scala/bincompat:NeedsGuava24
bazel run tricky/user/src/main/scala/bincompat:NeedsGuava29
cd ..

# (optional) Step 3: make changes to 3rdparty.yaml and regenerate 3rdparty/jvm_deps.bzl
# NOTE: This step can become a native-imagine binary with instant startup.
./sbt multiversion/run export
```

### Build a fat jar

```sh
$ sbt multiversion/assembly
[info] welcome to sbt 1.4.5 (Oracle Corporation Java 11.0.8)
[info] loading settings for project global-plugins from idea.sbt,metals.sbt ...
[info] loading global plugins from /Users/yic/.sbt/1.0/plugins
[info] loading settings for project multiversion-build from plugins.sbt ...
[info] loading project definition from /Users/yic/workspace/multiversion/project
[info] loading settings for project default-bcbf9b from build.sbt ...
[info] set current project to default-bcbf9b (in build file:/Users/yic/workspace/multiversion/)
[warn] there's a key that's not used by any other settings/tasks:
[warn]  
[warn] * ThisBuild / scalafixCaching
[warn]   +- /Users/yic/workspace/multiversion/build.sbt:6
[warn]  
[warn] note: a setting might still be used by a command; to exclude a key from this `lintUnused` check
[warn] either append it to `Global / excludeLintKeys` or call .withRank(KeyRanks.Invisible) on the key
[info] Strategy 'deduplicate' was applied to a file (Run the task at debug level to see details)
[info] Strategy 'discard' was applied to 27 files (Run the task at debug level to see details)
[info] Assembly up to date: /Users/yic/workspace/multiversion/multi
```
