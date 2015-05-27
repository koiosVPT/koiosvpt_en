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

package org.coeus.vteditor.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.coeus.vteditor.programState;
import org.coeus.vteditor.Checks;
import org.coeus.vteditor.VTEditor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 *
 * @author Jrd
 */
public class myCompileAction extends AbstractAction implements LookupListener, ContextAwareAction {
    private Lookup context;
    Lookup.Result<VTEditor> result;
    private String fileName=null;
    private File f=null;

    private VTEditor cVTEditor=null;
    private static myCompileAction compileAction=null;

 

    private writeJavaFile writeJFile=null;
    private compileJavaFile compileJFile=null;

    public myCompileAction() {
        this(Utilities.actionsGlobalContext());
        compileAction=this;
    }


 public myCompileAction(Lookup context) {
    init(context);
    putValue(NAME,"Compile");
    putValue("iconBase","org/coeus/vteditor/resources/compile.gif");
  }


    private void init(Lookup context) {
    this.context = context;
    result = context.lookupResult(contextClass());
    result.addLookupListener(this);
    resultChanged(null);
    }

    public static myCompileAction getCompileAction()
    {return compileAction;}

    public Class<VTEditor> contextClass() {
    return VTEditor.class;
    }

    public void actionPerformed(ActionEvent e) {

        

        String message="";
        int type=0;

        fileName=cVTEditor.getObjectName()+".java";
        f=new File(fileName);

        Checks.constantsVariablesArraysChecks(cVTEditor.getRootNode());
        Checks checks = new Checks(cVTEditor.getRootNode(),cVTEditor.getArrayObjectList(),
             cVTEditor.getVariableObjectList(),cVTEditor.getConstantObjectList());

        writeJFile=new writeJavaFile(cVTEditor.getRootNode(),f,cVTEditor.getObjectName(),false);
        compileJFile = new compileJavaFile(fileName);
       
            //create .java
            writeJFile.start();

            //compile .java
           compileJFile.start();
               
           int javacExitVal = compileJFile.getJavacExitVal();

////                if (javacExitVal!=0 )
////                {
////                message="Παρουσιάστηκαν λάθη κατα τη διαδικασία της μεταγλώττισης!!  "+
////                        cVTEditor.getDisplayName().toUpperCase()+" !" +
////                        "\nΕίτε το προγραμμά σας περιέχει λάθή ,είτε υπάρχει πρόβλημα με το!";
////                type=NotifyDescriptor.ERROR_MESSAGE;
////                f.delete();
////
////                cVTEditor.getProgramState().setCompilerStateERROR(checks.getErrorsNum());
////               }
////
////                else
                if (javacExitVal!=0 || checks.areErrors())
                {
                message="Error(s) were found while compiling. The program cannot be executed."+
                        cVTEditor.getProgramState().getProgramName()+" !" +
                        "\nCheck your program using the Check command from Program menu, Program -> Check!";
                type=NotifyDescriptor.ERROR_MESSAGE;
                f.delete();

                cVTEditor.getProgramState().setCompilerStateERROR(checks.getErrorsNum());
               }

                else if(checks.areWarnings())
                {
                message=cVTEditor.getProgramState().getProgramName()+ " was compiled without errors,"+
                         "but it contains "+String.valueOf(checks.getWarningsNum())+" warning/s!Please fix it/them before program execution."+
                         "\nCheck your program using the Check command from Program menu, Program -> Check!";
               type=NotifyDescriptor.WARNING_MESSAGE;

                cVTEditor.getProgramState().setCompilerStateWARNING(checks.getWarningsNum());
                f.deleteOnExit();
                File classFile=new File(cVTEditor.getObjectName()+".class");
                classFile.deleteOnExit();
                }
                
                else
                {
                message=cVTEditor.getProgramState().getProgramName()+ " was compiled without errors!"
                        +"\nYou can continue with program execution.";
                type=NotifyDescriptor.INFORMATION_MESSAGE;

                cVTEditor.getProgramState().setCompilerStateOK();
                f.deleteOnExit();
                File classFile=new File(cVTEditor.getObjectName()+".class");
                classFile.deleteOnExit();
                }

             NotifyDescriptor d = new NotifyDescriptor.Confirmation(message,
             "Compilation Results",NotifyDescriptor.DEFAULT_OPTION,type);
             DialogDisplayer.getDefault().notify(d);

       
      new checkActionsState();

    }

    public void resultChanged(LookupEvent ev) {
       if(result.allInstances().size() != 0)
       {
        cVTEditor =  result.allInstances().iterator().next();
        checkCompileAction();
       }
       else
           setEnabled(false);
       }

    public Action createContextAwareInstance(Lookup actionContext) {
       return new mySearchAction(context);
    }

    public void checkCompileAction()
    {
     if (cVTEditor!=null && (cVTEditor.getProgramState().getCompilerState()==programState.ERRORS
             || cVTEditor.getProgramState().getCompilerState()==programState.WARNINGS
             || cVTEditor.getProgramState().getCompilerState()==programState.OK))
            setEnabled(false);
         else
            setEnabled(true);
    }
   
}


