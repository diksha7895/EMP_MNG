import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';

//const AUTH_API = 'http://localhost:8081/empmng/';
//const AUTH_API = 'https://cyhf5zo2pi.execute-api.ap-northeast-1.amazonaws.com/UAT/';
const AUTH_API = 'http://emp-mng-lb-322037909.ap-northeast-1.elb.amazonaws.com/empmng/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json'
  // ,'Access-Control-Allow-Origin':"*",'Access-Control-Allow-Headers':"origin",
  // 'Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token'
})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) { }

  isLoggedIn(){
    return !!this.tokenStorage.getToken();
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username,
      password
    }, httpOptions);
  }

  register( firstname: string, lastname: string,username: string, email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'RegisterUser', {
      firstname,
      lastname,
      username,
      email,
      password
    }, httpOptions);
  }
}
