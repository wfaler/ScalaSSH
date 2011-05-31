package org.scalassh

import com.jcraft.jsch.Session

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 31/05/2011
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */

object SshSession {

  def ssh(session: Session)(func: (Session) => Unit): Unit = {
    func(session)
    session.disconnect
  }

  def ssh(host: Host)(func: (Session) => Unit): Unit = {
    // lookup login credential config in ${user.home}/.scalassh/${hostname}
    //ssh(session, func)
  }

}