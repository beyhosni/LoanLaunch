{
  "apiGateway": {
    "baseUrl": "http://localhost:8080"
  },
  "oidc": {
    "issuer": "http://localhost:8180/realms/loanllaunch",
    "clientId": "loanllaunch-frontend",
    "redirectUri": "http://localhost:4200/callback",
    "postLogoutRedirectUri": "http://localhost:4200",
    "scope": "openid profile email",
    "responseType": "code",
    "requireHttps": false,
    "showDebugInformation": true,
    "useSilentRefresh": true,
    "silentRefreshRedirectUri": "http://localhost:4200/silent-refresh.html",
    "sessionChecksEnabled": true
  },
  "features": {
    "mockMode": false
  }
}
