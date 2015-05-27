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



/*
 * VTEditor.java
 *
 * Created on 5 Μαρ 2009, 2:55:24 πμ
 */
package org.coeus.vteditor;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import org.coeus.poclasses.createBuiltInFunctions;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Collection;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultEditorKit;


import org.coeus.vteditor.btvnodes.CreateMain;

import org.coeus.vteditor.actions.myDeleteAction;
import org.coeus.vteditor.btvnodes.PCNodeLeaf;
import org.coeus.vteditor.btvnodes.PCNodeParent;
import org.coeus.vteditor.btvnodes.RootNode;
import org.coeus.vteditor.actions.getUndoInstance;
import org.coeus.vteditor.actions.undoRedoClassList;
import org.coeus.btvpalette.BTVPalTopComponent;

import org.coeus.btvpalette.myAdvanceAction;
import org.coeus.poclasses.ArrayObjectList;
import org.coeus.poclasses.AssignObjectList;
import org.coeus.poclasses.CallObjectList;
import org.coeus.poclasses.CoeusProgram;
import org.coeus.poclasses.ConstantObjectList;
import org.coeus.poclasses.ConvertGreek2English;
import org.coeus.poclasses.DoWhileObjectList;
import org.coeus.poclasses.ForObjectList;
import org.coeus.poclasses.FunctionObjectList;
import org.coeus.poclasses.IfObjectList;
import org.coeus.poclasses.ProcedureObjectList;
import org.coeus.poclasses.ProgramObject;
import org.coeus.poclasses.ReadObjectList;
import org.coeus.poclasses.ReturnObjectList;
import org.coeus.poclasses.VariableObjectList;
import org.coeus.poclasses.WhileObjectList;
import org.coeus.poclasses.WriteObjectList;
import org.coeus.vteditor.actions.myMoveDownAction;
import org.coeus.vteditor.actions.myMoveUpAction;
import org.coeus.vteditor.actions.mySaveAction;
import org.coeus.vteditor.actions.myShowLineNumbersAction;
import org.coeus.wizards.AllObjectsList;
import org.coeus.wizards.TextActions.AddPopupMenuOnlyCopySelectAll;
import org.coeus.wizards.WizardsDefinitions;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.actions.CutAction;
import org.openide.actions.PasteAction;
import org.openide.awt.StatusDisplayer;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

/**
 *
 * @author Jrd
 */
public class VTEditor extends TopComponent implements ExplorerManager.Provider,LookupListener {

    private static int count = 1;
    private final int index;
    private transient ExplorerManager explorerManager = new ExplorerManager();
    private static boolean TextViewSelected;
    private boolean TextViewSelectedInstance;
    private Lookup.Result result=null,resultPCParentNodes = null,resultPCLeafNodes=null,resultRootNode=null;
    private RootNode PRoot=null;
    private MyBeanTreeView mbtv=null;
    private static final String NEW_PROGRAM_NAME_BASE="ΝEW_PROGRAM_";
    private static String NEW_PROGRAM_NAME;
    private JTextPane TPane=null;

 
   private boolean MoveUpEnabled=false,MoveDownEnabled=false,
           CutEnabled=false,DeleteEnabled=false,PasteEnabled=false,CutPressed=false;
   private AbstractNode an=null;
   private String displayName=null,displayCategory=null;

    public AllObjectsList allol;
    private ConstantObjectList conl;
    private VariableObjectList varl;
    private ArrayObjectList arrl;
    private FunctionObjectList funl;
    private ProcedureObjectList prol;
    private ReadObjectList readl;
    private WriteObjectList writel;
    private CallObjectList calll;
    private ReturnObjectList retl;
    private AssignObjectList assl;
    private DoWhileObjectList dwhl;
    private ForObjectList forl;
    private IfObjectList iffl;
    private WhileObjectList whil;

    private undoRedoClassList undoRedoList=null;
    private modificationMadeClass modificationMade=null;
    private programState state=null;


//    private Action MoveDown= SystemAction.get(MoveDownAction.class);
//    private Action MoveUp= SystemAction.get(MoveUpAction.class);
    private myMoveUpAction MoveUp=null;
    private myMoveDownAction MoveDown=null;
    private Action Cut = SystemAction.get(CutAction.class);
    private Action Paste = SystemAction.get(PasteAction.class);

    private String fileName=null;
    private File file =null;
    
    private String programOutput=null;

    private static VTEditor currentVTEditor=null;
    private  JTextArea linesTextArea=null;


    /** Creates new form VTeditor */
    public VTEditor(boolean NewFile) {
        initComponents();

        
       linesTextArea=new JTextArea();
       linesTextArea.setFont(new Font(Font.MONOSPACED,Font.BOLD,12));
       linesTextArea.setSize(20, this.textualViewPanel.getWidth());
       linesTextArea.setBackground(UIManager.getColor("Label.background"));
       linesTextArea.setBorder(BorderFactory.createEmptyBorder());
       //this.jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
       linesTextArea.setLineWrap(true);
       linesTextArea.setWrapStyleWord(true);
       linesTextArea.setEditable(false);
       textualViewPanel.add(linesTextArea,BorderLayout.WEST);
        
        TPane=new JTextPane();
        TPane.addCaretListener(new VisibleCaretListener());
        new AddPopupMenuOnlyCopySelectAll(TPane);
        textualViewPanel.add(TPane);

     

        if (NewFile)
        {
          index=count++;
        }
        else
            index=count;

        NEW_PROGRAM_NAME=NEW_PROGRAM_NAME_BASE + index;
        this.setDisplayName(NEW_PROGRAM_NAME +".kos");
        this.setIcon(ImageUtilities.loadImage("org/coeus/vteditor/resources/fileIcon16.png", true));

        //////Begin with visual view
        TextViewSelected=false;
        TextViewSelectedInstance=false;
        ///////end
        
        ////////**********WONT USE PALETTE API**********
        //////Create Pallete
        //paletteLU = SingletonPaletteSupport.createPalette();
        //SingletonPaletteSupport.MyFilter.setCategoriesHidden(TextViewSelected);
       //////End create palette

        /////////////////Create Lists for Programming Objects/////////////////////
         allol = new AllObjectsList();
         conl = new ConstantObjectList();
         varl = new VariableObjectList();
         arrl = new ArrayObjectList();
         funl = new FunctionObjectList();
         prol = new ProcedureObjectList ();
         readl= new ReadObjectList();
         writel= new WriteObjectList();
         calll= new CallObjectList();
         retl= new ReturnObjectList();
         assl= new AssignObjectList();
         dwhl= new DoWhileObjectList();
         forl=new ForObjectList();
         iffl= new  IfObjectList();
         whil= new WhileObjectList();

         undoRedoList=new undoRedoClassList();
         modificationMade= new modificationMadeClass();
         state= new programState(this.getDisplayName());
        /////////////////Create Lists for Programming Objects/////////////////////

        /////////Create MyBeanTreeView
        mbtv =new MyBeanTreeView();


             /////////////Associate  LOOKUP of explorer manager,palette and this

         ProxyLookup mainLookup = new ProxyLookup(new Lookup[]{
                    org.openide.explorer.ExplorerUtils.createLookup(explorerManager, getActionMap())
                 //  ,Lookups.fixed(paletteLU) WONT USE PALETTE API
                   ,Lookups.singleton(this.allol)
                   ,Lookups.singleton(this.conl)
                   ,Lookups.singleton(this.varl)
                   ,Lookups.singleton(this.arrl)
                   ,Lookups.singleton(this.funl)
                   ,Lookups.singleton(this.prol)
                   ,Lookups.singleton(this.readl)
                   ,Lookups.singleton(this.writel)
                   ,Lookups.singleton(this.calll)
                   ,Lookups.singleton(this.retl)
                   ,Lookups.singleton(this.assl)
                   ,Lookups.singleton(this.dwhl)
                   ,Lookups.singleton(this.iffl)
                   ,Lookups.singleton(this.forl)
                   ,Lookups.singleton(this.whil)
                   ,Lookups.singleton(this.undoRedoList)
                   ,Lookups.singleton(this.modificationMade)
                   ,Lookups.singleton(this.state)
                   ,Lookups.singleton(this)
        });
        
         associateLookup(mainLookup);
         ///////////End associate lookup


         /////////Create built-in functions
        new createBuiltInFunctions();

       

        //If NewFile was created
        if (NewFile)
        {
 
        ///Create ProgramObject to add to RootNode's Lookup
        ProgramObject RootObject = new ProgramObject(NEW_PROGRAM_NAME,
                "program"+Integer.toString(index));
        

            ///Create Children for Root Node
        CreateMain npcnp =new CreateMain("program"+Integer.toString(index));

            ///Create lookup for RootNode
        ProxyLookup RootLookup = new ProxyLookup(new Lookup[]{
            Lookups.singleton(npcnp),Lookups.fixed(new Object[] {RootObject})} );

            ////Create RootNode and add it to BeanTreeView
          PRoot = new RootNode(npcnp,RootLookup);
    
         // PRoot.setDisplayName(this.getDisplayName());
         
        
         explorerManager.setRootContext(PRoot);
 new getUndoInstance(PRoot);
       // explorerManager.getRootContext().setDisplayName(this.getDisplayName());
        }
        ///If Open was selected
        else
        {}
        VPane.setViewportView(mbtv);
        ////////////////////End create MyBeanTreeView

        ////////////////BeanTreeView Action Map
        ActionMap map = getActionMap();
        map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(explorerManager));
        map.put(DefaultEditorKit.cutAction, ExplorerUtils.actionCut(explorerManager));
        map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(explorerManager));
        map.put("delete", ExplorerUtils.actionDelete(explorerManager, true));
        ///////////////End BeanTreeView Action Map


         
        /////////////////Initialization of ProgramCompents Registry///////
//////////////////////////////////// putClientProperty("print.printable", Boolean.TRUE); // NOI18N
       
        /////////////////END Initialization of ProgramCompents Registry///////
      

        /////Add lookuplistener for VTEditor Objects
    Lookup.Template tpl = new Lookup.Template (VTEditor.class);
    result = Utilities.actionsGlobalContext().lookup(tpl);
    result.addLookupListener (this);

 
    resultPCParentNodes = Utilities.actionsGlobalContext().lookupResult(PCNodeParent.class);
    resultPCParentNodes.addLookupListener (this);

    resultPCLeafNodes = Utilities.actionsGlobalContext().lookupResult(PCNodeLeaf.class);
    resultPCLeafNodes.addLookupListener (this);

    resultRootNode = Utilities.actionsGlobalContext().lookupResult(RootNode.class);
    resultRootNode.addLookupListener (this);
    ////End lookuplistener

     //////Disable buttons
    setMyButtonsEnabled(false);

    /////Check visual or textual tab to repaint palette
        VTTabs.addChangeListener(new VTTabsChangeListener());



        //Enable Print Action item for textual and visual view
        //this.putClientProperty(java.awt.print.Printable.class, "");
      
        this.mbtv.getTree().putClientProperty("print.printable", Boolean.TRUE);
        this.mbtv.getTree().putClientProperty("print.name",  "Graphical View - "+this.state.getProgramName());
        this.mbtv.getTree().putClientProperty("print.order", "1");

//        this.TPane.putClientProperty("print.printable", Boolean.TRUE);
//        this.TPane.putClientProperty("print.name", "Προβολή Κειμένου - "+this.state.getProgramName());
//        this.TPane.putClientProperty("print.order", "2");
        this.textualViewPanel.putClientProperty("print.printable", Boolean.TRUE);
        this.textualViewPanel.putClientProperty("print.name", "Textual View - "+this.state.getProgramName());
        this.textualViewPanel.putClientProperty("print.order", "2");
  

    
currentVTEditor=this;
}
////////////////////////////////////////End of constructor////////////////////////////////////////////////////


private class VTTabsChangeListener implements ChangeListener{

        public void stateChanged(ChangeEvent e) {
             /////////////Textual View
       if (VTTabs.getSelectedIndex()==1)          

       {
           myAdvanceAction.getAdvanceAction().setPresentersEnabled(false);

           TextViewSelected=true;
           TextViewSelectedInstance=true;
           setMyButtonsVisible(false);
           BTVPalTopComponent.MyFilter(TextViewSelected);

           //Checks.constantsVariablesArraysChecks(PRoot);

           new CreateCoeusProgram(PRoot,TPane,linesTextArea);
          
        
          // new CreateJavaProgram(PRoot, TPane);

         //  new TestObjects(TPane,allol,conl,varl,arrl,funl,prol);

          if (programOutput!=null)
           new addTextToTextualView(TPane,programOutput);


       }
       ///////////Visual View
       else
       {
           myAdvanceAction.getAdvanceAction().setPresentersEnabled(true);

           TextViewSelected=false;
           TextViewSelectedInstance=false;
           BTVPalTopComponent.MyFilter(TextViewSelected);
           setMyButtonsVisible(true);
       }
   /////End check visual or textual
       myShowLineNumbersAction.getShowLinesAction().checkShowLinesAction();
  }
}

public void setMyBooleansForButtons(boolean tf)
{
 MoveUpEnabled=tf;
 MoveDownEnabled=tf;
 CutEnabled=tf;
 DeleteEnabled=tf;
 PasteEnabled=tf;
}


public void setMyButtonsVisible (boolean tf)
{
 MoveUpBut.setVisible(tf);
 MoveDownBut.setVisible(tf);
 DeleteBut.setVisible(tf);
 CutBut.setVisible(tf);
 PasteBut.setVisible(tf);
 jLabel1.setVisible(tf);
 jLabel2.setVisible(tf);
 jLabel3.setVisible(tf);
 expandAllButton.setVisible(tf);
 collapseAllButton.setVisible(tf);
}

public void setMyButtonsEnabled (boolean tf)
{
 MoveUpBut.setEnabled(tf);
 MoveDownBut.setEnabled(tf);
 DeleteBut.setEnabled(tf);
 CutBut.setEnabled(tf);
 PasteBut.setEnabled(tf);
}


   public void ParentNodeSetButtons(Collection<PCNodeParent> cpn)
   {
            PCNodeParent pn = (PCNodeParent) cpn.iterator().next();
                 StatusDisplayer.getDefault().setStatusText(pn.getDispName()+" ("+pn.getDispCateg()+") was selected" );
             an=(AbstractNode)pn;
             displayName=pn.getDispName();
             displayCategory=pn.getDispCateg();

                 MoveUpEnabled=true;
                 MoveDownEnabled=true;
                 DeleteEnabled=true;
                 CutEnabled=true;
                 PasteEnabled=true;

        int nodesCount= pn.getParentNode().getChildren().getNodesCount();
        String firstNode = (pn.getParentNode().getChildren().getNodeAt(0)).getDisplayName();
        String lastNode = (pn.getParentNode().getChildren().getNodeAt(nodesCount-1)).getDisplayName();


      if (firstNode==null || pn.getDisplayName().equalsIgnoreCase(firstNode))
            MoveUpEnabled=false;
      if (lastNode==null || pn.getDisplayName().equalsIgnoreCase(lastNode))
            MoveDownEnabled=false;
      if (pn.getDispName().equalsIgnoreCase(CoeusProgram.MAIN_PROC))
           DeleteEnabled=false;
      //  if (pn.getDispCateg().equalsIgnoreCase(WizardsDefinitions.PROCEDURE) || pn.getDispCateg().equalsIgnoreCase(WizardsDefinitions.FUNCTION) )
           CutEnabled=false;
     if ( pn.isIfThenElseChild(pn.getDispCateg()))
     { MoveUpEnabled=false; MoveDownEnabled=false; DeleteEnabled=false;}


        if (!CutPressed)
            PasteEnabled=false;
       
       MoveUpBut.setEnabled(MoveUpEnabled);
       MoveDownBut.setEnabled(MoveDownEnabled);
       DeleteBut.setEnabled(DeleteEnabled);
       CutBut.setEnabled(CutEnabled);
       PasteBut.setEnabled(PasteEnabled);
   }

   public void LeafNodeSetButtons(Collection<PCNodeLeaf> cln)
   {
            PCNodeLeaf ln = (PCNodeLeaf) cln.iterator().next();
               StatusDisplayer.getDefault().setStatusText(ln.getDispName()+" ("+ln.getDispCateg()+")was selected" );
             an=(AbstractNode)ln;
             displayName=ln.getDispName();
             displayCategory=ln.getDispCateg();

                 MoveUpEnabled=true;
                 MoveDownEnabled=true;
                 DeleteEnabled=true;
                 CutEnabled=false;
                 PasteEnabled=false;

        int nodesCount= ln.getParentNode().getChildren().getNodesCount();
        String firstNode = (ln.getParentNode().getChildren().getNodeAt(0)).getDisplayName();
        String lastNode = (ln.getParentNode().getChildren().getNodeAt(nodesCount-1)).getDisplayName();


      if (firstNode==null || ln.getDisplayName().equalsIgnoreCase(firstNode))
            MoveUpEnabled=false;
      if (lastNode==null || ln.getDisplayName().equalsIgnoreCase(lastNode))
            MoveDownEnabled=false;
      if (ln.getDispCateg().equals(WizardsDefinitions.VARIABLE) || ln.getDispCateg().equals(WizardsDefinitions.CONSTANT) || ln.getDispCateg().equals(WizardsDefinitions.ARRAY) ) 
            CutEnabled=true;

       MoveUpBut.setEnabled(MoveUpEnabled);
       MoveDownBut.setEnabled(MoveDownEnabled);
       DeleteBut.setEnabled(DeleteEnabled);
       CutBut.setEnabled(CutEnabled);
       PasteBut.setEnabled(PasteEnabled);
    }

   public void RootNodeSetButtons(Collection<RootNode> crn)
   {
       RootNode rn= (RootNode) crn.iterator().next();
          StatusDisplayer.getDefault().setStatusText("The beginning of program "+rn.getDisplayName()+" was selected");

          PasteEnabled=true;
          if (CutPressed) PasteBut.setEnabled(PasteEnabled);
   }

 
    @Override
   public boolean canClose()
   { ActionEvent e = null;
      if (this.modificationMade.isModificationMade())
      {
         NotifyDescriptor d = new NotifyDescriptor.Confirmation("The file has been changed, do you want to save it before exiting?",
                 "Save Changes",NotifyDescriptor.YES_NO_CANCEL_OPTION, NotifyDescriptor.QUESTION_MESSAGE);

          Object answer=DialogDisplayer.getDefault().notify(d);
          if (answer ==NotifyDescriptor.YES_OPTION)
          {
           mySaveAction save=new mySaveAction();
           save.actionPerformed(e);
          }
          else if (answer==NotifyDescriptor.CANCEL_OPTION)
              return false;
      }
     if(this.state.getNotification()!=null)
      this.state.getNotification().clear();
      return true;
    }

 
  
      public void resultChanged(LookupEvent lookupEvent) {
        setMyBooleansForButtons(false);
        setMyButtonsEnabled(false);

 ////Track Editors Windows Changes..
        // Lookup.Result r = (Lookup.Result) lookupEvent.getSource();
        Collection<VTEditor> c = result.allInstances();
        if (!c.isEmpty()) {
            currentVTEditor = (VTEditor) c.iterator().next();
            TextViewSelected=currentVTEditor.TextViewSelectedInstance;
            BTVPalTopComponent.MyFilter(TextViewSelected);
          }

 StatusDisplayer.getDefault().setStatusText("");
///Check if Root,Parent or Laef Nodes are selected
        Collection<PCNodeParent> cpn = resultPCParentNodes.allInstances();
        Collection<PCNodeLeaf> cln = resultPCLeafNodes.allInstances();
        Collection<RootNode> crn = resultRootNode.allInstances();

 ////Check if ParentNode selected
          if (!cpn.isEmpty())
            {
             ParentNodeSetButtons(cpn);
            }
////Check if LeafNode selected
            else  if (!cln.isEmpty())
            {
              LeafNodeSetButtons(cln);
            }
////Check if RootNode selected
            else  if (!crn.isEmpty()) {
            RootNodeSetButtons(crn);
        }

      }




   ///////Setters

   public void setRootNodeAfterLoad(RootNode rn,File file)
    {
    this.PRoot=rn;
    explorerManager.setRootContext(this.PRoot);
    this.setDisplayName(this.PRoot.getDisplayName()+".kos");
    this.state.setProgramName(this.getDisplayName());
    this.TPane.putClientProperty("print.name", "Textual View - "+this.state.getProgramName());
    this.mbtv.getTree().putClientProperty("print.name",  "Graphical View - "+this.state.getProgramName());
  //  this.setFileName(this.getDisplayName());
 //   this.setFile(file);
   }

   public void setRootNode(RootNode rn,String displayName)
    {
    mbtv.getCategoryNodesState(this.PRoot);
    this.PRoot=rn;
    explorerManager.setRootContext(this.PRoot);
    this.setDisplayName(displayName);
    mbtv.setCategoryNodesState(this.PRoot);
   }

   public void setProgramOutput(String programOutput) {
        this.programOutput = programOutput;
    }


   public void setDisplayNameAfterSave(String name)
   {
   ProgramObject po= this.PRoot.getLookup().lookup(ProgramObject.class);
   po.setDisplayName(name);
   po.setObjectName(new ConvertGreek2English(name).getEnglishIdintifier() );
   explorerManager.getRootContext().setDisplayName(name);
   this.setDisplayName(name+".kos");
   this.state.setProgramName(this.getDisplayName());
   this.TPane.putClientProperty("print.name", "Textual View - "+this.state.getProgramName());
   this.mbtv.getTree().putClientProperty("print.name",  "Graphical View - "+this.state.getProgramName());
   }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

  
    public void setFile(File file) {
        this.file = file;
    }


    public void setProgramState(programState state) {
        this.state = state;
    }

 /////////Getters
  public static VTEditor getCurrentVTEditor()
  {return currentVTEditor;}

   public ExplorerManager getExplorerManager() {
        return explorerManager;
    }

    @Override
    public int getPersistenceType() {
//        return PERSISTENCE_ONLY_OPENED;
         return PERSISTENCE_NEVER;
   }

    public JTextPane getTextPane() {
        return TPane;
    }

     public JTextArea getLineArea() {
        return linesTextArea;
    }

    public JPanel getTextualViewPanel()
    {return textualViewPanel;}

    public MyBeanTreeView getMyBeabTreeView() {
        return mbtv;
    }

     public JTabbedPane getVisualTextTabsPane() {
        return this.VTTabs;
    }

    public programState getProgramState() {
        return this.state;
    }


    public String getObjectName()
    {
     ProgramObject po= this.PRoot.getLookup().lookup(ProgramObject.class);
     return po.getObjName();
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return file;
    }

    public modificationMadeClass getModificationMade() {
        return modificationMade;
    }
    

    public RootNode getRootNode()
    {return this.PRoot;}

    public AllObjectsList getAllObjectsList()
    {return this.allol;}

    public ConstantObjectList getConstantObjectList()
    {return this.conl;}

    public VariableObjectList getVariableObjectList()
    {return this.varl;}

    public ArrayObjectList getArrayObjectList()
    {return this.arrl;}

    public FunctionObjectList getFunctionObjectList()
    {return this.funl;}

    public ProcedureObjectList getProcedureObjectList()
    {return this.prol;}

    public ReadObjectList getReadObjectList()
    {return this.readl;}

    public WriteObjectList getWriteObjectList()
    {return this.writel;}

    public CallObjectList getCallObjectList()
    {return this.calll;}

    public ReturnObjectList getReturnObjectList()
    {return this.retl;}

    public AssignObjectList getAssignObjectList()
    {return this.assl;}

    public DoWhileObjectList getDoWhileObjectList()
    {return this.dwhl;}

    public ForObjectList getForObjectList()
    {return this.forl;}

    public IfObjectList getIfObjectList()
    {return this.iffl;}

    public WhileObjectList getWhileObjectList()
    {return this.whil;}

    public undoRedoClassList getUndoRedoList() {
        return undoRedoList;
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        VTTabs = new javax.swing.JTabbedPane();
        VPane = new javax.swing.JScrollPane();
        beanTreeView1 = new org.openide.explorer.view.BeanTreeView();
        jScrollPane2 = new javax.swing.JScrollPane();
        textualViewPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        MoveUpBut = new javax.swing.JButton();
        MoveDownBut = new javax.swing.JButton();
        DeleteBut = new javax.swing.JButton();
        CutBut = new javax.swing.JButton();
        PasteBut = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        expandAllButton = new javax.swing.JButton();
        collapseAllButton = new javax.swing.JButton();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.jLabel1.text")); // NOI18N

        VTTabs.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        VTTabs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                VTTabsFocusGained(evt);
            }
        });

        VPane.setViewportView(beanTreeView1);

        VTTabs.addTab(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.VPane.TabConstraints.tabTitle"), VPane); // NOI18N

        textualViewPanel.setLayout(new java.awt.BorderLayout());
        jScrollPane2.setViewportView(textualViewPanel);

        VTTabs.addTab(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.jScrollPane2.TabConstraints.tabTitle"), jScrollPane2); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.jLabel2.text")); // NOI18N

        MoveUpBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/up.gif"))); // NOI18N
        MoveUpBut.setText(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.MoveUpBut.text")); // NOI18N
        MoveUpBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoveUpButActionPerformed(evt);
            }
        });

        MoveDownBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/down.gif"))); // NOI18N
        MoveDownBut.setText(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.MoveDownBut.text")); // NOI18N
        MoveDownBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoveDownButActionPerformed(evt);
            }
        });

        DeleteBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/delete.gif"))); // NOI18N
        DeleteBut.setText(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.DeleteBut.text")); // NOI18N
        DeleteBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButActionPerformed(evt);
            }
        });

        CutBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/cut.gif"))); // NOI18N
        CutBut.setText(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.CutBut.text")); // NOI18N
        CutBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CutButActionPerformed(evt);
            }
        });

        PasteBut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/paste.gif"))); // NOI18N
        PasteBut.setText(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.PasteBut.text")); // NOI18N
        PasteBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasteButActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.jLabel3.text")); // NOI18N

        expandAllButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/openLocalExplorer.gif"))); // NOI18N
        expandAllButton.setText(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.expandAllButton.text")); // NOI18N
        expandAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expandAllButtonActionPerformed(evt);
            }
        });

        collapseAllButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/coeus/vteditor/resources/closeLocalExplorer.gif"))); // NOI18N
        collapseAllButton.setText(org.openide.util.NbBundle.getMessage(VTEditor.class, "VTEditor.collapseAllButton.text")); // NOI18N
        collapseAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collapseAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(VTTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MoveUpBut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(expandAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(collapseAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(MoveDownBut, 0, 0, Short.MAX_VALUE)
                    .addComponent(CutBut, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(DeleteBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PasteBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VTTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(MoveUpBut, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(MoveDownBut, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(CutBut, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PasteBut, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(DeleteBut, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(expandAllButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapseAllButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void MoveDownButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoveDownButActionPerformed

        Node[] selectedNodes = explorerManager.getSelectedNodes();
        MoveDown=myMoveDownAction.getMyMoveDownAction();

        MoveDown.actionPerformed(evt);
        try {
            explorerManager.setSelectedNodes(selectedNodes);
        } catch (PropertyVetoException ex) {}// Exceptions.printStackTrace(ex); }
         MoveDownBut.setEnabled(MoveDown.isEnabled());
}//GEN-LAST:event_MoveDownButActionPerformed

    private void DeleteButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButActionPerformed
            Action Delete = new myDeleteAction(an);
            Delete.actionPerformed(evt);
            DeleteBut.setEnabled(Delete.isEnabled());
            if (CutPressed && jLabel3.getText().equalsIgnoreCase((displayCategory+": "+displayName)))
            {
               CutPressed=false;
               jLabel3.setText("[None]");
            }
}//GEN-LAST:event_DeleteButActionPerformed

    private void MoveUpButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoveUpButActionPerformed

        Node[] selectedNodes = explorerManager.getSelectedNodes();
        MoveUp=myMoveUpAction.getMyMoveUpAction();

        MoveUp.actionPerformed(evt);
        try {
            explorerManager.setSelectedNodes(selectedNodes);
        } catch (PropertyVetoException ex) {}// Exceptions.printStackTrace(ex); }
        MoveUpBut.setEnabled(MoveUp.isEnabled());
    }//GEN-LAST:event_MoveUpButActionPerformed

    private void CutButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CutButActionPerformed
         Cut.actionPerformed(evt);
         CutPressed=true;
         jLabel3.setText(displayCategory+": "+displayName);
         CutBut.setEnabled(Cut.isEnabled());
    }//GEN-LAST:event_CutButActionPerformed

    private void PasteButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasteButActionPerformed
       
        Paste.actionPerformed(evt);
        CutPressed=false;
        jLabel3.setText("[None]");
        PasteBut.setEnabled(Paste.isEnabled());
    }//GEN-LAST:event_PasteButActionPerformed

    private void expandAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expandAllButtonActionPerformed
        mbtv.expandAllNodes();

    }//GEN-LAST:event_expandAllButtonActionPerformed

    private void collapseAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collapseAllButtonActionPerformed
       mbtv.collapseAllNodes();
    }//GEN-LAST:event_collapseAllButtonActionPerformed

    private void VTTabsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_VTTabsFocusGained

}//GEN-LAST:event_VTTabsFocusGained



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CutBut;
    private javax.swing.JButton DeleteBut;
    private javax.swing.JButton MoveDownBut;
    private javax.swing.JButton MoveUpBut;
    private javax.swing.JButton PasteBut;
    private javax.swing.JScrollPane VPane;
    private javax.swing.JTabbedPane VTTabs;
    private org.openide.explorer.view.BeanTreeView beanTreeView1;
    private javax.swing.JButton collapseAllButton;
    private javax.swing.JButton expandAllButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel textualViewPanel;
    // End of variables declaration//GEN-END:variables


}
