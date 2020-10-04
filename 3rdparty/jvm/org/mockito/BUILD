package(default_visibility = ['//visibility:public'])
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

http_file(
  name = "org_mockito_mockito_all_1_10_19_jar",
  urls = ["https://repo1.maven.org/maven2/org/mockito/mockito-all/1.10.19/mockito-all-1.10.19.jar"],
  shas = ["539df70269cc254a58cccc5d8e43286b4a73bf30"],
)
java_import(
  name = ["org_mockito_mockito_all_1_10_19"],
  jars = [":org_mockito_mockito_all_1_10_19_jar"],
)

http_file(
  name = "org_mockito_mockito_core_3_5_13_jar",
  urls = ["https://repo1.maven.org/maven2/org/mockito/mockito-core/3.5.13/mockito-core-3.5.13.jar"],
  shas = ["4e99d47fe55166bc9017d7aaf7ecaf6183f54121"],
)
java_import(
  name = ["org_mockito_mockito_core_3_5_13"],
  jars = [":org_mockito_mockito_core_3_5_13_jar"],
)

http_file(
  name = "net_bytebuddy_byte_buddy_1_10_15_jar",
  urls = ["https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.10.15/byte-buddy-1.10.15.jar"],
  shas = ["259957c76d345cb3db5411f57a0654300aaca04c"],
)
java_import(
  name = ["net_bytebuddy_byte_buddy_1_10_15"],
  jars = [":net_bytebuddy_byte_buddy_1_10_15_jar"],
)

http_file(
  name = "net_bytebuddy_byte_buddy_agent_1_10_15_jar",
  urls = ["https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.10.15/byte-buddy-agent-1.10.15.jar"],
  shas = ["7f0cc687ee3177fd451d17b9b80325a8cdd1c7ff"],
)
java_import(
  name = ["net_bytebuddy_byte_buddy_agent_1_10_15"],
  jars = [":net_bytebuddy_byte_buddy_agent_1_10_15_jar"],
)

http_file(
  name = "org_objenesis_objenesis_3_1_jar",
  urls = ["https://repo1.maven.org/maven2/org/objenesis/objenesis/3.1/objenesis-3.1.jar"],
  shas = ["48f12deaae83a8dfc3775d830c9fd60ea59bbbca"],
)
java_import(
  name = ["org_objenesis_objenesis_3_1"],
  jars = [":org_objenesis_objenesis_3_1_jar"],
)
