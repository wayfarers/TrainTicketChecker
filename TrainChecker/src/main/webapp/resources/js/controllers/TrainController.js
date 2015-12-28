'use strict';

/**
 * TrainController
 * @constructor
 */
var TrainController = function($scope, $http) {
	$scope.trains = [];
    $scope.fromStation = '';
    $scope.toStation = '';
    
    $scope.fetchStationList = function() {
        $http.get('stations/stationlist.json').success(function(stationList){
            $scope.stations = stationList;
        });
    };
    
    $scope.getFilteredStations = function(typed) {
		return $http.get('stations/getStations', {params: {rq: typed}})
			.then(function(i) {return i.data});
    };
    
    addDateTo($scope);
    
    $scope.sendRequest = function() {
		$http.get('stations/sendRequest', 
				{params: {fromStation: $scope.fromStation, 
							toStation: $scope.toStation,
							dt: $scope.dt}}).success(function(res) {$scope.trains = res.value; $scope.errorMsg = res.errorDescription});
	};
};

