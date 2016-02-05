name := """gnip-rule-validator"""

scalaVersion := "2.11.6"

packageSummary := "Gnip Rule Validator"

packageDescription := "Gnip Rule Validator using parser combinators"

maintainer := "Jeroen Rosenberg <jeroen@oxyme.com>"

//uncomment the following line if you want cross build
// crossScalaVersions := Seq("2.10.4", "2.11.6")

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ => false }

pomExtra :=
  <url>https://github.com/jeroenr/gnip-rule-validator</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://www.opensource.org/licenses/bsd-license.php</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:jeroenr/gnip-rule-validator.git</url>
      <connection>scm:git:git@github.com:jeroenr/gnip-rule-validator.git</connection>
    </scm>
    <developers>
      <developer>
        <id>jeroenr</id>
        <name>Jeroen Rosenberg</name>
      </developer>
    </developers>

scalacOptions ++=  Seq(
  "-deprecation",
  "-unchecked",
  "-feature"
)

libraryDependencies ++= Seq(
//  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "com.lihaoyi" %% "fastparse" % "0.3.4",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.lihaoyi" %% "fastparse" % "0.3.4"
)

scalariformSettings

//uncomment the following line if you want a java app packaging
// enablePlugins(JavaAppPackaging)
// enablePlugins(UniversalPlugin)
