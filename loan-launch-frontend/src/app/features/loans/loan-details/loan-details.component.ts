import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-loan-details',
    standalone: true,
    imports: [CommonModule],
    template: `
    <h2>Loan Details</h2>
    <p>Loan details will be here.</p>
  `
})
export class LoanDetailsComponent { }
