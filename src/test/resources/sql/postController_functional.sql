DELETE FROM blog_test.user;
INSERT INTO blog_test.user (`id`, `login`, `password`) values 
(1, "test_admin", "test_admin"),
(2, "test_user", "test_user");

DELETE FROM blog_test.role;
INSERT INTO blog_test.role (`id`, `name`) VALUES 
(1, "ROLE_ADMIN"),
(2, "ROLE_USER");

DELETE FROM blog_test.user_role;
INSERT INTO blog_test.user_role (`user_id`, `role_id`) VALUES 
(1, 1),
(2, 2);

DELETE FROM blog_test.post;
INSERT INTO blog_test.post (`id`, `date`, `title`, `content`, `user_id`) VALUES
(1, "2020-03-04 15:16:17", "test title 1", "test content 1", 1),
(2, "2020-08-09 10:11:12", "test title 2", "test content 2", 2),
(3, "2020-08-09 11:11:12", "test title 3", "test content 3", 1),
(4, "2020-08-09 12:11:12", "test title 4", "test content 4", 1),
(5, "2020-08-09 13:11:12", "test title 5", "test content 5", 1),
(6, "2020-08-09 14:11:12", "test title 6", "test content 6", 1),
(7, "2020-08-09 15:11:12", "test title 7", "test content 7", 2),
(8, "2020-08-09 16:11:12", "test title 8", "test content 8", 2),
(9, "2020-08-09 17:11:12", "test title 9", "test content 9", 2),
(10, "2020-08-09 18:11:12", "test title 10", "test content 10", 2),
(11, "2020-08-09 19:11:12", "test title 11", "test content 11", 2);