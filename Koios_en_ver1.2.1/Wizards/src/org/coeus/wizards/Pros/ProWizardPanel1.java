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

import java.awt.Component;
import javax.swing.event.ChangeListener;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards.Messages;
import org.coeus.wizards.ReservedWords;
import org.coeus.wizards.WizardSettingsValues;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class ProWizardPanel1 implements WizardDescriptor.ValidatingPanel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private ProVisualPanel1 component;
    private WizardDescriptor wizardDescriptor;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent() {
        if (component == null) {
            component = new ProVisualPanel1();
        }
        return component;
    }

    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
    // If you have context help:
    // return new HelpCtx(SampleWizardPanel1.class);
    }

    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return true;
    // If it depends on some condition (form filled out...), then:
    // return someCondition();
    // and when this condition changes (last form field filled in...) then:
    // fireChangeEvent();
    // and uncomment the complicated stuff below.
    }

    public final void addChangeListener(ChangeListener l) {
    }

    public final void removeChangeListener(ChangeListener l) {
    }
    /*
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0
    public final void addChangeListener(ChangeListener l) {
    synchronized (listeners) {
    listeners.add(l);
    }
    }
    public final void removeChangeListener(ChangeListener l) {
    synchronized (listeners) {
    listeners.remove(l);
    }
    }
    protected final void fireChangeEvent() {
    Iterator<ChangeListener> it;
    synchronized (listeners) {
    it = new HashSet<ChangeListener>(listeners).iterator();
    }
    ChangeEvent ev = new ChangeEvent(this);
    while (it.hasNext()) {
    it.next().stateChanged(ev);
    }
    }
     */

    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    public void readSettings(Object settings) {

      boolean update;
      String proname;
   
      wizardDescriptor = (WizardDescriptor) settings;

      update = (Boolean)wizardDescriptor.getProperty(WizardSettingsValues.UPDATE);  
      wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_PRO_PANEL1);

      if(update)
      {
      proname= (String)wizardDescriptor.getProperty(WizardSettingsValues.PRO_NAME);
      component.getNameTextField().setText(proname);
      }


    }

    public void storeSettings(Object settings) {

       String prop_str1;

        wizardDescriptor = (WizardDescriptor) settings;

        ProWizardAction.setProName(ProVisualPanel1.getProName());
        prop_str1 = ProVisualPanel1.getProName();
        wizardDescriptor.putProperty(WizardSettingsValues.PRO_NAME, prop_str1);

    }




    public void validate() throws WizardValidationException {
        String proname = ProVisualPanel1.getProName();
        AllObjects type = null;
        String dispScope= WizardsDefinitions.PROCEDURE;//+": "+proname;

////EMPTY NAME
        if (proname.equalsIgnoreCase("")) {

            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_PRO_NAME_BL1 + "\n" + Messages.ERR_PRO_NAME_BL2));
            throw new WizardValidationException(component.getNameTextField(), Messages.ERR_PRO_NAME_BL1, null);

        }

/////ONLY LETTERS,NUMS AND _
      for (int q = 0; q < proname.length(); q++) {


          if ((q == 0) && (!(proname.charAt(q) >= 65 && proname.charAt(q) <= 90) && //A-Z
                    !(proname.charAt(q) >= 97 && proname.charAt(q) <= 122) && //a-z
                    !(proname.charAt(q) >= 913 && proname.charAt(q) <= 937) && //Α-Ω
                    !(proname.charAt(q) >= 945 && proname.charAt(q) <= 969) &&//α-ω
                    !(proname.charAt(q) == 36) &&//$
                    !(proname.charAt(q) == 95)))//_
            {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_PRO_NAME_LET1 +
                        "\n" + Messages.ERR_PRO_NAME_LET2));
                throw new WizardValidationException(component.getNameTextField(), Messages.ERR_PRO_NAME_LET1, null);
            }
            else if (!(proname.charAt(q) >= 65 && proname.charAt(q) <= 90) && //A-Z
                    !(proname.charAt(q) >= 97 && proname.charAt(q) <= 122) && //a-z
                    !(proname.charAt(q) >= 913 && proname.charAt(q) <= 937) && //Α-Ω
                    !(proname.charAt(q) >= 945 && proname.charAt(q) <= 969) &&//α-ω
                    !(proname.charAt(q) >= 48 && proname.charAt(q) <= 57) && //0-9
                    !(proname.charAt(q) == 36) &&//$
                    !(proname.charAt(q) == 95))//_
            { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_PRO_NAME_LET3 +
                        "\n" + Messages.ERR_PRO_NAME_LET2));
                throw new WizardValidationException(component.getNameTextField(), Messages.ERR_PRO_NAME_LET3, null);}

      }

////CHECK IF RESERVED WORD
        if (ReservedWords.CheckReservedWord(proname))
        {DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_PRO_NAME_RES +
                        "\n" + Messages.ERR_PRO_NAME_LET2));
                throw new WizardValidationException(component.getNameTextField(), Messages.ERR_PRO_NAME_RES, null);}

////CHECK IF IDENTIFIER ALREADY IN USE
    AllObjectsList caol = AllObjectsList.getAllObjList();
   // type =caol.SearchByDisplayName_DispScope(proname,dispScope);
   type =caol.SearchByDisplayName_4FunctionsOrProcedures(proname);

 if( (caol!=null) && (type!=null) )
    { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_PRO_NAME_USE + type.getDispCateg()+
               " " +type.getDispName()+"\n" +Messages.ERR_PRO_NAME_USE1+ Messages.ERR_PRO_NAME_LET2));
              throw new WizardValidationException(component.getNameTextField(), Messages.ERR_CON_NAME_USE + type.getDispCateg()+ " " +type.getDispName(), null);}


    }
}

