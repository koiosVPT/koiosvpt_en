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
package org.coeus.wizards.Read;

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
public final class ReadWizardAction implements ActionListener {

    private boolean created=false;
    private boolean update=false;
    private String [] attributes = null;
    private String dispScope=null;
    private String objScope=null,info;

    private static LinkedList<String> message=null;//,copy_message=null;
    private static LinkedList<String> dispArguements=null;//,copy_dispArguements=null;
    private static LinkedList<String> objArguements=null;//,copy_objArguements=null;
    private static LinkedList <String> dispArguementsType=null;//,copy_dispArguementsType=null;
    private static LinkedList <String> objArguementsType=null;//,copy_objArguementsType=null;
    private static LinkedList <String> dispCategory=null;//,copy_dispCategory=null;

    private LinkedList<String> copy_message=null;
    private LinkedList<String> copy_dispArguements=null;
    private LinkedList<String> copy_objArguements=null;
    private LinkedList <String>copy_dispArguementsType=null;
    private LinkedList <String> copy_objArguementsType=null;
    private LinkedList <String> copy_dispCategory=null;

    private WizardDescriptor.ValidatingPanel[] panels;


     public ReadWizardAction (boolean updateState,String [] inattributes)
    {this.update=updateState;
     this.attributes=inattributes;
     
    message= new LinkedList<String>();
    dispArguements=new LinkedList<String>();
    objArguements=new LinkedList<String>();
    dispArguementsType=new LinkedList<String>();
    objArguementsType=new LinkedList<String>();
    dispCategory=new LinkedList<String>();
   
    copy_message=new LinkedList<String>();
    copy_dispArguements=new LinkedList<String>();
    copy_objArguements=new LinkedList<String>();
    copy_dispArguementsType=new LinkedList<String>();
    copy_objArguementsType=new LinkedList<String>();
    copy_dispCategory=new LinkedList<String>();
     }

    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        if (this.update)//Update
        {  wizardDescriptor.setTitle("Updating \"READ\" Command");
           wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));

        new  copyList(message,copy_message);
        new  copyList(dispArguements,copy_dispArguements);
        new  copyList(objArguements,copy_objArguements);
        new  copyList(dispArguementsType,copy_dispArguementsType);
        new  copyList(objArguementsType,copy_objArguementsType);
        new  copyList(dispCategory,copy_dispCategory);

      //   new  printLists(message,dispArguements,objArguements,dispArguementsType,objArguementsType,dispCategory);
          info=Messages.INFO_READ1b;
        }
        else//Create
        { wizardDescriptor.setTitle("\"READ\" Command Creation Wizard");
          wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
          info=Messages.INFO_READ1a;

        }
        wizardDescriptor.putProperty("WizardPanel_image", ImageUtilities.loadImage("org/coeus/wizards/icons4wizards/Wiz_Rea.png", true));
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_READ_PANEL1);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {


            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(info+
                    dispArguements.size()+ Messages.INFO_READ2+this.getDispArgs2Show()));
            this.created=true;
        }
        else
        {
        new copyList(copy_message,message);
        new copyList(copy_dispArguements,dispArguements);
        new copyList(copy_objArguements,objArguements);
        new copyList(copy_dispArguementsType,dispArguementsType);
        new copyList(copy_objArguementsType,objArguementsType);
        new copyList(copy_dispCategory,dispCategory);
        }
   
    }


    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.ValidatingPanel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.ValidatingPanel[]{
                        new ReadWizardPanel1(this.objScope)
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



   public String  getDispArgs2Show()
 { String show="";

        show="("+dispCategory.get(0)+") "+dispArguements.get(0);
          for (int i=1;i<dispArguements.size();i++)
             show=show+", ("+dispCategory.get(i)+") "+dispArguements.get(i);

     return show;
}

    public String getName() {
        return "Οδηγός Δημιουργίας Εντολής \"ΔΙΑΒΑΣΕ\"";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }


     public boolean getCreated()
    { return this.created;    }

    public static LinkedList<String> getMessages()
    {return ReadWizardAction.message;}

    public static LinkedList<String> getDispArguements()
    {return ReadWizardAction.dispArguements;}

     public static LinkedList<String> getObjArguements()
    {return ReadWizardAction.objArguements;}

     public static LinkedList<String> getDispArguementsType()
    {return ReadWizardAction.dispArguementsType;}

     public static LinkedList<String> getObjArguementsType()
    {return ReadWizardAction.objArguementsType;}

    public static LinkedList<String> getDispCategory()
    {return ReadWizardAction.dispCategory;}

    public static void setMesages(LinkedList<String> m)
    {ReadWizardAction.message=m;}

    public static void setDispArguements(LinkedList<String> args)
    {ReadWizardAction.dispArguements=args;}

    public static void setObjArguements(LinkedList<String> args)
    {ReadWizardAction.objArguements=args;}

    public static void setDispArguementsType(LinkedList<String> args)
    {ReadWizardAction.dispArguementsType=args;}

    public static void setObjArguementsType(LinkedList<String> args)
    {ReadWizardAction.objArguementsType=args;}

    public static void setDispCategory(LinkedList<String> args)
    {ReadWizardAction.dispCategory=args;}
 
    public String getDispScope()
    {return this.dispScope;}

     public String getObjScope()
    {return this.objScope;}


     public void setDispScope (String iDispScope)
     {this.dispScope=iDispScope;}

     public void setObjScope (String iObjScope)
     {this.objScope=iObjScope;}


}
