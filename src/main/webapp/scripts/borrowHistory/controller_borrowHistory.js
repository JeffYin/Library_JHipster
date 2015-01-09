'use strict';

booksApp.controller('BorrowHistoryController', function ($scope, resolvedBorrowHistory, BorrowHistory, resolvedItem, resolvedReaderCard,$timeout,ReaderCard, Reader,Bibliograph, Item,$location) {

        $scope.borrowHistorys = resolvedBorrowHistory;
        $scope.items = resolvedItem;
        $scope.readerCards = resolvedReaderCard;
        
        
        $scope.readerBarcode="";
//        $scope.readerName="";
//        $scope.readerHomePhone="";
//        $scope.readerMobilePhone="";

        $scope.itemBarcode="";
        $scope.itemCallNumber="";
        $scope.itemTitle="";
        
        
        $scope.selectedReader = ""; 
        $scope.selectedReaderList="";

        $scope.selectedItem="";
        $scope.foundItemList=[];
        $scope.selectedItemsId={};
        $scope.selectedItemList=[];
        
        //The ItemList in the main checkout page. 
        $scope.checkedOutItemList=[];

        
        
        $scope.foundMoreItemsReaders=false;

        $scope.create = function () {
            BorrowHistory.save($scope.borrowHistory,
                function () {
                    $scope.borrowHistorys = BorrowHistory.query();
                    $('#saveBorrowHistoryModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.borrowHistory = BorrowHistory.get({id: id});
            $('#saveBorrowHistoryModal').modal('show');
        };

       
        $scope.clear = function () {
            $scope.borrowHistory = {borrowDate: null, returnDate: null, cleared: null, comments: null, id: null};
        };
        
        /* Search Reader Card by barcode */
        $scope.getReadercardByBarcode = function() {
        	ReaderCard.get({barcode: $scope.readerBarcode},function(readerCard) {
				$scope.selectedReader =readerCard.reader;
				//Clear the readerBarcode inputbox.
				$scope.readerBarcode="";
			});
        };

        /*
      //Fetch the reader list which contains the given name. 
        $scope.getReaderByName = function() {
        	Reader.query({name: $scope.readerName},function(readers) {
        		$scope.selectedReaderList =readers;
        		
        		$scope.foundMoreReaders=readers.length>1; 
        		if (readers.length==1) {
        			$scope.selectedReader =readers[0];
        		}
        	});
        };
        
        
        //Fetch the reader list which contains the given home number. 
        $scope.getReaderByHomePhone = function() {
        	Reader.query({homePhone: $scope.readerHomePhone},function(readers) {
        		$scope.selectedReaderList =readers;
        		$scope.foundMoreReaders=readers.length>1; 
        		if (readers.length==1) {
        			$scope.selectedReader =readers[0];
        		}
        	});
        };
        
        //Fetch the reader list which contains the given home number. 
        $scope.getReaderByMobilePhone = function() {
        	Reader.query({mobilePhone: $scope.readerMobilePhone},function(readers) {
        		$scope.selectedReaderList =readers;
        		$scope.foundMoreReaders=readers.length>1; 
        		if (readers.length==1) {
        			$scope.selectedReader =readers[0];
        		}
        	});
        };
        */
        
        // Item Information collection
        
        /* Search Item by its barcode */
        $scope.getItemByBarcode = function() {
        	Item.get({barcode: $scope.itemBarcode},function(item) {
//				$scope.selectedItem = item;
        	    var itemArray=[];
        	    itemArray.push(item);
				mergeItemArray($scope.checkedOutItemList,itemArray);
				$scope.itemBarcode="";
			});
        };      

        /* Search Item by its callNumber */
        $scope.getItemByCallNumber = function() {
        	$scope.selectedItemsId=[];
        	Item.query({callNumber: $scope.itemCallNumber},function(items) {
        		$scope.itemCallNumber="";
        		$scope.foundItemList = items;
        		$scope.foundMoreItems=items.length>=1;
        		/*
        		if (items.length==1) {
        			$scope.selectedItem =items[0];
        		}
        		*/
        	});
        };      
        
        /* Search Item by its title. */
        
        $scope.getItemByTitle = function() {
        	$scope.selectedItemsId=[];
        	Item.query({title: $scope.itemTitle},function(items) {
        		$scope.foundItemList = items;
        		$scope.foundMoreItems=items.length>=1;
        		$scope.itemTitle="";
        		/*
        		if (items.length==1) {
        			$scope.selectedItem =items[0];
        		}
        		*/
        	});
        };
        
        $scope.readerSelected = function(selected) {
        	var readerJson =selected.originalObject;
        	$scope.selectedReader =readerJson;
        };
        
        
        $scope.addItemToList = function() {
          //TODO: Get the checked item and add them to the selected lists. 
        	
        }
        
        //Close the Item popup windows and copy the selected Items to the main list. 
        $scope.addItemsFromPopupWindow = function() {
        	mergeItemArray($scope.checkedOutItemList,$scope.selectedItemList);
        	$scope.foundMoreItems = false;
        }
        //Close the Item popup windows. 

        $scope.hideItemPopupWindow = function() {
        	$scope.foundMoreItems = false;
        }
        
        $scope.$watch("selectedItemsId", function(selection) {
        	$scope.selectedItemList = [];
            angular.forEach(selection, function(val, idx){
              if(val) {
                var newRow = angular.copy($scope.foundItemList[idx]);         
                $scope.selectedItemList.push(newRow);
              }
            });
          }, true);
        
        $scope.deleteSelectedItem = function(itemId) {
        	var selectedItemList=$scope.checkedOutItemList;
        	for (var i = 0; i < selectedItemList.length; i++){
        			if (selectedItemList[i].id === itemId){
        				selectedItemList.splice(i, 1)
        				break;
        			}
        	}
        };
        
    });

//Merge two arrays and remove the duplicate items. 
function mergeItemArray(arr1, arr2) {
//	var _arr = [];
//	for (var i = 0; i < arr1.length; i++) {
//		_arr.push(arr1[i]);
//	}
	var _dup;
	for (var i = 0; i < arr2.length; i++){
		_dup = false;
		for (var _i = 0; _i < arr1.length; _i++){
			if (arr2[i].id === arr1[_i].id){
				_dup = true;
				break;
			}
		}
		if (!_dup){
			arr1.push(arr2[i]);
		}
	}
}

