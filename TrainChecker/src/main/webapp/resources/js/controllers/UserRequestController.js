'use strict';

angular.module('TrainCheckerApp').controller('UserRequestController', ['$scope', '$http', '$location','requestService', '$filter', function($scope, $http, $location, requestService, $filter) {
	
	$scope.userInfo = null;
	$scope.requests = null;
	$scope.requestDetails = null;
	$scope.showExpired = false;
	$scope.showInactive = true;
	$scope.lastResponse = {};
	
	$scope.getUserInfo = function() {
		$http.get('userInfo').success(function(res){
			$scope.userInfo = res;
		});
	};
	
	$scope.getUserInfo();
	
	$scope.getTypesAsArray = function(str) {
		return str.split(',');
	}
	
	$scope.getUserRequests = function() {
		$http.get('userRequests/getUserRequests').success(function(res){
			$scope.requests = res;
		});
	};
	
	$scope.getUserRequests();
	
	$scope.changeStatus = function(requestId) {
		$http.get('userRequests/changeRequestStatus', {params: {requestIdStr: requestId}}).success(function() {
			$scope.getUserRequests();
		});
	};
	
	$scope.getLastResponse = function() {
		$http.get('userRequests/lastResponse', {params: {ticketRequestId: $scope.requestDetails.request.id}}).success(function(res) {
			$scope.lastResponse = res;
		});
	};
	
	$scope.showDetails = function(request) {
		if (!request.expired) {
			requestService.setStations(
					request.request.from.stationName, 
					request.request.to.stationName, 
					request.request.tripDate);
			requestService.setSearch(true);
			$location.path('/stations');
		}
		
		
//		if ($scope.requestDetails == null || request.id != $scope.requestDetails.id) {
//			$scope.requestDetails = request;
//		} else {
//			//deselect current request
//			$scope.requestDetails = null;
//		}
		
	}
}]);