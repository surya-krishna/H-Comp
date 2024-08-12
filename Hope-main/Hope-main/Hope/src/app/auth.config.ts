import { AuthConfig } from 'angular-oauth2-oidc';

export const authConfig: AuthConfig = {
  issuer: 'https://accounts.google.com',
  clientId: '866839830909-rmfmibp8grentrnfl91hlurmqqu4mf75.apps.googleusercontent.com',
  redirectUri: window.location.origin + '/userDashboard',
  scope: 'https://www.googleapis.com/auth/fitness.activity.read',
  responseType: 'token id_token',
  showDebugInformation: true, 
};
