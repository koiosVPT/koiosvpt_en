/*
 *Copyright (c) 2009-2015, Ioannis Vasilopoulos
 *All rights reserved.
 *
 *Redistribution and use in source and binary forms, with or without
 *modification, are permitted provided that the following conditions are met:
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *    * Neither the name of Koios nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *
 *THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
 *DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */


package org.coeus.vteditor.execenv;


import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


/**
 *This code was based on code by
 * Andrew Davison © 2009
 * found in Java Prog. Techniques for Games. Java Art Chapter 3. Tracing
 */
public class mySimpleTrace {


private VirtualMachine vm=null;
private myJDIEventMonitor watcher=  null;
private StreamRedirecter errRedirect = null;
private myOutStreamRedirecter outRedirect = null;
private myHashtable<String,String> coeus2Java=null, coeus2description=null;
private myCodeHighlighter codeHighlighter=null;
private Process process=null;
private myHashtable<String,Integer> jcommandLocator=null, jcommandLineStart=null,jcommandLineEnd=null;
private Hashtable<Integer,Integer> commandBeiginEndPairs=null;

public mySimpleTrace(String[] args,myHashtable<String,String> coeus2Java,
        myHashtable<String,String> coeus2description,myCodeHighlighter codeHighlighter,
        myHashtable<String,Integer>jcommandLocator,myHashtable<String,Integer>jcommandLineStart,
        myHashtable<String,Integer>jcommandLineEnd,Hashtable<Integer,Integer> commandBeiginEndPairs)
{
 vm = launchConnect(args);
 this.coeus2Java=coeus2Java;
 this.coeus2description=coeus2description;
 this.codeHighlighter= codeHighlighter;
 this.jcommandLocator=jcommandLocator;
 this.jcommandLineStart=jcommandLineStart;
 this.jcommandLineEnd=jcommandLineEnd;
 this.commandBeiginEndPairs=commandBeiginEndPairs;
monitorJVM();
}

private VirtualMachine launchConnect(String[] args)
{

LaunchingConnector conn = getCommandLineConnector();
Map<String,Connector.Argument> connArgs = setMainArgs(conn, args);
try {
vm = conn.launch(connArgs); // launch the JVM and connect to it

System.out.println("jvm version: "+vm.version());

}
catch (IOException e) {
throw new Error("Unable to launch JVM: " + e);
}
catch (IllegalConnectorArgumentsException e) {
throw new Error("Internal error: " + e);
}
catch (VMStartException e) {
throw new Error("JVM failed to start: " + e);
}
return vm;
} // end


private LaunchingConnector getCommandLineConnector()
{
List<Connector> conns =
Bootstrap.virtualMachineManager().allConnectors();
for (Connector conn: conns) {
if (conn.name().equals("com.sun.jdi.CommandLineLaunch"))
return (LaunchingConnector) conn;
}
throw new Error("No launching connector found");
}

private Map<String,Connector.Argument> setMainArgs(
LaunchingConnector conn, String[] args)
{
    // get connector field for program's main() method
Map<String,Connector.Argument> connArgs = conn.defaultArguments();
Connector.Argument mArgs =
(Connector.Argument) connArgs.get("main");
if (mArgs == null)
throw new Error("Bad launching connector");
// concatenate all tracer's input args into a single string
StringBuffer sb = new StringBuffer();
for (int i=0; i < args.length; i++)
sb.append(args[i] + " ");
mArgs.setValue(sb.toString()); // assign args to main field
return connArgs;
} // end of setMainArgs()


public void monitorJVM()
{
// start JDI event handler which displays trace info


/* redirect VM's output and error streams
to the system output and error streams */
//////////////ADITION////
process = vm.process();

watcher = new myJDIEventMonitor(vm,this.coeus2Java,this.coeus2description,this.codeHighlighter,
        jcommandLocator,jcommandLineStart,jcommandLineEnd,this.commandBeiginEndPairs);

errRedirect = new StreamRedirecter("error reader",
process.getErrorStream(), System.err);

outRedirect = new myOutStreamRedirecter("output reader",
process.getInputStream(), executionDialog.getExecutionDialog().getOutputTextPane(),watcher);



vm.resume();
}

/////////ADITION ENDS - ORIGINALCODE FOLLOWS /////
//Process process = vm.process();
//Thread errRedirect = new myOutStreamRedirecter("error reader",
//process.getErrorStream(), System.err);
//Thread outRedirect = new myOutStreamRedirecter("output reader",
//process.getInputStream(), executionDialog.getExecutionDialog().getOutputTextPane());
//errRedirect.start();
//outRedirect.start();
//vm.resume(); // start the application
//try {
//watcher.join(); // Wait until JDI watcher terminates
//errRedirect.join(); // make sure all outputs have been forwarded
//outRedirect.join();
//}
//catch (InterruptedException e) { }
//} // end of monitorJVM()


public void startTrace()
{
            watcher.start();
            errRedirect.start();
            outRedirect.start();
}


public void resumeTrace()
{
        synchronized (watcher) {
            watcher.setWait(false);
            watcher.notify();
        }
         outRedirect.setWait(false);
}



public void pauseTrace()
{
            synchronized (watcher) {
            watcher.setWait(true);
        }

            outRedirect.setWait(true);
}

public void killThreads()
{

watcher.interrupt();
errRedirect.interrupt();
outRedirect.interrupt();
try{
vm.exit(1);}
catch(com.sun.jdi.VMDisconnectedException ex){}
}

    public myJDIEventMonitor getWatcher() {
        return watcher;
    }


   public myOutStreamRedirecter getOutRedirect() {
        return outRedirect;
    }


}
