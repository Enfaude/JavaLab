var solve = function(board){
	var min = 0;
	var max = 4;
	var x = Math.floor(Math.random()*(max-min+1)+min);
	var y = Math.floor(Math.random()*(max-min+1)+min);
    var coords = [x, y];
	var intArrType = Java.type("int[]")
	var result = Java.to(coords, intArrType)

    return result;
};

solve();