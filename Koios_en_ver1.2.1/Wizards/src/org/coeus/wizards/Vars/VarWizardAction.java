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
package org.coeus.wizards.Vars;

import org.coeus.wizards.*;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import javax.swing.JComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.util.ImageUtilities;



// An example action demonstrating how the wizard could be called from within
// your code. You can copy-paste the code below wherever you need.
public final class VarWizardAction implements ActionListener {

    private String varName;
    private String varType;
    private String varInitValue;
    private boolean created=false;
    private boolean update=false;
    private String [] attributes;
    private String info1,info2;
    private String dispScope;
    private String objScope;

    private WizardDescriptor.ValidatingPanel[] panels;

    public VarWizardAction (boolean updateState,String [] inattributes)
    {this.update=updateState;
     this.attributes=inattributes;}

    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        if (this.update)//Update
        {  wizardDescriptor.setTitle("Updating Variable Properties");
           wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
           wizardDescriptor.putProperty(WizardSettingsValues.VAR_NAME, attributes[0]);
           wizardDescriptor.putProperty(WizardSettingsValues.VAR_TYPE, attributes[1]);
           wizardDescriptor.putProperty(WizardSettingsValues.VAR_VALUE,attributes[2]);
           info1=Messages.INFO_VAR1b;
           info2=Messages.INFO_VAR5b;

        }
        else//Create
        { wizardDescriptor.setTitle("Variable Declaration Wizard");
          wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
          info1=Messages.INFO_VAR1a;
          info2=Messages.INFO_VAR5a;
        }
        wizardDescriptor.putProperty("WizardPanel_image", ImageUtilities.loadImage("org/coeus/wizards/icons4wizards/Wiz_Var.png", true));
        //wizardDescriptor.putProperty("WizardPanel_infoMessage", Messages.INFO_Panel1);
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_VAR_PANEL1);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {

            this.varName = (String)wizardDescriptor.getProperty(WizardSettingsValues.VAR_NAME);
            this.varType =  (String)wizardDescriptor.getProperty(WizardSettingsValues.VAR_TYPE);
            this.varInitValue = (String)wizardDescriptor.getProperty(WizardSettingsValues.VAR_VALUE);

            this.varName = this.varName.toUpperCase();

            if (!this.varInitValue.isEmpty())
            {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(info1 +
                        varType + Messages.INFO_VAR2+ varName + Messages.INFO_VAR3 + varInitValue+ info2));}
            else
            {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(info1 +
                        varType + Messages.INFO_VAR2+ varName + Messages.INFO_VAR4+info2));}

           
            this.created=true;
       }

    }

    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.ValidatingPanel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.ValidatingPanel[]{
                        new VarWizardPanel1(this.dispScope,this.objScope),
                        new VarWizardPanel2()
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
                    ////////Initial Info Message
                   // jc.putClientProperty("WizardPanel_infoMessage",Messages.INFO_Panel1);
                            jc.putClientProperty(WizardDescriptor.PROP_INFO_MESSAGE,Messages.INFO_VAR_PANEL1);

                }
            }
        }
        return panels;
    }





    public String getName() {
        return "Start Sample Wizard";
    }


    public String getVarName()
    {
     return this.varName;
    }

    public String getVarType()
    {
     return this.varType;
    }

    public String getVarInitValue()
    {
     return this.varInitValue;
    }

    public boolean getCreated()
    {
     return this.created;
    }

      public String getDispScope()
    {return this.dispScope;}

     public String getObjScope()
    {return this.objScope;}


     public void setDispScope (String iDispScope)
     {this.dispScope=iDispScope;}

     public void setObjScope (String iObjScope)
     {this.objScope=iObjScope;}

}
