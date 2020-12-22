INSERT INTO `employee` (`id`,`givenName`,`patronymic`,`surName`) VALUES (1,'Фома','Фомич','Чистяков');
INSERT INTO `employee` (`id`,`givenName`,`patronymic`,`surName`) VALUES (2,'Егор','Егорович','Забелин');
INSERT INTO `employee` (`id`,`givenName`,`patronymic`,`surName`) VALUES (3,'Василий','Васильевич','Кузнецов');

INSERT INTO `dct` (`id`,`number`,`year`,`addressee_id`,`performer_id`,`signatory_id`) VALUES (4,1,2020,3,2,1);

TRUNCATE `id_generator`;
INSERT INTO `id_generator` (`next_val`) VALUES (5);
INSERT INTO `id_generator` (`next_val`) VALUES (5);
