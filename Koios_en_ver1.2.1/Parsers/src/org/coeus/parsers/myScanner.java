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
 * @author Jrd This code is based on
 * http://www.codecodex.com/wiki/index.php?title=Recursive_descent_parsing
 */


import java.util.LinkedList;


public class myScanner {

private char ch = ' ';


private String intValue = "";
private String line="";
private String identifier ="";


public int token=-1;
private int column = -1;

private boolean condition;

private LinkedList<String> identifiersList=null;
private LinkedList<String> lexErrorsMessagesList=null;
private LinkedList<String> lexErrorsList=null;
private LinkedList<String> AllOpersList=null;
private LinkedList<Integer> tokensList=null;


public myScanner (String inText,boolean is_condition) {
    this.line=inText.trim();
    identifiersList=new LinkedList();
    lexErrorsMessagesList=new LinkedList();
    lexErrorsList=new LinkedList();
    AllOpersList=new  LinkedList();
    tokensList=new  LinkedList();
    this.condition=is_condition;
} // Scanner

public int getToken()
{
  //if (condition)
     return getConditionToken ( ) ;
  //else
    // return getExpressionToken ( ) ;
}


public int getExpressionToken ( ) {

    while (Character.isWhitespace(ch))
	ch = get( );
    if ((Character.isLetter(ch)) || (ch=='_')) {
    identifier= createIdentifier();
      //Check if and,or,not..
	  if (identifier.equalsIgnoreCase(tokens.toString(tokens.and)))
           token = tokens.and;
      else if (identifier.equalsIgnoreCase(tokens.toString(tokens.not)))
          token = tokens.not;
      else if (identifier.equalsIgnoreCase(tokens.toString(tokens.or)))
          token = tokens.or;
      else if (identifier.equalsIgnoreCase(tokens.toString(tokens.truev)))
          token = tokens.truev;
      else if (identifier.equalsIgnoreCase(tokens.toString(tokens.falsev)))
          token = tokens.falsev;
      else
          token = tokens.identifier;
    AllOpersList.add(identifier);
    tokensList.add(token);
    }
    else if (Character.isDigit(ch)) {
	intValue = createNumber( );
	token = tokens.number;
    AllOpersList.add(intValue);
    tokensList.add(token);
    }
    else {
	switch (ch) {

	    case '+' : ch = get( );
		token = tokens.plusop;
    AllOpersList.add("+");
    tokensList.add(token);
		break;

	    case '-' : ch = get( );
		token = tokens.minusop;
    AllOpersList.add("-");
    tokensList.add(token);
		break;

	    case '*' : ch = get( );
		token = tokens.timesop;
    AllOpersList.add("*");
    tokensList.add(token);
		break;

	    case '/' : ch = get( );
		token = tokens.divideop;
    AllOpersList.add("/");
    tokensList.add(token);
		break;


	    case '(' : ch = get( );
		token = tokens.lparen;
    AllOpersList.add("(");
    tokensList.add(token);
		break;

	    case ')' : ch = get( );
		token = tokens.rparen;
    AllOpersList.add(")");
    tokensList.add(token);
		break;

        case '[': ch = get( );
		token = tokens.lbracket;
    AllOpersList.add("[");
    tokensList.add(token);
		break;

        case ']' : ch = get( );
		token = tokens.rbracket;
    AllOpersList.add("]");
    tokensList.add(token);
		break;

        case '%' : ch = get( );
		token = tokens.modop;
    AllOpersList.add("%");
    tokensList.add(token);
		break;

        case ',' : ch = get( );
		token = tokens.comma;
    AllOpersList.add(",");
    tokensList.add(token);
		break;

        case '@' : ch = get( );
		token = tokens.at_sign;
    AllOpersList.add("@");
    tokensList.add(token);
		break;

        default :
            lexErrorsMessagesList.add( parserDefinitions.LEX_UNEXPECTED_CHAR1+Character.toString(ch)+parserDefinitions.LEX_UNEXPECTED_CHAR2 );
            lexErrorsList.add(Character.toString(ch));
            ch=get();
		break;
      } // switch
    } // if
  return token;
} // getToken




public int getConditionToken ( ) {
     
    while (Character.isWhitespace(ch))
	ch = get( );
    if ((Character.isLetter(ch)) || (ch=='_')) {
    identifier= createIdentifier();
      //Check if and,or,not..
	  if (identifier.equalsIgnoreCase(tokens.toString(tokens.and)))
           token = tokens.and;
      else if (identifier.equalsIgnoreCase(tokens.toString(tokens.not)))
          token = tokens.not;
      else if (identifier.equalsIgnoreCase(tokens.toString(tokens.or)))
          token = tokens.or;
      else if (identifier.equalsIgnoreCase(tokens.toString(tokens.truev)))
          token = tokens.truev;
      else if (identifier.equalsIgnoreCase(tokens.toString(tokens.falsev)))
          token = tokens.falsev;
      else
          token = tokens.identifier;
    AllOpersList.add(identifier);
    tokensList.add(token);
    }
    else if (Character.isDigit(ch)) {
	intValue = createNumber( );
	token = tokens.number;
    AllOpersList.add(intValue);
    tokensList.add(token);
    }
    else {
	switch (ch) {

            case '<' : ch = get( );
                   if (ch=='=')
                   {
                    ch=get();
                    token=tokens.lesser_eq;
                    AllOpersList.add("<=");
                   }
                   else
                   {
		             token = tokens.lesser;
                     AllOpersList.add("<");
                   }
             tokensList.add(token);
	     	break;


               case '>' : ch = get( );
                   if (ch=='=')
                   {
                    ch=get();
                    token=tokens.greater_eq;
                    AllOpersList.add(">=");
                   }
                   else
                   {
		             token = tokens.greater;
                     AllOpersList.add(">");
                   }
             tokensList.add(token);
	     	break;


            case '=' : ch = get( );
                   if (ch=='=')
                   {
                    ch=get();
                    token=tokens.equal;
                    AllOpersList.add("==");
                    tokensList.add(token);
                   }
                   else
                   {
                     lexErrorsMessagesList.add( parserDefinitions.LEX_MISSING_EQUAL1+Character.toString(ch)+parserDefinitions.LEX_MISSING_EQUAL2);
                     lexErrorsList.add(Character.toString(ch));
                   }
	     	break;

             case '!' : ch = get( );
                   if (ch=='=')
                   {
                    ch=get();
                    token=tokens.nonequal;
                    AllOpersList.add("!=");
                    tokensList.add(token);
                   }
                   else
                     lexErrorsMessagesList.add( parserDefinitions.LEX_MISSING_EQUAL1+Character.toString(ch)+parserDefinitions.LEX_MISSING_EQUAL2);
                     lexErrorsList.add(Character.toString(ch));
	          break;


	    case '+' : ch = get( );
		token = tokens.plusop;
    AllOpersList.add("+");
    tokensList.add(token);
		break;

	    case '-' : ch = get( );
		token = tokens.minusop;
    AllOpersList.add("-");
    tokensList.add(token);
		break;

	    case '*' : ch = get( );
		token = tokens.timesop;
    AllOpersList.add("*");
    tokensList.add(token);
		break;

	    case '/' : ch = get( );
		token = tokens.divideop;
    AllOpersList.add("/");
    tokensList.add(token);
		break;


	    case '(' : ch = get( );
		token = tokens.lparen;
    AllOpersList.add("(");
    tokensList.add(token);
		break;

	    case ')' : ch = get( );
		token = tokens.rparen;
    AllOpersList.add(")");
    tokensList.add(token);
		break;

        case '[': ch = get( );
		token = tokens.lbracket;
    AllOpersList.add("[");
    tokensList.add(token);
		break;

        case ']' : ch = get( );
		token = tokens.rbracket;
    AllOpersList.add("]");
    tokensList.add(token);
		break;

        case '%' : ch = get( );
		token = tokens.modop;
    AllOpersList.add("%");
    tokensList.add(token);
		break;

        case ',' : ch = get( );
		token = tokens.comma;
    AllOpersList.add(",");
    tokensList.add(token);
		break;

        case '\'' : ch = get( );
		token = tokens.quotation;
    AllOpersList.add("'");
    tokensList.add(token);
		break;
        case '\"' : ch = get( );
		token = tokens.double_quotation;
    AllOpersList.add("\"");
    tokensList.add(token);
		break;

        case '@' : ch = get( );
		token = tokens.at_sign;
    AllOpersList.add("@");
    tokensList.add(token);
		break;

        default : 
            lexErrorsMessagesList.add( parserDefinitions.LEX_UNEXPECTED_CHAR1+Character.toString(ch)+parserDefinitions.LEX_UNEXPECTED_CHAR2);
            lexErrorsList.add(Character.toString(ch));
            ch=get();
		break;
      } // switch
    } // if
  return token;
} // getToken


public char getLastChar_ch()
{return this.ch;}

public void setLastChar_ch(char c)
{this.ch=c;}



public int getColumn ( ) {
    return this.column;
} // number


public String getNumber ( ) {
    return this.intValue;
} // number


public String getIdentifier ( ) {
    return this.identifier;
} // identifier


public String createIdentifier ( ) {
    String s="";

    do {
	s = s+ch;
	ch = get( );
    } while ((Character.isLetter(ch))|| (Character.isDigit(ch))||(ch=='_'));
    s=s.toUpperCase();
    if (s.equalsIgnoreCase("true")) s="true";
    if (s.equalsIgnoreCase("false")) s="false";
    //if (s.equalsIgnoreCase("")) s="";
    identifiersList.add(s);
    return s;
} // getNumber


private String createNumber ( ) {
    String s="";
    int dot_count=0;
    do {
	s = s+ch;
	ch = get( );
    if (ch=='.') dot_count++;
    if (dot_count>1)
    {
        lexErrorsMessagesList.add(parserDefinitions.LEX_2_DOTS+s);
        lexErrorsList.add(s);
        ch='@';
    }
    } while ((Character.isDigit(ch)) || ch=='.');
    return s;
} // getNumber


public char get ( ) {
    column++;

    if (column < line.length()) {
	if (line == null)
	   return '@';
    return line.charAt(column);
	}
    else
        return '@';
} // get

//Getters
public  LinkedList<String> getIdentifiersList()
{return this.identifiersList;}

public LinkedList <String> getLexErrorsList()
{return this.lexErrorsList;}

public LinkedList <String> getLexErrorsMessagesList()
{return this.lexErrorsMessagesList;}

public  LinkedList<String> getAllOpersList()
{return this.AllOpersList;}

public  LinkedList<Integer> getTokenList()
{return this.tokensList;}

} // Scanner



