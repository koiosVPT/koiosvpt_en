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
package org.coeus.wizards.FuncCall;

import java.awt.Component;
import javax.swing.event.ChangeListener;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards._HelpFuntions.copyList;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class FuncCallWizardPanel2 implements WizardDescriptor.ValidatingPanel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private FuncCallVisualPanel2 component;
 //   private WizardDescriptor wizardDescriptor;
    private String objScope=null;
    private FuncCallWizardAction fcwa=null;
 

    public FuncCallWizardPanel2(String iObjScope,FuncCallWizardAction ifcwa)
    {
      this.objScope=iObjScope;
      this.fcwa=ifcwa;
    }

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent() {
        if (component == null) {
            component = new FuncCallVisualPanel2(this.objScope,WizardsDefinitions.FUNC_CALL,fcwa);
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
        component.getDispParameterActual().clear();
        component.getObjParameterActual().clear();
        component.getParameterObjType().clear();
        component.getParameterTypical().clear();
        component.getListModel().clear();


//          if (!FuncCallWizardPanel1.previousFunctionName.equalsIgnoreCase(fcwa.getDispProcedureName()))
//        {
            NotifyDescriptor d=null;
            if (fcwa.getTypicalParameters().size()==0)
            {
              d =new NotifyDescriptor.Message("FUNCTION "+fcwa.getDispFunctionName()+" does not take any parameters!\n"
                      +"Press \"Finish\" to continue!",NotifyDescriptor.INFORMATION_MESSAGE);
            }
            else
            {
              String s="";
              int l=0;
              for (String s1:fcwa.getDispActualParametersType())
              {
               s=s+s1+" "+fcwa.getTypicalParameters().get(l)+"\n";
               l++;
              }


              d =new NotifyDescriptor.Message("FUNCTION "+fcwa.getDispFunctionName()+" takes the following "
                      +fcwa.getTypicalParameters().size()+" formal parameter(s):\n"+s
                      +"In the next step, you will define the "+fcwa.getTypicalParameters().size()+
                      " actual parameters, which will replace these formal parameters during function call!\n",NotifyDescriptor.INFORMATION_MESSAGE);
            }

            DialogDisplayer.getDefault().notify(d);
            component.getActualParameters();
//        }
//        else if (!fcwa.getTypicalParameters().isEmpty())
//        {
//        new copyList(fcwa.getDispActualParameters(),component.getDispParameterActual());
//        new copyList(fcwa.getObjActualParameters(),component.getObjParameterActual());
//       // new copyList(CallWizardAction.getDispActualParametersType(),component.get);
//        new copyList(fcwa.getObjActualParametersType(),component.getParameterObjType());
//        new copyList(fcwa.getTypicalParameters(),component.getParameterTypical());
//
//          DefaultListModel listModel=component.getListModel();
//
//        for (int q=0;q<component.getDispParameterActual().size();q++)
//
//             listModel.addElement(component.strings2ListModelItem(fcwa.getDispActualParametersType().get(q),
//                     component.getParameterTypical().get(q),component.getDispParameterActual().get(q)));
//
//        }

    component.getLabelProcName().setText(fcwa.getDispFunctionName());

    }

    public void storeSettings(Object settings) {
        new copyList(component.getDispParameterActual(),fcwa.getDispActualParameters());
        new copyList(component.getObjParameterActual(),fcwa.getObjActualParameters());
    }

    public void validate() throws WizardValidationException {
        
    }
}

