import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  currentUser: any;
  salary: any;

  constructor(private token: TokenStorageService,private service: UserService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    let id= this.currentUser.id;
    console.log("user id: "+id);

    this.service.getEmployeeById(id).subscribe(
      data => {
        this.salary = data['body'].salary;
      },
      err => {
        console.log(err);
      }
    )
  }
}
