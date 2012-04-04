name := "tetravex"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.9.1"

exportJars := true

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

libraryDependencies += "commons-lang" % "commons-lang" % "2.6"

scalacOptions += "-deprecation"