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

package org.coeus.wizards;

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
public class AllObjectsList implements LookupListener {

    public LinkedList <AllObjects> allObjects;
    private  Lookup.Result result = null;
    private static AllObjectsList allOL=null;


    public AllObjectsList()
    { this.allObjects =new LinkedList<AllObjects>();
       Lookup.Template <AllObjectsList>  tpl = new Lookup.Template<AllObjectsList>(AllObjectsList.class);
       result = Utilities.actionsGlobalContext().lookup(tpl);
       result.addLookupListener(this);
       allOL=this;}

   public LinkedList < AllObjects> copyAllObjectsList (){
       LinkedList < AllObjects> aoList =new LinkedList< AllObjects>();
       for ( AllObjects ao:this.allObjects)
           aoList.add(new  AllObjects(ao));
       return aoList;
    }


    public void add2List(AllObjects o) {
       allOL.allObjects.add(o);
    }

    public void removeFromList(AllObjects o)
    {
     allOL.allObjects.remove(o);
    }

     public static AllObjectsList getAllObjList()
    {
     return allOL;
    }

     public AllObjects SearchByDisplayName (String dispName)
    {
     for (AllObjects so:allOL.allObjects)
      {
        if(so.getDispName()!=null && so.getDispName().equalsIgnoreCase(dispName))
        { return so;}
      }
     return null;
    }

    public AllObjects SearchByDisplayName_4ConstantOrVariable (String dispName,String objScope)
    {
     for (AllObjects so:allOL.allObjects)
      {
        if(so.getDispName()!=null && so.getDispName().equalsIgnoreCase(dispName)
           &&(so.getObjScope().equalsIgnoreCase(objScope) || so.getObjScope().equalsIgnoreCase("global"))
           &&(so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.CONSTANT)
           ||so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.VARIABLE)))
        { return so;}
      }
     return null;
    }

 public AllObjects SearchByDisplayName_4Array (String dispName,String objScope)
    {
     for (AllObjects so:allOL.allObjects)
      {
        if(so.getDispName()!=null && so.getDispName().equalsIgnoreCase(dispName)
           &&(so.getObjScope().equalsIgnoreCase(objScope) || so.getObjScope().equalsIgnoreCase("global"))
           && so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.ARRAY))
        { return so;}
      }
     return null;
    }


 public AllObjects SearchByDisplayName_4Function (String dispName)
    {
     for (AllObjects so:allOL.allObjects)
      {
        if(so.getDispName()!=null && so.getDispName().equalsIgnoreCase(dispName)
           && so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION))
        { return so;}
      }
     return null;
    }

     public AllObjects SearchByObjectName (String objName)
    {
     for (AllObjects so:allOL.allObjects)
      {
        if(so.getObjName().equalsIgnoreCase(objName))
        { return so;}
      }
     return null;
    }

     public LinkedList<AllObjects> SearchByDisplayCategory (String dispCategorie)
     { LinkedList <AllObjects> list=new LinkedList<AllObjects>();

     for (AllObjects so:allOL.allObjects)
      {
        if(so.getDispCateg().equalsIgnoreCase(dispCategorie))
        { list.add(so); }
      }
     return list;
    }


     public AllObjects SearchByDisplayName_ExcludeFunctionsProcedures (String dispName)
    {
     for (AllObjects so:allOL.allObjects)
      {
        if ((!so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.PROCEDURE) )&&
                (!so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION)))
        {
         if(so.getDispName()!=null && so.getDispName().equalsIgnoreCase(dispName))
           { return so;}
        }
      }
     return null;
    }




    public AllObjects SearchByDisplayName_DispScope (String name,String dispScope)
    {
     for (AllObjects so:allOL.allObjects)
      {
        if(so.getDispName()!=null && so.getDispScope()!=null
          && so.getDispName().equalsIgnoreCase(name)&&so.getDispScope().equalsIgnoreCase(dispScope))
        { return so;}
      }
     return null;
    }



     public AllObjects SearchByObjectName_DispScope (String objName,String dispScope)
    {
     for (AllObjects so:allOL.allObjects)
      {
        if(so.getObjName().equalsIgnoreCase(objName)&&so.getDispScope().equalsIgnoreCase(dispScope))
        { return so;}
      }
     return null;
    }


     public LinkedList<AllObjects> SearchByDisplayCategorie_ObjectScope (String dispCategory,String objScope)
    { LinkedList <AllObjects> list=new LinkedList<AllObjects>();
      
     for (AllObjects so:allOL.allObjects)
      {
        if(so.getDispCateg().equalsIgnoreCase(dispCategory)&&so.getObjScope().equalsIgnoreCase(objScope))
        { list.add(so); }
      }
     return list;
    }

   public LinkedList<AllObjects> SearchByDisplayCategorie_ObjectScope_ObjectType (String dispCategory,String objScope,String objType)
    { LinkedList <AllObjects> list=new LinkedList<AllObjects>();

     for (AllObjects so:allOL.allObjects)
      {
        if(so.getDispCateg().equalsIgnoreCase(dispCategory)&&so.getObjScope().equalsIgnoreCase(objScope)
           &&so.getObjType().equalsIgnoreCase(objType)     )
        { list.add(so); }
      }
     return list;
    }


     public AllObjects SearchByDisplayName_4FunctionsOrProcedures(String name) {
       for (AllObjects so:allOL.allObjects)
      {
        if(so.getDispName()!=null && so.getDispName().equalsIgnoreCase(name)  &&
                (so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION) || so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.PROCEDURE)))
        { return so;}
      }
     return null;
    }

    public AllObjects SearchByObjectScope_4FunctionsOrProcedures(String objScope) {
       for (AllObjects so:allOL.allObjects)
      { 
        if(so.getObjScope().equalsIgnoreCase(objScope)  &&
                (so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION)
                || so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.PROCEDURE)))
        { return so;}
      }
     return null;
    }


      public LinkedList<AllObjects> SearchByObjectType_4Functions(String objType) {
      LinkedList <AllObjects> list=new LinkedList<AllObjects>();
      
     for (AllObjects so:allOL.allObjects)
      {
       if (objType==null)
       {
       if(so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION))
              { list.add(so); }
       }
       else
       {if(so.getObjType()!=null  && so.getObjType().equalsIgnoreCase(objType) &&
                so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION))
              { list.add(so); }
       }
     }
    return list;
  }


   public LinkedList<AllObjects> SearchByObjectType_4Procedures() {
      LinkedList <AllObjects> list=new LinkedList<AllObjects>();

     for (AllObjects so:allOL.allObjects)
      {
       if(so.getDispCateg().equalsIgnoreCase(WizardsDefinitions.PROCEDURE)
               && !so.getDispName().equalsIgnoreCase(WizardsDefinitions.MAIN_PROC))
              { list.add(so); }
     }
    return list;
  }



    public void resultChanged(LookupEvent ev) {
        Lookup.Result r = (Lookup.Result) ev.getSource();
        Collection c = r.allInstances();
        if (!c.isEmpty()) {
            AllObjectsList cAllOL = (AllObjectsList) c.iterator().next();
            allOL=cAllOL;
          }
    }

}
