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
import org.coeus.poclasses.ProgramObject;
import org.coeus.poclasses.ReadObject;
import org.coeus.poclasses.ReturnObject;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.WhileObject;
import org.coeus.poclasses.WriteObject;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Jrd
 */
public class getLookupFromNode {
    private static Lookup lookup=null;


   public getLookupFromNode(PCNodeParent node)
    {
     lookup=node.getLookup();
    }

   public getLookupFromNode(PCNodeLeaf node)
    {
    lookup=node.getLookup();
    }

    public getLookupFromNode(Lookup inLookup)
    {
    lookup=inLookup;
    }

     public static Lookup createLookupFromObject ()
    {  Lookup returnLookup=null;
        ProgramObject progo= lookup.lookup(ProgramObject.class);

        ArrayObject arro = lookup.lookup(ArrayObject.class);
        ConstantObject cono = lookup.lookup(ConstantObject.class);
        VariableObject varo = lookup.lookup(VariableObject.class);
        ReadObject reado = lookup.lookup(ReadObject.class);
        WriteObject writeo = lookup.lookup(WriteObject.class);
        CallObject callo = lookup.lookup(CallObject.class);
        ReturnObject reto = lookup.lookup(ReturnObject.class);
        AssignObject asso = lookup.lookup(AssignObject.class);
        DoWhileObject dwho = lookup.lookup(DoWhileObject.class);
        ForObject foro = lookup.lookup(ForObject.class);
        IfObject iffo = lookup.lookup(IfObject.class);
        WhileObject whio = lookup.lookup(WhileObject.class);

        ProcedureObject proo = lookup.lookup(ProcedureObject.class);
        FunctionObject funo = lookup.lookup(FunctionObject.class);


        if (arro != null) {
            ArrayObject arro1=new ArrayObject (arro);
            returnLookup=Lookups.fixed(new Object[]{arro1});
        }
        else if (cono != null) {
            ConstantObject cono1=new ConstantObject (cono);
            returnLookup=Lookups.fixed(new Object[]{cono1});
        }
        else if (varo != null) {
            VariableObject varo1=new VariableObject(varo);
            returnLookup=Lookups.fixed(new Object[]{varo1});
        }
        else if (reado != null) {
            ReadObject reado1=new ReadObject (reado);
            returnLookup=Lookups.fixed(new Object[]{reado1});
        }
        else if (writeo != null) {
            WriteObject writeo1=new WriteObject (writeo);
            returnLookup=Lookups.fixed(new Object[]{writeo1});
        }
        else if (callo != null) {
            CallObject callo1=new  CallObject (callo);
            returnLookup=Lookups.fixed(new Object[]{callo1});
        }
        else if (reto != null) {
            ReturnObject reto1=new ReturnObject (reto);
            returnLookup=Lookups.fixed(new Object[]{reto1});
        }
        else if (dwho != null) {
            DoWhileObject dwho1=new DoWhileObject (dwho);
            returnLookup=Lookups.fixed(new Object[]{dwho1});
        }
        else if (foro != null) {
            ForObject foro1=new ForObject (foro);
            returnLookup=Lookups.fixed(new Object[]{foro1});
        }
        else if (iffo != null) {
            IfObject iffo1=new IfObject (iffo);
            returnLookup=Lookups.fixed(new Object[]{iffo1});
        }
        else if (asso != null) {
            AssignObject asso1=new AssignObject (asso);
            returnLookup=Lookups.fixed(new Object[]{asso1});
        }
        else if (whio != null) {
            WhileObject whio1=new WhileObject (whio);
            returnLookup=Lookups.fixed(new Object[]{whio1});
        }
        else if (proo != null) {
             ProcedureObject proo1=new  ProcedureObject (proo);
            returnLookup=Lookups.fixed(new Object[]{proo1});
        }
        else if (funo != null) {
            FunctionObject funo1= new FunctionObject (funo);
            returnLookup=Lookups.fixed(new Object[]{funo1});
        }
        else if (progo != null) {
            ProgramObject progo1= new ProgramObject (progo);
            returnLookup=Lookups.fixed(new Object[]{progo1});
        }
        else
          returnLookup=Lookups.fixed();

    return returnLookup;
    }


}
