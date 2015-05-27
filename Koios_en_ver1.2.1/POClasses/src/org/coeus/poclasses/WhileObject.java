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
public class WhileObject {

    private String dispName=null;
    private String objName=null;
    private String dispCategorie=null;
    private String dispScope=null;
    private String objScope=null;


    private  String dispWhileValue=null;
    private  String objWhileValue=null;
 

    private boolean created=false;


    private static int whileCount = 0;
    private final int whileIndex;
    LinkedList<String> locked=new LinkedList();

    private ActionEvent e;


    public WhileObject(String dScope,String oScope)
    {
    whileIndex=whileCount++;
    WhileWizardAction WhileAction = new WhileWizardAction(false,null,false);
    this.dispScope=dScope;
    this.objScope=oScope;
    WhileAction.setDispScope(this.dispScope);
    WhileAction.setObjScope(this.objScope);
    WhileAction.actionPerformed(e);
    created=WhileAction.getCreated();
    if(created==true)
    {
    this.dispWhileValue=WhileWizardAction.getDispWhileValue();
    this.objWhileValue=WhileWizardAction.getObjWhileValue();
   
    this.dispCategorie=WizardsDefinitions.COM_WHILE;
    this.dispName=WizardsDefinitions.WHILE1+this.dispWhileValue+WizardsDefinitions.WHILE2;
    this.objName="while"+whileIndex;

    this.created=true;
    LockObjects lo=new LockObjects(this);
    lo.lock();
    WhileObjectList col= WhileObjectList.getWhileObjList();
    col.add2List(this);
    }
    }

    public WhileObject( String idispWhileValue,String iobjWhileValue,String dScope,String oScope,LinkedList<String> iLocked)
    {
    whileIndex=whileCount++;
    this.dispWhileValue=idispWhileValue;
    this.objWhileValue=iobjWhileValue;
   
    this.dispScope=dScope;
    this.objScope=oScope;
    this.objName="while"+whileIndex;
    this.dispCategorie=WizardsDefinitions.COM_WHILE;
    this.dispName=WizardsDefinitions.WHILE1+this.dispWhileValue+WizardsDefinitions.WHILE2;
    this.locked=iLocked;

    this.created=true;
    WhileObjectList col= WhileObjectList.getWhileObjList();
    col.add2List(this);
    }


 public WhileObject(WhileObject wo )
    {
    this.whileIndex=wo.whileIndex;
    this.dispWhileValue=wo.dispWhileValue;
    this.objWhileValue=wo.objWhileValue;

    this.dispScope=wo.dispScope;
    this.objScope=wo.objScope;
    this.objName=wo.objName;
    this.dispCategorie=WizardsDefinitions.COM_WHILE;
    this.dispName=wo.dispName;
    this.locked.addAll(wo.locked);

    this.created=wo.created;
    //WhileObjectList col= WhileObjectList.getWhileObjList();
    //col.add2List(this);
    }


public void UpdateWhileObject()
 {
 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new WhileObjectProperties(this) ,"Confirmation Changing "+this.getDispName(),
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
   {
    LockObjects lo=new LockObjects(this);
    lo.unlock();
    WhileObjectList col= WhileObjectList.getWhileObjList();
    col.removeFromList(this);
    String [] attributes =null;//new String [3];
    WhileWizardAction WhileAction = new WhileWizardAction(true,attributes,false);
    WhileAction.setDispScope(this.dispScope);
    WhileAction.setObjScope(this.objScope);
    WhileWizardAction.setDispWhileValue(this.dispWhileValue);
    WhileWizardAction.setObjWhileValue(this.objWhileValue);
   
    WhileAction.actionPerformed(e);
    created=WhileAction.getCreated();
    if(created==true)
    {
    this.dispWhileValue=WhileWizardAction.getDispWhileValue();
    this.objWhileValue=WhileWizardAction.getObjWhileValue();
    this.dispName=WizardsDefinitions.WHILE1+this.dispWhileValue+WizardsDefinitions.WHILE2;

   

     this.created=true;
    }
    LockObjects nlo=new LockObjects(this);
    nlo.lock();
    col.add2List(this);
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

  
    public String getDispWhileValue()
    {return this.dispWhileValue;}

    public  String getObjWhileValue()
    {return this.objWhileValue;}




 ///Setters
public void setDispScope(String indScope)
{this.dispScope=indScope;}

public void setObjScope(String inoScope)
{this.objScope=inoScope;}

 public void setLocked(LinkedList<String> ilocked)
 {this.locked=ilocked;}


    public void setDispWhileValue(String iRV)
    {this.dispWhileValue=iRV;}

    public void setObjWhileValue(String iRV)
    {this.objWhileValue=iRV;}



 /////////Printers//////////
 public LinkedList<PrintStructure> getCoeusString1()
     {
    LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
    readArguements2print ra2p=null;

       part.add(new PrintStructure(CoeusProgram.WHILE1,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       part.add(new PrintStructure(" ( ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));


             ra2p =new readArguements2print(this.dispWhileValue,true);
             part.addAll(ra2p.getPrintStructure());


       part.add(new PrintStructure(" ) ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
       part.add(new PrintStructure(CoeusProgram.WHILE2,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

       return part;
     }



    public String getJavaString1()
     {
      return "\nwhile ("+this.objWhileValue+") \n{\n ";
     }


    public LinkedList<PrintStructure> getCoeusString2()
    {
     LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
     part.add(new PrintStructure(CoeusProgram.WHILE3,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
        
        return part;}




    public String getJavaString2()
    {return "}\n";}


    public String getDescription()
 {
 String des="The condition "+this.dispWhileValue+" is evaluated.\n\nIf it is valid, the command(s) in this " +
         "WHILE..REPEAT iteration statement is\\are executed.\n\n"+
         "If the condition is not valid, the execution of the program continues with the command following the highlighted END OF WHILE.";

 return des;
 }


}
