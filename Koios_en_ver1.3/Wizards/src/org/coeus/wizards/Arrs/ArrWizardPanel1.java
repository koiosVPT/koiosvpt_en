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

package org.coeus.wizards.Arrs;

import java.awt.Component;
import java.util.LinkedList;
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

public class ArrWizardPanel1 implements WizardDescriptor.ValidatingPanel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private ArrVisualPanel1 component;
    private WizardDescriptor wizardDescriptor;
    private String dispScope;
    private String objScope;

    public ArrWizardPanel1(String idispScope,String iobjScope)
    {this.dispScope=idispScope;this.objScope=iobjScope;    }

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent() {
        if (component == null) {
            component = new ArrVisualPanel1();
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
      String arrname,arrtype,arrdims,arrdim1,arrdim2;
      wizardDescriptor = (WizardDescriptor) settings;

      update = (Boolean)wizardDescriptor.getProperty(WizardSettingsValues.UPDATE);
      wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_ARR_PANEL1);

      if(update)
      {
      arrname= (String)wizardDescriptor.getProperty(WizardSettingsValues.ARR_NAME);
      arrtype= (String)wizardDescriptor.getProperty(WizardSettingsValues.ARR_TYPE);
      arrdims= (String)wizardDescriptor.getProperty(WizardSettingsValues.ARR_DIMS);
      arrdim1= (String)wizardDescriptor.getProperty(WizardSettingsValues.ARR_DIM1);
      arrdim2= (String)wizardDescriptor.getProperty(WizardSettingsValues.ARR_DIM2);
      
      component.getNameTextField().setText(arrname);
      component.getTypeComboBox().setSelectedItem(arrtype);
      
      if (arrdims.equalsIgnoreCase("1"))
      { component.getDimComboBox().setSelectedIndex(0);
        component.getDim1TextField().setText(arrdim1);}
      else
      { 
        component.getDimComboBox().setSelectedIndex(1); 
        component.Dim2setEnabled(true);
        component.getDim1TextField().setText(arrdim1);
        component.getDim2TextField().setText(arrdim2);
      }

      }

    }

    public void storeSettings(Object settings) {
        String prop_str1, prop_str2,prop_str3,prop_str4,prop_str5="";
        wizardDescriptor = (WizardDescriptor) settings;

        prop_str1 = ArrVisualPanel1.getArrName();
        prop_str2 = ArrVisualPanel1.getArrType();
        prop_str3 = ArrVisualPanel1.getArrDim();
        if (ArrVisualPanel1.CheckDim2())
        {   prop_str4 = ArrVisualPanel1.getDim1Size();
            prop_str5=ArrVisualPanel1.getDim2Size();}
        else
        {prop_str4 =ArrVisualPanel1.getDim1Size();
         prop_str5 ="1"; }

      
        wizardDescriptor.putProperty(WizardSettingsValues.ARR_NAME, prop_str1);
        wizardDescriptor.putProperty(WizardSettingsValues.ARR_TYPE, prop_str2);
        wizardDescriptor.putProperty(WizardSettingsValues.ARR_DIMS, prop_str3);
        wizardDescriptor.putProperty(WizardSettingsValues.ARR_DIM1, prop_str4);
        wizardDescriptor.putProperty(WizardSettingsValues.ARR_DIM2, prop_str5);
    }

    public void validate() throws WizardValidationException {

        ArrWizardPanel2.makeTempValuesNull();
        
        String arrname = ArrVisualPanel1.getArrName();
        String arrdim1 = ArrVisualPanel1.getDim1Size();
        String arrdim2 = ArrVisualPanel1.getDim2Size();

        String[] dispParams=null;
        AllObjects type = null;

/////array name empty
        if (arrname.isEmpty()) {

            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_ARR_NAME_BL1 + "\n" + Messages.ERR_ARR_NAME_BL2));
            throw new WizardValidationException(component.getNameTextField(), Messages.ERR_ARR_NAME_BL1, null);

        }

/////check array name
      for (int q = 0; q < arrname.length(); q++) {


          if ((q == 0) && (!(arrname.charAt(q) >= 65 && arrname.charAt(q) <= 90) && //A-Z
                    !(arrname.charAt(q) >= 97 && arrname.charAt(q) <= 122) && //a-z
                    !(arrname.charAt(q) >= 913 && arrname.charAt(q) <= 937) && //Α-Ω
                    !(arrname.charAt(q) >= 945 && arrname.charAt(q) <= 969) &&//α-ω 
                    !(arrname.charAt(q) == 36) &&//$
                    !(arrname.charAt(q) == 95)))//_
            {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_ARR_NAME_LET1 +
                        "\n" + Messages.ERR_ARR_NAME_LET2));
                throw new WizardValidationException(component.getNameTextField(), Messages.ERR_ARR_NAME_LET1, null);
            }
            else if (!(arrname.charAt(q) >= 65 && arrname.charAt(q) <= 90) && //A-Z
                    !(arrname.charAt(q) >= 97 && arrname.charAt(q) <= 122) && //a-z
                    !(arrname.charAt(q) >= 913 && arrname.charAt(q) <= 937) && //Α-Ω
                    !(arrname.charAt(q) >= 945 && arrname.charAt(q) <= 969) &&//α-ω
                    !(arrname.charAt(q) >= 48 && arrname.charAt(q) <= 57) && //0-9
                    !(arrname.charAt(q) == 36) &&//$
                    !(arrname.charAt(q) == 95))//_
            { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_ARR_NAME_LET3 +
                        "\n" + Messages.ERR_ARR_NAME_LET2));
                throw new WizardValidationException(component.getNameTextField(), Messages.ERR_ARR_NAME_LET3, null);}

      }

////RESERVED WORD
        if (ReservedWords.CheckReservedWord(arrname))
        {DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_ARR_NAME_RES +
                        "\n" + Messages.ERR_ARR_NAME_LET2));
                throw new WizardValidationException(component.getNameTextField(), Messages.ERR_ARR_NAME_RES, null);}


    ///IDENTIFIER IN USE IN THIS FUNCTION/PROCEDURE
    AllObjectsList caol = AllObjectsList.getAllObjList();
     type =caol.SearchByDisplayName_DispScope(arrname,this.dispScope);

    if( (caol!=null) && (type!=null) )
    { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_ARR_NAME_USE + type.getDispCateg()+
               " " +type.getDispName()+"\n" +Messages.ERR_ARR_NAME_USE1+ this.dispScope+"!\n"+ Messages.ERR_ARR_NAME_LET2));
              throw new WizardValidationException(component.getNameTextField(), Messages.ERR_ARR_NAME_USE + type.getDispCateg()+ " " +type.getDispName(), null);}

/////CHECK IF IDENTIFIER ALREADY IN USE GLOBAL
if (this.dispScope.equalsIgnoreCase(WizardsDefinitions.GLOBAL))
     type =caol.SearchByDisplayName_ExcludeFunctionsProcedures(arrname);
    else
     type =caol.SearchByDisplayName_DispScope(arrname,WizardsDefinitions.GLOBAL);

    if( (caol!=null) && (type!=null) )
    { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_ARR_NAME_USE + type.getDispCateg()+
               " " +type.getDispName()+"\n" +Messages.ERR_ARR_NAME_USE2+type.getDispScope()+"!\n"+ Messages.ERR_ARR_NAME_LET2));
              throw new WizardValidationException(component.getNameTextField(), Messages.ERR_ARR_NAME_USE + type.getDispCateg()+ " " +type.getDispName(), null);}


/////CHECK IF IDENTIFIER IS GLOBAL IS USED AS A PARAMETER OF ALL FUNCTION/PROCEDURE
if (this.dispScope.equalsIgnoreCase(WizardsDefinitions.GLOBAL))
{
dispParams=null;
//Get All Procedures
LinkedList <AllObjects> AllFunsPros =caol.SearchByDisplayCategory(WizardsDefinitions.PROCEDURE);
//GetAll Functions
LinkedList <AllObjects> AllFunsPros1 =caol.SearchByDisplayCategory(WizardsDefinitions.FUNCTION);
AllFunsPros.addAll(AllFunsPros1);

    if(!AllFunsPros.isEmpty())
    {
      for (AllObjects AOi:AllFunsPros)
      {
         if (!AOi.getDispParams().isEmpty())
         {dispParams = new String [AOi.getDispParams().size()];
          for (int op=0;op<AOi.getDispParams().size();op++)
                dispParams[op]=AOi.getDispParams().get(op);
         }

         if( dispParams!=null )
          {
            for(int pn=0;pn<dispParams.length;pn++)
            {
             if(arrname.equalsIgnoreCase(dispParams[pn]))
               { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_ARR_NAME_USE
                   +Messages.ERR_ARR_NAME_PARM1+(pn+1)+Messages.ERR_ARR_NAME_PARM2+AOi.getDispCateg()+" "
                   +AOi.getDispName()+"!\n"+ Messages.ERR_ARR_NAME_LET2));
                 throw new WizardValidationException(component.getNameTextField(), Messages.ERR_ARR_NAME_USE
                 +Messages.ERR_ARR_NAME_PARM1+(pn+1)+Messages.ERR_ARR_NAME_PARM2+AOi.getDispCateg(), null);
               }
            }
          }
      }
    }
}
else
{
/////CHECK IF LOCAL IDENTIFIER ALREADY USED AS A PARAMETER OF THIS FUNCTION/PROCEDURE
    dispParams=null;
    ///For Functions,Procedures objName=objScope
    AllObjects FunPro =caol.SearchByObjectScope_4FunctionsOrProcedures(this.objScope);
    if(FunPro!=null)
    if (!FunPro.getDispParams().isEmpty())
         {dispParams = new String [FunPro.getDispParams().size()];
          for (int op=0;op<FunPro.getDispParams().size();op++)
                dispParams[op]=FunPro.getDispParams().get(op);
         }

    if( dispParams!=null )
    {
        for(int pn=0;pn<dispParams.length;pn++)
        {
            if(arrname.equalsIgnoreCase(dispParams[pn]))
             { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_ARR_NAME_USE
           +Messages.ERR_ARR_NAME_PARM1+(pn+1)+Messages.ERR_ARR_NAME_PARM2+FunPro.getDispCateg()+" "+FunPro.getDispName()
               +" !\n"+ Messages.ERR_ARR_NAME_LET2));
              throw new WizardValidationException(component.getNameTextField(),
                      Messages.ERR_ARR_NAME_USE
           +Messages.ERR_ARR_NAME_PARM1+(pn+1)+Messages.ERR_ARR_NAME_PARM2+FunPro.getDispCateg(), null);}
        }
    }
}
 

/////////check DIM1 empty
    if (arrdim1.isEmpty()) {

            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_ARR_DIM1_EM + "\n" + Messages.ERR_ARR_DIM1_EM1));
            throw new WizardValidationException(ArrVisualPanel1.jTextField1, Messages.ERR_ARR_DIM1_EM, null);

        }

///////check DIM1 only numbers
    for (int q = 0; q < arrdim1.length(); q++) {
                if (!(arrdim1.charAt(q) >= 48 && arrdim1.charAt(q) <= 57)) {
                    DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                            Messages.ERR_ARR_DIM1_INT + "\n" + Messages.ERR_ARR_DIM1_EM1));
                    throw new WizardValidationException(ArrVisualPanel1.jTextField1, Messages.ERR_ARR_DIM1_INT, null);
                }
            }

    
////Check size>1
    if (Integer.valueOf(arrdim1)<=1)
    {DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                            Messages.ERR_ARR_DIM1_INT + "\n" + Messages.ERR_ARR_DIM1_EM1));
                    throw new WizardValidationException(ArrVisualPanel1.jTextField1, Messages.ERR_ARR_DIM1_INT, null);
    }


    if (ArrVisualPanel1.CheckDim2())
    {
       /////////check DIM1 empty
    if (arrdim2.isEmpty()) {

            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_ARR_DIM2_EM + "\n" + Messages.ERR_ARR_DIM2_EM1));
            throw new WizardValidationException(ArrVisualPanel1.jTextField2, Messages.ERR_ARR_DIM2_EM, null);

        }

///////check DIM1 only numbers
    for (int q = 0; q < arrdim2.length(); q++) {
                if (!(arrdim2.charAt(q) >= 48 && arrdim2.charAt(q) <= 57)) {
                    DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                            Messages.ERR_ARR_DIM2_INT + "\n" + Messages.ERR_ARR_DIM2_EM1));
                    throw new WizardValidationException(ArrVisualPanel1.jTextField2, Messages.ERR_ARR_DIM2_INT, null);
                }
            }

/////Check size >1
  if (Integer.valueOf(arrdim2)<=1)
    {DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                            Messages.ERR_ARR_DIM2_INT + "\n" + Messages.ERR_ARR_DIM2_EM1));
                    throw new WizardValidationException(ArrVisualPanel1.jTextField2, Messages.ERR_ARR_DIM2_INT, null);
    }



    }





    }
    
}

