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


package org.coeus.parsers;

import java.util.LinkedList;

/**
 *
 * @author Jrd
 */
public class printLists {

    public printLists(LinkedList<String> messages, LinkedList<String> dispNames, LinkedList<String> objNames, LinkedList<String> dispTypes, LinkedList<String> objTypes, LinkedList<String> dispCategory) {
    
        System.out.println("\n\n");
        int i=0;

       for (String s:messages)
       {
           System.out.println(s+"\n");
           System.out.println(dispNames.get(i)+"\n");
           System.out.println(objNames.get(i)+"\n");
           System.out.println(dispTypes.get(i)+"\n");
           System.out.println(objTypes.get(i)+"\n");
           System.out.println(dispCategory.get(i)+"\n");
           i++;
       }
        System.out.println("\n\n");
   }

    public printLists(LinkedList<String> messages, LinkedList<String> dispNames, LinkedList<String> objNames, LinkedList<String> dispTypes, LinkedList<String> objTypes) {

        System.out.println("\n\n");
        int i=0;

       for (String s:messages)
       {
           System.out.println(s+"\n");
           System.out.println(dispNames.get(i)+"\n");
           System.out.println(objNames.get(i)+"\n");
           System.out.println(dispTypes.get(i)+"\n");
           System.out.println(objTypes.get(i)+"\n");
           i++;
       }
        System.out.println("\n\n");
   }


    public printLists(LinkedList<String> messages, LinkedList<String> dispNames, LinkedList<String> objNames, LinkedList<String> dispTypes) {

        System.out.println("\n\n");
        int i=0;

       for (String s:messages)
       {
           System.out.println(s+"\n");
           System.out.println(dispNames.get(i)+"\n");
           System.out.println(objNames.get(i)+"\n");
           System.out.println(dispTypes.get(i)+"\n");
           i++;
       }
        System.out.println("\n\n");
   }



   public printLists(LinkedList<String> messages, LinkedList<String> dispNames, LinkedList<String> objNames) {

        System.out.println("\n\n");
        int i=0;

       for (String s:messages)
       {
           System.out.println(s+"\n");
           System.out.println(dispNames.get(i)+"\n");
           System.out.println(objNames.get(i)+"\n");
           i++;
       }
        System.out.println("\n\n");
   }

 public printLists(LinkedList<String> messages, LinkedList<String> dispNames) {

        System.out.println("\n\n");
        int i=0;

       for (String s:messages)
       {
           System.out.println(s+"\n");
           System.out.println(dispNames.get(i)+"\n");
           i++;
       }
        System.out.println("\n\n");
   }

 public printLists(LinkedList<String> messages) {

        System.out.println("\n\n");
        int i=0;

       for (String s:messages)
       {
           System.out.println(s+"\n");
           i++;
       }
        System.out.println("\n\n");
   }
}

