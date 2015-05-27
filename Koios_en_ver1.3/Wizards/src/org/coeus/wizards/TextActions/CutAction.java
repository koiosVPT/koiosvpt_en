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

/**
 *This code was based on code by
 * @author Steve Webb
 */


import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.text.JTextComponent;

/**
 *
 * Action class to provide an action to Cut from a component.
 *
 * @author steve.webb
 */
class CutAction extends AbstractAction
{
    /**
     * Icon to displayed against this action.
     */
    //static final private ImageIcon icon =
    //        new ImageIcon(ClassLoader.getSystemResource("toolbarButtonGraphics/general/Cut16.gif"));

    /**
     * The component the action is associated with.
     */
    JTextComponent comp;

    /**
     * Default constructor.
     * @param comp The component the action is associated with.
     */
    public CutAction(JTextComponent comp)
    {
        super("Αποκοπή" /*,icon*/);
        this.comp = comp;
    }

    /**
     * Action has been performed on the component.
     * @param e ignored
     */
    public void actionPerformed(ActionEvent e)
    {
        comp.cut();
    }

    /**
     * Checks if the action can be performed.
     * @return True if the action is allowed
     */
    @Override
    public boolean isEnabled()
    {
        return comp.isEditable()
        && comp.isEnabled()
        && comp.getSelectedText()!=null;
    }
}

