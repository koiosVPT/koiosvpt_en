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
 * executionDialog.java
 *
 * Created on 8 Οκτ 2009, 2:33:01 πμ
 */

package org.coeus.vteditor.execenv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.event.ChangeEvent;
import org.coeus.vteditor.actions.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;
import org.coeus.vteditor.CreateCoeusProgram;
import org.coeus.vteditor.VisibleCaretListener;
import org.coeus.vteditor.btvnodes.RootNode;
import org.openide.util.ImageUtilities;



/**
 *
 * @author Jrd
 */
public class executionDialog extends JDialog  {
private static executionDialog execDialog=null;
private RootNode rootNode=null;
private JTextPane codeTextPane=null;
private String fileName=null;
private mySimpleTrace traceJavaProgram=null;
private boolean paused=false;
private myHashtable<String,String> coeus2Java=null,coeus2description=null;
private myHashtable<String,Integer> commandLocator=null,javaCommandLocator=null,
        javaCommandLineStart=null,javaCommandLineEnd=null;
private Hashtable<Integer,Integer> commandBeiginEndPairs=null;
private long speedFactor=75;
private myCodeHighlighter codeHighlighter=null;
private JTextArea linesTextArea=null;



    /** Creates new form executionDialog */
    public executionDialog(Frame parent, boolean modal,RootNode vteRootNode,String filename) {
        super(parent,"", Dialog.ModalityType.MODELESS );
        initComponents();
        stopButton.setIcon((Icon) ImageUtilities.loadImage("org/coeus/vteditor/resources/myStopBtn.png", true));
        pauseButton.setIcon((Icon) ImageUtilities.loadImage("org/coeus/vteditor/resources/myPauseBtn.png", true));
        runButton.setIcon((Icon) ImageUtilities.loadImage("org/coeus/vteditor/resources/myRunProject24.png", true));

         this.fileName=filename;
        rootNode=vteRootNode;

        //////Initialize linesTextArea
       linesTextArea=new JTextArea();
       linesTextArea.setFont(new Font(Font.MONOSPACED,Font.BOLD,12));
       linesTextArea.setSize(20, this.jScrollPane1.getWidth());
       linesTextArea.setBackground(UIManager.getColor("Label.background"));
       linesTextArea.setBorder(BorderFactory.createEmptyBorder());
       //this.jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
       linesTextArea.setLineWrap(true);
       linesTextArea.setWrapStyleWord(true);
       linesTextArea.setEditable(false);
       codePanel.add(linesTextArea,BorderLayout.WEST);

        //Initialize codeTextPane
        codeTextPane = new JTextPane();
        codeTextPane.addCaretListener(new VisibleCaretListener());
        new CreateCoeusProgram(rootNode,codeTextPane,linesTextArea);
        codeHighlighter= new myCodeHighlighter(rootNode,codeTextPane);
        codeTextPane.setHighlighter(codeHighlighter);
        codePanel.add(codeTextPane);
 


        ///Initialize command TextArea
       commandTextArea.setBackground(UIManager.getColor("Label.background"));
       commandTextArea.setBorder(BorderFactory.createEmptyBorder());
       this.jScrollPane4.setBorder(BorderFactory.createEmptyBorder());
       commandTextArea.setLineWrap(true);
       commandTextArea.setWrapStyleWord(true);
       commandTextArea.setEditable(false);


        ///Initialize commentTextPane
        commentTextArea.setBackground(UIManager.getColor("Label.background"));
        commentTextArea.setBorder(BorderFactory.createEmptyBorder());
        this.jScrollPane3.setBorder(BorderFactory.createEmptyBorder());
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);
        commentTextArea.setEditable(false);

        ///Initialize status TextArea
       statusTextArea.setBackground(UIManager.getColor("Label.background"));
       statusTextArea.setBorder(BorderFactory.createEmptyBorder());
       this.jScrollPane5.setBorder(BorderFactory.createEmptyBorder());
       statusTextArea.setLineWrap(true);
       statusTextArea.setWrapStyleWord(true);
       statusTextArea.setEditable(false);
      


        /////Initialize speedSlider
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setPaintTicks(true);
//        speedSlider.setPaintLabels(true);
//        Hashtable labelTable = new Hashtable();
//        JLabel slowLabel =new JLabel("Αργή");
//        slowLabel.setFont(new Font("Tahoma",Font.PLAIN,10));
//        JLabel fastLabel= new JLabel("Γρήγορη");
//        fastLabel.setFont(new Font("Tahoma",Font.PLAIN,10));
//        labelTable.put( new Integer( 100 ),slowLabel  );
//        labelTable.put( new Integer( 0), fastLabel );
//        speedSlider.setLabelTable( labelTable );
        speedSlider.setValue(70);
    
        

        coeus2Java=new myHashtable<String,String>();
        coeus2description=new myHashtable<String,String>();
        commandLocator=new myHashtable<String,Integer>();
        javaCommandLocator=new myHashtable<String,Integer>();
        javaCommandLineStart=new myHashtable<String,Integer>();
        javaCommandLineEnd=new myHashtable<String,Integer>();
        javaCommandLineStart.put("PROGRAM NAME",-1);
        javaCommandLineEnd.put("PROGRAM NAME",-1);
        javaCommandLocator.put("PROGRAM NAME",-1);
        commandBeiginEndPairs=new Hashtable<Integer,Integer>();
        new createCoeusJavaTable(rootNode,filename,coeus2Java,coeus2description,commandLocator,javaCommandLocator,
                        javaCommandLineStart,javaCommandLineEnd,commandBeiginEndPairs);


//       for (int i:coeus2Java.keySet())
//            System.out.println("\n\n"+i+" " + coeus2Java.getK(i)+ "      "+coeus2Java.getV(i)+"\n\n");

//        for (int i:coeus2description.keySet())
//            System.out.println("\n\n"+i+" " + coeus2description.getK(i)+ "      "+coeus2description.getV(i)+"\n\n");


        for (int i:javaCommandLineStart.keySet())
            System.out.println("\n\n"+i+" " + javaCommandLineStart.getK(i)+ "      "+javaCommandLineStart.getV(i)+"  "+
            javaCommandLineEnd.getV(i)+"\n\n");
 
     
        for (int i:javaCommandLocator.keySet())
            System.out.println("\n\n"+i+" " + javaCommandLocator.getK(i)+ "      "+javaCommandLocator.getV(i)+"\n\n");

        //        System.out.println("\n\n\n\n\n");
//        for (String s:coeus2Node.keySet())
//            System.out.println("\n\n" + s+ "      "+coeus2Node.get(s)+"\n\n");

        
        showLineNumbersCheckBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (showLineNumbersCheckBox.isSelected())
                     myShowLineNumbersAction.getShowLinesAction().getCheckBox().setState(true);
                else
                     myShowLineNumbersAction.getShowLinesAction().getCheckBox().setState(false);

                new CreateCoeusProgram(rootNode,codeTextPane,linesTextArea);
            }
        });
       showLineNumbersCheckBox.setSelected(myShowLineNumbersAction.getShowLinesAction().getState());

     runButton.setEnabled(true);
     pauseButton.setEnabled(false);
     stopButton.setEnabled(false);
   
      execDialog=this;
    }


    private void setExecution()
    {

//    this.codeHighlighter.addHighlight("ΠΡΟΓΡΑΜΜΑ");
    commandTextArea.setText("PROGRAM "+ rootNode.getDisplayName());
    commentTextArea.setText("is the first command of the program!");

//    this.traceJavaProgram=new mySimpleTrace(new String[]{"-cp  .;myjoptionpane.jar "+fileName},
//this.traceJavaProgram=new mySimpleTrace(new String[]{"-Dfile.encoding=UTF-8 -cp  .; "+fileName},
this.traceJavaProgram=new mySimpleTrace(new String[]{"-Dfile.encoding=UTF-8 -cp  . "+fileName},
            this.coeus2Java,this.coeus2description,this.codeHighlighter,
            this.javaCommandLocator,this.javaCommandLineStart,this.javaCommandLineEnd,this.commandBeiginEndPairs);

    this.traceJavaProgram.getWatcher().setSpeed((100 - speedSlider.getValue())*speedFactor);
    this.traceJavaProgram.getOutRedirect().setSpeed((100 - speedSlider.getValue())*speedFactor);


    this.speedSlider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
    if (!source.getValueIsAdjusting()) {
        int fps = (int)source.getValue();
        traceJavaProgram.getWatcher().setSpeed((100 - fps)*speedFactor);
        traceJavaProgram.getOutRedirect().setSpeed((100 - speedSlider.getValue())*speedFactor);
    }

            }
        });

    }



   public static void setComment(String s)
   {
   //commentTextArea.setText(commentTextArea.getText()+s+"\n");
   commentTextArea.setText(s+"\n");
   }

   public static executionDialog getExecutionDialog()
   {return execDialog;}

    public JTextPane getOutputTextPane() {
        return outputTextPane;
    }

    public static JTextArea getCommentTextArea() {
            return commentTextArea;
        }


    public static void setCommandName(String s)
    {   commandTextArea.setText("");
        commandTextArea.setText(s);
    }

     public static JTextArea getStatusTextArea() {
            return statusTextArea;
        }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textCodePanel = new javax.swing.JPanel();
        showLineNumbersCheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        codePanel = new javax.swing.JPanel();
        buttonsPanel = new javax.swing.JPanel();
        runButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        speedSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        commentTextArea = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        statusTextArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        commandTextArea = new javax.swing.JTextArea();
        outputWindow = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        outputTextPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.title")); // NOI18N
        setIconImages(null);
        setModal(true);

        textCodePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.textCodePanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(102, 102, 102))); // NOI18N

        showLineNumbersCheckBox.setText(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.showLineNumbersCheckBox.text")); // NOI18N
        showLineNumbersCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showLineNumbersCheckBoxActionPerformed(evt);
            }
        });

        codePanel.setLayout(new java.awt.BorderLayout());
        jScrollPane1.setViewportView(codePanel);

        javax.swing.GroupLayout textCodePanelLayout = new javax.swing.GroupLayout(textCodePanel);
        textCodePanel.setLayout(textCodePanelLayout);
        textCodePanelLayout.setHorizontalGroup(
            textCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textCodePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showLineNumbersCheckBox)
                .addContainerGap(345, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );
        textCodePanelLayout.setVerticalGroup(
            textCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, textCodePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addGap(9, 9, 9)
                .addComponent(showLineNumbersCheckBox))
        );

        buttonsPanel.setBorder(new javax.swing.border.MatteBorder(null));

        runButton.setText(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.runButton.text")); // NOI18N
        runButton.setPreferredSize(new java.awt.Dimension(80, 25));
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        pauseButton.setText(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.pauseButton.text")); // NOI18N
        pauseButton.setPreferredSize(new java.awt.Dimension(80, 25));
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        stopButton.setText(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.stopButton.text")); // NOI18N
        stopButton.setPreferredSize(new java.awt.Dimension(80, 25));
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.jLabel3.text")); // NOI18N

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.jLabel4.text")); // NOI18N

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.jLabel5.text")); // NOI18N

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.jLabel6.text")); // NOI18N

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(runButton, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pauseButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(stopButton, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buttonsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                        .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                .addContainerGap())
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buttonsPanelLayout.createSequentialGroup()
                        .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(runButton, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(pauseButton, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(43, 43, 43))
                    .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(speedSlider, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.jPanel1.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(102, 102, 102))); // NOI18N

        commentTextArea.setColumns(20);
        commentTextArea.setFont(new java.awt.Font("Bookman Old Style", 0, 12));
        commentTextArea.setRows(5);
        jScrollPane3.setViewportView(commentTextArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
        );

        statusTextArea.setColumns(20);
        statusTextArea.setFont(new java.awt.Font("Monospaced", 1, 11));
        statusTextArea.setForeground(new java.awt.Color(0, 51, 51));
        statusTextArea.setRows(5);
        statusTextArea.setText(org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.statusTextArea.text")); // NOI18N
        jScrollPane5.setViewportView(statusTextArea);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(102, 102, 102))); // NOI18N

        commandTextArea.setColumns(20);
        commandTextArea.setFont(new java.awt.Font("Monospaced", 1, 12));
        commandTextArea.setRows(5);
        jScrollPane4.setViewportView(commandTextArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        outputWindow.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(executionDialog.class, "executionDialog.outputWindow.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(51, 51, 51))); // NOI18N

        outputTextPane.setBackground(new java.awt.Color(51, 51, 51));
        outputTextPane.setForeground(new java.awt.Color(204, 204, 255));
        jScrollPane2.setViewportView(outputTextPane);

        javax.swing.GroupLayout outputWindowLayout = new javax.swing.GroupLayout(outputWindow);
        outputWindow.setLayout(outputWindowLayout);
        outputWindowLayout.setHorizontalGroup(
            outputWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        outputWindowLayout.setVerticalGroup(
            outputWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputWindowLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textCodePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(outputWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outputWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textCodePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showLineNumbersCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showLineNumbersCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showLineNumbersCheckBoxActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
  

     if (!paused)
     {
     setExecution();
     this.outputTextPane.setText("");
     this.traceJavaProgram.startTrace();
     }
     else
       this.traceJavaProgram.resumeTrace();

     runButton.setEnabled(false);
     pauseButton.setEnabled(true);
     stopButton.setEnabled(true);

     paused=false;

     //statusTextArea.setForeground(new Color (0,255,51));
     statusTextArea.setForeground(new Color (0,204,0));
     statusTextArea.setText("The program is running....."
              +"\n\nTo pause the execution of the program, click the \"Pause\" button"
              +"\nTo stop the execution of the program, click the \"Stop\" button");


    }//GEN-LAST:event_runButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
    if (this.traceJavaProgram!=null &&
          this.traceJavaProgram.getWatcher().isAlive())
    {
     this.traceJavaProgram.killThreads();

     runButton.setEnabled(true);
     pauseButton.setEnabled(false);
     stopButton.setEnabled(false);

     paused=false;
     this.outputTextPane.setText("");
     commandTextArea.setText("");
     commentTextArea.setText("The execution of program "+rootNode.getDisplayName()+".kos was interrupted by user!");
     statusTextArea.setForeground(Color.RED);
     statusTextArea.setText("Program execution was interrupted by user."+
             "\nTo restart the execution of the program, click the \"Start\" button");
    }
    }//GEN-LAST:event_stopButtonActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
     if (this.traceJavaProgram!=null &&
       this.traceJavaProgram.getWatcher().isAlive())
      {if (paused)
       {
       this.traceJavaProgram.resumeTrace();

       runButton.setEnabled(false);
       pauseButton.setEnabled(true);
       stopButton.setEnabled(true);

       paused=false;
       statusTextArea.setForeground(Color.green);
       statusTextArea.setText("The program is running....."
              +"\n\nTo pause the execution of the program, click the \"Pause\" button"
              +"\nTo stop the execution of the program, click the \"Stop\" button");
            }
       else
       {
       this.traceJavaProgram.pauseTrace();

       runButton.setEnabled(true);
       pauseButton.setEnabled(true);
       stopButton.setEnabled(true);

       paused=true;
       statusTextArea.setForeground(Color.orange);// Color(153,153,0));
       statusTextArea.setText("The execution of the program is paused...."
                +"\n\nTo continuew, click the \"Pauese\" or the \"Start\" button"
                +"\nTo stop the execution of the program, click the \"Stop\" button");

       }
     }
    }//GEN-LAST:event_pauseButtonActionPerformed

//    /**
//    * @param args the command line arguments
//    */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                executionDialog dialog = new executionDialog(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JPanel codePanel;
    private static javax.swing.JTextArea commandTextArea;
    private static javax.swing.JTextArea commentTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextPane outputTextPane;
    private javax.swing.JPanel outputWindow;
    public static javax.swing.JButton pauseButton;
    public static javax.swing.JButton runButton;
    private javax.swing.JCheckBox showLineNumbersCheckBox;
    private javax.swing.JSlider speedSlider;
    private static javax.swing.JTextArea statusTextArea;
    public static javax.swing.JButton stopButton;
    private javax.swing.JPanel textCodePanel;
    // End of variables declaration//GEN-END:variables

    

   
}
