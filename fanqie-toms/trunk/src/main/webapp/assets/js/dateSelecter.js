
// 按条件生成查询日期

$(function(){
	// datepicker
	$( "#from_datepicker, #to_datepicker" ).datepicker({
		showOtherMonths: true,
		selectOtherMonths: true,
		dateFormat: 'yy-mm-dd',
		maxDate: new Date()
	}); 
	
	// 日期初始化，默认选择昨天
	$('#from_datepicker, #to_datepicker').val( TC.plusDate(new Date(), '-1', 'd', 'yyyy-MM-dd'));
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

	// 筛选地区后，加载客栈列表
	var url= $('.data-url').attr('data-url');
	$.ajax({
		type:'GET',
		url:url+"?v"+new Date().getTime(),
	    dataType:'json',
	    success:function(json){
	    	var aLabel = ""; //存放客栈标签
	    	var aList = "";  //存放客栈列表
	    	// 遍历获取客栈标签
	    	for(var attr in json.data){
	    		aLabel += "<option value='"+json.data[attr].innLabelId+"'>"+json.data[attr].innLabelName+"</option>";
	    	}
	    	// 遍历获取客栈列表
    		function getInnName(num){
	    		aList = "";
	    		for(var innList in json.data[num].bangInnList){
	    			aList += "<option value='"+json.data[num].bangInnList[innList].innId+"'>"+json.data[num].bangInnList[innList].innName+"</option>"
	    		};
    		}
    		// 默认加载第一个列表
    		getInnName(0);
    		// 联动刷新客栈列表
    		$('#kz-tags').change(function(){
    			var num = $(this).children(':selected').index();
    			getInnName(num);
    			$('#kz_item').html(aList);
    		})
    		// 写入DOM
	    	$('#kz-tags').html(aLabel);
	    	$('#kz_item').html(aList);
           //加载运营概况数据；

	    }
	});
})