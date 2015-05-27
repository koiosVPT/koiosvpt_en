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


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 *
 * Andrew Davison © 2009
 * found in Java Prog. Techniques for Games. Java Art Chapter 3. Tracing
 */


 public class StreamRedirecter extends Thread
{
private static final int BUFFER_SIZE = 2048;
private final Reader in;
private final Writer out;


public StreamRedirecter(String name, InputStream in,
OutputStream out)
{
super(name);
//this.in = new InputStreamReader(in); // stream to copy from
//this.out = new OutputStreamWriter(out); // stream to copy to
this.in = new InputStreamReader(in,Charset.forName("UTF-8")); // stream to copy from
this.out = new OutputStreamWriter(out,Charset.forName("UTF-8")); // stream to copy to
setPriority(Thread.MAX_PRIORITY - 1);
} // end of StreamRedirecter()

    @Override
public void run()
// copy BUFFER_SIZE chars at a time
{
try {
char[] cbuf = new char[BUFFER_SIZE];
int count;
while ((count = in.read(cbuf, 0, BUFFER_SIZE)) >= 0)
out.write(cbuf, 0, count);
out.flush();
}
catch (IOException e)
{ System.err.println("StreamRedirecter: " + e); }
} // end of run()

} // end of StreamRedirecter class


