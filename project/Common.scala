import sbt.Keys._
import sbt._
import com.typesafe.sbt.SbtGit.versionWithGit

/** Common settings for PGP plugin. */
object PgpCommonSettings extends AutoPlugin {

  override def requires = plugins.IvyPlugin
  override def trigger = allRequirements

  object autoImport {
    // Dependencies
    val gigahorseOkhttp = "com.eed3si9n" %% "gigahorse-okhttp" % "0.3.0"
    val bouncyCastlePgp = "org.bouncycastle" % "bcpg-jdk15on" % "1.51"
    val specs2 = "org.specs2" %% "specs2-core" % "3.8.9"
    val sbtIo  = "org.scala-sbt" %% "io" % "1.0.0-M11"
    val parserCombinators = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.5"
    val sbtCoreNext = Def.setting {
      Defaults.sbtPluginExtra("org.scala-sbt" % "sbt-core-next" % "0.1.1", sbtBinaryVersion.value, scalaBinaryVersion.value)
    }
  }

  override def projectSettings =
    Seq(
      organization := "com.jsuereth",
      scalacOptions in Compile := Seq("-feature", "-deprecation", "-Xlint"),
      publishMavenStyle := false,
      publishTo := {
        val scalasbt = "http://repo.scala-sbt.org/scalasbt/"
        val (name, u) = if (version.value.contains("-SNAPSHOT")) ("sbt-plugin-snapshots", scalasbt+"sbt-plugin-snapshots")
        else ("sbt-plugin-releases", scalasbt+"sbt-plugin-releases")
        Some(Resolver.url(name, url(u))(Resolver.ivyStylePatterns))
      }
    ) ++ Bintray.settings
}
