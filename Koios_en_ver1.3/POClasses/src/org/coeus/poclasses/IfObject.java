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
import org.coeus.wizards.If.IfWizardAction;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class IfObject {

    private String dispName=null;
    private String objName=null;
    private String dispCategorie=null;
    private String dispScope=null;
    private String objScope=null;


    private  LinkedList<String> dispStatements=new LinkedList();
    private  LinkedList<String> objStatements=new LinkedList();
    private  LinkedList<String> dispStatementsCategories=new LinkedList();
    private  LinkedList <Integer> oldPosition=new LinkedList();
    private  LinkedList <Integer> currentPosition= new LinkedList();
 

    private boolean created=false;


    private static int ifCount = 0;
    private final int ifIndex;
    LinkedList<String> locked=new LinkedList();

     private ActionEvent e;


    public IfObject(String dScope,String oScope)
      {
    ifIndex=ifCount++;
    IfWizardAction IfAction = new IfWizardAction(false,null);
    this.dispScope=dScope;
    this.objScope=oScope;
    IfAction.setDispScope(this.dispScope);
    IfAction.setObjScope(this.objScope);
    IfAction.actionPerformed(e);
    created=IfAction.getCreated();
    if(created==true)
    {
    this.dispStatements=IfWizardAction.getDispStatement();
    this.objStatements=IfWizardAction.getObjStatement();
    this.dispStatementsCategories=IfWizardAction.getDispStatementCategory();
    this.oldPosition=IfWizardAction.getOldPositions();
    this.currentPosition=IfWizardAction.getCurrentPositions();

    this.dispCategorie=WizardsDefinitions.COM_IF;
    this.dispName=WizardsDefinitions.IF+(ifIndex+1);
    this.objName="if"+ifIndex;

    this.created=true;
    LockObjects lo=new LockObjects(this);
    lo.lock();
    IfObjectList iol= IfObjectList.getIfObjList();
    iol.add2List(this);
    }
    }


    public  IfObject( LinkedList<String> iDispStatement,LinkedList<String> iObjStatement,
     LinkedList<String> iDispStatementCategories,LinkedList<Integer> iOldPosition,LinkedList<Integer> iCurrentPosition,
     String dScope,String oScope,LinkedList<String> iLocked)
    {
    ifIndex=ifCount++;
    this.dispScope=dScope;
    this.objScope=oScope;
    this.dispStatements=iDispStatement;
    this.objStatements=iObjStatement;
    this.dispStatementsCategories=iDispStatementCategories;
    this.oldPosition=iOldPosition;
    this.currentPosition=iCurrentPosition;
    this.objName="if"+ifIndex;
    this.dispCategorie=WizardsDefinitions.COM_IF;
    this.dispName=WizardsDefinitions.IF+(ifIndex+1);
    this.locked=iLocked;

    this.created=true;
    IfObjectList iol= IfObjectList.getIfObjList();
    iol.add2List(this);
 }


  public IfObject( IfObject io)
    {
    ifIndex=io.ifIndex;
    this.dispScope=io.dispScope;
    this.objScope=io.objScope;
    this.dispStatements.addAll(io.dispStatements);
    this.objStatements.addAll(io.objStatements);
    this.dispStatementsCategories.addAll(io.dispStatementsCategories);
    this.oldPosition.addAll(io.oldPosition);
    this.currentPosition.addAll(io.currentPosition);
    this.objName=io.objName;
    this.dispCategorie=WizardsDefinitions.COM_IF;
    this.dispName=io.dispName;
    this.locked.addAll(io.locked);

    this.created=io.created;
    //IfObjectList iol= IfObjectList.getIfObjList();
    //iol.add2List(this);
 }


public void UpdateIfObject()
 {
 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new IfObjectProperties(this) ,"Confirmation Changing "+this.getDispName(),
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
   {
    LockObjects lo=new LockObjects(this);
    lo.unlock();
    IfObjectList iol=IfObjectList.getIfObjList();
    iol.removeFromList(this);
    String [] attributes =null;//new String [3];
   IfWizardAction IfAction = new IfWizardAction(true,attributes);
   IfAction.setDispScope(this.dispScope);
   IfAction.setObjScope(this.objScope);
   IfWizardAction.setDispStatement(this.dispStatements);
   IfWizardAction.setObjStatement(this.objStatements);
   IfWizardAction.setDispStatementCategory(this.dispStatementsCategories);
   IfWizardAction.setOldPositions(this.oldPosition);  
    IfWizardAction.setCurrentPositions(this.currentPosition);

   
    IfAction.actionPerformed(e);
    created=IfAction.getCreated();
    if(created==true)
    {
    this.dispStatements=IfWizardAction.getDispStatement();
    this.objStatements=IfWizardAction.getObjStatement();
    this.dispStatementsCategories=IfWizardAction.getDispStatementCategory();
    this.oldPosition=IfWizardAction.getOldPositions();
    this.currentPosition=IfWizardAction.getCurrentPositions();
    

    this.dispName=WizardsDefinitions.IF+(ifIndex+1);

     this.created=true;
    }
    LockObjects nlo=new LockObjects(this);
    nlo.lock();
    iol.add2List(this);
   }

    }


 public String showBlocks()
    {
    String s="";
    s=this.dispStatementsCategories.get(0)+": ("+this.dispStatements.get(0)+")";
    for (int i=1;i<this.dispStatements.size();i++)
        if (this.dispStatementsCategories.get(i).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
          s=s+"\n"+this.dispStatementsCategories.get(i)+": ("+this.dispStatements.get(i)+")";
        else
          s=s+"\n"+this.dispStatementsCategories.get(i);



    return s;
    }

 public String getObjName()
 {return this.objName;}

  public String getDispName()
  {return this.dispName;}

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

    public LinkedList<String> getDispStatements()
    {return this.dispStatements;}

     public LinkedList<String> getObjStatements()
    {return this.objStatements;}

    public LinkedList<String> getDispStatementsCategories()
    {return this.dispStatementsCategories;}

      public LinkedList<Integer> getOldPositions()
    {return this.oldPosition;}

    public LinkedList<Integer> getCurrentPositions()
    {return this.currentPosition;}
   
   
///Setters
public void setDispScope(String indScope)
{this.dispScope=indScope;}

public void setObjScope(String inoScope)
{this.objScope=inoScope;}

 public void setLocked(LinkedList<String> ilocked)
 {this.locked=ilocked;}


     public void setDispStatements(LinkedList<String> iDispStatements)
    {this.dispStatements=iDispStatements;}

     public void setObjStatements(LinkedList<String> iObjStatements)
    {this.objStatements=iObjStatements;}

    public void setDispStatementsCategories(LinkedList<String> iDispStatementsCategories)
    {this.dispStatementsCategories=iDispStatementsCategories;}


  public void setOldPositions(LinkedList<Integer> iOldPositions)
    {this.oldPosition=iOldPositions;}

    public void setCurrentPositions(LinkedList<Integer> iCurrentPositions)
    {this.oldPosition=iCurrentPositions;}

  public LinkedList<PrintStructure> getCoeusString1(int pos)
  {
    LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
    readArguements2print ra2p=null;

    if (this.dispStatementsCategories.get(pos).equalsIgnoreCase(WizardsDefinitions.IF_STATEMENT))
    {
    part.add(new PrintStructure(CoeusProgram.IF1,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
    part.add(new PrintStructure("(",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
    ra2p =new readArguements2print(this.dispStatements.get(pos),true);
    part.addAll(ra2p.getPrintStructure());
    part.add(new PrintStructure(")\n",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
    }
    else if (this.dispStatementsCategories.get(pos).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
    {
    part.add(new PrintStructure(CoeusProgram.ELSEIF,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
    part.add(new PrintStructure("(",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
    ra2p =new readArguements2print(this.dispStatements.get(pos),true);
    part.addAll(ra2p.getPrintStructure());
    part.add(new PrintStructure(")\n",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
    }
    else
    part.add(new PrintStructure(CoeusProgram.ELSE+"\n",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
 

 return part;
  }

  public  LinkedList<PrintStructure> getCoeusString2()
  {
  LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
  part.add(new PrintStructure(CoeusProgram.ENDIF,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
  return part;
  }


    public String getJavaString1(int pos)
    {
    String s="";
    if (this.dispStatementsCategories.get(pos).equalsIgnoreCase(WizardsDefinitions.IF_STATEMENT))
        s=s+"if ("+this.objStatements.get(pos)+") {\n";
    else if (this.dispStatementsCategories.get(pos).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
        s=s+"else if ("+this.objStatements.get(pos)+") {\n";
    else
        s=s+"else {\n";

    return s;
    }


    public String getJavaString2()
    {return "}\n";}


    public String getDescription(int pos)
 {
 String des;
 int size=this.dispStatements.size();

     if (this.dispStatementsCategories.get(pos).equalsIgnoreCase(WizardsDefinitions.IF_STATEMENT))
    {
    des="This command is checking whether the condition "+this.dispStatements.get(pos)
            +" is valid.\n\nIf it is, the group of commands between IF ("+
            this.dispStatements.get(pos)+") and ";
    
    if (pos+1<size)
    {
     if (this.dispStatementsCategories.get(pos+1).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
          des=des+CoeusProgram.ELSEIF+"("+this.dispStatements.get(pos+1)
                    +")is executed, and the execution of the program continues with the command following END OF IF. " +
                    "\n\nIF the condition "+this.dispStatements.get(pos)
                    +" is FALSE, then the condition in the following ELSE IF is checked, namely " +
                     CoeusProgram.ELSEIF+" ("+this.dispStatements.get(pos+1)+")";
     else
           des=des+"ELSE is executed, and the execution of the program continues with the command following END OF IF." +
               "\n\nIf the condition "+this.dispStatements.get(pos)
                +" is FALSE, then the group of commands between ELSE and END OF IF is executed. " +
            "The execution of the program continues with the command following END OF IF.";
    }
    else
        des=des+"END OF IF is executed, and the execution of the program continues with the command following END OF IF " +
               "\n\nIf the condition "+this.dispStatements.get(pos)
                +" is FALSE, then the execution of the program continues with the command following END OF IF.";
    }
    else
    {
    des="This command is checking whether the condition "+this.dispStatements.get(pos)
            +" is valid, since NONE of the previous conditions was valid." +
            "\n\nIf the condition is TRUE, the group of commands between ELSE IF ("+
            this.dispStatements.get(pos)+") and ";

    if (pos+1<size)
    {
     if (this.dispStatementsCategories.get(pos+1).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))   
          des=des+CoeusProgram.ELSEIF+"("+this.dispStatements.get(pos+1)
                    +")is executed, and the execution of the program continues with the command following END OF IF." +
                    "\n\nIf the condition  "+this.dispStatements.get(pos)
                    +" is FALSE, then the condition in the following ELSE IF is checked, namely " +
                     CoeusProgram.ELSEIF+" ("+this.dispStatements.get(pos+1)+")";
     else
           des=des+"ELSE is executed, and the execution of the program continues with the command following END OF IF. " +
               "\n\nIf the condition "+this.dispStatements.get(pos)
                +" is FALSE, then since NONE of the previous conditions was valid," +
            "the group of commands between ELSE and END OF IF is executed. " +
            "The execution of the program continues with the commands following END OF IF.";
    }
    else
        des=des+"END OF IF is executed. The execution of the program continues with the commands following END OF IF. " +
               "\n\nIf the condition "+this.dispStatements.get(pos)
                +" is FALSE, then the execution of the program continues with the commands following END OF IF.";

    }

 return des;
 }


}
