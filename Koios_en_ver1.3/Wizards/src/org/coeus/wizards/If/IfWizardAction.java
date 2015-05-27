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

package org.coeus.wizards.If;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.LinkedList;
import javax.swing.JComponent;
import org.coeus.wizards.Messages;
import org.coeus.wizards.WizardSettingsValues;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards._HelpFuntions.copyIntegerList;
import org.coeus.wizards._HelpFuntions.copyList;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.ImageUtilities;

// An example action demonstrating how the wizard could be called from within
// your code. You can copy-paste the code below wherever you need.
public final class IfWizardAction implements ActionListener {

    private boolean created=false;
    private boolean update=false;
    private String [] attributes = null;
    private String dispScope=null;
    private String objScope=null;
    private String commandName="IF..ELSE",info;


    private static LinkedList <String> dispStatement=null;
    private static LinkedList <String> objStatement=null;
    private static LinkedList <String> dispStatementCategory=null;
    private static LinkedList <Integer> oldPosition=null;
    private static LinkedList <Integer> currentPosition= null;




    private LinkedList <String>copy_dispStatement=null;
    private LinkedList <String> copy_objStatement=null;
    private LinkedList <String> copy_dispStatementCategory=null;
    private LinkedList <Integer> copy_oldPosition= null;
    private LinkedList <Integer> copy_currentPosition=null;

    private WizardDescriptor.ValidatingPanel[] panels;


     public IfWizardAction (boolean updateState,String [] inattributes)
    {this.update=updateState;
     this.attributes=inattributes;


    dispStatement=new LinkedList<String>();
    objStatement=new LinkedList<String>();
    dispStatementCategory=new LinkedList<String>();
    oldPosition= new LinkedList<Integer>();
    currentPosition= new LinkedList<Integer>();

    copy_dispStatement=new LinkedList<String>();
    copy_objStatement=new LinkedList<String>();
    copy_dispStatementCategory=new LinkedList<String>();
    copy_oldPosition= new LinkedList<Integer>();
    copy_currentPosition= new LinkedList<Integer>();

     }

    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        if (this.update)//Update
        {  wizardDescriptor.setTitle("Updating \"IF..ELSE\" Command");
           wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));


        new  copyList(dispStatement,copy_dispStatement);
        new  copyList(objStatement,copy_objStatement);
        new  copyList(dispStatementCategory,copy_dispStatementCategory);
        new  copyIntegerList(oldPosition,copy_oldPosition);
        new  copyIntegerList(currentPosition,copy_currentPosition);
      //   new  printLists(message,dispArguements,objArguements,dispStatement,objStatement,dispCategory);
        info=Messages.INFO_IF1b;
        }
        else//Create
        { wizardDescriptor.setTitle("\"IF..ELSE\" Command Creation Wizard");
          wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
          info=Messages.INFO_IF1a;
        }
        wizardDescriptor.putProperty("WizardPanel_image", ImageUtilities.loadImage("org/coeus/wizards/icons4wizards/Wiz_If.png", true));
        wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_FOR_PANEL1);
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {


            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(info+showBlocks()
                    +Messages.INFO_IF2));
            this.created=true;
        }
        else
        {

        new copyList(copy_dispStatement,dispStatement);
        new copyList(copy_objStatement,objStatement);
        new copyList(copy_dispStatementCategory,dispStatementCategory);
        new  copyIntegerList(copy_oldPosition,oldPosition);
        new  copyIntegerList(copy_currentPosition,currentPosition);
        }

    }


    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.ValidatingPanel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.ValidatingPanel[]{
                        new IfWizardPanel1(this.objScope,this.commandName)
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
        return "Οδηγός Δημιουργίας Εντολής \"AN..ΑΛΛΙΩΣ\"";
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }


    public String showBlocks()
    {
    String s="";
    s=IfWizardAction.dispStatementCategory.get(0)+": ("+IfWizardAction.dispStatement.get(0)+")";
    for (int i=1;i<IfWizardAction.dispStatement.size();i++)
        if (IfWizardAction.dispStatementCategory.get(i).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
          s=s+"\n"+IfWizardAction.dispStatementCategory.get(i)+": ("+IfWizardAction.dispStatement.get(i)+")";
        else
          s=s+"\n"+IfWizardAction.dispStatementCategory.get(i);

    return s;
    }

//Getters
     public boolean getCreated()
    { return this.created;    }

   
     public static LinkedList<String> getDispStatement()
    {return IfWizardAction.dispStatement;}

     public static LinkedList<String> getObjStatement()
    {return IfWizardAction.objStatement;}

   public static LinkedList<String> getDispStatementCategory()
    {return IfWizardAction.dispStatementCategory;}

     public String getDispScope()
    {return this.dispScope;}

     public String getObjScope()
    {return this.objScope;}

    public static LinkedList<Integer> getOldPositions()
    {return IfWizardAction.oldPosition;}

    public static LinkedList<Integer> getCurrentPositions()
    {return IfWizardAction.currentPosition;}

//Setters
    public static void setDispStatement(LinkedList<String> args)
    {IfWizardAction.dispStatement=args;}

    public static void setObjStatement(LinkedList<String> args)
    {IfWizardAction.objStatement=args;}

    public static void setDispStatementCategory(LinkedList<String> args)
    {IfWizardAction.dispStatementCategory=args;}

    public static void  setOldPositions(LinkedList<Integer> iOldPositions)
    {IfWizardAction.oldPosition=iOldPositions;}

    public static void  setCurrentPositions(LinkedList<Integer> iCurrentPositions)
    {IfWizardAction.oldPosition=iCurrentPositions;}

     public void setDispScope (String iDispScope)
     {this.dispScope=iDispScope;}

     public void setObjScope (String iObjScope)
     {this.objScope=iObjScope;}


}
