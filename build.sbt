import ProjectInfo._
import kevinlee.sbt.SbtCommon.crossVersionProps
import kevinlee.semver.{Major, Minor, SemanticVersion}
import org.scoverage.coveralls.Imports.CoverallsKeys.coverallsTokenFile

ThisBuild / scalaVersion     := ProjectScalaVersion
ThisBuild / version          := ProjectVersion
ThisBuild / organization     := "kevinlee"

lazy val justFp = (project in file("."))
  .enablePlugins(DevOopsGitReleasePlugin)
  .settings(
    name := "just-fp"
  , description  := "Just FP Lib"
  , developers   := List(
      Developer("Kevin-Lee", "Kevin Lee", "kevin.code@kevinlee.io", url("https://github.com/Kevin-Lee"))
    )
  , crossScalaVersions := CrossScalaVersions
  , scalacOptions :=
    crossVersionProps(Seq.empty, SemanticVersion.parseUnsafe(scalaVersion.value)) {
      case (Major(2), Minor(12)) =>
        scalacOptions.value ++ commonScalacOptions
      case (Major(2), Minor(11)) =>
        (scalacOptions.value ++ commonScalacOptions).filter(_ != "-Ywarn-unused-import")
      case _ =>
        (scalacOptions.value ++ commonScalacOptions)
          .filter(option =>
            option != "-Ywarn-unused-import" && option != "-Ywarn-numeric-widen"
          )
    }.distinct

  , resolvers += Resolver.sonatypeRepo("releases")
  , addCompilerPlugin("org.typelevel" % "kind-projector" % "0.10.1" cross CrossVersion.binary)
  , wartremoverErrors in (Compile, compile) ++= commonWarts
  , wartremoverErrors in (Test, compile) ++= commonWarts
  , resolvers += Deps.hedgehogRepo
  , libraryDependencies ++= Deps.hedgehogLibs
  , dependencyOverrides ++= crossVersionProps(Seq.empty[ModuleID], SemanticVersion.parseUnsafe(scalaVersion.value)) {
      case (Major(2), Minor(10)) =>
        Seq("org.wartremover" %% "wartremover" % "2.3.7")
      case x =>
        Seq.empty
    }
  , testFrameworks ++= Seq(TestFramework("hedgehog.sbt.Framework"))

  /* Bintray { */
  , bintrayPackageLabels := Seq("Scala", "Functional Programming", "FP")
  , bintrayVcsUrl := Some("""git@github.com:Kevin-Lee/just-fp.git""")
  , licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
  /* } Bintray */

  , initialCommands in console := """import kevinlee.fp._"""

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

