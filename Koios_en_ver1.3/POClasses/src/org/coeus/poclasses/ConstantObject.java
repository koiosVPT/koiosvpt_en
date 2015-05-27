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
import org.coeus.wizards.Cons.ConWizardAction;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;


/**
 *
 * @author Jrd
 */
public class ConstantObject {
    private String dispName;
    private String dispType;
    private String dispCategorie;
    private String dispScope;
    private String objValue;
    private String objName;
    private String objType;
    private String objScope;
    private int [] Greek2EngChanges;
    private boolean created=false;

    private static int conCount = 0;
    private final int conIndex;
    LinkedList<String> locked=new LinkedList();

    
    private ActionEvent e;


    public ConstantObject(String dScope,String oScope)
    {
    conIndex=conCount++;
    ConWizardAction ConAction = new ConWizardAction(false,null);
    this.dispScope=dScope;
    this.objScope=oScope;
    ConAction.setDispScope(this.dispScope);
    ConAction.setObjScope(this.objScope);
    ConAction.actionPerformed(e);
    created=ConAction.getCreated();
    if(created==true)
    {
    this.dispName=ConAction.getConName();
    this.dispType=ConAction.getConType();
    this.objValue=ConAction.getConInitValue();
    this.dispCategorie=WizardsDefinitions.CONSTANT;
    this.dispScope=dScope;
    this.objScope=oScope;
    ConvertGreek2English cG2E = new ConvertGreek2English(this.dispName);
    this.objName=cG2E.getEnglishIdintifier();
    this.Greek2EngChanges=cG2E.getGreek2EnglishChanges();



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
    ConstantObjectList col= ConstantObjectList.getConObjList();
    col.add2List(this);
    }
    

    }


 public ConstantObject(String iDispName,String iDispType,String iObjValue,String iObjName,
         String iObjType,String iDispScope, String iObjScope,LinkedList<String> iLocked,int [] iGreek2EnglishChanges )
    {
    conIndex=conCount++;
    this.dispName=iDispName;
    this.dispType=iDispType;
    this.objValue=iObjValue;
    this.dispCategorie=WizardsDefinitions.CONSTANT;
    this.objType=iObjType;
    this.dispScope=iDispScope;
    this.objScope=iObjScope;
    this.objName=iObjName;
    this.Greek2EngChanges=iGreek2EnglishChanges;
    this.locked=iLocked;

    this.created=true;
    ConstantObjectList col= ConstantObjectList.getConObjList();
    col.add2List(this);
    }


  public ConstantObject(ConstantObject co )
    {
    this.conIndex=co.conIndex;
    this.dispName=co.dispName;
    this.dispType=co.dispType;
    this.objValue=co.objValue;
    this.dispCategorie=WizardsDefinitions.CONSTANT;
    this.objType=co.objType;
    this.dispScope=co.dispScope;
    this.objScope=co.objScope;
    this.objName=co.objName;
    this.Greek2EngChanges=co.Greek2EngChanges;
    this.locked.addAll(co.locked);
    this.created=co.created;
    //ConstantObjectList col= ConstantObjectList.getConObjList();
    //col.add2List(this);
    }



    public void UpdateConstantObject()//(ConstantObject co)
    {
   
/*Dialog d= DialogDisplayer.getDefault().createDialog(new DialogDescriptor(new ConstantObjectProperties(this),"Ιδιότητες ΣΤΑΘΕΡΑΣ: "+this.getDispName()
         ,true,DialogDescriptor.YES_NO_OPTION,null,DialogDescriptor.BOTTOM_ALIGN,null,
         new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               
                if (e.getSource().toString().equals("0"))
                {answer=true;}
                else
                {answer=false;}
            }
        }));
d.setVisible(true);
d.toFront();*/
 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new ConstantObjectProperties(this) ,"Confirmation Changing "+this.dispName+" ("+ this.dispCategorie+")",
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
            "If you want to change this CONSTANT, change or delete all commands that use it!",
            "Error Changing "+this.dispCategorie+" Properties",
            NotifyDescriptor.PLAIN_MESSAGE,NotifyDescriptor.ERROR_MESSAGE);

            DialogDisplayer.getDefault().notify(d0);

     }
    else
     {
     ConstantObjectList col= ConstantObjectList.getConObjList();
     col.removeFromList(this);
     String [] attributes =new String [3];
     attributes[0]=this.getDispName();
     attributes[1]=this.getDispType();
     attributes[2]=this.getObjValue();

    ConWizardAction ConAction = new ConWizardAction(true,attributes);
    ConAction.setDispScope(this.dispScope);
    ConAction.setObjScope(this.objScope);
    ConAction.actionPerformed(e);
    created=ConAction.getCreated();
    if(created==true)
    {
    this.dispName=ConAction.getConName();
    this.dispType=ConAction.getConType();
    this.objValue=ConAction.getConInitValue();

    ConvertGreek2English cG2E = new ConvertGreek2English(this.dispName);
    this.objName=cG2E.getEnglishIdintifier();
    this.Greek2EngChanges=cG2E.getGreek2EnglishChanges();
 

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
     col.add2List(this);
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

  public String getObjValue()
 {return this.objValue;}

  public String getDispCateg()
  {return this.dispCategorie;}

  public boolean getCreated()
 {return this.created;}

  public String getDispScope()
 {return this.dispScope;}
  
  public String getObjScope()
 {return this.objScope;}

  public LinkedList<String> getLocked()
 {return this.locked;}

 public int [] getGreek2EngChanges()
 {return this.Greek2EngChanges;}
  
 ///Setters
public void setDispScope(String indScope)
{this.dispScope=indScope;}

public void setObjScope(String inoScope)
{this.objScope=inoScope;}

public void setLocked(LinkedList<String> ilocked)
 {this.locked=ilocked;}

 public void  getGreek2EngChanges(int [] inGr2EnChs)
 {this.Greek2EngChanges=inGr2EnChs;}

/////////Printers//////////

 public LinkedList<PrintStructure> getCoeusString1()
     { LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();


       part.add(new PrintStructure(CoeusProgram.CONSTANT+this.dispType+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

       if (this.objScope.equals("global"))
         part.add(new PrintStructure(this.dispName,PrintStructure.CONSTANT_FONT,PrintStructure.GLOBAL_COLOR));
       else
         part.add(new PrintStructure(this.dispName,PrintStructure.CONSTANT_FONT,PrintStructure.IDENTFIER_COLOR));

       part.add(new PrintStructure(CoeusProgram.ASSIGN_IV,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       part.add(new PrintStructure(" = ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));


       if (this.objType.equals(JavaProgram.INT) || this.objType.equals(JavaProgram.DOUBLE))
       {
           part.add(new PrintStructure(this.objValue,PrintStructure.IDENTIFIER_FONT,PrintStructure.NUMBER_COLOR));
           part.add(new PrintStructure(" ;\n",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

       }
     else if (this.objType.equals(JavaProgram.CHAR))
     {
         part.add(new PrintStructure("'",PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
         part.add(new PrintStructure(this.objValue,PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
         part.add(new PrintStructure("' ;\n",PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
     }
     else if (this.objType.equals(JavaProgram.STRING))
     {
         part.add(new PrintStructure("\"",PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
         part.add(new PrintStructure(this.objValue,PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
         part.add(new PrintStructure("\" ;\n",PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
     }
     else if (this.objType.equals(JavaProgram.BOOLEAN))
     {
         part.add(new PrintStructure(this.objValue,PrintStructure.IDENTIFIER_FONT,PrintStructure.BOOLEAN_COLOR));
         part.add(new PrintStructure(" ;\n",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
 
     }

 
       return part;
     }


     public String getJavaString1()
     { String s = "";
         if (this.objScope.equals("global"))
           s= "private ";//static ";

         s=s+ "final "+ this.objType+" "+this.objName+" = ";

           if (this.objType.equals(JavaProgram.INT))
                s = s + this.objValue+ " ;\n";
            else if (this.objType.equals(JavaProgram.CHAR))
                s = s +" '" +this.objValue+"' "+ " ;\n";
            else if (this.objType.equals(JavaProgram.DOUBLE))
//                s = s +" (float) "+ this.objValue+ " ;\n";
                  s = s + this.objValue+ " ;\n";
           else if (this.objType.equals(JavaProgram.STRING))
                s = s +" \"" +this.objValue+"\" "+ " ;\n";
            else if (this.objType.equals(JavaProgram.BOOLEAN))
            {
              if (this.objValue.equals(CoeusProgram.TRUE))
                s = s + "true ;\n";
              else
                s = s + "false ;\n";
            }
         
      
       return s;
     }

     public String getDescription()
 {
 String des="This command is creating a CONSTANT of "+this.dispType+" type, with name "+this.dispName
         +" and initial value "+this.objValue+".\n\nThe value of this CONSTANT cannot change during the execution of the program.";
      
  return des;
 }
 

}
