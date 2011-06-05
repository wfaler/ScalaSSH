name := "scalassh"

version := "0.0.1-SNAPSHOT"

organization := "org.scalassh"

scalaVersion := "2.9.0-1"

libraryDependencies ++= Seq(
	"com.jcraft" % "jsch" % "0.1.44",
	"org.specs2" % "specs2_2.9.0" % "1.3" % "test"
)

resolvers += "Jsch Repo" at "http://jsch.sourceforge.net/maven2/"