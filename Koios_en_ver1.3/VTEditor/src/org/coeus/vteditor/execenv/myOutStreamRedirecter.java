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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.swing.text.JTextComponent;



/**
 *
 * Andrew Davison © 2009
 * found in Java Prog. Techniques for Games. Java Art Chapter 3. Tracing
 */


 public class myOutStreamRedirecter extends Thread
{

private final BufferedReader in;
private final JTextComponent out;
private  myJDIEventMonitor watcher=null;
private long speed;
private boolean wait=false;



public myOutStreamRedirecter(String name, InputStream in,
JTextComponent out,myJDIEventMonitor watcher)
{
super(name);
//this.in = new BufferedReader(new InputStreamReader(in)); // stream to copy from
this.in = new BufferedReader(new InputStreamReader(in,Charset.forName("UTF-8"))); // stream to copy from
this.out = out; // TextComponent to copy to
setPriority(Thread.MAX_PRIORITY - 1);
this.watcher=watcher;
} // end of StreamRedirecter()

    @Override
public void run()
// copy BUFFER_SIZE chars at a time
{
try {
String line=null;

while ((line=in.readLine()) != null)
{
    this.watcher.setReadyToWrite(true);
pauseTrace();

out.setText(out.getText()+line+"\n");
     try {this.sleep(speed/2);}
     catch (InterruptedException ex) {}

if (!wait)
resumeTrace();
this.watcher.setReadyToWrite(false);
}
 
}

catch (IOException e)
{ System.err.println("StreamRedirecter: " + e); }
} // end of run()


  public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getSpeed() {
        return speed;
    }


    public void resumeTrace()
{
        synchronized (watcher) {
            watcher.setWait(false);
            watcher.notify();
        }
}



public void pauseTrace()
{
            synchronized (watcher) {
            watcher.setWait(true);
        }
}


 public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }


 } // end of StreamRedirecter class