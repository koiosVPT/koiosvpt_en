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

/**
 *
 * @author Jrd
 */
public class getFunctionsList {

  
  
    private LinkedList<AllObjects> list=null;
    private AllObjectsList aol=null;
    private int listSize=0;

    private String [] DispNames=null;
    private String [] ObjNames=null;
    private String [] DispTypes=null;
    private String [] ObjTypes=null;
    private LinkedList <LinkedList<String>> DispParams=null;
    private LinkedList <LinkedList<String>> DispParamsTypes=null;
    private LinkedList <LinkedList<String>> ObjParams=null;
    private LinkedList <LinkedList<String>> ObjParamsTypes=null;


 public  getFunctionsList(String type)
 {
     aol= AllObjectsList.getAllObjList();

     if (type.equalsIgnoreCase("all_datatypes"))
       list=aol.SearchByObjectType_4Functions(null);
     else
       list=aol.SearchByObjectType_4Functions(type);

        DispParams=new LinkedList<LinkedList<String>>();
        DispParamsTypes=new LinkedList<LinkedList<String>>();
        ObjParams=new LinkedList<LinkedList<String>>();
        ObjParamsTypes=new LinkedList<LinkedList<String>>();
 
       if (!list.isEmpty())
       {
       createStrings();
       }

 }

 public getFunctionsList(LinkedList<AllObjects> inlist)
 {
  this.list=new LinkedList<AllObjects>();
  this.list.addAll(inlist);
        DispParams=new LinkedList<LinkedList<String>>();
        DispParamsTypes=new LinkedList<LinkedList<String>>();
        ObjParams=new LinkedList<LinkedList<String>>();
        ObjParamsTypes=new LinkedList<LinkedList<String>>();

       if (!list.isEmpty())
       {
       createStrings();
       }
 }


 public void createStrings()
 {
   listSize=list.size();

        DispNames=new String[listSize];
        ObjNames=new String[listSize];
        DispTypes=new String[listSize];
        ObjTypes=new String[listSize];

        for (int k=0;k<listSize;k++)
        {
//        DispNames[k]=((AllObjects)list.get(k)).getDispName();
//        ObjNames[k]=((AllObjects)list.get(k)).getObjName();
//        DispTypes[k]=((AllObjects)list.get(k)).getDispType();
//        ObjTypes[k]=((AllObjects)list.get(k)).getObjType();
        DispNames[k]=list.get(k).getDispName();
        ObjNames[k]=list.get(k).getObjName();
        DispTypes[k]=list.get(k).getDispType();
        ObjTypes[k]=list.get(k).getObjType();

        DispParams.add(list.get(k).getDispParams());
        DispParamsTypes.add(list.get(k).getDispParamsTypes());
        ObjParams.add(list.get(k).getObjParams());
        ObjParamsTypes.add(list.get(k).getObjParamsTypes());
        }
 }

  public String [][] getStringArraystWithFunctions()
 { return new String[][]{ DispNames, ObjNames,DispTypes,ObjTypes};}

 public LinkedList<AllObjects> getListWithFunctions()
 {return this.list;}

 public int getListWithFunctionsSize()
 {   return this.listSize;}

 public String [] getDisplayNames()
 {return this.DispNames;}

 public String [] getDisplayTypes()
 {return this.DispTypes;}

 public String [] getObjectNames()
 {return this.ObjNames;}

 public String [] getObjectTypes()
 {return this.ObjTypes;}

  public LinkedList<LinkedList<String>> getDispParams()
 {return this.DispParams;}

 public LinkedList<LinkedList<String>> getDispParamsTypes()
 {return this.DispParamsTypes;}

 public LinkedList<LinkedList<String>> getObjParams()
 {return this.ObjParams;}

 public LinkedList<LinkedList<String>> getObjParamsType()
{return this.ObjParamsTypes;}

}
