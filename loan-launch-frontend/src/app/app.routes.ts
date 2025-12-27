import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
    {
        path: '',
        redirectTo: '/orgs',
        pathMatch: 'full'
    },
    {
        path: 'login',
        loadComponent: () => import('./features/login/login.component').then(m => m.LoginComponent)
    },
    {
        path: 'callback',
        loadComponent: () => import('./features/callback/callback.component').then(m => m.CallbackComponent)
    },
    {
        path: '',
        loadComponent: () => import('./shared/components/layout/layout.component').then(m => m.LayoutComponent),
        canActivate: [authGuard],
        children: [
            {
                path: 'orgs',
                loadComponent: () => import('./features/organizations/organization-list/organization-list.component')
                    .then(m => m.OrganizationListComponent)
            },
            {
                path: 'orgs/:orgId/loans',
                loadComponent: () => import('./features/loans/loan-list/loan-list.component')
                    .then(m => m.LoanListComponent)
            },
            {
                path: 'orgs/:orgId/loans/:loanId',
                loadComponent: () => import('./features/loans/loan-details/loan-details.component')
                    .then(m => m.LoanDetailsComponent)
            },
            {
                path: 'orgs/:orgId/audit',
                loadComponent: () => import('./features/audit/audit-list/audit-list.component')
                    .then(m => m.AuditListComponent)
            }
        ]
    },
    {
        path: '**',
        redirectTo: '/orgs'
    }
];
