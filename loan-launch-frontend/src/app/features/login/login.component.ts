import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { AuthService } from '../../core/auth/auth.service';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, MatButtonModule, MatCardModule],
    template: `
    <div class="login-container">
      <mat-card class="login-card">
        <mat-card-header>
          <mat-card-title>
            <h1>🚀 LoanLaunch</h1>
          </mat-card-title>
          <mat-card-subtitle>AI-Powered Intelligent Lending Platform</mat-card-subtitle>
        </mat-card-header>
        
        <mat-card-content>
          <p>Welcome to LoanLaunch. Please sign in to continue.</p>
        </mat-card-content>
        
        <mat-card-actions>
          <button mat-raised-button color="primary" (click)="login()" class="login-button">
            Sign in with Keycloak
          </button>
        </mat-card-actions>
      </mat-card>
    </div>
  `,
    styles: [`
    .login-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    }

    .login-card {
      max-width: 400px;
      width: 100%;
      margin: 20px;
      text-align: center;
    }

    mat-card-header {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 20px 0;
    }

    h1 {
      margin: 0;
      font-size: 2.5rem;
    }

    mat-card-content {
      padding: 20px 0;
    }

    .login-button {
      width: 100%;
      height: 48px;
      font-size: 16px;
    }
  `]
})
export class LoginComponent {
    constructor(
        private authService: AuthService,
        private router: Router
    ) {
        // If already authenticated, redirect to orgs
        if (this.authService.isAuthenticated()) {
            this.router.navigate(['/orgs']);
        }
    }

    login(): void {
        this.authService.login();
    }
}
