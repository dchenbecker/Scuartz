import sbt._

class ScuartzBuild(info : ProjectInfo) extends DefaultProject(info) {
  val quartz = "org.quartz-scheduler" % "quartz" % "1.8.4"
  val specs = "org.scala-tools.testing" % "specs" % "1.6.2.1"
  val slf4j = "org.slf4j" % "slf4j-simple" % "1.6.1"
}
