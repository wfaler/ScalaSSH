package org.scalassh

import org.specs2.mutable.Specification
import java.util.Properties
import java.io.{File, FileInputStream}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 02/06/2011
 * Time: 23:35
 * To change this template use File | Settings | File Templates.
 */

class ConfigAndEnvironmentSetupForTestingTest extends Specification{
  val directory = System.getProperty("user.home") + System.getProperty("file.separator") + ".scalassh" +
    System.getProperty("file.separator")
  var login: Login = null
  val props = new Properties
  props.load(new FileInputStream(new File(directory, "localhost.properties")))

  "Retrieving a login configuration for 'password' for the host 'localhost'" should{
    "find a LoginBuilder" in {
      (Config.loginBuilders("password").isInstanceOf[UsernamePasswordLoginBuilder]) must beTrue
    }
    "return a Login when LoginBuilders.login is called" in{
      login = Config.loginBuilders("password").build(props)
      login must be_!=(null)
    }
    " give me a login with a username and password" in {
      login.asInstanceOf[UsernamePasswordLogin].password must be_!=(null)
      login.asInstanceOf[UsernamePasswordLogin].username must be_!=(null)
    }
  }

}