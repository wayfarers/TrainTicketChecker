'use strict';

/**
 * TrainController
 * @constructor
 */
var TrainController = function($scope, $http) {
    $scope.stations = [];
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
    
    
    
   // $scope.fetchStationList();
};

