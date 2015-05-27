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

package org.coeus.wizards.If;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;

import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.BalloonTip.AttachLocation;
import net.java.balloontip.BalloonTip.Orientation;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.RoundedBalloonStyle;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards._HelpFuntions.addCommandConditionPanel;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public final class IfVisualPanel1 extends JPanel {
    private String objScope;
   


    private LinkedList <String> dispStatement= new LinkedList<String>();
    private LinkedList <String> objStatement= new LinkedList<String>();
    private LinkedList <String> dispStatementCategory= new LinkedList<String>();
    private LinkedList <Integer> oldPosition= new LinkedList<Integer>();
    private LinkedList <Integer> currentPosition= new LinkedList<Integer>();

    private static final int stringListSize=35;
    private int selection=-1;
    private DefaultListModel listModel = null;
   

     private String commandName=null;
    /** Creates new form ReadVisualPanel1 */
    public IfVisualPanel1(String iObjScope,String icommandName) {
        initComponents();


     //customize tool tip apperance
        UIManager.put("ToolTip.background", new ColorUIResource(220, 220, 220)); // The color is #fff7c8.
        Border border = BorderFactory.createLineBorder(new Color(76,79,83)); // The color is #4c4f53.
        UIManager.put("ToolTip.border", border);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(20000);// 20 seconds
        //end customize tool tip apperance

//           selectMessageBut.setToolTipText("<html><center>" + "This is a" + "<br><b>" + "tool tip" + "<font color=\"#800080\" " +
//            "size=\"4\" face=\"Verdana\">tool tip verdana" +  "</font></b></center></html>");
 addElseIf.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To add an ELSE IF statement, click the \"" + "<b>Add ELSE IF</b>"+ "\"."+"</font></html>");

 addElse.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
         "To add an ELSE IF statement, click the \"" + "<b>Add ELSE</b>"+ "\"."+"</font></html>");




        this.objScope=iObjScope;
        this.commandName=icommandName;
        setEditDeleteButtonsEnabled(false);
//        setUpDownButtonsEnabled(false);
        
     

        listModel = new DefaultListModel();
        jList1.setModel(listModel);


        jList1.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int sel;
                sel=jList1.getSelectedIndex();
                if (sel>-1)
                {
                  selection=sel;
                  checkButtons();
                }
            }
        });

    jList1.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                checkButtons(); }
            public void focusLost(FocusEvent e) {
              //  setUpDownButtonsEnabled(false);
            }
        });
 //new printLists(messages,dispNames,objNames,dispTypes,objTypes,dispCategory);

            jList1.addMouseListener(new MouseListener () {

            public void mouseClicked(MouseEvent e) {
               if (selection>-1)
                  if(e.getClickCount() == 2)
                       editElement();
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


    public void getIfStatement()
    {
     addIfStatement();
    }


    @Override
    public String getName() {
        return "Condition Definition";
    }

     private void setEditDeleteButtonsEnabled(boolean tf)
    {
      EditBut.setEnabled(tf);
      DeleteSelectedBut.setEnabled(tf);
      DeleteAllBut.setEnabled(tf);
    }

    private void setDeleteButtonsEnabled(boolean tf)
    {
      DeleteSelectedBut.setEnabled(tf);
      DeleteAllBut.setEnabled(tf);
    }
  
//    private void setUpDownButtonsEnabled(boolean tf)
//    {
//     UpBut.setEnabled(tf);
//     DownBut.setEnabled(tf);
//    }

    public void setAddButtonsEnabled(boolean tf)
    {
    this.addElse.setEnabled(tf);
    this.addElseIf.setEnabled(tf);
    }


  ///GETTERS


    public LinkedList<String> getDispStatements()
    {return this.dispStatement;}

     public LinkedList<String> getObjStatements()
    {return this.objStatement;}

     public LinkedList<String> getDispStatementsCategories()
    {return this.dispStatementCategory;}

    public LinkedList<Integer> getOldPositions()
    {return this.oldPosition;}

    public LinkedList<Integer> getCurrentPositions()
    {return this.currentPosition;}

     /////SETTERS


    public void setDispStatements(LinkedList<String> iDispStatement)
    {this.dispStatement=iDispStatement;}

    public void setObjStatements(LinkedList<String> iObjStatement)
    {this.objStatement=iObjStatement;}

     public void setDispStatementCategories(LinkedList<String> iDispStatementCategories)
    {this.dispStatementCategory=iDispStatementCategories;}

    public void setOldPositions(LinkedList<Integer> iOldPositions)
    {this.oldPosition=iOldPositions;}
    
    public void setCurrentPositions(LinkedList<Integer> iCurrentPositions)
    {this.currentPosition=iCurrentPositions;}

     public String strings2ListModelItem(String category,String type)
     { String result="",ncategory,ntype;

       if (category.length()>stringListSize)
           ncategory=category.substring(0,stringListSize);
       else
         ncategory = padWithSpaceChars(category);

       ntype=type;

     result=ncategory+ntype;
     return result;
     }

    private String padWithSpaceChars (String inString)
    {String temp="",outString="";
    int difference = stringListSize- inString.length();
    for (int i=0;i<difference;i++)
        temp=temp+" ";
    outString=inString+temp;
    return outString;
    }

private void addIfStatement()
{addCondition(WizardsDefinitions.IF_STATEMENT,"IF",NotifyDescriptor.DEFAULT_OPTION);
}

private void updateIfStatement()
{updateCondition(WizardsDefinitions.IF_STATEMENT,"IF");}

private void addElseIfStatement()
{addCondition(WizardsDefinitions.ELSE_IF_STATEMENT,"ELSE IF",NotifyDescriptor.OK_CANCEL_OPTION);
}

private void updateElseIfStatement()
{updateCondition(WizardsDefinitions.ELSE_IF_STATEMENT,"ELSE IF");
}

private void addElseStatement()
{
 dispStatement.add("");
 objStatement.add("");
 dispStatementCategory.add(WizardsDefinitions.ELSE_STATEMENT);

 listModel.addElement(strings2ListModelItem(WizardsDefinitions.ELSE_STATEMENT,""));

 setAddButtonsEnabled(false);
}

private void updateElseStatement()
{}

private void editElement()
{
 if (selection>-1)
 {
  if (dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.IF_STATEMENT))
          updateIfStatement();
  else if (dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
          updateElseIfStatement();
  else if (dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.ELSE))
          updateElseStatement();
  jList1.setSelectedIndex(selection);
 }
}



  
 public void addCondition(String statement,String textLabel, int buttons)
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,null,this.commandName);
     aCP.getTextFiledLabel().setText(textLabel);


     aCP.getHeaders()[0].setText("Defining Logical Condition for ");
     aCP.getHeaders()[1].setText(statement);
     aCP.getHeaders()[2].setVisible(false);
     aCP.getHeaders()[3].setVisible(false);
     aCP.showParenthesisLabels(true);


      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Defining Logical Condition for "+statement,
      buttons,NotifyDescriptor.PLAIN_MESSAGE);

     Object answer= DialogDisplayer.getDefault().notify(d);

     if (buttons ==NotifyDescriptor.DEFAULT_OPTION )
     {
      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);
     }

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.DEFAULT_OPTION))
         {
         if (newCondition(aCP.getDisplayCondition(),statement))
         {
             dispStatement.add(aCP.getDisplayCondition());
             objStatement.add(aCP.getObjectCondition());
             dispStatementCategory.add(statement);


            listModel.addElement(strings2ListModelItem(statement,aCP.getDisplayCondition()));
         }
        }
      }
    }




    public void updateCondition(String statement,String textLabel)
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,dispStatement.get(selection),this.commandName);
     aCP.getTextFiledLabel().setText(textLabel);


     aCP.getHeaders()[0].setText("Modifying Logical Condition for ");
     aCP.getHeaders()[1].setText(statement);
     aCP.getHeaders()[2].setVisible(false);
     aCP.getHeaders()[3].setVisible(false);
     aCP.showParenthesisLabels(true);



      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Modifying Logical Condition for "+statement,
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.OK_CANCEL_OPTION))
         {
          if (newCondition(aCP.getDisplayCondition(),statement))
          {
           dispStatement.remove(selection);
           objStatement.remove(selection);
           dispStatementCategory.remove(selection);

           dispStatement.add(selection,aCP.getDisplayCondition());
           objStatement.add(selection,aCP.getObjectCondition());
           dispStatementCategory.add(selection,statement);

          listModel.remove(selection);
          listModel.insertElementAt(strings2ListModelItem(statement,aCP.getDisplayCondition()),selection);
          }
        }
      }
    }


private boolean newCondition(String condition,String statement)
{
 boolean conditionFound=false;

 for (int i=0;i<this.dispStatement.size();i++)
     if(this.dispStatement.get(i).equalsIgnoreCase(condition))
         conditionFound=true;
 if (conditionFound)
 {
  NotifyDescriptor d =new NotifyDescriptor.Confirmation("The LOGICAL CONDITION that you have entered is already\n" +
          "combined with a previous IF or ELSE IF statement.\n" +
          "Do you want to combine this condition with the particular " +statement+ " as well?",
  "Defining Logical Condition for ",
   NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.QUESTION_MESSAGE);

   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.YES_OPTION)
      return true;
   else
     return false;
 }
 else
     return true;

}
// private void checkButtons()
//    {
////       setUpDownButtonsEnabled(true);
//
//       if (selection>-1)
//       {
//         if (this.dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.IF_STATEMENT))
//         {
//         this.EditBut.setEnabled(true);
//         setUpDownButtonsEnabled(false);
//         setDeleteButtonsEnabled(false);
//         }
//         else if (this.dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
//         {
//         setUpDownButtonsEnabled(true);
//         setEditDeleteButtonsEnabled(true);
//             if (selection==1)
//               UpBut.setEnabled(false);
//             if (this.dispStatementCategory.peekLast().equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT)&& selection == listModel.getSize()-2
//             ||this.dispStatementCategory.peekLast().equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT)&& selection == listModel.getSize()-1 )
//               DownBut.setEnabled(false);
//         }
//         else if (this.dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT))
//         {
//         setUpDownButtonsEnabled(false);
//         setEditDeleteButtonsEnabled(true);
//         this.EditBut.setEnabled(false);
//         }
//       }
//       else
//       {
//           setUpDownButtonsEnabled(false);
//           setEditDeleteButtonsEnabled(false);
//       }
//
//       //if (listModel.size()==1)   setUpDownButtonsEnabled(false);
//    }


     private void checkButtons()
    {
//       setUpDownButtonsEnabled(true);

       if (selection>-1)
       {
         if (this.dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.IF_STATEMENT))
         {
         this.EditBut.setEnabled(true);
         setDeleteButtonsEnabled(false);
         }
         else if (this.dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
         setEditDeleteButtonsEnabled(true);
         else if (this.dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT))
         {
         setEditDeleteButtonsEnabled(true);
         this.EditBut.setEnabled(false);
         }
       }
//       else
//           setEditDeleteButtonsEnabled(false);
    }

//private void oneElementUp()
//{String s2,s3,s4;
//    if (selection>0)
//    {
//     int selected=selection;
//     String  object= (String)listModel.getElementAt(selected);
//     listModel.remove(selected);
//     listModel.insertElementAt(object,selected-1);
//     jList1.setSelectedIndex(selected-1);
//
//
//          s2= dispStatement.get(selected);
//          s3= objStatement.get(selected);
//          s4= dispStatementCategory.get(selected);
//
//           dispStatement.remove(selected);
//           objStatement.remove(selected);
//           dispStatementCategory.remove(selected);
//
//
//           dispStatement.add(selected-1, s2);
//           objStatement.add(selected-1, s3);
//           dispStatementCategory.add(selected-1, s4);
//    }
//}
//
//
//private void oneElementDown()
//{String s2,s3,s4;
//    if (selection>-1 && selection<listModel.getSize()-1)
//    {
//     int selected=selection;
//     String  object= (String)listModel.getElementAt(selected);
//     listModel.remove(selected);
//     listModel.insertElementAt(object,selected+1);
//     jList1.setSelectedIndex(selected+1);
//
//
//          s2= dispStatement.get(selected);
//          s3= objStatement.get(selected);
//          s4= dispStatementCategory.get(selected);
//
//           dispStatement.remove(selected);
//           objStatement.remove(selected);
//           dispStatementCategory.remove(selected);
//
//           dispStatement.add(selected+1, s2);
//           objStatement.add(selected+1, s3);
//           dispStatementCategory.add(selected+1, s4);
//
//    }
//}
//

private void clearAll()
{ String s1,s2,s3;
     NotifyDescriptor d =new NotifyDescriptor.Confirmation(
                "You are going to delete all ELSE IF and ELSE statements!\n" +
                "Do you want to continue?",
                "Deletion Confirmation",
     NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.WARNING_MESSAGE);

         if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION) {

           s1= dispStatement.get(0);
           s2=objStatement.get(0);
           s3=dispStatementCategory.get(0);

           dispStatement.clear();
           objStatement.clear();
           dispStatementCategory.clear();

           listModel.clear();


           dispStatement.add(s1);
           objStatement.add(s2);
           dispStatementCategory.add(s3);

           listModel.addElement(strings2ListModelItem(s3,s1));
        

           setEditDeleteButtonsEnabled(false);
//           setUpDownButtonsEnabled(false);
     
           
         setAddButtonsEnabled(true);
           selection=-1;
     }
}


public void clearOne()
{
   if (selection>-1)
         {
           String temp=this.dispStatementCategory.get(selection);
           dispStatement.remove(selection);
           objStatement.remove(selection);
           dispStatementCategory.remove(selection);    

           listModel.remove(selection);

          if (selection==0 && !listModel.isEmpty())
          {}
          else if (selection<=listModel.size() )
             selection=selection-1;
        

          if (temp.equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT))
          setAddButtonsEnabled(true);

         jList1.setSelectedIndex(selection);
      }

}

    public DefaultListModel getListModel()
    {return this.listModel;}



      private void createDirectionsPopupWindow()
   {


       String balloonText="<html><div align=\"justify\"><font color=\"#000000\"size=\"4\" face=\"Tahoma\">" +
        "An IF..ELSE conditional statement is used to execute different blocks of commands based on<br>" +
        "whether a condition is valid or not. An IF..ELSE consists of one IF statement, none or more<br>" +
        "ELSE_IF statements and none or one ELSE statement. Each ELSE_IF statement is combined with a<br>" +
        "different condition, while the ELSE statement is not combined with a condition. When an IF..ELSE<br>" +
        "conditional statement is added in the program, a window pops up, in which the condition for the<br>" +
        "IF statement can be entered. click the <b>«Add ELSE IF»</b> button, to add an ELSE_IF statement.<br>" +
        "Click the <b>«Add ELSE»</b> button, to add an ELSE statement. You can get information about each<br>" +
        "button, by placing the mouse over it. After the addition of an ELSE statement no more ELSE_IF or<br>" +
        "ELSE statements can be added. In the case that you want to add more ELSE_IF statements after an<br>" +
        "ELSE statement, you should first delete the else statements, add the ELSE_IF statements and finally,<br>" +
        "add the ELSE statement. You can change the order of the ELSE_IF statement by clicking the <b>«Up»</b><br>" +
        "or <b>«Down»</b> button. Click the <b>«Modify Selected Statement»</b> button, to modify the conditions<br>" +
        "in the IF and ELSE_IF statements. Click the <b>«Delete Selected Statement»</b> or the <b>«Delete All<br>" +
        "Statements»</b> button, to delete ELSE_IF and ELSE statement. An IF statement cannot be deleted,<br>" +
        "because an IF..ELSE conditional statement should contain at least an IF statement. An ELSE statement<br>" +
        "cannot be modified, because a condition is not combined with it.</font></div></html>";

       BalloonTipStyle edgedLook = new RoundedBalloonStyle(10,10,Color.WHITE, Color.BLUE);

     new BalloonTip(this.directionsButton,balloonText,edgedLook,Orientation.RIGHT_ABOVE,AttachLocation.SOUTHWEST,40,20,true  );

   }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addElseIf = new javax.swing.JButton();
        addElse = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        EditBut = new javax.swing.JButton();
        DeleteSelectedBut = new javax.swing.JButton();
        DeleteAllBut = new javax.swing.JButton();
        directionsButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(addElseIf, org.openide.util.NbBundle.getMessage(IfVisualPanel1.class, "IfVisualPanel1.addElseIf.text")); // NOI18N
        addElseIf.setPreferredSize(new java.awt.Dimension(155, 25));
        addElseIf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addElseIfActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(addElse, org.openide.util.NbBundle.getMessage(IfVisualPanel1.class, "IfVisualPanel1.addElse.text")); // NOI18N
        addElse.setPreferredSize(new java.awt.Dimension(155, 25));
        addElse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addElseActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(IfVisualPanel1.class, "IfVisualPanel1.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        jScrollPane1.setViewportView(jList1);

        org.openide.awt.Mnemonics.setLocalizedText(EditBut, org.openide.util.NbBundle.getMessage(IfVisualPanel1.class, "IfVisualPanel1.EditBut.text")); // NOI18N
        EditBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(DeleteSelectedBut, org.openide.util.NbBundle.getMessage(IfVisualPanel1.class, "IfVisualPanel1.DeleteSelectedBut.text")); // NOI18N
        DeleteSelectedBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteSelectedButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(DeleteAllBut, org.openide.util.NbBundle.getMessage(IfVisualPanel1.class, "IfVisualPanel1.DeleteAllBut.text")); // NOI18N
        DeleteAllBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteAllButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EditBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DeleteSelectedBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DeleteAllBut))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EditBut)
                    .addComponent(DeleteSelectedBut)
                    .addComponent(DeleteAllBut)))
        );

        org.openide.awt.Mnemonics.setLocalizedText(directionsButton, org.openide.util.NbBundle.getMessage(IfVisualPanel1.class, "IfVisualPanel1.directionsButton.text")); // NOI18N
        directionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directionsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addElseIf, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addElse, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(directionsButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addElseIf, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addElse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(directionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addElseIfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addElseIfActionPerformed
        addElseIfStatement();
}//GEN-LAST:event_addElseIfActionPerformed

    private void addElseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addElseActionPerformed
        addElseStatement();
}//GEN-LAST:event_addElseActionPerformed

    private void EditButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButActionPerformed
        editElement();
}//GEN-LAST:event_EditButActionPerformed

    private void DeleteSelectedButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteSelectedButActionPerformed
        clearOne();
}//GEN-LAST:event_DeleteSelectedButActionPerformed

    private void DeleteAllButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteAllButActionPerformed
        clearAll();
}//GEN-LAST:event_DeleteAllButActionPerformed

    private void directionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directionsButtonActionPerformed
       createDirectionsPopupWindow();
    }//GEN-LAST:event_directionsButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeleteAllBut;
    private javax.swing.JButton DeleteSelectedBut;
    private javax.swing.JButton EditBut;
    private javax.swing.JButton addElse;
    private javax.swing.JButton addElseIf;
    private javax.swing.JButton directionsButton;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

