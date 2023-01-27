import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { BoardAdminComponent } from './components/board-admin/board-admin.component';
import { UpdateEmployeeComponent } from './components/update-employee/update-employee.component';
import { EmployeeListComponent } from './components/employee-list/employee-list.component';
import { JobComponent } from './components/job/job.component';
import { CreatejobComponent } from './components/createjob/createjob.component';
import { UpdatejobComponent } from './components/updatejob/updatejob.component';
import { AuthGuard } from './_guard/auth.guard';

// bind the CanActivate guard to the routes
const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]  },
  { path: 'admin', component: BoardAdminComponent, canActivate: [AuthGuard]  },
  { path: 'update', component: UpdateEmployeeComponent, canActivate: [AuthGuard]  },
  { path: 'emplist', component: EmployeeListComponent, canActivate: [AuthGuard] },
  { path: 'jobs', component: JobComponent, canActivate: [AuthGuard] },
  { path: 'createJob', component: CreatejobComponent, canActivate: [AuthGuard] },
  { path: 'modifyJob', component: UpdatejobComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
