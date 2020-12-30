load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_test")

def _cross_names(name, cross_build):
  buf = []
  for cross in cross_build:
    suffix = "-".join([key + "_" + value for key, value in cross.items()])
    deps_map = {k: k + "_" + v for k, v in cross.items()}
    buf.append((deps_map, name + "-" + suffix))
  return buf

def cross_scala_library(name, cross_build, deps, exports,**kwargs):
  for cross, cross_name in _cross_names(name, cross_build):
    scala_library(
      name = cross_name,
      deps = [dep.format(**cross) for dep in deps],
      exports = [dep.format(**cross) for dep in exports],
      **kwargs
    )

