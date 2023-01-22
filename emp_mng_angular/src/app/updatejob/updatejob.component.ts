import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-updatejob',
  templateUrl: './updatejob.component.html',
  styleUrls: ['./updatejob.component.css']
})
export class UpdatejobComponent implements OnInit {
  updatejob : any = {
    jobname: '',
    starttime: '',
    endtime: '',
    profit: '',
    applicableRole: ''
    } ;
    flag = false;
  
   jobid: any | null = '';
   id: any | null = '';

  isUpdateDone = false;
  isSuccessful = false;
  isUpdateFailed = false;
  errorMessage = '';
 // role = '';
  

  constructor(private route: ActivatedRoute,private router: Router,private tokenService: TokenStorageService,
    private userService: UserService) { }

  ngOnInit() {
   // this.jobid = this.route.snapshot.params['jobid'];
    this.id = this.tokenService.getUser().id;
    console.log('User id is  ' + this.id);
    
  }

  onSubmit() {
    this.updateJob();    
  }

  updateJob() {
    const { jobname, starttime, endtime, profit, applicableRole } = this.updatejob;
    let roles = [];
    roles.push(applicableRole);
    this.userService.updateJob(jobname, starttime, endtime, profit, applicableRole,this.jobid).subscribe(
      response => {
        console.log(response);
        this.flag=false;
        this.isUpdateDone=true;
        this.isSuccessful=true;
        this.isUpdateFailed=false;
        this.updatejob = '';
        this.router.navigate(['jobs']);
      },
      err => {
        console.log(err);
        this.errorMessage = err.error.message;
        this.isUpdateFailed=true;
        this.isUpdateDone=false;
        this.isSuccessful=false;
        this.flag=false;
      }
    )
    
  }
 
}
