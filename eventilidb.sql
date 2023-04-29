-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 23 mars 2023 à 14:01
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
-- Base de données : `eventilidb`
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

--
-- Déchargement des données de la table `avis`
--

INSERT INTO `avis` (`id_av`, `rating`, `comment`, `id_service`, `pers`, `Date`) VALUES
(32, 5, 'xdcfgvbhnj,k', 111, 31, '2023-03-22 21:16:00'),
(33, 7, 'bbbbbbbbb', 111, 31, '2017-12-31 23:00:00'),
(34, 4, 'hnj,k', 111, 19, '2023-03-23 11:08:43');

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
(263, 'fvgbnm', '2023-03-29 09:00:00', '2023-03-29 18:00:00', 'cvbn', 'Gratuit', 'Privé', 0, 0, 19, 33);

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
(6, 'camera.png', '1656681710445.jpg', 31),
(15, '1656681710445.jpg', '1656681710445.jpg', 31),
(16, 'bunting.png', '1656681710445.jpg', 31),
(17, '1656681710445.jpg', '333604147_210512748291976_7877968719684014449_n.png', 18),
(18, '1656681710445.jpg', 'camera.png', 19),
(19, '332878947_227756396315016_3856732524844157218_n.png', '1656681710445.jpg', 31),
(20, '1656681710445.jpg', '1656681710445.jpg', 31),
(21, '1656681710445.jpg', '333604147_210512748291976_7877968719684014449_n.png', 18),
(22, '1656681710445.jpg', '333604147_210512748291976_7877968719684014449_n.png', 18),
(23, '333604147_210512748291976_7877968719684014449_n.png', '333604147_210512748291976_7877968719684014449_n.png', 18),
(24, 'camera.png', 'camera.png', 19),
(25, 'profile.jpg', '1656681710445.jpg', 31),
(26, '1656681710445.jpg', '1656681710445.jpg', 31),
(27, 'b41e344ac65be32808603451ec2cb60e17e0de95_eventdecordesignlightingnjnyceggsoticeventsnjsbesteventdecoratoreventlightingeventdesignweddingbarmitzvahbatmitzvahgalafundraiser09.jpg', '1656681710445.jpg', 31),
(28, 'b41e344ac65be32808603451ec2cb60e17e0de95_eventdecordesignlightingnjnyceggsoticeventsnjsbesteventdecoratoreventlightingeventdesignweddingbarmitzvahbatmitzvahgalafundraiser09.jpg', '1656681710445.jpg', 31),
(29, '1656681710445.jpg', '1656681710445.jpg', 31),
(32, '1656681710445.jpg', '1656681710445.jpg', 33);

-- --------------------------------------------------------

--
-- Structure de la table `imagess`
--

CREATE TABLE `imagess` (
  `idimgss` int(11) NOT NULL,
  `img` text NOT NULL,
  `sous_service` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `imagess`
--

INSERT INTO `imagess` (`idimgss`, `img`, `sous_service`) VALUES
(1, 'new-year.png', 110),
(2, 'folder.png', 111),
(3, 'edit.png', 111),
(4, 'documents.png', 111);

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
(30, 'Marriage.jpg', 263);

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
  `token` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`id_pers`, `nom_pers`, `prenom_pers`, `num_tel`, `email`, `mdp`, `adresse`, `rib`, `role`, `token`) VALUES
(18, 'rabeb', 'bhy', '28899807', 'rabeb.benhajyhaia@esprit.tn', '$2a$10$zpbHApcZ.ZsG2BkMZwC4.eSPST5fDOc5VTx6IHiwWlfqAtFl1v./.', 'tunis', '22222222222222222222', 'partenaire', ''),
(19, 'chaima', 'tlili', '28899807', 'chaima.tli10@gmail.com', '$2a$10$l6pEkSQe4hD2i/5sSCbejej2BOHp12765/fE2JqbsksNy7Qpv6kVO', 'ezzahra', '33333333333333333333', 'organisateur', ''),
(31, 'aaaa', 'aaa', '11111111', 'yesmine.guesmi@esprit.tn', '$2a$10$8JJBt6xrEsOf.p8r8EY0KeGUwZ/4M7cBPDAdposSKaywTd5QI2/Eq', 'aaaaaaaaa', '11111111111111111111', 'admin', 'JfbisNII'),
(33, 'tlili', 'chaima', '99339684', 'tlili.chaima@esprit.tn', '$2a$10$zt3t59O4f3yuSRIcU7S7a.apBpLwS06QK.hSz0yBPgV7He0OhecUS', 'ezzahra', '11111111111111111112', 'admin', '');

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

CREATE TABLE `reclamation` (
  `id_rec` int(11) NOT NULL,
  `description` text NOT NULL,
  `titre` varchar(30) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateheure` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `reclamation`
--

INSERT INTO `reclamation` (`id_rec`, `description`, `titre`, `userId`, `dateheure`) VALUES
(3, 'pidev2023', 'titre', 18, '2023-03-07 13:13:54'),
(4, 'pidev2023', 'titre', 18, '2023-03-07 13:13:57');

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
(40, 'Musique'),
(41, 'Nouriture'),
(42, 'Lieu'),
(43, 'photographe'),
(44, 'Décoration'),
(45, 'Lieu');

-- --------------------------------------------------------

--
-- Structure de la table `sousservice`
--

CREATE TABLE `sousservice` (
  `id` int(11) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `image` text NOT NULL,
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

INSERT INTO `sousservice` (`id`, `nom`, `image`, `description`, `prix`, `note`, `id_eventCateg`, `id_service`, `id_Pers`) VALUES
(110, 'cvbnm', 'new-year.png', 'fvgbhnm', 520, 0, '22', 45, 33),
(111, 'vbnm,', 'folder.png', 'ghjk', 22, 0, '19', 44, 33),
(114, 'gjh', 'hbnj,k', 'bhknj,k', 89, 8, '22', 44, 18);

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
  `userID` int(11) NOT NULL,
  `date_trans` date NOT NULL,
  `mode_trans` enum('Square','Stripe','Amazon Pay','Google Pay','Apple Pay') NOT NULL,
  `montant_tot` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

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
  ADD KEY `fk_trans` (`userID`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `avis`
--
ALTER TABLE `avis`
  MODIFY `id_av` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

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
  MODIFY `id_ev` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=280;

--
-- AUTO_INCREMENT pour la table `imagepers`
--
ALTER TABLE `imagepers`
  MODIFY `id_imp` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT pour la table `imagess`
--
ALTER TABLE `imagess`
  MODIFY `idimgss` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `imgev`
--
ALTER TABLE `imgev`
  MODIFY `id_imgev` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT pour la table `personne`
--
ALTER TABLE `personne`
  MODIFY `id_pers` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT pour la table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `id_rec` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `reponse`
--
ALTER TABLE `reponse`
  MODIFY `id_rep` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id_res` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT pour la table `service`
--
ALTER TABLE `service`
  MODIFY `id_service` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT pour la table `sousservice`
--
ALTER TABLE `sousservice`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=115;

--
-- AUTO_INCREMENT pour la table `sponsoring`
--
ALTER TABLE `sponsoring`
  MODIFY `id_sponso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `id_tick` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id_trans` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

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
  ADD CONSTRAINT `fk_trans` FOREIGN KEY (`userID`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
