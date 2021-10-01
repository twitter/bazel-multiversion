
### Publishing to Ivy local for local testing

```sh
$ sbt
> multiversion/publishLocal
....
> exit

$ cs launch com.twitter.multiversion:bazel-multiversion_2.12:0.1.0-SNAPSHOT --main-class multiversion.MultiVersion -- help
```


### Building a GraalVM native image for local testing

```sh
$ sbt
> multiversion/nativeImage
....
> exit

$ multiversion/target/native-image/multiversion help
```

### Building a fat JAR for local testing

```sh
$ sbt
> multiversion/assembly
....
[info] Assembly up to date: /Users/user/workspace/multiversion/multiversion/target/scala-2.12/bazel-multiversion-assembly-0.1.0-SNAPSHOT.jar

> exit

$ java -jar multiversion/target/scala-2.12/bazel-multiversion-assembly-0.1.0-SNAPSHOT.jar
```
