name := "backend"

exportJars := true

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.7",
  "com.spatial4j" % "spatial4j" % "0.5",
  "com.github.nscala-time" %% "nscala-time" % "2.12.0",
  "com.github.scopt" %% "scopt" % "3.5.0"
)

javaOptions += "-XX:+UseConcMarkSweepGC"
javaOptions += "-XX:+UseParNewGC"
javaOptions += "-Xmx8g"