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


import org.coeus.vteditor.btvnodes.*;
import org.coeus.poclasses.ArrayObjectList;
import org.coeus.poclasses.AssignObjectList;
import org.coeus.poclasses.CallObjectList;
import org.coeus.poclasses.ConstantObjectList;
import org.coeus.poclasses.DoWhileObjectList;
import org.coeus.poclasses.ForObjectList;
import org.coeus.poclasses.FunctionObjectList;
import org.coeus.poclasses.IfObjectList;
import org.coeus.poclasses.ProcedureObjectList;
import org.coeus.poclasses.ReadObjectList;
import org.coeus.poclasses.ReturnObjectList;
import org.coeus.poclasses.VariableObjectList;
import org.coeus.poclasses.WhileObjectList;
import org.coeus.poclasses.WriteObjectList;
import org.coeus.poclasses.createBuiltInFunctions;
import org.coeus.wizards.AllObjectsList;

/**
 *
 * @author Jrd
 */
public class undoRedoClass {




    private undoRedoNodeClass undoRedoNode=null;


public void saveRootNodeLists(RootNode rNode)
{
 this.undoRedoNode=new undoRedoNodeClass();
 this.undoRedoNode=this.undoRedoNode.saveNodes(rNode);

}



public void loadRootNodeLists(ConstantObjectList iconl,VariableObjectList ivarl,
    ArrayObjectList iarrl,FunctionObjectList ifunl,ProcedureObjectList iprol,ReadObjectList ireadl,
    WriteObjectList iwritel,CallObjectList icalll,ReturnObjectList iretl,AssignObjectList iassl,
    DoWhileObjectList idwhl,ForObjectList iforl,IfObjectList iiffl,WhileObjectList iwhil,AllObjectsList aoll)
{

 iconl.conList.clear();
 ivarl.varList.clear();
 iarrl.arrList.clear();
 ifunl.funList.clear();
 iprol.proList.clear();
 ireadl.readList.clear();
 iwritel.writeList.clear();
 icalll.callList.clear();
 iretl.returnList.clear();
 iassl.assignList.clear();
 idwhl.doWhileList.clear();
 iforl.forList.clear();
 iiffl.ifList.clear();
 iwhil.whileList.clear();
 aoll.allObjects.clear();
 new createBuiltInFunctions();
}


public undoRedoNodeClass getUndoRedoRootNode() {
    return undoRedoNode;
}




}
