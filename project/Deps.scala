import sbt._

object Deps {
  val hedgehogVersion = "7ab9f74d7ca93864170cda181f4b4909156c7413"
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

}
