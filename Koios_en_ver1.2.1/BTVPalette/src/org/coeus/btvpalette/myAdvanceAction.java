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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToggleButton;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;

/**
 *
 * @author Jrd
 */
public class myAdvanceAction extends AbstractAction implements LookupListener, 
        ContextAwareAction, Presenter.Menu, Presenter.Toolbar {
    private Lookup context;
    Lookup.Result<BTVPalTopComponent> result;
    private boolean JCBState=true;
    private JCheckBoxMenuItem jCBMMenu = null;//, jCBMToolbar;
    private JToggleButton jCBMToolbar;
    private static myAdvanceAction myAdvanceActionInstance=null;
    private String filename="adusaapref.tmp";
    File f=null;


    public myAdvanceAction() {
        this(Utilities.actionsGlobalContext());
        myAdvanceActionInstance=this;
    }


 public myAdvanceAction(Lookup context) {
    init(context);
    putValue(NAME,"Advanced User");
   
    checkUsersPreference();
          

    jCBMMenu = new JCheckBoxMenuItem("Advanced User", null);
        jCBMMenu.setState(JCBState);
        jCBMMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyBeanTreeView4Palette.getCategoryNodesState();
                JCBState=jCBMMenu.getState();
                BTVPalTopComponent.advancePCComps(JCBState);
                jCBMToolbar.setSelected(JCBState);
                //jCBMToolbar.setSelected(JCBState);
               MyBeanTreeView4Palette.setCategoryNodesState();
               saveUsersPreference();
            }
        });


        jCBMToolbar = new JToggleButton ("Advanced User");
        jCBMToolbar.setIcon((Icon) ImageUtilities.loadImage("org/coeus/btvpalette/chkbx0.png",true));
        jCBMToolbar.setSelectedIcon((Icon) ImageUtilities.loadImage("org/coeus/btvpalette/chkbx1.png",true));
        jCBMToolbar.setSelected(JCBState);
        jCBMToolbar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyBeanTreeView4Palette.getCategoryNodesState();
                JCBState=jCBMToolbar.isSelected();
                BTVPalTopComponent.advancePCComps(JCBState);
                jCBMMenu.setState(JCBState);
                MyBeanTreeView4Palette.setCategoryNodesState();
                saveUsersPreference();
           }
        });

 }


    private void init(Lookup context) {
    this.context = context;
    result = context.lookupResult(contextClass());
    result.addLookupListener(this);
    resultChanged(null);
   }


    public Class<BTVPalTopComponent> contextClass() {
    return BTVPalTopComponent.class;
    }

    public void actionPerformed(ActionEvent e) {
       
    }

    public void resultChanged(LookupEvent ev) {
       setEnabled(result.allItems().size() != 0);
      
    }


    public Action createContextAwareInstance(Lookup actionContext) {
       return new myAdvanceAction(context);
    }

    public JCheckBoxMenuItem getMenuPresenter() {
        return jCBMMenu;
    }

    public JToggleButton getToolbarPresenter() {
       return jCBMToolbar;
    }

public static myAdvanceAction getAdvanceAction()
{return myAdvanceActionInstance;}


public void setPresentersEnabled(boolean tf)
{
 jCBMMenu.setEnabled(tf);
 jCBMToolbar.setEnabled(tf);
}

public boolean getJCBState()
{return JCBState;}

    private void checkUsersPreference() {

   f= new File(this.filename);
   BufferedReader br=null;


   if (f.exists())
   {
            try {
                br = new BufferedReader(new FileReader(f));
                int preference = br.read();
                br.close();
                if (preference==1)
                    JCBState=true;
                else
                    JCBState=false;

            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);

             }

    }
   }

    private void saveUsersPreference()
    {
    f=new File(this.filename);

    BufferedWriter bw=null;

        try {
            bw = new BufferedWriter(new FileWriter(f));
            int preference;
            if (this.JCBState)
                preference=1;
            else
                preference=0;
            bw.write(preference);
            bw.close();

        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }


    }
    

}
