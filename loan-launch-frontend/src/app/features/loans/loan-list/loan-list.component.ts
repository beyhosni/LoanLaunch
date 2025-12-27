import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-loan-list',
    standalone: true,
    imports: [CommonModule],
    template: `
    <h2>Loans</h2>
    <p>Loan list will be here.</p>
  `
})
export class LoanListComponent { }
