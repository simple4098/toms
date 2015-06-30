// 客户资料分析页面交互

$(function(){
	// loading
	$('#myButton').on('click', function () {
		var $btn = $(this).button('loading');
		// business logic...
		//$btn.button('reset')
		setTimeout(function(){
			$btn.button('reset');
		},1000)
	});

	// 百分比控件
	var oldie = /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase());
	$('.easy-pie-chart.percentage').each(function(){
		$(this).easyPieChart({
			barColor: $(this).data('color'),
			trackColor: '#EEEEEE',
			scaleColor: false,
			lineCap: 'butt',
			lineWidth: 8,
			animate: oldie ? false : 1000,
			size:60
		}).css('color', $(this).data('color'));
	});

	

	
})