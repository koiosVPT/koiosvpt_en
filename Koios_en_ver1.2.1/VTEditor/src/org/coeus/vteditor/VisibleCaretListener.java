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

package org.coeus.vteditor;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 *  This code was base on code found at :http://tips4java.wordpress.com/2009/01/25/no-wrap-text-pane/
 *  Simple class to ensure that the caret is visible within the viewport
 *  of the scrollpane. This is the normal situation. However, I've noticed
 *  that solutions that attempt to turn a text pane into a non wrapping
 *  text pane will result in the caret not being visible when adding text
 *  to the right edge of the viewport.
 *
 *  In general, this class can be used any time you wish to increase the number
 *  of visible pixels after the caret on the right edge of a scroll pane.
 */
public class VisibleCaretListener implements CaretListener
{
	private int visiblePixels;

	/**
	 *  Convenience constructor to create a VisibleCaretListener using
	 *  the default value for visible pixels, which is set to 2.
	 */
	public VisibleCaretListener()
	{
		this(2);
	}

	/**
	 *  Create a VisibleCaretListener.
	 *
	 *  @param pixels the number of visible pixels after the caret.
	 */
	public VisibleCaretListener(int visiblePixels)
	{
		setVisiblePixels( visiblePixels );
	}

	/**
	 *  Get the number of visble pixels displayed after the Caret.
	 *
	 *  @return the number of visible pixels after the caret.
	 */
	public int getVisiblePixels()
	{
		return visiblePixels;
	}

	/**
	 *  Control the number of pixels that should be visible in the viewport
	 *  after the caret position.
	 *
	 *  @param pixels the number of visible pixels after the caret.
	 */
	public void setVisiblePixels(int visiblePixels)
	{
		this.visiblePixels = visiblePixels;
	}
//
//	Implement CaretListener interface
//
	public void caretUpdate(final CaretEvent e)
	{
		//  Attempt to scroll the viewport to make sure Caret is visible

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
   				try
   				{
       				JTextComponent component = (JTextComponent)e.getSource();
       				int position = component.getCaretPosition();
       				Rectangle r = component.modelToView(position);
       				r.x += visiblePixels;
       				component.scrollRectToVisible(r);
   				}
   				catch(Exception ble) {}
			}
		});
	}
}
