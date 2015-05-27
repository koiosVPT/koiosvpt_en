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



/** This code was based on
 * @(#)DefaultTableHeaderCellRenderer.java	1.0 02/24/09
 */

//package darrylbu.renderer;

package org.coeus.wizards.Arrs;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A default cell renderer for a JTableHeader.
 * <P>
 * Extends {@link DefaultTableCellRenderer}.
 * <P>
 * DefaultTableHeaderCellRenderer attempts to provide identical behavior to the
 * renderer which the Swing subsystem uses by default, the Sun proprietary
 * class sun.swing.table.DefaultTableCellHeaderRenderer.
 * <P>
 * To apply any desired customization, DefaultTableHeaderCellRenderer may be
 * suitably extended.
 * 
 * @author Darryl
 */
public class DefaultTableHeaderCellRenderer extends DefaultTableCellRenderer {

   /**
    * Constructs a <code>DefaultTableHeaderCellRenderer</code>.
    * <P>
    * The horizontal alignment and text position are set as appropriate to a
    * table header cell, and the renderer is set to be non-opaque.
    */
   public DefaultTableHeaderCellRenderer() {
      setHorizontalAlignment(CENTER);
      setHorizontalTextPosition(LEFT);
      setBackground(new Color(236,233,216));
      setOpaque(false);
   }

   /**
    * Returns the default table header cell renderer.
    * <P>
    * The icon is set as appropriate for the header cell of a sorted or
    * unsorted column, and the border appropriate to a table header cell is
    * applied.
    * <P>
    * Subclasses may overide this method to provide custom content or
    * formatting.
    * 
    * @param table the <code>JTable</code>.
    * @param value the value to assign to the header cell
    * @param isSelected This parameter is ignored.
    * @param hasFocus This parameter is ignored.
    * @param row This parameter is ignored.
    * @param column the column of the header cell to render
    * @return the default table header cell renderer
    * 
    * @see DefaultTableCellRenderer#getTableCellRendererComponent(JTable,
    * Object, boolean, boolean, int, int)
    */
   @Override
   public Component getTableCellRendererComponent(JTable table, Object value,
         boolean isSelected, boolean hasFocus, int row, int column) {
      super.getTableCellRendererComponent(table, value,
            isSelected, hasFocus, row, column);
      setIcon(getIcon(table, column));
      setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      setBackground(new Color(236,233,216));
      return this;
   }

   /**
    * Overloaded to return an icon suitable to a sorted column, or null if
    * the column is unsorted.
    * 
    * @param table the <code>JTable</code>.
    * @param column the colummn index.
    * @return the sort icon, or null if the column is unsorted.
    */
   protected Icon getIcon(JTable table, int column) {
      SortKey sortKey = getSortKey(table, column);
      if (sortKey != null && sortKey.getColumn() == column) {
         SortOrder sortOrder = sortKey.getSortOrder();
         switch (sortOrder) {
            case ASCENDING:
               return UIManager.getIcon("Table.ascendingSortIcon");
            case DESCENDING:
               return UIManager.getIcon("Table.descendingSortIcon");
         }
      }
      return null;
   }

   protected SortKey getSortKey(JTable table, int column) {
      RowSorter rowSorter = table.getRowSorter();
      if (rowSorter == null) {
         return null;
      }

      List sortedColumns = rowSorter.getSortKeys();
      if (sortedColumns.size() > 0) {
         return (SortKey) sortedColumns.get(0);
      }
      return null;
   }
}
