'use strict';

angular.module('TrainCheckerApp')
	.service('requestService', function(){
		var fromStation = null;
		var toStation = null;
		var trainNum = null;
		var tripDate = null;
		var tripDateStr = null;
		var search = false;
		
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
		
		this.setDateStr = function(date) {
			tripDateStr = date;
		}
		
		this.setTrainNum = function(num) {
			trainNum = num;
		}
		
		this.getTrainNum = function() {
			return trainNum;
		}
		
		this.isSearch = function() {
			return search;
		}
		
		this.setSearch = function(searchFlag) {
			search = searchFlag;
		}
		
		this.clear = function () {
			fromStation = null;
			toStation = null;
			trainNum = null;
			tripDate = null;
			tripDateStr = null;
			search = false;
		}
	});