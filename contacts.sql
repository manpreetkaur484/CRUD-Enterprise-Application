DROP DATABASE IF EXISTS contacts;
CREATE DATABASE contacts;
USE contacts;

create table SEC_USER
(
  userId           BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userName         VARCHAR(36) NOT NULL UNIQUE,
  encryptedPassword VARCHAR(128) NOT NULL,
  ENABLED           BIT NOT NULL 
) ;

create table SEC_ROLE
(
  roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
) ;

create table USER_ROLE
(
  ID      BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

alter table USER_ROLE
  add constraint USER_ROLE_UK unique (userId, roleId);

alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (userId)
  references SEC_USER (userId);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (roleId)
  references SEC_ROLE (roleId);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('member', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('guest', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('admin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
insert into sec_role (roleName)
values ('ROLE_MEMBER');
insert into sec_role (roleName)
values ('ROLE_GUEST');
insert into sec_role (roleName)
values ('ROLE_ADMIN');

insert into user_role (userId, roleId)
values (1, 1);
insert into user_role (userId, roleId)
values (2, 2);
insert into user_role (userId, roleId)
values (3, 3);



create table contact_list
(
  id           INT NOT NULL Primary Key AUTO_INCREMENT,
  name         VARCHAR(255) NOT NULL,
  phoneNumber VARCHAR(20) NOT NULL,
  address      VARCHAR(300) NOT NULL,
  email         VARCHAR(200) NOT NULL,
  role VARCHAR(100) NOT NULL
) ;

COMMIT;

