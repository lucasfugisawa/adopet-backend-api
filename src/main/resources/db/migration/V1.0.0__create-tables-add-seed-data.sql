create table adopet.adopet_user
(
    id                  bigint auto_increment
        primary key,
    about               varchar(255) null,
    creation_date       datetime(6)  null,
    email               varchar(255) null,
    enabled             bit          not null,
    name                varchar(255) null,
    password            varchar(255) null,
    profile_picture_url varchar(255) null
);

create table adopet.adopet_pet
(
    id                  bigint auto_increment
        primary key,
    species             varchar(255) null,
    city                varchar(255) null,
    creation_date       datetime(6)  null,
    name                varchar(255) null,
    personality         varchar(255) null,
    profile_picture_url varchar(255) null,
    size                varchar(255) null,
    state               varchar(255) null,
    status              varchar(255) null,
    status_date         datetime(6)  null,
    owner_id            bigint       null,
    constraint FKlh3hpg09v13ba8tx9b0t08u9w
        foreign key (owner_id) references adopet.adopet_user (id)
);

insert into adopet.adopet_user (id, about, creation_date, email, enabled, name, password, profile_picture_url)
values (1, 'Adopet OOTB Admin.', '2023-03-16 18:41:47.343464', 'lucasfugisawa@gmail.com', true, 'Administrator',
        '12345', 'https://fugisawa.com.br/avatar.png');

insert into adopet.adopet_pet (id, city, creation_date, name, personality, profile_picture_url, size, state, status,
                               status_date, owner_id, species)
values (1, 'Batatais', '2023-03-16 00:00:00', 'Sample Pet', 'Default.', 'https://example.com/pet.jpg', 'MEDIUM', 'SP',
        'AVAILABLE', '2023-03-16 00:00:00', 1, 'DOG');

