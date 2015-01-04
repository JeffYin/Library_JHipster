'use strict';

booksApp.controller('BorrowHistoryController', function ($scope, resolvedBorrowHistory, BorrowHistory, resolvedItem, resolvedReaderCard,$timeout,ReaderCard, Reader) {

        $scope.borrowHistorys = resolvedBorrowHistory;
        $scope.items = resolvedItem;
        $scope.readerCards = resolvedReaderCard;
        
        
        $scope.readerBarcode="";
        $scope.readerName="";
        $scope.readerHomePhone="";
        $scope.readerMobilePhone="";
        
        
        $scope.selectedReader = ""; 
        $scope.selectedReaderList="";

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
        
        
               
        
    });
