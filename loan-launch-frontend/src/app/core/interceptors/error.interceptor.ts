import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(
        private router: Router,
        private snackBar: MatSnackBar
    ) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            catchError((error: HttpErrorResponse) => {
                let errorMessage = 'An error occurred';

                if (error.error instanceof ErrorEvent) {
                    // Client-side error
                    errorMessage = `Error: ${error.error.message}`;
                } else {
                    // Server-side error
                    switch (error.status) {
                        case 401:
                            errorMessage = 'Unauthorized. Please login again.';
                            this.router.navigate(['/login']);
                            break;
                        case 403:
                            errorMessage = 'Access denied. You don\'t have permission.';
                            break;
                        case 404:
                            errorMessage = 'Resource not found.';
                            break;
                        case 500:
                            errorMessage = 'Internal server error. Please try again later.';
                            break;
                        default:
                            errorMessage = error.error?.message || `Error: ${error.status} ${error.statusText}`;
                    }
                }

                this.snackBar.open(errorMessage, 'Close', {
                    duration: 5000,
                    horizontalPosition: 'end',
                    verticalPosition: 'top',
                    panelClass: ['error-snackbar']
                });

                return throwError(() => error);
            })
        );
    }
}
