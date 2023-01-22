import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './_services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showTraineeBoard = false;
  showTesterBoard = false;
  showDeveloperBoard = false;
  showEngineerBoard = false;
  showAnalystBoard = false;
  username?: string;

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showTraineeBoard = this.roles.includes('ROLE_TRAINEE');
      this.showTesterBoard = this.roles.includes('ROLE_TESTER');
      this.showDeveloperBoard = this.roles.includes('ROLE_DEVELOPER');
      this.showEngineerBoard = this.roles.includes('ROLE_ENGINEER');
      this.showAnalystBoard = this.roles.includes('ROLE_ANALYST');
      this.username = user.username;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
