import ProjectInfo._
import kevinlee.sbt.SbtCommon.crossVersionProps
import just.semver.SemVer
import SemVer.{Major, Minor}

ThisBuild / semanticdbEnabled := false

ThisBuild / scalaVersion := props.ProjectScalaVersion
ThisBuild / version := ProjectVersion
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

libraryDependencies := (
  if (isDotty.value)
    libraryDependencies
      .value
      .filterNot(props.removeDottyIncompatible)
  else
    libraryDependencies.value
)
libraryDependencies := libraryDependencies.value.map(_.withDottyCompat(scalaVersion.value))

lazy val core = (project in file("core"))
//  .disablePlugins((if (isDotty.value) List(WartRemover) else Seq.empty[AutoPlugin]):_*)
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
      else if (scalaVersion.value.startsWith("3.0"))
        List(
          sharedSourceDir / "scala-2.12_3.0",
          sharedSourceDir / "scala-2.13_3.0",
        )
      else
        Seq.empty
    },
    scalacOptions :=
      (if (isDotty.value)
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
    resolvers ++= List(
      props.hedgehogRepo
    ),
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
          List("com.lihaoyi" % "ammonite" % "2.3.8-65-0f0d597f" % Test cross CrossVersion.full)
        case _      =>
          Seq.empty[ModuleID]
      }),
    libraryDependencies :=
      crossVersionProps(List.empty, SemVer.parseUnsafe(scalaVersion.value)) {
        case (Major(2), Minor(10), _) =>
          libs.hedgehogLibs(props.hedgehogVersionFor2_10) ++
            libraryDependencies.value.filterNot(m => m.organization == "org.wartremover" && m.name == "wartremover")
        case x                        =>
          libs.hedgehogLibs(props.hedgehogVersion) ++
            libraryDependencies.value
      },
    libraryDependencies := (
      if (isDotty.value) {
        libraryDependencies
          .value
          .filterNot(props.removeDottyIncompatible)
      } else
        (libraryDependencies).value
    ),
    libraryDependencies := libraryDependencies.value.map(_.withDottyCompat(scalaVersion.value)),
    Test / sourceGenerators +=
      (scalaBinaryVersion.value match {
        case "2.10"                   =>
          task(Seq.empty[File])
        case "2.11" | "2.12" | "2.13" =>
          task {
            val file = (Test / sourceManaged).value / "amm.scala"
            IO.write(file, """object amm extends App { ammonite.Main.main(args) }""")
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

//lazy val docDir = file("docs")
//lazy val docs = (project in docDir)
//  .enablePlugins(MicrositesPlugin)
//  .settings(noPublish)
//  .settings(
//    name := prefixedProjectName("docs")
//  /* microsites { */
//  , micrositeName := prefixedProjectName("")
//  , micrositeAuthor := "Kevin Lee"
//  , micrositeHomepage := "https://blog.kevinlee.io"
//  , micrositeDescription := "Just FP"
//  , micrositeGithubOwner := "Kevin-Lee"
//  , micrositeGithubRepo := "just-fp"
//  , micrositeBaseUrl := "/just-fp"
//  , micrositeDocumentationUrl := s"${micrositeBaseUrl.value}/docs"
//  , micrositePushSiteWith := GitHub4s
//  , micrositeGithubToken := sys.env.get("GITHUB_TOKEN")
////  , micrositeTheme := "pattern"
//  , micrositeHighlightTheme := "atom-one-light"
//  , micrositeGitterChannel := false
//  , micrositeGithubLinks := false
//  , micrositeShareOnSocial := false
//  , micrositeHighlightLanguages ++= List("shell")
//
//  , micrositeConfigYaml := ConfigYml(
//      yamlPath = Some(docDir / "microsite" / "_config.yml")
//    )
//  , micrositeImgDirectory := docDir / "microsite" / "img"
//  , micrositeCssDirectory := docDir / "microsite" / "css"
//  , micrositeSassDirectory := docDir / "microsite" / "sass"
//  , micrositeJsDirectory := docDir / "microsite" / "js"
//  , micrositeExternalLayoutsDirectory := docDir / "microsite" / "layouts"
//  , micrositeExternalIncludesDirectory := docDir / "microsite" / "includes"
//  , micrositeDataDirectory := docDir / "microsite" / "data"
//  , micrositeStaticDirectory := docDir / "microsite" / "static"
//  , micrositeExtraMdFilesOutput := docDir / "microsite" / "extra_md"
//  , micrositePluginsDirectory := docDir / "microsite" / "plugins"
//
//  /* } microsites */
//
//  )
//  .dependsOn(core)

lazy val docs = (project in file("generated-docs"))
  .enablePlugins(MdocPlugin, DocusaurPlugin)
  .settings(
    name := prefixedProjectName("docs"),
    mdocVariables := Map(
      "VERSION" -> (ThisBuild / version).value,
      "SUPPORTED_SCALA_VERSIONS" -> {
        val versions = props.CrossScalaVersions.map(v => s"`$v`")
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
      if (isDotty.value)
        libraryDependencies
          .value
          .filterNot(props.removeDottyIncompatible)
      else
        libraryDependencies.value
    ),
    libraryDependencies := libraryDependencies.value.map(_.withDottyCompat(scalaVersion.value))
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
  .settings(noPublish)
  .settings(noDoc)
  .aggregate(core)

lazy val props =
  new {

    val DottyVersions       = List("3.0.0-RC1", "3.0.0-RC2")
    val ProjectScalaVersion = "2.13.3"

    val removeDottyIncompatible: ModuleID => Boolean =
      m =>
        m.name == "wartremover" ||
          m.name == "ammonite" ||
          m.name == "kind-projector" ||
          m.name == "mdoc"

    val CrossScalaVersions: Seq[String] =
      (List(
        "2.10.7",
        "2.11.12",
        "2.12.12",
        "2.13.3"
      ) ++ DottyVersions).distinct

    val GitHubUsername = "Kevin-Lee"
    val RepoName       = "just-fp"
    val ProjectName    = RepoName

    val hedgehogVersionFor2_10        = "7bd29241fababd9a3e954fd38083ed280fc9e4e8"
    val hedgehogVersion               = "0.6.5"
    val hedgehogRepo: MavenRepository = "bintray-scala-hedgehog" at "https://dl.bintray.com/hedgehogqa/scala-hedgehog"
  }

lazy val libs =
  new {
    def hedgehogLibs(hedgehogVersion: String): Seq[ModuleID] = List(
      "qa.hedgehog" %% "hedgehog-core"   % hedgehogVersion % Test,
      "qa.hedgehog" %% "hedgehog-runner" % hedgehogVersion % Test,
      "qa.hedgehog" %% "hedgehog-sbt"    % hedgehogVersion % Test,
    )
  }

def prefixedProjectName(name: String) = s"${props.ProjectName}${if (name.isEmpty)
  ""
else
  s"-$name"}"
