import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { TokenStorageService } from '../../_services/token-storage.service';
import { UserService } from '../../_services/user.service';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit, OnDestroy {

  private subscription: Subscription = new Subscription;
  
  form : any = {
    firstname: '',
    lastname: '',
    email: '',
    role: null
    } ;

  userid: any | null = '';
  id: any | null = '';

  isUpdateDone = false;
  isSuccessful = false;
  isUpdateFailed = false;
  errorMessage = '';
  role = '';
  

  constructor(private route: ActivatedRoute,private router: Router,private tokenService: TokenStorageService,
    private userService: UserService) { }

  ngOnInit() {
    this.userid = this.route.snapshot.params['userid'];
    this.id = this.tokenService.getUser().id;
    
    // this.employeeService.getEmployee(this.employee.empid)
    //   .subscribe(data => {
    //     console.log(data)
    //     this.employee = data;
    //   }, error => console.log(error));
  }

  onSubmit() {
    this.updateEmployee();    
  }

  updateEmployee() {
    const { firstname, lastname, email, role } = this.form;
    let roles = [];
    roles.push(role);
    this.subscription = this.userService.updateUser(firstname,lastname,email,role,this.userid).subscribe(
      response => {
        console.log(response);
        this.isUpdateDone=true;
        this.isSuccessful=true;
        this.isUpdateFailed=false;
        this.form = '';
      },
      err => {
        console.log(err);
        this.errorMessage = err.error.message;
        this.isUpdateFailed=true;
        this.isUpdateDone=false;
        this.isSuccessful=false;
      }
    )

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
}
  
}
