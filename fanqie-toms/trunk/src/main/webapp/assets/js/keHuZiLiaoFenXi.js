// 客户资料分析页面交互
$(function(){
	// loading
	$('#myButton').on('click', function () {
	    var $btn = $(this).button('loading')
	    // business logic...
	    //$btn.button('reset')
	    setTimeout(function(){
	    	$btn.button('reset');
	    },1000)
    });

	// datepicker
	$( "#from_datepicker, #to_datepicker" ).datepicker({
		showOtherMonths: true,
		selectOtherMonths: true,
		format: 'yyyy-mm-dd'
		//isRTL:true,

		
		/*
		changeMonth: true,
		changeYear: true,
		
		showButtonPanel: true,
		beforeShow: function() {
			//change button colors
			var datepicker = $(this).datepicker( "widget" );
			setTimeout(function(){
				var buttons = datepicker.find('.ui-datepicker-buttonpane')
				.find('button');
				buttons.eq(0).addClass('btn btn-xs');
				buttons.eq(1).addClass('btn btn-xs btn-success');
				buttons.wrapInner('<span class="bigger-110" />');
			}, 0);
		}
	*/
	}); 

	// 百分比控件配置
	var oldie = /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase());
	$('.easy-pie-chart.percentage').each(function(){
		$(this).easyPieChart({
			barColor: $(this).data('color'),
			trackColor: '#EEEEEE',
			scaleColor: false,
			lineCap: 'butt',
			lineWidth: 8,
			animate: oldie ? false : 1000,
			size:50
		}).css('color', $(this).data('color'));
	});

	// 快捷日期
	$('#fast_select').change(function(){
		var date = new Date(),
			year = date.getFullYear(),
			month = date.getMonth()+1,
			day = date.getDate();
		function getYestoday(date){      
		    var yesterday_milliseconds=date.getTime()-1000*60*60*24;       
		    var yesterday = new Date();       
		    yesterday.setTime(yesterday_milliseconds);       
		        
		    var strYear = yesterday.getFullYear();    
		    var strDay = yesterday.getDate();    
		    var strMonth = yesterday.getMonth()+1;  
		    if(strMonth<10)    
		    {    
		        strMonth="0"+strMonth;    
		    }    
		    datastr = strYear+"-"+strMonth+"-"+strDay;  
		    return datastr;  
		}
		switch($(this).val()){
				case '昨日':
						$('#from_datepicker, #to_datepicker').val( year +'-'+ month +'-'+ (day-1));
						break;
				case '本月':
		}
	})
   	
})