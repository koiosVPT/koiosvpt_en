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
import org.coeus.poclasses.ProcedureObject;
import org.coeus.poclasses.ReadObject;
import org.coeus.poclasses.ReturnObject;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.WhileObject;
import org.coeus.poclasses.WriteObject;
import org.openide.util.Lookup;

/**
 *
 * @author Jrd
 */
public class UpdateNodesObject {

    public UpdateNodesObject(Lookup inlookup)
    {
        ArrayObject arro = inlookup.lookup(ArrayObject.class);
        ConstantObject cono = inlookup.lookup(ConstantObject.class);
        FunctionObject funo = inlookup.lookup(FunctionObject.class);
        ProcedureObject proo = inlookup.lookup(ProcedureObject.class);
        VariableObject varo = inlookup.lookup(VariableObject.class);
        ReadObject reado = inlookup.lookup(ReadObject.class);
        WriteObject writeo = inlookup.lookup(WriteObject.class);
        CallObject callo = inlookup.lookup(CallObject.class);
        ReturnObject reto = inlookup.lookup(ReturnObject.class);
        AssignObject asso = inlookup.lookup(AssignObject.class);
        DoWhileObject dwho = inlookup.lookup(DoWhileObject.class);
        ForObject foro = inlookup.lookup(ForObject.class);
        WhileObject whio = inlookup.lookup(WhileObject.class);

        if (arro != null) {
          arro.UpdateArrayObject();
        } else if (cono != null) {
          cono.UpdateConstantObject();
        } else if (funo != null) {
           funo.UpdateFunctionObject();
        } else if (proo != null) {
           proo.UpdateProcedureObject();
        } else if (varo != null) {
         varo.UpdateVariableObject();
        }
        else if (reado != null) {
         reado.UpdateReadObject();
        }
         else if (writeo != null) {
         writeo.UpdateWriteObject();
        }
        else if (callo != null) {
         callo.UpdateCallObject();
        }
        else if (reto != null) {
         reto.UpdateReturnObject();
        }
        else if (asso != null) {
         asso.UpdateAssignObject();
        }
        else if (dwho != null) {
         dwho.UpdateDoWhileObject();
        }
        else if (foro != null) {
         foro.UpdateForObject();
        }
        else if (whio != null) {
         whio.UpdateWhileObject();
        }
    }
  
}
