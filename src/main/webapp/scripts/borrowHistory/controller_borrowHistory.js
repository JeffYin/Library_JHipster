'use strict';

booksApp.controller('BorrowHistoryController', function ($scope, resolvedBorrowHistory, BorrowHistory, resolvedItem, resolvedReaderCard,$timeout,ReaderCard) {

        $scope.borrowHistorys = resolvedBorrowHistory;
        $scope.items = resolvedItem;
        $scope.readerCards = resolvedReaderCard;
        $scope.readerBarcode="";

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

        /*
        $scope.delete = function (id) {
            BorrowHistory.delete({id: id},
                function () {
                    $scope.borrowHistorys = BorrowHistory.query();
                });
        };
       */
        $scope.clear = function () {
            $scope.borrowHistory = {borrowDate: null, returnDate: null, cleared: null, comments: null, id: null};
        };
        
        /* Search Reader Card by barcode */
        $scope.getReaercardByBarcode = function() {
        	ReaderCard.getByBarcode({barcode: $scope.readerBarcode},function(readerCard) {
				$scope.readerCards=readerCard;
			});
        };
        
        /*
        var timeout; 
        $scope.$watch('readerBarcode', function(newBarCode) {
        	if (newBarCode) {
        		if (timeout) {
        			$timeout.cancel(timeout); 
        		}
        		
        		timeout = $timeout(function(){
        			ReaderCard.getByBarcode({barcode: newBarCode},function(readerCard) {
        				$scope.readerCards=readerCard;
        			});
        		},350);
        	}
           
        });
        */
        
        
        
    });
