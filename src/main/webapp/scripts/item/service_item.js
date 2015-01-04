'use strict';

booksApp.factory('Item', function ($resource) {
        return $resource('app/rest/items/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
