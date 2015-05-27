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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.coeus.vteditor.btvnodes.RootNode;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class writeJavaFile extends Thread{
    private RootNode rootNode=null;
    private File f;
    private BufferedWriter bw = null;
    private String fileName=null;
    private boolean exportJavaFile;
    private  NotifyDescriptor d=null;

    public writeJavaFile(RootNode rootnode,File file,String filename,boolean exportjavafile)
    {
    this.rootNode=rootnode;
    this.f=file;
    this.fileName=filename;
    this.exportJavaFile=exportjavafile;
    
    if (exportJavaFile)
        d=new NotifyDescriptor.Confirmation("An error occurred while saving file "+file.getName(),
         NotifyDescriptor.DEFAULT_OPTION, NotifyDescriptor.ERROR_MESSAGE);
    else
        d = new NotifyDescriptor.Confirmation("An error occurred while writing the temporary file to disk!",
             "Error Compiling Program",NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.ERROR_MESSAGE);
    
    }
            
    
    
    @Override
    public void run()
    {
        
        try {
            //bw = new BufferedWriter(new FileWriter(f));
            bw = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(f),"UTF-8"));
             new writeJavaProgramBW(rootNode, bw, fileName);
            bw.close();

            if (exportJavaFile)
            {
              d =new NotifyDescriptor.Confirmation(
            "Java file with name  "+ f.getName()
             +"\nwas created in "+f.getPath(),"JAVA File Creation",
             NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.INFORMATION_MESSAGE);
            DialogDisplayer.getDefault().notify(d);
            }

        } catch (IOException ex) {            
             DialogDisplayer.getDefault().notify(d);
        } finally {
            try {
                bw.close();
                this.interrupt();
            } catch (IOException ex) {                
             DialogDisplayer.getDefault().notify(d);
            }
        }
    
    
    }

}
