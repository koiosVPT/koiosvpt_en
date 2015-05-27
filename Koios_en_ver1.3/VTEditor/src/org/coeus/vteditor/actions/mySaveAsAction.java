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

import com.thoughtworks.xstream.XStream;
import org.coeus.vteditor.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;

import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.windows.TopComponent;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jrd
 */


public class mySaveAsAction extends AbstractAction implements LookupListener, ContextAwareAction {
    private Lookup context;
    Lookup.Result<VTEditor> result;
    private static mySaveAsAction saveAsAction=null;

    
    private File file=null;
    private String fileName=null;
    private VTEditor cVTEditor;

    private saveCoeusFile saveCFile=null;
    private XStream xstream;


    public mySaveAsAction() {
        this(Utilities.actionsGlobalContext());
        xstream = new XStream();
        xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
        xstream.alias("SaveLoad", saveLoadClass.class);

        saveAsAction=this;
    }


 public mySaveAsAction(Lookup context) {
    init(context);
    putValue(NAME,"Save As...");
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

     public static mySaveAsAction getSaveAsAction()
    {return saveAsAction;}


    @SuppressWarnings("static-access")
    public void actionPerformed(ActionEvent e) {

     

    greekFileChooser gfc= greekFileChooser.myGreekFileChooser(greekFileChooser.KOS_EXT);
    int gFCOprtion=gfc.showSaveDialog(gfc);
      
    if(gFCOprtion == greekFileChooser.CANCEL_OPTION) return;

      String inFileName=greekFileChooser.getFileNameWithoutExt(gfc.getSelectedFile(),greekFileChooser.KOS_EXT);

      if (alreadyOpen(inFileName))
      {
      NotifyDescriptor d = new NotifyDescriptor.Confirmation("Invalid file name for saving.\n" +
              "File  "+gfc.getSelectedFile().getName()+" is alreay open!",
      NotifyDescriptor.DEFAULT_OPTION, NotifyDescriptor.ERROR_MESSAGE);
      DialogDisplayer.getDefault().notify(d);
      return;
      }

      File temp=new File(gfc.getSelectedFile().getPath()+"."+greekFileChooser.KOS_EXT);

      if (gfc.getSelectedFile().exists() || temp.exists() )
      {
       NotifyDescriptor d = new NotifyDescriptor.Confirmation("File "+gfc.getSelectedFile().getName()+" already exists!\n" +
              "If you save the file with the current name, the data contained in the existing file will be lost.\n" +
              "Do you want to continue?",
              "Confirm File Replace",NotifyDescriptor.YES_NO_OPTION, NotifyDescriptor.QUESTION_MESSAGE);
          if (DialogDisplayer.getDefault().notify(d)==NotifyDescriptor.NO_OPTION)
          return;
      }


      fileName=gfc.getFileNameWithoutExt(gfc.getSelectedFile(),greekFileChooser.KOS_EXT).toUpperCase();
       while (!greekFileChooser.checkFileName (inFileName))
      {
       gFCOprtion=gfc.showSaveDialog(gfc);
       if(gFCOprtion == greekFileChooser.CANCEL_OPTION) return;
       inFileName=greekFileChooser.getFileNameWithoutExt(gfc.getSelectedFile(),greekFileChooser.KOS_EXT);
      }


      file =new File (gfc.getPathNameWithoutExt(gfc.getSelectedFile(),greekFileChooser.KOS_EXT)+"."+greekFileChooser.KOS_EXT);
      
      cVTEditor.setDisplayNameAfterSave(fileName);
      cVTEditor.setFileName(fileName);
      cVTEditor.setFile(file);

      saveCFile=new saveCoeusFile(cVTEditor,file,xstream);
      saveCFile.start();

         new checkActionsState();
    }

    public void resultChanged(LookupEvent ev) {
       if(result.allInstances().size() != 0)
       {
          cVTEditor =  result.allInstances().iterator().next();
          setEnabled(true);
       }
       else
           setEnabled(false);
       }

    public Action createContextAwareInstance(Lookup actionContext) {
       return new mySaveAsAction(context);
    }


 private boolean alreadyOpen(String inFileName) {

    Set<TopComponent> TCSet= TopComponent.getRegistry().getOpened();
    for (TopComponent TC :TCSet )
     {
      if (TC instanceof VTEditor)
      {
       VTEditor VTEtemp=(VTEditor)TC;
             
          if(VTEtemp.getFileName()!=null && VTEtemp.getFileName().equalsIgnoreCase(inFileName))
            return true;
        }
    }
    return false;
    }


  public void checkSaveAsAction()
  {
    if (result.allClasses().size() != 0)
    {
     cVTEditor =  result.allInstances().iterator().next();
     setEnabled(true);
    }
    else setEnabled(false);

  }
}






