import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { v4 as uuidv4 } from 'uuid';

@Injectable()
export class CorrelationIdInterceptor implements HttpInterceptor {
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const correlationId = uuidv4();

        const modifiedRequest = request.clone({
            setHeaders: {
                'X-Correlation-Id': correlationId
            }
        });

        console.log(`[${correlationId}] ${request.method} ${request.url}`);

        return next.handle(modifiedRequest);
    }
}
