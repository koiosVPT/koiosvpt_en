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
import org.coeus.poclasses.IfObject;
import org.coeus.poclasses.ReadObject;
import org.coeus.poclasses.ReturnObject;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.WhileObject;
import org.coeus.poclasses.WriteObject;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;
import org.openide.util.Lookup;

/**
 *
 * @author Jrd
 */
public class UpdateScope {

String s;

    public static Lookup UpdateScope (Lookup inlookup,String iDispScope,String iObjScope)
    { 
        ArrayObject arro = inlookup.lookup(ArrayObject.class);
        ConstantObject cono = inlookup.lookup(ConstantObject.class);
        VariableObject varo = inlookup.lookup(VariableObject.class);
        ReadObject reado = inlookup.lookup(ReadObject.class);
        WriteObject writeo = inlookup.lookup(WriteObject.class);
        CallObject callo = inlookup.lookup(CallObject.class);
        ReturnObject reto = inlookup.lookup(ReturnObject.class);
        AssignObject asso = inlookup.lookup(AssignObject.class);
        DoWhileObject dwho = inlookup.lookup(DoWhileObject.class);
        ForObject foro = inlookup.lookup(ForObject.class);
        IfObject iffo = inlookup.lookup(IfObject.class);
        WhileObject whio = inlookup.lookup(WhileObject.class);

        if (arro != null) {
           AllObjectsList caol = AllObjectsList.getAllObjList();
           AllObjects ao = caol.SearchByDisplayName_DispScope(arro.getDispName(),arro.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           arro.setDispScope(iDispScope);
           arro.setObjScope(iObjScope);         
        } else if (cono != null) {
           AllObjectsList caol = AllObjectsList.getAllObjList();
           AllObjects ao = caol.SearchByDisplayName_DispScope(cono.getDispName(),cono.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           cono.setDispScope(iDispScope);
           cono.setObjScope(iObjScope);
        } else if (varo != null) {
           AllObjectsList caol = AllObjectsList.getAllObjList();
           AllObjects ao = caol.SearchByDisplayName_DispScope(varo.getDispName(),varo.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           varo.setDispScope(iDispScope);
           varo.setObjScope(iObjScope);
        }else if (reado != null) {
           AllObjectsList caol = AllObjectsList.getAllObjList();
           AllObjects ao = caol.SearchByObjectName_DispScope(reado.getObjName(),reado.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           reado.setDispScope(iDispScope);
           reado.setObjScope(iObjScope);
        }else if (writeo != null) {
           AllObjectsList caol = AllObjectsList.getAllObjList();
           AllObjects ao = caol.SearchByObjectName_DispScope(writeo.getObjName(),writeo.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           writeo.setDispScope(iDispScope);
           writeo.setObjScope(iObjScope);
        }
        else if (callo != null) {
           AllObjectsList caol = AllObjectsList.getAllObjList();
           AllObjects ao = caol.SearchByObjectName_DispScope(callo.getObjName(),callo.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           callo.setDispScope(iDispScope);
           callo.setObjScope(iObjScope);
        }
        else if (reto != null) {
           AllObjectsList raol = AllObjectsList.getAllObjList();
           AllObjects ao = raol.SearchByObjectName_DispScope(reto.getObjName(),reto.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           reto.setDispScope(iDispScope);
           reto.setObjScope(iObjScope);
        }
        else if (dwho != null) {
           AllObjectsList aaol = AllObjectsList.getAllObjList();
           AllObjects ao = aaol.SearchByObjectName_DispScope(dwho.getObjName(),dwho.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           dwho.setDispScope(iDispScope);
           dwho.setObjScope(iObjScope);
        }
        else if (foro != null) {
           AllObjectsList aaol = AllObjectsList.getAllObjList();
           AllObjects ao = aaol.SearchByObjectName_DispScope(foro.getObjName(),foro.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           foro.setDispScope(iDispScope);
           foro.setObjScope(iObjScope);
        }
        else if (iffo != null) {
           AllObjectsList aaol = AllObjectsList.getAllObjList();
           AllObjects ao = aaol.SearchByObjectName_DispScope(iffo.getObjName(),iffo.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           iffo.setDispScope(iDispScope);
           iffo.setObjScope(iObjScope);
        }
        else if (asso != null) {
           AllObjectsList aaol = AllObjectsList.getAllObjList();
           AllObjects ao = aaol.SearchByObjectName_DispScope(asso.getObjName(),asso.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           asso.setDispScope(iDispScope);
           asso.setObjScope(iObjScope);
        }
        else if (whio != null) {
           AllObjectsList aaol = AllObjectsList.getAllObjList();
           AllObjects ao = aaol.SearchByObjectName_DispScope(whio.getObjName(),whio.getDispScope());
           if (ao!=null)
           {ao.setDispScope(iDispScope);ao.setObjScope(iObjScope);}
           whio.setDispScope(iDispScope);
           whio.setObjScope(iObjScope);
        }
    return inlookup;
    }

}
