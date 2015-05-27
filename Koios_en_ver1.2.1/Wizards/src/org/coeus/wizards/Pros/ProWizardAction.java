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
package org.coeus.wizards.Pros;

import org.coeus.wizards.*;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.LinkedList;
import javax.swing.JComponent;
import org.coeus.wizards._HelpFuntions.copyList;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.util.ImageUtilities;


// An example action demonstrating how the wizard could be called from within
// your code. You can copy-paste the code below wherever you need.
public final class ProWizardAction implements ActionListener {

    private static String proName;


    private String proParamNum;
    private boolean created=false;
    private boolean update=false;
    private String [] attributes;
    private String info1,info2;
    private String dispScope;
    private String objScope;
    private static LinkedList<String> dispParameters=null;
    private static LinkedList<String> dispParametersTypes=null;
    private  LinkedList<String> copy_dispParameters=null;
    private  LinkedList<String> copy_dispParametersTypes=null;

    private String info3="";

    private WizardDescriptor.ValidatingPanel[] panels;

    public ProWizardAction (boolean updateState,String [] inattributes)
    {this.update=updateState;
     this.attributes=inattributes;
     dispParametersTypes=new LinkedList<String>();
     dispParameters=new LinkedList<String>();
     copy_dispParametersTypes=new LinkedList<String>();
     copy_dispParameters=new LinkedList<String>();
}

    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        if (this.update)//Update
        {  wizardDescriptor.setTitle("Updating Procedure Properties");
           wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
           wizardDescriptor.putProperty(WizardSettingsValues.PRO_NAME, attributes[0]);

           new copyList(dispParametersTypes,copy_dispParametersTypes);
           new copyList(dispParameters,copy_dispParameters);

           info1=Messages.INFO_PRO1b;
           info2=Messages.INFO_PRO5b;
        }
        else//Create
        { wizardDescriptor.setTitle("Procedure Declaration Wizard");
          wizardDescriptor.putProperty(WizardSettingsValues.UPDATE, Boolean.valueOf(update));
          info1=Messages.INFO_PRO1a;
          info2=Messages.INFO_PRO5a;
        }
        wizardDescriptor.putProperty("WizardPanel_image", ImageUtilities.loadImage("org/coeus/wizards/icons4wizards/Wiz_Proc.png", true));
        //wizardDescriptor.putProperty("WizardPanel_infoMessage", Messages.INFO_Panel1);
       wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_PRO_PANEL1);
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {

            this.proName = (String)wizardDescriptor.getProperty(WizardSettingsValues.PRO_NAME);
            this.proParamNum = Integer.toString(dispParameters.size());

             if (dispParameters.size()==0)
                info3= Messages.INFO_PRO4 ;
            else
                info3=Messages.INFO_PRO3;

            this.proName = this.proName.toUpperCase();

            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(info1 +
             Messages.INFO_PRO2+ this.proName + info3 + this.getDispParams2Show()+info2));

            this.created=true;
        }
        else
        {
           new copyList(copy_dispParametersTypes,dispParametersTypes);
           new copyList(copy_dispParameters,dispParameters);
        }

    }

    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.ValidatingPanel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.ValidatingPanel[]{
                        new ProWizardPanel1(),
                        new ProWizardPanel2()
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
                      jc.putClientProperty(WizardDescriptor.PROP_INFO_MESSAGE,Messages.INFO_CON_PANEL1);

                }
            }
        }
        return panels;
    }



public String  getDispParams2Show()
 { String show="";

      if (!dispParameters.isEmpty())
      {   show="\n"+dispParametersTypes.get(0)+" "+dispParameters.get(0);
          for (int i=1;i<dispParameters.size();i++)
             show=show+",\n"+dispParametersTypes.get(i)+" "+dispParameters.get(i);
          show=show+"\n";
      }
     return show;
}

    public String getName() {
        return "Start Sample Wizard";
    }


    public static String getProName()
    {
     return proName;
    }



     public String getProParamNum()
    {
      return Integer.toString(dispParameters.size());
    }

    public static LinkedList<String> getProDispParametersTypes()
    {
     return ProWizardAction.dispParametersTypes;
    }


     public static LinkedList<String> getProDispParameters()
    {
     return ProWizardAction.dispParameters;
    }

    public static void setProDispParametersTypes(LinkedList<String>  inDispParamTypes)
    {ProWizardAction.dispParametersTypes=inDispParamTypes;}


    public static void setProDispParameters(LinkedList<String>  inDispParam)
    {ProWizardAction.dispParameters=inDispParam;}

    public boolean getCreated()
    {
     return this.created;
    }


    public static void setProName(String iProName)
    {
      proName=iProName;
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
