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
import java.util.LinkedList;
import java.util.List;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;


import org.coeus.poclasses.ArrayObject;
import org.coeus.poclasses.AssignObject;
import org.coeus.poclasses.CallObject;
import org.coeus.poclasses.ConstantObject;
import org.coeus.poclasses.DoWhileObject;
import org.coeus.poclasses.ForObject;
import org.coeus.poclasses.FunctionObject;
import org.coeus.poclasses.IfObject;
import org.coeus.poclasses.ProcedureObject;
import org.coeus.poclasses.ReadObject;
import org.coeus.poclasses.ReturnObject;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.WhileObject;
import org.coeus.poclasses.WriteObject;
import org.coeus.wizards.AllObjects;


//import org.coeus.poclasses.ArrayObjectList;
//import org.coeus.poclasses.AssignObjectList;
//import org.coeus.poclasses.CallObjectList;
//import org.coeus.poclasses.ConstantObjectList;
//import org.coeus.poclasses.DoWhileObjectList;
//import org.coeus.poclasses.ForObjectList;
//import org.coeus.poclasses.FunctionObjectList;
//import org.coeus.poclasses.IfObjectList;
//import org.coeus.poclasses.ProcedureObjectList;
//import org.coeus.poclasses.ReadObjectList;
//import org.coeus.poclasses.ReturnObjectList;
//import org.coeus.poclasses.VariableObjectList;
//import org.coeus.poclasses.WhileObjectList;
//import org.coeus.poclasses.WriteObjectList;
//import org.coeus.wizards.AllObjectsList;


/**
 *
 * @author Jrd
 */
public class undoRedoNodeClass {
        
      public class ifThenElseSaveLoadStruct
     {
     String displayName=null;
     String icon=null;
     String dispName=null;
     String category=null;
     String myTransfearble=null;

        public ifThenElseSaveLoadStruct(String iDisplayName,String iIcon,
                String iDispName,String iCategory, String iMyTransferable) {
          this.displayName=iDisplayName;
          this.icon=iIcon;
          this.dispName=iDispName;
          this.category=iCategory;
          this.myTransfearble=iMyTransferable;
        }

        public String getCategory() {
            return category;
        }

        public String getDispName() {
            return dispName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getIcon() {
            return icon;
        }

        public String getMyTransfearble() {
            return myTransfearble;
        }
     }
    
    

     private Object lookupObject=null;
     private String nodeType=null;
     private String displayScope=null;
     private String objectScope=null;
     private ifThenElseSaveLoadStruct itesls=null;

     private LinkedList<undoRedoNodeClass> childrens=new LinkedList();
     private final static String ROOT="root";
     private final static String PARENT="parent";
     private final static String LEAF="leaf";


   


        public LinkedList<undoRedoNodeClass> getChildrens() {
            return childrens;
        }

        public Object getLookupObject() {
            return lookupObject;
        }

    public String getNodeType() {
        return nodeType;
    }

    public String getDisplayScope() {
        return displayScope;
    }

    public String getObjectScope() {
        return objectScope;
    }

    public ifThenElseSaveLoadStruct getItesls() {
        return itesls;
    }



        public void setLookupObject(Object lookupObject) {
            this.lookupObject = lookupObject;
        }

      public void setChildrens(LinkedList<undoRedoNodeClass> childrens) {
            this.childrens = childrens;
        }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public void setDisplayScope(String displayScope) {
        this.displayScope = displayScope;
    }

    public void setObjectScope(String objectScope) {
        this.objectScope = objectScope;
    }

    public void setItesls(ifThenElseSaveLoadStruct itesls) {
        this.itesls = itesls;
    }






public undoRedoNodeClass saveNodes (AbstractNode an)
   {
    List<Node> childrenList=null;
    int childrenNumber=0;

   undoRedoNodeClass save_node= new undoRedoNodeClass();
   if (an instanceof RootNode)
   {           
   RootNode rn=(RootNode)an;
   save_node.setNodeType(ROOT);
   save_node.setDisplayScope(WizardsDefinitions.GLOBAL);
   save_node.setObjectScope(WizardsDefinitions.GLOBAL_EN);
   save_node.setLookupObject(
           new copyObjectFromNodeLookup(rn.getLookup()).getLookupObject());
   childrenList = rn.getChildren().snapshot();
   childrenNumber=childrenList.size();
   }
   else if(an instanceof PCNodeParent)
   {
   PCNodeParent pn=(PCNodeParent)an;
   save_node.setNodeType(PARENT);
   save_node.setDisplayScope(pn.getDispScope());
   save_node.setObjectScope(pn.getObjScope());
   save_node.setLookupObject(
           new copyObjectFromNodeLookup(pn.getLookup()).getLookupObject());
   childrenList = pn.getChildren().snapshot();
   childrenNumber=childrenList.size();
   if (pn.isIfThenElseChild(pn.getDispCateg()))
    save_node.setItesls(new ifThenElseSaveLoadStruct(pn.getDisplayName(),pn.getIconBaseWithExtension(),
            pn.getDispName(),pn.getDispCateg(),pn.getMyTransferableType()));
  }
   else if(an instanceof PCNodeLeaf)
   {
   PCNodeLeaf ln=(PCNodeLeaf)an;
   save_node.setDisplayScope(ln.getDispScope());
   save_node.setObjectScope(ln.getObjScope());
   save_node.setNodeType(LEAF);
   save_node.setLookupObject(
           new copyObjectFromNodeLookup(ln.getLookup()).getLookupObject());
   }

   for (int i=0;i<childrenNumber;i++)
   {
    Node n = childrenList.get(i);
    if (n instanceof PCNodeParent)
     {
      PCNodeParent np=(PCNodeParent)n;
      save_node.getChildrens().add(saveNodes(np));
      }
    else if(n instanceof PCNodeLeaf)
     {
       PCNodeLeaf nl=(PCNodeLeaf)n;
       save_node.getChildrens().add(saveNodes(nl));
     }
   }
  return save_node;
  }



 public RootNode loadRootNode(undoRedoNodeClass sv,LinkedList<ConstantObject> iconl,
    LinkedList <VariableObject> ivarl,LinkedList <ArrayObject> iarrl,
    LinkedList <FunctionObject> ifunl,LinkedList <ProcedureObject> iprol,
    LinkedList <ReadObject> ireadl,LinkedList <WriteObject> iwritel,
    LinkedList <CallObject> icalll,LinkedList <ReturnObject> iretl,
    LinkedList <AssignObject> iassl,LinkedList <DoWhileObject> idwhl,
    LinkedList <ForObject> iforl,LinkedList<IfObject> iiffl,
    LinkedList <WhileObject> iwhil,LinkedList<AllObjects> aoll)
{
 Children newChildren= new CreateMainChild();

 ProxyLookup rnodeLookup = new ProxyLookup(new Lookup[]{
            Lookups.singleton(newChildren),
             new copyObjectFromNodeLookup(sv.getLookupObject()).createLookupFromObject(iconl, ivarl, iarrl, ifunl,
      iprol,ireadl,iwritel,icalll,iretl,iassl,idwhl,iforl,iiffl,iwhil,aoll) } );

 RootNode rNode = new RootNode(newChildren,rnodeLookup);

 for (undoRedoNodeClass csv:sv.getChildrens())
    {
     if (csv.getNodeType().equals(PARENT))
       rNode.getChildren().add(new Node[]{loadParentNode(csv,iconl, ivarl, iarrl, ifunl,
      iprol,ireadl,iwritel,icalll,iretl,iassl,idwhl,iforl,iiffl,iwhil,aoll)});
     else if (csv.getNodeType().equals(LEAF) )
       rNode.getChildren().add(new Node[]{loadLeafNode(csv,iconl, ivarl, iarrl, ifunl,
      iprol,ireadl,iwritel,icalll,iretl,iassl,idwhl,iforl,iiffl,iwhil,aoll)});
    }

 return rNode;
 }

 private PCNodeParent loadParentNode(undoRedoNodeClass sv,LinkedList<ConstantObject> iconl,
    LinkedList <VariableObject> ivarl,LinkedList <ArrayObject> iarrl,
    LinkedList <FunctionObject> ifunl,LinkedList <ProcedureObject> iprol,
    LinkedList <ReadObject> ireadl,LinkedList <WriteObject> iwritel,
    LinkedList <CallObject> icalll,LinkedList <ReturnObject> iretl,
    LinkedList <AssignObject> iassl,LinkedList <DoWhileObject> idwhl,
    LinkedList <ForObject> iforl,LinkedList<IfObject> iiffl,
    LinkedList <WhileObject> iwhil,LinkedList<AllObjects> aoll) {

 Children newChildren= new CreateMainChild();

 ProxyLookup pnodeLookup = new ProxyLookup(new Lookup[]{
     Lookups.singleton(newChildren),
     new copyObjectFromNodeLookup(sv.getLookupObject()).createLookupFromObject(iconl, ivarl, iarrl, ifunl,
      iprol,ireadl,iwritel,icalll,iretl,iassl,idwhl,iforl,iiffl,iwhil,aoll) } );

 PCNodeParent parentNode = new PCNodeParent(newChildren,pnodeLookup,sv.getDisplayScope(),sv.getObjectScope());

 if (sv.getItesls()!=null)
 {
 ifThenElseSaveLoadStruct nitesls=sv.getItesls();
 parentNode.setDisplayName(nitesls.getDisplayName());
 parentNode.mySetIconBaseWithExtension(nitesls.getIcon());
 parentNode.setDispName(nitesls.getDispName());
 parentNode.setDispCategory(nitesls.getCategory());
 parentNode.setMyTransferableType(nitesls.getMyTransfearble());
 }
 

 for (undoRedoNodeClass csv:sv.getChildrens())
    {
     if (csv.getNodeType().equals(PARENT) )
            parentNode.getChildren().add(new Node[]{loadParentNode(csv,iconl, ivarl, iarrl, ifunl,
      iprol,ireadl,iwritel,icalll,iretl,iassl,idwhl,iforl,iiffl,iwhil,aoll)});
     else if (csv.getNodeType().equals(LEAF) )
       parentNode.getChildren().add(new Node[]{loadLeafNode(csv,iconl, ivarl, iarrl, ifunl,
      iprol,ireadl,iwritel,icalll,iretl,iassl,idwhl,iforl,iiffl,iwhil,aoll)});
    }


 return parentNode;
    }


 private PCNodeLeaf loadLeafNode(undoRedoNodeClass sv,LinkedList<ConstantObject> iconl,
    LinkedList <VariableObject> ivarl,LinkedList <ArrayObject> iarrl,
    LinkedList <FunctionObject> ifunl,LinkedList <ProcedureObject> iprol,
    LinkedList <ReadObject> ireadl,LinkedList <WriteObject> iwritel,
    LinkedList <CallObject> icalll,LinkedList <ReturnObject> iretl,
    LinkedList <AssignObject> iassl,LinkedList <DoWhileObject> idwhl,
    LinkedList <ForObject> iforl,LinkedList<IfObject> iiffl,
    LinkedList <WhileObject> iwhil,LinkedList<AllObjects> aoll) {
 Children newChildren= Children.LEAF;

 ProxyLookup lnodeLookup = new ProxyLookup(new Lookup[]{
         Lookups.singleton(newChildren),
         new copyObjectFromNodeLookup(sv.getLookupObject()).createLookupFromObject(iconl, ivarl, iarrl, ifunl,
      iprol,ireadl,iwritel,icalll,iretl,iassl,idwhl,iforl,iiffl,iwhil,aoll) } );


 PCNodeLeaf leafNode = new PCNodeLeaf(newChildren,lnodeLookup,sv.getDisplayScope(),sv.getObjectScope());

 return leafNode;

 }



  public void printSaveList() {
       // System.out.println("\n\n" + loadNode.getNode().getDisplayName() +"\n\n");
        LinkedList<undoRedoNodeClass> list= this.getChildrens();
        for (undoRedoNodeClass sn:list)
        {
//        System.out.println("\n\n" + sn.getNode().getDisplayName()+" :  number of children = "+sn.getChildrens().size() +"\n\n");
        sn.printSaveList();
        }

        System.out.println("\n\n--------------------------\n\n");
     //   System.out.println("\n" + this.getNode().getDisplayName() +"\n");

    }

}
