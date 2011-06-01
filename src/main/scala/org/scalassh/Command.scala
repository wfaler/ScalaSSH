package org.scalassh

import com.jcraft.jsch.{ChannelExec, Session}
import org.scalassh.SshSession._
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.lang.Boolean


/**
 * Simplest form of Jsch Shell command: takes some input and waits for the exit code to change and adds the output to a result.
 * To be extended by specific command classes like "Ls", "Pwd" etc to add typesafety.
 */
trait Command {

  def execute(command: String, timeoutMs: Option[Long] = None, checkIntervalMs: Long = 50): CommandResult = {
    val channel = session.openChannel("exec").asInstanceOf[ChannelExec]
    val output = new ByteArrayOutputStream
    channel.setOutputStream(System.out)
    channel.setCommand(command)

    channel.connect

    var exit = -1
    val startTime = System.currentTimeMillis
    while(exit < 0 && !timedOut(timeoutMs, startTime)){ // ugly loop, but exitStatus seems to be only clue Jsch gives us whether something has completed
      println("hello")
      exit = channel.getExitStatus
      Thread.sleep(checkIntervalMs)
    }
    val result = CommandResult(exit, new String(output.toByteArray))
    channel.disconnect
    output.close
    result
  }

  private def timedOut(timeoutMs: Option[Long], startTime: Long): Boolean = {
    timeoutMs match{
      case None => false
      case Some(millis) =>{
        val timeoutTime = startTime + millis
        return (timeoutTime < System.currentTimeMillis)
      }
    }
  }

}

case class CommandResult(exitCode: Int, output: String)


object CommandTest extends App{
  ssh("localhost"){
    println(Command("ls -ltr").output)
  }
}


object Command extends Command{
  def apply(command: String, timeoutMs: Option[Long] = None, checkIntervalMs: Long = 50) = execute(command, timeoutMs, checkIntervalMs)
}
