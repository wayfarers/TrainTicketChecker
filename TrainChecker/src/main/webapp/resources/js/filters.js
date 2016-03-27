angular.module('filters', [])
	.filter('checkStatus', function() {
		return function(input) {
			return input ? 'Active' : 'Inactive';
		};
})
	.filter('train', function() {
		return function(input) {
			if (input == null || input == '') {
				return "ANY";
			} else {
				return input;
			}
		};
	})
	
	.filter('noUpdate', function() {
		return function(input) {
			if (input == null || input == '') {
				return "no update";
			} else {
				return input;
			}
		};
	})
;