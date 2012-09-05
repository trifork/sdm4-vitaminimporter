CREATE TABLE VitaminGrunddata (
    VitaminGrunddataPID BIGINT(15) AUTO_INCREMENT NOT NULL PRIMARY KEY,

    DrugID BIGINT(12),
    Varetype VARCHAR(2),
    Varedeltype VARCHAR(2),
    AlfabetSekvensplads VARCHAR(9),
    SpecNummer VARCHAR(5),
    Navn VARCHAR(30),
    FormKode VARCHAR(7),
    FormTekst VARCHAR(20),
    KodeYderligereFormOplysninger VARCHAR(7),
    StyrkeTekst VARCHAR(20),
    StyrkeNumerisk DECIMAL(10,3),
    StyrkeEnhed VARCHAR(3),
    MTIndehaverKode BIGINT(6),
    RepraesentantDistributoerKode BIGINT(6),
    ATCKode VARCHAR(6),
    AdministrationsvejKode VARCHAR(8),
    Trafikadvarsel BOOLEAN,
    Substitution VARCHAR(1),
    Substitutionsgruppe VARCHAR(4),
    Dosisdispensering VARCHAR(1),
    KarantaeneDato DATE,
    Sletningsstatus VARCHAR(20),

    CreatedDate DATETIME NOT NULL,
    ModifiedDate DATETIME NOT NULL,
    ValidFrom DATETIME NOT NULL,
    ValidTo DATETIME NOT NULL,

    INDEX (DrugId, ValidTo, ValidFrom)
);

CREATE TABLE VitaminFirmadata (
    VitaminFirmadataPID BIGINT(15) AUTO_INCREMENT NOT NULL PRIMARY KEY,

    FirmaID BIGINT(6),
    KortFirmaMaerke VARCHAR(20),
    LangtFirmaMaerke VARCHAR(32),
    ParallelimportKode VARCHAR(2),

    CreatedDate DATETIME NOT NULL,
    ModifiedDate DATETIME NOT NULL,
    ValidFrom DATETIME NOT NULL,
    ValidTo DATETIME NOT NULL,

    INDEX (FirmaId, ValidTo, ValidFrom)
);

CREATE TABLE VitaminUdgaaedeNavne (
    VitaminUdgaedeNavnePID BIGINT(15) AUTO_INCREMENT NOT NULL PRIMARY KEY,

    DrugID BIGINT(12),
    AendringsDato DATE,
    TidligereNavn VARCHAR(50),

    CreatedDate DATETIME NOT NULL,
    ModifiedDate DATETIME NOT NULL,
    ValidFrom DATETIME NOT NULL,
    ValidTo DATETIME NOT NULL,

    INDEX (DrugId, ValidTo, ValidFrom)
);

CREATE TABLE VitaminIndholdsstoffer (
    VitaminIndholdsstofferPID BIGINT(15) AUTO_INCREMENT NOT NULL PRIMARY KEY,

    DrugID BIGINT(12),
    StofKlasse VARCHAR(100),
    Substansgruppe VARCHAR(100),
    Substans VARCHAR(150),

    CreatedDate DATETIME NOT NULL,
    ModifiedDate DATETIME NOT NULL,
    ValidFrom DATETIME NOT NULL,
    ValidTo DATETIME NOT NULL,

    INDEX (DrugId, ValidTo, ValidFrom)
);