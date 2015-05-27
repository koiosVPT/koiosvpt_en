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
 * getVariableList.java
 *
 * Created on 3 Ιουν 2009, 10:52:44 μμ
 */

package org.coeus.wizards._HelpFuntions;



import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class getReadArrayElementsListPanel extends javax.swing.JPanel {

 
    private String [] DispNames=null;
    private String [] ObjNames=null;
    private String [] DispTypes=null;
    private String [] ObjTypes=null;
    private String [] Dim1=null;
    private String [] Dim2=null;
    private String [] listObjects=null;


    private getArrayElementsList gAEL=null;

    private String dArrayName=null;
    private String oArrayName=null;
    private String dArrayType=null;
    private String oArrayType=null;
    private String dim1=null;
    private String dim2=null;

    private String dIndexNameR=null;
    private String oIndexNameR=null;
    private String dIndexNameC=null;
    private String oIndexNameC=null;

    private String[] indexDisplayValuesR=null;
    private String[] indexObjectValuesR=null;
    private String[] indexDisplayValuesC=null;
    private String[] indexObjectValuesC=null;

    private String[] indexListObjectsR=null;
    private String[] indexListObjectsC=null;

    private String returnDisplayStatement="";
    private String returnObjectStatement="";

    private String objScope=null;
    private String commandName=WizardsDefinitions.READ;

    /** Creates new form getArrayElementsListList */
    public getReadArrayElementsListPanel(getArrayElementsList igAEL,String message,String dname,String oname,String
            dtype,String otype) {

        initComponents();
        this.gAEL=igAEL;
        this.objScope=gAEL.getObjScope();

        CreateIndexBut.setEnabled(false);
        columnComponentsSetVisisble(false);


         String [][] strings = gAEL.getStringArraystWithArrays();
         DispNames=strings[0];
         ObjNames=strings[1];
         DispTypes=strings[2];
         ObjTypes=strings[3];
         Dim1=strings[4];
         Dim2=strings[5];

           listObjects=new String [DispNames.length];
         for (int lop=0;lop<listObjects.length;lop++)
             listObjects[lop]=DispTypes[lop]+" "+DispNames[lop];//+" ("+DispTypes[lop]+")";

   CreateIndexBut.setToolTipText("<html><font color=\"#003333\""+"size=\"4\" face=\"Tahoma\">"+
           "Click the \"" + "<b>Create Index(es) Expression</b>"+ "\" button, to create<br>" +
           "complex expressions for the index(es) of the array, e.g. [2*i+1].</font></html>");

        if (DispNames!=null)
        {
        arrayList.setFixedCellWidth(120);
        arrayIndexListR.setFixedCellWidth(48);
        arrayIndexListC.setFixedCellWidth(48);

        arrayList.setListData(listObjects);
        arrayList.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                                       
                     returnDisplayStatement="";
                     returnObjectStatement="";
              
                     columnComponentsSetVisisble(false);
                    
                    int selection=arrayList.getSelectedIndex();
                    dArrayName=DispNames[selection];
                    oArrayName=ObjNames[selection];
                    dArrayType=DispTypes[selection];
                    oArrayType=ObjTypes[selection];
                    dim1=Dim1[selection];
                    dim2=Dim2[selection];


                    jLabel5.setText(dArrayName);
                    //jLabel11.setText((String)arrayList.getSelectedValue());
                    jLabel17.setText("Select Row Index");
                    CreateIndexBut.setEnabled(true);                  

                   

                    if (Integer.valueOf(dim2)==1)
                    {
                   // columnComponentsSetVisisble(false);
                    String [][] indexStrings =gAEL.calculateIndexValues(Integer.valueOf(dim1));
                    indexDisplayValuesR=indexStrings[0];
                    indexObjectValuesR=indexStrings[1];
                    indexListObjectsR=indexStrings[2];
                    arrayIndexListR.setListData(indexListObjectsR);
                    }
                    else
                    {
                      columnComponentsSetVisisble(true);
                      String [][] indexStrings =gAEL.calculateIndexValues(Integer.valueOf(dim1));
                      indexDisplayValuesR=indexStrings[0];
                      indexObjectValuesR=indexStrings[1];
                      indexListObjectsR=indexStrings[2];
                      arrayIndexListR.setListData(indexListObjectsR);
                     
                      indexStrings =gAEL.calculateIndexValues(Integer.valueOf(dim2));
                      indexDisplayValuesC=indexStrings[0];
                      indexObjectValuesC=indexStrings[1];
                      indexListObjectsC=indexStrings[2];
                      arrayIndexListC.setListData(indexListObjectsC);
                      arrayIndexListC.setEnabled(false);
                    }
                }
            });

    
        arrayIndexListR.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    int selection=arrayIndexListR.getSelectedIndex();
                    if (selection>-1)
                    {
                        dIndexNameR=indexDisplayValuesR[selection];
                        oIndexNameR=indexObjectValuesR[selection];
                        arrayIndexListC.setEnabled(true);
                        if (Integer.valueOf(dim2)==1)
                        {
                            jLabel5.setText(dArrayName);
                            jLabel17.setText("["+dIndexNameR+"]");

                            returnDisplayStatement=jLabel5.getText()+jLabel17.getText();
                            returnObjectStatement=oArrayName+"["+oIndexNameR+"]";
                        }
                        else
                        {
                           jLabel5.setText(dArrayName);
                           returnDisplayStatement="";
                           returnObjectStatement="";
                           jLabel17.setText("Select Column Index");
                        }
                    }
                }
            });


           arrayIndexListC.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    
                    int selection=arrayIndexListC.getSelectedIndex();
                    if (selection>-1)
                    {
                        dIndexNameC=indexDisplayValuesC[selection];
                        oIndexNameC=indexObjectValuesC[selection];


                        jLabel17.setText("["+dIndexNameR+"]"+"["+dIndexNameC+"]");

                        returnDisplayStatement=jLabel5.getText()+jLabel17.getText();
                        returnObjectStatement=oArrayName+"["+oIndexNameR+"]"+"["+oIndexNameC+"]";
                    }
                }
            });


          ////If update set old values to components
          if(message!=null && dname!=null && oname!=null && dtype!=null && otype!=null)
          {
           message=message.substring(1,message.length()-1);
           messageTextField.setText(message);
           int firstLeftBracketPos=dname.indexOf('[');
           int firstRightBracketPos=dname.indexOf(']');

           arrayList.setSelectedIndex(searchArray(DispNames,dname.substring(0,firstLeftBracketPos)));
           arrayIndexListR.setSelectedIndex(searchArray(indexDisplayValuesR,dname.substring(firstLeftBracketPos+1,firstRightBracketPos)));

           int selected=arrayList.getSelectedIndex();
           if (selected>-1)
           {
               dim1=Dim1[selected];
               dim2=Dim2[selected];
                if (dim2!=null && Integer.valueOf(dim2)!=1)
                { int lastLeftBracketPos=dname.lastIndexOf('[');
                  int lastRightBracketPos=dname.lastIndexOf(']');
                  arrayIndexListC.setSelectedIndex(searchArray(indexDisplayValuesR,dname.substring(lastLeftBracketPos+1,lastRightBracketPos)));
                }
           }
          jLabel5.setText(dname.substring(0,firstLeftBracketPos));
          jLabel17.setText(dname.substring(firstLeftBracketPos,dname.length()));

            returnDisplayStatement=dname;
            returnObjectStatement=oname;
            dArrayType=dtype;
            oArrayType=otype;
          }///update


        }
    }

private int searchArray(String [] array, String target)
{
for (int i=0;i<array.length;i++)
    if (array[i].equalsIgnoreCase(target))
        return i;

return -1;
}


  public void columnComponentsSetVisisble(boolean tf)
  {
   jPanel8.setVisible(tf);
   jScrollPane3.setVisible(tf);
   arrayIndexListC.setVisible(tf);
  }

  public void createIndexComponentsSetVisisble(boolean tf)
  {
   CreateIndexBut.setVisible(tf);
  }

   public  String getMessage()
   {return "\""+this.messageTextField.getText()+"\"";}

   public  String getReturnDisplaeStateMent()
   {return this.returnDisplayStatement;}

   public  String getReturnObjectStatement()
   {return this.returnObjectStatement;}

   public String getDArrayName()
    {return this.dArrayName;}

   public String getOArrayName()
    {return this.oArrayName;}

   public String getDArrayType()
    {return this.dArrayType;}

    public String getOArrayType()
    {return this.oArrayType;}

    public String getDIndexNameR()
    {return this.dIndexNameR;}

   public String getOIndexNameR()
    {return this.oIndexNameR;}

    public String getDIndexNameC()
    {return this.dIndexNameC;}

   public String getOIndexNameC()
    {return this.oIndexNameC;}


// get array indices with addWriteExpressionPanel
   private void getCreatedIndices ()
   { String temp="";
     String label17Text=jLabel17.getText();

     if (label17Text.equals("[NONE]")||label17Text.equals("Select Row Index") )
     {}
     else if (label17Text.equals("Select Column Index"))
       temp=dIndexNameR;
     else
     {
      int lBR1=label17Text.indexOf('[');
      int rBR1=label17Text.indexOf(']');
      temp=label17Text.substring(lBR1+1,rBR1);
     }


     addCommandExpressionPanel cAI= new addCommandExpressionPanel(this.objScope,temp,this.commandName);
     cAI.getHeaders()[0].setText("Create an integer expression for the row index of array ");
     cAI.getHeaders()[1].setText(dArrayName);
     cAI.getHeaders()[2].setText(" e.g.  ARRAY_Α1");
     cAI.getHeaders()[3].setText("[i+1]");
     cAI.getTextFiledLabel().setText("Row Index");
     cAI.showBracketLabels(true);

       NotifyDescriptor d =new NotifyDescriptor.Confirmation(cAI,"Create an integer expression for the row index",
       NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);

       if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
       {
          if( cAI.checkValidExpression( NotifyDescriptor.OK_CANCEL_OPTION))
             {

             if (Integer.valueOf(dim2)>1)
             {
                if (label17Text.equals("[NONE]")||label17Text.equals("Select Row Index") ||
                       label17Text.equals("Select Column Index"))
                 temp=dIndexNameC;
                 else
                 {
                  int lBR2=label17Text.lastIndexOf('[');
                  int rBR2=label17Text.lastIndexOf(']');
                  temp=label17Text.substring(lBR2+1,rBR2);
                 }




             addCommandExpressionPanel cAIc= new addCommandExpressionPanel(this.objScope,temp,this.commandName);
             cAIc.getHeaders()[0].setText("Create an integer expression for the colum index of array ");
             cAIc.getHeaders()[1].setText(dArrayName);
             cAIc.getHeaders()[2].setText(" e.g.  ARRAY_Α1 [i+1]");
             cAIc.getHeaders()[3].setText("[j+2]");
             cAIc.getTextFiledLabel().setText("Column Index");
             cAIc.showBracketLabels(true);

             NotifyDescriptor dc =new NotifyDescriptor.Confirmation(cAIc,"Create an integer expression for the column index",
             NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);
              if ( DialogDisplayer.getDefault().notify(dc)==NotifyDescriptor.OK_OPTION)
              {
               if( cAIc.checkValidExpression( NotifyDescriptor.OK_CANCEL_OPTION))
               {
                jLabel17.setText("["+cAI.getDisplayExpression()+"]["+cAIc.getDisplayExpression()+"]");
                returnDisplayStatement=jLabel5.getText()+jLabel17.getText();
                returnObjectStatement=oArrayName+"[(int) "+cAI.getObjectExpresssion()
                        +"][(int) "+cAIc.getObjectExpresssion()+"]";
               }
              }
             }
             else
             {
               jLabel17.setText("["+cAI.getDisplayExpression()+"]");
               returnDisplayStatement=jLabel5.getText()+jLabel17.getText();
               returnObjectStatement=oArrayName+cAI.getObjectExpresssion();
             }
            }
        }
   }



// get array indices with createArrayIndexPanel

//   private void getCreatedIndices ()
//   { createArrayIndexPanel cAI= new createArrayIndexPanel(DispNames,dArrayName,Integer.valueOf(dim2),jLabel17.getText(),this.objScope);
//
//       NotifyDescriptor d =new NotifyDescriptor.Confirmation(cAI,cAI.getName(),
//       NotifyDescriptor.OK_CANCEL_OPTION,NotifyDescriptor.PLAIN_MESSAGE);
//
//       if ( DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.OK_OPTION)
//       {
//          if( cAI.checkInputIfValid())
//             {
//
//              jLabel17.setText("["+cAI.getReturnIndices()[0]+"]");
//
//              if (Integer.valueOf(dim2)>1)
//                    jLabel17.setText("["+cAI.getReturnIndices()[0]+"]["+cAI.getReturnIndices()[1]+"]");
//
//              returnDisplayStatement=jLabel5.getText()+jLabel17.getText();
//
//              returnObjectStatement=oArrayName+cAI.getObjectStatement();
//             }
//        }
//   }
 

      /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        arrayList = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        messageTextField = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        arrayIndexListR = new javax.swing.JList();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        arrayIndexListC = new javax.swing.JList();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        CreateIndexBut = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jLabel7.setText(org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jLabel7.text")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        arrayList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "There are no ARRAYS of local or global scope" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(arrayList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jPanel3.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText(org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jLabel5.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jPanel5.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel21.setText(org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jLabel21.text")); // NOI18N

        messageTextField.setText(org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.messageTextField.text")); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel22.setText(org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jLabel22.text")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel21)
                .addComponent(messageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel22))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jPanel7.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        arrayIndexListR.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "No Array is selected" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(arrayIndexListR);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jPanel8.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        arrayIndexListC.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "No Array is selected" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(arrayIndexListC);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jPanel6.TabConstraints.tabTitle"), jPanel6); // NOI18N

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jPanel10.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        CreateIndexBut.setText(org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.CreateIndexBut.text")); // NOI18N
        CreateIndexBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateIndexButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(CreateIndexBut, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addGap(77, 77, 77))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(CreateIndexBut)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jPanel9.TabConstraints.tabTitle"), jPanel9); // NOI18N

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jPanel11.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText(org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jLabel17.text")); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, 0, 412, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText(org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jLabel1.text")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText(org.openide.util.NbBundle.getMessage(getReadArrayElementsListPanel.class, "getReadArrayElementsListPanel.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7))
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void CreateIndexButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateIndexButActionPerformed
     getCreatedIndices ();
    }//GEN-LAST:event_CreateIndexButActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CreateIndexBut;
    private javax.swing.JList arrayIndexListC;
    private javax.swing.JList arrayIndexListR;
    private javax.swing.JList arrayList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField messageTextField;
    // End of variables declaration//GEN-END:variables

}
