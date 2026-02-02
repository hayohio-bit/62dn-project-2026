-- Add sync history table
CREATE TABLE IF NOT EXISTS sync_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sync_trigger_type VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    records_synced INT DEFAULT 0,
    error_message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
