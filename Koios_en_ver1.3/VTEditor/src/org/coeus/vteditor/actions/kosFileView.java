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

import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Jrd
 */
public class kosFileView extends FileView{

     private String fileType=null;

    public kosFileView(String fileType) {
        this.fileType=fileType;
    }

    @Override
    public String getDescription(File f) {
    String extension = greekFileChooser.getExtension(f);

    if (extension != null) {
        if (extension.equals(greekFileChooser.KOS_EXT))
            return "Αρχείο Κοίος";

         if (extension.equals(greekFileChooser.JAVA_EXT))
            return "Αρχείο JAVA";
    }
    return null;
    }

    @Override
    public Icon getIcon(File f) {
    String extension = greekFileChooser.getExtension(f);

    if (extension != null) {
        if (extension.equals(greekFileChooser.KOS_EXT))
            return new ImageIcon(ImageUtilities.loadImage("org/coeus/vteditor/resources/fileIcon16.png", true));

          //  if (extension.equals(greekFileChooser.JAVA_EXT))
         //   return new ImageIcon(ImageUtilities.loadImage("org/coeus/vteditor/resources/javaIcon16.png", true));

    }
    return null;
  }

    @Override
    public String getName(File f) {
        return f.getName();
    }

    @Override
    public String getTypeDescription(File f) {
        return null;
    }

    @Override
    public Boolean isTraversable(File f) {
        return null;
    }



}
