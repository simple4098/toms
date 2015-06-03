$(function(){
	for(var i=1; i<$('.table tr').length; i++){
		$('.table tr').eq(i).children('td').slice(0,3).css('background','#e5f1f4');
	}

})