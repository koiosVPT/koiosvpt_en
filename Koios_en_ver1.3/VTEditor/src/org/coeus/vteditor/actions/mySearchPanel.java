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
 * mySearchPanel.java
 *
 * Created on 3 Σεπ 2009, 2:26:05 πμ
 */

package org.coeus.vteditor.actions;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.coeus.vteditor.btvnodes.RootNode;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Jrd
 */
public class mySearchPanel extends javax.swing.JPanel {

    private LinkedList<Node> serachResults=null;
    private RootNode rootNode=null;
    private ExplorerManager explorerManager=null;
    private DefaultListModel listModel = null;
    private int selection=-1;

    /** Creates new form mySearchPanel */
    public mySearchPanel(RootNode rn,ExplorerManager em) {
        initComponents();
        this.rootNode=rn;
        this.explorerManager=em;
        this.serachResults=new LinkedList();
        
        this.listModel = new DefaultListModel();
        this.resultsList.setModel(listModel);
        setUpDownButtonsEnabled(false);


        resultsList.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
                int sel;
                sel=resultsList.getSelectedIndex();
                if (sel>-1)
                {
                  selection=sel;
                  checkUpDownButtons();
                  setSelectedNode();
                }
            }
        });


           resultsList.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                checkUpDownButtons(); }
            public void focusLost(FocusEvent e) {
              //  setUpDownButtonsEnabled(false);
            }
        });

   resultsList.addMouseListener(new MouseListener () {

            public void mouseClicked(MouseEvent e) {
               if (selection>-1)
                //  if(e.getClickCount() == 2)
                       setSelectedNode();
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }



        });
    }

    private void setSelectedNode()
    {
     Node [] nodes=new Node[]{this.serachResults.get(selection)};
        try {
            this.explorerManager.setSelectedNodes(nodes);
        } catch (PropertyVetoException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

  
    private void search()
    {
    this.serachResults.clear();
    String text2find=this.searchTextFiled.getText().trim();
    if(text2find.isEmpty())
    {
    NotifyDescriptor d = new NotifyDescriptor.Confirmation("No data in Search textfield!","Search Textfield Empty",
             NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.ERROR_MESSAGE);
  DialogDisplayer.getDefault().notify(d);
    }
    else
      searchNodes(this.rootNode,text2find);
 
   if (!this.serachResults.isEmpty())
   {
    this.listModel.clear();
    for (Node n:this.serachResults)
        this.listModel.addElement(n.getDisplayName().trim());   
   }
   else if (!text2find.isEmpty())
    {
     NotifyDescriptor d = new NotifyDescriptor.Confirmation("No results were found for \""+text2find+"\"",
           "No Results Found",
             NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.INFORMATION_MESSAGE);
    DialogDisplayer.getDefault().notify(d);
    }


 }


    private void searchNodes(Node n1,String text2find)
    {
     text2find=text2find.toUpperCase();
     List<Node> l=n1.getChildren().snapshot();
     if (l.size() >= 0)
      {
       for ( int ir=0;ir<l.size();ir++ )
       {
          Node n = (Node)l.get(ir);
          if (n.getDisplayName().trim().contains(text2find))
             this.serachResults.add(n);
          if(n.getChildren().getNodesCount()>0)
              searchNodes(n,text2find);
        }
      }
     }

     private void checkUpDownButtons()
    {
       setUpDownButtonsEnabled(true);

       if (selection>-1)
       {
         if (selection==0)
               this.upButton.setEnabled(false);
         if (selection==listModel.getSize()-1)
               this.downButton.setEnabled(false);
       }
       else
           setUpDownButtonsEnabled(false);

       if (listModel.size()==1)   setUpDownButtonsEnabled(false);
    }


    private void setUpDownButtonsEnabled(boolean tf)
    {
     this.upButton.setEnabled(tf);
     this.downButton.setEnabled(tf);
    }

    @Override
    public String getName()
    {return "Find...";}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        searchTextFiled = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultsList = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        searchButton = new javax.swing.JButton();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(mySearchPanel.class, "mySearchPanel.jLabel1.text")); // NOI18N

        searchTextFiled.setText(org.openide.util.NbBundle.getMessage(mySearchPanel.class, "mySearchPanel.searchTextFiled.text")); // NOI18N
        searchTextFiled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFiledActionPerformed(evt);
            }
        });

        resultsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "For search results click the \"Find\" button..." };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(resultsList);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(mySearchPanel.class, "mySearchPanel.jLabel2.text")); // NOI18N

        searchButton.setText(org.openide.util.NbBundle.getMessage(mySearchPanel.class, "mySearchPanel.searchButton.text")); // NOI18N
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        upButton.setText(org.openide.util.NbBundle.getMessage(mySearchPanel.class, "mySearchPanel.upButton.text")); // NOI18N
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });

        downButton.setText(org.openide.util.NbBundle.getMessage(mySearchPanel.class, "mySearchPanel.downButton.text")); // NOI18N
        downButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(searchTextFiled, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(downButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(upButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextFiled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(upButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(downButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchTextFiledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFiledActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_searchTextFiledActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
      search();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtonActionPerformed
        if (selection>0)
        {
            selection=selection-1;
        this.resultsList.setSelectedIndex(selection);
        }
    }//GEN-LAST:event_upButtonActionPerformed

    private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtonActionPerformed
       if (selection<this.listModel.size()-1)
       {
            selection=selection+1;
        this.resultsList.setSelectedIndex(selection);
       }
    }//GEN-LAST:event_downButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton downButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList resultsList;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextFiled;
    private javax.swing.JButton upButton;
    // End of variables declaration//GEN-END:variables

}
