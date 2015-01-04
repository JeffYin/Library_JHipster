'use strict';

booksApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/borrowHistory', {
                    templateUrl: 'views/borrowHistorys.html',
                    controller: 'BorrowHistoryController',
                    resolve:{
                        resolvedBorrowHistory: ['BorrowHistory', function (BorrowHistory) {
                            return BorrowHistory.query().$promise;
                        }],
                        resolvedItem: ['Item', function (Item) {
                            return Item.query().$promise;
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
