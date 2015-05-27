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

import org.coeus.vteditor.programState;
import org.coeus.vteditor.modificationMadeClass;
import java.util.Collection;
import java.util.LinkedList;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 *
 * @author Jrd
 */
public class undoRedoClassList implements LookupListener {

    public LinkedList <undoRedoClass> undoRedoList;
    private  Lookup.Result result = null;
    private static undoRedoClassList undoRedoClassList=null;
    private int index=-1;

     public undoRedoClassList (){
       this.undoRedoList =new LinkedList();
       Lookup.Template tpl = new Lookup.Template (undoRedoClassList.class);
       result = Utilities.actionsGlobalContext().lookup(tpl);
       result.addLookupListener(this);
       undoRedoClassList=this;

    }


     public int getSize ()
    {
     return  undoRedoClassList.getUndoRedoList().size();
    }

    public static undoRedoClassList getUndoRedoClassList()
    {
     return undoRedoClassList;
    }

    public void addUndoInsatnce(undoRedoClass udrc)
    {
    if (index!=this.undoRedoList.size()-1)
    {
        int  listSize=this.undoRedoList.size()-1;
        for (int i=index+1;i<=listSize;i++)
                   this.undoRedoList.removeLast();
    }
    this.undoRedoList.add(udrc);
    index++;
    }


    public undoRedoClass getUndo()
    {
     index--; //to eixa etsi kai de douleve epidi otan to index htan 0 prota pigaine -1 kai meta eperne to stigmiotypo
     undoRedoClass urc = this.undoRedoList.get(index);
     modificationMadeClass.getCurrentModificationMadeClass().makeModification();
     programState.getCurrentProgramState().setCompilerStateUNCOMPILED();
     return urc;
    }

    
    public undoRedoClass getRedo()
    {
     index++;   
     modificationMadeClass.getCurrentModificationMadeClass().makeModification();
     programState.getCurrentProgramState().setCompilerStateUNCOMPILED();
     return  this.undoRedoList.get(index);
    }

    public void clear()
    {this.undoRedoList.clear();
    index=-1;}
    
    public int getIndex()
    {return this.index;}

   public LinkedList<undoRedoClass> getUndoRedoList()
   {return this.undoRedoList;}

    public void resultChanged(LookupEvent ev) {
        Lookup.Result r = (Lookup.Result) ev.getSource();
        Collection c = r.allInstances();
        if (!c.isEmpty()) {
            undoRedoClassList urcl = (undoRedoClassList) c.iterator().next();
            undoRedoClassList=urcl;
          }
    }
 
}
