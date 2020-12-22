create table Dct (id bigint not null, number integer, year integer, addressee_id bigint, performer_id bigint, signatory_id bigint, primary key (id)) engine=InnoDB;
create table Employee (id bigint not null, givenName varchar(255), patronymic varchar(255), surName varchar(255), primary key (id)) engine=InnoDB;
create table ID_GENERATOR (next_val bigint) engine=InnoDB;
INSERT INTO `id_generator` (`next_val`) VALUES (1);
INSERT INTO `id_generator` (`next_val`) VALUES (1);
alter table Dct add constraint FKsfu2b33wk6x6ka27ijcmta15v foreign key (addressee_id) references Employee (id);
alter table Dct add constraint FKk1u6ys66cp5hhxc2ndk5uw5w0 foreign key (performer_id) references Employee (id);
alter table Dct add constraint FKg64krox094r85giro84k192a7 foreign key (signatory_id) references Employee (id);
