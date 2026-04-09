import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http'; // 👈
import { importProvidersFrom } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS } from '@angular/common/http'; // 👈
import { AuthInterceptor } from './interceptor/auth_interceptor';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()), 
    importProvidersFrom(FormsModule),
    {
      provide: HTTP_INTERCEPTORS, //
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
};
