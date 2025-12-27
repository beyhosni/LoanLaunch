-- V1__Create_risk_assessments_table.sql
CREATE TABLE risk_assessments (
    id UUID PRIMARY KEY,
    organization_id UUID NOT NULL,
    overall_score INTEGER NOT NULL,
    risk_level VARCHAR(50) NOT NULL,
    factors JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_risk_assessments_org ON risk_assessments(organization_id);
