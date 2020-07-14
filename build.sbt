import ProjectInfo._
import kevinlee.sbt.SbtCommon.crossVersionProps
import just.semver.SemVer
import SemVer.{Major, Minor}
import microsites.ConfigYml

val ProjectScalaVersion: String = "2.13.1"
val CrossScalaVersions: Seq[String] = Seq("2.10.7", "2.11.12", "2.12.11", ProjectScalaVersion)

def prefixedProjectName(name: String) = s"just-fp${if (name.isEmpty) "" else s"-$name"}"

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  skip in publish := true
)

val hedgehogVersionFor2_10 = "7bd29241fababd9a3e954fd38083ed280fc9e4e8"
val hedgehogVersion = "97854199ef795a5dfba15478fd9abe66035ddea2"
val hedgehogRepo: MavenRepository =
  "bintray-scala-hedgehog" at "https://dl.bintray.com/hedgehogqa/scala-hedgehog"

def hedgehogLibs(hedgehogVersion: String): Seq[ModuleID] = Seq(
    "qa.hedgehog" %% "hedgehog-core" % hedgehogVersion % Test
  , "qa.hedgehog" %% "hedgehog-runner" % hedgehogVersion % Test
  , "qa.hedgehog" %% "hedgehog-sbt" % hedgehogVersion % Test
  )

ThisBuild / scalaVersion     := ProjectScalaVersion
ThisBuild / version          := ProjectVersion
ThisBuild / organization     := "io.kevinlee"
ThisBuild / developers   := List(
    Developer("Kevin-Lee", "Kevin Lee", "kevin.code@kevinlee.io", url("https://github.com/Kevin-Lee"))
  )
ThisBuild / homepage := Some(url("https://github.com/Kevin-Lee/just-fp"))
ThisBuild / scmInfo :=
  Some(ScmInfo(
      url("https://github.com/Kevin-Lee/just-fp")
    , "git@github.com:Kevin-Lee/just-fp.git"
    ))

lazy val justFp = (project in file("."))
  .enablePlugins(DevOopsGitReleasePlugin)
  .settings(
    name := prefixedProjectName("")
  , description  := "Just FP Lib"
  )
  .settings(noPublish)
  .aggregate(core, docs)

lazy val core = (project in file("core"))
  .enablePlugins(DevOopsGitReleasePlugin)
  .settings(
    name := prefixedProjectName("core")
  , description  := "Just FP Lib - Core"
  , crossScalaVersions := CrossScalaVersions
  , unmanagedSourceDirectories in Compile ++= {
      val sharedSourceDir = baseDirectory.value / "src/main"
      if (scalaVersion.value.startsWith("2.13") || scalaVersion.value.startsWith("2.12"))
        Seq(sharedSourceDir / "scala-2.12_2.13")
      else
        Seq(sharedSourceDir / "scala-2.10_2.11")
    }
  , resolvers ++= Seq(
        hedgehogRepo
      )
  , addCompilerPlugin("org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full)
  , libraryDependencies :=
      crossVersionProps(List.empty, SemVer.parseUnsafe(scalaVersion.value)) {
        case (Major(2), Minor(10)) =>
          hedgehogLibs(hedgehogVersionFor2_10) ++
            libraryDependencies.value.filterNot(
              m => m.organization == "org.wartremover" && m.name == "wartremover"
            )
        case x =>
          hedgehogLibs(hedgehogVersion) ++
            libraryDependencies.value
      }
  /* Ammonite-REPL { */
  , libraryDependencies ++=
      (scalaBinaryVersion.value match {
        case "2.10" =>
          Seq.empty[ModuleID]
        case "2.11" =>
          Seq("com.lihaoyi" % "ammonite" % "1.6.7" % Test cross CrossVersion.full)
        case "2.12" =>
          Seq.empty[ModuleID] // TODO: add ammonite when it supports Scala 2.12.11
        case _ =>
          Seq("com.lihaoyi" % "ammonite" % "2.0.4" % Test cross CrossVersion.full)
      })
  , sourceGenerators in Test +=
      (scalaBinaryVersion.value match {
        case "2.10" =>
          task(Seq.empty[File])
        case "2.12" =>
          task(Seq.empty[File]) // TODO: add ammonite when it supports Scala 2.12.11
        case _ =>
          task {
            val file = (sourceManaged in Test).value / "amm.scala"
            IO.write(file, """object amm extends App { ammonite.Main.main(args) }""")
            Seq(file)
          }
      })
  /* } Ammonite-REPL */
//  , wartremoverErrors in (Compile, compile) ++= commonWarts((scalaBinaryVersion in update).value)
//  , wartremoverErrors in (Test, compile) ++= commonWarts((scalaBinaryVersion in update).value)
  , wartremoverErrors ++= commonWarts((scalaBinaryVersion in update).value)
  //      , wartremoverErrors ++= Warts.all
  , Compile / console / wartremoverErrors := List.empty
  , Compile / console / scalacOptions := (console / scalacOptions).value.filterNot(_.contains("wartremover"))
  , Test / console / wartremoverErrors := List.empty
  , Test / console / scalacOptions := (console / scalacOptions).value.filterNot(_.contains("wartremover"))
  , testFrameworks ++= Seq(TestFramework("hedgehog.sbt.Framework"))
  /* Bintray { */
  , bintrayPackageLabels := Seq("Scala", "Functional Programming", "FP")
  , bintrayVcsUrl := Some("""git@github.com:Kevin-Lee/just-fp.git""")
  , licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
  /* } Bintray */

  , initialCommands in console :=
      """import just.fp._; import just.fp.syntax._"""

  /* Coveralls { */
  , coverageHighlighting := (CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 10)) =>
      false
    case _ =>
      true
  })
  /* } Coveralls */
  )

lazy val docDir = file("docs")
lazy val docs = (project in docDir)
  .enablePlugins(MicrositesPlugin)
  .settings(noPublish)
  .settings(
    name := prefixedProjectName("docs")
  /* microsites { */
  , micrositeName := prefixedProjectName("")
  , micrositeAuthor := "Kevin Lee"
  , micrositeHomepage := "https://blog.kevinlee.io"
  , micrositeDescription := "Just FP"
  , micrositeGithubOwner := "Kevin-Lee"
  , micrositeGithubRepo := "just-fp"
  , micrositeBaseUrl := "/just-fp"
  , micrositeDocumentationUrl := s"${micrositeBaseUrl.value}/docs"
  , micrositePushSiteWith := GitHub4s
  , micrositeGithubToken := sys.env.get("GITHUB_TOKEN")
//  , micrositeTheme := "pattern"
  , micrositeHighlightTheme := "atom-one-light"
  , micrositeGitterChannel := false
  , micrositeGithubLinks := false
  , micrositeShareOnSocial := false
  , micrositeHighlightLanguages ++= Seq("shell")

  , micrositeConfigYaml := ConfigYml(
      yamlPath = Some(docDir / "microsite" / "_config.yml")
    )
  , micrositeImgDirectory := docDir / "microsite" / "img"
  , micrositeCssDirectory := docDir / "microsite" / "css"
  , micrositeSassDirectory := docDir / "microsite" / "sass"
  , micrositeJsDirectory := docDir / "microsite" / "js"
  , micrositeExternalLayoutsDirectory := docDir / "microsite" / "layouts"
  , micrositeExternalIncludesDirectory := docDir / "microsite" / "includes"
  , micrositeDataDirectory := docDir / "microsite" / "data"
  , micrositeStaticDirectory := docDir / "microsite" / "static"
  , micrositeExtraMdFilesOutput := docDir / "microsite" / "extra_md"
  , micrositePluginsDirectory := docDir / "microsite" / "plugins"

  /* } microsites */

  )
  .dependsOn(core)
