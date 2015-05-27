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
package org.coeus.wizards.Call;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.LinkedList;
import javax.swing.JComponent;
import org.coeus.wizards.Messages;
import org.coeus.wizards.WizardSettingsValues;
import org.coeus.wizards._HelpFuntions.copyList;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.ImageUtilities;

// An example action demonstrating how the wizard could be called from within
// your code. You can copy-paste the code below wherever you need.
public final class CallWizardAction implements ActionListener {

   private boolean created=false;
    private boolean update=false;
    private String [] attributes = null;
    private String dispScope=null;
    private String objScope=null;
    private String info=null;

    private static String dispProcedureName=null;
    private static String objProcedureName=null;
    private static LinkedList<String> dispActualParameters=null;
    private static LinkedList<String> objActualParameters=null;
    private static LinkedList <String> dispActualParametersType=null;
    private static LinkedList <String> objActualParametersType=null;
    private static LinkedList <String> typicalParameters=null;
    //private static LinkedList <String> dispCategory=null;
   
    private LinkedList<String> copy_dispParameters=null;
    private LinkedList<String> copy_objParameters=null;
    private LinkedList <String>copy_dispParametersType=null;
    private LinkedList <String> copy_objParametersType=null;
    //private LinkedList <String> copy_dispCategory=null;
    private LinkedList <String> copy_typicalParameters=null;
    private WizardDescriptor.ValidatingPanel[] panels;


     public CallWizardAction (boolean updateState,String [] inattributes)
    {this.update=updateState;
     this.attributes=inattributes;

   
    dispActualParameters=new LinkedList<String>();
    objActualParameters=new LinkedList<String>();
    dispActualParametersType=new LinkedList<String>();
    objActualParametersType=new LinkedList<String>();
//    dispCategory=new LinkedList();
    typicalParameters=new LinkedList<String>();

    copy_dispParameters=new LinkedList<String>();
    copy_objParameters=new LinkedList<String>();
    copy_dispParametersType=new LinkedList<String>();
    copy_objParametersType=new LinkedList<String>();
 //   copy_dispCategory=new LinkedList();
    copy_typicalParameters=new LinkedList<String>();
     }

    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));

        if (this.update)//Update
        {  wizardDescriptor.setTitle("Updating \"CALL\" Command");
           wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));

      
        new  copyList(dispActualParameters,copy_dispParameters);
        new  copyList(objActualParameters,copy_objParameters);
        new  copyList(dispActualParametersType,copy_dispParametersType);
        new  copyList(objActualParametersType,copy_objParametersType);
//        new  copyList(dispCategory,copy_dispCategory);
        new  copyList(typicalParameters,copy_typicalParameters);
      //   new  printLists(message,dispParameters,objParameters,dispParametersType,objParametersType,dispCategory);
        info=Messages.INFO_CALL1b;
        }
        else//Create
        {
         wizardDescriptor.setTitle("\"CALL\" Command Creation Wizard");
         wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
         info=Messages.INFO_CALL1a;
        }
        wizardDescriptor.putProperty("WizardPanel_image", ImageUtilities.loadImage("org/coeus/wizards/icons4wizards/Wiz_Cal.png", true));
        
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_CALL_PANEL1);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {


            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(info+"\n"+dispProcedureName+
            " "+this.getDispTyipcalParameters2Show()+"\n"+ Messages.INFO_CALL2+this.getDispActualParameters2Show()));
            this.created=true;
        }
        else
        {
        new copyList(copy_dispParameters,dispActualParameters);
        new copyList(copy_objParameters,objActualParameters);
        new copyList(copy_dispParametersType,dispActualParametersType);
        new copyList(copy_objParametersType,objActualParametersType);
//        new copyList(copy_dispCategory,dispCategory);
        new copyList(copy_typicalParameters,typicalParameters);
        }

    }


    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.ValidatingPanel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.ValidatingPanel[]{
                        new CallWizardPanel1(this.objScope),
                        new CallWizardPanel2(this.objScope)
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
                    jc.putClientProperty("WizardPanel_contentDisplayed", Boolean.TRUE);
                    // Turn on numbering of all steps
                    jc.putClientProperty("WizardPanel_contentNumbered", Boolean.TRUE);
 
                }
            }
        }
        return panels;
    }


  public String  getDispTyipcalParameters2Show()
 { String show="( )";

      if (!dispActualParameters.isEmpty())
      {
        show="("+dispActualParametersType.get(0)+" "+typicalParameters.get(0);
          for (int i=1;i<dispActualParameters.size();i++)
             show=show+", "+dispActualParametersType.get(i)+" "+typicalParameters.get(i);
        show=show+")";
      }
     return show;
}


   public String  getDispActualParameters2Show()
 { String show="\nPROCEDURE does not take any parameters!";
      
      if (!dispActualParameters.isEmpty())
      {
        show="\n"+dispActualParameters.get(0)+" ~> ("+dispActualParametersType.get(0)+") "+typicalParameters.get(0);
         for (int i=1;i<dispActualParameters.size();i++)
             show=show+"\n"+dispActualParameters.get(i)+" ~> ("+dispActualParametersType.get(i)+") "+typicalParameters.get(i);
      }

     return show;
}

    public String getName() {
        return "Οδηγός Δημιουργίας Εντολής \"ΚΑΛΕΣΕ\"";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }


     public boolean getCreated()
    { return this.created;}

    public static String getDispProcedureName()
    {return CallWizardAction.dispProcedureName;}

     public static String getObjProcedureName()
    {return CallWizardAction.objProcedureName;}

    public static LinkedList<String> getDispActualParameters()
    {return CallWizardAction.dispActualParameters;}

     public static LinkedList<String> getObjActualParameters()
    {return CallWizardAction.objActualParameters;}

     public static LinkedList<String> getDispActualParametersType()
    {return CallWizardAction.dispActualParametersType;}

     public static LinkedList<String> getObjActualParametersType()
    {return CallWizardAction.objActualParametersType;}

//    public static LinkedList<String> getDispCategory()
 //   {return CallWizardAction.dispCategory;}

    public static LinkedList<String> getTypicalParameters()
    {return CallWizardAction.typicalParameters;}

     public String getDispScope()
    {return this.dispScope;}

     public String getObjScope()
    {return this.objScope;}

    public static void setDispProcedureName(String iPN)
    {CallWizardAction.dispProcedureName=iPN;}

   public static void setObjProcedureName(String iPN)
    {CallWizardAction.objProcedureName=iPN;}

   public static void setDispActualParameters(LinkedList<String> args)
    {CallWizardAction.dispActualParameters=args;}

    public static void setObjActualParameters(LinkedList<String> args)
    {CallWizardAction.objActualParameters=args;}

    public static void setDispActualParametersType(LinkedList<String> args)
    {CallWizardAction.dispActualParametersType=args;}

    public static void setObjActualParametersType(LinkedList<String> args)
    {CallWizardAction.objActualParametersType=args;}

 //   public static void setDispCategory(LinkedList<String> args)
//    {CallWizardAction.dispCategory=args;}

    public static void setTypicalParameters(LinkedList<String> iPD)
    {CallWizardAction.typicalParameters=iPD;}
 
     public void setDispScope (String iDispScope)
     {this.dispScope=iDispScope;}

     public void setObjScope (String iObjScope)
     {this.objScope=iObjScope;}

   

}
