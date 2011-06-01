package org.scalassh

import com.jcraft.jsch.{ChannelExec, Session}
import java.io.ByteArrayOutputStream
import org.scalassh.SshSession._


/**
 * Simplest form of Jsch Shell command: takes some input and waits for the exit code to change and adds the output to a result.
 * To be extended by specific command classes like "Ls", "Pwd" etc to add typesafety.
 */

trait ShellCommand {

  def execute(command: String, checkIntervalMs: Int = 50): CommandResult = {
    val channel = session.openChannel("exec").asInstanceOf[ChannelExec]
    val output = new ByteArrayOutputStream
    channel.setOutputStream(output)
    channel.setCommand(command)

    channel.connect

    var exit = -1
    while(exit < 0){ // ugly loop, but exitStatus seems to be only clue Jsch gives us whether something has completed
      exit = channel.getExitStatus
      Thread.sleep(checkIntervalMs)
    }
    val result = CommandResult(exit, new String(output.toByteArray))
    channel.disconnect
    output.close
    result
  }

}

case class CommandResult(exitCode: Int, output: String)


object CommandTest extends App{
  ssh(UsernamePasswordLogin(Host("localhost"), "username", "password")){
    val result = Ls("ltr")
    println("Exit Code: " + result.exitCode)
    println("Output: " + result.output)

  }
}

object Ls extends ShellCommand{
  // could obviously take ls opts and return a strongly typed List of files returned
  def apply(opts: String) = execute("ls -" + opts)
}