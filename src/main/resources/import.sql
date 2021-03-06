INSERT INTO `icur_user` (`id`,`login`,`password_hash`,`first_name`,`last_name`,`email`,`activated`,`lang_key`,`activation_key`,`reset_key`) VALUES (1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','1','en',NULL,NULL);
INSERT INTO `icur_user` (`id`,`login`,`password_hash`,`first_name`,`last_name`,`email`,`activated`,`lang_key`,`activation_key`,`reset_key`) VALUES (2,'anonymoususer','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User','anonymous@localhost','1','en',NULL,NULL);
INSERT INTO `icur_user` (`id`,`login`,`password_hash`,`first_name`,`last_name`,`email`,`activated`,`lang_key`,`activation_key`,`reset_key`) VALUES (3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','1','en',NULL,NULL);
INSERT INTO `icur_user` (`id`,`login`,`password_hash`,`first_name`,`last_name`,`email`,`activated`,`lang_key`,`activation_key`,`reset_key`) VALUES (4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','1','en',NULL,NULL);

INSERT INTO `icur_authority` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `icur_authority` (`name`) VALUES ('ROLE_USER');

INSERT INTO `icur_user_authority` (`user_id`,`authority_name`) VALUES (1,'ROLE_ADMIN');
INSERT INTO `icur_user_authority` (`user_id`,`authority_name`) VALUES (1,'ROLE_USER');
INSERT INTO `icur_user_authority` (`user_id`,`authority_name`) VALUES (3,'ROLE_ADMIN');
INSERT INTO `icur_user_authority` (`user_id`,`authority_name`) VALUES (3,'ROLE_USER');
INSERT INTO `icur_user_authority` (`user_id`,`authority_name`) VALUES (4,'ROLE_USER');