'use strict';

angular.module('TrainCheckerApp').controller('newPassController', ['$scope', '$http', '$location', function($scope, $http, $location) {
	$scope.error = false;
	$scope.tk_ok = false;
	$scope.userId;
	
	$scope.checkToken();
	
	$scope.checkToken = function() {
		$http.get('newPass', 
				 {params: $location.search()})
							.success(function(res) {
								if(res < 1) {
									$scope.error = true;
								} else {
									$scope.tk_ok = true;
									$scope.userId = res;
								}
							})
							.error(function(res) {$scope.error = true});
	}
	
}]);
