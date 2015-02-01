'use strict';

var checkinController = booksApp.controller('CheckinController', function ($scope, CheckIn, $timeout) {
	 $scope.getItemByBarcode = function() {
  	   CheckIn.barcode.update($scope.itemBarcode, function(item) {
     	    	$scope.itemBarcode="";
		});
     };      
        
    });




