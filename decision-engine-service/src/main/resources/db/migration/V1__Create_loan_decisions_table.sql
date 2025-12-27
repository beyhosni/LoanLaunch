-- V1__Create_loan_decisions_table.sql
CREATE TABLE loan_decisions (
    id UUID PRIMARY KEY,
    organization_id UUID NOT NULL,
    loan_application_id UUID NOT NULL,
    decision VARCHAR(50) NOT NULL,
    decision_reason TEXT,
    approved_amount DECIMAL(15,2),
    interest_rate DECIMAL(5,2),
    term_months INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_loan_decisions_app ON loan_decisions(loan_application_id);
