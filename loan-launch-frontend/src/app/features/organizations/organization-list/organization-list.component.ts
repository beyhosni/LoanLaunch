import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-organization-list',
    standalone: true,
    imports: [CommonModule],
    template: `
    <h2>Organizations</h2>
    <p>Organization list will be here.</p>
  `
})
export class OrganizationListComponent { }
