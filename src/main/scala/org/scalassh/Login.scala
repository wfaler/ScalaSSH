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

  def login(host: Host, jsch: JSch): Session

  def login(host: Host): Session = login(host, new JSch)

  def login(host: String, jsch: JSch): Session = login(Host(host), jsch)

  def login(host: String): Session = login(Host(host), new JSch)

}