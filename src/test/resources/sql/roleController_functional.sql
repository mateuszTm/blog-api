DELETE FROM blog_test.user;
INSERT INTO blog_test.user (`id`, `login`, `password`) values 
(1, "test_admin", "$2a$10$j6.kO9aTgTTxpRxhZZPrKeXuam/wiuL8o5pPYc.3tRxxXeMbcDKdm"),
(2, "test_user", "$2a$10$IMH8ECe84UsTizfXbgssyumGI9s4z4nK2IERKSZLLjCz2d2mNZ9YS");

DELETE FROM blog_test.role;
INSERT INTO blog_test.role (`id`, `name`) VALUES 
(1, "ROLE_ADMIN"),
(2, "ROLE_USER"),
(3, "ROLE_TEST_1"),
(4, "ROLE_TEST_2");

DELETE FROM blog_test.user_role;
INSERT INTO blog_test.user_role (`user_id`, `role_id`) VALUES 
(1, 1),
(2, 2);