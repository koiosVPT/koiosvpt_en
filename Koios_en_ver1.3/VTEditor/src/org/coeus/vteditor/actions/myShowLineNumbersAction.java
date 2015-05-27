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
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import org.coeus.vteditor.CreateCoeusProgram;
import org.coeus.vteditor.VTEditor;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;
import org.openide.windows.TopComponent;

/**
 *
 * @author Jrd
 */
public class myShowLineNumbersAction extends AbstractAction implements LookupListener, ContextAwareAction,Presenter.Menu {
    private Lookup context;
    Lookup.Result<VTEditor> result;
    private static myShowLineNumbersAction showLinesAction=null;
    private  VTEditor cVTEditor=null;
    private  JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem("Show Line Numbers...", null);;





    public myShowLineNumbersAction() {
        this(Utilities.actionsGlobalContext());
        showLinesAction=this;
    }


 public myShowLineNumbersAction(Lookup context) {
    init(context);
    putValue(NAME,"Show Line Numbers...");
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

    public static myShowLineNumbersAction getShowLinesAction()
    {return showLinesAction;}

    public void actionPerformed(ActionEvent e) {
               
    }

    public void resultChanged(LookupEvent ev) {
       checkShowLinesAction();
       setEnabled(true);
       }

    public Action createContextAwareInstance(Lookup actionContext) {
       return new myShowLineNumbersAction(context);
    }


  public void checkShowLinesAction()
  {
    if (result.allClasses().size() != 0)
    {
     cVTEditor =  result.allInstances().iterator().next();
     checkBox.setEnabled(true);
    }
    else checkBox.setEnabled(false);

  }

  public boolean getState()
  {return checkBox.getState();}

   public JCheckBoxMenuItem getCheckBox()
  {return checkBox;}

  @Override
    public JMenuItem getMenuPresenter() {
       
        checkBox.setSelected(true);

        checkBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            Set<TopComponent> TCSet= TopComponent.getRegistry().getOpened();
            for (TopComponent TC :TCSet )
             {
              if (TC instanceof VTEditor)
              {
                VTEditor vte=(VTEditor)TC;
                new CreateCoeusProgram(vte.getRootNode(),vte.getTextPane(),cVTEditor.getLineArea());
               }
            }
          }
        });
        
        return checkBox;
    }


}

