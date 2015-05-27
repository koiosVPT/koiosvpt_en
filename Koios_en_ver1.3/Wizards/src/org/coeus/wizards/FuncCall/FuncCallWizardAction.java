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


package org.coeus.wizards.FuncCall;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.LinkedList;
import javax.swing.JComponent;
import org.coeus.wizards.Messages;
import org.coeus.wizards.WizardSettingsValues;
import org.coeus.wizards._HelpFuntions.getFunctionsList;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

// An example action demonstrating how the wizard could be called from within
// your code. You can copy-paste the code below wherever you need.
public final class FuncCallWizardAction implements ActionListener {

   private boolean created=false;
    private boolean update=false;
   // private String [] attributes = null;
    private String dispScope=null;
    private String objScope=null;

    private  String dispFunctionName=null;
    private  String objFunctionName=null;
    private  String dispFunctionType=null;
    private  String objFunctionType=null;
    private  LinkedList<String> dispActualParameters=null;
    private  LinkedList<String> objActualParameters=null;
    private  LinkedList <String> dispActualParametersType=null;
    private  LinkedList <String> objActualParametersType=null;
    private  LinkedList <String> typicalParameters=null;
    //private static LinkedList <String> dispCategory=null;

//    private LinkedList<String> copy_dispParameters=null;
//    private LinkedList<String> copy_objParameters=null;
//    private LinkedList <String>copy_dispParametersType=null;
//    private LinkedList <String> copy_objParametersType=null;
//    //private LinkedList <String> copy_dispCategory=null;
//    private LinkedList <String> copy_typicalParameters=null;
    private WizardDescriptor.ValidatingPanel[] panels;

    private getFunctionsList gFL=null;


     public FuncCallWizardAction (boolean updateState,String iObjScope,getFunctionsList igFL)
    {this.update=updateState;
    // this.attributes=inattributes;
     this.gFL=igFL;
     this.objScope=iObjScope;

    dispActualParameters=new LinkedList<String>();
    objActualParameters=new LinkedList<String>();
    dispActualParametersType=new LinkedList<String>();
    objActualParametersType=new LinkedList<String>();
//    dispCategory=new LinkedList();
    typicalParameters=new LinkedList<String>();
//
//    copy_dispParameters=new LinkedList();
//    copy_objParameters=new LinkedList();
//    copy_dispParametersType=new LinkedList();
//    copy_objParametersType=new LinkedList();
// //   copy_dispCategory=new LinkedList();
//    copy_typicalParameters=new LinkedList();
     }

    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
//        if (this.update)//Update
//        {  wizardDescriptor.setTitle("Ενημέρωση Εντολής \"ΚΑΛΕΣΕ\".....");
//           wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
//
//
//        new  copyList(dispActualParameters,copy_dispParameters);
//        new  copyList(objActualParameters,copy_objParameters);
//        new  copyList(dispActualParametersType,copy_dispParametersType);
//        new  copyList(objActualParametersType,copy_objParametersType);
////        new  copyList(dispCategory,copy_dispCategory);
//        new  copyList(typicalParameters,copy_typicalParameters);
//      //   new  printLists(message,dispParameters,objParameters,dispParametersType,objParametersType,dispCategory);
//
//        }
//        else//Create
//        {
         wizardDescriptor.setTitle("Defining Actual Parameter(s) Wizard.....");
         wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
  //      }
       // wizardDescriptor.putProperty("WizardPanel_image", ImageUtilities.loadImage("org/coeus/wizards/icons4wizards/Wiz_Cal.png", true));
        wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_CALL_PANEL1);
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {


         //   DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.INFO_CALL1+"\n"+dispProcedureName+
        //   " "+this.getDispTyipcalParameters2Show()+"\n"+ Messages.INFO_CALL2+this.getDispActualParameters2Show()));
            this.created=true;
        }
//        else
//        {
//        new copyList(copy_dispParameters,dispActualParameters);
//        new copyList(copy_objParameters,objActualParameters);
//        new copyList(copy_dispParametersType,dispActualParametersType);
//        new copyList(copy_objParametersType,objActualParametersType);
////        new copyList(copy_dispCategory,dispCategory);
//        new copyList(copy_typicalParameters,typicalParameters);
//        }

    }


    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.ValidatingPanel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.ValidatingPanel[]{
                        new FuncCallWizardPanel1(this.objScope,gFL,this),
                        new FuncCallWizardPanel2(this.objScope,this)
            };
            String[] steps = new String[panels.length];
            for (int i = 0; i < panels.length; i++) {
                Component c = panels[i].getComponent();
                // Default step name to component name of panel. Mainly useful
                // for getting the name of the target chooser to appear in the
                // list of steps.
                steps[i] = c.getName();
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    // Sets step number of a component
                    // TODO if using org.openide.dialogs >= 7.8, can use WizardDescriptor.PROP_*:
                    jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
                    // Sets steps names for a panel
                    jc.putClientProperty("WizardPanel_contentData", steps);
                    // Turn on subtitle creation on each step
                    jc.putClientProperty("WizardPanel_autoWizardStyle", Boolean.TRUE);
                    // Show steps on the left side with the image on the background
                    jc.putClientProperty("WizardPanel_contentDisplayed", Boolean.FALSE);
                    // Turn on numbering of all steps
                    jc.putClientProperty("WizardPanel_contentNumbered", Boolean.TRUE);
                }
            }
        }
        return panels;
    }


 public String getDisplayFunctionCall()
 {
  String  show = dispFunctionName+"(";

  if (!dispActualParameters.isEmpty())
  {
     show=show+dispActualParameters.get(0);
     for (int i=1;i<dispActualParameters.size();i++)
         show=show+","+dispActualParameters.get(i);
  }

  show= show+")";
  return show;
 }


  public String getObjectFunctionCall()
 {
  String  show = objFunctionName+"(";

  if (!objActualParameters.isEmpty())
  {
     if (!objFunctionName.equalsIgnoreCase("(int)"))
         show=show+"("+objActualParametersType.get(0)+") ("+objActualParameters.get(0)+") ";
     else
        show=show+dispActualParameters.get(0);
     for (int i=1;i<objActualParameters.size();i++)
        show=show+", ("+objActualParametersType.get(i)+") ("+objActualParameters.get(i)+") ";

  }

  show= show+")";
  return show;
 }


//  public String  getDispTyipcalParameters2Show()
// { String show="( )";
//
//      if (!dispActualParameters.isEmpty())
//      {
//        show="("+dispActualParametersType.get(0)+" "+typicalParameters.get(0);
//          for (int i=1;i<dispActualParameters.size();i++)
//             show=show+", "+dispActualParametersType.get(i)+" "+typicalParameters.get(i);
//        show=show+")";
//      }
//     return show;
//}
//
//
//   public String  getDispActualParameters2Show()
// { String show="\nΗ ΔΙΑΔΙΚΑΣΙΑ δεν δέχεται παραμέτρους!";
//
//      if (!dispActualParameters.isEmpty())
//      {
//        show="\n("+dispActualParametersType.get(0)+") "+typicalParameters.get(0)+": "+dispActualParameters.get(0);
//         for (int i=1;i<dispActualParameters.size();i++)
//             show=show+"\n("+dispActualParametersType.get(i)+") "+typicalParameters.get(i)+": "+dispActualParameters.get(i);
//      }
//
//     return show;
//}

    public String getName() {
        return "Οδηγός Συμπλήρωσης Πραγματικών Παραμέτρων Συνάρτησης";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }


     public boolean getCreated()
    { return this.created;}

    public String getDispFunctionName()
    {return this.dispFunctionName;}

     public String getObjFunctionName()
    {return this.objFunctionName;}

    public String getDispFunctionType()
    {return this.dispFunctionType;}

     public String getObjFunctionType()
    {return this.objFunctionType;}

     public LinkedList<String> getDispActualParameters()
    {return this.dispActualParameters;}

     public LinkedList<String> getObjActualParameters()
    {return this.objActualParameters;}

     public LinkedList<String> getDispActualParametersType()
    {return this.dispActualParametersType;}

     public LinkedList<String> getObjActualParametersType()
    {return this.objActualParametersType;}

//    public static LinkedList<String> getDispCategory()
 //   {return CallWizardAction.dispCategory;}
    public String getDispScope()
    {return this.dispScope;}

     public String getObjScope()
    {return this.objScope;}


     public LinkedList<String> getTypicalParameters()
    {return this.typicalParameters;}

    public void setDispFunctionName(String iPN)
    {this.dispFunctionName=iPN;}

    public  void setObjFunctionName(String iPN)
    {this.objFunctionName=iPN;}

    public  void setDispFunctionType(String iPN)
    {this.dispFunctionType=iPN;}

    public  void setObjFunctionType(String iPN)
    {this.objFunctionType=iPN;}

    public  void setDispActualParameters(LinkedList<String> args)
    {this.dispActualParameters=args;}

    public  void setObjActualParameters(LinkedList<String> args)
    {this.objActualParameters=args;}

    public  void setDispActualParametersType(LinkedList<String> args)
    {this.dispActualParametersType=args;}

    public void setObjActualParametersType(LinkedList<String> args)
    {this.objActualParametersType=args;}

 //   public static void setDispCategory(LinkedList<String> args)
//    {CallWizardAction.dispCategory=args;}

    public void setTypicalParameters(LinkedList<String> iPD)
    {this.typicalParameters=iPD;}

     public void setDispScope (String iDispScope)
     {this.dispScope=iDispScope;}

     public void setObjScope (String iObjScope)
     {this.objScope=iObjScope;}



}
