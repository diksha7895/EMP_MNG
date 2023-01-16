import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from '../_model/employee';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit {

  
  form : any = {
    firstname: null,
    lastname: null,
    email: null,
    role: null
    } ;

  userid: any | null = '';
  id: any | null = '';

  isUpdateDone = false;
  isSuccessful = false;
  isUpdateFailed = false;
  errorMessage = '';
  

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
    this.userService.updateUser(firstname,lastname,email,role,this.userid).subscribe(
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
    // this.employeeService.updateEmployee(this.employee.empid, this.employee)
    //   .subscribe(data => {
    //     console.log(data);
    //     this.employee = new Employee();
    //     this.gotoList();
    //   }, error => console.log(error));
  }
  // gotoList() {
  //   this.router.navigate(['/employees']);
  // }
}
