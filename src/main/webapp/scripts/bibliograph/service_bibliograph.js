'use strict';

booksApp.factory('Bibliograph', function ($resource) {
        return $resource('app/rest/bibliographs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
