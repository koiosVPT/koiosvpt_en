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

import java.util.LinkedList;
import org.coeus.parsers.myScanner;
import org.coeus.parsers.tokens;
import org.coeus.poclasses.AssignObject;
import org.coeus.poclasses.CallObject;
import org.coeus.poclasses.DoWhileObject;
import org.coeus.poclasses.ForObject;
import org.coeus.poclasses.IfObject;
import org.coeus.poclasses.ReadObject;
import org.coeus.poclasses.ReturnObject;
import org.coeus.poclasses.WhileObject;
import org.coeus.poclasses.WriteObject;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.util.Lookup;

/**
 *
 * @author Jrd
 */
public class getArguementsFromLookup {



    private String displayName=null;
    private String displayNameArgs=null;
    private String dispCategory=null;
    private LinkedList<String> args=new LinkedList();
    private LinkedList<String> allOperants=new LinkedList();
    private LinkedList<String> identifiers=new LinkedList();

    public getArguementsFromLookup(Lookup lookup)
    {
        ReadObject reado = lookup.lookup(ReadObject.class);
        WriteObject writeo = lookup.lookup(WriteObject.class);
        CallObject callo = lookup.lookup(CallObject.class);
        ReturnObject reto = lookup.lookup(ReturnObject.class);
        AssignObject asso = lookup.lookup(AssignObject.class);
        DoWhileObject dwho = lookup.lookup(DoWhileObject.class);
        ForObject foro = lookup.lookup(ForObject.class);
        IfObject iffo = lookup.lookup(IfObject.class);
        WhileObject whio = lookup.lookup(WhileObject.class);


        if (reado != null)
           getArguementsFromCommand(reado);
        else if (writeo != null)
           getArguementsFromCommand(writeo);
        else if (callo != null)
           getArguementsFromCommand(callo);
        else if (reto != null)
           getArguementsFromCommand(reto);
        else if (dwho != null)
           getArguementsFromCommand(dwho);
        else if (foro != null)
           getArguementsFromCommand(foro);
        else if (iffo != null)
           getArguementsFromCommand(iffo);
        else if (asso != null)
           getArguementsFromCommand(asso);
        else if (whio != null)
           getArguementsFromCommand(whio);

    }

    public LinkedList<String> getArgs() {
        return args;
    }

    public String getDispCategory() {
        return dispCategory;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayNameArgs() {
        return displayNameArgs;
    }

    public LinkedList<String> getIdentifiers() {
        return identifiers;
    }

    public LinkedList<String> getAllOperants() {
        return allOperants;
    }


    public getArguementsFromLookup getResult()
    {return this;}

    private void getArguementsFromCommand(AssignObject ao)
    {
     args.clear();
     this.displayName=ao.getDispName();
     int commadnLength=WizardsDefinitions.ASSIGN.length();
     this.displayNameArgs=ao.getDispName().substring(commadnLength).trim();
     this.dispCategory=ao.getDispCateg();//WizardsDefinitions.COM_ASSIGN
     args.add(ao.getDispAssignName());
     //args.add(ao.getDispAssignValue());
    }

    private void getArguementsFromCommand(CallObject co)
    {
     args.clear();
     this.displayName=co.getDispName();
     int commadnLength=WizardsDefinitions.CALL.length();
     this.displayNameArgs=co.getDispName().substring(commadnLength).trim();
     this.dispCategory=co.getDispCateg();//WizardsDefinitions.COM_ASSIGN
     args.addAll(co.getDispActualParameters());
    }

 private void getArguementsFromCommand(DoWhileObject dwo)
    {
     args.clear();
     this.displayName=dwo.getDispName();
     int commadnLength=WizardsDefinitions.DOWHILE.length();
     this.displayNameArgs=dwo.getDispName().substring(commadnLength).trim();
     this.dispCategory=dwo.getDispCateg();//WizardsDefinitions.COM_ASSIGN
     args.add(dwo.getDispDoWhileValue());
    }

 private void getArguementsFromCommand(ForObject fo)
    {
     args.clear();
     this.displayName=fo.getDispName();
     int commadnLength=WizardsDefinitions.FOR.length();
     this.displayNameArgs=fo.getDispName().substring(commadnLength).trim();
     this.dispCategory=fo.getDispCateg();//WizardsDefinitions.COM_ASSIGN
     args.add(fo.getForVariableDispName());
     args.addAll(fo.getDispStatement());
   }

  private void getArguementsFromCommand(ReadObject ro)
    {
     args.clear();
     this.displayName=ro.getDispName();
     int commadnLength=WizardsDefinitions.READ.length();
     this.displayNameArgs=ro.getDispName().substring(commadnLength).trim();
     this.dispCategory=ro.getDispCateg();//WizardsDefinitions.COM_ASSIGN
     args.addAll(ro.getDispArguments());
     }

   private void getArguementsFromCommand(ReturnObject ro)
    {
     args.clear();
     this.displayName=ro.getDispName();
     int commadnLength=WizardsDefinitions.RETURN.length();
     this.displayNameArgs=ro.getDispName().substring(commadnLength).trim();
     this.dispCategory=ro.getDispCateg();//WizardsDefinitions.COM_ASSIGN
     args.add(ro.getDispReturnValue());
    }


    private void  getArguementsFromCommand(WhileObject wo)
    {
     args.clear();
     this.displayName=wo.getDispName();
     int commadnLength=WizardsDefinitions.WHILE.length();
     this.displayNameArgs=wo.getDispName().substring(commadnLength).trim();
     this.dispCategory=wo.getDispCateg();//WizardsDefinitions.COM_ASSIGN
     args.add(wo.getDispWhileValue());
    }

     private void getArguementsFromCommand(WriteObject wo)
    {
     args.clear();
     this.displayName=wo.getDispName();
     int commadnLength=WizardsDefinitions.WRITE.length();
     this.displayNameArgs=wo.getDispName().substring(commadnLength).trim();
     this.dispCategory=wo.getDispCateg();//WizardsDefinitions.COM_ASSIGN
     args.addAll(wo.getDispArguments());
    }

    private void getArguementsFromCommand(IfObject io)
    {
     args.clear();
     this.displayName=io.getDispName();
     int commadnLength=WizardsDefinitions.IF.length();
     this.displayNameArgs=io.getDispName().substring(commadnLength).trim();
     this.dispCategory=io.getDispCateg();//WizardsDefinitions.COM_ASSIGN
     args.addAll(io.getDispStatements());
    }


    public boolean containsName (String CVA_Name)
    {
    for (String s:this.args)
    {
    if(existsIdentifierInString(s,CVA_Name))
       return true;
    }
  return false;
    }


  public boolean existsIdentifierInString(String inString,String identifier)
  {
   
    myScanner scanner =new myScanner(inString,true);
     do
         scanner.getToken();
     while(scanner.token!=tokens.at_sign);
    this.identifiers.clear();
    this.allOperants.clear();
    this.identifiers.addAll(scanner.getIdentifiersList());
    this.allOperants.addAll(scanner.getAllOpersList());
    
    for (String s:scanner.getAllOpersList())
      if (s.equalsIgnoreCase(identifier))
          return true;


  return false;
  }

}
