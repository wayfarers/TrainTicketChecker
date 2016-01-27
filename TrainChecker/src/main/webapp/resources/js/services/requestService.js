'use strict';

angular.module('TrainCheckerApp')
	.service('requestService', function(){
		var fromStation = null;
		var toStation = null;
		var trainNum = null;
		var tripDate = null;
		
		this.setStations = function(from, to, date){
			fromStation = from;
			toStation = to;
			tripDate = date;
		}
		
		this.getFrom = function(){
			return fromStation;
		}
		
		this.getTo = function(){
			return toStation;
		}
		
		this.getTripDate = function(){
			return tripDate;
		}
	});