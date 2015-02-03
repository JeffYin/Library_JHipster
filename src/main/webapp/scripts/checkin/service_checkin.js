'use strict';

booksApp.factory('CheckIn', function ($resource) {
	return {
		barcode: $resource('app/rest/checkin/barcode', {}, {
			  'update': {method:'PUT'}
		 }),
		 
		 callNumber: $resource('app/rest/checkin/callNumber', {}, {
			  'update': {method:'PUT',isArray:true}
		 }),

		 id: $resource('app/rest/checkin/id', {}, {
			 'update': {method:'PUT',isArray:true}
		 }),
		  
		 title: $resource('app/rest/checkin/title', {}, {
			  'update': {method:'PUT',isArray:true}
		 })
		 
	 }
	
    });
