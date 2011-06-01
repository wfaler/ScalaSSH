package org.scalassh

import com.jcraft.jsch.Session
import util.DynamicVariable

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 31/05/2011
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */

object SshSession {
  private val _session = new DynamicVariable[Session](null)
  def session = _session value

  def ssh(session: Session)(func: => Unit): Unit = {
    _session.withValue(session){
      func
    }
    session.disconnect
  }

  def ssh(host: Host)(func: => Unit): Unit = {
    // lookup login credential config in ${user.home}/.scalassh/${hostname}
    //ssh(session, func)
  }

}