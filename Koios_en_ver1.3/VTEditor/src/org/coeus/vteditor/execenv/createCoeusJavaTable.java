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



package org.coeus.vteditor.execenv;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import org.coeus.poclasses.ArrayObject;
import org.coeus.poclasses.AssignObject;
import org.coeus.poclasses.CallObject;
import org.coeus.poclasses.ConstantObject;
import org.coeus.poclasses.DoWhileObject;
import org.coeus.poclasses.ForObject;
import org.coeus.poclasses.FunctionObject;
import org.coeus.poclasses.IfObject;
import org.coeus.poclasses.ProcedureObject;
import org.coeus.poclasses.ProgramObject;
import org.coeus.poclasses.ReadObject;
import org.coeus.poclasses.ReturnObject;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.WhileObject;
import org.coeus.poclasses.WriteObject;

import org.openide.nodes.Node;


import java.util.LinkedList;
import org.coeus.poclasses.CoeusProgram;
import org.coeus.poclasses.PrintStructure;
import org.coeus.vteditor.Checks;
import org.coeus.vteditor.btvnodes.RootNode;

/**
 *
 * @author Jrd
 */
public class createCoeusJavaTable {

int commandIndex=1;
int totalTextLineNumbers=9;
LinkedList<Integer> commandBegginPositionIntext=null;
int commandBeginPos=-1;



    private String ps2String (LinkedList<PrintStructure> psarr)
    {
     String temp;
     temp="";
     for (PrintStructure ps:psarr)
         temp=temp+ps.getText();
     return temp;
    }

    public createCoeusJavaTable(RootNode rn,String className, myHashtable<String, String> coeus2Java,
            myHashtable<String, String> coeus2description, myHashtable<String,Integer> commandLocator,
            myHashtable<String,Integer> jcommandLocator,myHashtable<String,Integer>jcommandLocatorStart,
            myHashtable<String,Integer>jcommandLocatorEnd,Hashtable<Integer,Integer> commandBeginEndPair)
    {
    commandIndex=0;
    coeus2Java.clear();
    commandBegginPositionIntext=new LinkedList<Integer> ();

    Checks.constantsVariablesArraysChecks(rn);

     ProgramObject po =rn.getLookup().lookup(ProgramObject.class);


     coeus2Java.put(ps2String(po.getCoeusString1()), po.getJavaString1(className)) ;
     coeus2description.put(ps2String(po.getCoeusString1()), po.getDescription()) ;
     commandLocator.put(ps2String(po.getCoeusString1()), commandIndex);
     commandIndex++;
       if (rn.getChildren().getNodesCount() >= 0) {
         
            for (Enumeration e=rn.getChildren().nodes(); e.hasMoreElements(); ) {
                Node n = (Node)e.nextElement();

                ArrayObject arro = n.getLookup().lookup(ArrayObject.class);
                ConstantObject cono = n.getLookup().lookup(ConstantObject.class);
                FunctionObject funo = n.getLookup().lookup(FunctionObject.class);
                ProcedureObject proo = n.getLookup().lookup(ProcedureObject.class);
                VariableObject varo = n.getLookup().lookup(VariableObject.class);

               if (arro!=null)
               {
               coeus2Java.put(ps2String(arro.getCoeusString1()), arro.getJavaString1());
               coeus2description.put(ps2String(arro.getCoeusString1()), arro.getDescription()) ;
               commandLocator.put(ps2String(arro.getCoeusString1()), commandIndex);
               jcommandLocator.put(arro.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(arro.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(arro.getJavaString1(),getTextLineNumbers(arro.getJavaString1()) );
               commandIndex++;
               }
               else if (cono!=null)
               {
               coeus2Java.put(ps2String(cono.getCoeusString1()), cono.getJavaString1());
               coeus2description.put(ps2String(cono.getCoeusString1()), cono.getDescription()) ;
               commandLocator.put(ps2String(cono.getCoeusString1()), commandIndex);
               jcommandLocator.put(cono.getJavaString1(), commandIndex);
               commandIndex++;
               jcommandLocatorStart.put(cono.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(cono.getJavaString1(),getTextLineNumbers(cono.getJavaString1()) );
               }
                else if (varo!=null)
               {
               coeus2Java.put(ps2String(varo.getCoeusString1()), varo.getJavaString1());
               coeus2description.put(ps2String(varo.getCoeusString1()), varo.getDescription()) ;
               commandLocator.put(ps2String(varo.getCoeusString1()), commandIndex);
               jcommandLocator.put(varo.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(varo.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(varo.getJavaString1(),getTextLineNumbers(varo.getJavaString1()) );
               commandIndex++;
             }

               else if (funo!=null || proo!=null)
                 CreateObjects(n,className,coeus2Java,coeus2description,commandLocator,
                         jcommandLocator,jcommandLocatorStart,jcommandLocatorEnd,commandBeginEndPair);
            }
        }
        coeus2Java.put(ps2String(po.getCoeusString2()), po.getJavaString2(className));
        coeus2description.put(ps2String(po.getCoeusString2()), po.getDescription2()) ;
        commandLocator.put(ps2String(po.getCoeusString2()), commandIndex);
        jcommandLocator.put(po.getJavaString2(className), commandIndex);
        commandIndex++;
     }




  private void CreateObjects(Node rn,String className,myHashtable<String, String> coeus2Java,
            myHashtable<String, String> coeus2description, myHashtable<String,Integer> commandLocator, 
            myHashtable<String,Integer> jcommandLocator,myHashtable<String,Integer>jcommandLocatorStart,
            myHashtable<String,Integer>jcommandLocatorEnd,Hashtable<Integer,Integer> commandBeginEndPair)
  {
       
        ArrayObject arro = rn.getLookup().lookup(ArrayObject.class);
        ConstantObject cono = rn.getLookup().lookup(ConstantObject.class);
        FunctionObject funo = rn.getLookup().lookup(FunctionObject.class);
        ProcedureObject proo = rn.getLookup().lookup(ProcedureObject.class);
        VariableObject varo = rn.getLookup().lookup(VariableObject.class);
        ReadObject reado = rn.getLookup().lookup(ReadObject.class);
        WriteObject writeo = rn.getLookup().lookup(WriteObject.class);
        CallObject callo = rn.getLookup().lookup(CallObject.class);
        ReturnObject reto = rn.getLookup().lookup(ReturnObject.class);
        AssignObject asso = rn.getLookup().lookup(AssignObject.class);
        DoWhileObject dwho = rn.getLookup().lookup(DoWhileObject.class);
        ForObject foro = rn.getLookup().lookup(ForObject.class);
        IfObject iffo = rn.getLookup().lookup(IfObject.class);
        WhileObject whio = rn.getLookup().lookup(WhileObject.class);

        if (arro != null) {
               coeus2Java.put(ps2String(arro.getCoeusString1()), arro.getJavaString1());
               coeus2description.put(ps2String(arro.getCoeusString1()),arro.getDescription()) ;
               commandLocator.put(ps2String(arro.getCoeusString1()), commandIndex);
               jcommandLocator.put(arro.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(arro.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(arro.getJavaString1(),getTextLineNumbers(arro.getJavaString1()) );
               commandIndex++;
        } else if (cono != null) {
               coeus2Java.put(ps2String(cono.getCoeusString1()), cono.getJavaString1());
               coeus2description.put(ps2String(cono.getCoeusString1()), cono.getDescription()) ;
               commandLocator.put(ps2String(cono.getCoeusString1()), commandIndex);
               jcommandLocator.put(cono.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(cono.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(cono.getJavaString1(),getTextLineNumbers(cono.getJavaString1()) );
               commandIndex++;
       } else if (varo != null) {
               coeus2Java.put(ps2String(varo.getCoeusString1()), varo.getJavaString1());
               coeus2description.put(ps2String(varo.getCoeusString1()), varo.getDescription()) ;
               commandLocator.put(ps2String(varo.getCoeusString1()), commandIndex);
               jcommandLocator.put(varo.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(varo.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(varo.getJavaString1(),getTextLineNumbers(varo.getJavaString1()) );
               commandIndex++;
       }else if (reado != null) {
               coeus2Java.put(ps2String(reado.getCoeusString1()), reado.getJavaString1());
               coeus2description.put(ps2String(reado.getCoeusString1()), reado.getDescription()) ;
               commandLocator.put(ps2String(reado.getCoeusString1()), commandIndex);
               jcommandLocator.put(reado.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(reado.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(reado.getJavaString1(),getTextLineNumbers(reado.getJavaString1()) );
               commandIndex++;
       }else if (writeo != null) {
               coeus2Java.put(ps2String(writeo.getCoeusString1()), writeo.getJavaString1());
               coeus2description.put(ps2String(writeo.getCoeusString1()), writeo.getDescription()) ;
               commandLocator.put(ps2String(writeo.getCoeusString1()), commandIndex);
               jcommandLocator.put(writeo.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(writeo.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(writeo.getJavaString1(),getTextLineNumbers(writeo.getJavaString1()) );
               commandIndex++;
       }else if (callo != null) {
               coeus2Java.put(ps2String(callo.getCoeusString1()), callo.getJavaString1());
               coeus2description.put(ps2String(callo.getCoeusString1()), callo.getDescription()) ;
               commandLocator.put(ps2String(callo.getCoeusString1()), commandIndex);
               jcommandLocator.put(callo.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(callo.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(callo.getJavaString1(),getTextLineNumbers(callo.getJavaString1()) );
               commandIndex++;
       }else if (reto != null) {
               coeus2Java.put(ps2String(reto.getCoeusString1()), reto.getJavaString1());
               coeus2description.put(ps2String(reto.getCoeusString1()), reto.getDescription()) ;
               commandLocator.put(ps2String(reto.getCoeusString1()), commandIndex);
               jcommandLocator.put(reto.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(reto.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(reto.getJavaString1(),getTextLineNumbers(reto.getJavaString1()) );
               commandIndex++;
       }else if (asso != null) {
               coeus2Java.put(ps2String(asso.getCoeusString1()), asso.getJavaString1());
               coeus2description.put(ps2String(asso.getCoeusString1()), asso.getDescription()) ;
               commandLocator.put(ps2String(asso.getCoeusString1()), commandIndex);
               jcommandLocator.put(asso.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(asso.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(asso.getJavaString1(),getTextLineNumbers(asso.getJavaString1()) );
               commandIndex++;
       } else if (funo != null) {
               coeus2Java.put(ps2String(funo.getCoeusString1()), funo.getJavaString1());
               coeus2description.put(ps2String(funo.getCoeusString1()),funo.getDescription()) ;
               commandLocator.put(ps2String(funo.getCoeusString1()), commandIndex);
               jcommandLocator.put(funo.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(funo.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(funo.getJavaString1(),getTextLineNumbers(funo.getJavaString1()) );
               commandIndex++;
       }
       else if (proo != null) {
           String procName="";
           if (proo.getDispName().equalsIgnoreCase(CoeusProgram.MAIN_PROC))
               procName=proo.getJavaString1(className);
            else
               procName=proo.getJavaString1(null);
               
               coeus2Java.put(ps2String(proo.getCoeusString1()), procName);
               coeus2description.put(ps2String(proo.getCoeusString1()), proo.getDescription()) ;
               commandLocator.put(ps2String(proo.getCoeusString1()), commandIndex);
               jcommandLocator.put(procName, commandIndex);
               jcommandLocatorStart.put(procName,totalTextLineNumbers+1);
               jcommandLocatorEnd.put(procName,getTextLineNumbers(procName) );
             commandIndex++;
       }
       else if (dwho != null) {
               coeus2Java.put(ps2String(dwho.getCoeusString1()), dwho.getJavaString1());
               coeus2description.put(ps2String(dwho.getCoeusString1()), "") ;
               commandLocator.put(ps2String(dwho.getCoeusString1()), commandIndex);
               jcommandLocator.put(dwho.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(dwho.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(dwho.getJavaString1(),getTextLineNumbers(dwho.getJavaString1()) );
               this.commandBeginPos++;
               this.commandBegginPositionIntext.add(commandIndex);
               commandIndex++;
       }
       else if (foro != null) {
               coeus2Java.put(ps2String(foro.getCoeusString1()), foro.getJavaString1());
               coeus2description.put(ps2String(foro.getCoeusString1()), foro.getDescription()) ;
               commandLocator.put(ps2String(foro.getCoeusString1()), commandIndex);
               jcommandLocator.put(foro.getJavaString1(), commandIndex);
               jcommandLocatorStart.put(foro.getJavaString1(),totalTextLineNumbers+1);
               jcommandLocatorEnd.put(foro.getJavaString1(),getTextLineNumbers(foro.getJavaString1()) );
               this.commandBeginPos++;
               this.commandBegginPositionIntext.add(commandIndex);
               commandIndex++;

       }
       else if (iffo != null) {
           List<Node> ifChildren=rn.getChildren().snapshot();
           for (int childIndex=0;childIndex<ifChildren.size();childIndex++)
           {
             coeus2Java.put(ps2String(iffo.getCoeusString1(childIndex)), iffo.getJavaString1(childIndex));
             coeus2description.put(ps2String(iffo.getCoeusString1(childIndex)), iffo.getDescription(childIndex)) ;
             commandLocator.put(ps2String(iffo.getCoeusString1(childIndex)), commandIndex);
             jcommandLocator.put(iffo.getJavaString1(childIndex), commandIndex);
             jcommandLocatorStart.put(iffo.getJavaString1(childIndex),totalTextLineNumbers+1);
             jcommandLocatorEnd.put(iffo.getJavaString1(childIndex),getTextLineNumbers(iffo.getJavaString1(childIndex)) );
             if (childIndex==0)//get command line only for if statement
             {
             this.commandBeginPos++;
             this.commandBegginPositionIntext.add(commandIndex);
             }
             commandIndex++;

             CreateObjects(ifChildren.get(childIndex),className,coeus2Java,coeus2description,commandLocator,
                     jcommandLocator,jcommandLocatorStart,jcommandLocatorEnd,commandBeginEndPair);

           //coeus2Java.put(ps2String(iffo.getCoeusString2()), iffo.getJavaString2());
           //coeus2description.put(ps2String(iffo.getCoeusString2()), "") ;
           //commandLocator.put(ps2String(iffo.getCoeusString2()), commandIndex);
          // jcommandLocator.put(iffo.getJavaString2(), commandIndex);
           //jcommandLocatorStart.put(iffo.getJavaString2(),totalTextLineNumbers+1);
           //jcommandLocatorEnd.put(iffo.getJavaString2(),getTextLineNumbers(iffo.getJavaString2()) );
           //commandIndex++; na mhn afkithei edo giati ayjanetai sto parakato iffo
           getTextLineNumbers(iffo.getJavaString2());//metrise th grammh tou }//end if-else sto javacode
           }
        }
       else if (whio != null){
           coeus2Java.put(ps2String(whio.getCoeusString1()), whio.getJavaString1());
           coeus2description.put(ps2String(whio.getCoeusString1()), whio.getDescription()) ;
           commandLocator.put(ps2String(whio.getCoeusString1()), commandIndex);
           jcommandLocator.put(whio.getJavaString1(), commandIndex);
           jcommandLocatorStart.put(whio.getJavaString1(),totalTextLineNumbers+1);
           jcommandLocatorEnd.put(whio.getJavaString1(),getTextLineNumbers(whio.getJavaString1()) );
           this.commandBeginPos++;
           this.commandBegginPositionIntext.add(commandIndex);
           commandIndex++;

        }
      if (rn.getChildren().getNodesCount() >= 0 && iffo==null) {
            for (Enumeration e=rn.getChildren().nodes(); e.hasMoreElements(); ) {
                Node n = (Node)e.nextElement();
                CreateObjects(n,className,coeus2Java,coeus2description,commandLocator,
                        jcommandLocator,jcommandLocatorStart,jcommandLocatorEnd,commandBeginEndPair);
            }
        }

       if (funo != null) {
           String endFuncName="exiting "+funo.getObjName();

           coeus2Java.put(ps2String(funo.getCoeusString2()), endFuncName);
           coeus2description.put(ps2String(funo.getCoeusString2()), funo.getDescription2()) ;
           commandLocator.put(ps2String(funo.getCoeusString2()), commandIndex);
           jcommandLocator.put(endFuncName, commandIndex);
           jcommandLocatorStart.put(endFuncName,totalTextLineNumbers+1);
           jcommandLocatorEnd.put(endFuncName,getTextLineNumbers(funo.getJavaString2()) );
           commandIndex++;
       } else if (proo != null) {
           String endProcName="";
           if (proo.getDispName().equalsIgnoreCase(CoeusProgram.MAIN_PROC))
//               endProcName="exiting constructor";
               endProcName="exiting mymain";
            else
               endProcName="exiting "+proo.getObjName();

           coeus2Java.put(ps2String(proo.getCoeusString2()), endProcName);
           coeus2description.put(ps2String(proo.getCoeusString2()), proo.getDescription2()) ;
           commandLocator.put(ps2String(proo.getCoeusString2()), commandIndex);
           jcommandLocator.put(endProcName, commandIndex);
           jcommandLocatorStart.put(endProcName,totalTextLineNumbers+1);
           jcommandLocatorEnd.put(endProcName,getTextLineNumbers(proo.getJavaString2()) );
           commandIndex++;
       }
         else if (dwho != null) {
           coeus2Java.put(ps2String(dwho.getCoeusString2()), dwho.getJavaString2());
           coeus2description.put(ps2String(dwho.getCoeusString2()),dwho.getDescription()) ;
           commandLocator.put(ps2String(dwho.getCoeusString2()), commandIndex);
           jcommandLocator.put(dwho.getJavaString2(), commandIndex);
           jcommandLocatorStart.put(dwho.getJavaString2(),totalTextLineNumbers+1);
           jcommandLocatorEnd.put(dwho.getJavaString2(),getTextLineNumbers(dwho.getJavaString2()) );
           // do-while: do comes before while change order of key-value in the hashtable
           commandBeginEndPair.put(commandIndex,this.commandBegginPositionIntext.get(this.commandBeginPos));
           this.commandBegginPositionIntext.removeLast();
           this.commandBeginPos--;
           commandIndex++;
       }
       else if (foro != null) {
           coeus2Java.put(ps2String(foro.getCoeusString2()), foro.getJavaString2());
           coeus2description.put(ps2String(foro.getCoeusString2()), "") ;
           commandLocator.put(ps2String(foro.getCoeusString2()), commandIndex);
           jcommandLocator.put(foro.getJavaString2(), commandIndex);
           jcommandLocatorStart.put(foro.getJavaString2(),totalTextLineNumbers+1);
           jcommandLocatorEnd.put(foro.getJavaString2(),getTextLineNumbers(foro.getJavaString2()) );
           commandBeginEndPair.put(this.commandBegginPositionIntext.get(this.commandBeginPos), commandIndex);
           this.commandBegginPositionIntext.removeLast();
           this.commandBeginPos--;
           commandIndex++;
        }
       else if (iffo != null) {
           coeus2Java.put(ps2String(iffo.getCoeusString2()), iffo.getJavaString2());
           coeus2description.put(ps2String(iffo.getCoeusString2()), "") ;
           commandLocator.put(ps2String(iffo.getCoeusString2()), commandIndex);
           jcommandLocator.put(iffo.getJavaString2(), commandIndex);
           jcommandLocatorStart.put(iffo.getJavaString2(),totalTextLineNumbers);//exei metrhthei alloi de xreiazetai +1
           jcommandLocatorEnd.put(iffo.getJavaString2(),totalTextLineNumbers);//exei metrhthei alloi de xreiazetai +1
           commandBeginEndPair.put(this.commandBegginPositionIntext.get(this.commandBeginPos), commandIndex);
           this.commandBegginPositionIntext.removeLast();
           this.commandBeginPos--;
           commandIndex++;
        }
       else if (whio != null) {
           coeus2Java.put(ps2String(whio.getCoeusString2()), whio.getJavaString2());
           coeus2description.put(ps2String(whio.getCoeusString2()), "") ;
           commandLocator.put(ps2String(whio.getCoeusString2()), commandIndex);
           jcommandLocator.put(whio.getJavaString2(), commandIndex);
           jcommandLocatorStart.put(whio.getJavaString2(),totalTextLineNumbers+1);
           jcommandLocatorEnd.put(whio.getJavaString2(),getTextLineNumbers(whio.getJavaString2()) );
           commandBeginEndPair.put(this.commandBegginPositionIntext.get(this.commandBeginPos), commandIndex);
           this.commandBegginPositionIntext.removeLast();
           this.commandBeginPos--;
           commandIndex++;
        }
  }


  private int getTextLineNumbers(String s)
  {
    int i=0;
    for (int j=0;j<s.length();j++)
    {
      if (s.charAt(j)=='\n')
      {
       i++;
      }
    }
   return this.totalTextLineNumbers=this.totalTextLineNumbers+i;
  }

//  public void writeJavaProgramBW (RootNode rn,BufferedWriter  bw,String className)
//    {
//
//    Checks.constantsVariablesArraysChecks(rn);
//
//    ProgramObject po =rn.getLookup().lookup(ProgramObject.class);
//
//    printText(bw,po.getJavaString1(className),"\n");
//       if (rn.getChildren().getNodesCount() >= 0) {
//            for (Enumeration e=rn.getChildren().nodes(); e.hasMoreElements(); ) {
//                Node n = (Node)e.nextElement();
//
//                ArrayObject arro = n.getLookup().lookup(ArrayObject.class);
//                ConstantObject cono = n.getLookup().lookup(ConstantObject.class);
//                FunctionObject funo = n.getLookup().lookup(FunctionObject.class);
//                ProcedureObject proo = n.getLookup().lookup(ProcedureObject.class);
//                VariableObject varo = n.getLookup().lookup(VariableObject.class);
//
//
//
//               if (arro!=null)
//                    printText(bw,arro.getJavaString1(),mytab);
//               else if (cono!=null)
//                    printText(bw,cono.getJavaString1(),mytab);
//                else if (varo!=null)
//                    printText(bw,varo.getJavaString1(),mytab);
//               else if (funo!=null || proo!=null)
//                { CreateObjects(n,bw,className);}
//            }
//        }
//      printText(bw,po.getJavaString2(className),"");
//     }
//
//
//
//
//  private void CreateObjects(Node rn,BufferedWriter  bw,String className)
//  {
//       String offset="";
//
//       for (int off=0;off<tabs;off++)
//       offset=offset+mytab;
//
//
//        ArrayObject arro = rn.getLookup().lookup(ArrayObject.class);
//        ConstantObject cono = rn.getLookup().lookup(ConstantObject.class);
//        FunctionObject funo = rn.getLookup().lookup(FunctionObject.class);
//        ProcedureObject proo = rn.getLookup().lookup(ProcedureObject.class);
//        VariableObject varo = rn.getLookup().lookup(VariableObject.class);
//        ReadObject reado = rn.getLookup().lookup(ReadObject.class);
//        WriteObject writeo = rn.getLookup().lookup(WriteObject.class);
//        CallObject callo = rn.getLookup().lookup(CallObject.class);
//        ReturnObject reto = rn.getLookup().lookup(ReturnObject.class);
//        AssignObject asso = rn.getLookup().lookup(AssignObject.class);
//        DoWhileObject dwho = rn.getLookup().lookup(DoWhileObject.class);
//        ForObject foro = rn.getLookup().lookup(ForObject.class);
//        IfObject iffo = rn.getLookup().lookup(IfObject.class);
//        WhileObject whio = rn.getLookup().lookup(WhileObject.class);
//
//
//        if (arro != null) {
//            printText(bw,arro.getJavaString1(),offset);
//        } else if (cono != null) {
//            printText(bw,cono.getJavaString1(),offset);
//       } else if (varo != null) {
//            printText(bw,varo.getJavaString1(),offset);
//       } else if (reado!=null){
//                    printText(bw,reado.getJavaString1(),mytab);
//       }else if (writeo != null) {
//            printText(bw,writeo.getJavaString1(),offset);
//       }else if (callo != null) {
//            printText(bw,callo.getJavaString1(),offset);
//       }else if (reto != null) {
//            printText(bw,reto.getJavaString1(),offset);
//       }else if (asso != null) {
//            printText(bw,asso.getJavaString1(),offset);
//
//       } else if (funo != null) {
//            printText(bw,funo.getJavaString1(),"\n"+mytab);
//            tabs++;
//       } else if (proo != null) {
//            if (proo.getDispName().equalsIgnoreCase(CoeusProgram.MAIN_PROC))
//               printText(bw,proo.getJavaString1(className),"\n"+mytab);
//            else
//               printText(bw,proo.getJavaString1(null),"\n"+mytab);
//            tabs++;
//        }
//       else if (dwho != null) {
//            printText(bw,dwho.getJavaString1(),"\n"+mytab);
//            tabs++;
//        }
//       else if (foro != null) {
//            printText(bw,foro.getJavaString1(),"\n"+mytab);
//            tabs++;
//        }
//       else if (iffo != null) {
//            List<Node> ifChildren=rn.getChildren().snapshot();
//           for (int childIndex=0;childIndex<ifChildren.size();childIndex++)
//           {
//             printText(bw,iffo.getJavaString1(childIndex),"\n"+mytab);
//             tabs++;
//             CreateObjects(ifChildren.get(childIndex),bw,null);
//             tabs--;
//             printText(bw,iffo.getJavaString2(),"\n"+mytab);
//           }
//        }
//       else if (whio != null) {
//            printText(bw,whio.getJavaString1(),"\n"+mytab);
//            tabs++;
//        }
//
//        if (rn.getChildren().getNodesCount() >= 0 && iffo==null) {
//            for (Enumeration e=rn.getChildren().nodes(); e.hasMoreElements(); ) {
//                Node n = (Node)e.nextElement();
//                CreateObjects(n,bw,null);
//            }
//        }
//
//       if (funo != null) {
//             printText(bw,funo.getJavaString2(),"\n"+mytab);
//             tabs--;
//       } else if (proo != null) {
//             printText(bw,proo.getJavaString2(),"\n"+mytab);
//             tabs--;
//        }
//else if (dwho != null) {
//            printText(bw,dwho.getJavaString2(),"\n"+mytab);
//            tabs--;
//        }
//       else if (foro != null) {
//            printText(bw,foro.getJavaString2(),"\n"+mytab);
//            tabs--;
//        }
////       else if (iffo != null) {
////            printText(bw,iffo.getJavaString2(),"\n"+mytab);
////            tabs--;
////        }
//       else if (whio != null) {
//            printText(bw,whio.getJavaString2(),"\n"+mytab);
//            tabs--;
//        }
//  }


}
