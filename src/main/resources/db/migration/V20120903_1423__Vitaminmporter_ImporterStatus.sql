 CREATE TABLE IF NOT EXISTS vitaminimporterImportStatus (
    Id BIGINT(15) AUTO_INCREMENT NOT NULL PRIMARY KEY,
    StartTime DATETIME NOT NULL,
    EndTime DATETIME,
    Outcome VARCHAR(20),

    INDEX (StartTime)
) ENGINE=InnoDB COLLATE=utf8_bin;