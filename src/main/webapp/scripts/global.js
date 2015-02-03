//Merge two arrays and remove the duplicate items. 
function mergeItemArray(arr1, arr2) {
//	var _arr = [];
//	for (var i = 0; i < arr1.length; i++) {
//		_arr.push(arr1[i]);
//	}
	var _dup;
	for (var i = 0; i < arr2.length; i++){
		_dup = false;
		for (var _i = 0; _i < arr1.length; _i++){
			if (arr2[i].id === arr1[_i].id){
				_dup = true;
				break;
			}
		}
		if (!_dup){
			arr1.push(arr2[i]);
		}
	}
}