'use strict';

booksApp.factory('CheckIn', function ($resource) {
	return {
		barcode: $resource('app/rest/checkin/barcode', {}, {
			  'update': {method:'PUT'}
		 })
	 }
	
    });
