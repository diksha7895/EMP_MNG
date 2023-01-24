import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-createjob',
  templateUrl: './createjob.component.html',
  styleUrls: ['./createjob.component.css']
})
export class CreatejobComponent implements OnInit, OnDestroy {
  private subscription: Subscription = new Subscription;

  isSuccessful = false;
  errorMessage = "";
  isUser=false;
  //applicableRole='';

  createjob: any = {
    jobname: '',
    starttime: '',
    endtime: '',
    profit: '',
    applicableRole: ''
  };
  constructor(private userService: UserService,private router:Router) { }

  ngOnInit() {
    //this.content = "Admin Dashboard";
  /*  this.userService.getAllUser().subscribe(
      data => {
        this.isUser=true;
        this.createjob = data;
       // this.users.forEach(e => console.log(e.roles[0].name));
      },
      err => {
        //this.content = JSON.parse(err.error).message;
        console.log(err);
        this.errorMessage = err.err;
        alert("No user found.");
      }
    );*/
  }

  onCreate() : void {
    const{jobname, starttime, endtime, profit, applicableRole} = this.createjob;
    // let roles = [];
    // roles.push(applicableRole);
    console.log("OnCreate role:"+applicableRole);
    this.subscription = this.userService.createJob(jobname,starttime,endtime,profit,applicableRole).subscribe(
      data=> {
      this.isSuccessful = true;
       setTimeout(() => {
         window.location.reload();
       }, 3000);
    },
    error=> {
      this.isSuccessful = false;
      console.error(error);
      this.errorMessage = error.error;
      //this.isSuccessful = false;
    })
  }
  
  ngOnDestroy() {
    this.subscription.unsubscribe();
}

}
