import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {
  content?: string;

  isUser = false;
  errorMessage ="";
  users : any[] = [];
  user: any = {
    id: '',
    firstName: '',
    lastName: '',
    salary: '',
    email: ''
};

  constructor(private userService: UserService,private router:Router) { }

  ngOnInit(): void {
    this.content = "Admin Dashboard";
    this.userService.getAllUser().subscribe(
      data => {
        this.content = "Admin Dashboard";
        this.users = data.body;
      },
      err => {
        //this.content = JSON.parse(err.error).message;
        console.log(err);
        this.errorMessage = err.err;
      }
    );
  }

  updateUser(userid:any) : void{
    console.log("Update User Id :"+userid.value);
    this.router.navigate(['update',{userid:userid}]);
  }

  deleteUser(userid : any){
    const block : any ='Yes';
    this.userService.deleteUser(userid).subscribe(
      data => {
          this.isUser = true;
          console.log(data.body);
          
      }

    )
  }
}
