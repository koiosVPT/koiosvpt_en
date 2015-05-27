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


package org.coeus.poclasses;

/**
 *
 * @author Jrd
 */


import java.util.Collection;
import java.util.LinkedList;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;


/**
 *
 * @author Jrd
 */
public class ProcedureObjectList implements LookupListener {

    public LinkedList <ProcedureObject> proList;
    private  Lookup.Result result = null;
    private static ProcedureObjectList prool=null;



    public ProcedureObjectList (){
       this.proList =new LinkedList();
       Lookup.Template tpl = new Lookup.Template (ProcedureObjectList.class);
       result = Utilities.actionsGlobalContext().lookup(tpl);
       result.addLookupListener (this);
       prool=this;
    }


  public ProcedureObjectList(ProcedureObjectList pol)
    {
       this.proList =new LinkedList();
    this.proList.addAll(pol.proList);
    this.result=pol.result;
    }

public LinkedList <ProcedureObject> copyProcedureObjectList (){
       LinkedList <ProcedureObject> poList =new LinkedList();
       for (ProcedureObject po:this.proList)
           poList.add(new ProcedureObject(po));
       return poList;
    }

    public void add2List(ProcedureObject o) {
       prool.proList.add(o);
       AllObjects ao =new AllObjects(o.getDispCateg(),o.getDispName(),null,
       o.getObjName(),null,null,o.getObjScope(),o.getDispScope(),o.getDispParams(),o.getObjParams()
               ,o.getDispParamsTypes(),o.getObjParamsTypes(),null,null);
       AllObjectsList caol = AllObjectsList.getAllObjList();
       caol.add2List(ao);
    }

    public void removeFromList(ProcedureObject o)
    {
     prool.proList.remove(o);
     AllObjectsList caol = AllObjectsList.getAllObjList();
     AllObjects ao = caol.SearchByDisplayName_DispScope(o.getDispName(),o.getDispScope());
     caol.removeFromList(ao);
    }

    public int getSize ()
    {
     return prool.proList.size();
    }

    public static ProcedureObjectList getProObjList()
    {
     return prool;
    }



    public void resultChanged(LookupEvent ev) {
        Lookup.Result r = (Lookup.Result) ev.getSource();
        Collection c = r.allInstances();
        if (!c.isEmpty()) {
            ProcedureObjectList cpol = (ProcedureObjectList) c.iterator().next();
            prool=cpol;
          }
    }

}

