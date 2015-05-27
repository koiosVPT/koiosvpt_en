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


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.coeus.wizards._HelpFuntions.getFunctionsList;

public final class FuncCallVisualPanel1 extends JPanel {

    private String [] DispNames=null;
    private String [] ObjNames=null;
    private String [] DispTypes=null;
    private String [] ObjTypes=null;
    private String [] listObjects=null;

    private String dName=null;
    private String oName=null;
    private String dType=null;
    private String oType=null;

    private getFunctionsList gFL=null;


    private LinkedList<String> dispParams=null;
    private LinkedList<String> objParams=null;
    private LinkedList<String> dispParamsTypes=null;
    private LinkedList<String> objParamsTypes=null;


    /** Creates new form CallVisualPanel1 */
    public FuncCallVisualPanel1(getFunctionsList igFL) {
        initComponents();

        this.gFL=igFL;

        dispParams=new LinkedList<String>();
        objParams=new LinkedList<String>();
        dispParamsTypes=new LinkedList<String>();
        objParamsTypes=new LinkedList<String>();



         String [][] strings = gFL.getStringArraystWithFunctions();
         DispNames=strings[0];
         ObjNames=strings[1];
         DispTypes=strings[2];
         ObjTypes=strings[3];


           listObjects=new String [DispNames.length];
         for (int lop=0;lop<listObjects.length;lop++)
             listObjects[lop]=DispTypes[lop]+" "+DispNames[lop];//+" ("+DispTypes[lop]+")";

        if (DispNames!=null)
        {
           this.jComboBox1.removeAllItems();
           for (String s:listObjects)
              this.jComboBox1.addItem(s);
           this.jComboBox1.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {

                    int selection=jComboBox1.getSelectedIndex();

                    dName=DispNames[selection];
                    oName=ObjNames[selection];
                    dType=DispTypes[selection];
                    oType=ObjTypes[selection];

                    dispParams=gFL.getDispParams().get(selection);
                    objParams=gFL.getObjParams().get(selection);
                    dispParamsTypes=gFL.getDispParamsTypes().get(selection);
                    objParamsTypes=gFL.getObjParamsType().get(selection);
                    
                }
            });

        dName=DispNames[0];
        oName=ObjNames[0];
        dType=DispTypes[0];
        oType=ObjTypes[0];

        dispParams=gFL.getDispParams().get(0);
        objParams=gFL.getObjParams().get(0);
        dispParamsTypes=gFL.getDispParamsTypes().get(0);
        objParamsTypes=gFL.getObjParamsType().get(0);
        }
    }



 private int searchArray(String [] array, String target)
{
for (int i=0;i<array.length;i++)
    if (array[i].equalsIgnoreCase(target))
        return i;

return -1;
}


    @Override
    public String getName() {
        return "Function Selection";
    }

     public JComboBox getProceduresComboBox()
     {return this.jComboBox1;}



   public String getDName()
    {return this.dName;}

   public String getOName()
    {return this.oName;}

   public String getDType()
    {return this.dType;}

    public String getOType()
    {return this.oType;}


    public LinkedList<String> getDispParams()
    {return this.dispParams;}

    public LinkedList<String> getObjParams()
    {return this.objParams;}

    public LinkedList<String> getDispParamsTypes()
    {return this.dispParamsTypes;}

    public LinkedList<String> getObjParamsTypes()
    {return this.objParamsTypes;}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(FuncCallVisualPanel1.class, "FuncCallVisualPanel1.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

