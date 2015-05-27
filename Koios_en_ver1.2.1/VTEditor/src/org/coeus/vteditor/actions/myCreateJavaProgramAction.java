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

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;

import org.coeus.vteditor.VTEditor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 *
 * @author Jrd
 */
public class myCreateJavaProgramAction extends AbstractAction implements LookupListener, ContextAwareAction {
    private Lookup context;
    Lookup.Result<VTEditor> result;
    private BufferedWriter bw =null;
   private static myCreateJavaProgramAction createJavaProgramAction=null;
    private  VTEditor cVTEditor=null;


    private File file=null;
    private String fileName=null;
    private writeJavaFile writeJFile=null;
 


    public myCreateJavaProgramAction() {
        this(Utilities.actionsGlobalContext());
        createJavaProgramAction=this;
    }


 public myCreateJavaProgramAction(Lookup context) {
    init(context);
    putValue(NAME,"Export JAVA Program");
//    putValue(SMALL_ICON,new ImageIcon(
//    ImageUtilities.loadImage("org/coeus/vteditor/resources/save.png", true)));
//    putValue(LARGE_ICON_KEY,new ImageIcon(
//    ImageUtilities.loadImage("org/coeus/vteditor/resources/folder.png", true)));
  }


    private void init(Lookup context) {
    this.context = context;
    result = context.lookupResult(contextClass());
    result.addLookupListener(this);
    resultChanged(null);
    }


    public Class<VTEditor> contextClass() {
    return VTEditor.class;
    }

    public static myCreateJavaProgramAction getCreateJavaProgramAction()
    {return createJavaProgramAction;}


    public void actionPerformed(ActionEvent e) {
     
      greekFileChooser gfc= greekFileChooser.myGreekFileChooser(greekFileChooser.JAVA_EXT);
      int gFCOprtion=gfc.showSaveDialog(gfc);

      if(gFCOprtion == greekFileChooser.CANCEL_OPTION) return;


      File temp=new File(gfc.getSelectedFile().getPath()+"."+greekFileChooser.JAVA_EXT);

      if (gfc.getSelectedFile().exists() || temp.exists() )
      {
      NotifyDescriptor d = new NotifyDescriptor.Confirmation("JAVA file "+gfc.getSelectedFile().getName()+" already exists!\n" +
              "If you save the file with the current name, the data contained in the existing file will be lost.\n" +
              "Do you want to continue?",
              "Confirm File Replace",NotifyDescriptor.YES_NO_OPTION, NotifyDescriptor.QUESTION_MESSAGE);
      if (DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.NO_OPTION)
          return;
      }

      fileName=greekFileChooser.getFileNameWithoutExt(gfc.getSelectedFile(),greekFileChooser.JAVA_EXT);
     
      while (!greekFileChooser.checkFileName (fileName))
      {
       gFCOprtion=gfc.showSaveDialog(gfc);
       if(gFCOprtion == greekFileChooser.CANCEL_OPTION) return;
       fileName=greekFileChooser.getFileNameWithoutExt(gfc.getSelectedFile(),greekFileChooser.KOS_EXT);
      }


      file =new File (greekFileChooser.getPathNameWithoutExt(gfc.getSelectedFile(),greekFileChooser.JAVA_EXT)+"."+greekFileChooser.JAVA_EXT);

       writeJFile=new writeJavaFile(cVTEditor.getRootNode(),file,fileName,true);

              //create .Java
            writeJFile.start();
 
     
        new checkActionsState();

    }

    public void resultChanged(LookupEvent ev) {
       checkCreateJavaProgramAction();
       }

    public Action createContextAwareInstance(Lookup actionContext) {
       return new myCreateJavaProgramAction(context);
    }


     public void checkCreateJavaProgramAction()
  {
    if (result.allClasses().size() != 0)
    {
     cVTEditor =  result.allInstances().iterator().next();
     setEnabled(true);
    }
    else setEnabled(false);

  }

}
