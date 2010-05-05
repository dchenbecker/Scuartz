import sbt._

class ScuartzBuild(info : ProjectInfo) extends DefaultProject(info) {
  val quartz = "org.quartz-scheduler" % "quartz" % "1.8.0"
  val specs = "org.scala-tools.testing" % "specs" % "1.6.2.1"
}
