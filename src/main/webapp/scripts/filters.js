'use strict';

angular.module('booksApp')
 .filter( 'returnDate', function($translate) {
	return function(dueDays) {
//		moment.locale("zh-cn");
		var currentLocale=$translate.use();
		moment.locale(currentLocale);
		return moment().add(dueDays,"days").format("dddd, MMMM Do YYYY");
	}
})

;