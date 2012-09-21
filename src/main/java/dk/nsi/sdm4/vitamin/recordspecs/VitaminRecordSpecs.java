/**
 * The MIT License
 *
 * Original work sponsored and donated by National Board of e-Health (NSI), Denmark
 * (http://www.nsi.dk)
 *
 * Copyright (C) 2011 National Board of e-Health (NSI), Denmark (http://www.nsi.dk)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dk.nsi.sdm4.vitamin.recordspecs;

import dk.nsi.sdm4.core.persistence.recordpersister.RecordSpecification;

import static dk.nsi.sdm4.core.persistence.recordpersister.FieldSpecification.field;

/**
 * Specifikation af feltlænger og -navne for de fire typer data i vitamin-filerne.
 */
public class VitaminRecordSpecs {
    public static final RecordSpecification GRUNDDATA_RECORD_SPEC = RecordSpecification.createSpecification("VitaminGrunddata", "drugID", 
            field("drugID", 11).numerical(),
            field("varetype", 2),
            field("varedeltype", 2),
            field("alfabetSekvensplads", 9),
            field("specNummer", 5),
		    field("navn", 30),
            field("formTekst", 20),
		    field("formKode", 7),
		    field("kodeYderligereFormOplysninger", 7),
            field("styrkeTekst", 20),
            field("styrkeNumerisk", 10).decimal10_3(),
            field("styrkeEnhed", 3),
            field("mtIndehaverKode", 6).numerical(),
            field("repraesentantDistributoerKode", 6).numerical(),
            field("atcKode", 8),
            field("administrationsvejKode", 8),
            field("trafikadvarsel", 1),
            field("substitution", 1),
		    field("blank", 3).ignored(),
            field("substitutionsgruppe", 4),
            field("dosisdispensering", 1),
		    field("blank", 8).ignored(),
		    field("karantaeneDato", 8));
    
    public static final RecordSpecification FIRMADATA_RECORD_SPEC = RecordSpecification.createSpecification("VitaminFirmadata", "firmaID", 
            field("firmaID", 6).numerical(),
            field("langtFirmaMaerke", 32),
		    field("kortFirmaMaerke", 20),
		    field("parallelimportKode", 2));
    
    public static final RecordSpecification UDGAAEDENAVNE_RECORD_SPEC = RecordSpecification.createSpecification("VitaminUdgaaedeNavne", "drugID", 
            field("drugID", 11).numerical(),
            field("aendringsDato", 8),
            field("tidligereNavn", 50));
    
    public static final RecordSpecification INDHOLDSSTOFFER_RECORD_SPEC = RecordSpecification.createSpecification("VitaminIndholdsstoffer", "drugID", 
            field("drugID", 11).numerical(),
		    field("tom", 6).ignored(),
		    field("stofklasse", 100),
            field("substansgruppe", 100),
            field("substans", 150));

}
