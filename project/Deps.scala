import sbt._

object Deps {
  val hedgehogVersion = "d74f5bb31f26d3e3b7f7d0198b6e768a1ed20669"
  val hedgehogRepo =
    Resolver.url(
      "bintray-scala-hedgehog",
      url("https://dl.bintray.com/hedgehogqa/scala-hedgehog")
    )(Resolver.ivyStylePatterns)

  val hedgehogLibs: Seq[ModuleID] = Seq(
      "hedgehog" %% "hedgehog-core" % hedgehogVersion % Test
    , "hedgehog" %% "hedgehog-runner" % hedgehogVersion % Test
    , "hedgehog" %% "hedgehog-sbt" % hedgehogVersion % Test
  )

  val wartRemover: ModuleID = "org.wartremover" % "sbt-wartremover" % "2.2.1"

  val scoverage: ModuleID = "org.scoverage" % "sbt-scoverage" % "1.5.1"

}
