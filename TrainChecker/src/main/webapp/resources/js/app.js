'use strict';

var TrainCheckerApp = {};

var app = angular.module('TrainCheckerApp', ['ngRoute', 'ui.bootstrap', 'filters']);

// Declare app level module which depends on filters, and services
app.config(['$routeProvider', function ($routeProvider) {
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
    
    $routeProvider.otherwise({redirectTo: '/stations'});
    
    

}]);
