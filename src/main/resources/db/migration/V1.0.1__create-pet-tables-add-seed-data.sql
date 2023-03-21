create table adopet_pet
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
        foreign key (owner_id) references adopet_user (id)
);

insert into adopet_pet (id, city, creation_date, name, personality, profile_picture_url, size, state, status,
                        status_date, owner_id, species)
values (1, 'Batatais', '2023-03-16 00:00:00', 'Sample Pet', 'Default.', 'https://example.com/pet.jpg', 'MEDIUM', 'SP',
        'AVAILABLE', '2023-03-16 00:00:00', 1, 'DOG');
