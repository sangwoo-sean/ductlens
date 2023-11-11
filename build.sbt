inThisBuild(
  Seq(
    scalaVersion := "3.3.1",
  )
)

lazy val root = (project in file("."))
  .settings(
    name := "ductlens",
    version          := "0.1.0-SNAPSHOT",
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    wartremoverWarnings ++= Warts.all,
  )

lazy val backend = (project in file("backend"))
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.19",
      "dev.zio" %% "zio-test" % "2.0.19" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
