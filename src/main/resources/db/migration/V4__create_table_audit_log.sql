CREATE TABLE audit_log
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    username               VARCHAR(255) NULL,
    action                 VARCHAR(255) NULL,
    resource               VARCHAR(255) NULL,
    details                TEXT,
    status                 VARCHAR(50) NULL,
    current_json           text,
    new_json               text,
    external_response_json text,
    created_at             DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at             DATETIME DEFAULT CURRENT_TIMESTAMP
);