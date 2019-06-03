import sbt._

object Deps {
  val hedgehogVersion = "f120baa58d2362761de3d03512ccd5d0199f07fc"
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
