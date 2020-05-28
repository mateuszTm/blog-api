USE `blog_test`;


DELETE FROM `oauth_client_details`;
INSERT INTO `oauth_client_details` VALUES 
('client-id',NULL,'$2a$10$GEqNDMckLYgJxZfRRmP/n.aZUF0u3a.gHPTgCOT.bWTqLRj/PR2qy','read,write','password,authorization_code,refresh_token,implicit','http://localhost:4200/login,http://localhost:4200/',NULL,36000,36000,NULL,'read,write');


DELETE FROM `profile`;
INSERT INTO `profile` VALUES 
(1,'test_admin','',1),
(2,'test_user','',1);


DELETE FROM `post`;
INSERT INTO `post` VALUES
(1,'2020-03-02 14:55:24','tytul 1',_binary 'tresc 1',1),
(2,'1991-02-04 14:55:24','tytul 2',_binary 'tresc 2',1),
(3,'1991-02-04 20:00:01','tytul 3',_binary 'tresc 3',1),
(4,'2020-03-10 08:17:49','tytul 4',_binary 'tresc 4',1),
(5,'2020-03-10 08:18:03','tytul 5',_binary 'tresc 5',1),
(6,'2020-03-10 14:12:16','tytul 6',_binary 'tresc 6',1),
(7,'2020-05-13 03:53:05','tytul 7',_binary 'tresc 7',1),
(8,'2020-05-08 09:36:53','tytul 8',_binary 'tresc 8',1),
(9,'2020-05-11 11:21:45','tytul 9',_binary 'tresc 9',1),
(10,'2020-05-11 11:26:06','tytul 10',_binary 'tresc 10',2),
(11,'2020-05-12 08:57:26','tytul 11',_binary 'tresc 11',2),
(12,'2020-05-12 09:03:44','tytul 12',_binary 'tresc 12',2),
(13,'2020-05-12 11:32:51','tytul 13',_binary 'tresc 13',2),
(14,'2020-05-12 11:36:02','tytul 14',_binary 'tresc 14',2),
(15,'2020-05-12 13:22:52','tytul 15',_binary 'tresc 15',2),
(16,'2020-05-13 14:20:51','tytul 16',_binary 'tresc 16',2);


DELETE FROM `user_role`;


DELETE FROM `role`;
INSERT INTO `role` VALUES 
(1,'ADMIN'),
(2,'USER');


DELETE FROM `user`;
INSERT INTO `user` VALUES 
(1, "test_admin", "$2a$10$PTgQAqQMZl4uYTEvDwuzc.fdUObPL0Xiv8m7yjiP/KfjECremLAAi", 0),
(2, "test_user", "$2a$10$OKxZqlZw766GiCN/YOd3puHByM1Tf4DmH2UU4Zgcejkf.Dosa6y3C", 0);


INSERT INTO `user_role` VALUES 
(1,1),
(1,2),
(2,2);