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


package org.coeus.wizards.Read;

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
import org.coeus.btvpalette.myAdvanceAction;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards._HelpFuntions.getArrayElementsList;
import org.coeus.wizards._HelpFuntions.getParametersList;
import org.coeus.wizards._HelpFuntions.getReadArrayElementsListPanel;
import org.coeus.wizards._HelpFuntions.getReadParametersListPanel;
import org.coeus.wizards._HelpFuntions.getReadVariablesListPanel;
import org.coeus.wizards._HelpFuntions.getVariablesList;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public final class ReadVisualPanel1 extends JPanel {
    private String objScope;

    private LinkedList <String> messages= new LinkedList<String>();
    private LinkedList <String> dispNames= new LinkedList<String>();
    private LinkedList <String> objNames= new LinkedList<String>();
    private LinkedList <String> dispTypes= new LinkedList<String>();
    private LinkedList <String> objTypes= new LinkedList<String>();
    private LinkedList <String> dispCategory= new LinkedList<String>();

    private static final int stringListSize=35;
    private int selection=-1;
    private DefaultListModel listModel = null;

     private getArrayElementsList gAEL=null;
     private getParametersList gPL= null;
     private getVariablesList gVL = null;

    /** Creates new form ReadVisualPanel1 */
    public ReadVisualPanel1(String iObjScope) {
        initComponents();

              //customize tool tip apperance
//        UIManager.put("ToolTip.background", new ColorUIResource(255, 247, 200)); // The color is #fff7c8.
        UIManager.put("ToolTip.background", new ColorUIResource(220, 220, 220)); // The color is #fff7c8.
        Border border = BorderFactory.createLineBorder(new Color(76,79,83)); // The color is #4c4f53.
        UIManager.put("ToolTip.border", border);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(20000);// 20 seconds
        //end customize tool tip apperance

        selectVarBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "In order to store a value given by the user in a variable, click the \"" + "<b>Select Variable</b>"+ "\" button."+"</font></html>");

        selectArrElemBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "In order to store a value given by the user in an array element, click the \"" + "<b>Select Array Element</b>"+ "\" button."+"</font></html>");

        selectParBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "In order to store a value given by the user in an input parameter, click the \"" + "<b>Select Input Parameter</b>"+ "\" button."+"</font></html>");



        showAdvanceButtons();
        this.objScope=iObjScope;
        setEditDeleteButtonsEnabled(false);
        setUpDownButtonsEnabled(false);

        listModel = new DefaultListModel();
        jList1.setModel(listModel);

         gAEL = new getArrayElementsList(this.objScope,"all_datatypes");
         gPL  = new getParametersList(this.objScope,"all_datatypes");
         gVL = new getVariablesList(this.objScope,"all_datatypes");

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

    @Override
    public String getName() {
        return "Item(s) Definition";
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
    private void setUpDownButtonsEnabled(boolean tf)
    {
     UpBut.setEnabled(tf);
     DownBut.setEnabled(tf);
    }

   
  ///GETTERS
    public LinkedList<String> getMessages()
    {return this.messages;}

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
    public void setMessages(LinkedList<String> iMessages)
    {this.messages=iMessages;}

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


     public String strings2ListModelItem(String name,String category,String type,String message)
     { String result="",nname,ncategory,ntype,nmessage;
      
       if (name.length()>stringListSize)
           nname=name.substring(0,stringListSize);
       else
         nname = padWithSpaceChars(name);

       ncategory=padWithSpaceChars(category);
       ntype=padWithSpaceChars(type);
      
      nmessage=message;

     result=nname+ncategory+ntype+nmessage;
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
      getReadParametersListPanel gPLPanel = new getReadParametersListPanel(gPL,null,null,null,null,null);

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(gPLPanel,"Input Parameter Selection",
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(gPLPanel.getDName()!=null)
       {
           if (!gPLPanel.getDName().equalsIgnoreCase(""))
           {
           messages.add(gPLPanel.getMessage());
           dispNames.add(gPLPanel.getDName());
           objNames.add(gPLPanel.getOName());
           dispTypes.add(gPLPanel.getDType());
           objTypes.add(gPLPanel.getOType());
           dispCategory.add(WizardsDefinitions.PARAMETER);

//           if (listModel.isEmpty())  setEditDeleteButtonsEnabled(true);
       listModel.addElement(strings2ListModelItem(gPLPanel.getDName(),WizardsDefinitions.PARAMETER,gPLPanel.getDType(),gPLPanel.getMessage()));

           }
       }
   }
 }



public void updateParameter()
    {
      getReadParametersListPanel gPLPanel = new getReadParametersListPanel(gPL,messages.get(selection),
         dispNames.get(selection),objNames.get(selection),dispTypes.get(selection),objTypes.get(selection));

      NotifyDescriptor d =new NotifyDescriptor.Confirmation(gPLPanel,"Input Parameter Selection",
      NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(gPLPanel.getDName()!=null)
       {
           if (!gPLPanel.getDName().equalsIgnoreCase(""))
           {
           messages.remove(selection);
           dispNames.remove(selection);
           objNames.remove(selection);
           dispTypes.remove(selection);
           objTypes.remove(selection);
           dispCategory.remove(selection);


           messages.add(selection,gPLPanel.getMessage());
           dispNames.add(selection,gPLPanel.getDName());
           objNames.add(selection,gPLPanel.getOName());
           dispTypes.add(selection,gPLPanel.getDType());
           objTypes.add(selection,gPLPanel.getOType());
           dispCategory.add(selection,WizardsDefinitions.PARAMETER);

//           if (listModel.isEmpty())  setEditDeleteButtonsEnabled(true);

       listModel.remove(selection);
       listModel.insertElementAt(strings2ListModelItem(gPLPanel.getDName(),WizardsDefinitions.PARAMETER,gPLPanel.getDType(),gPLPanel.getMessage()), selection);

           }
       }
   }
 }



public void updateVariable()
{
 getReadVariablesListPanel gVLPanel = new getReadVariablesListPanel(gVL,messages.get(selection),
         dispNames.get(selection),objNames.get(selection),dispTypes.get(selection),objTypes.get(selection));

       NotifyDescriptor d =new NotifyDescriptor.Confirmation(gVLPanel,"Variable Selection",
       NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(gVLPanel.getDName()!=null)
       {
           if (!gVLPanel.getDName().equalsIgnoreCase(""))
           {  
            messages.remove(selection);
           dispNames.remove(selection);
           objNames.remove(selection);
           dispTypes.remove(selection);
           objTypes.remove(selection);
           dispCategory.remove(selection);


           messages.add(selection,gVLPanel.getMessage());
           dispNames.add(selection,gVLPanel.getDName());
           objNames.add(selection,gVLPanel.getOName());
           dispTypes.add(selection,gVLPanel.getDType());
           objTypes.add(selection,gVLPanel.getOType());
           dispCategory.add(selection,WizardsDefinitions.VARIABLE);

//           if (listModel.isEmpty())     setEditDeleteButtonsEnabled(true);

           listModel.remove(selection);
           listModel.insertElementAt(strings2ListModelItem(gVLPanel.getDName(),WizardsDefinitions.VARIABLE,gVLPanel.getDType(),gVLPanel.getMessage()),selection);
          }
       }
   }
}


public void addVariable()
{
 getReadVariablesListPanel gVLPanel = new getReadVariablesListPanel(gVL,null,null,null,null,null);

       NotifyDescriptor d =new NotifyDescriptor.Confirmation(gVLPanel,"Variable Selection",
       NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(gVLPanel.getDName()!=null)
       {
           if (!gVLPanel.getDName().equalsIgnoreCase(""))
           {
           messages.add(gVLPanel.getMessage());
           dispNames.add(gVLPanel.getDName());
           objNames.add(gVLPanel.getOName());
           dispTypes.add(gVLPanel.getDType());
           objTypes.add(gVLPanel.getOType());
           dispCategory.add(WizardsDefinitions.VARIABLE);

//           if (listModel.isEmpty())     setEditDeleteButtonsEnabled(true);
           listModel.addElement(strings2ListModelItem(gVLPanel.getDName(),WizardsDefinitions.VARIABLE,gVLPanel.getDType(),gVLPanel.getMessage()));

           }
       }
   }
}


public void addArrayElement(String name)
{
    getReadArrayElementsListPanel gAELPanel = new getReadArrayElementsListPanel(gAEL,null,null,null,null,null);

       NotifyDescriptor d =new NotifyDescriptor.Confirmation(gAELPanel,"Array Element Selection",
       NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(gAELPanel.getDArrayName()!=null)
       {
         if (!gAELPanel.getReturnDisplaeStateMent().equalsIgnoreCase(""))
         {
           messages.add(gAELPanel.getMessage());
           dispNames.add(gAELPanel.getReturnDisplaeStateMent());
           objNames.add(gAELPanel.getReturnObjectStatement());
           dispTypes.add(gAELPanel.getDArrayType());
           objTypes.add(gAELPanel.getOArrayType());
           dispCategory.add(WizardsDefinitions.ARRAY_ELEMANT);

//           if (listModel.isEmpty())      setEditDeleteButtonsEnabled(true);
          listModel.addElement(strings2ListModelItem(gAELPanel.getReturnDisplaeStateMent(),WizardsDefinitions.ARRAY_ELEMANT,gAELPanel.getDArrayType(),gAELPanel.getMessage()));

       }
       }
   }
}


public void updateArrayElement()
{
    getReadArrayElementsListPanel gAELPanel = new getReadArrayElementsListPanel(gAEL,messages.get(selection),
         dispNames.get(selection),objNames.get(selection),dispTypes.get(selection),objTypes.get(selection));

       NotifyDescriptor d =new NotifyDescriptor.Confirmation(gAELPanel,"Array Element Selection",
       NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);


   if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
   {
       if(gAELPanel.getDArrayName()!=null)
       {
         if (!gAELPanel.getReturnDisplaeStateMent().equalsIgnoreCase(""))
         { 
           messages.remove(selection);
           dispNames.remove(selection);
           objNames.remove(selection);
           dispTypes.remove(selection);
           objTypes.remove(selection);
           dispCategory.remove(selection);

           messages.add(selection,gAELPanel.getMessage());
           dispNames.add(selection,gAELPanel.getReturnDisplaeStateMent());
           objNames.add(selection,gAELPanel.getReturnObjectStatement());
           dispTypes.add(selection,gAELPanel.getDArrayType());
           objTypes.add(selection,gAELPanel.getOArrayType());
           dispCategory.add(WizardsDefinitions.ARRAY_ELEMANT);

//           if (listModel.isEmpty())      setEditDeleteButtonsEnabled(true);

          listModel.remove(selection);
          listModel.insertElementAt(strings2ListModelItem(gAELPanel.getReturnDisplaeStateMent(),WizardsDefinitions.ARRAY_ELEMANT,gAELPanel.getDArrayType(),gAELPanel.getMessage()),selection);
        }
       }
   }
}



private void editElement()
{
 if (selection>-1)
 {
  if (dispCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.PARAMETER))
          updateParameter();
  else if (dispCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.VARIABLE))
          updateVariable();
  else if (dispCategory.get(selection).equalsIgnoreCase(WizardsDefinitions.ARRAY_ELEMANT))
          updateArrayElement();
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
{String s1,s2,s3,s4,s5,s6;
    if (selection>0)
    {
     int selected=selection;
     String  object= (String)listModel.getElementAt(selected);
     listModel.remove(selected);
     listModel.insertElementAt(object,selected-1);
     jList1.setSelectedIndex(selected-1);

          s1= messages.get(selected);
          s2= dispNames.get(selected);
          s3= objNames.get(selected);
          s4= dispTypes.get(selected);
          s5= objTypes.get(selected);
          s6= dispCategory.get(selected);

           messages.remove(selected);
           dispNames.remove(selected);
           objNames.remove(selected);
           dispTypes.remove(selected);
           objTypes.remove(selected);
           dispCategory.remove(selected);


           messages.add(selected-1, s1);
           dispNames.add(selected-1, s2);
           objNames.add(selected-1, s3);
           dispTypes.add(selected-1, s4);
           objTypes.add(selected-1, s5);
           dispCategory.add(selected-1, s6);

    
    }
}


private void oneElementDown()
{String s1,s2,s3,s4,s5,s6;
    if (selection>-1 && selection<listModel.getSize()-1)
    {
     int selected=selection;
     String  object= (String)listModel.getElementAt(selected);
     listModel.remove(selected);
     listModel.insertElementAt(object,selected+1);
     jList1.setSelectedIndex(selected+1);

          s1= messages.get(selected);
          s2= dispNames.get(selected);
          s3= objNames.get(selected);
          s4= dispTypes.get(selected);
          s5= objTypes.get(selected);
          s6= dispCategory.get(selected);

           messages.remove(selected);
           dispNames.remove(selected);
           objNames.remove(selected);
           dispTypes.remove(selected);
           objTypes.remove(selected);
           dispCategory.remove(selected);


           messages.add(selected+1, s1);
           dispNames.add(selected+1, s2);
           objNames.add(selected+1, s3);
           dispTypes.add(selected+1, s4);
           objTypes.add(selected+1, s5);
           dispCategory.add(selected+1, s6);

   
    }
}


private void clearAll()
{

     NotifyDescriptor d =new NotifyDescriptor.Confirmation(
                "You are going to delete all items in the list!\n" +
                "Do you want to continue?",
                "Deletion Confirmation",
     NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.WARNING_MESSAGE);

         if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION) {
          
           messages.clear();
           dispNames.clear();
           objNames.clear();
           dispTypes.clear();
           objTypes.clear();
           dispCategory.clear();

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
           messages.remove(selection);
           dispNames.remove(selection);
           objNames.remove(selection);
           dispTypes.remove(selection);
           objTypes.remove(selection);
           dispCategory.remove(selection);

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



  private void createDirectionsPopupWindow()
   {


       String balloonText="<html><div align=\"justify\"><font color=\"#000000\"size=\"4\" face=\"Tahoma\">" +
         
          "The execution of a READ input command temporarily halts the run of the program and<br>" +
          "waits for the user to enter (input) data from the keyboard. Click the <b>«Select Variable»</b><br>" +
          "button, to store data that will be entered by the user to a variable. Click the <b>«Select<br>" +
          "Array Element»</b> button, to store data that will be entered by the user to an array element.<br>" +
          "Click the <b>«Select Input Parameter»</b> button, to store data that will be entered by the<br>" +
          "user to an input parameter. You can get information about each button, by placing the<br>" +
          "mouse over it. Each time one of these buttons is clicked, your selection is added in the list with<br>" +
          "the items, which will store the data that will be entered by the user. The number of values<br>" +
          "that the user will be asked to enter, during the execution of the READ command, is equal<br>" +
          "to the number of the items in the list. The first value that will be entered by the user will<br>" +
          "be stored in the first item (item at the top) in the list. The second value will be stored<br>" +
          "in the second item and so on. You can change the order of the items in the list by clicking<br>" +
          "the <b>«Up»</b> or the <b>«Down»</b> button. To modify an item, click the <b>«Modify Selected<br>" +
          "Item»</b> button.  You can remove an item from the list by clicking the <b>«Delete Selected<br>" +
          "Item»</b> or the <b>«Delete All Items»</b> button.</font></div></html>";

       BalloonTipStyle edgedLook = new RoundedBalloonStyle(10,10,Color.WHITE, Color.BLUE);

     new BalloonTip(this.directionsButton,balloonText,edgedLook,Orientation.RIGHT_ABOVE,AttachLocation.SOUTHWEST,40,20,true  );

   }


    public DefaultListModel getListModel()
    {return this.listModel;}


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectArrElemBut = new javax.swing.JButton();
        selectVarBut = new javax.swing.JButton();
        selectParBut = new javax.swing.JButton();
        directionsButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        EditBut = new javax.swing.JButton();
        DeleteSelectedBut = new javax.swing.JButton();
        DeleteAllBut = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        UpBut = new javax.swing.JButton();
        DownBut = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(selectArrElemBut, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.selectArrElemBut.text")); // NOI18N
        selectArrElemBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectArrElemBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectArrElemButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(selectVarBut, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.selectVarBut.text")); // NOI18N
        selectVarBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectVarBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectVarButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(selectParBut, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.selectParBut.text")); // NOI18N
        selectParBut.setPreferredSize(new java.awt.Dimension(155, 25));
        selectParBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectParButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(directionsButton, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.directionsButton.text")); // NOI18N
        directionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directionsButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.jLabel12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.jLabel13.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.jLabel14.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel15, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.jLabel15.text")); // NOI18N

        jScrollPane1.setViewportView(jList1);

        org.openide.awt.Mnemonics.setLocalizedText(EditBut, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.EditBut.text")); // NOI18N
        EditBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(DeleteSelectedBut, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.DeleteSelectedBut.text")); // NOI18N
        DeleteSelectedBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteSelectedButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(DeleteAllBut, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.DeleteAllBut.text")); // NOI18N
        DeleteAllBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteAllButActionPerformed(evt);
            }
        });

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel16, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.jLabel16.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(UpBut, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.UpBut.text")); // NOI18N
        UpBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpButActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(DownBut, org.openide.util.NbBundle.getMessage(ReadVisualPanel1.class, "ReadVisualPanel1.DownBut.text")); // NOI18N
        DownBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(EditBut, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DeleteSelectedBut, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DeleteAllBut, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(DownBut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(UpBut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(9, 9, 9)))
                        .addGap(10, 10, 10)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(UpBut)
                        .addGap(11, 11, 11)
                        .addComponent(DownBut))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EditBut)
                    .addComponent(DeleteSelectedBut)
                    .addComponent(DeleteAllBut)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectVarBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(selectArrElemBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(selectParBut, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(directionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(selectParBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(selectArrElemBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(selectVarBut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(directionsButton))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void DeleteAllButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteAllButActionPerformed
       clearAll();
}//GEN-LAST:event_DeleteAllButActionPerformed

    private void selectParButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectParButActionPerformed
    addParameter();
}//GEN-LAST:event_selectParButActionPerformed

    private void selectVarButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectVarButActionPerformed
     addVariable();
}//GEN-LAST:event_selectVarButActionPerformed

    private void DeleteSelectedButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteSelectedButActionPerformed
      clearOne();
}//GEN-LAST:event_DeleteSelectedButActionPerformed

    private void selectArrElemButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectArrElemButActionPerformed
    addArrayElement(null);
}//GEN-LAST:event_selectArrElemButActionPerformed

    private void EditButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButActionPerformed
     editElement();
}//GEN-LAST:event_EditButActionPerformed

    private void UpButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpButActionPerformed
       oneElementUp();
    }//GEN-LAST:event_UpButActionPerformed

    private void DownButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DownButActionPerformed
       oneElementDown();
    }//GEN-LAST:event_DownButActionPerformed

    private void directionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directionsButtonActionPerformed
       createDirectionsPopupWindow();
    }//GEN-LAST:event_directionsButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeleteAllBut;
    private javax.swing.JButton DeleteSelectedBut;
    private javax.swing.JButton DownBut;
    private javax.swing.JButton EditBut;
    private javax.swing.JButton UpBut;
    private javax.swing.JButton directionsButton;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton selectArrElemBut;
    private javax.swing.JButton selectParBut;
    private javax.swing.JButton selectVarBut;
    // End of variables declaration//GEN-END:variables
}

