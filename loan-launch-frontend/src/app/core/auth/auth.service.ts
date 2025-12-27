import { Injectable } from '@angular/core';
import { OAuthService, AuthConfig } from 'angular-oauth2-oidc';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { UserProfile } from '../../shared/models/models';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
    public isAuthenticated$: Observable<boolean> = this.isAuthenticatedSubject.asObservable();

    private userProfileSubject = new BehaviorSubject<UserProfile | null>(null);
    public userProfile$: Observable<UserProfile | null> = this.userProfileSubject.asObservable();

    constructor(
        private oauthService: OAuthService,
        private router: Router
    ) {
        this.configureOAuth();
    }

    private configureOAuth(): void {
        const authConfig: AuthConfig = {
            issuer: environment.oidc.issuer,
            redirectUri: environment.oidc.redirectUri,
            clientId: environment.oidc.clientId,
            responseType: environment.oidc.responseType,
            scope: environment.oidc.scope,
            showDebugInformation: environment.oidc.showDebugInformation,
            requireHttps: environment.oidc.requireHttps,
            postLogoutRedirectUri: environment.oidc.postLogoutRedirectUri,
            useSilentRefresh: environment.oidc.useSilentRefresh,
            silentRefreshRedirectUri: environment.oidc.silentRefreshRedirectUri,
            sessionChecksEnabled: environment.oidc.sessionChecksEnabled
        };

        this.oauthService.configure(authConfig);
        this.oauthService.setupAutomaticSilentRefresh();

        // Handle token events
        this.oauthService.events.subscribe(event => {
            if (event.type === 'token_received' || event.type === 'token_refreshed') {
                this.updateAuthenticationState();
            } else if (event.type === 'session_terminated' || event.type === 'session_error') {
                this.isAuthenticatedSubject.next(false);
                this.userProfileSubject.next(null);
            }
        });
    }

    public async initAuth(): Promise<boolean> {
        try {
            await this.oauthService.loadDiscoveryDocumentAndTryLogin();

            if (this.oauthService.hasValidAccessToken()) {
                this.updateAuthenticationState();
                return true;
            }

            return false;
        } catch (error) {
            console.error('Error during authentication initialization:', error);
            return false;
        }
    }

    public login(): void {
        this.oauthService.initCodeFlow();
    }

    public logout(): void {
        this.oauthService.logOut();
        this.isAuthenticatedSubject.next(false);
        this.userProfileSubject.next(null);
        this.router.navigate(['/login']);
    }

    public getAccessToken(): string | null {
        return this.oauthService.getAccessToken();
    }

    public isAuthenticated(): boolean {
        return this.oauthService.hasValidAccessToken();
    }

    public getUserProfile(): UserProfile | null {
        if (!this.isAuthenticated()) {
            return null;
        }

        const claims = this.oauthService.getIdentityClaims() as any;
        if (!claims) {
            return null;
        }

        return {
            sub: claims.sub,
            email: claims.email,
            name: claims.name,
            preferred_username: claims.preferred_username,
            roles: claims.realm_access?.roles || []
        };
    }

    private updateAuthenticationState(): void {
        const isAuth = this.isAuthenticated();
        this.isAuthenticatedSubject.next(isAuth);

        if (isAuth) {
            const profile = this.getUserProfile();
            this.userProfileSubject.next(profile);
        }
    }

    public hasRole(role: string): boolean {
        const profile = this.getUserProfile();
        return profile?.roles?.includes(role) || false;
    }
}
