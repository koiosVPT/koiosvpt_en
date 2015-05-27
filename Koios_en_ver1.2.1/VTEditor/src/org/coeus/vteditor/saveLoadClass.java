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

import java.util.LinkedList;
import org.coeus.vteditor.btvnodes.RootNode;
import org.coeus.poclasses.ArrayObject;
import org.coeus.poclasses.ArrayObjectList;
import org.coeus.poclasses.AssignObject;
import org.coeus.poclasses.AssignObjectList;
import org.coeus.poclasses.CallObject;
import org.coeus.poclasses.CallObjectList;
import org.coeus.poclasses.ConstantObject;
import org.coeus.poclasses.ConstantObjectList;
import org.coeus.poclasses.DoWhileObject;
import org.coeus.poclasses.DoWhileObjectList;
import org.coeus.poclasses.ForObject;
import org.coeus.poclasses.ForObjectList;
import org.coeus.poclasses.FunctionObject;
import org.coeus.poclasses.FunctionObjectList;
import org.coeus.poclasses.IfObject;
import org.coeus.poclasses.IfObjectList;
import org.coeus.poclasses.ProcedureObject;
import org.coeus.poclasses.ProcedureObjectList;
import org.coeus.poclasses.ReadObject;
import org.coeus.poclasses.ReadObjectList;
import org.coeus.poclasses.ReturnObject;
import org.coeus.poclasses.ReturnObjectList;
import org.coeus.poclasses.VariableObject;
import org.coeus.poclasses.VariableObjectList;
import org.coeus.poclasses.WhileObject;
import org.coeus.poclasses.WhileObjectList;
import org.coeus.poclasses.WriteObject;
import org.coeus.poclasses.WriteObjectList;
import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;

/**
 *
 * @author Jrd
 */
public class saveLoadClass {

   // private RootNode rnode=null;



    private LinkedList <ConstantObject> conl=new LinkedList();
    private LinkedList <VariableObject> varl=new LinkedList();
    private LinkedList <ArrayObject> arrl=new LinkedList();
    private LinkedList <FunctionObject> funl=new LinkedList();
    private LinkedList <ProcedureObject> prol=new LinkedList();
    private LinkedList <ReadObject> readl=new LinkedList();
    private LinkedList <WriteObject> writel=new LinkedList();
    private LinkedList <CallObject> calll=new LinkedList();
    private LinkedList <ReturnObject> retl=new LinkedList();
    private LinkedList <AssignObject> assl=new LinkedList();
    private LinkedList <DoWhileObject> dwhl=new LinkedList();
    private LinkedList <ForObject> forl=new LinkedList();
    private LinkedList <IfObject> iffl=new LinkedList();
    private LinkedList <WhileObject> whil=new LinkedList();
    private LinkedList <AllObjects> aol=new LinkedList();


    private saveNodeClass saveRootNode=null;



//public void saveRootNodeLists(RootNode rNode,ConstantObjectList iconl,VariableObjectList ivarl,
//    ArrayObjectList iarrl,FunctionObjectList ifunl,ProcedureObjectList iprol,ReadObjectList ireadl,
//    WriteObjectList iwritel,CallObjectList icalll,ReturnObjectList iretl,AssignObjectList iassl,
//    DoWhileObjectList idwhl,ForObjectList iforl,IfObjectList iiffl,WhileObjectList iwhil,AllObjectsList aoll)
//{
// this.saveRootNode=new saveNodeClass();
// this.saveRootNode=this.saveRootNode.saveNodes(rNode);
// this.conl=iconl.copyConstantObjectList();
// this.varl=ivarl.copyVariableObjectList();
// this.arrl=iarrl.copyArrayObjectList();
// this.funl=ifunl.copyFunctionObjectList();
// this.prol=iprol.copyProcedureObjectList();
// this.readl=ireadl.copyReadObjectList();
// this.writel=iwritel.copyWriteObjectList();
// this.calll=icalll.copyCallObjectList();
// this.retl=iretl.copyReturnObjectList();
// this.assl=iassl.copyAssignObjectList();
// this.dwhl=idwhl.copyDoWhileObjectList();
// this.forl=iforl.copyForObjectList();
// this.iffl=iiffl.copyIfObjectList();
// this.whil=iwhil.copyWhileObjectList();
// this.aol=aoll.copyAllObjectsList();
//}

public void saveRootNodeLists(RootNode rNode,ConstantObjectList iconl,VariableObjectList ivarl,
    ArrayObjectList iarrl,FunctionObjectList ifunl,ProcedureObjectList iprol,ReadObjectList ireadl,
    WriteObjectList iwritel,CallObjectList icalll,ReturnObjectList iretl,AssignObjectList iassl,
    DoWhileObjectList idwhl,ForObjectList iforl,IfObjectList iiffl,WhileObjectList iwhil,AllObjectsList aoll)
{
 this.saveRootNode=new saveNodeClass();
 this.saveRootNode=this.saveRootNode.saveNodes(rNode);
 this.conl=iconl.conList;
 this.varl=ivarl.varList;
 this.arrl=iarrl.arrList;
 this.funl=ifunl.funList;
 this.prol=iprol.proList;
 this.readl=ireadl.readList;
 this.writel=iwritel.writeList;
 this.calll=icalll.callList;
 this.retl=iretl.returnList;
 this.assl=iassl.assignList;
 this.dwhl=idwhl.doWhileList;
 this.forl=iforl.forList;
 this.iffl=iiffl.ifList;
 this.whil=iwhil.whileList;
 this.aol=aoll.allObjects;

}

public void loadRootNodeLists(ConstantObjectList iconl,VariableObjectList ivarl,
    ArrayObjectList iarrl,FunctionObjectList ifunl,ProcedureObjectList iprol,ReadObjectList ireadl,
    WriteObjectList iwritel,CallObjectList icalll,ReturnObjectList iretl,AssignObjectList iassl,
    DoWhileObjectList idwhl,ForObjectList iforl,IfObjectList iiffl,WhileObjectList iwhil,AllObjectsList aoll)
{

 iconl.conList.clear();
 iconl.conList=this.conl;
 ivarl.varList.clear();
 ivarl.varList=this.varl;
 iarrl.arrList.clear();
 iarrl.arrList=this.arrl;
 ifunl.funList.clear();
 ifunl.funList=this.funl;
 iprol.proList.clear();
 iprol.proList=this.prol;
 ireadl.readList.clear();
 ireadl.readList=this.readl;
 iwritel.writeList.clear();
 iwritel.writeList=this.writel;
 icalll.callList.clear();
 icalll.callList=this.calll;
 iretl.returnList.clear();
 iretl.returnList=this.retl;
 iassl.assignList.clear();
 iassl.assignList=this.assl;
 idwhl.doWhileList.clear();
 idwhl.doWhileList=this.dwhl;
 iforl.forList.clear();
 iforl.forList=this.forl;
 iiffl.ifList.clear();
 iiffl.ifList=this.iffl;
 iwhil.whileList.clear();
 iwhil.whileList=this.whil;
 aoll.allObjects.clear();
 aoll.allObjects=this.aol;
}


//public void loadRootNodeLists(ConstantObjectList iconl,VariableObjectList ivarl,
//    ArrayObjectList iarrl,FunctionObjectList ifunl,ProcedureObjectList iprol,ReadObjectList ireadl,
//    WriteObjectList iwritel,CallObjectList icalll,ReturnObjectList iretl,AssignObjectList iassl,
//    DoWhileObjectList idwhl,ForObjectList iforl,IfObjectList iiffl,WhileObjectList iwhil,AllObjectsList aoll)
//{
//
// iconl.conList.clear();
// iconl.conList=this.copyConstantObjectList();
// ivarl.varList.clear();
// ivarl.varList=this.copyVariableObjectList();
// iarrl.arrList.clear();
// iarrl.arrList=this.copyArrayObjectList();
// ifunl.funList.clear();
// ifunl.funList=this.copyFunctionObjectList();
// iprol.proList.clear();
// iprol.proList=this.copyProcedureObjectList();
// ireadl.readList.clear();
// ireadl.readList=this.copyReadObjectList();
// iwritel.writeList.clear();
// iwritel.writeList=this.copyWriteObjectList();
// icalll.callList.clear();
// icalll.callList=this.copyCallObjectList();
// iretl.returnList.clear();
// iretl.returnList=this.copyReturnObjectList();
// iassl.assignList.clear();
// iassl.assignList=this.copyAssignObjectList();
// idwhl.doWhileList.clear();
// idwhl.doWhileList=this.copyDoWhileObjectList();
// iforl.forList.clear();
// iforl.forList=this.copyForObjectList();
// iiffl.ifList.clear();
// iiffl.ifList=this.copyIfObjectList();
// iwhil.whileList.clear();
// iwhil.whileList=this.copyWhileObjectList();
// aoll.allObjects.clear();
// aoll.allObjects=this.copyAllObjectsList();
//}


public saveNodeClass getSaveRootNode() {
    return saveRootNode;
}


 public LinkedList <VariableObject> copyVariableObjectList (){
       LinkedList <VariableObject> voList =new LinkedList();
       for (VariableObject vo:this.varl)
           voList.add(new VariableObject(vo));
       return voList;
    }

 public LinkedList <WriteObject> copyWriteObjectList (){
       LinkedList <WriteObject> woList =new LinkedList();
       for (WriteObject wo:this.writel)
           woList.add(new WriteObject(wo));
       return woList;
    }

  public LinkedList <WhileObject> copyWhileObjectList (){
       LinkedList <WhileObject> woList =new LinkedList();
       for (WhileObject wo:this.whil)
           woList.add(new WhileObject(wo));
       return woList;
    }

  public LinkedList <ReturnObject> copyReturnObjectList (){
       LinkedList <ReturnObject> roList =new LinkedList();
       for (ReturnObject ro:this.retl)
           roList.add(new ReturnObject(ro));
       return roList;
    }

   public LinkedList <ReadObject> copyReadObjectList (){
       LinkedList <ReadObject> roList =new LinkedList();
       for (ReadObject ro:this.readl)
           roList.add(new ReadObject(ro));
       return roList;
    }

public LinkedList <ProcedureObject> copyProcedureObjectList (){
       LinkedList <ProcedureObject> poList =new LinkedList();
       for (ProcedureObject po:this.prol)
           poList.add(new ProcedureObject(po));
       return poList;
    }

public LinkedList <IfObject> copyIfObjectList (){
       LinkedList <IfObject> ioList =new LinkedList();
       for (IfObject io:this.iffl)
           ioList.add(new IfObject(io));
       return ioList;
    }


public LinkedList <FunctionObject> copyFunctionObjectList (){
       LinkedList <FunctionObject> foList =new LinkedList();
       for (FunctionObject fo:this.funl)
           foList.add(new FunctionObject(fo));
       return foList;
    }

public LinkedList <ForObject> copyForObjectList (){
       LinkedList <ForObject> foList =new LinkedList();
       for (ForObject fo:this.forl)
           foList.add(new ForObject(fo));
       return foList;
    }

public LinkedList <DoWhileObject> copyDoWhileObjectList (){
       LinkedList <DoWhileObject> dwoList =new LinkedList();
       for (DoWhileObject dwo:this.dwhl)
           dwoList.add(new DoWhileObject(dwo));
       return dwoList;
    }

public LinkedList <CallObject> copyCallObjectList (){
       LinkedList <CallObject> coList =new LinkedList();
       for (CallObject co:this.calll)
           coList.add(new CallObject(co));
       return coList;
    }

public LinkedList <AssignObject> copyAssignObjectList (){
       LinkedList <AssignObject> aoList =new LinkedList();
       for (AssignObject ao:this.assl)
           aoList.add(new AssignObject(ao));
       return aoList;
    }

public LinkedList <ArrayObject> copyArrayObjectList (){
       LinkedList <ArrayObject> aoList =new LinkedList();
       for (ArrayObject ao:this.arrl)
           aoList.add(new ArrayObject(ao));
       return aoList;
    }


  public LinkedList <ConstantObject> copyConstantObjectList (){
       LinkedList <ConstantObject> coList =new LinkedList();
       for (ConstantObject co:this.conl)
           coList.add(new ConstantObject(co));
       return coList;
    }

  public LinkedList < AllObjects> copyAllObjectsList (){
       LinkedList < AllObjects> aoList =new LinkedList();
       for ( AllObjects ao:this.aol)
           aoList.add(new  AllObjects(ao));
       return aoList;
    }

}
