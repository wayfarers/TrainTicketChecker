'use strict';

angular.module('TrainCheckerApp').controller('UserRequestController', ['$scope', '$http', function($scope, $http) {
	
	$scope.requests = null;
	$scope.requestDetails = null;
	$scope.showExpired = true;
	
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
			return res;
		});
	};
	
	$scope.showDetails = function(request) {
		$scope.requestDetails = request;
//		$scope.requestDetails.lastResponse = $scope.getLastResponse();
	}
}]);