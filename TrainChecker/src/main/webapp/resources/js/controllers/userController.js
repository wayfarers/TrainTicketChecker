'use strict';

angular.module('TrainCheckerApp').controller('userController', ['$scope', '$http', function($scope, $http) {
	
	$scope.formError = false;
	$scope.registerSuccess = false;
	$scope.registerFailed = false;
	$scope.usernameBusy = false;
	
	$scope.user = {login: null, password: null, email: null};
	
	$scope.register = function() {
		$scope.formError = false;
		$scope.registerSuccess = false;
		$scope.registerFailed = false;
		$scope.usernameBusy = false;
		
		if ($scope.user.login == null && $scope.user.password == null && $scope.email == null) {
			$scope.formError = true;
		} else {
			$http.post('register', $scope.user).success(function(res) {
				if (res == 0) {
					$scope.registerSuccess = true;
				} else if (res == 1) {
					$scope.usernameBusy = true;
				} else {
					$scope.registerFailed = true;
				}
			}).error(function() {
				$scope.registerFailed = true;
			});
		}
	};
}]);