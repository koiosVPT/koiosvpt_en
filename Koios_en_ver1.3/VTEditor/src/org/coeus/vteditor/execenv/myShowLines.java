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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * Andrew Davison © 2009
 * found in Java Prog. Techniques for Games. Java Art Chapter 3. Tracing
 */
public class myShowLines {


    // global in ShowLines class
private ArrayList<String> code;
public myShowLines(String fileName)
{
code = new ArrayList<String>();
String line = null;
BufferedReader in = null;
try {
//in = new BufferedReader(new FileReader(fileName));
in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
while ((line = in.readLine()) != null)
code.add(line);
}
catch (IOException ex) {
executionDialog.setComment("Error in opening file: " + fileName);
}
finally {
try {
if (in != null)
in.close();
}
catch (IOException e) {}
}
} // end of showLines()




public String show(int lineNum)
{
if (code == null)
return "No code to show";
if ((lineNum < 1) || (lineNum > code.size()))
return "Line no. out of range";
//return ( "" + lineNum + ".\t" + code.get(lineNum-1)); // use num-1
///////////////original mexri edo////////
///////my code/////////////////////
//if(! code.get(lineNum-1).startsWith("class"))
return ( code.get(lineNum-1)); // use num-1
//else return "";
/////////////////my code ends/////

} // end of show()


}
