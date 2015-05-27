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


import org.openide.nodes.Children;
import org.openide.nodes.Node;


public class CategoryChildren extends Children.Keys <Category> {

    private boolean advance;

    private String[][] categories = new String[][]{
        {"Variables - Constants","Cat_ConVar.png"},{"Input - Output Commands","Cat_InOut.png"},
        {"Conditional Statement","Cat_if.png"},{"Iteration Statement","Cat_ForWhile.png"},
        {"Call - Assign","Cat_AssignCall.png"},{"Procedures - Functions","Cat_FunProc.png"}
       };

     private String path ="org/coeus/btvpalette/pngs/categories/";

    public CategoryChildren(boolean advance) {
    this.advance=advance;
    }

    protected Node[] createNodes(Category key) {
        Category obj = key;
        return new Node[] { new CategoryNode(obj,this.advance) };
    }

    @Override
    protected void addNotify() {
        super.addNotify();
        Category[] objs = new Category[categories.length];
        for (int i = 0; i < objs.length; i++) {
            Category cat = new Category() {};
            cat.setName(categories[i][0]);
            cat.setIcon(path+categories[i][1]);
            objs[i] = cat;
        }
        setKeys(objs);
    }

   

}