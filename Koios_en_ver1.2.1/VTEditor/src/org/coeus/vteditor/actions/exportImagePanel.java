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
 * exportImagePanel.java
 *
 * Created on 2 Νοε 2009, 6:52:14 μμ
 */

package org.coeus.vteditor.actions;

import java.awt.AlphaComposite;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.coeus.vteditor.CreateCoeusProgram;
import org.coeus.vteditor.VTEditor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 * based on code found on http://www.javalobby.org/articles/ultimate-image/#7,
 * http://java360.blogspot.com/2009/08/java-concatenate-multiple-images-into.html
 * http://java.sun.com/docs/books/tutorial/2d/images/saveimage.html
 */
public class exportImagePanel extends javax.swing.JPanel {

    private  Container visualContainer,textualContainer;
    private  JImagePanel vvp=null, tvp=null;
    private BufferedImage originalVisualImage=null,originalTextualImage=null,
            resizedVisualImage=null,resizedTextualImage=null,
            resizedTranslucentVisualImage,resizedTranslucentTextualImage;
    private VTEditor cVTEditor=null;
    private String ext="jpg";
    private String fileName;
    private File file;


    /** Creates new form exportImagePanel */
    public exportImagePanel(VTEditor vteditor) {
        initComponents();
        cVTEditor=vteditor; 
        new CreateCoeusProgram(cVTEditor.getRootNode(),cVTEditor.getTextPane(),cVTEditor.getLineArea());
       
        visualContainer = this.cVTEditor.getMyBeabTreeView().getTree();

       // this.cVTEditor.getVisualTextTabsPane().setSelectedIndex(1);
       // this.cVTEditor.getVisualTextTabsPane().setSelectedIndex(0);

        textualContainer=this.cVTEditor.getTextualViewPanel();
        originalVisualImage=getImage(visualContainer, "Graphical View - "+cVTEditor.getProgramState().getProgramName());
        originalTextualImage=getImage(textualContainer,"Textual View - "+cVTEditor.getProgramState().getProgramName());
        
        resizedVisualImage=resizeImage(originalVisualImage,300,455);
        resizedTextualImage=resizeImage(originalTextualImage,300,455);
        
         resizedTranslucentVisualImage= makeTranslucentImage (resizedVisualImage,(float) 0.5);
         resizedTranslucentTextualImage=makeTranslucentImage (resizedTextualImage,(float) 0.5);

        vvp=new JImagePanel(resizedTranslucentVisualImage,0,0);
        tvp=new JImagePanel(resizedTranslucentTextualImage,0,0);
        visualViewScrollPane.getViewport().removeAll();
        visualViewScrollPane.setViewportView(vvp);
        textualViewScrollPane.getViewport().removeAll();
        textualViewScrollPane.setViewportView(tvp);

        extensionComboBox.setEnabled(false);

        this.visualViewCheckBox.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
              if ( visualViewCheckBox.isSelected())
              {
                  vvp.loadNewImage(resizedVisualImage);
                  extensionComboBox.setEnabled(true);
              }
              if ( !visualViewCheckBox.isSelected())
              {
                  vvp.loadNewImage(resizedTranslucentVisualImage);
                  if (!textualViewCheckBox.isSelected())
                     extensionComboBox.setEnabled(false);
              }
            }
        });

        
        
        this.textualViewCheckBox.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
              if ( textualViewCheckBox.isSelected())
              {
                  tvp.loadNewImage(resizedTextualImage);
                  extensionComboBox.setEnabled(true);
              }
              if ( !textualViewCheckBox.isSelected())
              {
                  tvp.loadNewImage(resizedTranslucentTextualImage);
                  if (!visualViewCheckBox.isSelected())
                     extensionComboBox.setEnabled(false);
              }
            }
        });
       
    }


    private BufferedImage getImage(Container container,String title) {
    BufferedImage image1 = new BufferedImage(container.getWidth(),
            container.getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = image1.createGraphics();
    container.paint(g2);
    g2.dispose();
    
    BufferedImage image2=new BufferedImage(image1.getWidth(),
            image1.getHeight()+20,BufferedImage.TYPE_INT_RGB);


    g2=image2.createGraphics();
    g2.drawString(title, 15, 15);
    g2.drawLine(0,17, image1.getWidth(),17);
    g2.drawImage(image1,0, 20,null);
    g2.dispose();
    
    return image2;
}

    private BufferedImage resizeImage (BufferedImage img, int newW, int newH) {   
        int w = img.getWidth();   
        int h = img.getHeight();   
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());   
        Graphics2D g = dimg.createGraphics();   
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);   
        g.dispose();   
        return dimg;   
    }  


    private BufferedImage makeTranslucentImage (BufferedImage img, float transperancy) {
        // Create the image using the
        BufferedImage aimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TRANSLUCENT);
        // Get the images graphics
        Graphics2D g = aimg.createGraphics();
        // Set the Graphics composite to Alpha
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transperancy));
        // Draw the LOADED img into the prepared reciver image
        g.drawImage(img, null, 0, 0);
        // let go of all system resources in this Graphics
        g.dispose();
        // Return the image
        return aimg;
    }


    public void saveImges()
    {
      if(this.visualViewCheckBox.isSelected() || this.textualViewCheckBox.isSelected())
       saveImagesToFile();      
    }


    private void saveImagesToFile()
    {
     BufferedImage finalImage=null;

     int selection=this.extensionComboBox.getSelectedIndex();
     if (selection==0)
         ext="jpg";
     else if (selection==1)
         ext="gif";
     else if (selection==2)
         ext="png";

      greekFileChooser gfc= greekFileChooser.myGreekFileChooser(ext);
      int gFCOprtion=gfc.showSaveDialog(gfc);

      if(gFCOprtion == greekFileChooser.CANCEL_OPTION) return;


      File temp=new File(gfc.getSelectedFile().getPath()+"."+ext);

      if (gfc.getSelectedFile().exists() || temp.exists() )
      {
      NotifyDescriptor d = new NotifyDescriptor.Confirmation("The image file "+gfc.getSelectedFile().getName()+" already exists!\n" +
              "If you save the file with the current name, the data contained in the existing file will be lost.\n" +
              "Do you want to continue? ",
              "Confirm File Replace",NotifyDescriptor.YES_NO_OPTION, NotifyDescriptor.QUESTION_MESSAGE);
      if (DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.NO_OPTION)
          return;
      }

      fileName=greekFileChooser.getFileNameWithoutExt(gfc.getSelectedFile(),ext);
      file =new File (greekFileChooser.getPathNameWithoutExt(gfc.getSelectedFile(),ext)+"."+ext);


      if (this.visualViewCheckBox.isSelected())
          finalImage=this.originalVisualImage;
      if (this.textualViewCheckBox.isSelected())
          finalImage=this.originalTextualImage;
      if (this.visualViewCheckBox.isSelected() && this.textualViewCheckBox.isSelected())
          {
          int widthVisualImg = originalVisualImage.getWidth();
          int heightVisualImg = originalVisualImage.getHeight();
          int widthTextualImg = originalTextualImage.getWidth();
          int heightTextualImg = originalTextualImage.getHeight();
         
          BufferedImage temp1,temp2;
          int maxHeight;
          if (heightVisualImg>= heightTextualImg)
          {
          maxHeight=heightVisualImg;
          temp1=originalVisualImage;
          temp2=resizeImage(originalTextualImage,widthTextualImg, maxHeight);
          }
          else 
          {
          maxHeight=heightTextualImg;
          temp1=resizeImage(originalVisualImage,widthVisualImg, maxHeight);
          temp2=originalTextualImage;
          }
          
          
          finalImage = new BufferedImage ( widthVisualImg + widthTextualImg+1,
                  maxHeight,BufferedImage.TYPE_INT_RGB);

          Graphics2D g =finalImage.createGraphics();
          g.drawImage(temp1,0, 0,null);
          g.drawLine(widthVisualImg,0, widthVisualImg,20);
          g.drawImage(temp2,widthVisualImg+1, 0,null);
          g.dispose();
          }
        try {
            ImageIO.write(finalImage, ext, file);
        } catch (IOException ex) {
         NotifyDescriptor d = new NotifyDescriptor.Confirmation("An error occured while saving file "+file.getName(),
         NotifyDescriptor.DEFAULT_OPTION, NotifyDescriptor.ERROR_MESSAGE);
         DialogDisplayer.getDefault().notify(d);
        }


    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        visualViewCheckBox = new javax.swing.JCheckBox();
        textualViewCheckBox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        visualViewScrollPane = new javax.swing.JScrollPane();
        textualViewScrollPane = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        extensionComboBox = new javax.swing.JComboBox();

        visualViewCheckBox.setText(org.openide.util.NbBundle.getMessage(exportImagePanel.class, "exportImagePanel.visualViewCheckBox.text")); // NOI18N

        textualViewCheckBox.setText(org.openide.util.NbBundle.getMessage(exportImagePanel.class, "exportImagePanel.textualViewCheckBox.text")); // NOI18N
        textualViewCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textualViewCheckBoxActionPerformed(evt);
            }
        });

        jLabel1.setText(org.openide.util.NbBundle.getMessage(exportImagePanel.class, "exportImagePanel.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(exportImagePanel.class, "exportImagePanel.jLabel2.text")); // NOI18N

        extensionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "JPEG Image", "GIF Image", "PNG Image" }));
        extensionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extensionComboBoxActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(visualViewCheckBox)
                            .addComponent(visualViewScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textualViewCheckBox)
                            .addComponent(textualViewScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(extensionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(visualViewCheckBox)
                    .addComponent(textualViewCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(visualViewScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                    .addComponent(textualViewScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(extensionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textualViewCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textualViewCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textualViewCheckBoxActionPerformed

    private void extensionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_extensionComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_extensionComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox extensionComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JCheckBox textualViewCheckBox;
    private javax.swing.JScrollPane textualViewScrollPane;
    private javax.swing.JCheckBox visualViewCheckBox;
    private javax.swing.JScrollPane visualViewScrollPane;
    // End of variables declaration//GEN-END:variables

    public class JImagePanel extends JPanel{
	private BufferedImage image;
	int x, y;
	public JImagePanel(BufferedImage image, int x, int y) {
		super();
		this.image = image;
		this.x = x;
		this.y = y;
	}

        @Override
	protected void paintComponent(Graphics g) {
                super.paintComponent(g);
		g.drawImage(image, x, y, null);
	}

        public void loadNewImage(BufferedImage image)
        {
        this.image = image;
        repaint();
        }
}

}
