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

import org.coeus.vteditor.actions.getArguementsFromLookup;
import java.util.LinkedList;
import java.util.List;
import org.coeus.vteditor.btvnodes.PCNodeLeaf;
import org.coeus.vteditor.btvnodes.PCNodeParent;

import org.coeus.poclasses.ArrayObject;
import org.coeus.poclasses.ArrayObjectList;
import org.coeus.poclasses.ConstantObject;
import org.coeus.poclasses.ConstantObjectList;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.VariableObjectList;
import org.coeus.wizards.WizardsDefinitions;

import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Index.ArrayChildren;
import org.openide.nodes.Node;


/**
 *
 * @author Jrd
 */
public class Checks {
   
  private LinkedList<String> unusedCategoryName=new LinkedList();
  private LinkedList<String> uninitializedVariableName=new LinkedList();
  private LinkedList<String> uninitializedArrayName=new LinkedList();
  private LinkedList<String> returnMissing=new LinkedList();
  private LinkedList<String> tooManyReturn=new LinkedList();
  private LinkedList<String> commandsAfterReturn=new LinkedList();
  private String  noReturnFoundName=null,noReturnFoundCategory=null;



  public Checks(Node rn,ArrayObjectList aol,VariableObjectList vol,ConstantObjectList col)
  { 
  constantsVariablesArraysChecks(rn);
  uninitializedVariablesArraysChecks(rn,aol,vol,col);
  returnMissing(rn);
  }

    public LinkedList<String> getCommandsAfterReturn() {
        return commandsAfterReturn;
    }

    public LinkedList<String> getReturnMissing() {
        return returnMissing;
    }

    public LinkedList<String> getTooManyReturn() {
        return tooManyReturn;
    }

    public LinkedList<String> getUninitializedArrayName() {
        return uninitializedArrayName;
    }

    public LinkedList<String> getUninitializedVariableName() {
        return uninitializedVariableName;
    }

    public LinkedList<String> getUnusedCategoryName() {
        return unusedCategoryName;
    }

   public boolean areWarnings()
   {return !uninitializedArrayName.isEmpty() ||
           !uninitializedVariableName.isEmpty() ||
           !unusedCategoryName.isEmpty();
   }

    public int getWarningsNum()
   {return uninitializedArrayName.size()+
           uninitializedVariableName.size()+
           unusedCategoryName.size();
   }

   public boolean areErrors()
   {return !commandsAfterReturn.isEmpty() ||
           !returnMissing.isEmpty() ||
           !tooManyReturn.isEmpty();
   }

    public int getErrorsNum()
   {return commandsAfterReturn.size()+
           returnMissing.size()+
           tooManyReturn.size();
   }

 ////////////////////REORDER CONSTANTS VARIABLES AND ARRAYS AT THE TOP OF NODES
 public static void constantsVariablesArraysChecks(Node rn)
 {  
//ActionEvent evt = null;
List<Node> l=rn.getChildren().snapshot();
if (l.size() >= 0) {
         ////////////////////GET ENUMERATION OF NODES
//            for (Enumeration e=rn.getChildren().nodes(); e.hasMoreElements(); ) {
//                Node n = (Node)e.nextElement();
//////////////////////////GET LIST OF NODES
                for ( int ir=l.size()-1;ir>=0;ir-- ) {
                Node n = (Node)l.get(ir);
//////////////////////////////////////////////////////////////
                if (n instanceof PCNodeParent)
                {
                    PCNodeParent pcnp = (PCNodeParent) n;
                    if (pcnp.getDispCateg().equalsIgnoreCase(WizardsDefinitions.PROCEDURE)
                            || pcnp.getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION))
                    constantsVariablesArraysChecks(n);

                }
                else if (n instanceof PCNodeLeaf)
                {
                     PCNodeLeaf pcnl = (PCNodeLeaf) n;
                     if (pcnl.getDispCateg().equalsIgnoreCase(WizardsDefinitions.ARRAY)
                         || pcnl.getDispCateg().equalsIgnoreCase(WizardsDefinitions.CONSTANT)
                         || pcnl.getDispCateg().equalsIgnoreCase(WizardsDefinitions.VARIABLE))
                     {
                      Children c=n.getParentNode().getChildren();
                      if (c instanceof Index.ArrayChildren)//CreateMainChild)
                      { 
                      Index.ArrayChildren ic =(Index.ArrayChildren)c;
                      int pos=ic.indexOf(n);
                      ic.move(pos, 0);
                      }

                     }
                }
            }
     }
 }
 
 
/////////////////////SEARCH FOR UNUSED CONSTANTS,ARRAYS NAD VARIABLES
///////////////////SEARCH FOR UNINITIALIZED ARRAYS AND VARIABLES
 public void uninitializedVariablesArraysChecks(Node rn,ArrayObjectList aol,
         VariableObjectList vol,ConstantObjectList col)
 {
unusedCategoryName.clear();
uninitializedVariableName.clear();
uninitializedArrayName.clear();


////check if there any unused elements
for(ArrayObject ao:aol.arrList)
{ 
    if (ao.getLocked().isEmpty())
     {
      unusedCategoryName.add(ao.getDispCateg()+" "+ao.getDispName());
     }
    else if(ao.getObjValue()==null)
       searchIfObjectUninitialized(rn,ao.getDispName(),ao.getDispCateg());
}

for(VariableObject vo:vol.varList)
{
    if (vo.getLocked().isEmpty())
     {
      unusedCategoryName.add(vo.getDispCateg()+" "+vo.getDispName());
     }
    else if(vo.getObjValue().isEmpty())
       searchIfObjectUninitialized(rn,vo.getDispName(),vo.getDispCateg());
}

for(ConstantObject co:col.conList)
{
    if (co.getLocked().isEmpty())
     {
      unusedCategoryName.add(co.getDispCateg()+" "+co.getDispName());
      }
  }

}


    private void searchIfObjectUninitialized(Node rn,String dispName,String dispCategory)
    {
    getArguementsFromLookup gafl=firstNodeConatiningDispName(rn,dispName);
   //     System.out.println("\n\ndispName " +dispName+" "+dispCategory+" found in "+gafl.getDisplayName() +"  "+gafl.getDispCategory()+"\n\n");
       
    if (gafl!=null && dispCategory.equalsIgnoreCase(WizardsDefinitions.ARRAY))
    {
        if (!uninitializedArrayName.contains(dispName
                +" in "+gafl.getDisplayName()))
        uninitializedArrayName.add(dispName
                +" in "+gafl.getDisplayName());
    }

    else if (gafl!=null && dispCategory.equalsIgnoreCase(WizardsDefinitions.VARIABLE) &&
            (gafl.getDispCategory().equalsIgnoreCase(WizardsDefinitions.COM_ASSIGN)
            || gafl.getDispCategory().equalsIgnoreCase(WizardsDefinitions.COM_READ)))
     {
      int numOfLeftBracket=0;
      int numOfLeftParenthesis=0;

       for (String s:gafl.getAllOperants())
       { 
         if (s.equalsIgnoreCase("[")) numOfLeftBracket++;
         if (s.equalsIgnoreCase("(")) numOfLeftParenthesis++;
         if (s.equalsIgnoreCase("]")) numOfLeftBracket--;
         if (s.equalsIgnoreCase(")")) numOfLeftParenthesis--;
         if (s.equalsIgnoreCase(dispName) && (numOfLeftBracket>0 || numOfLeftParenthesis>0))
         {
             if (!uninitializedVariableName.contains(dispName
                +" in "+gafl.getDisplayName()))
            uninitializedVariableName.add(dispName
                      +" in "+gafl.getDisplayName());
         }
       }
     }

    else if (gafl!=null && dispCategory.equalsIgnoreCase(WizardsDefinitions.VARIABLE) &&
                gafl.getDispCategory().equalsIgnoreCase(WizardsDefinitions.COM_FOR))
    {
      checkExpressionsInForCommand(gafl,dispName,dispCategory);
    }

    else
    {
      uninitializedVariableName.add(dispName
              +" in "+gafl.getDisplayName());
    }
  }




private void checkExpressionsInForCommand(getArguementsFromLookup gafl,String dispName,String dispCategory)
{
  String arg=null,varName=null;
  int varPos;
  int numOfLeftBracket=0;
  int numOfLeftParenthesis=0;


  varName=gafl.getArgs().get(0);//Variable_Name in for(variable_name=..;....;.....)
//  System.out.println("\n\nvarName " +varName +"\n\n");
  gafl.existsIdentifierInString(varName,"");
  for (String s:gafl.getAllOperants())
       { //System.out.println("\n\n" + s +"\n\n");
         if (s.equalsIgnoreCase("[")) numOfLeftBracket++;
         if (s.equalsIgnoreCase("(")) numOfLeftParenthesis++;
         if (s.equalsIgnoreCase("]")) numOfLeftBracket--;
         if (s.equalsIgnoreCase(")")) numOfLeftParenthesis--;
         if (s.equalsIgnoreCase(dispName) && (numOfLeftBracket>0 || numOfLeftParenthesis>0))
         {
             if (!uninitializedVariableName.contains(dispName
                +" in "+gafl.getDisplayName()))
              uninitializedVariableName.add(dispName
                      +" in "+gafl.getDisplayName());
         }
       }
  

  arg=gafl.getArgs().get(1);//1st statement of for command 
  varPos=arg.indexOf(varName)+varName.length();
  arg=arg.substring(varPos);//get statement before relational op, i.e =<a+1
  gafl.existsIdentifierInString(arg,"");
//  System.out.println("\n\n\n1IDs");
  for (String s:gafl.getIdentifiers())
  {
       // System.out.println("\n\n" +s);
        if (dispName.equalsIgnoreCase(s))
        {
          if (!uninitializedVariableName.contains(dispName
                +" in "+gafl.getDisplayName()))
              uninitializedVariableName.add(dispName
                      +" in "+gafl.getDisplayName());
        }
  }

  arg=gafl.getArgs().get(2);//2nd statement of for command
//  varPos=arg.indexOf(varName)+varName.length();
//  arg=arg.substring(varPos);//get statement before =, i.e =a+1
  gafl.existsIdentifierInString(arg,"");
//  System.out.println("\n\n\n2IDs");
  for (String s:gafl.getIdentifiers())
  {
//        System.out.println("\n\n" +s);
        if (dispName.equalsIgnoreCase(s)&& !s.equalsIgnoreCase(varName))
        {
          if (!uninitializedVariableName.contains(dispName
                +" in "+gafl.getDisplayName()))
              uninitializedVariableName.add(dispName
                      +" in "+gafl.getDisplayName());
        }
  }


  arg=gafl.getArgs().get(3);//3rd statement of for command
//  varPos=arg.indexOf(varName)+varName.length();
//  arg=arg.substring(varPos);//get statement before =, i.e =a+1
//  varPos=arg.indexOf(varName)+varName.length();
//  arg=arg.substring(varPos);//get statement fater cariable name, i.e +1
  gafl.existsIdentifierInString(arg,"");
//  System.out.println("\n\n\n3IDs");
  for (String s:gafl.getIdentifiers())
  {
//        System.out.println("\n\n" +s);
        if (dispName.equalsIgnoreCase(s)&& !s.equalsIgnoreCase(varName))
        {
          if (!uninitializedVariableName.contains(dispName
                +" in "+gafl.getDisplayName()))
              uninitializedVariableName.add(dispName
                      +" in "+gafl.getDisplayName());
        }
  }
}


private  getArguementsFromLookup firstNodeConatiningDispName(Node rn,String dispName)
{
getArguementsFromLookup gafl=null,result=null;
List<Node> l=rn.getChildren().snapshot();

    for ( int ir=0;ir<l.size();ir++ )
    {
        Node n = (Node)l.get(ir);
        if (n instanceof PCNodeParent)
        {
            PCNodeParent pcnp = (PCNodeParent) n;
            gafl= new getArguementsFromLookup(pcnp.getLookup());
            if (gafl!=null && gafl.containsName(dispName))
               return  result=gafl;
            gafl=firstNodeConatiningDispName(n,dispName);
            if (gafl!=null && gafl.containsName(dispName))
              return result=gafl;
        }
        else if (n instanceof PCNodeLeaf)
        {
          PCNodeLeaf pcnl = (PCNodeLeaf) n;
           gafl= new getArguementsFromLookup(pcnl.getLookup());
            if (gafl!=null && gafl.containsName(dispName))
               return  result=gafl;
        }
   }
return result;
}



/////////////////////////////////RETURN MISSING////////////////////////

public void returnMissing(Node rn) {

returnMissing.clear();
tooManyReturn.clear();
commandsAfterReturn.clear();

String message=null;
boolean returnFound=false;
List<Node> l=rn.getChildren().snapshot();
if (l.size() >= 0)
   {
    for ( int ir=0;ir<l.size();ir++ )
    {
        Node n = (Node)l.get(ir);
        if (n instanceof PCNodeParent)
        {
            PCNodeParent pcnp = (PCNodeParent) n;
            if (pcnp.getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION))
            {
              noReturnFoundName=null;
              noReturnFoundCategory=null;
              returnFound=checkReturn(pcnp);
                if (!returnFound )
                {
                 if(noReturnFoundName!=null && noReturnFoundCategory!=null)                
                    message="in "+noReturnFoundCategory
                      +" "+noReturnFoundName+" in FUNCTION "+pcnp.getDispName()+" !";
                 else
                   message="in FUNCTION "+pcnp.getDispName()+" !";

                returnMissing.add(message);
            }
        }
     }
   }
  }
}




private boolean checkReturn(PCNodeParent np)
{ boolean result=false;
  String message=null;
  Index.ArrayChildren children =(ArrayChildren) np.getChildren();
  Node returnNode=null;
  int numOfReturns=0;
  int returnPos=0;
  List<Node> l=children.snapshot();
  
  if (l.size() >= 0)
   {
    for ( int ir=0;ir<l.size();ir++ )
    {
        Node n = (Node)l.get(ir);
        if (n instanceof PCNodeLeaf)
        {
            PCNodeLeaf pcnl = (PCNodeLeaf) n;
            if (pcnl.getDispCateg().equalsIgnoreCase(WizardsDefinitions.COM_RETURN))
            {
            returnNode=n;
            returnPos=ir;
            numOfReturns++;
            }
        }
     }
   }

  if (returnNode!=null)
  {
    if (numOfReturns>1)
    {
      if (np.getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION))   
      message="in "+np.getDispCateg()+" "+np.getDispName();
      else
      message="in "+np.getDispCateg()+" "+np.getDispName()+" in FUNCTION "
           +np.getParentFunctionName();
      
      tooManyReturn.add(message);
    }
    else if (returnPos!=l.size()-1)
    {
     String commands="";
     for(int ir=returnPos+1;ir<l.size();ir++)
       {commands=commands+"\n"+l.get(ir).getDisplayName().trim();}
     if (np.getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION))
     message="in " +np.getDispCateg()+" "+np.getDispName()
         +" - the following command(s) will not be executed:";
     else
     message="in "+np.getDispCateg()+" "+np.getDispName()+" in FUNCTION "
         +np.getParentFunctionName()+" - the following command(s) will not be executed:";

      commandsAfterReturn.add(message+commands);
    }    
  result=true;
  }
  else//No Return found in main body..check IFs 
  { boolean temp;
    for ( int ir=0;ir<l.size();ir++ )
    {
        Node n = (Node)l.get(ir);
        if (n instanceof PCNodeParent)
        {
            PCNodeParent pcnp = (PCNodeParent) n;
            if (pcnp.getDispCateg().equalsIgnoreCase(WizardsDefinitions.COM_IF))
            {
               Index.ArrayChildren ifChildren=(ArrayChildren) pcnp.getChildren();
               int numOfIfChildren=ifChildren.getNodesCount();
               result=true;
               for (int k=0;k<numOfIfChildren;k++)
               {
               temp=checkReturn((PCNodeParent)ifChildren.getNodeAt(k));
               if (!temp && noReturnFoundName==null && noReturnFoundCategory==null)
               {
                 noReturnFoundName=((PCNodeParent)ifChildren.getNodeAt(k)).getDispName();
                 noReturnFoundCategory=((PCNodeParent)ifChildren.getNodeAt(k)).getDispCateg();
               }
               result=result && temp;
               }                
            }
        }
     }
  }  
return result;
}

}
