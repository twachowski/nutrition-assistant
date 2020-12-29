import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TargetsSettingsComponent } from './profile-screen/targets-settings/targets-settings.component';
import { BiometricsSettingsComponent } from './profile-screen/biometrics-settings/biometrics-settings.component';
import { LoginActivator } from './login-activator';
import { WelcomeScreenComponent } from './welcome-screen/welcome-screen.component';
import { DiaryScreenComponent } from './diary-screen/diary-screen.component';
import { HomeActivator } from './home-activator';
import { ProfileScreenComponent } from './profile-screen/profile-screen.component';
import { RegistrationConfirmComponent } from './registration-confirm/registration-confirm.component';
import { UserAccountActivatorService } from './services/user-account-activator.service';


const routes: Routes = [
  { path: 'home', component: WelcomeScreenComponent, canActivate: [HomeActivator] },
  {
    path: 'registration/confirm/:token',
    component: RegistrationConfirmComponent,
    resolve: { result: UserAccountActivatorService }
  },
  { path: 'diary', component: DiaryScreenComponent, canActivate: [LoginActivator] },
  {
    path: 'profile',
    component: ProfileScreenComponent,
    canActivate: [LoginActivator],
    children: [
      { path: '', redirectTo: 'biometrics', pathMatch: 'full' },
      { path: 'biometrics', component: BiometricsSettingsComponent, canActivate: [LoginActivator] },
      { path: 'targets', component: TargetsSettingsComponent, canActivate: [LoginActivator] }
    ]
  },
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule],
  providers: [HomeActivator, LoginActivator]
})
export class AppRoutingModule { }
