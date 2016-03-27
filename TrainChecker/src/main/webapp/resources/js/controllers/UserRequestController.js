'use strict';

angular.module('TrainCheckerApp').controller('UserRequestController', ['$scope', '$http', function($scope, $http) {
	
	$scope.requests = null;
	$scope.requestDetails = null;
	$scope.showExpired = true;
	$scope.showInactive = true;
	$scope.lastResponse = {};
	
	$(document).ready(function () {  
		  var top = $('#details').offset().top - parseFloat($('#details').css('marginTop').replace(/auto/, 0));
		  $(window).scroll(function (event) {
		    // what the y position of the scroll is
		    var y = $(this).scrollTop();
		  
		    // whether that's below the form
		    if (y >= top) {
		      // if so, ad the fixed class
		      $('#details').addClass('fixed');
		    } else {
		      // otherwise remove it
		      $('#details').removeClass('fixed');
		    }
		  });
	});
	
	$scope.getTypesAsArray = function(str) {
		return str.split(',');
	}
	
	$scope.getUserRequests = function() {
		$http.get('userRequests/getUserRequests').success(function(res){
			$scope.requests = res;
		});
	};
	
	$scope.getUserRequests();
	
	$scope.changeStatus = function(requestId) {
		$http.get('userRequests/changeRequestStatus', {params: {requestIdStr: requestId}}).success(function() {
			$scope.getUserRequests();
		});
	};
	
	$scope.getLastResponse = function() {
		$http.get('userRequests/lastResponse', {params: {ticketRequestId: $scope.requestDetails.request.id}}).success(function(res) {
			$scope.lastResponse = res;
		});
	};
	
	$scope.showDetails = function(request) {
		if ($scope.requestDetails == null || request.id != $scope.requestDetails.id) {
			$scope.requestDetails = request;
		} else {
			//deselect current request
			$scope.requestDetails = null;
		}
		
	}
}]);