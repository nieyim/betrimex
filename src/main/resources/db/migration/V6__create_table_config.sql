CREATE TABLE config
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    config_key   VARCHAR(100) NULL,
    config_value TEXT NULL,
    description  TEXT NULL,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (1, 'TEMPLATE_PATH', 'D:\\AITS_Project\\Betrimex\\betrimex-be\\src\\main\\resources\\reports\\templates\\Betrimex.jasper', 'Betrimex Report', '2025-08-04 12:00:07', '2025-08-04 12:00:07');
INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (2, 'HISTORY_LOT_TEMPLATE_PATH', 'D:\\AITS_Project\\Betrimex\\betrimex-be\\src\\main\\resources\\reports\\templates\\BetrimexHistoryReport.jasper', 'Betrimex Report', '2025-08-04 12:00:07', '2025-08-04 12:00:07');
INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (3, 'HISTORY_DETAIL_LOT_TEMPLATE_PATH', 'C:\\\\Users\\\\admin\\\\Documents\\\\GitHub\\\\betrimex-be\\\\src\\\\main\\\\resources\\\\reports\\\\templates\\\\betrimex_report.jasper', 'Betrimex Report', '2025-08-04 12:00:07', '2025-08-04 15:09:21');
INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (4, 'PDF_REPORT', 'report.pdf', 'PDF Report', '2025-08-04 12:00:07', '2025-08-04 12:00:07');
INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (5, 'EXCEL_REPORT', 'report.xlsx', 'Excel Report', '2025-08-04 12:00:07', '2025-08-04 12:00:07');
INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (6, 'AMAZON_UPLOAD_FILE', '', '', '2025-08-04 12:00:07', '2025-08-04 12:00:07');
INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (7, 'CHUNK_SIZE', '1024 * 1024L', '', '2025-08-04 12:00:07', '2025-08-04 12:00:07');
INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (8, 'BETRIMEX_API_CURL', '', '', '2025-08-04 12:00:07', '2025-08-04 14:06:20');
INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (9, 'BETRIMEX_BASIC_AUTH', '', '', '2025-08-04 12:00:07', '2025-08-04 12:01:55');
INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (10, 'MC_MACHINE_NAME', 'MANUAL_INPUT', '', '2025-08-04 12:00:07', '2025-08-04 12:00:07');
INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (11, 'AI_COUNT', 'AI_COUNT', '', '2025-08-04 12:00:07', '2025-08-04 12:00:07');
INSERT INTO `config` (`id`, `config_key`, `config_value`, `description`, `created_at`, `updated_at`) VALUES (12, 'MANUAL_INPUT', 'MANUAL_INPUT', NULL, '2025-08-04 13:36:50', '2025-08-04 13:36:50');
