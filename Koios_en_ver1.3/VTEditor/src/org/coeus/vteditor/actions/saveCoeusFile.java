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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import org.coeus.poclasses.ArrayObjectList;
import org.coeus.poclasses.AssignObjectList;
import org.coeus.poclasses.CallObjectList;
import org.coeus.poclasses.ConstantObjectList;
import org.coeus.poclasses.DoWhileObjectList;
import org.coeus.poclasses.ForObjectList;
import org.coeus.poclasses.FunctionObjectList;
import org.coeus.poclasses.IfObjectList;
import org.coeus.poclasses.ProcedureObjectList;
import org.coeus.poclasses.ReadObjectList;
import org.coeus.poclasses.ReturnObjectList;
import org.coeus.poclasses.VariableObjectList;
import org.coeus.poclasses.WhileObjectList;
import org.coeus.poclasses.WriteObjectList;
import org.coeus.vteditor.VTEditor;
import org.coeus.vteditor.saveLoadClass;
import org.coeus.wizards.AllObjectsList;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.StatusDisplayer;
import org.openide.windows.WindowManager;

/**
 *
 * @author Jrd
 */
public class saveCoeusFile extends Thread{

    private saveLoadClass save=null;
    private File file=null;
    private VTEditor cVTEditor;
    private XStream xstream;
    private  Charset utf8 = Charset.forName("UTF-8");

   public saveCoeusFile(VTEditor vteditor,File f,XStream Xstream)
   {
   this.cVTEditor=vteditor;
   this.file=f;
   this.xstream=Xstream;
   }


    @Override
    public void run()
    {
          try {

        save=new saveLoadClass();
        save.saveRootNodeLists(cVTEditor.getRootNode(),ConstantObjectList.getConObjList(),
            VariableObjectList.getVarObjList(),ArrayObjectList.getArrObjList(),
            FunctionObjectList.getFunObjList(),ProcedureObjectList.getProObjList(),
            ReadObjectList.getReadObjList(),WriteObjectList.getWriteObjList(),
            CallObjectList.getCallObjList(),ReturnObjectList.getReturnObjList(),
            AssignObjectList.getAssignObjList(),DoWhileObjectList.getDoWhileObjList(),
            ForObjectList.getForObjList(),IfObjectList.getIfObjList(),
            WhileObjectList.getWhileObjList(),AllObjectsList.getAllObjList());

            cVTEditor.getUndoRedoList().clear();
            new getUndoInstance(cVTEditor.getRootNode());

             FileOutputStream outputStream =new FileOutputStream(file);
             Writer writer = new OutputStreamWriter(outputStream, utf8);
             xstream.toXML(save, writer);
             outputStream.close();

//Old file writng method - no UTF-8            
//            FileWriter fw = new FileWriter(file);
//            ObjectOutputStream out = xstream.createObjectOutputStream(fw,"save");
//            out.writeObject(save);
//            out.close();


         WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
            public void run() {
            cVTEditor.getModificationMade().clearModifications();
                if (cVTEditor.getDisplayName().contains("*"))
                     {
                     int starPos=cVTEditor.getDisplayName().lastIndexOf("*");
                     cVTEditor.setDisplayName
                             (cVTEditor.getDisplayName().substring(0, starPos));
                     }
            StatusDisplayer.getDefault().setStatusText("Saving file "+file.getPath());

          }});
           this.interrupt();
         } catch (IOException ex) {
           // Exceptions.printStackTrace(ex);
         NotifyDescriptor d = new NotifyDescriptor.Confirmation("An error occurred while saving file "+file.getName(),
         NotifyDescriptor.DEFAULT_OPTION, NotifyDescriptor.ERROR_MESSAGE);
         DialogDisplayer.getDefault().notify(d);
         }




    }

}
