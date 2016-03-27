'use strict';

angular.module('TrainCheckerApp').controller('newPassController', ['$scope', '$http', '$location', function($scope, $http, $location) {
	$scope.wrongLink = false;
	$scope.error = false;
	$scope.tk_ok = false;
	$scope.pw_ok = false;
	$scope.pw_not_match = false;
	$scope.userId;
	$scope.pass1;
	$scope.pass2;
	
	$scope.checkToken = function() {
		$http.get('checkLink', 
				 {params: $location.search()})
							.success(function(res) {
								if(res < 1) {
									$scope.wrongLink = true;
								} else {
									$scope.tk_ok = true;
									$scope.userId = res;
								}
							})
							.error(function(res) {$scope.error = true});
	}
	
	$scope.setPass = function() {
		$scope.error = false;
		$scope.tk_ok = false;
		$scope.pw_ok = false;
		$scope.pw_not_match = false;
		if($scope.pass1 != $scope.pass2) {
			$scope.pw_not_match = true;
		} else {
			$http.get('setNewPass', {params: {tk: $location.search().tk,
				pw: $scope.pass1}}).success(function(res) {
					if(res < 0) {
						$scope.error = true;
					} else {
						$scope.pw_ok = true;
					}
				});
		}
	}
	
	$scope.checkToken();
	
}]);
