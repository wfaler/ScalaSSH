# ScalaSSH - Type-safe shell scripting over SSH with Scala
This project will aim to provide a strongly typed, extensible way of interacting with a shell over ssh via Scala: pwd, ls, apt-get etc will be strongly typed objects, Ls will return a List of files and so on. 
The project is built with [Simple Build Tool ("sbt")](http://code.google.com/p/simple-build-tool/) and relies on [Jsch](http://www.jcraft.com/jsch/) for its SSH implementation.

## Features
At the moment, the functionality is limited to executing one- or any arbitrary number of shell commands over an ssh connection within a single ssh session closure. We will however strive to add type-safe versions with proper return types of common bash commands such as apt-get, ls, pwd etc soon enough, so you get a proper Scala DSL for shell scripting.

## Limitations
* nohup prefixed commands do not seem to work on Ubuntu or OS X, any commands run in nohup will stop executing once the command closes (will investigate further)
* Due to Jsch limitations, each command is executed in isolation, in other words absolute paths will need to be used, as prior "cd"'s will not be acknowledged.
* Piping commands together in a single Scala command has not been tested - this may or may not work, depending on when the exit code is returned (after each command? after all commands?).

## Usage
You can login using either username/password or username/key-file login.
For instance, username/password login:
	
	import org.scalassh.SshSession._
	import org.scalassh.Command
	
	// your code starts here:
	ssh(UsernamePasswordLogin("localhost", "yourusername", "yourpassword")){
		// run ls in the ssh session, "result" is of type CommandResult
		val result = Command("ls -ltr")
		println("Exit Code: " + result.exitCode)
		println("Resulting Output: " + result.output)
	} 
	// the end of the closure is the end of the ssh session, will auto-close here
	
For key file login (imports omitted from now on):
	
	// your code starts here:
	ssh(KeyLogin("localhost", "yourusername", "/home/mylocaluser/.ssh/id_rsa")){
		// run ls in the ssh session, "result" is of type CommandResult
		val result = Command("ls -ltr")
		println("Exit Code: " + result.exitCode)
		println("Resulting Output: " + result.output)
	} 
	// the end of the closure is the end of the ssh session, will auto-close here
	
	// if your keyfile requires a passphrase, simply use:
	// KeyLogin("localhost", "yourusername", "/home/mylocaluser/.ssh/id_rsa", "passphrase")
	
It is also possible to omit the login entirely and rely on a configuration file stored in the executing users ${user.home}/.scalassh directory (which the user can create). This would work thusly:
	
	ssh("localhost"){
		// run ls in the ssh session, "result" is of type CommandResult
		val result = Command("ls -ltr")
		println("Exit Code: " + result.exitCode)
		println("Resulting Output: " + result.output)
	}
	
For a username/password login, the ScalaSSH would try to resolve the settings from a ${user.home}/.scalassh/${hostname}.properties file, for instance for a Linux machine with the user "johndoe" connecting to "localhost", the file would be resolved to:
	
	/home/johndoe/.scalassh/localhost.properties
	
The file would have the following layout:
	
	login=password # tells that it is using username/password authentication
	username=johndoe
	password=yourpassword
	
For a key-file login, you might have the following layout:

	login=key # tells that it is using keyfile authentication
	username=johndoe
	keyfile=/home/johndoe/.ssh/id_rsa
	passphrase=mykeypassphrase #optional, only needed if the key requires a passphrase
	
## Using Sudo
The sudo password prompt poses another challenge, as ScalaSSH commands are non-interactive. However the workaround for this, if acceptable is to remove the password prompting for sudo.
On Linux, this is typically done by running:

	sudo visudo
	
..which would edit the /etc/sudoers file. You need to the section that contains the admin-group users to include the "NOPASSWD" directive as below:

	# Members of the admin group may gain root privileges
	%admin ALL=NOPASSWD: ALL
	
If you wish, you may make this more restrictive by explicitly stating which commands will not require a password prompt instead of only giving a blanket no-prompt to everything run in sudo.