'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/checkin', {
                    templateUrl: 'views/checkin.html',
                    controller: 'CheckinController',
                    resolve:{
                      
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
