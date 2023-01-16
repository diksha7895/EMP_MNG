import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';

const API_URL = 'http://localhost:8081/empmng/';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient, private token : TokenStorageService) { }



  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  //get all Users or Employee
  getAllUser(): Observable<any> {
    return this.http.get(API_URL + 'EmployeeDetail');
  }

  //update user or employee data
  updateUser(firstname : String, lastname : String, email : String, role : any, userid : any) : Observable<any> {
   
     return this.http.post(API_URL+'updateUser/'+userid,{
       firstname,
       lastname,
       email,
       role
     },{responseType : 'text'});
 }

  deleteUser(userid : any) : Observable<any> {
    return this.http.delete(API_URL+'deleteUser/'+userid,{responseType : 'text'});
  }
}
