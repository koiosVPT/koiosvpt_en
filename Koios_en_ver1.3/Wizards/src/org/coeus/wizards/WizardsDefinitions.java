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
//
//package org.coeus.wizards;
//
///**
// *
// * @author Jrd
// */
//public class WizardsDefinitions {
//
//    public static final String INT = "int";
//    public static final String FLOAT = "float";
//    public static final String DOUBLE = "double";
//    public static final String CHAR = "char";
//    public static final String BOOLEAN = "boolean";
//    public static final String STRING="String";
//
//
//    public static final String GLOBAL="ΓΕΝΙΚΗ";
//    public static final String GLOBAL_EN="global";
//    public static final String MAIN_PROC="ΚΥΡΙΩΣ ΔΙΑΔΙΚΑΣΙΑ";
//
//
//    public static final String CONSTANT="ΣΤΑΘΕΡΑ";
//    public static final String VARIABLE="ΜΕΤΑΒΛΗΤΗ";
//    public static final String ARRAY="ΠΙΝΑΚΑΣ";
//    public static final String FUNCTION = "ΣΥΝΑΡΤΗΣΗ";
//    public static final String PROCEDURE = "ΔΙΑΔΙΚΑΣΙΑ";
//
//
//    public static final String ARRAY_ELEMANT="ΣΤΟΙΧΕΙΟ ΠΙΝΑΚΑ";
//    public static final String PARAMETER="ΠΑΡΑΜΕΤΡΟΣ ΕΙΣΟΔΟΥ";
//
//
//    public static final String COMMAND="ΕΝΤΟΛΗ";
//    public static final String COM_READ="ΕΝΤΟΛΗ ΔΙΑΒΑΣΕ";
//    public static final String READ="ΔΙΑΒΑΣΕ ";
//
//    public static final String COM_WRITE="ΕΝΤΟΛΗ ΓΡΑΨΕ";
//    public static final String WRITE="ΓΡΑΨΕ ";
//
//    public static final String COM_CALL="ΕΝΤΟΛΗ ΚΑΛΕΣΕ";
//    public static final String CALL="ΚΑΛΕΣΕ ";
//
//    public static final String COM_FUNC_CALL="ΕΝΤΟΛΗ ΚΑΛΕΣΕ ΣΥΝΑΡΤΗΣΗ";
//    public static final String FUNC_CALL="ΚΑΛΕΣΕ ΣΥΝΑΡΤΗΣΗ";
//
//
//    public static final String COM_ASSIGN="ΕΝΤΟΛΗ ΑΝΑΘΕΣΕ";
//    public static final String ASSIGN="ΑΝΑΘΕΣΕ ";
//
//    public static final String COM_RETURN="ΕΝΤΟΛΗ ΕΠΕΣΤΡΕΨΕ";
//    public static final String RETURN="ΕΠΕΣΤΡΕΨΕ ";
//
//    public static final String COM_WHILE="ΕΝΤΟΛΗ ΟΣΟ..ΕΠΑΝΕΛΑΒΕ";
//    public static final String WHILE="ΟΣΟ..ΕΠΑΝΕΛΑΒΕ";
//    public static final String WHILE1="ΟΣΟ (";
//    public static final String WHILE2=") ΕΠΑΝΕΛΑΒΕ";
//
//
//    public static final String COM_IF="ΕΝΤΟΛΗ ΑΝ";
//    public static final String IF="ΕΝΤΟΛΗ ΑΝ No ";
//    public static final String IF1="ΑΝ (";
//    public static final String IF2=") ΤΟΤΕ";
//    public static final String ELSE_IF="ΑΛΛΙΩΣ ΑΝ ";
//    public static final String ELSE="ΑΛΛΙΩΣ ";
//
//    public static final String IF_STATEMENT="TMHMA ΑΝ";
//    public static final String ELSE_IF_STATEMENT="TMHMA ΑΛΛΙΩΣ ΑΝ";
//    public static final String ELSE_STATEMENT="TMHMA ΑΛΛΙΩΣ";
//
//
//
//    public static final String COM_DOWHILE="ΕΝΤΟΛΗ ΚΑΝΕ..ΟΣΟ";
//    public static final String DOWHILE="ΚΑΝΕ..ΟΣΟ";
//    public static final String DOWHILE1="ΚΑΝΕ...";
//    public static final String DOWHILE2="ΟΣΟ (";
//
//    public static final String COM_FOR="ΕΝΤΟΛΗ ΓΙΑ..ΜΕΧΡΙ";
//    public static final String FOR="ΓΙΑ";
//    public static final String FOR1="ΓΙΑ ";
//    public static final String FOR2=" ΜΕΧΡΙ ";
//    public static final String FOR3=" ΜΕ ΒΗΜΑ";
//
//
//
//    public static final String INT1="ΑΚΕΡΑΙΑ";
//    public static final String DOUBLE1="ΠΡΑΓΜΑΤΙΚΗ";
//    public static final String FLOAT1=DOUBLE1;
//    public static final String CHAR1="ΧΑΡΑΚΤΗΡΑΣ";
//    public static final String BOOLEAN1="ΛΟΓΙΚΗ";
//    public static final String STRING1="ΣΥΜΒΟΛΟΣΕΙΡΑ";
//
//
//    public static final String MESSAGE =STRING1;// "ΜΥΝΗΜΑ";
//
//    public static final String INT2="ΑΚΕΡΑΙΩΝ";
//    public static final String DOUBLE2="ΠΡΑΓΜΑΤΙΚΩΝ";
//    public static final String FLOAT2=DOUBLE2;
//    public static final String CHAR2="ΧΑΡΑΚΤΗΡΩΝ";
//    public static final String BOOLEAN2="ΛΟΓΙΚΩΝ";
//    public static final String STRING2="ΣΥΜΒΟΛΟΣΕΙΡΩΝ";
//
////    public static final String INT3="ΑΚΕΡΑΙΟΣ";
////    public static final String DOUBLE3="ΠΡΑΓΜΑΤΙΚΟΣ";
////    public static final String FLOAT3=DOUBLE3;
////    public static final String CHAR3="ΧΑΡΑΚΤΗΡΑΣ";
////    public static final String BOOLEAN3="ΛΟΓΙΚΟΣ";
//
//    public static final String DIM1="Μονοδιάστος (1-Δ)";
//    public static final String DIM2="Δισδιάστος  (2-Δ)";
//
//    public static final String CONDITION="ΛΟΓΙΚΗ ΣΥΝΘΗΚΗ";
//    public static final String EXPRESSION="ΑΡΙΘΜΗΤΙΚΗ ΕΚΦΡΑΣΗ";
//    public static final String CHARACTER=CHAR1;//Prin thn allagh htan ="ΧΑΡΑΚΤΗΡΑΣ";
//
//    public static final String INITIAl_VALUE="ΑΡΧΙΚΗ ΤΙΜΗ";
//    public static final String FINAL_VALUE="ΤΕΛΙΚΗ ΤΙΜΗ";
//    public static final String STEP="ΒΗΜΑ";
//
//
//
//
//
//
//}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coeus.wizards;

/**
 *
 * @author Jrd
 */
public class WizardsDefinitions {

    public static final String INT = "int";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";
    public static final String CHAR = "char";
    public static final String BOOLEAN = "boolean";
    public static final String STRING="String";


    public static final String GLOBAL="GLOBAL";
    public static final String GLOBAL_EN="global";
    public static final String MAIN_PROC="MAIN PROCEDURE";


    public static final String CONSTANT="CONSTANT";
    public static final String VARIABLE="VARIABLE";
    public static final String ARRAY="ARRAY";
    public static final String FUNCTION = "FUNCTION";
    public static final String PROCEDURE = "PROCEDURE";


    public static final String ARRAY_ELEMANT="ARRAY ELEMENT";
    public static final String PARAMETER="INPUT PARAMETER";


    public static final String COMMAND="COMMAND";
    public static final String COM_READ="READ COMMAND";
    public static final String READ="READ ";

    public static final String COM_WRITE="WRITE COMMAND";
    public static final String WRITE="WRITE ";

    public static final String COM_CALL="CALL COMMAND";
    public static final String CALL="CALL ";

    public static final String COM_FUNC_CALL="CALL FUNCTION COMMAND";
    public static final String FUNC_CALL="CALL FUNCTION ";


    public static final String COM_ASSIGN="ASSIGN COMMAND";
    public static final String ASSIGN="ASSIGN ";

    public static final String COM_RETURN="RETURN COMMAND";
    public static final String RETURN="RETURN ";

    public static final String COM_WHILE="WHILE..REPEAT ITERATION STATEMENT";
    public static final String WHILE="WHILE..REPEAT";
    public static final String WHILE1="WHILE (";
    public static final String WHILE2=") REPEAT";


    public static final String COM_IF="IF..ELSE CONDITIONAL STATEMENT";
    public static final String IF="IF COMMAND No ";
    public static final String IF1="IF (";
    public static final String IF2=") THEN";
    public static final String ELSE_IF="ELSE IF ";
    public static final String ELSE="ELSE ";

    public static final String IF_STATEMENT="IF STATEMENT";
    public static final String ELSE_IF_STATEMENT="ELSE IF STATEMENT";
    public static final String ELSE_STATEMENT="ELSE STATEMENT";



    public static final String COM_DOWHILE="DO..WHILE ITERATION STATEMENT";
    public static final String DOWHILE="DO..WHILE";
    public static final String DOWHILE1="DO..";
    public static final String DOWHILE2="WHILE (";

    public static final String COM_FOR="FOR..LOOP ITERATION STATEMENT";
    public static final String FOR="FOR";
    public static final String FOR1="FOR ";
    public static final String FOR2=" AND ";
    public static final String FOR3=" LOOP WITH STEP";



    public static final String INT1="INTEGER";
    public static final String DOUBLE1="REAL";
    public static final String FLOAT1=DOUBLE1;
    public static final String CHAR1="CHARACTER";
    public static final String BOOLEAN1="BOOLEAN";
    public static final String STRING1="STRING";


    public static final String MESSAGE =STRING1;// "ΜΥΝΗΜΑ";

    public static final String INT2="INTEGER";
    public static final String DOUBLE2="REAL";
    public static final String FLOAT2=DOUBLE2;
    public static final String CHAR2="CHARACTER";
    public static final String BOOLEAN2="BOOLEAN";
    public static final String STRING2="STRING";

//    public static final String INT3="ΑΚΕΡΑΙΟΣ";
//    public static final String DOUBLE3="ΠΡΑΓΜΑΤΙΚΟΣ";
//    public static final String FLOAT3=DOUBLE3;
//    public static final String CHAR3="ΧΑΡΑΚΤΗΡΑΣ";
//    public static final String BOOLEAN3="ΛΟΓΙΚΟΣ";

    public static final String DIM1="ONE-DIMENSIONAL (1-D)";
    public static final String DIM2="TWO-DIMENSIONAL (2-D)";

    public static final String CONDITION="LOGICAL CONDITION";
    public static final String EXPRESSION="ARITHMETIC EXPRESSION";
    public static final String CHARACTER=CHAR1;//Prin thn allagh htan ="ΧΑΡΑΚΤΗΡΑΣ";

    public static final String INITIAl_VALUE="INITIAL EXPRESSION";
    public static final String FINAL_VALUE="FINAL CONDITION";
    public static final String STEP="STEP";






}
