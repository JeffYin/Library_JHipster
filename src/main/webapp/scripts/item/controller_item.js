'use strict';

booksApp.controller('ItemController', function ($scope, resolvedItem, Item, resolvedBibliograph) {

        $scope.items = resolvedItem;
        $scope.bibliographs = resolvedBibliograph;

        $scope.create = function () {
            Item.save($scope.item,
                function () {
                    $scope.items = Item.query();
                    $('#saveItemModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.item = Item.get({id: id});
            $('#saveItemModal').modal('show');
        };

        $scope.delete = function (id) {
            Item.delete({id: id},
                function () {
                    $scope.items = Item.query();
                });
        };

        $scope.clear = function () {
            $scope.item = {barcode: null, status: null, comments: null, id: null};
        };
    });
