'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/item', {
                    templateUrl: 'views/items.html',
                    controller: 'ItemController',
                    resolve:{
                        resolvedItem: ['Item', function (Item) {
                            return Item.query().$promise;
                        }],
                        resolvedBibliograph: ['Bibliograph', function (Bibliograph) {
                            return Bibliograph.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
