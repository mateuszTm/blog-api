
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