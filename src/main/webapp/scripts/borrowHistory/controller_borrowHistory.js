'use strict';

booksApp.controller('BorrowHistoryController', function ($scope, resolvedBorrowHistory, BorrowHistory, resolvedItem, resolvedReaderCard,$timeout,ReaderCard, Reader) {

        $scope.borrowHistorys = resolvedBorrowHistory;
        $scope.items = resolvedItem;
        $scope.readerCards = resolvedReaderCard;
        
        
        $scope.readerBarcode="";
        $scope.readerName="";
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

        //Fetchh the read list which contains the given parameter. 
        $scope.getReaderByName = function() {
        	Reader.query({name: $scope.readerName},function(readers) {
        		$scope.selectedReaderList =readers;
        		if (readers.length==1) {
        			$scope.selectedReader =readers[0];
        		}
        	});
        };
        
               
        
    });
