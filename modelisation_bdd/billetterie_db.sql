-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : lun. 13 oct. 2025 à 13:12
-- Version du serveur : 8.0.35
-- Version de PHP : 8.2.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `billetterie_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `Billet`
--

CREATE TABLE `Billet` (
  `id_billet` int NOT NULL,
  `id_client` int DEFAULT NULL,
  `id_evenement` int DEFAULT NULL,
  `id_salle` int DEFAULT NULL,
  `id_horaire` int DEFAULT NULL,
  `dateAchat` datetime DEFAULT NULL,
  `prix` decimal(5,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Categorie`
--

CREATE TABLE `Categorie` (
  `id_categorie` int NOT NULL,
  `nomCategorie` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Client`
--

CREATE TABLE `Client` (
  `id_client` int NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `dateNaissance` date DEFAULT NULL,
  `telephone` varchar(15) DEFAULT NULL,
  `histoireAchat` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `ComplexeCulturel`
--

CREATE TABLE `ComplexeCulturel` (
  `id_complexe` int NOT NULL,
  `nomComplexe` varchar(50) DEFAULT NULL,
  `adresse` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Evenement`
--

CREATE TABLE `Evenement` (
  `id_evenement` int NOT NULL,
  `nomEvenement` varchar(50) DEFAULT NULL,
  `affiche` varchar(100) DEFAULT NULL,
  `description` text,
  `date` datetime DEFAULT NULL,
  `ageMinimal` int DEFAULT NULL,
  `duree` int DEFAULT NULL,
  `prix` decimal(5,2) DEFAULT NULL,
  `id_salle` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Evenement_Categorie`
--

CREATE TABLE `Evenement_Categorie` (
  `id_evenement` int NOT NULL,
  `id_categorie` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Evenement_Horaire`
--

CREATE TABLE `Evenement_Horaire` (
  `id_evenement` int NOT NULL,
  `id_horaire` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Evenement_Salle`
--

CREATE TABLE `Evenement_Salle` (
  `id_evenement` int NOT NULL,
  `id_salle` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Evenement_Tags`
--

CREATE TABLE `Evenement_Tags` (
  `id_evenement` int NOT NULL,
  `id_tag` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Horaire`
--

CREATE TABLE `Horaire` (
  `id_horaire` int NOT NULL,
  `heureDebut` time DEFAULT NULL,
  `heureFin` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Salle`
--

CREATE TABLE `Salle` (
  `id_salle` int NOT NULL,
  `nomSalle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Salle_Complexe`
--

CREATE TABLE `Salle_Complexe` (
  `id_salle` int NOT NULL,
  `id_complexe` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Tags`
--

CREATE TABLE `Tags` (
  `id_tag` int NOT NULL,
  `nomTag` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Billet`
--
ALTER TABLE `Billet`
  ADD PRIMARY KEY (`id_billet`),
  ADD KEY `id_client` (`id_client`),
  ADD KEY `id_evenement` (`id_evenement`),
  ADD KEY `id_salle` (`id_salle`),
  ADD KEY `id_horaire` (`id_horaire`);

--
-- Index pour la table `Categorie`
--
ALTER TABLE `Categorie`
  ADD PRIMARY KEY (`id_categorie`);

--
-- Index pour la table `Client`
--
ALTER TABLE `Client`
  ADD PRIMARY KEY (`id_client`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Index pour la table `ComplexeCulturel`
--
ALTER TABLE `ComplexeCulturel`
  ADD PRIMARY KEY (`id_complexe`);

--
-- Index pour la table `Evenement`
--
ALTER TABLE `Evenement`
  ADD PRIMARY KEY (`id_evenement`),
  ADD KEY `fk_evenement_salle` (`id_salle`);

--
-- Index pour la table `Evenement_Categorie`
--
ALTER TABLE `Evenement_Categorie`
  ADD PRIMARY KEY (`id_evenement`,`id_categorie`),
  ADD KEY `id_categorie` (`id_categorie`);

--
-- Index pour la table `Evenement_Horaire`
--
ALTER TABLE `Evenement_Horaire`
  ADD PRIMARY KEY (`id_evenement`,`id_horaire`),
  ADD KEY `id_horaire` (`id_horaire`);

--
-- Index pour la table `Evenement_Salle`
--
ALTER TABLE `Evenement_Salle`
  ADD PRIMARY KEY (`id_evenement`,`id_salle`),
  ADD KEY `id_salle` (`id_salle`);

--
-- Index pour la table `Evenement_Tags`
--
ALTER TABLE `Evenement_Tags`
  ADD PRIMARY KEY (`id_evenement`,`id_tag`),
  ADD KEY `id_tag` (`id_tag`);

--
-- Index pour la table `Horaire`
--
ALTER TABLE `Horaire`
  ADD PRIMARY KEY (`id_horaire`);

--
-- Index pour la table `Salle`
--
ALTER TABLE `Salle`
  ADD PRIMARY KEY (`id_salle`);

--
-- Index pour la table `Salle_Complexe`
--
ALTER TABLE `Salle_Complexe`
  ADD PRIMARY KEY (`id_salle`,`id_complexe`),
  ADD KEY `id_complexe` (`id_complexe`);

--
-- Index pour la table `Tags`
--
ALTER TABLE `Tags`
  ADD PRIMARY KEY (`id_tag`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `Billet`
--
ALTER TABLE `Billet`
  MODIFY `id_billet` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Categorie`
--
ALTER TABLE `Categorie`
  MODIFY `id_categorie` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Client`
--
ALTER TABLE `Client`
  MODIFY `id_client` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `ComplexeCulturel`
--
ALTER TABLE `ComplexeCulturel`
  MODIFY `id_complexe` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Evenement`
--
ALTER TABLE `Evenement`
  MODIFY `id_evenement` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Horaire`
--
ALTER TABLE `Horaire`
  MODIFY `id_horaire` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Salle`
--
ALTER TABLE `Salle`
  MODIFY `id_salle` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Tags`
--
ALTER TABLE `Tags`
  MODIFY `id_tag` int NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Billet`
--
ALTER TABLE `Billet`
  ADD CONSTRAINT `billet_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `Client` (`id_client`),
  ADD CONSTRAINT `billet_ibfk_2` FOREIGN KEY (`id_evenement`) REFERENCES `Evenement` (`id_evenement`),
  ADD CONSTRAINT `billet_ibfk_3` FOREIGN KEY (`id_salle`) REFERENCES `Salle` (`id_salle`),
  ADD CONSTRAINT `billet_ibfk_4` FOREIGN KEY (`id_horaire`) REFERENCES `Horaire` (`id_horaire`);

--
-- Contraintes pour la table `Evenement`
--
ALTER TABLE `Evenement`
  ADD CONSTRAINT `fk_evenement_salle` FOREIGN KEY (`id_salle`) REFERENCES `Salle` (`id_salle`);

--
-- Contraintes pour la table `Evenement_Categorie`
--
ALTER TABLE `Evenement_Categorie`
  ADD CONSTRAINT `evenement_categorie_ibfk_1` FOREIGN KEY (`id_evenement`) REFERENCES `Evenement` (`id_evenement`),
  ADD CONSTRAINT `evenement_categorie_ibfk_2` FOREIGN KEY (`id_categorie`) REFERENCES `Categorie` (`id_categorie`);

--
-- Contraintes pour la table `Evenement_Horaire`
--
ALTER TABLE `Evenement_Horaire`
  ADD CONSTRAINT `evenement_horaire_ibfk_1` FOREIGN KEY (`id_evenement`) REFERENCES `Evenement` (`id_evenement`),
  ADD CONSTRAINT `evenement_horaire_ibfk_2` FOREIGN KEY (`id_horaire`) REFERENCES `Horaire` (`id_horaire`);

--
-- Contraintes pour la table `Evenement_Salle`
--
ALTER TABLE `Evenement_Salle`
  ADD CONSTRAINT `evenement_salle_ibfk_1` FOREIGN KEY (`id_evenement`) REFERENCES `Evenement` (`id_evenement`),
  ADD CONSTRAINT `evenement_salle_ibfk_2` FOREIGN KEY (`id_salle`) REFERENCES `Salle` (`id_salle`);

--
-- Contraintes pour la table `Evenement_Tags`
--
ALTER TABLE `Evenement_Tags`
  ADD CONSTRAINT `evenement_tags_ibfk_1` FOREIGN KEY (`id_evenement`) REFERENCES `Evenement` (`id_evenement`),
  ADD CONSTRAINT `evenement_tags_ibfk_2` FOREIGN KEY (`id_tag`) REFERENCES `Tags` (`id_tag`);

--
-- Contraintes pour la table `Salle_Complexe`
--
ALTER TABLE `Salle_Complexe`
  ADD CONSTRAINT `salle_complexe_ibfk_1` FOREIGN KEY (`id_salle`) REFERENCES `Salle` (`id_salle`),
  ADD CONSTRAINT `salle_complexe_ibfk_2` FOREIGN KEY (`id_complexe`) REFERENCES `ComplexeCulturel` (`id_complexe`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
