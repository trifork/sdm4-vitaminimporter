/**
 * The contents of this file are subject to the Mozilla Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Contributor(s): Contributors are attributed in the source code
 * where applicable.
 *
 * The Original Code is "Stamdata".
 *
 * The Initial Developer of the Original Code is Trifork Public A/S.
 *
 * Portions created for the Original Code are Copyright 2011,
 * Lægemiddelstyrelsen. All Rights Reserved.
 *
 * Portions created for the FMKi Project are Copyright 2011,
 * National Board of e-Health (NSI). All Rights Reserved.
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
