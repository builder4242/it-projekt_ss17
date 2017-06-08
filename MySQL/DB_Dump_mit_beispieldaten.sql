-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Erstellungszeit: 07. Jun 2017 um 16:32
-- Server-Version: 10.1.21-MariaDB
-- PHP-Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `projektmarktplatzdb`
--
CREATE DATABASE IF NOT EXISTS `projektmarktplatzdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `projektmarktplatzdb`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ausschreibung`
--

CREATE TABLE `ausschreibung` (
  `ID` int(11) NOT NULL,
  `Bezeichnung` varchar(255) DEFAULT NULL,
  `Ausschreibungstext` text,
  `Bewerbungsfrist` date DEFAULT NULL,
  `Projekt_ID` int(11) NOT NULL,
  `Partnerprofil_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `ausschreibung`
--

INSERT INTO `ausschreibung` (`ID`, `Bezeichnung`, `Ausschreibungstext`, `Bewerbungsfrist`, `Projekt_ID`, `Partnerprofil_ID`) VALUES
(1, 'Facility on duty director', 'Wir brauchen ein Hausmeister', '2017-07-21', 2, 3),
(2, 'Müllmann', 'Egal. Hauptsache tragen', '2017-07-30', 1, 3),
(3, 'CEO', 'Bewerber benötigt folgende Qualifikation:\r\n- Gut aussehen\r\n- große Klappe\r\n- möglichst fachlich keine Ahnung', '2017-08-08', 1, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `beteiligung`
--

CREATE TABLE `beteiligung` (
  `ID` int(11) NOT NULL,
  `Personentage` int(11) DEFAULT NULL,
  `Startdatum` date DEFAULT NULL,
  `Enddatum` date DEFAULT NULL,
  `Projekt_ID` int(11) NOT NULL,
  `Organisationseinheit_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `beteiligung`
--

INSERT INTO `beteiligung` (`ID`, `Personentage`, `Startdatum`, `Enddatum`, `Projekt_ID`, `Organisationseinheit_ID`) VALUES
(1, 10, '2017-06-01', '2017-07-20', 1, 2),
(2, 200, '2017-02-01', '2017-08-30', 2, 4);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bewerbung`
--

CREATE TABLE `bewerbung` (
  `ID` int(11) NOT NULL,
  `Erstelldatum` date DEFAULT NULL,
  `Bewerbungstext` text,
  `Ausschreibung_ID` int(11) NOT NULL,
  `Organisationseinheit_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `bewerbung`
--

INSERT INTO `bewerbung` (`ID`, `Erstelldatum`, `Bewerbungstext`, `Ausschreibung_ID`, `Organisationseinheit_ID`) VALUES
(1, '2017-03-06', 'Nehmt mich. Ich bin der Geilste.', 1, 2),
(2, '2017-06-06', 'Ich kann nichts. Brauch aber Geld.', 2, 3);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bewertung`
--

CREATE TABLE `bewertung` (
  `ID` int(11) NOT NULL,
  `Wert` decimal(10,0) DEFAULT NULL,
  `Stellungnahme` text,
  `Erstelldatum` date DEFAULT NULL,
  `Bewerbung_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `bewertung`
--

INSERT INTO `bewertung` (`ID`, `Wert`, `Stellungnahme`, `Erstelldatum`, `Bewerbung_ID`) VALUES
(1, '1', 'Der kann wirklich nichts', '2017-06-07', 2),
(2, '10', 'Alter. Wie geil der ist.', '2017-06-06', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `eigenschaft`
--

CREATE TABLE `eigenschaft` (
  `ID` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Wert` varchar(255) DEFAULT NULL,
  `Partnerprofil_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `eigenschaft`
--

INSERT INTO `eigenschaft` (`ID`, `Name`, `Wert`, `Partnerprofil_ID`) VALUES
(1, 'hindert', '10', 1),
(2, 'geil', '5', 1),
(3, 'eloquent', '1', 1),
(4, 'groß', '6', 2),
(5, 'klein', '7', 2),
(6, 'cool', '3', 3);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `organisationseinheit`
--

CREATE TABLE `organisationseinheit` (
  `ID` int(11) NOT NULL,
  `Typ` char(1) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Vorname` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Strasse` varchar(255) DEFAULT NULL,
  `PLZ` int(11) DEFAULT NULL,
  `Ort` varchar(255) DEFAULT NULL,
  `Tel` varchar(255) DEFAULT NULL,
  `GoogleID` varchar(255) DEFAULT NULL,
  `Partnerprofil_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `organisationseinheit`
--

INSERT INTO `organisationseinheit` (`ID`, `Typ`, `Name`, `Vorname`, `Email`, `Strasse`, `PLZ`, `Ort`, `Tel`, `GoogleID`, `Partnerprofil_ID`) VALUES
(1, 'P', 'Fleps', 'Guenther', 'gunther@testfleps.de', 'Teststr', 12345, 'Stugart', '0711000000', 'skjhf8ez94hoh439ownc', 1),
(2, 'U', 'Capgemini', 'kein', 'info@cage.com', 'Beispielweg', 70597, 'Stuttgart', '071100110011', 'klsnjv98z793rhgobwv', 2),
(3, 'T', 'GoodQualityInLastSecond', 'kein', 'info@goodquality.de', 'Markplatz 5', 10219, 'Berlin', '0190666666', 'oie8tu49hgnovrhe', 3),
(4, 'P', 'Bulat', 'Tugba', 'tugba@bulat.de', 'Dahintenweg 7', 70794, 'AmADW', '0711984738', 'iurehf934fh3ovrv', 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `partnerprofil`
--

CREATE TABLE `partnerprofil` (
  `ID` int(11) NOT NULL,
  `Erstelldatum` date DEFAULT NULL,
  `Aenderungsdatum` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `partnerprofil`
--

INSERT INTO `partnerprofil` (`ID`, `Erstelldatum`, `Aenderungsdatum`) VALUES
(1, '2017-06-01', '2017-06-02'),
(2, '2017-04-03', '2017-04-03'),
(3, '2017-06-02', '2017-06-03');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `projekt`
--

CREATE TABLE `projekt` (
  `ID` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Startdatum` date DEFAULT NULL,
  `Enddatum` date DEFAULT NULL,
  `Beschreibung` text,
  `Projektmarktplatz_ID` int(11) NOT NULL,
  `Projektbetreiber_ID` int(11) NOT NULL,
  `Projektleiter_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `projekt`
--

INSERT INTO `projekt` (`ID`, `Name`, `Startdatum`, `Enddatum`, `Beschreibung`, `Projektmarktplatz_ID`, `Projektbetreiber_ID`, `Projektleiter_ID`) VALUES
(1, 'ITProjekt', '2017-02-13', '2017-06-01', 'In diesem Projekt geht es um IT. Deswegen ist der Name IT-Projekt', 2, 4, 1),
(2, 'NochEinProjekt', '2017-06-01', '2017-06-02', 'Das ist ein kurzes Projekt', 2, 3, 4);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `projektmarktplatz`
--

CREATE TABLE `projektmarktplatz` (
  `ID` int(11) NOT NULL,
  `Bezeichnung` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `projektmarktplatz`
--

INSERT INTO `projektmarktplatz` (`ID`, `Bezeichnung`) VALUES
(1, 'Daimler'),
(2, 'BMW'),
(3, 'BurgerKing'),
(4, 'McDonalds');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `projektmarktplatz_has_organisationseinheit`
--

CREATE TABLE `projektmarktplatz_has_organisationseinheit` (
  `Projektmarktplatz_ID` int(11) NOT NULL,
  `Organisationseinheit_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `projektmarktplatz_has_organisationseinheit`
--

INSERT INTO `projektmarktplatz_has_organisationseinheit` (`Projektmarktplatz_ID`, `Organisationseinheit_ID`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `ausschreibung`
--
ALTER TABLE `ausschreibung`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_Ausschreibung_Projekt1_idx` (`Projekt_ID`),
  ADD KEY `fk_Ausschreibung_Partnerprofil1_idx` (`Partnerprofil_ID`);

--
-- Indizes für die Tabelle `beteiligung`
--
ALTER TABLE `beteiligung`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_Beteiligung_Projekt1_idx` (`Projekt_ID`),
  ADD KEY `fk_Beteiligung_Organisationseinheit1_idx` (`Organisationseinheit_ID`);

--
-- Indizes für die Tabelle `bewerbung`
--
ALTER TABLE `bewerbung`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_Bewerbung_Ausschreibung1_idx` (`Ausschreibung_ID`),
  ADD KEY `fk_Bewerbung_Organisationseinheit1_idx` (`Organisationseinheit_ID`);

--
-- Indizes für die Tabelle `bewertung`
--
ALTER TABLE `bewertung`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_Bewertung_Bewerbung1_idx` (`Bewerbung_ID`);

--
-- Indizes für die Tabelle `eigenschaft`
--
ALTER TABLE `eigenschaft`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_Eigenschaft_Partnerprofil1_idx` (`Partnerprofil_ID`);

--
-- Indizes für die Tabelle `organisationseinheit`
--
ALTER TABLE `organisationseinheit`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_Organisationseinheit_Partnerprofil1_idx` (`Partnerprofil_ID`);

--
-- Indizes für die Tabelle `partnerprofil`
--
ALTER TABLE `partnerprofil`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `projekt`
--
ALTER TABLE `projekt`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_Projekt_Projektmarktplatz_idx` (`Projektmarktplatz_ID`),
  ADD KEY `fk_Projekt_Organisationseinheit1_idx` (`Projektbetreiber_ID`),
  ADD KEY `fk_Projekt_Organisationseinheit2_idx` (`Projektleiter_ID`);

--
-- Indizes für die Tabelle `projektmarktplatz`
--
ALTER TABLE `projektmarktplatz`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `projektmarktplatz_has_organisationseinheit`
--
ALTER TABLE `projektmarktplatz_has_organisationseinheit`
  ADD PRIMARY KEY (`Projektmarktplatz_ID`,`Organisationseinheit_ID`),
  ADD KEY `fk_Projektmarktplatz_has_Organisationseinheit_Organisations_idx` (`Organisationseinheit_ID`),
  ADD KEY `fk_Projektmarktplatz_has_Organisationseinheit_Projektmarktp_idx` (`Projektmarktplatz_ID`);

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `ausschreibung`
--
ALTER TABLE `ausschreibung`
  ADD CONSTRAINT `fk_Ausschreibung_Partnerprofil1` FOREIGN KEY (`Partnerprofil_ID`) REFERENCES `partnerprofil` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Ausschreibung_Projekt1` FOREIGN KEY (`Projekt_ID`) REFERENCES `projekt` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `beteiligung`
--
ALTER TABLE `beteiligung`
  ADD CONSTRAINT `fk_Beteiligung_Organisationseinheit1` FOREIGN KEY (`Organisationseinheit_ID`) REFERENCES `organisationseinheit` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Beteiligung_Projekt1` FOREIGN KEY (`Projekt_ID`) REFERENCES `projekt` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `bewerbung`
--
ALTER TABLE `bewerbung`
  ADD CONSTRAINT `fk_Bewerbung_Ausschreibung1` FOREIGN KEY (`Ausschreibung_ID`) REFERENCES `ausschreibung` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Bewerbung_Organisationseinheit1` FOREIGN KEY (`Organisationseinheit_ID`) REFERENCES `organisationseinheit` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `bewertung`
--
ALTER TABLE `bewertung`
  ADD CONSTRAINT `fk_Bewertung_Bewerbung1` FOREIGN KEY (`Bewerbung_ID`) REFERENCES `bewerbung` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `eigenschaft`
--
ALTER TABLE `eigenschaft`
  ADD CONSTRAINT `fk_Eigenschaft_Partnerprofil1` FOREIGN KEY (`Partnerprofil_ID`) REFERENCES `partnerprofil` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `organisationseinheit`
--
ALTER TABLE `organisationseinheit`
  ADD CONSTRAINT `fk_Organisationseinheit_Partnerprofil1` FOREIGN KEY (`Partnerprofil_ID`) REFERENCES `partnerprofil` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `projekt`
--
ALTER TABLE `projekt`
  ADD CONSTRAINT `fk_Projekt_Organisationseinheit1` FOREIGN KEY (`Projektbetreiber_ID`) REFERENCES `organisationseinheit` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Projekt_Organisationseinheit2` FOREIGN KEY (`Projektleiter_ID`) REFERENCES `organisationseinheit` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Projekt_Projektmarktplatz` FOREIGN KEY (`Projektmarktplatz_ID`) REFERENCES `projektmarktplatz` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `projektmarktplatz_has_organisationseinheit`
--
ALTER TABLE `projektmarktplatz_has_organisationseinheit`
  ADD CONSTRAINT `fk_Projektmarktplatz_has_Organisationseinheit_Organisationsei1` FOREIGN KEY (`Organisationseinheit_ID`) REFERENCES `organisationseinheit` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Projektmarktplatz_has_Organisationseinheit_Projektmarktpla1` FOREIGN KEY (`Projektmarktplatz_ID`) REFERENCES `projektmarktplatz` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
