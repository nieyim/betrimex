CREATE TABLE users
(
    id         CHAR(36)     NOT NULL PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(255),
    role       ENUM('USER','ADMIN') NOT NULL DEFAULT 'USER',
    enabled    BOOLEAN   DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
