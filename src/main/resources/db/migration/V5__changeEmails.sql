INSERT INTO `users` (`email`, `password`, `first_name`, `last_name`, `phone`, `code`, `code_expiry`, `status`, `government_id`, `last_login`, `deleted`, `created_on`, `department`, `industry_id`, `role_id`, `created_by`, `service_provider_id`) VALUES
    ('e@e.com', '$2a$10$QymjTcaGlJIOhVogNKBM2.Q7YnzYqebdG2pY3qT8vjbYx.sW3piKW', 'JF', 'KYC', '111', NULL, NULL, 1, 'e', '2022-07-20 16:21:41', 0, '2022-02-08 19:11:09', 'test dep', 19, 4, 1, 29);
update users set email = "kycadmin@test.com" where id = 1;
update users set email = "spadmin@test.com" where id = 2;
update users set email = "cadmin@test.com" where id = 3;
update users set email = "telco@test.com" where id = 20;
update users set email = "bank@test.com" where email = "e@e.com";
