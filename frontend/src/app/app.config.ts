import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http'; // ← This is what was missing
import { importProvidersFrom } from '@angular/core';
import { FormsModule } from '@angular/forms';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter([]), // add your routes here later if needed
    provideHttpClient(), // ← THIS LINE IS REQUIRED
    importProvidersFrom(FormsModule),
  ],
};
