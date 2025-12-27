import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
    Organization,
    LoanApplication,
    LoanDecision,
    AuditEvent,
    ApiResponse,
    PageResponse,
    CreateOrganizationRequest,
    CreateLoanRequest
} from '../../shared/models/models';

@Injectable({
    providedIn: 'root'
})
export class ApiService {
    private baseUrl = environment.apiGateway.baseUrl;

    constructor(private http: HttpClient) { }

    // Organizations
    getOrganizations(): Observable<ApiResponse<Organization[]>> {
        return this.http.get<ApiResponse<Organization[]>>(`${this.baseUrl}/api/organizations`);
    }

    getOrganization(id: string): Observable<ApiResponse<Organization>> {
        return this.http.get<ApiResponse<Organization>>(`${this.baseUrl}/api/organizations/${id}`);
    }

    createOrganization(request: CreateOrganizationRequest): Observable<ApiResponse<Organization>> {
        return this.http.post<ApiResponse<Organization>>(`${this.baseUrl}/api/organizations`, request);
    }

    updateOrganization(id: string, request: Partial<CreateOrganizationRequest>): Observable<ApiResponse<Organization>> {
        return this.http.put<ApiResponse<Organization>>(`${this.baseUrl}/api/organizations/${id}`, request);
    }

    deleteOrganization(id: string): Observable<ApiResponse<void>> {
        return this.http.delete<ApiResponse<void>>(`${this.baseUrl}/api/organizations/${id}`);
    }

    // Loan Applications
    getLoanApplications(organizationId: string): Observable<ApiResponse<LoanApplication[]>> {
        return this.http.get<ApiResponse<LoanApplication[]>>(
            `${this.baseUrl}/api/loans/organization/${organizationId}`
        );
    }

    getLoanApplication(id: string): Observable<ApiResponse<LoanApplication>> {
        return this.http.get<ApiResponse<LoanApplication>>(`${this.baseUrl}/api/loans/${id}`);
    }

    createLoanApplication(request: CreateLoanRequest): Observable<ApiResponse<LoanApplication>> {
        const params = new HttpParams()
            .set('organizationId', request.organizationId)
            .set('requestedAmount', request.requestedAmount.toString())
            .set('requestedTermMonths', request.requestedTermMonths.toString())
            .set('purpose', request.purpose);

        return this.http.post<ApiResponse<LoanApplication>>(`${this.baseUrl}/api/loans`, null, { params });
    }

    submitLoanApplication(id: string): Observable<ApiResponse<LoanApplication>> {
        return this.http.post<ApiResponse<LoanApplication>>(`${this.baseUrl}/api/loans/${id}/submit`, null);
    }

    refreshLoanData(organizationId: string, loanId: string): Observable<ApiResponse<any>> {
        return this.http.post<ApiResponse<any>>(
            `${this.baseUrl}/api/ingestion/sync/${organizationId}`,
            null
        );
    }

    // Audit Events
    getAuditEvents(organizationId: string, page: number = 0, size: number = 20): Observable<ApiResponse<AuditEvent[]>> {
        const params = new HttpParams()
            .set('page', page.toString())
            .set('size', size.toString());

        return this.http.get<ApiResponse<AuditEvent[]>>(
            `${this.baseUrl}/api/audit/organization/${organizationId}`,
            { params }
        );
    }
}
