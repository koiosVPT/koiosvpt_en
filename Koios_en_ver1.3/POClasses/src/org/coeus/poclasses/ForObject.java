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
import org.coeus.wizards.For.ForWizardAction;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class ForObject {

    private String dispName=null;
    private String objName=null;
    private String dispCategorie=null;
    private String dispScope=null;
    private String objScope=null;


    private  String dispForName=null;
    private  String objForName=null;
    private  String dispForType=null;
    private  String objForType=null;
    private  String dispForCategory=null;
    private  LinkedList <String> dispStatement=new LinkedList();
    private  LinkedList <String> objStatement=new LinkedList();
    private  LinkedList <String> dispStatementCategory=new LinkedList();

    private boolean created=false;


    private static int forCount = 0;
    private final int forIndex;
   LinkedList<String> locked=new LinkedList();

     private ActionEvent e;


    public ForObject(String dScope,String oScope)
    {
    forIndex=forCount++;
    ForWizardAction ForAction = new ForWizardAction(false,null);
    this.dispScope=dScope;
    this.objScope=oScope;
    ForAction.setDispScope(this.dispScope);
    ForAction.setObjScope(this.objScope);
    ForAction.actionPerformed(e);
    created=ForAction.getCreated();
    if(created==true)
    {
    this.dispForName=ForWizardAction.getDispName();
    this.objForName=ForWizardAction.getObjName();
    this.dispForType=ForWizardAction.getDispType();
    this.objForType=ForWizardAction.getObjType();
    this.dispForCategory=ForWizardAction.getDispCategory();
    this.dispStatement=ForWizardAction.getDispStatement();
    this.objStatement=ForWizardAction.getObjStatement();
    this.dispStatementCategory=ForWizardAction.getDispStatementCategory();

    this.dispCategorie=WizardsDefinitions.COM_FOR;
    this.dispName=WizardsDefinitions.FOR1+this.dispStatement.get(0)+WizardsDefinitions.FOR2+this.dispStatement.get(1);
    this.objName="for"+forIndex;

    this.created=true;
    LockObjects lo=new LockObjects(this);
    lo.lock();
    ForObjectList col= ForObjectList.getForObjList();
    col.add2List(this);
    }
    }

    public ForObject(String idispForName,String iobjForName,String idispForType,String iobjForType,
            String idispForCategory, LinkedList<String>idispStatement, LinkedList<String>iobjStatement,
            LinkedList<String> idispStatementCategory,
            String idispForValue,String iobjForValue,String dScope,String oScope,LinkedList<String> iLocked)
    {
    forIndex=forCount++;
    this.dispForName=idispForName;
    this.objForName=iobjForName;
    this.dispForType=idispForType;
    this.objForType=iobjForType;
    this.dispForCategory=idispForCategory;
    this.dispStatement=idispStatement;
    this.objStatement=iobjStatement;
    this.dispStatementCategory=idispStatementCategory;

    this.dispScope=dScope;
    this.objScope=oScope;
    this.locked=iLocked;
    this.objName="for"+forIndex;
    this.dispCategorie=WizardsDefinitions.COM_FOR;
    this.dispName=WizardsDefinitions.FOR1+this.dispStatement.get(0)+WizardsDefinitions.FOR2+this.dispStatement.get(1);

    this.created=true;
    ForObjectList col= ForObjectList.getForObjList();
    col.add2List(this);
 }



 public ForObject(ForObject fo)
    {
    this.forIndex=fo.forIndex;
    this.dispForName=fo.dispForName;
    this.objForName=fo.objForName;
    this.dispForType=fo.dispForType;
    this.objForType=fo.objForType;
    this.dispForCategory=fo.dispForCategory;
    this.dispStatement.addAll(fo.dispStatement);
    this.objStatement.addAll(fo.objStatement);
    this.dispStatementCategory.addAll(fo.dispStatementCategory);

    this.dispScope=fo.dispScope;
    this.objScope=fo.objScope;
    this.locked.addAll(fo.locked);
    this.objName=fo.objName;
    this.dispCategorie=WizardsDefinitions.COM_FOR;
    this.dispName=fo.dispName;
    this.created=fo.created;
    //ForObjectList col= ForObjectList.getForObjList();
    //col.add2List(this);
 }


public void UpdateForObject()
 {
 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new ForObjectProperties(this) ,"Confirmation Changing "+this.getDispName(),
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
   {
    LockObjects lo=new LockObjects(this);
    lo.unlock();
    ForObjectList col= ForObjectList.getForObjList();
    col.removeFromList(this);
    String [] attributes =null;//new String [3];
    ForWizardAction ForAction = new ForWizardAction(true,attributes);
    ForAction.setDispScope(this.dispScope);
    ForAction.setObjScope(this.objScope);
    ForWizardAction.setDispName( this.dispForName);
    ForWizardAction.setObjName(this.objForName);
    ForWizardAction.setDispType(this.dispForType);
    ForWizardAction.setObjType(  this.objForType);
    ForWizardAction.setDispCategory( this.dispForCategory);
    ForWizardAction.setDispStatement(this.dispStatement);
    ForWizardAction.setObjStatement(this.objStatement);
    ForWizardAction.setDispStatementCategory(this.dispStatementCategory);
    ForAction.actionPerformed(e);
    created=ForAction.getCreated();
    if(created==true)
    {
     this.dispForName=ForWizardAction.getDispName();
    this.objForName=ForWizardAction.getObjName();
    this.dispForType=ForWizardAction.getDispType();
    this.objForType=ForWizardAction.getObjType();
    this.dispForCategory=ForWizardAction.getDispCategory();
    this.dispStatement=ForWizardAction.getDispStatement();
    this.objStatement=ForWizardAction.getObjStatement();
    this.dispStatementCategory=ForWizardAction.getDispStatementCategory();
    this.dispName=WizardsDefinitions.FOR1+this.dispStatement.get(0)+WizardsDefinitions.FOR2+this.dispStatement.get(1);

     this.created=true;
    }
    LockObjects nlo=new LockObjects(this);
    nlo.lock();
    col.add2List(this);
   }

    }

public String getForVariableDispName() {
    return dispForName;
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

  public LinkedList<String> getDispStatement()
  {return this.dispStatement;}

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
     int indexOfEq;
     LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
     readArguements2print ra2p=null;

       part.add(new PrintStructure(CoeusProgram.FOR1,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

       indexOfEq =this.dispStatement.get(0).indexOf("=");
       ra2p =new readArguements2print(this.dispStatement.get(0).substring(0, indexOfEq),false);
       part.addAll(ra2p.getPrintStructure());
       part.add(new PrintStructure("=",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
       ra2p =new readArguements2print(this.dispStatement.get(0).substring(indexOfEq+1),false);
       part.addAll(ra2p.getPrintStructure());

       part.add(new PrintStructure(CoeusProgram.FOR3,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       ra2p =new readArguements2print(this.dispStatement.get(1),true);
       part.addAll(ra2p.getPrintStructure());

       part.add(new PrintStructure(CoeusProgram.FOR4,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

        indexOfEq =this.dispStatement.get(2).indexOf("=");
       ra2p =new readArguements2print(this.dispStatement.get(2).substring(0, indexOfEq),false);
       part.addAll(ra2p.getPrintStructure());
       part.add(new PrintStructure("=",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
       ra2p =new readArguements2print(this.dispStatement.get(2).substring(indexOfEq+1),false);
       part.addAll(ra2p.getPrintStructure());


       part.add(new PrintStructure("\n ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

       return part;
     }



    public String getJavaString1()
     {
      return "\n for ("+this.objStatement.get(0)+";"+
              this.objStatement.get(1)+";"+
              this.objStatement.get(2)+") \n{\n ";
     }


    public LinkedList<PrintStructure> getCoeusString2()
    {
     LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
     part.add(new PrintStructure(CoeusProgram.FOR5,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

        return part;}

    public String getJavaString2()
    {return "}\n";}


 public String getDescription()
 {
 String for1=ps2String(this.getCoeusString1());
 String des="This iteration statement is executing repeatedly the group of commands that is between\n"+for1+"\nand\n\nthe highlighted END OF FOR.\n\n" +
         "In every loop the value of INITIAL EXPRESSION varies by the value of STEP and this new value is used in the next loop.\n" +
         "The group of commands will be executed repeatedly until the FINAL CONDITION is no longer valid."
         +"\n\n INITIAL EXPRESSION: "+this.getDispStatement().get(0)
         +"\n\n FINAL CONDITION   : "+this.getDispStatement().get(1)
         +"\n\n STEP              : "+this.getDispStatement().get(2);

 return des;
 }


  private String ps2String (LinkedList<PrintStructure> psarr)
    {
     String temp;
     temp="";
     for (PrintStructure ps:psarr)
         temp=temp+ps.getText();
     return temp;
    }
}
