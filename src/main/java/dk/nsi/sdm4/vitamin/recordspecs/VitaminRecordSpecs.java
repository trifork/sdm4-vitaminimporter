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
            field("drugID", 11, false).numerical(),
            field("varetype", 2, false),
            field("varedeltype", 2, false),
            field("alfabetSekvensplads", 9, false),
            field("specNummer", 5, false),
		    field("navn", 30, false),
            field("formTekst", 20, false),
		    field("formKode", 7, false),
		    field("kodeYderligereFormOplysninger", 7, false),
            field("styrkeTekst", 20, false),
            field("styrkeNumerisk", 10, false).decimal10_3(),
            field("styrkeEnhed", 3, false),
            field("mtIndehaverKode", 6, false).numerical(),
            field("repraesentantDistributoerKode", 6, false).numerical(),
            field("atcKode", 8, false),
            field("administrationsvejKode", 8, false),
            field("trafikadvarsel", 1, false),
            field("substitution", 1, false),
		    field("blank", 3, false).ignored(),
            field("substitutionsgruppe", 4, false),
            field("dosisdispensering", 1, false),
		    field("blank", 8, false).ignored(),
		    field("karantaeneDato", 8, false));
    
    public static final RecordSpecification FIRMADATA_RECORD_SPEC = RecordSpecification.createSpecification("VitaminFirmadata", "firmaID", 
            field("firmaID", 6, false).numerical(),
            field("langtFirmaMaerke", 32, false),
		    field("kortFirmaMaerke", 20, false),
		    field("parallelimportKode", 2, false));
    
    public static final RecordSpecification UDGAAEDENAVNE_RECORD_SPEC = RecordSpecification.createSpecification("VitaminUdgaaedeNavne", "Id",
            // Id is calculatedField after records is parse so make sure it is set to ignored
            field("Id", 40, false).calculated(),
            field("drugID", 11, false).numerical(),
            field("aendringsDato", 8, false),
            field("tidligereNavn", 50, false));

    public static final RecordSpecification INDHOLDSSTOFFER_RECORD_SPEC = RecordSpecification.createSpecification("VitaminIndholdsstoffer", "Id",
            // Id is calculatedField after records is parse so make sure it is set to ignored
            field("Id", 40, false).calculated(),
            field("drugID", 11, false).numerical(),
		    field("tom", 6, false).ignored(),
		    field("stofklasse", 100, false),
            field("substansgruppe", 100, false),
            field("substans", 150, false));

}
