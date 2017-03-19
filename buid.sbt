import sbt.Keys._

name := "featurebit"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test" withSources()

libraryDependencies += "com.typesafe" % "config" % "1.2.1"