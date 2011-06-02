package org.scalassh

import org.specs2.mutable.Specification
import java.io.File
import org.scalassh.SshSession._

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 02/06/2011
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */

class SimpleConnectionTest extends Specification with SpecSetup{
  before

  var result: CommandResult = null

  val emptyFile = new File(testDir, "SimpleConnectionTest.txt")
  emptyFile.createNewFile


  "Listing a directory" should {
    ssh("localhost"){
      result = Command("ls -ltr " + testDir.getAbsolutePath)
    }
    "return a valid exitCode"  in {
      result.exitCode must be_==(0)
    }

    "have SimpleConnectionTest.txt in the output" in{
      result.output must contain("SimpleConnectionTest.txt")
    }
  }
  after
}