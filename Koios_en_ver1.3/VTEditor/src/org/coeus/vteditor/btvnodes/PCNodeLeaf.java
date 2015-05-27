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
import org.coeus.vteditor.actions.myDeleteAction;
import org.coeus.vteditor.modificationMadeClass;
import javax.swing.Action;

import org.coeus.vteditor.actions.myMoveDownAction;
import org.coeus.vteditor.actions.myMoveUpAction;
import org.coeus.vteditor.mypropertieswindow.myPropertiesTopComponent;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.actions.CutAction;


import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author Jrd
 */
public class PCNodeLeaf extends AbstractNode {

    //String mytab = "                 ";
    String [] ObjAttrib =new String [5];
    private String myTransferableType;
    private String dispCateg;
    private String dispName;
    private String dispType;
    private String dispScope;
    private String objValue;
    private String objScope;
    private String iconBaseWithExtension;
  



   

    public PCNodeLeaf(Children c, ProxyLookup plu,String inDispScope,String inObjScope) {
        //this(c, new InstanceContent());
        super(c, plu);
        this.objScope=inObjScope;
        this.dispScope=inDispScope;
        this.ObjAttrib=SetNodeAttFromObject.SetNodeLeafAttributes(this);
        this.myTransferableType=ObjAttrib[0];
        this.dispCateg=ObjAttrib[1];
        this.dispName=ObjAttrib[2];
        this.dispType=ObjAttrib[3];
        this.objValue=ObjAttrib[4];


      }


     public PCNodeLeaf(PCNodeLeaf sourceNode,Children children,ProxyLookup plu) {
       
        super(children, plu);
        this.objScope=sourceNode.getObjScope();
        this.dispScope=sourceNode.getDispScope();
        this.ObjAttrib=SetNodeAttFromObject.SetNodeLeafAttributes(this);
        this.myTransferableType=ObjAttrib[0];
        this.dispCateg=ObjAttrib[1];
        this.dispName=ObjAttrib[2];
        this.dispType=ObjAttrib[3];
        this.objValue=ObjAttrib[4];


      }


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



    @Override
    public Action getPreferredAction() {


        Lookup nodesLookup = this.getLookup();  
        new  UpdateNodesObject(nodesLookup);
        this.ObjAttrib=SetNodeAttFromObject.SetNodeLeafAttributes(this);
        this.myTransferableType=ObjAttrib[0];
        this.dispCateg=ObjAttrib[1];
        this.dispName=ObjAttrib[2];
        this.dispType=ObjAttrib[3];
        this.objValue=ObjAttrib[4];
 new getUndoInstance(getRootNode());
 modificationMadeClass.getCurrentModificationMadeClass().makeModification();
 programState.getCurrentProgramState().setCompilerStateUNCOMPILED();

myPropertiesTopComponent.getCurrent().resultChanged(new LookupEvent (nodesLookup.lookupResult(Node.class)));

        return super.getPreferredAction();
    }

public RootNode getRootNode()
{
  Node parentNode=this.getParentNode();

  while(!(parentNode instanceof RootNode))
   parentNode=parentNode.getParentNode();

  return (RootNode) parentNode;
}

   
    @Override
    public boolean canCut() {
        return true;
    }

     


    @Override
    public boolean canDestroy() {
       return true;
    }

 

    @Override
    public Action[] getActions(boolean popup) {
         if (this.dispCateg.equals(WizardsDefinitions.VARIABLE) || this.dispCateg.equals(WizardsDefinitions.CONSTANT) || this.dispCateg.equals(WizardsDefinitions.ARRAY) ) {
            return new Action[]{
                        myMoveUpAction.getMyMoveUpAction(),
                        myMoveDownAction.getMyMoveDownAction(),
                        //SystemAction.get(MoveUpAction.class),
                        //SystemAction.get(MoveDownAction.class),
                        null,
                        SystemAction.get(CutAction.class),
                        null,
                        new myDeleteAction(this)};
         }
         else
         {
                    return new Action[]{
                        myMoveUpAction.getMyMoveUpAction(),
                        myMoveDownAction.getMyMoveDownAction(),
                        //SystemAction.get(MoveUpAction.class),
                        //SystemAction.get(MoveDownAction.class),
                        null,
                       // SystemAction.get(CutAction.class),
                        null,
                        new myDeleteAction(this)};
         }
        }
    
}
