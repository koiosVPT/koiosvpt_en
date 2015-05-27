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
import org.coeus.wizards.Call.CallWizardAction;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class CallObject {

    private String dispName=null;
    private String objName=null;
    private String dispCategorie=null;
    private String dispScope=null;
    private String objScope=null;
  

    private String dispProcedureName=null;
    private String objProcedureName=null;
    private LinkedList <String> dispActualParameters=new LinkedList();
    private LinkedList <String> objActualParameters=new LinkedList();
    private LinkedList <String> dispActualParametersType=new LinkedList();
    private LinkedList <String> objActualParametersType=new LinkedList();
    private LinkedList <String> typicalParameters=new LinkedList();
    private boolean created=false;


    private static int callCount = 0;
    private final int callIndex;
    private LinkedList<String> locked=new LinkedList();
    private ActionEvent e;


    public CallObject(String dScope,String oScope)
    {
    callIndex=callCount++;
    CallWizardAction CallAction = new CallWizardAction(false,null);
    this.dispScope=dScope;
    this.objScope=oScope;
    CallAction.setDispScope(this.dispScope);
    CallAction.setObjScope(this.objScope);
    CallAction.actionPerformed(e);
    created=CallAction.getCreated();
    if(created==true)
    {
    this.dispProcedureName=CallWizardAction.getDispProcedureName();
    this.objProcedureName=CallWizardAction.getObjProcedureName();
    this.dispActualParameters=CallWizardAction.getDispActualParameters();
    this.objActualParameters=CallWizardAction.getObjActualParameters();
    this.dispActualParametersType=CallWizardAction.getDispActualParametersType();
    this.objActualParametersType=CallWizardAction.getObjActualParametersType();
//    this.dispArgCategory=CallWizardAction.getDispCategory();
    this.typicalParameters=CallWizardAction.getTypicalParameters();
    this.dispCategorie=WizardsDefinitions.COM_CALL;
    this.dispName=WizardsDefinitions.CALL+this.dispProcedureName;
    this.objName="call"+callIndex;

    this.created=true;
    LockObjects lo=new LockObjects(this);
    lo.lock();
    CallObjectList col= CallObjectList.getCallObjList();
    col.add2List(this);
    }
    }

    public CallObject(String iDispProcedureName,String iObjProcedureName,LinkedList<String> iDispActualParameters,
            LinkedList<String> iObjActualParameters,// LinkedList<String> iDispArguentsCategory,
            LinkedList <String> itypicalParameters,
           LinkedList<String> iDispActualParametersType ,LinkedList<String> iObjActualParametersType
           ,String dScope,String oScope,LinkedList<String> iLocked)
    {
    callIndex=callCount++;
    this.dispProcedureName=iDispProcedureName;
    this.objProcedureName=iObjProcedureName;
    this.dispActualParameters=iDispActualParameters;
    this.objActualParameters=iObjActualParameters;
    this.dispActualParametersType=iDispActualParametersType;
    this.objActualParametersType=iObjActualParametersType;
//    this.dispArgCategory=iDispArguentsCategory;
    this.typicalParameters=itypicalParameters;
    this.objName="call"+callIndex;
    this.dispCategorie=WizardsDefinitions.COM_CALL;
    this.dispName=WizardsDefinitions.CALL+this.dispProcedureName;

    this.dispScope=dScope;
    this.objScope=oScope;
    this.locked=iLocked;
    this.created=true;
    CallObjectList col= CallObjectList.getCallObjList();
    col.add2List(this);
 }


  public CallObject(CallObject co)
    {
    this.callIndex=co.callIndex;
    this.dispProcedureName=co.dispProcedureName;
    this.objProcedureName=co.objProcedureName;
    this.dispActualParameters.addAll(co.dispActualParameters);
    this.objActualParameters.addAll(co.objActualParameters);
    this.dispActualParametersType.addAll(co.dispActualParametersType);
    this.objActualParametersType.addAll(co.objActualParametersType);
    this.typicalParameters.addAll(co.typicalParameters);
    this.objName=co.objName;
    this.dispCategorie=WizardsDefinitions.COM_CALL;
    this.dispName=co.dispName;
    this.dispScope=co.dispScope;
    this.objScope=co.objScope;
    this.locked.addAll(co.locked);
    this.created=co.created;
    //CallObjectList col= CallObjectList.getCallObjList();
    //col.add2List(this);
 }



public void UpdateCallObject()
 {
 NotifyDescriptor d =new NotifyDescriptor.Confirmation(
 new CallObjectProperties(this) ,"Confirmation Changing "+this.getDispName(),
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
   {
    LockObjects lo=new LockObjects(this);
    lo.unlock();
    CallObjectList col= CallObjectList.getCallObjList();
    col.removeFromList(this);
    String [] attributes =null;//new String [3];
    CallWizardAction CallAction = new CallWizardAction(true,attributes);
    CallAction.setDispScope(this.dispScope);
    CallAction.setObjScope(this.objScope);
    CallWizardAction.setDispProcedureName(this.dispProcedureName);
    CallWizardAction.setObjProcedureName(this.objProcedureName);
    CallWizardAction.setDispActualParameters(this.dispActualParameters);
    CallWizardAction.setObjActualParameters(this.objActualParameters);
    CallWizardAction.setDispActualParametersType(this.dispActualParametersType);
    CallWizardAction.setObjActualParametersType(this.objActualParametersType);
//    CallWizardAction.setDispCategory(this.dispArgCategory);
    CallWizardAction.setTypicalParameters(this.typicalParameters);

    CallAction.actionPerformed(e);
    created=CallAction.getCreated();
    if(created==true)
    {
    this.dispProcedureName=CallWizardAction.getDispProcedureName();
    this.objProcedureName=CallWizardAction.getObjProcedureName();
    this.dispActualParameters=CallWizardAction.getDispActualParameters();
    this.objActualParameters=CallWizardAction.getObjActualParameters();
    this.dispActualParametersType=CallWizardAction.getDispActualParametersType();
    this.objActualParametersType=CallWizardAction.getObjActualParametersType();
//    this.dispArgCategory=CallWizardAction.getDispCategory();
    this.typicalParameters=CallWizardAction.getTypicalParameters();
    this.dispName=WizardsDefinitions.CALL+this.dispProcedureName;


     this.created=true;
    }
    LockObjects nlo=new LockObjects(this);
    nlo.lock();
    col.add2List(this);
   }

    }


  public String  getDispTyipcalParameters2Show()
 { String show="( )";

      if (!dispActualParameters.isEmpty())
      {
        show="("+dispActualParametersType.get(0)+" "+typicalParameters.get(0);
          for (int i=1;i<dispActualParameters.size();i++)
             show=show+", \n"+dispActualParametersType.get(i)+" "+typicalParameters.get(i);
        show=show+")";
      }
     return show;
}


   public String  getDispActualParameters2Show()
 { String show="\nPROCEDURE does not take any input parameters!";

      if (!dispActualParameters.isEmpty())
      {
        show="\n"+dispActualParameters.get(0)+" ~> ("+dispActualParametersType.get(0)+") "+typicalParameters.get(0);
         for (int i=1;i<dispActualParameters.size();i++)
             show=show+"\n"+dispActualParameters.get(i)+" ~> ("+dispActualParametersType.get(i)+") "+typicalParameters.get(i);
      }

     return show;
}


///Getters
 public int getActualParametersNumber()
 {int num=0;
 if (this.dispActualParameters!=null)
    num=this.dispActualParameters.size();
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

   public String getDispProcedureName()
   {return this.dispProcedureName;}

   public String getObjProcedureName()
   {return this.objProcedureName;}

  public LinkedList<String> getDispActualParameters()
   { return this.dispActualParameters;}

   public LinkedList<String> getObjActualParameters()
   { return this.objActualParameters;}

   public LinkedList <String> gettypicalParameters ()
   {return this.typicalParameters;}

     public LinkedList<String> getDispActualParametersType()
    {return this.dispActualParametersType;}

     public LinkedList<String> getObjActualParametersType()
    {return this.objActualParametersType;}


 ///Setters
public void setDispScope(String indScope)
{this.dispScope=indScope;}

public void setObjScope(String inoScope)
{this.objScope=inoScope;}

 public void setLocked(LinkedList<String> ilocked)
 {this.locked=ilocked;}


  public void setDispActualParameters(LinkedList<String> iDispActualParameters)
   {this.dispActualParameters=iDispActualParameters;}

   public void setObjActualParameters(LinkedList<String> iObjActualParameters)
   {this.objActualParameters=iObjActualParameters;}

   public void setDispActualParametersType(LinkedList<String> args)
    {this.dispActualParametersType=args;}

    public void setObjActualParametersType(LinkedList<String> args)
    {this.objActualParametersType=args;}

 /////////Printers//////////
 public LinkedList<PrintStructure> getCoeusString1()
     {
    LinkedList<PrintStructure> part = new LinkedList<PrintStructure>();
    readArguements2print ra2p=null;

       part.add(new PrintStructure(CoeusProgram.CALL+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));

       part.add(new PrintStructure(this.dispProcedureName+" (",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

       if (!this.dispActualParameters.isEmpty())
       {
       if (this.objActualParametersType.get(0).equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
       {
           ra2p =new readArguements2print(this.dispActualParameters.get(0),true);
           part.addAll(ra2p.getPrintStructure());
       }
//       else if (this.objActualParametersType.get(0).equalsIgnoreCase(WizardsDefinitions.CHAR) ||
//              this.objActualParametersType.get(0).equalsIgnoreCase(WizardsDefinitions.STRING) )
      else if ((this.objActualParametersType.get(0).equalsIgnoreCase(WizardsDefinitions.CHAR) &&
       this.dispActualParameters.get(0).startsWith("'"))
       ||
       (this.objActualParametersType.get(0).equalsIgnoreCase(WizardsDefinitions.STRING)
        && this.dispActualParameters.get(0).startsWith("\"")))
           part.add(new PrintStructure(this.dispActualParameters.get(0),PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
       else
       {
            ra2p =new readArguements2print(this.dispActualParameters.get(0),false);
            part.addAll(ra2p.getPrintStructure());
       }

       for (int i=1;i<this.dispActualParameters.size();i++)
        { 
          part.add(new PrintStructure(", ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
           if (this.objActualParametersType.get(i).equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
           {
               ra2p =new readArguements2print(this.dispActualParameters.get(i),true);
               part.addAll(ra2p.getPrintStructure());
           }
//           else if (this.objActualParametersType.get(i).equalsIgnoreCase(WizardsDefinitions.CHAR)  ||
//              this.objActualParametersType.get(i).equalsIgnoreCase(WizardsDefinitions.STRING) )
        else if ((this.objActualParametersType.get(i).equalsIgnoreCase(WizardsDefinitions.CHAR) &&
       this.dispActualParameters.get(i).startsWith("'"))
       ||
       (this.objActualParametersType.get(i).equalsIgnoreCase(WizardsDefinitions.STRING)
        && this.dispActualParameters.get(i).startsWith("\"")))
               part.add(new PrintStructure(this.dispActualParameters.get(i),PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
           else
           {
                ra2p =new readArguements2print(this.dispActualParameters.get(i),false);
                part.addAll(ra2p.getPrintStructure());
           }
        }
       }
       part.add(new PrintStructure(" );\n ",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

       return part;
     }



    public String getJavaString1()
     {String s="";

      s=this.objProcedureName+" (";

      if (!this.objActualParameters.isEmpty())
      {
      s=s+this.objActualParameters.get(0);
      for (int i=1;i<this.objActualParameters.size();i++)
         s=s+", "+this.objActualParameters.get(i);
      }
      s=s+" );\n";

      return s;
     }

 public String getDescription()
 {
 String des="This command is calling PROCEDURE "+this.dispProcedureName+
         ".\nProgram looks for PROCEDURE " +this.dispProcedureName
         +" and executes its commands sequentially.";
         if (!this.dispActualParameters.isEmpty())
         {
         String actualParams=this.dispActualParameters.get(0)+" ("+this.dispActualParametersType.get(0)+")";
         for (int i=1;i<this.dispActualParameters.size();i++)
             actualParams=actualParams+",\n"+this.dispActualParameters.get(i)+" ("+this.dispActualParametersType.get(i)+")";
                 
         des=des+"\n\nDuring the execution of the commands in PROCEDURE "+this.dispProcedureName+
                 ",\nthe formal input parameters\n\n"+this.getDispTyipcalParameters2Show() +
                 "\n\nwill be replaced by the actual input parameters\n\n"+actualParams
                 +"."+"\n\nWhen all commands of the called PROCEDURE are executed, the execution of the program " +
                 "continues with the command following this CALL command.";
         }

 return des;
 }


}
