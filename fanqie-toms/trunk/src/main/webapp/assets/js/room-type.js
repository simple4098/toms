//ajax 获取标签楼盘
$(function(){
	var url= $('.data-url').attr('data-url');
	$.ajax({
		type:'GET',
		url:url+"?v"+new Date().getTime(),
		dataType:'json',
		success:function(json){
			if(json.data.length==0){
				return false;
			}
			var aLabel = ""; //存放客栈标签
			var aList = "";  //存放客栈列表
			// 遍历获取客栈标签
			for(var attr in json.data){
				aLabel += "<option value='"+json.data[attr].innLabelId+"'>"+json.data[attr].innLabelName+"</option>";
			}
			// 遍历获取客栈列表
			function getInnName(num){
				aList = "<option value>--请选择客栈--</option>";
				for(var innList in json.data[num].bangInnList){
					aList += "<option value='"+json.data[num].bangInnList[innList].accountId+"'>"+json.data[num].bangInnList[innList].innName+"</option>"
				};
			}
			// 默认加载第一个列表
			getInnName(0);
			// 联动刷新客栈列表
			$('#kz-tags-r').change(function(){
				var num = $(this).children(':selected').index();
				getInnName(num);
				$('#kz_item-r').html(aList);
			})
			// 写入DOM
			$('#kz-tags-r').html(aLabel);
			$('#kz_item-r').html(aList);
			var startDate = $('#from_datepicker').val(), endDate = $('#to_datepicker').val(), tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
			var postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'accountId': accountId};
			getRoomType(postData);
		}
	});
})

/**
 * 房态房量
 */
function getRoomType(postData){
	var url = $("#dataUrlId").attr("data-url")+"?v="+new Date().getTime();
	$.ajax({
		type:'POST',
		data:postData,
		url:url,
		dataType:'html',
		success:function(data){
			$("#roomTypeContainerId").empty();
			if (data.indexOf("没有") == -1) {
				$('.hand-btn').attr('disabled', false);
			} else {
				$('.hand-btn').attr('disabled', true);
			}
			$("#roomTypeContainerId").html(data);
		}
	})
}
$('#from_datepicker').change(function(){
	var url = $("#dataUrlId").attr("data-url")+"?v="+new Date().getTime();
	var tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
	var date = $(this).val();
	$('#to_datepicker').val( TC.plusDate(date, '30', 'd', 'yyyy-MM-dd') );
	var postDate = {'startDate': $('#from_datepicker').val(), 'endDate': $('#to_datepicker').val(),'tagId':tagId,'accountId':accountId};
	$.ajax({
		type:'POST',
		data: postDate,
		url:url,
		dataType:'html',
		success:function(data){
			$("#roomTypeContainerId").empty();
			if (data.indexOf("没有") == -1) {
				$('.hand-btn').attr('disabled', false);
			} else {
				$('.hand-btn').attr('disabled', true);
			}
			$("#roomTypeContainerId").html(data);
		}
	})
})
/*点击手动下单*/
$('.hand-btn').on('click', function () {
	var url = $("#dataUrlId").attr("data-url") + ".json?v=" + new Date().getTime();
	var tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
	var date = $(this).val();
	$('#to_datepicker').val(TC.plusDate(date, '30', 'd', 'yyyy-MM-dd'));
	var postDate = {
		'startDate': $('#from_datepicker').val(),
		'endDate': $('#to_datepicker').val(),
		'tagId': tagId,
		'accountId': accountId
	};
	$('.room-type option').remove();
	$.ajax({
		type: 'POST',
		data: postDate,
		url: url,
		dataType: 'json',
		success: function (data) {
			if (data.roomType != null) {
				$('.account-id').val(accountId);
				$('.tag-id').val(tagId);
				for (var i = 0; i < data.roomType.list.length; i++) {
					$('.room-type').append('<option value="' + data.roomType.list[i].roomTypeId + '">' + data.roomType.list[i].roomTypeName + '</option>');
					$('#hangOrder').modal();
				}
			}

		}
	})
});
/*当入住日期和离开日期失去光标时*/
$('.leave-time').on('blur', function () {
	var url = $("#dataUrlId").attr("data-url") + ".json?v=" + new Date().getTime();
	var tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
	var date = $(this).val();
	$('#to_datepicker').val(TC.plusDate(date, '30', 'd', 'yyyy-MM-dd'));
	var postDate = {
		'startDate': $('.live-time').val(),
		'endDate': $('.leave-time').val(),
		'tagId': tagId,
		'accountId': accountId
	};
	$('.room-type option').remove();
	$.ajax({
		type: 'POST',
		data: postDate,
		url: url,
		dataType: 'json',
		success: function (data) {
			if (data.roomType != null) {
				for (var i = 0; i < data.roomType.list.length; i++) {
					$('.room-type').append('<option value="' + data.roomType.list[i].roomTypeId + '">' + data.roomType.list[i].roomTypeName + '</option>');
				}
			}
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

$('#myButton').on('click', function(){
	var startDate = $('#from_datepicker').val(), endDate = $('#to_datepicker').val(), tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
	var postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'accountId': accountId};
	getRoomType( postData );
});
