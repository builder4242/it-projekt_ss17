-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 28. Jun 2017 um 23:42
-- Server-Version: 10.1.16-MariaDB
-- PHP-Version: 5.6.24

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
(1, 'Praktikant im Bereich Softwareentwicklung (ab Oktober 2017)', 'Anforderungen:\r\n- Student (m/w) im Bereich (technische) Informatik, Softwareentwicklung oder einer vergleichbaren Studienrichtung mit dem Schwerpunkt Softwareentwicklung\r\n- Gute Kenntnisse in der Programmierung (Java)\r\n- Grundkenntnisse in Datenbanken und Netzwerken\r\n- Sicherer Umgang mit den MS Office-Programmen\r\n- Sehr gute Deutsch- und Englischkenntnisse in Wort und Schrift\r\n- Schnelle Auffassungsgabe, interkulturelle Kompetenz, Lernfähigkeit, Selbstständigkeit, Kommunikations- und Teamfähigkeit sowie analytisches und ganzheitliches Denken', '2017-08-01', 1, 1),
(2, 'Projektleiter/in', 'Ihre Aufgaben:\n- Teile und Werkzeugkalkulation\n- Projektkonzeption\n- Angebotsbearbeitung\n- Terminverfolgung der Projekte\n- Kundensupport bezüglich kunststoff- und werkzeugtechnischer Fragen\n- Auswahl der Werkzeuglieferanten\n- Mitwirkung bei der Auswahl der Lieferanten für Kaufteile\n- Beschaffung von Prototypen (Teile und Werkzeuge)\n- Auswahl geeigneter Fertigungsverfahren in Zusammenarbeit mit den Fachbereichsleitern\n- Bestellung von Werkzeugen, Hilfsvorrichtungen, sowie sonstiger für die Durchführung des Projekts benötigten Hilfsmittel\n\n\nIhre Qualifikation:  - Entwicklungserfahrung im Automotive Bereich\n- Ausbildung als Kunststofftechniker\n- Gute Englischkenntnisse', '2016-12-31', 2, 9),
(3, 'Gärtner/Gärtnerin', 'Ihre Aufgaben:\r\n- Pflege und Reinhaltung der Außen- und Grünanlagen wie Rasen-, Wiesen-, Stauden- und Gehölzpflege und das Bedienen der zur Pflege gehörenden Geräte\r\n- Anfallende gärtnerische Tätigkeiten\r\n\r\nIhr Profil:\r\n- Abgeschlossene Berufsausbildung zum/zur Gärtner/Gärtnerin, vorzugsweise der Fachrichtung Garten- und Landschaftsbau\r\n- Fachwissen in den Bereichen Rasen- und Grünflächenpflege sowie Gartengestaltung\r\n- Teamfähigkeit, Durchsetzungskraft, Kommunikationsstärke\r\n- Führerschein in der Klasse B/III', '2017-02-28', 3, 7),
(4, 'Lektor (m/w)', 'Ihre Aufgaben:\r\n- Eigenständiges Projektmanagement von Titeln\r\n- Selbstsichere Zusammenarbeit mit internationalen Autoren\r\n- Klärung der Bild-, Text- und Künstlerrechte sowie Bildbeschaffung\r\n\r\nIhr Profil:\r\n- Berufserfahrung in einem international agierenden Verlag\r\n- Sehr gutes Deutsch und Englisch, fundierte Kenntnisse in einer weiteren Fremdsprache\r\n- Auge fürs Detail und Interesse an den publizierten Themen\r\n- Gute EDV-Kenntnisse (Acrobat, MS Office, InDesign Basics)\r\n- Selbstständiges, organisiertes und teamorientiertes Arbeiten mit positivem Fokus', '2017-04-30', 4, 6),
(5, 'VR Software Engineer', 'Dafür brennen Sie:\r\n- Ihr Herz schlägt für Augmented und Virtual Reality Anwendungen.\r\n- Sie begeistern sich für alle neuen Technologien rund um AR und VR und wollen Ihr Wissen ins Team und in spannende Kundenprojekte einbringen.\r\n- Sie lieben es, mit 3D Engines und VR-Hardware zu experimentieren und brennen dafür, ständig Neues zu lernen.\r\n\r\nDas bringen Sie mit:\r\n- Sie haben Ihr Studium der Informatik oder einer ähnlichen Fachrichtung gut abgeschlossen und bringen erste praktische Erfahrung mit Java, Swift oder Javascript mit.\r\n- Sie haben bereits mit VR-Hardware (Oculus Rift, Gear VR, Cardboard) und 3D-Engines (Unity 3D) gearbeitet oder bringen Begeisterung mit, dazuzulernen.\r\n- Sie sind neugierig und denken sich schnell in neue VR- und 3D-Technologien wie OpenGL ein.\r\n- Sie arbeiten gerne mit anderen Menschen zusammen, tauschen sich gerne aus und bringen Ihre Stärken und Ihr Wissen in Ihr Team ein.\r\n- Sie verfügen über sehr gute Deutsch- und Englischkenntnisse in Sprache und Schrift.', '2017-02-26', 5, 10);

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
(1, 100, '2017-03-01', '2017-08-31', 1, 2),
(2, 10, '2017-07-03', '2017-07-31', 2, 6),
(3, 50, '2017-05-02', '2017-08-31', 3, 8),
(4, 120, '2017-01-02', '2017-07-31', 4, 5),
(5, 30, '2017-08-01', '2017-10-31', 5, 3);

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
(1, '2017-07-20', 'Sehr geehrte Damen und Herren,\n\nmit großem Interesse bewerbe ich mich auf ein Praktikum im Bereich Softwareentwicklung.\nIch freue mich auf ein persönliches Gespräch.\n\nMit freundlichen Grüßen\nTanja Bieber', 1, 3),
(2, '2016-10-19', 'Sehr geehrte Damen und Herren,\n\nmit großem Interesse bewerbe ich mich als Projektleiter in Ihrem Unternehmen.\nIch freue mich auf ein persönliches Gespräch.\n\nMit freundlichen Grüßen\nHans Müller', 2, 1),
(3, '2017-02-20', 'Sehr geehrte Damen und Herren,\r\n\r\nmit großem Interesse bewerben wir uns als Team Grün als Gärtner.\r\nWir freuen uns auf ein persönliches Gespräch.\r\n\r\nMit freundlichen Grüßen\r\nTeam Grün', 3, 8),
(4, '2017-03-15', 'Sehr geehrte Damen und Herren,\r\n\r\nmit großem Interesse bewerben wir uns als GRIN Verlag GmbH als Lektor.\r\nWir freuen uns auf ein persönliches Gespräch.\r\n\r\nMit freundlichen Grüßen\r\nGRIN Verlag GmbH', 4, 5),
(5, '2017-01-07', 'Sehr geehrte Damen und Herren,\r\n\r\nmit großem Interesse bewerben wir uns als team:orange GmbH als VR Software Engineer.\r\nWir freuen uns auf ein persönliches Gespräch.\r\n\r\nMit freundlichen Grüßen\r\nteam:orange GmbH', 5, 9);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bewertung`
--

CREATE TABLE `bewertung` (
  `ID` int(11) NOT NULL,
  `Wert` float DEFAULT NULL,
  `Stellungnahme` text,
  `Erstelldatum` date DEFAULT NULL,
  `Bewerbung_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `bewertung`
--

INSERT INTO `bewertung` (`ID`, `Wert`, `Stellungnahme`, `Erstelldatum`, `Bewerbung_ID`) VALUES
(1, 0, 'Sehr geehrter Herr Müller,\r\n\r\nWir bedauern, Ihnen keine positive Rückmeldung geben zu können und wünschen Ihnen für Ihre persönliche und berufliche Zukunft alles Gute und viel Erfolg.\r\n\r\nMit freundlichen Grüßen\r\nHenriette Maier', '2016-11-02', 2),
(2, 0.5, 'Sehr geehrte Frau Bieber,\r\n\r\naufgrund der zahlreichen Bewerbungen kann die Bearbeitung einige Zeit in Anspruch nehmen, bitte haben Sie etwas Geduld. Wir setzen uns so bald wie möglich wieder mit Ihnen in Verbindung.\r\n\r\nMit freundlichen Grüßen\r\nMAIRDUMONT GmbH & Co. KG', '2017-07-21', 1),
(3, 1, 'Sehr geehrtes Team Grün,\r\n\r\nvielen Dank für Ihre Bewerbung und das vorliegende Interesse.\r\nWir freuen uns Ihnen mitteilen zu können, dass wir Sie gerne zu einem persönlichen Kennenlernen einladen möchten.\r\n\r\nMit freundlichen Grüßen\r\nAnne-Marie Schmidt\r\n\r\n', '2017-02-27', 3),
(4, 0.3, 'Sehr geehrte GRIN Verlag GmbH,\r\n\r\naufgrund der zahlreichen Bewerbungen kann die Bearbeitung einige Zeit in Anspruch nehmen, bitte haben Sie etwas Geduld. Wir setzen uns so bald wie möglich wieder mit Ihnen in Verbindung.\r\n\r\nMit freundlichen Grüßen\r\nTanja Bieber', '2017-04-24', 4),
(5, 0.7, 'Sehr geehrte team:orange GmbH,\r\n\r\naufgrund der zahlreichen Bewerbungen kann die Bearbeitung einige Zeit in Anspruch nehmen, bitte haben Sie etwas Geduld. Wir setzen uns so bald wie möglich wieder mit Ihnen in Verbindung.\r\n\r\nMit freundlichen Grüßen\r\nFrankfurter Allgemeine Zeitung GmbH', '2017-01-30', 5);

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
(1, 'Arbeitsgebiet', 'Web Design', 1),
(2, 'Berufserfahrung in Jahren', '5 Jahre', 2),
(3, 'Sprachkenntnisse', 'Fortgeschritten', 3),
(4, 'MS-Office Kenntnisse', 'sichere Kenntnisse', 4),
(5, 'Programmiersprache', 'Java, C, C+', 5),
(6, 'Programmierkenntnisse', 'gute Programmierkenntnisse', 6),
(7, 'Berufserfahrung in Jahren', '2 Jahre', 1),
(8, 'Sprachkenntnisse', 'sehr gutes Englisch', 1),
(9, 'Arbeitsgebiet', 'Controlling', 2),
(10, 'Sprachkenntnisse', 'Spanisch', 2);

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
  `Partnerprofil_ID` int(11) DEFAULT NULL,
  `UAdmin_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `organisationseinheit`
--

INSERT INTO `organisationseinheit` (`ID`, `Typ`, `Name`, `Vorname`, `Email`, `Strasse`, `PLZ`, `Ort`, `Tel`, `Partnerprofil_ID`, `UAdmin_ID`) VALUES
(1, 'P', 'Müller', 'Hans', 'hans.mueller@web.de', 'Laternenweg 8', 70794, 'Filderstadt', '0711435687', 1, NULL),
(2, 'P', 'Schmidt', 'Anne-Marie', 'schmidtm@hotmail.com', 'Kreuzweg 14', 72622, 'Nürtingen', '0702245322', 2, NULL),
(3, 'P', 'Bieber', 'Tanja', 't.bieber90@gmx.de', 'Brunnenstraße 52', 72631, 'Aichtal', '07127956321', 3, NULL),
(4, 'P', 'Maier', 'Henriette', 'maier.h@hotmail.com', 'Blütenallee 64', 71063, 'Sindelfingen', '0703167901', 4, NULL),
(5, 'U', 'GRIN Verlag GmbH', NULL, 'info@grin.com', 'Nymphenburger Straße 86', 80636, 'München', '0895505590', 5, NULL),
(6, 'U', 'Frankfurter Allgemeine Zeitung GmbH', NULL, 'info@faz.net', 'Hellerhofstraße 2', 60327, 'Frankfurt am Main', '06975910', 6, NULL),
(7, 'U', 'MAIRDUMONT GmbH & Co. KG', NULL, 'info@mairdumont.com', 'Marco-Polo-Straße 1', 73760, 'Ostfildern', '071145020', 7, NULL),
(8, 'T', 'Team Grün', NULL, 'info@team-gruen-elzach.de', 'Wittenbachstraße 25', 79215, 'Elzach', '07682920090', 8, NULL),
(9, 'T', 'team:orange GmbH', NULL, 'info@teamorange.de', 'Pfaffenackerstraße 3', 73732, 'Esslingen am Neckar', '071113774040', 9, NULL),
(10, 'T', 'Team Connex AG', NULL, 'gerhardt@teamconnex.com', 'Schönbuchstraße 48', 71155, 'Altdorf', '07031270334', 10, NULL);

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
(1, '2017-06-28', '2017-07-03'),
(2, '2017-06-25', '2017-06-28'),
(3, '2017-06-05', '2017-06-23'),
(4, '2017-05-16', '2017-05-17'),
(5, '2017-07-10', '2017-07-24'),
(6, '2017-07-03', '2017-07-31'),
(7, '2016-12-13', '2017-04-05'),
(8, '2017-06-13', '2017-06-28'),
(9, '2017-06-08', '2017-06-12'),
(10, '2017-05-24', '2017-06-27');

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
  `Projektleiter_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `projekt`
--

INSERT INTO `projekt` (`ID`, `Name`, `Startdatum`, `Enddatum`, `Beschreibung`, `Projektmarktplatz_ID`, `Projektleiter_ID`) VALUES
(1, 'IT Projekt', '2017-03-21', '2017-07-03', 'Erstellen eines Zielsystems.', 1, 3),
(2, 'Gründungsworkshop', '2017-03-20', '2017-06-26', 'Wie gründet man ein Unternehmen? Wie wird die Gründungsidee umgesetzt? Welche Finanzierungsmöglichkeiten gibt es? All das erfahren Sie hier!', 2, 1),
(3, 'Grüner Daumen', '2017-05-02', '2017-06-12', 'Sie haben eine Affinität für Gartenarbeiten? Sie helfen gerne Ihren Mitmenschen den Garten zu verschönern? Dann machen Sie mit beim "Grünen Daumen", um die Beete ihrer Mitmenschen zu erobern!', 3, 8),
(4, 'be a self-publisher!', '2017-07-03', '2017-07-31', 'Verdiene Geld mit deinen Hausarbeiten! Lade deine Seminar- und Hausarbeiten hoch \r\nund verdiene bei jedem Verkauf. ', 4, 5),
(5, 'Virtuelle Welten', '2017-03-22', '2017-06-28', '- Verständnis für wesentliche Grundlagen virtueller Welten\n- Theoretische Basis mit anwendungsbezogenem Fokus\n- 3D-Modellierung mit gängigen Tools (Blender und Unity)\n- Verständnis für Virtual und Mixed Reality durch praktische Projektarbeit', 1, 2);

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
(1, 'Informationstechnologie'),
(2, 'Betriebswirtschaft'),
(3, 'Handwerk'),
(4, 'Dienstleistung');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `unternehmen_has_person`
--

CREATE TABLE `unternehmen_has_person` (
  `Unternehmen_ID` int(11) NOT NULL,
  `Person_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  ADD KEY `fk_Organisationseinheit_Partnerprofil1_idx` (`Partnerprofil_ID`),
  ADD KEY `fk_UAdmin_fk_idx` (`UAdmin_ID`);

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
  ADD KEY `fk_Projekt_Organisationseinheit2_idx` (`Projektleiter_ID`);

--
-- Indizes für die Tabelle `projektmarktplatz`
--
ALTER TABLE `projektmarktplatz`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `unternehmen_has_person`
--
ALTER TABLE `unternehmen_has_person`
  ADD PRIMARY KEY (`Unternehmen_ID`,`Person_ID`),
  ADD KEY `fk_Person_idx` (`Person_ID`),
  ADD KEY `fk_Unternehmen_idx` (`Unternehmen_ID`);

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
  ADD CONSTRAINT `fk_Organisationseinheit_Partnerprofil1` FOREIGN KEY (`Partnerprofil_ID`) REFERENCES `partnerprofil` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UAdmin_fk` FOREIGN KEY (`UAdmin_ID`) REFERENCES `organisationseinheit` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `projekt`
--
ALTER TABLE `projekt`
  ADD CONSTRAINT `fk_Projekt_Organisationseinheit2` FOREIGN KEY (`Projektleiter_ID`) REFERENCES `organisationseinheit` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Projekt_Projektmarktplatz` FOREIGN KEY (`Projektmarktplatz_ID`) REFERENCES `projektmarktplatz` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `unternehmen_has_person`
--
ALTER TABLE `unternehmen_has_person`
  ADD CONSTRAINT `fk_Person` FOREIGN KEY (`Person_ID`) REFERENCES `organisationseinheit` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Unternehmen` FOREIGN KEY (`Unternehmen_ID`) REFERENCES `organisationseinheit` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
