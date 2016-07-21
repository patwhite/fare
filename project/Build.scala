import sbt._
import Keys._

import com.typesafe.sbt.SbtScalariform._
import scalariform.formatter.preferences._
import sbtunidoc.Plugin._
import play.sbt.PlayScala

object CoreBuild extends Build {

lazy val aliasConfig = alias(
  ("rebuild", ";clean; compile; package"),
  ("up", ";project backend; run")
)

lazy val SystemTest = config("st") extend Test

def alias(m: (String, String)*) = {
  val s = m.map(p => addCommandAlias(p._1, p._2)).reduce(_ ++ _)
  (_: Project).settings(s: _*)
}

  //////////////////////////////////////////////////////////////////////////////
// PROJECTS
//////////////////////////////////////////////////////////////////////////////
	lazy val core = Project(id = "core",
                              base = file("."),
                              configurations = Seq(IntegrationTest, SystemTest),
                              settings = commonSettings) aggregate (
                                app,
                                backend,
                                services,
                                data,
                              	common,
                                models) configure aliasConfig


    lazy val app = Project(id = "app",
                                     settings = commonSettings,
                                     configurations = Seq(IntegrationTest, SystemTest),
                                     base = file("app")).dependsOn(
                                       services,
                                       data,
                                       common,
                                       models
                                     ).enablePlugins(PlayScala)


	lazy val backend = Project(id = "backend",
                              	settings = commonSettings,
                              	configurations = Seq(IntegrationTest, SystemTest),
                                base = file("backend")).dependsOn(
                                   services,
                                   data,
                                   common,
                                   models)

	lazy val services = Project(id = "services",
                              	settings = commonSettings,
                              	configurations = Seq(IntegrationTest, SystemTest),
                                 base = file("services")).dependsOn(
                                 data,
                                 common,
                                 models)

    lazy val data = Project(id = "data",
                                settings = commonSettings,
                                configurations = Seq(IntegrationTest),
                                  base = file("data")).dependsOn(
                                  common,
                                  models)

	lazy val common = Project(id = "common",
                              	settings = commonSettings,
                                 base = file("common")).dependsOn(
                                 models)

	lazy val models = Project(id = "models",
                              	settings = commonSettings,
                                 base = file("models"))

//////////////////////////////////////////////////////////////////////////////
// PROJECT INFO
//////////////////////////////////////////////////////////////////////////////

  val ORGANIZATION    = "traego"
  val PROJECT_NAME    = "core"
  val PROJECT_VERSION = "0.1-SNAPSHOT"
  val SCALA_VERSION   = "2.11.8"


//////////////////////////////////////////////////////////////////////////////
// DEPENDENCY VERSIONS
//////////////////////////////////////////////////////////////////////////////

  val TYPESAFE_CONFIG_VERSION = "1.3.0"
  val SCALATEST_VERSION       = "2.2.6"
  val SLF4J_VERSION           = "1.7.21"
  val LOGBACK_VERSION         = "1.1.7"
  val SCALA_LOGGING_VERSION   = "3.4.0"

//////////////////////////////////////////////////////////////////////////////
// SHARED SETTINGS
//////////////////////////////////////////////////////////////////////////////

  lazy val commonSettings = Defaults.coreDefaultSettings ++
                            basicSettings ++
                            formatSettings

  lazy val basicSettings = Seq(
    version := PROJECT_VERSION,
    organization := ORGANIZATION,
    scalaVersion := SCALA_VERSION,
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases")
    ),

    libraryDependencies ++= Seq(
      "com.typesafe"                % "config"          % TYPESAFE_CONFIG_VERSION,
      "com.typesafe.scala-logging" %% "scala-logging"   % SCALA_LOGGING_VERSION,
      "org.slf4j"                   % "slf4j-api"       % SLF4J_VERSION,
      "ch.qos.logback"              % "logback-classic" % LOGBACK_VERSION % "runtime",
      "org.scalatest"              %% "scalatest"       % SCALATEST_VERSION % "test"
    ),

    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-feature",
      "-language:postfixOps",
      "-Xfatal-warnings",
      "-encoding",
      "utf8"
    ),

    // javaOptions += "-Djava.library.path=%s:%s".format(
    //  sys.props("java.library.path")
    //),

    fork in run := true,

    fork in Test := true,

    // parallelExecution in Test := false
    parallelExecution in Test := true
  )

  lazy val formatSettings = scalariformSettings ++ Seq(
    ScalariformKeys.preferences := FormattingPreferences()
      .setPreference(IndentWithTabs, false)
      .setPreference(IndentSpaces, 2)
      .setPreference(AlignParameters, false)
      .setPreference(DoubleIndentClassDeclaration, true)
      .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
      .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
      .setPreference(DanglingCloseParenthesis, Preserve)
      .setPreference(CompactControlReadability, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(PreserveSpaceBeforeArguments, true)
      .setPreference(SpaceBeforeColon, false)
      .setPreference(SpaceInsideBrackets, false)
      .setPreference(SpaceInsideParentheses, false)
      .setPreference(SpacesWithinPatternBinders, true)
      .setPreference(FormatXml, true)
  )



//credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

}
