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



package org.coeus.vteditor.mypropertieswindow;

import java.util.Collection;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import org.coeus.poclasses.ArrayObject;
import org.coeus.poclasses.ArrayObjectProperties;
import org.coeus.poclasses.AssignObject;
import org.coeus.poclasses.AssignObjectProperties;
import org.coeus.poclasses.CallObject;
import org.coeus.poclasses.CallObjectProperties;
import org.coeus.poclasses.ConstantObject;
import org.coeus.poclasses.ConstantObjectProperties;
import org.coeus.poclasses.DoWhileObject;
import org.coeus.poclasses.DoWhileObjectProperties;
import org.coeus.poclasses.ForObject;
import org.coeus.poclasses.ForObjectProperties;
import org.coeus.poclasses.FunctionObject;
import org.coeus.poclasses.FunctionObjectProperties;
import org.coeus.poclasses.IfObject;
import org.coeus.poclasses.IfObjectProperties;
import org.coeus.poclasses.ProcedureObject;
import org.coeus.poclasses.ProcedureObjectProperties;
import org.coeus.poclasses.ProgramObject;
import org.coeus.poclasses.ReadObject;
import org.coeus.poclasses.ReadObjectProperties;
import org.coeus.poclasses.ReturnObject;
import org.coeus.poclasses.ReturnObjectProperties;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.VariableObjectProperties;
import org.coeus.poclasses.WhileObject;
import org.coeus.poclasses.WhileObjectProperties;
import org.coeus.poclasses.WriteObject;
import org.coeus.poclasses.WriteObjectProperties;
import org.coeus.vteditor.btvnodes.PCNodeParent;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd="-//org.coeus.vteditor.mypropertieswindow//myProperties//EN",
    autostore=false
)
public final class myPropertiesTopComponent extends TopComponent implements LookupListener {

    private static myPropertiesTopComponent instance,current;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "myPropertiesTopComponent";


    private Lookup.Result resultNodes = null;
    private Lookup.Result result=null;
    private static String IF =": If ";
    private static String ELSEIF =": Else If ";
    private static String ELSE =": Else ";
    private static String PROGRAM ="Program: ";
    private  Lookup.Template tpl=null;

    public myPropertiesTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(myPropertiesTopComponent.class, "CTL_myPropertiesTopComponent"));
        setToolTipText(NbBundle.getMessage(myPropertiesTopComponent.class, "HINT_myPropertiesTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
	putClientProperty(TopComponent.PROP_KEEP_PREFERRED_SIZE_WHEN_SLIDED_IN, Boolean.TRUE);

          jScrollPane1.setViewportView(new EmptyPanel());
       jScrollPane1.setBorder(BorderFactory.createEmptyBorder());

    tpl = new Lookup.Template (Node.class);
    result = Utilities.actionsGlobalContext().lookup(tpl);
    result.addLookupListener (this);

    current=this;
    }


      public void resultChanged(LookupEvent ev) {

        //Lookup.Result rpnp = (Lookup.Result) ev.getSource();
        //Collection  cpn = rpnp.allInstances();


    Collection<AbstractNode> cpn = result.allInstances();
 ////Check if AbstractNode selected
      if (!cpn.isEmpty())
      {
         AbstractNode an = (AbstractNode) cpn.iterator().next();
         if (an!=null)
          {
             if (an instanceof PCNodeParent)
             {
               PCNodeParent pNode=(PCNodeParent)an;
               if (pNode.getDispCateg().equalsIgnoreCase(WizardsDefinitions.IF_STATEMENT))
               {
                  jScrollPane1.getViewport().removeAll();
                  EmptyPanel prgJP =new EmptyPanel();
                  prgJP.getLabel().setText(WizardsDefinitions.IF_STATEMENT+IF+pNode.getDispName());
                  jScrollPane1.setViewportView(prgJP);
               }
               else if( pNode.getDispCateg().equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
               {
                  jScrollPane1.getViewport().removeAll();
                  EmptyPanel prgJP =new EmptyPanel();
                  prgJP.getLabel().setText(WizardsDefinitions.ELSE_IF_STATEMENT+ELSEIF+pNode.getDispName());
                  jScrollPane1.setViewportView(prgJP);
               }
               else if (pNode.getDispCateg().equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT) )
               {
                  jScrollPane1.getViewport().removeAll();
                  EmptyPanel prgJP =new EmptyPanel();
                  prgJP.getLabel().setText(WizardsDefinitions.ELSE_STATEMENT+ELSE);
                  jScrollPane1.setViewportView(prgJP);
               }
               else
               {
                 Lookup lookup = an.getLookup();
                 processLookup(lookup);
                }
             }
          else
             {
               Lookup lookup = an.getLookup();
               processLookup(lookup);
              }
          }
          else
          {
             jScrollPane1.getViewport().removeAll();
             jScrollPane1.setViewportView(new EmptyPanel());
          }
      }

    }


       public void  processLookup(Lookup lookup)
       {

         if (lookup!=null)
         {
          ProgramObject prgo = lookup.lookup(ProgramObject.class);
          ArrayObject arro = lookup.lookup(ArrayObject.class);
          ConstantObject cono = lookup.lookup(ConstantObject.class);
          FunctionObject funo = lookup.lookup(FunctionObject.class);
          ProcedureObject proo = lookup.lookup(ProcedureObject.class);
          VariableObject varo = lookup.lookup(VariableObject.class);
          ReadObject reado = lookup.lookup(ReadObject.class);
          WriteObject writeo = lookup.lookup(WriteObject.class);
          CallObject callo = lookup.lookup(CallObject.class);
          ReturnObject reto = lookup.lookup(ReturnObject.class);
          AssignObject asso = lookup.lookup(AssignObject.class);
          DoWhileObject dwho = lookup.lookup(DoWhileObject.class);
          ForObject foro = lookup.lookup(ForObject.class);
          IfObject iffo = lookup.lookup(IfObject.class);
          WhileObject whio = lookup.lookup(WhileObject.class);

        if (prgo != null) {
          jScrollPane1.getViewport().removeAll();
          EmptyPanel prgJP =new EmptyPanel();
          prgJP.getLabel().setText(PROGRAM+prgo.getDispName());
          jScrollPane1.setViewportView(prgJP);
        }
        else if (arro != null) {
          jScrollPane1.getViewport().removeAll();
          ArrayObjectProperties arrJP=new ArrayObjectProperties(arro);
          arrJP.showLabel(false);
          jScrollPane1.setViewportView(arrJP);
        } else if (cono != null) {
          jScrollPane1.getViewport().removeAll();
          ConstantObjectProperties conJP = new ConstantObjectProperties(cono);
          conJP.showLabel(false);
          jScrollPane1.setViewportView(conJP);
        } else if (funo != null) {
          jScrollPane1.getViewport().removeAll();
          FunctionObjectProperties funJP =new FunctionObjectProperties(funo);
          funJP.showLabel(false);
          jScrollPane1.setViewportView(funJP);
        } else if (proo != null) {
          jScrollPane1.getViewport().removeAll();
          ProcedureObjectProperties proJP =new ProcedureObjectProperties(proo);
          proJP.showLabel(false);
          jScrollPane1.setViewportView(proJP);
        } else if (varo != null) {
          jScrollPane1.getViewport().removeAll();
          VariableObjectProperties varJP =new VariableObjectProperties(varo);
          varJP.showLabel(false);
          jScrollPane1.setViewportView(varJP);
        } else if (reado != null) {
          jScrollPane1.getViewport().removeAll();
          ReadObjectProperties readJP =new ReadObjectProperties(reado);
          readJP.showLabel(false);
          jScrollPane1.setViewportView(readJP);
        }
        else if (writeo != null) {
          jScrollPane1.getViewport().removeAll();
          WriteObjectProperties writeJP =new WriteObjectProperties(writeo);
          writeJP.showLabel(false);
          jScrollPane1.setViewportView(writeJP);
        }
        else if (callo != null) {
          jScrollPane1.getViewport().removeAll();
          CallObjectProperties callJP =new CallObjectProperties(callo);
          callJP.showLabel(false);
          jScrollPane1.setViewportView(callJP);
        }
        else if (reto != null) {
          jScrollPane1.getViewport().removeAll();
          ReturnObjectProperties retJP =new ReturnObjectProperties(reto);
          retJP.showLabel(false);
          jScrollPane1.setViewportView(retJP);
        }
        else if (asso != null) {
          jScrollPane1.getViewport().removeAll();
          AssignObjectProperties assJP =new AssignObjectProperties(asso);
          assJP.showLabel(false);
          jScrollPane1.setViewportView(assJP);
        }
        else if (dwho != null) {
          jScrollPane1.getViewport().removeAll();
          DoWhileObjectProperties dwhJP =new DoWhileObjectProperties(dwho);
          dwhJP.showLabel(false);
          jScrollPane1.setViewportView(dwhJP);
        }
        else if (foro != null) {
          jScrollPane1.getViewport().removeAll();
          ForObjectProperties forJP =new ForObjectProperties(foro);
          forJP.showLabel(false);
          jScrollPane1.setViewportView(forJP);
        }
        else if (iffo != null) {
          jScrollPane1.getViewport().removeAll();
          IfObjectProperties iffJP =new IfObjectProperties(iffo);
          iffJP.showLabel(false);
          jScrollPane1.setViewportView(iffJP);
        }
        else if (whio != null) {
          jScrollPane1.getViewport().removeAll();
          WhileObjectProperties whiJP =new WhileObjectProperties(whio);
          whiJP.showLabel(false);
          jScrollPane1.setViewportView(whiJP);
        }
       else
        {
          jScrollPane1.getViewport().removeAll();
          jScrollPane1.setViewportView(new EmptyPanel());

        }
      }
   }

    public JScrollPane getJCP1 ()
    {
       return this.jScrollPane1;
    }


   public static myPropertiesTopComponent getCurrent()
   {return current;}


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized myPropertiesTopComponent getDefault() {
        if (instance == null) {
            instance = new myPropertiesTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the myPropertiesTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized myPropertiesTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(myPropertiesTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof myPropertiesTopComponent) {
            return (myPropertiesTopComponent) win;
        }
        Logger.getLogger(myPropertiesTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
         result = Utilities.actionsGlobalContext().lookup(tpl);
        result.addLookupListener (this);
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
//            result.removeLookupListener(this);
//            result=null;
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        myPropertiesTopComponent singleton = myPropertiesTopComponent.getDefault();
        singleton.readPropertiesImpl(p);
        return singleton;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }
}
