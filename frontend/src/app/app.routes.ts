import { Routes } from '@angular/router';

import { LoginComponent } from './features/auth/login/login';

import { RegisterComponent } from './features/auth/register/register';

import { AdminDashboardComponent } from './features/admin/admin-dashboard/admin-dashboard';

import { CandidateDashboardComponent } from './features/candidate/candidate-dashboard/candidate-dashboard';

import { ResultHistoryComponent } from './features/result/result-history/result-history';

import { AdminLayout } from './shared/layouts/admin-layout/admin-layout';
import { QuestionsComponent } from './features/admin/questions/questions';
import { ExamsComponent } from './features/admin/exams/exams';
import { StatisticsComponent } from './features/admin/statistics/statistics';
import { LeaderboardComponent } from './features/admin/leaderboard/leaderboard';


export const routes: Routes = [

    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },

    {
        path: 'login',
        component: LoginComponent
    },

    {
        path: 'register',
        component: RegisterComponent
    },

    {
        path: 'admin',
        component: AdminLayout,

        children: [

            {
                path: 'dashboard',
                component: AdminDashboardComponent
            },

            {
                path: 'questions',
                component: QuestionsComponent
            },

            {
                path: 'exams',
                component: ExamsComponent
            },

            {
                path: 'statistics',
                component: StatisticsComponent
            },

            {
                path: 'leaderboard',
                component: LeaderboardComponent
            }

        ]
    },

    {
        path: 'candidate/dashboard',
        component: CandidateDashboardComponent
    },

    {
        path: 'results',
        component: ResultHistoryComponent
    },

    {
        path: 'leaderboard',
        component: LeaderboardComponent
    },

    {
        path: 'statistics',
        component: StatisticsComponent
    },

    {
        path: '**',
        redirectTo: 'login'   //'**' matches any URL that does not match any of the routes defined above it.
    }

];

