import sbt.Keys.scalaBinaryVersion
import sbt.Keys.update
import wartremover.WartRemover.autoImport.{Wart, Warts}

/**
  * @author Kevin Lee
  * @since 2018-05-21
  */
object ProjectInfo {

  val ProjectScalaVersion: String = "2.13.1"
  val CrossScalaVersions: Seq[String] = Seq("2.10.7", "2.11.12", "2.12.10", ProjectScalaVersion)

  val ProjectVersion: String = "1.3.2"

  def commonWarts(scalaBinaryVersion: String): Seq[wartremover.Wart] = scalaBinaryVersion match {
    case "2.10" =>
      Seq.empty
    case _ =>
    Warts.allBut(Wart.DefaultArguments, Wart.Overloading, Wart.Any, Wart.Nothing, Wart.NonUnitStatements)
  }

}
