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
import org.coeus.wizards.While.WhileWizardAction;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class DoWhileObject {

    private String dispName=null;
    private String objName=null;
    private String dispCategorie=null;
    private String dispScope=null;
    private String objScope=null;


    private  String dispDoWhileValue=null;
    private  String objDoWhileValue=null;


    private boolean created=false;


    private static int dowhileCount = 0;
    private final int dowhileIndex;
   LinkedList<String> locked=new LinkedList();

     private ActionEvent e;


    public DoWhileObject(String dScope,String oScope)
    {
    dowhileIndex=dowhileCount++;
    WhileWizardAction WhileAction = new WhileWizardAction(false,null,true);
    this.dispScope=dScope;
    this.objScope=oScope;
    WhileAction.setDispScope(this.dispScope);
    WhileAction.setObjScope(this.objScope);
    WhileAction.actionPerformed(e);
    created=WhileAction.getCreated();
    if(created==true)
    {
    this.dispDoWhileValue=WhileWizardAction.getDispWhileValue();
    this.objDoWhileValue=WhileWizardAction.getObjWhileValue();

    this.dispCategorie=WizardsDefinitions.COM_DOWHILE;
    this.dispName=WizardsDefinitions.DOWHILE1+WizardsDefinitions.DOWHILE2+this.dispDoWhileValue+")";
    this.objName="dowhile"+dowhileIndex;

    this.created=true;
    LockObjects lo=new LockObjects(this);
    lo.lock();
    DoWhileObjectList dwol= DoWhileObjectList.getDoWhileObjList();
    dwol.add2List(this);
    }
    }

    public DoWhileObject( String idispWhileValue,String iobjWhileValue,String dScope,String oScope,LinkedList<String> iLocked)
    {
    dowhileIndex=dowhileCount++;
    this.dispDoWhileValue=idispWhileValue;
    this.objDoWhileValue=iobjWhileValue;

    this.dispScope=dScope;
    this.objScope=oScope;
    this.locked=iLocked;
    this.objName="dowhile"+dowhileIndex;
    this.dispCategorie=WizardsDefinitions.COM_DOWHILE;
    this.dispName=WizardsDefinitions.DOWHILE1+WizardsDefinitions.DOWHILE2+this.dispDoWhileValue+")";


    this.created=true;
    DoWhileObjectList dwol= DoWhileObjectList.getDoWhileObjList();
    dwol.add2List(this);
   }

 public DoWhileObject(DoWhileObject dwh )
    {
    this.dowhileIndex=dwh.dowhileIndex;
    this.dispDoWhileValue=dwh.dispDoWhileValue;
    this.objDoWhileValue=dwh.objDoWhileValue;
    this.dispScope=dwh.dispScope;
    this.objScope=dwh.objScope;
    this.locked.addAll(dwh.locked);
    this.objName=dwh.objName;
    this.dispCategorie=WizardsDefinitions.COM_DOWHILE;
    this.dispName=dwh.dispName;
    this.created=dwh.created;
    //DoWhileObjectList dwol= DoWhileObjectList.getDoWhileObjList();
    //dwol.add2List(this);
 }

public void UpdateDoWhileObject()
 {
 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new DoWhileObjectProperties(this) ,"Confirmation Changing "+this.getDispName(),
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
   {
    LockObjects lo=new LockObjects(this);
    lo.unlock();
    DoWhileObjectList dwol= DoWhileObjectList.getDoWhileObjList();
    dwol.removeFromList(this);
    String [] attributes =null;//new String [3];
    WhileWizardAction WhileAction = new WhileWizardAction(true,attributes,true);
    WhileAction.setDispScope(this.dispScope);
    WhileAction.setObjScope(this.objScope);
    WhileWizardAction.setDispWhileValue(this.dispDoWhileValue);
    WhileWizardAction.setObjWhileValue(this.objDoWhileValue);

    WhileAction.actionPerformed(e);
    created=WhileAction.getCreated();
    if(created==true)
    {
    this.dispDoWhileValue=WhileWizardAction.getDispWhileValue();
    this.objDoWhileValue=WhileWizardAction.getObjWhileValue();
    this.dispName=WizardsDefinitions.DOWHILE1+WizardsDefinitions.DOWHILE2+this.dispDoWhileValue+")";


     this.created=true;
    }
    LockObjects nlo=new LockObjects(this);
    nlo.lock();
    dwol.add2List(this);
   }

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


    public String getDispDoWhileValue()
    {return this.dispDoWhileValue;}

    public  String getObjDoWhileValue()
    {return this.objDoWhileValue;}




 ///Setters
public void setDispScope(String indScope)
{this.dispScope=indScope;}

public void setObjScope(String inoScope)
{this.objScope=inoScope;}

 public void setLocked(LinkedList<String> ilocked)
 {this.locked=ilocked;}


    public void setDispDoWhileValue(String iRV)
    {this.dispDoWhileValue=iRV;}

    public void setObjDoWhileValue(String iRV)
    {this.objDoWhileValue=iRV;}



 /////////Printers//////////
 public LinkedList<PrintStructure> getCoeusString1()
     {
    LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();


       part.add(new PrintStructure(CoeusProgram.DOWHILE1,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       return part;
     }



    public String getJavaString1()
     {
      return "do {\n ";
     }


    public LinkedList<PrintStructure> getCoeusString2()
    {
    LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
    readArguements2print ra2p=null;

    part.add(new PrintStructure(CoeusProgram.DOWHILE2,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

             ra2p =new readArguements2print(this.dispDoWhileValue,true);
             part.addAll(ra2p.getPrintStructure());

    part.add(new PrintStructure(" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

    part.add(new PrintStructure(CoeusProgram.DOWHILE3,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

    return part;
    }

    public String getJavaString2()
    {return "} while ("+this.objDoWhileValue+");\n ";}


 public String getDescription()
 {
  String des="The condition "+this.dispDoWhileValue+" is evaluated.\n\nIf it is valid, the command(s) in this " +
         "DO..WHILE iteration statement is\\are executed again.\n\n"+
         "If the condition is not valid the execution of the program continues with the following command.";

 return des;
 }
 


}
