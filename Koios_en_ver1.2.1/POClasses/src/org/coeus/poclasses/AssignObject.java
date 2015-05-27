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
import org.coeus.wizards.Assign.AssignWizardAction;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class AssignObject {

    private String dispName=null;
    private String objName=null;
    private String dispCategorie=null;
    private String dispScope=null;
    private String objScope=null;


    private  String dispAssignName=null;
    private  String objAssignName=null;
    private  String dispAssignType=null;
    private  String objAssignType=null;
    private  String dispAssignValue=null;
    private  String objAssignValue=null;
    private  String dispCategory=null;
    private  String dispAssignCategory=null;
    private  String objAssignCategory=null;

    private boolean created=false;


    private static int assignCount = 0;
    private final int assignIndex;
    LinkedList<String> locked=new LinkedList();

     private ActionEvent e;


    public AssignObject(String dScope,String oScope)
    {
    assignIndex=assignCount++;
    AssignWizardAction AssignAction = new AssignWizardAction(false,null);
    this.dispScope=dScope;
    this.objScope=oScope;
    AssignAction.setDispScope(this.dispScope);
    AssignAction.setObjScope(this.objScope);
    AssignAction.actionPerformed(e);
    created=AssignAction.getCreated();
    if(created==true)
    {
    this.dispAssignName=AssignWizardAction.getDispAssignName();
    this.objAssignName=AssignWizardAction.getObjAssignName();
    this.dispAssignType=AssignWizardAction.getDispAssignType();
    this.objAssignType=AssignWizardAction.getObjAssignType();
    this.dispCategory=AssignWizardAction.getDispCategory();
    this.dispAssignValue=AssignWizardAction.getDispAssignValue();
    this.objAssignValue=AssignWizardAction.getObjAssignValue();
    this.dispAssignCategory=AssignWizardAction.getDispAssignCategory();
    this.objAssignCategory=AssignWizardAction.getObjAssignCategory();

    this.dispCategorie=WizardsDefinitions.COM_ASSIGN;
    this.dispName=WizardsDefinitions.ASSIGN+this.dispAssignName;
    this.objName="assign"+assignIndex;

    this.created=true;
    LockObjects lo=new LockObjects(this);
    lo.lock();
    AssignObjectList col= AssignObjectList.getAssignObjList();
    col.add2List(this);
    }
   }

    public AssignObject(String idispAssignName,String iobjAssignName,String idispAssignType,String iDispCategory,
  String iobjAssignType,String idispAssignValue,String iobjAssignValue,String idispCategory,
            String iobjCategory,String dScope,String oScope,LinkedList<String> iLocked)
    {
    assignIndex=assignCount++;
    this.dispAssignName=idispAssignName;
    this.objAssignName=iobjAssignName;
    this.dispAssignType=idispAssignType;
    this.dispCategory=iDispCategory;   
    this.objAssignType=iobjAssignType;
    this.dispAssignValue=idispAssignValue;
    this.objAssignValue=iobjAssignValue;
    this.dispAssignCategory=idispCategory;
    this.objAssignCategory=iobjCategory;

    this.dispScope=dScope;
    this.objScope=oScope;
    this.locked=iLocked;
  
    this.dispCategorie=WizardsDefinitions.COM_ASSIGN;
    this.dispName=WizardsDefinitions.ASSIGN+this.dispAssignName;
    this.objName="assign"+assignIndex;

    this.created=true;
    AssignObjectList col= AssignObjectList.getAssignObjList();
    col.add2List(this);
 }


  public AssignObject(AssignObject ao)
    {
    this.assignIndex=ao.assignIndex;
    this.dispAssignName=ao.dispAssignName;
    this.objAssignName=ao.objAssignName;
    this.dispAssignType=ao.dispAssignType;
    this.dispCategory=ao.dispCategory;
    this.objAssignType=ao.objAssignType;
    this.dispAssignValue=ao.dispAssignValue;
    this.objAssignValue=ao.objAssignValue;
    this.dispAssignCategory=ao.dispAssignCategory;
    this.objAssignCategory=ao.objAssignCategory;
    this.dispScope=ao.dispScope;
    this.objScope=ao.objScope;
    this.locked.addAll(ao.locked);
    this.dispCategorie=WizardsDefinitions.COM_ASSIGN;
    this.dispName=ao.dispName;
    this.objName=ao.objName;
    this.created=ao.created;
    //AssignObjectList col= AssignObjectList.getAssignObjList();
    //col.add2List(this);
 }



public void UpdateAssignObject()
 {
 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new AssignObjectProperties(this) ,"Confirmation Changing "+this.dispCategorie,
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
   {
    LockObjects lo=new LockObjects(this);
    lo.unlock();
    AssignObjectList col= AssignObjectList.getAssignObjList();
    col.removeFromList(this);
    String [] attributes =null;//new String [3];
    AssignWizardAction AssignAction = new AssignWizardAction(true,attributes);
    AssignAction.setDispScope(this.dispScope);
    AssignAction.setObjScope(this.objScope);

    AssignWizardAction.setDispAssignName( this.dispAssignName);
    AssignWizardAction.setObjAssignName(this.objAssignName);
    AssignWizardAction.setDispAssignType(this.dispAssignType);
    AssignWizardAction.setDispCategory(this.dispCategory);
    AssignWizardAction.setObjAssignType(this.objAssignType);
    AssignWizardAction.setDispAssignValue(this.dispAssignValue);
    AssignWizardAction.setObjAssignValue(this.objAssignValue);
    AssignWizardAction.setDispAssignCategory(this.dispAssignCategory);
    AssignWizardAction.setObjAssignCategory(this.objAssignCategory);

    AssignAction.actionPerformed(e);
    created=AssignAction.getCreated();
    if(created==true)
    {
    this.dispAssignName=AssignWizardAction.getDispAssignName();
    this.objAssignName=AssignWizardAction.getObjAssignName();
    this.dispAssignType=AssignWizardAction.getDispAssignType();
    this.objAssignType=AssignWizardAction.getObjAssignType();
    this.dispCategory=AssignWizardAction.getDispCategory();
    this.dispAssignValue=AssignWizardAction.getDispAssignValue();
    this.objAssignValue=AssignWizardAction.getObjAssignValue();
    this.dispAssignCategory=AssignWizardAction.getDispAssignCategory();
    this.objAssignCategory=AssignWizardAction.getObjAssignCategory();

    this.dispName=WizardsDefinitions.ASSIGN+this.dispAssignName;


     this.created=true;  
    }
    LockObjects nlo=new LockObjects(this);
    nlo.lock();
    col.add2List(this);
   }

    }


 

///Getters
 

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

   public String getDispAssignName()
    {return this.dispAssignName;}

     public String getObjAssignName()
    {return this.objAssignName;}

    public String getDispAssignType()
    {return this.dispAssignType;}

    public String getObjAssignType()
    {return this.objAssignType;}

    public  String getDispCategory()
    {return this.dispCategory;}

    public String getDispAssignValue()
    {return this.dispAssignValue;}

    public  String getObjAssignValue()
    {return this.objAssignValue;}

    public  String getDispAssignCategory()
    {return this.dispAssignCategory;}

     public  String getObjRetCategory()
    {return this.objAssignCategory;}


 ///Setters
public void setDispScope(String indScope)
{this.dispScope=indScope;}

public void setObjScope(String inoScope)
{this.objScope=inoScope;}

 public void setLocked(LinkedList<String> ilocked)
 {this.locked=ilocked;}


    public  void setDispAssignName(String iPN)
    {this.dispAssignName=iPN;}

    public void setObjAssignName(String iPN)
    {this.objAssignName=iPN;}

    public void setDispAssignType(String iPT)
    {this.dispAssignType=iPT;}

    public void setObjAssignType(String iPT)
    {this.objAssignType=iPT;}

    public void setDispCategory(String iDC)
    {this.dispCategory=iDC;}

    public void setDispAssignValue(String iRV)
    {this.dispAssignValue=iRV;}

    public void setObjAssignValue(String iRV)
    {this.objAssignValue=iRV;}


     public void setDispAssignCategory(String iDC)
    {this.dispAssignCategory=iDC;}

    public void setObjAssignCategory(String iOC)
    {this.objAssignCategory=iOC;}



 /////////Printers//////////
 public LinkedList<PrintStructure> getCoeusString1()
     {
    LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
    readArguements2print ra2p=null;

       part.add(new PrintStructure(CoeusProgram.ASSIGN,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

      // part.add(new PrintStructure(this.dispAssignName,PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
             ra2p =new readArguements2print(this.dispAssignName,true);
             part.addAll(ra2p.getPrintStructure());

       part.add(new PrintStructure(CoeusProgram.ASSIGN1,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

       if (this.dispAssignCategory.equalsIgnoreCase(WizardsDefinitions.CONDITION))
         {
             ra2p =new readArguements2print(this.dispAssignValue,true);
             part.addAll(ra2p.getPrintStructure());
         }
       else if ((this.dispAssignCategory.equalsIgnoreCase(WizardsDefinitions.CHARACTER) &&
                   this.dispAssignValue.startsWith("'"))
                   ||
                   (this.dispAssignCategory.equalsIgnoreCase(WizardsDefinitions.STRING1)
                  && this.dispAssignValue.startsWith("\"")))
          part.add(new PrintStructure(this.dispAssignValue,PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
       else
         {
           ra2p =new readArguements2print(this.dispAssignValue,false);
         part.addAll(ra2p.getPrintStructure());
         }

       //decideOnInput(  part,this.dispAssignValue,this.dispAssignCategory);
       part.add(new PrintStructure(" ;\n ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

       return part;
     }


    private void decideOnInput( LinkedList<PrintStructure> part,String text,String category)
    {
      readArguements2print ra2p=null;   
        
    if (category.equalsIgnoreCase(WizardsDefinitions.CONDITION))
         {
             ra2p =new readArguements2print(text,true);
             part.addAll(ra2p.getPrintStructure());
         }
         else if ((category.equalsIgnoreCase(WizardsDefinitions.CHARACTER) &&
                   category.startsWith("'"))
                   ||
                   (category.equalsIgnoreCase(WizardsDefinitions.STRING1)
                  && category.startsWith("\"")))
         part.add(new PrintStructure(text,PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
         else
         {
           ra2p =new readArguements2print(text,false);
         part.addAll(ra2p.getPrintStructure());
         }

    
    
    
    }


    public String getJavaString1()
     {
      return this.objAssignName+" = "/*+"("+this.objAssignType+") "*/+this.objAssignValue+"; \n";
     }


    public String getDescription()
 {
 String des="This command is assigning to "+this.dispAssignName
         +"("+this.getDispCategory()+" - "+this.getDispAssignType()+") "
         +"\nthe value of "+this.getDispAssignValue()+" ("+this.getDispAssignCategory()+")";

 return des;
 }


}
