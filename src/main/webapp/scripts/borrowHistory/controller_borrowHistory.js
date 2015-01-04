'use strict';

booksApp.controller('BorrowHistoryController', function ($scope, resolvedBorrowHistory, BorrowHistory, resolvedItem, resolvedReaderCard,$timeout,ReaderCard) {

        $scope.borrowHistorys = resolvedBorrowHistory;
        $scope.items = resolvedItem;
        $scope.readerCards = resolvedReaderCard;
        
        
        $scope.readerBarcode="";
        $scope.readerName="";

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
				$scope.readerCards=readerCard;
			});
        };

        //TODO:finished the function. 
        $scope.getReaderByName = function() {
        	ReaderCard.get({name: $scope.readerName},function(reader) {
        		$scope.readerCards=readerCard;
        	});
        };
        
               
        
    });
