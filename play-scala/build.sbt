name := """play-scala-ws-mgdb"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "0.12-RC1"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
