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



package org.coeus.vteditor;

import org.coeus.vteditor.actions.*;
import java.util.LinkedList;
import java.util.List;
import org.coeus.vteditor.btvnodes.CreateMainChild;
import org.coeus.vteditor.btvnodes.PCNodeLeaf;
import org.coeus.vteditor.btvnodes.PCNodeParent;
import org.coeus.vteditor.btvnodes.RootNode;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author Jrd
 */
public class saveNodeClass {
        
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
    
    
    // private AbstractNode node=null;
     private Object lookupObject=null;
     private String nodeType=null;
     private String displayScope=null;
     private String objectScope=null;
     private ifThenElseSaveLoadStruct itesls=null;

     private LinkedList<saveNodeClass> childrens=new LinkedList();
     private final static String ROOT="root";
     private final static String PARENT="parent";
     private final static String LEAF="leaf";


   


        public LinkedList<saveNodeClass> getChildrens() {
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

      public void setChildrens(LinkedList<saveNodeClass> childrens) {
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



//        public void setNode(AbstractNode node) {
//            this.node = node;
//        }

        //        public AbstractNode getNode() {
//            return node;
//        }

    

//        public void setNodeLookup(Lookup nodeLookup) {
//
//           new getObjectFromLookUp(nodeLookup);
//           this.nodeLookup = new ProxyLookup(new Lookup[]{Lookups.fixed(
//              new Object[] {getObjectFromLookUp.createLookupFromObject()})} );
//        }


public saveNodeClass saveNodes (AbstractNode an)
   {
    List<Node> childrenList=null;
    int childrenNumber=0;

   saveNodeClass save_node= new saveNodeClass();
   if (an instanceof RootNode)
   {
   RootNode rn=(RootNode)an;
   save_node.setNodeType(ROOT);
   save_node.setDisplayScope(WizardsDefinitions.GLOBAL);
   save_node.setObjectScope(WizardsDefinitions.GLOBAL_EN);
   save_node.setLookupObject(
           new getObjectFromNodeLookup(rn.getLookup()).getLookupObject());
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
           new getObjectFromNodeLookup(pn.getLookup()).getLookupObject());
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
           new getObjectFromNodeLookup(ln.getLookup()).getLookupObject());
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



 public RootNode loadRootNode(saveNodeClass sv)
{
 Children newChildren= new CreateMainChild();

 ProxyLookup rnodeLookup = new ProxyLookup(new Lookup[]{
            Lookups.singleton(newChildren),
             new getObjectFromNodeLookup(sv.getLookupObject()).createLookupFromObject() } );

 RootNode rNode = new RootNode(newChildren,rnodeLookup);

 for (saveNodeClass csv:sv.getChildrens())
    {
     if (csv.getNodeType().equals(PARENT))
       rNode.getChildren().add(new Node[]{loadParentNode(csv)});
     else if (csv.getNodeType().equals(LEAF) )
       rNode.getChildren().add(new Node[]{loadLeafNode(csv)});
    }

 return rNode;
 }

 private PCNodeParent loadParentNode(saveNodeClass sv) {

 Children newChildren= new CreateMainChild();

 ProxyLookup pnodeLookup = new ProxyLookup(new Lookup[]{
     Lookups.singleton(newChildren),
     new getObjectFromNodeLookup(sv.getLookupObject()).createLookupFromObject() } );

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
 

 for (saveNodeClass csv:sv.getChildrens())
    {
     if (csv.getNodeType().equals(PARENT) )
            parentNode.getChildren().add(new Node[]{loadParentNode(csv)});
     else if (csv.getNodeType().equals(LEAF) )
       parentNode.getChildren().add(new Node[]{loadLeafNode(csv)});
    }


 return parentNode;
    }


 private PCNodeLeaf loadLeafNode(saveNodeClass sv) {
 Children newChildren= Children.LEAF;

 ProxyLookup lnodeLookup = new ProxyLookup(new Lookup[]{
         Lookups.singleton(newChildren),
         new getObjectFromNodeLookup(sv.getLookupObject()).createLookupFromObject() } );


 PCNodeLeaf leafNode = new PCNodeLeaf(newChildren,lnodeLookup,sv.getDisplayScope(),sv.getObjectScope());

 return leafNode;

 }



// public saveNodeClass saveNodes (AbstractNode an)
//   {
//    List<Node> childrenList=null;
//    int childrenNumber=0;
//
//   saveNodeClass save_node= new saveNodeClass();
//   if (an instanceof RootNode)
//   {
//   RootNode rn=(RootNode)an;
//   save_node.setNode(rn);
//   save_node.setLookupObject(
//           new getObjectFromNodeLookup(rn.getLookup()).getLookupObject());
//   childrenList = rn.getChildren().snapshot();
//   childrenNumber=childrenList.size();
//   }
//   else if(an instanceof PCNodeParent)
//   {
//   PCNodeParent pn=(PCNodeParent)an;
//   save_node.setNode(pn);
//   save_node.setLookupObject(
//           new getObjectFromNodeLookup(pn.getLookup()).getLookupObject());
//   childrenList = pn.getChildren().snapshot();
//   childrenNumber=childrenList.size();
//  }
//   else if(an instanceof PCNodeLeaf)
//   {
//   PCNodeLeaf ln=(PCNodeLeaf)an;
//   save_node.setNode(ln);
//   save_node.setLookupObject(
//           new getObjectFromNodeLookup(ln.getLookup()).getLookupObject());
//   }
//
//   for (int i=0;i<childrenNumber;i++)
//   {
//    Node n = childrenList.get(i);
//    if (n instanceof PCNodeParent)
//     {
//      PCNodeParent np=(PCNodeParent)n;
//      save_node.getChildrens().add(saveNodes(np));
//      }
//    else if(n instanceof PCNodeLeaf)
//     {
//       PCNodeLeaf nl=(PCNodeLeaf)n;
//       save_node.getChildrens().add(saveNodes(nl));
//     }
//   }
//  return save_node;
//  }
//
//
//
// public RootNode loadRootNode(saveNodeClass sv)
//{
// Children newChildren= new CreateMainChild();
//
// ProxyLookup rnodeLookup = new ProxyLookup(new Lookup[]{
//            Lookups.singleton(newChildren),
//             new getObjectFromNodeLookup(sv.getLookupObject()).createLookupFromObject() } );
//
// RootNode rNode = new RootNode((RootNode)sv.getNode(),newChildren,rnodeLookup);
//
// for (saveNodeClass csv:sv.getChildrens())
//    {
//     if (csv.getNode() instanceof PCNodeParent )
//     {
//       PCNodeParent pn=(PCNodeParent) csv.getNode();
//       rNode.getChildren().add(new Node[]{loadParentNode(pn,csv)});
//     }
//     else if (csv.getNode() instanceof PCNodeLeaf )
//     {
//       PCNodeLeaf pl=(PCNodeLeaf) csv.getNode();
//       rNode.getChildren().add(new Node[]{loadLeafNode(pl,csv)});
//     }
//    }
//
// return rNode;
// }
//
// private PCNodeParent loadParentNode(PCNodeParent pNode,saveNodeClass sv) {
//
// Children newChildren= new CreateMainChild();
//
//
//
// ProxyLookup pnodeLookup = new ProxyLookup(new Lookup[]{
//     Lookups.singleton(newChildren),
//     new getObjectFromNodeLookup(sv.getLookupObject()).createLookupFromObject() } );
//
// PCNodeParent parentNode = new PCNodeParent(pNode,newChildren,pnodeLookup);
//
// for (saveNodeClass csv:sv.getChildrens())
//    {
//     if (csv.getNode() instanceof PCNodeParent )
//     {
//       PCNodeParent pn=(PCNodeParent) csv.getNode();
//       parentNode.getChildren().add(new Node[]{loadParentNode(pn,csv)});
//     }
//     else if (csv.getNode() instanceof PCNodeLeaf )
//     {
//       PCNodeLeaf pl=(PCNodeLeaf) csv.getNode();
//       parentNode.getChildren().add(new Node[]{loadLeafNode(pl,csv)});
//     }
//    }
//
//
// return parentNode;
//    }
//
//
// private PCNodeLeaf loadLeafNode(PCNodeLeaf lNode,saveNodeClass sv) {
// Children newChildren= Children.LEAF;
//
// ProxyLookup lnodeLookup = new ProxyLookup(new Lookup[]{
//         Lookups.singleton(newChildren),
//         new getObjectFromNodeLookup(sv.getLookupObject()).createLookupFromObject() } );
//
//
// PCNodeLeaf leafNode = new PCNodeLeaf(lNode,newChildren,lnodeLookup);
//
// return leafNode;
//
// }


  public void printSaveList() {
       // System.out.println("\n\n" + loadNode.getNode().getDisplayName() +"\n\n");
        LinkedList<saveNodeClass> list= this.getChildrens();
        for (saveNodeClass sn:list)
        {
//        System.out.println("\n\n" + sn.getNode().getDisplayName()+" :  number of children = "+sn.getChildrens().size() +"\n\n");
        sn.printSaveList();
        }

        System.out.println("\n\n--------------------------\n\n");
     //   System.out.println("\n" + this.getNode().getDisplayName() +"\n");

    }

}
