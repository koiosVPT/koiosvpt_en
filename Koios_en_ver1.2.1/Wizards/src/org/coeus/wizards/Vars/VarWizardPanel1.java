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

import org.coeus.wizards.*;
import java.awt.Component;
import java.util.LinkedList;
import javax.swing.event.ChangeListener;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class VarWizardPanel1 implements WizardDescriptor.ValidatingPanel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private VarVisualPanel1 component;
    private WizardDescriptor wizardDescriptor;
    private String dispScope;
    private String objScope;

    public VarWizardPanel1(String idispScope,String iobjScope)
    {this.dispScope=idispScope;this.objScope=iobjScope;    }
    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent() {
        if (component == null) {
            component = new VarVisualPanel1();
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
      String conname,contype;
      wizardDescriptor = (WizardDescriptor) settings;

      update = (Boolean)wizardDescriptor.getProperty(WizardSettingsValues.UPDATE);

      wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_VAR_PANEL1);

      if(update)
      {
      conname= (String)wizardDescriptor.getProperty(WizardSettingsValues.VAR_NAME);
      contype= (String)wizardDescriptor.getProperty(WizardSettingsValues.VAR_TYPE);
      component.getTextField().setText(conname);
      component.getComboBox().setSelectedItem(contype);
      wizardDescriptor.putProperty(WizardSettingsValues.VAR_LAST_TYPE, contype);
      }

    }

    public void storeSettings(Object settings) {
        String prop_str1, prop_str2;
        wizardDescriptor = (WizardDescriptor) settings;

        prop_str1 = VarVisualPanel1.getConName();
        prop_str2 = VarVisualPanel1.getConType();


        wizardDescriptor.putProperty(WizardSettingsValues.VAR_NAME, prop_str1);
        wizardDescriptor.putProperty(WizardSettingsValues.VAR_TYPE, prop_str2);
    }


    public void validate() throws WizardValidationException {
        String conname = VarVisualPanel1.getConName();
        AllObjects  type = null,FunPro=null;;
        String[] dispParams=null;

//EMPTRY NAME
        if (conname.equalsIgnoreCase("")) {

            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_VAR_NAME_BL1 + "\n" + Messages.ERR_VAR_NAME_BL2));
            throw new WizardValidationException(component.getTextField(), Messages.ERR_VAR_NAME_BL1, null);

        }

////ONLY LETTERS , NUMS AND _
      for (int q = 0; q < conname.length(); q++) {


          if ((q == 0) && (!(conname.charAt(q) >= 65 && conname.charAt(q) <= 90) && //A-Z
                    !(conname.charAt(q) >= 97 && conname.charAt(q) <= 122) && //a-z
                    !(conname.charAt(q) >= 913 && conname.charAt(q) <= 937) && //Α-Ω
                    !(conname.charAt(q) >= 945 && conname.charAt(q) <= 969) &&//α-ω
                    !(conname.charAt(q) == 36) &&//$
                    !(conname.charAt(q) == 95)))//_
            {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_VAR_NAME_LET1 +
                        "\n" + Messages.ERR_VAR_NAME_LET2));
                throw new WizardValidationException(component.getTextField(), Messages.ERR_VAR_NAME_LET1, null);
            }
            else if (!(conname.charAt(q) >= 65 && conname.charAt(q) <= 90) && //A-Z
                    !(conname.charAt(q) >= 97 && conname.charAt(q) <= 122) && //a-z
                    !(conname.charAt(q) >= 913 && conname.charAt(q) <= 937) && //Α-Ω
                    !(conname.charAt(q) >= 945 && conname.charAt(q) <= 969) &&//α-ω
                    !(conname.charAt(q) >= 48 && conname.charAt(q) <= 57) && //0-9
                    !(conname.charAt(q) == 36) &&//$
                    !(conname.charAt(q) == 95))//_
            { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_VAR_NAME_LET3 +
                        "\n" + Messages.ERR_VAR_NAME_LET2));
                throw new WizardValidationException(component.getTextField(), Messages.ERR_VAR_NAME_LET3, null);}

      }

////CHECK IF RESERVED WORD
        if (ReservedWords.CheckReservedWord(conname))
        {DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_VAR_NAME_RES +
                        "\n" + Messages.ERR_VAR_NAME_LET2));
                throw new WizardValidationException(component.getTextField(), Messages.ERR_VAR_NAME_RES, null);}

        

    AllObjectsList caol = AllObjectsList.getAllObjList();
/////CHECK IF IDENTIFIER ALREADY IN USE IN THIS FUNCTION/PROCEDURE
    type =caol.SearchByDisplayName_DispScope(conname,this.dispScope);

    if( (caol!=null) && (type!=null) )
    { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_VAR_NAME_USE + type.getDispCateg()+
               " " +type.getDispName()+"\n" +Messages.ERR_VAR_NAME_USE1+ this.dispScope+" !\n"+ Messages.ERR_VAR_NAME_LET2));
              throw new WizardValidationException(component.getTextField(), Messages.ERR_VAR_NAME_USE + type.getDispCateg()+ " " +type.getDispName(), null);}
     
/////CHECK IF IDENTIFIER ALREADY IN USE GLOBAL
    if (this.dispScope.equalsIgnoreCase(WizardsDefinitions.GLOBAL))
     type =caol.SearchByDisplayName_ExcludeFunctionsProcedures(conname);
    else
     type =caol.SearchByDisplayName_DispScope(conname,WizardsDefinitions.GLOBAL);

    if( (caol!=null) && (type!=null) )
    { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_VAR_NAME_USE + type.getDispCateg()+
               " " +type.getDispName()+"\n" +Messages.ERR_VAR_NAME_USE2+type.getDispScope()+" !\n"+ Messages.ERR_VAR_NAME_LET2));
              throw new WizardValidationException(component.getTextField(), Messages.ERR_VAR_NAME_USE + type.getDispCateg()+ " " +type.getDispName(), null);}

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
             if(conname.equalsIgnoreCase(dispParams[pn]))
               { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_VAR_NAME_USE
                   +Messages.ERR_VAR_NAME_PARM1+(pn+1)+Messages.ERR_VAR_NAME_PARM2+AOi.getDispCateg()+" "
                   +AOi.getDispName()+"!\n"+ Messages.ERR_VAR_NAME_LET2));
                 throw new WizardValidationException(component.getTextField(), Messages.ERR_VAR_NAME_USE
                 +Messages.ERR_VAR_NAME_PARM1+(pn+1)+Messages.ERR_VAR_NAME_PARM2+AOi.getDispCateg(), null);
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
   FunPro =caol.SearchByObjectScope_4FunctionsOrProcedures(this.objScope);
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
            if(conname.equalsIgnoreCase(dispParams[pn]))
             { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_VAR_NAME_USE
           +Messages.ERR_VAR_NAME_PARM1+(pn+1)+Messages.ERR_VAR_NAME_PARM2+FunPro.getDispCateg()+" "+FunPro.getDispName()
               +" !\n"+ Messages.ERR_VAR_NAME_LET2));
              throw new WizardValidationException(component.getTextField(),
                      Messages.ERR_VAR_NAME_USE
           +Messages.ERR_VAR_NAME_PARM1+(pn+1)+Messages.ERR_VAR_NAME_PARM2+FunPro.getDispCateg(), null);}
        }
    }
   }

 }


}

