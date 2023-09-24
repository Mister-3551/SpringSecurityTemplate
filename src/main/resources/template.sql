SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- DATABASE: `template`
--

CREATE DATABASE template CHARACTER SET utf8 COLLATE utf8_unicode_ci;

-- --------------------------------------------------------

--
-- TABLE STRUCTURE `authorities`
--

CREATE TABLE `authorities` (
  `id` int(11) NOT NULL,
  `authority` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- DATA FOR TABLE 'authorities'
--

INSERT INTO `authorities` (`id`, `authority`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- TABLE STRUCTURE `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `full_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email_address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `birth_date` date DEFAULT NULL,
  `country` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account_confirmed` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `account_locked` enum('0','1') COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `unlock_date` datetime DEFAULT NULL,
  `reports_number` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- DATA FOR TABLE `users`
--

INSERT INTO `users` (`id`, `full_name`, `username`, `email_address`, `password`, `birth_date`, `country`, `account_confirmed`, `account_locked`, `unlock_date`, `reports_number`) VALUES
(1, 'ADMIN', 'admin', 'admin@example.com', '$2a$12$NZTb30OYzjfA7CPePx8pLurbsQllhFJz1JoWMXirwLx5U6WWMjfA.', '2000-04-09', 'Slovenia', '1', '0', NULL, 0),
(2, 'USER', 'user', 'user@example.com', '$2a$12$NA7l.m9MF9rzYa9FTAMSluG6XeId9CqzZUETmwoG9W6.9CX/41hqG', '1999-09-18', 'Slovenia', '1', '0', NULL, 0);

-- --------------------------------------------------------

--
-- TABLE STRUCTURE `users_authorities`
--

CREATE TABLE `users_authorities` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_authority` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- DATA FOR TABLE `users_authorities`
--

INSERT INTO `users_authorities` (`id`, `id_user`, `id_authority`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 1);

--
-- Dropped table indexes
--

--
-- Table indexes `authorities`
--
ALTER TABLE `authorities`
  ADD PRIMARY KEY (`id`);

--
-- Table indexes `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_username` (`username`),
  ADD UNIQUE KEY `users_email_address` (`email_address`);

--
-- Table indexes `users_authorities`
--
ALTER TABLE `users_authorities`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_UA_id` (`id_user`),
  ADD KEY `FK_AU_id` (`id_authority`);

--
-- AUTO_INCREMENT of discarded tables
--

--
-- AUTO_INCREMENT tables `authorities`
--
ALTER TABLE `authorities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT tables `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT tables `users_authorities`
--
ALTER TABLE `users_authorities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Limitations of status summary tables
--

--
-- Constraints for the table `users_authorities`
--
ALTER TABLE `users_authorities`
  ADD CONSTRAINT `FK_AU_id` FOREIGN KEY (`id_authority`) REFERENCES `authorities` (`id`),
  ADD CONSTRAINT `FK_UA_id` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);
COMMIT;
