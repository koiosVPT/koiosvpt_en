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
 * warningsErrorsPanel.java
 *
 * Created on 1 Οκτ 2009, 5:26:40 μμ
 */

package org.coeus.vteditor.actions;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import org.coeus.vteditor.Checks;

/**
 *
 * @author Jrd
 */
public class warningsErrorsPanel extends javax.swing.JPanel {

private int errorsNum=0;
private int warningsNum=0;
private String message;


    /** Creates new form warningsErrorsPanel */
    public warningsErrorsPanel(Checks checks) {
        initComponents();
        String text="";


        warningsPanel.setVisible(false);
        errorsPanel.setVisible(false);
        messageLabel.setIcon(new ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/ok16.png")));
        messageLabel.setText("Congratulations! The program contains no warnings and no errors!");
        messageLabel1.setText("You can continue with compiling and executing the program.");
        message="Program contains no errors – Continue with compiling!";

        if (checks.areWarnings())
        {
            warningsPanel.setVisible(true);
            
            this.unusedLabel.setVisible(false);
            this.unusedTextArea.setVisible(false);
            this.jScrollPane1.setVisible(false);
       

            this.uninitializedVariableLabel.setVisible(false);
            this.uninitializedVariableTextArea.setVisible(false);
           this.jScrollPane2.setVisible(false);
          
            this.uninitializedArrayLabel.setVisible(false);
            this.uninitializedArrayLabel2.setVisible(false);
            this.uninitializedArrayTextArea.setVisible(false);
            this.jScrollPane3.setVisible(false);
           
            if (!checks.getUnusedCategoryName().isEmpty())
            {
            this.unusedLabel.setVisible(true);
            this.unusedTextArea.setVisible(true);
            this.jScrollPane1.setVisible(true);
            this.jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
            this.unusedTextArea.setLineWrap(true);
            this.unusedTextArea.setWrapStyleWord(true);
            this.unusedTextArea.setEditable(false);

            text="";
            for (String s:checks.getUnusedCategoryName())
                text=text+"\n"+s;
            this.unusedTextArea.setText(text);

           
            warningsNum=warningsNum+checks.getUnusedCategoryName().size();
            }

            if (!checks.getUninitializedVariableName().isEmpty())
            {
            this.uninitializedVariableLabel.setVisible(true);
            this.uninitializedVariableTextArea.setVisible(true);
            this.uninitializedVariableTextArea.setBorder(BorderFactory.createEmptyBorder());
            this.jScrollPane2.setVisible(true);
            this.jScrollPane2.setBorder(BorderFactory.createEmptyBorder());
            this.uninitializedVariableTextArea.setLineWrap(true);
            this.uninitializedVariableTextArea.setWrapStyleWord(true);
            this.uninitializedVariableTextArea.setEditable(false);

            text="";
            for (String s:checks.getUninitializedVariableName())
                text=text+"\n"+s;
            this.uninitializedVariableTextArea.setText(text);

            warningsNum=warningsNum+checks.getUninitializedVariableName().size();
            }

            if (!checks.getUninitializedArrayName().isEmpty())
            {
            this.uninitializedArrayLabel.setVisible(true);
            this.uninitializedArrayLabel2.setVisible(true);
            this.uninitializedArrayTextArea.setVisible(true);
            this.jScrollPane3.setVisible(true);
            this.jScrollPane3.setBorder(BorderFactory.createEmptyBorder());
            this.uninitializedArrayTextArea.setLineWrap(true);
            this.uninitializedArrayTextArea.setWrapStyleWord(true);
            this.uninitializedArrayTextArea.setEditable(false);

             text="";
            for (String s:checks.getUninitializedArrayName())
                text=text+"\n"+s;
            this.uninitializedArrayTextArea.setText(text);

            this.messageLabel.setBackground(UIManager.getColor("Label.background"));
            warningsNum=warningsNum+checks.getUninitializedArrayName().size();
            }

        this.warningsLabel.setText(Integer.toString(warningsNum) + " WARNINGS");
        messageLabel.setIcon(new ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/warning.gif")));
        messageLabel.setText("Program contains "+Integer.toString(warningsNum)+" warning(s)!");
        messageLabel1.setText("If the program is executed without fixing the warning(s), it is possible that it will not be executed correctly.");

        message="Program contains "+Integer.toString(warningsNum)+" warning(s)!";

        }


        if (checks.areErrors())
        {
            errorsPanel.setVisible(true);

            this.manyReturnsLabel.setVisible(false);
            this.manyReturnsLabel2.setVisible(false);
            this.manyReturnsTextArea.setVisible(false);
            this.jScrollPane4.setVisible(false);

            this.commandsAfterReturnLabel.setVisible(false);
            this.commandsAfterReturnTextArea.setVisible(false);
            this.jScrollPane5.setVisible(false);

            this.returnMissingLabel.setVisible(false);
            this.returnMissingTextArea.setVisible(false);
            this.jScrollPane6.setVisible(false);


            if (!checks.getTooManyReturn().isEmpty())
            {
            this.manyReturnsLabel.setVisible(true);
            this.manyReturnsLabel2.setVisible(true);
            this.manyReturnsTextArea.setVisible(true);
            this.jScrollPane4.setVisible(true);
            this.jScrollPane4.setBorder(BorderFactory.createEmptyBorder());
            this.manyReturnsTextArea.setLineWrap(true);
            this.manyReturnsTextArea.setWrapStyleWord(true);
            this.manyReturnsTextArea.setEditable(false);

             text="";
            for (String s:checks.getTooManyReturn())
                text=text+"\n"+s;
            this.manyReturnsTextArea.setText(text);

            errorsNum=errorsNum+checks.getTooManyReturn().size();
            }

            if (!checks.getCommandsAfterReturn().isEmpty())
            {
            this.commandsAfterReturnLabel.setVisible(true);
            this.commandsAfterReturnTextArea.setVisible(true);
            this.jScrollPane5.setVisible(true);
            this.jScrollPane5.setBorder(BorderFactory.createEmptyBorder());
            this.commandsAfterReturnTextArea.setLineWrap(true);
            this.commandsAfterReturnTextArea.setWrapStyleWord(true);
            this.commandsAfterReturnTextArea.setEditable(false);

             text="";
            for (String s:checks.getCommandsAfterReturn())
                text=text+"\n"+s;
            this.commandsAfterReturnTextArea.setText(text);


            errorsNum=errorsNum+checks.getCommandsAfterReturn().size();
            }

            if (!checks.getReturnMissing().isEmpty())
            {
            this.returnMissingLabel.setVisible(true);
            this.returnMissingTextArea.setVisible(true);
            this.jScrollPane6.setVisible(true);
            this.jScrollPane6.setBorder(BorderFactory.createEmptyBorder());
            this.returnMissingTextArea.setLineWrap(true);
            this.returnMissingTextArea.setWrapStyleWord(true);
            this.returnMissingTextArea.setEditable(false);

             text="";
            for (String s:checks.getReturnMissing())
                text=text+"\n"+s;
            this.returnMissingTextArea.setText(text);


            errorsNum=errorsNum+checks.getReturnMissing().size();
            }
        this.errorsLabel.setText(Integer.toString(errorsNum)+" ERRORS");
        messageLabel.setIcon(new ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/error.gif")));
        messageLabel.setText("Program contains "+ Integer.toString(errorsNum) +" error(s)!");
        messageLabel1.setText("You cannot execute a program with errors.Correct all errors before continuing with compiling and executing the program.");

        message="Program contains "+ Integer.toString(errorsNum) +" error(s)!";
        }
       
    }

    public String getMessage() {
        return message;
    }




    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        warningsPanel = new javax.swing.JPanel();
        warningsLabel = new javax.swing.JLabel();
        unusedLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        unusedTextArea = new javax.swing.JTextArea();
        uninitializedVariableLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        uninitializedVariableTextArea = new javax.swing.JTextArea();
        uninitializedArrayLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        uninitializedArrayTextArea = new javax.swing.JTextArea();
        uninitializedArrayLabel2 = new javax.swing.JLabel();
        errorsPanel = new javax.swing.JPanel();
        errorsLabel = new javax.swing.JLabel();
        manyReturnsLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        manyReturnsTextArea = new javax.swing.JTextArea();
        commandsAfterReturnLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        commandsAfterReturnTextArea = new javax.swing.JTextArea();
        returnMissingLabel = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        returnMissingTextArea = new javax.swing.JTextArea();
        manyReturnsLabel = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();
        messageLabel1 = new javax.swing.JLabel();

        warningsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        warningsLabel.setBackground(new java.awt.Color(255, 255, 0));
        warningsLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        warningsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        warningsLabel.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.warningsLabel.text")); // NOI18N
        warningsLabel.setOpaque(true);

        unusedLabel.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.unusedLabel.text")); // NOI18N

        unusedTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        unusedTextArea.setColumns(20);
        unusedTextArea.setFont(new java.awt.Font("Tahoma", 1, 11));
        unusedTextArea.setRows(5);
        unusedTextArea.setBorder(null);
        jScrollPane1.setViewportView(unusedTextArea);

        uninitializedVariableLabel.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.uninitializedVariableLabel.text")); // NOI18N

        uninitializedVariableTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        uninitializedVariableTextArea.setColumns(20);
        uninitializedVariableTextArea.setFont(new java.awt.Font("Tahoma", 1, 11));
        uninitializedVariableTextArea.setRows(5);
        uninitializedVariableTextArea.setBorder(null);
        jScrollPane2.setViewportView(uninitializedVariableTextArea);

        uninitializedArrayLabel.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.uninitializedArrayLabel.text")); // NOI18N

        uninitializedArrayTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        uninitializedArrayTextArea.setColumns(20);
        uninitializedArrayTextArea.setFont(new java.awt.Font("Tahoma", 1, 11));
        uninitializedArrayTextArea.setRows(5);
        uninitializedArrayTextArea.setBorder(null);
        jScrollPane3.setViewportView(uninitializedArrayTextArea);

        uninitializedArrayLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        uninitializedArrayLabel2.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.uninitializedArrayLabel2.text")); // NOI18N

        javax.swing.GroupLayout warningsPanelLayout = new javax.swing.GroupLayout(warningsPanel);
        warningsPanel.setLayout(warningsPanelLayout);
        warningsPanelLayout.setHorizontalGroup(
            warningsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(warningsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(warningsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(unusedLabel)
                .addContainerGap(307, Short.MAX_VALUE))
            .addGroup(warningsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(warningsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(uninitializedVariableLabel)
                .addContainerGap(315, Short.MAX_VALUE))
            .addGroup(warningsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(warningsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(uninitializedArrayLabel)
                .addContainerGap(339, Short.MAX_VALUE))
            .addGroup(warningsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(warningsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(uninitializedArrayLabel2)
                .addContainerGap(342, Short.MAX_VALUE))
        );
        warningsPanelLayout.setVerticalGroup(
            warningsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(warningsPanelLayout.createSequentialGroup()
                .addComponent(warningsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unusedLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(uninitializedVariableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(uninitializedArrayLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(uninitializedArrayLabel2)
                .addContainerGap())
        );

        errorsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        errorsLabel.setBackground(new java.awt.Color(255, 0, 0));
        errorsLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        errorsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorsLabel.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.errorsLabel.text")); // NOI18N
        errorsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        errorsLabel.setOpaque(true);

        manyReturnsLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        manyReturnsLabel2.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.manyReturnsLabel2.text")); // NOI18N

        manyReturnsTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        manyReturnsTextArea.setColumns(20);
        manyReturnsTextArea.setFont(new java.awt.Font("Tahoma", 1, 11));
        manyReturnsTextArea.setRows(5);
        manyReturnsTextArea.setBorder(null);
        jScrollPane4.setViewportView(manyReturnsTextArea);

        commandsAfterReturnLabel.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.commandsAfterReturnLabel.text")); // NOI18N

        commandsAfterReturnTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        commandsAfterReturnTextArea.setColumns(20);
        commandsAfterReturnTextArea.setFont(new java.awt.Font("Tahoma", 1, 11));
        commandsAfterReturnTextArea.setRows(5);
        commandsAfterReturnTextArea.setBorder(null);
        jScrollPane5.setViewportView(commandsAfterReturnTextArea);

        returnMissingLabel.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.returnMissingLabel.text")); // NOI18N

        returnMissingTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        returnMissingTextArea.setColumns(20);
        returnMissingTextArea.setFont(new java.awt.Font("Tahoma", 1, 11));
        returnMissingTextArea.setRows(5);
        returnMissingTextArea.setBorder(null);
        jScrollPane6.setViewportView(returnMissingTextArea);

        manyReturnsLabel.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.manyReturnsLabel.text")); // NOI18N

        javax.swing.GroupLayout errorsPanelLayout = new javax.swing.GroupLayout(errorsPanel);
        errorsPanel.setLayout(errorsPanelLayout);
        errorsPanelLayout.setHorizontalGroup(
            errorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(errorsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
            .addGroup(errorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(manyReturnsLabel2)
                .addContainerGap(131, Short.MAX_VALUE))
            .addGroup(errorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(errorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(manyReturnsLabel)
                .addContainerGap(470, Short.MAX_VALUE))
            .addGroup(errorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(errorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(errorsPanelLayout.createSequentialGroup()
                        .addGroup(errorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(commandsAfterReturnLabel)
                            .addComponent(returnMissingLabel))
                        .addGap(348, 348, 348))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, errorsPanelLayout.createSequentialGroup()
                        .addGroup(errorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        errorsPanelLayout.setVerticalGroup(
            errorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(errorsPanelLayout.createSequentialGroup()
                .addComponent(errorsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manyReturnsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manyReturnsLabel2)
                .addGap(18, 18, 18)
                .addComponent(commandsAfterReturnLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(returnMissingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        messageLabel.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.messageLabel.text")); // NOI18N
        messageLabel.setOpaque(true);

        messageLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        messageLabel1.setText(org.openide.util.NbBundle.getMessage(warningsErrorsPanel.class, "warningsErrorsPanel.messageLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(warningsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(errorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(messageLabel)
                    .addComponent(messageLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(warningsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(errorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(messageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageLabel1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel commandsAfterReturnLabel;
    private javax.swing.JTextArea commandsAfterReturnTextArea;
    private javax.swing.JLabel errorsLabel;
    private javax.swing.JPanel errorsPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel manyReturnsLabel;
    private javax.swing.JLabel manyReturnsLabel2;
    private javax.swing.JTextArea manyReturnsTextArea;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel messageLabel1;
    private javax.swing.JLabel returnMissingLabel;
    private javax.swing.JTextArea returnMissingTextArea;
    private javax.swing.JLabel uninitializedArrayLabel;
    private javax.swing.JLabel uninitializedArrayLabel2;
    private javax.swing.JTextArea uninitializedArrayTextArea;
    private javax.swing.JLabel uninitializedVariableLabel;
    private javax.swing.JTextArea uninitializedVariableTextArea;
    private javax.swing.JLabel unusedLabel;
    private javax.swing.JTextArea unusedTextArea;
    private javax.swing.JLabel warningsLabel;
    private javax.swing.JPanel warningsPanel;
    // End of variables declaration//GEN-END:variables

}
