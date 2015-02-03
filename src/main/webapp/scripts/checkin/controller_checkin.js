'use strict';

var checkinController = booksApp.controller('CheckinController', function ($scope, CheckIn, BorrowHistory,$timeout) {
	$scope.selectedItemsId={};
	
	//The ItemList in the main checkin page. 
    $scope.checkedInHistoryList=[];
    
	 $scope.checkInByItemBarcode = function() {
  	   CheckIn.barcode.update($scope.itemBarcode, function(history) {
  		   var historyList=[];
  		   historyList.push(history);
  		   mergeItemArray($scope.checkedInHistoryList,historyList)
     	   $scope.itemBarcode="";
		});
     };      

     /*
     $scope.checkInByItemCallNumber = function() {
    	 $scope.selectedItemsId.cl
    	 CheckIn.callNumber.update($scope.callNumber, function(historyList) {
    		 $scope.callNumber="";
    	 });
     };      
     
     $scope.checkInByTitle = function() {r
    	 CheckIn.title.update($scope.itemBarcode, function(item) {
    		 $scope.title="";
    	 });
     };      
     */
     
     /* Search BorrowHistory by Item's callNumber */
     $scope.getHistoryByCallNumber = function() {
     	$scope.selectedItemsId=[];
     	BorrowHistory.query({callNumber: $scope.itemCallNumber},function(histories) {
     		$scope.foundItemList = histories;
     		$scope.foundMoreItems=histories.length>=1;
     	});
     	$scope.itemCallNumber='';
     };      

     /* Search BorrowHistory by Item's callNumber */
     $scope.getHistoryByTitle = function() {
    	 $scope.selectedItemsId=[];
    	 BorrowHistory.query({title: $scope.itemTitle},function(items) {
    		 $scope.foundItemList = items;
    		 $scope.foundMoreItems=items.length>=1;
    	 });
    	 $scope.itemTitle='';
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
    	 
    	 CheckIn.id.update(selectedHistoryId, function(historyList) {
    		 mergeItemArray($scope.checkedInHistoryList,historyList)
    	 });
    	 
    	 
     	$scope.foundMoreItems = false;
     };
     
     
     $scope.hideItemPopupWindow = function() {
     	$scope.foundMoreItems = false;
     };
        
     
     $scope.clear = function () {
    	 $scope.itemBarcode="";
    	 $scope.itemCallNumber='';
    	 $scope.itemTitle='';
     	$scope.checkedInHistoryList=[];
     	
     };
    });




