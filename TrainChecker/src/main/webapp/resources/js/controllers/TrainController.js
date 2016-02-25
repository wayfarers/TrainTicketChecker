'use strict';

/**
 * TrainController
 * @constructor
 */

angular.module('TrainCheckerApp').controller('TrainController', ['$scope', '$http', '$location', 'requestService', function($scope, $http, $location, requestService) {
	
//	function checkAuthentication() {
//		if ($rootScope.authenticated) {
//			$location.path("/");
//			$scope.error = false;
//		} else {
//			$location.path("/login");
//			$scope.error = true;
//		}
//	}
//	
//	checkAuthentication();
	
	addDateTo($scope);
	
	$scope.trains = [];
    $scope.fromStation = '';
    $scope.toStation = '';
    $scope.trainNum = null;
    
    $scope.fromStation = requestService.getFrom();
    $scope.toStation = requestService.getTo();
    $scope.tripDate = requestService.getTripDate();
    
    
    $scope.fetchStationList = function() {
        $http.get('stations/stationlist.json').success(function(stationList){
            $scope.stations = stationList;
        });
    };
    
    $scope.getFilteredStations = function(typed) {
		return $http.get('stations/getStations', {params: {rq: typed}})
			.then(function(i) {return i.data});
    };
    
    $scope.sendRequest = function() {
    	console.log($scope.dt);
		$http.get('stations/sendRequest', 
				{params: {fromStation: $scope.fromStation, 
							toStation: $scope.toStation, 
							dt: $("#calendar").val()}}).success(function(res) {$scope.trains = res.value; $scope.errorMsg = res.errorDescription});
	};
	
	$scope.requestAlert = function() {
		requestService.setStations($scope.fromStation, $scope.toStation, $("#calendar").val());
//		requestService.setDateStr($("#calendar").val());
		$location.path('/requestForm');
	}
	
	$scope.swapStations = function() {
		var tmp = $scope.fromStation;
		$scope.fromStation = $scope.toStation;
		$scope.toStation = tmp;
	}
}]);
