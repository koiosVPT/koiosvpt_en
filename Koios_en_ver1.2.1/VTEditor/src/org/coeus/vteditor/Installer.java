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
import java.io.File;
import java.util.Set;
import org.coeus.vteditor.actions.mySaveAllAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.modules.ModuleInstall;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

   Lookup.Result<VTEditor> result;

    @Override
    public boolean closing() {
        //return super.closing();
        ActionEvent e=null;
        result=Utilities.actionsGlobalContext().lookupResult(VTEditor.class);
        if(result.allItems().size()!=0)
        {
            boolean temp=false;

            Set<TopComponent> TCSet= TopComponent.getRegistry().getOpened();
            for (TopComponent TC :TCSet )
             {
              if (TC instanceof VTEditor)
              {
               VTEditor vte=(VTEditor)TC;
               temp=temp || vte.getModificationMade().isModificationMade();
              }
            }

            if (temp)
            {
             NotifyDescriptor d = new NotifyDescriptor.Confirmation("Program(s) has/have been changed. Do you want to save these changes before exiting?",
                     "Save Changes",NotifyDescriptor.YES_NO_CANCEL_OPTION, NotifyDescriptor.QUESTION_MESSAGE);

              Object answer=DialogDisplayer.getDefault().notify(d);
              if (answer == NotifyDescriptor.YES_OPTION)
              {
              mySaveAllAction saveAll =new mySaveAllAction();
              saveAll.actionPerformed(e); 
              }
              else if (answer == NotifyDescriptor.CANCEL_OPTION)
               return false;
            }

        }

     File f=new File("myJOP.class");
     if (f.exists())f.delete();
     
     return true;
    }



//    @Override
//    public void restored() {
//        // By default, do nothing.
//        // Put your startup code here.
//    }
}
