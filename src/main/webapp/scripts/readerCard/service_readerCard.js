'use strict';

booksApp.factory('ReaderCard', function ($resource) {
        return $resource('app/rest/readerCards/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}            
        });
    });
