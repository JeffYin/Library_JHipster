'use strict';

booksApp.factory('Tag', function ($resource) {
        return $resource('app/rest/tags/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
