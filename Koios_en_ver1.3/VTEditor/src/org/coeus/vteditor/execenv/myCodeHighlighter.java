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



package org.coeus.vteditor.execenv;

import java.awt.Color;
import java.util.Hashtable;
import java.util.LinkedList;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import org.coeus.vteditor.btvnodes.RootNode;

/**
 *
 * @author Jrd
 */
public class myCodeHighlighter extends DefaultHighlighter{

    private JTextPane component=null;
    private String content=null;
    LinkedList<String>  commands=null;
    LinkedList<Integer> start=null,end=null;
     /** The last highlighter used */
    Highlighter highlighter;
  /** Used to paint good parenthesis matches */
    Highlighter.HighlightPainter goodPainter;

    /** Used to paint bad parenthesis matches */
    Highlighter.HighlightPainter goodPainter2;

    public myCodeHighlighter(RootNode rn,JTextPane jtp)
    {

    this.component=jtp;
    commands =new LinkedList();
    start =new LinkedList();
    end =new LinkedList();

      Document doc= component.getDocument();
        try {
            content = doc.getText(0, doc.getLength());
        } catch (BadLocationException ex) {}
    this.goodPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    this.goodPainter2 = new DefaultHighlighter.DefaultHighlightPainter(new Color(255,204,51));

    String temp=content;

       
    int start_pos=0;
    int end_pos=0;
    while (start_pos<content.length())
    {

    temp = (String) content.substring(start_pos, content.length());
    start_pos=start_pos+ countWhiteSpaces(temp);
    temp = (String) content.substring(start_pos, content.length());

    end_pos=start_pos+temp.indexOf('\n');

     if (temp.startsWith("ARRAY ")) 
         end_pos=start_pos+temp.indexOf(';')+1;

     temp = (String) content.substring(start_pos, end_pos);


     if (end_pos==-1)
     {
         end_pos=temp.length();
         commands.add(temp.substring(start_pos, end_pos));
         start.add(start_pos);
         end.add(end_pos);
     }
     else
     {
       commands.add(temp);
       start.add(start_pos);
       end.add(end_pos);
     }

      start_pos=end_pos+1;

    }


    }



   public void addHighlight(String command, int commandLine, int textLineNumber,myHashtable<String,Integer>jcommandLocator,
           myHashtable<String,Integer>jcommandLineStart,myHashtable<String,Integer>jcommandLineEnd,
           Hashtable<Integer,Integer> commandBeiginEndPairs)
   {
      
       component.getHighlighter().removeAllHighlights();
       try {


             if (textLineNumber>0)
             {
               for (int i=0; i<jcommandLineStart.size();i++ )
                 {
                  if (textLineNumber>= jcommandLineStart.getV(i) && textLineNumber<= jcommandLineEnd.getV(i))
                  {
                    //  System.out.println("\n\ntrew" +i+jcommandLineStart.getK(i) +"\n\n");
                    //  System.out.println( jcommandLocator.get((String)jcommandLineStart.getK(i)) +"\n\n");

//                       int commandNumber = jcommandLocator.get(jcommandLineStart.getK(i));
//                       component.setCaretPosition(start.get(commandNumber));
//                       component.getHighlighter().addHighlight(start.get(commandNumber), end.get(commandNumber), goodPainter);
                       component.setCaretPosition(start.get(i));
                       component.getHighlighter().addHighlight(start.get(i), end.get(i), goodPainter);
                       
                       if (commandBeiginEndPairs.get(i)!=null)
                       {
                        int commandsEndPos=(int)commandBeiginEndPairs.get(i);
                        component.getHighlighter().addHighlight(start.get(commandsEndPos), end.get(commandsEndPos), goodPainter2);
                       }

                  }

                 }
             }
             else
             {
              component.setCaretPosition(start.get(commandLine));
              component.getHighlighter().addHighlight(start.get(commandLine), end.get(commandLine), goodPainter);
             }
           

    //    int index = content.indexOf(command, 0);
    //    if (index >= 0) {   // match found
    //        Element elem=Utilities.getParagraphElement(component, index);
            //int start = elem.getStartOffset();
            //int end = elem.getEndOffset();
                //int end = index + command.length();
               // component.setCaretPosition(index);
              // component.getHighlighter().addHighlight(index, end, goodPainter);
                

             // }
            } catch (BadLocationException e) {}

        }


    private int countWhiteSpaces(String s)
    {
      int p=0;
      while (s.charAt(p)==' ' || s.charAt(p)=='\n')
          p++;
      return p;
    }

   }



