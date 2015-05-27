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

import org.coeus.vteditor.actions.checkActionsState;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.windows.WindowManager;

/**
 *
 * @author Jrd
 */
public class modificationMadeClass implements LookupListener{

    Lookup.Result<modificationMadeClass> result;
    private static modificationMadeClass currentModificationMadeClass=null;
    private boolean modificationMade;


    public modificationMadeClass()
    {
    init(Utilities.actionsGlobalContext());
    currentModificationMadeClass=this;
    this.modificationMade=false;
    }


    private void init(Lookup context) {

    result = context.lookupResult(modificationMadeClass.class);
    result.addLookupListener(this);
    //resultChanged(null);
    }

    public void resultChanged(LookupEvent ev) {
    if (result.allInstances().size() != 0)
      currentModificationMadeClass=result.allInstances().iterator().next();
    }

    public boolean isModificationMade() {
        return modificationMade;
    }

    public static modificationMadeClass getCurrentModificationMadeClass() {
        return currentModificationMadeClass;
    }

   public  void makeModification()
   { this.modificationMade=true;

     new checkActionsState();

     if (VTEditor.getCurrentVTEditor()!=null && !VTEditor.getCurrentVTEditor().getDisplayName().endsWith("*"))
        WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
            public void run() {
                 VTEditor.getCurrentVTEditor().setDisplayName(
            VTEditor.getCurrentVTEditor().getDisplayName()+"*");
            }
        });   

}

    public  void clearModifications()
   { this.modificationMade=false;
     new checkActionsState();
    }



    
}
