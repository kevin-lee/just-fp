logLevel := sbt.Level.Warn

addSbtPlugin("com.github.sbt"  % "sbt-ci-release"  % "1.5.12")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.20")
addSbtPlugin("ch.epfl.scala"   % "sbt-scalafix"    % "0.11.0")
addSbtPlugin("org.scalameta"   % "sbt-scalafmt"    % "2.5.0")
addSbtPlugin("org.scoverage"   % "sbt-scoverage"   % "2.0.8")
addSbtPlugin("org.scalameta"   % "sbt-mdoc"        % "2.3.2")
addSbtPlugin("io.kevinlee"     % "sbt-docusaur"    % "0.13.0")

val sbtDevOopsVersion = "2.24.0"
addSbtPlugin("io.kevinlee" % "sbt-devoops-scala"     % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-sbt-extra" % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-github"    % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-starter"   % sbtDevOopsVersion)
