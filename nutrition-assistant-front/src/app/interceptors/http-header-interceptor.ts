import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class HttpHeaderInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let httpHeaders = new HttpHeaders();

        if (req.body) {
            httpHeaders = httpHeaders.append('Content-Type', 'application/json');
        }
        const jwt = localStorage.getItem('jwt');
        if (jwt) {
            httpHeaders = httpHeaders.append('Authorization', 'Bearer ' + jwt);
        }
        const request = req.clone({
            headers: httpHeaders
        });

        return next.handle(request);
    }

}
