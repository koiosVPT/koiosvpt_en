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



package org.coeus.vteditor;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import org.coeus.vteditor.actions.checkActionsState;
import org.openide.awt.Notification;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;



/**
 *
 * @author Jrd
 */
public class programState implements LookupListener{

    Lookup.Result<programState> result=null;
    private static programState currentProgram=null;

    public static final int NULL=-1;
    public static final int UNCOMPILED=0;
    public static final int CHECKED=1;
    public static final int ERRORS=2;
    public static final int WARNINGS=3;
    public static final int OK=4;

    private int compilerState=NULL;
    private static Notification notification=null;


    private String programName=null,message=null;
    private int errorsNum=0,warningsNum=0;
    private boolean notificationShown=false;

    public programState(String programName) {
    this.programName=programName;
    this.compilerState=NULL;

    result = Utilities.actionsGlobalContext().lookupResult(programState.class);
    result.addLookupListener(this);
    resultChanged(null);

    currentProgram=this;
    }

   
    public void setCompilerStateERROR(int errorsNum)
    {  
    if (this.compilerState!=ERRORS || notificationShown==false)
    {
      this.compilerState=ERRORS;
      showErrorsNotification(errorsNum);
    }
   new checkActionsState();

   }
    
    public void setCompilerStateWARNING(int warningsNum)
    {
    if (this.compilerState!=WARNINGS || notificationShown==false)
     {
      this.compilerState=WARNINGS;
      showWarningsNotification(warningsNum);
     }
   new checkActionsState();
     
    }

    public void setCompilerStateOK()
    {
    if (this.compilerState!=OK || notificationShown==false)
     {
      this.compilerState=OK;
      showOKNotification();
     }
   new checkActionsState();

    }

    public void setCompilerStateCHECKED(String message)
    {
    if (this.compilerState!=CHECKED || notificationShown==false)
     {
      this.compilerState=CHECKED;
      this.showCheckedNotification(message);
     }
   new checkActionsState();
    }

    public void setCompilerStateUNCOMPILED()
    {
    if (this.compilerState!=UNCOMPILED || notificationShown==false)
    {
     this.compilerState=UNCOMPILED;
     this.showUncompiledNotification();
    }
    new checkActionsState();
    }


    private void showErrorsNotification(int errorsNum)
    {  this.errorsNum=errorsNum;
       clearNotification();
       notification= NotificationDisplayer.getDefault().notify("Error(s) found while compiling the program",
                 new ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/error.gif")),
                 "Program "+ programName.toUpperCase()+" contains "+String.valueOf(this.errorsNum)+" error/s!",
                 new ActionListener() { public void actionPerformed(ActionEvent e) {notificationShown=false;}},
                 NotificationDisplayer.Priority.HIGH);
     notificationShown=true;
       }

    private void showWarningsNotification(int warningsNum)
    {
     this.warningsNum=warningsNum;
     clearNotification();
     notification=NotificationDisplayer.getDefault().notify("Warning(s) found while compiling the program",
     new ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/warning.gif")),
     "Program "+ programName.toUpperCase()+" contains "+String.valueOf(this.warningsNum)+" warning/s!",
     new ActionListener() { public void actionPerformed(ActionEvent e) {notificationShown=false;}},
     NotificationDisplayer.Priority.HIGH);
     notificationShown=true;
     }


    private void showOKNotification()
    {
     clearNotification();
     notification=NotificationDisplayer.getDefault().notify("Error-free compilation",
     new ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/ok16.png")),
     "You can execute program "+ programName.toUpperCase()+".",
     new ActionListener() { public void actionPerformed(ActionEvent e) {notificationShown=false;}},
     NotificationDisplayer.Priority.HIGH);
     notificationShown=true;
  }


    public void showCheckedNotification(String message)
    {
     this.message=message;
     clearNotification();
     notification=NotificationDisplayer.getDefault().notify("Checking program "+programName+" :",
     new ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/info.png")),this.message,
     new ActionListener() { public void actionPerformed(ActionEvent e) {notificationShown=false;}},
     NotificationDisplayer.Priority.HIGH);
     notificationShown=true;
   }


   public void showUncompiledNotification()
    {
     clearNotification();
     notification=NotificationDisplayer.getDefault().notify("Program "+programName+" has been changed!",
     new ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/questionMark16.png")),
     "Program check for warnings and errors is required!",
     new ActionListener() { public void actionPerformed(ActionEvent e) { notificationShown=false;}},
     NotificationDisplayer.Priority.HIGH);
     notificationShown=true;
   }
    

    public void clearNotification() {
             if (notification!=null)
          notification.clear();
    }

    public int getCompilerState() {
        return this.compilerState;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }


      public static programState getCurrentProgramState()
    { return currentProgram;}

   private  void updateNotification(int compilerState) {
       if (compilerState==NULL)
           clearNotification();
       else if (compilerState==UNCOMPILED)
           currentProgram.showUncompiledNotification();
       else if (compilerState==CHECKED)
           currentProgram.showCheckedNotification(currentProgram.message);
       else if (compilerState==ERRORS)
           currentProgram.showErrorsNotification(currentProgram.errorsNum);
       else if (compilerState==WARNINGS)
           currentProgram.showWarningsNotification(currentProgram.warningsNum);
       else if (compilerState==OK)
           currentProgram.showOKNotification();


    }


    public void resultChanged(LookupEvent ev) {
     if (result.allInstances().size() != 0)
     {   clearNotification();
         currentProgram=result.allInstances().iterator().next();
         updateNotification(currentProgram.getCompilerState());
     }
    }

    public String getProgramName() {
        return programName;
    }

    
        
     
    

   

   
}
