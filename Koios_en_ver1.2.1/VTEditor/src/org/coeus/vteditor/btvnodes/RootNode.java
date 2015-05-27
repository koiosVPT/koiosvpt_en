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
import java.awt.Image;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.io.IOException;
import java.util.List;
import javax.swing.Action;


import org.coeus.btvpalette.ProgComp;
import org.coeus.poclasses.ProgramObject;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.actions.PasteAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.PasteType;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author Jrd
 */
public class RootNode extends AbstractNode  {

    private String displayName=null;
    private String objScope = WizardsDefinitions.GLOBAL_EN;
    private String dispScope = WizardsDefinitions.GLOBAL;
    private Lookup cnlu=null;
    private String sourceObjScope=null;

    /** Creates a new instance of RootNode */
    public RootNode(Children children, Lookup lookup) {
        super(children, lookup);
        ProgramObject po =(ProgramObject) lookup.lookup(ProgramObject.class);
        this.setDisplayName(po.getDispName());

    }


     public RootNode(RootNode rootNode,Children children, Lookup lookup) {
        super(children, lookup);
        this.displayName=rootNode.getDisplayName();
        this.setDisplayName(rootNode.getDisplayName());
    }

 
    public Children copyRootNodeChildren()
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
                PCNodeParent child = new PCNodeParent(PCNPnodeChild,newChildren,pl);
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


    @Override
    public Image getIcon(int type) {
        return Utilities.loadImage("org/coeus/vteditor/resources/right-rectangle.png");
    }

    @Override
    public Image getOpenedIcon(int type) {
        return Utilities.loadImage("org/coeus/vteditor/resources/down-rectangle.png");

    }

    @Override
    protected void createPasteTypes(Transferable t, List s) {
        super.createPasteTypes(t, s);
        PasteType paste = getDropType(t, NodeTransfer.COPY + NodeTransfer.MOVE, -1);
        if (null != paste) {
            s.add(paste);
        }
    }

    @Override
    public void setDisplayName(String s) {
        super.setDisplayName(s);
        this.displayName=s;
    }

   public RootNode getRootNode()
   {return this;}

    @Override
    public PasteType getDropType(Transferable t, int arg1, int arg2) {
        //////////////////IF ITEM COMES FROM PALETTE
        if (t.isDataFlavorSupported(ProgComp.PC_DATA_FLAVOR)) {
            try {
                final ProgComp pc = (ProgComp) t.getTransferData(ProgComp.PC_DATA_FLAVOR);
                ///////////IF ITEM IS NODELEAF
                if ((pc.getType().equals("var")) ||
                        (pc.getType().equals("con")) ||
                        (pc.getType().equals("arr"))) {

                    return new PasteType() {

                        @Override
                        public Transferable paste() throws IOException {

                            Children newchild = Children.LEAF;

                            Lookup nObjLookup = CreateNewPObjectFromPalette.CreateNewPObject(pc,dispScope,objScope);
                            if (nObjLookup != null) {
                                ProxyLookup plul = new ProxyLookup(new Lookup[]{
                                            nObjLookup, Lookups.singleton(newchild)});
 
                        PCNodeLeaf child=new PCNodeLeaf(newchild, plul, dispScope, objScope );
                        getChildren().add(new Node[]{child});
                        moveChildAtTheEnd.move((Index.ArrayChildren )getChildren(), child);
  new getUndoInstance(getRootNode());
  modificationMadeClass.getCurrentModificationMadeClass().makeModification();
  programState.getCurrentProgramState().setCompilerStateUNCOMPILED();

                            }
                            //return null;
                            return pc;
                        }
                    };
                } ///////////IF ITEM IS NODEPARENT
                else if ((pc.getType().equals("arr")) ||
                        (pc.getType().equals("pro")) ||
                        (pc.getType().equals("fun"))) {
                    return new PasteType() {

                        @Override
                        public Transferable paste() throws IOException {

                            Children newchild = new CreateMainChild();

                            Lookup nObjLookup = CreateNewPObjectFromPalette.CreateNewPObject(pc,dispScope,objScope);
                            if (nObjLookup != null) {
                             ProxyLookup plul = new ProxyLookup(new Lookup[]{
                                        nObjLookup, Lookups.singleton(newchild)});

                             PCNodeParent child= new PCNodeParent(newchild, plul, dispScope, objScope );
                             getChildren().add(new Node[]{child});
                             moveChildAtTheEnd.move((Index.ArrayChildren )getChildren(), child);
 new getUndoInstance(getRootNode());
 modificationMadeClass.getCurrentModificationMadeClass().makeModification();
 programState.getCurrentProgramState().setCompilerStateUNCOMPILED();

                            }
                            return null;
                        }
                    };
                }
            } catch (UnsupportedFlavorException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        } //IF ITEM IS ALREADY IN THE PROGRAM.... MOVE
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
                if ((type.equals("arr")) || (type.equals("var")) || (type.equals("con"))) {

                    return new PasteType() {

                                @Override
                                public Transferable paste() throws IOException {

                                     AllObjectsList caol = AllObjectsList.getAllObjList();
                                     AllObjects ao =caol.SearchByDisplayName_DispScope(cutNodeLeaf.getDispName(),dispScope);


                                       if (ao!=null)
                                       {
                                        NotifyDescriptor d0 = new NotifyDescriptor.Confirmation("The "+
                                        cutNodeLeaf.getDispCateg() + " " + cutNodeLeaf.getDispType()
                                        + " with name " + cutNodeLeaf.getDispName() + "\n"
                                        + " cannot be moved, because a " + ao.getDispCateg()
                                        + " with name " +ao.getDispName()+ " already exists in scope of "+ dispScope
                                        + " !\n\nTwo different items with the same name are not allowed to exist within the same scope!",
                                        "Error Moving " + cutNodeLeaf.getDispCateg(),
                                        NotifyDescriptor.PLAIN_MESSAGE, NotifyDescriptor.ERROR_MESSAGE);

                                        DialogDisplayer.getDefault().notify(d0);
                                       }
                                       else{

                                     if (!(sourceObjScope.equals(objScope))) {
                                NotifyDescriptor d = new NotifyDescriptor.Confirmation("The scope of "+
                                cutNodeLeaf.getDispCateg() + " " + cutNodeLeaf.getDispType()
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
                        moveChildAtTheEnd.move((Index.ArrayChildren )getChildren(), child);
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
///COPY NOT ALLOWED-------------------------
        ///////////////ELSE ACTION IS COPY
      /*    else  if (copyNode != null){

        ////////////////////////////

        final String type="";

        if ((type.equals("arr"))|| (type.equals("var"))|| (type.equals("con")))
        {
        return new PasteType() {
        @Override
        public Transferable paste() throws IOException {
        System.out.println("\n\nCopy:Paste\n\n");
        Children newchild = Children.LEAF;
        Lookup nObjLookup= CreateNewPObjectFromPalette.CreateNewPObject(type);
        ProxyLookup plul = new ProxyLookup(new Lookup[]{
        nObjLookup,Lookups.singleton(newchild)});
        getChildren().add(new Node[]{new PCNodeLeaf(newchild, plul,scope)});
        return null;
        }
        };
        }
        }*/
        }
        return null;
    }

    @Override
    public Action[] getActions(boolean popup) {
        return new Action[]{
                    SystemAction.get(PasteAction.class)};
    }

   
}
/*private void test(Transferable t)
{
if (NodeTransfer.node(t, DnDConstants.ACTION_COPY_OR_MOVE)!=null)
{System.out.println("\n\nACTION_COPY_OR_MOVE :"+DnDConstants.ACTION_COPY_OR_MOVE+"\n\n");}
if (NodeTransfer.node(t, DnDConstants.ACTION_COPY)!=null)
{System.out.println("\n\nACTION_COPY :"+DnDConstants.ACTION_COPY+"\n\n");}
if (NodeTransfer.node(t, DnDConstants.ACTION_LINK)!=null)
{System.out.println("\n\nACTION_LINK :"+DnDConstants.ACTION_LINK+"\n\n");}
if (NodeTransfer.node(t, DnDConstants.ACTION_MOVE)!=null)
{System.out.println("\n\nACTION_MOVE :"+DnDConstants.ACTION_MOVE+"\n\n");}
if (NodeTransfer.node(t, DnDConstants.ACTION_NONE)!=null)
{System.out.println("\n\nACTION_NONE :"+DnDConstants.ACTION_NONE+"\n\n");}
if (NodeTransfer.node(t, DnDConstants.ACTION_REFERENCE)!=null)
{System.out.println("\n\nACTION_REFERENCE :"+DnDConstants.ACTION_REFERENCE+"\n\n");}


if (NodeTransfer.node(t, NodeTransfer.CLIPBOARD_COPY)!=null)
{System.out.println("\n\n CLIPBOARD_COPY  :"+NodeTransfer.CLIPBOARD_COPY+" \n\n");}
if (NodeTransfer.node(t, NodeTransfer.CLIPBOARD_CUT)!=null)
{System.out.println("\n\n CLIPBOARD_CUT   :"+NodeTransfer.CLIPBOARD_CUT+" \n\n");}
if (NodeTransfer.node(t, NodeTransfer.COPY)!=null)
{System.out.println("\n\n COPY   :"+NodeTransfer.COPY+" \n\n");}
if (NodeTransfer.node(t, NodeTransfer.DND_COPY)!=null)
{System.out.println("\n\n DND_COPY   :"+NodeTransfer.DND_COPY+" \n\n");}
if (NodeTransfer.node(t, NodeTransfer.DND_COPY_OR_MOVE)!=null)
{System.out.println("\n\n DND_COPY_OR_MOVE   :"+NodeTransfer.DND_COPY_OR_MOVE+" \n\n");}
if (NodeTransfer.node(t, NodeTransfer.DND_LINK)!=null)
{System.out.println("\n\n  DND_LINK   :"+NodeTransfer.DND_LINK+" \n\n");}
if (NodeTransfer.node(t, NodeTransfer.DND_MOVE)!=null)
{System.out.println("\n\n DND_MOVE   :"+NodeTransfer.DND_MOVE+" \n\n");}
if (NodeTransfer.node(t, NodeTransfer.DND_NONE)!=null)
{System.out.println("\n\n DND_NONE   :"+NodeTransfer.DND_NONE+" \n\n");}
if (NodeTransfer.node(t, NodeTransfer.DND_REFERENCE)!=null)
{System.out.println("\n\n DND_REFERENCE   :"+NodeTransfer.DND_REFERENCE+" \n\n");}
if (NodeTransfer.node(t, NodeTransfer.MOVE)!=null)
{System.out.println("\n\n MOVE   :"+NodeTransfer.MOVE+" \n\n");}

}*/
