
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
  `id_horaire` int DEFAULT NULL,
  `dateAchat` datetime DEFAULT NULL,
  `prix` decimal(5,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Billet`
--

INSERT INTO `Billet` (`id_billet`, `id_client`, `id_evenement`, `id_horaire`, `dateAchat`, `prix`) VALUES
(1, 16, 9, 23, '2026-07-07 00:00:00', 22.00),
(2, 14, 2, 4, '2025-12-15 00:00:00', 20.00),
(3, 22, 7, 19, '2026-06-15 00:00:00', 6.00),
(4, 9, 10, 25, '2026-06-21 00:00:00', 5.00),
(5, 9, 6, 14, '2026-04-18 00:00:00', 28.00),
(6, 27, 2, 5, '2026-01-07 00:00:00', 20.00),
(7, 3, 8, 20, '2026-06-07 00:00:00', 8.00),
(8, 26, 3, 6, '2026-03-08 00:00:00', 45.00),
(9, 15, 10, 24, '2026-05-15 00:00:00', 5.00),
(10, 10, 10, 25, '2026-07-10 00:00:00', 5.00),
(11, 16, 9, 22, '2026-07-18 00:00:00', 22.00),
(12, 21, 6, 14, '2026-03-17 00:00:00', 28.00),
(13, 18, 6, 16, '2026-04-16 00:00:00', 28.00),
(14, 25, 7, 18, '2026-06-30 00:00:00', 6.00),
(15, 29, 1, 1, '2026-02-24 00:00:00', 35.00),
(16, 3, 7, 17, '2026-06-08 00:00:00', 6.00),
(17, 5, 8, 21, '2026-06-23 00:00:00', 8.00),
(18, 26, 7, 17, '2026-06-04 00:00:00', 6.00),
(19, 28, 9, 23, '2026-08-29 00:00:00', 22.00),
(20, 10, 4, 9, '2026-02-27 00:00:00', 12.50),
(21, 21, 4, 10, '2026-03-24 00:00:00', 12.50),
(22, 5, 6, 14, '2026-03-29 00:00:00', 28.00),
(23, 30, 10, 24, '2026-05-19 00:00:00', 5.00),
(24, 19, 1, 2, '2026-01-04 00:00:00', 35.00),
(25, 16, 5, 13, '2026-04-29 00:00:00', 10.00),
(26, 26, 7, 18, '2026-05-24 00:00:00', 6.00),
(27, 21, 7, 18, '2026-05-10 00:00:00', 6.00),
(28, 18, 1, 2, '2026-01-14 00:00:00', 35.00),
(29, 16, 9, 23, '2026-08-26 00:00:00', 22.00),
(30, 29, 4, 9, '2026-02-25 00:00:00', 12.50),
(31, 25, 7, 17, '2026-04-29 00:00:00', 6.00),
(32, 10, 7, 18, '2026-05-18 00:00:00', 6.00),
(33, 4, 4, 8, '2026-05-18 00:00:00', 12.50),
(34, 26, 7, 18, '2026-04-25 00:00:00', 6.00),
(35, 21, 1, 1, '2025-12-27 00:00:00', 35.00),
(36, 19, 5, 13, '2026-04-15 00:00:00', 10.00),
(37, 17, 2, 4, '2026-02-26 00:00:00', 20.00),
(38, 27, 2, 4, '2026-01-03 00:00:00', 20.00),
(39, 25, 2, 5, '2026-02-26 00:00:00', 20.00),
(40, 17, 4, 9, '2026-04-03 00:00:00', 12.50),
(41, 13, 1, 3, '2026-01-21 00:00:00', 35.00),
(42, 2, 6, 16, '2026-04-28 00:00:00', 28.00),
(43, 3, 4, 8, '2026-05-04 00:00:00', 12.50),
(44, 19, 7, 17, '2026-05-04 00:00:00', 6.00),
(45, 6, 9, 22, '2026-09-04 00:00:00', 22.00),
(46, 13, 3, 7, '2026-01-28 00:00:00', 45.00),
(47, 26, 9, 22, '2026-08-20 00:00:00', 22.00),
(48, 24, 1, 2, '2026-02-20 00:00:00', 35.00),
(49, 18, 6, 15, '2026-05-31 00:00:00', 28.00),
(50, 3, 2, 4, '2026-03-04 00:00:00', 20.00),
(51, 9, 1, 3, '2025-12-26 00:00:00', 35.00),
(52, 3, 5, 11, '2026-03-25 00:00:00', 10.00),
(53, 1, 10, 25, '2026-07-16 00:00:00', 5.00),
(54, 26, 7, 17, '2026-07-04 00:00:00', 6.00),
(55, 26, 9, 23, '2026-07-18 00:00:00', 22.00),
(56, 24, 6, 16, '2026-06-03 00:00:00', 28.00),
(57, 26, 5, 13, '2026-05-30 00:00:00', 10.00),
(58, 25, 5, 13, '2026-05-13 00:00:00', 10.00),
(59, 16, 4, 8, '2026-05-03 00:00:00', 12.50),
(60, 21, 7, 17, '2026-06-16 00:00:00', 6.00),
(61, 14, 7, 17, '2026-04-25 00:00:00', 6.00),
(62, 12, 9, 22, '2026-07-02 00:00:00', 22.00),
(63, 9, 7, 17, '2026-06-30 00:00:00', 6.00),
(64, 4, 9, 22, '2026-09-11 00:00:00', 22.00),
(65, 10, 6, 15, '2026-04-04 00:00:00', 28.00),
(66, 4, 1, 2, '2026-02-10 00:00:00', 35.00),
(67, 24, 5, 13, '2026-05-06 00:00:00', 10.00),
(68, 1, 7, 19, '2026-06-29 00:00:00', 6.00),
(69, 4, 6, 15, '2026-06-13 00:00:00', 28.00),
(70, 26, 4, 8, '2026-03-18 00:00:00', 12.50);

-- --------------------------------------------------------

--
-- Structure de la table `Categorie`
--

CREATE TABLE `Categorie` (
  `id_categorie` int NOT NULL,
  `nomCategorie` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Categorie`
--

INSERT INTO `Categorie` (`id_categorie`, `nomCategorie`) VALUES
(1, 'Théâtre'),
(2, 'Musique'),
(3, 'Danse'),
(4, 'Conférence'),
(5, 'Cinéma'),
(6, 'Jeune Public');

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

--
-- Déchargement des données de la table `Client`
--

INSERT INTO `Client` (`id_client`, `nom`, `email`, `adresse`, `dateNaissance`, `telephone`, `histoireAchat`) VALUES
(1, 'Lucas Martin', 'lucas.martin@example.com', '69 rue de Exemple, 75005 Paris', '1990-01-14', '0643738222', ''),
(2, 'Emma Bernard', 'emma.bernard@example.com', '37 rue de Exemple, 75014 Paris', '1980-11-01', '0625058410', ''),
(3, 'Léa Dubois', 'léa.dubois@example.com', '37 rue de Exemple, 75014 Paris', '1997-08-18', '0625550723', ''),
(4, 'Hugo Thomas', 'hugo.thomas@example.com', '27 rue de Exemple, 75017 Paris', '1994-11-11', '0650566596', ''),
(5, 'Chloé Robert', 'chloé.robert@example.com', '84 rue de Exemple, 75006 Paris', '2003-01-20', '0629153706', ''),
(6, 'Noah Petit', 'noah.petit@example.com', '61 rue de Exemple, 75017 Paris', '1984-05-08', '0696114480', ''),
(7, 'Camille Durand', 'camille.durand@example.com', '70 rue de Exemple, 75018 Paris', '1987-12-20', '0633074480', ''),
(8, 'Lola Moreau', 'lola.moreau@example.com', '7 rue de Exemple, 75006 Paris', '1995-09-26', '0658271857', ''),
(9, 'Ethan Leroy', 'ethan.leroy@example.com', '28 rue de Exemple, 75019 Paris', '1982-09-19', '0663338320', ''),
(10, 'Manon Roux', 'manon.roux@example.com', '105 rue de Exemple, 75009 Paris', '1984-04-10', '0625739927', ''),
(11, 'Julien Faure', 'julien.faure@example.com', '48 rue de Exemple, 75011 Paris', '1982-03-20', '0630005348', ''),
(12, 'Sarah Garnier', 'sarah.garnier@example.com', '109 rue de Exemple, 75013 Paris', '2001-10-27', '0643540631', ''),
(13, 'Mathis Chevalier', 'mathis.chevalier@example.com', '82 rue de Exemple, 75011 Paris', '1993-05-28', '0695231463', ''),
(14, 'Zoé Marchand', 'zoé.marchand@example.com', '47 rue de Exemple, 75010 Paris', '1990-03-05', '0663723683', ''),
(15, 'Arthur Lemaire', 'arthur.lemaire@example.com', '10 rue de Exemple, 75004 Paris', '1997-01-26', '0680248623', ''),
(16, 'Inès Perrin', 'inès.perrin@example.com', '81 rue de Exemple, 75013 Paris', '2003-11-27', '0657869134', ''),
(17, 'Nathan Morin', 'nathan.morin@example.com', '13 rue de Exemple, 75019 Paris', '2002-08-22', '0648742999', ''),
(18, 'Mila Garcia', 'mila.garcia@example.com', '7 rue de Exemple, 75008 Paris', '2002-09-22', '0616357218', ''),
(19, 'Tom Blanc', 'tom.blanc@example.com', '100 rue de Exemple, 75019 Paris', '1998-06-26', '0632476190', ''),
(20, 'Anaïs Gauthier', 'anaïs.gauthier@example.com', '34 rue de Exemple, 75017 Paris', '2003-01-25', '0647466542', ''),
(21, 'Adrien Muller', 'adrien.muller@example.com', '109 rue de Exemple, 75007 Paris', '1999-11-28', '0633780292', ''),
(22, 'Clara Henry', 'clara.henry@example.com', '113 rue de Exemple, 75017 Paris', '1997-02-06', '0676664302', ''),
(23, 'Louis Rousseau', 'louis.rousseau@example.com', '59 rue de Exemple, 75001 Paris', '1996-08-18', '0673369437', ''),
(24, 'Sacha Barbier', 'sacha.barbier@example.com', '9 rue de Exemple, 75016 Paris', '1992-05-06', '0648473063', ''),
(25, 'Lucie Colin', 'lucie.colin@example.com', '2 rue de Exemple, 75012 Paris', '1994-02-07', '0629998536', ''),
(26, 'Paul Lambert', 'paul.lambert@example.com', '29 rue de Exemple, 75020 Paris', '1988-06-14', '0619468745', ''),
(27, 'Julia Simon', 'julia.simon@example.com', '6 rue de Exemple, 75007 Paris', '1999-03-11', '0692645783', ''),
(28, 'Maxime Michel', 'maxime.michel@example.com', '118 rue de Exemple, 75004 Paris', '1986-03-10', '0666105228', ''),
(29, 'Laura David', 'laura.david@example.com', '62 rue de Exemple, 75018 Paris', '1996-05-26', '0624976695', ''),
(30, 'Simon Leclerc', 'simon.leclerc@example.com', '75 rue de Exemple, 75005 Paris', '1995-08-13', '0619576098', '');

-- --------------------------------------------------------

--
-- Structure de la table `ComplexeCulturel`
--

CREATE TABLE `ComplexeCulturel` (
  `id_complexe` int NOT NULL,
  `nomComplexe` varchar(50) DEFAULT NULL,
  `adresse` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ComplexeCulturel`
--

INSERT INTO `ComplexeCulturel` (`id_complexe`, `nomComplexe`, `adresse`) VALUES
(1, 'Théâtre du Parc', '12 avenue des Arts, 75001 Paris'),
(2, 'Centre Culturel Lumière', '5 rue de la Liberté, 69002 Lyon'),
(3, 'Espace Nov\'Ais', '23 boulevard du Quai, 31000 Toulouse');

-- --------------------------------------------------------

--
-- Structure de la table `Evenement`
--

CREATE TABLE `Evenement` (
  `id_evenement` int NOT NULL,
  `nomEvenement` varchar(50) DEFAULT NULL,
  `affiche` varchar(100) DEFAULT NULL,
  `description` text,
  `date` date DEFAULT NULL,
  `ageMinimal` int DEFAULT NULL,
  `duree` int DEFAULT NULL,
  `prix` decimal(5,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Evenement`
--

INSERT INTO `Evenement` (`id_evenement`, `nomEvenement`, `affiche`, `description`, `date`, `ageMinimal`, `duree`, `prix`) VALUES
(1, 'La Nuit des Étoiles', 'nuit_etoiles.jpg', 'Concert symphonique sous la direction de M. Duval.', '2026-03-12', 6, 120, 35.00),
(2, 'Pièce: Le Voyageur', 'voyageur.jpg', 'Pièce contemporaine en 2 actes.', '2026-03-12', 12, 90, 20.00),
(3, 'Festival Danse Libre', 'danse.jpg', 'Trois compagnies internationales présentent leurs créations.', '2026-04-05', 10, 150, 45.00),
(4, 'Conférence: L\'IA et nous', 'ia.jpg', 'Table ronde avec experts en IA.', '2026-05-20', 16, 60, 12.50),
(5, 'Avant-première: Lumières', 'lumieres.jpg', 'Projection en avant-première du film \'Lumières\'.', '2026-06-01', 10, 110, 10.00),
(6, 'Concert Pop: Nova', 'nova.jpg', 'Concert du groupe Nova pour la tournée \'Horizons\'.', '2026-06-15', 0, 100, 28.00),
(7, 'Atelier Marionnettes', 'marionnettes.jpg', 'Atelier pour enfants animé par la Compagnie Petit Pas.', '2026-07-10', 3, 60, 6.00),
(8, 'Rencontre Auteur: Claire Morel', 'morel.jpg', 'Rencontre & dédicace avec l\'auteure Claire Morel.', '2026-08-03', 12, 90, 8.00),
(9, 'Soirée Jazz Intime', 'jazz.jpg', 'Trio de jazz dans une ambiance intimiste.', '2026-09-12', 0, 80, 22.00),
(10, 'Ciné-Plein Air', 'cine_pa.jpg', 'Projection en plein air d\'un classique du cinéma.', '2026-07-20', 0, 120, 5.00);

-- --------------------------------------------------------

--
-- Structure de la table `Evenement_Categorie`
--

CREATE TABLE `Evenement_Categorie` (
  `id_evenement` int NOT NULL,
  `id_categorie` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Evenement_Categorie`
--

INSERT INTO `Evenement_Categorie` (`id_evenement`, `id_categorie`) VALUES
(3, 1),
(4, 1),
(7, 1),
(9, 1),
(1, 2),
(2, 2),
(5, 2),
(9, 3),
(8, 5),
(2, 6),
(5, 6),
(6, 6),
(10, 6);

-- --------------------------------------------------------

--
-- Structure de la table `Evenement_Horaire`
--

CREATE TABLE `Evenement_Horaire` (
  `id_evenement` int NOT NULL,
  `id_horaire` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Evenement_Horaire`
--

INSERT INTO `Evenement_Horaire` (`id_evenement`, `id_horaire`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(2, 5),
(3, 6),
(3, 7),
(4, 8),
(4, 9),
(4, 10),
(5, 11),
(5, 12),
(5, 13),
(6, 14),
(6, 15),
(6, 16),
(7, 17),
(7, 18),
(7, 19),
(8, 20),
(8, 21),
(9, 22),
(9, 23),
(10, 24),
(10, 25);

-- --------------------------------------------------------

--
-- Structure de la table `Evenement_Salle`
--

CREATE TABLE `Evenement_Salle` (
  `id_evenement` int NOT NULL,
  `id_salle` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Evenement_Salle`
--

INSERT INTO `Evenement_Salle` (`id_evenement`, `id_salle`) VALUES
(1, 1),
(6, 1),
(10, 3),
(7, 6),
(9, 6),
(4, 7),
(1, 8),
(4, 8),
(8, 8),
(3, 9),
(5, 11),
(2, 12),
(8, 12);

-- --------------------------------------------------------

--
-- Structure de la table `Evenement_Tags`
--

CREATE TABLE `Evenement_Tags` (
  `id_evenement` int NOT NULL,
  `id_tag` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Evenement_Tags`
--

INSERT INTO `Evenement_Tags` (`id_evenement`, `id_tag`) VALUES
(7, 1),
(1, 2),
(3, 2),
(4, 2),
(7, 2),
(8, 2),
(10, 2),
(4, 3),
(5, 3),
(2, 4),
(3, 4),
(6, 4),
(8, 4),
(9, 4),
(10, 4),
(5, 5),
(6, 5),
(8, 5),
(2, 6),
(4, 6),
(7, 6),
(1, 7),
(9, 7),
(9, 8);

-- --------------------------------------------------------

--
-- Structure de la table `Horaire`
--

CREATE TABLE `Horaire` (
  `id_horaire` int NOT NULL,
  `heureDebut` time DEFAULT NULL,
  `heureFin` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Horaire`
--

INSERT INTO `Horaire` (`id_horaire`, `heureDebut`, `heureFin`) VALUES
(1, '18:00:00', '20:00:00'),
(2, '20:00:00', '22:00:00'),
(3, '22:00:00', '00:00:00'),
(4, '18:00:00', '19:00:00'),
(5, '20:00:00', '21:00:00'),
(6, '18:00:00', '20:00:00'),
(7, '20:00:00', '22:00:00'),
(8, '18:00:00', '19:00:00'),
(9, '20:00:00', '21:00:00'),
(10, '22:00:00', '23:00:00'),
(11, '18:00:00', '19:00:00'),
(12, '20:00:00', '21:00:00'),
(13, '22:00:00', '23:00:00'),
(14, '18:00:00', '19:00:00'),
(15, '20:00:00', '21:00:00'),
(16, '22:00:00', '23:00:00'),
(17, '18:00:00', '19:00:00'),
(18, '20:00:00', '21:00:00'),
(19, '22:00:00', '23:00:00'),
(20, '18:00:00', '19:00:00'),
(21, '20:00:00', '21:00:00'),
(22, '18:00:00', '19:00:00'),
(23, '20:00:00', '21:00:00'),
(24, '18:00:00', '20:00:00'),
(25, '20:00:00', '22:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `Salle`
--

CREATE TABLE `Salle` (
  `id_salle` int NOT NULL,
  `nomSalle` varchar(50) DEFAULT NULL,
  `id_complexe` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Salle`
--

INSERT INTO `Salle` (`id_salle`, `nomSalle`, `id_complexe`) VALUES
(1, 'Salle A - Théâtre', 1),
(2, 'Salle B - Théâtre', 1),
(3, 'Salle C - Théâtre', 1),
(4, 'Salle D - Théâtre', 1),
(5, 'Salle A - Centre', 2),
(6, 'Salle B - Centre', 2),
(7, 'Salle C - Centre', 2),
(8, 'Salle D - Centre', 2),
(9, 'Salle A - Espace', 3),
(10, 'Salle B - Espace', 3),
(11, 'Salle C - Espace', 3),
(12, 'Salle D - Espace', 3);

-- --------------------------------------------------------

--
-- Structure de la table `Tags`
--

CREATE TABLE `Tags` (
  `id_tag` int NOT NULL,
  `nomTag` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Tags`
--

INSERT INTO `Tags` (`id_tag`, `nomTag`) VALUES
(1, 'FR'),
(2, 'EN'),
(3, 'Soirée'),
(4, 'Avant-première'),
(5, 'Festival'),
(6, 'Gratuit'),
(7, 'Réservé'),
(8, 'Premium');

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
  ADD PRIMARY KEY (`id_evenement`);

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
  ADD PRIMARY KEY (`id_salle`),
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
  MODIFY `id_billet` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT pour la table `Categorie`
--
ALTER TABLE `Categorie`
  MODIFY `id_categorie` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `Client`
--
ALTER TABLE `Client`
  MODIFY `id_client` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT pour la table `Evenement`
--
ALTER TABLE `Evenement`
  MODIFY `id_evenement` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `Horaire`
--
ALTER TABLE `Horaire`
  MODIFY `id_horaire` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT pour la table `Salle`
--
ALTER TABLE `Salle`
  MODIFY `id_salle` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `Tags`
--
ALTER TABLE `Tags`
  MODIFY `id_tag` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Billet`
--
ALTER TABLE `Billet`
  ADD CONSTRAINT `billet_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `Client` (`id_client`),
  ADD CONSTRAINT `billet_ibfk_2` FOREIGN KEY (`id_evenement`) REFERENCES `Evenement` (`id_evenement`),
  ADD CONSTRAINT `billet_ibfk_3` FOREIGN KEY (`id_horaire`) REFERENCES `Horaire` (`id_horaire`);

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
-- Contraintes pour la table `Salle`
--
ALTER TABLE `Salle`
  ADD CONSTRAINT `salle_ibfk_1` FOREIGN KEY (`id_complexe`) REFERENCES `ComplexeCulturel` (`id_complexe`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
