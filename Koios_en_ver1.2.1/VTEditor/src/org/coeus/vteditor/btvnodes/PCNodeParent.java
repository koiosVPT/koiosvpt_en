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

import org.coeus.vteditor.actions.getUndoInstance;
import org.coeus.vteditor.programState;
import org.coeus.vteditor.modificationMadeClass;
import org.coeus.vteditor.actions.myDeleteAction;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Action;

import org.coeus.btvpalette.ProgComp;

import org.coeus.poclasses.ArrayObject;
import org.coeus.poclasses.CoeusProgram;
import org.coeus.poclasses.ConstantObject;
import org.coeus.poclasses.VariableObject;
import org.coeus.vteditor.actions.myMoveDownAction;
import org.coeus.vteditor.actions.myMoveUpAction;
import org.coeus.vteditor.mypropertieswindow.myPropertiesTopComponent;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.actions.CutAction;


import org.openide.actions.PasteAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.PasteType;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author Jrd
 */
public class PCNodeParent extends AbstractNode {

   // private String mytab = "                 ";
    String [] ObjAttrib =new String [5];
    private String myTransferableType;
    private String dispCateg;
    private String dispName;
    private String dispType;
    private String dispScope;
    private String objValue;
    private String objScope;
    private String iconBaseWithExtension;

    private Lookup cnlu=null;
    private String sourceObjScope=null;


    public PCNodeParent(Children c, ProxyLookup plu,String inDispScope,String inObjScope) {
        super(c, plu);
        this.objScope=inObjScope;
        this.dispScope=inDispScope;
        this.ObjAttrib=SetNodeAttFromObject.SetNodeParentAttributes(this);
        this.myTransferableType=ObjAttrib[0];
        this.dispCateg=ObjAttrib[1];
        this.dispName=ObjAttrib[2];
        this.dispType=ObjAttrib[3];
        this.objValue=ObjAttrib[4];
        
      }

     public PCNodeParent(PCNodeParent sourceNode,Children children,ProxyLookup plu) {
        super(children, plu);
        this.objScope=sourceNode.getObjScope();
        this.dispScope=sourceNode.getDispScope();
        if (isIfThenElseChild(sourceNode.getDispCateg()))
        {
         this.setDisplayName(sourceNode.getDisplayName());
         this.mySetIconBaseWithExtension(sourceNode.getIconBaseWithExtension());
         this.dispName=sourceNode.getDispName();
         this.dispCateg=sourceNode.getDispCateg();
         this.myTransferableType=sourceNode.getMyTransferableType();
        }
        else
        {
        this.ObjAttrib=SetNodeAttFromObject.SetNodeParentAttributes(this);
        this.myTransferableType=ObjAttrib[0];
        this.dispCateg=ObjAttrib[1];
        this.dispName=ObjAttrib[2];
        this.dispType=ObjAttrib[3];
        this.objValue=ObjAttrib[4];
        }

      }


    public Children copyPCNodeParentChildren()
    {
     Children children = new CreateMainChild();

     List<Node> PCNodeParentChildren= this.getChildren().snapshot();
            for ( int ir=0;ir<PCNodeParentChildren.size();ir++ )
            {
             Node nodeChild = PCNodeParentChildren.get(ir);
             if (nodeChild instanceof PCNodeParent)
                {
                PCNodeParent PCNPnodeChild =(PCNodeParent) nodeChild;
                Children newChildren = PCNPnodeChild.copyPCNodeParentChildren();
                new getLookupFromNode(PCNPnodeChild);
                ProxyLookup pl = new ProxyLookup(new Lookup[]
                      {getLookupFromNode.createLookupFromObject(),Lookups.singleton(newChildren)});
                PCNodeParent child = new PCNodeParent((PCNodeParent) nodeChild,newChildren,pl);
                children.add(new Node[]{child});
                }
             else if (nodeChild instanceof PCNodeLeaf)
                 {
                 PCNodeLeaf PCNLnodeChild =(PCNodeLeaf) nodeChild;
                 Children newChildren= Children.LEAF;
                 new getLookupFromNode(PCNLnodeChild);
                 ProxyLookup pl = new ProxyLookup(new Lookup[]
                      {getLookupFromNode.createLookupFromObject(),Lookups.singleton(newChildren)});
                PCNodeLeaf child = new PCNodeLeaf(PCNLnodeChild,newChildren,pl);
                children.add(new Node[]{child});
                }
            }
    return children;
    }


public RootNode getRootNode()
{
  Node parentNode=this.getParentNode();  
  
  while(!(parentNode instanceof RootNode))
   parentNode=parentNode.getParentNode();

  return (RootNode) parentNode;
}


public boolean isChildOfFunction()
{
  boolean result = false;
  Node thisNode=this;

  while(!(thisNode instanceof RootNode))
  {
    if(thisNode instanceof PCNodeParent)
    {
     if (((PCNodeParent)thisNode).getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION))
     {result=true;}
    }
   thisNode=thisNode.getParentNode();
  }

  return result;
}



public String getParentFunctionName()
{
  String name = null;
  Node thisNode=this;

  while(!(thisNode instanceof RootNode))
  {
    if(thisNode instanceof PCNodeParent)
    {
     if (((PCNodeParent)thisNode).getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION))
     {name=((PCNodeParent)thisNode).getDispName();}
    }
   thisNode=thisNode.getParentNode();
  }

  return name;
}


public boolean isIfThenElseChild(String dispCateg)
{
if(  dispCateg.equalsIgnoreCase(WizardsDefinitions.IF_STATEMENT)
  || dispCateg.equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT)
  || dispCateg.equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT) )
    return true;
else
    return false;


}

    
    
//    public Node[] copyPCNodeParentChildren2NodeArray()
//    {
//     List<Node> PCNodeParentChildren= this.getChildren().snapshot();
//
//     Node [] children = new Node[PCNodeParentChildren.size()];
//     for ( int ir=0;ir<PCNodeParentChildren.size();ir++ )
//            {
//             Node nodeChild = PCNodeParentChildren.get(ir);
//             if (nodeChild instanceof PCNodeParent)
//                {
//                PCNodeParent PCNPnodeChild =(PCNodeParent) nodeChild;
//                Children newChildren = PCNPnodeChild.copyPCNodeParentChildren();
//                new getLookupFromNode(PCNPnodeChild);
//                ProxyLookup pl = new ProxyLookup(new Lookup[]
//                      {getLookupFromNode.createLookupFromObject(),Lookups.singleton(newChildren)});
//                PCNodeParent child = new PCNodeParent((PCNodeParent) nodeChild,newChildren,pl);
//                children[ir]=child;
//                }
//             else if (nodeChild instanceof PCNodeLeaf)
//                {
//                PCNodeLeaf child = new PCNodeLeaf((PCNodeLeaf) nodeChild);
//                children[ir]=child;
//                }
//            }
//    return  children;
//    }
   
    
    
    public String getMyTransferableType()
    {return this.myTransferableType;}

    public String getObjScope()
    {return this.objScope;}

    public void setObjScope(String inScope)
    {this.objScope=inScope;}

    public String getDispScope()
    {return this.dispScope;}

    public void setDispScope(String inScope)
    {this.dispScope=inScope;}

    public String getDispName()
    {return this.dispName;}

    public String getDispType()
    {return this.dispType;}

    public String getObjValue()
    {return this.objValue;}

    public String getDispCateg()
    {return this.dispCateg;}

    public String getIconBaseWithExtension()
   {return this.iconBaseWithExtension;}

    public void setDispName(String inName)
    {this.dispName=inName;}

    public void setDispCategory(String inCategory)
    {this.dispCateg=inCategory;}

    public void setMyTransferableType(String inMyTransferableType)
    {this.myTransferableType=inMyTransferableType;}


    public void mySetIconBaseWithExtension(String base) {
     this.setIconBaseWithExtension(base);
     this.iconBaseWithExtension=base;
    }


  @Override
    public Action getPreferredAction() {

    if (this.dispCateg.equalsIgnoreCase(WizardsDefinitions.COM_IF))
    {
        new  UpdateIfThenElseChildren(this);
        this.ObjAttrib=SetNodeAttFromObject.SetNodeParentAttributes(this);
        this.myTransferableType=ObjAttrib[0];
        this.dispCateg=ObjAttrib[1];
        this.dispName=ObjAttrib[2];
        this.dispType=ObjAttrib[3];
        this.objValue=ObjAttrib[4];

         myPropertiesTopComponent.getCurrent().resultChanged(new LookupEvent (this.getLookup().lookupResult(Node.class)));
    }
    else if (!this.dispName.equals(CoeusProgram.MAIN_PROC)
         && !isIfThenElseChild(this.dispCateg) )
    {
        Lookup nodesLookup = this.getLookup();
        new  UpdateNodesObject(nodesLookup);
        this.ObjAttrib=SetNodeAttFromObject.SetNodeParentAttributes(this);
        this.myTransferableType=ObjAttrib[0];
        this.dispCateg=ObjAttrib[1];
        this.dispName=ObjAttrib[2];
        this.dispType=ObjAttrib[3];
        this.objValue=ObjAttrib[4];

        myPropertiesTopComponent.getCurrent().resultChanged(new LookupEvent (nodesLookup.lookupResult(Node.class)));

    }
new getUndoInstance(getRootNode());
modificationMadeClass.getCurrentModificationMadeClass().makeModification();
programState.getCurrentProgramState().setCompilerStateUNCOMPILED();
    


     return super.getPreferredAction();
  }



    @Override
    public boolean canCut() {
        return false;
    }

    @Override
    public boolean canDestroy() {
      if (this.dispName.equals(CoeusProgram.MAIN_PROC))
          {return false;}
        else
        {return true;} 
    }



    @Override
    protected void createPasteTypes(Transferable t, List s) {
        super.createPasteTypes(t, s);
           PasteType paste = getDropType(t, DnDConstants.ACTION_COPY, -1);
            if (null != paste) {
                s.add(paste);
            }
     }

    @Override
    public PasteType getDropType(Transferable t, int arg1, int arg2) {
        /////IF ITEM FROM PALETTE
    if (!this.getDispCateg().equalsIgnoreCase(WizardsDefinitions.COM_IF))
    {
     if (t.isDataFlavorSupported(ProgComp.PC_DATA_FLAVOR))
     {
       final ProgComp pc;
            try {
                pc = (ProgComp) t.getTransferData(ProgComp.PC_DATA_FLAVOR);
                /////IF PARENT NODE
                    if ((pc.getType().equals("iff")) || (pc.getType().equals("for")) 
                    || (pc.getType().equals("whi")) || (pc.getType().equals("dwh")))
                    {
                       return new PasteType() {

                        @Override
                        public Transferable paste() throws IOException {

                       if(!pc.getType().equals("iff"))
                       {
                       Children newchild = new CreateMainChild();

                       Lookup nObjLookup= CreateNewPObjectFromPalette.CreateNewPObject(pc,dispScope,objScope);
                       if (nObjLookup != null) {
                         ProxyLookup plul = new ProxyLookup(new Lookup[]{
                                     nObjLookup,Lookups.singleton(newchild)});
                        PCNodeParent child=new PCNodeParent(newchild, plul,dispScope,objScope);
                        getChildren().add(new Node[]{child});
                        moveChildAtTheEnd.move((Index.ArrayChildren)getChildren(), child);
  new getUndoInstance(getRootNode());
  modificationMadeClass.getCurrentModificationMadeClass().makeModification();
  programState.getCurrentProgramState().setCompilerStateUNCOMPILED();
                       }
                       }
                       else// if node is if create if children
                       {
                       Lookup nObjLookup= CreateNewPObjectFromPalette.CreateNewPObject(pc,dispScope,objScope);
                       if (nObjLookup != null) {
                         Children if_then_elseChildren  = new ifThenElseChildren(nObjLookup,dispScope,objScope);
                         ProxyLookup plul = new ProxyLookup(new Lookup[]{
                                     nObjLookup,Lookups.singleton(if_then_elseChildren)});

                        PCNodeParent child=new PCNodeParent(if_then_elseChildren, plul,dispScope,objScope);
                        getChildren().add(new Node[]{child});
                        moveChildAtTheEnd.move((Index.ArrayChildren )getChildren(), child);
  new getUndoInstance(getRootNode());
  modificationMadeClass.getCurrentModificationMadeClass().makeModification();
  programState.getCurrentProgramState().setCompilerStateUNCOMPILED();
                       }


                       }
                        return null;
                        }
                      };
                    }
                /////IF LEAF NODE
                    else if ((pc.getType().equals("con")&&(this.dispCateg.equalsIgnoreCase(WizardsDefinitions.PROCEDURE)
                               ||this.dispCateg.equalsIgnoreCase(WizardsDefinitions.FUNCTION)))
                     
                    || (pc.getType().equals("var")&&(this.dispCateg.equalsIgnoreCase(WizardsDefinitions.PROCEDURE)
                              ||this.dispCateg.equalsIgnoreCase(WizardsDefinitions.FUNCTION))                    )
                    || (pc.getType().equals("arr")&&(this.dispCateg.equalsIgnoreCase(WizardsDefinitions.PROCEDURE)
                   ||this.dispCateg.equalsIgnoreCase(WizardsDefinitions.FUNCTION)))
                    || (pc.getType().equals("wri"))
                    || (pc.getType().equals("rea"))
                    || (pc.getType().equals("ass"))
                    || (pc.getType().equals("cal")) //&&(this.dispCateg.equals(WizardsDefinitions.FUNCTION)
                                    //|| this.dispCateg.equals(WizardsDefinitions.PROCEDURE) ))
                    || ((pc.getType().equals("ret"))&&(this.isChildOfFunction())&&
                    (this.dispCateg.equals(WizardsDefinitions.FUNCTION) || this.isIfThenElseChild(this.dispCateg))))
                    {
                         return new PasteType() {

                        @Override
                        public Transferable paste() throws IOException {

                             LinkedList<AllObjects> ao=null;
                            if ( pc.getType().equals("cal") )
                            {
                              AllObjectsList caol = AllObjectsList.getAllObjList();
                               ao =caol.SearchByObjectType_4Procedures();
                            }

                            if ( pc.getType().equals("cal") && ao.isEmpty())
                            {
                                NotifyDescriptor d0 = new NotifyDescriptor.Confirmation(
                               "A CALL command cannot be created, because\n " +
                               "this command is used to call PROCEDURES, but\n"+
                               "this program does not contain any PROCEDURES!\n" +
                               "A CALL command is not required for "+ WizardsDefinitions.MAIN_PROC+", which is called automatically by the program!",
                               "Error Creating CALL Command",
                                NotifyDescriptor.PLAIN_MESSAGE, NotifyDescriptor.ERROR_MESSAGE);
                                DialogDisplayer.getDefault().notify(d0);
                            }
                            else
                            {
                            Children newchild = Children.LEAF;

                            Lookup nObjLookup = CreateNewPObjectFromPalette.CreateNewPObject(pc,dispScope,objScope);
                            if (nObjLookup != null) {
                                ProxyLookup plul = new ProxyLookup(new Lookup[]{
                                            nObjLookup, Lookups.singleton(newchild)});

                        PCNodeLeaf child=new PCNodeLeaf(newchild, plul,  dispScope,objScope);
                        getChildren().add(new Node[]{child});
                        moveChildAtTheEnd.move((Index.ArrayChildren )getChildren(), child);
  new getUndoInstance(getRootNode());
  modificationMadeClass.getCurrentModificationMadeClass().makeModification();
  programState.getCurrentProgramState().setCompilerStateUNCOMPILED();

                            }
                            }
                            //return null;
                            return pc;
                        }
                    };
                    }
            } catch (UnsupportedFlavorException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
     }
     //IF ITEM IS ALREADY IN THE PROGRAM.... MOVE
        else //if (t.isDataFlavorSupported(PCNodeLeaf.NODE_LEAF_FLAVOR))
        {
            ///NO COPY WILL BE DONE - final Node copyNode = NodeTransfer.node(t, DnDConstants.ACTION_COPY_OR_MOVE + NodeTransfer.CLIPBOARD_CUT);
            final PCNodeLeaf cutNodeLeaf = (PCNodeLeaf) NodeTransfer.node(t, NodeTransfer.CLIPBOARD_CUT + DnDConstants.ACTION_MOVE);
            if (cutNodeLeaf != null) {
                //////////IF ACTION IS CUT

                cnlu = cutNodeLeaf.getLookup();
                String type = cutNodeLeaf.getMyTransferableType();
                sourceObjScope = cutNodeLeaf.getObjScope();
//ALLOW CUT ONLY FOR CONSTANTS,VARIABLES ,ARRAYS
                if (((type.equals("arr")) || (type.equals("var")) || (type.equals("con")))
                &&(this.dispCateg.equalsIgnoreCase(WizardsDefinitions.PROCEDURE)
                   ||this.dispCateg.equalsIgnoreCase(WizardsDefinitions.FUNCTION))){

                    return new PasteType() {

                                @Override
                                public Transferable paste() throws IOException {

                                     ///Check if there is already an object with the same name of the moving node
                                     AllObjectsList caol = AllObjectsList.getAllObjList();
                                     AllObjects ao =caol.SearchByDisplayName_DispScope(cutNodeLeaf.getDispName(),dispScope);

                                     //Check if there is already a parameter with the name of the moving node
                                     AllObjects FunPro = caol.SearchByObjectScope_4FunctionsOrProcedures(objScope);
                                     String [] dispParams= null;

                                       if (!FunPro.getDispParams().isEmpty())
                                         { dispParams = new String [FunPro.getDispParams().size()];
                                           for (int op=0;op<FunPro.getDispParams().size();op++)
                                                dispParams[op]=FunPro.getDispParams().get(op);
                                          }

                                     int foundParameter=0;
                                     if( dispParams!=null )
                                     {
                                      for(int pn=0;pn<dispParams.length;pn++)
                                        {
                                            if(cutNodeLeaf.getDispName().equals(dispParams[pn]))
                                             foundParameter=(pn+1);
                                        }
                                     }

                                     //If item is "locked" cancel move
                                        LinkedList<String> locked = new LinkedList();
                                        ArrayObject arro = cnlu.lookup(ArrayObject.class);
                                        ConstantObject cono = cnlu.lookup(ConstantObject.class);
                                        VariableObject varo = cnlu.lookup(VariableObject.class);
                                        if (arro!=null)
                                            locked=arro.getLocked();
                                        if (cono!=null)
                                            locked=cono.getLocked();
                                        if (varo!=null)
                                            locked=varo.getLocked();

                                     if (!locked.isEmpty())
                                     {
                                       String uses="";
                                        for (String s:locked)
                                            uses=uses+s+"\n";

                                        NotifyDescriptor d0 =new NotifyDescriptor.Confirmation("The "
                                        + cutNodeLeaf.getDispCateg() + " " + cutNodeLeaf.getDispType()+ " with name " + cutNodeLeaf.getDispName()
                                        +"\ncannot be moved, because it is being used by the following command(s): \n"+uses+
                                         "If you still want to move this "+ cutNodeLeaf.getDispCateg() +", change or delete all commands that use it!"
                                                ,"Error Moving "+cutNodeLeaf.getDispCateg(),
                                        NotifyDescriptor.PLAIN_MESSAGE,NotifyDescriptor.ERROR_MESSAGE);
                                        DialogDisplayer.getDefault().notify(d0);
                                     }
                                     //if there is item with same name at destiniation, cancel move
                                     else if (ao!=null)
                                       {
                                        NotifyDescriptor d0 = new NotifyDescriptor.Confirmation("The "+
                                        cutNodeLeaf.getDispCateg() + " " + cutNodeLeaf.getDispType()
                                        + " with name " + cutNodeLeaf.getDispName() + "\n"
                                        + " cannot be moved, because a " + ao.getDispCateg()
                                        + " with the name " +ao.getDispName()+ " already exists in scope of "+ dispScope
                                        + " !\n\nTwo different items with the same name are not allowed to exist within the same scope!",
                                        "Error Moving " + cutNodeLeaf.getDispCateg(),
                                        NotifyDescriptor.PLAIN_MESSAGE, NotifyDescriptor.ERROR_MESSAGE);
                                        DialogDisplayer.getDefault().notify(d0);
                                       }
                                     //if there is parameter with same name at destiniation, cancel move
                                       else if(foundParameter>0)
                                       {
                                        NotifyDescriptor d1 = new NotifyDescriptor.Confirmation("The "+
                                        cutNodeLeaf.getDispCateg() + " " + cutNodeLeaf.getDispType()
                                        + " with name " + cutNodeLeaf.getDispName() + "\n"
                                        + " cannot be moved, because this name is already being used for " +foundParameter+" input parameter of the"+ FunPro.getDispCateg()
                                        + " " +FunPro.getDispName()+ " !\n\nAn input parameter cannot have the same   "
                                        +"name with\n a local "+cutNodeLeaf.getDispCateg()+"in the same subprogram!",
                                        "Error Moving " + cutNodeLeaf.getDispCateg(),
                                        NotifyDescriptor.PLAIN_MESSAGE, NotifyDescriptor.ERROR_MESSAGE);
                                       DialogDisplayer.getDefault().notify(d1);
                                       }
                                 else{

                                     if (!(sourceObjScope.equals(objScope))) {
                                NotifyDescriptor d = new NotifyDescriptor.Confirmation("The scope of "+
                                cutNodeLeaf.getDispCateg() + " :" + cutNodeLeaf.getDispType()
                                + "\n" + "with name " + cutNodeLeaf.getDispName()
                                + " is going to change from " + cutNodeLeaf.getDispScope()
                                + " to " + dispScope
                                + " !\n\nDo you want to continue?",
                                "Scope Change Confirmation " + cutNodeLeaf.getDispCateg(),
                                NotifyDescriptor.YES_NO_OPTION, NotifyDescriptor.WARNING_MESSAGE);



                                    if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION) {
                                    cnlu =UpdateScope.UpdateScope(cnlu,dispScope,objScope);
                                    Children newchild = Children.LEAF;
                                    ProxyLookup plul = new ProxyLookup(new Lookup[]{
                                                Lookups.singleton(newchild), cnlu});
                                   
                        PCNodeLeaf child=new PCNodeLeaf(newchild, plul,dispScope,objScope );
                        getChildren().add(new Node[]{child});
                        moveChildAtTheEnd.move((Index.ArrayChildren)getChildren(), child);
                                    cutNodeLeaf.destroy();
  new getUndoInstance(getRootNode());
  modificationMadeClass.getCurrentModificationMadeClass().makeModification();
  programState.getCurrentProgramState().setCompilerStateUNCOMPILED();

                                    }
                                     }

                                }
                                    return null;
                                }
                            };

                }
            }
        }
      }
     return null;
   }

    

    @Override
    public Action[] getActions(boolean popup) {
        if (this.dispName.equals(CoeusProgram.MAIN_PROC)) {
            return new Action[]{
                        //SystemAction.get(MoveUpAction.class),
                        //SystemAction.get(MoveDownAction.class),
                        myMoveUpAction.getMyMoveUpAction(),
                        myMoveDownAction.getMyMoveDownAction(),
                        null,
                        SystemAction.get(PasteAction.class)};

        } else if (this.dispCateg.equals(WizardsDefinitions.FUNCTION)
                || this.dispCateg.equals(WizardsDefinitions.PROCEDURE) ) {
            return new Action[]{
                        //SystemAction.get(MoveUpAction.class),
                        //SystemAction.get(MoveDownAction.class),
                        myMoveUpAction.getMyMoveUpAction(),
                        myMoveDownAction.getMyMoveDownAction(),
                        null,
                        SystemAction.get(PasteAction.class),
                        null,
                        new myDeleteAction(this)};
        }
        else if (isIfThenElseChild(this.dispCateg))
        {
         return new Action[]{ SystemAction.get(PasteAction.class)};
        }
        else {
            return new Action[]{
                        //SystemAction.get(MoveUpAction.class),
                        //SystemAction.get(MoveDownAction.class),
                        myMoveUpAction.getMyMoveUpAction(),
                        myMoveDownAction.getMyMoveDownAction(),
                       null,
                        SystemAction.get(CutAction.class),
                        SystemAction.get(PasteAction.class),
                        null,
                        new myDeleteAction(this)};
        }
    }
}
