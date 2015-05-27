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
 * @author Jrd This code is based on
 * http://www.codecodex.com/wiki/index.php?title=Recursive_descent_parsing
 */
public class tokens {

public static final int underscore = 0;
public static final int period    = 1;
public static final int plusop    = 2;
public static final int minusop   = 3;
public static final int timesop   = 4;
public static final int divideop  = 5;
public static final int lparen    = 6;
public static final int rparen    = 7;
public static final int identifier = 8;
public static final int number    = 9;
public static final int lbracket  = 10;
public static final int rbracket  = 11;
public static final int modop =12;
public static final int comma=13;
public static final int and=14;
public static final int or=15;
public static final int not=16;
public static final int truev=17;
public static final int falsev=18;
public static final int greater=19;
public static final int greater_eq=20;
public static final int lesser=21;
public static final int lesser_eq=22;
public static final int equal=23;
public static final int nonequal=24;
public static final int quotation = 25;
public static final int double_quotation = 26;
public static final int at_sign   = 27;
public static final int char_in_expression   = 50;
public static final int string_in_expression   = 51;

private static String[] spelling = {
    "_", ".", "+", "-", "*", "/", "(", ")",
    parserDefinitions.IDENTIFIER,parserDefinitions.NUMBER ,"[","]","%",",",
    parserDefinitions.AND,parserDefinitions.OR,parserDefinitions.NOT,parserDefinitions.TRUE,
    parserDefinitions.FALSE,">",">=","<","<=","==","!=","\'","\"","@"};

public static String toString (int i) {
    if (i < 0 || i > at_sign)
	return "";
    return spelling[i];
} // toString

} // Token
