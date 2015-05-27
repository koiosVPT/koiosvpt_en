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
package org.coeus.vteditor.btvnodes;


import org.coeus.poclasses.ArrayObject;
import org.coeus.poclasses.AssignObject;
import org.coeus.poclasses.CallObject;
import org.coeus.poclasses.ConstantObject;
import org.coeus.poclasses.DoWhileObject;
import org.coeus.poclasses.ForObject;
import org.coeus.poclasses.FunctionObject;
import org.coeus.poclasses.IfObject;
import org.coeus.poclasses.ProcedureObject;
import org.coeus.poclasses.ReadObject;
import org.coeus.poclasses.ReturnObject;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.WhileObject;
import org.coeus.poclasses.WriteObject;


/**
 *
 * @author Jrd
 */
public class SetNodeAttFromObject {

    
    private static String path ="org/coeus/btvpalette/pngs/items/";
    private static String mytab = "                 ";



    public static String[] SetNodeLeafAttributes(PCNodeLeaf innode) {
        String [] retAttrib= new String [5];
        ArrayObject arro = innode.getLookup().lookup(ArrayObject.class);
        ConstantObject cono = innode.getLookup().lookup(ConstantObject.class);
        VariableObject varo = innode.getLookup().lookup(VariableObject.class);
        ReadObject reado = innode.getLookup().lookup(ReadObject.class);
        WriteObject writeo = innode.getLookup().lookup(WriteObject.class);
        CallObject callo = innode.getLookup().lookup(CallObject.class);
        ReturnObject reto = innode.getLookup().lookup(ReturnObject.class);
        AssignObject asso = innode.getLookup().lookup(AssignObject.class);



        if (arro != null) {
            innode.setDisplayName(mytab+arro.getDispName()+" - "+arro.getDimension()+"-D:"+arro.getDispType());
            innode.setIconBaseWithExtension(path+"array.png");
            //arro.setObjScope(innode.getObjScope());
            //arro.setDispScope(innode.getDispScope());
            retAttrib[0]="arr";
            retAttrib[1]=arro.getDispCateg();
            retAttrib[2]=arro.getDispName();
            retAttrib[3]=arro.getDispType();
            //retAttrib[4]=arro.getObjValue();
        } else if (cono != null) {
            innode.setDisplayName(mytab+cono.getDispName()+" = "+cono.getObjValue());
            innode.setIconBaseWithExtension(path+"con1.png");
            //cono.setObjScope(innode.getObjScope());
            //cono.setDispScope(innode.getDispScope());
            retAttrib[0]="con";
            retAttrib[1]=cono.getDispCateg();
            retAttrib[2]=cono.getDispName();
            retAttrib[3]=cono.getDispType();
            retAttrib[4]=cono.getObjValue();
        } else if (varo != null) {
            String val="";
            if (!varo.getObjValue().isEmpty())
             val=" = "+varo.getObjValue();

            innode.setDisplayName(mytab+varo.getDispName()+val);
            innode.setIconBaseWithExtension(path+"var.png");
            //varo.setObjScope(innode.getObjScope());
            //varo.setDispScope(innode.getDispScope());
            retAttrib[0]="var";
            retAttrib[1]=varo.getDispCateg();
            retAttrib[2]=varo.getDispName();
            retAttrib[3]=varo.getDispType();
            retAttrib[4]=varo.getObjValue();
        }
        else if (reado != null) {
            innode.setDisplayName(mytab+reado.getDispName());
            innode.setIconBaseWithExtension(path+"read.png");
            //varo.setObjScope(innode.getObjScope());
            //varo.setDispScope(innode.getDispScope());
            retAttrib[0]="rea";
            retAttrib[1]=reado.getDispCateg();
            retAttrib[2]=reado.getDispName();
            retAttrib[3]=null;//reado.getDispType();
            retAttrib[4]=null;//reado.getObjValue();
        }
        else if (writeo != null) {
            innode.setDisplayName(mytab+writeo.getDispName());
            innode.setIconBaseWithExtension(path+"write.png");
            //varo.setObjScope(innode.getObjScope());
            //varo.setDispScope(innode.getDispScope());
            retAttrib[0]="wri";
            retAttrib[1]=writeo.getDispCateg();
            retAttrib[2]=writeo.getDispName();
            retAttrib[3]=null;//writeo.getDispType();
            retAttrib[4]=null;//writeo.getObjValue();
        }
        else if (callo != null) {
            innode.setDisplayName(mytab+callo.getDispName());
            innode.setIconBaseWithExtension(path+"call.png");
            //varo.setObjScope(innode.getObjScope());
            //varo.setDispScope(innode.getDispScope());
            retAttrib[0]="cal";
            retAttrib[1]=callo.getDispCateg();
            retAttrib[2]=callo.getDispName();
            retAttrib[3]=null;//callo.getDispType();
            retAttrib[4]=null;//callo.getObjValue();
        }
        else if (reto != null) {
            innode.setDisplayName(mytab+reto.getDispName());
            innode.setIconBaseWithExtension(path+"return.png");
            //varo.setObjScope(innode.getObjScope());
            //varo.setDispScope(innode.getDispScope());
            retAttrib[0]="ret";
            retAttrib[1]=reto.getDispCateg();
            retAttrib[2]=reto.getDispName();
            retAttrib[3]=null;//callo.getDispType();
            retAttrib[4]=null;//callo.getObjValue();
        }
        else if (asso != null) {
            innode.setDisplayName(mytab+asso.getDispName());
            innode.setIconBaseWithExtension(path+"assign.png");
            //varo.setObjScope(innode.getObjScope());
            //varo.setDispScope(innode.getDispScope());
            retAttrib[0]="ass";
            retAttrib[1]=asso.getDispCateg();
            retAttrib[2]=asso.getDispName();
            retAttrib[3]=null;//callo.getDispType();
            retAttrib[4]=null;//callo.getObjValue();
        }

     return retAttrib;
    }

 public static String[] SetNodeParentAttributes(PCNodeParent innode) {
        String [] retAttrib=new String [5];
        FunctionObject funo = innode.getLookup().lookup(FunctionObject.class);
        ProcedureObject proo = innode.getLookup().lookup(ProcedureObject.class);
        DoWhileObject dwho = innode.getLookup().lookup(DoWhileObject.class);
        ForObject foro = innode.getLookup().lookup(ForObject.class);
        IfObject iffo = innode.getLookup().lookup(IfObject.class);
        WhileObject whio = innode.getLookup().lookup(WhileObject.class);
     

         if (funo != null) {
            innode.setObjScope(funo.getObjScope());
            innode.setDispScope(funo.getDispScope());
            retAttrib[0]="fun";
            retAttrib[1]=funo.getDispCateg();
            retAttrib[2]=funo.getDispName();
            retAttrib[3]=funo.getDispType();
            retAttrib[4]="";
            innode.setDisplayName(mytab+funo.getDispType()+" "+funo.getDispName()+" ("+ funo.getDispParams2Show() +" )");
            innode.setIconBaseWithExtension(path+"func.png");
         }
         else if (proo != null) {
            innode.setObjScope(proo.getObjScope());
            innode.setDispScope(proo.getDispScope());
            retAttrib[0]="pro";
            retAttrib[1]=proo.getDispCateg();
            retAttrib[2]=proo.getDispName();
            retAttrib[3]="";
            retAttrib[4]="";
            innode.setDisplayName(mytab+proo.getDispName()+" ("+ proo.getDispParams2Show() +" )");
            innode.setIconBaseWithExtension(path+"proc.png");
          } 
         else if (dwho != null) {
            //innode.setObjScope(proo.getObjScope());
            //innode.setDispScope(proo.getDispScope());
            retAttrib[0]="dwh";
            retAttrib[1]=dwho.getDispCateg();
            retAttrib[2]=dwho.getDispName();
            retAttrib[3]="";
            retAttrib[4]="";
            innode.setDisplayName(mytab+dwho.getDispName());
            innode.setIconBaseWithExtension(path+"doWhile.png");
          }
         else if (iffo != null) {
            //innode.setObjScope(proo.getObjScope());
            //innode.setDispScope(proo.getDispScope());
            retAttrib[0]="iff";
            retAttrib[1]=iffo.getDispCateg();
            retAttrib[2]=iffo.getDispName();
            retAttrib[3]="";
            retAttrib[4]="";
            innode.setDisplayName(mytab+iffo.getDispName());
            innode.setIconBaseWithExtension(path+"if_then_else.png");
          }
         else if (foro != null) {
            //innode.setObjScope(proo.getObjScope());
            //innode.setDispScope(proo.getDispScope());
            retAttrib[0]="for";
            retAttrib[1]=foro.getDispCateg();
            retAttrib[2]=foro.getDispName();
            retAttrib[3]="";
            retAttrib[4]="";
            innode.setDisplayName(mytab+foro.getDispName());
            innode.setIconBaseWithExtension(path+"for.png");
          }
         else if (whio != null) {
            //innode.setObjScope(proo.getObjScope());
            //innode.setDispScope(proo.getDispScope());
            retAttrib[0]="dwh";
            retAttrib[1]=whio.getDispCateg();
            retAttrib[2]=whio.getDispName();
            retAttrib[3]="";
            retAttrib[4]="";
            innode.setDisplayName(mytab+whio.getDispName());
            innode.setIconBaseWithExtension(path+"while.png");
          }
     return retAttrib;
    }


}
