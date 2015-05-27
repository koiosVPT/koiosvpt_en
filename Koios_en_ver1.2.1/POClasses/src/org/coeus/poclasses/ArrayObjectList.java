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
public class ArrayObjectList implements LookupListener {

    public LinkedList <ArrayObject> arrList;
    private  Lookup.Result result = null;
    private static ArrayObjectList arrol=null;



    public ArrayObjectList (){
       this.arrList =new LinkedList();
       Lookup.Template tpl = new Lookup.Template (ArrayObjectList.class);
       result = Utilities.actionsGlobalContext().lookup(tpl);
       result.addLookupListener (this);
       arrol=this;
    }

    public ArrayObjectList(ArrayObjectList aol)
    {
       this.arrList =new LinkedList();
    this.arrList.addAll(aol.arrList);
    this.result=aol.result;
    }

public LinkedList <ArrayObject> copyArrayObjectList (){
       LinkedList <ArrayObject> aoList =new LinkedList();
       for (ArrayObject ao:this.arrList)
           aoList.add(new ArrayObject(ao));
       return aoList;
    }

    public void add2List(ArrayObject o) {
       arrol.arrList.add(o);
       AllObjects ao =new AllObjects(o.getDispCateg(),o.getDispName(),o.getDispType(),
       o.getObjName(),o.getObjType(),null,o.getObjScope(),o.getDispScope(),null,null,null,null,
       o.getDim1Size(),o.getDim2Size());
       AllObjectsList caol = AllObjectsList.getAllObjList();
       caol.add2List(ao);
    }

    public void removeFromList(ArrayObject o)
    {
     arrol.arrList.remove(o);
     AllObjectsList caol = AllObjectsList.getAllObjList();
     AllObjects ao = caol.SearchByDisplayName_DispScope(o.getDispName(),o.getDispScope());
     caol.removeFromList(ao);
    }


    
    public int getSize ()
    {
     return arrol.arrList.size();
    }

    public static ArrayObjectList getArrObjList()
    {
     return arrol;
    }



    public void resultChanged(LookupEvent ev) { 
        Lookup.Result r = (Lookup.Result) ev.getSource();
        Collection c = r.allInstances();
        if (!c.isEmpty()) {
            ArrayObjectList caol = (ArrayObjectList) c.iterator().next();
            arrol=caol;
          }
    }

}

