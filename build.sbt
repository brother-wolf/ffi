name := "ffi"

version := "0.0.1"

scalaVersion := "2.11.12"


lazy val IntegrationTest = config("it") extend Test

val release = true

val releaseOrDebug = if (release) { "release" } else { "debug" }

unmanagedClasspath in Test += baseDirectory.value / s"rust/target/$releaseOrDebug"
unmanagedClasspath in (Compile, runMain) += baseDirectory.value / s"rust/target/$releaseOrDebug"

lazy val root = (project in file(".")).configs(IntegrationTest)
  .settings(inConfig(IntegrationTest)(Defaults.testSettings) : _*)
  .settings(
    Defaults.itSettings,
    libraryDependencies ++=
      Seq(
        "org.scalatest" %% "scalatest" % "3.0.1" % "test, it",
        "com.github.jnr" % "jnr-ffi" % "2.2.0"
      )
  )


