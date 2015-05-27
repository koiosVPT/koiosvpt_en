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

import org.coeus.parsers.parserDefinitions;

/**
 *
 * @author Jrd
 */
public class ReservedWords {

//Reserved words in java
//
//abstract	assert	boolean	break	byte	case
//catch	char	class	const*	continue	default
//double	do	else	enum	extends	false
//final	        finally	float	for	goto*	if
//implements	import	instanceof	int	interface	long
//native	new	null	package	private	protected
//public	return	short	static	strictfp	super
//switch	synchronized	this	throw	throws	transient
//true	        try	void	volatile	while


    private static final String [] ReservedWords ={"ΑΚΕΡΑΙΑ","ΑΚΕΡΑΙΩΝ",
    "ΠΡΑΓΜΑΤΙΚΗ","ΠΡΑΓΜΑΤΙΚΩΝ",
    "ΧΑΡΑΚΤΗΡΑΣ","ΧΑΡΑΚΤΗΡΩΝ",
    "ΛΟΓΙΚΗ","ΛΟΓΙΚΩΝ",
    "ΚΥΡΙΩΣ", "ΔΙΑΔΙΚΑΣΙΑ","ΤΕΛΟΣ","ΔΙΑΔΙΚΑΣΙΑΣ",
    "ΣΥΝΑΡΤΗΣ","ΣΥΝΑΡΤΗΣΗΣ",
            "ΣΤΑΘΕΡΑ","ΜΕΤΑΒΛΗΤΗ","ΠΙΝΑΚΑΣ",
            "ΑΝΑΘΕΣΕ","ΣΤΗ","ΤΗΝ","ΤΙΜΗ",
            "ΑΝ","ΙΣΧΥΕΙ","ΟΙ","ΣΥΝΘΗΚΗ","ΣΥΝΘΗΚΕΣ","ΕΚΤΕΛΕΣΕ","ΤΗΝ","ΕΝΤΟΛΗ","ΤΙΣ,ΕΝΤΟΛΕΣ",
            "ΟΣΟ","ΕΠΑΝΕΛΑΒΕ","ΚΑΝΕ",
            "ΓΙΑ","ΑΠΟ","ΜΕΧΡΙ","ΜΕ","ΒΗΜΑ","ΑΛΛΙΩΣ",
            "ΕΞΟΔΟΣ",
            "ΚΑΛΕΣΕ","ΤΗ","ΠΑΡΑΜΕΤΡΟΥΣ","ΠΑΡΑΜΕΤΡΟ",
            "ΕΠΕΣΤΡΕΨΕ",
            "ΤΕΤΡΑΓΩΝΙΚΗ_ΡΙΖΑ","ΗΜΙΤΟΝΟ","ΣΥΝΗΜΙΤΟΝΟ","ΕΦΑΠΤΟΜΕΝΗ","ΑΚΕΡΑΙΟ_ΜΕΡΟΣ","ΛΟΓΑΡΙΘΜΟΣ",
            "ΑΠΟΛΥΤΗ_ΤΙΜΗ","ΥΨΩΣΗ_ΔΥΝΑΜΗ","ΥΨΩΣΗ_Ε",
            "OR","AND","NOT",
            "SINE","COSINE","TANGENT","SQUARE_ROOT","LOGARITHM","E_TO_POWER_OF","ABSOLUTE_VALUE","POWER","INTEGER_PART",
            "MAIN",
            parserDefinitions.TRUE,parserDefinitions.FALSE,
            "ΔΙΑΙΡΕΣΗΣ","ΥΠΟΛΟΙΠΟ","ΔΙΑΙΡΕΣΗ","ΑΡΧΙΚΗ","ΜΕ","ΑΡΧΙΚΕΣ","ΤΙΜΕΣ",
            "=","'","-",";"
    };

    

    public static boolean CheckReservedWord(String word)
    {

      for (int i=0;i<ReservedWords.length;i++)
      {
        if(ReservedWords[i].equalsIgnoreCase(word))
                return true;
      }
     return false;
    }



        private static final String [] ReservedWordsWithoutBoolOperators ={"ΑΚΕΡΑΙΑ","ΑΚΕΡΑΙΩΝ",
    "ΠΡΑΓΜΑΤΙΚΗ","ΠΡΑΓΜΑΤΙΚΩΝ",
    "ΧΑΡΑΚΤΗΡΑΣ","ΧΑΡΑΚΤΗΡΩΝ",
    "ΛΟΓΙΚΗ","ΛΟΓΙΚΩΝ",
    "ΚΥΡΙΩΣ", "ΔΙΑΔΙΚΑΣΙΑ","ΤΕΛΟΣ","ΔΙΑΔΙΚΑΣΙΑΣ",
    "ΣΥΝΑΡΤΗΣ","ΣΥΝΑΡΤΗΣΗΣ",
            "ΣΤΑΘΕΡΑ","ΜΕΤΑΒΛΗΤΗ","ΠΙΝΑΚΑΣ",
            "ΑΝΑΘΕΣΕ","ΣΤΗ","ΤΗΝ","ΤΙΜΗ",
            "ΑΝ","ΙΣΧΥΕΙ","ΟΙ","ΣΥΝΘΗΚΗ","ΣΥΝΘΗΚΕΣ","ΕΚΤΕΛΕΣΕ","ΤΗΝ","ΕΝΤΟΛΗ","ΤΙΣ,ΕΝΤΟΛΕΣ",
            "ΟΣΟ","ΕΠΑΝΕΛΑΒΕ","ΚΑΝΕ",
            "ΓΙΑ","ΑΠΟ","ΜΕΧΡΙ","ΜΕ","ΒΗΜΑ","ΑΛΛΙΩΣ",
            "ΕΞΟΔΟΣ",
            "ΚΑΛΕΣΕ","ΤΗ","ΠΑΡΑΜΕΤΡΟΥΣ","ΠΑΡΑΜΕΤΡΟ",
            "ΕΠΕΣΤΡΕΨΕ",
            "ΤΕΤΡΑΓΩΝΙΚΗ_ΡΙΖΑ","ΗΜΙΤΟΝΟ","ΣΥΝΗΜΙΤΟΝΟ","ΕΦΑΠΤΟΜΕΝΗ","ΑΚΕΡΑΙΟ_ΜΕΡΟΣ","ΛΟΓΑΡΙΘΜΟΣ",
            "ΑΠΟΛΥΤΗ_ΤΙΜΗ","ΥΨΩΣΗ_ΔΥΝΑΜΗ","ΥΨΩΣΗ_Ε",
        
            "SINE","COSINE","TANGENT","SQUARE_ROOT","LOGARITHM","E_TO_POWER_OF","ABSOLUTE_VALUE","POWER","INTEGER_PART",
            "MAIN",

           "ΔΙΑΙΡΕΣΗΣ","ΥΠΟΛΟΙΠΟ","ΔΙΑΙΡΕΣΗ","ΑΡΧΙΚΗ","ΜΕ","ΑΡΧΙΚΕΣ","ΤΙΜΕΣ",
            "=","'","-",";"
    };


    public static boolean CheckReservedWordNotBoolOperators(String word)
    {

      for (int i=0;i<ReservedWordsWithoutBoolOperators.length;i++)
      {
        if(ReservedWordsWithoutBoolOperators[i].equalsIgnoreCase(word))
                return true;
      }
     return false;
    }
    
}
