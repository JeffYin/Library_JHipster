'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/bibliograph', {
                    templateUrl: 'views/bibliographs.html',
                    controller: 'BibliographController',
                    resolve:{
                        resolvedBibliograph: ['Bibliograph', function (Bibliograph) {
                            return Bibliograph.query().$promise;
                        }],
                        resolvedTag: ['Tag', function (Tag) {
                            return Tag.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
