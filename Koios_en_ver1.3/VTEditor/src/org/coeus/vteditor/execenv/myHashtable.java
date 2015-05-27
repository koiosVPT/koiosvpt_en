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



package org.coeus.vteditor.execenv;

import java.util.LinkedList;

/**
 *
 * @author Jrd
 */
public class myHashtable<K,V>  {

//must be a string. otherwise cahnge public V get ()
     public class myHashtableObject <K,V>
      { public K k;
        public V v;
        public int i;

       public myHashtableObject (K k, V v)
       {
        this.k=k;
        this.v=v;
        this.i=index;
        index++;
           
       }
     }

    private LinkedList<myHashtableObject> keysValuesList=null;
    public int index=0;

    
 public myHashtable() {
         keysValuesList = new LinkedList();
    }
   

    public LinkedList <myHashtableObject> getMyObjectList()
    {
      return keysValuesList;
    }

    public void clear() {
       this.keysValuesList.clear();
    }

    public int  size() {
       return this.keysValuesList.size();
    }

    public void put (K k, V v )
    {
        myHashtableObject mo= new myHashtableObject(k,v);
        keysValuesList.add(mo);
    }


   public LinkedList<Integer> keySet()
   {
     LinkedList<Integer> il = new LinkedList();
     for (myHashtableObject mo:this.keysValuesList)
       il.add(mo.i);
     return il;

   }

   public V getV(int i)
   {
    myHashtableObject mo=null;
    mo=this.keysValuesList.get(i);
    return (V) mo.v;
   }

   public K getK(int i)
   {
    myHashtableObject mo=null;
    mo=this.keysValuesList.get(i);
    return (K) mo.k;
   }

   
    public V get(K k)
   {
    V v=null;
    for (myHashtableObject mo:this.keysValuesList)
    {
      if (mo.k.equals(k))
      {v=(V) mo.v;}
    }
    return v;
   }
   
}



