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
import java.io.FilenameFilter;
import java.io.IOException;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author Jrd
 */
public class compileJavaFile extends Thread{

    private int javacExitVal;
    private String fileName=null;
    private String OS=null;
    private String javaVersion=null;
     
    public compileJavaFile(String filename)
    {
    this.fileName=filename;
    }
    
    @Override
    public void run() 
    {
    
     Runtime rt = Runtime.getRuntime();
    //Process pr = rt.exec("cmd /c dir");
    Process javac;
     String jdkPath=getJDKPath();
             if (jdkPath==null)
             {
              DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                      "The Java Development Kit (JDK) seems to be not installed on your system.\n" +
                      "You can create programs using Koios but they cannot be executed without JDK.\n" +
                      "Please install JDK 6 or higher and run Koios again!", NotifyDescriptor.ERROR_MESSAGE));
             }
             try
             {
  //             javac = rt.exec(jdkPath+"javac  -g -target 1.5 -cp .  " + fileName);
//             OS = System.getProperty("os.name") + System.getProperty("os.version");
//             System.out.println(OS);
//             System.out.println(System.getProperty("file.encoding") );

             OS = System.getProperty("os.name")+" "+ System.getProperty("os.version");
             javaVersion = System.getProperty("java.version");
            //  System.out.println(OS+" "+ jdkPath);
           //  System.out.println(System.getProperty("file.encoding") );

              if (OS.startsWith("Windows"))
             {
               int verStart=javaVersion.indexOf('.');
               int verEnd=javaVersion.indexOf('.', verStart+1);
               int jver =Integer.parseInt((String) javaVersion.subSequence(verStart+1, verEnd));
              if (jver>6)
               {
                 String [] cmd = new String[7];
                  cmd[0]= "\""+jdkPath+"javac\"";
                  cmd[1]= "-encoding";
                  cmd[2]= "UTF-8";
                  cmd[3]= "-g";
                  cmd[4]= "-cp";
                  cmd[5]= "." ;
                  cmd[6]= fileName;
                  javac = rt.exec(cmd);
                 //setJDKPath = rt.exec("cmd set PATH=%path%;"+jdkPath );
                 //javac = rt.exec("javac  -g -cp .  " + fileName);
//             javac = rt.exec(jdkPath+"javac  -g -cp .;myjoptionpane.jar  " + fileName);
              }
              else
              javac = rt.exec(jdkPath+"javac  -encoding UTF-8 -g -cp .  " + fileName);

                }
               else //linux
              javac = rt.exec(jdkPath+"javac  -encoding UTF-8 -g -cp .  " + fileName);
                 
             
             javacExitVal = javac.waitFor();
             this.interrupt();
            }
         catch (IOException ex) {
           NotifyDescriptor d = new NotifyDescriptor.Confirmation("An error occurred while writing the temporary file to disk!",
             "Error Compiling Program",NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.ERROR_MESSAGE);
             DialogDisplayer.getDefault().notify(d);
          }
         catch (InterruptedException ex) {
            NotifyDescriptor d = new NotifyDescriptor.Confirmation("An unexpeced error occurred while compiling the program!",
         "Error Compiling Program",NotifyDescriptor.DEFAULT_OPTION,NotifyDescriptor.ERROR_MESSAGE);
         DialogDisplayer.getDefault().notify(d);
         }
    
    }

    public int getJavacExitVal() {
        return javacExitVal;
    }



    public String getJDKPath()
    {
    File javaHome = new File( System.getProperty( "java.home" ) ).getParentFile();
    File jdkDir=null;
    String jdkPath=null;

    if( javaHome.getName().contains( "jdk" ) ) { //This will happen when running in IntelliJ
            jdkDir = javaHome;
    } else { //This will happen when running as a normal Java app, ie. from command line or double-clicking jar
            File[] candidates = javaHome.listFiles( new FilenameFilter() {
                    public boolean accept( File dir, String name ) {
                            return name.contains( "jdk" );
                    }
            } );
            if (candidates.length>=1)
            jdkDir = candidates[ candidates.length - 1 ]; //Assume that the last one is the most recent one
           
    }
    if (jdkDir!=null)
    jdkPath=jdkDir.getPath()+File.separator+"bin"+File.separator;
    //System.out.println( "Jdk dir: " + jdkDir+"\n"+jdkPath );

    return jdkPath;
    }
    
}


