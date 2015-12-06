'use strict';

var TrainCheckerApp = {};

var app = angular.module('TrainCheckerApp', ['TrainCheckerApp.filters', 'TrainCheckerApp.services', 'TrainCheckerApp.directives', 'autocomplete', 'ngRoute', 'ui.bootstrap']);

// Declare app level module which depends on filters, and services
app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/stations', {
        templateUrl: 'stations/layout',
        controller: TrainController
    });
    
    $routeProvider.otherwise({redirectTo: '/stations'});

}]);
