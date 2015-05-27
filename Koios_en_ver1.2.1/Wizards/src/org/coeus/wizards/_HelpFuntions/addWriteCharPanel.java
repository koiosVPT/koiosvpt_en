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

public final class addWriteCharPanel extends JPanel {
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

      private String commandName="WRITE";
      private boolean charInputByUser=false;


    /** Creates new form ReadVisualPanel1 */
    public addWriteCharPanel (String iObjScope,String in_char) {
        initComponents();
        showAdvanceButtons();
        setAddCharButtonsVisible(false);
        this.objScope=iObjScope;
        this.errorLabel.setText("");

    //customize tool tip apperance
        UIManager.put("ToolTip.background", new ColorUIResource(220, 220, 220)); // The color is #fff7c8.
        Border border = BorderFactory.createLineBorder(new Color(76,79,83)); // The color is #4c4f53.
        UIManager.put("ToolTip.border", border);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(20000);// 20 seconds
        //end customize tool tip apperance

 selectVariableBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
            "To select a character that is stored in a VARIABLE, click the \"" + "<b>Select Variable</b>"+ "\" button.</font></html>");

selectArrElemBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To select a character that is stored in an ARRAY ELEMENT, click the \"" + "<b>Select Array Element</b>"+ "\" button.</font></html>");

selectConstantBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To select a character that is stored in a CONSTANT, click the \"" + "<b>Select Constant</b>"+ "\" button.</font></html>");

selectFunctionBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To select a character that is returned by a FUNCTION, click the \"" + "<b>Select Function</b>"+ "\" button.</font></html>");

selectParameterBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To select a character that is passed by an INPUT PARAMETER, click the \"" + "<b>Select Input Parameter</b>"+ "\" button.</font></html>");
 
 inputCharBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To enter a character from the keyboard, click the \"" + "<b>Enter Character</b>"+ "\" button.</font></html>");

 deleteCharBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To clear the selected character, click the \"" + "<b>Delete Character</b>"+ "\" button.</font></html>");


         gAEL = new getArrayElementsList(this.objScope,WizardsDefinitions.CHAR);
         gPL  = new getParametersList(this.objScope,WizardsDefinitions.CHAR);
         gVL = new getVariablesList(this.objScope,WizardsDefinitions.CHAR);
         gCL = new getConstantsList(this.objScope,WizardsDefinitions.CHAR);
         gFL = new getFunctionsList(WizardsDefinitions.CHAR);

        setSelectButtonsEnabled(true);
        if (in_char!=null)
        {    setSelectButtonsEnabled(false);
             this.charLabel.setText(in_char);
           //  this.objNames.add(in_obj_char);
        }

 //new printLists(messages,dispNames,objNames,dispTypes,objTypes,dispCategory);

    }


     @Override
    public String getName() {
        return "CHARACTER Selection";
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


      private void setAddCharButtonsVisible(boolean tf)
        {
          

          this.deleteCharBut.setVisible(!tf);
          this.charLabel.setVisible(!tf);

          this.QLabel1.setVisible(tf);
          this.QLabel2.setVisible(tf);
          this.charTextField.setVisible(tf);
        }



   private void setSelectButtonsEnabled(boolean tf)
    {
      this.selectArrElemBut.setEnabled(tf);
      this.selectConstantBut.setEnabled(tf);
      this.selectFunctionBut.setEnabled(tf);
      this.selectParameterBut.setEnabled(tf);
      this.selectVariableBut.setEnabled(tf);
      this.inputCharBut.setEnabled(tf);
      this.deleteCharBut.setEnabled(!tf);

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

          charLabel.setText(gCLP.getDName());
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

     charLabel.setText(gPLPanel.getDName());
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

             charLabel.setText(fcwa.getDisplayFunctionCall());
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

           charLabel.setText(gVLPanel.getDName());
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

       charLabel.setText(gAELPanel.getReturnDisplaeStateMent());
      setSelectButtonsEnabled(false);
         }
      }
   }
}

//private void addChar()
//{
//  addCharPanel aCP = new addCharPanel();
//
//   NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,aCP.getName(),
//       NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);
//
//   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
//   {
//         if (aCP.checkInputChar())
//         {
//           dispNames.add(aCP.getChar());
//           objNames.add(aCP.getChar());
//           dispTypes.add(WizardsDefinitions.CHAR1);
//           objTypes.add(WizardsDefinitions.CHAR);
//           dispCategory.add(WizardsDefinitions.CHAR1);
//
//        charLabel.setText(aCP.getChar());
//        setSelectButtonsEnabled(false);
//       }
//   }
//}

private void addChar()
{
   this.errorLabel.setText("");
   this.charTextField.setText("");
   setAddCharButtonsVisible(true);

   this.selectArrElemBut.setEnabled(false);
   this.selectConstantBut.setEnabled(false);
   this.selectFunctionBut.setEnabled(false);
   this.selectParameterBut.setEnabled(false);
   this.selectVariableBut.setEnabled(false);
   this.inputCharBut.setEnabled(false);
   this.deleteCharBut.setEnabled(false);

   new GetFocus(charTextField);
}

//private void validConditions ()
//{   result=true;
//    if (this.charLabel.getText().isEmpty() ||
//            this.charLabel.getText().equalsIgnoreCase("[ΚΑΝΕΝΑΣ]"))
//    {
//     result=false;
//     error[0]="Δεν έχει επιλεγεί ΧΑΡΑΚΤΗΡΑΣ!Η Έκφρασή δεν μπορέι να είναι κενή.";
//     error[1]="";
//    }
//}


public void putCharInLabel()
{
           this.errorLabel.setText("");
           this.charLabel.setText("\'"+ this.charTextField.getText()+"\'") ;

           dispNames.add("\'"+ this.charTextField.getText()+"\'");
           objNames.add("\'"+ this.charTextField.getText()+"\'");
           dispTypes.add(WizardsDefinitions.CHAR1);
           objTypes.add(WizardsDefinitions.CHAR);
           dispCategory.add(WizardsDefinitions.CHAR1);

           setSelectButtonsEnabled(false);
           setAddCharButtonsVisible(false);
}




private void validConditions ()
{   result=true;

    if ( this.charTextField.isVisible())
    {
    if (this.charTextField.getText().isEmpty())
       {
         result=false;
         error[0]="You have not entered a character!";
         error[1]="";
         new GetFocus(this.charTextField);
       }
    ///JUST ONE CHARACTER
    else if ((this.charTextField.getText().length() >= 2)) {// || (!(val.charAt(0) >= 48 && val.charAt(0) <= 57))) {
         result=false;
         error[0]="You have entered "+charTextField.getText().length()+" characters. You can enter only one character!";
         error[1]=charTextField.getText().substring(1,charTextField.getText().length());
         new GetFocus(this.charTextField);
         }
    else
        {
        this.putCharInLabel();
        charInputByUser=true;
      }
    }
    else
    {
     if (this.charLabel.getText().isEmpty() ||
         this.charLabel.getText().equalsIgnoreCase("[NONE]") )
         {
           result=false;
           error[0]="You have not selected a CHARACTER item!Make a selection and then click OK.";
           error[1]="";
         }
    }
}


public boolean checkValidInput(int buttonsOptions)
{
 NotifyDescriptor d=null;
 Object answer=null;

 validConditions();
              while(!result || charInputByUser)
             {
              if (!result)
              {
                d =new NotifyDescriptor.Confirmation(this,this.getName(),
                buttonsOptions,NotifyDescriptor.PLAIN_MESSAGE);
                this.errorLabel.setText(error[0]);
               }
             else //if (charInputByUser)
                {
                  d =new NotifyDescriptor.Confirmation(this,this.getName(),
                   buttonsOptions,NotifyDescriptor.PLAIN_MESSAGE);

                   this.errorLabel.setText("");
                   charInputByUser=false;
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
charLabel.setText("[NONE]");
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


public JTextField getCharTextField()
{ return this.charTextField;}


public String getDisplayChar()
{ return charLabel.getText();}

public String getObjChar()
//{ return charLabel.getText();}
{ return this.objNames.getFirst();}

 public String getDType()
 {return WizardsDefinitions.CHAR1;}

 public String getOType()
 {return WizardsDefinitions.CHAR;}


//public JLabel [] getHeaders()
//{return new JLabel [] {this.jLabel5,this.jLabel6,this.jLabel7,this.jLabel8};}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectConstantBut = new javax.swing.JButton();
        selectVariableBut = new javax.swing.JButton();
        selectFunctionBut = new javax.swing.JButton();
        selectArrElemBut = new javax.swing.JButton();
        inputCharBut = new javax.swing.JButton();
        selectParameterBut = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        charLabel = new javax.swing.JLabel();
        deleteCharBut = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();
        QLabel1 = new javax.swing.JLabel();
        charTextField = new javax.swing.JTextField();
        QLabel2 = new javax.swing.JLabel();

        selectConstantBut.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.selectConstantBut.text_1")); // NOI18N
        selectConstantBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectConstantButActionPerformed(evt);
            }
        });

        selectVariableBut.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.selectVariableBut.text_1")); // NOI18N
        selectVariableBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectVariableButActionPerformed(evt);
            }
        });

        selectFunctionBut.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.selectFunctionBut.text_1")); // NOI18N
        selectFunctionBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFunctionButActionPerformed(evt);
            }
        });

        selectArrElemBut.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.selectArrElemBut.text_1")); // NOI18N
        selectArrElemBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectArrElemButActionPerformed(evt);
            }
        });

        inputCharBut.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.inputCharBut.text_1")); // NOI18N
        inputCharBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputCharButActionPerformed(evt);
            }
        });

        selectParameterBut.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.selectParameterBut.text_1")); // NOI18N
        selectParameterBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectParameterButActionPerformed(evt);
            }
        });

        jLabel2.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.jLabel2.text_1")); // NOI18N

        charLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        charLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        charLabel.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.charLabel.text_1")); // NOI18N

        deleteCharBut.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.deleteCharBut.text_1")); // NOI18N
        deleteCharBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCharButActionPerformed(evt);
            }
        });

        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        errorLabel.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.errorLabel.text_1")); // NOI18N

        QLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        QLabel1.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.QLabel1.text_1")); // NOI18N

        charTextField.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.charTextField.text")); // NOI18N
        charTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charTextFieldActionPerformed(evt);
            }
        });

        QLabel2.setFont(new java.awt.Font("Tahoma", 1, 24));
        QLabel2.setText(org.openide.util.NbBundle.getMessage(addWriteCharPanel.class, "addWriteCharPanel.QLabel2.text_1")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(selectVariableBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(selectConstantBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(selectArrElemBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(selectParameterBut, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(charLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(QLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(charTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(QLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(deleteCharBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputCharBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectFunctionBut, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectVariableBut)
                    .addComponent(selectParameterBut)
                    .addComponent(inputCharBut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectConstantBut)
                    .addComponent(selectArrElemBut)
                    .addComponent(selectFunctionBut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(charLabel)
                        .addComponent(QLabel1)
                        .addComponent(charTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(QLabel2)
                        .addComponent(jLabel2))
                    .addComponent(deleteCharBut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void selectArrElemButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectArrElemButActionPerformed
        addArrayElement();
}//GEN-LAST:event_selectArrElemButActionPerformed

    private void inputCharButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputCharButActionPerformed
        addChar();
}//GEN-LAST:event_inputCharButActionPerformed

    private void selectParameterButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectParameterButActionPerformed
        addParameter();
}//GEN-LAST:event_selectParameterButActionPerformed

    private void deleteCharButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCharButActionPerformed
        clearAll();
}//GEN-LAST:event_deleteCharButActionPerformed

    private void charTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_charTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel QLabel1;
    private javax.swing.JLabel QLabel2;
    private javax.swing.JLabel charLabel;
    private javax.swing.JTextField charTextField;
    private javax.swing.JButton deleteCharBut;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JButton inputCharBut;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton selectArrElemBut;
    private javax.swing.JButton selectConstantBut;
    private javax.swing.JButton selectFunctionBut;
    private javax.swing.JButton selectParameterBut;
    private javax.swing.JButton selectVariableBut;
    // End of variables declaration//GEN-END:variables

}
