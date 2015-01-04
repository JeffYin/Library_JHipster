'use strict';

booksApp.factory('Reader', function ($resource) {
        return $resource('app/rest/readers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'},
            'getByName':{method:'GET', isArray:false}
        });
    });
