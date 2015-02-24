'use strict';

booksApp.controller('BibliographController', function ($scope, resolvedBibliograph, Bibliograph, resolvedTag) {

        $scope.bibliographs = resolvedBibliograph;
        $scope.tags = resolvedTag;
        $scope.bibliographTags=null; 
        
        $scope.create = function () {
            Bibliograph.save($scope.bibliograph,
                function () {
                    $scope.bibliographs = Bibliograph.query();
                    $('#saveBibliographModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.bibliograph = Bibliograph.get({id: id});
            $('#saveBibliographModal').modal('show');
        };

        $scope.delete = function (id) {
            Bibliograph.delete({id: id},
                function () {
                    $scope.bibliographs = Bibliograph.query();
                });
        };
        

        $scope.clear = function () {
            $scope.bibliograph = {title: null, author: null, intro: null, publisher: null, callNumber: null, dueDays: null, imageUrl: null, type: null, id: null};
        };
        /*
        
        $scope.containTags = function(tag) {
        	$scope.bibliograph.$promise.then(function(thisBibliograph){
        		var bibliographTagArr = thisBibliograph.tags;
        	    console.log(bibliographTagArr);
        	    angular.forEach(bibliographTagArr, function(bibliographTag) {
            		if (bibliographTag.id==tag.id) {
            	        return true; 
            	    }
            	}
            	);
        	});
        	
        	return false; 
        }
        */
    });
