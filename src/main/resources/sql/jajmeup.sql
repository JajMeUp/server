-- phpMyAdmin SQL Dump
-- version 4.5.5.1
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Jeu 21 Septembre 2017 à 18:35
-- Version du serveur :  5.7.11
-- Version de PHP :  7.0.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `jajmeup`
--

-- --------------------------------------------------------

--
-- Structure de la table `alarm`
--

CREATE TABLE `alarm` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `idTarget` bigint(20) UNSIGNED NOT NULL,
  `idVoter` bigint(20) UNSIGNED NOT NULL,
  `link` varchar(20) COLLATE utf8_bin NOT NULL,
  `message` varchar(140) COLLATE utf8_bin NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `alarm`
--

INSERT INTO `alarm` (`id`, `idTarget`, `idVoter`, `link`, `message`, `created`) VALUES
(1, 2, 1, 'eNGzltK_tlc', 'EPICA !!!!!', '2017-06-25 14:21:42');

-- --------------------------------------------------------

--
-- Structure de la table `friendship`
--

CREATE TABLE `friendship` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `idTarget` bigint(20) UNSIGNED NOT NULL,
  `idRequester` bigint(20) UNSIGNED NOT NULL,
  `status` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `friendship`
--

INSERT INTO `friendship` (`id`, `idTarget`, `idRequester`, `status`, `updated`) VALUES
(1, 2, 1, 'ACCEPTED', '2017-07-04 19:05:42'),
(2, 13, 14, 'ACCEPTED', '2017-08-25 14:04:49');

-- --------------------------------------------------------

--
-- Structure de la table `profile`
--

CREATE TABLE `profile` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `visibility` varchar(20) COLLATE utf8_bin NOT NULL,
  `display_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `picture` varchar(255) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `profile`
--

INSERT INTO `profile` (`id`, `user_id`, `visibility`, `display_name`, `picture`) VALUES
(13, 13, 'PRIVATE', 'test', ''),
(14, 14, 'FRIENDS', 'Hackerman', '');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(10) UNSIGNED NOT NULL,
  `username` varchar(200) COLLATE utf8_bin NOT NULL,
  `password` varchar(200) COLLATE utf8_bin NOT NULL,
  `role` enum('USER','ADMIN') COLLATE utf8_bin NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `role`, `created`) VALUES
(13, 'test@test.fr', '$2a$10$mZYEgeMZfG4YKDhG2mHifut1jD8koTZIyRNJFFJadHbvqHgO4mbc6', 'USER', '2017-07-16 08:39:19'),
(14, 'anthony.gourd@insa-cvl.fr', '$2a$10$6U3Mo.qeFFHcaBLVOgop7eRwSCeTXr..ZjpVVmRB5DgTbQ/A5NsB6', 'USER', '2017-07-18 20:31:26');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `alarm`
--
ALTER TABLE `alarm`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idVoter` (`idVoter`);

--
-- Index pour la table `friendship`
--
ALTER TABLE `friendship`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idUser` (`idTarget`),
  ADD KEY `idAmi` (`idRequester`);

--
-- Index pour la table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `alarm`
--
ALTER TABLE `alarm`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `friendship`
--
ALTER TABLE `friendship`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `profile`
--
ALTER TABLE `profile`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
