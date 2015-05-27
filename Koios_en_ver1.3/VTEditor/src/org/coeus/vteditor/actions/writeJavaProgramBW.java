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


///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package org.coeus.vteditor.actions;
//
//import java.io.IOException;
//import java.io.BufferedWriter ;
//import java.util.Enumeration;
//import java.util.List;
//import org.coeus.vteditor.btvnodes.RootNode;
//import org.coeus.poclasses.ArrayObject;
//import org.coeus.poclasses.AssignObject;
//import org.coeus.poclasses.CallObject;
//import org.coeus.poclasses.CoeusProgram;
//import org.coeus.poclasses.ConstantObject;
//import org.coeus.poclasses.DoWhileObject;
//import org.coeus.poclasses.ForObject;
//import org.coeus.poclasses.FunctionObject;
//import org.coeus.poclasses.IfObject;
//import org.coeus.poclasses.ProcedureObject;
//import org.coeus.poclasses.ProgramObject;
//import org.coeus.poclasses.ReadObject;
//import org.coeus.poclasses.ReturnObject;
//import org.coeus.poclasses.VariableObject;
//import org.coeus.poclasses.WhileObject;
//import org.coeus.poclasses.WriteObject;
//import org.coeus.vteditor.Checks;
//import org.openide.nodes.Node;
//import org.openide.util.Exceptions;
//
///**
// *
// * @author Jrd
// */
//public class writeJavaProgramBW {
//
//    String mytab="     ";
//    static int tabs=1;
//    public writeJavaProgramBW (RootNode rn,BufferedWriter  bw,String className)
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
//
//
//  private void printText (BufferedWriter  bw,String text,String offset)
//{
//        try {
//            bw.write(offset + text);
//        } catch (IOException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//       offset="";
//}
//
//
//
//
//}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coeus.vteditor.actions;

import java.io.IOException;
import java.io.BufferedWriter ;
import java.util.Enumeration;
import java.util.List;
import org.coeus.vteditor.btvnodes.RootNode;
import org.coeus.poclasses.ArrayObject;
import org.coeus.poclasses.AssignObject;
import org.coeus.poclasses.CallObject;
import org.coeus.poclasses.CoeusProgram;
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
import org.coeus.vteditor.Checks;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Jrd
 */
public class writeJavaProgramBW {

    String mytab="     ";
    static int tabs=1;
    public writeJavaProgramBW (RootNode rn,BufferedWriter  bw,String className)
    {

    Checks.constantsVariablesArraysChecks(rn);

    ProgramObject po =rn.getLookup().lookup(ProgramObject.class);

    printText(bw,po.getJavaString1(className),"");
       if (rn.getChildren().getNodesCount() >= 0) {
            for (Enumeration e=rn.getChildren().nodes(); e.hasMoreElements(); ) {
                Node n = (Node)e.nextElement();

                ArrayObject arro = n.getLookup().lookup(ArrayObject.class);
                ConstantObject cono = n.getLookup().lookup(ConstantObject.class);
                FunctionObject funo = n.getLookup().lookup(FunctionObject.class);
                ProcedureObject proo = n.getLookup().lookup(ProcedureObject.class);
                VariableObject varo = n.getLookup().lookup(VariableObject.class);



               if (arro!=null)
                    printText(bw,arro.getJavaString1(),mytab);
               else if (cono!=null)
                    printText(bw,cono.getJavaString1(),mytab);
                else if (varo!=null)
                    printText(bw,varo.getJavaString1(),mytab);
               else if (funo!=null || proo!=null)
                { CreateObjects(n,bw,className);}
            }
        }
      printText(bw,po.getJavaString2(className),"");
     }




  private void CreateObjects(Node rn,BufferedWriter  bw,String className)
  {
       String offset="";

       for (int off=0;off<tabs;off++)
       offset=offset+mytab;


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
            printText(bw,arro.getJavaString1(),offset);
        } else if (cono != null) {
            printText(bw,cono.getJavaString1(),offset);
       } else if (varo != null) {
            printText(bw,varo.getJavaString1(),offset);
       } else if (reado!=null){
                    printText(bw,reado.getJavaString1(),mytab);
       }else if (writeo != null) {
            printText(bw,writeo.getJavaString1(),offset);
       }else if (callo != null) {
            printText(bw,callo.getJavaString1(),offset);
       }else if (reto != null) {
            printText(bw,reto.getJavaString1(),offset);
       }else if (asso != null) {
            printText(bw,asso.getJavaString1(),offset);

       } else if (funo != null) {
            printText(bw,funo.getJavaString1(),mytab);
            tabs++;
       } else if (proo != null) {
            if (proo.getDispName().equalsIgnoreCase(CoeusProgram.MAIN_PROC))
               printText(bw,proo.getJavaString1(className),mytab);
            else
               printText(bw,proo.getJavaString1(null),mytab);
            tabs++;
        }
       else if (dwho != null) {
            printText(bw,dwho.getJavaString1(),mytab);
            tabs++;
        }
       else if (foro != null) {
            printText(bw,foro.getJavaString1(),mytab);
            tabs++;
        }
       else if (iffo != null) {
            List<Node> ifChildren=rn.getChildren().snapshot();
           for (int childIndex=0;childIndex<ifChildren.size();childIndex++)
           {
             printText(bw,iffo.getJavaString1(childIndex),mytab);
             tabs++;
             CreateObjects(ifChildren.get(childIndex),bw,null);
             tabs--;
             printText(bw,iffo.getJavaString2(),mytab);
           }
        }
       else if (whio != null) {
            printText(bw,whio.getJavaString1(),mytab);
            tabs++;
        }

        if (rn.getChildren().getNodesCount() >= 0 && iffo==null) {
            for (Enumeration e=rn.getChildren().nodes(); e.hasMoreElements(); ) {
                Node n = (Node)e.nextElement();
                CreateObjects(n,bw,null);
            }
        }

       if (funo != null) {
             printText(bw,funo.getJavaString2(),mytab);
             tabs--;
       } else if (proo != null) {
             printText(bw,proo.getJavaString2(),mytab);
             tabs--;
        }
else if (dwho != null) {
            printText(bw,dwho.getJavaString2(),mytab);
            tabs--;
        }
       else if (foro != null) {
            printText(bw,foro.getJavaString2(),mytab);
            tabs--;
        }
//       else if (iffo != null) {
//            printText(bw,iffo.getJavaString2(),mytab);
//            tabs--;
//        }
       else if (whio != null) {
            printText(bw,whio.getJavaString2(),mytab);
            tabs--;
        }
  }


  private void printText (BufferedWriter  bw,String text,String offset)
{
        try {
            bw.write(offset + text);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
       offset="";
}




}
