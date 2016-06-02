angular.module('TrainCheckerApp').controller(
		'navigation',
		function($rootScope, $scope, $http, $location, $window, $templateCache, $route) {
			$scope.error = false;
			
			var templatesList = ["wip.html", "stations.html", "myRequests.html", "requestForm.html", "profile.html"];
			
			var refreshTemlpates = function() {
				for (i = 0; i < templatesList.length; i++) {
					$templateCache.remove("views/" + templatesList[i]);
				}
			};
			
			var authenticate = function(credentials, callback) {
				
				//TODO: JS error if using cyrillic: Failed to execute 'btoa' on 'Window': The string to be encoded contains characters outside of the Latin1 range.
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
							$location.path("#/userRequests");
						} else {
							$route.reload();
						}
					} else {
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