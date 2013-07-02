import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName         = "test-app"
  val appVersion      = "1.0-SNAPSHOT"

  // Import Postgres, Play SMTP Mailer, and BCrypt
  val appDependencies = Seq(
    "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
    "com.typesafe" %% "play-plugins-mailer" % "2.0.4",
    "org.mindrot" % "jbcrypt" % "0.3m"
  )

  // Include the 'binders' folder so 'routes' can use it
  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    routesImport += "binders._"
  )

}
