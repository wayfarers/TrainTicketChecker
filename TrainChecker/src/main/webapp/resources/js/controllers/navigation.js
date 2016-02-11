angular.module('TrainCheckerApp').controller(
		'navigation',
		function($rootScope, $scope, $http, $location, $window, $templateCache, $route) {

			var templatesList = ["wip.html", "stations.html", "myRequests.html"];
			
			var refreshTemlpates = function() {
				for (i = 0; i < templatesList.length; i++) {
					$templateCache.remove("views/" + templatesList[i]);
				}
			};
			
			var authenticate = function(credentials, callback) {

				var headers = credentials ? {
					authorization : "Basic "
							+ btoa(credentials.username + ":"
									+ credentials.password)
				} : {};

				$http.get('user', {
					headers : headers
				}).success(function(data) {
					if (data.name) {
						$rootScope.authenticated = true;
						refreshTemlpates();
					} else {
						$rootScope.authenticated = false;
					}
					callback && callback();
				}).error(function() {
					$rootScope.authenticated = false;
					callback && callback();
				});

			};

			authenticate();
			$scope.credentials = {};
			$scope.login = function() {
				authenticate($scope.credentials, function() {
					if ($rootScope.authenticated) {
						$scope.error = false;
						refreshTemlpates();
						if ($location.path() == '/login') {
							$location.path("/");
						} else {
							$route.reload();
						}
					} else {
						$location.path("/login");
						$scope.error = true;
					}
				});
			};
			
			$scope.logout = function() {
				$http.post('logout', {}).success(function() {
					$rootScope.authenticated = false;
				}).error(function(data) {
				    $rootScope.authenticated = false;
				});
				$scope.credentials = {};
				refreshTemlpates();
				$location.path("/logout");
			};
		});