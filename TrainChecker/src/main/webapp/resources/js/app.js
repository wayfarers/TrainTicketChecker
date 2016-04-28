'use strict';

var TrainCheckerApp = {};

var app = angular.module('TrainCheckerApp', ['ngRoute', 'ui.bootstrap', 'filters', 'ngLoadingSpinner']);

// Declare app level module which depends on filters, and services
app.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
    $routeProvider.when('/stations', {
        templateUrl: 'views/stations.html',
        controller: 'TrainController'
    });
    
    $routeProvider.when('/requestForm', {
        templateUrl: 'views/requestForm.html',
        controller: 'AlertController'
    });
    
    $routeProvider.when('/wip', {
        templateUrl: 'views/wip.html',
    });
    
    $routeProvider.when('/about', {
        templateUrl: 'views/about.html',
    });
    
    $routeProvider.when('/userRequests', {
        templateUrl: 'views/myRequests.html',
        controller: 'UserRequestController'
    });
    
    $routeProvider.when('/login', {
		templateUrl : 'views/login.html',
		controller : 'navigation'
	});
    
    $routeProvider.when('/logout', {
		templateUrl : 'views/logout.html',
		controller : 'navigation'
	});
    
    $routeProvider.when('/resetpass', {
		templateUrl : 'views/resetpass.html',
		controller : 'passResetController'
	});
    
    $routeProvider.when('/newPass', {
		templateUrl : 'views/newPass.html',
		controller : 'newPassController'
	});
    
    $routeProvider.when('/registration', {
		templateUrl : 'views/registration.html',
		controller : 'userController'
	});
    
    $routeProvider.when('/profile', {
		templateUrl : 'views/profile.html',
		controller : 'profileController'
	});
    
    $routeProvider.otherwise({redirectTo: '/stations'});
    
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

}]);
