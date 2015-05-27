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

import java.util.LinkedList;
import org.coeus.parsers.myParser;
import org.coeus.parsers.myScanner;
import org.coeus.parsers.tokens;
import org.coeus.wizards.WizardsDefinitions;

/**
 *
 * @author Jrd
 */
public class LockObjects {

    private LinkedList<String> consVarsArrsParams2lock=new LinkedList();
    private LinkedList<String> procsFuncs2lock=new LinkedList();
    private String lockedby=null;
    private String dispScope=null;


    public  LockObjects(AssignObject ao)
    {
     consVarsArrsParams2lock.clear();
     procsFuncs2lock.clear();
     this.dispScope=ao.getDispScope();
     string2itemsList(ao.getDispAssignName());
     string2itemsList(ao.getDispAssignValue());
     lockedby=ao.getDispName();
    }

    public  LockObjects(CallObject co)
    {
     consVarsArrsParams2lock.clear();
     procsFuncs2lock.clear();
     this.dispScope=co.getDispScope();
     //string2ProcOrFuncList(co.getDispProcedureName());
     procsFuncs2lock.add(co.getDispProcedureName());
     //string2itemsList(co.getDispProcedureName());
     for (String s:co.getDispActualParameters())
        string2itemsList(s);
     lockedby=co.getDispName();
    }

 public  LockObjects(DoWhileObject dwo)
    {
     consVarsArrsParams2lock.clear();
     procsFuncs2lock.clear();
     this.dispScope=dwo.getDispScope();
     string2itemsList(dwo.getDispDoWhileValue());
     lockedby=dwo.getDispName();
    }

 public  LockObjects(ForObject fo)
    {
     consVarsArrsParams2lock.clear();
     procsFuncs2lock.clear();
     this.dispScope=fo.getDispScope();
      for (String s:fo.getDispStatement())
        string2itemsList(s);
     lockedby=fo.getDispName();
    }

  public  LockObjects(ReadObject ro)
    {
     consVarsArrsParams2lock.clear();
     procsFuncs2lock.clear();
     this.dispScope=ro.getDispScope();
     for (String s: ro.getDispArguments())
     { 
        string2itemsList(s);
     }
     lockedby=ro.getDispName();
    }

   public  LockObjects(ReturnObject ro)
    {
     consVarsArrsParams2lock.clear();
     procsFuncs2lock.clear();
    this.dispScope=ro.getDispScope();
    //string2ProcOrFuncList(ro.getDispFunctionName());
    procsFuncs2lock.add(ro.getDispFunctionName());
    //string2itemsList(ro.getDispFunctionName());
    string2itemsList(ro.getDispReturnValue());
    lockedby=ro.getDispName();
    }


    public  LockObjects(WhileObject wo)
    {
     consVarsArrsParams2lock.clear();
     procsFuncs2lock.clear();
     this.dispScope=wo.getDispScope();
     string2itemsList(wo.getDispWhileValue());
     lockedby=wo.getDispName();
    }

     public  LockObjects(WriteObject wo)
    {
     consVarsArrsParams2lock.clear();
     procsFuncs2lock.clear();
     this.dispScope=wo.getDispScope();
     for (String s: wo.getDispArguments())
        string2itemsList(s);
   lockedby=wo.getDispName();
    }

    public  LockObjects(IfObject io)
    {
     consVarsArrsParams2lock.clear();
     procsFuncs2lock.clear();
     this.dispScope=io.getDispScope();
     for (String s: io.getDispStatements())
        string2itemsList(s);
     lockedby=io.getDispName();

    }

   

    private void string2itemsList(String s)
    {
     myScanner scanner =new myScanner(s,true);
     myParser parser =new myParser(scanner);
     parser.checkCondition();
     do
         scanner.getToken();
     while(scanner.token!=tokens.at_sign);
     consVarsArrsParams2lock.addAll(parser.getConvarList());
     consVarsArrsParams2lock.addAll(parser.getBoolConvarList());
     consVarsArrsParams2lock.addAll(parser.getConsVarsParametersList());
     consVarsArrsParams2lock.addAll(keepArraysName(parser.getArraysList()));
     consVarsArrsParams2lock.addAll(keepArraysName(parser.getBoolArraysList()));
     consVarsArrsParams2lock.addAll(keepArraysName(parser.getArraysParametersList()));
     procsFuncs2lock.addAll(keepFuncsProcsName(parser.getFunctionParametersList()));
     procsFuncs2lock.addAll(keepFuncsProcsName(parser.getBoolFunctionParametersList()));
     procsFuncs2lock.addAll(keepFuncsProcsName(parser.getProcsFuncsParametersList()));
    }

    private LinkedList<String> keepFuncsProcsName(LinkedList<String> funcsProcsList)
    {
    LinkedList<String> onlyNames =new LinkedList<String>();

    for (String s:funcsProcsList)
    onlyNames.add(s.substring(0,s.indexOf('(')));

    return onlyNames;
    }

     private LinkedList<String> keepArraysName(LinkedList<String> arraysList)
    {
    LinkedList<String> onlyNames =new LinkedList<String>();

    for (String s:arraysList)
    onlyNames.add(s.substring(0,s.indexOf('[')));

    return onlyNames;
    }


//    private void string2ProcOrFuncList(String s)
//    {
//     myScanner scanner =new myScanner(s,true);
//     do
//         scanner.getToken();
//     while(scanner.token!=tokens.at_sign);
//     procOrFunc2lock.addAll(scanner.getIdentifiersList());
//    }


 public void lock()
    {

     for (String s:this.consVarsArrsParams2lock)
     {  
      ConstantObject co=getConstant(s,this.dispScope);
      VariableObject vo=getVariable(s,this.dispScope);
      ArrayObject ao=getArray(s,this.dispScope);
      FunctionObject fop=getFunctionParameters(s,this.dispScope);
      ProcedureObject pop=getProcedureParameters(s,this.dispScope);
//      FunctionObject fo=getFunction(s);
//      ProcedureObject po=getProcedure(s);
      if (co!=null)
      {
         if (!co.getLocked().contains(lockedby))
             co.getLocked().add(lockedby);}
      else if (vo!=null)
      {
         if (!vo.getLocked().contains(lockedby))
          vo.getLocked().add(lockedby);}
      else if (ao!=null)
      {
      if (!ao.getLocked().contains(lockedby))
          ao.getLocked().add(lockedby);}
      else if (fop!=null)
      {
      if (!fop.getLocked().contains(lockedby))
          fop.getLocked().add(lockedby);}
      else if (pop!=null)
      {
      if (!pop.getLocked().contains(lockedby))
          pop.getLocked().add(lockedby);}
     
//      else if (fo!=null)
//      {
//      if (!fo.getLocked().contains(lockedby))
//          fo.getLocked().add(lockedby);}
//      else if (po!=null)
//      {
//      if (!po.getLocked().contains(lockedby))
//          po.getLocked().add(lockedby);}
    }

      for (String s:this.procsFuncs2lock)
     {
      FunctionObject fo=getFunction(s);
      ProcedureObject po=getProcedure(s);
      if (fo!=null)
      {
      if (!fo.getLocked().contains(lockedby))
          fo.getLocked().add(lockedby);}
      else if (po!=null)
      {
      if (!po.getLocked().contains(lockedby))
          po.getLocked().add(lockedby);}
     }

 }


  public void unlock()
    {
     int i=0;
     for (String s:this.consVarsArrsParams2lock)
     {
      ConstantObject co=getConstant(s,this.dispScope);
      VariableObject vo=getVariable(s,this.dispScope);
      ArrayObject ao=getArray(s,this.dispScope);
      FunctionObject fop=getFunctionParameters(s,this.dispScope);
      ProcedureObject pop=getProcedureParameters(s,this.dispScope);
//      FunctionObject fo=getFunction(s);
//      ProcedureObject po=getProcedure(s);
      if (co!=null)
      {co.getLocked().remove(lockedby);}
      else if (vo!=null)
      {vo.getLocked().remove(lockedby);}
      else if (ao!=null)
      {ao.getLocked().remove(lockedby);}
      else if (fop!=null)
      {fop.getLocked().remove(lockedby);}
      else if (pop!=null)
      {pop.getLocked().remove(lockedby);}
//      else if (fo!=null)
//      {fo.getLocked().remove(lockedby);}
//      else if (po!=null)
//      {po.getLocked().remove(lockedby);}
     }

       for (String s:this.procsFuncs2lock)
     {
      FunctionObject fo=getFunction(s);
      ProcedureObject po=getProcedure(s);
      if (fo!=null)
      {
      fo.getLocked().remove(lockedby);}
      else if (po!=null)
      {
      po.getLocked().remove(lockedby);}
     }
  }



 private ConstantObject getConstant (String dname,String dscope)
 {
  ConstantObject co=null;
  ConstantObjectList col =ConstantObjectList.getConObjList();
  if (col!=null)
  {
  for(ConstantObject cot:col.conList)
  {
     if (cot.getDispName().equalsIgnoreCase(dname) && cot.getDispScope().equalsIgnoreCase(dscope)
     || cot.getDispName().equalsIgnoreCase(dname) && cot.getDispScope().equalsIgnoreCase(WizardsDefinitions.GLOBAL) )
      return cot;
  }
  }
  return co;
 }

 private VariableObject getVariable (String dname,String dscope)
 {
  VariableObject vo=null;
  VariableObjectList vol =VariableObjectList.getVarObjList();
  if (vol!=null)
  {
  for(VariableObject vot:vol.varList)
     if (vot.getDispName().equalsIgnoreCase(dname) && vot.getDispScope().equalsIgnoreCase(dscope)
     || vot.getDispName().equalsIgnoreCase(dname) && vot.getDispScope().equalsIgnoreCase(WizardsDefinitions.GLOBAL) )
       return vot;
  }
  return vo;
 }

  private ArrayObject getArray (String dname,String dscope)
 {
  ArrayObject ao=null;
  ArrayObjectList aol =ArrayObjectList.getArrObjList();
  if (aol!=null)
  {
  for(ArrayObject aot:aol.arrList)
     if (aot.getDispName().equalsIgnoreCase(dname) && aot.getDispScope().equalsIgnoreCase(dscope)
   || aot.getDispName().equalsIgnoreCase(dname) && aot.getDispScope().equalsIgnoreCase(WizardsDefinitions.GLOBAL) )
      return aot;
  }
  return ao;
 }

private ProcedureObject getProcedure (String dname)
 {
  ProcedureObject po=null;
  ProcedureObjectList pol =ProcedureObjectList.getProObjList();
  if (pol!=null)
  {
  for(ProcedureObject pot:pol.proList)
     if (pot.getDispName().equalsIgnoreCase(dname) && pot.getDispScope().equalsIgnoreCase(WizardsDefinitions.PROCEDURE+": "+dname))
      return pot;
  }
  return po;
 }


private ProcedureObject getProcedureParameters (String dname,String dscope)
 {
  ProcedureObject po=null;
  ProcedureObjectList pol =ProcedureObjectList.getProObjList();
  if (pol!=null)
  {
  for(ProcedureObject pot:pol.proList)
     if (pot.getDispScope().equalsIgnoreCase(dscope))
     {
      for (String s:pot.getDispParams())
      {
       if(s.equalsIgnoreCase(dname))
           return pot;
      }
     }
  }
  return po;
 }


private FunctionObject getFunction (String dname)
 {
  FunctionObject fo=null;
  FunctionObjectList fol =FunctionObjectList.getFunObjList();
  if (fol!=null)
  {
  for(FunctionObject fot:fol.funList)
     if (fot.getDispName().equalsIgnoreCase(dname))// && fot.getDispScope().equalsIgnoreCase(WizardsDefinitions.FUNCTION+": "+dname))
       return fot;
  }
  return fo;
 }



private FunctionObject getFunctionParameters (String dname,String dscope)
 {
  FunctionObject fo=null;
  FunctionObjectList fol =FunctionObjectList.getFunObjList();
  if (fol!=null)
  {
  for(FunctionObject fot:fol.funList)
  if (fot.getDispScope().equalsIgnoreCase(dscope))
     {
      for (String s:fot.getDispParams())
      {
       if(s.equalsIgnoreCase(dname))
           return fot;
      }
     }
  }
  return fo;
 }

}
