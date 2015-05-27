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


package org.coeus.wizards._HelpFuntions;

import java.awt.Color;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

/**
 *This code was based on code by
 * @author Joshua Engel , code found at
 * http://www.informit.com/articles/article.aspx?p=31204
 /**
 * A class to support highlighting of parenthesis.  To use it, add it as a
 * caret listener to your text component.
 *
 * It listens for the location of the dot.  If the character before the dot
 * is a close paren, it finds the matching start paren and highlights both
 * of them.  Otherwise it clears the highlighting.
 *
 * This object can be shared among multiple components.  It will only
 * highlight one at a time.
 **/

public class parenthesesMatcher implements CaretListener{

    /** The tags returned from the highlighter, used for clearing the
        current highlight. */
    Object start, end;

    /** The last highlighter used */
    Highlighter highlighter;

    /** Used to paint good parenthesis matches */
    Highlighter.HighlightPainter goodPainter;

    /** Used to paint bad parenthesis matches */
    Highlighter.HighlightPainter badPainter;

    /** Highlights using a good painter for matched parens, and a bad
        painter for unmatched parens */
    public parenthesesMatcher(Highlighter.HighlightPainter goodHighlightPainter,
		   Highlighter.HighlightPainter badHighlightPainter)
    {
	this.goodPainter = goodHighlightPainter;
	this.badPainter = badHighlightPainter;
    }

    /** A BracketMatcher with the default highlighters (cyan and magenta) */
    public parenthesesMatcher()
    {
	this(new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW),
	     new DefaultHighlighter.DefaultHighlightPainter(Color.RED));
    }

    public void clearHighlights()
    {
	if(highlighter != null) {
	    if(start != null)
		highlighter.removeHighlight(start);
	    if(end != null)
		highlighter.removeHighlight(end);
	    start = end = null;
	    highlighter = null;
	}
    }

    /** Returns the character at position p in the document*/
    public static char getCharAt(Document doc, int p)
	throws BadLocationException
    {
	return doc.getText(p, 1).charAt(0);
    }

    /** Returns the position of the matching parenthesis (bracket,
     * whatever) for the character at paren.  It counts all kinds of
     * brackets, so the "matching" parenthesis might be a bad one.  For
     * this demo, we're not going to take quotes or comments into account
     * since that's not the point.
     *
     * It's assumed that paren is the position of some parenthesis
     * character
     *
     * @return the position of the matching paren, or -1 if none is found
     **/
    public static int findPreviousMatchingParen(Document d, int paren)
	throws BadLocationException
    {
	int parenCount = 1;
	int i = paren-1;
	for(; i >= 0; i--) {
	    char c = getCharAt(d, i);
	    switch(c) {
	    case ')':
	    case '}':
	    case ']':
		parenCount++;
		break;
	    case '(':
	    case '{':
	    case '[':
		parenCount--;
		break;
	    }
	    if(parenCount == 0)
		break;
	}
	return i;
    }



      public static int findNextMatchingParen(Document d, int paren)
	throws BadLocationException
    {
	int parenCount = 1;
	int i = paren+1;
	for(; i <d.getLength(); i++) {
	    char c = getCharAt(d, i);
	    switch(c) {
	    case ')':
	    case '}':
	    case ']':
		parenCount--;
		break;
	    case '(':
	    case '{':
	    case '[':
		parenCount++;
		break;
	    }
	    if(parenCount == 0)
		break;
	}
	return i;
    }




    /** Called whenever the caret moves, it updates the highlights */
    public void caretUpdate(CaretEvent e)
    {
	clearHighlights();
	JTextComponent source = (JTextComponent) e.getSource();
	highlighter = source.getHighlighter();
	Document doc = source.getDocument();
	if(e.getDot() == 0) {
	    return;
	}

	// The character we want is the one before the current position
	int closeParen = e.getDot()-1;
	try {
	    char c = getCharAt(doc, closeParen);
	    if(c == ')' ||
	       c == ']' ||
	       c == '}') {
		int openParen = findPreviousMatchingParen(doc, closeParen);
		if(openParen >= 0) {
		    char c2 = getCharAt(doc, openParen);
		    if((c2 == '(' && c == ')') ||
		       (c2 == '{' && c == '}') ||
		       (c2 == '[' && c == ']')) {
			start = highlighter.addHighlight(openParen,
							 openParen+1,
							 goodPainter);
			end = highlighter.addHighlight(closeParen,
						       closeParen+1,
						       goodPainter);
		    }
		    else {
			start = highlighter.addHighlight(openParen,
							 openParen+1,
							 badPainter);
			end = highlighter.addHighlight(closeParen,
						       closeParen+1,
						       badPainter);
		    }
		}
		else {
		    end = highlighter.addHighlight(closeParen,
						   closeParen+1,
						   badPainter);
		}

	    }
	}
    catch(BadLocationException ex) {
	    throw new Error(ex);
	}



    ///my test......
    int myOpenParen = e.getDot()-1;
	try {
	    char c = getCharAt(doc, myOpenParen);
	    if(c == '(' ||
	       c == '[' ||
	       c == '{') {
		int myCloseParen = findNextMatchingParen(doc, myOpenParen);
		if(myCloseParen >= 0) {
            char c2 = getCharAt(doc, myCloseParen);
            System.out.println("\n\n" + myCloseParen+" : "+ c2+"\n\n");
		    if((c == '(' && c2 == ')') ||
		       (c == '{' && c2 == '}') ||
		       (c == '[' && c2 == ']')) {
			start = highlighter.addHighlight(myCloseParen,
							 myCloseParen+1,
							 goodPainter);
			end = highlighter.addHighlight(myOpenParen,
						       myOpenParen+1,
						       goodPainter);
		    }
		    else {
			start = highlighter.addHighlight(myCloseParen,
							 myCloseParen+1,
							 badPainter);
			end = highlighter.addHighlight(myOpenParen,
						       myOpenParen+1,
						       badPainter);
		    }
		}
		else {
		    end = highlighter.addHighlight(myOpenParen,
						   myOpenParen+1,
						   badPainter);
		}

	    }
	}
    catch(BadLocationException ex) {
	    throw new Error(ex);
	}


  }

    
}
