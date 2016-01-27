'use strict';

angular.module('TrainCheckerApp').controller('UserRequestController', ['$scope', '$http', function($scope, $http) {
	$scope.requests = null;
	
	
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
	
}]);