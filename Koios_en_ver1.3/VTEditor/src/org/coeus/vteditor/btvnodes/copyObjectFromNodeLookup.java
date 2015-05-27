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

import java.util.LinkedList;


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
import org.coeus.wizards.AllObjects;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;




/**
 *
 * @author Jrd
 */
public class copyObjectFromNodeLookup {

    private Object lookupObject=null;

    public Object getLookupObject() {
        return lookupObject;
    }

 public copyObjectFromNodeLookup(Object object)
    {
    this.lookupObject=object;
    }

    public copyObjectFromNodeLookup(Lookup lookup)
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
            ArrayObject arro1 = new ArrayObject(arro);
            lookupObject=arro1;
        }
        else if (cono != null) {
            ConstantObject cono1 = new ConstantObject(cono);
           lookupObject=cono1;
        }
        else if (varo != null) {
            VariableObject varo1 = new VariableObject(varo);
            lookupObject=varo1;
        }
        else if (reado != null) {
            ReadObject reado1 = new ReadObject(reado);
            lookupObject=reado1;
        }
        else if (writeo != null) {
            WriteObject writeo1 = new WriteObject(writeo);
           lookupObject=writeo1;
        }
        else if (callo != null) {
            CallObject callo1 = new CallObject(callo);
           lookupObject=callo1;
        }
        else if (reto != null) {
            ReturnObject reto1 = new ReturnObject(reto);
            lookupObject=reto1;
        }
        else if (dwho != null) {
            DoWhileObject dwho1 = new DoWhileObject(dwho);
            lookupObject=dwho1;
        }
        else if (foro != null) {
            ForObject foro1 = new ForObject(foro);
           lookupObject=foro1;
        }
        else if (iffo != null) {
            IfObject iffo1 = new IfObject(iffo);
           lookupObject=iffo1;
        }
        else if (asso != null) {
            AssignObject asso1 = new AssignObject(asso);
           lookupObject=asso1;
        }
        else if (whio != null) {
            WhileObject whio1 = new WhileObject(whio);
           lookupObject=whio1;
        }
        else if (proo != null) {
            ProcedureObject proo1 = new ProcedureObject(proo);
            lookupObject=proo1;
        }
        else if (funo != null) {
            FunctionObject funo1 = new FunctionObject(funo);
         lookupObject=funo1;
        }
        else if (progo != null) {
            ProgramObject progo1 = new ProgramObject(progo);
            lookupObject=progo1;
        }
    
    }



  
 public  Lookup createLookupFromObject (LinkedList<ConstantObject> iconl,
    LinkedList <VariableObject> ivarl,LinkedList <ArrayObject> iarrl,
    LinkedList <FunctionObject> ifunl,LinkedList <ProcedureObject> iprol,
    LinkedList <ReadObject> ireadl,LinkedList <WriteObject> iwritel,
    LinkedList <CallObject> icalll,LinkedList <ReturnObject> iretl,
    LinkedList <AssignObject> iassl,LinkedList <DoWhileObject> idwhl,
    LinkedList <ForObject> iforl,LinkedList<IfObject> iiffl,
    LinkedList <WhileObject> iwhil,LinkedList<AllObjects> aoll)
    {   Lookup returnLookup=null;

        if (this.lookupObject instanceof ArrayObject ) {
            ArrayObject arro1= new ArrayObject((ArrayObject) this.lookupObject);
            iarrl.add(arro1);
             AllObjects ao =new AllObjects(arro1.getDispCateg(),arro1.getDispName(),arro1.getDispType(),
       arro1.getObjName(),arro1.getObjType(),null,arro1.getObjScope(),arro1.getDispScope(),null,null,null,null,
       arro1.getDim1Size(),arro1.getDim2Size());
             aoll.add(ao);
             returnLookup=Lookups.fixed(new Object[]{arro1});
        }
        else if (this.lookupObject instanceof ConstantObject) {
            ConstantObject cono1=new ConstantObject((ConstantObject) this.lookupObject);
            iconl.add(cono1);
            AllObjects ao =new AllObjects(cono1.getDispCateg(),cono1.getDispName(),
            cono1.getDispType(),cono1.getObjName(),cono1.getObjType(),cono1.getObjValue(),
                    cono1.getObjScope(),cono1.getDispScope(),null,null,null,null,null,null);
             aoll.add(ao);            returnLookup=Lookups.fixed(new Object[]{cono1});
        }
        else if (this.lookupObject instanceof VariableObject) {
            VariableObject varo1=new VariableObject((VariableObject) this.lookupObject);
            ivarl.add(varo1);
            AllObjects ao =new AllObjects(varo1.getDispCateg(),varo1.getDispName(),
            varo1.getDispType(),varo1.getObjName(),varo1.getObjType(),varo1.getObjValue(),
                    varo1.getObjScope(),varo1.getDispScope(),null,null,null,null,null,null);
             aoll.add(ao);
            returnLookup=Lookups.fixed(new Object[]{varo1});
        }
        else if (this.lookupObject instanceof  ReadObject) {
            ReadObject reado1=new ReadObject((ReadObject) this.lookupObject);
            ireadl.add(reado1);
            AllObjects ao =new AllObjects(reado1.getDispCateg(),reado1.getDispName(),null,
       reado1.getObjName(),null,null,reado1.getObjScope(),reado1.getDispScope(),null,null,null,null,null,null);
             aoll.add(ao);
             returnLookup=Lookups.fixed(new Object[]{reado1});
        }
        else if (this.lookupObject instanceof WriteObject) {
            WriteObject writeo1=new WriteObject((WriteObject) this.lookupObject);
             iwritel.add(writeo1);
            AllObjects ao =new AllObjects(writeo1.getDispCateg(),writeo1.getDispName(),null,
       writeo1.getObjName(),null,null,writeo1.getObjScope(),writeo1.getDispScope(),null,null,null,null,null,null);
             aoll.add(ao);
            returnLookup=Lookups.fixed(new Object[]{writeo1});
        }
        else if (this.lookupObject instanceof CallObject) {
            CallObject callo1=new CallObject((CallObject) this.lookupObject);
            icalll.add(callo1);
            AllObjects ao =new AllObjects(callo1.getDispCateg(),callo1.getDispName(),null,
            callo1.getObjName(),null,null,callo1.getObjScope(),callo1.getDispScope(),null,null,null,null,null,null);
             aoll.add(ao);
             returnLookup=Lookups.fixed(new Object[]{callo1});
        }
        else if (this.lookupObject instanceof ReturnObject) {
            ReturnObject reto1=new ReturnObject((ReturnObject) this.lookupObject);
            iretl.add(reto1);
            AllObjects ao =new AllObjects(reto1.getDispCateg(),reto1.getDispName(),null,
       reto1.getObjName(),null,null,reto1.getObjScope(),reto1.getDispScope(),null,null,null,null,null,null);
             aoll.add(ao);
             returnLookup=Lookups.fixed(new Object[]{reto1});
        }
        else if (this.lookupObject instanceof DoWhileObject) {
            DoWhileObject dwho1=new DoWhileObject((DoWhileObject) this.lookupObject);
            idwhl.add(dwho1);
            AllObjects ao =new AllObjects(dwho1.getDispCateg(),dwho1.getDispName(),null,
       dwho1.getObjName(),null,null,dwho1.getObjScope(),dwho1.getDispScope(),null,null,null,null,null,null);
             aoll.add(ao);
             returnLookup=Lookups.fixed(new Object[]{dwho1});
        }
        else if (this.lookupObject instanceof ForObject) {
            ForObject foro1=new ForObject((ForObject) this.lookupObject);
            iforl.add(foro1);
            AllObjects ao =new AllObjects(foro1.getDispCateg(),foro1.getDispName(),null,
       foro1.getObjName(),null,null,foro1.getObjScope(),foro1.getDispScope(),null,null,null,null,null,null);
             aoll.add(ao);
             returnLookup=Lookups.fixed(new Object[]{foro1});
        }
        else if (this.lookupObject instanceof IfObject) {
            IfObject iffo1=new IfObject((IfObject) this.lookupObject);
             iiffl.add(iffo1);
            AllObjects ao =new AllObjects(iffo1.getDispCateg(),iffo1.getDispName(),null,
       iffo1.getObjName(),null,null,iffo1.getObjScope(),iffo1.getDispScope(),null,null,null,null,null,null);
             aoll.add(ao);
             returnLookup=Lookups.fixed(new Object[]{iffo1});
        }
        else if (this.lookupObject instanceof AssignObject) {
            AssignObject asso1= new AssignObject((AssignObject)this.lookupObject);
            iassl.add(asso1);
             AllObjects ao =new AllObjects(asso1.getDispCateg(),asso1.getDispName(),null,
       asso1.getObjName(),null,null,asso1.getObjScope(),asso1.getDispScope(),null,null,null,null,null,null);
             aoll.add(ao);
             returnLookup=Lookups.fixed(new Object[]{asso1});
        }
        else if (this.lookupObject instanceof WhileObject) {
            WhileObject whio1=new WhileObject((WhileObject)this.lookupObject);
            iwhil.add(whio1);
            AllObjects ao =new AllObjects(whio1.getDispCateg(),whio1.getDispName(),null,
       whio1.getObjName(),null,null,whio1.getObjScope(),whio1.getDispScope(),null,null,null,null,null,null);
             aoll.add(ao);
             returnLookup=Lookups.fixed(new Object[]{whio1});
        }
        else if (this.lookupObject instanceof  ProcedureObject) {
             ProcedureObject proo1=new ProcedureObject((ProcedureObject) this.lookupObject);
     iprol.add(proo1);
            AllObjects ao =new AllObjects(proo1.getDispCateg(),proo1.getDispName(),null,
       proo1.getObjName(),null,null,proo1.getObjScope(),proo1.getDispScope(),
             proo1.getDispParams(),proo1.getObjParams(),proo1.getDispParamsTypes(),
             proo1.getObjParamsTypes(),null,null);
             aoll.add(ao);
            returnLookup=Lookups.fixed(new Object[]{proo1});
        }
        else if (this.lookupObject instanceof FunctionObject) {
            FunctionObject funo1=new FunctionObject((FunctionObject) this.lookupObject);
            ifunl.add(funo1);
            AllObjects ao =new AllObjects(funo1.getDispCateg(),funo1.getDispName(),
              funo1.getDispType(),funo1.getObjName(),funo1.getObjType(),null,
              funo1.getObjScope(),funo1.getDispScope(),funo1.getDispParams(),funo1.getObjParams(),
       funo1.getDispParamsTypes(),funo1.getObjParamsTypes(),null,null);
             aoll.add(ao);
            returnLookup=Lookups.fixed(new Object[]{funo1});
        }
        else if (this.lookupObject instanceof ProgramObject) {
            ProgramObject progo1=new ProgramObject((ProgramObject) this.lookupObject);
            returnLookup=Lookups.fixed(new Object[]{progo1});
        }
        else
          returnLookup=Lookups.fixed();

    return returnLookup;
    }




}
