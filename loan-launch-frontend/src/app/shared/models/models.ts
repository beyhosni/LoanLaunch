export interface Organization {
    id: string;
    name: string;
    legalName: string;
    taxId: string;
    industry: string;
    email: string;
    phone?: string;
    address?: string;
    city?: string;
    country?: string;
    status: 'PENDING' | 'ACTIVE' | 'SUSPENDED';
    createdAt: string;
    updatedAt: string;
}

export interface LoanApplication {
    id: string;
    applicationNumber: string;
    organizationId: string;
    requestedAmount: number;
    requestedTermMonths: number;
    purpose: string;
    status: 'DRAFT' | 'SUBMITTED' | 'UNDER_REVIEW' | 'APPROVED' | 'REJECTED' | 'DISBURSED';
    decisionId?: string;
    submittedAt?: string;
    createdAt: string;
    updatedAt: string;
}

export interface LoanDecision {
    id: string;
    loanApplicationId: string;
    decision: 'APPROVED' | 'REJECTED' | 'MANUAL_REVIEW';
    decisionReason?: string;
    approvedAmount?: number;
    interestRate?: number;
    termMonths?: number;
    monthlyPayment?: number;
    totalRepayment?: number;
    riskScore?: number;
    createdAt: string;
}

export interface AuditEvent {
    id: string;
    eventId: string;
    eventType: string;
    aggregateId: string;
    aggregateType: string;
    payload: any;
    metadata?: any;
    timestamp: string;
    createdAt: string;
}

export interface ApiResponse<T> {
    success: boolean;
    message?: string;
    data: T;
    timestamp?: string;
}

export interface PageResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
}

export interface CreateOrganizationRequest {
    name: string;
    legalName: string;
    taxId: string;
    industry: string;
    email: string;
    phone?: string;
    address?: string;
    city?: string;
    country?: string;
}

export interface CreateLoanRequest {
    organizationId: string;
    requestedAmount: number;
    requestedTermMonths: number;
    purpose: string;
}

export interface UserProfile {
    sub: string;
    email: string;
    name?: string;
    preferred_username?: string;
    roles?: string[];
}
