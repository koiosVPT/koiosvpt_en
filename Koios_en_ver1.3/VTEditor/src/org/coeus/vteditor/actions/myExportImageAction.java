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
import org.coeus.vteditor.VTEditor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;


public class myExportImageAction extends AbstractAction
        implements LookupListener, ContextAwareAction{

    private Lookup context;
    Lookup.Result<VTEditor> result;
    private static myExportImageAction exportImageAction=null;
    private  VTEditor cVTEditor=null;




    public myExportImageAction() {
        this(Utilities.actionsGlobalContext());
        exportImageAction=this;
    }


 public myExportImageAction(Lookup context) {
    init(context);
    putValue(NAME,"Export Program as Image");
  }


    private void init(Lookup context) {
    this.context = context;
    result = context.lookupResult(contextClass());
    result.addLookupListener(this);
    resultChanged(null);
    }


    public Class<VTEditor> contextClass() {
    return VTEditor.class;
    }

    public static myExportImageAction getExportImageAction()
    {return exportImageAction;}

    public void actionPerformed(ActionEvent e) {        
        exportImagePanel eIP= 
                new exportImagePanel(this.cVTEditor);

        NotifyDescriptor d = new NotifyDescriptor.Confirmation(eIP,"Export program "
                +cVTEditor.getProgramState().getProgramName()+" as image",
             NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

        if (DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
           eIP.saveImges();


       // new checkActionsState();
    }

    public void resultChanged(LookupEvent ev) {
       checkExportImageAction();
       }

    public Action createContextAwareInstance(Lookup actionContext) {
       return new myExportImageAction(context);
    }

    public void checkExportImageAction()
  {
    if (result.allInstances().size() != 0)
    {
     cVTEditor =  result.allInstances().iterator().next();
     setEnabled(true);
    }
    else setEnabled(false);
  }

 
}

