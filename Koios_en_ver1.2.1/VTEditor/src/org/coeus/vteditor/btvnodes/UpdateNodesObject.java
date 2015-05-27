/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
