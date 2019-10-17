import sbt._

object Deps {
  val hedgehogVersion = "6dba7c9ba065e423000e9aa2b6981ce3d70b74cb"
  val hedgehogRepo: MavenRepository =
    "bintray-scala-hedgehog" at "https://dl.bintray.com/hedgehogqa/scala-hedgehog"

  val hedgehogLibs: Seq[ModuleID] = Seq(
      "hedgehog" %% "hedgehog-core" % hedgehogVersion % Test
    , "hedgehog" %% "hedgehog-runner" % hedgehogVersion % Test
    , "hedgehog" %% "hedgehog-sbt" % hedgehogVersion % Test
  )

}
