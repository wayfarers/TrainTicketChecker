'use strict';

angular.module('TrainCheckerApp').controller('passResetController', ['$scope', '$http', function($scope, $http) {
	$scope.noSuchUser = false;
	$scope.done = false;
	$scope.serverError = false;
	$scope.login = '';
	
	$scope.resetPass = function() {
		$scope.serverError = false;
		$http.get('requestReset', 
				 {params: {login: $scope.login}})
							.success(function(res) {
								$scope.serverError = false;
								if(res === "no_user") {
									$scope.noSuchUser = true;
								} else {
									$scope.done = true;
								}
							})
							.error(function(res) {$scope.serverError = true});
	}
	
}]);
