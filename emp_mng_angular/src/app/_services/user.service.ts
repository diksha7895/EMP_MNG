import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';

//const API_URL = 'http://localhost:8081/empmng/';
//const API_URL = 'https://cyhf5zo2pi.execute-api.ap-northeast-1.amazonaws.com/UAT/';
const API_URL = 'http://emp-mng-lb-322037909.ap-northeast-1.elb.amazonaws.com/empmng/';

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
  //** */
  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }
  
  //get all Users or Employee
  getAllUser(): Observable<any> {
    return this.http.get(API_URL + 'getUsers');
  }

  //update user or employee data
  updateUser(firstname: string, lastname: string, email: string, role: any, userid: any) : Observable<any> {
    console.log("role="+role);
     return this.http.post(API_URL+'updateUser/'+userid,{
       firstname,
       lastname,
       email,
       role
     },
     {responseType : 'text'});
 }

  deleteUser(userid: any) : Observable<any> {
    return this.http.delete(API_URL+'deleteUser/'+userid,{responseType : 'text'});
  }

  //get all employee details
  getEmployeeList(): Observable<any> {
    console.log(API_URL + 'EmployeeDetail');
    return this.http.get(API_URL + 'EmployeeDetail');
  }

  getEmployeeById(id:any) : Observable<any>{
    return this.http.get(API_URL+'EmployeeDetail/'+id);
  } 

  //get all Jobs
  getAllJobs(): Observable<any> {
    console.log(API_URL + 'Jobs');
    return this.http.get(API_URL + 'Jobs');
  }
 
  createJob(jobname: string, starttime: string, endtime: string, profit: number, applicableRole: any) : Observable<any> {
    
    console.log("role="+applicableRole);
    return this.http.post(API_URL+'createJob',{
       jobname,
       starttime,
       endtime,
       profit,
       applicableRole
     },
     {responseType : 'text'});
    }

    updateJob(jobname: string,starttime: string,endtime: string,profit: number,applicableRole: any,jobid: any) : Observable<any>{
      console.log("role="+applicableRole);
      return this.http.post(API_URL+'modifyJob/'+jobid,{
        jobname,
        starttime,
        endtime,
        profit,
        applicableRole
      },
      {responseType : 'text'});
    }

    allocateJob(userid: any, jobid: any, role: any, status: string) {
      return this.http.get(API_URL+'processJobs/'+jobid+'/'+userid+'/'+status+'/'+role,{
         responseType: 'text' });
    }

    abortJob(userid: any, jobid: any, role: any, status: string) {
      return this.http.get(API_URL+'processJobs/'+jobid+'/'+userid+'/'+status+'/'+role,{ 
        responseType: 'text' });
    }
  
    completeJob(userid: any, jobid: any, role: any, status: string) {
      return this.http.get(API_URL+'processJobs/'+jobid+'/'+userid+'/'+status+'/'+role,
       { responseType: 'text' });
    }
}

