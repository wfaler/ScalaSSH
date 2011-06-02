package org.scalassh

import java.util.Properties

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 02/06/2011
 * Time: 23:23
 * To change this template use File | Settings | File Templates.
 */

trait LoginBuilder {
  def build(properties: Properties): Login
}

class KeyLoginBuilder extends LoginBuilder{
  def build(props: Properties) = KeyLogin(props.getProperty("username"), props.getProperty("keyfile"), Option(props.getProperty("passphrase")))
}

class UsernamePasswordLoginBuilder extends LoginBuilder{
  def build(props: Properties) = UsernamePasswordLogin(props.getProperty("username"), props.getProperty("password"))
}
