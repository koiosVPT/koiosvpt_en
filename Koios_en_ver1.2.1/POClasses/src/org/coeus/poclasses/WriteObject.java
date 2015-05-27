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

import org.coeus.wizards.Write.WriteWizardAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class WriteObject {

    private String dispName=null;
    private String objName=null;
    private String dispCategorie=null;
    private String dispScope=null;
    private String objScope=null;

  //  private LinkedList <String> messages=null;
    private LinkedList <String> dispArguements=new LinkedList();
    private LinkedList <String> objArguements=new LinkedList();
    private LinkedList <String> dispArguementsType=new LinkedList();
    private LinkedList <String> objArguementsType=new LinkedList();
    private LinkedList <String> dispArgCategory=new LinkedList();
    private boolean created=false;


    private static int writeCount = 0;
    private final int writeIndex;
    LinkedList<String> locked=new LinkedList();

     private ActionEvent e;


    public WriteObject(String dScope,String oScope)
    {
    writeIndex=writeCount++;
    WriteWizardAction WriteAction = new WriteWizardAction(false,null);
    this.dispScope=dScope;
    this.objScope=oScope;
    WriteAction.setDispScope(this.dispScope);
    WriteAction.setObjScope(this.objScope);
    WriteAction.actionPerformed(e);
    created=WriteAction.getCreated();
    if(created==true)
    {
//    this.messages=WriteWizardAction.getMessages();
    this.dispArguements=WriteWizardAction.getDispArguements();
    this.objArguements=WriteWizardAction.getObjArguements();
    this.dispArguementsType=WriteWizardAction.getDispArguementsType();
    this.objArguementsType=WriteWizardAction.getObjArguementsType();
    this.dispArgCategory=WriteWizardAction.getDispCategory();
    this.dispCategorie=WizardsDefinitions.COM_WRITE;
    this.dispName=WizardsDefinitions.WRITE+this.getDispArgsTitle();
    this.objName="write"+writeIndex;

    this.created=true;
    LockObjects lo=new LockObjects(this);
    lo.lock();
    WriteObjectList wol= WriteObjectList.getWriteObjList();
    wol.add2List(this);
    }
    }

    public WriteObject(LinkedList<String> iDispArguements, LinkedList<String> iObjArguements,
            LinkedList<String> iDispArguementsType ,LinkedList<String> iObjArguementsType ,
            LinkedList<String> iDispArguentsCategory ,String dScope,String oScope,LinkedList<String> iLocked)
    {
    writeIndex=writeCount++;
//    this.messages=iMessages;
    this.dispArguements=iDispArguements;
    this.objArguements=iObjArguements;
    this.dispArguementsType=iDispArguementsType;
    this.objArguementsType=iObjArguementsType;
    this.dispArgCategory=iDispArguentsCategory;
    this.objName="write"+writeIndex;
    this.dispCategorie=WizardsDefinitions.COM_WRITE;
    this.dispName=WizardsDefinitions.WRITE+this.getDispArgsTitle();

     this.dispScope=dScope;
    this.objScope=oScope;
    this.locked=iLocked;
    this.created=true;
    WriteObjectList wol= WriteObjectList.getWriteObjList();
    wol.add2List(this);
 }


  public WriteObject(WriteObject wo)
    {
    this.writeIndex=wo.writeIndex;
    this.dispArguements.addAll(wo.dispArguements);
    this.objArguements.addAll(wo.objArguements);
    this.dispArguementsType.addAll(wo.dispArguementsType);
    this.objArguementsType.addAll(wo.objArguementsType);
    this.dispArgCategory.addAll(wo.dispArgCategory);
    this.objName=wo.objName;
    this.dispCategorie=WizardsDefinitions.COM_WRITE;
    this.dispName=wo.dispName;

    this.dispScope=wo.dispScope;
    this.objScope=wo.objScope;
    this.locked.addAll(wo.locked);
    this.created=wo.created;
 //   WriteObjectList wol= WriteObjectList.getWriteObjList();
 //   wol.add2List(this);
 }


public void UpdateWriteObject()
 {
 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new WriteObjectProperties(this) ,"Confirmation Changing "+this.getDispCateg(),
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
   {
    LockObjects lo=new LockObjects(this);
    lo.unlock();
    WriteObjectList wol= WriteObjectList.getWriteObjList();
    wol.removeFromList(this);
    String [] attributes =null;//new String [3];
    WriteWizardAction WriteAction = new WriteWizardAction(true,attributes);
    WriteAction.setDispScope(this.dispScope);
    WriteAction.setObjScope(this.objScope);
//    WriteWizardAction.setMesages(this.messages);
    WriteWizardAction.setDispArguements(this.dispArguements);
    WriteWizardAction.setObjArguements(this.objArguements);
    WriteWizardAction.setDispArguementsType(this.dispArguementsType);
    WriteWizardAction.setObjArguementsType(this.objArguementsType);
    WriteWizardAction.setDispCategory(this.dispArgCategory);
  


    WriteAction.actionPerformed(e);
    created=WriteAction.getCreated();
    if(created==true)
    {
//    this.messages=WriteWizardAction.getMessages();
    this.dispArguements=WriteWizardAction.getDispArguements();
    this.objArguements=WriteWizardAction.getObjArguements();
    this.dispArguementsType=WriteWizardAction.getDispArguementsType();
    this.objArguementsType=WriteWizardAction.getObjArguementsType();
    this.dispArgCategory=WriteWizardAction.getDispCategory();
    this.dispName=WizardsDefinitions.WRITE+this.getDispArgsTitle();


     this.created=true;
    }
    LockObjects nlo=new LockObjects(this);
    nlo.lock();
    wol.add2List(this);
   }

    }


 public String  getDispArgs2Show()
 { String show="";

   if (!this.dispArguements.isEmpty())
   {
        show="("+dispArgCategory.get(0)+") "+dispArguements.get(0);
          for (int i=1;i<dispArguements.size();i++)
             show=show+",\n("+dispArgCategory.get(i)+") "+dispArguements.get(i);
   }

     return show;
}


 public String  getDispArgsTitle()
 { String show="";

   if (!this.dispArguements.isEmpty())
   {
//    if (this.dispArguementsType.get(0).equalsIgnoreCase(WizardsDefinitions.MESSAGE))
//      show=WizardsDefinitions.MESSAGE;
//    else
       show=dispArguements.get(0);
    for (int i=1;i<dispArguementsType.size();i++)
//            if (this.dispArguementsType.get(i).equalsIgnoreCase(WizardsDefinitions.MESSAGE))
//              show=show+", "+WizardsDefinitions.MESSAGE;
//            else
              show=show+", "+dispArguements.get(i);
   }

     return show;
}


///Getters
 public int getArguementsNumber()
 {int num=0;
 if (this.dispArguements!=null)
    num=this.dispArguements.size();
  return num;
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

 // public LinkedList<String> getMessages()
//  {return this.messages;}

  public LinkedList<String> getDispArguments()
   { return this.dispArguements;}

   public LinkedList<String> getObjArguements()
   { return this.objArguements;}

     public LinkedList<String> getDispArguementsType()
    {return this.dispArguementsType;}

     public LinkedList<String> getObjArguementsType()
    {return this.objArguementsType;}


 ///Setters
public void setDispScope(String indScope)
{this.dispScope=indScope;}

public void setObjScope(String inoScope)
{this.objScope=inoScope;}

 public void setLocked(LinkedList<String> ilocked)
 {this.locked=ilocked;}

// public void setMessages(LinkedList<String> iMessages)
//  {this.messages=iMessages;}

  public void setDispArguments(LinkedList<String> iDispArguements)
   {this.dispArguements=iDispArguements;}

   public void setObjArguements(LinkedList<String> iObjArguements)
   {this.objArguements=iObjArguements;}

   public void setDispArguementsType(LinkedList<String> args)
    {this.dispArguementsType=args;}

    public void setObjArguementsType(LinkedList<String> args)
    {this.objArguementsType=args;}

 /////////Printers//////////

 public LinkedList<PrintStructure> getCoeusString1()
     {
    LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
    readArguements2print ra2p=null;

       part.add(new PrintStructure(CoeusProgram.WRITE+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

       if (!this.dispArguements.isEmpty())
       {
       if (this.dispArgCategory.get(0).equalsIgnoreCase(WizardsDefinitions.CONDITION))
       {
           ra2p =new readArguements2print(this.dispArguements.get(0),true);
           part.addAll(ra2p.getPrintStructure());
       }
       else if ((this.dispArgCategory.get(0).equalsIgnoreCase(WizardsDefinitions.CHARACTER) &&
                   this.dispArguements.get(0).startsWith("'"))
                   ||
                   (this.dispArgCategory.get(0).equalsIgnoreCase(WizardsDefinitions.MESSAGE)
                  && this.dispArguements.get(0).startsWith("\"")))
           part.add(new PrintStructure(this.dispArguements.get(0),PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
       else
       {
            ra2p =new readArguements2print(this.dispArguements.get(0),false);
            part.addAll(ra2p.getPrintStructure());
       }
   

      for (int i=1;i<this.dispArguements.size();i++)
       {
         part.add(new PrintStructure(", ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
    
       if (this.dispArgCategory.get(i).equalsIgnoreCase(WizardsDefinitions.CONDITION))
           {
               ra2p =new readArguements2print(this.dispArguements.get(i),true);
               part.addAll(ra2p.getPrintStructure());
           }
               else if ((this.dispArgCategory.get(i).equalsIgnoreCase(WizardsDefinitions.CHARACTER) &&
                   this.dispArguements.get(i).startsWith("'"))
                   ||
                   (this.dispArgCategory.get(i).equalsIgnoreCase(WizardsDefinitions.MESSAGE)
                  && this.dispArguements.get(i).startsWith("\"")))
               part.add(new PrintStructure(this.dispArguements.get(i),PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
           else
           {
                ra2p =new readArguements2print(this.dispArguements.get(i),false);    
                part.addAll(ra2p.getPrintStructure());
           }
         }
      }
       part.add(new PrintStructure(";\n ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

       return part;
     }



    public String getJavaString1()
     {String s="";

       
      s="\nSystem.out.println(";

      if (!this.objArguements.isEmpty())
      {
        s=s+this.objArguements.get(0); 
        for (int i=1;i<this.objArguements.size();i++)
            s=s+" + \" \"+"+this.objArguements.get(i);
      }

      s=s+" );\n";

     return s;
     }


 public String getDescription()
 {
 String des="This command is showing on the Output Window ";
 if (!this.dispArguements.isEmpty())
 {
  des=des+"the following "+this.dispArguements.size()+" item(s):";
  if (this.dispArgCategory.get(0).equalsIgnoreCase(WizardsDefinitions.CONDITION))
  {   des=des+"\n\nthe evaluation of the LOGICAL CONDITION\n"+
             this.dispArguements.get(0)+", which can be TRUE or FALSE.\n"+
             "During the evaluation of this LOGICAL CONDITION, any VARIABLES, CONSTANTS,\nARRAY ELEMENTS and FUNCTIONS" +
             "  in it are replaced by their values.\n";
  }
 else if (this.dispArgCategory.get(0).equalsIgnoreCase(WizardsDefinitions.CHARACTER)
        || this.dispArgCategory.get(0).equalsIgnoreCase(WizardsDefinitions.MESSAGE))
  {
       if (this.dispArguements.get(0).startsWith("'") || this.dispArguements.get(0).startsWith("\""))
       {
           des=des+"\n\n the "+this.dispArgCategory.get(0)+" "+this.dispArguements.get(0)+".";
       }
       else
       {
       des=des+"\n\n the value of "+this.dispArgCategory.get(0)+" "+this.dispArguements.get(0)+".";
       }
   }
   else
   {
  des=des+"\n\nthe evaluation of the ARITHMETIC EXPRSSION "+
             this.dispArguements.get(0)+
      ".\nDuring the evaluation of this ARITHMETIC EXPRSSION, any VARIABLES, CONSTANTS,\nARRAY ELEMENTS and FUNCTIONS" +
      " in it are replaced by their values.\n";
  }
  for (int i=1;i<this.dispArguements.size();i++)
       {
  if (this.dispArgCategory.get(i).equalsIgnoreCase(WizardsDefinitions.CONDITION))
  {   des=des+"\n\nthe evaluation of the LOGICAL CONDITION\n"+
             this.dispArguements.get(i)+", which can be TRUE or FALSE.\n"+
             "During the evaluation of this LOGICAL CONDITION, any VARIABLES, CONSTANTS,\nARRAY ELEMENTS and FUNCTIONS" +
             " in it are replaced by their values.\n";
  }
 else if (this.dispArgCategory.get(i).equalsIgnoreCase(WizardsDefinitions.CHARACTER)
        || this.dispArgCategory.get(i).equalsIgnoreCase(WizardsDefinitions.MESSAGE))
  {
       if (this.dispArguements.get(i).startsWith("'") || this.dispArguements.get(i).startsWith("\""))
       {
           des=des+"\n\n the "+this.dispArgCategory.get(i)+" "+this.dispArguements.get(i)+".";
       }
       else
       {
       des=des+"\n\n the value of "+this.dispArgCategory.get(i)+" "+this.dispArguements.get(i)+".";
       }
   }
   else
   {
  des=des+"\n\nthe evaluation of the ARITHMETIC EXPRSSION "+
             this.dispArguements.get(i)+
      ".\nDuring the evaluation of this ARITHMETIC EXPRSSION, any VARIABLES, CONSTANTS,\nARRAY ELEMENTS and FUNCTIONS" +
      " in it its VARIABLES are replaced by their values.\n";
  }
  }
 }
 else
     des=des+"a blank line.";


 return des;
 }



}
