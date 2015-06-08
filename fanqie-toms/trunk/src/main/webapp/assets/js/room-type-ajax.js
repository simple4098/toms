$('#from_datepicker').change(function(){
	var url = $("#dataUrlId").attr("data-url")+"?v="+new Date().getTime();
	var date = $(this).val();
	var tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
	$('#to_datepicker').val( TC.plusDate(date, '30', 'd', 'yyyy-MM-dd') );
	var postDate = {'startDate': $('#from_datepicker').val(), 'endDate': $('#to_datepicker').val(),'tagId':tagId,'accountId':accountId};
	$.ajax({
		type:'POST',
		data: postDate,
		url:url,
		dataType:'html',
		success:function(data){
			$("#roomTypeContainerId").empty();
			$("#roomTypeContainerId").html(data);
		}
	})
})
//上一月
$('#prevM').on('click',function(){
	var date = $('#from_datepicker').val();
	$('#from_datepicker').val( TC.plusDate(date, '-1', 'M', 'yyyy-MM-dd') ).change();
})
// 下一月
$('#nextM').on('click',function(){
	var date = $('#from_datepicker').val();
	$('#from_datepicker').val( TC.plusDate(date, '1', 'M', 'yyyy-MM-dd') ).change();
})

// datepicker
$( "#from_datepicker, #to_datepicker" ).datepicker({
	showOtherMonths: true,
	selectOtherMonths: true,
	dateFormat: 'yy-mm-dd',
	maxDate: new Date()
});
$.datepicker.regional['zh-CN'] = {
	closeText: '关闭',
	prevText: '<上月',
	nextText: '下月>',
	currentText: '今天',
	monthNames: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
	monthNamesShort: ['一','二','三','四','五','六',
		'七','八','九','十','十一','十二'],
	dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
	dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
	dayNamesMin: ['日','一','二','三','四','五','六'],
	weekHeader: '周',
	dateFormat: 'yy-mm-dd',
	firstDay: 1,
	isRTL: false,
	showMonthAfterYear: true,
	yearSuffix: '年'};
$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
function setSize(){
	var baseWidth = $(window).width()-210;
	$('.main-content').width( baseWidth );
	$('.table-right').width(baseWidth-130);
}
setSize();
$(window).on('resize', setSize);