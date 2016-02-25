'use strict';

angular.module('TrainCheckerApp').controller('AlertController', ['$scope', '$http', 'requestService', function($scope, $http, requestService) {
	 $scope.fromStation = requestService.getFrom();
	 $scope.toStation = requestService.getTo();
	 $scope.tripDate = requestService.getTripDate();
	 $scope.placeTypes = [];
	 $scope.trainNum = null;
	 
	 $scope.createAlert = function() {
		 $scope.alertMsg = 'Sending data...';
		 $("[type=checkbox]:checked").each(function() {$scope.placeTypes.push($(this).val())})
		 $http.get('stations/createAlert', 
				 {params: {fromStation: $scope.fromStation, 
					 		toStation: $scope.toStation,
							tripDate: requestService.getTripDate(),
							placeTypes: $scope.placeTypes,
							trainNum: $scope.trainNum}})
							.success(function(res) {$scope.alertMsg = res});
	 };
}]);