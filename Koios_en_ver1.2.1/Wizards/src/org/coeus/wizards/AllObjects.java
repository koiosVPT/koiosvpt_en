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

package org.coeus.wizards;

import java.util.LinkedList;

/**
 *
 * @author Jrd
 */
public class AllObjects {

       private String dispCateg;
       private String dispName;
       private String dispType;
       private String objName;
       private String objType;
       private String objValue;
       private String objScope;
       private String dispScope;
       private String arrayDim1;
       private String arrayDim2;
       private LinkedList<String> dispParameters=new LinkedList<String>();
       private LinkedList<String> objParameters=new LinkedList<String>();
       private LinkedList<String> dispParametersTypes=new LinkedList<String>();
       private LinkedList<String> objParametersTypes=new LinkedList<String>();

public AllObjects (String idispCateg, String idispName,String idispType,String iobjName,
       String iobjType, String iobjValue,String iobjScope,String idispScope,LinkedList<String> iDispParams,
       LinkedList<String> iObjParams,LinkedList<String> iDispParamsTypes,LinkedList<String> iObjParamsTypes,
       String iArrayDim1,String iArrayDim2)
{
 this.dispCateg=idispCateg;
 this.dispName=idispName;
 this.dispType=idispType;
 this.objName=iobjName;
 this.objType=iobjType;
 this.objValue=iobjValue;
 this.objScope=iobjScope;
 this.dispScope=idispScope;
 this.arrayDim1=iArrayDim1;
 this.arrayDim2=iArrayDim2;
 this.dispParameters=iDispParams;
 this.objParameters= iObjParams;
 this.dispParametersTypes=iDispParamsTypes;
 this.objParametersTypes= iObjParamsTypes;
 }


public AllObjects (AllObjects ao)
{
 this.dispCateg=ao.dispCateg;
 this.dispName=ao.dispName;
 this.dispType=ao.dispType;
 this.objName=ao.objName;
 this.objType=ao.objType;
 this.objValue=ao.objValue;
 this.objScope=ao.objScope;
 this.dispScope=ao.dispScope;
 this.arrayDim1=ao.arrayDim1;
 this.arrayDim2=ao.arrayDim2;
 if (ao.dispParameters!=null)
    this.dispParameters.addAll(ao.dispParameters);
 if (ao.objParameters!=null)
     this.objParameters.addAll(ao.objParameters);
 if (ao.dispParametersTypes!=null)
     this.dispParametersTypes.addAll(ao.dispParametersTypes);
if (ao.objParametersTypes!=null)
    this.objParametersTypes.addAll(ao.objParametersTypes);
 }


///Setters

public void setDispCateg (String iDispCateg)
{this.dispCateg=iDispCateg;}

public void setDispName (String iDispName)
{this.dispName=iDispName;}

public void setDispType (String iDispType)
{this.dispType=iDispType;}

public void setObjName (String iObjName)
{this.objName=iObjName;}

public void setObjType (String iObjType)
{this.objType=iObjType;}

public void setObjValue (String iObjValue)
{this.objValue=iObjValue;}

public void setObjScope (String iObjScope)
{this.objScope=iObjScope;}

public void setDispScope (String iDispScope)
{ this.dispScope=iDispScope;}

public void setArrayDim1 (String iArrayDim1)
{ this.arrayDim1=iArrayDim1;}

public void setArrayDim2 (String iArrayDim2)
{ this.arrayDim2=iArrayDim2;}

public void setDispParams(LinkedList<String> idispParams)
{this.dispParameters=idispParams;}

public void setObjParams(LinkedList<String> iobjParams)
{this.objParameters=iobjParams;}

public void setDispParamsTypes(LinkedList<String> idispParamsTypes)
{this.dispParametersTypes=idispParamsTypes;}

public void setObjParamsTypes(LinkedList<String> iobjParamsTypes)
{this.objParametersTypes=iobjParamsTypes;}

//Getters

public String getDispCateg ()
{return this.dispCateg;}

public String getDispName ()
{return this.dispName;}

public String getDispType ()
{return this.dispType;}

public String getObjName ()
{return this.objName;}

public String getObjType ()
{return this.objType;}

public String getObjValue ()
{return this.objValue;}

public String getObjScope ()
{return this.objScope;}

public String getDispScope ()
{return this.dispScope;}

public String getArrayDim1 ()
{return this.arrayDim1;}

public String getArrayDim2 ()
{return this.arrayDim2;}

public LinkedList<String> getDispParams()
{return this.dispParameters;}

public LinkedList<String> getObjParams()
{return this.objParameters;}

public LinkedList<String> getDispParamsTypes()
{return this.dispParametersTypes;}

public LinkedList<String> getObjParamsTypes()
{return this.objParametersTypes;}
}
