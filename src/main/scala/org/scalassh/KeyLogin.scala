package org.scalassh

import scala.Some
import com.jcraft.jsch.{Session, JSch}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/06/2011
 * Time: 00:17
 * To change this template use File | Settings | File Templates.
 */

case class KeyLogin(username: String, keyFileLocation: String, keyPassphrase: Option[String] = None) extends Login {
  def login(host: Host, jsch: JSch) = {
    keyPassphrase match{
      case None => jsch.addIdentity(keyFileLocation)
      case Some(s) => jsch.addIdentity(keyFileLocation, s)
    }
    val session = jsch.getSession(username, host.host, host.port)
    if(!Config.strictHostKeyChecking)
      session.setConfig("StrictHostKeyChecking", "no")
    session.connect(Config.loginTimeout)
    session
  }
}

object KeyLogin{
  def apply(host: String, username: String, keyFileLocation: String): Session = apply(Host(host), username, keyFileLocation)
  def apply(host: Host, username: String, keyFileLocation: String): Session = KeyLogin(username, keyFileLocation).login(host)

  def apply(host: String, username: String, keyFileLocation: String, keyPassphrase: String): Session = apply(Host(host), username, keyFileLocation, keyPassphrase)
  def apply(host: Host, username: String, keyFileLocation: String, keyPassphrase: String): Session = KeyLogin(username, keyFileLocation, Some(keyPassphrase)).login(host)
}