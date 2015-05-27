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
import org.coeus.wizards.Read.ReadWizardAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class ReadObject {

    private String dispName=null;
    private String objName=null;
    private String dispCategorie=null;
    private String dispScope=null;
    private String objScope=null;

    private LinkedList <String> messages=new LinkedList();
    private LinkedList <String> dispArguements=new LinkedList();
    private LinkedList <String> objArguements=new LinkedList();
    private LinkedList <String> dispArguementsType=new LinkedList();
    private LinkedList <String> objArguementsType=new LinkedList();
    private LinkedList <String> dispArgCategory=new LinkedList();
    private boolean created=false;


    private static int readCount = 0;
    private final int readIndex;
    LinkedList<String> locked=new LinkedList();

     private ActionEvent e;


    public ReadObject(String dScope,String oScope)
    {
    readIndex=readCount++;
    ReadWizardAction ReadAction = new ReadWizardAction(false,null);
    this.dispScope=dScope;
    this.objScope=oScope;
    ReadAction.setDispScope(this.dispScope);
    ReadAction.setObjScope(this.objScope);
    ReadAction.actionPerformed(e);
    created=ReadAction.getCreated();
    if(created==true)
    {
    this.messages=ReadWizardAction.getMessages();
    this.dispArguements=ReadWizardAction.getDispArguements();
    this.objArguements=ReadWizardAction.getObjArguements();
    this.dispArguementsType=ReadWizardAction.getDispArguementsType();
    this.objArguementsType=ReadWizardAction.getObjArguementsType();
    this.dispArgCategory=ReadWizardAction.getDispCategory();
    this.dispCategorie=WizardsDefinitions.COM_READ;
    this.dispName=WizardsDefinitions.READ+this.getDispArgsTitle();
    this.objName="read"+readIndex;

    this.created=true;
    LockObjects lo=new LockObjects(this);
    lo.lock();
    ReadObjectList rol= ReadObjectList.getReadObjList();
    rol.add2List(this);
    }
    }

    public ReadObject(LinkedList<String> iMessages,LinkedList<String> iDispArguements, LinkedList<String> iObjArguements,
            LinkedList<String> iDispArguementsType ,LinkedList<String> iObjArguementsType ,
            LinkedList<String> iDispArguentsCategory ,String dScope,String oScope,LinkedList<String> iLocked)
    {
    readIndex=readCount++;
    this.messages=iMessages;
    this.dispArguements=iDispArguements;
    this.objArguements=iObjArguements;
    this.dispArguementsType=iDispArguementsType;
    this.objArguementsType=iObjArguementsType;
    this.dispArgCategory=iDispArguentsCategory;
    this.objName="read"+readIndex;
    this.dispCategorie=WizardsDefinitions.COM_READ;
    this.dispName=WizardsDefinitions.READ+this.getDispArgsTitle();

    this.dispScope=dScope;
    this.objScope=oScope;
    this.locked=iLocked;
    this.created=true;
    ReadObjectList rol= ReadObjectList.getReadObjList();
    rol.add2List(this);
 }


    public ReadObject(ReadObject ro)
    {
    readIndex=ro.readIndex;
    this.messages.addAll(ro.messages);
    this.dispArguements.addAll(ro.dispArguements);
    this.objArguements.addAll(ro.objArguements);
    this.dispArguementsType.addAll(ro.dispArguementsType);
    this.objArguementsType.addAll(ro.objArguementsType);
    this.dispArgCategory.addAll(ro.dispArgCategory);
    this.objName=ro.objName;
    this.dispCategorie=WizardsDefinitions.COM_READ;
    this.dispName=ro.dispName;

    this.dispScope=ro.dispScope;
    this.objScope=ro.objScope;
    this.locked.addAll(ro.locked);
    this.created=ro.created;
    //ReadObjectList rol= ReadObjectList.getReadObjList();
    //rol.add2List(this);
 }


public void UpdateReadObject()
 {
 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new ReadObjectProperties(this) ,"Confirmation Changing "+this.getDispCateg(),
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
   {
    LockObjects lo=new LockObjects(this);
    lo.unlock();
    ReadObjectList rol= ReadObjectList.getReadObjList();
    rol.removeFromList(this);
    String [] attributes =null;//new String [3];
    ReadWizardAction ReadAction = new ReadWizardAction(true,attributes);
    ReadAction.setDispScope(this.dispScope);
    ReadAction.setObjScope(this.objScope);
    ReadWizardAction.setMesages(this.messages);
    ReadWizardAction.setDispArguements(this.dispArguements);
    ReadWizardAction.setObjArguements(this.objArguements);
    ReadWizardAction.setDispArguementsType(this.dispArguementsType);
    ReadWizardAction.setObjArguementsType(this.objArguementsType);
    ReadWizardAction.setDispCategory(this.dispArgCategory);

    ReadAction.actionPerformed(e);
    created=ReadAction.getCreated();
    if(created==true)
    {
    this.messages=ReadWizardAction.getMessages();
    this.dispArguements=ReadWizardAction.getDispArguements();
    this.objArguements=ReadWizardAction.getObjArguements();
    this.dispArguementsType=ReadWizardAction.getDispArguementsType();
    this.objArguementsType=ReadWizardAction.getObjArguementsType();
    this.dispArgCategory=ReadWizardAction.getDispCategory();
    this.dispName=WizardsDefinitions.READ+this.getDispArgsTitle();

     this.created=true;
    }
    LockObjects nlo=new LockObjects(this);
    nlo.lock();
    rol.add2List(this);
   }

    }


 public String  getDispArgs2Show()
 { String show="";

   if (!this.dispArguements.isEmpty())
   {
        show="("+dispArgCategory.get(0)+" - "+this.dispArguementsType.get(0)+") "+dispArguements.get(0);
          for (int i=1;i<dispArguements.size();i++)
             show=show+",\n("+dispArgCategory.get(i)+" - "+this.dispArguementsType.get(i)+") "+dispArguements.get(i);
   }

     return show;
}


 public String  getDispArgsTitle()
 { String show="";

   if (!this.dispArguements.isEmpty())
   {
        show=dispArguements.get(0);
          for (int i=1;i<dispArguements.size();i++)
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

  public LinkedList<String> getMessages()
  {return this.messages;}
   
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
 
 public void setMessages(LinkedList<String> iMessages)
  {this.messages=iMessages;}
   
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

       part.add(new PrintStructure(CoeusProgram.READ+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

       part.add(new PrintStructure(this.messages.get(0)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
       ra2p =new readArguements2print(this.dispArguements.get(0),false);
       part.addAll(ra2p.getPrintStructure());

      for (int i=1;i<this.dispArguements.size();i++)
       {
       part.add(new PrintStructure(", ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
       part.add(new PrintStructure(this.messages.get(i)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));

       ra2p =new readArguements2print(this.dispArguements.get(i),false);
       part.addAll(ra2p.getPrintStructure());
      }
       part.add(new PrintStructure(";\n ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

       return part;
     }

// public LinkedList<PrintStructure> getCoeusString1()
//     {
//    LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
//     AllObjectsList caol = AllObjectsList.getAllObjList();
//     AllObjects ao =null ;
//
//       part.add(new PrintStructure(CoeusProgram.READ+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
//
//       part.add(new PrintStructure(this.messages.get(0),PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
//
//       if (this.dispArgCategory.get(0).equals(WizardsDefinitions.VARIABLE))
//       {
//         ao=caol.SearchByDisplayName(this.dispArguements.get(0));
//
//         if (ao.getDispScope().equals(WizardsDefinitions.GLOBAL))
//              part.add(new PrintStructure(this.dispArguements.get(0)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.GLOBAL_COLOR));
//         else
//              part.add(new PrintStructure(this.dispArguements.get(0)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
//       }
//       else if (this.dispArgCategory.get(0).equals(WizardsDefinitions.ARRAY_ELEMANT))
//       {
//        // ao=caol.SearchByDisplayName(this.dispArguements.get(0));
//       //  if (ao.getDispScope().equals(WizardsDefinitions.GLOBAL))
//      //        part[2]=new PrintStructure(this.dispArguements.get(0)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.GLOBAL_COLOR);
//       //  else
//              part.add(new PrintStructure(this.dispArguements.get(0)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
//       }
//      else
//              part.add(new PrintStructure(this.dispArguements.get(0)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
//      for (int i=1;i<this.dispArguements.size();i++)
//       {
//       part.add(new PrintStructure(", ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
//       part.add(new PrintStructure(this.messages.get(i),PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
//
//       if (this.dispArgCategory.get(i).equals(WizardsDefinitions.VARIABLE))
//       {
//         ao=caol.SearchByDisplayName(this.dispArguements.get(i));
//
//         if (ao.getDispScope().equals(WizardsDefinitions.GLOBAL))
//              part.add(new PrintStructure(this.dispArguements.get(i)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.GLOBAL_COLOR));
//         else
//              part.add(new PrintStructure(this.dispArguements.get(i)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
//       }////////////Diorthosi ston pinaka ....mono to onoma me xroma kai oi arithmoi me xroma
//       else if (this.dispArgCategory.get(0).equals(WizardsDefinitions.ARRAY_ELEMANT))
//       {
//         //ao=caol.SearchByDisplayName(this.dispArguements.get(i));
//        // if (ao.getDispScope().equals(WizardsDefinitions.GLOBAL))
//       //       part[i*2+3]=new PrintStructure(this.dispArguements.get(i)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.GLOBAL_COLOR);
//      //   else
//         part.add(new PrintStructure(this.dispArguements.get(i)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
//       }
//      else
//         part.add(new PrintStructure(this.dispArguements.get(i)+" ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
//
//       }
//       part.add(new PrintStructure(";\n ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
//
//       return part;
//     }
//

    public String getJavaString1()
     {String s="";

     // s="\ntry{\n";

      for (int i=0;i<this.dispArguements.size();i++)
      {
        if (this.objArguementsType.get(i).equals(JavaProgram.INT))
        {
         s=s+this.objArguements.get(i)+" =  Integer.parseInt(myJOP.myGreekJOP().myshowInputDialog(" +
             this.messages.get(i)+", \"Integer Number Input\",3));\n" ;
         }
        if (this.objArguementsType.get(i).equals(JavaProgram.DOUBLE))
        {
         s=s+this.objArguements.get(i)+" =  Double.parseDouble(myJOP.myGreekJOP().myshowInputDialog(" +
             this.messages.get(i)+", \"Real Number Input\",3));\n" ;
         }
        if (this.objArguementsType.get(i).equals(JavaProgram.CHAR))
        {
         s=s+"temp =  myJOP.myGreekJOP().myshowInputDialog(" + this.messages.get(i)+", \"Character Input\",3);\n"
                 +this.objArguements.get(i)+" = temp.charAt(0); \n";
         }
        if (this.objArguementsType.get(i).equals(JavaProgram.STRING))
        {
         s=s+this.objArguements.get(i)+" = myJOP.myGreekJOP().myshowInputDialog("
                 + this.messages.get(i)+", \"String Input\",3);\n";

         }
        if (this.objArguementsType.get(i).equals(JavaProgram.BOOLEAN))
        {

         s=s+this.objArguements.get(i)+" =(myJOP.myGreekJOP().myshowInputDialog("
                 +this.messages.get(i)+", \"Boolean Input\",3).equalsIgnoreCase(\"true\")) ?true :false;\n" ;
         }
      }



     return s;
     }


 public String getDescription()
 {
 String des="The user is asked to enter "+this.getArguementsNumber()+" value(s) from the keyboard.\n"
         +"According to the order that the values are entered,they are stored in the following items:\n\n"+this.getDispArgs2Show()
         +"\n\nIf the \"CANCEL\" button is clicked or the wrong type of input is given, the program will terminate its execution!";

 return des;
 }



//    public String getJavaString1()
//     {String s="";
//
//     // s="\ntry{\n";
//
//      for (int i=0;i<this.dispArguements.size();i++)
//      {
//        if (this.objArguementsType.get(i).equals(JavaProgram.INT))
//        {
//         s=s+this.objArguements.get(i)+" =  Integer.parseInt(JOptionPane.showInputDialog(null," +
//             this.messages.get(i)+", \"Είσοδος Δεδομένων\",3));\n" ;
//         }
//        if (this.objArguementsType.get(i).equals(JavaProgram.DOUBLE))
//        {
//         s=s+this.objArguements.get(i)+" =  Double.parseDouble(JOptionPane.showInputDialog(null," +
//             this.messages.get(i)+", \"Είσοδος Δεδομένων\",3));\n" ;
//         }
//        if (this.objArguementsType.get(i).equals(JavaProgram.CHAR))
//        {
//         s=s+"temp =  JOptionPane.showInputDialog(null," + this.messages.get(i)+", \"Είσοδος Δεδομένων\",3);\n"
//                 +this.objArguements.get(i)+" = temp.charAt(0); \n";
//         }
//        if (this.objArguementsType.get(i).equals(JavaProgram.STRING))
//        {
//         s=s+this.objArguements.get(i)+" =  JOptionPane.showInputDialog(null,"
//                 + this.messages.get(i)+", \"Είσοδος Δεδομένων\",3);\n";
//
//         }
//        if (this.objArguementsType.get(i).equals(JavaProgram.BOOLEAN))
//        {
//
//         s=s+this.objArguements.get(i)+" =(JOptionPane.showInputDialog(null,"
//                 +this.messages.get(i)+", \"Είσοδος Δεδομένων\",3).equalsIgnoreCase(\"αληθης\")) ?true :false;\n" ;
//         }
//      }
//// s=s+"}\ncatch (NumberFormatException ex) { }\n\n ";
//
//
//     return s;
//     }

    
 
         
}
