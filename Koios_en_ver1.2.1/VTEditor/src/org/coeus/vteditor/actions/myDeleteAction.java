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

import org.coeus.vteditor.programState;
import org.coeus.vteditor.modificationMadeClass;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.coeus.poclasses.ArrayObject;
import org.coeus.poclasses.ArrayObjectList;
import org.coeus.poclasses.AssignObject;
import org.coeus.poclasses.AssignObjectList;
import org.coeus.poclasses.CallObject;
import org.coeus.poclasses.CallObjectList;
import org.coeus.poclasses.ConstantObject;
import org.coeus.poclasses.ConstantObjectList;
import org.coeus.poclasses.DoWhileObject;
import org.coeus.poclasses.DoWhileObjectList;
import org.coeus.poclasses.ForObject;
import org.coeus.poclasses.ForObjectList;
import org.coeus.poclasses.FunctionObject;
import org.coeus.poclasses.FunctionObjectList;
import org.coeus.poclasses.IfObject;
import org.coeus.poclasses.IfObjectList;
import org.coeus.poclasses.LockObjects;
import org.coeus.poclasses.ProcedureObject;
import org.coeus.poclasses.ProcedureObjectList;
import org.coeus.poclasses.ReadObject;
import org.coeus.poclasses.ReadObjectList;
import org.coeus.poclasses.ReturnObject;
import org.coeus.poclasses.ReturnObjectList;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.VariableObjectList;
import org.coeus.poclasses.WhileObject;
import org.coeus.poclasses.WhileObjectList;
import org.coeus.poclasses.WriteObject;
import org.coeus.poclasses.WriteObjectList;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.actions.DeleteAction;
import org.openide.nodes.AbstractNode;
import org.openide.util.actions.SystemAction;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jrd
 */
 public class myDeleteAction extends AbstractAction implements ActionListener {

    Action a;
    private String name;
    private String category;
    private String type;
    private LinkedList<String> locked;
    private String scope;


    private ArrayObject arro;
    private ConstantObject cono;
    private FunctionObject funo;
    private ProcedureObject proo ;
    private VariableObject varo;
    private ReadObject reado;
    private WriteObject writeo;
    private CallObject callo;
    private AssignObject asso;
    private ReturnObject reto;
    private DoWhileObject dwho;
    private IfObject iffo;
    private ForObject foro;
    private WhileObject whio;

    private AbstractNode dnode;
    private boolean deleteFunPro=true;
    private boolean checkIfThenElseNode=false;



    public myDeleteAction (AbstractNode node) {
        putValue (NAME, "Delete");
        a=(Action)SystemAction.get(DeleteAction.class);
        node.setValue("customDelete", Boolean.TRUE);

        dnode=node;

        arro = node.getLookup().lookup(ArrayObject.class);
        cono = node.getLookup().lookup(ConstantObject.class);
        funo = node.getLookup().lookup(FunctionObject.class);
        proo = node.getLookup().lookup(ProcedureObject.class);
        varo = node.getLookup().lookup(VariableObject.class);
        reado = node.getLookup().lookup(ReadObject.class);
        writeo = node.getLookup().lookup(WriteObject.class);
        callo = node.getLookup().lookup(CallObject.class);
        reto = node.getLookup().lookup(ReturnObject.class);
        asso = node.getLookup().lookup(AssignObject.class);
        dwho = node.getLookup().lookup(DoWhileObject.class);
        iffo = node.getLookup().lookup(IfObject.class);
        foro = node.getLookup().lookup(ForObject.class);
        whio = node.getLookup().lookup(WhileObject.class);

        if (arro != null) {
              this.category=arro.getDispCateg();
              this.name="with name "+arro.getDispName();
              this.type=" ("+arro.getDispType()+") ";
              this.locked=arro.getLocked();
              this.scope=arro.getDispScope();
        } else if (cono != null) {
              this.category=cono.getDispCateg();
              this.name="with name "+cono.getDispName();
              this.type=" ("+cono.getDispType()+") ";
              this.locked=cono.getLocked();
              this.scope=cono.getDispScope();
        } else if (funo != null) {
              this.category=funo.getDispCateg();
              this.name="with name "+funo.getDispName();
              this.type=" ("+funo.getDispType()+") ";
              this.locked=funo.getLocked();
              this.scope=funo.getDispScope();
        } else if (proo != null) {
              this.category=proo.getDispCateg();
              this.name="with name "+proo.getDispName();
              this.type="";
              this.locked=proo.getLocked();
              this.scope=proo.getDispScope();
        } else if (varo != null) {
              this.category=varo.getDispCateg();
              this.name="with name "+varo.getDispName();
              this.type=" ("+varo.getDispType()+") ";
              this.locked=varo.getLocked();
              this.scope=varo.getDispScope();
        }else if (reado != null) {
              this.category="The command ";//reado.getDispCateg();
              this.name=reado.getDispName();
              this.type="";
              this.locked=reado.getLocked();
              this.scope=reado.getDispScope();
        }
         else if (writeo != null) {
              this.category="The command ";writeo.getDispCateg();
              this.name=writeo.getDispName();
              this.type="";
              this.locked=writeo.getLocked();
              this.scope=writeo.getDispScope();
        }
       else if (callo != null) {
              this.category="The command ";callo.getDispCateg();
              this.name=callo.getDispName();
              this.type="";
              this.locked=callo.getLocked();
              this.scope=callo.getDispScope();
        }
       else if (reto != null) {
              this.category="The command ";reto.getDispCateg();
              this.name=reto.getDispName();
              this.type="";
              this.locked=reto.getLocked();
              this.scope=reto.getDispScope();
        }
       else if (asso != null) {
              this.category="The command ";asso.getDispCateg();
              this.name=asso.getDispName();
              this.type="";
              this.locked=asso.getLocked();
              this.scope=asso.getDispScope();
        }
       else if (dwho != null) {
              this.category="The command ";dwho.getDispCateg();
              this.name=dwho.getDispName();
              this.type="";
              this.locked=dwho.getLocked();
              this.scope=dwho.getDispScope();
        }
       else if (iffo != null) {
              this.category="The command ";iffo.getDispCateg();
              this.name=iffo.getDispName();
              this.type="";
              this.locked=iffo.getLocked();
              this.scope=iffo.getDispScope();
        }
       else if (foro != null) {
              this.category="The command ";foro.getDispCateg();
              this.name=foro.getDispName();
              this.type="";
              this.locked=foro.getLocked();
              this.scope=foro.getDispScope();
        }
       else if (whio != null) {
              this.category="The command ";whio.getDispCateg();
              this.name=whio.getDispName();
              this.type="";
              this.locked=whio.getLocked();
              this.scope=whio.getDispScope();
        }

    }

    public void actionPerformed(ActionEvent e) {

        if (!locked.isEmpty())
        {   String message="";

            String uses="";
            for (String s:locked)
                uses=uses+s+"\n";

            if (category.equalsIgnoreCase(WizardsDefinitions.FUNCTION)
                    ||category.equalsIgnoreCase(WizardsDefinitions.PROCEDURE))
                message=category+type+
                name+"\n and any programming items it contains cannot be deleted, because it or its parameter(s) are being used\n" +
                "in the following command(s): \n"+uses+
                "If you want to delete this element, change or delete all commands that use it!";
            else
                message= category+type+
                name+"\n and any programming items it contains cannot be deleted, because it is being used in the following command(s): \n"+uses+
                "If you want to delete this element, change or delete all commands that use it!";

            NotifyDescriptor d0 =new NotifyDescriptor.Confirmation(message, "Error Deleting "+category,
            NotifyDescriptor.PLAIN_MESSAGE,NotifyDescriptor.ERROR_MESSAGE);

            DialogDisplayer.getDefault().notify(d0);
        }
         else
        {

        NotifyDescriptor d =new NotifyDescriptor.Confirmation(
                category+type+
                name+" is going to be deleted!\n\nDo you want to continue?",
                "Confirm Deleting "+category,
                NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.WARNING_MESSAGE);

         if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION) {
                if (arro != null) {

                   ArrayObjectList aol= ArrayObjectList.getArrObjList();
                   aol.removeFromList(arro);
                } else if (cono != null) {

                  ConstantObjectList col= ConstantObjectList.getConObjList();
                  col.removeFromList(cono);
                } else if (funo != null) {
                  deleteChildren(dnode,true);
                  if (deleteFunPro)
                  {
                   FunctionObjectList fol= FunctionObjectList.getFunObjList();
                   fol.removeFromList(funo);
                  }
                } else if (proo != null) {
                  deleteChildren(dnode,true);
                  if (deleteFunPro)
                  {
                   ProcedureObjectList pol= ProcedureObjectList.getProObjList();
                   pol.removeFromList(proo);
                  }

                } else if (varo != null) {

                  VariableObjectList vol= VariableObjectList.getVarObjList();
                  vol.removeFromList(varo);
                }else if (reado != null) {

                  LockObjects lo=new LockObjects(reado);
                  lo.unlock();
                  ReadObjectList rol= ReadObjectList.getReadObjList();
                  rol.removeFromList(reado);
                }else if (writeo != null) {

                  LockObjects lo=new LockObjects(writeo);
                  lo.unlock();
                  WriteObjectList wol= WriteObjectList.getWriteObjList();
                  wol.removeFromList(writeo);
                }
                else if (callo != null) {

                  LockObjects lo=new LockObjects(callo);
                  lo.unlock();
                  CallObjectList col= CallObjectList.getCallObjList();
                  col.removeFromList(callo);
                }
                else if (reto != null) {

                  LockObjects lo=new LockObjects(reto);
                  lo.unlock();
                  ReturnObjectList rol= ReturnObjectList.getReturnObjList();
                  rol.removeFromList(reto);
                }
                else if (asso != null) {

                  LockObjects lo=new LockObjects(asso);
                  lo.unlock();
                  AssignObjectList aol= AssignObjectList.getAssignObjList();
                  aol.removeFromList(asso);
                }
                else if (dwho != null) {
                 deleteChildren(dnode,true);
                  if (deleteFunPro)
                  {
                  LockObjects lo=new LockObjects(dwho);
                  lo.unlock();
                  DoWhileObjectList dwol= DoWhileObjectList.getDoWhileObjList();
                  dwol.removeFromList(dwho);
                  }                   
                }
                else if (foro != null) {
                 deleteChildren(dnode,true);
                  if (deleteFunPro)
                  {
                  LockObjects lo=new LockObjects(foro);
                  lo.unlock();
                  ForObjectList fol= ForObjectList.getForObjList();
                  fol.removeFromList(foro);
                  }
                }
                else if (iffo != null) {
                 checkIfThenElseNode=true;
                 deleteChildren(dnode,true);
                  if (deleteFunPro)
                  {
                  LockObjects lo=new LockObjects(iffo);
                  lo.unlock();
                  IfObjectList iol= IfObjectList.getIfObjList();
                  iol.removeFromList(iffo);
                  }
                }
                else if (whio != null) {
                 deleteChildren(dnode,true);
                  if (deleteFunPro)
                  {
                  LockObjects lo=new LockObjects(whio);
                  lo.unlock();
                  WhileObjectList wol= WhileObjectList.getWhileObjList();
                  wol.removeFromList(whio);
                  }
                }
          if(deleteFunPro)
          {
            AbstractNode pNode=(AbstractNode) dnode.getParentNode();
            a.actionPerformed(e);
   new getUndoInstance(pNode);
   modificationMadeClass.getCurrentModificationMadeClass().makeModification();
   programState.getCurrentProgramState().setCompilerStateUNCOMPILED();
          }
         }
      }

    }

    @Override
    public boolean isEnabled() {
     return a.isEnabled();
    }


    public void deleteChildren(AbstractNode node,boolean showNotify)
    {
    int numChild = node.getChildren().getNodesCount();

        if (checkIfThenElseNode)
       {
       int numberOfIfChildren=numChild;
       numChild=0;
       for (int i=0; i<numberOfIfChildren;i++)
           numChild=node.getChildren().getNodeAt(i).getChildren().getNodesCount();
           checkIfThenElseNode=false;
       }


     if (numChild>0)
     {
         if (showNotify )
         {
                NotifyDescriptor dd =new NotifyDescriptor.Confirmation(
                     category+" "+name+" contains element(s) that will be deleted!\n\n" +
                    "Do you want to continue?",
                    "Confirm Deleting Element(s) of "+category,
                    NotifyDescriptor.YES_NO_OPTION,NotifyDescriptor.WARNING_MESSAGE);

                deleteFunPro =DialogDisplayer.getDefault().notify(dd) == NotifyDescriptor.YES_OPTION;
         }
     

         if (deleteFunPro)
         {
            
             

            for (Enumeration e=node.getChildren().nodes(); e.hasMoreElements(); ) {
                AbstractNode n = (AbstractNode)e.nextElement();

               deleteChildren(n,false);
               ArrayObject arro1 = n.getLookup().lookup(ArrayObject.class);
               ConstantObject cono1 = n.getLookup().lookup(ConstantObject.class);
               VariableObject varo1 = n.getLookup().lookup(VariableObject.class);
               ReadObject reado1 = n.getLookup().lookup(ReadObject.class);
               WriteObject writeo1 = n.getLookup().lookup(WriteObject.class);
               CallObject callo1 = n.getLookup().lookup(CallObject.class);
               ReturnObject reto1 = n.getLookup().lookup(ReturnObject.class);
               AssignObject asso1 = n.getLookup().lookup(AssignObject.class);
               DoWhileObject dwho1 = n.getLookup().lookup(DoWhileObject.class);
               IfObject iffo1 = n.getLookup().lookup(IfObject.class);
               ForObject foro1 = n.getLookup().lookup(ForObject.class);
               WhileObject whio1 = n.getLookup().lookup(WhileObject.class);

                if (arro1 != null) {
                   ArrayObjectList aol= ArrayObjectList.getArrObjList();
                   aol.removeFromList(arro1);
                } else if (cono1 != null) {
                  ConstantObjectList col= ConstantObjectList.getConObjList();
                  col.removeFromList(cono1);
                } else if (varo1 != null) {
                  VariableObjectList vol= VariableObjectList.getVarObjList();
                  vol.removeFromList(varo1);
                }else if (reado1 != null) {
                  LockObjects lo=new LockObjects(reado1);
                  lo.unlock();
                  ReadObjectList rol= ReadObjectList.getReadObjList();
                  rol.removeFromList(reado1);
                }
               else if (writeo1 != null) {
                  LockObjects lo=new LockObjects(writeo1);
                  lo.unlock();
                  WriteObjectList wol= WriteObjectList.getWriteObjList();
                  wol.removeFromList(writeo1);
                }
                else if (callo1 != null) {
                  LockObjects lo=new LockObjects(callo1);
                  lo.unlock();
                  CallObjectList col= CallObjectList.getCallObjList();
                  col.removeFromList(callo1);          
                }
                else if (reto1 != null) {
                  LockObjects lo=new LockObjects(reto1);
                  lo.unlock();
                  ReturnObjectList rol= ReturnObjectList.getReturnObjList();
                  rol.removeFromList(reto1);
                }
                else if (asso1 != null) {
                  LockObjects lo=new LockObjects(asso1);
                  lo.unlock();
                  AssignObjectList aol= AssignObjectList.getAssignObjList();
                  aol.removeFromList(asso1);
                }
                else if (dwho1 != null) {
            //     deleteChildren(dnode,true);
                  if (deleteFunPro)
                  {
                  LockObjects lo=new LockObjects(dwho1);
                  lo.unlock();
                  DoWhileObjectList dwol= DoWhileObjectList.getDoWhileObjList();
                  dwol.removeFromList(dwho1);
                  }
                }
                else if (foro1 != null) {
        //         deleteChildren(dnode,true);
                  if (deleteFunPro)
                  {
                  LockObjects lo=new LockObjects(foro1);
                  lo.unlock();
                  ForObjectList fol= ForObjectList.getForObjList();
                  fol.removeFromList(foro1);
                  }
                }
                else if (iffo1 != null) {
     //            deleteChildren(dnode,true);
                  if (deleteFunPro)
                  {
                  LockObjects lo=new LockObjects(iffo1);
                  lo.unlock();
                  IfObjectList iol= IfObjectList.getIfObjList();
                  iol.removeFromList(iffo1);
                  }
                }
                else if (whio1 != null) {
    //             deleteChildren(dnode,true);
                  if (deleteFunPro)
                  {
                  LockObjects lo=new LockObjects(whio1);
                  lo.unlock();
                  WhileObjectList wol= WhileObjectList.getWhileObjList();
                  wol.removeFromList(whio1);
                  }
                }
//                else if (n instanceof PCNodeParent)
//                {
//                PCNodeParent n1 = (PCNodeParent) n;
//                if (n1.getDispCateg().equalsIgnoreCase(WizardsDefinitions.IF_STATEMENT)
//                 || n1.getDispCateg().equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT)
//                 || n1.getDispCateg().equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT))
//                    deleteChildren(n,false);
//
//                }
               

               try {
                n.destroy();}
                 catch (IOException ex) {}
         }
       }
    }
 }


  }
