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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
import org.coeus.wizards.FuncCall.FuncCallWizardAction;
import org.coeus.wizards.TextActions.AddPopupMenu;
import org.coeus.wizards.TextActions.GetFocus;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public final class addCommandExpressionPanel extends JPanel {
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

     private String objectExpression=null;
     private String dispTypicalParameterName=null;

     private boolean result;
     private String[] error =new String[2];
     private checkUsersInput check=null;
    private ActionEvent e;
         private String commandName=null;

    /** Creates new form ReadVisualPanel1 */
    public addCommandExpressionPanel (String iObjScope,String expression,String iCommandName) {
        initComponents();
        showAdvanceButtons();
        this.objScope=iObjScope;
        this.commandName=iCommandName;
        setDeleteButtonsEnabled(false);
        new AddPopupMenu(this.expressionTextField);
        this.expressionTextField.addCaretListener(new parenthesesMatcher());
        errorLabel.setText("");
        showBracketLabels(false);

       errorLabel.setBackground(UIManager.getColor("Label.background"));
       errorLabel.setBorder(BorderFactory.createEmptyBorder());
       this.jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
       errorLabel.setLineWrap(true);
       errorLabel.setWrapStyleWord(true);
       errorLabel.setEditable(false);

        //customize tool tip apperance
        UIManager.put("ToolTip.background", new ColorUIResource(220, 220, 220)); // The color is #fff7c8.
        Border border = BorderFactory.createLineBorder(new Color(76,79,83)); // The color is #4c4f53.
        UIManager.put("ToolTip.border", border);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(20000);// 20 seconds
        //end customize tool tip apperance

           this.jPanel2.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To insert an arithmetic operator in the expression, click a button from the \"" + "<b>Arithmetic</b>"+ "\" panel."+"</font></html>");
        this.jPanel4.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To insert a number or a parenthesis in the expression, click a button from the \"" + "<b>Numbers & Operators</b>"+ "\" panel."+"</font></html>");
        this.selectVariableBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To insert a variable in the expression, click the \"" + "<b>Add Variable</b>"+ "\" button."+"</font></html>");
        this.selectArrElemBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To insert an array element in the expression, click the \"" + "<b>Add Array Element</b>"+ "\" button."+"</font></html>");
        this.selectConstantBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To insert a constant in the expression, click the \"" + "<b>Add Constant</b>"+ "\" button."+"</font></html>");
        this.selectFunctionBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To insert a function in the expression, click the \"" + "<b>Add Function</b>"+ "\" button."+"</font></html>");
        this.selectParameterBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "To insert an input parameter in the expression, click the \"" + "<b>Add Input Parameter</b>"+ "\" button."+"</font></html>");


      getParametersList intPL=null,doublePL = null;
      getArrayElementsList intAEL=null,doubleAEL=null;
      getVariablesList intVL=null,doubleVL= null;
      getConstantsList intCL=null,doubleCL = null;
      getFunctionsList intFL=null,doubleFL = null;


        if (expression!=null)
        {
             setDeleteButtonsEnabled(true);
             expressionTextField.setText(expression);
        }

////Get integers..........
         intPL  = new getParametersList(this.objScope,WizardsDefinitions.INT);
         intAEL = new getArrayElementsList(this.objScope,WizardsDefinitions.INT);
         intVL = new getVariablesList(this.objScope,WizardsDefinitions.INT);
         intCL = new getConstantsList(this.objScope,WizardsDefinitions.INT);
         intFL = new getFunctionsList(WizardsDefinitions.INT);
////Get double...........
         doublePL  = new getParametersList(this.objScope,WizardsDefinitions.DOUBLE);
         doubleAEL = new getArrayElementsList(this.objScope,WizardsDefinitions.DOUBLE);
         doubleVL = new getVariablesList(this.objScope,WizardsDefinitions.DOUBLE);
         doubleCL = new getConstantsList(this.objScope,WizardsDefinitions.DOUBLE);
         doubleFL = new getFunctionsList(WizardsDefinitions.DOUBLE);


//Combine integers and doubles.................
         intPL.getDispParams().addAll(doublePL.getDispParams());
         intPL.getObjParams().addAll(doublePL.getObjParams());
         intPL.getDispParamsTypes().addAll(doublePL.getDispParamsTypes());
         intPL.getObjParamsTypes().addAll(doublePL.getObjParamsTypes());

         intAEL.getListWithArrays().addAll(doubleAEL.getListWithArrays());
         intVL.getListWithVariables().addAll(doubleVL.getListWithVariables());
         intCL.getListWithConstants().addAll(doubleCL.getListWithConstants());
         intFL.getListWithFunctions().addAll(doubleFL.getListWithFunctions());

///////Use combined integer and doubles.....
         gPL  = new getParametersList(this.objScope, intPL.getDispParams(), intPL.getObjParams(),
                 intPL.getDispParamsTypes(),intPL.getObjParamsTypes());

         gAEL = new getArrayElementsList(this.objScope,intAEL.getListWithArrays());
         gVL = new getVariablesList(this.objScope,intVL.getListWithVariables());
         gCL = new getConstantsList(this.objScope, intCL.getListWithConstants());
         gFL = new getFunctionsList(intFL.getListWithFunctions());


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

 //new printLists(messages,dispNames,objNames,dispTypes,objTypes,dispCategory);

         expressionTextField.addKeyListener(new KeyListener() {

                   public void keyTyped(KeyEvent e) {
               switch (e.getKeyCode())
               {
//                   case KeyEvent.VK_BACK_SPACE:
//                       break;
//                   case KeyEvent.VK_DELETE:
//                       break;
                   case KeyEvent.VK_RIGHT:
                       break;
                   case KeyEvent.VK_LEFT:
                       break;
                   default: e.consume();
                        break;
               }

            }

            public void keyPressed(KeyEvent e) {
//               if (e.getKeyCode()==KeyEvent.VK_SPACE)
//                       addText(" ");
                 switch (e.getKeyCode())
               {
                   case KeyEvent.VK_RIGHT:
                       break;
                   case KeyEvent.VK_LEFT:
                       break;
                   default: e.consume();
                        break;
               }
            }

            public void keyReleased(KeyEvent e) {

            }
        });

    }

    @Override
    public String getName() {
        return "Expression Definition";
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



     private void setDeleteButtonsEnabled(boolean tf)
    {
      deleteExpressionBut.setEnabled(tf);
      deleteLastBut1.setEnabled(tf);
    }

   public JButton getParametersButton()
   {return this.selectParameterBut;}

   public void showBracketLabels(boolean tf)
   {
      lBracketLabel.setVisible(tf);
      rBracketLabel.setVisible(tf);
   }

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

//          expressionTextField.setText(expressionTextField.getText()+gCLP.getDName());
            addTextNoSpaces(gCLP.getDName());
         setDeleteButtonsEnabled(true);
          }
       }
   }
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

     // expressionTextField.setText(expressionTextField.getText()+gPLPanel.getDName());
           addTextNoSpaces(gPLPanel.getDName());
          setDeleteButtonsEnabled(true);
           }
       }
   }
 }


public void addFunction()
{
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
          // expressionTextField.setText(expressionTextField.getText()+" "+fcwa.getDisplayFunctionCall());
             addTextNoSpaces(fcwa.getDisplayFunctionCall());
           setDeleteButtonsEnabled(true);
       //    }
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
          // messages.add(gVLPanel.getMessage());
           dispNames.add(gVLPanel.getDName());
           objNames.add(gVLPanel.getOName());
           dispTypes.add(gVLPanel.getDType());
           objTypes.add(gVLPanel.getOType());
           dispCategory.add(WizardsDefinitions.VARIABLE);

           //expressionTextField.setText(expressionTextField.getText()+gVLPanel.getDName());
           addTextNoSpaces(gVLPanel.getDName());
          setDeleteButtonsEnabled(true);
           }
       }
   }
}


public void addArrayElement()
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

          //expressionTextField.setText(expressionTextField.getText()+gAELPanel.getReturnDisplaeStateMent());
          addTextNoSpaces(gAELPanel.getReturnDisplaeStateMent());
           setDeleteButtonsEnabled(true);
         }
       }
   }
}



private void clearAll()
{

     NotifyDescriptor d =new NotifyDescriptor.Confirmation(
                "You are going to delete all the terms in the expression!\n" +
                "Do you want to continue?",
                "Deletion Confirmation",
     NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.WARNING_MESSAGE);

         if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION)
         {
                  expressionTextField.setText("");
                   setDeleteButtonsEnabled(false);
          }
}

private void deleteLast()
{
    String s=expressionTextField.getText().trim();
    int lastSpacePos=s.lastIndexOf(" ");
    if (lastSpacePos>0)
       expressionTextField.setText(s.substring(0,lastSpacePos ));
    else
    {
         expressionTextField.setText("");
         setDeleteButtonsEnabled(false);
    }
}

public void addText(String text)
{
  int pos=this.expressionTextField.getCaretPosition();
 if (pos>=0 && pos <this.expressionTextField.getText().length())
 {
    String  part1=expressionTextField.getText().substring(0, pos);
    String  part2=expressionTextField.getText().substring(pos,expressionTextField.getText().length());
    this.expressionTextField.setText(part1+" "+text+" "+part2);
    this.expressionTextField.setCaretPosition(pos+text.length()+1);
 }
 else
      expressionTextField.setText(expressionTextField.getText()+" "+text+" ");
new GetFocus(this.expressionTextField);
 setDeleteButtonsEnabled(true);
}

public void addTextNoSpaces(String text)
{
  int pos=this.expressionTextField.getCaretPosition();
 if (pos>=0 && pos <this.expressionTextField.getText().length())
 {
    String  part1=expressionTextField.getText().substring(0, pos);
    String  part2=expressionTextField.getText().substring(pos,expressionTextField.getText().length());
    this.expressionTextField.setText(part1+text+part2);
    this.expressionTextField.setCaretPosition(pos+text.length()+1);
 }
 else
      expressionTextField.setText(expressionTextField.getText()+text);
new GetFocus(this.expressionTextField);
 setDeleteButtonsEnabled(true);
}


public String getDisplayExpression()
{ return expressionTextField.getText();}

public void setDisplayTypicalParameter(String s)
{this.dispTypicalParameterName=s;}

public String getDisplayTypicalParameter()
{return this.dispTypicalParameterName;}

public JTextField getExpressionTextFiled()
{return this.expressionTextField;}

public JTextArea getErrorLabel()
{return this.errorLabel;}

public JLabel [] getHeaders()
{return new JLabel [] {this.jLabel21,this.jLabel22,this.jLabel23,this.jLabel24};}

public String getObjectExpresssion()
{return this.objectExpression;}


private void validConditions()
{   result=true;
      if(this.expressionTextField.getText().isEmpty() || checkIdentifierForWhiteChars(this.expressionTextField.getText()))
        {
         result=false;
         error[0]="The expression cannot be blank!";
         error[1]="";
        }
      else
        {
            check =new checkUsersInput(this.expressionTextField.getText(),this.objScope,false);
            result= check.checkExpression();
            error =check.getError();
         }

}



 public boolean checkValidExpression(int buttonsOptions)
    {
     validConditions();
     while(!result)
             {
              NotifyDescriptor d =new NotifyDescriptor.Confirmation(this,this.getName(),
              buttonsOptions,NotifyDescriptor.PLAIN_MESSAGE);

              this.errorLabel.setText(error[0]);
              searchNselect(this.expressionTextField,error[1]);
              new GetFocus(this.expressionTextField);

             Object answer=DialogDisplayer.getDefault().notify(d);

             if ( answer==NotifyDescriptor.OK_OPTION)
              validConditions();
             else if (answer==NotifyDescriptor.CLOSED_OPTION)
               result=false;// return false;
             else if ( answer==NotifyDescriptor.CANCEL_OPTION)
               return false;

          }
    this.objectExpression=check.getExpressionObjStatetment();
  return true;
  }


 private void searchNselect (JTextField tf,String text)
 {
   String content = tf.getText();
   content=content.toUpperCase();
        int index = content.indexOf(text, 0);
        if (index >= 0) {   // match found
           // try {
                int end = index + text.length();
               // hilit.addHighlight(index, end, painter);
                tf.setCaretPosition(end);
                tf.setSelectionStart(index);
                tf.setSelectionEnd(end);
           // } catch (BadLocationException e) {}

        }
 }


 private boolean checkIdentifierForWhiteChars(String id)
  { return (id.trim()).isEmpty(); }

 public JLabel getTextFiledLabel()
 {return this.textFiledLabel;}


   private void createDirectionsPopupWindow()
   {

     String balloonText="<html><div align=\"justify\"><font color=\"#000000\"size=\"4\" face=\"Tahoma\">" +
                                "You can define an ARITHMETIC EXPRESSION by selecting its terms.<br>" +
                                "You cannot add a term using the keyboard. Click a button to enter<br>" +
                                "the term on its label in the expression. You can get information about<br>" +
                                "buttons, by placing the mouse over a button or a panel. Each term a<br>" +
                                "button is clicked, your selection is entered at the end of the expression.<br>" +
                                "However, if you place the cursor somewhere in the expression, the next<br>" +
                                "term will be entered in the position of the cursor. Click the <b>«Delete<br>" +
                                "Last Term»</b> button, to delete the last term in the expression. Click the<br>" +
                                "<b>«Delete All Terms»</b>button, to delete the whole expression. When the<br>" +
                                "<b>«ΟΚ»</b> button is clicked, the correctness of the expression is checked.<br>" +
                                "If the expression is syntactically correct is accepted, otherwise a message<br>" +
                                "with information about the error appears.</font></div></html>";

       BalloonTipStyle edgedLook = new RoundedBalloonStyle(10,10,Color.WHITE, Color.BLUE);

     new BalloonTip(this.directionsButton,balloonText,edgedLook,Orientation.RIGHT_ABOVE,AttachLocation.SOUTHWEST,40,20,true  );

   }


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
        selectArrElemBut = new javax.swing.JButton();
        selectParameterBut = new javax.swing.JButton();
        selectFunctionBut = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        textFiledLabel = new javax.swing.JLabel();
        expressionTextField = new javax.swing.JTextField();
        deleteExpressionBut = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        addBut = new javax.swing.JButton();
        minusBut = new javax.swing.JButton();
        multBut = new javax.swing.JButton();
        divBut = new javax.swing.JButton();
        modBut = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        zeroBut = new javax.swing.JButton();
        oneBut = new javax.swing.JButton();
        twoBut = new javax.swing.JButton();
        lParBut = new javax.swing.JButton();
        rParBut = new javax.swing.JButton();
        fiveBut = new javax.swing.JButton();
        threeBut = new javax.swing.JButton();
        fourBut = new javax.swing.JButton();
        sixBut = new javax.swing.JButton();
        sevenBut = new javax.swing.JButton();
        eightBut = new javax.swing.JButton();
        nineBut = new javax.swing.JButton();
        pointBut = new javax.swing.JButton();
        deleteLastBut1 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        directionsButton = new javax.swing.JButton();
        lBracketLabel = new javax.swing.JLabel();
        rBracketLabel = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        errorLabel = new javax.swing.JTextArea();

        selectConstantBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.selectConstantBut.text_1")); // NOI18N
        selectConstantBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectConstantBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectConstantButActionPerformed(evt);
            }
        });

        selectVariableBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.selectVariableBut.text_1")); // NOI18N
        selectVariableBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectVariableBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectVariableButActionPerformed(evt);
            }
        });

        selectArrElemBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.selectArrElemBut.text_1")); // NOI18N
        selectArrElemBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectArrElemBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectArrElemButActionPerformed(evt);
            }
        });

        selectParameterBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.selectParameterBut.text_1")); // NOI18N
        selectParameterBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectParameterBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectParameterButActionPerformed(evt);
            }
        });

        selectFunctionBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.selectFunctionBut.text_1")); // NOI18N
        selectFunctionBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectFunctionBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFunctionButActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jLabel20.text_1")); // NOI18N

        textFiledLabel.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.textFiledLabel.text_1")); // NOI18N

        expressionTextField.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.expressionTextField.text")); // NOI18N
        expressionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expressionTextFieldActionPerformed(evt);
            }
        });

        deleteExpressionBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.deleteExpressionBut.text")); // NOI18N
        deleteExpressionBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteExpressionButActionPerformed(evt);
            }
        });

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 51), 1, true));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        addBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.addBut.text_1")); // NOI18N
        addBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButActionPerformed(evt);
            }
        });

        minusBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.minusBut.text_1")); // NOI18N
        minusBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusButActionPerformed(evt);
            }
        });

        multBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.multBut.text_1")); // NOI18N
        multBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multButActionPerformed(evt);
            }
        });

        divBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.divBut.text_1")); // NOI18N
        divBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                divButActionPerformed(evt);
            }
        });

        modBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.modBut.text_1")); // NOI18N
        modBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modButActionPerformed(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jLabel13.text_1")); // NOI18N

        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jLabel14.text_1")); // NOI18N

        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jLabel16.text_1")); // NOI18N

        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jLabel18.text_1")); // NOI18N

        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jLabel19.text_1")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(divBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(minusBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(multBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(modBut, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBut)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minusBut)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(multBut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(divBut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modBut, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(5, Short.MAX_VALUE))
        );

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        zeroBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.zeroBut.text_1")); // NOI18N
        zeroBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zeroButActionPerformed(evt);
            }
        });

        oneBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.oneBut.text_1")); // NOI18N
        oneBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oneButActionPerformed(evt);
            }
        });

        twoBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.twoBut.text_1")); // NOI18N
        twoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoButActionPerformed(evt);
            }
        });

        lParBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.lParBut.text_1")); // NOI18N
        lParBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lParButActionPerformed(evt);
            }
        });

        rParBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.rParBut.text_1")); // NOI18N
        rParBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rParButActionPerformed(evt);
            }
        });

        fiveBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.fiveBut.text_1")); // NOI18N
        fiveBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiveButActionPerformed(evt);
            }
        });

        threeBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.threeBut.text_1")); // NOI18N
        threeBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threeButActionPerformed(evt);
            }
        });

        fourBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.fourBut.text_1")); // NOI18N
        fourBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourButActionPerformed(evt);
            }
        });

        sixBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.sixBut.text_1")); // NOI18N
        sixBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixButActionPerformed(evt);
            }
        });

        sevenBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.sevenBut.text_1")); // NOI18N
        sevenBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sevenButActionPerformed(evt);
            }
        });

        eightBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.eightBut.text_1")); // NOI18N
        eightBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eightButActionPerformed(evt);
            }
        });

        nineBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.nineBut.text_1")); // NOI18N
        nineBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nineButActionPerformed(evt);
            }
        });

        pointBut.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.pointBut.text_1")); // NOI18N
        pointBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pointButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(fourBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fiveBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sixBut))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(oneBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(twoBut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(threeBut))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lParBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sevenBut))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pointBut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(eightBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(zeroBut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rParBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nineBut))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oneBut)
                    .addComponent(twoBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(threeBut))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fourBut)
                    .addComponent(fiveBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sixBut))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sevenBut)
                    .addComponent(eightBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nineBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(zeroBut)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lParBut)
                    .addComponent(pointBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rParBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        deleteLastBut1.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.deleteLastBut1.text_1")); // NOI18N
        deleteLastBut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLastBut1ActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jLabel28.text_1")); // NOI18N

        directionsButton.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.directionsButton.text")); // NOI18N
        directionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directionsButtonActionPerformed(evt);
            }
        });

        lBracketLabel.setFont(new java.awt.Font("Tahoma", 1, 18));
        lBracketLabel.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.lBracketLabel.text")); // NOI18N

        rBracketLabel.setFont(new java.awt.Font("Tahoma", 1, 18));
        rBracketLabel.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.rBracketLabel.text")); // NOI18N

        jLabel21.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jLabel21.text")); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel22.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jLabel22.text")); // NOI18N

        jLabel23.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jLabel23.text")); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel24.setText(org.openide.util.NbBundle.getMessage(addCommandExpressionPanel.class, "addCommandExpressionPanel.jLabel24.text")); // NOI18N

        errorLabel.setColumns(20);
        errorLabel.setFont(new java.awt.Font("Tahoma", 0, 11));
        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        errorLabel.setRows(5);
        jScrollPane1.setViewportView(errorLabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel24))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectVariableBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectConstantBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectFunctionBut, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(jLabel28))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(selectArrElemBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(selectParameterBut, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addGap(142, 142, 142)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(directionsButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(deleteLastBut1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(deleteExpressionBut)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textFiledLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lBracketLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expressionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rBracketLabel))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectVariableBut, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectParameterBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(directionsButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectConstantBut, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectArrElemBut, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectFunctionBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(deleteLastBut1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteExpressionBut))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFiledLabel)
                    .addComponent(lBracketLabel)
                    .addComponent(expressionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rBracketLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selectConstantButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectConstantButActionPerformed
        addConstant();
}//GEN-LAST:event_selectConstantButActionPerformed

    private void selectVariableButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectVariableButActionPerformed
        addVariable();
}//GEN-LAST:event_selectVariableButActionPerformed

    private void selectArrElemButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectArrElemButActionPerformed
        addArrayElement();
}//GEN-LAST:event_selectArrElemButActionPerformed

    private void selectParameterButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectParameterButActionPerformed
        addParameter();
}//GEN-LAST:event_selectParameterButActionPerformed

    private void selectFunctionButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectFunctionButActionPerformed
        addFunction();
}//GEN-LAST:event_selectFunctionButActionPerformed

    private void expressionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expressionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expressionTextFieldActionPerformed

    private void addButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButActionPerformed
        addText(addBut.getText());
}//GEN-LAST:event_addButActionPerformed

    private void minusButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusButActionPerformed
        addText(minusBut.getText());
}//GEN-LAST:event_minusButActionPerformed

    private void multButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multButActionPerformed
        addText(multBut.getText());
}//GEN-LAST:event_multButActionPerformed

    private void divButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_divButActionPerformed
        addText(divBut.getText());
}//GEN-LAST:event_divButActionPerformed

    private void modButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modButActionPerformed
        addText(modBut.getText());
}//GEN-LAST:event_modButActionPerformed

    private void zeroButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zeroButActionPerformed
        addTextNoSpaces(zeroBut.getText());
}//GEN-LAST:event_zeroButActionPerformed

    private void oneButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oneButActionPerformed
        addTextNoSpaces(oneBut.getText());
}//GEN-LAST:event_oneButActionPerformed

    private void twoButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoButActionPerformed
        addTextNoSpaces(twoBut.getText());
}//GEN-LAST:event_twoButActionPerformed

    private void lParButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lParButActionPerformed
        addText(lParBut.getText());
}//GEN-LAST:event_lParButActionPerformed

    private void rParButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rParButActionPerformed
        addText(rParBut.getText());
}//GEN-LAST:event_rParButActionPerformed

    private void fiveButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiveButActionPerformed
        addTextNoSpaces(fiveBut.getText());
}//GEN-LAST:event_fiveButActionPerformed

    private void threeButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threeButActionPerformed
        addTextNoSpaces(threeBut.getText());
}//GEN-LAST:event_threeButActionPerformed

    private void fourButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourButActionPerformed
        addTextNoSpaces(fourBut.getText());
}//GEN-LAST:event_fourButActionPerformed

    private void sixButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixButActionPerformed
        addTextNoSpaces(sixBut.getText());
}//GEN-LAST:event_sixButActionPerformed

    private void sevenButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sevenButActionPerformed
        addTextNoSpaces(sevenBut.getText());
}//GEN-LAST:event_sevenButActionPerformed

    private void eightButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eightButActionPerformed
        addTextNoSpaces(eightBut.getText());
}//GEN-LAST:event_eightButActionPerformed

    private void nineButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nineButActionPerformed
        addTextNoSpaces(nineBut.getText());
}//GEN-LAST:event_nineButActionPerformed

    private void pointButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pointButActionPerformed
        addTextNoSpaces(pointBut.getText());
}//GEN-LAST:event_pointButActionPerformed

    private void deleteLastBut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteLastBut1ActionPerformed
        deleteLast();
}//GEN-LAST:event_deleteLastBut1ActionPerformed

    private void deleteExpressionButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteExpressionButActionPerformed
        clearAll();
}//GEN-LAST:event_deleteExpressionButActionPerformed

    private void directionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directionsButtonActionPerformed
             this.createDirectionsPopupWindow();
    }//GEN-LAST:event_directionsButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBut;
    private javax.swing.JButton deleteExpressionBut;
    private javax.swing.JButton deleteLastBut1;
    private javax.swing.JButton directionsButton;
    private javax.swing.JButton divBut;
    private javax.swing.JButton eightBut;
    private javax.swing.JTextArea errorLabel;
    private javax.swing.JTextField expressionTextField;
    private javax.swing.JButton fiveBut;
    private javax.swing.JButton fourBut;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lBracketLabel;
    private javax.swing.JButton lParBut;
    private javax.swing.JButton minusBut;
    private javax.swing.JButton modBut;
    private javax.swing.JButton multBut;
    private javax.swing.JButton nineBut;
    private javax.swing.JButton oneBut;
    private javax.swing.JButton pointBut;
    private javax.swing.JLabel rBracketLabel;
    private javax.swing.JButton rParBut;
    private javax.swing.JButton selectArrElemBut;
    private javax.swing.JButton selectConstantBut;
    private javax.swing.JButton selectFunctionBut;
    private javax.swing.JButton selectParameterBut;
    private javax.swing.JButton selectVariableBut;
    private javax.swing.JButton sevenBut;
    private javax.swing.JButton sixBut;
    private javax.swing.JLabel textFiledLabel;
    private javax.swing.JButton threeBut;
    private javax.swing.JButton twoBut;
    private javax.swing.JButton zeroBut;
    // End of variables declaration//GEN-END:variables

}
