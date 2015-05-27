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
import org.coeus.wizards.Pros.ProWizardAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class ProcedureObject {

    private String dispName;
    //private String dispType;
    private String dispCategorie;
    private String dispScope;
    private LinkedList<String> dispParams=new LinkedList();
    private LinkedList<String> objParams=new LinkedList();
    private LinkedList<String> dispParamsTypes=new LinkedList();
    private LinkedList<String> objParamsTypes=new LinkedList();
    private String objName;
    private String objScope;
    private int [] Greek2EngChanges=null;
    private int [][] parametersGreek2EngChanges=null;
    private String paramNum;
    private boolean created=false;
   
    private  LinkedList<String> locked=new LinkedList();

    private ActionEvent e;
   
   
   

 

    public ProcedureObject()
    { 
     ProWizardAction ProAction = new ProWizardAction(false,null);
     ProAction.actionPerformed(e);
    created=ProAction.getCreated();
    if(created==true)
    {
    this.dispName=ProAction.getProName();
    this.dispParams = ProWizardAction.getProDispParameters();
    this.dispParamsTypes = ProWizardAction.getProDispParametersTypes();
    this.paramNum= ProAction.getProParamNum();
    Disp2ObjParameters(this.dispParams,this.dispParamsTypes);
    this.dispCategorie=WizardsDefinitions.PROCEDURE;

    ConvertGreek2English cG2E = new ConvertGreek2English(this.dispName);
    this.objName=cG2E.getEnglishIdintifier();
    this.Greek2EngChanges=cG2E.getGreek2EnglishChanges();

    this.dispScope=WizardsDefinitions.PROCEDURE+" "+this.dispName;
    this.objScope=this.objName;


    this.created=true;
    ProcedureObjectList pol= ProcedureObjectList.getProObjList();
    pol.add2List(this);
    }
    }

    public ProcedureObject (String main,String objectName)
    { 
      this.dispCategorie=WizardsDefinitions.PROCEDURE;
      this.dispName=CoeusProgram.MAIN_PROC;
      this.objName=objectName;
      this.objScope=objectName;
 //     this.dispScope=WizardsDefinitions.PROCEDURE+": "+"ΚΥΡΙΑ ΣΥΝΑΡΤΗΣΗ";
      this.dispScope="MAIN PROCEDURE";
      this.paramNum="0";

    
    ProcedureObjectList pol= ProcedureObjectList.getProObjList();
    pol.add2List(this);
      
    }


    public ProcedureObject(String iDispName,LinkedList<String> iDispParams,LinkedList<String> iObjParams,
       LinkedList<String> iDispParamsTypes,LinkedList<String> iObjParamsTypes,String iObjName,String iDispScope, 
       String iObjScope,LinkedList<String> iLocked,int [] iGreek2EnglishChanges,
       int[][] iparametersGreek2EngChanges)
    {
  
    this.dispName=iDispName;
    this.dispParams=iDispParams;
    this.objParams=iObjParams;
    this.dispParamsTypes=iDispParamsTypes;
    this.objParamsTypes=iObjParamsTypes;
//    this.dispParams.addAll(iDispParams);
//    this.objParams.addAll(iObjParams);
//    this.dispParamsTypes.addAll(iDispParamsTypes);
//    this.objParamsTypes.addAll(iObjParamsTypes);
    this.dispCategorie=WizardsDefinitions.PROCEDURE;
    this.objName=iObjName;
    this.dispScope=iDispScope;
    this.objScope=iObjScope;
    this.locked=iLocked;
    this.Greek2EngChanges=iGreek2EnglishChanges;
    this.parametersGreek2EngChanges=iparametersGreek2EngChanges;
    this.paramNum=Integer.toString(this.dispParams.size());


    this.created=true;
    ProcedureObjectList pol= ProcedureObjectList.getProObjList();
    pol.add2List(this);
    }

     public ProcedureObject(ProcedureObject po)
    {

    this.dispName=po.dispName;
    this.dispParams.addAll(po.dispParams);
    this.objParams.addAll(po.objParams);
    this.dispParamsTypes.addAll(po.dispParamsTypes);
    this.objParamsTypes.addAll(po.objParamsTypes);
    this.dispCategorie=WizardsDefinitions.PROCEDURE;
    this.objName=po.objName;
    this.dispScope=po.dispScope;
    this.objScope=po.objScope;
    this.locked.addAll(po.locked);
    this.Greek2EngChanges=po.Greek2EngChanges;
    this.parametersGreek2EngChanges=po.parametersGreek2EngChanges;
    this.paramNum=po.paramNum;

    this.created=po.created;
    //ProcedureObjectList pol= ProcedureObjectList.getProObjList();
    //pol.add2List(this);
    }


 public void UpdateProcedureObject()//(ConstantObject co)
    {

 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new ProcedureObjectProperties(this) ,"Confirmation Changing "+this.dispName+" ("+ this.dispCategorie+")",
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
            "If you want to change this PROCEDURE, change or delete all commands that is being used in!",
            "Error Changing "+this.dispCategorie+" Properties",
            NotifyDescriptor.PLAIN_MESSAGE,NotifyDescriptor.ERROR_MESSAGE);

            DialogDisplayer.getDefault().notify(d0);

     }
    else
     {
     ProcedureObjectList pol= ProcedureObjectList.getProObjList();
     pol.removeFromList(this);
     String [] attributes =new String [3];
     attributes[0]=this.getDispName();
    

    ProWizardAction ProAction = new ProWizardAction(true,attributes);
    ProWizardAction.setProDispParameters(this.dispParams);
    ProWizardAction.setProDispParametersTypes(this.dispParamsTypes);
    ProAction.actionPerformed(e);
    created=ProAction.getCreated();
    if(created==true)
    {
    this.dispName=ProAction.getProName();
    this.dispParams = ProWizardAction.getProDispParameters();
    this.dispParamsTypes = ProWizardAction.getProDispParametersTypes();
    this.paramNum= ProAction.getProParamNum();
    Disp2ObjParameters(this.dispParams,this.dispParamsTypes);

    ConvertGreek2English cG2E = new ConvertGreek2English(this.dispName);
    this.objName=cG2E.getEnglishIdintifier();
    this.Greek2EngChanges=cG2E.getGreek2EnglishChanges();

    this.dispScope=WizardsDefinitions.PROCEDURE+": "+this.dispName;
    this.objScope=this.objName;
    

    this.created=true;
    }
     pol.add2List(this);
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




 public String getObjName()
 {return this.objName;}


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
      {   show=this.dispParamsTypes.get(0)+" "+this.dispParams.get(0);
          for (int i=1;i<this.dispParams.size();i++)
             show=show+", "+this.dispParamsTypes.get(i)+" "+this.dispParams.get(i);
      }

     return show;}


 public String  getDispParams2ShowNextLines()
 { String show="";

     if (!this.dispParamsTypes.isEmpty())
      {   show=this.dispParamsTypes.get(0)+" "+this.dispParams.get(0);
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

    public void setObjName(String objName) {
        this.objName = objName;
    }


////////////PRINTERS///////////////


 public LinkedList<PrintStructure> getCoeusString1()
     { LinkedList<PrintStructure> part = new LinkedList();


       if (!this.dispName.equalsIgnoreCase(CoeusProgram.MAIN_PROC))
       {
       part.add(new PrintStructure(CoeusProgram.PROCEDURE+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       part.add(new PrintStructure(this.dispName+" ",PrintStructure.PROCEDUREUNCTION_NAME_FONT,PrintStructure.IDENTFIER_COLOR));
       }
       else
       part.add(new PrintStructure(this.dispName+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
        
       
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
     { LinkedList<PrintStructure> end = new LinkedList<PrintStructure>();
       if (this.dispName.equalsIgnoreCase(CoeusProgram.MAIN_PROC))
         end.add(new PrintStructure(CoeusProgram.PROCEDURE_ENDΜΑΙΝ,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       else
       {end.add(new PrintStructure(CoeusProgram.PROCEDURE_ENDPROC,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       end.add(new PrintStructure(this.dispName+"\n\n",PrintStructure.PROCEDUREUNCTION_NAME_FONT,PrintStructure.IDENTFIER_COLOR));
       }
       return end;
       }
     



    public String getJavaString1(String className)
     {String s;
      if (className==null)
           className=this.objName;

      if (this.dispName.equals(CoeusProgram.MAIN_PROC))
//        s= "public "+className +" () {\n"+
        s= "public void mymain () {\n"+
           "    try {\n";
      else
      {
        s= "private void "+className+" (  ";
         if(!this.objParams.isEmpty())
          {
             for (int i=0;i<this.objParams.size();i++)
              {
              s=s+this.objParamsTypes.get(i)+" "+this.objParams.get(i)+", ";
              }

           }
          s=s.substring(0, s.length()-2)+" ) {\n";
      }
     return s;
     }


      public String getJavaString2()
     { String s="";
       if (this.dispName.equals(CoeusProgram.MAIN_PROC))
          s= "}\ncatch (NumberFormatException ex) { }\n\n";

       s=s+"}\n\n";
       return s; }


      public String getDescription()
 {
 String des;
 if (this.dispName.equalsIgnoreCase(CoeusProgram.MAIN_PROC))
  des="This command marks the beginning of the MAIN PROCEDURE, which is the first PROCEDURE that is executed." +
      "\nAll the commands of the MAIN PROCEDURE will be executed sequentially.";
 else
  des="This command marks the beginning of PROCEDURE "+
         this.dispName+ ".\nThe execution of the program continues with the first command of this PROCEDURE." +
         "\nAll the commands in it will be executed sequentially.";
 return des;
 }


 public String getDescription2()
 {
 String des2;
 
 if (this.dispName.equalsIgnoreCase(CoeusProgram.MAIN_PROC))
    des2="This command marks the end of the MAIN PROCEDURE and the execution of this program is virtually finished.";
 else
    des2="This command marks the end of PROCEDURE "+
         this.dispName+".\nThe execution of the program continues with the command following the CALL PROCEDURE "+this.dispName+ " command.";

 return des2;
 }
}
