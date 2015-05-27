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


import java.awt.event.ActionEvent;
import java.util.LinkedList;
import org.coeus.wizards.Arrs.ArrWizardAction;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;


/**
 *
 * @author Jrd
 */
public class ArrayObject {
    private String dispName;
    private String dispType;
    private String dispCategorie;
    private String dispScope;
    private String objDims;
    private String objDim1Size;
    private String objDim2Size;
    private String objName;
    private String objType;
    private String objScope;
    private int [] Greek2EngChanges;
    private boolean created=false;
    private String [][] objValues;

    private static int arrCount = 0;
    private final int arrIndex;
    LinkedList<String> locked=new LinkedList();


    private ActionEvent e;


    public ArrayObject(String dScope,String oScope)
    {
    arrIndex=arrCount++;
    ArrWizardAction ArrAction = new ArrWizardAction(false,null);
    this.dispScope=dScope;
    this.objScope=oScope;
    ArrAction.setDispScope(this.dispScope);
    ArrAction.setObjScope(this.objScope);
    ArrAction.actionPerformed(e);
    created=ArrAction.getCreated();
    if(created==true)
    {
    this.dispName=ArrAction.getArrName();
    this.dispType=ArrAction.getArrType();
    this.objDims=ArrAction.getDims();
    this.objDim1Size=ArrAction.getDim1Size();
    this.objDim2Size=ArrAction.getDim2Size();
    this.dispCategorie=WizardsDefinitions.ARRAY;
    this.objValues= ArrWizardAction.getArrInitValue();
    ConvertGreek2English cG2E = new ConvertGreek2English(this.dispName);
    this.objName=cG2E.getEnglishIdintifier();
    this.Greek2EngChanges=cG2E.getGreek2EnglishChanges();


    if (this.dispType.equals(WizardsDefinitions.INT2))
    {this.objType=JavaProgram.INT;}
    else if (this.dispType.equals(WizardsDefinitions.FLOAT2))
    {this.objType=JavaProgram.DOUBLE;}
    else if (this.dispType.equals(WizardsDefinitions.CHAR2))
    {this.objType=JavaProgram.CHAR;}
    else if (this.dispType.equals(WizardsDefinitions.BOOLEAN2))
    {this.objType=JavaProgram.BOOLEAN;}
    else if (this.dispType.equals(WizardsDefinitions.STRING2))
    {this.objType=JavaProgram.STRING;}
  

    this.created=true;
    ArrayObjectList aol= ArrayObjectList.getArrObjList();
    aol.add2List(this);
    }


    }


 public ArrayObject(String iDispName,String iDispType,String iObjDims,String iObjDim1Size,String iObjDim2Size,
         String iObjValue,String iObjName,String iObjType,String iDispScope, String iObjScope,LinkedList<String> iLocked,
         String [][] iObjValues,int [] iGreek2EnglishChanges )
    {
    arrIndex=arrCount++;
    this.dispName=iDispName;
    this.dispType=iDispType;
    this.objDims=iObjDims;
    this.objDim1Size=iObjDim1Size;
    this.objDim2Size=iObjDim2Size;
    this.dispCategorie=WizardsDefinitions.ARRAY;
    this.objName=iObjName;
    this.objType=iObjType;
    this.dispScope=iDispScope;
    this.objScope=iObjScope;
    this.objValues=iObjValues;
    this.Greek2EngChanges=iGreek2EnglishChanges;

    this.created=true;
    ArrayObjectList aol= ArrayObjectList.getArrObjList();
    aol.add2List(this);
    }


 public ArrayObject(ArrayObject ao )
    {
    this.arrIndex=ao.arrIndex;
    this.dispName=ao.dispName;
    this.dispType=ao.dispType;
    this.objDims=ao.objDims;
    this.objDim1Size=ao.objDim1Size;
    this.objDim2Size=ao.objDim2Size;
    this.dispCategorie=WizardsDefinitions.ARRAY;
    this.objName=ao.objName;
    this.objType=ao.objType;
    this.dispScope=ao.dispScope;
    this.objScope=ao.objScope;
    this.objValues=ao.objValues;
    this.Greek2EngChanges=ao.Greek2EngChanges;
    this.created=ao.created;
    this.locked.addAll(ao.locked);
    //ArrayObjectList aol= ArrayObjectList.getArrObjList();
    //aol.add2List(this);
    }

    public void UpdateArrayObject()//(ConstantObject co)
    {


 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new ArrayObjectProperties(this) ,"Confirmation Changing "+this.dispName+" ("+ this.dispCategorie+")",
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
   {
    if(!locked.isEmpty())
     {
            String uses="";
            for (String s:locked)
                uses=uses+s+"\n";

             NotifyDescriptor d0 =new NotifyDescriptor.Confirmation(
            "The properties of "+this.dispName+" ("+ this.dispCategorie+") cannot be changed, because it is being used\n" +
            "in the following command(s): \n"+uses+
            "If you want to change this ARRAY, change or delete all commands that use it!",
            "Error Changing "+this.dispCategorie+" Properties",
            NotifyDescriptor.PLAIN_MESSAGE,NotifyDescriptor.ERROR_MESSAGE);

            DialogDisplayer.getDefault().notify(d0);

     }
    else
     {
     ArrayObjectList aol= ArrayObjectList.getArrObjList();
     aol.removeFromList(this);
     String [] attributes =new String [5];
     attributes[0]=this.dispName;
     attributes[1]=this.dispType;
     attributes[2]=this.objDims;
     attributes[3]=this.objDim1Size;
     attributes[4]=this.objDim2Size;
     ArrWizardAction ArrAction = new ArrWizardAction(true,attributes);
     ArrWizardAction.setArrInitValue(this.objValues);
     ArrAction.setDispScope(this.dispScope);
     ArrAction.setObjScope(this.objScope);
     ArrAction.actionPerformed(e);
     created=ArrAction.getCreated();
    if(created==true)
    {
    this.dispName=ArrAction.getArrName();
    this.dispType=ArrAction.getArrType();
    this.objDims=ArrAction.getDims();
    this.objDim1Size=ArrAction.getDim1Size();
    this.objDim2Size=ArrAction.getDim2Size();
    this.dispCategorie=WizardsDefinitions.ARRAY;
    this.objName="array"+Integer.toString(arrIndex);
    this.objValues= ArrWizardAction.getArrInitValue();

    ConvertGreek2English cG2E = new ConvertGreek2English(this.dispName);
    this.objName=cG2E.getEnglishIdintifier();
    this.Greek2EngChanges=cG2E.getGreek2EnglishChanges();


    if (this.dispType.equals(WizardsDefinitions.INT2))
    {this.objType=JavaProgram.INT;}
    else if (this.dispType.equals(WizardsDefinitions.FLOAT2))
    {this.objType=JavaProgram.DOUBLE;}
    else if (this.dispType.equals(WizardsDefinitions.CHAR2))
    {this.objType=JavaProgram.CHAR;}
    else if (this.dispType.equals(WizardsDefinitions.BOOLEAN2))
    {this.objType=JavaProgram.BOOLEAN;}
    else if (this.dispType.equals(WizardsDefinitions.STRING2))
    {this.objType=JavaProgram.STRING;}
  
    

     this.created=true;
    }
     aol.add2List(this);
   }
  }

}

///Getters
 public String getDispName()
 {return this.dispName;}

 public String getDispType()
 {return this.dispType;}


 public String getObjName()
 {return this.objName;}

 public String getObjType()
 {return this.objType;}

  public String [][] getObjValue()
 {return this.objValues;}

  public String getDispCateg()
  {return this.dispCategorie;}

  public boolean getCreated()
 {return this.created;}

  public String getDispScope()
 {return this.dispScope;}

 public String getObjScope()
 {return this.objScope;}

 public String getDimension()
 {return this.objDims;}

 public String getDim1Size()
 {return this.objDim1Size;}

 public String getDim2Size()
 {return this.objDim2Size;}

 public LinkedList<String> getLocked()
 {return this.locked;}

 ///Setters
public void setDispScope(String indScope)
{this.dispScope=indScope;}

public void setObjScope(String inoScope)
{this.objScope=inoScope;}

public void setLocked(LinkedList<String> ilocked)
 {this.locked=ilocked;}



 /////////Printers//////////

 public LinkedList<PrintStructure> getCoeusString1()
  {
     //int size = Integer.valueOf(this.objDim1Size)*Integer.valueOf(this.objDim2Size)*3+8*Integer.valueOf(this.objDim1Size);
     LinkedList<PrintStructure> part = new LinkedList();

       

       part.add(new PrintStructure(CoeusProgram.ARRAY+this.dispType+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

       if (this.objScope.equals("global"))
         part.add(new PrintStructure(this.dispName,PrintStructure.IDENTIFIER_FONT,PrintStructure.GLOBAL_COLOR));
       else
         part.add(new PrintStructure(this.dispName,PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

       if (this.objDims.equals("1"))
           part.add(new PrintStructure(" ["+this.objDim1Size+"] ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
       else
           part.add(new PrintStructure(" ["+this.objDim1Size+"] ["+this.objDim2Size+"] ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));


       if (this.objValues!=null)
             CreateInitialValues(part) ;
       else
             part.add(new PrintStructure(" ;\n",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
      
       return part;
     }

 private void CreateInitialValues(LinkedList<PrintStructure> psa) {
   String sp="                                                       ";
   int i,j;//,csize=Integer.valueOf(this.objDim2Size);
   psa.add(new PrintStructure(CoeusProgram.ASSIGN_IVS,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
   psa.add(new PrintStructure(" = { ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

      if (this.objType.equals(JavaProgram.INT) || this.objType.equals(JavaProgram.DOUBLE))
           {
              for (i=0;i<Integer.valueOf(this.objDim1Size);i++)
              { if (this.objDims.equals("2"))
                     psa.add(new PrintStructure("\n"+sp+" { ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                  for (j=0;j<Integer.valueOf(this.objDim2Size);j++)
                 {
                  psa.add(new PrintStructure(this.objValues[i][j],PrintStructure.IDENTIFIER_FONT,PrintStructure.NUMBER_COLOR));
                  psa.add(new PrintStructure(", ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                 }  
              if (this.objDims.equals("2"))
               {
                 psa.removeLast();
                 psa.add(new PrintStructure(" }",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                 psa.add(new PrintStructure(",",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
               }
              }  
          }
      else if (this.objType.equals(JavaProgram.STRING))
         {
              for (i=0;i<Integer.valueOf(this.objDim1Size);i++)
              { if (this.objDims.equals("2"))
                     psa.add(new PrintStructure("\n"+sp+" { ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                  for (j=0;j<Integer.valueOf(this.objDim2Size);j++)
                 {
                  psa.add( new PrintStructure(" \"",PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
                  psa.add(new PrintStructure(this.objValues[i][j],PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
                  psa.add(new PrintStructure("\"",PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
                  psa.add(new PrintStructure(", ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                 }
              if (this.objDims.equals("2"))
               {
                 psa.removeLast();
                 psa.add(new PrintStructure(" }",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                 psa.add(new PrintStructure(",",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
               }
              }
         }
       else if (this.objType.equals(JavaProgram.CHAR))
         {
              for (i=0;i<Integer.valueOf(this.objDim1Size);i++)
              { if (this.objDims.equals("2"))
                     psa.add(new PrintStructure("\n"+sp+" { ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                  for (j=0;j<Integer.valueOf(this.objDim2Size);j++)
                 {
                  psa.add( new PrintStructure(" '",PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
                  psa.add(new PrintStructure(this.objValues[i][j],PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
                  psa.add(new PrintStructure("'",PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
                  psa.add(new PrintStructure(", ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                 }
              if (this.objDims.equals("2"))
               {
                 psa.removeLast();
                 psa.add(new PrintStructure(" }",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                 psa.add(new PrintStructure(",",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
               }
              }
         }
        else if (this.objType.equals(JavaProgram.BOOLEAN))
         {
              for (i=0;i<Integer.valueOf(this.objDim1Size);i++)
              {
                  if (this.objDims.equals("2"))
                     psa.add(new PrintStructure("\n"+sp+" { ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                  for (j=0;j<Integer.valueOf(this.objDim2Size);j++)
                 {
                  psa.add(new PrintStructure(this.objValues[i][j],PrintStructure.IDENTIFIER_FONT,PrintStructure.BOOLEAN_COLOR));
                  psa.add(new PrintStructure(", ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                 }
              if (this.objDims.equals("2"))
               {
                 psa.removeLast();
                 psa.add(new PrintStructure(" }",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
                 psa.add(new PrintStructure(",",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
               }
              }
         }
   psa.removeLast();
   psa.add(new PrintStructure(" }; \n",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
  }


 public String getJavaString1()
     {String s="",sp="                 ";
      
      int rn= Integer.valueOf(this.objDim1Size);
      int cn= Integer.valueOf(this.objDim2Size);
      int i,j;

      if (this.objScope.equals("global"))
      s= "private ";
      
      s= s+ this.objType+" []";
      if (this.objDims.equals("2"))
        s=s+"[]";

      s=s+" "+this.objName+" ";
      if (this.objValues!=null)
      {
       s= s+ " = { \n"+sp;
       if (this.objType.equals(JavaProgram.INT))
       {
           for (i=0;i<rn;i++)
           {   if (this.objDims.equals("2"))
                 s=s+"{";
            for (j=0;j<cn;j++)
                s=s+this.objValues[i][j]+", ";
               if (this.objDims.equals("2"))
            s=s+"},\n"+sp;
           }
       }
       else if (this.objType.equals(JavaProgram.DOUBLE))
       {
           for (i=0;i<rn;i++)
           {if (this.objDims.equals("2"))
            s=s+"{";
            for (j=0;j<cn;j++)
                s=s+this.objValues[i][j]+", ";
            if (this.objDims.equals("2"))
            s=s+"},\n"+sp;
           }
       }
       else if (this.objType.equals(JavaProgram.CHAR))
       {
           for (i=0;i<rn;i++)
           {if (this.objDims.equals("2"))
            s=s+"{";
            for (j=0;j<cn;j++)
                s=s+"'"+this.objValues[i][j]+"', ";
            if (this.objDims.equals("2"))
            s=s+"},\n"+sp;
           }
       }
       else if (this.objType.equals(JavaProgram.STRING))
       {
           for (i=0;i<rn;i++)
           {if (this.objDims.equals("2"))
            s=s+"{";
            for (j=0;j<cn;j++)
                s=s+"\""+this.objValues[i][j]+"\", ";
            if (this.objDims.equals("2"))
            s=s+"},\n"+sp;
           }
       }
       else if (this.objType.equals(JavaProgram.BOOLEAN))
       {
           for (i=0;i<rn;i++)
           {if (this.objDims.equals("2"))
            s=s+"{";
            for (j=0;j<cn;j++)
            {
               if (this.objValues[i][j].equals(CoeusProgram.TRUE))
                s=s+"true, ";
               else if (this.objValues[i][j].equals(CoeusProgram.FALSE))
                s=s+"false, ";
            }
            if (this.objDims.equals("2"))
            s=s+"},\n"+sp;
           }
       }
       s=s+"} ; \n";
      }
      else
      {
      s= s + "= new "+this.objType +" ["+this.objDim1Size+"]";

      if (this.objDims.equals("2"))
       s=s+"["+this.objDim2Size+"]";

      s=s+" ;\n";
      }

      return s;
     }


 public String getDescription()
 {
 int i,j,rn,cn;

 rn=Integer.valueOf(this.objDim1Size);
 cn=Integer.valueOf(this.objDim2Size);
 String size="";

 if (this.objDims.equals("2"))
     size=rn+"X"+cn;
 else
     size=this.objDim1Size;

 String des="This command is creating an ARRAY with name "+this.dispName
         +", which can be used for storing "+size+" "+this.dispType+" values.";

 if (this.objValues!=null)
 {
     String tempValues="";
     for (i=0;i<rn;i++)
           {   if (this.objDims.equals("2"))
                 tempValues=tempValues+" ";
            for (j=0;j<cn;j++)
                tempValues=tempValues+this.objValues[i][j]+" ";
               if (this.objDims.equals("2"))
            tempValues=tempValues+"\n";
           }
             
             
     des=des+"\n\nDuring the creation of ARRAY "+this.dispName
         +", the following values are assigned to it:\n"+tempValues;


 }
 return des;
 }



}
