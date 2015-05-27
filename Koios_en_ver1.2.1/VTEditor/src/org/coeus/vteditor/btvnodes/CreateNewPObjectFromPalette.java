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

import org.coeus.btvpalette.ProgComp;
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
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Jrd
 */
public class CreateNewPObjectFromPalette {

    public static Lookup CreateNewPObject(ProgComp pc,String dScope, String oScope)
    { Lookup retLookup=null;

      if (pc.getType().equals("con"))
      {ConstantObject ncono = new ConstantObject(dScope,oScope);
       if (ncono.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{ncono});}
       else
       {retLookup=null;}
      }
      else if (pc.getType().equals("var"))
      {VariableObject nvaro = new VariableObject(dScope,oScope);
       if (nvaro.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{nvaro});}
       else
       {retLookup=null;}
      }
      else if (pc.getType().equals("arr"))
      {ArrayObject narro = new ArrayObject(dScope,oScope);
       if (narro.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{narro});}
       else
       {retLookup=null;}
      }
     else if (pc.getType().equals("fun"))
      {FunctionObject nfuno = new FunctionObject();
       if (nfuno.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{nfuno});}
       else
       {retLookup=null;}
      }
     else if (pc.getType().equals("pro"))
      {ProcedureObject nproo = new ProcedureObject();
       if (nproo.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{nproo});}
       else
       {retLookup=null;}
      }
      else if (pc.getType().equals("rea"))
      {ReadObject nreado = new ReadObject(dScope,oScope);
       if (nreado.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{nreado});}
       else
       {retLookup=null;}
      }
     else if (pc.getType().equals("wri"))
      {WriteObject nwriteo = new WriteObject(dScope,oScope);
       if (nwriteo.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{nwriteo});}
       else
       {retLookup=null;}
      }
       else if (pc.getType().equals("cal"))
      {CallObject ncallo = new CallObject(dScope,oScope);
       if (ncallo.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{ncallo});}
       else
       {retLookup=null;}
      }
       else if (pc.getType().equals("ret"))
      {ReturnObject nreto = new ReturnObject(dScope,oScope);
       if (nreto.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{nreto});}
       else
       {retLookup=null;}
      }
     else if (pc.getType().equals("ass"))
      {AssignObject nasso = new AssignObject(dScope,oScope);
       if (nasso.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{nasso});}
       else
       {retLookup=null;}
      }
     else if (pc.getType().equals("dwh"))
      {DoWhileObject dwho = new DoWhileObject(dScope,oScope);
       if (dwho.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{dwho});}
       else
       {retLookup=null;}
      }
     else if (pc.getType().equals("iff"))
      {IfObject iffo = new IfObject(dScope,oScope);
       if (iffo.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{iffo});}
       else
       {retLookup=null;}
      }
     else if (pc.getType().equals("for"))
      {ForObject foro = new ForObject(dScope,oScope);
       if (foro.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{foro});}
       else
       {retLookup=null;}
      }
     else if (pc.getType().equals("whi"))
      {WhileObject whio = new WhileObject(dScope,oScope);
       if (whio.getCreated()==true)
       {retLookup=Lookups.fixed(new Object[]{whio});}
       else
       {retLookup=null;}
      }

     return retLookup;
    }


//     public static Lookup CreateNewPObject(String type)
//    { Lookup retLookup =null;
//
//      if (type.equals("con"))
//      {ConstantObject ncono = new ConstantObject();
//       retLookup=Lookups.fixed(new Object[]{ncono});
//      }
//      else if (type.equals("var"))
//      {VariableObject nvaro = new VariableObject();
//       retLookup=Lookups.fixed(new Object[]{nvaro});
//      }
//    return retLookup;
//    }



}
