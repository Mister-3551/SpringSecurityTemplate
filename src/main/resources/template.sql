-- Zbirka podatkov: `template`
--

-- --------------------------------------------------------

CREATE TABLE `authorities` (
  `id` int(11) NOT NULL,
  `authority` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT INTO `authorities` (`id`, `authority`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Struktura tabele `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `full_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email_address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `birth_date` date DEFAULT NULL,
  `country` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account_locked` enum('0','1') COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `unlock_date` datetime DEFAULT NULL,
  `reports_number` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `users` (`id`, `full_name`, `username`, `email_address`, `password`, `birth_date`, `country`, `account_locked`, `unlock_date`, `reports_number`) VALUES
(1, 'ADMIN', 'admin', 'admin@example.com', '$2a$12$8RTVJIO5qRoK.UEfvKxYHutT1izYBO2gOwigSV3d0jl5xCdE6NSKu', '2002-07-05', 'Slovenia', '0', NULL, 0),
(2, 'USER', 'user', 'uporabnik@example.com', '$2a$12$XafR89eETmicau8mBsrDzub5z8YZmwaTCdb3IKCng2ctc7cE1FRMm', '2023-09-18', 'Slovenia', '0', NULL, 0);

-- --------------------------------------------------------

--
-- Struktura tabele `users_authorities`
--

CREATE TABLE `users_authorities` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_authority` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `users_authorities` (`id`, `id_user`, `id_authority`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 1);

--
-- Indeksi zavrženih tabel
--

--
-- Indeksi tabele `authorities`
--
ALTER TABLE `authorities`
  ADD PRIMARY KEY (`id`);

--
-- Indeksi tabele `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_username` (`username`),
  ADD UNIQUE KEY `users_email_address` (`email_address`);

--
-- Indeksi tabele `users_authorities`
--
ALTER TABLE `users_authorities`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_UA_id` (`id_user`),
  ADD KEY `FK_AU_id` (`id_authority`);

--
-- AUTO_INCREMENT zavrženih tabel
--

--
-- AUTO_INCREMENT tabele `authorities`
--
ALTER TABLE `authorities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT tabele `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT tabele `users_authorities`
--
ALTER TABLE `users_authorities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Omejitve tabel za povzetek stanja
--

--
-- Omejitve za tabelo `users_authorities`
--
ALTER TABLE `users_authorities`
  ADD CONSTRAINT `FK_AU_id` FOREIGN KEY (`id_authority`) REFERENCES `authorities` (`id`),
  ADD CONSTRAINT `FK_UA_id` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);
COMMIT;
