'use strict';

/**
 * TrainController
 * @constructor
 */
var TrainController = function($scope, $http) {
    $scope.stations = {};
    $scope.selectedStation = '';
    
    $scope.fetchStationList = function() {
        $http.get('stations/stationlist.json').success(function(stationList){
            $scope.stations = stationList;
        });
    };
    
    $scope.updateStations = function(typed) {
    	if(typed.length > 1) {
    		$http.get('stations/getStations', {params: {rq: typed}}).success(function(stationList){
    		$scope.stations = stationList;
    		});
    	}
    };
    
   // $scope.fetchStationList();
};