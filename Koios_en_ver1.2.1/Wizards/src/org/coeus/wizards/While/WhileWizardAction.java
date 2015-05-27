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
package org.coeus.wizards.While;


import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import javax.swing.JComponent;
import org.coeus.wizards.Messages;
import org.coeus.wizards.WizardSettingsValues;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.ImageUtilities;

// An example action demonstrating how the wizard could be called from within
// your code. You can copy-paste the code below wherever you need.
public final class WhileWizardAction implements ActionListener {

    private boolean created=false;
    private boolean update=false;
    private String [] attributes = null;
    private String dispScope=null;
    private String objScope=null;

    private static String dispWhileValue=null;
    private static String objWhileValue=null;
    private String commandTitle=null;
    private String picture=null,info;
    private boolean doWhile;
   
    private WizardDescriptor.ValidatingPanel[] panels;


     public WhileWizardAction (boolean updateState,String [] inattributes,boolean idoWhile)
    {this.update=updateState;
     this.attributes=inattributes;
     this.doWhile=idoWhile;
     
     if (doWhile)
     {   commandTitle="DO..WHILE";
         picture="org/coeus/wizards/icons4wizards/Wiz_DoWhile.png";
     }
     else
     {
         commandTitle="WHILE..REPEAT";
         picture="org/coeus/wizards/icons4wizards/Wiz_While.png";
     }

     }

    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));

       
        if (this.update)//Update
        {  wizardDescriptor.setTitle("Updating \""+commandTitle+"\" Command");
           wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
           if (doWhile)
             info=Messages.INFO_DOWHILE2b;
            else
             info=Messages.INFO_WHILE2b;
        }
        else//Create
        {
         wizardDescriptor.setTitle("\""+commandTitle+"\" Command Creation Wizard");
         wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
           if (doWhile)
             info=Messages.INFO_DOWHILE2a;
            else
             info=Messages.INFO_WHILE2a;

        }
        wizardDescriptor.putProperty("WizardPanel_image", ImageUtilities.loadImage(picture, true));
       
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_WHILE_PANEL1);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {
          DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.INFO_WHILE1+commandTitle+
           info+dispWhileValue+Messages.INFO_WHILE3));
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
                        new WhileWizardPanel1(this.objScope,this.doWhile)

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
        return "Οδηγός Δημιουργίας Εντολής \""+commandTitle+"\"";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }


     public boolean getCreated()
    { return this.created;}

  
     public static String getDispWhileValue()
    {return WhileWizardAction.dispWhileValue;}

     public static String getObjWhileValue()
    {return WhileWizardAction.objWhileValue;}

  
     public String getDispScope()
    {return this.dispScope;}

     public String getObjScope()
    {return this.objScope;}

  
    public static void setDispWhileValue(String iRV)
    {WhileWizardAction.dispWhileValue=iRV;}

    public static void setObjWhileValue(String iRV)
    {WhileWizardAction.objWhileValue=iRV;}


    public void setDispScope (String iDispScope)
     {this.dispScope=iDispScope;}

     public void setObjScope (String iObjScope)
     {this.objScope=iObjScope;}


}
