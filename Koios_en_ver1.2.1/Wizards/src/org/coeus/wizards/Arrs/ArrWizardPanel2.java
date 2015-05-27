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

import org.coeus.wizards._HelpFuntions.CheckString2D;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.coeus.parsers.parserDefinitions;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards.Messages;
import org.coeus.wizards.TextActions.GetFocus;
import org.coeus.wizards.WizardSettingsValues;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class ArrWizardPanel2 implements WizardDescriptor.ValidatingPanel {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private ArrVisualPanel2 component;
    WizardDescriptor wizardDescriptor;
    int vp,vt,numrows,numcolumns;
    DefaultTableModel dataTableModel,indexTableModel;
    String [][] initValues=null;
    private static String [] []tempValues=null;
    String lasttype,lastdim1,lastdim2,arrdim1,arrdim2;
    private int errorRow,errorColumn;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.


     public Component getComponent() {
         
        String label8;
         
        if (component == null) {
            component = new ArrVisualPanel2();
        }
        component.jLabel3.setText(ArrVisualPanel1.getArrName());
        component.jLabel3.setText(ArrVisualPanel1.getArrName());
        component.jLabel5.setText(ArrVisualPanel1.getArrType());
        component.jLabel9.setText("Size of 1st Dimension: "+ArrVisualPanel1.getDim1Size());
      
        component.setMyCompEnabled(true);



         if (ArrVisualPanel1.getArrType().equalsIgnoreCase(WizardsDefinitions.INT2)) {
              vp=0;
        } else  if (ArrVisualPanel1.getArrType().equalsIgnoreCase(WizardsDefinitions.FLOAT2)) {
             vp = 1;
        }else if (ArrVisualPanel1.getArrType().equalsIgnoreCase(WizardsDefinitions.CHAR2)) {
             vp = 2;
        } else if (ArrVisualPanel1.getArrType().equalsIgnoreCase(WizardsDefinitions.BOOLEAN2)) {
             vp = 3;
        }else if (ArrVisualPanel1.getArrType().equalsIgnoreCase(WizardsDefinitions.STRING2)) {
             vp = 4;
        }


        if (ArrVisualPanel1.getArrDim().equalsIgnoreCase("1"))
        {label8=WizardsDefinitions.DIM1;
         component.jLabel10.setVisible(false);
      
            dataTableModel = myDataTableModel(ArrVisualPanel1.getDim1Size(),"1");
            indexTableModel = myIndexTableModel(); 
        }
        else
        {label8=WizardsDefinitions.DIM2;
         component.jLabel10.setVisible(true);
         component.jLabel10.setText("Size of 2nd Dimension: "+ArrVisualPanel1.getDim2Size());
         dataTableModel = myDataTableModel(ArrVisualPanel1.getDim1Size(),ArrVisualPanel1.getDim2Size());
         indexTableModel = myIndexTableModel();
         }

         component.jLabel8.setText(label8);
         component.jTable1.setModel(dataTableModel);

         component.jTable2.setModel(indexTableModel);
         Class mcc= component.jTable2.getColumnClass(0);
         //////////PAINT CELLS OF ROW INDEX TABLE
         DefaultTableCellRenderer  tcr = new DefaultTableCellRenderer();
         tcr.setBackground(new Color(236,233,216));
         component.jTable2.setDefaultRenderer(mcc,tcr );

         component.jTable1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

         if (tempValues!=null)
         {
            for (int m=0;m<numrows;m++)
                 for (int l=0;l<numcolumns;l++)
                   component.jTable1.setValueAt(tempValues[m][l], m,l);

              component.jTable1.changeSelection(errorRow,errorColumn, false, false);
              component.jTable1.requestFocus();
         }

                new GetFocus(component.jTable1);
        return component;
    }

   
    private DefaultTableModel myDataTableModel (String rows,String columns)
    {DefaultTableModel tm;
     String [] colNames;
     

     if (rows.isEmpty()|| columns.isEmpty())
     {numrows=1;numcolumns=1;
      colNames = new String[numcolumns];}
     else
     {
      numrows=Integer.valueOf(rows);
      numcolumns=Integer.valueOf(columns);
      colNames = new String[numcolumns];
      for (int q=0;q<numcolumns;q++)
          colNames[q]=String.valueOf(q);
      
     }

     tm=new javax.swing.table.DefaultTableModel( numrows,numcolumns );
     tm.setColumnIdentifiers(colNames);


 //  System.out.println("\n\n\nlastd1: " +lastdim1+"   ,ard1: "+arrdim1+"   ,ld2: "+lastdim2+"   ,ard2: "+arrdim2
//           +"\nvt: "+vt+"   ,vp: "+vp+"\n\n\n");
   if (lastdim1!=null && arrdim1!=null && lastdim2!=null && arrdim2!=null){
    
     if (!CheckString2D.isEmpty(initValues, numrows, numcolumns)
      && lastdim1.equalsIgnoreCase(arrdim1) && lastdim2.equalsIgnoreCase(arrdim2) && vt==vp)
     { component.setMyCompEnabled(true);
      for (int m=0;m<numrows;m++)
                     for (int l=0;l<numcolumns;l++)
           { tm.setValueAt(initValues[m][l],m,l);}
     }
   }

     return tm;
    }

  private DefaultTableModel myIndexTableModel ()
  { DefaultTableModel itm;
    

    String [] colNames=new String[1];
    colNames[0]=" ";
        
     itm=new javax.swing.table.DefaultTableModel( numrows,1 );
     for (int q=0;q<numrows;q++)
        itm.setValueAt(q,q,0);
     itm.setColumnIdentifiers(colNames);
    return itm;
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

        wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, Messages.INFO_ARR_PANEL2);

        update = (Boolean) wizardDescriptor.getProperty(WizardSettingsValues.UPDATE);
        lasttype =(String) wizardDescriptor.getProperty(WizardSettingsValues.VAR_LAST_TYPE);
        initValues =(String [][]) ArrWizardAction.getArrInitValue();
        lastdim1 =(String) wizardDescriptor.getProperty(WizardSettingsValues.ARR_LAST_DIM1);
        lastdim2 =(String) wizardDescriptor.getProperty(WizardSettingsValues.ARR_LAST_DIM2);
        arrdim1= (String)wizardDescriptor.getProperty(WizardSettingsValues.ARR_DIM1);
        arrdim2= (String)wizardDescriptor.getProperty(WizardSettingsValues.ARR_DIM2);
        
       if(update)
        {
        if (lasttype.equalsIgnoreCase(WizardsDefinitions.INT2)) {
             vt=0;
        } else if (lasttype.equalsIgnoreCase(WizardsDefinitions.FLOAT2)) {
             vt = 1;
        }else if (lasttype.equalsIgnoreCase(WizardsDefinitions.CHAR2)) {
             vt = 2;
        } else if (lasttype.equalsIgnoreCase(WizardsDefinitions.BOOLEAN2)) {
             vt = 3;           
        }else if (lasttype.equalsIgnoreCase(WizardsDefinitions.STRING2)) {
             vt = 4;
        }
       }

           
    }

    public void storeSettings(Object settings) {
        String [][] values=null;
      
        JTable dataTable = component.getDataTable();

        if ( component.CheckInitValue())
        {
           values = new String[numrows][numcolumns];
               for (int m=0;m<numrows;m++)
                   for (int l=0;l<numcolumns;l++)
                   {
                     values[m][l]=(String)dataTable.getValueAt(m, l);
                   }
        }
      
        ArrWizardAction.setArrInitValue(values);
    
    }

    public static void makeTempValuesNull()
    {tempValues=null;}

    public void validate() throws WizardValidationException {
     
     JTable dataTable = component.getDataTable();
     if (component.CheckInitValue())
     {
        /// if Initial Values true...get values
       tempValues = new String[numrows][numcolumns];
          for (int m=0;m<numrows;m++)
            for (int l=0;l<numcolumns;l++)
       tempValues[m][l]=(String)dataTable.getValueAt(m, l);

   
   
        for (int m=0;m<numrows;m++)
          for (int l=0;l<numcolumns;l++)
           {
               int iq=1;

          ///Check if there are null initial tempValues
         if ((tempValues[m][l]==null))
         { 
         errorRow=m;
         errorColumn=l;
         DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
              Messages.ERR_ARR_VALUE_EM1 + "\n" + Messages.ERR_ARR_VALUE_BOOL2+"["+m+","+l+"]"));
         throw new WizardValidationException(component.getDataTable(), Messages.ERR_ARR_VALUE_EM1, null);
         }

               
        if ((tempValues[m][l].equalsIgnoreCase("")))
         {
         errorRow=m;
         errorColumn=l;
         DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
              Messages.ERR_ARR_VALUE_EM1 + "\n" + Messages.ERR_ARR_VALUE_BOOL2+"["+m+","+l+"]"));
         throw new WizardValidationException(dataTable, Messages.ERR_ARR_VALUE_EM1, null);
         }

            //if array type int
            if (ArrVisualPanel1.getArrType().equalsIgnoreCase(WizardsDefinitions.INT2)) {
  
                    ////ONLY NYMBERS
                ///check element 0 for a sign
                if (tempValues[m][l].charAt(0)=='+')
                {tempValues[m][l]=tempValues[m][l].substring(1);dataTable.setValueAt(tempValues[m][l], m,l); iq=0;}
                else if (!( (tempValues[m][l].charAt(0) >= 48 && tempValues[m][l].charAt(0) <= 57)
                              || (tempValues[m][l].charAt(0)=='-') ) )
                          {
                        errorRow=m;
                        errorColumn=l;
                        DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                                Messages.ERR_ARR_VALUE_NUM1 + "\n"+ Messages.ERR_ARR_VALUE_BOOL2+"["+m+","+l+"]"));
                        throw new WizardValidationException(dataTable, Messages.ERR_ARR_VALUE_NUM1, null);
                    }
                
                for (int q = iq; q < tempValues[m][l].length(); q++) {
                    ///rest of elements
                    if (!(tempValues[m][l].charAt(q) >= 48 && tempValues[m][l].charAt(q) <= 57)) {
                      errorRow=m;
                      errorColumn=l;
                      DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                                Messages.ERR_ARR_VALUE_NUM1 + "\n"+ Messages.ERR_ARR_VALUE_BOOL2+"["+m+","+l+"]"));
                        throw new WizardValidationException(dataTable, Messages.ERR_ARR_VALUE_NUM1, null);
                    }
                }

                /////MIN-MAX NUMBERS -2147483648 ,+2147483647
                try {
                    Integer.parseInt(tempValues[m][l]);
                } catch (NumberFormatException nfe) {
                  errorRow=m;
                  errorColumn=l;
                  DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                            Messages.ERR_ARR_VALUE_OOL1 + "\n" + Messages.ERR_ARR_VALUE_BOOL2+"["+m+","+l+"]"));
                    throw new WizardValidationException(dataTable, Messages.ERR_ARR_VALUE_OOL1, null);
                }
      

            //if array type float
             } else  if (ArrVisualPanel1.getArrType().equalsIgnoreCase(WizardsDefinitions.FLOAT2)) {
                 int point_num=0;
             
                //check element 0 for a sign
                if (tempValues[m][l].charAt(0)=='+')
                {tempValues[m][l]=tempValues[m][l].substring(1);dataTable.setValueAt(tempValues[m][l], m,l);iq=0; }
                else if (!( (tempValues[m][l].charAt(0) >= 48 && tempValues[m][l].charAt(0) <= 57)
                              || (tempValues[m][l].charAt(0)=='-') ) )
                          {
                       errorRow=m;
                       errorColumn=l;
                       DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                                Messages.ERR_ARR_VALUE_OOL2 + "\n"+ Messages.ERR_ARR_VALUE_BOOL2+"["+m+","+l+"]"));
                        throw new WizardValidationException(dataTable, Messages.ERR_ARR_VALUE_OOL2, null);
                    }

                for (int q = iq; q < tempValues[m][l].length(); q++) {

                    ///rest of elements
                if (!(((tempValues[m][l].charAt(q) >= 48 && tempValues[m][l].charAt(q) <= 57)) || tempValues[m][l].charAt(q)=='.')) {
                   errorRow=m;
                   errorColumn=l;
                   DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                            Messages.ERR_ARR_VALUE_OOL2 + "\n" + Messages.ERR_ARR_VALUE_BOOL2+"["+m+","+l+"]"));
                    throw new WizardValidationException(dataTable, Messages.ERR_ARR_VALUE_OOL2, null);
                    }
                if (tempValues[m][l].charAt(q)=='.') point_num++;
               }

               
               if (point_num>=2)
               {
                  errorRow=m;
                  errorColumn=l;
                  DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                            Messages.ERR_ARR_VALUE_OOL3 + "\n" + Messages.ERR_ARR_VALUE_BOOL2+"["+m+","+l+"]"));
                    throw new WizardValidationException(dataTable, Messages.ERR_ARR_VALUE_OOL3, null);
                }


                //MIN MAX VALUE

                    try {
                       dataTable.setValueAt(Float.toString( Float.parseFloat(tempValues[m][l])),m,l);
                    } catch (NumberFormatException nfe) {
                      errorRow=m;
                      errorColumn=l;
                      DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                                Messages.ERR_ARR_VALUE_OOL5 + "\n" + Messages.ERR_ARR_VALUE_BOOL2+"["+m+","+l+"]"));
                        throw new WizardValidationException(dataTable, Messages.ERR_ARR_VALUE_OOL5, null);
                    }

         

              //if array type char
            }else if (ArrVisualPanel1.getArrType().equalsIgnoreCase(WizardsDefinitions.CHAR2)) {


                ////Check for Just one charachter
                if ((tempValues[m][l].length() >= 2)) {
                errorRow=m;
                errorColumn=l;
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                        Messages.ERR_VAR_VALUE_OOL4 + "\n"  +Messages.ERR_ARR_VALUE_OOL4A+tempValues[m][l].length()+Messages.ERR_ARR_VALUE_OOL4B+".\n"
                        + Messages.ERR_ARR_VALUE_BOOL2+"["+m+","+l+"]"));
                throw new WizardValidationException(dataTable, Messages.ERR_VAR_VALUE_OOL4, null);
                }

            //if array type string
            }else if (ArrVisualPanel1.getArrType().equalsIgnoreCase(WizardsDefinitions.STRING2)) {

            //if array type boolean
            } else if (ArrVisualPanel1.getArrType().equalsIgnoreCase(WizardsDefinitions.BOOLEAN2)) {
        

                           ///tempValues can be only TREU or FALSE
                           if (!(tempValues[m][l].equalsIgnoreCase(parserDefinitions.TRUE) || tempValues[m][l].equalsIgnoreCase(parserDefinitions.FALSE)))
                            {
                              errorRow=m;
                              errorColumn=l;
                              DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                                  Messages.ERR_ARR_VALUE_BOOL1 + "\n" + Messages.ERR_ARR_VALUE_BOOL2+"["+m+","+l+"]"));
                               throw new WizardValidationException(dataTable, Messages.ERR_ARR_VALUE_BOOL1, null);
                            }
                      }
            }
        }
       
    }

}

