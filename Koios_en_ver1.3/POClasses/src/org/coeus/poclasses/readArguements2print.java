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

/**
 *
 * @author Jrd
 */

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import org.coeus.parsers.myParser;
import org.coeus.parsers.myScanner;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;
import org.coeus.wizards.WizardsDefinitions;



public class readArguements2print {


//// private String text=null;
// private String objScope=null;
// private String [] error=null;
//
//
//private LinkedList<String> identifiersList=null;
//private LinkedList<String> lexErrorsMessagesList=null;
//private LinkedList<String> lexErrorsList=null;
private LinkedList<String> AllOpersList=null;
private LinkedList<Integer> tokensList=null;
//private LinkedList<String> parseErrorsList=null;
//private LinkedList<String> parseMessageErrorsList=null;
//private LinkedList<String> arraysList=null;
//private LinkedList<String> function_parametersList=null;
//private LinkedList<String> con_varList=null;


public readArguements2print(String text,boolean condition)
{
 myParser parser =new myParser(new myScanner(text,condition));
 if (condition)
   parser.checkCondition();
 else
    parser.checkExpression();

// identifiersList=parser.getIdentifiersList();
 AllOpersList=parser.getAllOpersList();
 tokensList=parser.getTokenList();
// arraysList=parser.getArraysList();
// function_parametersList=parser.getFunctionParametersList();
// con_varList=parser.getConvarList();
}


public LinkedList<PrintStructure> getPrintStructure ()
{
 LinkedList<PrintStructure> list = new LinkedList<PrintStructure>();
 int i=0;

 for (String s:AllOpersList)
  { 
   if (tokensList.get(i)==8)
           list.add(identifiersPrintStructure(s));
   else if (tokensList.get(i)==9)
       list.add(new PrintStructure(s,PrintStructure.IDENTIFIER_FONT,PrintStructure.NUMBER_COLOR));
   else if (tokensList.get(i)==25 || tokensList.get(i)==26 || tokensList.get(i)==50 || tokensList.get(i)==51)
       list.add(new PrintStructure(s,PrintStructure.IDENTIFIER_FONT,PrintStructure.CHARACTER_COLOR));
   else if (tokensList.get(i)>=14  && tokensList.get(i)<=18)
       list.add(new PrintStructure(" "+s+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
    else if (tokensList.get(i)!=27)
       list.add(new PrintStructure(s,PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));
  i++;
 }
return list;
}

 private PrintStructure identifiersPrintStructure (String s)
 { Font font=null;
  Color color=null;

  AllObjectsList caol = AllObjectsList.getAllObjList();
  AllObjects ao =null ;
  ao=caol.SearchByDisplayName(s);
 
  if (ao!=null)
  {
    if (ao.getDispCateg().equalsIgnoreCase(WizardsDefinitions.CONSTANT))
    {
       font=PrintStructure.CONSTANT_FONT;
       if (ao.getDispScope().equalsIgnoreCase(WizardsDefinitions.GLOBAL))
            color=PrintStructure.GLOBAL_COLOR;
       else
            color=PrintStructure.IDENTFIER_COLOR;
    }
    else if (ao.getDispCateg().equalsIgnoreCase(WizardsDefinitions.VARIABLE) ||
              ao.getDispCateg().equalsIgnoreCase(WizardsDefinitions.ARRAY))
    {
       font=PrintStructure.IDENTIFIER_FONT;
       if (ao.getDispScope().equalsIgnoreCase(WizardsDefinitions.GLOBAL))
            color=PrintStructure.GLOBAL_COLOR;
       else
            color=PrintStructure.IDENTFIER_COLOR;
    }
    else
     {
       font=PrintStructure.IDENTIFIER_FONT;
       color=PrintStructure.IDENTFIER_COLOR;
     }
  }
  else
  {
   font=PrintStructure.IDENTIFIER_FONT;
   color=PrintStructure.IDENTFIER_COLOR;
  }
  return new PrintStructure(" "+s+" ",font,color);
 }

}
