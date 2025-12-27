# LoanLaunch Frontend

Angular 17+ frontend application for LoanLaunch intelligent lending platform.

## 🚀 Quick Start

### Prerequisites

- Node.js 18+ 
- npm 9+
- Angular CLI 17+

### Installation

```bash
# Install dependencies
npm install

# Start development server
npm start

# Open browser
http://localhost:4200
```

### Configuration

Edit `src/environments/environment.ts`:

```typescript
{
  "apiGateway": {
    "baseUrl": "http://localhost:8080"  // API Gateway URL
  },
  "oidc": {
    "issuer": "http://localhost:8180/realms/loanllaunch",  // Keycloak URL
    "clientId": "loanllaunch-frontend",
    // ... other OIDC settings
  }
}
```

## 🏗️ Architecture

### Structure

```
src/
├── app/
│   ├── core/                  # Singleton services
│   │   ├── auth/             # OIDC authentication
│   │   ├── guards/           # Route guards
│   │   ├── interceptors/     # HTTP interceptors
│   │   └── services/         # API services
│   ├── shared/               # Shared components
│   │   ├── components/       # Reusable UI components
│   │   └── models/           # TypeScript interfaces
│   └── features/             # Feature modules
│       ├── login/
│       ├── organizations/
│       ├── loans/
│       └── audit/
├── environments/             # Environment configs
└── assets/                   # Static assets
```

### Key Features

- ✅ **OIDC Authentication** - Keycloak compatible with PKCE flow
- ✅ **Correlation ID** - Auto-generated for request tracing
- ✅ **Token Management** - Secure in-memory storage with refresh
- ✅ **Route Guards** - Protected routes
- ✅ **HTTP Interceptors** - Auto token injection
- ✅ **Angular Material** - Modern UI components
- ✅ **Standalone Components** - Angular 17+ architecture

## 🔐 Authentication

### Keycloak Setup

1. Create realm: `loanllaunch`
2. Create client: `loanllaunch-frontend`
3. Configure client:
   - Client Protocol: `openid-connect`
   - Access Type: `public`
   - Valid Redirect URIs: `http://localhost:4200/*`
   - Web Origins: `http://localhost:4200`
   - PKCE: `Enabled`

### Login Flow

```
1. User clicks "Login"
2. Redirect to Keycloak
3. User authenticates
4. Redirect back with authorization code
5. Exchange code for tokens (PKCE)
6. Store tokens in memory
7. Auto-refresh before expiry
```

## 📡 API Integration

### Headers Sent

Every API request includes:

```http
Authorization: Bearer <access_token>
X-Correlation-Id: <uuid-v4>
Content-Type: application/json
```

### API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/orgs` | GET | List organizations |
| `/api/orgs` | POST | Create organization |
| `/api/orgs/{id}/loans` | GET | List loans |
| `/api/orgs/{id}/loans` | POST | Create loan |
| `/api/orgs/{id}/loans/{loanId}` | GET | Get loan details |
| `/api/orgs/{id}/loans/{loanId}/refresh` | POST | Refresh data |
| `/api/orgs/{id}/audit` | GET | Get audit events |

## 🎨 UI Components

### Pages

1. **Login** (`/login`) - OIDC login page
2. **Organizations** (`/orgs`) - List and create organizations
3. **Loans** (`/orgs/:orgId/loans`) - Loan applications list
4. **Loan Details** (`/orgs/:orgId/loans/:loanId`) - Pipeline status & decision
5. **Audit** (`/orgs/:orgId/audit`) - Event log

### Material Components Used

- MatToolbar - Top navigation
- MatSidenav - Sidebar navigation
- MatCard - Content cards
- MatTable - Data tables
- MatDialog - Modals
- MatStepper - Pipeline status
- MatSnackBar - Notifications

## 🧪 Development

### Run Development Server

```bash
npm start
# or
ng serve
```

Navigate to `http://localhost:4200`

### Build

```bash
# Development build
npm run build

# Production build
npm run build:prod
```

### Linting

```bash
npm run lint
```

## 🐳 Docker

### Build Image

```bash
docker build -t loanllaunch-frontend .
```

### Run Container

```bash
docker run -p 4200:80 loanllaunch-frontend
```

## 🔧 Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `API_GATEWAY_URL` | Backend API URL | `http://localhost:8080` |
| `OIDC_ISSUER` | Keycloak issuer URL | `http://localhost:8180/realms/loanllaunch` |
| `OIDC_CLIENT_ID` | OAuth2 client ID | `loanllaunch-frontend` |

## 📝 Mock Mode

For development without backend:

```typescript
// environment.ts
{
  "features": {
    "mockMode": true
  }
}
```

Mock services will return fake data.

## 🚀 Deployment

### Production Build

```bash
npm run build:prod
```

Output: `dist/loan-launch-frontend/`

### Serve with Nginx

```nginx
server {
    listen 80;
    server_name loanllaunch.com;
    root /usr/share/nginx/html;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location /api {
        proxy_pass http://api-gateway:8080;
    }
}
```

## 📚 Documentation

- [Angular Documentation](https://angular.io/docs)
- [Angular Material](https://material.angular.io/)
- [angular-oauth2-oidc](https://github.com/manfredsteyer/angular-oauth2-oidc)

## 🤝 Contributing

1. Create feature branch
2. Make changes
3. Run linter
4. Submit PR

## 📄 License

MIT
