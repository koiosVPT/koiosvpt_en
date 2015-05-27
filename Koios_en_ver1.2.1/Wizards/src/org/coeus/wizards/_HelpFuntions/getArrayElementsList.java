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
public class getArrayElementsList {

    private AllObjectsList  aol=null;
    private String [] DispNames=null;
    private String [] ObjNames=null;
    private String [] DispTypes=null;
    private String [] ObjTypes=null;
    private String [] Dim1=null;
    private String [] Dim2=null;
    private String objScope=null;
    private int listLocalSize=0;

    private LinkedList <AllObjects> listLocal;
    private LinkedList <AllObjects> listGlobal;
    private LinkedList <String> arrayIndexes=null;

   
    private String[] indexDisplayValues=null;
    private String[] indexObjectValues=null;
    private String[] indexListObjects=null;

    public getArrayElementsList(String iObjScope,String type)
    {
        this.objScope=iObjScope;
         ////Get local and Global Arrays
         aol= AllObjectsList.getAllObjList();
        if (type.equalsIgnoreCase("all_datatypes"))
        {
         listLocal=aol.SearchByDisplayCategorie_ObjectScope(WizardsDefinitions.ARRAY,this.objScope);
         listGlobal=aol.SearchByDisplayCategorie_ObjectScope(WizardsDefinitions.ARRAY,"global");
        }else
         {
          listLocal=aol.SearchByDisplayCategorie_ObjectScope_ObjectType(WizardsDefinitions.ARRAY,iObjScope,type);
          listGlobal=aol.SearchByDisplayCategorie_ObjectScope_ObjectType(WizardsDefinitions.ARRAY,"global",type);
         }

       //////Create modelList with ArrayNames
        if (!listLocal.isEmpty()|| !listGlobal.isEmpty())
        {
        listLocal.addAll(listGlobal);
        createStrings();
        }
    }

 public  getArrayElementsList(String iObjScope,LinkedList<AllObjects> list)
 {
  this.objScope=iObjScope;
  listLocal=new LinkedList<AllObjects>();
  this.listLocal.addAll(list);
  if (!listLocal.isEmpty())
       createStrings();
  }



  public void createStrings()
  {
  listLocalSize=listLocal.size();

        DispNames=new String[listLocalSize];
        ObjNames=new String[listLocalSize];
        DispTypes=new String[listLocalSize];
        ObjTypes=new String[listLocalSize];
        Dim1=new String[listLocalSize];
        Dim2=new String[listLocalSize];

        for (int k=0;k<listLocalSize;k++)
        {
//        DispNames[k]=((AllObjects)listLocal.get(k)).getDispName();
//        ObjNames[k]=((AllObjects)listLocal.get(k)).getObjName();
//        DispTypes[k]=((AllObjects)listLocal.get(k)).getDispType();
//        ObjTypes[k]=((AllObjects)listLocal.get(k)).getObjType();
//        Dim1[k]=((AllObjects)listLocal.get(k)).getArrayDim1();
//        Dim2[k]=((AllObjects)listLocal.get(k)).getArrayDim2();
        DispNames[k]=listLocal.get(k).getDispName();
        ObjNames[k]=listLocal.get(k).getObjName();
        DispTypes[k]=listLocal.get(k).getDispType();
        ObjTypes[k]=listLocal.get(k).getObjType();
        Dim1[k]=listLocal.get(k).getArrayDim1();
        Dim2[k]=listLocal.get(k).getArrayDim2();
        }
    }





    public String [][] calculateIndexValues(int dim)
   { int temp,p,temp1,temp2;
     getVariablesList gVL = new getVariablesList(this.objScope,"int");
     getParametersList gPL = new getParametersList(this.objScope,"int");
     getConstantsList gCL = new getConstantsList(this.objScope,"int");

 
      ///Calculate Sizes for Indices,Parameters,Variables
       arrayIndexes=new LinkedList<String>();
         for ( p=0;p<dim;p++)
            arrayIndexes.add(Integer.toString(p));

        indexDisplayValues= new String [arrayIndexes.size()+gVL.getListWithVariablesSize()+
                gPL.getListWithParametersSize()+gCL.getListWithConstantsSize()];

        indexObjectValues= new String [arrayIndexes.size()+gVL.getListWithVariablesSize()+
                gPL.getListWithParametersSize()+gCL.getListWithConstantsSize()];

        indexListObjects= new String [arrayIndexes.size()+gVL.getListWithVariablesSize()+
                gPL.getListWithParametersSize()+gCL.getListWithConstantsSize()];


        /////Insert Array Indexes
        for ( p=0;p<arrayIndexes.size();p++ )
        {
            indexDisplayValues[p]=arrayIndexes.get(p);
            indexObjectValues[p]=arrayIndexes.get(p);
            indexListObjects[p]=arrayIndexes.get(p);
        }
        temp=p;

        /////Insert Parameters
        for ( p=0;p<gPL.getListWithParametersSize();p++ )
        {
            indexDisplayValues[p+temp]=gPL.getDisplayNames()[p];
            indexObjectValues[p+temp]=gPL.getObjectNames()[p];
            indexListObjects[p+temp]=gPL.getDisplayNames()[p]+" ("+gPL.getDisplayTypess()[p]+")";
        }
         temp1=p;

         ////Insert Variables
        for ( p=0;p<gVL.getListWithVariablesSize();p++ )
        {
            indexDisplayValues[p+temp+temp1]=gVL.getDisplayNames()[p];
            indexObjectValues[p+temp+temp1]=gVL.getObjectNames()[p];
            indexListObjects[p+temp+temp1]=gVL.getDisplayNames()[p]+" ("+gVL.getDisplayTypes()[p]+")";
        }    

         temp2=p;
         ////Insert Constant
        for ( p=0;p<gCL.getListWithConstantsSize();p++ )
        {
            indexDisplayValues[p+temp+temp1+temp2]=gCL.getDisplayNames()[p];
            indexObjectValues[p+temp+temp1+temp2]=gCL.getObjectNames()[p];
            indexListObjects[p+temp+temp1+temp2]=gCL.getDisplayNames()[p]+" ("+gCL.getDisplayTypes()[p]+")";

        }


   return new String[][]{indexDisplayValues,indexObjectValues,indexListObjects};
   }


 public String [][] getStringArraystWithArrays()
 { return new String[][]{ DispNames, ObjNames,DispTypes,ObjTypes,Dim1,Dim2};}

 public LinkedList<AllObjects> getListWithArrays()
 {return this.listLocal;}

  public int getListWithArraysSize()
 { int size=0;
   if (listLocal!=null)
       size=this.listLocal.size();
   return size;
 }

 public String [] getDisplayNames()
 {return this.DispNames;}

 public String [] getDisplayTypess()
 {return this.DispTypes;}

 public String [] getObjectNames()
 {return this.ObjNames;}

 public String [] getObjectTypes()
 {return this.ObjTypes;}

  public String [] getDim1()
 {return this.Dim1;}

 public String [] getDim2()
 {return this.Dim2;}

  public String [] getIndexDisplayValues()
 {return this.indexDisplayValues;}

 public String [] getIndexObjectValues()
 {return this.indexObjectValues;}

 public String getObjScope()
 {return this.objScope;}
}
