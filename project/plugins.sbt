logLevel := sbt.Level.Warn

addSbtPlugin("com.geirsson"    % "sbt-ci-release"  % "1.5.7")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.13")
addSbtPlugin("org.scoverage"   % "sbt-scoverage"   % "1.6.1")
addSbtPlugin("org.scoverage"   % "sbt-coveralls"   % "1.2.7")
addSbtPlugin("org.scalameta"   % "sbt-mdoc"        % "2.3.2")
addSbtPlugin("io.kevinlee"     % "sbt-docusaur"    % "0.12.0")
addSbtPlugin("ch.epfl.scala"   % "sbt-scalafix"    % "0.9.28")

val sbtDevOopsVersion = "2.23.0"
addSbtPlugin("io.kevinlee" % "sbt-devoops-scala"     % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-sbt-extra" % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-github"    % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-starter"    % sbtDevOopsVersion)
