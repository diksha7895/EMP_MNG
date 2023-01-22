import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {
  content?: string;
  // isUser = false;
  // isAdmin = false;
  showStatus: boolean = false;
  errorMessage ="";
  showMessage ="";
  isCompleted = false;
  isStarted = false;
  isAborted = false;
  currentUserRole : any | undefined;
  currentUser: any;
  empId: any;
  private roles: string[] = [];

  jobs : any[] = [];
  sortedList: any[] = [];
  job: any = {
    id: '',
    jobname: '',
    starttime: '',
    endtime: '',
    profit: '',
    applicableRole: '',
    jobStartDate: '',
    jobUpdatedDate: '',
    employeeId: '',
    status: '',
    jobstarttime: '',
    isChange: false,
    isPresent: false
};
updatejob: any ={
  jobname: '',
    starttime: '',
    endtime: '',
    profit: '',
    applicableRole: ''
}
updateMessage ="";
  isJobUpdated: boolean = false;

  constructor(private userService: UserService,private token: TokenStorageService,private router: Router) { }

  ngOnInit(): void {
    
     const user = this.token.getUser();
     this.roles = user.roles;
     console.log(user.roles);
     this.showStatus = this.roles.includes('ROLE_ADMIN');
     
     this.empId = user.id;
    
    //const role = this.currentUserRole;
    if(this.showStatus){
      this.userService.getAllJobs().subscribe(
            data => {
              console.log(data.body);
             // this.isUser=true;
              this.sortedList=data;
        },
          err => {
              console.log(err);
              this.errorMessage = err.err;
              //alert("No jobs found.");
            }
          )
        }
      else{
        this.userService.getAllJobs().subscribe(
          data => {
            //this.isUser=true;
            console.log("in else :"+data.body);
            this.jobs = data;
            this.jobs.forEach(item => {
              console.log(item['status']);
              if(item['status']==='NOT_STARTED' || item['status']==='IN_PROGRESS'){
                this.sortedList.push(item);
              }
              console.log("sorted list : "+this.sortedList);
            });
            this.showMessage = "All available jobs";
          },
          err => {
            console.log(err);
            this.errorMessage = err.err;
          }
        )
     } 

   }

  //  for admin access functionality
   doEdit(job : any){
    this.jobs.forEach(e =>{
       e.isChange=false;
    });
    job.isChange=true;
    this.updatejob.jobname = job.jobname;
    this.updatejob.starttime = job.starttime;
    this.updatejob.endtime = job.endtime;
    this.updatejob.profit = job.profit;
    this.updatejob.applicableRole = job.applicableRole;
  }

  updateJobDetails(jobid:any){
    if (this.updatejob.jobname == '' || this.updatejob.starttime == '' || this.updatejob.endtime == '' || this.updatejob.profit == '' || this.updatejob.applicableRole == '') {
      alert("Fields are blank.");
      return;
    }
    const{jobname,starttime,endtime,profit,applicableRole}=this.updatejob;
    this.userService.updateJob(jobname,starttime,endtime,profit,applicableRole,jobid).subscribe(
      data => {
        console.log(data);
        this.isJobUpdated=true;
        this.updateMessage="Job details updated successfully";
        setTimeout(() => {
          this.isJobUpdated=false;
          this.updateMessage="";
          window.location.reload();
        },3000);
      },
      err => {
        console.log(err);
        this.isJobUpdated=false;
        this.errorMessage = err.err.message;
      }
    )
    
  }

  // for user access functionality
  onAllocate(item: any) {
    this.jobs.forEach(element => {
      element.isPresent = false;
    });
    this.currentUser = this.token.getUser();
    console.log(this.currentUser.id);
    
    let userid = this.currentUser.id;
    console.log("this.currentUser.id"+userid);
    let jobid: any = item.id;
    let role: any = this.currentUser.roles[0];
    let status: string = 'Allocate';

    this.userService.allocateJob(userid, jobid, role, status).subscribe(
      data => {
        console.log(data);
       this.isStarted = true;
        item.isPresent = true;
         setTimeout(function () {
           window.location.reload();
         }, 3000);
      },
      err => {
        console.log(err.error);
        this.isStarted = false;
        const myObj = JSON.parse(err.error);
      }
    )
  }

  onComplete(job: any) {
    this.currentUser = this.token.getUser();
    console.log(this.currentUser.id);
    console.log('Assigned role to user :' + this.currentUser.roles[0]);

    let userid: any = this.currentUser.id;
    let jobid: any = job.id;
    let role: any = this.currentUser.roles[0];
    let status: string = 'Complete';

    this.userService.completeJob(userid, jobid, role, status).subscribe(
      data => {
        console.log(data);
        // alert("Job Completed..")
        this.isCompleted = true;
        setTimeout(function () {
          window.location.reload();
        }, 3000);
      },
      err => {
        console.log(err.error);
        this.isCompleted = false;
        const myObj = JSON.parse(err.error);
      }
    )

  }

  onAbort(job: any) {
    this.currentUser = this.token.getUser();
    console.log(this.currentUser.id);
    console.log('Assigned role to user :' + this.currentUser.roles[0]);

    let userid: any = this.currentUser.id;
    let jobid: any = job.id;
    let role: any = this.currentUser.roles[0];
    let status: string = 'Abort';

    this.userService.abortJob(userid, jobid, role, status).subscribe(
      data => {
        console.log(data);
        this.isAborted = true;
        job.isPresent = false;
        setTimeout(function () {
          window.location.reload();
        }, 3000);
      },
      err => {
        console.log(err.error);
        this.isAborted = false;
        const myObj = JSON.parse(err.error);
      }
    )

  }
}
