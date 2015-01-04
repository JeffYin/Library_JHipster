'use strict';

booksApp.factory('BorrowHistory', function ($resource) {
        return $resource('app/rest/borrowHistorys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
