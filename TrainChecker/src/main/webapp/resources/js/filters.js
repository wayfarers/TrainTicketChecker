angular.module('filters', [])
	.filter('checkStatus', function() {
		return function(input) {
			return input ? 'Активний' : 'Неактивний';
		};
})
	.filter('train', function() {
		return function(input) {
			if (input == null || input == '') {
				return "Будь який";
			} else {
				return input;
			}
		};
	})
	
	.filter('noUpdate', function() {
		return function(input) {
			if (input == null || input == '') {
				return "оновлень поки немає";
			} else {
				return input;
			}
		};
	})
	
	.filter('placeType', function() {
		return function(input) {
			if (input == 'ANY') {
				return "Будь які";
			} else if (input == 'PLATZ') {
				return "Плацкарт";
			} else if (input == 'COUPE') {
				return "Купе";
			} else if (input == 'SIT1') {
				return "Сидячий 1го класу";
			} else if (input == 'SIT2') {
				return "Сидячий 2го класу";
			} else if (input == 'SIT3') {
				return "Сидячий 3го класу";
			} else if (input == 'LUX') {
				return "Люкс";
			} else if (input == 'SOFT') {
				return "М'яке";
			} else {
				return input;
			} 
		};
	})
;