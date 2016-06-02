'use strict';

angular.module('TrainCheckerApp').controller('AlertController', ['$scope', '$http', 'requestService', function($scope, $http, requestService) {
	 $scope.fromStation = requestService.getFrom();
	 $scope.toStation = requestService.getTo();
	 $scope.tripDate = requestService.getTripDate();
	 $scope.placeTypes = [];
	 $scope.trainNum = requestService.getTrainNum();
	 $scope.anyType = true;
	 $scope.noCheckbox = false;
	 
	 $scope.createAlert = function() {
		 $("[type=checkbox]:checked").each(function() {$scope.placeTypes.push($(this).val())})
		 if ($scope.placeTypes.length < 1) {
			 $scope.noCheckbox = true;
			 return;
		 }
		 $scope.alertMsg = 'Відправка даних...';
		 $http.get('stations/createAlert', 
				 {params: {fromStation: $scope.fromStation, 
					 		toStation: $scope.toStation,
							tripDate: requestService.getTripDate(),
							placeTypes: $scope.placeTypes,
							trainNum: $scope.trainNum}})
							.success(function(res) {$scope.alertMsg = res});
	 };
}]);