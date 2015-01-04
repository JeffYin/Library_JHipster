'use strict';

booksApp.controller('ReaderController', function ($scope, resolvedReader, Reader, resolvedReaderCard) {

        $scope.readers = resolvedReader;
        $scope.readerCards = resolvedReaderCard;

        $scope.create = function () {
            Reader.save($scope.reader,
                function () {
                    $scope.readers = Reader.query();
                    $('#saveReaderModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.reader = Reader.get({id: id});
            $('#saveReaderModal').modal('show');
        };

        $scope.delete = function (id) {
            Reader.delete({id: id},
                function () {
                    $scope.readers = Reader.query();
                });
        };

        $scope.clear = function () {
            $scope.reader = {permanentNo: null, name: null, homePhone: null, mobilePhone: null, email: null, address: null, postCode: null, id: null};
        };
    });
