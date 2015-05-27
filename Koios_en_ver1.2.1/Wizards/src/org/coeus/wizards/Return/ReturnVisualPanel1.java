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

package org.coeus.wizards.Return;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards._HelpFuntions.addCommandCharPanel;
import org.coeus.wizards._HelpFuntions.addCommandConditionPanel;
import org.coeus.wizards._HelpFuntions.addCommandExpressionPanel;


import org.coeus.wizards._HelpFuntions.addCommandStringPanel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public final class ReturnVisualPanel1 extends JPanel {
    
    private String objScope=null;
    private String commandName=null;
 
    /** Creates new form ReturnVisualPanel2 */
    public ReturnVisualPanel1(String iObjScope,String iCommandName) {
        initComponents();
        this.objScope=iObjScope;

        this.functionNameLabel.setText(ReturnWizardAction.getDispFunctionName());
        this.functionTypeLabel.setText(ReturnWizardAction.getDispFunctionType());
//        this.functionNameLabel1.setText(ReturnWizardAction.getDispFunctionName());
//        this.dispCategoryLabel.setText(ReturnWizardAction.getDispCategory());
        this.commandName=iCommandName;
        editButton.setEnabled(false);
    }


      @Override
    public String getName() {
        return "Definition of Return Value";
    }

 public void getReturnValue()
    {

        this.functionNameLabel.setText(ReturnWizardAction.getDispFunctionName());
        this.functionTypeLabel.setText(ReturnWizardAction.getDispFunctionType());
  //      this.functionNameLabel1.setText(ReturnWizardAction.getDispFunctionName());
  //      this.dispCategoryLabel.setText(ReturnWizardAction.getDispCategory());

          if (ReturnWizardAction.getDispCategory().equalsIgnoreCase(WizardsDefinitions.EXPRESSION))
                addExpression();
          else if (ReturnWizardAction.getDispCategory().equalsIgnoreCase(WizardsDefinitions.CHARACTER))
               addCharacter();
          else if (ReturnWizardAction.getDispCategory().equalsIgnoreCase(WizardsDefinitions.STRING1))
               addString();
          else if (ReturnWizardAction.getDispCategory().equalsIgnoreCase(WizardsDefinitions.CONDITION))
               addCondition();
    }

 

    public void addExpression()
    {
     addCommandExpressionPanel aEP = new addCommandExpressionPanel(this.objScope,null,this.commandName);
     aEP.getHeaders()[0].setText("Function:");
     aEP.getHeaders()[1].setText(ReturnWizardAction.getDispFunctionName());
     aEP.getHeaders()[2].setText("Type of Return Value:");
     aEP.getHeaders()[3].setText(ReturnWizardAction.getDispFunctionType());

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Defining Return Value of Function "+ReturnWizardAction.getDispFunctionName(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidExpression(NotifyDescriptor.DEFAULT_OPTION))
         {
         ReturnWizardAction.setDispReturnValue(aEP.getDisplayExpression());
         ReturnWizardAction.setObjReturnValue("("+ReturnWizardAction.getObjFunctionType()+") ("+aEP.getObjectExpresssion()+")");

         returnLabel.setText(aEP.getDisplayExpression());
         editButton.setEnabled(true);
        }
       }
  }


    public void updateExpression()
    {
     addCommandExpressionPanel aEP = new addCommandExpressionPanel(this.objScope,returnLabel.getText(),this.commandName);
     aEP.getHeaders()[0].setText("Function:");
     aEP.getHeaders()[1].setText(ReturnWizardAction.getDispFunctionName());
     aEP.getHeaders()[2].setText("Type of Return Value:");
     aEP.getHeaders()[3].setText(ReturnWizardAction.getDispFunctionType());

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Modifying Return Value of Function "+ReturnWizardAction.getDispFunctionName(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidExpression(NotifyDescriptor.OK_CANCEL_OPTION))
         {
         ReturnWizardAction.setDispReturnValue(aEP.getDisplayExpression());
         ReturnWizardAction.setObjReturnValue("("+ReturnWizardAction.getObjFunctionType()+") ("+aEP.getObjectExpresssion()+")");

         returnLabel.setText(aEP.getDisplayExpression());
         editButton.setEnabled(true);
        }
       }
   }

  

    public void addCharacter()
    {
     addCommandCharPanel aEP = new addCommandCharPanel(this.objScope,null,this.commandName);
     aEP.getHeaders()[0].setText("Function:");
     aEP.getHeaders()[1].setText(ReturnWizardAction.getDispFunctionName());
     aEP.getHeaders()[2].setText("Type of Return Value:");
     aEP.getHeaders()[3].setText(ReturnWizardAction.getDispFunctionType());

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Defining Return Value of Function "+ReturnWizardAction.getDispFunctionName(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidInput(NotifyDescriptor.DEFAULT_OPTION))
         {
  ///////////////////////////////////
              if (aEP.getCharTextField().isVisible())
               {
                   aEP.putCharInLabel();
                   d =new NotifyDescriptor.Confirmation(aEP,aEP.getName(),
                   NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

                    if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
                         {
                             if (aEP.checkValidInput(NotifyDescriptor.DEFAULT_OPTION))
                               {
                                 ReturnWizardAction.setDispReturnValue(aEP.getDisplayChar());
                                 ReturnWizardAction.setObjReturnValue("("+ReturnWizardAction.getObjFunctionType()+") ("+aEP.getObjChar()+")");

                                 returnLabel.setText(aEP.getDisplayChar());
                                 editButton.setEnabled(true);  
                                }
                           }
                        }
                    else
                    {
                     ////////////////////////////////////
                     ReturnWizardAction.setDispReturnValue(aEP.getDisplayChar());
                     ReturnWizardAction.setObjReturnValue("("+ReturnWizardAction.getObjFunctionType()+") ("+aEP.getObjChar()+")");

                     returnLabel.setText(aEP.getDisplayChar());
                     editButton.setEnabled(true);
                    }

            }
       }
   }

    public void updateCharacter()
    {
     addCommandCharPanel aEP = new addCommandCharPanel(this.objScope,returnLabel.getText(),this.commandName);
     aEP.getHeaders()[0].setText("Function:");
     aEP.getHeaders()[1].setText(ReturnWizardAction.getDispFunctionName());
     aEP.getHeaders()[2].setText("Type of Return Value:");
     aEP.getHeaders()[3].setText(ReturnWizardAction.getDispFunctionType());

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Modifying Return Value of Function "+ReturnWizardAction.getDispFunctionName(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidInput(NotifyDescriptor.OK_CANCEL_OPTION)  && !aEP.getObjNames().isEmpty())
         {
  ///////////////////////////////////
              if (aEP.getCharTextField().isVisible())
               {
                   aEP.putCharInLabel();
                   d =new NotifyDescriptor.Confirmation(aEP,aEP.getName(),
                   NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

                    if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
                         {
                             if (aEP.checkValidInput(NotifyDescriptor.OK_CANCEL_OPTION))
                               {
                                 ReturnWizardAction.setDispReturnValue(aEP.getDisplayChar());
                                 ReturnWizardAction.setObjReturnValue("("+ReturnWizardAction.getObjFunctionType()+") ("+aEP.getObjChar()+")");

                                 returnLabel.setText(aEP.getDisplayChar());
                                 editButton.setEnabled(true);
                               }
                        }
                    }
                    else
                    {
               ////////////////////////////////////
                     ReturnWizardAction.setDispReturnValue(aEP.getDisplayChar());
                     ReturnWizardAction.setObjReturnValue("("+ReturnWizardAction.getObjFunctionType()+") ("+aEP.getObjChar()+")");

                     returnLabel.setText(aEP.getDisplayChar());
                     editButton.setEnabled(true);
                    }
              }
         }
    }

   public void addString()
    {
     addCommandStringPanel aSP = new addCommandStringPanel(this.objScope,null,this.commandName);
     aSP.getHeaders()[0].setText("Function:");
     aSP.getHeaders()[1].setText(ReturnWizardAction.getDispFunctionName());
     aSP.getHeaders()[2].setText("Type of Return Value:");
     aSP.getHeaders()[3].setText(ReturnWizardAction.getDispFunctionType());

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aSP,
      "Defining Return Value of Function "+ReturnWizardAction.getDispFunctionName(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aSP.checkValidInput(NotifyDescriptor.DEFAULT_OPTION))
         {
         ReturnWizardAction.setDispReturnValue(aSP.getDisplayString());
//         ReturnWizardAction.setObjReturnValue("("+ReturnWizardAction.getObjFunctionType()+") ("+aSP.getObjString()+")");
         ReturnWizardAction.setObjReturnValue(aSP.getObjString());

         returnLabel.setText(aSP.getDisplayString());
         editButton.setEnabled(true);
        }
       }
   }

       public void updateString()
    {
     addCommandStringPanel aSP = new addCommandStringPanel(this.objScope,returnLabel.getText(),this.commandName);
     aSP.getHeaders()[0].setText("Function:");
     aSP.getHeaders()[1].setText(ReturnWizardAction.getDispFunctionName());
     aSP.getHeaders()[2].setText("Type of Return Value:");
     aSP.getHeaders()[3].setText(ReturnWizardAction.getDispFunctionType());

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aSP,
      "Modifying Return Value of Function "+ReturnWizardAction.getDispFunctionName(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aSP.checkValidInput(NotifyDescriptor.OK_CANCEL_OPTION)  && !aSP.getObjNames().isEmpty())
         {
         ReturnWizardAction.setDispReturnValue(aSP.getDisplayString());
//         ReturnWizardAction.setObjReturnValue("("+ReturnWizardAction.getObjFunctionType()+") ("+aSP.getObjString()+")");
         ReturnWizardAction.setObjReturnValue(aSP.getObjString());

         returnLabel.setText(aSP.getDisplayString());
         editButton.setEnabled(true);
        }
      }
    }


    public void addCondition()
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,null,this.commandName);
     aCP.getHeaders()[0].setText("Function:");
     aCP.getHeaders()[1].setText(ReturnWizardAction.getDispFunctionName());
     aCP.getHeaders()[2].setText("Type of Return Value:");
     aCP.getHeaders()[3].setText(ReturnWizardAction.getDispFunctionType());

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Defining Return Value of Function "+ReturnWizardAction.getDispFunctionName(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.DEFAULT_OPTION))
         {
         ReturnWizardAction.setDispReturnValue(aCP.getDisplayCondition());
         ReturnWizardAction.setObjReturnValue("("+ReturnWizardAction.getObjFunctionType()+") ("+aCP.getObjectCondition()+")");

         returnLabel.setText(aCP.getDisplayCondition());
         editButton.setEnabled(true);
        }
      }
    }




    public void updateCondition()
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,returnLabel.getText(),this.commandName);
     aCP.getHeaders()[0].setText("Function:");
     aCP.getHeaders()[1].setText(ReturnWizardAction.getDispFunctionName());
     aCP.getHeaders()[2].setText("Return Value Type:");
     aCP.getHeaders()[3].setText(ReturnWizardAction.getDispFunctionType());

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Modifying Return Value of Function "+ReturnWizardAction.getDispFunctionName(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.OK_CANCEL_OPTION))
         {
         ReturnWizardAction.setDispReturnValue(aCP.getDisplayCondition());
         ReturnWizardAction.setObjReturnValue("("+ReturnWizardAction.getObjFunctionType()+") ("+aCP.getObjectCondition()+")");

         returnLabel.setText(aCP.getDisplayCondition());
         editButton.setEnabled(true);
        }
      }
    }


    public void editReturnValue()
    {
       if (ReturnWizardAction.getDispCategory().equalsIgnoreCase(WizardsDefinitions.EXPRESSION))
              updateExpression();
      else if (ReturnWizardAction.getDispCategory().equalsIgnoreCase(WizardsDefinitions.CHARACTER))
             updateCharacter();
      else if (ReturnWizardAction.getDispCategory().equalsIgnoreCase(WizardsDefinitions.STRING1))
             updateString();
      else if (ReturnWizardAction.getDispCategory().equalsIgnoreCase(WizardsDefinitions.CONDITION))
            updateCondition();
  }

public JLabel getReturnLabel()
{return this.returnLabel;}

public JButton getEditButton()
{return this.editButton;}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        functionTypeLabel = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        returnLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        functionNameLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ReturnVisualPanel1.class, "ReturnVisualPanel1.jLabel1.text")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(ReturnVisualPanel1.class, "ReturnVisualPanel1.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        functionTypeLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        org.openide.awt.Mnemonics.setLocalizedText(functionTypeLabel, org.openide.util.NbBundle.getMessage(ReturnVisualPanel1.class, "ReturnVisualPanel1.functionTypeLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(functionTypeLabel)
                .addContainerGap(239, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(functionTypeLabel)
        );

        org.openide.awt.Mnemonics.setLocalizedText(editButton, org.openide.util.NbBundle.getMessage(ReturnVisualPanel1.class, "ReturnVisualPanel1.editButton.text")); // NOI18N
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(ReturnVisualPanel1.class, "ReturnVisualPanel1.jPanel3.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        returnLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(returnLabel, org.openide.util.NbBundle.getMessage(ReturnVisualPanel1.class, "ReturnVisualPanel1.returnLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(returnLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(returnLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(ReturnVisualPanel1.class, "ReturnVisualPanel1.jPanel4.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        functionNameLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        org.openide.awt.Mnemonics.setLocalizedText(functionNameLabel, org.openide.util.NbBundle.getMessage(ReturnVisualPanel1.class, "ReturnVisualPanel1.functionNameLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(functionNameLabel)
                .addContainerGap(374, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(functionNameLabel)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, 0, 431, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ReturnVisualPanel1.class, "ReturnVisualPanel1.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(ReturnVisualPanel1.class, "ReturnVisualPanel1.jLabel3.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        editReturnValue();
}//GEN-LAST:event_editButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editButton;
    private javax.swing.JLabel functionNameLabel;
    private javax.swing.JLabel functionTypeLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel returnLabel;
    // End of variables declaration//GEN-END:variables
}

