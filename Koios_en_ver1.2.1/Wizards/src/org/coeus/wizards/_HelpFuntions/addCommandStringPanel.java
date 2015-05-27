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

/*
 * addConditionPanel.java
 *
 * Created on 19 Ιουν 2009, 9:06:39 μμ
 */

package org.coeus.wizards._HelpFuntions;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import org.coeus.btvpalette.myAdvanceAction;
import org.coeus.wizards.FuncCall.FuncCallWizardAction;
import org.coeus.wizards.TextActions.GetFocus;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public final class addCommandStringPanel extends JPanel {
    private String objScope;

   // private LinkedList <String> messages= new LinkedList();
    private LinkedList <String> dispNames= new LinkedList<String>();
    private LinkedList <String> objNames= new LinkedList<String>();
    private LinkedList <String> dispTypes= new LinkedList<String>();
    private LinkedList <String> objTypes= new LinkedList<String>();
    private LinkedList <String> dispCategory= new LinkedList<String>();

     private getArrayElementsList gAEL=null;
     private getParametersList gPL= null;
     private getVariablesList gVL = null;
     private getConstantsList gCL = null;
     private getFunctionsList gFL = null;

     private boolean result=true;
     private String [] error=new String[2];
     private ActionEvent e;

     private  boolean textInputByUser=false;
     private String commandName=null;

      /** Creates new form ReadVisualPanel1 */
    public addCommandStringPanel (String iObjScope,String in_STRING,String iCommandName) {
        initComponents();
        showAdvanceButtons();
        setAddStringButtonsVisible(false);
        this.objScope=iObjScope;
        this.commandName=iCommandName;
        this.errorLabel.setText("");


//customize tool tip apperance
        UIManager.put("ToolTip.background", new ColorUIResource(220, 220, 220)); // The color is #fff7c8.
        Border border = BorderFactory.createLineBorder(new Color(76,79,83)); // The color is #4c4f53.
        UIManager.put("ToolTip.border", border);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(20000);// 20 seconds
        //end customize tool tip apperance

 selectVariableBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
          "To select a string that is stored in a VARIABLE, click the \"" + "<b>Select Variable</b>"+ "\" button.</font></html>");

selectArrElemBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To select a string that is stored in an ARRAY ELEMENT, click the \"" + "<b>Select Array Element</b>"+ "\" button.</font></html>");

selectConstantBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To select a string that is stored in a CONSTANT, click the \"" + "<b>Select Constant</b>"+ "\" button.</font></html>");

selectFunctionBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To select a string that is returned by a FUNCTION, click the \"" + "<b>Select Function</b>"+ "\" button.</font></html>");

selectParameterBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To select a string that is passed by an INPUT PARAMETER, click the \"" + "<b>Select Input Parameter</b>"+ "\" button.</font></html>");

 inputStringBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To enter a string from the keyboard, click the \"" + "<b>Enter Character</b>"+ "\" button.</font></html>");

 deleteStringBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To clear the selected string, click the \"" + "<b>Delete String</b>"+ "\" button.</font></html>");



         gAEL = new getArrayElementsList(this.objScope,WizardsDefinitions.STRING);
         gPL  = new getParametersList(this.objScope,WizardsDefinitions.STRING);
         gVL = new getVariablesList(this.objScope,WizardsDefinitions.STRING);
         gCL = new getConstantsList(this.objScope,WizardsDefinitions.STRING);
         gFL = new getFunctionsList(WizardsDefinitions.STRING);

        setSelectButtonsEnabled(true);
        if (in_STRING!=null)
        {    setSelectButtonsEnabled(false);
             this.stringLabel.setText(in_STRING);
        }

 //new printLists(messages,dispNames,objNames,dispTypes,objTypes,dispCategory);

    }

    @Override
    public String getName() {
        return "STRING Selection";
    }

     private void showAdvanceButtons()
    {
     myAdvanceAction mAA = myAdvanceAction.getAdvanceAction();
     if (!mAA.getToolbarPresenter().isSelected())
       setAdvancedButtonsVisible(false);
    }


      private void setAdvancedButtonsVisible(boolean tf)
    {
      this.selectArrElemBut.setVisible(tf);
      this.selectConstantBut.setVisible(tf);
      this.selectFunctionBut.setVisible(tf);
    }

     
private void setAddStringButtonsVisible(boolean tf)
{
      
  this.deleteStringBut.setVisible(!tf);
  this.stringLabel.setVisible(!tf);

  this.DQLabel1.setVisible(tf);
  this.DQLabel2.setVisible(tf);
  this.stringTextField.setVisible(tf);
}


   private void setSelectButtonsEnabled(boolean tf)
    {

      this.selectArrElemBut.setEnabled(tf);
      this.selectConstantBut.setEnabled(tf);
      this.selectFunctionBut.setEnabled(tf);
      this.selectParameterBut.setEnabled(tf);
      this.selectVariableBut.setEnabled(tf);
      this.inputStringBut.setEnabled(tf);
      this.deleteStringBut.setEnabled(!tf);

        if (gVL.getListWithVariables().isEmpty())
             selectVariableBut.setEnabled(false);

         if (gAEL.getDisplayNames()==null)
             selectArrElemBut.setEnabled(false);

         if (gPL.getDisplayNames()==null)
             selectParameterBut.setEnabled(false);

         if (gCL.getDisplayNames()==null)
             selectConstantBut.setEnabled(false);

          if (gFL.getDisplayNames()==null)
             selectFunctionBut.setEnabled(false);
    }

 

    public JButton getParametersButton()
   {return this.selectParameterBut;}

  ///GETTERS
//    public LinkedList<String> getMessages()
//    {return this.messages;}

    public LinkedList<String> getDispArguements()
    {return this.dispNames;}

     public LinkedList<String> getObjArguements()
    {return this.objNames;}

     public LinkedList<String> getDispArguementsType()
    {return this.dispTypes;}

     public LinkedList<String> getObjArguementsType()
    {return this.objTypes;}

    public LinkedList<String> getDispCategory()
    {return this.dispCategory;}

       public boolean isResult() {
        return result;
    }

     public String[] getError() {
        return error;
    }

     /////SETTERS
//    public void setMessages(LinkedList<String> iMessages)
//    {this.messages=iMessages;}

    public void setDispArguements(LinkedList<String> iDispNames)
    {this.dispNames=iDispNames;}

     public void setObjArguements(LinkedList<String> iObjNames)
    {this.objNames=iObjNames;}

     public void setDispArguementsType(LinkedList<String> iDispTypes)
    {this.dispTypes=iDispTypes;}

     public void setObjArguementsType(LinkedList<String> iObjTypes)
    {this.objTypes=iObjTypes;}

    public void setDispCategory(LinkedList<String> iDispCategory)
    {this.dispCategory=iDispCategory;}

       public void setError(String[] error) {
        this.error = error;
    }

     public void setResult(boolean result) {
        this.result = result;
    }






       public void addConstant()
    {
      this.errorLabel.setText("");
      getCommandConstantsListPanel gCLP = new getCommandConstantsListPanel(gCL,this.commandName,null,null,null,null);

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(gCLP,"Constant Selection",
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(gCLP.getDName()!=null)
       {
           if (!gCLP.getDName().equalsIgnoreCase(""))
           {
           dispNames.add(gCLP.getDName());
           objNames.add(gCLP.getOName());
           dispTypes.add(gCLP.getDType());
           objTypes.add(gCLP.getOType());
           dispCategory.add(WizardsDefinitions.CONSTANT);

           stringLabel.setText(gCLP.getDName());
           setSelectButtonsEnabled(false);
          }
       }
   }
 }


  public void addParameter()
    {
     this.errorLabel.setText("");
     getCommandParametersListPanel gPLPanel = new getCommandParametersListPanel(gPL,this.commandName,null,null,null,null);

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(gPLPanel,"Input Parameter Selection",
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(gPLPanel.getDName()!=null)
       {
           if (!gPLPanel.getDName().equalsIgnoreCase(""))
           {
           dispNames.add(gPLPanel.getDName());
           objNames.add(gPLPanel.getOName());
           dispTypes.add(gPLPanel.getDType());
           objTypes.add(gPLPanel.getOType());
           dispCategory.add(WizardsDefinitions.PARAMETER);

     stringLabel.setText(gPLPanel.getDName());
     setSelectButtonsEnabled(false);
           }
       }
   }
 }


public void addFunction()
{
       this.errorLabel.setText("");
 FuncCallWizardAction fcwa= new FuncCallWizardAction(false,this.objScope,gFL);
  fcwa.actionPerformed(e);

   if ( fcwa.getCreated())
   {
         //  if (!gFLP.getDName().equalsIgnoreCase(""))
        //   {
           dispNames.add(fcwa.getDisplayFunctionCall());
           objNames.add(fcwa.getObjectFunctionCall());
           dispTypes.add(fcwa.getDispFunctionType());
           objTypes.add(fcwa.getObjFunctionType());
           dispCategory.add(WizardsDefinitions.FUNCTION);

             stringLabel.setText(fcwa.getDisplayFunctionCall());
 setSelectButtonsEnabled(true);
       //    }
   }

}



public void addVariable()
{
       this.errorLabel.setText("");
getCommandVariablesListPanel gVLPanel = new getCommandVariablesListPanel(gVL,this.commandName,null,null,null,null);

       NotifyDescriptor d =new NotifyDescriptor.Confirmation(gVLPanel,"Variable Selection",
       NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(gVLPanel.getDName()!=null)
       {
           if (!gVLPanel.getDName().equalsIgnoreCase(""))
           {
          // messages.add(gVLPanel.getMessage());
           dispNames.add(gVLPanel.getDName());
           objNames.add(gVLPanel.getOName());
           dispTypes.add(gVLPanel.getDType());
           objTypes.add(gVLPanel.getOType());
           dispCategory.add(WizardsDefinitions.VARIABLE);

           stringLabel.setText(gVLPanel.getDName());
           setSelectButtonsEnabled(false);
           }
       }
   }
}


public void addArrayElement()
{
          this.errorLabel.setText("");
getCommandArrayElementsListPanel gAELPanel = new getCommandArrayElementsListPanel(gAEL,this.commandName,null,null,null,null);

       NotifyDescriptor d =new NotifyDescriptor.Confirmation(gAELPanel,"Array Element Selection",
       NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(gAELPanel.getDArrayName()!=null)
       {
         if (!gAELPanel.getReturnDisplaeStateMent().equalsIgnoreCase(""))
         {
           dispNames.add(gAELPanel.getReturnDisplaeStateMent());
           objNames.add(gAELPanel.getReturnObjectStatement());
           dispTypes.add(gAELPanel.getDArrayType());
           objTypes.add(gAELPanel.getOArrayType());
           dispCategory.add(WizardsDefinitions.ARRAY_ELEMANT);

       stringLabel.setText(gAELPanel.getReturnDisplaeStateMent());
      setSelectButtonsEnabled(false);
         }
      }
   }
}

private void addString()
{
   this.errorLabel.setText("");
   this.stringTextField.setText("");
   setAddStringButtonsVisible(true);

   this.selectArrElemBut.setEnabled(false);
   this.selectConstantBut.setEnabled(false);
   this.selectFunctionBut.setEnabled(false);
   this.selectParameterBut.setEnabled(false);
   this.selectVariableBut.setEnabled(false);
   this.inputStringBut.setEnabled(false);
   this.deleteStringBut.setEnabled(false);

   new GetFocus(stringTextField);
}


//private void addString(addWriteStringPanel aWSP)
//{
//   aWSP.stringTextField.setText("");
//   aWSP.setAddStringButtonsVisible(true);
//   new GetFocus(aWSP.stringTextField);
//
// if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
//   {
//           if (checkValidInput(NotifyDescriptor.OK_CANCEL_OPTION))
//           {
//           this.errorLabel.setText("");
//           this.stringLabel.setText("\""+ this.stringTextField.getText()+"\"") ;
//
//           dispNames.add("\""+ this.stringTextField.getText()+"\"");
//           objNames.add("\""+ this.stringTextField.getText()+"\"");
//           dispTypes.add(WizardsDefinitions.STRING1);
//           objTypes.add(WizardsDefinitions.STRING);
//           dispCategory.add(WizardsDefinitions.STRING1);
//
//           setSelectButtonsEnabled(false);
//           setAddStringButtonsVisible(false);
//           //d =new NotifyDescriptor.Confirmation(gSP,gSP.getName(),
//           //NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);
//
//
//
//           }
//   }
//
/////////////////////////////////////////
//}



//private void validConditions ()
//{   result=true;
//   
//    if ( (this.stringLabel.getText().isEmpty() && this.stringTextField.getText().isEmpty()) ||
//         (this.stringLabel.getText().equalsIgnoreCase("[ΚΕΝΗ]") && this.stringTextField.getText().isEmpty()))
//    {
//     result=false;
//     error[0]="Δεν έχει επιλεγεί ΣΥΜΒΟΛΟΣΕΙΡΑ!Η Έκφρασή δεν μπορέι να είναι κενή.";
//     error[1]="";
//    }
//}

public void putStringInLabel()
{
           this.errorLabel.setText("");
           this.stringLabel.setText("\""+ this.stringTextField.getText()+"\"") ;

           dispNames.add("\""+ this.stringTextField.getText()+"\"");
           objNames.add("\""+ this.stringTextField.getText()+"\"");
           dispTypes.add(WizardsDefinitions.STRING1);
           objTypes.add(WizardsDefinitions.STRING);
           dispCategory.add(WizardsDefinitions.STRING1);

           setSelectButtonsEnabled(false);
           setAddStringButtonsVisible(false);
}

private void validConditions ()
{   result=true;
       
    if ( this.stringTextField.isVisible())
    {
    if (this.stringTextField.getText().isEmpty()) 
       {
         result=false;
         error[0]="You have not entered a string!";
         error[1]="";
         new GetFocus(this.stringTextField);
       }
    else
      {
        this.putStringInLabel();
        textInputByUser=true;
      }
    }
    else
    {
     if (this.stringLabel.getText().isEmpty() ||
         this.stringLabel.getText().equalsIgnoreCase("[NONE]") )
         {
           result=false;
           error[0]="You have not selected a STRING item!Make a selection and then click OK.";
           error[1]="";
         }
    }
}



public boolean checkValidInput(int buttonsOptions)
{
 NotifyDescriptor d=null;
 Object answer=null;

 validConditions();
              while(!result || textInputByUser)
             {
              if (!result) 
                {
                   d =new NotifyDescriptor.Confirmation(this,this.getName(),
                   buttonsOptions,NotifyDescriptor.PLAIN_MESSAGE);

                   this.errorLabel.setText(error[0]);
                }
             else //if (textInputByUser)
                {
                  d =new NotifyDescriptor.Confirmation(this,this.getName(),
                   buttonsOptions,NotifyDescriptor.PLAIN_MESSAGE);

                   this.errorLabel.setText("");
                   textInputByUser=false;
                }
             answer=DialogDisplayer.getDefault().notify(d);

             if ( answer==NotifyDescriptor.OK_OPTION)
              validConditions();
             else if (answer==NotifyDescriptor.CLOSED_OPTION)
               result=false;// return false;
             else if ( answer==NotifyDescriptor.CANCEL_OPTION)
               return false;
          }
  return true;
}



private void clearAll()
{
stringLabel.setText("[NONE]");
dispNames.clear();
objNames.clear();
dispTypes.clear();
objTypes.clear();
dispCategory.clear();

setSelectButtonsEnabled(true);
}

 public LinkedList<String> getObjNames() {
        return objNames;
    }


public JTextField getStringTextField()
{ return this.stringTextField;}

public JLabel getStringLabel()
{ return this.stringLabel;}

public JLabel getErrorLabel()
{ return this.errorLabel;}

public String getDisplayString()
{ return stringLabel.getText();}

public String getObjString()
//{ return charLabel.getText();}
{ return this.objNames.getFirst();}


// public void setDisplayString(String text)
//{ stringLabel.setText(text);}
//
//public void setObjString(String objText)
//{this.objNames.add(objText);}

// public String getDType()
//    {return WizardsDefinitions.STRING1;}
//
// public String getOType()
//    {return WizardsDefinitions.STRING;}


    public JLabel [] getHeaders()
{return new JLabel [] {this.jLabel5,this.jLabel6,this.jLabel7,this.jLabel8};}




    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        selectConstantBut = new javax.swing.JButton();
        selectVariableBut = new javax.swing.JButton();
        selectFunctionBut = new javax.swing.JButton();
        selectArrElemBut = new javax.swing.JButton();
        selectParameterBut = new javax.swing.JButton();
        inputStringBut = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        stringLabel = new javax.swing.JLabel();
        deleteStringBut = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();
        stringTextField = new javax.swing.JTextField();
        DQLabel1 = new javax.swing.JLabel();
        DQLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        selectConstantBut.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.selectConstantBut.text_1")); // NOI18N
        selectConstantBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectConstantButActionPerformed(evt);
            }
        });

        selectVariableBut.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.selectVariableBut.text_1")); // NOI18N
        selectVariableBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectVariableButActionPerformed(evt);
            }
        });

        selectFunctionBut.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.selectFunctionBut.text_1")); // NOI18N
        selectFunctionBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFunctionButActionPerformed(evt);
            }
        });

        selectArrElemBut.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.selectArrElemBut.text_1")); // NOI18N
        selectArrElemBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectArrElemButActionPerformed(evt);
            }
        });

        selectParameterBut.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.selectParameterBut.text_1")); // NOI18N
        selectParameterBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectParameterButActionPerformed(evt);
            }
        });

        inputStringBut.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.inputStringBut.text_1")); // NOI18N
        inputStringBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputStringButActionPerformed(evt);
            }
        });

        jLabel2.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.jLabel2.text_1")); // NOI18N

        stringLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        stringLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stringLabel.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.stringLabel.text_1")); // NOI18N

        deleteStringBut.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.deleteStringBut.text_1")); // NOI18N
        deleteStringBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteStringButActionPerformed(evt);
            }
        });

        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        errorLabel.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.errorLabel.text_1")); // NOI18N

        stringTextField.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.stringTextField.text")); // NOI18N

        DQLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        DQLabel1.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.DQLabel1.text")); // NOI18N

        DQLabel2.setFont(new java.awt.Font("Tahoma", 1, 24));
        DQLabel2.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.DQLabel2.text")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.jLabel5.text_1")); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.jLabel6.text_1")); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.jLabel7.text_1")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText(org.openide.util.NbBundle.getMessage(addCommandStringPanel.class, "addCommandStringPanel.jLabel8.text_1")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(stringLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(selectConstantBut, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                            .addComponent(selectVariableBut, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(selectArrElemBut, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                            .addComponent(selectParameterBut, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(deleteStringBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(selectFunctionBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(inputStringBut, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)))
                            .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(DQLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stringTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DQLabel2)))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectVariableBut)
                    .addComponent(selectParameterBut)
                    .addComponent(inputStringBut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectConstantBut)
                    .addComponent(selectArrElemBut)
                    .addComponent(selectFunctionBut))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(stringLabel)
                        .addComponent(deleteStringBut)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(stringTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(DQLabel1))
                    .addComponent(DQLabel2))
                .addGap(18, 18, 18)
                .addComponent(errorLabel)
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selectConstantButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectConstantButActionPerformed
        addConstant();
}//GEN-LAST:event_selectConstantButActionPerformed

    private void selectVariableButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectVariableButActionPerformed
        addVariable();
}//GEN-LAST:event_selectVariableButActionPerformed

    private void selectFunctionButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectFunctionButActionPerformed
        addFunction();
}//GEN-LAST:event_selectFunctionButActionPerformed

    private void selectParameterButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectParameterButActionPerformed
      addParameter();
}//GEN-LAST:event_selectParameterButActionPerformed

    private void inputStringButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputStringButActionPerformed
    addString();
}//GEN-LAST:event_inputStringButActionPerformed

    private void deleteStringButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteStringButActionPerformed
    clearAll();
    }//GEN-LAST:event_deleteStringButActionPerformed

    private void selectArrElemButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectArrElemButActionPerformed
      addArrayElement();
    }//GEN-LAST:event_selectArrElemButActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DQLabel1;
    private javax.swing.JLabel DQLabel2;
    private javax.swing.JButton deleteStringBut;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JButton inputStringBut;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton selectArrElemBut;
    private javax.swing.JButton selectConstantBut;
    private javax.swing.JButton selectFunctionBut;
    private javax.swing.JButton selectParameterBut;
    private javax.swing.JButton selectVariableBut;
    private javax.swing.JLabel stringLabel;
    private javax.swing.JTextField stringTextField;
    // End of variables declaration//GEN-END:variables

}
