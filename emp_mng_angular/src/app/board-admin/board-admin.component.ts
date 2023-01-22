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
  isUserUpdated=false;
  updateMessage ="";
  isUserDeleted=false;
  deleteMessage ="";
  users : any[] = [];
  userRole: any | undefined;
  user: any = {
    id: '',
    firstName: '',
    lastName: '',
    salary: '',
    email: '',
    roles:[],
    isChange: false
};
form: any = {
  firstname: '',
  lastname: '',
  email: '',
  role: ''
  } ;

  constructor(private userService: UserService,private router:Router) { }

  ngOnInit(): void {
    this.content = "Admin Dashboard";
    this.userService.getAllUser().subscribe(
      data => {
        this.isUser=true;
        this.users = data;
       
       // this.users.forEach(e => console.log(e.roles[0].name));
      },
      err => {
        //this.content = JSON.parse(err.error).message;
        console.log(err);
        this.errorMessage = err.err;
        alert("No user found.");
      }
    );
  }

  doEdit(user : any){
    this.users.forEach(e =>{
       e.isChange=false;
    });
    user.isChange=true;
    this.form.firstname = user.firstname;
    this.form.lastname = user.lastname;
    this.form.email = user.email;
  }

  updateUserInfo(userid:any){

    // if(this.form.firstname == '' || this.form.lastname == '' || this.form.email == ''){

    // }

    const{firstname, lastname, email, role} = this.form;
    let roles = [];
    roles.push(role);

    this.userService.updateUser(firstname, lastname, email, roles, userid).subscribe(
      data => {
        console.log(data);
        this.isUserUpdated=true;
        this.updateMessage="User details updated successfully";
        setTimeout(() => {
          this.isUserUpdated=false;
          this.updateMessage="";
          window.location.reload();
        },5000);
      },
      err => {
        //this.content = JSON.parse(err.error).message;
        console.log(err);
        this.isUserUpdated=false;
        this.errorMessage = err.err.message;
        //alert("No user found.");
      }
    )
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
          this.isUserDeleted = true;
          this.deleteMessage = "User deleted"
          console.log(data.body);
      },
        err => {
         console.log(err);
         this.errorMessage=err.err;
       }

    )
    window.location.reload();
  }
}
