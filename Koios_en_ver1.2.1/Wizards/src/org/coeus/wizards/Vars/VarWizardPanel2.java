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

import java.awt.BorderLayout;
import org.coeus.wizards.*;
import java.awt.Component;
import javax.swing.event.ChangeListener;
import org.coeus.wizards.TextActions.GetFocus;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;


public class VarWizardPanel2 implements WizardDescriptor.ValidatingPanel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private int vp;
    private WizardDescriptor wizardDescriptor;
    private VarVisualPanel2a componenta;
    private VarVisualPanel2b componentb;
    private VarVisualPanel2c componentc;
    private VarVisualPanel2d componentd;
    private VarVisualPanel2e componente;
    private float float_value;

    private VarVisualPanel2 base;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
   /* public Component getComponent() {
    if (component == null) {
    component = new ConVisualPanel2a();
    }
    return component;
    }*/



   public Component getComponent() {

       if (base == null) {
    base = new VarVisualPanel2();
    }

      if (componenta==null)
      {componenta = new VarVisualPanel2a();
       componentb = new VarVisualPanel2b();
       componentc = new VarVisualPanel2c();
       componentd = new VarVisualPanel2d();
       componente = new VarVisualPanel2e();
      }


       if (VarVisualPanel1.getConType().equalsIgnoreCase(WizardsDefinitions.INT1)) {
       base.removeAll();
       base.add(componenta,BorderLayout.NORTH);
       componenta.jLabel3.setText(VarVisualPanel1.getConName());
       new GetFocus(componenta.getTextField());
       vp=0;
       }else if (VarVisualPanel1.getConType().equalsIgnoreCase(WizardsDefinitions.FLOAT1)) {
       base.removeAll();
       base.add(componentb,BorderLayout.NORTH);
       componentb.jLabel3.setText(VarVisualPanel1.getConName());
       new GetFocus(componentb.jTextField1);
       vp = 1;
       }else if (VarVisualPanel1.getConType().equalsIgnoreCase(WizardsDefinitions.CHAR1)) {
       base.removeAll();
       base.add(componentc,BorderLayout.NORTH);
       componentc.jLabel3.setText(VarVisualPanel1.getConName());
       new GetFocus(componentc.getTextFiled());
       vp = 2;
       } else if (VarVisualPanel1.getConType().equalsIgnoreCase(WizardsDefinitions.BOOLEAN1)) {
       base.removeAll();
       base.add(componentd,BorderLayout.NORTH);
       componentd.jLabel3.setText(VarVisualPanel1.getConName());
       new GetFocus(componentd.getComboBox());
       vp = 3;
       }else if (VarVisualPanel1.getConType().equalsIgnoreCase(WizardsDefinitions.STRING1)) {
       base.removeAll();
       base.add(componente,BorderLayout.NORTH);
       componente.jLabel3.setText(VarVisualPanel1.getConName());
       new GetFocus(componente.getTextFiled());
       vp = 4;
       }
    return base;

    }

   public static String getName() {
        return "Value Initialisation";
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
        String convalue,lasttype;
        int vt=-1;
        wizardDescriptor = (WizardDescriptor) settings;

        wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_VAR_PANEL2);


        if (VarVisualPanel1.getConType().equalsIgnoreCase(WizardsDefinitions.INT1)) {
              vp=0;
        } else  if (VarVisualPanel1.getConType().equalsIgnoreCase(WizardsDefinitions.FLOAT1)) {
             vp = 1;
        }else if (VarVisualPanel1.getConType().equalsIgnoreCase(WizardsDefinitions.CHAR1)) {
             vp = 2;
        } else if (VarVisualPanel1.getConType().equalsIgnoreCase(WizardsDefinitions.BOOLEAN1)) {
             vp = 3;
        }else if (VarVisualPanel1.getConType().equalsIgnoreCase(WizardsDefinitions.STRING1)) {
             vp = 4;
        }

        update = (Boolean) wizardDescriptor.getProperty(WizardSettingsValues.UPDATE);
        convalue = (String) wizardDescriptor.getProperty(WizardSettingsValues.VAR_VALUE);
        lasttype =(String) wizardDescriptor.getProperty(WizardSettingsValues.VAR_LAST_TYPE);
        
        if(update && !convalue.isEmpty())
        {
        if (lasttype.equalsIgnoreCase(WizardsDefinitions.INT1)) {
             vt=0;
             componenta.jRadioButton2.setSelected(true);
             componenta.setMyCompEnabled(true);
        } else if (lasttype.equalsIgnoreCase(WizardsDefinitions.FLOAT1)) {
             vt = 1;
             componentb.jRadioButton2.setSelected(true);
             componentb.setMyCompEnabled(true);
        }else if (lasttype.equalsIgnoreCase(WizardsDefinitions.CHAR1)) {
             vt = 2;
             componentc.jRadioButton2.setSelected(true);
             componentc.setMyCompEnabled(true);
        } else if (lasttype.equalsIgnoreCase(WizardsDefinitions.BOOLEAN1)) {
             vt = 3;
             componentd.jRadioButton2.setSelected(true);
             componentd.setMyCompEnabled(true);
        }else if (lasttype.equalsIgnoreCase(WizardsDefinitions.STRING1)) {
             vt = 4;
             componente.jRadioButton2.setSelected(true);
             componente.setMyCompEnabled(true);
        }
        }


        if (update && vp==vt && !convalue.isEmpty()) {
            if (vp == 0) {
                if (convalue.startsWith("-")) {
                    componenta.getComboBox().setSelectedItem("-");
                    componenta.getTextField().setText(convalue.substring(1));
                } else {
                    componenta.getTextField().setText(convalue);
                }

            } else if (vp == 1) {
                int point_pos = convalue.indexOf(".");

                if (convalue.startsWith("-")) {
                    componentb.getComboBox().setSelectedItem("-");
                    componentb.getTextFiled1().setText(convalue.substring(1, point_pos));
                    componentb.getTextFiled2().setText(convalue.substring(point_pos + 1));
                } else {
                    componentb.getTextFiled1().setText(convalue.substring(0, point_pos));
                    componentb.getTextFiled2().setText(convalue.substring(point_pos + 1));
                }

            } else if (vp == 2) {
                componentc.getTextFiled().setText(convalue);
            } else if (vp == 3) {
                componentd.getComboBox().setSelectedItem(convalue);
            }else if (vp == 4) {
                componente.getTextFiled().setText(convalue);
            }

        }



    }

    public void storeSettings(Object settings) {
        String prop_str="";
        wizardDescriptor = (WizardDescriptor) settings;


        if (vp == 0 && componenta.jRadioButton2.isSelected()) {
            prop_str = componenta.getValue();
        } else if (vp == 1 && componentb.jRadioButton2.isSelected()) {
            prop_str = Float.toString(float_value);
        } else if (vp == 2 && componentc.jRadioButton2.isSelected()) {
            prop_str = componentc.getValue();
        } else if (vp == 3 && componentd.jRadioButton2.isSelected()) {
             prop_str = componentd.getValue();
        }else if (vp == 4 && componente.jRadioButton2.isSelected()) {
            prop_str = componente.getValue();
        }
        
       
        wizardDescriptor.putProperty(WizardSettingsValues.VAR_VALUE, prop_str);
        }

    public void validate() throws WizardValidationException {
        boolean initVal=false;
 
        
        
                if (vp == 0) {//INTEGER
            String val = componenta.getIntValue();
            initVal=componenta.jRadioButton2.isSelected();
            
             if(initVal)
              {
            /////EMPTY VALUE
            if (val.equalsIgnoreCase("")&& initVal) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        Messages.ERR_VAR_VALUE_EM1 + "\n" + Messages.ERR_VAR_VALUE_EM2));
                throw new WizardValidationException(componenta.getTextField(), Messages.ERR_VAR_VALUE_EM1, null);
            }

            ////ONLY NYMBERS
            for (int q = 0; q < val.length(); q++) {
                if (!(val.charAt(q) >= 48 && val.charAt(q) <= 57)) {
                    DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                            Messages.ERR_VAR_VALUE_NUM1 + "\n" + Messages.ERR_VAR_VALUE_NUM2));
                    throw new WizardValidationException(componenta.getTextField(), Messages.ERR_VAR_VALUE_NUM1, null);
                }
            }

            /////MIN-MAX NUMBERS -2147483648 ,+2147483647
            try {
                Integer.parseInt(componenta.getValue());
            } catch (NumberFormatException nfe) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        Messages.ERR_VAR_VALUE_OOL1 + "\n" + Messages.ERR_VAR_VALUE_NUM2));
                throw new WizardValidationException(componenta.getTextField(), Messages.ERR_VAR_VALUE_OOL1, null);
            }

        } 
        } else if (vp == 1) {//FLOAT
            String val = componentb.getIntValue();
            initVal=componentb.jRadioButton2.isSelected();

            if(initVal)
              {
            //EMPTY VALUE
            if (val.equalsIgnoreCase("") && initVal) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        Messages.ERR_VAR_VALUE_EM1 + "\n" + Messages.ERR_VAR_VALUE_EM2));
                throw new WizardValidationException(componentb.jTextField1, Messages.ERR_VAR_VALUE_EM1, null);
            }

            //ONLY NUMBERS
            for (int q = 0; q < val.length(); q++) {
                if (!(val.charAt(q) >= 48 && val.charAt(q) <= 57)) {
                    DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                            Messages.ERR_VAR_VALUE_OOL2 + "\n" + Messages.ERR_VAR_VALUE_NUM2));
                    throw new WizardValidationException(componentb.jTextField1, Messages.ERR_VAR_VALUE_OOL2, null);
                }
            }

            String val1 = componentb.getDecValue();
            for (int q = 0; q < val1.length(); q++) {
                if (!(val1.charAt(q) >= 48 && val1.charAt(q) <= 57)) {
                    DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                            Messages.ERR_VAR_VALUE_OOL3 + "\n" + Messages.ERR_VAR_VALUE_NUM2));
                    throw new WizardValidationException(componentb.jTextField2, Messages.ERR_VAR_VALUE_OOL3, null);
                }
            }

            //MIN MAX VALUE

            try {
                float_value = Float.parseFloat(componentb.getValue());
            } catch (NumberFormatException nfe) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        Messages.ERR_VAR_VALUE_OOL5 + "\n" + Messages.ERR_VAR_VALUE_NUM2));
                throw new WizardValidationException(componentb.jTextField1, Messages.ERR_VAR_VALUE_OOL5, null);
            }

          }
        } else if (vp == 2) {//CHARACTER

            String val = componentc.getValue();
            initVal=componentc.jRadioButton2.isSelected();

            if(initVal)
              {
            //EMPTY VALUE
            if (val.equalsIgnoreCase("") && initVal) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        Messages.ERR_VAR_VALUE_EM1 + "\n" + Messages.ERR_VAR_VALUE_EM2));
                throw new WizardValidationException(componentc.getTextFiled(), Messages.ERR_VAR_VALUE_EM1, null);
            }


            ///JUST ONE CHARACTER
            if ((val.length() >= 2)) {// || (!(val.charAt(0) >= 48 && val.charAt(0) <= 57))) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        Messages.ERR_VAR_VALUE_OOL4 + "\n" + Messages.ERR_VAR_VALUE_OOL4A+val.length()+Messages.ERR_VAR_VALUE_OOL4B+".\n"
                        + Messages.ERR_VAR_VALUE_NUM2));
                throw new WizardValidationException(componentc.getTextFiled(), Messages.ERR_VAR_VALUE_OOL4, null);
            }
            }
        } else if (vp == 3) {//BOOLEAN

        }else if (vp == 4) {//STRING

            String val = componente.getValue();
            initVal=componente.jRadioButton2.isSelected();

            if(initVal)
              {
            //EMPTY VALUE
            if (val.equalsIgnoreCase("") && initVal) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        Messages.ERR_VAR_VALUE_EM1 + "\n" + Messages.ERR_VAR_VALUE_EM2));
                throw new WizardValidationException(componente.getTextFiled(), Messages.ERR_VAR_VALUE_EM1, null);
            }
          }
        }
    }
}

