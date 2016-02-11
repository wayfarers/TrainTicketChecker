'use strict';

var TrainCheckerApp = {};

var app = angular.module('TrainCheckerApp', ['ngRoute', 'ui.bootstrap', 'filters']);

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
    
    $routeProvider.when('/', {
		templateUrl : 'views/home.html',
		controller : 'navigation'
	});
    
    $routeProvider.when('/registration', {
		templateUrl : 'views/registration.html',
		controller : 'userController'
	});
    
    $routeProvider.otherwise({redirectTo: '/'});
    
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

}]);
