import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router, Route } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';



@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private auth : AuthService , private token: TokenStorageService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    //state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
     
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
     
      const user = this.token.getUser();
      console.log("user guard :"+user);
      const roles : string[] = user.roles;
      console.log("role guard :"+roles);

      // provides the route configuration options.
      const { routeConfig } = route; 

      // provides the path of the route.
      const { path } = routeConfig as Route;

      if (path?.includes('admin') && this.auth.isLoggedIn()) {
        // if user is admin and is trying to access admin routes, allow access.
          return true;
        }
      if (path?.includes('createJob') && this.auth.isLoggedIn()) {
        // if user is admin and is trying to access admin routes, allow access.
          return true;
        }

      if (path?.includes('emplist') && this.auth.isLoggedIn()) {
          return true;
      }

      if (path?.includes('jobs') && this.auth.isLoggedIn() && roles.length) {
          return true;
      }
      
      if (path?.includes('home') && this.auth.isLoggedIn()) {
          return true;
        }
      if (path?.includes('profile') && this.auth.isLoggedIn()){
        return true;
      }

      
        return false;  
      
  }
  
}
