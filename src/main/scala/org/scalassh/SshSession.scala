package org.scalassh

import com.jcraft.jsch.Session
import util.DynamicVariable
import collection.mutable.HashMap
import java.util.Properties
import java.io.{FileInputStream, File}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 31/05/2011
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */

object SshSession {

  val directory = System.getProperty("user.home") + System.getProperty("file.separator") + ".scalassh" +
  System.getProperty("file.separator")
  private val map = new HashMap[Host, Login]

  private val _session = new DynamicVariable[Session](null)
  def session = _session value

  def ssh(session: Session)(func: => Unit): Unit = {
    _session.withValue(session){
      func
    }
    session.disconnect
  }

  def ssh(host: String)(func: => Unit): Unit = ssh(Host(host))(func)

  def ssh(host: Host)(func: => Unit): Unit = ssh(loginFor(host).login(host))(func)

  def loginFor(host: Host): Login = {
    map.get(host) match {
      case Some(login) => login
      case None => {
        val file = new File(directory + host.host + ".properties")
        val props = new Properties
        val in = new FileInputStream(file)
        props.load(in)
        in.close
        val login = Config.loginBuilders(props.getProperty("login")).build(props)
        map.put(host, login)
        login
      }
    }
  }

}