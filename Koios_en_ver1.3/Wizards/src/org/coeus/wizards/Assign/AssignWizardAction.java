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


package org.coeus.wizards.Assign;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import javax.swing.JComponent;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;
import org.coeus.wizards.Messages;
import org.coeus.wizards.WizardSettingsValues;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.ImageUtilities;

// An example action demonstrating how the wizard could be called from within
// your code. You can copy-paste the code below wherever you need.
public final class AssignWizardAction implements ActionListener {

   private boolean created=false;
    private boolean update=false;
    private String [] attributes = null;
    private String dispScope=null;
    private String objScope=null;
    private String info=null;

    private static String dispName=null;
    private static String objName=null;
    private static String dispType=null;
    private static String objType=null;
    private static String dispAssignValue=null;
    private static String objAssignValue=null;
    private static String dispCategory=null;
    private static String dispAssignCategory=null;
    private static String objAssignCategory=null;

    private AllObjectsList aol=null;
    private AllObjects aoi=null;
    private WizardDescriptor.ValidatingPanel[] panels;


     public AssignWizardAction (boolean updateState,String [] inattributes)
    {this.update=updateState;
     this.attributes=inattributes;
     }

    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));

        aol=AllObjectsList.getAllObjList();
        aoi=aol.SearchByObjectScope_4FunctionsOrProcedures(this.objScope);

        if (this.update)//Update
        {  wizardDescriptor.setTitle("Updating \"ASSIGN\" Command");
           wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
           info=Messages.INFO_ASSIGN1b;
        }
        else//Create
        {
         wizardDescriptor.setTitle("\"ASSIGN\" Command Creation Wizard");
         wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
         info=Messages.INFO_ASSIGN1a;
       //  getProperties(aoi);
        }
        wizardDescriptor.putProperty("WizardPanel_image", ImageUtilities.loadImage("org/coeus/wizards/icons4wizards/Wiz_Ass.png", true));
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_ASSIGN_PANEL1);

        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled && dispName!=null) {


            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(info+ dispName+" ("+
            dispType+" "+dispCategory+") "+ Messages.INFO_ASSIGN2+ dispAssignCategory
                    +" :\n"+dispAssignValue));
            this.created=true;
        }

    }


//     public static void getProperties(AllObjects iaoi)
//    {
//
//        dispName=iaoi.getDispName();
//        dispType=iaoi.getDispType();
//        objName=iaoi.getObjName();
//        objType=iaoi.getObjType();
//        dispCategory=iaoi.getDispCateg();
//
//    }
//




    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.ValidatingPanel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.ValidatingPanel[]{
                        new AssignWizardPanel1(this.objScope)                       
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



    public String getName() {
        return "Οδηγός Δημιουργίας Εντολής \"ΚΑΛΕΣΕ\"";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }


     public boolean getCreated()
    { return this.created;}

    public static String getDispAssignName()
    {return AssignWizardAction.dispName;}

     public static String getObjAssignName()
    {return AssignWizardAction.objName;}

       public static String getDispAssignType()
    {return AssignWizardAction.dispType;}

     public static String getObjAssignType()
    {return AssignWizardAction.objType;}

     public static String getDispAssignValue()
    {return AssignWizardAction.dispAssignValue;}

     public static String getObjAssignValue()
    {return AssignWizardAction.objAssignValue;}

    public static String getDispCategory()
    {return AssignWizardAction.dispCategory;}

    public static String getDispAssignCategory()
    {return AssignWizardAction.dispAssignCategory;}

    public static String getObjAssignCategory()
    {return AssignWizardAction.objAssignCategory;}

     public String getDispScope()
    {return this.dispScope;}

     public String getObjScope()
    {return this.objScope;}

   public static void setDispAssignName(String iPN)
    {AssignWizardAction.dispName=iPN;}

    public static void setObjAssignName(String iPN)
    {AssignWizardAction.objName=iPN;}

    public static void setDispAssignType(String iPT)
    {AssignWizardAction.dispType=iPT;}

    public static void setObjAssignType(String iPT)
    {AssignWizardAction.objType=iPT;}

    public static void setDispAssignValue(String iRV)
    {AssignWizardAction.dispAssignValue=iRV;}

    public static void setObjAssignValue(String iRV)
    {AssignWizardAction.objAssignValue=iRV;}


     public static void setDispCategory(String iDC)
    {AssignWizardAction.dispCategory=iDC;}

    public static void setDispAssignCategory(String iDC)
    {AssignWizardAction.dispAssignCategory=iDC;}


    public static void setObjAssignCategory(String iDC)
    {AssignWizardAction.objAssignCategory=iDC;}


     public void setDispScope (String iDispScope)
     {this.dispScope=iDispScope;}

     public void setObjScope (String iObjScope)
     {this.objScope=iObjScope;}



}

