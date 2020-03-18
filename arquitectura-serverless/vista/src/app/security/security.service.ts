import {environment} from '../../environments/environment';
import {Injectable} from '@angular/core';
import {AuthenticationDetails, CognitoUser, CognitoUserAttribute, CognitoUserPool} from 'amazon-cognito-identity-js';
import {Observable} from 'rxjs';
import {NewPasswordUser} from './new-password-user-model';
import * as AWS from 'aws-sdk/global';
import {Router} from '@angular/router';

@Injectable()
export class SecurityService {

    constructor(private router: Router) {}

    getUserPool(): CognitoUserPool {
        const data = { UserPoolId : environment.userPoolId,
            ClientId : environment.clientId
        };
        return new CognitoUserPool(data);
    }

    authenticate(username: string, password: string): Observable<any> {
        return Observable.create(observer => {
            console.log('SecurityService: starting the authentication');
            const authenticationData = {
                Username: username,
                Password: password,
            };
            const authenticationDetails = new AuthenticationDetails(authenticationData);

            const userData = {
                Username: username,
                Pool: this.getUserPool()
            };

            console.log('SecurityService: Params set...Authenticating the user');
            const cognitoUser = new CognitoUser(userData);
            cognitoUser.authenticateUser(authenticationDetails, {
                newPasswordRequired: function (userAttributes, requiredAttributes) {
                    observer.error('SetPassword');
                    observer.complete();
                },
                onSuccess: function (result) {
                    observer.next(result);
                    observer.complete();
                },
                onFailure: function (err) {
                  if (err.code && err.code === 'PasswordResetRequiredException') {
                    observer.error('ForgotPassword');
                    observer.complete();
                  } else {
                    observer.error(err);
                    observer.complete();
                  }
                },
            });
        });
    }

    forgotPassword(username: string): Observable<any> {
        return Observable.create(observer => {
            const userData = {
                Username: username,
                Pool: this.getUserPool()
            };
            const cognitoUser = new CognitoUser(userData);
            cognitoUser.forgotPassword({
                onSuccess: function (data) {
                    observer.next(data);
                    observer.complete();
                },
                onFailure: function (err) {
                    observer.error(err);
                    observer.complete();
                },
                inputVerificationCode() {
                    observer.next(true);
                    observer.complete();
                }
            });
        });
    }

    confirmNewPassword(email: string, verificationCode: string, password: string): Observable<any> {
        return Observable.create(observer => {
            const userData = {
                Username: email,
                Pool: this.getUserPool()
            };

            const cognitoUser = new CognitoUser(userData);

            cognitoUser.confirmPassword(verificationCode, password, {
                onSuccess: function () {
                    observer.next(true);
                    observer.complete();
                },
                onFailure: function (err) {
                    observer.error(err);
                    observer.complete();
                }
            });
        });
    }

    changePassword(newPasswordUser: NewPasswordUser): Observable<any> {
        return Observable.create(observer => {
            const cognitoUser = this.getUserPool().getCurrentUser();
            if (cognitoUser != null) {
                cognitoUser.getSession(function (err, session) {
                    if (err) {
                        console.log('SecurityService: Couldn\'t get the session: ' + err, err.stack);
                        observer.error(err);
                        observer.complete();
                    } else {
                        cognitoUser.changePassword(newPasswordUser.existingPassword, newPasswordUser.password, function (error, result) {
                            if (error) {
                                observer.error(error);
                                observer.complete();
                            } else {
                                observer.next(result);
                                observer.complete();
                            }
                        });
                    }
                });
            } else {
                observer.error(null);
                observer.complete();
            }
        });
    }

    newPassword(newPasswordUser: NewPasswordUser): Observable<any> {
        return Observable.create(observer => {
            // Get these details and call
            // cognitoUser.completeNewPasswordChallenge(newPassword, userAttributes, this);
            const authenticationData = {
                Username: newPasswordUser.username,
                Password: newPasswordUser.existingPassword,
            };
            const authenticationDetails = new AuthenticationDetails(authenticationData);

            const userData = {
                Username: newPasswordUser.username,
                Pool: this.getUserPool()
            };

            console.log('SecurityService: Params set...Authenticating the user');
            const cognitoUser = new CognitoUser(userData);
            console.log('SecurityService: config is ' + AWS.config);
            cognitoUser.authenticateUser(authenticationDetails, {
                newPasswordRequired: function (userAttributes, requiredAttributes) {
                    // User was signed up by an admin and must provide new
                    // password and required attributes, if any, to complete
                    // authentication.

                    // the api doesn't accept this field back
                    delete userAttributes.email_verified;
                    cognitoUser.completeNewPasswordChallenge(newPasswordUser.password, requiredAttributes, {
                        onSuccess: function (result) {
                            observer.next(userAttributes);
                            observer.complete();
                        },
                        onFailure: function (err) {
                            observer.error(err);
                            observer.complete();
                        }
                    });
                },
                onSuccess: function (result) {
                    observer.next(result);
                    observer.complete();
                },
                onFailure: function (err) {
                    observer.error(err);
                    observer.complete();
                }
            });
        });
    }

    logout() {
        console.log('SecurityService: Logging out');
        this.getUserPool().getCurrentUser().signOut();
        this.router.navigate(['/security/login']);
    }


    public validateSession(): Observable<boolean> {
        return Observable.create(observer => {
            const data = { UserPoolId : environment.userPoolId,
                ClientId : environment.clientId
            };
            const userPool = new CognitoUserPool(data);
            const cognitoUser = userPool.getCurrentUser();
            if (cognitoUser != null) {
                cognitoUser.getSession(function(err, session) {
                    if (err) {
                        console.log(err);
                        observer.error(err);
                    } else {
                        observer.next(session.isValid());
                    }
                    observer.complete();
                });
            } else {
                observer.next(false);
                observer.complete();
            }
        });
    }

    public isLoggedOut(): Observable<boolean> {
        return Observable.create(observer => {
            const data = { UserPoolId : environment.userPoolId,
                ClientId : environment.clientId
            };
            const userPool = new CognitoUserPool(data);
            const cognitoUser = userPool.getCurrentUser();
            if (cognitoUser != null) {
                cognitoUser.getSession(function(err, session) {
                    if (err) {
                        observer.next(true); // reverse
                    } else {
                        observer.error(false); // reverse
                    }
                    observer.complete();
                });
            } else {
                observer.next(true); // reverse
                observer.complete();
            }
        });
    }


    public getAccessToken(): Observable<any> {
        return Observable.create(observer => {
            const data = { UserPoolId : environment.userPoolId,
                ClientId : environment.clientId
            };
            const userPool = new CognitoUserPool(data);
            const cognitoUser = userPool.getCurrentUser();
            if (cognitoUser != null) {
                cognitoUser.getSession(function(err, session) {
                    if (err) {
                        console.log(err);
                        observer.error(err);
                    } else {
                        observer.next(session.getIdToken().getJwtToken());
                    }
                    observer.complete();
                });
            } else {
                observer.next(null);
                observer.complete();
            }
        });
    }

  public getCurrentUserAttributes(): Observable<CognitoUserAttribute[]> {
    return Observable.create(observer => {
      const data = { UserPoolId : environment.userPoolId,
        ClientId : environment.clientId
      };
      const userPool = new CognitoUserPool(data);
      const cognitoUser = userPool.getCurrentUser();
      if (cognitoUser != null) {
        cognitoUser.getSession(function(err, session) {
          if (err) {
            console.log(err);
            observer.error(err);
            observer.complete();
          } else {
            cognitoUser.getUserAttributes((error, attrs) => {
              observer.next(attrs);
              observer.complete();
            });
          }
        });
      } else {
        observer.next(false);
        observer.complete();
      }
    });
  }

    public getCurrentUser(): CognitoUser {
        const data = { UserPoolId : environment.userPoolId,
          ClientId : environment.clientId
        };
        const userPool = new CognitoUserPool(data);
        return userPool.getCurrentUser();
    }

}
