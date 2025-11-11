CREATE TABLE tokens (
                        id CHAR(36) NOT NULL PRIMARY KEY,
                        token VARCHAR(255),
                        token_type VARCHAR(50),
                        expired BOOLEAN NOT NULL DEFAULT FALSE,
                        revoked BOOLEAN NOT NULL DEFAULT FALSE,
                        user_id CHAR(36),
                        CONSTRAINT fk_tokens_user FOREIGN KEY (user_id) REFERENCES users(id)
);