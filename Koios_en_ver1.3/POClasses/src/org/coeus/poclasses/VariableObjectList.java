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
public class VariableObjectList implements LookupListener {

    public LinkedList <VariableObject> varList;
    private  Lookup.Result result = null;
    private static VariableObjectList varol=null;



    public VariableObjectList (){
       this.varList =new LinkedList();
       Lookup.Template tpl = new Lookup.Template (VariableObjectList.class);
       result = Utilities.actionsGlobalContext().lookup(tpl);
       result.addLookupListener (this);
       varol=this;
    }

     public LinkedList <VariableObject> copyVariableObjectList (){
       LinkedList <VariableObject> voList =new LinkedList();
       for (VariableObject vo:this.varList)
           voList.add(new VariableObject(vo));
       return voList;
    }

  public VariableObjectList(VariableObjectList vol)
    {
    this.varList =new LinkedList();
    this.varList.addAll(vol.varList);
    this.result=vol.result;
    }


    public void add2List(VariableObject o) {
       varol.varList.add(o);
       AllObjects ao =new AllObjects(o.getDispCateg(),o.getDispName(),o.getDispType(),
       o.getObjName(),o.getObjType(),o.getObjValue(),o.getObjScope(),o.getDispScope(),null,null,null,null,null,null);
       AllObjectsList caol = AllObjectsList.getAllObjList();
       caol.add2List(ao);
    }

    public void removeFromList(VariableObject o)
    {
     varol.varList.remove(o);
     AllObjectsList caol = AllObjectsList.getAllObjList();
     AllObjects ao = caol.SearchByDisplayName_DispScope(o.getDispName(),o.getDispScope());
     caol.removeFromList(ao);
    }

    public int getSize ()
    {
     return varol.varList.size();
    }

    public static VariableObjectList getVarObjList()
    {
     return varol;
    }



    public void resultChanged(LookupEvent ev) {
        Lookup.Result r = (Lookup.Result) ev.getSource();
        Collection c = r.allInstances();
        if (!c.isEmpty()) {
            VariableObjectList cvol = (VariableObjectList) c.iterator().next();
            varol=cvol;
          }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

