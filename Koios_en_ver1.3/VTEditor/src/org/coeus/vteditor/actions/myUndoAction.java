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

import org.coeus.vteditor.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.coeus.vteditor.btvnodes.RootNode;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

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
import org.coeus.wizards.AllObjectsList;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.ContextAwareAction;

/**
 *
 * @author Jrd
 */
//public class myUndoAction extends AbstractAction implements LookupListener{
//
//   private  Lookup.Result result = null;
//   private VTEditor cVTEditor=currentVTEditor.getCurrentVTEditor();
//   private RootNode newrn=null;
//
//
//   public myUndoAction()
//    {  Lookup.Template tpl = new Lookup.Template (VTEditor.class);
//       result = Utilities.actionsGlobalContext().lookup(tpl);
//       result.addLookupListener(this);
//   }
//
//
//    public void actionPerformed(ActionEvent e) {
//        if (cVTEditor!=null&&
//        undoRedoClassList.getUndoRedoClassList().getIndex()>0)
//        {
//        undoRedoClass ur = undoRedoClassList.getUndoRedoClassList().getUndo();
//
//        ur.loadRootNodeLists(ConstantObjectList.getConObjList(),
//            VariableObjectList.getVarObjList(),ArrayObjectList.getArrObjList(),
//            FunctionObjectList.getFunObjList(),ProcedureObjectList.getProObjList(),
//            ReadObjectList.getReadObjList(),WriteObjectList.getWriteObjList(),
//            CallObjectList.getCallObjList(),ReturnObjectList.getReturnObjList(),
//            AssignObjectList.getAssignObjList(),DoWhileObjectList.getDoWhileObjList(),
//            ForObjectList.getForObjList(),IfObjectList.getIfObjList(),
//            WhileObjectList.getWhileObjList(),AllObjectsList.getAllObjList());
//
//        this.newrn=ur.getUndoRedoRootNode().loadRootNode(ur.getUndoRedoRootNode(),ConstantObjectList.getConObjList().conList,
//            VariableObjectList.getVarObjList().varList,ArrayObjectList.getArrObjList().arrList,
//            FunctionObjectList.getFunObjList().funList,ProcedureObjectList.getProObjList().proList,
//            ReadObjectList.getReadObjList().readList,WriteObjectList.getWriteObjList().writeList,
//            CallObjectList.getCallObjList().callList,ReturnObjectList.getReturnObjList().returnList,
//            AssignObjectList.getAssignObjList().assignList,DoWhileObjectList.getDoWhileObjList().doWhileList,
//            ForObjectList.getForObjList().forList,IfObjectList.getIfObjList().ifList,
//            WhileObjectList.getWhileObjList().whileList,AllObjectsList.getAllObjList().allObjects);
//
//        cVTEditor.setRootNode(this.newrn);
//        }
//        else
//        {
//         NotifyDescriptor d = new NotifyDescriptor.Confirmation("Δεν ύπαρχει προηγούμενη ενέργεια!",
//            "Μη Δυνατότητα Αναίρεσης",NotifyDescriptor.DEFAULT_OPTION, NotifyDescriptor.INFORMATION_MESSAGE);
//         DialogDisplayer.getDefault().notify(d);
//        }
//    }
//
////    @Override
////    public boolean isEnabled() {
////
////      return (cVTEditor!=null&&
////        undoRedoClassList.getUndoRedoClassList().getIndex()>0);
////    }
//
//     public void resultChanged(LookupEvent ev) {
//cVTEditor=currentVTEditor.getCurrentVTEditor();
////        Collection<VTEditor> c = result.allInstances();
////        if (!c.isEmpty())
////            cVTEditor = (VTEditor) c.iterator().next();
//    }
//
//}



public class myUndoAction extends AbstractAction implements LookupListener, ContextAwareAction {
    private Lookup context;
    Lookup.Result<VTEditor> result;
    private RootNode newrn=null;
    private VTEditor cVTEditor=null;
    private static myUndoAction myUndoActionInstance=null;



    public myUndoAction() {
        this(Utilities.actionsGlobalContext());
        myUndoActionInstance=this;
    }


 public myUndoAction(Lookup context) {
    init(context);
    putValue(NAME,"Undo");
    putValue("iconBase","org/coeus/vteditor/resources/undo.gif");

  }


    private void init(Lookup context) {
    this.context = context;
    result = context.lookupResult(contextClass());
    result.addLookupListener(this);
    resultChanged(null);
    }


    public Class<VTEditor> contextClass() {
    return VTEditor.class;
    }

    public void actionPerformed(ActionEvent e) {
//       cVTEditor =  result.allInstances().iterator().next();
//       if (undoRedoClassList.getUndoRedoClassList().getIndex()>0)
//        {
//        undoRedoClass ur = undoRedoClassList.getUndoRedoClassList().getUndo();
       if (cVTEditor.getUndoRedoList().getIndex()>0)
        {
        undoRedoClass ur = cVTEditor.getUndoRedoList().getUndo();

        ur.loadRootNodeLists(ConstantObjectList.getConObjList(),
            VariableObjectList.getVarObjList(),ArrayObjectList.getArrObjList(),
            FunctionObjectList.getFunObjList(),ProcedureObjectList.getProObjList(),
            ReadObjectList.getReadObjList(),WriteObjectList.getWriteObjList(),
            CallObjectList.getCallObjList(),ReturnObjectList.getReturnObjList(),
            AssignObjectList.getAssignObjList(),DoWhileObjectList.getDoWhileObjList(),
            ForObjectList.getForObjList(),IfObjectList.getIfObjList(),
            WhileObjectList.getWhileObjList(),AllObjectsList.getAllObjList());

        this.newrn=ur.getUndoRedoRootNode().loadRootNode(ur.getUndoRedoRootNode(),ConstantObjectList.getConObjList().conList,
            VariableObjectList.getVarObjList().varList,ArrayObjectList.getArrObjList().arrList,
            FunctionObjectList.getFunObjList().funList,ProcedureObjectList.getProObjList().proList,
            ReadObjectList.getReadObjList().readList,WriteObjectList.getWriteObjList().writeList,
            CallObjectList.getCallObjList().callList,ReturnObjectList.getReturnObjList().returnList,
            AssignObjectList.getAssignObjList().assignList,DoWhileObjectList.getDoWhileObjList().doWhileList,
            ForObjectList.getForObjList().forList,IfObjectList.getIfObjList().ifList,
            WhileObjectList.getWhileObjList().whileList,AllObjectsList.getAllObjList().allObjects);

        cVTEditor.setRootNode(this.newrn,cVTEditor.getDisplayName());
       }
      else
        {
         NotifyDescriptor d = new NotifyDescriptor.Confirmation("No action to undo!",
            "Error Undoing Action",NotifyDescriptor.DEFAULT_OPTION, NotifyDescriptor.INFORMATION_MESSAGE);
         DialogDisplayer.getDefault().notify(d);
        }
   new checkActionsState();
 }

    public void resultChanged(LookupEvent ev) {
       if(result.allInstances().size() != 0)
       {
         cVTEditor =  result.allInstances().iterator().next();
         checkUndoAction();
       }
       else
           setEnabled(false);
       }
    public Action createContextAwareInstance(Lookup actionContext) {
       return new myUndoAction(context);
    }



    public  void checkUndoAction()
    {
         if (cVTEditor.getUndoRedoList().getIndex()>0)
              setEnabled(true);
         else
              setEnabled(false);
    }

    public static myUndoAction getMyUndoAction()
    {return myUndoActionInstance;}

}
