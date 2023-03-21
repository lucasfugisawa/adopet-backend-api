CREATE TABLE adopet_role
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name   VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
INSERT INTO adopet_role (id, name, description)
VALUES (1, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');
INSERT INTO adopet_role (id, name, description)
VALUES (2, 'STANDARD_USER', 'Standard User - Has no admin rights');

create table adopet_user
(
    id                  bigint       not null auto_increment
        primary key,
    about               varchar(255) null,
    creation_date       datetime(6)  null,
    email               varchar(255) null,
    enabled             bit          not null,
    name                varchar(255) null,
    password            varchar(255) null,
    profile_picture_url varchar(255) null
);

insert into adopet_user (id, about, creation_date, email, enabled, name, password, profile_picture_url)
values (1, 'Adopet OOTB Admin.', '2023-03-16 18:41:47.343464', 'admin@adopet.com.br', true, 'Adopet Administrator',
        '$2a$10$4aLMQwpH5C2KDcU2iYoIJ.aoP4eJPcdUtB.WduvYTHNDtP1z7JlO.', 'https://adopet.com.br/adopet-logo.png');

CREATE TABLE adopet_user_role
(
    id      BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES adopet_user (id),
    FOREIGN KEY (role_id) REFERENCES adopet_role (id)
);
INSERT INTO adopet_user_role(user_id, role_id)
VALUES (1, 1);
INSERT INTO adopet_user_role(user_id, role_id)
VALUES (1, 2);
