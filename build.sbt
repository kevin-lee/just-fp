ThisBuild / semanticdbEnabled := false

ThisBuild / scalaVersion := props.ProjectScalaVersion
ThisBuild / organization := "io.kevinlee"
ThisBuild / developers := List(
  Developer(
    props.GitHubUsername,
    "Kevin Lee",
    "kevin.code@kevinlee.io",
    url(s"https://github.com/${props.GitHubUsername}"),
  )
)
ThisBuild / homepage := url(s"https://github.com/${props.GitHubUsername}/${props.RepoName}").some
ThisBuild / scmInfo :=
  ScmInfo(
    url(s"https://github.com/${props.GitHubUsername}/${props.RepoName}"),
    s"git@github.com:${props.GitHubUsername}/${props.RepoName}.git"
  ).some
ThisBuild / licenses := List("MIT" -> url("http://opensource.org/licenses/MIT"))

ThisBuild / resolvers += "sonatype-snapshots" at s"https://${props.SonatypeCredentialHost}/content/repositories/snapshots"

libraryDependencies := (
  if (isScala3(scalaVersion.value))
    libraryDependencies
      .value
      .filterNot(props.removeDottyIncompatible)
  else
    libraryDependencies.value
)

lazy val core = (project in file("core"))
  .settings(
    name := prefixedProjectName("core"),
    semanticdbEnabled := false,
    description := "Just FP Lib - Core",
    crossScalaVersions := props.CrossScalaVersions,
    Compile / unmanagedSourceDirectories ++= {
      val sharedSourceDir = baseDirectory.value / "src/main"
      if (scalaVersion.value.startsWith("2.10") || scalaVersion.value.startsWith("2.11"))
        List(sharedSourceDir / "scala-2.10_2.11")
      else if (scalaVersion.value.startsWith("2.12"))
        List(
          sharedSourceDir / "scala-2.12_2.13",
          sharedSourceDir / "scala-2.12_3.0",
        )
      else if (scalaVersion.value.startsWith("2.13"))
        List(
          sharedSourceDir / "scala-2.12_2.13",
          sharedSourceDir / "scala-2.12_3.0",
          sharedSourceDir / "scala-2.13_3.0",
        )
      else if (isScala3(scalaVersion.value))
        List(
          sharedSourceDir / "scala-2.12_3.0",
          sharedSourceDir / "scala-2.13_3.0",
        )
      else
        Seq.empty
    },
    scalacOptions :=
      (if (isScala3(scalaVersion.value))
         List(
           "-source:3.0-migration",
           "-Ykind-projector",
           "-language:" + List(
             "dynamics",
             "existentials",
             "higherKinds",
             "reflectiveCalls",
             "experimental.macros",
             "implicitConversions",
           ).mkString(","),
         )
       else
         scalacOptions.value),
    addCompilerPlugin("org.typelevel" % "kind-projector" % "0.11.3" cross CrossVersion.full),
    /* Ammonite-REPL { */
    libraryDependencies ++=
      (scalaBinaryVersion.value match {
        case "2.10" =>
          Seq.empty[ModuleID]
        case "2.11" =>
          List("com.lihaoyi" % "ammonite" % "1.6.7-2-c28002d" % Test cross CrossVersion.full)
        case "2.12" =>
          List("com.lihaoyi" % "ammonite" % "2.3.8-58-aa8b2ab1" % Test cross CrossVersion.full)
        case "2.13" =>
          List("com.lihaoyi" % "ammonite" % "2.5.9" % Test cross CrossVersion.full)
        case _      =>
          Seq.empty[ModuleID]
      }),
    libraryDependencies ++= libs.hedgehogLibs,
    libraryDependencies := (
      if (isScala3(scalaVersion.value)) {
        libraryDependencies
          .value
          .filterNot(props.removeDottyIncompatible)
      } else
        (libraryDependencies).value
    ),
    Test / sourceGenerators +=
      (scalaBinaryVersion.value match {
        case "2.10"                   =>
          task(Seq.empty[File])
        case "2.11" | "2.12" =>
          task {
            val file = (Test / sourceManaged).value / "amm.scala"
            IO.write(file, """object amm extends App { ammonite.Main.main(args) }""")
            List(file)
          }
        case "2.13" =>
          task {
            val file = (Test / sourceManaged).value / "amm.scala"
            IO.write(file, """object amm extends App { ammonite.AmmoniteMain.main(args) }""")
            List(file)
          }
        case _                        =>
          task(Seq.empty[File])
      }),
    /* } Ammonite-REPL */
//  , Compile / compile / wartremoverErrors ++= commonWarts((update / scalaBinaryVersion).value)
//  , Test / compile / wartremoverErrors ++= commonWarts((update / scalaBinaryVersion).value)
//  , wartremoverErrors ++= commonWarts((update / scalaBinaryVersion).value)
    //      , wartremoverErrors ++= Warts.all
//  , Compile / console / wartremoverErrors := List.empty
    Compile / console / scalacOptions := (console / scalacOptions).value.filterNot(_.contains("wartremover")),
//    Test / console / wartremoverErrors := List.empty,
    Test / console / scalacOptions := (console / scalacOptions).value.filterNot(_.contains("wartremover")),
    testFrameworks ++= List(TestFramework("hedgehog.sbt.Framework")),
    /* Bintray { */
    licenses := List("MIT" -> url("http://opensource.org/licenses/MIT")),
    /* } Bintray */

    console / initialCommands :=
      """import just.fp._; import just.fp.syntax._""",
    /* Coveralls { */
    coverageHighlighting := (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, 10)) =>
        false
      case _             =>
        true
    })
    /* } Coveralls */
  )

lazy val docs = (project in file("generated-docs"))
  .enablePlugins(MdocPlugin, DocusaurPlugin)
  .settings(
    name := prefixedProjectName("docs"),
    mdocVariables := Map(
      "VERSION" -> {
        import sys.process._
        "git fetch --tags".!
        val tag = "git rev-list --tags --max-count=1".!!.trim
        s"git describe --tags $tag".!!.trim.stripPrefix("v")
      },
      "SUPPORTED_SCALA_VERSIONS" -> {
        val versions = props.CrossScalaVersions
          .map(CrossVersion.binaryScalaVersion)
          .map(binVer => s"`$binVer`")
        if (versions.length > 1)
          s"${versions.init.mkString(", ")} and ${versions.last}"
        else
          versions.mkString
      },
    ),
    docusaurDir := (ThisBuild / baseDirectory).value / "website",
    docusaurBuildDir := docusaurDir.value / "build",
    gitHubPagesOrgName := props.GitHubUsername,
    gitHubPagesRepoName := props.RepoName,
    libraryDependencies := (
      if (isScala3(scalaVersion.value))
        libraryDependencies
          .value
          .filterNot(props.removeDottyIncompatible)
      else
        libraryDependencies.value
    ),
  )
  .settings(noPublish)
  .dependsOn(core)

lazy val justFp = (project in file("."))
  .enablePlugins(DevOopsGitHubReleasePlugin)
  .settings(
    name := prefixedProjectName(""),
    description := "Just FP Lib",
    semanticdbEnabled := false,
    devOopsPackagedArtifacts := List(
      s"*/target/scala-*/${name.value}*.jar",
    ),
  )
  .settings(mavenCentralPublishSettings)
  .settings(noPublish)
  .settings(noDoc)
  .aggregate(core)

lazy val props =
  new {

    val DottyVersions       = List("3.0.0")
    val ProjectScalaVersion = "2.13.10"

    val removeDottyIncompatible: ModuleID => Boolean =
      m =>
        m.name == "wartremover" ||
          m.name == "ammonite" ||
          m.name == "kind-projector" ||
          m.name == "mdoc"

    val CrossScalaVersions: Seq[String] =
      (List(
        "2.11.12",
        "2.12.12",
        "2.13.10"
      ) ++ DottyVersions).distinct

    val GitHubUsername = "Kevin-Lee"
    val RepoName       = "just-fp"
    val ProjectName    = RepoName

    val SonatypeCredentialHost = "s01.oss.sonatype.org"
    val SonatypeRepository = s"https://$SonatypeCredentialHost/service/local"

    val hedgehogVersion        = "0.8.0"
  }

lazy val libs =
  new {
    lazy val hedgehogLibs: List[ModuleID] = List(
      "qa.hedgehog" %% "hedgehog-core"   % props.hedgehogVersion % Test,
      "qa.hedgehog" %% "hedgehog-runner" % props.hedgehogVersion % Test,
      "qa.hedgehog" %% "hedgehog-sbt"    % props.hedgehogVersion % Test,
    )
  }

lazy val mavenCentralPublishSettings: SettingsDefinition = List(
  /* Publish to Maven Central { */
  sonatypeCredentialHost := props.SonatypeCredentialHost,
  sonatypeRepository     := props.SonatypeRepository,
  /* } Publish to Maven Central */
)

def prefixedProjectName(name: String) = s"${props.ProjectName}${if (name.isEmpty)
  ""
else
  s"-$name"}"

def isScala3(scalaVersion: String): Boolean = scalaVersion.startsWith("3.")
