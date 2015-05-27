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



package org.coeus.vteditor.actions;

import javax.swing.JFileChooser;

import java.io.File;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd 
 * This code was based on code by Maik Wuensche found in
 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4423439
 *
 *
 **/



public class greekFileChooser extends JFileChooser {
    public static final String KOS_EXT="kos";
    public static final String JAVA_EXT="java";


  // constructor, only the super class, not called directly
  public greekFileChooser (String fileType) {
    super();
    this.setMultiSelectionEnabled(false);
    this.addChoosableFileFilter(new kosFilter(fileType));
    this.setFileView(new kosFileView(fileType));
  }

  // get an instance of the file chooser
  public static greekFileChooser myGreekFileChooser(String fileType) {

//    UIManager.put("FileChooser.lookInLabelText","Εύρεση στο:");
//    UIManager.put("FileChooser.fileNameLabelText","Όνομα Αρχείου:");
//    UIManager.put("FileChooser.filesOfTypeLabelText","Τύπος Αρχείου:");
//    UIManager.put("FileChooser.upFolderToolTipText","Ένα επίπεδο πάνω");
//    UIManager.put("FileChooser.upFolderAccessibleName","Πάνω");
//    UIManager.put("FileChooser.homeFolderToolTipText","Home");
//    UIManager.put("FileChooser.homeFolderAccessibleName","Home");
//    UIManager.put("FileChooser.newFolderToolTipText","Δημιουργία Νέου Φακέλου");
//    UIManager.put("FileChooser.newFolderAccessibleName","Νέος Φάκελος");
//    UIManager.put("FileChooser.listViewButtonToolTipText","Λίστα");
//    UIManager.put("FileChooser.listViewButtonAccessibleName","Λίστα");
//    UIManager.put("FileChooser.detailsViewButtonToolTipText","Λεπτομέριες");
//    UIManager.put("FileChooser.detailsViewButtonAccessibleName","Λεπτομέριες");
//    UIManager.put("FileChooser.cancelButtonText","Ακύρωση");
//    UIManager.put("FileChooser.cancelButtonToolTipText","Ακύρωση της Επιλογής");
//    UIManager.put("FileChooser.acceptAllFileFilterText","Όλα τα Αρχεία (*.*)");
    return new greekFileChooser(fileType);
  }


//  // show the file open dialog
//    @Override
//  public int showOpenDialog(Component parent) {
//    setDialogTitle("Άνοιγμα");
//    setApproveButtonToolTipText("Άνοιγμα Επιλεγμένου Αρχείου");
//    setDialogType(OPEN_DIALOG);
//
//    return super.showDialog(parent,("Άνοιγμα"));
//  }
//
//  // show the file save dialog
//    @Override
//  public int showSaveDialog(Component parent) {
//    setDialogTitle("Αποθήκευση ως...");
//    setApproveButtonToolTipText("Αποθήκευση Επιλεγμένου Αρχείου");
//    setDialogType(SAVE_DIALOG);
//
//    return super.showDialog(parent,"Αποθήκευση");
//  }

   public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }


    public static String getFileNameWithoutExt(File f,String extens)
   {
       String fileName=f.getName();
       String ext=greekFileChooser.getExtension(f);

      if (ext!=null && ext.equals(extens))
      {
        int lastDotIndex=fileName.lastIndexOf(".");
        fileName=fileName.substring(0,lastDotIndex);
      }

       return fileName;
   }

   public static String getPathNameWithoutExt(File f,String extens)
   {
       String pathName=f.getPath();
       String ext=greekFileChooser.getExtension(f);

      if (ext!=null && ext.equals(extens))
      {
        int lastDotIndex=pathName.lastIndexOf(".");
        pathName=pathName.substring(0,lastDotIndex);
      }
       return pathName;
   }


   public static boolean checkFileName(String conname)
   {
    boolean nameOK=true;
    for (int q = 0; q < conname.length(); q++) {


          if ((q == 0) && (!(conname.charAt(q) >= 65 && conname.charAt(q) <= 90) && //A-Z
                    !(conname.charAt(q) >= 97 && conname.charAt(q) <= 122) && //a-z
                    !(conname.charAt(q) >= 913 && conname.charAt(q) <= 937) && //Α-Ω
                    !(conname.charAt(q) >= 945 && conname.charAt(q) <= 969) &&//α-ω
                    !(conname.charAt(q) == 36) &&//$
                    !(conname.charAt(q) == 95)))//_
            {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("The file was not saved, because you have entered the name:"+conname+
                  ".\nThe name of a file must begin with a letter, or a dollar sign($) or an underscore(_)!"+
                        "\n" + "Enter a valid filename or click Cancel.",NotifyDescriptor.ERROR_MESSAGE));
                nameOK=false;

            }
            else if (!(conname.charAt(q) >= 65 && conname.charAt(q) <= 90) && //A-Z
                    !(conname.charAt(q) >= 97 && conname.charAt(q) <= 122) && //a-z
                    !(conname.charAt(q) >= 913 && conname.charAt(q) <= 937) && //Α-Ω
                    !(conname.charAt(q) >= 945 && conname.charAt(q) <= 969) &&//α-ω
                    !(conname.charAt(q) >= 48 && conname.charAt(q) <= 57) && //0-9
                    !(conname.charAt(q) == 36) &&//$
                    !(conname.charAt(q) == 95))//_
            { DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
             "The file was not saved, because you have entered the name:"+conname+
             "\nThe name of a file must contain one or more characters. Each character\n" +
             "can be a letter or a number or a dollar sign($)and or an underscore(_)"+
                        "\n" + "Enter a valid filename or click Cancel.",NotifyDescriptor.ERROR_MESSAGE));
              nameOK=false;
            }

      }
    return nameOK;
   }



}






