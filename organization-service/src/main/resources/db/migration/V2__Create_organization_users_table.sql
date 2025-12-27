-- V2__Create_organization_users_table.sql

CREATE TABLE organization_users (
    id UUID PRIMARY KEY,
    organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    user_id UUID NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_org_user UNIQUE (organization_id, user_id)
);

CREATE INDEX idx_organization_users_org_id ON organization_users(organization_id);
CREATE INDEX idx_organization_users_user_id ON organization_users(user_id);

COMMENT ON TABLE organization_users IS 'Maps users to organizations with roles';
COMMENT ON COLUMN organization_users.role IS 'User role within organization: OWNER, ADMIN, MEMBER';
