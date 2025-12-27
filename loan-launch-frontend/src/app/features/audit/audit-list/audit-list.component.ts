import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-audit-list',
    standalone: true,
    imports: [CommonModule],
    template: `
    <h2>Audit Log</h2>
    <p>Audit log will be here.</p>
  `
})
export class AuditListComponent { }
