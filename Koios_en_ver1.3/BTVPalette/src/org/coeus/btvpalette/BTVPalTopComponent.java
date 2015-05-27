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


package org.coeus.btvpalette;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.swing.ActionMap;
import javax.swing.text.DefaultEditorKit;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
public final class BTVPalTopComponent extends TopComponent implements ExplorerManager.Provider {

    private static boolean advance;
    private MyBeanTreeView4Palette mbtvp=null;
    private static AbstractNode paletteRoot=null,paletteRootAdvanced=null;

    private static BTVPalTopComponent instance,current=null;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

    private static final String PREFERRED_ID = "BTVPalTopComponent";

    private static transient ExplorerManager ExpManPal = new ExplorerManager();
    private static boolean bool=false;

    public BTVPalTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(BTVPalTopComponent.class, "CTL_BTVPalTopComponent"));
        setToolTipText(NbBundle.getMessage(BTVPalTopComponent.class, "HINT_BTVPalTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));



        ///Load explorer manager onto component
        associateLookup( ExplorerUtils.createLookup(ExpManPal, getActionMap()));

         ////Create BeanTreeView4palette
        mbtvp = new MyBeanTreeView4Palette();
        mbtvp.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(mbtvp,BorderLayout.CENTER);
        ////End Create BeanTreeView4palette


        ///////Show nodes
        //ExpManPal.setRootContext(new AbstractNode(new CategoryChildren()));
        advance=false;
        paletteRoot=new AbstractNode(new CategoryChildren(false));
        paletteRootAdvanced=new AbstractNode(new CategoryChildren(true));
        advancePCComps(advance);
        //////End Show nodes

       
        ////////////////ExplorerManagerPalette Action Map
        ActionMap map = getActionMap();
        map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(ExpManPal));
        //////////////End ExplorerMAnagerPalette  Action Map


       setPaletteRoot( myAdvanceAction.getAdvanceAction().getJCBState());

        current=this;

    }

     public ExplorerManager getExplorerManager() {
        return ExpManPal;
    }

  public AbstractNode getPRAdvanced() {
        return paletteRootAdvanced;
    }

   public AbstractNode getPR() {
        return paletteRoot;
    }

   public static BTVPalTopComponent getCurrentBTVPalTopComponent()
   {return current;}

   public void setPaletteRoot(boolean advance)
   {
     if (advance)
        ExpManPal.setRootContext(paletteRootAdvanced);
     else
       ExpManPal.setRootContext(paletteRoot);
   }



   public static void advancePCComps(boolean advance)
   {
   BTVPalTopComponent.advance=advance;
   MyBeanTreeView4Palette.getCategoryNodesState();
   if (advance)
        ExpManPal.setRootContext(paletteRootAdvanced);
   else
       ExpManPal.setRootContext(paletteRoot);
   MyBeanTreeView4Palette.setCategoryNodesState();
   }



   public static void MyFilter (boolean TextView )
   {  
      if (TextView!=bool && TextView==true)
      {MyBeanTreeView4Palette.getCategoryNodesState();
       ExpManPal.setRootContext(new AbstractNode(Children.LEAF));
       bool=true;}
      else if (TextView!=bool && TextView==false)
      {   if (advance)
              ExpManPal.setRootContext(paletteRootAdvanced);
          else
               ExpManPal.setRootContext(paletteRoot);
         MyBeanTreeView4Palette.setCategoryNodesState();
         bool=false;
      }
   }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized BTVPalTopComponent getDefault() {
        if (instance == null) {
            instance = new BTVPalTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the BTVPalTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized BTVPalTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(BTVPalTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof BTVPalTopComponent) {
            return (BTVPalTopComponent) win;
        }
        Logger.getLogger(BTVPalTopComponent.class.getName()).warning(
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
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    /** replaces this in object stream */
    @Override
    public Object writeReplace() {
        return new ResolvableHelper();
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

   

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 1L;

        public Object readResolve() {
            return BTVPalTopComponent.getDefault();
        }
    }
}
