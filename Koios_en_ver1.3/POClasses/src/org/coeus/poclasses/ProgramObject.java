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

/**
 *
 * @author Jrd
 */
public class ProgramObject {
    
   private String displayName;
   private String objectName;
   

  public ProgramObject(String dName,String oName)
  {
  this.displayName=dName;
  this.objectName=oName;
  }


  public ProgramObject(ProgramObject po)
  {
  this.displayName=po.displayName;
  this.objectName=po.objectName;
  }
    
 public void setDisplayName (String ndname)
    {this.displayName=ndname;} 
 
 public void setObjectName (String noname)
    {this.objectName=noname;}

 public String getDispName ()
    {return displayName;}

     public String getObjName ()
    {return objectName;}

     public LinkedList<PrintStructure> getCoeusString1()
     { LinkedList<PrintStructure> part = new LinkedList();

       part.add(new PrintStructure(CoeusProgram.PROGRAM1+" ",PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       part.add(new PrintStructure(this.displayName+"\n\n",PrintStructure.IDENTIFIER_FONT,PrintStructure.IDENTFIER_COLOR));

       return part;
     }

     public LinkedList<PrintStructure> getCoeusString2()
     { LinkedList<PrintStructure> end = new LinkedList<PrintStructure> ();
       end.add(new PrintStructure(CoeusProgram.PROGRAM2,PrintStructure.RESERVED_WORD_FONT,PrintStructure.RESERVED_WORD_COLOR));
       return end;
     }


     public String getJavaString1(String className)
     {
          if (className==null)
           className=this.objectName;

         String s= //"package "+this.objectName+" ;\n\n"+
              "import java.*;\nimport javax.swing.JOptionPane;" +
              "\nimport javax.swing.JFrame;\n\n\n"+
             
              "public class "+className+" {\n\nString temp=null;\n\n"//+
//
//             "\n private String showInputDialog(String message,String title)"+
//"\n{"+
//"\n  String result= \" \";"+
//"\n  NotifyDescriptor.InputLine d =new NotifyDescriptor.InputLine(message, title,"+
//"\n         NotifyDescriptor.DEFAULT_OPTION,  NotifyDescriptor.QUESTION_MESSAGE);"+
//"\n  if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION)"+
//"\n      result=d.getInputText();"+
//"\n   return result;"+
//"\n}\n\n\n\n"
              ;
     return s;
     }


      public String getJavaString2(String className)
     {
          if (className==null)
           className=this.objectName;

     String s= "\n\n       public static void main(String [] args) {\n\n"+
//                 "                  new "+className+"();\n"+
                 "                  new "+className+"().mymain();\n"+
                 "          }\n\n"+
                 "}\n\n";
     
     s=s+"class myJOP extends JOptionPane{public  static myJOP myGreekJOP(){"+
 "\n return new myJOP();}\n"+
"public myJOP(){super();}\n"+
"             public String myshowInputDialog(String message, String title, int messageType)\n"+
"{JFrame frame = new JFrame();frame.setUndecorated(true);frame.setLocationRelativeTo(null);frame.setAlwaysOnTop(true);\n"+
"frame.setVisible(true);String answer =JOptionPane.showInputDialog(frame, message, title, messageType);\n"+
"frame.dispose();return answer;};}";

     return s;
     }

     public String getDescription()
 {
 String des="This command marks the beginning of program "+this.displayName+".";

 return des;
 }

      public String getDescription2()
 {
 String des2="This command marks the end of program "+this.displayName+".";
       

 return des2;
 }

}


   

    
   