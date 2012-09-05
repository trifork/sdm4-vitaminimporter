package dk.nsi.sdm4.vitamin.recordspecs;

import dk.nsi.sdm4.core.persistence.recordpersister.RecordSpecification;

import static dk.nsi.sdm4.core.persistence.recordpersister.FieldSpecification.field;

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
            field("styrkeNumerisk", 10).numerical(),
            field("styrkeEnhed", 3),
            field("mtIndehaverKode", 6).numerical(),
            field("repraesentantDistributoerKode", 6).numerical(),
            field("atcKode", 8),
            field("administrationsvejKode", 8),
            field("trafikadvarsel", 1),
            field("substitution", 1),
		    field("blank", 3).doNotPersist(),
            field("substitutionsgruppe", 4),
            field("dosisdispensering", 1),
		    field("blank", 8).doNotPersist(),
		    field("karantaeneDato", 8),
            field("sletningsstatus", 0));
    
    public static final RecordSpecification FIRMADATA_RECORD_SPEC = RecordSpecification.createSpecification("VitaminFirmadata", "firmaID", 
            field("firmaID", 0),
            field("kortFirmaMaerke", 0),
            field("langtFirmaMaerke", 0),
            field("parallelimportKode", 0));
    
    public static final RecordSpecification UDGAAEDENAVNE_RECORD_SPEC = RecordSpecification.createSpecification("VitaminUdgaaedeNavne", "drugID", 
            field("drugID", 0),
            field("aendringsDato", 0),
            field("tidligereNavn", 0));
    
    public static final RecordSpecification INDHOLDSSTOFFER_RECORD_SPEC = RecordSpecification.createSpecification("VitaminIndholdsstoffer", "drugID", 
            field("drugID", 0),
            field("stofKlasse", 0),
            field("substansgruppe", 0),
            field("substans", 0));

}
