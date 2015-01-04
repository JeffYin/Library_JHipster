'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/reader', {
                    templateUrl: 'views/readers.html',
                    controller: 'ReaderController',
                    resolve:{
                        resolvedReader: ['Reader', function (Reader) {
                            return Reader.query().$promise;
                        }],
                        resolvedReaderCard: ['ReaderCard', function (ReaderCard) {
                            return ReaderCard.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
