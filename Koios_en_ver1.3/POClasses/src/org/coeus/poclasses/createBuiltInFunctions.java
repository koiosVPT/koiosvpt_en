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

import java.util.LinkedList;
import org.coeus.wizards.WizardsDefinitions;

/**
 *
 * @author Jrd
 */
public class createBuiltInFunctions {

     private FunctionObject sin=null,cos=null,tan=null,sqrt=null,
            log=null,exp=null,integer=null,abs=null,pow=null;


    public createBuiltInFunctions() {
        //////////Create Ready Functions: sin,cos,tan,sqrt,log,exp,integer,abs,pow
        LinkedList<String> dispNames1 =new LinkedList();
//        dispNames1.add(" ");
        dispNames1.add("1st Parameter");

        LinkedList<String> objNames1 =new LinkedList();
        objNames1.add(" ");

        LinkedList<String> dispNames2 =new LinkedList();
//        dispNames2.add(" ");
//        dispNames2.add(" ");
        dispNames2.add("1st Parameter");
        dispNames2.add("2nd Parameter");

        LinkedList<String> objNames2 =new LinkedList();
        objNames2.add(" ");
        objNames2.add(" ");

        LinkedList<String> dispType1 =new LinkedList();
        dispType1.add("INTEGER or REAL");

        LinkedList<String> dispTypeD =new LinkedList();
        dispTypeD.add("REAL");

        LinkedList<String> objType1 =new LinkedList();
        objType1.add(WizardsDefinitions.DOUBLE);

        LinkedList<String> dispType2 =new LinkedList();
        dispType2.add("INTEGER or REAL");
        dispType2.add("INTEGER or REAL");

        LinkedList<String> objTypes2 = new LinkedList();
        objTypes2.add(WizardsDefinitions.DOUBLE);
        objTypes2.add(WizardsDefinitions.DOUBLE);

//        LinkedList<String> objType2 =new LinkedList();
//        objType2.add(WizardsDefinitions.DOUBLE);
//        objType2.add(WizardsDefinitions.DOUBLE);
//        objType2.add("skata");
        
        LinkedList<String> locked= new LinkedList();

        sin = new FunctionObject("SINE",WizardsDefinitions.FLOAT1,dispNames1,objNames1,dispType1,objType1,
        "Math.sin",WizardsDefinitions.DOUBLE,"ready","ready",locked,new int[]{0},new int[][]{{0}} );

        cos = new FunctionObject("COSINE",WizardsDefinitions.FLOAT1,dispNames1,objNames1,dispType1,objType1,
        "Math.cos",WizardsDefinitions.DOUBLE,"ready","ready",locked,new int[]{0},new int[][]{{0}} );

        tan = new FunctionObject("TANGENT",WizardsDefinitions.FLOAT1,dispNames1,objNames1,dispType1,objType1,
        "Math.tan",WizardsDefinitions.DOUBLE,"ready","ready",locked,new int[]{0},new int[][]{{0}} );

        sqrt = new FunctionObject("SQUARE_ROOT",WizardsDefinitions.FLOAT1,dispNames1,objNames1,dispType1,objType1,
        "Math.sqrt",WizardsDefinitions.DOUBLE,"ready","ready",locked,new int[]{0},new int[][]{{0}} );

        log = new FunctionObject("LOGARITHM",WizardsDefinitions.FLOAT1,dispNames1,objNames1,dispType1,objType1,
        "Math.log",WizardsDefinitions.DOUBLE,"ready","ready",locked,new int[]{0},new int[][]{{0}} );

        exp = new FunctionObject("E_TO_POWER_OF",WizardsDefinitions.FLOAT1,dispNames1,objNames1,dispType1,objType1,
        "Math.exp",WizardsDefinitions.DOUBLE,"ready","ready",locked,new int[]{0},new int[][]{{0}} );

        abs = new FunctionObject("ABSOLUTE_VALUE",WizardsDefinitions.FLOAT1,dispNames1,objNames1,dispType1,objType1,
        "Math.abs",WizardsDefinitions.DOUBLE,"ready","ready",locked,new int[]{0},new int[][]{{0}} );

        pow = new FunctionObject("POWER",WizardsDefinitions.FLOAT1,dispNames2,objNames2,dispType2,objTypes2,
        "Math.pow",WizardsDefinitions.DOUBLE,"ready","ready",locked,new int[]{0},new int[][]{{0}} );

        integer = new FunctionObject("INTEGER_PART",WizardsDefinitions.INT1,dispNames1,objNames1,dispTypeD,objType1,
        "(int)",WizardsDefinitions.INT,"ready","ready",locked,new int[]{0},new int[][]{{0}} );

    }



}
