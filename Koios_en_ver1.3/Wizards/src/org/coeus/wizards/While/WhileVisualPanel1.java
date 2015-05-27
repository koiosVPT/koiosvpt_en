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


package org.coeus.wizards.While;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards._HelpFuntions.addCommandConditionPanel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public final class WhileVisualPanel1 extends JPanel {

    private String objScope=null;
    private boolean doWhile=false;
     private String commandName=null;

    /** Creates new form WhileVisualPanel2 */
    public WhileVisualPanel1(String iObjScope,boolean idoWhile) {
        initComponents();
        this.objScope=iObjScope;
        this.doWhile=idoWhile;

        if (doWhile)
          this.commandName=WizardsDefinitions.DOWHILE;
        else
          this.commandName=WizardsDefinitions.WHILE;

        initLabels();
        editButton.setEnabled(false);
    }


      @Override
    public String getName() {
        return "Definition of Logical Condition";
    }

 public void initLabels()
 {
   if (doWhile)
    {
     this.jLabel1.setText("is valid, the command(s) in this");
     this.jLabel2.setText("DO..WHILE");
     this.jLabel3.setText("iteration statement will be executed repeatedly.");
  //   this.jLabel4.setText("ΟΣΟ ισχύει η παρακάτω ΛΟΓΙΚΗ ΣΥΝΘΗΚΗ :");
     this.whileLabel.setText("[NO CONDITION]");
   }
   else
    {
    this.jLabel1.setText("is valid, the command(s) in this");
    this.jLabel2.setText("WHILE..REPEAT");
    this.jLabel3.setText("iteration statement will be executed repeatedly.");
  //  this.jLabel4.setText("ΟΣΟ ισχύει η παρακάτω ΛΟΓΙΚΗ ΣΥΝΘΗΚΗ :");
    this.whileLabel.setText("[NO CONDITION]");
    }
 }

 public void getWhileValue()
    {
       initLabels();
//       boolean conditionCreated=addCondition();
//       while( !conditionCreated)
//          conditionCreated=
       addCondition();
    }


    public void addCondition()
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,null,this.commandName);
     aCP.getHeaders()[0].setText("Command:");
     aCP.getHeaders()[1].setText(this.commandName);
     aCP.getHeaders()[2].setVisible(false);
     aCP.getHeaders()[3].setVisible(false);

    
      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Defining the Logical Condition of "+this.jLabel2.getText(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

     Object answer= DialogDisplayer.getDefault().notify(d);
         
      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.DEFAULT_OPTION))
         {
         WhileWizardAction.setDispWhileValue(aCP.getDisplayCondition());
         WhileWizardAction.setObjWhileValue(aCP.getObjectCondition());

         whileLabel.setText(aCP.getDisplayCondition());
         editButton.setEnabled(true);
         }
      }
      

    }




    public void updateCondition()
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,whileLabel.getText(),this.commandName);
     aCP.getHeaders()[0].setText("Command:");
     aCP.getHeaders()[1].setText(this.commandName);
     aCP.getHeaders()[2].setVisible(false);
     aCP.getHeaders()[3].setVisible(false);


      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Modifying the Logical Condition of "+this.jLabel2.getText(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.OK_CANCEL_OPTION))
         {
         WhileWizardAction.setDispWhileValue(aCP.getDisplayCondition());
         WhileWizardAction.setObjWhileValue(aCP.getObjectCondition());

         whileLabel.setText(aCP.getDisplayCondition());
         editButton.setEnabled(true);
        }
      }
    }


    public void editWhileValue()
    {
      updateCondition();
     }

public JLabel getWhileLabel()
{return this.whileLabel;}

public JButton getEditButton()
{return this.editButton;}


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        whileLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(WhileVisualPanel1.class, "WhileVisualPanel1.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        whileLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        org.openide.awt.Mnemonics.setLocalizedText(whileLabel, org.openide.util.NbBundle.getMessage(WhileVisualPanel1.class, "WhileVisualPanel1.whileLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(WhileVisualPanel1.class, "WhileVisualPanel1.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(WhileVisualPanel1.class, "WhileVisualPanel1.jLabel3.text")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(WhileVisualPanel1.class, "WhileVisualPanel1.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(editButton, org.openide.util.NbBundle.getMessage(WhileVisualPanel1.class, "WhileVisualPanel1.editButton.text")); // NOI18N
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(whileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jLabel3))
                .addGap(57, 57, 57))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(245, Short.MAX_VALUE)
                .addComponent(editButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(whileLabel)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(editButton))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        editWhileValue();
}//GEN-LAST:event_editButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel whileLabel;
    // End of variables declaration//GEN-END:variables
}

