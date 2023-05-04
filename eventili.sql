-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 04 mai 2023 à 10:31
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `eventili`
--

-- --------------------------------------------------------

--
-- Structure de la table `avis`
--

CREATE TABLE `avis` (
  `id_av` int(11) NOT NULL,
  `rating` float NOT NULL,
  `comment` text NOT NULL,
  `id_service` int(11) NOT NULL,
  `pers` int(11) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `categ_event`
--

CREATE TABLE `categ_event` (
  `id_categ` int(11) NOT NULL,
  `type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `categ_event`
--

INSERT INTO `categ_event` (`id_categ`, `type`) VALUES
(19, 'Anniversaire'),
(20, 'Concert'),
(21, 'Mariage'),
(22, 'Séminaire'),
(28, 'Festival');

-- --------------------------------------------------------

--
-- Structure de la table `chat`
--

CREATE TABLE `chat` (
  `id_chat` int(11) NOT NULL,
  `message_chat` text NOT NULL,
  `userId` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `dataheure_chat` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `evenement`
--

CREATE TABLE `evenement` (
  `id_ev` int(11) NOT NULL,
  `titre` varchar(30) NOT NULL,
  `date_debut` datetime NOT NULL,
  `date_fin` datetime NOT NULL,
  `description_ev` text NOT NULL,
  `type` enum('Gratuit','Payant') NOT NULL,
  `visibilite` enum('Privé','Public') NOT NULL,
  `limiteParticipant` int(11) NOT NULL,
  `prix` float NOT NULL,
  `id_categ` int(11) NOT NULL,
  `id_pers` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `evenement`
--

INSERT INTO `evenement` (`id_ev`, `titre`, `date_debut`, `date_fin`, `description_ev`, `type`, `visibilite`, `limiteParticipant`, `prix`, `id_categ`, `id_pers`) VALUES
(350, 'gfhjj', '2023-05-30 00:00:00', '2023-05-30 23:59:00', 'fdgh', 'Gratuit', 'Public', 0, 0, 19, 39),
(351, 'dgfhh', '2023-05-30 00:00:00', '2023-05-30 23:59:00', 'fgjh', 'Gratuit', 'Public', 0, 0, 19, 39),
(353, 'zxcvbnm,', '2023-05-01 00:00:00', '2023-05-01 23:59:00', 'xcvbnm,./', 'Gratuit', 'Privé', 0, 0, 19, 39),
(354, 'eventsuccess', '2023-05-01 00:00:00', '2023-05-01 23:59:00', 'vbnm,.', 'Gratuit', 'Public', 0, 0, 19, 39),
(361, 'event1', '2023-05-30 00:00:00', '2023-05-30 23:59:00', 'event', 'Gratuit', 'Privé', 0, 0, 19, 42),
(362, 'event1', '2023-05-30 00:00:00', '2023-05-30 23:59:00', 'event1', 'Gratuit', 'Public', 0, 0, 19, 43),
(368, 'ev1', '2023-05-28 00:00:00', '2023-05-28 23:59:00', 'jkl', 'Gratuit', 'Privé', 0, 0, 19, 50),
(369, 'ev5', '2023-05-24 00:00:00', '2023-05-24 23:59:00', 'gh', 'Gratuit', 'Privé', 0, 0, 19, 50);

-- --------------------------------------------------------

--
-- Structure de la table `imagepers`
--

CREATE TABLE `imagepers` (
  `id_imp` int(50) NOT NULL,
  `imageP` varchar(255) NOT NULL,
  `last` varchar(255) NOT NULL,
  `id_pers` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `imagepers`
--

INSERT INTO `imagepers` (`id_imp`, `imageP`, `last`, `id_pers`) VALUES
(36, '123331475_1542833722567235_5134712474481165947_n.jpg', '123331475_1542833722567235_5134712474481165947_n.jpg', 42);

-- --------------------------------------------------------

--
-- Structure de la table `imagess`
--

CREATE TABLE `imagess` (
  `idimgss` int(11) NOT NULL,
  `img` text DEFAULT NULL,
  `sous_service` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `imagess`
--

INSERT INTO `imagess` (`idimgss`, `img`, `sous_service`) VALUES
(321, '6a6c149b051f87c1675ee6598484aad0.jpg', 294),
(322, '9217acfb9fc0c51f6b3580122501263d.png', 295),
(323, '9024660aa20f932f16cdf19aa4b2b35a.png', 296);

-- --------------------------------------------------------

--
-- Structure de la table `imgev`
--

CREATE TABLE `imgev` (
  `id_imgev` int(11) NOT NULL,
  `imageE` varchar(255) NOT NULL,
  `id_even` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `imgev`
--

INSERT INTO `imgev` (`id_imgev`, `imageE`, `id_even`) VALUES
(121, '87e06eeeea0e1693cd95fec10e9a796e.png', 350),
(122, '2694b703c6c82e13985482bfb7aad238.png', 351),
(123, '3e7e27cb57fc28f2e230d366fd53b51e.png', 353),
(124, 'c3a4a4eea1cdf5b60cece4bb47da6ae7.png', 353),
(125, '63594d9ad9b2c1acb425812fdd3b13af.png', 354),
(132, '27cca1fe16c0e1d217a2000180fd7805.jpg', 361),
(133, 'c8337e696caf0294688b73c57752ab78.jpg', 362),
(139, '42edcc3024dc12721134dc517c0f72e7.png', 368),
(140, '90a420e4de11b4f2e4aa7e735e46bfc0.jpg', 369);

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE `personne` (
  `id_pers` int(11) NOT NULL,
  `nom_pers` varchar(30) NOT NULL,
  `prenom_pers` varchar(30) NOT NULL,
  `num_tel` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `mdp` varchar(255) NOT NULL,
  `adresse` varchar(30) NOT NULL,
  `rib` text NOT NULL,
  `role` enum('organisateur','partenaire','admin') NOT NULL,
  `token` varchar(30) NOT NULL,
  `is_verified` tinyint(1) NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`id_pers`, `nom_pers`, `prenom_pers`, `num_tel`, `email`, `mdp`, `adresse`, `rib`, `role`, `token`, `is_verified`, `date`) VALUES
(39, 'Tlili', 'Chaima', '99339684', 'chaima.tli10@gmail.com', '$2y$13$sAtQ3XpQBuacRMJHpgf10e9MsUa8T1ZZB46j5ood4pVGyVZigDs1u', 'hjkl', '11111111111111111111', 'organisateur', 'Na9Id4skeLnkvOAla8yJATaaAWBz9d', 1, '2023-05-03 20:22:17'),
(42, 'ghzel', 'dali', '99339684', 'tlili.chaima1@esprit.tn', '$2y$13$niNxYFJgFZNug6MdKxqzo.VthihhZ3nnt19FZEZ4BsfJX94DpD12m', 'ariana', '11111111111111111111', 'organisateur', 'qsdgYNLSa9nD4tui0x1BiaQduQyy4B', 1, '2023-05-04 00:08:02'),
(43, 'yessmin', 'yessmin', '51468579', 'tlili.chaima@esprit.tn1', '$2y$13$5yd5n8l/5d1Zcb5/KSqKfeP/IccGOWDOyGG1djX1xqUxhx68xZTmy', 'ariana', '11111111111111111111', 'organisateur', 'XqDxuX1Yfio9jUO-Ly6oWpv2H8HEpT', 1, '2023-05-04 01:00:04'),
(44, 'yessmin', 'yessmin', '51468579', 'tlili.chaima@esprit.tn', '$2y$13$5yd5n8l/5d1Zcb5/KSqKfeP/IccGOWDOyGG1djX1xqUxhx68xZTmy', 'ariana', '11111111111111111111', 'organisateur', 'XqDxuX1Yfio9jUO-Ly6oWpv2H8HEpT', 1, '2023-05-04 01:00:04'),
(45, 'yessmin', 'yessmin', '51468579', 'tlili.chaima@esprit.tn', '$2y$13$5yd5n8l/5d1Zcb5/KSqKfeP/IccGOWDOyGG1djX1xqUxhx68xZTmy', 'ariana', '11111111111111111111', 'organisateur', 'XqDxuX1Yfio9jUO-Ly6oWpv2H8HEpT', 1, '2023-05-04 01:00:04'),
(46, 'yessmin', 'yessmin', '51468579', 'tlili.chaima@esprit.tn', '$2y$13$5yd5n8l/5d1Zcb5/KSqKfeP/IccGOWDOyGG1djX1xqUxhx68xZTmy', 'ariana', '11111111111111111111', 'organisateur', 'XqDxuX1Yfio9jUO-Ly6oWpv2H8HEpT', 1, '2023-05-04 01:00:04'),
(47, 'yessmin', 'yessmin', '51468579', 'tlili.chaima@esprit.tn', '$2y$13$5yd5n8l/5d1Zcb5/KSqKfeP/IccGOWDOyGG1djX1xqUxhx68xZTmy', 'ariana', '11111111111111111111', 'organisateur', 'XqDxuX1Yfio9jUO-Ly6oWpv2H8HEpT', 1, '2023-05-04 01:00:04'),
(48, 'yessmin', 'yessmin', '51468579', 'tlili.chaima@esprit.tn', '$2y$13$5yd5n8l/5d1Zcb5/KSqKfeP/IccGOWDOyGG1djX1xqUxhx68xZTmy', 'ariana', '11111111111111111111', 'organisateur', 'XqDxuX1Yfio9jUO-Ly6oWpv2H8HEpT', 1, '2023-05-04 01:00:04'),
(49, 'yessmin', 'yessmin', '51468579', 'tlili.chaima@esprit.tn', '$2y$13$5yd5n8l/5d1Zcb5/KSqKfeP/IccGOWDOyGG1djX1xqUxhx68xZTmy', 'ariana', '11111111111111111111', 'organisateur', 'XqDxuX1Yfio9jUO-Ly6oWpv2H8HEpT', 1, '2023-05-04 01:00:04'),
(50, 'mansouri', 'ahmed', '53698741', 'rabeb.benhajyhaia@esprit.tn', '$2y$13$JAVBIuJYFTxGiyckg/BAWOvsBcHiQXeNDQ2knp/60zR9UtVzqdYPS', 'bizerte', '11111111111111111111', 'organisateur', 'impmzMG2OoixC3HwdgsvgfIL-sxJkg', 1, '2023-05-04 07:17:15');

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

CREATE TABLE `reclamation` (
  `id_rec` int(11) NOT NULL,
  `description` text NOT NULL,
  `titre` varchar(30) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateheure` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` varchar(255) NOT NULL DEFAULT 'ouvert'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `reclamation`
--

INSERT INTO `reclamation` (`id_rec`, `description`, `titre`, `userId`, `dateheure`, `status`) VALUES
(5, 'le service a été annulé', 'Service annulé', 39, '2023-05-03 19:16:28', 'cloturer'),
(6, 'ef', 'ef', 43, '2023-05-03 23:35:21', 'cloturer');

-- --------------------------------------------------------

--
-- Structure de la table `reponse`
--

CREATE TABLE `reponse` (
  `id_rep` int(11) NOT NULL,
  `message` text NOT NULL,
  `senderId` int(11) NOT NULL,
  `rec_id` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `isEdited` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `reponse`
--

INSERT INTO `reponse` (`id_rep`, `message`, `senderId`, `rec_id`, `timestamp`, `isEdited`) VALUES
(4, 'dfgh', 49, 6, '2023-05-04 00:34:00', 0);

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `id_res` int(11) NOT NULL,
  `id_ss` text DEFAULT NULL,
  `id_ev` int(11) DEFAULT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`id_res`, `id_ss`, `id_ev`, `status`) VALUES
(82, '295,294', 368, 0),
(83, '296', 369, 0);

-- --------------------------------------------------------

--
-- Structure de la table `reset_password_request`
--

CREATE TABLE `reset_password_request` (
  `id_pers` int(11) DEFAULT NULL,
  `idP` int(11) NOT NULL,
  `selector` varchar(20) NOT NULL,
  `hashed_token` varchar(100) NOT NULL,
  `requested_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `expires_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `reset_password_request`
--

INSERT INTO `reset_password_request` (`id_pers`, `idP`, `selector`, `hashed_token`, `requested_at`, `expires_at`) VALUES
(43, 0, 'azKmvSf6zVkgqEHZHGzO', 'DCIwedkitWxnFYybQXTivRVV3wf+v1dBRyT72/RVruc=', '2023-05-04 00:45:37', '2023-05-04 01:45:37');

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

CREATE TABLE `service` (
  `id_service` int(11) NOT NULL,
  `nom` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `service`
--

INSERT INTO `service` (`id_service`, `nom`) VALUES
(117, 'musique'),
(118, 'gateaux'),
(119, 'lieu'),
(120, 'Décoration'),
(128, 'fourniture'),
(130, 'sucré'),
(131, 'sushi'),
(133, 'coiffure'),
(134, 'hj');

-- --------------------------------------------------------

--
-- Structure de la table `sousservice`
--

CREATE TABLE `sousservice` (
  `id` int(11) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `description` text NOT NULL,
  `prix` float NOT NULL,
  `note` float NOT NULL,
  `id_eventCateg` text NOT NULL,
  `id_service` int(11) NOT NULL,
  `id_Pers` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `sousservice`
--

INSERT INTO `sousservice` (`id`, `nom`, `description`, `prix`, `note`, `id_eventCateg`, `id_service`, `id_Pers`) VALUES
(294, 'serv2', 'sdfghj', 9, 0, '19', 119, 50),
(295, 'serv3', 'dfghj', 87, 0, '20', 117, 50),
(296, 'serv4', 'fdghjkl', 8966, 0, '19,28', 117, 50);

-- --------------------------------------------------------

--
-- Structure de la table `sponsoring`
--

CREATE TABLE `sponsoring` (
  `id_sponso` int(11) NOT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL,
  `nombre_impression` int(11) NOT NULL,
  `id_trans` int(11) NOT NULL,
  `id_event` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `sponsoring`
--

INSERT INTO `sponsoring` (`id_sponso`, `date_debut`, `date_fin`, `nombre_impression`, `id_trans`, `id_event`) VALUES
(5, '2023-05-10', '2023-06-08', 600, 9, 351),
(6, '2023-05-04', '2023-07-13', 6000, 12, 362);

-- --------------------------------------------------------

--
-- Structure de la table `ticket`
--

CREATE TABLE `ticket` (
  `id_tick` int(11) NOT NULL,
  `nb_tick` int(11) NOT NULL,
  `prix_tick` float NOT NULL,
  `status` varchar(30) NOT NULL,
  `id_tran` int(11) NOT NULL,
  `idEvent` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `transaction`
--

CREATE TABLE `transaction` (
  `id_trans` int(11) NOT NULL,
  `valeur_trans` float NOT NULL,
  `devis` enum('USD','EUR','JPY','GBP','CHF','CAD','AUD','CNY','HKD','INR') NOT NULL,
  `user_id` int(11) NOT NULL,
  `date_trans` date NOT NULL,
  `mode_trans` enum('Square','Stripe','Amazon Pay','Google Pay','Apple Pay') NOT NULL,
  `montant_tot` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `transaction`
--

INSERT INTO `transaction` (`id_trans`, `valeur_trans`, `devis`, `user_id`, `date_trans`, `mode_trans`, `montant_tot`) VALUES
(9, 12, '', 39, '2023-05-03', 'Stripe', 42),
(10, 15.75, '', 39, '2023-05-03', 'Stripe', 45.75),
(11, 19041.8, '', 43, '2023-05-03', 'Stripe', 19071.8),
(12, 120, '', 43, '2023-05-03', 'Stripe', 150),
(13, 18900, '', 43, '2023-05-03', 'Stripe', 18930),
(14, 126, '', 50, '2023-05-04', 'Stripe', 156),
(15, 30, '', 50, '2023-05-04', 'Stripe', 60),
(16, 34.65, '', 50, '2023-05-04', 'Stripe', 64.65),
(17, 22.05, '', 50, '2023-05-04', 'Stripe', 52.05),
(18, 47.25, '', 50, '2023-05-04', 'Stripe', 77.25),
(19, 28.35, '', 50, '2023-05-04', 'Stripe', 58.35);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `avis`
--
ALTER TABLE `avis`
  ADD PRIMARY KEY (`id_av`),
  ADD KEY `fk_serv` (`id_service`),
  ADD KEY `gggggggg` (`pers`);

--
-- Index pour la table `categ_event`
--
ALTER TABLE `categ_event`
  ADD PRIMARY KEY (`id_categ`);

--
-- Index pour la table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`id_chat`),
  ADD KEY `fk_chat` (`userId`),
  ADD KEY `fk_eventchat` (`event_id`);

--
-- Index pour la table `evenement`
--
ALTER TABLE `evenement`
  ADD PRIMARY KEY (`id_ev`),
  ADD KEY `fk_categ` (`id_categ`),
  ADD KEY `fk_p` (`id_pers`);

--
-- Index pour la table `imagepers`
--
ALTER TABLE `imagepers`
  ADD PRIMARY KEY (`id_imp`),
  ADD KEY `fk_perI` (`id_pers`);

--
-- Index pour la table `imagess`
--
ALTER TABLE `imagess`
  ADD PRIMARY KEY (`idimgss`),
  ADD KEY `fk_image_ss` (`sous_service`);

--
-- Index pour la table `imgev`
--
ALTER TABLE `imgev`
  ADD PRIMARY KEY (`id_imgev`),
  ADD KEY `fk_even` (`id_even`);

--
-- Index pour la table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`id_pers`);

--
-- Index pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`id_rec`),
  ADD KEY `fk_personne` (`userId`);

--
-- Index pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD PRIMARY KEY (`id_rep`),
  ADD KEY `fk_rec` (`rec_id`),
  ADD KEY `fk_sender` (`senderId`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id_res`),
  ADD KEY `fk_event` (`id_ev`);

--
-- Index pour la table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`id_service`);

--
-- Index pour la table `sousservice`
--
ALTER TABLE `sousservice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_service` (`id_service`),
  ADD KEY `fk_pers` (`id_Pers`);

--
-- Index pour la table `sponsoring`
--
ALTER TABLE `sponsoring`
  ADD PRIMARY KEY (`id_sponso`),
  ADD KEY `fk_eve` (`id_event`),
  ADD KEY `fk_sponso` (`id_trans`);

--
-- Index pour la table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`id_tick`),
  ADD KEY `fk_ev` (`idEvent`),
  ADD KEY `fk_tick` (`id_tran`);

--
-- Index pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id_trans`),
  ADD KEY `fk_trans` (`user_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `avis`
--
ALTER TABLE `avis`
  MODIFY `id_av` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT pour la table `categ_event`
--
ALTER TABLE `categ_event`
  MODIFY `id_categ` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT pour la table `chat`
--
ALTER TABLE `chat`
  MODIFY `id_chat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `evenement`
--
ALTER TABLE `evenement`
  MODIFY `id_ev` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=370;

--
-- AUTO_INCREMENT pour la table `imagepers`
--
ALTER TABLE `imagepers`
  MODIFY `id_imp` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT pour la table `imagess`
--
ALTER TABLE `imagess`
  MODIFY `idimgss` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=324;

--
-- AUTO_INCREMENT pour la table `imgev`
--
ALTER TABLE `imgev`
  MODIFY `id_imgev` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=141;

--
-- AUTO_INCREMENT pour la table `personne`
--
ALTER TABLE `personne`
  MODIFY `id_pers` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT pour la table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `id_rec` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `reponse`
--
ALTER TABLE `reponse`
  MODIFY `id_rep` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id_res` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;

--
-- AUTO_INCREMENT pour la table `service`
--
ALTER TABLE `service`
  MODIFY `id_service` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=136;

--
-- AUTO_INCREMENT pour la table `sousservice`
--
ALTER TABLE `sousservice`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=297;

--
-- AUTO_INCREMENT pour la table `sponsoring`
--
ALTER TABLE `sponsoring`
  MODIFY `id_sponso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `id_tick` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id_trans` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `avis`
--
ALTER TABLE `avis`
  ADD CONSTRAINT `fk_serv` FOREIGN KEY (`id_service`) REFERENCES `sousservice` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `gggggggg` FOREIGN KEY (`pers`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `chat`
--
ALTER TABLE `chat`
  ADD CONSTRAINT `fk_chat` FOREIGN KEY (`userId`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_eventchat` FOREIGN KEY (`event_id`) REFERENCES `evenement` (`id_ev`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `evenement`
--
ALTER TABLE `evenement`
  ADD CONSTRAINT `fk_categ` FOREIGN KEY (`id_categ`) REFERENCES `categ_event` (`id_categ`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_p` FOREIGN KEY (`id_pers`) REFERENCES `personne` (`id_pers`);

--
-- Contraintes pour la table `imagepers`
--
ALTER TABLE `imagepers`
  ADD CONSTRAINT `fk_perI` FOREIGN KEY (`id_pers`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `imagess`
--
ALTER TABLE `imagess`
  ADD CONSTRAINT `fk_image_ss` FOREIGN KEY (`sous_service`) REFERENCES `sousservice` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `imgev`
--
ALTER TABLE `imgev`
  ADD CONSTRAINT `fk_even` FOREIGN KEY (`id_even`) REFERENCES `evenement` (`id_ev`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD CONSTRAINT `fk_personne` FOREIGN KEY (`userId`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD CONSTRAINT `fk_rec` FOREIGN KEY (`rec_id`) REFERENCES `reclamation` (`id_rec`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_sender` FOREIGN KEY (`senderId`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `fk_event` FOREIGN KEY (`id_ev`) REFERENCES `evenement` (`id_ev`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `sousservice`
--
ALTER TABLE `sousservice`
  ADD CONSTRAINT `fk_pers` FOREIGN KEY (`id_Pers`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_service` FOREIGN KEY (`id_service`) REFERENCES `service` (`id_service`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `sponsoring`
--
ALTER TABLE `sponsoring`
  ADD CONSTRAINT `fk_eve` FOREIGN KEY (`id_event`) REFERENCES `evenement` (`id_ev`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `fk_ev` FOREIGN KEY (`idEvent`) REFERENCES `evenement` (`id_ev`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_tick` FOREIGN KEY (`id_tran`) REFERENCES `transaction` (`id_trans`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `fk_trans` FOREIGN KEY (`user_id`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
