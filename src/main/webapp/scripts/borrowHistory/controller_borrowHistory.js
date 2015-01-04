'use strict';

booksApp.controller('BorrowHistoryController', function ($scope, resolvedBorrowHistory, BorrowHistory, resolvedItem, resolvedReaderCard,$timeout,ReaderCard, Reader,Bibliograph, Item) {

        $scope.borrowHistorys = resolvedBorrowHistory;
        $scope.items = resolvedItem;
        $scope.readerCards = resolvedReaderCard;
        
        
        $scope.readerBarcode="";
        $scope.readerName="";
        $scope.readerHomePhone="";
        $scope.readerMobilePhone="";

        $scope.itemBarcode="";
        $scope.itemCallNumber="";
        $scope.itemTitle="";
        
        
        $scope.selectedReader = ""; 
        $scope.selectedReaderList="";
        $scope.selectedItem="";
        $scope.selectedItemList="";

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
        	ReaderCard.getByBarcode({barcode: $scope.readerBarcode},function(readerCard) {
				$scope.selectedReader =readerCard.reader;
			});
        };

      //Fetch the reader list which contains the given name. 
        $scope.getReaderByName = function() {
        	Reader.query({name: $scope.readerName},function(readers) {
        		$scope.selectedReaderList =readers;
        		if (readers.length==1) {
        			$scope.selectedReader =readers[0];
        		}
        	});
        };
        
        
        //Fetch the reader list which contains the given home number. 
        $scope.getReaderByHomePhone = function() {
        	Reader.query({homePhone: $scope.readerHomePhone},function(readers) {
        		$scope.selectedReaderList =readers;
        		if (readers.length==1) {
        			$scope.selectedReader =readers[0];
        		}
        	});
        };
        
        //Fetch the reader list which contains the given home number. 
        $scope.getReaderByMobilePhone = function() {
        	Reader.query({mobilePhone: $scope.readerMobilePhone},function(readers) {
        		$scope.selectedReaderList =readers;
        		if (readers.length==1) {
        			$scope.selectedReader =readers[0];
        		}
        	});
        };
        
        // Item Information collection
        
        /* Search Reader Card by barcode */
        $scope.getItemByBarcode = function() {
        	Item.get({barcode: $scope.itemBarcode},function(item) {
				$scope.selectedItem = item;
			});
        };      

        /* Search Reader Card by callNumber */
        $scope.getItemByCallNumber = function() {
        	Item.query({callNumber: $scope.itemCallNumber},function(items) {
        		$scope.selectedItemList = items;
        		if (items.length==1) {
        			$scope.selectedItem =items[0];
        		}
        	});
        };      
        
        /* Search Reader Card by title */
        $scope.getItemByTitle = function() {
        	Item.query({title: $scope.itemTitle},function(items) {
        		$scope.selectedItemList = items;
        		if (items.length==1) {
        			$scope.selectedItem =items[0];
        		}
        	});
        };      
        
    });
