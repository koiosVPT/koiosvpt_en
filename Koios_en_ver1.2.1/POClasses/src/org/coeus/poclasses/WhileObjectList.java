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
public class WhileObjectList implements LookupListener {

    public LinkedList <WhileObject> whileList;
    private  Lookup.Result result = null;
    private static WhileObjectList whileol=null;



    public WhileObjectList (){
       this.whileList =new LinkedList();
       Lookup.Template tpl = new Lookup.Template (WhileObjectList.class);
       result = Utilities.actionsGlobalContext().lookup(tpl);
       result.addLookupListener (this);
       whileol=this;
    }

  public WhileObjectList(WhileObjectList wol)
    {
    this.whileList =new LinkedList();
    this.whileList.addAll(wol.whileList);
    this.result=wol.result;
    }

  public LinkedList <WhileObject> copyWhileObjectList (){
       LinkedList <WhileObject> woList =new LinkedList();
       for (WhileObject wo:this.whileList)
           woList.add(new WhileObject(wo));
       return woList;
    }

    public void add2List(WhileObject o) {
       whileol.whileList.add(o);
       AllObjects ao =new AllObjects(o.getDispCateg(),o.getDispName(),null,
       o.getObjName(),null,null,o.getObjScope(),o.getDispScope(),null,null,null,null,null,null);
       AllObjectsList caol = AllObjectsList.getAllObjList();
       caol.add2List(ao);
    }

    public void removeFromList(WhileObject o)
    {
     whileol.whileList.remove(o);
     AllObjectsList caol = AllObjectsList.getAllObjList();
     AllObjects ao = caol.SearchByObjectName(o.getObjName());
     caol.removeFromList(ao);
    }

    public int getSize ()
    {
     return whileol.whileList.size();
    }

    public static WhileObjectList getWhileObjList()
    {
     return whileol;
    }



    public void resultChanged(LookupEvent ev) {
        Lookup.Result r = (Lookup.Result) ev.getSource();
        Collection c = r.allInstances();
        if (!c.isEmpty()) {
            WhileObjectList ccol = (WhileObjectList) c.iterator().next();
            whileol=ccol;
          }
    }

}

