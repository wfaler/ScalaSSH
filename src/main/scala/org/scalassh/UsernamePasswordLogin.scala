package org.scalassh

import com.jcraft.jsch.{JSch, Session}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/06/2011
 * Time: 00:04
 * To change this template use File | Settings | File Templates.
 */

case class UsernamePasswordLogin(username: String, password: String) extends Login {
  def login(host: Host, jsch: JSch) = {
    val session = jsch.getSession(username, host.host, host.port)
    session.setPassword(password)
    if(!Config.strictHostKeyChecking)
      session.setConfig("StrictHostKeyChecking", "no")
    session.connect(Config.loginTimeout)
    session
  }
}

object UsernamePasswordLogin{
  def apply(host: Host, username: String, password: String): Session = UsernamePasswordLogin(username, password).login(host)
}