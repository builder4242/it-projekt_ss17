-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema projektmarktplatzdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `projektmarktplatzdb` ;

-- -----------------------------------------------------
-- Schema projektmarktplatzdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projektmarktplatzdb` DEFAULT CHARACTER SET utf8 ;
USE `projektmarktplatzdb` ;

-- -----------------------------------------------------
-- Table `projektmarktplatzdb`.`projektmarktplatz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projektmarktplatzdb`.`projektmarktplatz` ;

CREATE TABLE IF NOT EXISTS `projektmarktplatzdb`.`projektmarktplatz` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Bezeichnung` VARCHAR(255) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektmarktplatzdb`.`partnerprofil`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projektmarktplatzdb`.`partnerprofil` ;

CREATE TABLE IF NOT EXISTS `projektmarktplatzdb`.`partnerprofil` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Erstelldatum` DATE NULL,
  `Aenderungsdatum` DATE NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektmarktplatzdb`.`organisationseinheit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projektmarktplatzdb`.`organisationseinheit` ;

CREATE TABLE IF NOT EXISTS `projektmarktplatzdb`.`organisationseinheit` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Typ` CHAR(1) NULL,
  `Name` VARCHAR(255) NULL,
  `Vorname` VARCHAR(255) NULL,
  `Email` VARCHAR(255) NULL,
  `Strasse` VARCHAR(255) NULL,
  `PLZ` INT NULL,
  `Ort` VARCHAR(255) NULL,
  `Tel` VARCHAR(255) NULL,
  `GoogleID` VARCHAR(255) NULL,
  `Partnerprofil_ID` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Organisationseinheit_Partnerprofil1_idx` (`Partnerprofil_ID` ASC),
  CONSTRAINT `fk_Organisationseinheit_Partnerprofil1`
    FOREIGN KEY (`Partnerprofil_ID`)
    REFERENCES `projektmarktplatzdb`.`partnerprofil` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektmarktplatzdb`.`projekt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projektmarktplatzdb`.`projekt` ;

CREATE TABLE IF NOT EXISTS `projektmarktplatzdb`.`projekt` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(255) NULL,
  `Startdatum` DATE NULL,
  `Enddatum` DATE NULL,
  `Beschreibung` TEXT NULL,
  `Projektmarktplatz_ID` INT NOT NULL,
  `Projektbetreiber_ID` INT NOT NULL,
  `Projektleiter_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Projekt_Projektmarktplatz_idx` (`Projektmarktplatz_ID` ASC),
  INDEX `fk_Projekt_Organisationseinheit1_idx` (`Projektbetreiber_ID` ASC),
  INDEX `fk_Projekt_Organisationseinheit2_idx` (`Projektleiter_ID` ASC),
  CONSTRAINT `fk_Projekt_Projektmarktplatz`
    FOREIGN KEY (`Projektmarktplatz_ID`)
    REFERENCES `projektmarktplatzdb`.`projektmarktplatz` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Projekt_Organisationseinheit1`
    FOREIGN KEY (`Projektbetreiber_ID`)
    REFERENCES `projektmarktplatzdb`.`organisationseinheit` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Projekt_Organisationseinheit2`
    FOREIGN KEY (`Projektleiter_ID`)
    REFERENCES `projektmarktplatzdb`.`organisationseinheit` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektmarktplatzdb`.`beteiligung`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projektmarktplatzdb`.`beteiligung` ;

CREATE TABLE IF NOT EXISTS `projektmarktplatzdb`.`beteiligung` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Personentage` INT NULL,
  `Startdatum` DATE NULL,
  `Enddatum` DATE NULL,
  `Projekt_ID` INT NOT NULL,
  `Organisationseinheit_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Beteiligung_Projekt1_idx` (`Projekt_ID` ASC),
  INDEX `fk_Beteiligung_Organisationseinheit1_idx` (`Organisationseinheit_ID` ASC),
  CONSTRAINT `fk_Beteiligung_Projekt1`
    FOREIGN KEY (`Projekt_ID`)
    REFERENCES `projektmarktplatzdb`.`projekt` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Beteiligung_Organisationseinheit1`
    FOREIGN KEY (`Organisationseinheit_ID`)
    REFERENCES `projektmarktplatzdb`.`organisationseinheit` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektmarktplatzdb`.`ausschreibung`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projektmarktplatzdb`.`ausschreibung` ;

CREATE TABLE IF NOT EXISTS `projektmarktplatzdb`.`ausschreibung` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Bezeichnung` VARCHAR(255) NULL,
  `Auschschreibungstext` TEXT NULL,
  `Bewerbungsfrist` DATE NULL,
  `Projekt_ID` INT NOT NULL,
  `Partnerprofil_ID` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Ausschreibung_Projekt1_idx` (`Projekt_ID` ASC),
  INDEX `fk_Ausschreibung_Partnerprofil1_idx` (`Partnerprofil_ID` ASC),
  CONSTRAINT `fk_Ausschreibung_Projekt1`
    FOREIGN KEY (`Projekt_ID`)
    REFERENCES `projektmarktplatzdb`.`projekt` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ausschreibung_Partnerprofil1`
    FOREIGN KEY (`Partnerprofil_ID`)
    REFERENCES `projektmarktplatzdb`.`partnerprofil` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektmarktplatzdb`.`bewerbung`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projektmarktplatzdb`.`bewerbung` ;

CREATE TABLE IF NOT EXISTS `projektmarktplatzdb`.`bewerbung` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Erstelldatum` DATE NULL,
  `Bewerbungstext` TEXT NULL,
  `Ausschreibung_ID` INT NOT NULL,
  `Organisationseinheit_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Bewerbung_Ausschreibung1_idx` (`Ausschreibung_ID` ASC),
  INDEX `fk_Bewerbung_Organisationseinheit1_idx` (`Organisationseinheit_ID` ASC),
  CONSTRAINT `fk_Bewerbung_Ausschreibung1`
    FOREIGN KEY (`Ausschreibung_ID`)
    REFERENCES `projektmarktplatzdb`.`ausschreibung` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Bewerbung_Organisationseinheit1`
    FOREIGN KEY (`Organisationseinheit_ID`)
    REFERENCES `projektmarktplatzdb`.`organisationseinheit` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektmarktplatzdb`.`bewertung`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projektmarktplatzdb`.`bewertung` ;

CREATE TABLE IF NOT EXISTS `projektmarktplatzdb`.`bewertung` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Wert` DECIMAL NULL,
  `Stellungnahme` TEXT NULL,
  `Erstelldatumm` DATE NULL,
  `Bewerbung_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Bewertung_Bewerbung1_idx` (`Bewerbung_ID` ASC),
  CONSTRAINT `fk_Bewertung_Bewerbung1`
    FOREIGN KEY (`Bewerbung_ID`)
    REFERENCES `projektmarktplatzdb`.`bewerbung` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektmarktplatzdb`.`eigenschaft`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projektmarktplatzdb`.`eigenschaft` ;

CREATE TABLE IF NOT EXISTS `projektmarktplatzdb`.`eigenschaft` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(255) NULL,
  `Wert` VARCHAR(255) NULL,
  `Partnerprofil_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Eigenschaft_Partnerprofil1_idx` (`Partnerprofil_ID` ASC),
  CONSTRAINT `fk_Eigenschaft_Partnerprofil1`
    FOREIGN KEY (`Partnerprofil_ID`)
    REFERENCES `projektmarktplatzdb`.`partnerprofil` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektmarktplatzdb`.`projektmarktplatz_has_organisationseinheit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projektmarktplatzdb`.`projektmarktplatz_has_organisationseinheit` ;

CREATE TABLE IF NOT EXISTS `projektmarktplatzdb`.`projektmarktplatz_has_organisationseinheit` (
  `Projektmarktplatz_ID` INT NOT NULL,
  `Organisationseinheit_ID` INT NOT NULL,
  PRIMARY KEY (`Projektmarktplatz_ID`, `Organisationseinheit_ID`),
  INDEX `fk_Projektmarktplatz_has_Organisationseinheit_Organisations_idx` (`Organisationseinheit_ID` ASC),
  INDEX `fk_Projektmarktplatz_has_Organisationseinheit_Projektmarktp_idx` (`Projektmarktplatz_ID` ASC),
  CONSTRAINT `fk_Projektmarktplatz_has_Organisationseinheit_Projektmarktpla1`
    FOREIGN KEY (`Projektmarktplatz_ID`)
    REFERENCES `projektmarktplatzdb`.`projektmarktplatz` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Projektmarktplatz_has_Organisationseinheit_Organisationsei1`
    FOREIGN KEY (`Organisationseinheit_ID`)
    REFERENCES `projektmarktplatzdb`.`organisationseinheit` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
