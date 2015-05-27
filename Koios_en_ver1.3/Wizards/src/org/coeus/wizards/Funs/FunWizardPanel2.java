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

package org.coeus.wizards.Funs;

import java.awt.Component;
import javax.swing.DefaultListModel;
import javax.swing.event.ChangeListener;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;
import org.coeus.wizards.Messages;
import org.coeus.wizards.ReservedWords;
import org.coeus.wizards.WizardSettingsValues;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class FunWizardPanel2 implements WizardDescriptor.ValidatingPanel{

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private FunVisualPanel2 component;
    private WizardDescriptor wizardDescriptor;
    private static final int stringListSize=35;



    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent() {
        
        if (component == null) {
            component = new FunVisualPanel2();
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
      wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_FUN_PANEL2);

      component.getLabelTitle().setText(component.getLabelTitle().getText()+" "+FunWizardAction.getFunName().toUpperCase());

      update = (Boolean)wizardDescriptor.getProperty(WizardSettingsValues.UPDATE);
      if (update && !FunWizardAction.getFunDispParameters().isEmpty())
      {
            DefaultListModel listModel=component.getListModel();
        
        for (int q=0;q<FunWizardAction.getFunDispParameters().size();q++)

             listModel.addElement(strings2ListModelItem(FunWizardAction.getFunDispParametersTypes().get(q),
                     FunWizardAction.getFunDispParameters().get(q)));

       
             component.setDispArguements(FunWizardAction.getFunDispParameters());
             component.setDispArguementsType(FunWizardAction.getFunDispParametersTypes());
      }
    }


private String strings2ListModelItem(String type,String name)
  { String result="",temp="";

    int difference = stringListSize- type.length();
    for (int i=0;i<difference;i++)
        temp=temp+" ";
    result=type+temp+name;
    return result;
   }



    public void storeSettings(Object settings) {

      FunWizardAction.setFunDispParameters(component.getDispArguements());
      FunWizardAction.setFunDispParametersTypes(component.getDispArguementsType());

    }

    public void validate() throws WizardValidationException {
        String s;
        String [] param_names = new String [component.getDispArguements().size()];

         if (!component.getDispArguements().isEmpty())
         {
          for (int w=0;w<component.getDispArguements().size();w++)
          {
              s=component.getDispArguements().get(w);
              int w1=w+1;
              /////EMPTY ARGUEMENT
              if (s.equalsIgnoreCase("")) {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_EM1 + "\n" + 
                    Messages.ERR_FUN_ARGUEMENT_EM2+" Parameter no"+w1+"!"));
            component.getList().setSelectedIndex(component.getDispArguements().indexOf(s));
            throw new WizardValidationException(component.getList(),Messages.ERR_FUN_ARGUEMENT_EM1, null); }

              /////ONLY LETTERS,NUMS AND _
              for (int q = 0; q < s.length(); q++) {


                  if ((q == 0) && (!(s.charAt(q) >= 65 && s.charAt(q) <= 90) && //A-Z
                            !(s.charAt(q) >= 97 && s.charAt(q) <= 122) && //a-z
                            !(s.charAt(q) >= 913 && s.charAt(q) <= 937) && //Α-Ω
                            !(s.charAt(q) >= 945 && s.charAt(q) <= 969) &&//α-ω
                            !(s.charAt(q) == 36) &&//$
                            !(s.charAt(q) == 95)))//_
                    {
                        DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_LET1 +
                                "\n" + Messages.ERR_FUN_ARGUEMENT_LET2a+" "+s+" (Parameter no"+w1+")"+Messages.ERR_FUN_ARGUEMENT_LET2b));
                            component.getList().setSelectedIndex(component.getDispArguements().lastIndexOf(s));
                        throw new WizardValidationException(component.getList(), Messages.ERR_FUN_ARGUEMENT_LET1, null);
                    }
                    else if (!(s.charAt(q) >= 65 && s.charAt(q) <= 90) && //A-Z
                            !(s.charAt(q) >= 97 && s.charAt(q) <= 122) && //a-z
                            !(s.charAt(q) >= 913 && s.charAt(q) <= 937) && //Α-Ω
                            !(s.charAt(q) >= 945 && s.charAt(q) <= 969) &&//α-ω
                            !(s.charAt(q) >= 48 && s.charAt(q) <= 57) && //0-9
                            !(s.charAt(q) == 36) &&//$
                            !(s.charAt(q) == 95))//_
                    { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_LET3 +
                                "\n" + Messages.ERR_FUN_ARGUEMENT_LET2a+" "+s+" (Parameter no"+w1+")"+Messages.ERR_FUN_ARGUEMENT_LET2b));
                            component.getList().setSelectedIndex(component.getDispArguements().lastIndexOf(s));
                        throw new WizardValidationException(component.getList(), Messages.ERR_FUN_ARGUEMENT_LET3, null);}

              }

            ////CHECK IF RESERVED WORD
                    if (ReservedWords.CheckReservedWord(s))
                    {DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_RES +
                                    "\n" + Messages.ERR_FUN_ARGUEMENT_LET2a+" "+s+" (Parameter no"+w1+")"+Messages.ERR_FUN_ARGUEMENT_LET2b));
                            component.getList().setSelectedIndex(component.getDispArguements().lastIndexOf(s));
                            throw new WizardValidationException(component.getList(), Messages.ERR_FUN_ARGUEMENT_RES, null);}

            ////CHECK IF IDENTIFIER ALREADY IN USE IN THIS FUNCTION
                AllObjectsList caol = AllObjectsList.getAllObjList();
                AllObjects type =caol.SearchByDisplayName_DispScope(s,WizardsDefinitions.FUNCTION+" "+FunVisualPanel1.getFunName());
             

                 if( (caol!=null) && (type!=null) )
                { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_USE 
                 + type.getDispCateg()+" " +type.getDispName()+" in the same function!\n"
                 + Messages.ERR_FUN_ARGUEMENT_LET2a+" "+s+" (Parameter no"+w1+")"+Messages.ERR_FUN_ARGUEMENT_LET2b));
                    component.getList().setSelectedIndex(component.getDispArguements().lastIndexOf(s));
              throw new WizardValidationException(component.getList(), Messages.ERR_FUN_ARGUEMENT_USE + type.getDispCateg()+ " " +type.getDispName(), null);}

                
            ////CHECK IF IDENTIFIER ALREADY IN USE AS A GLOBAL OBJECT
             type =caol.SearchByDisplayName_DispScope(s,WizardsDefinitions.GLOBAL);


                 if( (caol!=null) && (type!=null) )
                { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_USE
                 + type.getDispCateg()+" " +type.getDispName()+" with scope: "+WizardsDefinitions.GLOBAL+"!\n"
                 + Messages.ERR_FUN_ARGUEMENT_LET2a+" "+s+" (Parameter no"+w1+")"+Messages.ERR_FUN_ARGUEMENT_LET2b));
                            component.getList().setSelectedIndex(component.getDispArguements().lastIndexOf(s));
              throw new WizardValidationException(component.getList(), Messages.ERR_FUN_ARGUEMENT_USE + type.getDispCateg()+ " " +type.getDispName(), null);}




                /////Check if parameter has the same name with the function
                if (s.equalsIgnoreCase(FunVisualPanel1.getFunName()))
                { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_USE1 +
                           "\n" + Messages.ERR_FUN_ARGUEMENT_LET2a+" "+s+" (Parameter no"+w1+")"+Messages.ERR_FUN_ARGUEMENT_LET2b));
                            component.getList().setSelectedIndex(component.getDispArguements().lastIndexOf(s));
                          throw new WizardValidationException(component.getList(), Messages.ERR_FUN_ARGUEMENT_USE1, null);
                }

               /////check if parameter has the same name with previous parameter
                for (int j=0;j<w;j++)
                {
                     if (s.equalsIgnoreCase(param_names[j]))
                        { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_USE2 + "Parameter no"+(j+1)+"."+
                                   "\n" + Messages.ERR_FUN_ARGUEMENT_LET2a+" "+s+" (Parameter no"+w1+")"+Messages.ERR_FUN_ARGUEMENT_LET2b));
                            component.getList().setSelectedIndex(component.getDispArguements().lastIndexOf(s));
                                  throw new WizardValidationException(component.getList(), Messages.ERR_FUN_ARGUEMENT_USE2 + "Parameter no"+(j+1)+".", null);
                        }
                }

                param_names[w]=s;
          }

        }        
    }


}

