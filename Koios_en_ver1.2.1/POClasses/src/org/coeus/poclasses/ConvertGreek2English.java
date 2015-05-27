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

package org.coeus.poclasses;

import org.coeus.wizards.AllObjects;
import org.coeus.wizards.AllObjectsList;

/**
 *
 * @author Jrd
 */
public class ConvertGreek2English {
    
    private String enIdintifier=null;
    private int [] GR2ENChanges;

    public  ConvertGreek2English(String grIdintifier)
    {
      int grIdL=grIdintifier.length(),i;
     
      GR2ENChanges= new int [grIdL];

      char[] c=grIdintifier.toCharArray();
      for (i=0;i<grIdL;i++)
      {
       switch(c[i])
       {
           case 'Α': c[i]='A'; GR2ENChanges[i]=1;break;
           case 'Β': c[i]='B'; GR2ENChanges[i]=1;break;
           case 'Γ': c[i]='G'; GR2ENChanges[i]=1;break;
           case 'Δ': c[i]='D'; GR2ENChanges[i]=1;break;
           case 'Ε': c[i]='E'; GR2ENChanges[i]=1;break;
           case 'Ζ': c[i]='Z'; GR2ENChanges[i]=1;break;
           case 'Η': c[i]='H'; GR2ENChanges[i]=1;break;
           case 'Θ': c[i]='U'; GR2ENChanges[i]=1;break;
           case 'Ι': c[i]='I'; GR2ENChanges[i]=1;break;
           case 'Κ': c[i]='K'; GR2ENChanges[i]=1;break;
           case 'Λ': c[i]='L'; GR2ENChanges[i]=1;break;
           case 'Μ': c[i]='M'; GR2ENChanges[i]=1;break;
           case 'Ν': c[i]='N'; GR2ENChanges[i]=1;break;
           case 'Ξ': c[i]='J'; GR2ENChanges[i]=1;break;
           case 'Ο': c[i]='O'; GR2ENChanges[i]=1;break;
           case 'Π': c[i]='P'; GR2ENChanges[i]=1;break;
           case 'Ρ': c[i]='R'; GR2ENChanges[i]=1;break;
           case 'Σ': c[i]='S'; GR2ENChanges[i]=1;break;
           case 'Τ': c[i]='T'; GR2ENChanges[i]=1;break;
           case 'Υ': c[i]='Y'; GR2ENChanges[i]=1;break;
           case 'Φ': c[i]='F'; GR2ENChanges[i]=1;break;
           case 'Χ': c[i]='X'; GR2ENChanges[i]=1;break;
           case 'Ψ': c[i]='C'; GR2ENChanges[i]=1;break;
           case 'Ω': c[i]='W'; GR2ENChanges[i]=1;break;
           case 'Ϊ': c[i]='I'; GR2ENChanges[i]=2;break;
           case 'Ϋ': c[i]='Y'; GR2ENChanges[i]=2;break;
           case 'α': c[i]='a'; GR2ENChanges[i]=1;break;
           case 'β': c[i]='b'; GR2ENChanges[i]=1;break;
           case 'γ': c[i]='g'; GR2ENChanges[i]=1;break;
           case 'δ': c[i]='d'; GR2ENChanges[i]=1;break;
           case 'ε': c[i]='e'; GR2ENChanges[i]=1;break;
           case 'ζ': c[i]='z'; GR2ENChanges[i]=1;break;
           case 'η': c[i]='h'; GR2ENChanges[i]=1;break;
           case 'θ': c[i]='u'; GR2ENChanges[i]=1;break;
           case 'ι': c[i]='i'; GR2ENChanges[i]=1;break;
           case 'κ': c[i]='k'; GR2ENChanges[i]=1;break;
           case 'λ': c[i]='l'; GR2ENChanges[i]=1;break;
           case 'μ': c[i]='m'; GR2ENChanges[i]=1;break;
           case 'ν': c[i]='n'; GR2ENChanges[i]=1;break;
           case 'ξ': c[i]='j'; GR2ENChanges[i]=1;break;
           case 'ο': c[i]='o'; GR2ENChanges[i]=1;break;
           case 'π': c[i]='p'; GR2ENChanges[i]=1;break;
           case 'ρ': c[i]='r'; GR2ENChanges[i]=1;break;
           case 'σ': c[i]='s'; GR2ENChanges[i]=1;break;
           case 'τ': c[i]='t'; GR2ENChanges[i]=1;break;
           case 'υ': c[i]='y'; GR2ENChanges[i]=1;break;
           case 'φ': c[i]='f'; GR2ENChanges[i]=1;break;
           case 'χ': c[i]='x'; GR2ENChanges[i]=1;break;
           case 'ψ': c[i]='c'; GR2ENChanges[i]=1;break;
           case 'ω': c[i]='w'; GR2ENChanges[i]=1;break;
           case 'ά': c[i]='a'; GR2ENChanges[i]=2;break;
           case 'έ': c[i]='e'; GR2ENChanges[i]=2;break;
           case 'ό': c[i]='o'; GR2ENChanges[i]=2;break;
           case 'ί': c[i]='i'; GR2ENChanges[i]=2;break;
           case 'ή': c[i]='h'; GR2ENChanges[i]=2;break;
           case 'ύ': c[i]='y'; GR2ENChanges[i]=2;break;
           case 'ς': c[i]='w'; GR2ENChanges[i]=2;break;
           case 'ϊ': c[i]='i'; GR2ENChanges[i]=3;break;
           case 'ϋ': c[i]='y'; GR2ENChanges[i]=3;break;
           case 'ΐ': c[i]='i'; GR2ENChanges[i]=4;break;
           case 'ΰ': c[i]='y'; GR2ENChanges[i]=4;break;
           default:GR2ENChanges[i]=0;break;
       }
      }
      
      String temp=String.valueOf(c);
      enIdintifier=temp;
      //enIdintifier=checkIfExists(temp);
    }

    private String checkIfExists(String temp)
    {   int index=0;
        AllObjectsList aol = AllObjectsList.getAllObjList();
        AllObjects found =aol.SearchByObjectName(temp);
        while (found!=null)
        {
        temp=temp+"_"+Integer.toString(index);
        index++;
        found =aol.SearchByObjectName(temp);
        }
        return temp;
      }
    
    
    public String getEnglishIdintifier()
    //{return this.enIdintifier;}
    {return this.enIdintifier.toUpperCase();}

    public int [] getGreek2EnglishChanges()
    {return this.GR2ENChanges;}

}
