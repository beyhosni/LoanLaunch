-- V1__Create_notifications_table.sql
CREATE TABLE notifications (
    id UUID PRIMARY KEY,
    recipient_email VARCHAR(255) NOT NULL,
    notification_type VARCHAR(100) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    sent_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_notifications_status ON notifications(status);
