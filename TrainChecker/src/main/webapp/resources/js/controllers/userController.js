'use strict';

angular.module('TrainCheckerApp').controller('userController', ['$scope', '$http', function($scope, $http) {
	
	$scope.formError = false;
	$scope.registerSuccess = false;
	$scope.registerFailed = false;
	
	$scope.user = {login: null, password: null, email: null};
	
	$scope.register = function() {
		$scope.formError = false;
		$scope.registerSuccess = false;
		$scope.registerFailed = false;
		
		if ($scope.user.login == null && $scope.user.password == null && $scope.email == null) {
			$scope.formError = true;
		} else {
			$http.post('register', $scope.user).success(function(res) {
				$scope.registerSuccess = res;
				$scope.registerFailed = !res;
			}).error(function() {
				$scope.registerFailed = true;
			});
		}
	};
}]);