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


package org.coeus.parsers;

/**
 *
 * @author Jrd
 */
public class parserDefinitions {

    public static final String IDENTIFIER="IDENTIFIER";
    public static final String NUMBER="NUMBER";
    public static final String AND="AND";
    public static final String NOT="NOT";
    public static final String OR="OR";
    public static final String TRUE="true";
    public static final String FALSE="false";

    ///////////////////ERROR MESSAGES////

    public static final String LEX_MISSING_EQUAL1="The \'=\' character was expected. Instead, the character ";
    public static final String LEX_MISSING_EQUAL2=" was found! You should probably replace it with a \'=\'.";
    public static final String LEX_UNEXPECTED_CHAR1="The character ";
    public static final String LEX_UNEXPECTED_CHAR2=" was found, but it was not expected! You should probably delete it.";
    public static final String LEX_2_DOTS="Two decimal points(.) were found in the same real number!";
    public static final String PAR_WRONG_CHAR1="The character ";
    public static final String PAR_WRONG_CHAR2=" was found ";
    public static final String PAR_WRONG_CHAR3=" This character is in the wrong position and you should probably delete it.";
    public static final String PAR_INPOS=" in position ";
    public static final String RIGHT_PAR_MISSING="Right parenthesis is missing \')\'";
    public static final String LEFT_PAR_MISSING="Left parenthesis is missing \'(\'";
    public static final String RIGHT_BRA_MISSING="Right bracket is missing \']\'";
    public static final String LEFT_BRA_MISSING="Left bracket is missing \'[\'";
    public static final String QUOTATION_MISSING="Quotation mark is missing \'";
    public static final String DOUBLE_QUOTATION_MISSING="Double quotation mark is missing \"";
    public static final String UNEXPECTED_TERM1="The expression or condition is not complete.It ends unexpectedly ";
    public static final String UNEXPECTED_TERM2=" You should probably add one or more terms.";


    
}
