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
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.coeus.poclasses.ArrayObject;
import org.coeus.poclasses.ArrayObjectList;
import org.coeus.poclasses.ConstantObject;
import org.coeus.poclasses.ConstantObjectList;
import org.coeus.poclasses.FunctionObject;
import org.coeus.poclasses.FunctionObjectList;
import org.coeus.poclasses.ProcedureObject;
import org.coeus.poclasses.ProcedureObjectList;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.VariableObjectList;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;

/**
 *
 * @author Jrd
 */
public class TestObjects {

 
    public TestObjects(JTextPane TPane, AllObjectsList allol,ConstantObjectList conl,VariableObjectList varl,
            ArrayObjectList arrl,FunctionObjectList funl,ProcedureObjectList prol)
    {

        MutableAttributeSet attrs = TPane.getInputAttributes();
        StyleConstants.setFontFamily(attrs, "Monospaced");
        StyleConstants.setFontSize(attrs, 12);
        StyleConstants.setBold(attrs, true);
        StyleConstants.setForeground(attrs, Color.BLACK);

        // Retrieve the pane's document object
        StyledDocument doc = TPane.getStyledDocument();

        String text;
        text="\n\n------------------------AllObjects----------------\n";

        try {
            doc.insertString(doc.getLength(), text, attrs);
        } catch (BadLocationException ex) {
           // Exceptions.printStackTrace(ex);
        }

     StyleConstants.setBold(attrs, false);

     for (ConstantObject k:conl.conList)
          {
            text="Constant:\nDName: "+k.getDispName()
                    +"\nDCategorie: "+k.getDispCateg()
                    +"\nDType: "+k.getDispType()
                    +"\nOValue: "+k.getObjValue()
                    +"\nOName: "+k.getObjName()
                    +"\nOType: "+k.getObjType()
                    +"\nDScope:"+k.getDispScope()
                    +"\nOScope:"+k.getObjScope()
                    +"\n\n";

                    StyleConstants.setForeground(attrs, Color.GREEN);
                    try {
                        doc.insertString(doc.getLength(), text, attrs);
                    } catch (BadLocationException ex) {
                       // Exceptions.printStackTrace(ex);
                    }
          }

           for (VariableObject k1:varl.varList)
          {
           text="Variable:\nDName: "+k1.getDispName()
                    +"\nDCategorie: "+k1.getDispCateg()
                    +"\nDType: "+k1.getDispType()
                    +"\nOValue: "+k1.getObjValue()
                    +"\nOName: "+k1.getObjName()
                    +"\nOType: "+k1.getObjType()
                  +"\nDScope:"+k1.getDispScope()
                    +"\nOScope:"+k1.getObjScope()
                      +"\n\n";
                    
                   StyleConstants.setForeground(attrs, Color.RED);
                    try {
                        doc.insertString(doc.getLength(), text, attrs);
                    } catch (BadLocationException ex) {
                       // Exceptions.printStackTrace(ex);
                    }
          }

  for (ArrayObject k0:arrl.arrList)
          {
            text="Array:\nDName: "+k0.getDispName()
                    +"\nDCategorie: "+k0.getDispCateg()
                    +"\nDType: "+k0.getDispType()
                    +"\nOValue: "+k0.getObjValue()
                    +"\nOName: "+k0.getObjName()
                    +"\nOType: "+k0.getObjType()
                    +"\nDScope:"+k0.getDispScope()
                    +"\nOScope:"+k0.getObjScope()
                    +"\nDims: "+k0.getDimension()
                    +"\nDims1: "+k0.getDim1Size()
                    +"\nDims2: "+k0.getDim2Size()
                    +"\n\n";

                    StyleConstants.setForeground(attrs, Color.YELLOW);
                    try {
                        doc.insertString(doc.getLength(), text, attrs);
                    } catch (BadLocationException ex) {
                       // Exceptions.printStackTrace(ex);
                    }
          }

   for (FunctionObject k2:funl.funList)
          {
            text="Function:\nDName: "+k2.getDispName()
                    +"\nDCategorie: "+k2.getDispCateg()
                    +"\nDType: "+k2.getDispType()
                    +"\nOName: "+k2.getObjName()
                    +"\nOType: "+k2.getObjType()
                    +"\nDScope:"+k2.getDispScope()
                    +"\nOScope:"+k2.getObjScope()
                    +"\nParamNum: "+k2.getParamNum()
                    +"\nDParams: "+k2.getDispParams2Show()
                    +"\nOParams: "+k2.getObjParams2Show()
                    +"\n\n";
                    
                  StyleConstants.setForeground(attrs, Color.BLUE);
                    try {
                        doc.insertString(doc.getLength(), text, attrs);
                    } catch (BadLocationException ex) {
                       // Exceptions.printStackTrace(ex);
                    }
          }

    for (ProcedureObject k3:prol.proList)
          {
            text="Procedure:\nDName: "+k3.getDispName()
                    +"\nDCategorie: "+k3.getDispCateg()
                    +"\nOName: "+k3.getObjName()
                    +"\nDScope:"+k3.getDispScope()
                    +"\nOScope:"+k3.getObjScope()
                    +"\nParamNum: "+k3.getParamNum()
                    +"\nDParams: "+k3.getDispParams2Show()
                    +"\nOParams: "+k3.getObjParams2Show()
                    +"\n\n";

                    StyleConstants.setForeground(attrs, Color.MAGENTA);
                    try {
                        doc.insertString(doc.getLength(), text, attrs);
                    } catch (BadLocationException ex) {
                       // Exceptions.printStackTrace(ex);
                    }
          }

      for (AllObjects s:allol.allObjects)
          {
            text="AllObejct:\nCategory: "+s.getDispCateg()
                    +"\nDName: "+s.getDispName()
                    +"\nDType: "+s.getDispType()
                    +"\nOName: "+s.getObjName()
                    +"\nOType: "+s.getObjType()
                    +"\nOValue: "+s.getObjValue()
                    +"\nOScope: "+s.getObjScope()
                    +"\nDScope: "+s.getDispScope()
                    +"\n\n";
            
                    StyleConstants.setForeground(attrs, Color.BLACK);
                    try {
                        doc.insertString(doc.getLength(), text, attrs);
                    } catch (BadLocationException ex) {
                       // Exceptions.printStackTrace(ex);
                    }
          }





    }
}
