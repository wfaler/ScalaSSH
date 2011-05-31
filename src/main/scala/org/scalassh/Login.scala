package org.scalassh

import com.jcraft.jsch.{JSch, Session}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/06/2011
 * Time: 00:03
 * To change this template use File | Settings | File Templates.
 */

trait Login {

  def login(host: Host, jsch: JSch = new JSch): Session

}