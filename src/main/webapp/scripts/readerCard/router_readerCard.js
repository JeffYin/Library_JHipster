'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/readerCard', {
                    templateUrl: 'views/readerCards.html',
                    controller: 'ReaderCardController',
                    resolve:{
                        resolvedReaderCard: ['ReaderCard', function (ReaderCard) {
                            return ReaderCard.query().$promise;
                        }],
                        resolvedReader: ['Reader', function (Reader) {
                            return Reader.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
