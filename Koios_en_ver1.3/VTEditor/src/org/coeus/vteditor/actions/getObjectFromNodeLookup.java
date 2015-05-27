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



package org.coeus.vteditor.actions;

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
public class getObjectFromNodeLookup {

    private Object lookupObject=null;

    public Object getLookupObject() {
        return lookupObject;
    }

 public getObjectFromNodeLookup(Object object)
    {
    this.lookupObject=object;
    }

    public getObjectFromNodeLookup(Lookup lookup)
    {
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
            lookupObject=arro;
        }
        else if (cono != null) {
           lookupObject=cono;
        }
        else if (varo != null) {
            lookupObject=varo;
        }
        else if (reado != null) {
            lookupObject=reado;
        }
        else if (writeo != null) {
           lookupObject=writeo;
        }
        else if (callo != null) {
           lookupObject=callo;
        }
        else if (reto != null) {
            lookupObject=reto;
        }
        else if (dwho != null) {
            lookupObject=dwho;
        }
        else if (foro != null) {
           lookupObject=foro;
        }
        else if (iffo != null) {
           lookupObject=iffo;
        }
        else if (asso != null) {
           lookupObject=asso;
        }
        else if (whio != null) {
           lookupObject=whio;
        }
        else if (proo != null) {
            lookupObject=proo;
        }
        else if (funo != null) {
         lookupObject=funo;
        }
        else if (progo != null) {
         lookupObject=progo;
        }
    
    }



  
 public  Lookup createLookupFromObject ()
    {   Lookup returnLookup=null;

        if (this.lookupObject instanceof ArrayObject ) {
            ArrayObject arro1= (ArrayObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{arro1});
        }
        else if (this.lookupObject instanceof ConstantObject) {
            ConstantObject cono1=(ConstantObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{cono1});
        }
        else if (this.lookupObject instanceof VariableObject) {
            VariableObject varo1=(VariableObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{varo1});
        }
        else if (this.lookupObject instanceof  ReadObject) {
            ReadObject reado1=(ReadObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{reado1});
        }
        else if (this.lookupObject instanceof WriteObject) {
            WriteObject writeo1=(WriteObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{writeo1});
        }
        else if (this.lookupObject instanceof CallObject) {
            CallObject callo1=(CallObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{callo1});
        }
        else if (this.lookupObject instanceof ReturnObject) {
            ReturnObject reto1=(ReturnObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{reto1});
        }
        else if (this.lookupObject instanceof DoWhileObject) {
            DoWhileObject dwho1=(DoWhileObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{dwho1});
        }
        else if (this.lookupObject instanceof ForObject) {
            ForObject foro1=(ForObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{foro1});
        }
        else if (this.lookupObject instanceof IfObject) {
            IfObject iffo1=(IfObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{iffo1});
        }
        else if (this.lookupObject instanceof AssignObject) {
            AssignObject asso1= (AssignObject)this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{asso1});
        }
        else if (this.lookupObject instanceof WhileObject) {
            WhileObject whio1=(WhileObject)this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{whio1});
        }
        else if (this.lookupObject instanceof  ProcedureObject) {
             ProcedureObject proo1=(ProcedureObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{proo1});
        }
        else if (this.lookupObject instanceof FunctionObject) {
            FunctionObject funo1=(FunctionObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{funo1});
        }
        else if (this.lookupObject instanceof ProgramObject) {
            ProgramObject progo1=(ProgramObject) this.lookupObject;
            returnLookup=Lookups.fixed(new Object[]{progo1});
        }
        else
          returnLookup=Lookups.fixed();

    return returnLookup;
    }




}
