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

import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.coeus.vteditor.btvnodes.RootNode;
import org.coeus.poclasses.ArrayObject;
import org.coeus.poclasses.AssignObject;
import org.coeus.poclasses.CallObject;
import org.coeus.poclasses.ConstantObject;
import org.coeus.poclasses.DoWhileObject;
import org.coeus.poclasses.ForObject;
import org.coeus.poclasses.FunctionObject;
import org.coeus.poclasses.IfObject;
import org.coeus.poclasses.PrintStructure;
import org.coeus.poclasses.ProcedureObject;
import org.coeus.poclasses.ProgramObject;
import org.coeus.poclasses.ReadObject;
import org.coeus.poclasses.ReturnObject;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.WhileObject;
import org.coeus.poclasses.WriteObject;
import org.coeus.vteditor.actions.myShowLineNumbersAction;

import org.openide.nodes.Node;

/**
 *
 * @author Jrd
 */
public class CreateCoeusProgram {

     String mytab="     ";
     int tabs=1;

     
    public CreateCoeusProgram (RootNode rn,JTextComponent jtp,JTextComponent linesComponent)
    {

    jtp.setText("");
    Checks.constantsVariablesArraysChecks(rn);

     ProgramObject po =rn.getLookup().lookup(ProgramObject.class);
  
     printText(jtp,po.getCoeusString1(),"");
       if (rn.getChildren().getNodesCount() >= 0) {
            for (Enumeration e=rn.getChildren().nodes(); e.hasMoreElements(); ) {
                Node n = (Node)e.nextElement();

                ArrayObject arro = n.getLookup().lookup(ArrayObject.class);
                ConstantObject cono = n.getLookup().lookup(ConstantObject.class);
                FunctionObject funo = n.getLookup().lookup(FunctionObject.class);
                ProcedureObject proo = n.getLookup().lookup(ProcedureObject.class);
                VariableObject varo = n.getLookup().lookup(VariableObject.class);

               if (arro!=null)
                    printText(jtp,arro.getCoeusString1(),mytab);
               else if (cono!=null)
                    printText(jtp,cono.getCoeusString1(),mytab);
                else if (varo!=null)
                    printText(jtp,varo.getCoeusString1(),mytab);
               else if (funo!=null || proo!=null)
                { CreateObjects(n,jtp);}
            }
        }
      printText(jtp,po.getCoeusString2(),"");

     linesComponent.setVisible(false);
     if (myShowLineNumbersAction.getShowLinesAction().getState())
           showLines(jtp,linesComponent);

     jtp.setCaretPosition(0);
     }




  private void CreateObjects(Node rn,JTextComponent jtp)
  {
       String offset="";
      
       for (int off=0;off<tabs;off++)
       offset=offset+mytab;


        ArrayObject arro = rn.getLookup().lookup(ArrayObject.class);
        ConstantObject cono = rn.getLookup().lookup(ConstantObject.class);
        FunctionObject funo = rn.getLookup().lookup(FunctionObject.class);
        ProcedureObject proo = rn.getLookup().lookup(ProcedureObject.class);
        VariableObject varo = rn.getLookup().lookup(VariableObject.class);
        ReadObject reado = rn.getLookup().lookup(ReadObject.class);
        WriteObject writeo = rn.getLookup().lookup(WriteObject.class);
        CallObject callo = rn.getLookup().lookup(CallObject.class);
        ReturnObject reto = rn.getLookup().lookup(ReturnObject.class);
        AssignObject asso = rn.getLookup().lookup(AssignObject.class);
        DoWhileObject dwho = rn.getLookup().lookup(DoWhileObject.class);
        ForObject foro = rn.getLookup().lookup(ForObject.class);
        IfObject iffo = rn.getLookup().lookup(IfObject.class);
        WhileObject whio = rn.getLookup().lookup(WhileObject.class);

        if (arro != null) {
            printText(jtp,arro.getCoeusString1(),offset);
        } else if (cono != null) {
            printText(jtp,cono.getCoeusString1(),offset);
       } else if (varo != null) {
            printText(jtp,varo.getCoeusString1(),offset);
       }else if (reado != null) {
            printText(jtp,reado.getCoeusString1(),offset);
       }else if (writeo != null) {
            printText(jtp,writeo.getCoeusString1(),offset);
       }else if (callo != null) {
            printText(jtp,callo.getCoeusString1(),offset);
       }else if (reto != null) {
            printText(jtp,reto.getCoeusString1(),offset);
       }else if (asso != null) {
            printText(jtp,asso.getCoeusString1(),offset);

       } else if (funo != null) {
            printText(jtp,funo.getCoeusString1(),"\n"+mytab);
            tabs++;
       }
       else if (proo != null) {
            printText(jtp,proo.getCoeusString1(),"\n"+mytab);
            tabs++;
        }
       else if (dwho != null) {
            printText(jtp,dwho.getCoeusString1(),"\n"+mytab);
            tabs++;
        }
       else if (foro != null) {
            printText(jtp,foro.getCoeusString1(),"\n"+mytab);
            tabs++;
        }
       else if (iffo != null) {           
           List<Node> ifChildren=rn.getChildren().snapshot();
           for (int childIndex=0;childIndex<ifChildren.size();childIndex++)
           {
             printText(jtp,iffo.getCoeusString1(childIndex),"\n"+mytab);
             tabs++;
             CreateObjects(ifChildren.get(childIndex),jtp);
             tabs--;
           }
          // printText(jtp,iffo.getCoeusString2(),"\n"+mytab);
        }
       else if (whio != null) {
            printText(jtp,whio.getCoeusString1(),"\n"+mytab);
            tabs++;
        }
 
      if (rn.getChildren().getNodesCount() >= 0 && iffo==null) {
            for (Enumeration e=rn.getChildren().nodes(); e.hasMoreElements(); ) {
                Node n = (Node)e.nextElement();
                CreateObjects(n,jtp);
            }
        }

       if (funo != null) {
             printText(jtp,funo.getCoeusString2(),"\n"+mytab);
             tabs--;
       } else if (proo != null) {
             printText(jtp,proo.getCoeusString2(),"\n"+mytab);
             tabs--;
        }
         else if (dwho != null) {
            printText(jtp,dwho.getCoeusString2(),"\n"+mytab);
            tabs--;
        }
       else if (foro != null) {
            printText(jtp,foro.getCoeusString2(),"\n"+mytab);
            tabs--;
        }
       else if (iffo != null) {
            printText(jtp,iffo.getCoeusString2(),"\n"+mytab);
            tabs--;
        }
       else if (whio != null) {
            printText(jtp,whio.getCoeusString2(),"\n"+mytab);
            tabs--;
        }

  }


  private void printText (JTextComponent jtp,LinkedList<PrintStructure> psa,String offset)
{
   for (PrintStructure ps:psa)
   { 
     if (ps!=null)
     {       
       setJTextPaneFont(jtp,ps.getFont(),ps.getColor(),offset+ps.getText());
     }
     offset="";
   }
}


    /**  based on code by Philip Isenhour Copyright 2003-2007
     * Utility method for setting the font and color of a JTextPane. 
     */
    public static void setJTextPaneFont(JTextComponent jtp, Font font, Color c,String text) {
        // Start with the current input attributes for the JTextPane. This
        // should ensure that we do not wipe out any existing attributes
        // (such as alignment or other paragraph attributes) currently
        // set on the text area.

        //MutableAttributeSet attrs = jtp.getInputAttributes();
        SimpleAttributeSet attrs= new SimpleAttributeSet();


        // Set the font family, size, and style, based on properties of
        // the Font object. Note that JTextPane supports a number of
        // character attributes beyond those supported by the Font class.
        // For example, underline, strike-through, super- and sub-script.
        StyleConstants.setFontFamily(attrs, font.getFamily());
        StyleConstants.setFontSize(attrs, font.getSize());
        StyleConstants.setItalic(attrs, font.isItalic());
        StyleConstants.setBold(attrs, font.isBold());

        // Set the font color
        StyleConstants.setForeground(attrs, c);

        // Retrieve the pane's document object
       // StyledDocument doc = jtp.getStyledDocument();

       Document doc = jtp.getDocument();
       
        try {
            doc.insertString(doc.getLength(), text, attrs);
        } catch (BadLocationException ex) {
           // Exceptions.printStackTrace(ex);
        }
        //doc.setCharacterAttributes(doc.getLength()-text.length(),text.length(), attrs, false);
    }


public void showLines(JTextComponent jtp,JTextComponent linesComponent)
  {
  int lineNo=1;
  Document doc;
 // String mytab1="    ";

  linesComponent.setVisible(true);
   doc = jtp.getDocument();

   //MutableAttributeSet NumAttrs = jtp.getInputAttributes();

   SimpleAttributeSet NumAttrs= new SimpleAttributeSet();
   StyleConstants.setFontFamily(NumAttrs, Font.SERIF);
   StyleConstants.setFontSize(NumAttrs, 12);
   StyleConstants.setBold(NumAttrs, false);
   StyleConstants.setForeground(NumAttrs, Color.BLACK);

  try {

      //doc.insertString(0, Integer.toString(lineNo)+mytab1,NumAttrs);
      linesComponent.setText("1");
      for (int i=0;i<doc.getEndPosition().getOffset()-2;i++)
         if (doc.getText(i, 1).equals("\n"))
            {
             lineNo++;
             linesComponent.setText(linesComponent.getText()+"\n"+Integer.toString(lineNo));
            }

     } catch (BadLocationException ex) {}

 // jtp.setDocument(doc);
  }

 
    
}
