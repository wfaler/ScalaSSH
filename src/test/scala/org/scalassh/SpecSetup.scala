package org.scalassh

import java.io.File

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 02/06/2011
 * Time: 23:12
 * To change this template use File | Settings | File Templates.
 */

trait SpecSetup {
  val tmp = new File(System.getProperty("java.io.tmpdir"))
  val testDir = new File(tmp, "scalassh")


  def before: Unit ={
    testDir.mkdirs
  }

  def after: Unit = {
    recursiveDelete(testDir)
  }

  def recursiveDelete(dir: File): Unit = {

    dir.listFiles.foreach(f => {
      if(f.isDirectory)
        recursiveDelete(f)
      f.delete()
    })

  }

}