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


package org.coeus.wizards.For;

import java.awt.Component;
import javax.swing.DefaultListModel;
import javax.swing.event.ChangeListener;
import org.coeus.wizards.Messages;
import org.coeus.wizards.WizardSettingsValues;
import org.coeus.wizards._HelpFuntions.copyList;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class ForWizardPanel1 implements WizardDescriptor.ValidatingPanel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private ForVisualPanel1 component;
    private String objScope,commandName;
    private WizardDescriptor wizardDescriptor;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.

    public ForWizardPanel1(String iObjScope,String icommandName)
    {this.objScope=iObjScope;
    this.commandName=icommandName;}

    public Component getComponent() {
        if (component == null) {
            component = new ForVisualPanel1(this.objScope,this.commandName);
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
      wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_PRO_PANEL2);

      update = (Boolean)wizardDescriptor.getProperty(WizardSettingsValues.UPDATE);
      if (update)
      {
            DefaultListModel listModel=component.getListModel();

             listModel.addElement(component.strings2ListModelItem(ForWizardAction.getDispStatementCategory().get(0),
                     ForWizardAction.getDispStatement().get(0)));
             listModel.addElement(component.strings2ListModelItem(ForWizardAction.getDispStatementCategory().get(1),
                     ForWizardAction.getDispStatement().get(1)));
             listModel.addElement(component.strings2ListModelItem(ForWizardAction.getDispStatementCategory().get(2),
                     ForWizardAction.getDispStatement().get(2)));


             component.setDispName(ForWizardAction.getDispName());
             component.setObjName(ForWizardAction.getObjName());
             component.setDispType(ForWizardAction.getDispType());
             component.setObjType(ForWizardAction.getObjType());
             component.setDispCategory(ForWizardAction.getDispCategory());
             new copyList(ForWizardAction.getDispStatement(), component.getDispStatement());
             new copyList(ForWizardAction.getObjStatement(),component.getObjStatement());
             new copyList(ForWizardAction.getDispStatementCategory(),component.getDispStatementCategory());


             component.getCategoryLabel().setText(ForWizardAction.getDispType()+" "+ForWizardAction.getDispCategory());
             component.getNameLabel().setText(ForWizardAction.getDispName());
      }

    }


    public void storeSettings(Object settings) {
    
       ForWizardAction.setDispName(component.getDispName());
       ForWizardAction.setObjName(component.getObjName());
       ForWizardAction.setDispType(component.getDispType());
       ForWizardAction.setObjType(component.getObjType());
       ForWizardAction.setDispCategory(component.getDispCategory());
      
        new copyList(component.getDispStatement(),ForWizardAction.getDispStatement() );
        new copyList(component.getObjStatement(),ForWizardAction.getObjStatement());
        new copyList(component.getDispStatementCategory(),ForWizardAction.getDispStatementCategory());

          
    }

    public void validate() throws WizardValidationException {
      
    }
}

