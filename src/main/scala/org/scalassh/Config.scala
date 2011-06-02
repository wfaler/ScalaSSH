package org.scalassh

import java.util.Properties

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 01/06/2011
 * Time: 00:21
 * To change this template use File | Settings | File Templates.
 */

object Config {
  var loginBuilders = Map("password" -> new UsernamePasswordLoginBuilder, "key" -> new KeyLoginBuilder)
  var loginTimeout = 20000
  var strictHostKeyChecking = false
}