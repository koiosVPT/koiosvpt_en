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


package org.coeus.poclasses;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Jrd
 */
public class PrintStructure {

    private String text;
    private Font font;
    private Color color;
    private static int size=12;
    private static String fontFamily="Monospaced";

    public PrintStructure(String iText,Font iFont,Color iColor)
    {this.text=iText;
    this.font=iFont;
    this.color=iColor;
    }

    public String getText()
    {return this.text;}

    public Font getFont()
    {return this.font;}

    public Color getColor()
    {return this.color;}


     public int getSize()
    {return size;}

    public String getFamilyFont()
    {return fontFamily;}


    public void setSize(int iSize)
    {size=iSize;}

    public void setFamilyFont(String iFamilyFont)
    {fontFamily=iFamilyFont;}


    /////////////////////COLORS/////////////////////////
    public static final Color RESERVED_WORD_COLOR=new Color (0,0,230);//light blue
    public static final Color IDENTFIER_COLOR=Color.BLACK;
    public static final Color GLOBAL_COLOR=new Color(0,153,0);//green
    public static final Color NUMBER_COLOR=new Color(200,170,85);//ekrou
    public static final Color CHARACTER_COLOR=new Color(206,123,0);//orange-brown
    public static final Color BOOLEAN_COLOR=Color.GRAY;
    public static final Color COMMENT_COLOR=new Color(150,150,150);///silver
    //////////////////////FONTS//////////////////////////
    public static final Font RESERVED_WORD_FONT= new Font(fontFamily,Font.BOLD,size);
    public static final Font IDENTIFIER_FONT= new Font(fontFamily,Font.PLAIN,size);
    public static final Font PROCEDUREUNCTION_NAME_FONT= new Font(fontFamily,Font.BOLD,size);
    public static final Font CONSTANT_FONT= new Font(fontFamily,Font.ITALIC,size);


}
