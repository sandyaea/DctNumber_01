INSERT INTO `employee` (`id`,`givenName`,`patronymic`,`surName`) VALUES (1,'Фома','Фомич','Чистяков');
INSERT INTO `employee` (`id`,`givenName`,`patronymic`,`surName`) VALUES (2,'Егор','Егорович','Забелин');
INSERT INTO `employee` (`id`,`givenName`,`patronymic`,`surName`) VALUES (3,'Василий','Васильевич','Кузнецов');

INSERT INTO `dct` (`id`,`year`,`number`,`addressee_id`,`performer_id`,`signatory_id`) VALUES (4,2020,1,3,2,1);
INSERT INTO `dct` (`id`,`year`,`number`,`addressee_id`,`performer_id`,`signatory_id`) VALUES (5,2020,2,3,2,1);
INSERT INTO `dct` (`id`,`year`,`number`,`addressee_id`,`performer_id`,`signatory_id`) VALUES (6,2020,4,3,2,1);

TRUNCATE `id_generator`;
INSERT INTO `id_generator` (`next_val`) VALUES (7);
INSERT INTO `id_generator` (`next_val`) VALUES (7);
