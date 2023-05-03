-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 03, 2023 at 10:28 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eventili`
--

-- --------------------------------------------------------

--
-- Table structure for table `avis`
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
-- Dumping data for table `avis`
--

INSERT INTO `avis` (`id_av`, `rating`, `comment`, `id_service`, `pers`, `Date`) VALUES
(57, 2, 'vhbj', 283, 39, '2023-05-02 00:37:49'),
(58, 4, 'jjj', 283, 39, '2023-05-02 00:38:02');

-- --------------------------------------------------------

--
-- Table structure for table `categ_event`
--

CREATE TABLE `categ_event` (
  `id_categ` int(11) NOT NULL,
  `type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `categ_event`
--

INSERT INTO `categ_event` (`id_categ`, `type`) VALUES
(19, 'Anniversaire'),
(20, 'Concert'),
(21, 'Mariage'),
(22, 'Séminaire'),
(28, 'Festival');

-- --------------------------------------------------------

--
-- Table structure for table `chat`
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
-- Table structure for table `evenement`
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
-- Dumping data for table `evenement`
--

INSERT INTO `evenement` (`id_ev`, `titre`, `date_debut`, `date_fin`, `description_ev`, `type`, `visibilite`, `limiteParticipant`, `prix`, `id_categ`, `id_pers`) VALUES
(350, 'gfhjj', '2023-05-30 00:00:00', '2023-05-30 23:59:00', 'fdgh', 'Gratuit', 'Public', 0, 0, 19, 39),
(351, 'dgfhh', '2023-05-30 00:00:00', '2023-05-30 23:59:00', 'fgjh', 'Gratuit', 'Public', 0, 0, 19, 39),
(357, 'zazerz', '2023-05-25 00:00:00', '2023-05-25 23:59:00', 'zaeezrza', 'Gratuit', 'Public', 0, 0, 19, 39),
(358, 'zzae', '2023-05-31 00:00:00', '2023-05-31 23:59:00', 'zae', 'Gratuit', 'Privé', 0, 0, 19, 39),
(360, 'zzae', '2023-05-25 00:00:00', '2023-05-25 23:59:00', 'aze', 'Gratuit', 'Public', 0, 0, 19, 39),
(361, 'zae', '2023-05-24 00:00:00', '2023-05-24 23:59:00', 'zae', 'Gratuit', 'Privé', 0, 0, 19, 39),
(362, 'zae', '2023-05-31 00:00:00', '2023-05-31 23:59:00', 'zae', 'Gratuit', 'Privé', 0, 0, 19, 39),
(363, 'zae', '2023-05-26 00:00:00', '2023-05-26 23:59:00', 'aze', 'Payant', 'Privé', 0, 50, 19, 39),
(364, '12354', '2023-05-31 00:00:00', '2023-05-31 23:59:00', 'zzae', 'Gratuit', 'Privé', 0, 0, 19, 39);

-- --------------------------------------------------------

--
-- Table structure for table `imagepers`
--

CREATE TABLE `imagepers` (
  `id_imp` int(50) NOT NULL,
  `imageP` varchar(255) NOT NULL,
  `last` varchar(255) NOT NULL,
  `id_pers` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `imagess`
--

CREATE TABLE `imagess` (
  `idimgss` int(11) NOT NULL,
  `img` text DEFAULT NULL,
  `sous_service` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `imagess`
--

INSERT INTO `imagess` (`idimgss`, `img`, `sous_service`) VALUES
(295, '9a2f9a14bf59c535a86516bb46c9433a.png', 283),
(296, '5d986ffd13886f99a739ba9614257862.png', 283),
(298, 'f57b0b2858d376958b2b5e88e32b931b.png', 283);

-- --------------------------------------------------------

--
-- Table structure for table `imgev`
--

CREATE TABLE `imgev` (
  `id_imgev` int(11) NOT NULL,
  `imageE` varchar(255) NOT NULL,
  `id_even` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `imgev`
--

INSERT INTO `imgev` (`id_imgev`, `imageE`, `id_even`) VALUES
(121, '87e06eeeea0e1693cd95fec10e9a796e.png', 350),
(122, '2694b703c6c82e13985482bfb7aad238.png', 351),
(128, 'a820876679bcd9b882f611bd9c96451a.gif', 357),
(129, '5321397f4773630a330e872d7b0e390e.gif', 358),
(131, '57bbadd00136b1eb5f01170a38692e7a.gif', 360),
(132, '2e1def1cbd1492bb6ef55dd8cf490118.gif', 361),
(133, 'd60390b10f4eaaeace3785b935922803.gif', 362),
(134, 'c4daf060379801abc3fc8086af262638.jpg', 363),
(135, '24978740e79cf999647a571308fae420.gif', 364);

-- --------------------------------------------------------

--
-- Table structure for table `personne`
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
-- Dumping data for table `personne`
--

INSERT INTO `personne` (`id_pers`, `nom_pers`, `prenom_pers`, `num_tel`, `email`, `mdp`, `adresse`, `rib`, `role`, `token`, `is_verified`, `date`) VALUES
(36, 'Tlili', 'Chaima', '99339684', 'tlili.chaima@esprit.tn', '$2y$13$pyE5L42ZSM51AVv6u4hDieQNL4wQ467r/kVdM6CK994FcY52t5zxO', 'hjkl', '11111111111111111111', 'organisateur', 'BlcW1UQDvM5sjcF7daWxA1ytN6qqla', 0, '2023-05-01 23:40:13'),
(39, 'Tlili', 'Chaima', '99339684', 'chaima.tli10@gmail.com', '$2y$13$sAtQ3XpQBuacRMJHpgf10e9MsUa8T1ZZB46j5ood4pVGyVZigDs1u', 'hjkl', '11111111111111111111', 'admin', 'Na9Id4skeLnkvOAla8yJATaaAWBz9d', 1, '2023-05-03 10:14:13'),
(40, 'Tlili', 'Chaima', '99339684', 'chaima.tli10@gmail.com', '$2y$13$sAtQ3XpQBuacRMJHpgf10e9MsUa8T1ZZB46j5ood4pVGyVZigDs1u', 'hjkl', '11111111111111111111', 'organisateur', 'Na9Id4skeLnkvOAla8yJATaaAWBz9d', 1, '2023-05-02 01:22:14');

-- --------------------------------------------------------

--
-- Table structure for table `reclamation`
--

CREATE TABLE `reclamation` (
  `id_rec` int(11) NOT NULL,
  `description` text NOT NULL,
  `titre` varchar(30) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateheure` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reponse`
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
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `id_res` int(11) NOT NULL,
  `id_ss` text DEFAULT NULL,
  `id_ev` int(11) DEFAULT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`id_res`, `id_ss`, `id_ev`, `status`) VALUES
(71, '283', 350, 0),
(72, '283', 351, 0),
(73, '', NULL, 0),
(74, '', NULL, 0),
(75, '', NULL, 0),
(76, '', NULL, 0),
(77, '283', 357, 0),
(78, '283', 358, 0),
(79, '283', 360, 0),
(80, '283', 361, 0),
(81, '283', 362, 0),
(82, '283', 363, 0),
(83, '283', 364, 0);

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `id_service` int(11) NOT NULL,
  `nom` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `service`
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
(134, 'wed');

-- --------------------------------------------------------

--
-- Table structure for table `sousservice`
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
-- Dumping data for table `sousservice`
--

INSERT INTO `sousservice` (`id`, `nom`, `description`, `prix`, `note`, `id_eventCateg`, `id_service`, `id_Pers`) VALUES
(283, 'hj', 'kl', 5, 0, '19,20', 117, 40);

-- --------------------------------------------------------

--
-- Table structure for table `sponsoring`
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
-- Dumping data for table `sponsoring`
--

INSERT INTO `sponsoring` (`id_sponso`, `date_debut`, `date_fin`, `nombre_impression`, `id_trans`, `id_event`) VALUES
(13, '2026-01-01', '2028-01-01', 40, 9, 358),
(14, '2025-01-01', '2027-01-01', 15, 9, 357),
(15, '2025-01-01', '2027-01-01', 123, 9, 361),
(16, '2024-01-01', '2027-01-01', 123, 31, 350),
(17, '2023-05-31', '2023-06-08', 900, 36, 350),
(18, '2025-01-01', '2027-01-01', 14, 9, 350);

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
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
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id_trans` int(11) NOT NULL,
  `valeur_trans` float NOT NULL,
  `devis` enum('USD','EUR','JPY','GBP','CHF','CAD','AUD','CNY','HKD','INR','TND') NOT NULL,
  `user_id` int(11) NOT NULL,
  `date_trans` date NOT NULL,
  `mode_trans` enum('Square','Stripe','Amazon Pay','Google Pay','Apple Pay') NOT NULL,
  `montant_tot` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id_trans`, `valeur_trans`, `devis`, `user_id`, `date_trans`, `mode_trans`, `montant_tot`) VALUES
(9, 240, 'USD', 36, '2026-05-02', 'Stripe', 300),
(31, 12, 'USD', 36, '2026-01-01', 'Square', 145),
(32, 123, 'USD', 40, '2026-01-01', 'Square', 233),
(33, 1575, 'USD', 39, '2023-05-03', 'Stripe', 1605),
(34, 1575, 'USD', 39, '2023-05-03', 'Stripe', 1605),
(35, 15.75, 'TND', 39, '2023-05-03', 'Stripe', 45.75),
(36, 18, '', 39, '2023-05-03', 'Stripe', 48),
(37, 123, 'USD', 36, '2025-01-01', 'Square', 10000000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `avis`
--
ALTER TABLE `avis`
  ADD PRIMARY KEY (`id_av`),
  ADD KEY `fk_serv` (`id_service`),
  ADD KEY `gggggggg` (`pers`);

--
-- Indexes for table `categ_event`
--
ALTER TABLE `categ_event`
  ADD PRIMARY KEY (`id_categ`);

--
-- Indexes for table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`id_chat`),
  ADD KEY `fk_chat` (`userId`),
  ADD KEY `fk_eventchat` (`event_id`);

--
-- Indexes for table `evenement`
--
ALTER TABLE `evenement`
  ADD PRIMARY KEY (`id_ev`),
  ADD KEY `fk_categ` (`id_categ`),
  ADD KEY `fk_p` (`id_pers`);

--
-- Indexes for table `imagepers`
--
ALTER TABLE `imagepers`
  ADD PRIMARY KEY (`id_imp`),
  ADD KEY `fk_perI` (`id_pers`);

--
-- Indexes for table `imagess`
--
ALTER TABLE `imagess`
  ADD PRIMARY KEY (`idimgss`),
  ADD KEY `fk_image_ss` (`sous_service`);

--
-- Indexes for table `imgev`
--
ALTER TABLE `imgev`
  ADD PRIMARY KEY (`id_imgev`),
  ADD KEY `fk_even` (`id_even`);

--
-- Indexes for table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`id_pers`);

--
-- Indexes for table `reclamation`
--
ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`id_rec`),
  ADD KEY `fk_personne` (`userId`);

--
-- Indexes for table `reponse`
--
ALTER TABLE `reponse`
  ADD PRIMARY KEY (`id_rep`),
  ADD KEY `fk_rec` (`rec_id`),
  ADD KEY `fk_sender` (`senderId`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id_res`),
  ADD KEY `fk_event` (`id_ev`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`id_service`);

--
-- Indexes for table `sousservice`
--
ALTER TABLE `sousservice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_service` (`id_service`),
  ADD KEY `fk_pers` (`id_Pers`);

--
-- Indexes for table `sponsoring`
--
ALTER TABLE `sponsoring`
  ADD PRIMARY KEY (`id_sponso`),
  ADD KEY `fk_eve` (`id_event`),
  ADD KEY `fk_sponso` (`id_trans`);

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`id_tick`),
  ADD KEY `fk_ev` (`idEvent`),
  ADD KEY `fk_tick` (`id_tran`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id_trans`),
  ADD KEY `fk_trans` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `avis`
--
ALTER TABLE `avis`
  MODIFY `id_av` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT for table `categ_event`
--
ALTER TABLE `categ_event`
  MODIFY `id_categ` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `chat`
--
ALTER TABLE `chat`
  MODIFY `id_chat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `evenement`
--
ALTER TABLE `evenement`
  MODIFY `id_ev` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=365;

--
-- AUTO_INCREMENT for table `imagepers`
--
ALTER TABLE `imagepers`
  MODIFY `id_imp` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `imagess`
--
ALTER TABLE `imagess`
  MODIFY `idimgss` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=301;

--
-- AUTO_INCREMENT for table `imgev`
--
ALTER TABLE `imgev`
  MODIFY `id_imgev` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=136;

--
-- AUTO_INCREMENT for table `personne`
--
ALTER TABLE `personne`
  MODIFY `id_pers` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `id_rec` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `reponse`
--
ALTER TABLE `reponse`
  MODIFY `id_rep` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id_res` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `id_service` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=135;

--
-- AUTO_INCREMENT for table `sousservice`
--
ALTER TABLE `sousservice`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=285;

--
-- AUTO_INCREMENT for table `sponsoring`
--
ALTER TABLE `sponsoring`
  MODIFY `id_sponso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `id_tick` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id_trans` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `avis`
--
ALTER TABLE `avis`
  ADD CONSTRAINT `fk_serv` FOREIGN KEY (`id_service`) REFERENCES `sousservice` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `gggggggg` FOREIGN KEY (`pers`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `chat`
--
ALTER TABLE `chat`
  ADD CONSTRAINT `fk_chat` FOREIGN KEY (`userId`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_eventchat` FOREIGN KEY (`event_id`) REFERENCES `evenement` (`id_ev`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `evenement`
--
ALTER TABLE `evenement`
  ADD CONSTRAINT `fk_categ` FOREIGN KEY (`id_categ`) REFERENCES `categ_event` (`id_categ`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_p` FOREIGN KEY (`id_pers`) REFERENCES `personne` (`id_pers`);

--
-- Constraints for table `imagepers`
--
ALTER TABLE `imagepers`
  ADD CONSTRAINT `fk_perI` FOREIGN KEY (`id_pers`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `imagess`
--
ALTER TABLE `imagess`
  ADD CONSTRAINT `fk_image_ss` FOREIGN KEY (`sous_service`) REFERENCES `sousservice` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `imgev`
--
ALTER TABLE `imgev`
  ADD CONSTRAINT `fk_even` FOREIGN KEY (`id_even`) REFERENCES `evenement` (`id_ev`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reclamation`
--
ALTER TABLE `reclamation`
  ADD CONSTRAINT `fk_personne` FOREIGN KEY (`userId`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reponse`
--
ALTER TABLE `reponse`
  ADD CONSTRAINT `fk_rec` FOREIGN KEY (`rec_id`) REFERENCES `reclamation` (`id_rec`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_sender` FOREIGN KEY (`senderId`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `fk_event` FOREIGN KEY (`id_ev`) REFERENCES `evenement` (`id_ev`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sousservice`
--
ALTER TABLE `sousservice`
  ADD CONSTRAINT `fk_pers` FOREIGN KEY (`id_Pers`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_service` FOREIGN KEY (`id_service`) REFERENCES `service` (`id_service`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sponsoring`
--
ALTER TABLE `sponsoring`
  ADD CONSTRAINT `fk_eve` FOREIGN KEY (`id_event`) REFERENCES `evenement` (`id_ev`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `fk_ev` FOREIGN KEY (`idEvent`) REFERENCES `evenement` (`id_ev`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_tick` FOREIGN KEY (`id_tran`) REFERENCES `transaction` (`id_trans`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `fk_trans` FOREIGN KEY (`user_id`) REFERENCES `personne` (`id_pers`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
