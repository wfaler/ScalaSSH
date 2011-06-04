import sbt._

class BowlerParentProject(info: ProjectInfo) extends DefaultProject(info){	
  val jsch = "com.jcraft" % "jsch" % "0.1.44"
  val specs2 = "org.specs2" % "specs2_2.9.0" % "1.3" % "test"


  def specs2Framework = new TestFramework("org.specs2.runner.SpecsFramework")
  override def testFrameworks = super.testFrameworks ++ Seq(specs2Framework)
	
  val jschRepo ="Jsch Repo" at "http://jsch.sourceforge.net/maven2/"
  val scalaToolsRepo = "Scala-Tools repo" at "http://scala-tools.org/repo-releases/"
	
}