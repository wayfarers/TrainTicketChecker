'use strict';

angular.module('TrainCheckerApp').controller('AlertController', ['$scope', '$http', 'requestService', function($scope, $http, requestService) {
	 $scope.fromStation = requestService.getFrom();
	 $scope.toStation = requestService.getTo();
	 $scope.tripDate = requestService.getTripDate();
	
	 $scope.createAlert = function() {
			$scope.alertMsg = 'Sending data...';
			$http.get('stations/createAlert', 
					{params: {fromStation: $scope.fromStation, 
								toStation: $scope.toStation,
								tripDate: requestService.getTripDate()}})
								.success(function(res) {$scope.alertMsg = res});
		}
}]);