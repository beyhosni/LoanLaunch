-- V1__Create_loan_applications_table.sql

CREATE TABLE loan_applications (
    id UUID PRIMARY KEY,
    application_number VARCHAR(50) UNIQUE NOT NULL,
    organization_id UUID NOT NULL,
    requested_amount DECIMAL(15,2) NOT NULL,
    requested_term_months INTEGER NOT NULL,
    purpose TEXT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    decision_id UUID,
    submitted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_loan_applications_org_id ON loan_applications(organization_id);
CREATE INDEX idx_loan_applications_status ON loan_applications(status);
CREATE INDEX idx_loan_applications_app_number ON loan_applications(application_number);

COMMENT ON TABLE loan_applications IS 'Stores loan application information';
