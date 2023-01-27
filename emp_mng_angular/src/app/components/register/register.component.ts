import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../../_services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, OnDestroy {

  private subscription: Subscription = new Subscription;
  
  form: any = {
    firstname: null,
    lastname: null,
    username: null,
    email: null,
    password: null
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService,private router : Router) { }

  ngOnInit(): void {
  }

  
  
  onSubmit(): void {
    const {firstname, lastname, username, email, password } = this.form;

    this.subscription = this.authService.register( firstname, lastname, username, email, password).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
       //this.router.navigate(['home']);
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
}

}
