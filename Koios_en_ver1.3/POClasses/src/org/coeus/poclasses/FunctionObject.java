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
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards.Funs.FunWizardAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jrd
 */
public class FunctionObject {
    private String dispName=null;
    private String dispType=null;
    private String dispCategorie=null;
    private String dispScope=null;
    private LinkedList<String> dispParams=new LinkedList();
    private LinkedList<String> objParams=new LinkedList();
    private LinkedList<String> dispParamsTypes=new LinkedList();
    private LinkedList<String> objParamsTypes=new LinkedList();
    private String objName=null;
    private String objType=null;
    private String objScope=null;
    private int [] Greek2EngChanges=null;
    private int [][] parametersGreek2EngChanges=null;
    private String paramNum=null;
    private boolean created=false;

 
   LinkedList<String> locked=new LinkedList();

    private ActionEvent e;

    public FunctionObject ()
    {
  
    FunWizardAction FunAction = new FunWizardAction(false,null);
    FunAction.actionPerformed(e);
    created=FunAction.getCreated();
    if(created==true)
    {
    this.dispName=FunAction.getFunName();
    this.dispType=FunAction.getFunType();
    this.dispParams = FunWizardAction.getFunDispParameters();
    this.dispParamsTypes = FunWizardAction.getFunDispParametersTypes();
    this.paramNum= FunAction.getFunParamNum();
    Disp2ObjParameters(this.dispParams,this.dispParamsTypes);
    this.dispCategorie=WizardsDefinitions.FUNCTION;

   
    ConvertGreek2English cG2E = new ConvertGreek2English(this.dispName);
    this.objName=cG2E.getEnglishIdintifier();
    this.Greek2EngChanges=cG2E.getGreek2EnglishChanges();

    this.dispScope=WizardsDefinitions.FUNCTION+" "+this.dispName;
    this.objScope=this.objName;


    if (this.dispType.equals(WizardsDefinitions.INT1))
    {this.objType=JavaProgram.INT;}
    else if (this.dispType.equals(WizardsDefinitions.FLOAT1))
    {this.objType=JavaProgram.DOUBLE;}
    else if (this.dispType.equals(WizardsDefinitions.CHAR1))
    {this.objType=JavaProgram.CHAR;}
    else if (this.dispType.equals(WizardsDefinitions.BOOLEAN1))
    {this.objType=JavaProgram.BOOLEAN;}
    else if (this.dispType.equals(WizardsDefinitions.STRING1))
    {this.objType=JavaProgram.STRING;}


    this.created=true;
    FunctionObjectList fol= FunctionObjectList.getFunObjList();
    fol.add2List(this);
    }
    }



public FunctionObject(String iDispName,String iDispType,LinkedList<String> iDispParams,
       LinkedList<String> iObjParams,LinkedList<String> iDispParamsTypes,
       LinkedList<String> iObjParamsTypes,String iObjName,String iObjType,String iDispScope,
       String iObjScope,LinkedList<String> iLocked,int [] iGreek2EnglishChanges
       ,int [][] iparametersGreek2EngChanges)
    {
  
    this.dispName=iDispName;
    this.dispType=iDispType;
    this.dispParams=iDispParams;
    this.objParams=iObjParams;
    this.dispParamsTypes=iDispParamsTypes;
    this.objParamsTypes=iObjParamsTypes;
//    this.dispParams.addAll(iDispParams);
//    this.objParams.addAll(iObjParams);
//    this.dispParamsTypes.addAll(iDispParamsTypes);
//    this.objParamsTypes.addAll(iObjParamsTypes);
    this.dispCategorie=WizardsDefinitions.FUNCTION;
    this.objName=iObjName;
    this.objType=iObjType;
    this.dispScope=iDispScope;
    this.objScope=iObjScope;
    this.locked.addAll(iLocked);
    this.Greek2EngChanges=iGreek2EnglishChanges;
    this.parametersGreek2EngChanges=iparametersGreek2EngChanges;
    this.paramNum=Integer.toString(this.dispParams.size());
    this.created=true;
    FunctionObjectList fol= FunctionObjectList.getFunObjList();
    fol.add2List(this);        
    }



public FunctionObject(FunctionObject fo )
    {

    this.dispName=fo.dispName;
    this.dispType=fo.dispType;
    this.dispParams.addAll(fo.dispParams);
    this.objParams.addAll(fo.objParams);
    this.dispParamsTypes.addAll(fo.dispParamsTypes);
    this.objParamsTypes.addAll(fo.objParamsTypes);
    this.dispCategorie=WizardsDefinitions.FUNCTION;
    this.objName=fo.objName;
    this.objType=fo.objType;
    this.dispScope=fo.dispScope;
    this.objScope=fo.objScope;
    this.locked.addAll(fo.locked);
    this.Greek2EngChanges=fo.Greek2EngChanges;
    this.parametersGreek2EngChanges=fo.parametersGreek2EngChanges;
    this.paramNum=fo.paramNum;
    this.created=fo.created;
    //FunctionObjectList fol= FunctionObjectList.getFunObjList();
    //fol.add2List(this);
    }



 public void UpdateFunctionObject()//(ConstantObject co)
    {

 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new FunctionObjectProperties(this) ,"Confirmation Changing "+this.dispName+" ("+ this.dispCategorie+")",
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
   {
    if(!locked.isEmpty())
     {
            String uses="";
            for (String s:locked)
                uses=uses+s+"\n";

             NotifyDescriptor d0 =new NotifyDescriptor.Confirmation(
            "The attributes of "+this.dispName+" ("+ this.dispCategorie+") cannot be changed, because it or its parameters\n" +
            "are being used in the following command/s: \n"+uses+
            "If you want to change this FUNCTION, change or delete all commands that is being used in!",
            "Error Changing "+this.dispCategorie+" Properties",
            NotifyDescriptor.PLAIN_MESSAGE,NotifyDescriptor.ERROR_MESSAGE);

            DialogDisplayer.getDefault().notify(d0);

     }
    else
     {
     FunctionObjectList fol= FunctionObjectList.getFunObjList();
     fol.removeFromList(this);
     String [] attributes =new String [3];
     attributes[0]=this.getDispName();
     attributes[1]=this.getDispType();

    FunWizardAction FunAction = new FunWizardAction(true,attributes);
    FunWizardAction.setFunDispParameters(this.dispParams);
    FunWizardAction.setFunDispParametersTypes(this.dispParamsTypes);

    FunAction.actionPerformed(e);

    created=FunAction.getCreated();
    
    if(created==true)
    {
    this.dispName=FunAction.getFunName();
    this.dispType=FunAction.getFunType();
    this.dispParams = FunWizardAction.getFunDispParameters();
    this.dispParamsTypes = FunWizardAction.getFunDispParametersTypes();
    this.paramNum= FunAction.getFunParamNum();
    Disp2ObjParameters(this.dispParams,this.dispParamsTypes);


    ConvertGreek2English cG2E = new ConvertGreek2English(this.dispName);
    this.objName=cG2E.getEnglishIdintifier();
    this.Greek2EngChanges=cG2E.getGreek2EnglishChanges();

    this.dispScope=WizardsDefinitions.FUNCTION+": "+this.dispName;
    this.objScope=this.objName;

if (this.dispType.equals(WizardsDefinitions.INT1))
    {this.objType=JavaProgram.INT;}
    else if (this.dispType.equals(WizardsDefinitions.FLOAT1))
    {this.objType=JavaProgram.DOUBLE;}
    else if (this.dispType.equals(WizardsDefinitions.CHAR1))
    {this.objType=JavaProgram.CHAR;}
    else if (this.dispType.equals(WizardsDefinitions.BOOLEAN1))
    {this.objType=JavaProgram.BOOLEAN;}
    else if (this.dispType.equals(WizardsDefinitions.STRING1))
    {this.objType=JavaProgram.STRING;}

  

    this.created=true;
    }
     fol.add2List(this);
   }
 }

}
 
 public void Disp2ObjParameters (LinkedList<String> inParams,LinkedList<String> inParamsTypes)
{
 objParams.clear();
 objParamsTypes.clear();

 if (!inParams.isEmpty())
  { 
    parametersGreek2EngChanges=new int [this.dispParams.size()][];
    for (int q=0;q<inParams.size();q++)
    {
        if (inParamsTypes.get(q).equals(WizardsDefinitions.INT1))
        {objParamsTypes.add(JavaProgram.INT);}
        else if (inParamsTypes.get(q).equals(WizardsDefinitions.FLOAT1))
        {objParamsTypes.add(JavaProgram.DOUBLE);}
        else if (inParamsTypes.get(q).equals(WizardsDefinitions.CHAR1))
        {objParamsTypes.add(JavaProgram.CHAR);}
        else if (inParamsTypes.get(q).equals(WizardsDefinitions.BOOLEAN1))
        {objParamsTypes.add(JavaProgram.BOOLEAN);}
        else if (inParamsTypes.get(q).equals(WizardsDefinitions.STRING1))
        {objParamsTypes.add(JavaProgram.STRING);}
         
      ConvertGreek2English cG2E = new ConvertGreek2English(dispParams.get(q));
      objParams.add(cG2E.getEnglishIdintifier());//+",";
      parametersGreek2EngChanges[q]=cG2E.getGreek2EnglishChanges();      
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

  public LinkedList<String> getObjParamsTypes()
 {return this.objParamsTypes;}

 public LinkedList<String> getDispParamsTypes()
 {return this.dispParamsTypes;}


  public LinkedList<String> getObjParams()
 {return this.objParams;}

 public LinkedList<String> getDispParams()
 {return this.dispParams;}

 
  public String  getDispParams2Show()
 { String show="";

     if (!this.dispParamsTypes.isEmpty())
      {
       show=this.dispParamsTypes.get(0)+" "+this.dispParams.get(0);
          for (int i=1;i<this.dispParams.size();i++)
             show=show+", "+this.dispParamsTypes.get(i)+" "+this.dispParams.get(i);
      }

     return show;}
 
 public String  getDispParams2ShowNextLines()
 { String show="";

     if (!this.dispParamsTypes.isEmpty())
      {
       show=this.dispParamsTypes.get(0)+" "+this.dispParams.get(0);
          for (int i=1;i<this.dispParams.size();i++)
             show=show+",\n"+this.dispParamsTypes.get(i)+" "+this.dispParams.get(i);
      }

     return show;}


  public String  getObjParams2Show()
 { String show="";

      if (!this.objParamsTypes.isEmpty())
      {   show=this.objParamsTypes.get(0)+" "+this.objParams.get(0);
          for (int i=1;i<this.objParams.size();i++)
             show=show+",\n"+this.objParamsTypes.get(i)+" "+this.objParams.get(i);
      }
     
     return show;}

  public void printParametersGreek2EngChanges()
  {

    if (!this.objParams.isEmpty())
    {
      for (int i=0;i<this.objParams.size();i++)
     { System.out.println("\n i="+i+" le:"+this.objParams.get(i).length()+"\n");
         for (int j=0;j<this.objParams.get(i).length();j++)
         {
             System.out.print(parametersGreek2EngChanges[i][j]);
             System.out.println("\n");
         }
         System.out.println("\n\n");
     }
    }
  }


  public String getDispCateg()
  {return this.dispCategorie;}

  public boolean getCreated()
 {return this.created;}

   public String getDispScope()
 {return this.dispScope;}

  public String getObjScope()
 {return this.objScope;}

  public String getParamNum()
 {return this.paramNum;}

  public LinkedList<String> getLocked()
 {return this.locked;}

  public int [] getGreek2EngChanges()
  {return this.Greek2EngChanges;}

  public int [][] getParametersGreek2EngChanges()
  {return this.parametersGreek2EngChanges;}

 ///Setters
public void setDispScope(String indScope)
{this.dispScope=indScope;}

public void setObjScope(String inoScope)
{this.objScope=inoScope;}

public void setLocked(LinkedList<String> ilocked)
 {this.locked=ilocked;}


////////////PRINTERS///////////////


 public LinkedList<PrintStructure> getCoeusString1()
     { LinkedList<PrintStructure> part = new LinkedList();
    

       part.add(new PrintStructure(this.dispType+" "+CoeusProgram.FUNCTION+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       part.add(new PrintStructure(this.dispName+" ",PrintStructure.PROCEDUREUNCTION_NAME_FONT,PrintStructure.IDENTFIER_COLOR));


       if(!this.dispParams.isEmpty() )
       {
       part.add(new PrintStructure(CoeusProgram.PARAMETRES,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       part.add(new PrintStructure("( ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
       part.add(new PrintStructure(this.dispParamsTypes.get(0),PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       part.add(new PrintStructure(" "+this.dispParams.get(0),PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
       for (int pos=1;pos<this.dispParams.size();pos++)
         {
          part.add(new PrintStructure(", "+this.dispParamsTypes.get(pos),PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
          part.add(new PrintStructure(" "+this.dispParams.get(pos),PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
          }
       part.add(new PrintStructure(" )\n\n",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
       }
       else
       part.add(new PrintStructure("( )\n\n",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

       return part;
     }

     public LinkedList<PrintStructure> getCoeusString2()
     { LinkedList<PrintStructure> end = new LinkedList();
       end.add(new PrintStructure(CoeusProgram.FUNCTION2,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       end.add(new PrintStructure(this.dispName+"\n\n",PrintStructure.PROCEDUREUNCTION_NAME_FONT,PrintStructure.IDENTFIER_COLOR));
       return end;
        }


      public String getJavaString1()
     { 
     String s= "private "+this.objType+" "+this.objName+" (  ";
     if(!this.objParams.isEmpty())
     {
       for (int i=0;i<this.objParams.size();i++)
       {
              s=s+this.objParamsTypes.get(i)+" "+this.objParams.get(i)+", ";
       }

     }
        s=s.substring(0, s.length()-2)+" ) {\n";

     return s;
     }


      public String getJavaString2()
     {return  "}\n\n"; }
      
   

      public PrintStructure [] getCoeusStringCallFunction()
      { PrintStructure [] part = new PrintStructure [16]; 
         if ( (this.dispName.equals("SINE")) ||(this.dispName.equals("COSINE")) ||
         (this.dispName.equals("TANGENT")) ||(this.dispName.equals("SQUARE_ROOT")) ||
         (this.dispName.equals("LOGARITHM")) ||(this.dispName.equals("E_TO_POWER_OF")) ||
         (this.dispName.equals("INTEGER_PART")) ||(this.dispName.equals("ABSOLUTE_VALUE")) ||
         (this.dispName.equals("POWER")) )
         {}
        
        
        return part;}
      
    

 
      public String getJavaCallFuntion()
     {  if ( (this.objName.equals("Math.sin")) ||(this.objName.equals("Math.cos")) ||
             (this.objName.equals("Math.tan")) ||(this.objName.equals("Math.sqrt")) ||
             (this.objName.equals("Math.log")) ||(this.objName.equals("(int)")) ||
             (this.objName.equals("Math.exp")) ||(this.objName.equals("Math.abs")) ||
             (this.objName.equals("Math.pow")) ||(this.objName.equals(""))   )
         {}
          
          
          
          
          return  "}\n\n"; }


      public String getDescription()
 {
 String des="This command marks the beginning of FUNCTION "+
         this.dispName+ " FUNCTION.\nThe execution of the program continues with the first command of this FUNCTION." +
         "\nAll the commands in it will be executed sequentially.";

 return des;
 }

 public String getDescription2()
 {
 String des2="This command marks the end of FUNCTION "+
         this.dispName+".\nThe execution of the program returns to the command that called the FUNCTION "+this.dispName+ ".";

 return des2;
 }
}
