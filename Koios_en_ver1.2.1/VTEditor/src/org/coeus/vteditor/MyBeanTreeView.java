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

import java.awt.Component;
import java.awt.Font;
import java.util.Enumeration;
import java.util.TreeMap;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.openide.explorer.view.BeanTreeView;
import org.openide.explorer.view.NodeRenderer;
import org.openide.nodes.AbstractNode;


/**
 *
 * @author Jrd
 */

public class MyBeanTreeView extends BeanTreeView {

private  TreeMap<String,Boolean> categoryNodesState = new TreeMap<String,Boolean>();

	public MyBeanTreeView() {
		super();
		initializeRenderer();

	}

	private void initializeRenderer() {
        Font font2use=new Font("Arial", Font.PLAIN, 18);
        int cheight=70;
        MyRenderer renderer = new MyRenderer(font2use,cheight);
        tree.setCellRenderer(renderer);
        TreeSelectionModel a = tree.getSelectionModel();
        a.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setSelectionModel(a);
	}




  private static class MyRenderer implements TreeCellRenderer
    {
       private NodeRenderer delegate = new NodeRenderer();
       Font newFont;
       int CellHeight;

        public MyRenderer( Font nFont, int CH) {
            newFont=nFont;
            CellHeight=CH;
        }


       public Component getTreeCellRendererComponent(JTree tree, Object value,
        boolean sel, boolean expanded, boolean leaf, int row,boolean hasFocus)
       {
            Component result = delegate.getTreeCellRendererComponent(tree, value,
                               sel,expanded,leaf, row, hasFocus);
           
            tree.setRowHeight(CellHeight);
            tree.setFont(newFont);
            return result;
       }
    }

  public JTree getTree()
  {return tree;}

  public void collapseAllNodes()
  {
  for (int i = tree.getRowCount() - 1; i > 0; i--)
         tree.collapseRow(i);  
  }

   public void expandAllNodes() {
    TreeNode root = (TreeNode) tree.getModel().getRoot();
    expandAll(tree, new TreePath(root));
  }

  private void expandAll(JTree tree, TreePath parent) {
    TreeNode node = (TreeNode) parent.getLastPathComponent();
    if (node.getChildCount() >= 0) {
      for (Enumeration e = node.children(); e.hasMoreElements();) {
        TreeNode n = (TreeNode) e.nextElement();
        TreePath path = parent.pathByAddingChild(n);
        expandAll(tree, path);
      }
    }
    tree.expandPath(parent);
  }


 public  void getCategoryNodesState(AbstractNode rootNode)
  {
  categoryNodesState.clear();
  TreeNode root=(TreeNode) tree.getModel().getRoot();
  TreePath path=new TreePath(root);
  categoryNodesState.put(rootNode.getDisplayName(),tree.isExpanded(path) );
  getRowState(root,path,rootNode);
  }

  public  void setCategoryNodesState(AbstractNode rootNode)
  { 
  TreeNode root=(TreeNode) tree.getModel().getRoot();
  TreePath path=new TreePath(root); 
   if (categoryNodesState.containsKey(rootNode.getDisplayName())
           && categoryNodesState.get(rootNode.getDisplayName()))
      tree.expandPath(path);
   else
      tree.collapsePath(path);
  setRowState(root,path,rootNode);
  }


  public void getRowState (TreeNode parent, TreePath path,AbstractNode absNode)
  {
  int categoriesNum=parent.getChildCount();
  for (int i=0;i<categoriesNum;i++)
  {
      TreeNode node =  (TreeNode) parent.getChildAt(i);
      TreePath newpath = path.pathByAddingChild(node);
      categoryNodesState.put(absNode.getChildren().getNodeAt(i).getDisplayName(),tree.isExpanded(newpath) );
      getRowState(node,newpath,(AbstractNode) absNode.getChildren().getNodeAt(i));
    }
  }

 public void setRowState(TreeNode parent,TreePath path,AbstractNode absNode)
 {
  int categoriesNum=parent.getChildCount();
  for (int i=0;i<categoriesNum;i++)
  {
   TreeNode node =  parent.getChildAt(i);
   TreePath newpath =path.pathByAddingChild(node);
   
   if (categoryNodesState.containsKey(absNode.getChildren().getNodeAt(i).getDisplayName())
           && categoryNodesState.get(absNode.getChildren().getNodeAt(i).getDisplayName()))
      tree.expandPath(newpath);
   else
      tree.collapsePath(newpath);
   setRowState(node,path,(AbstractNode) absNode.getChildren().getNodeAt(i));
   }
 }



}