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


package org.coeus.wizards.TextActions;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 * This code was based on code by
 * @author Steve Webb
 */
public class AddPopupMenu {
private JTextComponent jtc;

    public AddPopupMenu(JTextComponent injtc)
    {   jtc=injtc;
        jtc.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent evt)
            {
                jtc.requestFocus();
                dealWithMousePress(evt);
            }
        });}




     protected void dealWithMousePress(MouseEvent evt)
    {
        // Only interested in the right button
        if(SwingUtilities.isRightMouseButton(evt))
        {
            //if(MenuSelectionManager.defaultManager().getSelectedPath().length>0)
            //return;

            JPopupMenu menu = new JPopupMenu();
            menu.add(new CutAction(jtc));
            menu.add(new CopyAction(jtc));
            menu.add(new PasteAction(jtc));
            menu.addSeparator();
            menu.add(new DeleteAction(jtc));
            menu.addSeparator();
            menu.add(new SelectAllAction(jtc));

            // Display the menu
            Point pt = SwingUtilities.convertPoint(evt.getComponent(), evt.getPoint(), jtc);
            menu.show(jtc, pt.x, pt.y);
        }
    }






}
