'use strict';

angular.module('TrainCheckerApp').controller('profileController', ['$scope', '$http', function($scope, $http) {
	$scope.wrongPass = false;
	$scope.confirmFailed = false;
	$scope.passUpdated = false;
	$scope.infoUpdated = false;
	$scope.passInfo;
	$scope.passConfirm;
	
	function getUserInfo() {
		$http.get('userInfo').success(function(res){
			$scope.userInfo = res;
		});
	};
	
	getUserInfo();
	
	$scope.updateInfo = function() {
		$scope.wrongPass = false;
		$scope.confirmFailed = false;
		$scope.passUpdated = false;
		$scope.infoUpdated = false;
		$http.post('profile/updateUser', $scope.userInfo).success(function(res) {
			$scope.infoUpdated = true;
		});
	};
	
	$scope.updatePass = function() {
		$scope.wrongPass = false;
		$scope.confirmFailed = false;
		$scope.passUpdated = false;
		$scope.infoUpdated = false;
		if ($scope.passInfo.newPassword != $scope.passConfirm) {
			$scope.confirmFailed = true;
			return;
		}
		$http.post('profile/updatePass', $scope.passInfo).success(function(res) {
			if (res < 0) {
				$scope.wrongPass = true;
				return;
			} else {
				$scope.passUpdated = true;
				$scope.passInfo = {};
				$scope.passConfirm = '';
			}
		});
	};
	
}]);
