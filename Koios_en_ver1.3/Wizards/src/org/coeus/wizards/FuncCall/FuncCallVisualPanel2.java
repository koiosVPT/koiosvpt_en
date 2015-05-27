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


package org.coeus.wizards.FuncCall;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards._HelpFuntions.addCommandCharPanel;
import org.coeus.wizards._HelpFuntions.addCommandConditionPanel;
import org.coeus.wizards._HelpFuntions.addCommandExpressionPanel;

import org.coeus.wizards._HelpFuntions.addCommandStringPanel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public final class FuncCallVisualPanel2 extends JPanel {
    private int selection=-1;
    private LinkedList<String> parameterObjType=null;
    private LinkedList<String> parameterTypical=null;
    private LinkedList<String> dispParameterActual=null;
    private LinkedList<String> objParameterActual=null;
    private String objScope=null;
    private int l=0;

    private static final int stringListSize=35;

    private DefaultListModel listModel = null;
    private String commandName=null;
    private FuncCallWizardAction fcwa=null;

    /** Creates new form CallVisualPanel2 */
    public FuncCallVisualPanel2(String iObjScope,String iCommandName,FuncCallWizardAction ifcwa) {
        initComponents();
        this.fcwa=ifcwa;
        parameterObjType=new LinkedList<String> ();
        parameterTypical=new LinkedList<String> ();
        dispParameterActual=new LinkedList<String> ();
        objParameterActual=new LinkedList<String> ();
        this.objScope=iObjScope;
        this.jLabel7.setText(fcwa.getDispFunctionName());
        this.commandName=iCommandName;
        editButton.setEnabled(false);

        listModel = new DefaultListModel();
        jList1.setModel(listModel);

         jList1.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int sel;
                sel=jList1.getSelectedIndex();
                if (sel>-1)
                {
                   selection=sel;
                editButton.setEnabled(true);
                }
            }

        });

         jList1.addMouseListener(new MouseListener () {

            public void mouseClicked(MouseEvent e) {
               if (selection>-1)
                  if(e.getClickCount() == 2)
                       editParameter();
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
        return "Definition of Actual Parameter(s)";
    }


    public void getActualParameters()
    {
 //   jLabel7.setText(CallWizardAction.getDispProcedureName());
         l=0;
        for (String s:fcwa.getObjActualParametersType())
        { 
          if (s.equalsIgnoreCase(WizardsDefinitions.INT))
                addItegerParameter();
          else if (s.equalsIgnoreCase(WizardsDefinitions.DOUBLE))
               addDoubleParameter();
          else if (s.equalsIgnoreCase(WizardsDefinitions.CHAR))
               addCharParameter();
          else if (s.equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
               addBooleanParameter();
          else if (s.equalsIgnoreCase(WizardsDefinitions.STRING))
               addStringParameter();
        l++;
        }
    }


    public void addItegerParameter()
    {
     addCommandExpressionPanel aEP = new addCommandExpressionPanel(this.objScope,null,this.commandName);
//     aEP.getParametersButton().setVisible(false);
     aEP.getHeaders()[0].setText("Parameter Type:");
     aEP.getHeaders()[1].setText(WizardsDefinitions.INT1);
     aEP.getHeaders()[2].setText("Name of Formal Parameter:");
     aEP.getHeaders()[3].setText(fcwa.getTypicalParameters().get(l));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Defining Actual Parameter no"+(l+1)+" of FUNCTION "+fcwa.getDispFunctionName(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidExpression(NotifyDescriptor.DEFAULT_OPTION))
         {
         dispParameterActual.add(aEP.getDisplayExpression());
         objParameterActual.add("("+WizardsDefinitions.INT+") ("+aEP.getObjectExpresssion()+")");
         parameterObjType.add(WizardsDefinitions.INT);
         parameterTypical.add(fcwa.getTypicalParameters().get(l));

       listModel.addElement(strings2ListModelItem(WizardsDefinitions.INT1,parameterTypical.peekLast(),
               aEP.getDisplayExpression()));

        }
       }
  }


    public void updateIntegerParameter()
    {
     addCommandExpressionPanel aEP = new addCommandExpressionPanel(this.objScope,dispParameterActual.get(selection),this.commandName);
//     aEP.getParametersButton().setVisible(false);
     aEP.getHeaders()[0].setText("Parameter Type:");
     aEP.getHeaders()[1].setText(WizardsDefinitions.INT);
     aEP.getHeaders()[2].setText("Name of Formal Parameter:");
     aEP.getHeaders()[3].setText(parameterTypical.get(selection));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Modifying Actual Parameter no"+(selection+1)+" of FUNCTION "+fcwa.getDispFunctionName(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidExpression(NotifyDescriptor.OK_CANCEL_OPTION))
         {
         dispParameterActual.remove(selection);
         objParameterActual.remove(selection);

         dispParameterActual.add(selection,aEP.getDisplayExpression());
         objParameterActual.add(selection,"("+WizardsDefinitions.INT+") ("+aEP.getObjectExpresssion()+")");

       listModel.remove(selection);
       listModel.insertElementAt(strings2ListModelItem(WizardsDefinitions.INT1,parameterTypical.get(selection),
               aEP.getDisplayExpression()),selection);
        }
      }
   }

    public void addDoubleParameter()
    {
     addCommandExpressionPanel aEP = new addCommandExpressionPanel(this.objScope,null,this.commandName);
    // aEP.getParametersButton().setVisible(false);
     aEP.getHeaders()[0].setText("Parameter Type:");
     aEP.getHeaders()[1].setText(WizardsDefinitions.DOUBLE1);
     aEP.getHeaders()[2].setText("Name of Formal Parameter:");
     aEP.getHeaders()[3].setText(fcwa.getTypicalParameters().get(l));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Defining Actual Parameter no"+(l+1)+" of FUNCTION "+fcwa.getDispFunctionName(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidExpression(NotifyDescriptor.DEFAULT_OPTION))
         {
         dispParameterActual.add(aEP.getDisplayExpression());
         objParameterActual.add("("+WizardsDefinitions.DOUBLE+") ("+aEP.getObjectExpresssion()+")");
         parameterObjType.add(WizardsDefinitions.DOUBLE);
         parameterTypical.add(fcwa.getTypicalParameters().get(l));

       listModel.addElement(strings2ListModelItem(WizardsDefinitions.DOUBLE1,parameterTypical.peekLast(),
               aEP.getDisplayExpression()));

        }
       }
    }

    public void updateDoubleParameter()
    {
     addCommandExpressionPanel aEP = new addCommandExpressionPanel(this.objScope,dispParameterActual.get(selection),this.commandName);
    // aEP.getParametersButton().setVisible(false);
     aEP.getHeaders()[0].setText("Parameter Type:");
     aEP.getHeaders()[1].setText(WizardsDefinitions.DOUBLE1);
     aEP.getHeaders()[2].setText("Name of Formal Parameter:");
     aEP.getHeaders()[3].setText(parameterTypical.get(selection));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Modifying Actual Parameter no"+(selection+1)+" of FUNCTION "+fcwa.getDispFunctionName(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aEP.checkValidExpression(NotifyDescriptor.OK_CANCEL_OPTION))
         {
         dispParameterActual.remove(selection);
         objParameterActual.remove(selection);

         dispParameterActual.add(selection,aEP.getDisplayExpression());
         objParameterActual.add(selection,"("+WizardsDefinitions.DOUBLE+") ("+aEP.getObjectExpresssion()+")");

       listModel.remove(selection);
       listModel.insertElementAt(strings2ListModelItem(WizardsDefinitions.DOUBLE1,parameterTypical.get(selection),
               aEP.getDisplayExpression()),selection);
        }
      }
    }


    public void addCharParameter()
    {
     addCommandCharPanel aEP = new addCommandCharPanel(this.objScope,null,this.commandName);
   //  aEP.getParametersButton().setVisible(false);
     aEP.getHeaders()[0].setText("Parameter Type:");
     aEP.getHeaders()[1].setText(WizardsDefinitions.CHAR1);
     aEP.getHeaders()[2].setText("Name of Formal Parameter:");
     aEP.getHeaders()[3].setText(fcwa.getTypicalParameters().get(l));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Defining Actual Parameter no"+(l+1)+" of FUNCTION "+fcwa.getDispFunctionName(),
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
                                     dispParameterActual.add(aEP.getDisplayChar());
                                     objParameterActual.add("("+WizardsDefinitions.CHAR+") ("+aEP.getObjChar()+")");
                                     parameterObjType.add(WizardsDefinitions.CHAR);
                                     parameterTypical.add(fcwa.getTypicalParameters().get(l));

                                     listModel.addElement(strings2ListModelItem(WizardsDefinitions.CHAR1,parameterTypical.peekLast(),
                                     aEP.getDisplayChar()));
                                     }
                              }
                        }
                    else
                    {
               ////////////////////////////////////
                     dispParameterActual.add(aEP.getDisplayChar());
                     objParameterActual.add("("+WizardsDefinitions.CHAR+") ("+aEP.getObjChar()+")");
                     parameterObjType.add(WizardsDefinitions.CHAR);
                     parameterTypical.add(fcwa.getTypicalParameters().get(l));

                     listModel.addElement(strings2ListModelItem(WizardsDefinitions.CHAR1,parameterTypical.peekLast(),
                     aEP.getDisplayChar()));
                    }
                 
            }
      }
    }

    public void updateCharParameter()
    {
     addCommandCharPanel aEP = new addCommandCharPanel(this.objScope,
             dispParameterActual.get(selection),this.commandName);
   //  aEP.getParametersButton().setVisible(false);
     aEP.getHeaders()[0].setText("Parameter Type:");
     aEP.getHeaders()[1].setText(WizardsDefinitions.CHAR1);
     aEP.getHeaders()[2].setText("Name of Formal Parameter:");
     aEP.getHeaders()[3].setText(parameterTypical.get(selection));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aEP,
      "Modifying Actual Parameter no"+(selection+1)+" of FUNCTION "+fcwa.getDispFunctionName(),
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
                                 dispParameterActual.remove(selection);
                                 objParameterActual.remove(selection);

                                 dispParameterActual.add(selection,aEP.getDisplayChar());
                                 objParameterActual.add(selection,"("+WizardsDefinitions.CHAR+") ("+aEP.getObjChar()+")");

                                 listModel.remove(selection);
                                 listModel.insertElementAt(strings2ListModelItem(WizardsDefinitions.CHAR1,parameterTypical.get(selection),
                                 aEP.getDisplayChar()),selection);
                                 }
                             }
                        }
                    else
                    {
               ////////////////////////////////////
                     dispParameterActual.remove(selection);
                     objParameterActual.remove(selection);

                     dispParameterActual.add(selection,aEP.getDisplayChar());
                     objParameterActual.add(selection,"("+WizardsDefinitions.CHAR+") ("+aEP.getObjChar()+")");

                     listModel.remove(selection);
                     listModel.insertElementAt(strings2ListModelItem(WizardsDefinitions.CHAR1,parameterTypical.get(selection),
                     aEP.getDisplayChar()),selection);
                    }
              }
          
      }
  }


      public void addStringParameter()
    {
     addCommandStringPanel aSP = new addCommandStringPanel(this.objScope,null,this.commandName);
   //  aEP.getParametersButton().setVisible(false);
     aSP.getHeaders()[0].setText("Parameter Type:");
     aSP.getHeaders()[1].setText(WizardsDefinitions.STRING1);
     aSP.getHeaders()[2].setText("Name of Formal Parameter:");
     aSP.getHeaders()[3].setText(fcwa.getTypicalParameters().get(l));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aSP,
      "Defining Actual Parameter no"+(l+1)+" of FUNCTION "+fcwa.getDispFunctionName(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aSP.checkValidInput(NotifyDescriptor.DEFAULT_OPTION))
         {
         dispParameterActual.add(aSP.getDisplayString());
//         objParameterActual.add("("+WizardsDefinitions.STRING+") ("+aSP.getObjString()+")");
         objParameterActual.add(aSP.getObjString());
         parameterObjType.add(WizardsDefinitions.STRING);
         parameterTypical.add(fcwa.getTypicalParameters().get(l));

       listModel.addElement(strings2ListModelItem(WizardsDefinitions.STRING1,parameterTypical.peekLast(),
               aSP.getDisplayString()));
        }
      }
    }



   public void updateStringParameter()
    {
     addCommandStringPanel aSP = new addCommandStringPanel(this.objScope,
             dispParameterActual.get(selection),this.commandName);
   //  aEP.getParametersButton().setVisible(false);
     aSP.getHeaders()[0].setText("Parameter Type:");
     aSP.getHeaders()[1].setText(WizardsDefinitions.STRING1);
     aSP.getHeaders()[2].setText("Name of Formal Parameter:");
     aSP.getHeaders()[3].setText(parameterTypical.get(selection));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aSP,
      "Modifying Actual Parameter no"+(selection+1)+" of FUNCTION "+fcwa.getDispFunctionName(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aSP.checkValidInput(NotifyDescriptor.OK_CANCEL_OPTION)  && !aSP.getObjNames().isEmpty())
         {

           dispParameterActual.remove(selection);
         objParameterActual.remove(selection);

         dispParameterActual.add(selection,aSP.getDisplayString());
         objParameterActual.add(selection,"("+WizardsDefinitions.STRING+") ("+aSP.getObjString()+")");
         objParameterActual.add(selection,aSP.getObjString());

       listModel.remove(selection);
       listModel.insertElementAt(strings2ListModelItem(WizardsDefinitions.STRING1,parameterTypical.get(selection),
               aSP.getDisplayString()),selection);

        }
      }
    }


    public void addBooleanParameter()
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,null,this.commandName);
 //    aCP.getParametersButton().setVisible(false);
     aCP.getHeaders()[0].setText("Parameter Type:");
     aCP.getHeaders()[1].setText(WizardsDefinitions.BOOLEAN1);
     aCP.getHeaders()[2].setText("Name of Formal Parameter:");
     aCP.getHeaders()[3].setText(fcwa.getTypicalParameters().get(l));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Defining Actual Parameter no"+(l+1)+" of FUNCTION "+fcwa.getDispFunctionName(),
      NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


     Object answer= DialogDisplayer.getDefault().notify(d);

      while (answer==NotifyDescriptor.CLOSED_OPTION)
        answer= DialogDisplayer.getDefault().notify(d);

     if (answer==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.DEFAULT_OPTION))
         {
         dispParameterActual.add(aCP.getDisplayCondition());
         objParameterActual.add("("+WizardsDefinitions.BOOLEAN+") ("+aCP.getObjectCondition()+")");
         parameterObjType.add(WizardsDefinitions.BOOLEAN);
         parameterTypical.add(fcwa.getTypicalParameters().get(l));

       listModel.addElement(strings2ListModelItem(WizardsDefinitions.BOOLEAN1,parameterTypical.peekLast(),
               aCP.getDisplayCondition()));
        }
       }
    }




    public void updateBooleanParameter()
    {
     addCommandConditionPanel aCP = new addCommandConditionPanel(this.objScope,dispParameterActual.get(selection),this.commandName);
//     aCP.getParametersButton().setVisible(false);
     aCP.getHeaders()[0].setText("Parameter Type:");
     aCP.getHeaders()[1].setText(WizardsDefinitions.BOOLEAN1);
     aCP.getHeaders()[2].setText("Name of Formal Parameter:");
     aCP.getHeaders()[3].setText(parameterTypical.get(selection));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(aCP,
      "Modifying Actual Parameter no"+(selection+1)+" of FUNCTION "+fcwa.getDispFunctionName(),
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

      if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
      {
        if (aCP.checkValidCondition(NotifyDescriptor.OK_CANCEL_OPTION))
         {
         dispParameterActual.remove(selection);
         objParameterActual.remove(selection);

         dispParameterActual.add(selection,aCP.getDisplayCondition());
         objParameterActual.add(selection,"("+WizardsDefinitions.BOOLEAN+") ("+aCP.getObjectCondition()+")");

       listModel.remove(selection);
       listModel.insertElementAt(strings2ListModelItem(WizardsDefinitions.BOOLEAN1,parameterTypical.get(selection),
               aCP.getDisplayCondition()),selection);
        }
      }
    }


    public void editParameter()
    {
    if (selection>-1)
     {
      if (parameterObjType.get(selection).equalsIgnoreCase(WizardsDefinitions.INT))
              updateIntegerParameter();
      else if (parameterObjType.get(selection).equalsIgnoreCase(WizardsDefinitions.DOUBLE))
              updateDoubleParameter();
      else if (parameterObjType.get(selection).equalsIgnoreCase(WizardsDefinitions.CHAR))
              updateCharParameter();
      else if (parameterObjType.get(selection).equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
              updateBooleanParameter();
      else if (parameterObjType.get(selection).equalsIgnoreCase(WizardsDefinitions.STRING))
              updateStringParameter();
      jList1.setSelectedIndex(selection);
     }

    }


  public String strings2ListModelItem(String type,String typicalName,String actualParameter)
     { String nType,nTypicalName;

       nType=padWithSpaceChars(type);
       if (typicalName.length()>stringListSize)
           nTypicalName=typicalName.substring(0,stringListSize);
       else
         nTypicalName = padWithSpaceChars(typicalName);

     return nType+nTypicalName+actualParameter;
     }

    private String padWithSpaceChars (String inString)
    {String temp="",outString="";
    int difference = stringListSize- inString.length();
    for (int i=0;i<difference;i++)
        temp=temp+" ";
    outString=inString+temp;
    return outString;
    }


    public DefaultListModel getListModel()
    {return this.listModel;}


    public  LinkedList<String> getParameterTypical()
    {return this.parameterTypical;}

    public LinkedList<String> getDispParameterActual()
    {return this.dispParameterActual;}

    public LinkedList<String> getObjParameterActual()
    {return this.objParameterActual;}

    public LinkedList<String> getParameterObjType()
    {return this.parameterObjType;}

     public JLabel getLabelProcName()
    {return this.jLabel7;}


    public  void setParameterTypical(LinkedList<String> pt)
    {this.parameterTypical=pt;}

    public void setDispParameterActual(LinkedList<String> pa)
    {this.dispParameterActual=pa;}

    public void setObjParameterActual(LinkedList<String> opa)
    {this.objParameterActual=opa;}

    public void setParameterObjType(LinkedList<String> pot)
    {this.parameterObjType=pot;}



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        editButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(FuncCallVisualPanel2.class, "FuncCallVisualPanel2.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(FuncCallVisualPanel2.class, "FuncCallVisualPanel2.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(FuncCallVisualPanel2.class, "FuncCallVisualPanel2.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(FuncCallVisualPanel2.class, "FuncCallVisualPanel2.jLabel4.text")); // NOI18N

        jScrollPane1.setViewportView(jList1);

        org.openide.awt.Mnemonics.setLocalizedText(editButton, org.openide.util.NbBundle.getMessage(FuncCallVisualPanel2.class, "FuncCallVisualPanel2.editButton.text")); // NOI18N
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(editButton)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editButton))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(FuncCallVisualPanel2.class, "FuncCallVisualPanel2.jPanel3.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(FuncCallVisualPanel2.class, "FuncCallVisualPanel2.jLabel7.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(416, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        editParameter();
}//GEN-LAST:event_editButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

