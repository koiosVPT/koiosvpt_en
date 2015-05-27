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

import org.coeus.vteditor.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
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
//public class mySearchAction extends AbstractAction implements LookupListener{
//
//   private  Lookup.Result result = null;
//   //private VTEditor cVTEditor=null;
//   private VTEditor cVTEditor=currentVTEditor.getCurrentVTEditor();
//
//    public void actionPerformed(ActionEvent e) {
//       if  (cVTEditor!=null)
//       {
//         mySearchPanel msp= new mySearchPanel(cVTEditor.getRootNode(),cVTEditor.getExplorerManager());
//         NotifyDescriptor d =new NotifyDescriptor.Confirmation(
//         msp,msp.getName(),NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);
//         DialogDisplayer.getDefault().notify(d);
//       }
//        else
//        {
//         NotifyDescriptor d = new NotifyDescriptor.Confirmation("Δεν ύπαρχει πρόγραμμα για να γίνει ΕΥΡΕΣΗ!",
//            "Μη Δυνατότητα Ευρέσης",NotifyDescriptor.DEFAULT_OPTION, NotifyDescriptor.INFORMATION_MESSAGE);
//         DialogDisplayer.getDefault().notify(d);
//        }
//
//
//    }
//
//
//
// public void resultChanged(LookupEvent ev) {
//        Collection<VTEditor> c = result.allInstances();
//        if (!c.isEmpty())
//            cVTEditor = (VTEditor) c.iterator().next();
//    }
//
//}




public class mySearchAction extends AbstractAction implements LookupListener, ContextAwareAction {
    private Lookup context;
    Lookup.Result<VTEditor> result;
    private static mySearchAction searchAction=null;
    private  VTEditor cVTEditor=null;




    public mySearchAction() {
        this(Utilities.actionsGlobalContext());
        searchAction=this;
    }


 public mySearchAction(Lookup context) {
    init(context);
    putValue(NAME,"Find");
    putValue("iconBase","org/coeus/vteditor/resources/search.png");
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

    public static mySearchAction getSearchAction()
    {return searchAction;}

    public void actionPerformed(ActionEvent e) {
       
         mySearchPanel msp= new mySearchPanel(cVTEditor.getRootNode(),cVTEditor.getExplorerManager());
         NotifyDescriptor d =new NotifyDescriptor.Confirmation(
         msp,msp.getName(),NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);
         DialogDisplayer.getDefault().notify(d);

         new checkActionsState();
    }

    public void resultChanged(LookupEvent ev) {
       checkSearchAction();
       }

    public Action createContextAwareInstance(Lookup actionContext) {
       return new mySearchAction(context);
    }


     public void checkSearchAction()
  {
    if (result.allClasses().size() != 0)
    {
     cVTEditor =  result.allInstances().iterator().next();
     setEnabled(true);
    }
    else setEnabled(false);

  }
}
