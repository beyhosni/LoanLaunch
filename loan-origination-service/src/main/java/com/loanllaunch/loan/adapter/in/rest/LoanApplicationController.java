package com.loanllaunch.loan.adapter.in.rest;

import com.loanllaunch.common.dto.ApiResponse;
import com.loanllaunch.loan.application.service.LoanApplicationService;
import com.loanllaunch.loan.domain.model.LoanApplication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
@Tag(name = "Loan Applications", description = "Loan application management APIs")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    @PostMapping
    @Operation(summary = "Create a new loan application")
    public ResponseEntity<ApiResponse<LoanApplication>> createLoanApplication(
            @RequestParam UUID organizationId,
            @RequestParam BigDecimal requestedAmount,
            @RequestParam Integer requestedTermMonths,
            @RequestParam String purpose) {
        
        LoanApplication application = loanApplicationService.createLoanApplication(
                organizationId, requestedAmount, requestedTermMonths, purpose);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(application, "Loan application created successfully"));
    }

    @PostMapping("/{id}/submit")
    @Operation(summary = "Submit loan application for review")
    public ResponseEntity<ApiResponse<LoanApplication>> submitLoanApplication(@PathVariable UUID id) {
        LoanApplication application = loanApplicationService.submitLoanApplication(id);
        return ResponseEntity.ok(ApiResponse.success(application, "Loan application submitted successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan application by ID")
    public ResponseEntity<ApiResponse<LoanApplication>> getLoanApplication(@PathVariable UUID id) {
        LoanApplication application = loanApplicationService.getLoanApplication(id);
        return ResponseEntity.ok(ApiResponse.success(application));
    }

    @GetMapping("/organization/{organizationId}")
    @Operation(summary = "Get loan applications by organization")
    public ResponseEntity<ApiResponse<List<LoanApplication>>> getLoanApplicationsByOrganization(
            @PathVariable UUID organizationId) {
        List<LoanApplication> applications = loanApplicationService.getLoanApplicationsByOrganization(organizationId);
        return ResponseEntity.ok(ApiResponse.success(applications));
    }
}
