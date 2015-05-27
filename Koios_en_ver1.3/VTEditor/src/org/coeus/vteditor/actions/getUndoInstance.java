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
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;

/**
 *
 * @author Jrd
 */
public class getUndoInstance {


  public getUndoInstance(RootNode node)
      {
         undoRedoClass ur=new undoRedoClass();
         ur.saveRootNodeLists(node);
//                 ,ConstantObjectList.getConObjList(),
//            VariableObjectList.getVarObjList(),ArrayObjectList.getArrObjList(),
//            FunctionObjectList.getFunObjList(),ProcedureObjectList.getProObjList(),
//            ReadObjectList.getReadObjList(),WriteObjectList.getWriteObjList(),
//            CallObjectList.getCallObjList(),ReturnObjectList.getReturnObjList(),
//            AssignObjectList.getAssignObjList(),DoWhileObjectList.getDoWhileObjList(),
//            ForObjectList.getForObjList(),IfObjectList.getIfObjList(),
//            WhileObjectList.getWhileObjList(),AllObjectsList.getAllObjList());

         undoRedoClassList.getUndoRedoClassList().addUndoInsatnce(ur);
//new printTree(node);
         }

  public getUndoInstance(AbstractNode node)
      {
       Node parentNode=node;
  while(!(parentNode instanceof RootNode))
   parentNode=parentNode.getParentNode();


         undoRedoClass ur=new undoRedoClass();
         ur.saveRootNodeLists((RootNode) parentNode);

         undoRedoClassList.getUndoRedoClassList().addUndoInsatnce(ur);

         }


}
