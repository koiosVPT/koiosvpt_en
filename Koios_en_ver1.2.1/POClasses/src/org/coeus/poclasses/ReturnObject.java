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
import org.coeus.wizards.Return.ReturnWizardAction;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class ReturnObject {

    private String dispName=null;
    private String objName=null;
    private String dispCategorie=null;
    private String dispScope=null;
    private String objScope=null;


    private  String dispFunctionName=null;
    private  String objFunctionName=null;
    private  String dispFunctionType=null;
    private  String objFunctionType=null;
    private  String dispReturnValue=null;
    private  String objReturnValue=null;
    private  String dispReturnCategory=null;
    private  String objReturnCategory=null;

    private boolean created=false;


    private static int returnCount = 0;
    private final int returnIndex;
    LinkedList<String> locked=new LinkedList();

     private ActionEvent e;


    public ReturnObject(String dScope,String oScope)
    {
    returnIndex=returnCount++;
    ReturnWizardAction ReturnAction = new ReturnWizardAction(false,null);
    this.dispScope=dScope;
    this.objScope=oScope;
    ReturnAction.setDispScope(this.dispScope);
    ReturnAction.setObjScope(this.objScope);
    ReturnAction.actionPerformed(e);
    created=ReturnAction.getCreated();
    if(created==true)
    {
    this.dispFunctionName=ReturnWizardAction.getDispFunctionName();
    this.objFunctionName=ReturnWizardAction.getObjFunctionName();
    this.dispFunctionType=ReturnWizardAction.getDispFunctionType();
    this.objFunctionType=ReturnWizardAction.getObjFunctionType();
    this.dispReturnValue=ReturnWizardAction.getDispReturnValue();
    this.objReturnValue=ReturnWizardAction.getObjReturnValue();
    this.dispReturnCategory=ReturnWizardAction.getDispCategory();
    this.objReturnCategory=ReturnWizardAction.getObjCategory();

    this.dispCategorie=WizardsDefinitions.COM_RETURN;
    this.dispName=WizardsDefinitions.RETURN+this.dispReturnValue;
    this.objName="return"+returnIndex;

    this.created=true;
    LockObjects lo=new LockObjects(this);
    lo.lock();
    ReturnObjectList col= ReturnObjectList.getReturnObjList();
    col.add2List(this);
    }
    }

    public ReturnObject( String idispFunctionName,String iobjFunctionName,String idispFunctionType,
  String iobjFunctionType,String idispReturnValue,String iobjReturnValue,String idispCategory,
            String iobjCategory,String dScope,String oScope,LinkedList<String> iLocked)
    {
    returnIndex=returnCount++;
    this.dispFunctionName=idispFunctionName;
    this.objFunctionName=iobjFunctionName;
    this.dispFunctionType=idispFunctionType;
    this.objFunctionType=iobjFunctionType;
    this.dispReturnValue=idispReturnValue;
    this.objReturnValue=iobjReturnValue;
    this.dispReturnCategory=idispCategory;
    this.objReturnCategory=iobjCategory;

    this.objName="return"+returnIndex;
    this.dispCategorie=WizardsDefinitions.COM_RETURN;
    this.dispName=WizardsDefinitions.RETURN+this.dispReturnValue;
    this.locked=iLocked;

    this.dispScope=dScope;
    this.objScope=oScope;
    this.created=true;
    ReturnObjectList col= ReturnObjectList.getReturnObjList();
    col.add2List(this);
 }


  public ReturnObject(ReturnObject ro)
    {
    this.returnIndex=ro.returnIndex;
    this.dispFunctionName=ro.dispFunctionName;
    this.objFunctionName=ro.objFunctionName;
    this.dispFunctionType=ro.dispFunctionType;
    this.objFunctionType=ro.objFunctionType;
    this.dispReturnValue=ro.dispReturnValue;
    this.objReturnValue=ro.objReturnValue;
    this.dispReturnCategory=ro.dispReturnCategory;
    this.objReturnCategory=ro.objReturnCategory;

    this.objName=ro.objName;
    this.dispCategorie=WizardsDefinitions.COM_RETURN;
    this.dispName=ro.dispName;
    this.locked.addAll(ro.locked);

    this.dispScope=ro.dispScope;
    this.objScope=ro.objScope;
    this.created=ro.created;
    //ReturnObjectList col= ReturnObjectList.getReturnObjList();
   // col.add2List(this);
 }

public void UpdateReturnObject()
 {
 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new ReturnObjectProperties(this) ,"Confirmation Changing "+this.getDispName(),
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
   {
    LockObjects lo=new LockObjects(this);
    lo.unlock();
    ReturnObjectList col= ReturnObjectList.getReturnObjList();
    col.removeFromList(this);
    String [] attributes =null;//new String [3];
    ReturnWizardAction ReturnAction = new ReturnWizardAction(true,attributes);
    ReturnAction.setDispScope(this.dispScope);
    ReturnAction.setObjScope(this.objScope);
    ReturnWizardAction.setDispFunctionName( this.dispFunctionName);
    ReturnWizardAction.setObjFunctionName(this.objFunctionName);
    ReturnWizardAction.setDispFunctionType(this.dispFunctionType);
    ReturnWizardAction.setObjFunctionType(this.objFunctionType);
    ReturnWizardAction.setDispReturnValue(this.dispReturnValue);
    ReturnWizardAction.setObjReturnValue(this.objReturnValue);
    ReturnWizardAction.setDispCategory(this.dispReturnCategory);
    ReturnWizardAction.setObjCategory(this.objReturnCategory);

    ReturnAction.actionPerformed(e);
    created=ReturnAction.getCreated();
    if(created==true)
    {
    this.dispFunctionName=ReturnWizardAction.getDispFunctionName();
    this.objFunctionName=ReturnWizardAction.getObjFunctionName();
    this.dispFunctionType=ReturnWizardAction.getDispFunctionType();
    this.objFunctionType=ReturnWizardAction.getObjFunctionType();
    this.dispReturnValue=ReturnWizardAction.getDispReturnValue();
    this.objReturnValue=ReturnWizardAction.getObjReturnValue();
    this.dispReturnCategory=ReturnWizardAction.getDispCategory();
    this.objReturnCategory=ReturnWizardAction.getObjCategory();
    this.dispName=WizardsDefinitions.RETURN+this.dispReturnValue;

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

    public String getDispFunctionName()
    {return this.dispFunctionName;}

     public String getObjFunctionName()
    {return this.objFunctionName;}

    public String getDispFunctionType()
    {return this.dispFunctionType;}

    public String getObjFunctionType()
    {return this.objFunctionType;}

    public String getDispReturnValue()
    {return this.dispReturnValue;}

    public  String getObjReturnValue()
    {return this.objReturnValue;}

    public  String getDispReturnCategory()
    {return this.dispReturnCategory;}

     public  String getObjRetCategory()
    {return this.objReturnCategory;}



 ///Setters
public void setDispScope(String indScope)
{this.dispScope=indScope;}

public void setObjScope(String inoScope)
{this.objScope=inoScope;}

 public void setLocked(LinkedList<String> ilocked)
 {this.locked=ilocked;}


   public  void setDispFunctionName(String iPN)
    {this.dispFunctionName=iPN;}

    public void setObjFunctionName(String iPN)
    {this.objFunctionName=iPN;}

    public void setDispFunctionType(String iPT)
    {this.dispFunctionType=iPT;}

    public void setObjFunctionType(String iPT)
    {this.objFunctionType=iPT;}

    public void setDispReturnValue(String iRV)
    {this.dispReturnValue=iRV;}

    public void setObjReturnValue(String iRV)
    {this.objReturnValue=iRV;}


     public void setDispReturnCategory(String iDC)
    {this.dispReturnCategory=iDC;}

    public void setObjReturnCategory(String iOC)
    {this.objReturnCategory=iOC;}



 /////////Printers//////////
 public LinkedList<PrintStructure> getCoeusString1()
     {
    LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
    readArguements2print ra2p=null;

       part.add(new PrintStructure(CoeusProgram.RETURN+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

       
         if (this.dispReturnCategory.equalsIgnoreCase(WizardsDefinitions.CONDITION))
         {
             ra2p =new readArguements2print(this.dispReturnValue,true);
             part.addAll(ra2p.getPrintStructure());
         }
       else if ((this.dispReturnCategory.equalsIgnoreCase(WizardsDefinitions.CHARACTER) &&
                   this.dispReturnValue.startsWith("'"))
                   ||
                   (this.dispReturnCategory.equalsIgnoreCase(WizardsDefinitions.STRING1)
                  && this.dispReturnValue.startsWith("\"")))
         part.add(new PrintStructure(this.dispReturnValue,PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
         else
         {
           ra2p =new readArguements2print(this.dispReturnValue,false);
         part.addAll(ra2p.getPrintStructure());
         }

      
       part.add(new PrintStructure(" ;\n ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

       return part;
     }



    public String getJavaString1()
     {
      return "\n return ("+this.objReturnValue+"); \n ";
     }

public String getDescription()
 {
 String des="This RETURN command will return the "+this.dispFunctionType+" value of "+this.dispReturnValue+" to the command" +
         " that called FUNCTION "+this.dispFunctionName+".";

 return des;
 }

}
