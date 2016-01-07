'use strict';

var TrainCheckerApp = {};

var app = angular.module('TrainCheckerApp', ['ngRoute', 'ui.bootstrap']);

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
    
    $routeProvider.otherwise({redirectTo: '/stations'});
    
    

}]);
