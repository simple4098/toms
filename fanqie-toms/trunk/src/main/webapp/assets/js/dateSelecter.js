
// 按条件生成查询日期

$(function(){
	// datepicker
	$( "#from_datepicker, #to_datepicker" ).datepicker({
		showOtherMonths: true,
		selectOtherMonths: true,
		dateFormat: 'yy-mm-dd',
		maxDate: new Date()
	}); 
	// 快捷日期
	$('#fast_select').change(function(){
		var date = new Date(),
			$formDay = $('#from_datepicker'),
			$toDay = $('#to_datepicker');
		
		switch( $(this).val() ){
			case '昨日' :
					$('#from_datepicker, #to_datepicker').val( TC.plusDate(date, '-1', 'd', 'yyyy-MM-dd'));
					break;
			case '本月' :
					$formDay.val( TC.plusDate(date, -date.getDate()+1, 'd', 'yyyy-MM-dd'));
					$toDay.val( TC.plusDate(date, 0, 'd', 'yyyy-MM-dd'));
					break;
			case '近7天' :
					$formDay.val( TC.plusDate(date, -6, 'd', 'yyyy-MM-dd'));
					$toDay.val( TC.plusDate(date, 0, 'd', 'yyyy-MM-dd'));
					break;
			case '近30天' :
					$formDay.val( TC.plusDate(date, -29, 'd', 'yyyy-MM-dd'));
					$toDay.val( TC.plusDate(date, 0, 'd', 'yyyy-MM-dd'));
					break;
		}
	});
})