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
public class myCheckAction extends AbstractAction implements LookupListener, ContextAwareAction {
    private Lookup context;
    Lookup.Result<VTEditor> result;
    private static myCheckAction checkAction=null;
    private  VTEditor cVTEditor=null;



    public myCheckAction() {
        this(Utilities.actionsGlobalContext());
        checkAction=this;
    }


 public myCheckAction(Lookup context) {
    init(context);
    putValue(NAME,"Check");
    putValue("iconBase","org/coeus/vteditor/resources/check.png");
  }


    private void init(Lookup context) {
    this.context = context;
    //result = Utilities.actionsGlobalContext().lookup(new Lookup.Template (VTEditor.class));
    result = context.lookupResult(contextClass());
    result.addLookupListener(this);
    resultChanged(null);
    }


    public Class<VTEditor> contextClass() {
    return VTEditor.class;
    }

    public static myCheckAction getCheckAction()
    {return checkAction;}

    public void actionPerformed(ActionEvent e) {
    
     Checks.constantsVariablesArraysChecks(cVTEditor.getRootNode());
     Checks checks = new Checks(cVTEditor.getRootNode(),cVTEditor.getArrayObjectList(),
             cVTEditor.getVariableObjectList(),cVTEditor.getConstantObjectList());

   showCheckResults(checks);
   new checkActionsState();
    }

    public void resultChanged(LookupEvent ev) {
       checkCheckAction();
       }

    public Action createContextAwareInstance(Lookup actionContext) {
       return new myCheckAction(context);
    }


  private void showCheckResults(Checks checks)
  {
   warningsErrorsPanel weP=new warningsErrorsPanel(checks);
      
    NotifyDescriptor d =new NotifyDescriptor.Confirmation(weP,"Check Results",
                NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

   DialogDisplayer.getDefault().notify(d);

   programState.getCurrentProgramState().setCompilerStateCHECKED(weP.getMessage());
   
  }


  public void checkCheckAction()
  {
    if (result.allClasses().size() != 0)
    {
     cVTEditor =  result.allInstances().iterator().next();
     setEnabled(true);
    }
    else setEnabled(false);
  
  }

}

