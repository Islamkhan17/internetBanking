DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name            VARCHAR                           NOT NULL,
    lastname        VARCHAR                           NOT NULL,
    patronymic      VARCHAR                           NOT NULL,
    password        VARCHAR                           NOT NULL,
    address         VARCHAR                           NOT NULL,
    iin             INTEGER                           NOT NULL,
    phone           INTEGER                           NOT NULL
);
