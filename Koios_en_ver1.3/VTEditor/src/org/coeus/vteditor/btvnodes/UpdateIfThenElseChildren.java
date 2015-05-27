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


import org.coeus.vteditor.actions.myDeleteAction;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.coeus.poclasses.IfObject;
import org.coeus.wizards.WizardsDefinitions;
import org.coeus.wizards._HelpFuntions.copyList;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author Jrd
 */
public class UpdateIfThenElseChildren {

    private static String path ="org/coeus/btvpalette/pngs/items/";
    private static String mytab = "                 ";




    public UpdateIfThenElseChildren(PCNodeParent ifThenElseObjectNode)
    {
    LinkedList<String> oldStatements= new LinkedList();
    LinkedList<String> oldStatementsCategories = new LinkedList();
    LinkedList<String> newStatements= new LinkedList();
    LinkedList<String> newStatementsCategories = new LinkedList();
    IfObject iffo = ifThenElseObjectNode.getLookup().lookup(IfObject.class);

    new copyList(iffo.getDispStatements(),oldStatements);
    new copyList(iffo.getDispStatementsCategories(),oldStatementsCategories);

//    new printLists(oldStatements);
//    new printLists(oldStatementsCategories);

    iffo.UpdateIfObject();
    newStatements=iffo.getDispStatements();
    newStatementsCategories=iffo.getDispStatementsCategories();

//    new printLists(newStatements);
//    new printLists(newStatementsCategories);
    
     List statementsNodesList= ifThenElseObjectNode.getChildren().snapshot();
     int numOfStatementsChildren=statementsNodesList.size();
     PCNodeParent statementNode=null;
     Children elseChildren = null;
//            for ( int ir=0;ir<statementsObjectsList.size();ir++ )
            for ( int ir=0;ir<newStatements.size();ir++ )
            {
             statementNode =null;
             if(ir<numOfStatementsChildren)
                statementNode = (PCNodeParent)statementsNodesList.get(ir);

                if(ir==0)
                {
                    PCNodeParent newIfNode=updateNode("("+newStatements.get(ir)+")",
                    WizardsDefinitions.IF_STATEMENT,"if","if_statement.png",statementNode);
                    ifThenElseObjectNode.getChildren().add(new Node[]{newIfNode});
                    ifThenElseObjectNode.getChildren().remove(new Node[]{ statementNode});
                }// if
                /// else if this node existed in the tree prior to update
                else if (ir<oldStatementsCategories.size())
                {
                /// if this node (after update) is  ELSE_IF_STATEMENT NODE
                   if (newStatementsCategories.get(ir).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
                    {
                      //if the node in this position prior to update was an ELSE_IF_STATEMENT node
                      if (oldStatementsCategories.get(ir).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
                       {
                        // Create a new elseIfStetement node get the children from
                        // the old one, add it to the ifThenElse Node and delete the old one
                        PCNodeParent newElseIfNode=updateNode("("+newStatements.get(ir)+")",
                        WizardsDefinitions.ELSE_IF_STATEMENT,"elseif","else_if_statement.png",statementNode);
                        ifThenElseObjectNode.getChildren().add(new Node[]{newElseIfNode});
                        ifThenElseObjectNode.getChildren().remove(new Node[]{ statementNode});
                       }//if
                      //else if the node in this position prior to update was an ELSE_STATEMENT node
                       else if (oldStatementsCategories.get(ir).equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT))
                       {
                        // Get the children from the ELSE_STATEMENT node-
                        // to add them if there is an ELSE_STATEMENT found later
                        // Create a new elseIfStetement node
                        //add it to the ifThenElse Node and delete the old one
                        elseChildren=copyChildrenFromOldNode(statementNode);
                        PCNodeParent newElseIfNode = createNewElseIfNode(ifThenElseObjectNode,newStatements.get(ir));
                        ifThenElseObjectNode.getChildren().add(new Node[]{newElseIfNode});
                        ifThenElseObjectNode.getChildren().remove(new Node[]{ statementNode});
                       }//else if
                    }
                /// else if this node (after update) is  ELSE_STATEMENT NODE
                    else if (newStatementsCategories.get(ir).equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT))
                    {
                     //if the node in this position prior to update was an ELSE_IF_STATEMENT node
                       if (oldStatementsCategories.get(ir).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
                       {
                        //Create a new ELSE_STATEMENT Node and delete the rest (if any)
                        //of statements nodes 
                        // If in the rest of statements nodes there is an ELSE_STATEMENT node
                        //get this children and add them to this ELSE_STATEMENT NODE
                        // add this ELSE_STATEMENT NODE to the ifThenElse Statement Node
                        PCNodeParent newElseNode = createNewElseNode(ifThenElseObjectNode);
                        Node[] elseChildren1=getElseChildrenDeleteRestChildren(ifThenElseObjectNode,ir,statementsNodesList);
                         if(elseChildren1!=null)
                            newElseNode.getChildren().add(elseChildren1);
                        ifThenElseObjectNode.getChildren().add(new Node[]{newElseNode});
                        /////////////////////ifThenElseObjectNode.getChildren().remove(new Node[]{ statementNode});
                       }//if
                     //else if the node in this position prior to update was an ELSE_STATEMENT node
                       else if (oldStatementsCategories.get(ir).equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT))
                       {
                        //Create a new ELSE_STATEMENT NODE, get the children from the old one,
                        //add them to the new one, and add it to the ifThenElse Statement Node and delete the old one
                        PCNodeParent newElseNode=updateNode("",WizardsDefinitions.ELSE_STATEMENT,
                                "else","else_statement.png",statementNode);
                        ifThenElseObjectNode.getChildren().add(new Node[]{newElseNode});
                        ifThenElseObjectNode.getChildren().remove(new Node[]{ statementNode});
                       }//else if
                   }//else if
                }//else if            
              else ///else this node is new-added after update
               {
               /// if the new node is  ELSE_IF_STATEMENT NODE
                   if (newStatementsCategories.get(ir).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
                    {
                    // Create a new elseIfStetement node
                    // add it to the ifThenElse Node
                      PCNodeParent newElseIfNode = createNewElseIfNode(ifThenElseObjectNode,newStatements.get(ir));
                      ifThenElseObjectNode.getChildren().add(new Node[]{newElseIfNode});
                    }//if
               /// else if the new node is  ELSE_STATEMENT NODE
                   else if (newStatementsCategories.get(ir).equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT))
                    {
                    // Create a new elseStetement node if there are any children
                    // kept from previous elseStatetement node-now deleted-
                    // add them to the nw node, and add it to the ifThenElse Node
                     PCNodeParent newElseNode = createNewElseNode(ifThenElseObjectNode);
                     if(elseChildren!=null)
                         newElseNode.getChildren().add(this.children2NodeArray(elseChildren));
                     ifThenElseObjectNode.getChildren().add(new Node[]{newElseNode});
                    }//else if
                  }//else
            }//for
  //if there are any old nodes left...delete them
    if (newStatements.size()<oldStatements.size())
    deleteRestChildren(ifThenElseObjectNode);

    }



    private PCNodeParent createNewElseIfNode(PCNodeParent ifThenElseObjectNode,String condition)
    {
    Children newchild = new CreateMainChild();
    ProxyLookup pl = new ProxyLookup(new Lookup[]{Lookups.singleton(newchild)});
    PCNodeParent newElseIfNode =new PCNodeParent(newchild,pl,ifThenElseObjectNode.getDispScope()
            ,ifThenElseObjectNode.getObjScope());
    newElseIfNode.setDisplayName(mytab+"("+condition+")");
    newElseIfNode.mySetIconBaseWithExtension(path+"else_if_statement.png");
    newElseIfNode.setDispName("("+condition+")");
    newElseIfNode.setDispCategory(WizardsDefinitions.ELSE_IF_STATEMENT);
    newElseIfNode.setMyTransferableType("elseif");
    return newElseIfNode;
    }

    private PCNodeParent createNewElseNode(PCNodeParent ifThenElseObjectNode)
    {
    Children newchild = new CreateMainChild();
    ProxyLookup pl = new ProxyLookup(new Lookup[]{Lookups.singleton(newchild)});
    PCNodeParent newElseIfNode =new PCNodeParent(newchild,pl,ifThenElseObjectNode.getDispScope()
            ,ifThenElseObjectNode.getObjScope());
    newElseIfNode.setDisplayName(mytab+"");
    newElseIfNode.mySetIconBaseWithExtension(path+"else_statement.png");
    newElseIfNode.setDispName("");
    newElseIfNode.setDispCategory(WizardsDefinitions.ELSE_STATEMENT);
    newElseIfNode.setMyTransferableType("else");
    return newElseIfNode;
    }



    private void deleteRestChildren(PCNodeParent ifThenElseObjectNode) {
        myDeleteAction mda = new myDeleteAction(ifThenElseObjectNode);
        //Get the current children's list of ifThenElse Node;
        List statementsNodesList= ifThenElseObjectNode.getChildren().snapshot();
        int i=0;
        PCNodeParent node=(PCNodeParent) statementsNodesList.get(i);

        //until the IF_STATEMENT node is found delete all previous children and their children
        while (!node.getDispCateg().equalsIgnoreCase(WizardsDefinitions.IF_STATEMENT))
        {
          //System.out.println("\n\ndrc :i= " +i +"\n\n");
          mda.deleteChildren(node,false);
          try {node.destroy();}
            catch (IOException ex) {}
         i++;
         node=(PCNodeParent) statementsNodesList.get(i);
        }
    }

private Node[] getElseChildrenDeleteRestChildren(PCNodeParent ifThenElseObjectNode,int pos,List statementsNodesList) {
        myDeleteAction mda = new myDeleteAction(ifThenElseObjectNode);

        System.out.println("\n\ngetElseChildrenDeleteRestChildren: " +ifThenElseObjectNode.getDispName()+" "
        +" "+pos+"  "+ statementsNodesList.size()+"\n\n");

        Node[] elseChildren =null;
        for (int i=pos;i<statementsNodesList.size();i++)
        {
        PCNodeParent node=(PCNodeParent) statementsNodesList.get(i);
        if (node.getDispCateg().equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
            mda.deleteChildren(node,false);
        else if (node.getDispCateg().equalsIgnoreCase(WizardsDefinitions.ELSE_STATEMENT))
            elseChildren=this.children2NodeArray(node.copyPCNodeParentChildren());
        try {node.destroy();}
            catch (IOException ex) {}
        }
        return elseChildren;
    }

    private PCNodeParent updateNode(String ititle,String icategory,String imyTransferable,String icon,
            PCNodeParent statementNode)
    {
    Children children = copyChildrenFromOldNode(statementNode);

    ProxyLookup pl = new ProxyLookup(new Lookup[]{Lookups.singleton(children)});
    PCNodeParent newNode =new PCNodeParent(children,pl,statementNode.getDispScope(),statementNode.getObjScope());
    newNode.setDisplayName(mytab+ititle);
    newNode.mySetIconBaseWithExtension(path+icon);
    newNode.setDispName(ititle);
    newNode.setDispCategory(icategory);
    newNode.setMyTransferableType(imyTransferable);
 //       System.out.println("\n\n" + newNode.getDispName() +"\n\n");
//    printChildren(newNode);
    return newNode;
    }



    private Children  copyChildrenFromOldNode( PCNodeParent statementObjectNode) {
        Children children = new CreateMainChild();

         List<Node> statementObjectNodeChildren= statementObjectNode.getChildren().snapshot();
            for ( int ir=0;ir<statementObjectNodeChildren.size();ir++ )
            { 
                Node nodeChild = statementObjectNodeChildren.get(ir);
//   System.out.println("\n\n" + nodeChild.getDisplayName()+"\n\n");
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
                }                 //else System.out.println("\nelse\n");
            }
  //  System.out.println("\n\nAfterUpdate\n");
//    printChildren(children);
     return children;
    }


  private Node [] children2NodeArray(Children children)
  {
    List<Node> PCNodeParentChildren= children.snapshot();

     Node [] nodeArray = new Node[PCNodeParentChildren.size()];
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
                nodeArray[ir]=child;
                }
             else if (nodeChild instanceof PCNodeLeaf)
                {
                 PCNodeLeaf PCNLnodeChild =(PCNodeLeaf) nodeChild;
                 Children newChildren= Children.LEAF;
                 new getLookupFromNode(PCNLnodeChild);
                 ProxyLookup pl = new ProxyLookup(new Lookup[]
                      {getLookupFromNode.createLookupFromObject(),Lookups.singleton(newChildren)});
                PCNodeLeaf child = new PCNodeLeaf(PCNLnodeChild,newChildren,pl);
               // children.add(new Node[]{child});
                nodeArray[ir]=child;
                }
            }
    return  nodeArray;
  }




  private void printChildren(PCNodeParent node)
  {
   List<Node> statementObjectNodeChildren= node.getChildren().snapshot();
   System.out.println("\n\nNodesChildren\n");
   for ( int ir=0;ir<statementObjectNodeChildren.size();ir++ )
            { 
                Node nodeChild = statementObjectNodeChildren.get(ir);
       System.out.println("\n" + nodeChild.getDisplayName());
            }
       System.out.println("\n\n\n");
  
  
  }

 private void printChildren(Children children)
  {
   List<Node> statementObjectNodeChildren= children.snapshot();
   System.out.println("\n\nChildren\n");
            for ( int ir=0;ir<statementObjectNodeChildren.size();ir++ )
            {
                Node nodeChild = statementObjectNodeChildren.get(ir);
       System.out.println("\n" + nodeChild.getDisplayName());
            }
       System.out.println("\n\n\n");


  }


}
