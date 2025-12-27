import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { AuthService } from '../../../core/auth/auth.service';
import { UserProfile } from '../../models/models';

@Component({
    selector: 'app-layout',
    standalone: true,
    imports: [
        CommonModule,
        RouterModule,
        MatToolbarModule,
        MatSidenavModule,
        MatListModule,
        MatIconModule,
        MatButtonModule,
        MatMenuModule
    ],
    template: `
    <mat-sidenav-container class="sidenav-container">
      <mat-sidenav #drawer mode="side" opened class="sidenav">
        <div class="logo">
          <h2>🚀 LoanLaunch</h2>
        </div>
        
        <mat-nav-list>
          <a mat-list-item routerLink="/orgs" routerLinkActive="active">
            <mat-icon matListItemIcon>business</mat-icon>
            <span matListItemTitle>Organizations</span>
          </a>
        </mat-nav-list>
      </mat-sidenav>

      <mat-sidenav-content>
        <mat-toolbar color="primary">
          <span class="spacer"></span>
          
          <button mat-button [matMenuTriggerFor]="userMenu" *ngIf="userProfile">
            <mat-icon>account_circle</mat-icon>
            {{ userProfile.email }}
          </button>
          
          <mat-menu #userMenu="matMenu">
            <button mat-menu-item disabled>
              <mat-icon>person</mat-icon>
              <span>{{ userProfile?.name || userProfile?.email }}</span>
            </button>
            <mat-divider></mat-divider>
            <button mat-menu-item (click)="logout()">
              <mat-icon>logout</mat-icon>
              <span>Logout</span>
            </button>
          </mat-menu>
        </mat-toolbar>

        <div class="content">
          <router-outlet></router-outlet>
        </div>
      </mat-sidenav-content>
    </mat-sidenav-container>
  `,
    styles: [`
    .sidenav-container {
      height: 100vh;
    }

    .sidenav {
      width: 250px;
      background-color: #f5f5f5;
    }

    .logo {
      padding: 20px;
      text-align: center;
      border-bottom: 1px solid #ddd;
    }

    .logo h2 {
      margin: 0;
      font-size: 1.5rem;
      color: #333;
    }

    mat-nav-list {
      padding-top: 20px;
    }

    mat-nav-list a {
      margin: 4px 8px;
      border-radius: 4px;
    }

    mat-nav-list a.active {
      background-color: #e0e0e0;
    }

    .spacer {
      flex: 1 1 auto;
    }

    .content {
      padding: 20px;
      min-height: calc(100vh - 64px);
      background-color: #fafafa;
    }

    mat-toolbar {
      position: sticky;
      top: 0;
      z-index: 10;
    }
  `]
})
export class LayoutComponent implements OnInit {
    userProfile: UserProfile | null = null;

    constructor(private authService: AuthService) { }

    ngOnInit() {
        this.authService.userProfile$.subscribe(profile => {
            this.userProfile = profile;
        });
    }

    logout() {
        this.authService.logout();
    }
}
