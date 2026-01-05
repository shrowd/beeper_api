CREATE TABLE users(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(32) NOT NULL,
    last_name  VARCHAR(32) NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(60) NOT NULL,
    role       VARCHAR(12) NOT NULL
);