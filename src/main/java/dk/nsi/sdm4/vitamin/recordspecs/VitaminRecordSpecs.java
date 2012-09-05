package dk.nsi.sdm4.vitamin.recordspecs;

import dk.nsi.sdm4.core.persistence.recordpersister.RecordSpecification;
import static dk.nsi.sdm4.core.persistence.recordpersister.RecordSpecification.field;

public class VitaminRecordSpecs {

    public static final RecordSpecification GRUNDDATA_RECORD_SPEC = RecordSpecification.createSpecification("VitaminGrunddata", "drugID", 
            field("drugID", 0),
            field("varetype", 0),
            field("varedeltype", 0),
            field("alfabetSekvensplads", 0),
            field("specNummer", 0),
            field("formKode", 0),
            field("formTekst", 0),
            field("kodeYderligereFormOplysninger", 0),
            field("styrkeTekst", 0),
            field("styrkeNumerisk", 0),
            field("styrkeEnhed", 0),
            field("mtIndehaverKode", 0),
            field("repraesentantDistributoerKode", 0),
            field("atcKode", 0),
            field("administrationsvejKode", 0),
            field("trafikadvarsel", 0),
            field("substitution", 0),
            field("substitutionsgruppe", 0),
            field("dosisdispensering", 0),
            field("darantaenedato", 0),
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
