import { Observable, Subscription } from "rxjs";
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeeService } from "../_services/employee.service";
import { UserService } from "../_services/user.service";

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit, OnDestroy {

  private subscription: Subscription = new Subscription;
  //employees: Observable<Employee[]>;
  content?: string;
  isUser = false;
  errorMessage ="";
  employees : any[] = [];

  employee: any = {
    id: '',
    firstname: '',
    lastname: '',
    salary: '',
    email: '',
    roles: []
};


  constructor(private userService: UserService,private router: Router) {}



  // ngOnInit(): void {
  //   this.reloadData();
  // }
  // reloadData() {
    ngOnInit(): void { 
   // this.content = "Admin Dashboard";
   this.subscription = this.userService.getEmployeeList().subscribe(
      data => {
        this.isUser=true;
        this.employees = data;
      },
      err => {
        
        console.log(err);
        this.errorMessage = err.err;
        
      }
    );
   
  }
  
  ngOnDestroy() {
    this.subscription.unsubscribe();
}

}
