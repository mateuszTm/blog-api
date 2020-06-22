CREATE DATABASE  IF NOT EXISTS `blog`;
USE `blog`;


DROP TABLE IF EXISTS `post`;

DROP TABLE IF EXISTS `profile`;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `description` blob,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 SET character_set_client = utf8mb4 ;
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(100) NOT NULL,
  `content` blob NOT NULL,
  `profile_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_post_profile_id` (`profile_id`),
  CONSTRAINT `fk_post_profile_id` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

