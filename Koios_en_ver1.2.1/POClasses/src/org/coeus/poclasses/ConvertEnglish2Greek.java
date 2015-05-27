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

/**
 *
 * @author Jrd
 */
public class ConvertEnglish2Greek {
    
     private String grIdintifier=null;
    
     
     
    public  ConvertEnglish2Greek (String enIdintifier,int [] GR2ENChanges)
    {
      //int enIdL=enIdintifier.length(),i;
      int enIdL=GR2ENChanges.length,i;
   

      char[] c=enIdintifier.toCharArray();
      for (i=0;i<enIdL;i++)
      {
       switch(c[i])
       {
           case 'A': if(GR2ENChanges[i]==1) c[i]='Α';break;
           case 'B': if(GR2ENChanges[i]==1) c[i]='Β';break;
           case 'G': if(GR2ENChanges[i]==1) c[i]='Γ';break;
           case 'D': if(GR2ENChanges[i]==1) c[i]='Δ';break;
           case 'E': if(GR2ENChanges[i]==1) c[i]='Ε';break;
           case 'Z': if(GR2ENChanges[i]==1) c[i]='Ζ';break;
           case 'H': if(GR2ENChanges[i]==1) c[i]='Η';break;
           case 'U': if(GR2ENChanges[i]==1) c[i]='Θ';break;
           case 'I': if(GR2ENChanges[i]==1) c[i]='Ι';
                     if(GR2ENChanges[i]==2) c[i]='Ϊ';break;
           case 'K': if(GR2ENChanges[i]==1) c[i]='Κ';break;
           case 'L': if(GR2ENChanges[i]==1) c[i]='Λ';break;
           case 'M': if(GR2ENChanges[i]==1) c[i]='Μ';break;
           case 'N': if(GR2ENChanges[i]==1) c[i]='Ν';break;
           case 'J': if(GR2ENChanges[i]==1) c[i]='Ξ';break;
           case 'O': if(GR2ENChanges[i]==1) c[i]='Ο';break;
           case 'P': if(GR2ENChanges[i]==1) c[i]='Π';break;
           case 'R': if(GR2ENChanges[i]==1) c[i]='Ρ';break;
           case 'S': if(GR2ENChanges[i]==1) c[i]='Σ';break;
           case 'T': if(GR2ENChanges[i]==1) c[i]='Τ';break;
           case 'Y': if(GR2ENChanges[i]==1) c[i]='Υ';
                     if(GR2ENChanges[i]==2) c[i]='Ϋ';break;
           case 'F': if(GR2ENChanges[i]==1) c[i]='Φ';break;
           case 'X': if(GR2ENChanges[i]==1) c[i]='Χ';break;
           case 'C': if(GR2ENChanges[i]==1) c[i]='Ψ';break;
           case 'W': if(GR2ENChanges[i]==1) c[i]='Ω';break;
           case 'a': if(GR2ENChanges[i]==1) c[i]='α';
                     if(GR2ENChanges[i]==2) c[i]='ά'; break;           
           case 'b': if(GR2ENChanges[i]==1) c[i]='β';break;
           case 'g': if(GR2ENChanges[i]==1) c[i]='γ';break;
           case 'd': if(GR2ENChanges[i]==1) c[i]='δ';break;
           case 'e': if(GR2ENChanges[i]==1) c[i]='ε';
                     if(GR2ENChanges[i]==2) c[i]='έ';break;
           case 'z': if(GR2ENChanges[i]==1) c[i]='ζ';break;
           case 'h': if(GR2ENChanges[i]==1) c[i]='η';
                     if(GR2ENChanges[i]==2) c[i]='ή';break;
           case 'u': if(GR2ENChanges[i]==1) c[i]='θ';break;
           case 'i': if(GR2ENChanges[i]==1) c[i]='ι';
                     if(GR2ENChanges[i]==2) c[i]='ί';
                     if(GR2ENChanges[i]==3) c[i]='ϊ';
                     if(GR2ENChanges[i]==4) c[i]='ΐ';break;
           case 'k': if(GR2ENChanges[i]==1) c[i]='κ';break;
           case 'l': if(GR2ENChanges[i]==1) c[i]='λ';break;
           case 'm': if(GR2ENChanges[i]==1) c[i]='μ';break;
           case 'n': if(GR2ENChanges[i]==1) c[i]='ν';break;
           case 'j': if(GR2ENChanges[i]==1) c[i]='ξ';break;
           case 'o': if(GR2ENChanges[i]==1) c[i]='ο';
                     if(GR2ENChanges[i]==2) c[i]='ό';break;
           case 'p': if(GR2ENChanges[i]==1) c[i]='π';break;
           case 'r': if(GR2ENChanges[i]==1) c[i]='ρ';break;
           case 's': if(GR2ENChanges[i]==1) c[i]='σ';break;
           case 't': if(GR2ENChanges[i]==1) c[i]='τ';break;
           case 'y': if(GR2ENChanges[i]==1) c[i]='υ';
                     if(GR2ENChanges[i]==2) c[i]='ύ';
                     if(GR2ENChanges[i]==3) c[i]='ϋ';
                     if(GR2ENChanges[i]==4) c[i]='ΰ';break;
           case 'f': if(GR2ENChanges[i]==1) c[i]='φ';break;
           case 'x': if(GR2ENChanges[i]==1) c[i]='χ';break;
           case 'c': if(GR2ENChanges[i]==1) c[i]='ψ';break;
           case 'w': if(GR2ENChanges[i]==1) c[i]='ω';
                     if(GR2ENChanges[i]==2) c[i]='ς';break;
         }
      }
     grIdintifier=String.valueOf(c);
    }

    public String getGreekIdintifier()
    {return this.grIdintifier;}

}
