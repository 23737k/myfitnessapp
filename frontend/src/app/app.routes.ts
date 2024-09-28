import {Routes} from '@angular/router';
import {MainLayoutComponent} from "./shared/main-layout/main-layout.component";
import {LoginComponent} from "./features/auth/login/login.component";
import {RegisterComponent} from "./features/auth/register/register.component";
import {HomeComponent} from "./features/home/home.component";
import {RoutinesComponent} from './features/routines/routines.component';
import {RoutineDetailsComponent} from "./features/routine-details/routine-details.component";
import {WorkoutsComponent} from "./features/workouts/workouts/workouts.component";
import {NewWorkoutComponent} from "./features/workouts/new-workout/new-workout.component";

export const routes: Routes = [
  {
    path:'',
    component:MainLayoutComponent,
    children:[
      {path:'', component:HomeComponent},
      {path:'login',component:LoginComponent},
      {path:'register',component:RegisterComponent},
      {path:'routines', component:RoutinesComponent},
      {path: 'routines/:id', component: RoutineDetailsComponent},
      {path: 'workouts', component: WorkoutsComponent},
      {path: 'new-workout', component: NewWorkoutComponent}
    ]
  }
];
