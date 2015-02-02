'use strict';

booksApp.factory('CheckIn', function ($resource) {
	return {
		barcode: $resource('app/rest/checkin/barcode', {}, {
			  'update': {method:'PUT'}
		 }),
		 
		 callNumber: $resource('app/rest/checkin/callNumber', {}, {
			  'update': {method:'PUT'}
		 }),

		 id: $resource('app/rest/checkin/id', {}, {
			 'update': {method:'PUT'}
		 }),
		  
		 title: $resource('app/rest/checkin/title', {}, {
			  'update': {method:'PUT'}
		 })
		 
	 }
	
    });
