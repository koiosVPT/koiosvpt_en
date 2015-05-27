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


import java.util.LinkedList;
import java.util.List;
import org.coeus.poclasses.IfObject;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author Jrd
 */
public class ifThenElseChildren extends Index.ArrayChildren{
 
    private String dispScope=null;
    private String objScope=null;
    private LinkedList<String> statements=new LinkedList();
    private LinkedList<String> statementsCategories=new LinkedList();
    
    private static String path ="org/coeus/btvpalette/pngs/items/";
    private static String mytab = "                 ";

    
    public ifThenElseChildren(Lookup nObjLookup,String idispScope,String iobjScope) {
        IfObject io = nObjLookup.lookup(IfObject.class);
        this.dispScope=idispScope;
        this.objScope=iobjScope;
        this.statements=io.getDispStatements();
        this.statementsCategories=io.getDispStatementsCategories();

    }

    @Override
    protected List<Node> initCollection() {
    LinkedList childrenNodes = new LinkedList();
    String title="";
    String category="";
    String myTransferableType="";
    String icon;

    for (int i=0;i<statements.size();i++)
    {
    if (statementsCategories.get(i).equalsIgnoreCase(WizardsDefinitions.IF_STATEMENT))
    {
        title="("+statements.get(i)+")";
        category=WizardsDefinitions.IF_STATEMENT;
        myTransferableType="if";
        icon="if_statement.png";
    }
    else if (statementsCategories.get(i).equalsIgnoreCase(WizardsDefinitions.ELSE_IF_STATEMENT))
    {
        title="("+statements.get(i)+")";
        category=WizardsDefinitions.ELSE_IF_STATEMENT;
        myTransferableType="elseif";
        icon="else_if_statement.png";
    }
    else
    {
        title="";
        category=WizardsDefinitions.ELSE_STATEMENT;
        myTransferableType="else";
        icon="else_statement.png";
    }
    Children newchild = new CreateMainChild();
    ProxyLookup pl = new ProxyLookup(new Lookup[]{Lookups.singleton(newchild)});
    PCNodeParent newNode =new PCNodeParent(newchild,pl,this.dispScope,this.objScope);
    newNode.setDisplayName(mytab+title);
    newNode.mySetIconBaseWithExtension(path+icon);
    newNode.setDispName(title);
    newNode.setDispCategory(category);
    newNode.setMyTransferableType(myTransferableType);
    childrenNodes.add(newNode);
    }
    return childrenNodes;
    }

   
}
