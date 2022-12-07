-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 07 déc. 2022 à 08:21
-- Version du serveur : 5.7.31
-- Version de PHP : 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bdsae`
--

-- --------------------------------------------------------

--
-- Structure de la table `adherent`
--

DROP TABLE IF EXISTS `adherent`;
CREATE TABLE IF NOT EXISTS `adherent` (
  `derniereVisite` date DEFAULT NULL,
  `derniereInscription` date DEFAULT NULL,
  `niveauPratique` varchar(45) DEFAULT NULL,
  `photo` blob,
  `Personne_idPersonne` int(11) NOT NULL,
  PRIMARY KEY (`Personne_idPersonne`),
  KEY `fk_Adherent_Personne1_idx` (`Personne_idPersonne`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `adherent`
--

INSERT INTO `adherent` (`derniereVisite`, `derniereInscription`, `niveauPratique`, `photo`, `Personne_idPersonne`) VALUES
(NULL, NULL, NULL, NULL, 7),
(NULL, NULL, NULL, NULL, 23);

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `Personne_idPersonne` int(11) NOT NULL,
  PRIMARY KEY (`Personne_idPersonne`),
  KEY `fk_Admin_Personne1_idx` (`Personne_idPersonne`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`Personne_idPersonne`) VALUES
(36);

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
  `idArticle` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(45) NOT NULL,
  `contenu` longtext NOT NULL,
  `dateCreation` date NOT NULL,
  `dateMAJ` date NOT NULL,
  `Admin_Personne_idPersonne` int(11) NOT NULL,
  PRIMARY KEY (`idArticle`),
  KEY `fk_Article_Admin1_idx` (`Admin_Personne_idPersonne`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `circuit`
--

DROP TABLE IF EXISTS `circuit`;
CREATE TABLE IF NOT EXISTS `circuit` (
  `idCircuit` int(11) NOT NULL AUTO_INCREMENT,
  `adresse` varchar(120) NOT NULL,
  `nbrMaxPlace` int(11) NOT NULL,
  `disponible` tinyint(4) NOT NULL,
  PRIMARY KEY (`idCircuit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `match`
--

DROP TABLE IF EXISTS `match`;
CREATE TABLE IF NOT EXISTS `match` (
  `idMatch` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `heureDebut` time NOT NULL,
  `heureFin` time NOT NULL,
  `nbrPartNecessaire` int(11) NOT NULL,
  `Gagnant` int(11) DEFAULT NULL,
  `resultatFinal` varchar(45) DEFAULT NULL,
  `Circuit_idCircuit` int(11) NOT NULL,
  `Admin_Personne_idPersonne` int(11) NOT NULL,
  PRIMARY KEY (`idMatch`),
  KEY `fk_Match_Circuit1_idx` (`Circuit_idCircuit`),
  KEY `fk_Match_Admin1_idx` (`Admin_Personne_idPersonne`),
  KEY `fk_Match_Adherent2_idx` (`Gagnant`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `match_has_adherent`
--

DROP TABLE IF EXISTS `match_has_adherent`;
CREATE TABLE IF NOT EXISTS `match_has_adherent` (
  `Match_idMatch` int(11) NOT NULL,
  `Adherent_Personne_idPersonne` int(11) NOT NULL,
  PRIMARY KEY (`Match_idMatch`,`Adherent_Personne_idPersonne`),
  KEY `fk_Match_has_Adherent_Adherent1_idx` (`Adherent_Personne_idPersonne`),
  KEY `fk_Match_has_Adherent_Match1_idx` (`Match_idMatch`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `idPersonne` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) NOT NULL,
  `prenom` varchar(45) NOT NULL,
  `adresse` varchar(120) NOT NULL,
  `telephone` varchar(12) NOT NULL,
  `mail` varchar(45) NOT NULL,
  `mdp` varchar(45) NOT NULL,
  PRIMARY KEY (`idPersonne`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`idPersonne`, `nom`, `prenom`, `adresse`, `telephone`, `mail`, `mdp`) VALUES
(1, 'Macey', 'Fuller', 'Ap #247-422 Sed Rd.', '0820288737', 'mauris.elit@icloud.ca', 'pede.'),
(2, 'Gavin', 'Duncan', 'P.O. Box 862, 2022 Lorem Street', '0468636581', 'consectetuer.adipiscing@protonmail.net', 'turpis'),
(3, 'Emerson', 'Weaver', '104-8822 Mollis. Avenue', '0815748292', 'aliquam.tincidunt.nunc@protonmail.net', 'tellus.'),
(4, 'Lara', 'Lee', 'P.O. Box 839, 3683 Ante. Avenue', '0577275438', 'nulla.semper.tellus@hotmail.edu', 'Morbi'),
(5, 'Reagan', 'Boone', '4701 Odio St.', '0873561086', 'duis.volutpat@icloud.couk', 'venenatis'),
(6, 'Miriam', 'Cook', '865-2546 Nisi. Av.', '0210152887', 'tellus.nunc@aol.net', 'amet'),
(7, 'Austin', 'Clark', 'Ap #792-4844 Lorem Av.', '0167551738', 'velit.cras.lorem@outlook.ca', 'euismod'),
(8, 'Jolie', 'Cummings', 'Ap #587-2782 Nibh Ave', '0661858485', 'ligula.eu@hotmail.com', 'nunc'),
(9, 'Vincent', 'Hubbard', '873-7271 Velit Avenue', '0848678135', 'ligula.elit.pretium@yahoo.ca', 'Phasellus'),
(10, 'Hilary', 'Stone', '141-2304 Tortor, St.', '0706454461', 'mi.lorem.vehicula@icloud.net', 'odio'),
(11, 'Conan', 'Frank', 'P.O. Box 129, 5659 Proin Ave', '0377783876', 'nunc@protonmail.org', 'Fusce'),
(12, 'Lacota', 'Greene', '8833 Inceptos Ave', '0637669744', 'a.ultricies@yahoo.org', 'sed'),
(13, 'Julie', 'Hess', 'Ap #603-1678 Quisque St.', '0454687432', 'vulputate.lacus@icloud.com', 'consequat'),
(14, 'Theodore', 'Haynes', 'Ap #416-9368 Tellus, St.', '0687481672', 'sed.hendrerit@protonmail.ca', 'Nunc'),
(15, 'Garrison', 'Guy', 'Ap #290-4358 Cursus. St.', '0681177458', 'egestas.urna.justo@icloud.edu', 'dolor'),
(16, 'Lewis', 'Burt', 'Ap #933-5788 Aliquet Street', '0436482261', 'arcu.et@hotmail.net', 'Aliquam'),
(17, 'Fitzgerald', 'Crane', '133-8747 Enim, Street', '0186575226', 'nec.luctus@protonmail.com', 'tincidunt'),
(18, 'Jayme', 'White', 'P.O. Box 563, 222 Vitae Rd.', '0308109853', 'eu.nibh.vulputate@google.couk', 'Nam'),
(19, 'Mari', 'Watts', 'P.O. Box 894, 9612 In Av.', '0729006706', 'laoreet.ipsum.curabitur@icloud.org', 'faucibus'),
(20, 'Nathan', 'Kent', '2009 Tempor St.', '0724835615', 'nonummy.fusce.fermentum@outlook.ca', 'egestas.'),
(21, 'Adara', 'Boyd', '4395 Et, Avenue', '0977111771', 'et.malesuada.fames@icloud.org', 'amet'),
(22, 'Cole', 'Daniel', 'P.O. Box 659, 5872 Augue Av.', '0841646083', 'adipiscing.elit@outlook.net', 'faucibus'),
(23, 'Haley', 'Cannon', 'Ap #819-4554 Fusce Rd.', '0793211258', 'suspendisse.sagittis@protonmail.ca', 'ornare'),
(24, 'Leonard', 'Craig', 'Ap #942-6209 Donec St.', '0286821614', 'tempus.lorem.fringilla@yahoo.ca', 'sodales'),
(25, 'Baker', 'Olsen', 'Ap #254-6990 In Rd.', '0735517885', 'vulputate.dui@protonmail.com', 'neque.'),
(26, 'Quamar', 'Davidson', 'Ap #596-2374 Libero Av.', '0278304950', 'donec@hotmail.org', 'iaculis'),
(27, 'Louis', 'Reese', 'P.O. Box 544, 532 Duis Avenue', '0978565649', 'consectetuer@aol.ca', 'tortor'),
(28, 'Leroy', 'Crawford', 'Ap #773-4668 Ante, Road', '0993353660', 'ornare.elit@google.com', 'ornare.'),
(29, 'Vanna', 'Hardin', '5922 Morbi Rd.', '0545762732', 'tempor.lorem@protonmail.com', 'sed'),
(30, 'Brynn', 'Francis', '165-5206 Quis Ave', '0667232418', 'interdum.ligula@aol.org', 'nibh.'),
(31, 'Russell', 'Blake', '6674 Proin Avenue', '0211238464', 'faucibus.ut@yahoo.ca', 'gravida'),
(32, 'Kasimir', 'Gillespie', 'Ap #427-4970 Sed Rd.', '0596042466', 'in.faucibus@hotmail.couk', 'morbi'),
(33, 'Denise', 'Dillard', '733-1951 Mauris St.', '0883704248', 'et.nunc@icloud.net', 'urna'),
(34, 'Jacob', 'Gould', '615-8111 Dictum Ave', '0944181639', 'primis@protonmail.net', 'nec'),
(35, 'Hiram', 'Mays', '168-9951 Enim. Street', '0654765925', 'aliquam.gravida.mauris@yahoo.org', 'Nulla'),
(36, 'Jaquelyn', 'Washington', 'P.O. Box 763, 2344 Curabitur St.', '0689760511', 'et.nunc@aol.ca', 'id,'),
(37, 'Ian', 'Mueller', '544 Ipsum St.', '0283584573', 'a.enim@protonmail.edu', 'orci'),
(38, 'Vivian', 'Christensen', '9672 Netus Av.', '0116388135', 'vitae@outlook.com', 'Vestibulum'),
(39, 'Moana', 'Morse', '3874 Nulla Street', '0531916237', 'libero@outlook.ca', 'sapien'),
(40, 'Unity', 'Burks', 'P.O. Box 225, 2893 Donec Avenue', '0495012333', 'fusce@yahoo.net', 'aliquet,'),
(41, 'Fallon', 'Kirkland', '734 Per Av.', '0428035484', 'proin.ultrices@outlook.org', 'nunc'),
(42, 'Kennan', 'Mcdaniel', 'Ap #259-7519 Purus Av.', '0766500474', 'turpis@aol.edu', 'metus.'),
(43, 'Carter', 'Mcdonald', 'Ap #776-4104 Nunc St.', '0787242756', 'parturient.montes@protonmail.edu', 'libero'),
(44, 'Farrah', 'Hunt', '550-1140 Pede Avenue', '0692405097', 'in.molestie@google.org', 'Etiam'),
(45, 'Yvette', 'Raymond', 'Ap #888-7682 Aliquam Ave', '0306829406', 'sem@yahoo.couk', 'Ut'),
(46, 'Thor', 'Aguilar', '9084 Massa St.', '0880387886', 'luctus.aliquet@icloud.net', 'Phasellus'),
(47, 'Libby', 'Barton', 'Ap #684-9086 Quam. Road', '0545807106', 'mauris.morbi@icloud.ca', 'justo'),
(48, 'Charde', 'Frazier', '549-2342 Sem Av.', '0336691386', 'lorem.ipsum@protonmail.edu', 'Nulla'),
(49, 'Sopoline', 'Evans', '627-7147 Semper St.', '0227940127', 'sagittis.augue@google.ca', 'dui'),
(50, 'Alexander', 'Daugherty', '617 Donec Street', '0311827441', 'sed.et@protonmail.edu', 'sociis');

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `idRéservation` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `heureDebut` time NOT NULL,
  `heureFin` time NOT NULL,
  `nombreParticipant` int(11) NOT NULL,
  `Circuit_idCircuit` int(11) NOT NULL,
  `Adherent_Personne_idPersonne` int(11) NOT NULL,
  `Admin_Personne_idPersonne` int(11) NOT NULL,
  PRIMARY KEY (`idRéservation`),
  KEY `fk_Reservation_Circuit1_idx` (`Circuit_idCircuit`),
  KEY `fk_Reservation_Adherent1_idx` (`Adherent_Personne_idPersonne`),
  KEY `fk_Reservation_Admin1_idx` (`Admin_Personne_idPersonne`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `adherent`
--
ALTER TABLE `adherent`
  ADD CONSTRAINT `fk_Adherent_Personne1` FOREIGN KEY (`Personne_idPersonne`) REFERENCES `personne` (`idPersonne`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `fk_Admin_Personne1` FOREIGN KEY (`Personne_idPersonne`) REFERENCES `personne` (`idPersonne`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `fk_Article_Admin1` FOREIGN KEY (`Admin_Personne_idPersonne`) REFERENCES `admin` (`Personne_idPersonne`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `match`
--
ALTER TABLE `match`
  ADD CONSTRAINT `fk_Match_Adherent2` FOREIGN KEY (`Gagnant`) REFERENCES `adherent` (`Personne_idPersonne`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Match_Admin1` FOREIGN KEY (`Admin_Personne_idPersonne`) REFERENCES `admin` (`Personne_idPersonne`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Match_Circuit1` FOREIGN KEY (`Circuit_idCircuit`) REFERENCES `circuit` (`idCircuit`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `match_has_adherent`
--
ALTER TABLE `match_has_adherent`
  ADD CONSTRAINT `fk_Match_has_Adherent_Adherent1` FOREIGN KEY (`Adherent_Personne_idPersonne`) REFERENCES `adherent` (`Personne_idPersonne`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Match_has_Adherent_Match1` FOREIGN KEY (`Match_idMatch`) REFERENCES `match` (`idMatch`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `fk_Reservation_Adherent1` FOREIGN KEY (`Adherent_Personne_idPersonne`) REFERENCES `adherent` (`Personne_idPersonne`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Reservation_Admin1` FOREIGN KEY (`Admin_Personne_idPersonne`) REFERENCES `admin` (`Personne_idPersonne`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Reservation_Circuit1` FOREIGN KEY (`Circuit_idCircuit`) REFERENCES `circuit` (`idCircuit`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
