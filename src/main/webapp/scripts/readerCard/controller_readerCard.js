'use strict';

booksApp.controller('ReaderCardController', function ($scope, resolvedReaderCard, ReaderCard, resolvedReader) {

        $scope.readerCards = resolvedReaderCard;
        $scope.readers = resolvedReader;

        $scope.create = function () {
            ReaderCard.save($scope.readerCard,
                function () {
                    $scope.readerCards = ReaderCard.query();
                    $('#saveReaderCardModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.readerCard = ReaderCard.get({id: id});
            $('#saveReaderCardModal').modal('show');
        };

        $scope.delete = function (id) {
            ReaderCard.delete({id: id},
                function () {
                    $scope.readerCards = ReaderCard.query();
                });
        };

        $scope.clear = function () {
            $scope.readerCard = {barcode: null, status: null, id: null};
        };
    });
