-- V1__Create_raw_transactions_table.sql
CREATE TABLE raw_transactions (
    id UUID PRIMARY KEY,
    organization_id UUID NOT NULL,
    transaction_date DATE NOT NULL,
    description TEXT,
    amount DECIMAL(15,2) NOT NULL,
    transaction_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_raw_transactions_org ON raw_transactions(organization_id);
