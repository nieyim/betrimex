CREATE TABLE product
(
    id                  CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    company             VARCHAR(255) NULL,
    machine_id          VARCHAR(50) NULL,
    lot_id              VARCHAR(255) NULL,
    lot_id_detail              VARCHAR(255) NULL,
    lot_number          VARCHAR(255) NULL,
    red_card_lot        VARCHAR(50) NULL,
    vehicle_plate       VARCHAR(255) NULL,
    quantity            INT NULL,
    count_type          VARCHAR(255) NULL,
    is_sync             BOOLEAN              DEFAULT FALSE,
    video_path          VARCHAR(255) NULL,
    video_transfer_path VARCHAR(255) NULL,
    start_time          DATETIME NULL,
    end_time            DATETIME NULL,
    warehouse_staff     VARCHAR(255) NULL,
    status              VARCHAR(255) NULL,
    is_send_to_cloud    BOOLEAN              DEFAULT FALSE,
    qr_data_id          BIGINT NULL,
    created_at          DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME             DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_product_result_qr_data
        FOREIGN KEY (qr_data_id)
            REFERENCES qr_data (id)
            ON DELETE SET NULL
            ON UPDATE CASCADE
);

CREATE INDEX idx_product_created_at ON product (created_at);
