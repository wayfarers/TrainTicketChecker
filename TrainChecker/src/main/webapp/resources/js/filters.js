angular.module('filters', [])
	.filter('checkStatus', function() {
		return function(input) {
			return input ? 'Active' : 'Inactive';
		};
});