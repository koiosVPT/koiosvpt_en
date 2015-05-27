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

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;
import org.coeus.wizards.Messages;
import org.coeus.wizards.ReservedWords;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public final class FunVisualPanel2 extends JPanel {


    private LinkedList <String> dispNames= new LinkedList<String>();
    private LinkedList <String> dispTypes= new LinkedList<String>();


    private static final int stringListSize=35;
    private int selection=-1;
    private DefaultListModel listModel = null;




    /** Creates new form FunVisualPanel2 */
    public FunVisualPanel2() {
        initComponents();
        setEditDeleteButtonsEnabled(false);
        setUpDownButtonsEnabled(false);

  //customize tool tip apperance
        UIManager.put("ToolTip.background", new ColorUIResource(220, 220, 220)); // The color is #fff7c8.
        Border border = BorderFactory.createLineBorder(new Color(76,79,83)); // The color is #4c4f53.
        UIManager.put("ToolTip.border", border);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(20000);// 20 seconds
        //end customize tool tip apperance


         addParameter.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To add a formal INPUT PARAMETER to this function \"" + "<b>Add INPUT PARAMETER</b>"+ "\".<br>" +
           "To add more INPUT PARAMETERS, click this button again."+"</font></html>");

        listModel = new DefaultListModel();
        jList1.setModel(listModel);

      jList1.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int sel;                
                sel=jList1.getSelectedIndex();
                if (sel>-1)
                {
                  selection=sel;
                  checkUpDownButtons();
                  setEditDeleteButtonsEnabled(true);
                }
            }
        });

    jList1.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                checkUpDownButtons(); }
            public void focusLost(FocusEvent e) {
              //  setUpDownButtonsEnabled(false);
            }

        });

     jList1.addMouseListener(new MouseListener () {

            public void mouseClicked(MouseEvent e) {
               if (selection>-1)
                  if(e.getClickCount() == 2)
                       updateParameter();
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

    @Override
    public String getName() {
        return "Input Parameter Declaration";
    }
   

  public void addNewParameter()
    {
      addParameter addPar = new addParameter(WizardsDefinitions.INT1,null);

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(addPar,"Add New Parameter",
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(checkParameterUpdate(addPar.getParName(),this.dispNames))
       {          
           
           dispNames.add(addPar.getParName());
           dispTypes.add(addPar.getParType());
        
//           if (listModel.isEmpty())  setAllButtonsEnabled(true);

       listModel.addElement(strings2ListModelItem(addPar.getParType(),addPar.getParName()));          
       }
   }
 }



 public void updateParameter()
    {

      addParameter addPar = new addParameter(dispTypes.get(selection),dispNames.get(selection));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(addPar,"Modify Parameter",
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(checkParameterUpdate(addPar.getParName(),this.dispNames))
       {          
           dispNames.remove(selection);
           dispTypes.remove(selection);

           dispNames.add(selection,addPar.getParName());
           dispTypes.add(selection,addPar.getParType());
        
//       if (listModel.isEmpty())  setAllButtonsEnabled(true);

       listModel.remove(selection);
       listModel.insertElementAt(strings2ListModelItem(addPar.getParType(),addPar.getParName()), selection);

           
       }
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
     

   private void editParameter()
{
 if (selection>-1)
 {
  updateParameter();
  jList1.setSelectedIndex(selection);
 }
}

 

 private void checkUpDownButtons()
    {
       setUpDownButtonsEnabled(true);

       if (selection>-1)
       {
         if (selection==0)
               UpBut.setEnabled(false);
         if (selection==listModel.getSize()-1)
               DownBut.setEnabled(false);
       }
       else
           setUpDownButtonsEnabled(false);

       if (listModel.size()==1)   setUpDownButtonsEnabled(false);
    }

private void oneElementUp()
{String s1,s2;
    if (selection>0)
    {
     int selected=selection;
     String  object= (String)listModel.getElementAt(selected);
     listModel.remove(selected);
     listModel.insertElementAt(object,selected-1);
     jList1.setSelectedIndex(selected-1);


          s1= dispNames.get(selected);
          s2= dispTypes.get(selected);

           dispNames.remove(selected);
           dispTypes.remove(selected);

           dispNames.add(selected-1, s1);
           dispTypes.add(selected-1, s2);

    }
}


private void oneElementDown()
{String s1,s2;
    if (selection>-1 && selection<listModel.getSize()-1)
    {
     int selected=selection;
     String  object= (String)listModel.getElementAt(selected);
     listModel.remove(selected);
     listModel.insertElementAt(object,selected+1);
     jList1.setSelectedIndex(selected+1);

          s1= dispNames.get(selected);
          s2= dispTypes.get(selected);

           dispNames.remove(selected);
           dispTypes.remove(selected);

           dispNames.add(selected+1, s1);
           dispTypes.add(selected+1, s2);
    }
}


private void clearAll()
{
     NotifyDescriptor d =new NotifyDescriptor.Confirmation(
                "You are going to delete all input parameters of this function!\nDo you want to continue?",
                "Parameter(s) Deletion Confirmation",
     NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.WARNING_MESSAGE);

         if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION) {
           dispNames.clear();
           dispTypes.clear();

           listModel.clear();
           setEditDeleteButtonsEnabled(false);
           setUpDownButtonsEnabled(false);
           selection=-1;
          }
}


public void clearOne()
{
   if (selection>-1)
         {
           dispNames.remove(selection);
           dispTypes.remove(selection);
    
           listModel.remove(selection);

           if (selection==0 && !listModel.isEmpty())
          {}
          else if (selection<=listModel.size() )
             selection=selection-1;
          jList1.setSelectedIndex(selection);

          if (listModel.isEmpty())
          {
           setEditDeleteButtonsEnabled(false);
           setUpDownButtonsEnabled(false);
          }
     }
}

  private void setEditDeleteButtonsEnabled(boolean tf)
    {
      EditBut.setEnabled(tf);
      DeleteSelectedBut.setEnabled(tf);
      DeleteAllBut.setEnabled(tf);
    }

     private void setUpDownButtonsEnabled(boolean tf)
    {
     UpBut.setEnabled(tf);
     DownBut.setEnabled(tf);
    }

     ////Getters
     public LinkedList<String> getDispArguements()
    {return this.dispNames;}


    public LinkedList<String> getDispArguementsType()
    {return this.dispTypes;}


    public DefaultListModel getListModel()
    {return this.listModel;}

    public void setListModel(DefaultListModel lm)
    {this.listModel=lm;}
    
    public JList getList()
    {return this.jList1;}

     /////Setters
     public void setDispArguements(LinkedList<String> iDispNames)
    {this.dispNames=iDispNames;}


     public void setDispArguementsType(LinkedList<String> iDispTypes)
    {this.dispTypes=iDispTypes;}

     public JLabel getLabelTitle()
     {return this.jLabel2;}

 
public boolean checkParameterUpdate(String s, LinkedList<String> namesList) {
 boolean result=true;

               ////CHECK IF EMPTY
                    if (s.isEmpty())
                    {DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_EM1 +
                               Messages.ERR_FUN_ARGUEMENT_UPDTAE_CANCELED));
                    return false;
                  }


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
                            Messages.ERR_FUN_ARGUEMENT_UPDTAE_CANCELED));
                       return false;
                    }
                    else if (!(s.charAt(q) >= 65 && s.charAt(q) <= 90) && //A-Z
                            !(s.charAt(q) >= 97 && s.charAt(q) <= 122) && //a-z
                            !(s.charAt(q) >= 913 && s.charAt(q) <= 937) && //Α-Ω
                            !(s.charAt(q) >= 945 && s.charAt(q) <= 969) &&//α-ω
                            !(s.charAt(q) >= 48 && s.charAt(q) <= 57) && //0-9
                            !(s.charAt(q) == 36) &&//$
                            !(s.charAt(q) == 95))//_
                    { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_LET3 +
                        Messages.ERR_FUN_ARGUEMENT_UPDTAE_CANCELED));
                  return false;
                   }

                 }

            ////CHECK IF RESERVED WORD
                    if (ReservedWords.CheckReservedWord(s))
                    {DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_RES +
                               Messages.ERR_FUN_ARGUEMENT_UPDTAE_CANCELED));
                    return false;
                  }


            ////CHECK IF IDENTIFIER ALREADY IN USE IN THIS FUNCTION
                AllObjectsList caol = AllObjectsList.getAllObjList();
                AllObjects type =caol.SearchByDisplayName_DispScope(s,WizardsDefinitions.FUNCTION+" "+FunVisualPanel1.getFunName());


                 if( (caol!=null) && (type!=null) )
                { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_USE
                 + type.getDispCateg()+" " +type.getDispName()+" of the same function!\n"+
                 Messages.ERR_FUN_ARGUEMENT_UPDTAE_CANCELED));
             return false;
              }



            ////CHECK IF IDENTIFIER ALREADY IN USE AS A GLOBAL OBJECT
             type =caol.SearchByDisplayName_DispScope(s,WizardsDefinitions.GLOBAL);

                 if( (caol!=null) && (type!=null) )
                { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_USE
                 + type.getDispCateg()+" " +type.getDispName()+", with scope: "+WizardsDefinitions.GLOBAL+"!\n"
                 +Messages.ERR_FUN_ARGUEMENT_UPDTAE_CANCELED));
             return false;
              }





                /////Check if parameter has the same name with the function
                if (s.equalsIgnoreCase(FunVisualPanel1.getFunName()))
                { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_USE1 +
                  Messages.ERR_FUN_ARGUEMENT_UPDTAE_CANCELED));
             return false;
              }


               /////check if parameter has the same name with previous parameter
              
               int pos =namesList.indexOf(s);
               if (pos>-1 && pos!=selection)

                        { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(Messages.ERR_FUN_ARGUEMENT_USE2 +" Parameter no"+ (pos+1)+"."+
                             Messages.ERR_FUN_ARGUEMENT_UPDTAE_CANCELED));
                        return false;
                        }
 return result;
 }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        UpBut = new javax.swing.JButton();
        DownBut = new javax.swing.JButton();
        DeleteAllBut = new javax.swing.JButton();
        DeleteSelectedBut = new javax.swing.JButton();
        EditBut = new javax.swing.JButton();
        addParameter = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(FunVisualPanel2.class, "FunVisualPanel2.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(FunVisualPanel2.class, "FunVisualPanel2.jLabel14.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(FunVisualPanel2.class, "FunVisualPanel2.jLabel12.text")); // NOI18N

        jScrollPane1.setViewportView(jList1);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(FunVisualPanel2.class, "FunVisualPanel2.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(UpBut, org.openide.util.NbBundle.getMessage(FunVisualPanel2.class, "FunVisualPanel2.UpBut.text")); // NOI18N
        UpBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(DownBut, org.openide.util.NbBundle.getMessage(FunVisualPanel2.class, "FunVisualPanel2.DownBut.text")); // NOI18N
        DownBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(DeleteAllBut, org.openide.util.NbBundle.getMessage(FunVisualPanel2.class, "FunVisualPanel2.DeleteAllBut.text")); // NOI18N
        DeleteAllBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteAllButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(DeleteSelectedBut, org.openide.util.NbBundle.getMessage(FunVisualPanel2.class, "FunVisualPanel2.DeleteSelectedBut.text")); // NOI18N
        DeleteSelectedBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteSelectedButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(EditBut, org.openide.util.NbBundle.getMessage(FunVisualPanel2.class, "FunVisualPanel2.EditBut.text")); // NOI18N
        EditBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(addParameter, org.openide.util.NbBundle.getMessage(FunVisualPanel2.class, "FunVisualPanel2.addParameter.text")); // NOI18N
        addParameter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addParameterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EditBut, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DeleteSelectedBut, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DeleteAllBut, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                        .addGap(176, 176, 176)
                        .addComponent(addParameter))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UpBut, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(DownBut, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14)))
                    .addComponent(addParameter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UpBut)
                        .addGap(18, 18, 18)
                        .addComponent(DownBut))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EditBut)
                    .addComponent(DeleteSelectedBut)
                    .addComponent(DeleteAllBut, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(FunVisualPanel2.class, "FunVisualPanel2.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void EditButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButActionPerformed
        editParameter();
}//GEN-LAST:event_EditButActionPerformed

    private void DeleteSelectedButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteSelectedButActionPerformed
        clearOne();
}//GEN-LAST:event_DeleteSelectedButActionPerformed

    private void DeleteAllButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteAllButActionPerformed
        clearAll();
}//GEN-LAST:event_DeleteAllButActionPerformed

    private void DownButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DownButActionPerformed
        oneElementDown();
}//GEN-LAST:event_DownButActionPerformed

    private void UpButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpButActionPerformed
        oneElementUp();
}//GEN-LAST:event_UpButActionPerformed

    private void addParameterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addParameterActionPerformed
       addNewParameter();
    }//GEN-LAST:event_addParameterActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeleteAllBut;
    private javax.swing.JButton DeleteSelectedBut;
    private javax.swing.JButton DownBut;
    private javax.swing.JButton EditBut;
    private javax.swing.JButton UpBut;
    private javax.swing.JButton addParameter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

