import ProjectInfo._
import kevinlee.sbt.SbtCommon.crossVersionProps
import kevinlee.semver.{Major, Minor, SemanticVersion}
import org.scoverage.coveralls.Imports.CoverallsKeys.coverallsTokenFile

ThisBuild / scalaVersion     := ProjectScalaVersion
ThisBuild / version          := ProjectVersion
ThisBuild / organization     := "kevinlee"
ThisBuild / crossScalaVersions := CrossScalaVersions
ThisBuild / developers   := List(
      Developer("Kevin-Lee", "Kevin Lee", "kevin.code@kevinlee.io", url("https://github.com/Kevin-Lee"))
    )

lazy val justFp = (project in file("."))
  .enablePlugins(DevOopsGitReleasePlugin)
  .settings(
    name := "just-fp"
  , description  := "Just FP Lib"
  , unmanagedSourceDirectories in Compile ++= {
      val sharedSourceDir = (baseDirectory in ThisBuild).value / "src/main"
      if (scalaVersion.value.startsWith("2.13") || scalaVersion.value.startsWith("2.12"))
        Seq(sharedSourceDir / "scala-2.12_2.13")
      else
        Seq(sharedSourceDir / "scala-2.10_2.11")
    }
  , resolvers ++= Seq(
        Resolver.sonatypeRepo("releases")
      , Deps.hedgehogRepo
      )
  , addCompilerPlugin("org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full)
  , libraryDependencies := Deps.hedgehogLibs ++
      crossVersionProps(Seq.empty[ModuleID], SemanticVersion.parseUnsafe(scalaVersion.value)) {
        case (Major(2), Minor(10)) =>
          libraryDependencies.value.filterNot(m => m.organization == "org.wartremover" && m.name == "wartremover")
        case x =>
          libraryDependencies.value
      }
  , wartremoverErrors in (Compile, compile) ++= commonWarts((scalaBinaryVersion in update).value)
  , wartremoverErrors in (Test, compile) ++= commonWarts((scalaBinaryVersion in update).value)
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
  , coverallsTokenFile := Option(s"""${Path.userHome.absolutePath}/.coveralls-credentials""")
  /* } Coveralls */
)
