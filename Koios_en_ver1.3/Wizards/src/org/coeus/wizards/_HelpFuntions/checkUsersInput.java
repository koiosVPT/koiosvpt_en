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

import java.util.LinkedList;
import org.coeus.parsers.myParser;
import org.coeus.parsers.myScanner;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;
import org.coeus.wizards.ReservedWords;
import org.coeus.wizards.WizardsDefinitions;

/**
 *
 * @author Jrd
 */
public class checkUsersInput {

// private  createArrayIndexPanel cAI=null;


 private String text=null;
 private String objScope=null;
 //private getArrayElementsList gAEL=null;
//private getParametersList gPL= null;
// private getVariablesList gVL = null;
 private String [] error=null;


private LinkedList<String> identifiersList=null;
private LinkedList<String> lexErrorsMessagesList=null;
private LinkedList<String> lexErrorsList=null;
private LinkedList<String> AllOpersList=null;
private LinkedList<Integer> tokensList=null;
private LinkedList<String> parseErrorsList=null;
private LinkedList<String> parseMessageErrorsList=null;
private LinkedList<String> arraysList=null;
private LinkedList<String> function_parametersList=null;
private LinkedList<String> con_varList=null;
private LinkedList<String> boolArraysList=null;
private LinkedList<String> boolFunction_parametersList=null;
private LinkedList<String> boolCon_varList=null;



public checkUsersInput(String input,String iObjScope,boolean condition)
{
  //this.cAI=(createArrayIndexPanel)in_cAI;

  this.text=input;
  this.objScope=iObjScope;
  error=new String[2];

 // this.gAEL = new getArrayElementsList(this.objScope,"all_datatypes");
 // this.gPL  = new getParametersList(this.objScope,"all_datatypes");
 // this.gVL = new getVariablesList(this.objScope,"all_datatypes");

 myParser parser =new myParser(new myScanner(text,condition));
 if (condition)
   parser.checkCondition();
 else
   parser.checkExpression();

 identifiersList=parser.getIdentifiersList();
 lexErrorsMessagesList=parser.getLexMessageErrorsList();
 lexErrorsList=parser.getLexErrorsList();
 AllOpersList=parser.getAllOpersList();
 tokensList=parser.getTokenList();
 parseErrorsList=parser.getParseErrorsList();
 parseMessageErrorsList=parser.getParseMessageErrorsList();
 arraysList=parser.getArraysList();
 function_parametersList=parser.getFunctionParametersList();
 con_varList=parser.getConvarList();
 boolArraysList=parser.getBoolArraysList();
 boolFunction_parametersList=parser.getBoolFunctionParametersList();
 boolCon_varList=parser.getBoolConvarList();
}


public boolean checkExpression()
{
if (!lexErrorsMessagesList.isEmpty())
{
error[0]=lexErrorsMessagesList.get(0);
error[1]=lexErrorsList.get(0);
return false;
}
else if (!parseMessageErrorsList.isEmpty())
{
error[0]=parseMessageErrorsList.get(0);
error[1]=parseErrorsList.get(0);
 return false;
}

 if (!identifiersList.isEmpty())
{String invalid_id="";
 String reserved_id="";

     for (String s:identifiersList)
     {
        if (!checkIdentifier(s))
        {
            if (!checkIdentifierReservedWord(s))
           {
              if (!checkIdentifierReservedWordCondition(s))
              reserved_id=s;
            }
           else
              invalid_id=s;
//              reserved_id=s;
//           else
//              invalid_id=s;
        }
     }
     ////if reserved word
     if (!reserved_id.isEmpty())
     {
      error[0]="The term "+reserved_id+" is used for other purposes (reserved word)and cannot be\n" +
              "used in an arithmetic expression! You should delete it from the expression.";
      error[1]=reserved_id;
      return false;
     }
     //if unknown identifier 
     if (!invalid_id.isEmpty())
     {
       error[0]= "The term "+invalid_id+" is not declared or is out of scope and cannot be\n" +
              "used in this arithmetic expression! You should delete it from the expression.";
       error[1]=invalid_id;
       return false;
     }
 }
 
 if(!checkConstantOrVariable()) return false;
 if(!checkArray()) return false;
 if(!checkFunction()) return false;

  return true;
}

private boolean checkConstantOrVariable()
{
    AllObjects aoi=null;
    String paramType=null;
    AllObjectsList aol = AllObjectsList.getAllObjList();

  for (String s:con_varList)
  {     System.out.println("\n\n\n" + s+"\n\n\n");
     aoi=null;
     paramType=null;
     paramType=checkDispParameters(s,aol);
     if (paramType==null)
     {
     aoi=aol.SearchByDisplayName_4ConstantOrVariable(s,this.objScope);
     if (aoi==null)
       {
         error[0]="The term "+s+" is not declared as a constant or a variable in the program or is out of scope\n" +
              "and cannot be used as a constant or a variable in this arithmetic expression! You should delete it from the expression.";
         error[1]=s;
         return false;
        }
     else
     {
      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.CHAR))
       {
         error[0]="The type of "+s+" is CHARACTER and cannot be used in an\n " +
                 "arithmetic expressions! You should delete it from the expression.";
         error[1]=s;
         return false;
       }
      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.STRING))
       {
         error[0]="The type of "+s+" is STRING and cannot be used in an\n " +
                 "arithmetic expressions! You should delete it from the expression.";
         error[1]=s;
         return false;
       }
      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
       {
         error[0]="The type of "+s+" is BOOLEAN and cannot be used in\n " +
                 "arithmetic expressions! You should delete it from the expression.";
         error[1]=s;
         return false;
       }
     }
   }
   else
   {
      if (paramType.equalsIgnoreCase(WizardsDefinitions.CHAR))
       {
         error[0]="The type of input parameter "+s+" is CHARACTER and cannot be used\n" +
                 "in an arithmetic expression! You should delete it from the expression.";
         error[1]=s;
         return false;
       }
//      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.STRING))
      if (paramType.equalsIgnoreCase(WizardsDefinitions.STRING))
      {
         error[0]="The type of input parameter "+s+" is STRING and cannot be used\n" +
                 "in an arithmetic expression! You should delete it from the expression.";
         error[1]=s;
         return false;
       }
      if (paramType.equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
       {
         error[0]="The type of input parameter "+s+" is BOOLEAN and cannot be used\n" +
                 "in an arithmetic expression! You should delete it from the expression.";
         error[1]=s;
         return false;
       }
   }
  }
return true;
}


private boolean checkArray()
{
    int firstBracketPos=0; 
    AllObjects aoi=null;
    AllObjectsList aol = AllObjectsList.getAllObjList();
    for (String s:arraysList)
    {
     aoi=null;
     firstBracketPos=s.indexOf("[");
     s=s.substring(0,firstBracketPos);
     aoi=aol.SearchByDisplayName_4Array(s,this.objScope);
     if (aoi==null)
       {
         error[0]="The term "+s+" is not declared as an array in the program or is out of scope and cannot\n" +
                 "be used as an array in this arithmetic expression! You should delete it from the expression.";
         error[1]=s;
         return false;
        }
     else
     {
      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.CHAR))
       {
         error[0]="The type of array "+s+" is CHARACTER and cannot be used in an\n" +
                 "arithmetic expression! You should delete it from the expression.";
         error[1]=s;
         return false;
       }
      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.STRING))
       {
         error[0]="The type of array "+s+" is STRING and cannot be used in an\n" +
                 "arithmetic expression! You should delete it from the expression.";
         error[1]=s;
         return false;
       }
      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
       {
         error[0]="The type of array "+s+" is BOOLEAN and cannot be used in an\n" +
                 "arithmetic expression! You should delete it from the expression.";
         error[1]=s;
         return false;
       }
     }
    }
return true;
}


private boolean checkFunction()
{
    int firstBracketPos=0;
    AllObjects aoi=null;
    AllObjectsList aol = AllObjectsList.getAllObjList();
    for (String s:function_parametersList)
    {
     aoi=null;
     firstBracketPos=s.indexOf("(");
     s=s.substring(0,firstBracketPos);
     aoi=aol.SearchByDisplayName_4Function(s);
     if (aoi==null)
       {
         error[0]="The term "+s+" is not declared as a function in the program or is out of scope and cannot\n" +
                 "be used as a function in this arithmetic expression! You should delete it from the expression.";
         error[1]=s;
         return false;
        }
     else
     {
      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.CHAR))
       {
         error[0]="The return type of function of "+s+" is CHARACTER and cannot be used\n" +
                 "in an arithmetic expression!  You should delete it from the expression.";
         error[1]=s;
         return false;
       }
      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.STRING))
       {
         error[0]="The return type of function of "+s+" is STRING and cannot be used\n" +
                 "in an arithmetic expression!  You should delete it from the expression.";
         error[1]=s;
         return false;
       }
      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
       {
         error[0]="The return type of function of  "+s+" is BOOLEAN and cannot be used\n" +
                 "in an arithmetic expression!  You should delete it from the expression.";
         error[1]=s;
         return false;
       }
     }
    }
return true;
}

public  String [] getError()
{return this.error;}

private boolean checkIdentifier(String s)
    {
      AllObjects aoi=null;
      String param=null;
      AllObjectsList aol = AllObjectsList.getAllObjList();
    if(aol!=null)
        ///Search all elements
      aoi=aol.SearchByDisplayName(s);
    if (aoi==null)
    { //If none was found- check if it is a LOCAL parameter
      param=checkDispParameters(s,aol);
      if (param==null)
            return false;
    }
    return true;
    }

private String checkDispParameters (String s,AllObjectsList allObjList)
{
 String [] dispParams=null;
 String [] objParamsType=null;

   AllObjects FunPro =allObjList.SearchByObjectScope_4FunctionsOrProcedures(this.objScope);
    if(FunPro!=null)
        if (!FunPro.getDispParams().isEmpty())
         {
          dispParams = new String [FunPro.getDispParams().size()];
          objParamsType= new String [FunPro.getObjParamsTypes().size()];
          for (int op=0;op<FunPro.getDispParams().size();op++)
          {
                dispParams[op]=FunPro.getDispParams().get(op);
                objParamsType[op]= FunPro.getObjParamsTypes().get(op);
          }
         }
    if( dispParams!=null && objParamsType!=null)
    {
        for(int pn=0;pn<dispParams.length;pn++)
        { 
            if(s.equalsIgnoreCase(dispParams[pn]))
                 return objParamsType[pn];
        }
    }

return null;
}

private boolean checkIdentifierReservedWord(String s)
    {
     if (ReservedWords.CheckReservedWord(s))
      return false;
    return true;
    }

public String getExpressionObjStatetment()
 {  int i=0;
    String statement="";
    
     
      for (String s:this.AllOpersList)
      {
       if (tokensList.get(i)==14)
           s=" && ";
       else if (tokensList.get(i)==15)
           s=" || ";
       else if (tokensList.get(i)==16)
           s=" ! ";
       else if (tokensList.get(i)==17)
           s=" true ";
       else if (tokensList.get(i)==18)
           s=" false ";
       else if (tokensList.get(i)==8)
        //then s is an identifier and get its object represenation
        {
         AllObjects aoi=null;
         AllObjectsList aol = AllObjectsList.getAllObjList();
         if(aol!=null)
         {
           aoi=aol.SearchByDisplayName(s);
           if (aoi!=null)
               s= aoi.getObjName();
           else
               s=checkObjParameters(s,aol);

         }
        }
        statement=statement+s;
        i++;
      }
      statement=statement.substring(0, statement.length()-1);
   
     return statement;
}


private String checkObjParameters (String s,AllObjectsList allObjList)
{
 String res=null;


   AllObjects FunPro =allObjList.SearchByObjectScope_4FunctionsOrProcedures(this.objScope);
    if(FunPro!=null)
        if (!FunPro.getDispParams().isEmpty())
         {
          s=s.toUpperCase();
          int pos = FunPro.getDispParams().indexOf(s);
          res="("+FunPro.getObjParamsTypes().get(pos)+") ("+ FunPro.getObjParams().get(pos)+")";
         }
  return res;
 }




///////////////////////CONDITION CHECK////////////////////////////////////////////////////////


public boolean checkCondition()
{
if (!lexErrorsMessagesList.isEmpty())
{
error[0]=lexErrorsMessagesList.get(0);
error[1]=lexErrorsList.get(0);
return false;
}
else if (!parseMessageErrorsList.isEmpty())
{
error[0]=parseMessageErrorsList.get(0);
error[1]=parseErrorsList.get(0);
 return false;
}

 if (!identifiersList.isEmpty())
{String invalid_id="";
 String reserved_id="";

     for (String s:identifiersList)
     {
        if (!checkIdentifier(s))
        {
            if (!checkIdentifierReservedWord(s))
            {
              if (!checkIdentifierReservedWordCondition(s))
              reserved_id=s;
            }
           else
              invalid_id=s;
        }
     }
     ////if reserved word
     if (!reserved_id.isEmpty())
     {
      error[0]="The term "+reserved_id+" is used for other purposes (reserved word) and\n" +
              "cannot be used in a logical condition! You should delete it from the condition.";
      error[1]=reserved_id;
      return false;
     }
     //if unknown identifier
     if (!invalid_id.isEmpty())
     {
       error[0]= "The term "+invalid_id+" is not declared or is out of scope and\n" +
               "cannot be used in this condition! You should delete it from the condition.";
       error[1]=invalid_id;
       return false;
     }
 }

 if(!checkConstantOrVariableCondition()) return false;
 if(!checkArrayCondition()) return false;
 if(!checkFunctionCondition()) return false;

  return true;
}

private boolean checkConstantOrVariableCondition()
{
    AllObjects aoi=null;
    String paramType=null;
    AllObjectsList aol = AllObjectsList.getAllObjList();

  for (String s:con_varList)
  {
     aoi=null;
     paramType=null;
     paramType=checkDispParameters(s,aol);
     if (paramType==null)
     {
     aoi=aol.SearchByDisplayName_4ConstantOrVariable(s,this.objScope);
     if (aoi==null)
       {
         error[0]="The term "+s+" is not declared as a constant or a variable in the program or is out of scope\n" +
                 "and cannot be used as a constant or a variable in this logical condition! You should delete it from the condition.";
         error[1]=s;
         return false;
        }
//     else
//     {
//      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.CHAR))
//       {
//         error[0]="Η "+s+" είναι τύπου: ΧΑΡΑΚΤΗΡΑ και δεν μπορεί να χρησιμοποιηθεί σε αριθμητική ΕΚΦΡΑΣΗ!";
//         error[1]=s;
//         return false;
//       }
//     }
   }
//   else
//   {
//      if (paramType.equalsIgnoreCase(WizardsDefinitions.CHAR))
//       {
//         error[0]="Η παράμετρος "+s+" είναι τύπου: ΧΑΡΑΚΤΗΡΑ και δεν μπορεί να χρησιμοποιηθεί σε αριθμητική ΕΚΦΡΑΣΗ!";
//         error[1]=s;
//         return false;
//       }
//     }
  }

/////Check if idenifiers used as boolean are boolean indeed
   for (String s:boolCon_varList)
  {
     aoi=null;
     paramType=null;
     paramType=checkDispParameters(s,aol);
     if (paramType==null)
     {
     aoi=aol.SearchByDisplayName_4ConstantOrVariable(s,this.objScope);
     if (aoi==null)
       {
         error[0]="The term "+s+" is not declared as a constant or a variable in the program or is out of scope\n" +
                 "and cannot be used as a constant or a variable in this logical condition! You should delete it from the condition.";
         error[1]=s;
         return false;
        }
     else
     {
      if (!aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
       {
         error[0]="The term "+s+" is used in the logical condition as BOOLEAN, but its type is "+aoi.getDispType()+"!\n" +
                 "You should delete this term from the condition or use it as "+aoi.getDispType()+".";
         error[1]=s;
         return false;
       }
     }
   }
   else
   {
      if (!paramType.equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
       {
         error[0]="Input parameter "+s+" is used in the logical condition as BOOLEAN, but its type is "+aoi.getDispType()+"!\n" +
                 "You should delete this term from the condition or use it as "+aoi.getDispType()+".";
         error[1]=s;
         return false;
       }
     }
  }
return true;
}


private boolean checkArrayCondition()
{
    int firstBracketPos=0;
    AllObjects aoi=null;
    AllObjectsList aol = AllObjectsList.getAllObjList();
    for (String s:arraysList)
    {
     aoi=null;
     firstBracketPos=s.indexOf("[");
     s=s.substring(0,firstBracketPos);
     aoi=aol.SearchByDisplayName_4Array(s,this.objScope);
     if (aoi==null)
       {
         error[0]="The term "+s+" is not declared as an array in the program or is out of scope and\n" +
                 "cannot be used as an array in this logical condition! You should delete it from the condition.";
         error[1]=s;
         return false;
        }
//     else
//     {
//      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.CHAR))
//       {
//         error[0]="O "+s+" είναι ΠΙΝΑΚΑΣ ΧΑΡΑΚΤΗΡΩΝ και δεν μπορεί να χρησιμοποιηθεί σε αριθμητική ΕΚΦΡΑΣΗ!";
//         error[1]=s;
//         return false;
//       }
//     }
    }

 /////////////////////Check if arrays used as booleans are booleans indeed
    for (String s:boolArraysList)
    {
     aoi=null;
     firstBracketPos=s.indexOf("[");
     s=s.substring(0,firstBracketPos);
     aoi=aol.SearchByDisplayName_4Array(s,this.objScope);
     if (aoi==null)
       {
         error[0]="The term "+s+" is not declared as an array in the program or is out of scope and\n" +
                 "cannot be used as an array in this logical condition! You should delete it from the condition.";
         error[1]=s;
         return false;
        }
     else
     {
      if (!aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
       {
         error[0]="The term "+s+" is used in the logical condition as BOOLEAN array, but the type of the array is "+aoi.getDispType()+"!\n" +
                 "You should delete this term from the condition or use it as "+aoi.getDispType()+" array.";
         error[1]=s;
         return false;
       }
     }
    }


 return true;
}


private boolean checkFunctionCondition()
{
    int firstBracketPos=0;
    AllObjects aoi=null;
    AllObjectsList aol = AllObjectsList.getAllObjList();
    for (String s:function_parametersList)
    {
     aoi=null;
     firstBracketPos=s.indexOf("(");
     s=s.substring(0,firstBracketPos);
     aoi=aol.SearchByDisplayName_4Function(s);
     if (aoi==null)
       {
         error[0]="The term "+s+" is not declared as function in the program or is out of scope and\n" +
                 "cannot be used as a function in this logical condition! You should delete it from the condition.";
         error[1]=s;
         return false;
        }
//     else
//     {
//      if (aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.CHAR))
//       {
//         error[0]="Η συνάρτηση "+s+" επιστρέφει ΧΑΡΑΚΤΗΡΑ και δεν μπορεί να χρησιμοποιηθεί σε αριθμητική ΕΚΦΡΑΣΗ!";
//         error[1]=s;
//         return false;
//       }
//      }
    }

///////////    Check if function used as boolean is boolean indeed
      for (String s:boolFunction_parametersList)
    {
     aoi=null;
     firstBracketPos=s.indexOf("(");
     s=s.substring(0,firstBracketPos);
     aoi=aol.SearchByDisplayName_4Function(s);
     if (aoi==null)
       {
         error[0]="The term "+s+" is not declared as function in the program or is out of scope and\n" +
                 "cannot be used as a function in this logical condition! You should delete it from the condition.";
         error[1]=s;
         return false;
        }
     else
     {
      if (!aoi.getObjType().equalsIgnoreCase(WizardsDefinitions.BOOLEAN))
       {
         error[0]="The return type of function "+s+" is used in the logical condition as BOOLEAN, but it is "+aoi.getDispType()+"!\n" +
                 "You should delete this term from the condition or use it as "+aoi.getDispType()+".";
         error[1]=s;
         return false;
       }
      }
    }


return true;
}

private boolean checkIdentifierReservedWordCondition(String s)
    {
     if (ReservedWords.CheckReservedWordNotBoolOperators(s))
      return false;
    return true;
    }



public String getConditionObjStatetment()
 {  int i=0;
    String statement="";


      for (String s:this.AllOpersList)
      { 
       if (tokensList.get(i)==14)
           s=" && ";
       else if (tokensList.get(i)==15)
           s=" || ";
       else if (tokensList.get(i)==16)
           s=" ! ";
       else if (tokensList.get(i)==17)
           s=" true ";
       else if (tokensList.get(i)==18)
           s=" false ";
       else if (tokensList.get(i)==8)
        //then s is an identifier and get its object represenation
        {
         AllObjects aoi=null;
         AllObjectsList aol = AllObjectsList.getAllObjList();
         if(aol!=null)
         {
           aoi=aol.SearchByDisplayName(s);
           if (aoi!=null)
               s= aoi.getObjName();
           else
               s=checkObjParameters(s,aol);
         }
        }
        statement=statement+s;
        i++;
      }
      statement=statement.substring(0, statement.length()-1);

     return statement;
}



}