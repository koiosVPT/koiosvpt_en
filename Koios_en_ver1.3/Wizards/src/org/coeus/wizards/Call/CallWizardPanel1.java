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
import javax.swing.event.ChangeListener;
import org.coeus.wizards.Messages;
import org.coeus.wizards.WizardSettingsValues;
import org.coeus.wizards._HelpFuntions.copyList;
import org.coeus.wizards._HelpFuntions.getProceduresList;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class CallWizardPanel1 implements WizardDescriptor.ValidatingPanel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private CallVisualPanel1 component;
    private String objScope;
    private String[] procDispNames=null;
    private  getProceduresList gPL=null;
    private WizardDescriptor wizardDescriptor;
    public static String previousProcedureName=null;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.

    public CallWizardPanel1(String iObjScope)
    {this.objScope=iObjScope;}


    public Component getComponent() {
        if (component == null) {
            component = new CallVisualPanel1();

         gPL = new getProceduresList();
         procDispNames=gPL.getDisplayNames();

         component.getProceduresComboBox().removeAllItems();
         for (String s:procDispNames)
             component.getProceduresComboBox().addItem(s);
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
       
      wizardDescriptor = (WizardDescriptor) settings;

      update = (Boolean)wizardDescriptor.getProperty(WizardSettingsValues.UPDATE);

      //wizardDescriptor.putProperty("WizardPanel_infoMessage", Messages.INFO_Panel1);
      wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_CALL_PANEL1);

      if(update)
      {
           previousProcedureName=CallWizardAction.getDispProcedureName();
           component.getProceduresComboBox().setSelectedItem( CallWizardAction.getDispProcedureName() );
      }
    }

    public void storeSettings(Object settings) {
        int s= component.getProceduresComboBox().getSelectedIndex();
        CallWizardAction.setDispProcedureName(new String(gPL.getDisplayNames()[s]));
        CallWizardAction.setObjProcedureName(new String(gPL.getObjectNames()[s]));
        new copyList(gPL.getDispParams().get(s),CallWizardAction.getTypicalParameters());
        new copyList(gPL.getDispParamsTypes().get(s),CallWizardAction.getDispActualParametersType());
        new copyList(gPL.getObjParamsType().get(s),CallWizardAction.getObjActualParametersType());

      //  new printLists(CallWizardAction.getTypicalParameters(),
      //    CallWizardAction.getDispActualParametersType(),
      //    CallWizardAction.getObjActualParametersType() );
    }

    public void validate() throws WizardValidationException {
    
    }
}

