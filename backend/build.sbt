
name := "backend"

version := "1.0"

scalaVersion := "2.12.2"


resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
resolvers += "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

// https://mvnrepository.com/artifact/org.scalatest/scalatest
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0-RC1" % Test

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.7.1",
  "org.webjars" % "bootstrap" % "4.1.3",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.17.0-play27"
  //  "org.reactivemongo" %% "reactivemongo" % "0.17.0"
)
