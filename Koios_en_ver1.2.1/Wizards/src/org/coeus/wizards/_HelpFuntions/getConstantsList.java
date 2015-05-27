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

package org.coeus.wizards._HelpFuntions;

import java.util.LinkedList;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;
import org.coeus.wizards.WizardsDefinitions;

/**
 *
 * @author Jrd
 */
public class getConstantsList {


    private LinkedList<AllObjects> listLocal=null;
    private LinkedList<AllObjects> listGlobal=null;
    private AllObjectsList aol=null;
    private int listSize=0;

    private String [] DispNames=null;
    private String [] ObjNames=null;
    private String [] DispTypes=null;
    private String [] ObjTypes=null;

    private String objScope=null;

 public  getConstantsList(String iObjScope,String type)
 {

       this.objScope=iObjScope;
       aol= AllObjectsList.getAllObjList();
   
     if (type.equalsIgnoreCase("all_datatypes"))
     {
       listLocal=aol.SearchByDisplayCategorie_ObjectScope(WizardsDefinitions.CONSTANT,iObjScope);
       listGlobal=aol.SearchByDisplayCategorie_ObjectScope(WizardsDefinitions.CONSTANT,"global");
     }else
     {
      listLocal=aol.SearchByDisplayCategorie_ObjectScope_ObjectType(WizardsDefinitions.CONSTANT,iObjScope,type);
      listGlobal=aol.SearchByDisplayCategorie_ObjectScope_ObjectType(WizardsDefinitions.CONSTANT,"global",type);
     }


       if (!listLocal.isEmpty() || !listGlobal.isEmpty())

        {
        listLocal.addAll(listGlobal);
        createStrings();
     }

 }

  public  getConstantsList(String iObjScope,LinkedList<AllObjects> list)
 {
  this.objScope=iObjScope;
  this.listLocal=new LinkedList<AllObjects>();
  this.listLocal.addAll(list);
  if (!listLocal.isEmpty())
       createStrings();
  }


 public void createStrings()
 {
       listSize=listLocal.size();

        DispNames=new String[listSize];
        ObjNames=new String[listSize];
        DispTypes=new String[listSize];
        ObjTypes=new String[listSize];

        for (int k=0;k<listSize;k++)
        {
        DispNames[k]=listLocal.get(k).getDispName();
        ObjNames[k]=listLocal.get(k).getObjName();
        DispTypes[k]=listLocal.get(k).getDispType();
        ObjTypes[k]=listLocal.get(k).getObjType();
        }


 }



  public String [][] getStringArraystWithConstants()
 { return new String[][]{ DispNames, ObjNames,DispTypes,ObjTypes};}

 public LinkedList<AllObjects> getListWithConstants()
 {return this.listLocal;}

 public int getListWithConstantsSize()
 {   return this.listSize;}

 public String [] getDisplayNames()
 {return this.DispNames;}

 public String [] getDisplayTypes()
 {return this.DispTypes;}

 public String [] getObjectNames()
 {return this.ObjNames;}

 public String [] getObjectTypes()
 {return this.ObjTypes;}


 public String getObjScope()
 {return this.objScope;}

}
