INSERT INTO `employee` (`id`,`givenName`,`patronymic`,`surName`) VALUES (1,'����','�����','��������');
INSERT INTO `employee` (`id`,`givenName`,`patronymic`,`surName`) VALUES (2,'����','��������','�������');
INSERT INTO `employee` (`id`,`givenName`,`patronymic`,`surName`) VALUES (3,'�������','����������','��������');

INSERT INTO `dct` (`id`,`number`,`year`,`addressee_id`,`performer_id`,`signatory_id`) VALUES (4,1,2020,3,2,1);

TRUNCATE `id_generator`;
INSERT INTO `id_generator` (`next_val`) VALUES (5);
INSERT INTO `id_generator` (`next_val`) VALUES (5);
