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


package org.coeus.wizards.For;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
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
import org.coeus.btvpalette.myAdvanceAction;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards._HelpFuntions.addCommandConditionPanel;
import org.coeus.wizards._HelpFuntions.addCommandExpressionPanel;


import org.coeus.wizards._HelpFuntions.getArrayElementsList;
import org.coeus.wizards._HelpFuntions.getCommandArrayElementsListPanel;
import org.coeus.wizards._HelpFuntions.getCommandParametersListPanel;
import org.coeus.wizards._HelpFuntions.getCommandVariablesListPanel;
import org.coeus.wizards._HelpFuntions.getParametersList;
import org.coeus.wizards._HelpFuntions.getVariablesList;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public final class ForVisualPanel1 extends JPanel {
    private String objScope;

  
    private LinkedList <String> dispNames= new LinkedList<String>();
    private LinkedList <String> objNames= new LinkedList<String>();
    private LinkedList <String> dispTypes= new LinkedList<String>();
    private LinkedList <String> objTypes= new LinkedList<String>();
    private LinkedList <String> dispCategory= new LinkedList<String>();
    private LinkedList <String> dispStatement= new LinkedList<String>();
    private LinkedList <String> objStatement= new LinkedList<String>();
    private LinkedList <String> dispStatementCategory= new LinkedList<String>();

    private static final int stringListSize=35;
    private int selection=-1;
    private DefaultListModel listModel = null;

     private getArrayElementsList gAEL=null;
     private getParametersList gPL= null;
     private getVariablesList gVL = null;

     private String commandName=null;
    /** Creates new form ReadVisualPanel1 */
    public ForVisualPanel1(String iObjScope,String icommandName) {
        initComponents();
        showAdvanceButtons();
        this.objScope=iObjScope;
        this.commandName=icommandName;
        this.EditBut.setEnabled(false);

        listModel = new DefaultListModel();
        jList1.setModel(listModel);

        this.categoryLabel.setVisible(false);
        this.nameLabel.setVisible(false);

        //customize tool tip apperance
        UIManager.put("ToolTip.background", new ColorUIResource(220, 220, 220)); // The color is #fff7c8.
        Border border = BorderFactory.createLineBorder(new Color(76,79,83)); // The color is #4c4f53.
        UIManager.put("ToolTip.border", border);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(20000);// 20 seconds
        //end customize tool tip apperance


        selectVarBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To use a variable in the formation of this FOR..LOOP iteration statement, click the \"" + "<b>Select Variable</b>"+ "\" buton.</font></html>");

        selectArrElemBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To use an array element  the formation of this FOR..LOOP iteration statement, click the \"" + "<b>Select Array Element</b>"+ "\" button.</font></html>");

        selectParBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To use an input parameter in the formation of this FOR..LOOP iteration statement, click the \"" + "<b>Select Input Parameter</b>"+ "\" button.</font></html>");






      getParametersList intPL=null,doublePL = null;
      getArrayElementsList intAEL=null,doubleAEL=null;
      getVariablesList intVL=null,doubleVL= null;

      ///Get integers..........
         intPL  = new getParametersList(this.objScope,WizardsDefinitions.INT);
         intAEL = new getArrayElementsList(this.objScope,WizardsDefinitions.INT);
         intVL = new getVariablesList(this.objScope,WizardsDefinitions.INT);

////Get double...........
         doublePL  = new getParametersList(this.objScope,WizardsDefinitions.DOUBLE);
         doubleAEL = new getArrayElementsList(this.objScope,WizardsDefinitions.DOUBLE);
         doubleVL = new getVariablesList(this.objScope,WizardsDefinitions.DOUBLE);

//Combine integers and doubles.................
         intPL.getDispParams().addAll(doublePL.getDispParams());
         intPL.getObjParams().addAll(doublePL.getObjParams());
         intPL.getDispParamsTypes().addAll(doublePL.getDispParamsTypes());
         intPL.getObjParamsTypes().addAll(doublePL.getObjParamsTypes());

         intAEL.getListWithArrays().addAll(doubleAEL.getListWithArrays());
         intVL.getListWithVariables().addAll(doubleVL.getListWithVariables());


///////Use combined integer and doubles.....
         gPL  = new getParametersList(this.objScope, intPL.getDispParams(), intPL.getObjParams(),
                 intPL.getDispParamsTypes(),intPL.getObjParamsTypes());

         gAEL = new getArrayElementsList(this.objScope,intAEL.getListWithArrays());
         gVL = new getVariablesList(this.objScope,intVL.getListWithVariables());
      



          if (gVL.getListWithVariables().isEmpty())
             selectVarBut.setEnabled(false);


         if (gAEL.getDisplayNames()==null)
             selectArrElemBut.setEnabled(false);

         if (gPL.getDisplayNames()==null)
             selectParBut.setEnabled(false);



        jList1.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int sel;
                sel=jList1.getSelectedIndex();
                if (sel>-1)
                {
                  selection=sel;
                  EditBut.setEnabled(true);
                }
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


    public void getForValues()
    {
    this.listModel.clear();
    this.dispStatement.clear();
    this.objStatement.clear();
    this.dispStatementCategory.clear();


     addExpression(1);
     addCondition();
     addExpression(2);
    }


    @Override
    public String getName() {
        return "Values Definition";
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
    }


  ///GETTERS
   

    public String getDispName()
    {return this.dispNames.peekLast();}

     public String getObjName()
    {return this.objNames.peekLast();}

     public String getDispType()
    {return this.dispTypes.peekLast();}

     public String getObjType()
    {return this.objTypes.peekLast();}

    public String  getDispCategory()
    {return this.dispCategory.peekLast();}

      public  LinkedList<String> getDispStatement()
    {return this.dispStatement;}

     public  LinkedList<String> getObjStatement()
    {return this.objStatement;}

    public  LinkedList<String> getDispStatementCategory()
    {return this.dispStatementCategory;}

     public JButton getEditButton()
    {return this.EditBut;}

     /////SETTERS
 

    public void setDispName(String iDispNames)
    {this.dispNames.add(iDispNames);}

     public void setObjName(String iObjNames)
    {this.objNames.add(iObjNames);}

     public void setDispType(String iDispTypes)
    {this.dispTypes.add(iDispTypes);}

     public void setObjType(String iObjTypes)
    {this.objTypes.add(iObjTypes);}

    public void setDispCategory(String iDispCategory)
    {this.dispCategory.add(iDispCategory);}

   public  void setDispStatement(LinkedList<String> args)
    {this.dispStatement=args;}

    public void setObjStatement(LinkedList<String> args)
    {this.objStatement=args;}

    public  void setDispStatementCategory(LinkedList<String> args)
    {this.dispStatementCategory=args;}






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


    public void addParameter()
    {
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

           categoryLabel.setText(dispTypes.peekLast()+" "+dispCategory.peekLast());
           nameLabel.setText(dispNames.peekLast());
           getForValues();
           }
       }
   }
 }



public void addVariable()
{
 getCommandVariablesListPanel gVLPanel = new getCommandVariablesListPanel(gVL,this.commandName,null,null,null,null);

       NotifyDescriptor d =new NotifyDescriptor.Confirmation(gVLPanel,"Variable Selection",
       NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(gVLPanel.getDName()!=null)
       {
           if (!gVLPanel.getDName().equalsIgnoreCase(""))
           {
           dispNames.add(gVLPanel.getDName());
           objNames.add(gVLPanel.getOName());
           dispTypes.add(gVLPanel.getDType());
           objTypes.add(gVLPanel.getOType());
           dispCategory.add(WizardsDefinitions.VARIABLE);

           categoryLabel.setText(dispTypes.peekLast()+" "+dispCategory.peekLast());
           nameLabel.setText(dispNames.peekLast());
           getForValues();

           }
       }
   }
}


public void addArrayElement(String name)
{
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

           categoryLabel.setText(dispTypes.peekLast()+" "+dispCategory.peekLast());
           nameLabel.setText(dispNames.peekLast());
           getForValues();
         }
       }
   }
}



private void editElement()
{
 if (selection>-1)
 {
   
  if (dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.INITIAl_VALUE))
          updateExpression(1);
  else if (dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.FINAL_VALUE))
          updateCondition();
  else if (dispStatementCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.STEP))
          updateExpression(2);
  jList1.setSelectedIndex(selection);
 }
}



   public void addExpression(int i)
    {
     String s="",cat="",text=null;

     if (i==2)
         text=nameLabel.getText()+" ";
     
     addCommandExpressionPanel aEP = new addCommandExpressionPanel(this.objScope,text,this.commandName);
   
     if (i==1)
     {
     aEP.getHeaders()[0].setText("Defining Initial Expression for ");
     aEP.getHeaders()[1].setText(nameLabel.getText());
     s="Defining Initial Expression for ";
     aEP.getTextFiledLabel().setText("Initial Expression: "+nameLabel.getText()+"=");
     cat=WizardsDefinitions.INITIAl_VALUE;
     }
     else
     {
     aEP.getHeaders()[0].setText("Defining Step for ");
     aEP.getHeaders()[1].setText(nameLabel.getText());
     s="Defining Step for ";
     aEP.getTextFiledLabel().setText("Step: "+nameLabel.getText()+"=");
     cat=WizardsDefinitions.STEP;
     }
     
     aEP.getHeaders()[2].setVisible(false);
     aEP.getHeaders()[3].setVisible(false);

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      s+nameLabel.getText(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidExpression(NotifyDescriptor.DEFAULT_OPTION))
         {
          dispStatement.add(nameLabel.getText()+" = "+aEP.getDisplayExpression());
          objStatement.add(this.objNames.peekLast()+" = "+aEP.getObjectExpresssion());
          dispStatementCategory.add(cat);

          if (i==1)
          listModel.addElement(strings2ListModelItem(cat,aEP.getDisplayExpression()));
          else
          listModel.addElement(strings2ListModelItem(cat,nameLabel.getText()+" = "+aEP.getDisplayExpression()));
          }
       }
  }


    public void updateExpression(int i)
    {
     String s="",cat="";
     int pos =dispStatement.get(selection).indexOf("=");
     String text=dispStatement.get(selection).substring(pos+1);
     addCommandExpressionPanel aEP = new addCommandExpressionPanel(this.objScope,text,this.commandName);
     aEP.getTextFiledLabel().setText(nameLabel.getText()+"=");

     if (i==1)
     {
     aEP.getHeaders()[0].setText("Modifying Initial Expression for ");
     aEP.getHeaders()[1].setText(nameLabel.getText());
     s="Modifying Initial Expression for ";
     aEP.getTextFiledLabel().setText("Initial Expression: "+nameLabel.getText()+"=");
     cat=WizardsDefinitions.INITIAl_VALUE;
     }
     else
     {
     aEP.getHeaders()[0].setText("Modifying Step for");
     aEP.getHeaders()[1].setText(nameLabel.getText());
     s="Modifying Step for ";
     aEP.getTextFiledLabel().setText("Step: "+nameLabel.getText()+"=");
     cat=WizardsDefinitions.STEP;
     }

     aEP.getHeaders()[2].setVisible(false);
     aEP.getHeaders()[3].setVisible(false);

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      s+nameLabel.getText(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidExpression(NotifyDescriptor.OK_CANCEL_OPTION))
         {

          dispStatement.remove(selection);
          objStatement.remove(selection);
          dispStatementCategory.remove(selection);


          dispStatement.add(selection,nameLabel.getText()+" = "+aEP.getDisplayExpression());
          objStatement.add(selection,this.objNames.peekLast()+" = "+aEP.getObjectExpresssion());
          dispStatementCategory.add(selection,cat);

          listModel.remove(selection);
          listModel.insertElementAt(strings2ListModelItem(cat,aEP.getDisplayExpression()),selection);
          }
       }
   }

 public void addCondition()
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,nameLabel.getText()+" ",this.commandName);
     aCP.getTextFiledLabel().setText("Final Condition:");

     
     aCP.getHeaders()[0].setText("Defining Final Condition for ");
     aCP.getHeaders()[1].setText(nameLabel.getText());
     aCP.getHeaders()[2].setVisible(false);
     aCP.getHeaders()[3].setVisible(false);


      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Defining Final Condition for "+nameLabel.getText(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.DEFAULT_OPTION))
         {
         dispStatement.add(aCP.getDisplayCondition());
         objStatement.add(aCP.getObjectCondition());
         dispStatementCategory.add(WizardsDefinitions.FINAL_VALUE);
       

        listModel.addElement(strings2ListModelItem(WizardsDefinitions.FINAL_VALUE,aCP.getDisplayCondition()));
        }
      }
    }




    public void updateCondition()
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,dispStatement.get(selection),this.commandName);
     aCP.getTextFiledLabel().setText("Final Condition:");
 
     aCP.getHeaders()[0].setText("Modifying Final Condition for ");
     aCP.getHeaders()[1].setText(nameLabel.getText());
     aCP.getHeaders()[2].setVisible(false);
     aCP.getHeaders()[3].setVisible(false);


      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Modifying Final Condition for "+nameLabel.getText(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.OK_CANCEL_OPTION))
         {
          dispStatement.remove(selection);
          objStatement.remove(selection);
          dispStatementCategory.remove(selection);

         dispStatement.add(selection,aCP.getDisplayCondition());
         objStatement.add(selection,aCP.getObjectCondition());
         dispStatementCategory.add(selection,WizardsDefinitions.FINAL_VALUE);

        listModel.remove(selection);
        listModel.insertElementAt(strings2ListModelItem(WizardsDefinitions.FINAL_VALUE,aCP.getDisplayCondition()),selection);
        }
      }
    }



    public DefaultListModel getListModel()
    {return this.listModel;}

     public JLabel getCategoryLabel()
     {return this.categoryLabel;}

     public JLabel getNameLabel()
     {return this.nameLabel;}



      private void createDirectionsPopupWindow()
   {


       String balloonText="<html><div align=\"justify\"><font color=\"#000000\"size=\"4\" face=\"Tahoma\">" +
                                "A FOR..LOOP iteration statement is used to execute repeatedly one or more commands.<br>" +
                                "This statement contains two expressions and one condition: the initial expression,<br>" +
                                "the step and the final condition. Select a variable or an array element or an input<br>" +
                                "parameter to form these expressions and condition by clicking the <b>«Select Variable»</b>,<br>" +
                                "<b>«Select Array Element»</b> and <b>«Select Input Parameter»</b> button, respectively.<br>" +
                                "Your selection should be used in the two expressions as well as in the condition. You can<br>" +
                                "get information about each button, by placing the mouse over it. The initial expression is<br>" +
                                "evaluated and if the final condition is valid, the group of commands in the FOR..LOOP<br>" +
                                "iteration statement (between the command FOR.. and END OF FOR) is executed.  The<br>" +
                                "execution of all commands in the FOR..LOOP statement, is called a loop. When the<br>" +
                                "loop is over, the initial expression varies by the value of step and a new value<br>" +
                                "for the initial expression is evaluated.  Then, the final condition is evaluated using<br>" +
                                "the new value and if it is still valid, a new loop begins. This cycle continues until the<br>" +
                                "final condition is no longer valid. You can modify already defined expressions and condition,<br>" +
                                "by clicking the <b>«Modify Expression\\Condition»</b> button.</font></div></html>";

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

        selectVarBut = new javax.swing.JButton();
        selectArrElemBut = new javax.swing.JButton();
        selectParBut = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        EditBut = new javax.swing.JButton();
        categoryLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        directionsButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(selectVarBut, org.openide.util.NbBundle.getMessage(ForVisualPanel1.class, "ForVisualPanel1.selectVarBut.text")); // NOI18N
        selectVarBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectVarBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectVarButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(selectArrElemBut, org.openide.util.NbBundle.getMessage(ForVisualPanel1.class, "ForVisualPanel1.selectArrElemBut.text")); // NOI18N
        selectArrElemBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectArrElemBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectArrElemButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(selectParBut, org.openide.util.NbBundle.getMessage(ForVisualPanel1.class, "ForVisualPanel1.selectParBut.text")); // NOI18N
        selectParBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectParBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectParButActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(ForVisualPanel1.class, "ForVisualPanel1.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(ForVisualPanel1.class, "ForVisualPanel1.jLabel13.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(ForVisualPanel1.class, "ForVisualPanel1.jLabel14.text")); // NOI18N

        jScrollPane1.setViewportView(jList1);

        org.openide.awt.Mnemonics.setLocalizedText(EditBut, org.openide.util.NbBundle.getMessage(ForVisualPanel1.class, "ForVisualPanel1.EditBut.text")); // NOI18N
        EditBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(categoryLabel, org.openide.util.NbBundle.getMessage(ForVisualPanel1.class, "ForVisualPanel1.categoryLabel.text")); // NOI18N

        nameLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, org.openide.util.NbBundle.getMessage(ForVisualPanel1.class, "ForVisualPanel1.nameLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(categoryLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(nameLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EditBut))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EditBut)
                    .addComponent(categoryLabel)
                    .addComponent(nameLabel))
                .addContainerGap())
        );

        org.openide.awt.Mnemonics.setLocalizedText(directionsButton, org.openide.util.NbBundle.getMessage(ForVisualPanel1.class, "ForVisualPanel1.directionsButton.text")); // NOI18N
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(selectParBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(selectArrElemBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(selectVarBut, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                        .addGap(199, 199, 199)
                        .addComponent(directionsButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectVarBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(directionsButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectArrElemBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectParBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selectVarButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectVarButActionPerformed
        addVariable();
}//GEN-LAST:event_selectVarButActionPerformed

    private void selectArrElemButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectArrElemButActionPerformed
        addArrayElement(null);
}//GEN-LAST:event_selectArrElemButActionPerformed

    private void selectParButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectParButActionPerformed
        addParameter();
}//GEN-LAST:event_selectParButActionPerformed

    private void EditButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButActionPerformed
        editElement();
}//GEN-LAST:event_EditButActionPerformed

    private void directionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directionsButtonActionPerformed
        this.createDirectionsPopupWindow();
    }//GEN-LAST:event_directionsButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EditBut;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JButton directionsButton;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton selectArrElemBut;
    private javax.swing.JButton selectParBut;
    private javax.swing.JButton selectVarBut;
    // End of variables declaration//GEN-END:variables
}

