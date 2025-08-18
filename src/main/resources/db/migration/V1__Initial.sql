-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 17, 2022 at 09:27 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kyc`
--

-- --------------------------------------------------------

--
-- Table structure for table `anomalies`
--

CREATE TABLE `anomalies` (
  `id` bigint(20) NOT NULL,
  `reported_by_id` bigint(20) DEFAULT NULL,
  `reported_on` datetime NOT NULL DEFAULT current_timestamp(),
  `consumers_services_id` bigint(20) DEFAULT NULL,
  `consumer_id` bigint(20) DEFAULT NULL,
  `anomaly_type_id` bigint(20) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `anomaly_tracking`
--

CREATE TABLE `anomaly_tracking` (
  `id` bigint(20) NOT NULL,
  `anomaly_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_on` datetime DEFAULT current_timestamp(),
  `note` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `anomaly_types`
--

CREATE TABLE `anomaly_types` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `entity_id` bigint(20) DEFAULT NULL,
  `severity` varchar(45) DEFAULT NULL,
  `target_entity_type` smallint(6) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `created_on` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `anomaly_types`
--

INSERT INTO `anomaly_types` (`id`, `name`, `entity_id`, `severity`, `target_entity_type`, `created_by`, `created_on`) VALUES
(1, 'Incomplete Data', 0, '2', 0, 1, '2022-06-01 17:17:46'),
(2, 'Duplicate Records', 8, '3', 1, 3, '2022-06-01 17:17:46'),
(4, 'Exceeding Threshold', 19, '3', 2, 2, '2022-06-02 17:55:34');

-- --------------------------------------------------------

--
-- Table structure for table `consumers`
--

CREATE TABLE `consumers` (
  `id` bigint(20) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `service_provider_id` bigint(20) DEFAULT NULL,
  `created_on` datetime DEFAULT current_timestamp(),
  `gender` varchar(45) DEFAULT NULL,
  `nationality` varchar(45) DEFAULT NULL,
  `address` text DEFAULT NULL,
  `msisdn` varchar(45) DEFAULT NULL,
  `registration_date` date DEFAULT NULL,
  `birth_place` varchar(45) DEFAULT NULL,
  `birth_date` varchar(45) DEFAULT NULL,
  `subscriber_type` varchar(100) DEFAULT NULL,
  `identification_number` varchar(45) DEFAULT NULL,
  `identification_type` varchar(45) DEFAULT NULL,
  `identity_valitidy_date` date DEFAULT NULL,
  `identity_capture_path` varchar(255) DEFAULT NULL,
  `is_consistent` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `consumers_anomalies`
--

CREATE TABLE `consumers_anomalies` (
  `id` int(11) NOT NULL,
  `consumer_id` int(11) NOT NULL,
  `anomaly_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `consumers_services`
--

CREATE TABLE `consumers_services` (
  `id` bigint(20) NOT NULL,
  `consumer_id` bigint(20) DEFAULT NULL,
  `service_id` bigint(20) DEFAULT NULL,
  `created_on` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `documents`
--

CREATE TABLE `documents` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `document_url` varchar(255) DEFAULT NULL,
  `document_type_id` bigint(20) DEFAULT NULL,
  `created_on` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `documents`
--

INSERT INTO `documents` (`id`, `name`, `description`, `document_url`, `document_type_id`, `created_on`) VALUES
(1, 'a', 'a', 'a', NULL, '2022-03-08 16:43:18'),
(2, 'b', 'b', 'b', NULL, '2022-03-08 16:43:18'),
(3, 'c', 'c', 'c', NULL, '2022-03-08 16:43:18');

-- --------------------------------------------------------

--
-- Table structure for table `document_types`
--

CREATE TABLE `document_types` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_on` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `document_types`
--

INSERT INTO `document_types` (`id`, `name`, `description`, `created_on`) VALUES
(4, 'Carte d’identité', 'Une carte d\'identité est un document officiel qui permet à une personne de prouver son identité, son nom, son prénom et sa date de naissance, et sa nationalité.', '2022-03-08 17:08:10'),
(5, 'Carte d’électeur', 'La carte électorale (appelée carte d\'électeur) est un document prouvant votre inscription sur la liste électorale de la commune.', '2022-03-08 17:08:16'),
(9, 'Permis de conduire', '\nAutomobile\nLe permis de conduire est un examen qui donne l\'autorisation de conduire un véhicule motorisé, et qui consiste en une partie théorique sur le Code de la route et en une partie pratique de conduite avec un examinateur.', '2022-07-07 07:44:47'),
(10, 'Passeport', 'Pièce certifiant l\'identité et la nationalité, délivrée par une autorité administrative à un ressortissant pour lui permettre de se rendre à l\'étranger.', '2022-07-07 07:44:56'),
(11, 'Carte de service', 'Un service de carte met à disposition des cartes, des entités et des données attributaires dans de nombreux types d\'applications clientes.', '2022-07-07 07:45:08'),
(12, 'Carte d’étudiant', '\nLa carte d\'étudiant est délivrée à la rentrée aux étudiants inscrits dans le supérieur. Elle prouve que vous avez procédé à votre inscription administrative et donc que vous pouvez bénéficier de tous les avantages qu\'offre la vie étudiante.', '2022-07-07 07:45:20'),
(13, 'Carte d’élève', 'La carte scolaire désigne un système d\'affectation des élèves dans une école, un collège ou un lycée publics situé dans un secteur géographique où ces élèves sont domiciliés.', '2022-07-07 07:45:29'),
(14, 'Attestation de résidence', 'Une attestation de résidence ou attestation de domicile est un document qui certifie que vous habitez à une adresse et une localité donnée.', '2022-07-07 07:45:39');

-- --------------------------------------------------------

--
-- Table structure for table `industries`
--

CREATE TABLE `industries` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `deleted` bit(1) DEFAULT b'0',
  `created_on` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `industries`
--

INSERT INTO `industries` (`id`, `name`, `deleted`, `created_on`) VALUES
(19, 'GSM', b'0', '2022-03-08 14:57:47'),
(20, 'Banking', b'0', '2022-07-18 18:16:47'),
(24, 'Other Industry', b'0', '2022-06-03 15:13:10');

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` bigint(20) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `mark_read` tinyint(4) DEFAULT NULL,
  `notification_type` varchar(45) DEFAULT NULL,
  `clickable_id` bigint(20) DEFAULT NULL,
  `created_on` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `created_on` datetime NOT NULL DEFAULT current_timestamp(),
  `parent_role_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`, `created_on`, `parent_role_id`) VALUES
(1, 'KYC Admin', '2022-02-14 12:34:58', 1),
(2, 'SP Admin', '2022-02-14 12:34:58', 1),
(3, 'Compliance Admin', '2022-02-14 12:34:58', 1),
(4, 'SP User', '2022-02-14 12:34:58', 2);

-- --------------------------------------------------------

--
-- Table structure for table `services`
--

CREATE TABLE `services` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `status` tinyint(4) DEFAULT 0,
  `deleted` tinyint(4) DEFAULT 0,
  `created_on` datetime DEFAULT current_timestamp(),
  `service_type_id` bigint(20) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `approved_by` bigint(20) DEFAULT NULL,
  `rejection_reason` varchar(255) DEFAULT NULL,
  `service_provider_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `services`
--

INSERT INTO `services` (`id`, `name`, `status`, `deleted`, `created_on`, `service_type_id`, `created_by`, `approved_by`, `rejection_reason`, `service_provider_id`) VALUES
(24, 'Koza', 1, 0, '2022-03-11 16:19:22', 8, 1, 3, NULL, 20),
(25, 'Koza Refonte', 1, 0, '2022-03-17 12:13:16', 13, 1, 3, NULL, 20),
(29, 'Sepelas', 1, 0, '2022-03-17 12:14:41', 13, 1, 3, NULL, 20),
(30, 'PDV', 1, 0, '2022-03-18 11:36:28', 8, 1, 3, NULL, 20),
(31, 'Koza_VIP', 1, 0, '2022-03-18 11:36:45', 8, 1, 3, NULL, 24),
(32, 'Sepelas_VIP', 1, 0, '2022-03-18 11:37:03', 8, 1, 3, NULL, 20),
(40, 'Personal Loan', 1, 0, '2022-03-18 11:37:03', 24, 1, 3, NULL, 29),
(41, 'Home Loan', 0, 0, '2022-03-18 11:37:03', 24, 1, NULL, 'rejection reason', 29),
(42, 'Car Insurance', 0, 0, '2022-06-16 09:33:52', 25, 2, NULL, NULL, 29),
(43, 'Life Insurance', 0, 0, '2022-06-16 09:34:26', 25, 2, NULL, NULL, 30);

-- --------------------------------------------------------

--
-- Table structure for table `service_providers`
--

CREATE TABLE `service_providers` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `deleted` bit(1) DEFAULT b'0',
  `created_on` datetime DEFAULT current_timestamp(),
  `industry_id` bigint(20) NOT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `company_phone_number` varchar(45) DEFAULT NULL,
  `status` tinyint(4) DEFAULT 0,
  `approved_by` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `service_providers`
--

INSERT INTO `service_providers` (`id`, `name`, `deleted`, `created_on`, `industry_id`, `created_by`, `address`, `company_phone_number`, `status`, `approved_by`) VALUES
(20, 'Orange', b'0', '2022-03-11 10:56:27', 19, 1, NULL, NULL, 1, 2),
(24, 'Vodacom', b'0', '2022-03-24 16:11:43', 19, 1, NULL, NULL, 1, 2),
(27, 'Airtel', b'0', '2022-06-03 15:23:40', 19, 1, 'rrr', 'rr', 1, 2),
(28, 'Africell', b'0', '2022-06-03 15:25:26', 19, 1, 'mmm', 'mmmmm', 1, 2),
(29, 'Standard Bank', b'0', '2022-06-15 09:57:35', 20, 1, NULL, NULL, 1, 2),
(30, 'Bank of Africa', b'0', '2022-06-15 09:59:26', 20, 1, NULL, NULL, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `service_providers_services`
--

CREATE TABLE `service_providers_services` (
  `service_provider_id` bigint(20) NOT NULL,
  `service_id` bigint(20) NOT NULL,
  `created_on` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `service_providers_services`
--

INSERT INTO `service_providers_services` (`service_provider_id`, `service_id`, `created_on`) VALUES
(20, 24, '2022-03-18 16:09:17'),
(20, 25, '2022-03-18 16:09:17'),
(20, 29, '2022-03-18 16:09:17'),
(20, 30, '2022-03-18 16:09:17'),
(20, 31, '2022-03-18 16:09:17'),
(20, 32, '2022-03-18 16:09:17');

-- --------------------------------------------------------

--
-- Table structure for table `service_types`
--

CREATE TABLE `service_types` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `deleted` bit(1) DEFAULT b'0',
  `created_on` datetime DEFAULT current_timestamp(),
  `created_by` bigint(20) NOT NULL,
  `industry_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `service_types`
--

INSERT INTO `service_types` (`id`, `name`, `deleted`, `created_on`, `created_by`, `industry_id`) VALUES
(8, 'Prepaid', b'0', '2022-02-21 14:06:33', 1, 19),
(13, 'Postpaid', b'0', '2022-02-22 12:48:12', 1, 19),
(14, 'Hybrid', b'0', '2022-02-22 12:48:16', 1, 19),
(24, 'Loan', b'0', '2022-02-22 12:48:16', 1, 20),
(25, 'Insurance', b'0', '2022-06-16 07:44:06', 1, 20);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `code` bigint(20) DEFAULT NULL,
  `code_expiry` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT 2,
  `government_id` varchar(255) DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT 0,
  `created_on` datetime DEFAULT current_timestamp(),
  `department` varchar(50) NOT NULL,
  `industry_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `service_provider_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `first_name`, `last_name`, `phone`, `code`, `code_expiry`, `status`, `government_id`, `last_login`, `deleted`, `created_on`, `department`, `industry_id`, `role_id`, `created_by`, `service_provider_id`) VALUES
(1, 'a@a.com', '$2a$10$QymjTcaGlJIOhVogNKBM2.Q7YnzYqebdG2pY3qT8vjbYx.sW3piKW', 'JF', 'KYC', '111', NULL, NULL, 1, 'e', '2022-07-20 16:21:41', 0, '2022-02-08 19:11:09', 'test dep', 19, 1, 1, NULL),
(2, 'b@b.com', '$2a$10$QymjTcaGlJIOhVogNKBM2.Q7YnzYqebdG2pY3qT8vjbYx.sW3piKW', 'Sawsan', 'SP Admin', '1112', NULL, NULL, 1, 'd', '2022-07-20 16:20:58', 0, '2022-02-08 19:11:09', 'test dep', 19, 2, 1, NULL),
(3, 'c@c.com', '$2a$10$QymjTcaGlJIOhVogNKBM2.Q7YnzYqebdG2pY3qT8vjbYx.sW3piKW', 'Lara', 'Compliance', '111', NULL, NULL, 1, 'fgd', '2022-10-17 12:21:51', 0, '2022-02-14 12:22:12', 'test dep', 19, 3, 1, NULL),
(20, 'd@d.com', '$2a$10$QymjTcaGlJIOhVogNKBM2.Q7YnzYqebdG2pY3qT8vjbYx.sW3piKW', 'Naji', 'SP User', '111', NULL, NULL, 1, 'fgd', '2022-07-12 10:09:05', 0, '2022-02-14 12:22:12', 'test dep', 19, 4, 2, 20);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anomalies`
--
ALTER TABLE `anomalies`
  ADD PRIMARY KEY (`id`),
  ADD KEY `anomalies_consumers_fk` (`consumer_id`),
  ADD KEY `anomalies_consumers_services_fk` (`consumers_services_id`),
  ADD KEY `anomalies_reported_by_fk` (`reported_by_id`);

--
-- Indexes for table `anomaly_tracking`
--
ALTER TABLE `anomaly_tracking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `anomaly_tracking_anmalies_fk` (`anomaly_id`);

--
-- Indexes for table `anomaly_types`
--
ALTER TABLE `anomaly_types`
  ADD PRIMARY KEY (`id`),
  ADD KEY `anomally_types_created_by` (`created_by`);

--
-- Indexes for table `consumers`
--
ALTER TABLE `consumers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `consumers_service_providers_fk_idx` (`service_provider_id`);

--
-- Indexes for table `consumers_anomalies`
--
ALTER TABLE `consumers_anomalies`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `consumers_services`
--
ALTER TABLE `consumers_services`
  ADD PRIMARY KEY (`id`),
  ADD KEY `consumers_services_consumers_fk` (`consumer_id`),
  ADD KEY `consumers_services_services_fk` (`service_id`);

--
-- Indexes for table `documents`
--
ALTER TABLE `documents`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Name_UNIQUE` (`name`),
  ADD KEY `documents_document_types_fk_idx` (`document_type_id`);

--
-- Indexes for table `document_types`
--
ALTER TABLE `document_types`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Name_UNIQUE` (`name`);

--
-- Indexes for table `industries`
--
ALTER TABLE `industries`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_UNIQUE_industries` (`name`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `notifications_users_fk` (`user_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Name_UNIQUE` (`name`),
  ADD KEY `roles_roles_fk` (`parent_role_id`);

--
-- Indexes for table `services`
--
ALTER TABLE `services`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_UNIQUE_services` (`name`),
  ADD KEY `services_service_types_fk_idx` (`service_type_id`),
  ADD KEY `services_users_fk_idx` (`created_by`),
  ADD KEY `services_approved_by_fk` (`approved_by`);

--
-- Indexes for table `service_providers`
--
ALTER TABLE `service_providers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_UNIQUE_service_providers` (`name`),
  ADD KEY `service_providers_industries_fk_idx` (`industry_id`),
  ADD KEY `service_providers_created_by_fk` (`created_by`),
  ADD KEY `service_providers_approved_by_fk` (`approved_by`);

--
-- Indexes for table `service_providers_services`
--
ALTER TABLE `service_providers_services`
  ADD PRIMARY KEY (`service_provider_id`,`service_id`),
  ADD KEY `fk_service_providers_services_service_idx` (`service_id`);

--
-- Indexes for table `service_types`
--
ALTER TABLE `service_types`
  ADD PRIMARY KEY (`id`),
  ADD KEY `service_types_users_fk_idx` (`created_by`),
  ADD KEY `service_types_indurtries_fk` (`industry_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Email_UNIQUE` (`email`),
  ADD KEY `fk_users_departments_idx` (`department`),
  ADD KEY `fk_users_industries_idx` (`industry_id`),
  ADD KEY `fk_users_roles` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `anomalies`
--
ALTER TABLE `anomalies`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `anomaly_tracking`
--
ALTER TABLE `anomaly_tracking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `anomaly_types`
--
ALTER TABLE `anomaly_types`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `consumers`
--
ALTER TABLE `consumers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `consumers_anomalies`
--
ALTER TABLE `consumers_anomalies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `consumers_services`
--
ALTER TABLE `consumers_services`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `documents`
--
ALTER TABLE `documents`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `document_types`
--
ALTER TABLE `document_types`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `industries`
--
ALTER TABLE `industries`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `services`
--
ALTER TABLE `services`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `service_providers`
--
ALTER TABLE `service_providers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `service_types`
--
ALTER TABLE `service_types`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `anomalies`
--
ALTER TABLE `anomalies`
  ADD CONSTRAINT `anomalies_consumers_fk` FOREIGN KEY (`consumer_id`) REFERENCES `consumers` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `anomalies_consumers_services_fk` FOREIGN KEY (`consumers_services_id`) REFERENCES `consumers_services` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `anomalies_reported_by_fk` FOREIGN KEY (`reported_by_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `anomaly_tracking`
--
ALTER TABLE `anomaly_tracking`
  ADD CONSTRAINT `anomaly_tracking_anmalies_fk` FOREIGN KEY (`anomaly_id`) REFERENCES `anomalies` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `anomaly_types`
--
ALTER TABLE `anomaly_types`
  ADD CONSTRAINT `anomally_types_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `consumers`
--
ALTER TABLE `consumers`
  ADD CONSTRAINT `consumers_service_providers_fk` FOREIGN KEY (`service_provider_id`) REFERENCES `service_providers` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `documents`
--
ALTER TABLE `documents`
  ADD CONSTRAINT `documents_document_types_fk` FOREIGN KEY (`document_type_id`) REFERENCES `document_types` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `roles`
--
ALTER TABLE `roles`
  ADD CONSTRAINT `roles_roles_fk` FOREIGN KEY (`parent_role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `services`
--
ALTER TABLE `services`
  ADD CONSTRAINT `services_approved_by_fk` FOREIGN KEY (`approved_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `services_service_types_fk` FOREIGN KEY (`service_type_id`) REFERENCES `service_types` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `services_users_fk` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `service_providers`
--
ALTER TABLE `service_providers`
  ADD CONSTRAINT `service_providers_approved_by_fk` FOREIGN KEY (`approved_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `service_providers_created_by_fk` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `service_providers_industries_fk` FOREIGN KEY (`industry_id`) REFERENCES `industries` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `service_providers_services`
--
ALTER TABLE `service_providers_services`
  ADD CONSTRAINT `fk_service_providers_services_service_providers` FOREIGN KEY (`service_provider_id`) REFERENCES `service_providers` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_service_providers_services_services` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `service_types`
--
ALTER TABLE `service_types`
  ADD CONSTRAINT `service_types_indurtries_fk` FOREIGN KEY (`industry_id`) REFERENCES `industries` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `service_types_users_fk` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_users_industries` FOREIGN KEY (`industry_id`) REFERENCES `industries` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_users_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
