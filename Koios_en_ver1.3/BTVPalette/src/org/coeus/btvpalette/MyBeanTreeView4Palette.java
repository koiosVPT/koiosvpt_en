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

package org.coeus.btvpalette;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.util.LinkedList;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTreeUI;

import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.openide.explorer.view.BeanTreeView;
import org.openide.explorer.view.NodeRenderer;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Jrd
 */

public class MyBeanTreeView4Palette extends BeanTreeView {

private static LinkedList<Boolean> categoryNodesState = new LinkedList<Boolean>();
private static JTree staticTree=null;
    
	public MyBeanTreeView4Palette() {
		super();
		initializeRenderer();
        this.setRootVisible(false);
        staticTree=tree;
      
      
   	}

	private void initializeRenderer() {
        Font font2use=new Font("Arial", Font.PLAIN, 18); // font determines the width of the row cell
        int cheight=63;
        MyRenderer renderer = new MyRenderer(font2use,cheight);
        tree.setCellRenderer(renderer);
        //tree.putClientProperty("JTree.lineStyle", "None");
      //  UIManager.put("Tree.paintLines", Boolean.FALSE);///NO Lines for Tree
         UIManager.put("Tree.hash", Color.GRAY);///Paint lines  same color as backgroung
         tree.updateUI();
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
            Color c = new Color(236,233,216);
            tree.setBackground(Color.GRAY);
            //load my icon for node handles
            Image myiconc = ImageUtilities.loadImage("org/coeus/btvpalette/pngs/categories/Cat_Handle+.png");
            Image myicone = ImageUtilities.loadImage("org/coeus/btvpalette/pngs/categories/Cat_Handle-.png");
            BasicTreeUI mytreeUI = (BasicTreeUI)tree.getUI();
            mytreeUI.setCollapsedIcon((Icon) myiconc);
            mytreeUI.setExpandedIcon((Icon) myicone);   
            return result;
       }
    }

 public JTree getTree()
 {return tree;}

  public static void getCategoryNodesState()
  {
  categoryNodesState.clear();
  TreeNode root=(TreeNode) staticTree.getModel().getRoot();
  int categoriesNum=root.getChildCount();
  TreePath path=new TreePath(root);
  for (int i=0;i<categoriesNum;i++)
  {
      TreeNode node =  root.getChildAt(i);
      TreePath newpath = path.pathByAddingChild(node);
      int rowFromPath=staticTree.getRowForPath(newpath);
      categoryNodesState.add(staticTree.isExpanded(rowFromPath));
  }

  }

  public static void setCategoryNodesState()
  { 
  TreeNode root=(TreeNode) staticTree.getModel().getRoot();
  int categoriesNum=root.getChildCount();
  TreePath path=new TreePath(root);
  for (int i=0;i<categoriesNum;i++)
  {
      TreeNode node =  root.getChildAt(i);
      TreePath newpath =path.pathByAddingChild(node);
      int rowFromPath=staticTree.getRowForPath(newpath);
   if (categoryNodesState.get(i))
      staticTree.expandRow(rowFromPath);
   else
      staticTree.collapseRow(rowFromPath);
   }
  }

}