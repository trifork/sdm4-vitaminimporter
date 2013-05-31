
-- -----------------------------------------------------
-- Someone has to create the SKRS tables first time
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SKRSViewMapping` (
  `idSKRSViewMapping` BIGINT(15) NOT NULL AUTO_INCREMENT ,
  `register` VARCHAR(255) NOT NULL ,
  `datatype` VARCHAR(255) NOT NULL ,
  `version` INT NOT NULL ,
  `tableName` VARCHAR(255) NOT NULL ,
  `createdDate` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`idSKRSViewMapping`) ,
  INDEX `idx` (`register` ASC, `datatype` ASC, `version` ASC) ,
  UNIQUE INDEX `unique` (`register` ASC, `datatype` ASC, `version` ASC) )
  ENGINE = InnoDB;
CREATE  TABLE IF NOT EXISTS `SKRSColumns` (
  `idSKRSColumns` BIGINT(15) NOT NULL AUTO_INCREMENT ,
  `viewMap` BIGINT(15) NOT NULL ,
  `isPID` TINYINT NOT NULL ,
  `tableColumnName` VARCHAR(255) NOT NULL ,
  `feedColumnName` VARCHAR(255) NULL ,
  `feedPosition` INT NOT NULL ,
  `dataType` INT NOT NULL ,
  `maxLength` INT NULL ,
  PRIMARY KEY (`idSKRSColumns`) ,
  INDEX `viewMap_idx` (`viewMap` ASC) ,
  UNIQUE INDEX `viewColumn` (`tableColumnName` ASC, `viewMap` ASC) ,
  CONSTRAINT `viewMap`
  FOREIGN KEY (`viewMap` )
  REFERENCES `SKRSViewMapping` (`idSKRSViewMapping` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

-- ---------------------------------------------------------------------------------------------------------------------
-- Vitamin

INSERT IGNORE INTO SKRSViewMapping (register, datatype, version, tableName, createdDate) VALUES ('vitamin', 'grunddata', 1, 'VitaminGrunddata', NOW());
INSERT IGNORE INTO SKRSColumns (viewMap, isPID, tableColumnName, feedColumnName, feedPosition, dataType, maxLength) VALUES
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 1, 'PID',                                                     NULL, 0, -5, NULL),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'drugID',                                              'drugID', 1, -5, NULL),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'varetype',                                          'varetype', 2, 12, 2),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'varedeltype',                                    'varedeltype', 3, 12, 2),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'alfabetSekvensplads',                    'alfabetSekvensplads', 4, 12, 9),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'specNummer',                                      'specNummer', 5, 12, 5),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'navn',                                                  'navn', 6, 12, 30),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'formTekst',                                        'formTekst', 7, 12, 20),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'formKode',                                          'formKode', 8, 12, 7),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'kodeYderligereFormOplysninger','kodeYderligereFormOplysninger', 9, 12, 7),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'styrkeTekst',                                    'styrkeTekst',10, 12, 20),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'styrkeNumerisk',                              'styrkeNumerisk',11,  3, null),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'styrkeEnhed',                                    'styrkeEnhed',12, 12, 3),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'mtIndehaverKode',                            'mtIndehaverKode',13, -5, null),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'repraesentantDistributoerKode','repraesentantDistributoerKode',14, -5, null),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'atcKode',                                            'atcKode',15, 12, 8),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'administrationsvejKode',              'administrationsvejKode',16, 12, 8),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'trafikadvarsel',                              'trafikadvarsel',17, 12, 1),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'substitution',                                  'substitution',18, 12, 1),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'substitutionsgruppe',                    'substitutionsgruppe',19, 12, 4),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'dosisdispensering',                        'dosisdispensering',20, 12, 1),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'karantaeneDato',                              'karantaeneDato',21, 12, 8),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'ModifiedDate',                                  'ModifiedDate',22, 93, 12),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'ValidFrom',                                        'ValidFrom',23, 93, 12),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='grunddata' AND version=1), 0, 'ValidTo',                                            'ValidTo',24, 93, 12);

INSERT IGNORE INTO SKRSViewMapping (register, datatype, version, tableName, createdDate) VALUES ('vitamin', 'firmadata', 1, 'VitaminFirmadata', NOW());
INSERT IGNORE INTO SKRSColumns (viewMap, isPID, tableColumnName, feedColumnName, feedPosition, dataType, maxLength) VALUES
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='firmadata' AND version=1), 1, 'PID',                                NULL, 0, -5, NULL),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='firmadata' AND version=1), 0, 'FirmaID',                       'firmaID', 1, -5, NULL),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='firmadata' AND version=1), 0, 'KortFirmaMaerke',       'kortFirmaMaerke', 2, 12, 20),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='firmadata' AND version=1), 0, 'LangtFirmaMaerke',     'langtFirmaMaerke', 3, 12, 32),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='firmadata' AND version=1), 0, 'ParallelimportKode', 'parallelimportKode', 4, 12, 2),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='firmadata' AND version=1), 0, 'ModifiedDate',             'ModifiedDate', 5, 93, 12),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='firmadata' AND version=1), 0, 'ValidFrom',                   'ValidFrom', 6, 93, 12),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='firmadata' AND version=1), 0, 'ValidTo',                       'ValidTo', 7, 93, 12);

INSERT IGNORE INTO SKRSViewMapping (register, datatype, version, tableName, createdDate) VALUES ('vitamin', 'udgaaedenavne', 1, 'VitaminUdgaaedeNavne', NOW());
INSERT IGNORE INTO SKRSColumns (viewMap, isPID, tableColumnName, feedColumnName, feedPosition, dataType, maxLength) VALUES
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='udgaaedenavne' AND version=1), 1, 'PID',                      NULL, 0, -5, NULL),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='udgaaedenavne' AND version=1), 0, 'Id',                       'Id', 1, 12, 40),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='udgaaedenavne' AND version=1), 0, 'DrugID',               'drugID', 2, -5, null),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='udgaaedenavne' AND version=1), 0, 'AendringsDato', 'aendringsDato', 3, 12, 8),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='udgaaedenavne' AND version=1), 0, 'TidligereNavn', 'tidligereNavn', 4, 12, 50),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='udgaaedenavne' AND version=1), 0, 'ModifiedDate',   'ModifiedDate', 5, 93, 12),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='udgaaedenavne' AND version=1), 0, 'ValidFrom',         'ValidFrom', 6, 93, 12),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='udgaaedenavne' AND version=1), 0, 'ValidTo',             'ValidTo', 7, 93, 12);

INSERT IGNORE INTO SKRSViewMapping (register, datatype, version, tableName, createdDate) VALUES ('vitamin', 'indholdsstoffer', 1, 'VitaminIndholdsstoffer', NOW());
INSERT IGNORE INTO SKRSColumns (viewMap, isPID, tableColumnName, feedColumnName, feedPosition, dataType, maxLength) VALUES
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='indholdsstoffer' AND version=1), 1, 'PID',                        NULL, 0, -5, NULL),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='indholdsstoffer' AND version=1), 0, 'Id',                         'Id', 1, 12, 40),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='indholdsstoffer' AND version=1), 0, 'DrugID',                 'drugId', 2, -5, null),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='indholdsstoffer' AND version=1), 0, 'StofKlasse',         'stofKlasse', 3, 12, 100),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='indholdsstoffer' AND version=1), 0, 'Substansgruppe', 'substansgruppe', 4, 12, 100),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='indholdsstoffer' AND version=1), 0, 'Substans',             'substans', 5, 12, 150),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='indholdsstoffer' AND version=1), 0, 'ModifiedDate',     'ModifiedDate', 6, 93, 12),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='indholdsstoffer' AND version=1), 0, 'ValidFrom',           'ValidFrom', 7, 93, 12),
((SELECT idSKRSViewMapping FROM SKRSViewMapping WHERE register='vitamin' AND datatype='indholdsstoffer' AND version=1), 0, 'ValidTo',               'ValidTo', 8, 93, 12);
