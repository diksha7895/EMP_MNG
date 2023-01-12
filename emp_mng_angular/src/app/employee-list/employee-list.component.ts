import { Observable } from "rxjs";
import { Employee } from "../_model/employee";
import { EmployeeDetailsComponent } from '../employee-details/employee-details.component';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeeService } from "../_services/employee.service";

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  //employees: Observable<Employee[]>;

  employees: any = {
    empid: null,
    firstName: null,
    lastName: null,
    salary: null,
    emailId: null
};

  // employees  = {
  //   empid: "E101",
  //   firstName: "Diksha",
  //   lastName: "Gupta",
  //   salary: "500",
  //   emailId: "diksha@gmail.com"
  //   } ;

  constructor(private employeeService: EmployeeService,private router: Router) {}

  ngOnInit(): void {
    this.reloadData();
  }
  reloadData() {
    this.employees = this.employeeService.getEmployeesList();
   //for dummy creation
  //  const observable = this.employeeService.getEmployeesList(this.employees);
  //  observable.subscribe((response:any)=>{
  //   console.log(response);
  // })
  }
  

  deleteEmployee(id: number) {
    this.employeeService.deleteEmployee(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  employeeDetails(id: number){
    this.router.navigate(['details', id]);
  }

}
