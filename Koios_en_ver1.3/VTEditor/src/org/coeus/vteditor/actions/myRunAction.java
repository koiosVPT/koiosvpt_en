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

import org.coeus.vteditor.execenv.executionDialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;



import org.coeus.vteditor.VTEditor;
import org.coeus.vteditor.programState;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 *
 * @author Jrd
 */
public class myRunAction extends AbstractAction implements LookupListener, ContextAwareAction {
    private Lookup context;
    Lookup.Result<VTEditor> result;
    private static myRunAction runAction=null;
  //  private String fileName=null;
    private VTEditor cVTEditor=null;

    private  ProgressHandle handle =null;

    String output="";



    public myRunAction() {
        this(Utilities.actionsGlobalContext());
        runAction=this;
    }


 public myRunAction(Lookup context) {
    init(context);
    putValue(NAME,"Run");
    putValue("iconBase","org/coeus/vteditor/resources/runProject.png");
  }


    private void init(Lookup context) {
    this.context = context;
    result = context.lookupResult(contextClass());
    result.addLookupListener(this);
    resultChanged(null);
    }

    public static myRunAction getRunAction()
    {return runAction;}

    public Class<VTEditor> contextClass() {
    return VTEditor.class;
    }

    public void actionPerformed(ActionEvent e) {

         handle = ProgressHandleFactory.createHandle("Running...");
         handle.start();
        //fileName=cVTEditor.getObjectName();
        output="";

// try {
//
//                Runtime rt = Runtime.getRuntime();
//                Process java = rt.exec("java -classpath . "+fileName);
//
//                BufferedReader input = new BufferedReader(new InputStreamReader(java.getInputStream()));
//
//                String line=null;
//
//
//                while((line=input.readLine()) != null) {
//                   // System.out.println(line);
//                    output=output+line+"\n";
//                }
//
//                int javaExitVal = java.waitFor();
//                System.out.println("Exited with error code "+javaExitVal);
//
//                cVTEditor.setProgramOutput(output);
//
//            } catch(Exception ex) {
//                System.out.println(ex.toString());
//                ex.printStackTrace();
//            }

           

 java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                final executionDialog execDialog = new executionDialog(null/*new JFrame()*/, false,cVTEditor.getRootNode(),cVTEditor.getObjectName());
                execDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                       execDialog.setVisible(false);
                       execDialog.dispose();
                    }
                });
              
                execDialog.setIconImage( new ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/runProject.png")).getImage());
                execDialog.setLocationRelativeTo(null);
                Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
                execDialog.setLocation((d.width-execDialog.getSize().width)/2,(d.height-execDialog.getSize().height)/2);
                
                execDialog.setVisible(true);

   
            }
        });

new checkActionsState();
handle.finish();
    }

    public void resultChanged(LookupEvent ev) {
       if(result.allInstances().size() != 0)
       {
        cVTEditor =  result.allInstances().iterator().next();
        checkRunAction();
       }
       else
           setEnabled(false);
       }

    public Action createContextAwareInstance(Lookup actionContext) {
       return new mySearchAction(context);
    }

    public String getOutput() {
        return output;
    }

    public void checkRunAction()
    {
    if (cVTEditor.getProgramState().getCompilerState()==programState.OK ||
     cVTEditor.getProgramState().getCompilerState()==programState.WARNINGS)
    setEnabled(true);
    else
     setEnabled(false);
    }


}



