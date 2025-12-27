-- V1__Create_organizations_table.sql

CREATE TABLE organizations (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    legal_name VARCHAR(255) NOT NULL,
    tax_id VARCHAR(50) UNIQUE NOT NULL,
    industry VARCHAR(100),
    founded_date DATE,
    employee_count INTEGER,
    annual_revenue DECIMAL(15,2),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(50),
    postal_code VARCHAR(20),
    country VARCHAR(50),
    phone VARCHAR(50),
    email VARCHAR(255),
    website VARCHAR(255),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_organizations_tax_id ON organizations(tax_id);
CREATE INDEX idx_organizations_status ON organizations(status);
CREATE INDEX idx_organizations_created_at ON organizations(created_at);

COMMENT ON TABLE organizations IS 'Stores small business organization information';
COMMENT ON COLUMN organizations.status IS 'Organization verification status: PENDING, VERIFIED, REJECTED';
