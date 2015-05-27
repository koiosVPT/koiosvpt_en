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
package  org.coeus.parsers;

import java.util.LinkedList;


/**
 *
 * @author Jrd This code is based on
 * http://www.codecodex.com/wiki/index.php?title=Recursive_descent_parsing
 */



public class myParser {


    private myScanner scanner=null;
    private LinkedList<String> parseErrors=null;
    private LinkedList<String> parseMessageErrors=null;
    private LinkedList<String> arraysList=null;
    private LinkedList<String> function_parametersList=null;
    private LinkedList<String> con_varList=null;
    private LinkedList<String> boolArraysList=null;
    private LinkedList<String> boolFunction_parametersList=null;
    private LinkedList<String> boolCon_varList=null;
    private LinkedList<String> consVarsParametersList=null;
    private LinkedList<String> arraysParametersList=null;
    private LinkedList<String> procsFuncsParametersList=null;
        private int lastEntry=0;


public myParser (myScanner scanner) {
    this.scanner = scanner;
    this.parseErrors=new LinkedList();
    this.parseMessageErrors=new LinkedList();
    this.arraysList=new LinkedList();
    this.function_parametersList=new LinkedList();
    this.con_varList=new LinkedList();
    this.boolArraysList=new LinkedList();
    this.boolFunction_parametersList=new LinkedList();
    this.boolCon_varList=new LinkedList();
    this.consVarsParametersList=new LinkedList();
    this.arraysParametersList=new LinkedList();
    this.procsFuncsParametersList=new LinkedList();
} // Parser



public void checkCondition ( ) {
    String value="";

    //start parsing..........
     scanner.getToken( );
    	value =value+ condition( );
        if(scanner.token!=tokens.at_sign)
        {
            addParseMessageError( parserDefinitions.PAR_WRONG_CHAR1+tokens.toString(scanner.token)+parserDefinitions.PAR_WRONG_CHAR2+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!"+parserDefinitions.PAR_WRONG_CHAR3);
            addParseError(tokens.toString(scanner.token));
        }
}//checkConditiion



public void checkExpression ( ) {
    String value="";

    //start parsing..........
     scanner.getToken( );
    	value =value+ expression( );
        if(scanner.token!=tokens.at_sign)
        {
            addParseMessageError( parserDefinitions.PAR_WRONG_CHAR1+tokens.toString(scanner.token)+parserDefinitions.PAR_WRONG_CHAR2+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!"+parserDefinitions.PAR_WRONG_CHAR3);
            addParseError(tokens.toString(scanner.token));
        }
    
} // checkExpression




private String condition()
{
  //    condition = boolterm (OR boolterm)*
   String left="";

   left =boolterm( );
   while (scanner.token == tokens.or ) {
   	scanner.getToken( );
       left +=" "+tokens.toString(tokens.or)+" "+ boolterm( );
    } // while
    return left;
} // condition


private String boolterm ()
{
// boolterm = boolfactor (AND boolfactor)*
   String left="";

   left =boolfactor( );
   while (scanner.token == tokens.and ) {
   	scanner.getToken( );
       left +=" "+tokens.toString(tokens.and)+" "+ boolfactor( );
    } // while
    return left;
}

private String boolfactor ()
{ //boolfactor = true | false | "("condition")" | NOT "("condition")" |
  //id|id "["expression"]"|id"("parameters")" |
  // expression ("<"|"<="|"!"|">"|">="|"=") expression
 String value ="";
   switch (scanner.token) {
   case tokens.falsev:
            value = " "+tokens.toString(tokens.falsev)+" ";
            scanner.getToken( );  // flush false
	    break;
   case tokens.truev:
            value = " "+tokens.toString(tokens.truev)+" ";
            scanner.getToken( );  // flush true
	    break;
	case tokens.lparen:
            scanner.getToken( );//flush left parenthesis
            value = "("+condition( );
	    if (scanner.token != tokens.rparen)
        {
           addParseMessageError( parserDefinitions.RIGHT_PAR_MISSING+tokens.toString(scanner.token)+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
            addParseError("@");
        }
        else value=value+")";
            scanner.getToken( );  // flush right parenthesis
	    break;
    case tokens.not:
        value=value+tokens.toString(tokens.not);
        scanner.getToken(); //flush not
        if (scanner.token!=tokens.lparen)
        {
           addParseMessageError( parserDefinitions.LEFT_PAR_MISSING+tokens.toString(scanner.token)+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
            addParseError("@");
        }
        else
          {
             scanner.getToken();//flush left parenthesis
             value=value+"("+condition();
              if (scanner.token != tokens.rparen)
                {
                   addParseMessageError( parserDefinitions.RIGHT_PAR_MISSING+tokens.toString(scanner.token)+
                            parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
                    addParseError("@");
                }
              else value=value+")";
          }
                   scanner.getToken( );  // flush right parenthesis
        break;
       case tokens.identifier:
            value = scanner.getIdentifier();
            scanner.getToken( );  // flush identifier
            value= value + checkIDIfBOOLArrayOrFunction(value);
            value=value+chooseCase();
       break;
    case tokens.at_sign:
            addParseMessageError( parserDefinitions.UNEXPECTED_TERM1+parserDefinitions.PAR_INPOS+scanner.getColumn()+"!"+parserDefinitions.UNEXPECTED_TERM2);
            addParseError("@");
	    break;
   default:
         value = ExpressionRelatOpExpression();
	    break;
    } // switch
    return value;
} // factor



private String ExpressionRelatOpExpression()
{
 String left = expression();
 switch  (scanner.token) {
     case tokens.equal :
         scanner.getToken();
         left= left + tokens.toString(tokens.equal)+expression();
       break;
     case tokens.nonequal :
         scanner.getToken();
         left= left + tokens.toString(tokens.nonequal)+expression();
       break;
     case tokens.greater :
         scanner.getToken();
         left= left + tokens.toString(tokens.greater)+expression();
       break;
     case tokens.greater_eq :
         scanner.getToken();
         left= left + tokens.toString(tokens.greater_eq)+expression();
       break;
     case tokens.lesser :
         scanner.getToken();
         left= left + tokens.toString(tokens.lesser)+expression();
       break;
     case tokens.lesser_eq :
         scanner.getToken();
         left= left + tokens.toString(tokens.lesser_eq)+expression();
       break;
     default:
         String s="";
         if (scanner.token==tokens.at_sign)
             s=parserDefinitions.UNEXPECTED_TERM2+parserDefinitions.PAR_INPOS+scanner.getColumn()+"!"+parserDefinitions.UNEXPECTED_TERM2;
         else
             s=parserDefinitions.PAR_WRONG_CHAR1+tokens.toString(scanner.token)+parserDefinitions.PAR_WRONG_CHAR2+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!"+parserDefinitions.PAR_WRONG_CHAR3;

         addParseMessageError(s);
         addParseError(tokens.toString(scanner.token));
	    break;
     }//switch
return left;
}


private String expression ( ) {
    //    expression = (ε|"+"|"-") term { ( "+" | "-" ) term }*
     String left="";
     if (scanner.token==tokens.plusop)
        {left=left+"+";scanner.getToken(); }
    else if (scanner.token==tokens.minusop)
        {left=left+"-"; scanner.getToken(); }

   left = left+term( );
  while (scanner.token == tokens.plusop ||
   scanner.token == tokens.minusop ) {
    int saveToken = scanner.token;
	scanner.getToken( );
	switch (saveToken) {
	    case tokens.plusop:
            left +="+" + term( );
		break;
	    case tokens.minusop:
            left += "-"+term( );
		break;
	} // switch
    } // while
    return left;
} // expression


private String term ( ) {
    //    term = factor { ( "*" | "/" |"%") factor }*
    String left = factor( );
while (scanner.token == tokens.timesop ||  scanner.token == tokens.divideop ||
	   scanner.token == tokens.modop ) {
	int saveToken = scanner.token;
	scanner.getToken( );
	switch (saveToken) {
	    case tokens.timesop:
            left =left+"*"+ factor( );
		break;
	    case tokens.divideop:
            left =left+"/"+ factor( );
		break;
        case tokens.modop:
            left =left+"%"+ factor( );
		break;
	} // switch
    } // while
    return left;
} // term


private String factor ( ) {
    //    factor  = number | "(" expression ")" |id|id"["expression"]"| id "(" parameters ")"
   String value ="";
   switch (scanner.token) {
   case tokens.number:
            value = scanner.getNumber( );
            scanner.getToken( );  // flush number
	    break;
   case tokens.quotation:
            value ="\'"+getCharExpression();
	    break;
   case tokens.double_quotation:
            value ="\""+getStringExpression();
	    break;
   case tokens.identifier:
            value = scanner.getIdentifier();
            scanner.getToken( );  // flush identifier
            value= value + checkIDIfArrayOrFunction(value);
	    break;
	case tokens.lparen:
            scanner.getToken( );//flush left parenthesis
            value = "("+expression( );
	    if (scanner.token != tokens.rparen)
        {
        addParseMessageError( parserDefinitions.RIGHT_PAR_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
            addParseError("@");
        }
        else value=value+")";
            scanner.getToken( );  // flush right parenthesis;
	    break;
    case tokens.at_sign:
            addParseMessageError( parserDefinitions.UNEXPECTED_TERM1+parserDefinitions.PAR_INPOS+scanner.getColumn()+"!"+parserDefinitions.UNEXPECTED_TERM2);
            addParseError("@");
        break;
     default:
         addParseMessageError( parserDefinitions.PAR_WRONG_CHAR1+tokens.toString(scanner.token)+parserDefinitions.PAR_WRONG_CHAR2+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!"+parserDefinitions.PAR_WRONG_CHAR3);
            addParseError(tokens.toString(scanner.token));
	    break;
    } // switch
    return value;
} // factor


private String getCharExpression()
{
 String value="";

value=value+scanner.getLastChar_ch();// add char after first  ' inside  ' ' to the expression
String temp=""+scanner.getLastChar_ch();//store it to temp for later use
scanner.setLastChar_ch(scanner.get()); //"Dissapear" the char inside ' ' from the scanner
scanner.getToken();// and get next char to check if it is "'"-it shoule be

 if (scanner.token!=tokens.quotation)
 {
     addParseMessageError(parserDefinitions.QUOTATION_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
     addParseError("@");
 }
 else
    {
     value=value+"\'";
     scanner.getAllOpersList().removeLast();//remove "'"
     scanner.getTokenList().removeLast();//and its token
     scanner.getAllOpersList().add(temp);// add char in expression -inside ''
     scanner.getTokenList().add(tokens.char_in_expression);//and its token
     scanner.getAllOpersList().add("\'");// add "'" ,removed previously
     scanner.getTokenList().add(tokens.quotation);//and its token
     }
  scanner.getToken();//flush "'"
 return value;
}

private String getStringExpression()
{
 String value="";
 char c;

String temp=""+scanner.getLastChar_ch();//add first char after " inside  " " to the expression

//Get all chars until " or @-end of line
c=scanner.get();
while (c!='"' && c!='@')
{temp=temp+c;
c=scanner.get();}

scanner.setLastChar_ch(c); //"Dissapear" the string inside " "  from the scanner
scanner.getToken();// and get next char to check if it is "\""-it shoule be

 if (scanner.token!=tokens.double_quotation)
 {
     addParseMessageError(parserDefinitions.DOUBLE_QUOTATION_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
     addParseError("@");
 }
 else
    {
     value=temp+"\"";
     scanner.getAllOpersList().removeLast();//remove "\""
     scanner.getTokenList().removeLast();//and its token
     scanner.getAllOpersList().add(temp);// add string in expression -inside ""
     scanner.getTokenList().add(tokens.string_in_expression);//and its token
     scanner.getAllOpersList().add("\"");// add "\"" ,removed previously
     scanner.getTokenList().add(tokens.double_quotation);//and its token
     }
  scanner.getToken();//flush "'"
 return value;
}


public String checkIDIfArrayOrFunction(String name)
{
String value="";

 switch(scanner.token) {
     case tokens.lbracket ://Check if array element
                scanner.getToken();//flush leftbracket
                value=value+"["+expression();
                if (scanner.token != tokens.rbracket)
                {
                 addParseMessageError( parserDefinitions.RIGHT_BRA_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
                 addParseError("@");
                }
                else {
                    value=value+"]";
                           ///values is an array - add it to the arraysList
                           this.arraysList.add(name+value);}
                scanner.getToken( );
                ///Check if concers 2-dimensional array array[][]
                if (scanner.token==tokens.lbracket)//Check if array element
                {
                scanner.getToken();//flush leftbracket
                value=value+"["+expression();
                 if (scanner.token != tokens.rbracket)
                 {
                  addParseMessageError( parserDefinitions.RIGHT_BRA_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
                  addParseError("@");
                 }
                else {
                    value=value+"]";
                           ///values is an array -remove the last one beacause iat has only
                           //the first dimension and add it to the arraysList
                           this.arraysList.removeLast();
                           this.arraysList.add(name+value);}
                scanner.getToken( );
                }
     break;
     case tokens.lparen ://Check if function parameters
                scanner.getToken();//flush leftparenthesis
                value=value+"("+parameters();
                 if (scanner.token != tokens.rparen)
                 {
                 addParseMessageError( parserDefinitions.RIGHT_PAR_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
                 addParseError("@");
                 }
                else {
                    value=value+")";
                           ///values is a function with parameters - add it to the Functinon_parametersList
                           this.function_parametersList.add(name+value);}
                scanner.getToken( );
      break;
     default:
            //values is an identifier, ie constant or variable- add it to the con_varList
            this.con_varList.add(name);
     break;
 }//switch
return value;
}


///parameters ver.1
///this functions checks if there is a valis expression between ()

//public String parameters ()
////  parameters     = ε | parametersitem {,parametrsitem}*
//{
//  if (scanner.token==tokens.rparen)
//     return "";
//  else
//  {
//  String left=expression();
//  while (scanner.token == tokens.comma)
//  {
//    scanner.getToken( );
//    left =left+", "+ expression( );
//  } //while
//  return left;
//  }//else
//}//parameters



/////parameters ver.2
/////this functions ignores all characters between ()
//public String parameters ()
//{
// int numofRightPar=-1;
// char c;
// String left="";
//
// c=scanner.getLastChar_ch();
// if(c=='(') numofRightPar--;
// if(c==')') numofRightPar++;
//
//  while (c!= ')' || numofRightPar!=0 )
//  {
//      left=left+c;
//      c=scanner.get();
//      if(c=='(') numofRightPar--;
//      if(c==')') numofRightPar++;
//
//  } //while
// scanner.setLastChar_ch(c);
// scanner.getToken();
//  return left;
//
//}//parameters


//parameters ver.3
public String parameters ()
//  parameters     = ε | parametersitem {,parametrsitem}*
{
  if (scanner.token==tokens.rparen)
     return "";
  else
  {
  String left=getParameter();
  while (scanner.token == tokens.comma)
  {
    scanner.getToken( );//flush ,
    left =left+", "+ getParameter();
  } //while
  return left;
  }//else
}//parameters


public String getParameter()
{
String parameter="";

//IF PARAMETER BEGINS WITH TRUE,FALSE , NOT
if (scanner.token==tokens.truev || scanner.token==tokens.falsev || scanner.token==tokens.not)
     parameter=condition();
//IF PARAMETER BEGINS WITH IDENTIFIER NUMBER,"'",-
 else if (scanner.token==tokens.number || scanner.token==tokens.quotation
     || scanner.token==tokens.double_quotation   || scanner.token==tokens.minusop)
{
    parameter=expression();
    //if ==,!=,<,<=,>,>= follows then its part of expression relatOp expression
     if (scanner.token==tokens.equal || scanner.token==tokens.nonequal
       || scanner.token==tokens.greater || scanner.token==tokens.greater_eq
       || scanner.token==tokens.lesser || scanner.token==tokens.lesser_eq )
       {
        parameter=parameter+checkRelatOp();
        //if AND,Or follows then its part of CONDITION
        if (scanner.token==tokens.and || scanner.token==tokens.or)
           {parameter=parameter+checkLogicOp();}
       }
}
//IF PARAMETER BEGINS WITH IDENTIFIER
else if (scanner.token==tokens.identifier) {
            parameter = scanner.getIdentifier();
            scanner.getToken( );  // flush identifier
            parameter= parameter + checkParameterIfArrayOrFunction(parameter);
            /////////////////23/02/2012/////parameter= parameter + checkIDIfBOOLArrayOrFunction(parameter);
            //// an IDE parameter of a function in a condition is not nececeraly boolean
            //if +,-,/,*,% follows then its part of expression
            if (scanner.token==tokens.plusop || scanner.token==tokens.minusop
            || scanner.token==tokens.timesop || scanner.token==tokens.divideop
            || scanner.token==tokens.modop )
            {

              parameter=parameter+checkArithmOp();
             //if ==,!=,<,<=,>,>= follows then its part of expression relatOp expression
              if (scanner.token==tokens.equal || scanner.token==tokens.nonequal
               || scanner.token==tokens.greater || scanner.token==tokens.greater_eq
               || scanner.token==tokens.lesser || scanner.token==tokens.lesser_eq )
                 {
                 parameter=parameter+checkRelatOp();
                //if AND,Or follows then its part of CONDITION
                 if (scanner.token==tokens.and || scanner.token==tokens.or)
                   {parameter=parameter+checkLogicOp();}
                  }
            }
            //if ==,!=,<,<=,>,>= follows then its part of expression relatOp expression
            else if (scanner.token==tokens.equal || scanner.token==tokens.nonequal
               || scanner.token==tokens.greater || scanner.token==tokens.greater_eq
               || scanner.token==tokens.lesser || scanner.token==tokens.lesser_eq )
               {
                parameter=parameter+checkRelatOp();
                //if AND,Or follows then its part of CONDITION
                if (scanner.token==tokens.and || scanner.token==tokens.or)
                   {parameter=parameter+checkLogicOp();}
               }
                //if AND,Or follows then its part of CONDITION
            else if (scanner.token==tokens.and || scanner.token==tokens.or)
              { parameter=parameter+checkLogicOp();}

        }
//IF PARAMETER BEGINS WITH (
 else if (scanner.token==tokens.lparen)
{
 parameter=parameter+"(";
 scanner.getToken();
 parameter=parameter+getParameter();
 if (scanner.token != tokens.rparen)
     {
      addParseMessageError( parserDefinitions.RIGHT_PAR_MISSING+
        parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
      addParseError("@");
     }

  else
      parameter=parameter+")";
 scanner.getToken( );  // flush right parenthesis;
}
return parameter;
}


public String checkParameterIfArrayOrFunction(String name)
{
String value="";

 switch(scanner.token) {
     case tokens.lbracket ://Check if array element
                scanner.getToken();//flush leftbracket
                value=value+"["+expression();
                if (scanner.token != tokens.rbracket)
                {
                 addParseMessageError( parserDefinitions.RIGHT_BRA_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
                 addParseError("@");
                }
                else {
                    value=value+"]";
                           ///values is an array - add it to the arraysList
                          // this.arraysList.add(name+value);}
                           this.arraysParametersList.add(name+value);}
                scanner.getToken( );
                ///Check if concers 2-dimensional array array[][]
                if (scanner.token==tokens.lbracket)//Check if array element
                {
                scanner.getToken();//flush leftbracket
                value=value+"["+expression();
                 if (scanner.token != tokens.rbracket)
                 {
                  addParseMessageError( parserDefinitions.RIGHT_BRA_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
                  addParseError("@");
                 }
                else {
                    value=value+"]";
                           ///values is an array -remove the last one beacause iat has only
                           //the first dimension and add it to the arraysList
                           //this.arraysList.removeLast();
                           //this.arraysList.add(name+value);}
                           this.arraysParametersList.removeLast();
                           this.arraysParametersList.add(name+value);}
                    scanner.getToken( );
                }
     break;
     case tokens.lparen ://Check if function parameters
                scanner.getToken();//flush leftparenthesis
                value=value+"("+parameters();
                 if (scanner.token != tokens.rparen)
                 {
                 addParseMessageError( parserDefinitions.RIGHT_PAR_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
                 addParseError("@");
                 }
                else {
                    value=value+")";
                           ///values is a function with parameters - add it to the Functinon_parametersList
                           //this.function_parametersList.add(name+value);}
                           this.procsFuncsParametersList.add(name+value);}

                scanner.getToken( );
      break;
     default:
            //values is an identifier, ie constant or variable- add it to the con_varList
            //this.con_varList.add(name);
            this.consVarsParametersList.add(name);
     break;
 }//switch
return value;
}


private String checkArithmOp()
{
String value="";

removeLastEntry();
value=value+tokens.toString(scanner.token);
scanner.getToken();//flush + or /
value=value+expression();
return value;
}


private String checkRelatOp()
{
String value="";
value=value+RelatOpExpression();

return value;
}

private String checkLogicOp()
{
String value="";
 value=value+tokens.toString(scanner.token)+" ";
 scanner.getToken();//flush + or /
 value=value+condition();
return value;
}


private void addParseMessageError(String text)
{this.parseMessageErrors.add(text);}

private void addParseError(String text)
{this.parseErrors.add(text);}


//////////Getters

public  LinkedList<String> getFunctionParametersList()
{return this.function_parametersList; }

public  LinkedList<String> getConvarList()
{return this.con_varList; }

public  LinkedList<String> getArraysList()
{return this.arraysList; }

public  LinkedList<String> getParseErrorsList()
{return this.parseErrors; }

public  LinkedList<String> getParseMessageErrorsList()
{return this.parseMessageErrors; }

public  LinkedList<String> getLexErrorsList()
{return this.scanner.getLexErrorsList(); }

public  LinkedList<String> getLexMessageErrorsList()
{return this.scanner.getLexErrorsMessagesList(); }

public  LinkedList<String> getIdentifiersList()
{return this.scanner.getIdentifiersList(); }

public  LinkedList<String> getAllOpersList()
{return this.scanner.getAllOpersList(); }

public  LinkedList<Integer> getTokenList()
{return this.scanner.getTokenList(); }

    public LinkedList<String> getArraysParametersList() {
        return arraysParametersList;
    }

    public void setArraysParametersList(LinkedList<String> arraysParametersList) {
        this.arraysParametersList = arraysParametersList;
    }

    public LinkedList<String> getConsVarsParametersList() {
        return consVarsParametersList;
    }

    public void setConsVarsParametersList(LinkedList<String> consVarsParametersList) {
        this.consVarsParametersList = consVarsParametersList;
    }

    public LinkedList<String> getProcsFuncsParametersList() {
        return procsFuncsParametersList;
    }

    public void setProcsFuncsParametersList(LinkedList<String> procsFuncsParametersList) {
        this.procsFuncsParametersList = procsFuncsParametersList;
    }




///////////////////BOOLEAN MODIFICATIONS////////////////

public  LinkedList<String> getBoolFunctionParametersList()
{return this.boolFunction_parametersList; }

public  LinkedList<String> getBoolConvarList()
{return this.boolCon_varList; }

public  LinkedList<String> getBoolArraysList()
{return this.boolArraysList; }



public String checkIDIfBOOLArrayOrFunction(String name)
{
String value="";

 switch(scanner.token) {
     case tokens.lbracket ://Check if array element
                scanner.getToken();//flush leftbracket
                value=value+"["+expression();
                if (scanner.token != tokens.rbracket)
                {
                 addParseMessageError( parserDefinitions.RIGHT_BRA_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
                 addParseError("@");
                }
                else {
                    value=value+"]";
                           ///values is an array - add it to the arraysList
                          // this.arraysList.add(name+value);
                    this.boolArraysList.add(name+value);
                    lastEntry=1;
                }
                scanner.getToken( );
                ///Check if concers 2-dimensional array array[][]
                if (scanner.token==tokens.lbracket)//Check if array element
                {
                scanner.getToken();//flush leftbracket
                value=value+"["+expression();
                 if (scanner.token != tokens.rbracket)
                 {
                  addParseMessageError( parserDefinitions.RIGHT_BRA_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
                  addParseError("@");
                 }
                else {
                    value=value+"]";
                           ///values is an array -remove the last one beacause iat has only
                           //the first dimension and add it to the arraysList
                          // this.arraysList.removeLast();
                          // this.arraysList.add(name+value);
                           this.boolArraysList.removeLast();
                           this.boolArraysList.add(name+value);
                           lastEntry=1;
                }
                scanner.getToken( );
                }
     break;
     case tokens.lparen ://Check if function parameters
                scanner.getToken();//flush leftparenthesis
                value=value+"("+parameters();
                 if (scanner.token != tokens.rparen)
                 {
                 addParseMessageError( parserDefinitions.RIGHT_PAR_MISSING+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!");
                 addParseError("@");
                 }
                else {
                    value=value+")";
                           ///values is a function with parameters - add it to the Functinon_parametersList
                           //this.function_parametersList.add(name+value);
                  this.boolFunction_parametersList.add(name+value);
                  lastEntry=2;}
                scanner.getToken( );
      break;
     default:
            //values is an identifier, ie constant or variable- add it to the con_varList
           // this.con_varList.add(name);
                     this.boolCon_varList.add(name);
                     lastEntry=3;
     break;
 }//switch
return value;
}

public String chooseCase()
{
 String value="";
 if (scanner.token==tokens.plusop || scanner.token==tokens.minusop 
 ||scanner.token==tokens.timesop || scanner.token==tokens.divideop || scanner.token==tokens.modop ) {
          removeLastEntry();
          value=value+tokens.toString(scanner.token);
          scanner.getToken();//flush + ,-,*,/ or %
          value=value+expression()+RelatOpExpression();
        }
  else if (scanner.token==tokens.equal || scanner.token==tokens.nonequal || scanner.token==tokens.greater ||
 scanner.token==tokens.greater_eq || scanner.token==tokens.lesser || scanner.token==tokens.lesser_eq ){  //relational operator
         // value=value+tokens.toString(scanner.token);
         // scanner.getToken();//flush *,/ or %
          removeLastEntry();
          value=value+RelatOpExpression();
        }
 else if (scanner.token==tokens.and ) {
          value=value+tokens.toString(scanner.token)+" ";
          scanner.getToken();//flush + or /
          value=value+boolfactor();
        }
else if (scanner.token==tokens.or ) {
          value=value+tokens.toString(scanner.token)+" ";
          scanner.getToken();//flush + or /
          value=value+boolterm();
        }

 return value;
}


private String RelatOpExpression()
{
 String left = "";
 switch  (scanner.token) {
     case tokens.equal :
         scanner.getToken();
         left= left + tokens.toString(tokens.equal)+expression();
       break;
     case tokens.nonequal :
         scanner.getToken();
         left= left + tokens.toString(tokens.nonequal)+expression();
       break;
     case tokens.greater :
         scanner.getToken();
         left= left + tokens.toString(tokens.greater)+expression();
       break;
     case tokens.greater_eq :
         scanner.getToken();
         left= left + tokens.toString(tokens.greater_eq)+expression();
       break;
     case tokens.lesser :
         scanner.getToken();
         left= left + tokens.toString(tokens.lesser)+expression();
       break;
     case tokens.lesser_eq :
         scanner.getToken();
         left= left + tokens.toString(tokens.lesser_eq)+expression();
       break;
     default:
          String s="";
         if (scanner.token==tokens.at_sign)
             s=parserDefinitions.UNEXPECTED_TERM1+parserDefinitions.PAR_INPOS+scanner.getColumn()+"!"+parserDefinitions.UNEXPECTED_TERM2;
         else
             s=parserDefinitions.PAR_WRONG_CHAR1+tokens.toString(scanner.token)+parserDefinitions.PAR_WRONG_CHAR2+
                    parserDefinitions.PAR_INPOS+scanner.getColumn()+"!"+parserDefinitions.PAR_WRONG_CHAR3;

         addParseMessageError(s);
         addParseError(tokens.toString(scanner.token));
        //scanner.token=tokens.at_sign;// "@";//scanner.getToken();
	    break;
     }//switch
return left;
}

public void removeLastEntry()
{
 String s;
 switch (lastEntry)
 {
   case 1:
    s=this.boolArraysList.removeLast();
    this.arraysList.add(s);
   break;
   case 2:
    s=this.boolFunction_parametersList.removeLast();
    this.function_parametersList.add(s);
   break;
   case 3:
    s=this.boolCon_varList.removeLast();
    this.con_varList.add(s);
   break;
 }
}




} // class Parser
