import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';

@Component({
    selector: 'app-callback',
    standalone: true,
    imports: [CommonModule],
    template: `
    <div class="callback-container">
      <div class="spinner"></div>
      <p>Authenticating...</p>
    </div>
  `,
    styles: [`
    .callback-container {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
    }

    .spinner {
      border: 4px solid #f3f3f3;
      border-top: 4px solid #3498db;
      border-radius: 50%;
      width: 40px;
      height: 40px;
      animation: spin 1s linear infinite;
    }

    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }

    p {
      margin-top: 20px;
      font-size: 18px;
      color: #666;
    }
  `]
})
export class CallbackComponent implements OnInit {
    constructor(
        private authService: AuthService,
        private router: Router
    ) { }

    async ngOnInit() {
        const success = await this.authService.initAuth();

        if (success) {
            this.router.navigate(['/orgs']);
        } else {
            this.router.navigate(['/login']);
        }
    }
}
