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
public class getParametersList {

 
    private AllObjectsList aol=null;
  

    private String[] dispNames=null;
    private String[] objNames=null;
    private String[] dispTypes=null;
    private String[] objTypes=null;

    private LinkedList<String> dispParams=null;
    private LinkedList<String>  objParams=null;
    private LinkedList<String>  dispParamsTypes=null;
    private LinkedList<String>  objParamsTypes=null;
    private int size=0;

   // private int numParams=0;

    private String objScope=null;

    public getParametersList(String iObjScope,String type)
    {
        this.objScope=iObjScope;
        aol= AllObjectsList.getAllObjList();
        //Functions,Procedures : objName=objScope
        AllObjects aoi = aol.SearchByObjectScope_4FunctionsOrProcedures(iObjScope);
        
           dispParams=new LinkedList<String>();
           objParams=new LinkedList<String>();
           dispParamsTypes=new LinkedList<String>();
           objParamsTypes=new LinkedList<String>();
        
        

        if (aoi!=null)
        {
        if (type.equalsIgnoreCase("all_datatypes"))
        {
        dispParams.addAll(aoi.getDispParams());
        objParams.addAll(aoi.getObjParams());
        dispParamsTypes.addAll(aoi.getDispParamsTypes());
        objParamsTypes.addAll(aoi.getObjParamsTypes());
        }
        else //if (!aoi.getObjParamsTypes().isEmpty())
        {
         for (int p=0;p<aoi.getObjParamsTypes().size();p++)
         {
          if (aoi.getObjParamsTypes().get(p).equalsIgnoreCase(type))
          {
            dispParams.add(aoi.getDispParams().get(p));
            objParams.add(aoi.getObjParams().get(p));
            dispParamsTypes.add(aoi.getDispParamsTypes().get(p));
            objParamsTypes.add(aoi.getObjParamsTypes().get(p));         
          }             
         }         
        }
    }

        if (!dispParams.isEmpty())
        {
         createStrings();
        }

    }


    public getParametersList(String iObjScope,LinkedList<String> iDispParams,
            LinkedList<String> iObjParams, LinkedList<String> iDispParamsTypes,
            LinkedList<String> iObjParamsTypes)
    {
    this.objScope=iObjScope;

     dispParams=new LinkedList<String>();
     objParams=new LinkedList<String>();
     dispParamsTypes=new LinkedList<String>();
     objParamsTypes=new LinkedList<String>();

     dispParams.addAll(iDispParams);
     objParams.addAll(iObjParams);
     dispParamsTypes.addAll(iDispParamsTypes);
     objParamsTypes.addAll(iObjParamsTypes);

     if (!dispParams.isEmpty())
        {
         createStrings();
        }
    }


   public void createStrings()
   {
          size=dispParams.size();
          dispNames=new String[size];
          objNames=new String[size];
          dispTypes=new String[size];
          objTypes=new String[size];

          for (int i=0; i<dispParams.size();i++)
          {
            dispNames[i]=dispParams.get(i);
            objNames[i]=objParams.get(i);
            dispTypes[i]=dispParamsTypes.get(i);
            objTypes[i]=objParamsTypes.get(i);
          }
   }



  public String [][] getStringArraystWithParameters()
 { return new String[][]{ dispNames, objNames,dispTypes,objTypes};}



  public int getListWithParametersSize()
  {return this.size; }

 public String [] getDisplayNames()
 {return this.dispNames;}

 public String [] getDisplayTypess()
 {return this.dispTypes;}

 public String [] getObjectNames()
 {return this.objNames;}

 public String [] getObjectTypes()
 {return this.objTypes;}

 public String getObjScope()
 {return this.objScope;}

 public LinkedList<String> getDispParams ()
 {return this.dispParams;}

 public LinkedList<String> getObjParams ()
 {return this.objParams;}

 public LinkedList<String> getDispParamsTypes ()
 {return this.dispParamsTypes;}

 public LinkedList<String> getObjParamsTypes()
 {return this.objParamsTypes;}




}
