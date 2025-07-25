create table users
(
    id         bigint generated always as identity primary key,
    username   varchar not null unique,
    email      varchar not null unique,
    password   varchar not null,
    first_name varchar not null,
    last_name  varchar not null,
    role       varchar not null,
    created_at date    not null
);

INSERT INTO users (username, email, password, first_name, last_name, role, created_at)
VALUES ('user1', 'user1@gmail.com', '$2a$12$Xzbbo.fnPujPZQwqfKJGx.2W8eCVO0/qsI2d8pRrpcB/a4AnJK41y',
        'User1', 'User1', 'ROLE_USER', CURRENT_DATE);

INSERT INTO users (username, email, password, first_name, last_name, role, created_at)
VALUES ('user2', 'user2@gmail.com', '$2a$12$fXIVgq/JV3UwBPBvsuR1wOGGjIWa97m.qm97ukpRTN7dFRdZiKGQy',
        'User2', 'User2', 'ROLE_USER', CURRENT_DATE);

INSERT INTO users (username, email, password, first_name, last_name, role, created_at)
VALUES ('user3', 'user3@gmail.com', '$2a$12$lL3Wmy6HwkP4vQCnInCZduOe7n7B1wicrzxuRcmMLGe9lyXinh88i',
        'User3', 'User3', 'ROLE_USER', CURRENT_DATE);

INSERT INTO users (username, email, password, first_name, last_name, role, created_at)
VALUES ('user4', 'user4@gmail.com', '$2a$12$sVr77YyYV13LWBpz0tE0/.hlZIUli1KEwCiegojpUG8wULd7qa9JO',
        'User4', 'User4', 'ROLE_USER', CURRENT_DATE);

INSERT INTO users (username, email, password, first_name, last_name, role, created_at)
VALUES ('user5', 'user5@gmail.com', '$2a$12$8gQ92H926r7Onp8wzhImx.RE4yyGTVkeIUSnrKKq5yA/efaLj6Unu',
        'User5', 'User5', 'ROLE_USER', CURRENT_DATE);

INSERT INTO users (username, email, password, first_name, last_name, role, created_at)
VALUES ('admin1', 'admin1@gmail.com', '$2a$12$WogBK6h6nCKepksSwjKJROTCZ1OxBT6eixEhG3Knu3x6.KNXGyyQq',
        'Admin1', 'Admin1', 'ROLE_ADMIN', CURRENT_DATE);

INSERT INTO users (username, email, password, first_name, last_name, role, created_at)
VALUES ('admin2', 'admin2@gmail.com', '$2a$12$GCfqUEje882oNrZiTXcUGu6CXr93xH7VPI7C7r3ZSTtGgKd/C6GOS',
        'Admin2', 'Admin2', 'ROLE_ADMIN', CURRENT_DATE);

INSERT INTO users (username, email, password, first_name, last_name, role, created_at)
VALUES ('admin3', 'admin3@gmail.com', '$2a$12$c1MECqUWfUDniUNozLF7vecIuvoLK5j0xh73zCEnZmMnsBi9r6l12',
        'Admin3', 'Admin3', 'ROLE_ADMIN', CURRENT_DATE);

INSERT INTO users (username, email, password, first_name, last_name, role, created_at)
VALUES ('admin4', 'admin4@gmail.com', '$2a$12$PoChh8rHWKcfDoTyC8ssd.bMs.pjIoaLiYli5A/npueeAq7AdGoMq',
        'Admin4', 'Admin4', 'ROLE_ADMIN', CURRENT_DATE);

INSERT INTO users (username, email, password, first_name, last_name, role, created_at)
VALUES ('admin5', 'admin5@gmail.com', '$2a$12$OPXiVMIXtbvqJ//RmjdZU.AKTK92ROtpAY15rcss1nuGZ2JkjHNma',
        'Admin5', 'Admin5', 'ROLE_ADMIN', CURRENT_DATE);