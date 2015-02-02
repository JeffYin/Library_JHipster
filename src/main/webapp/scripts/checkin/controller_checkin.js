'use strict';

var checkinController = booksApp.controller('CheckinController', function ($scope, CheckIn, BorrowHistory,$timeout) {
	$scope.selectedItemsId={};
	
	 $scope.checkInByItemBarcode = function() {
  	   CheckIn.barcode.update($scope.itemBarcode, function(item) {
     	    	$scope.itemBarcode="";
		});
     };      

     $scope.checkInByItemCallNumber = function() {
    	 CheckIn.callNumber.update($scope.callNumber, function(item) {
    		 $scope.itemBarcode="";
    	 });
     };      
     
     $scope.checkInByTitle = function() {r
    	 CheckIn.title.update($scope.itemBarcode, function(item) {
    		 $scope.itemBarcode="";
    	 });
     };      
     
     
     /* Search BorrowHistory by Item's callNumber */
     $scope.getItemByCallNumber = function() {
     	$scope.selectedItemsId=[];
     	BorrowHistory.query({callNumber: $scope.itemCallNumber},function(items) {
     		$scope.foundItemList = items;
     		$scope.foundMoreItems=items.length>=1;
     	});
     };      
     
     
     //Close the Item popup windows and copy the selected Items to the main list. 
     $scope.checkinItem = function() {
    	 var selectedHistoryId = [];
    	 angular.forEach($scope.selectedItemsId, function(val, idx){
             if(val) {
               var checkInItemId = $scope.foundItemList[idx].id;
               selectedHistoryId.push(checkInItemId); 
               
//               alert(checkInItemId);
//               $scope.selectedItemList.push(newRow);
             }
           });
    	 
    	 CheckIn.id.update(selectedHistoryId, function(item) {
    		 $scope.callNumber="";
    	 });
    	 
     	$scope.foundMoreItems = false;
     }
        
    });




