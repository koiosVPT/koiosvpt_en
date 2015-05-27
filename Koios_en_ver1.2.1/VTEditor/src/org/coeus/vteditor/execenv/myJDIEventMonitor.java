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

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotPreparedException;
import com.sun.jdi.Field;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.ClassUnloadEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.ModificationWatchpointEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.ThreadStartEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.event.VMStartEvent;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.ClassUnloadRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;
import com.sun.jdi.request.MethodExitRequest;
import com.sun.jdi.request.ModificationWatchpointRequest;
import com.sun.jdi.request.StepRequest;
import com.sun.jdi.request.ThreadDeathRequest;
import com.sun.jdi.request.ThreadStartRequest;
import java.awt.Color;
import java.util.Hashtable;
import java.util.List;
import org.coeus.poclasses.CoeusProgram;

/**
 *
 * Andrew Davison © 2009
 * found in Java Prog. Techniques for Games. Java Art Chapter 3. Tracing
 */
public class myJDIEventMonitor extends Thread{


// globals
// exclude events generated for these classes
private final String[] excludes =
{ "java.*", "javax.*", "sun.*", "com.sun.*","myJOP"};//dont get events from myJOP
private final VirtualMachine vm; // the JVM

private boolean connected = true; // connected to VM?
private boolean vmDied; // has VM death occurred?

private myShowCode myshowCode;
private boolean wait=false;
private long speed;
private myHashtable<String,String> coeus2Java=null,coeus2description=null;
private myCodeHighlighter codeHighlighter=null;
private List<Field> fields = null;
private List<LocalVariable> myLocals = null;
private StackFrame myCurrFrame=null;
private ObjectReference objRef=null;
private myHashtable<String,Integer> jcommandLocator=null, jcommandLineStart=null,jcommandLineEnd=null;
//private int in_myJOptionPane=0;
private boolean readyToWrite=false;
private Hashtable<Integer,Integer> commandBeiginEndPairs=null;

public myJDIEventMonitor( VirtualMachine vm,myHashtable<String,String> coeus2Java,
        myHashtable<String,String> coeus2description,myCodeHighlighter codeHighlighter,
        myHashtable<String,Integer>jcommandLocator,myHashtable<String,Integer>jcommandLineStart,
        myHashtable<String,Integer>jcommandLineEnd,Hashtable<Integer,Integer> commandBeiginEndPairs)
{   this.vm=vm;
    setEventRequests();
    myshowCode = new myShowCode();
   this.coeus2Java= coeus2Java;
   this.coeus2description=coeus2description;
   this.codeHighlighter=codeHighlighter;
   this.jcommandLocator=jcommandLocator;
   this.jcommandLineStart=jcommandLineStart;
   this.jcommandLineEnd=jcommandLineEnd;
   this.commandBeiginEndPairs=commandBeiginEndPairs;
 }


private void setEventRequests()
{
EventRequestManager mgr = vm.eventRequestManager();
MethodEntryRequest menr = mgr.createMethodEntryRequest();
for (int i = 0; i < excludes.length; ++i) // report method entries
menr.addClassExclusionFilter(excludes[i]);
menr.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
menr.enable();
MethodExitRequest mexr = mgr.createMethodExitRequest();
for (int i = 0; i < excludes.length; ++i) // report method exits
mexr.addClassExclusionFilter(excludes[i]);
mexr.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
mexr.enable();
ClassPrepareRequest cpr = mgr.createClassPrepareRequest();
for (int i = 0; i < excludes.length; ++i) // report class loads
cpr.addClassExclusionFilter(excludes[i]);
cpr.enable();
ClassUnloadRequest cur = mgr.createClassUnloadRequest();
for (int i = 0; i < excludes.length; ++i) // report class unloads
cur.addClassExclusionFilter(excludes[i]);
cur.enable();
ThreadStartRequest tsr = mgr.createThreadStartRequest();
tsr.enable(); // report thread starts
ThreadDeathRequest tdr = mgr.createThreadDeathRequest();
tdr.enable(); // report thread deaths
} // end of setEventRequests()



    @Override
public void run()
{

//long currentSpeed = speed;
EventQueue queue = vm.eventQueue();
while (connected) {
try {
EventSet eventSet = queue.remove();
for(Event event : eventSet)
{
////Check to skip myjoptionpane.myInputDialog
//if (event instanceof MethodEntryEvent)
//{
//    MethodEntryEvent method_event = (MethodEntryEvent)event;
//    //System.out.println("\n\nmethodnameenter=" + method_event.method().declaringType().name()+"\n\n");
//   if (method_event.method().declaringType().name().equalsIgnoreCase("myJOP"))//"myjoptionpane.myInputDialog"))
//   {
//       if(in_myJOptionPane==0)
//       {
//        currentSpeed=speed;
//        speed=0;
//       }
//      in_myJOptionPane++;
//   }
//}
//
//if (event instanceof MethodExitEvent)
//{
//   MethodExitEvent method_event=((MethodExitEvent) event);
//   //System.out.println("\n\nmethodnameexit=" + method_event.method().declaringType().name()+"\n\n");
//   if (method_event.method().declaringType().name().equalsIgnoreCase("myJOP"))//"myjoptionpane.myInputDialog"))
//   {
//       in_myJOptionPane--;
//       if(in_myJOptionPane==0)
//           speed=currentSpeed;
//    }
//}


// Check if should wait
                synchronized (this) {
                    while (wait) {
                        try {
                            wait();
                        } catch (Exception e) {
                        }
                    }

                }

                 handleEvent(event);
}
eventSet.resume();

}
catch (InterruptedException e) { } // Ignore
catch (VMDisconnectedException discExc) {
handleDisconnectedException();
break;
}
}
} // end


private synchronized void handleDisconnectedException()
{
EventQueue queue = vm.eventQueue();
while (connected) {
try {
    EventSet eventSet = queue.remove();
for(Event event : eventSet) {
if (event instanceof VMDeathEvent)
vmDeathEvent((VMDeathEvent) event);
else if (event instanceof VMDisconnectEvent)
vmDisconnectEvent((VMDisconnectEvent) event);
}
eventSet.resume();
}
catch (InterruptedException e) { } // ignore
}
} // end of handleDisconnectedException

private void vmDeathEvent(VMDeathEvent event)
// Notification of VM termination
{ vmDied = true;
//processMessage("-- The application has exited --");
processMessage("exited", -1);
}
private void vmDisconnectEvent(VMDisconnectEvent event)
/* Notification of VM disconnection, either through normal
termination or because of an exception/error. */
{ connected = false;
//if (!vmDied)
//processMessage("- The application has been disconnected -");
//processMessage("disconected");
}



private void handleEvent(Event event)
{

// method events
if (event instanceof ExceptionEvent)             exceptionEvent((ExceptionEvent)event);
else if (event instanceof MethodEntryEvent)      methodEntryEvent((MethodEntryEvent) event);
//else if (event instanceof MethodEntryEvent)
//{
//   MethodEntryEvent method_event = (MethodEntryEvent)event;
//    System.out.println("\n\nmethodname=" + method_event.method().declaringType().name()+"\n\n");
//   if (!method_event.method().declaringType().name().equalsIgnoreCase("myjoptionpane.myInputDialog"))
//         methodEntryEvent((MethodEntryEvent) event);
//}
else if (event instanceof MethodExitEvent)       methodExitEvent((MethodExitEvent) event);
// class events
else if (event instanceof ClassPrepareEvent)     classPrepareEvent((ClassPrepareEvent) event);
else if (event instanceof ClassUnloadEvent)      classUnloadEvent((ClassUnloadEvent) event);
// thread events
else if (event instanceof ThreadStartEvent)      threadStartEvent((ThreadStartEvent) event);
else if (event instanceof ThreadDeathEvent)      threadDeathEvent((ThreadDeathEvent) event);
// step event -- a line of code is about to be executed
else if (event instanceof StepEvent)             stepEvent((StepEvent) event);
// modified field event -- a field is about to be changed
else if (event instanceof ModificationWatchpointEvent)  fieldWatchEvent((ModificationWatchpointEvent) event);
// VM events
else if (event instanceof VMStartEvent)           vmStartEvent((VMStartEvent) event);
else if (event instanceof VMDeathEvent)           vmDeathEvent((VMDeathEvent) event);
else if (event instanceof VMDisconnectEvent)      vmDisconnectEvent((VMDisconnectEvent) event);
//else  throw new Error("Unexpected event type");
} // end of handleEvent()

////////////////////////////////ORIGINAL CODE BEGINS///////////////////////
//
// private void vmStartEvent(VMStartEvent event)  {
//        processMessage("-- VM Started --");
//    }
//
//private void methodEntryEvent(MethodEntryEvent event)
//// entered a method but no code executed yet
//{
//Method meth = event.method();
//String className = meth.declaringType().name();
//processMessage("");
//if (meth.isConstructor())
//processMessage("entered " + className + " constructor");
//else
//processMessage("entered " + className+"."+meth.name()+"()");
//} // end of methodEntryEvent()
//
//private void methodExitEvent(MethodExitEvent event)
//// all code in method has been executed, and about to return
//{
//Method meth = event.method();
//String className = meth.declaringType().name();
//if (meth.isConstructor())
//processMessage("exiting " + className + " constructor");
//else
//processMessage("exiting " + className+"."+meth.name()+"()");
////processMessage("");
//} // end of methodExitEvent()
//
//private void classUnloadEvent(ClassUnloadEvent event)
//{ if (!vmDied)
//processMessage("unloaded class: " + event.className());
//}
//
//
//private void classPrepareEvent(ClassPrepareEvent event)
//// a new class has been loaded
//{
//ReferenceType ref = event.referenceType();
//List<Field> fields = ref.fields();
//List<Method> methods = ref.methods();
//String fnm;
//try {
//fnm = ref.sourceName(); // get filename of the class
//myshowCode.add(fnm);
//}
//catch (AbsentInformationException e)
//{ fnm = "??"; }
//processMessage("loaded class: " + ref.name()+" from " +fnm +
//" - fields=" + fields.size() + ", methods=" + methods.size() );
//processMessage(" method names: ");
//for(Method m : methods)
//processMessage(" | " + m.name() + "()" );
//setFieldsWatch(fields);
//} // end of classPrepareEvent()
//
//
//private void setFieldsWatch(List<Field> fields)
//{
//EventRequestManager mgr = vm.eventRequestManager();
//for (Field field : fields) {
//ModificationWatchpointRequest req =
//mgr.createModificationWatchpointRequest(field);
//for (int i = 0; i < excludes.length; i++)
//req.addClassExclusionFilter(excludes[i]);
//req.setSuspendPolicy(EventRequest.SUSPEND_NONE);
//req.enable();
//}
//} // end of setFieldsWatch()
//
//private void fieldWatchEvent(ModificationWatchpointEvent event)
//{
//Field f = event.field();
//Value value = event.valueToBe(); // value that _will_ be assigned
//processMessage(" > " + f.name() + " = " + value);
//} // end of fieldWatchEvent()
//
//
//
//private void threadDeathEvent(ThreadDeathEvent event)
//// the thread is about to terminate
//{
//ThreadReference thr = event.thread();
//if (thr.name().equals("DestroyJavaVM") ||
//thr.name().startsWith("AWT-") )
//return;
//if (thr.threadGroup().name().equals("system"))//ignore sys threads
//return;
//processMessage(thr.name() + " thread about to die");
//} // end of threadDeathEvent()
//
//
//private void threadStartEvent(ThreadStartEvent event)
//// a new thread has started running -- switch on single stepping
//{
//ThreadReference thr = event.thread();
//if (thr.name().equals("Signal Dispatcher") ||
//thr.name().equals("DestroyJavaVM") ||
//thr.name().startsWith("AWT-") ) // AWT threads
//return;
//if (thr.threadGroup().name().equals("system"))// ignore sys threads
//return;
//processMessage(thr.name() + " thread started");
//setStepping(thr);
//} // end of threadStartEvent()
//
//private void setStepping(ThreadReference thr)
//// start single stepping through the new thread
//{
//EventRequestManager mgr = vm.eventRequestManager();
//StepRequest sr = mgr.createStepRequest(thr,
//StepRequest.STEP_LINE, StepRequest.STEP_INTO);
//sr.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
//for (int i = 0; i < excludes.length; ++i)
//sr.addClassExclusionFilter(excludes[i]);
//sr.enable();
//} // end of setStepping()
//
//private void stepEvent(StepEvent event)
//{
//Location loc = event.location();
//try { // print the line
//String fnm = loc.sourceName(); // get code's filename
//processMessage(fnm + ": " +
//myshowCode.show(fnm, loc.lineNumber()) );
//
//    System.out.println(fnm + ": " +
//myshowCode.show(fnm, loc.lineNumber()) );
//}
//catch (AbsentInformationException e) {}
//if (loc.codeIndex() == 0) // at the start of a method
//    printInitialState( event.thread() );
//} // end of stepEvent()
//
//
//private void printInitialState(ThreadReference thr)
//{
//// get top-most stack frame
//StackFrame currFrame = null;
//try {
//currFrame = thr.frame(0);
//}
//catch (Exception e) {
//return;
//}
//printLocals(currFrame);
//// print fields for the 'this' object
//ObjectReference objRef = currFrame.thisObject();
//if (objRef != null) {
//processMessage(" object: " + objRef.toString());
//printFields(objRef);
//}
//} // end of printInitialState()
//
//
//
//private void printLocals(StackFrame currFrame)
//{
//List<LocalVariable> locals = null;
//try {
//locals = currFrame.visibleVariables();
//}
//catch (Exception e) {
//return;
//}
//if (locals.size() == 0) // no local vars in the list
//return;
//processMessage(" locals: ");
//for(LocalVariable l : locals)
//processMessage(" | " + l.name() +" = " + currFrame.getValue(l) );
//} // end of printLocals()
//
//
//private void printFields(ObjectReference objRef)
//{
//ReferenceType ref = objRef.referenceType(); // get class of object
//List<Field> fields = null;
//try {
//fields = ref.fields(); // get fields from the class
//}
//catch (ClassNotPreparedException e) {
//return;
//}
//processMessage(" fields: ");
//for(Field f : fields) // print field name and value
//processMessage(" | " + f.name() + " = " +
//objRef.getValue(f) );
//} // end of printFields()
/////////////////////////////////////ORIGINAL CODE ENDS/////////////


 private void vmStartEvent(VMStartEvent event)  {
      //  processMessage("-- VM Started --");
    }

private void methodEntryEvent(MethodEntryEvent event)
// entered a method but no code executed yet
{
Method meth = event.method();
//String className = meth.declaringType().name();

if (meth.isConstructor())
{}//processMessage("public class " + className + " {");
else
{
   processMessage(meth.returnTypeName() + " " + meth.name() + " (", -1); // meth.location().lineNumber());
}// meth.location().lineNumber());

} // end of methodEntryEvent()

private void methodExitEvent(MethodExitEvent event)
// all code in method has been executed, and about to return
{
Method meth = event.method();
//String className = meth.declaringType().name();
if (meth.isConstructor())
{}//processMessage("exiting constructor");// + className );
else
processMessage("exiting " +meth.name(),-1);// meth.location().lineNumber());
} // end of methodExitEvent()

private void classUnloadEvent(ClassUnloadEvent event)
{ //if (!vmDied)
//processMessage("unloaded class: " + event.className());
}


private void classPrepareEvent(ClassPrepareEvent event)
// a new class has been loaded
{
ReferenceType ref = event.referenceType();
List<Field> fields1 = ref.fields();
List<Method> methods = ref.methods();
String fnm;
try {
fnm = ref.sourceName(); // get filename of the class
myshowCode.add(fnm);
}
catch (AbsentInformationException e)
{ fnm = "??"; }
//processMessage("loaded class: " + ref.name()+" from " +fnm +
//" - fields=" + fields1.size() + ", methods=" + methods.size() );
//processMessage(" method names: ");
//for(Method m : methods)
//processMessage(" | " + m.name() + "()" );
setFieldsWatch(fields1);
} // end of classPrepareEvent()


private void setFieldsWatch(List<Field> fields)
{
EventRequestManager mgr = vm.eventRequestManager();
for (Field field : fields) {
ModificationWatchpointRequest req =
mgr.createModificationWatchpointRequest(field);
for (int i = 0; i < excludes.length; i++)
req.addClassExclusionFilter(excludes[i]);
req.setSuspendPolicy(EventRequest.SUSPEND_NONE);
req.enable();

//System.out.println("\n\n" + field.name()+"\n\n");
}
} // end of setFieldsWatch()

private void fieldWatchEvent(ModificationWatchpointEvent event)
{
Field f = event.field();
Value value = event.valueToBe(); // value that _will_ be assigned
//processMessage(" > " + f.name() + " = " + value);
//System.out.println(" > " + f.name() + " = " + value);
} // end of fieldWatchEvent()


private void threadDeathEvent(ThreadDeathEvent event)
// the thread is about to terminate
{
ThreadReference thr = event.thread();
if (thr.name().equals("DestroyJavaVM") ||
thr.name().startsWith("AWT-") )
return;
if (thr.threadGroup().name().equals("system"))//ignore sys threads
return;
//processMessage(thr.name() + " thread about to die");
} // end of threadDeathEvent()


private void threadStartEvent(ThreadStartEvent event)
// a new thread has started running -- switch on single stepping
{
ThreadReference thr = event.thread();
if (thr.name().equals("Signal Dispatcher") ||
thr.name().equals("DestroyJavaVM") ||
thr.name().startsWith("AWT-") ) // AWT threads
return;
if (thr.threadGroup().name().equals("system"))// ignore sys threads
return;
//processMessage(thr.name() + " thread started");
setStepping(thr);
} // end of threadStartEvent()

private void setStepping(ThreadReference thr)
// start single stepping through the new thread
{
EventRequestManager mgr = vm.eventRequestManager();
StepRequest sr = mgr.createStepRequest(thr,
StepRequest.STEP_LINE, StepRequest.STEP_INTO);
sr.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
for (int i = 0; i < excludes.length; ++i)
sr.addClassExclusionFilter(excludes[i]);
sr.enable();
} // end of setStepping()

private void stepEvent(StepEvent event)
{
Location loc = event.location();

try { // print the line
String fnm = loc.sourceName(); // get code's filename

//getLocals(event.thread());

processMessage(/*fnm + ": " +*/
myshowCode.show(fnm, loc.lineNumber()),loc.lineNumber() );

//myPrintLocals(event.thread());
//myPrintFields(event.thread());
}
catch (AbsentInformationException e) {}
if (loc.codeIndex() == 0) // at the start of a method
    printInitialState( event.thread() );
} // end of stepEvent()


private void printInitialState(ThreadReference thr)
{
// get top-most stack frame
StackFrame currFrame = null;
try {
currFrame = thr.frame(0);
}
catch (Exception e) {
return;
}
printLocals(currFrame);
// print fields for the 'this' object
objRef = currFrame.thisObject();
if (objRef != null) {
//processMessage(" object: " + objRef.toString());
printFields(objRef);
}
} // end of printInitialState()



private void printLocals(StackFrame currFrame)
{
List<LocalVariable> locals=null;
try {
locals = currFrame.visibleVariables();
}
catch (Exception e) {
return;
}
if (locals.size() == 0) // no local vars in the list
return;
//processMessage(" locals: ");
//for(LocalVariable l : locals)
//processMessage(" | " + l.name() +" = " + currFrame.getValue(l) );
} // end of printLocals()


private void printFields(ObjectReference objRefer)
{
ReferenceType ref = objRefer.referenceType(); // get class of object
try {
fields = ref.fields(); // get fields from the class
}
catch (ClassNotPreparedException e) {
return;
}
//processMessage(" fields: ");
//for(Field f : fields) // print field name and value
//processMessage(" | " + f.name() + " = " +
//objRefer.getValue(f) );
} // end of printFields()




void exceptionEvent(ExceptionEvent event) {
	    processMessage("Exception: " + event.exception() +
		    " catch: " + event.catchLocation(), event.catchLocation().lineNumber());

//            // Step to the catch
//            EventRequestManager mgr = vm.eventRequestManager();
//            StepRequest req = mgr.createStepRequest(thread,
//                                                    StepRequest.STEP_MIN,
//                                                    StepRequest.STEP_INTO);
//            req.addCountFilter(1);  // next step only
//            req.setSuspendPolicy(EventRequest.SUSPEND_ALL);
//            req.enable();
	}


//private void myPrintLocals(ThreadReference thr)
//{
//StackFrame currFrame1 = null;
//try {
//currFrame1 = thr.frame(0);
//}
//catch (Exception e) {
//return;
//}
//
//
//List<LocalVariable> locals1 = null;
//try {
//locals1 = currFrame1.visibleVariables();
//}
//catch (Exception e1) {
//return;
//}
//if (locals1.size() == 0) // no local vars in the list
//return;
//
////processMessage("---------------testing begins-----------------");
//System.out.println(" locals: ");
//for(LocalVariable l : locals1)
//System.out.println(" | " + l.name() +" = " + currFrame1.getValue(l) );
////processMessage("---------------testing end-----------------");
//} // end of printLocals()
//
//
//
//
//
//public void myPrintFields(ThreadReference thr)
//{
//StackFrame currFrame = null;
//try {
//currFrame = thr.frame(0);
//}
//catch (Exception e0) {
//    return;
//}
//ObjectReference objRef = currFrame.thisObject();
//if (objRef != null) {
//ReferenceType ref = objRef.referenceType(); // get class of object
//List<Field> fields = null;
//try {
//fields = ref.fields(); // get fields from the class
//}
//catch (ClassNotPreparedException e) {
//return;
//}
//processMessage("TESTING fields: ");
//for(Field f : fields) // print field name and value
//processMessage(" | " + f.name() + " = " +
//objRef.getValue(f) );
//}
//} // end of printFields()


 public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getSpeed() {
        return speed;
    }




     private void processMessage(String message,int textLineNumber)
    {
    String command="";
    String comment="";
    int commandLine=0;


    message=message.trim();
    comment=message;

        if (!message.isEmpty()
        && !message.startsWith("class ")
        && !message.startsWith("public class ")
        && !message.contains("String temp=null;")
        && !message.startsWith("new ")
        && !message.startsWith("exiting main")
        //&& !message.startsWith("exiting constructor")
        && !message.startsWith("catch (NumberFormatException ex)")
        && !message.equals("}")
        )
    {

    System.out.println("\n" + message+" "+textLineNumber+"\n");
    if (message.equals("exited"))
    {
    command="END OF PROGRAM";
    comment=coeus2description.get(CoeusProgram.PROGRAM2);//get(command);
    commandLine=coeus2description.size()-1;
    executionDialog.getStatusTextArea().setForeground(new Color(0,51,51));
    executionDialog.getStatusTextArea().setText("The program was successfully executed!"+
            "\n\nClick the \"Start\" button, to run the program again.");
     executionDialog.runButton.setEnabled(true);
     executionDialog.pauseButton.setEnabled(false);
     executionDialog.stopButton.setEnabled(false);
    }
    else if (message.startsWith("void main"))
    {
     for (int i:this.coeus2Java.keySet())
      {
        if ( coeus2Java.getK(i).contains("PROGRAM "))
        {    
        command=coeus2Java.getK(i);
        comment=coeus2description.getV(i);
        commandLine=i;
        }
     }
    
    }
//    else if (message.startsWith("public "))
//    {
//    command=CoeusProgram.MAIN_PROC;
//    comment="Το πρόγραμμα εντοπίζει την ΚΥΡΙΩΣ ΔΙΑΔΙΚΑΣΙΑ και ξεκινά εκτελώντας την πρώτη εντολή της."+
//            "\n\nΑν υπάρχουν γενικές μεταβλητές,η αρχικοποιήση τους θα εκτελετεί πρώτα";
//    //try {Thread.sleep(speed);
//    //        } catch (InterruptedException ex) { /*Exceptions.printStackTrace(ex);*/ }
//    }
    else
    {
    for (int i:this.coeus2Java.keySet())
      {
        if ( coeus2Java.getV(i).contains(message))
        {
         command=coeus2Java.getK(i);
//         System.out.println("\n"+message+" -> " +command +"\n\n\n");
         comment=coeus2description.getV(i);
//         if (message.startsWith("for ("))
//           comment=comment+ getVariableOrFieldValue(message);
         commandLine=i;
        }
       }
    }

    executionDialog.setCommandName(command);
    codeHighlighter.addHighlight(command,commandLine,textLineNumber,this.jcommandLocator,
           this.jcommandLineStart,this.jcommandLineEnd,this.commandBeiginEndPairs);
    executionDialog.setComment(comment);

  
     if (!command.trim().startsWith("READ"))
       {
        if (command.trim().startsWith("WRITE"))
        {
            try {Thread.sleep(speed/2);
            } catch (InterruptedException ex) { /*Exceptions.printStackTrace(ex);*/ }

        }
        else
        {
        try {Thread.sleep(speed);
            } catch (InterruptedException ex) { /*Exceptions.printStackTrace(ex);*/ }

        }
       }
     }
   }

    public boolean isReadyToWrite() {
        return readyToWrite;
    }

    public void setReadyToWrite(boolean readyToWrite) {
        this.readyToWrite = readyToWrite;
    }






    private String getVariableOrFieldValue(String message) {
     String value="";  
     Value v=null;


     int parenthesisPos=message.indexOf("(");
     int equalPos=message.indexOf("=");
     String vfName=message.substring(parenthesisPos+1, equalPos).trim();
        System.out.println("\nvfName= " + vfName );
     //Check if for variable is a local variable
     for (LocalVariable lv:myLocals)
     { System.out.println(lv.name());
      if (lv.name().equals(vfName))
          v=myCurrFrame.getValue(lv);
     }
        //Check if for variable is a field variable
     if (objRef != null)
     {
     for (Field f:objRef.referenceType().fields())
      {
      if (f.name().equals(vfName))
          v=objRef.getValue(f);
       }
     }
     
        System.out.println("\nvfValue= " +v );
     return value;
    }
      

private void getLocals(ThreadReference thr)
{
myCurrFrame = null;
try {
myCurrFrame = thr.frame(0);
}
catch (Exception e) {
return;
}
myLocals = null;
try {
myLocals = myCurrFrame.visibleVariables();
}
catch (Exception e1) {
return;
}
if (myLocals.size() == 0) // no local vars in the list
return;

} // end of getLocals()


}
