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

package org.coeus.wizards.Assign;

import java.awt.Color;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.BalloonTip.AttachLocation;
import net.java.balloontip.BalloonTip.Orientation;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.RoundedBalloonStyle;
import org.coeus.btvpalette.myAdvanceAction;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards._HelpFuntions.addCommandCharPanel;
import org.coeus.wizards._HelpFuntions.addCommandConditionPanel;
import org.coeus.wizards._HelpFuntions.addCommandExpressionPanel;


import org.coeus.wizards._HelpFuntions.addCommandStringPanel;
import org.coeus.wizards._HelpFuntions.getArrayElementsList;
import org.coeus.wizards._HelpFuntions.getCommandArrayElementsListPanel;
import org.coeus.wizards._HelpFuntions.getCommandParametersListPanel;
import org.coeus.wizards._HelpFuntions.getCommandVariablesListPanel;
import org.coeus.wizards._HelpFuntions.getParametersList;
import org.coeus.wizards._HelpFuntions.getVariablesList;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public final class AssignVisualPanel1 extends JPanel {
    private String objScope;

  
    private LinkedList <String> dispNames= new LinkedList<String>();
    private LinkedList <String> objNames= new LinkedList<String>();
    private LinkedList <String> dispTypes= new LinkedList<String>();
    private LinkedList <String> objTypes= new LinkedList<String>();
    private LinkedList <String> dispCategory= new LinkedList<String>();
  

     private getArrayElementsList gAEL=null;
     private getParametersList gPL= null;
     private getVariablesList gVL = null;
     private String commandName=null;

    /** Creates new form AssignVisualPanel1 */
    public AssignVisualPanel1(String iObjScope,String iCommandName) {
        initComponents();
        showAdvanceButtons();
        this.objScope=iObjScope;
        this.commandName=iCommandName;
        editBut.setEnabled(false);

        //customize tool tip apperance
        UIManager.put("ToolTip.background", new ColorUIResource(255, 247, 200)); // The color is #fff7c8.
        UIManager.put("ToolTip.background", new ColorUIResource(220, 220, 220)); // The color is #fff7c8.
        Border border = BorderFactory.createLineBorder(new Color(76,79,83)); // The color is #4c4f53.
        UIManager.put("ToolTip.border", border);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(20000);// 20 seconds
        //end customize tool tip apperance


          selectVarBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To assign a value to a variable, click \"" + "<b>Select Variable</b>"+ "\""+"</font></html>");

        selectArrElemBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To assign a value to an array element, click \"" + "<b>Select Array Element</b>"+ "\""+"</font></html>");

        selectParBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To assign a value to an input parameter, click \"" + "<b>Select Parameter</b>"+ "\""+"</font></html>");



         gAEL = new getArrayElementsList(this.objScope,"all_datatypes");
         gPL  = new getParametersList(this.objScope,"all_datatypes");
         gVL = new getVariablesList(this.objScope,"all_datatypes");

          if (gVL.getListWithVariables().isEmpty())
             selectVarBut.setEnabled(false);


         if (gAEL.getDisplayNames()==null)
             selectArrElemBut.setEnabled(false);

         if (gPL.getDisplayNames()==null)
             selectParBut.setEnabled(false);

    }

    @Override
    public String getName() {
        return "Assign Value";
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

    public JButton getEditButton()
    {return this.editBut;}


     /////SETTERS
   

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


    public void assignValue(String type)
    {
     if (type.equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
         addCondition();
     else if (type.equalsIgnoreCase(WizardsDefinitions.CHAR))
         addCharacter();
     else if (type.equalsIgnoreCase(WizardsDefinitions.STRING))
         addString();
     else
         addExpression();
    }


    public void editAssignValue(String type)
    {
     if (type.equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
         updateCondition();
     else if (type.equalsIgnoreCase(WizardsDefinitions.CHAR))
         updateCharacter();
     else if (type.equalsIgnoreCase(WizardsDefinitions.STRING))
         updateString();
     else
         updateExpression();

    }




    public void addParameter()
    {
      getCommandParametersListPanel gPLPanel = new getCommandParametersListPanel(gPL,this.commandName,null,null,null,null);

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(gPLPanel,"Parameter Selection",
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
           AssignWizardAction.setDispAssignName(dispNames.peekLast());
           AssignWizardAction.setObjAssignName(objNames.peekLast());
           AssignWizardAction.setDispAssignType(dispTypes.peekLast());
           AssignWizardAction.setObjAssignType(objTypes.peekLast());
           AssignWizardAction.setDispCategory(dispCategory.peekLast());
           assignValue(objTypes.peekLast());
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
           AssignWizardAction.setDispAssignName(dispNames.peekLast());
           AssignWizardAction.setObjAssignName(objNames.peekLast());
           AssignWizardAction.setDispAssignType(dispTypes.peekLast());
           AssignWizardAction.setObjAssignType(objTypes.peekLast());
           AssignWizardAction.setDispCategory(dispCategory.peekLast());
           assignValue(objTypes.peekLast());
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
           AssignWizardAction.setDispAssignName(dispNames.peekLast());
           AssignWizardAction.setObjAssignName(objNames.peekLast());
           AssignWizardAction.setDispAssignType(dispTypes.peekLast());
           AssignWizardAction.setObjAssignType(objTypes.peekLast());
           AssignWizardAction.setDispCategory(dispCategory.peekLast());
           assignValue(objTypes.peekLast());
        }
       }
   }
}



    public void addExpression()
    {
     addCommandExpressionPanel aEP = new addCommandExpressionPanel(this.objScope,null,this.commandName);
     aEP.getHeaders()[0].setText("Name:");
     aEP.getHeaders()[1].setText(nameLabel.getText());
     aEP.getHeaders()[2].setText("Type:");
     aEP.getHeaders()[3].setText(categoryLabel.getText());

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Assigning Arithmetic Expression to "+categoryLabel.getText()+" "+nameLabel.getText(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidExpression(NotifyDescriptor.DEFAULT_OPTION))
         {
         AssignWizardAction.setDispAssignValue(aEP.getDisplayExpression());
         AssignWizardAction.setObjAssignValue("("+AssignWizardAction.getObjAssignType()+") ("+aEP.getObjectExpresssion()+")");
         AssignWizardAction.setDispAssignCategory(WizardsDefinitions.EXPRESSION);
         AssignWizardAction.setObjAssignCategory("EXPRESSION");

         assignValueLabel.setText(aEP.getDisplayExpression());
         assignCategoryLabel.setText(WizardsDefinitions.EXPRESSION);
         editBut.setEnabled(true);
        }
       }
  }


    public void updateExpression()
    {
     addCommandExpressionPanel aEP = new addCommandExpressionPanel(this.objScope,assignValueLabel.getText(),this.commandName);
     aEP.getHeaders()[0].setText("Name:");
     aEP.getHeaders()[1].setText(nameLabel.getText());
     aEP.getHeaders()[2].setText("Type:");
     aEP.getHeaders()[3].setText(categoryLabel.getText());


      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Modifying Assigned Expression to "+categoryLabel.getText()+" "+nameLabel.getText(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidExpression(NotifyDescriptor.OK_CANCEL_OPTION))
         {
         AssignWizardAction.setDispAssignValue(aEP.getDisplayExpression());
         AssignWizardAction.setObjAssignValue("("+AssignWizardAction.getObjAssignType()+") ("+aEP.getObjectExpresssion()+")");
         AssignWizardAction.setDispAssignCategory(WizardsDefinitions.EXPRESSION);
         AssignWizardAction.setObjAssignCategory("EXPRESSION");

         assignValueLabel.setText(aEP.getDisplayExpression());
         assignCategoryLabel.setText(WizardsDefinitions.EXPRESSION);
         editBut.setEnabled(true);
        }
       }
   }



    public void addCharacter()
    {
     addCommandCharPanel aEP = new addCommandCharPanel(this.objScope,null,this.commandName);
     aEP.getHeaders()[0].setText("Name:");
     aEP.getHeaders()[1].setText(nameLabel.getText());
     aEP.getHeaders()[2].setText("Type:");
     aEP.getHeaders()[3].setText(categoryLabel.getText());


      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Assigning Character to "+categoryLabel.getText()+" "+nameLabel.getText(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidInput(NotifyDescriptor.DEFAULT_OPTION))
         {
              if (aEP.getCharTextField().isVisible())
               {
                   aEP.putCharInLabel();
                   d =new NotifyDescriptor.Confirmation(aEP,aEP.getName(),
                   NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

                    if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
                         {
                             if (aEP.checkValidInput(NotifyDescriptor.DEFAULT_OPTION))
                               {
                                 AssignWizardAction.setDispAssignValue(aEP.getDisplayChar());
                                 AssignWizardAction.setObjAssignValue("("+AssignWizardAction.getObjAssignType()+") ("+aEP.getObjChar()+")");
                                 AssignWizardAction.setDispAssignCategory(WizardsDefinitions.CHARACTER);
                                 AssignWizardAction.setObjAssignCategory("CHARACTER");

                                 assignValueLabel.setText(aEP.getDisplayChar());
                                 assignCategoryLabel.setText(WizardsDefinitions.CHARACTER);
                                 editBut.setEnabled(true);
                                }
                            }
                        }
                    else
                    {
               ////////////////////////////////////
                     AssignWizardAction.setDispAssignValue(aEP.getDisplayChar());
                     AssignWizardAction.setObjAssignValue("("+AssignWizardAction.getObjAssignType()+") ("+aEP.getObjChar()+")");
                     AssignWizardAction.setDispAssignCategory(WizardsDefinitions.CHARACTER);
                     AssignWizardAction.setObjAssignCategory("CHARACTER");

                     assignValueLabel.setText(aEP.getDisplayChar());
                     assignCategoryLabel.setText(WizardsDefinitions.CHARACTER);
                     editBut.setEnabled(true);
                    }
              
          }
      }
  }

    public void updateCharacter()
    {
     addCommandCharPanel aEP = new addCommandCharPanel(this.objScope,assignValueLabel.getText(),this.commandName);
     aEP.getHeaders()[0].setText("Name:");
     aEP.getHeaders()[1].setText(nameLabel.getText());
     aEP.getHeaders()[2].setText("Type:");
     aEP.getHeaders()[3].setText(categoryLabel.getText());


      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Modifying Assigned Character to "+categoryLabel.getText()+" "+nameLabel.getText(),
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
                                 AssignWizardAction.setDispAssignValue(aEP.getDisplayChar());
                                 AssignWizardAction.setObjAssignValue("("+AssignWizardAction.getObjAssignType()+") ("+aEP.getObjChar()+")");
                                 AssignWizardAction.setDispAssignCategory(WizardsDefinitions.CHARACTER);
                                 AssignWizardAction.setObjAssignCategory("CHARACTER");

                                 assignValueLabel.setText(aEP.getDisplayChar());
                                 assignCategoryLabel.setText(WizardsDefinitions.CHARACTER);
                                 editBut.setEnabled(true);
                               }
                        }
                    }
                    else
                    {
               ////////////////////////////////////
                     AssignWizardAction.setDispAssignValue(aEP.getDisplayChar());
                     AssignWizardAction.setObjAssignValue("("+AssignWizardAction.getObjAssignType()+") ("+aEP.getObjChar()+")");
                     AssignWizardAction.setDispAssignCategory(WizardsDefinitions.CHARACTER);
                     AssignWizardAction.setObjAssignCategory("CHARACTER");

                     assignValueLabel.setText(aEP.getDisplayChar());
                     assignCategoryLabel.setText(WizardsDefinitions.CHARACTER);
                     editBut.setEnabled(true);
                    }
               
          }
      }
    }

 public void addString()
    {
     addCommandStringPanel aSP = new addCommandStringPanel(this.objScope,null,this.commandName);
     aSP.getHeaders()[0].setText("Name:");
     aSP.getHeaders()[1].setText(nameLabel.getText());
     aSP.getHeaders()[2].setText("Type:");
     aSP.getHeaders()[3].setText(categoryLabel.getText());


      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aSP,
      "Assigning String to "+categoryLabel.getText()+" "+nameLabel.getText(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aSP.checkValidInput(NotifyDescriptor.DEFAULT_OPTION))
         {
         AssignWizardAction.setDispAssignValue(aSP.getDisplayString());
//         AssignWizardAction.setObjAssignValue("("+AssignWizardAction.getObjAssignType()+") ("+aSP.getObjString()+")");
         AssignWizardAction.setObjAssignValue(aSP.getObjString());
         AssignWizardAction.setDispAssignCategory(WizardsDefinitions.STRING1);
         AssignWizardAction.setObjAssignCategory("STRING");

         assignValueLabel.setText(aSP.getDisplayString());
         assignCategoryLabel.setText(WizardsDefinitions.STRING1);
         editBut.setEnabled(true);
        }
       }
   }


 public void updateString()
    {
     addCommandStringPanel aSP = new addCommandStringPanel(this.objScope,assignValueLabel.getText(),this.commandName);
     aSP.getHeaders()[0].setText("Name:");
     aSP.getHeaders()[1].setText(nameLabel.getText());
     aSP.getHeaders()[2].setText("Type:");
     aSP.getHeaders()[3].setText(categoryLabel.getText());


      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aSP,
      "Modifying Assigned String to "+categoryLabel.getText()+" "+nameLabel.getText(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aSP.checkValidInput(NotifyDescriptor.OK_CANCEL_OPTION)  && !aSP.getObjNames().isEmpty())
         {
         AssignWizardAction.setDispAssignValue(aSP.getDisplayString());
//         AssignWizardAction.setObjAssignValue("("+AssignWizardAction.getObjAssignType()+") ("+aSP.getObjString()+")");
         AssignWizardAction.setObjAssignValue(aSP.getObjString());
         AssignWizardAction.setDispAssignCategory(WizardsDefinitions.STRING1);
         AssignWizardAction.setObjAssignCategory("STRING");

         assignValueLabel.setText(aSP.getDisplayString());
         assignCategoryLabel.setText(WizardsDefinitions.STRING1);
         editBut.setEnabled(true);
        }
      }
    }


    public void addCondition()
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,null,this.commandName);
     aCP.getHeaders()[0].setText("Name:");
     aCP.getHeaders()[1].setText(nameLabel.getText());
     aCP.getHeaders()[2].setText("Type:");
     aCP.getHeaders()[3].setText(categoryLabel.getText());


      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Assigning Logical Condition to "+categoryLabel.getText()+" "+nameLabel.getText(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.DEFAULT_OPTION))
         {
         AssignWizardAction.setDispAssignValue(aCP.getDisplayCondition());
         AssignWizardAction.setObjAssignValue("("+AssignWizardAction.getObjAssignType()+") ("+aCP.getObjectCondition()+")");
         AssignWizardAction.setDispAssignCategory(WizardsDefinitions.CONDITION);
         AssignWizardAction.setObjAssignCategory("CONDITION");

         assignValueLabel.setText(aCP.getDisplayCondition());
         assignCategoryLabel.setText(WizardsDefinitions.CONDITION);
         editBut.setEnabled(true);
        }
      }
    }




    public void updateCondition()
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,assignValueLabel.getText(),this.commandName);
     aCP.getHeaders()[0].setText("Name:");
     aCP.getHeaders()[1].setText(nameLabel.getText());
     aCP.getHeaders()[2].setText("Type:");
     aCP.getHeaders()[3].setText(categoryLabel.getText());


      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Modifying Assigned Condition to "+categoryLabel.getText()+" "+nameLabel.getText(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.OK_CANCEL_OPTION))
         {
         AssignWizardAction.setDispAssignValue(aCP.getDisplayCondition());
         AssignWizardAction.setObjAssignValue("("+AssignWizardAction.getObjAssignType()+") ("+aCP.getObjectCondition()+")");
         AssignWizardAction.setDispAssignCategory(WizardsDefinitions.CONDITION);
         AssignWizardAction.setObjAssignCategory("CONDITION");

         assignValueLabel.setText(aCP.getDisplayCondition());
         assignCategoryLabel.setText(WizardsDefinitions.CONDITION);
         editBut.setEnabled(true);
        }
      }
    }

public JLabel getCategoryLabel()
{return this.categoryLabel;}

public JLabel getNameLabel()
{return this.nameLabel;}

public JLabel getAssignCategoryLabel()
{return this.assignCategoryLabel;}

public JLabel getAssignValueLabel()
{return this.assignValueLabel;}



 private void createDirectionsPopupWindow()
   {


       String balloonText="<html><div align=\"justify\"><font color=\"#000000\"size=\"4\" face=\"Tahoma\">" +
        "The ASSIGN command stores a value to a variable or an input parameter or an array element.<br>" +
        "To assign a value to a variable, click the «<b>Select Variable</b>» button. To assign a value<br>" +
        "to an array element, click the <b>«Select Array Element»</b> button. To assign a value to an input<br>" +
        "parameter, click the <b>«Select Input Parameter»</b> button. You can get information about each button<br>" +
        "by placing the mouse over it. According to the type of your selection (<i>e.g. integer, string, etc.</i>),<br>" +
        "a new window comes up, in which the appropriate value type can be entered. To modify an already assigned<br>" +
        "value, click the <b>«Modify Assigned Value»</b> button. If you click any of the <b>«Select Variable»</b>, <b>«Select<br>" +
        "Array Element» </b>and <b>«Select Input Parameters»</b> buttons,while you have already assigned a value, a new <br>" +
        "process of assigning a value begins and the selections you have already made are discarded.</font></div></html>";

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
        nameLabel = new javax.swing.JLabel();
        assignValueLabel = new javax.swing.JLabel();
        categoryLabel = new javax.swing.JLabel();
        assignCategoryLabel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        editBut = new javax.swing.JButton();
        directionsButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(selectVarBut, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.selectVarBut.text")); // NOI18N
        selectVarBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectVarBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectVarButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(selectArrElemBut, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.selectArrElemBut.text")); // NOI18N
        selectArrElemBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectArrElemBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectArrElemButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(selectParBut, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.selectParBut.text")); // NOI18N
        selectParBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectParBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectParButActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.jLabel13.text")); // NOI18N

        nameLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.nameLabel.text")); // NOI18N

        assignValueLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        org.openide.awt.Mnemonics.setLocalizedText(assignValueLabel, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.assignValueLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(categoryLabel, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.categoryLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(assignCategoryLabel, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.assignCategoryLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.jLabel12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(editBut, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.editBut.text")); // NOI18N
        editBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(categoryLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(assignCategoryLabel)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(assignValueLabel)
                    .addComponent(nameLabel))
                .addGap(295, 295, 295)
                .addComponent(editBut))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryLabel)
                    .addComponent(nameLabel)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(assignCategoryLabel)
                    .addComponent(assignValueLabel)
                    .addComponent(editBut)))
        );

        org.openide.awt.Mnemonics.setLocalizedText(directionsButton, org.openide.util.NbBundle.getMessage(AssignVisualPanel1.class, "AssignVisualPanel1.directionsButton.text")); // NOI18N
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectVarBut, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selectArrElemBut, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectParBut, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(directionsButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectVarBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectParBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectArrElemBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(directionsButton))
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

    private void editButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButActionPerformed
        editAssignValue(AssignWizardAction.getObjAssignType());
}//GEN-LAST:event_editButActionPerformed

    private void directionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directionsButtonActionPerformed
        this.createDirectionsPopupWindow();
    }//GEN-LAST:event_directionsButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel assignCategoryLabel;
    private javax.swing.JLabel assignValueLabel;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JButton directionsButton;
    private javax.swing.JButton editBut;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton selectArrElemBut;
    private javax.swing.JButton selectParBut;
    private javax.swing.JButton selectVarBut;
    // End of variables declaration//GEN-END:variables
}

