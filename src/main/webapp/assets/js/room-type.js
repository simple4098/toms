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
					aList += "<option value='"+json.data[num].bangInnList[innList].id+"'>"+json.data[num].bangInnList[innList].innName+"</option>"
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
			var maiAccount = $(".maiAccount:checked").val();
			var postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'accountId': accountId,'maiAccount':maiAccount};
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
			if (data.indexOf("没有房型数据") == -1) {
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
	$('.btn-hand-make-order').attr('disabled', false);
	$('#to_datepicker').val(TC.plusDate(date, '30', 'd', 'yyyy-MM-dd'));
	var maiAccount = $(".maiAccount:checked").val();
	var postDate = {
		'startDate': $('#from_datepicker').val(),
		'endDate': $('#to_datepicker').val(),
		'tagId': tagId,
		'accountId': accountId,
		'maiAccount':maiAccount
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
				$('.mai-account').val(maiAccount);
				$('.room-type').append('<option value="">-请选择-</option>');
				for (var i = 0; i < data.roomType.roomStatus.length; i++) {
					var obj = data.roomType.roomStatus[i];
					$('.room-type').append('<option value="' +obj.roomTypeId + '">' + obj.roomTypeName + '</option>');
					$('.channel-order-code').val(null);
					$('.guest-name').val(null);
					$('.guest-mobile').val(null);
					$('.live-time').val(null);
					$('.leave-time').val(null);
					$('.total-price').val(null);
					$('.comment').val(null);

					$('#hangOrder').modal();
				}
			}

		}
	})
});

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
	var maiAccount = $(".maiAccount:checked").val();
	var postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'accountId': accountId,'maiAccount':maiAccount};
	getRoomType( postData );
});

//spinner
var spinner = $("#roomNum").spinner({
	create: function (event, ui) {
		//add custom classes and icons
		$(this)
			.next().addClass('btn btn-success btn-homeAmount-add').html('<i class="icon-plus"></i>')
			.next().addClass('btn btn-danger btn-homeAmount').html('<i class="icon-minus"></i>')

		//larger buttons on touch devices
//            if(ace.click_event == "tap") $(this).closest('.ui-spinner').addClass('ui-spinner-touch');
		$('.btn-homeAmount').attr('disabled', true);
	}

});
$('.btn-homeAmount').on('click', function () {
	var value = $('.home-amount').val();
	if (value <= 1) {
		$('.btn-homeAmount').attr('disabled', true);
		$('.btn-homeAmount-add').attr('disabled', false);
	} else {
		$('.btn-homeAmount').attr('disabled', false);
		$('.btn-homeAmount-add').attr('disabled', false);
	}
});
$('.btn-homeAmount-add').on('click', function () {
	var value = $('.home-amount').val();
	var maxNum = $('.max-num').val();
	if (null == maxNum || 0 == maxNum) {
		maxNum = 9;
	}
	if (value > maxNum) {
		$('.btn-homeAmount').attr('disabled', false);
		$('.btn-homeAmount-add').attr('disabled', true);
	} else {
		$('.btn-homeAmount').attr('disabled', false);
		$('.btn-homeAmount-add').attr('disabled', false);

	}
});

$('.room-type').change(function () {
	$('.room_type_name').val($(this).find("option:selected").text());
})

var $roomType = $('#roomType'),
	$roomNum = $('#roomNum'),
	$tel = $('#tel'),
	$tips = $('.tips'),
	$group1 = $('#inTime, #outTime'),
	$group2 = $('#inTime, #outTime, #roomType')

// 第一组联动
$group1.datepicker({
	showOtherMonths: true,
	selectOtherMonths: false,
	minDate: new Date()
});

$group1.change(function () {

	if (dateCheck()) {
		var url = $('.room-type-url').attr('data-url');
		$.ajax({
			type: 'post',
			url: url,
			data: $("#hand-order-form").serialize(),
			success: function (data) {
				$('.room-type option').remove();
				if (data.status) {
					$('.room-type').append('<option value="">-请选择-</option>');
					for (var i = 0; i < data.data.length; i++) {
						$('.room-type').append('<option value="' + data.data[i].roomTypeId + '">' + data.data[i].roomTypeName + '</option>');
					}
				}
			},
			error: function () {
				layer.alert("系统错误");
			}
		})
	}
})

// 第二组联动
$group2.change(function () {
	if (dateCheck()) {
		var url = $('.room-num-url').attr('data-url');
		var typeName = $('.room-type').children('option:selected').text();
		$('.type-name').val(typeName);
		$.ajax({
			type: 'post',
			url: url,
			dataType: 'json',
			data: $("#hand-order-form").serialize(),
			success: function (data) {
				if (data.status) {
					$('.home-amount').val(1);
					if (data.data <= 1) {
						$('.max-num').val(data.data);
						$('.btn-homeAmount').attr('disabled', true);
						$('.btn-homeAmount-add').attr('disabled', true);
					} else {
						$('.max-num').val(data.data - 1);
						$('.btn-homeAmount').attr('disabled', true);
						$('.btn-homeAmount-add').attr('disabled', false);
					}
				}
			}
		})
	}
})

// 验证手机号
$tel.blur(function () {
	var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
	var isMob = /^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
	var value = $(this).val().trim();
	if (!(isMob.test(value) || isPhone.test(value))) {
		$tips.html('请输入正确的手机号码！').show()
	}
	else {
		$tips.empty().hide()
	}
})

// 提交事件
$('#submitBtn').click(function () {
	var typeName = $('.room-type').children('option:selected').text();
	$('.type-name').val(typeName);
	if (formCheck() && dateCheck()) {
		var url = $(this).attr('data-url');
		layer.msg('加载中', {icon: 16});
		$(this).attr('disabled', true);
		$.ajax({
			url: url,
			type: 'post',
			dataType: 'json',
			data: $("#hand-order-form").serialize(),
			success: function (data) {
				layer.closeAll('loading');
				$(this).attr('disabled', false);
				if (data.status) {
					layer.confirm('提示信息：下单成功', {icon: 6});
					var startDate = $('#from_datepicker').val(), endDate = $('#to_datepicker').val(), tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
					var maiAccount = $(".maiAccount:checked").val();
					var postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'accountId': accountId,'maiAccount':maiAccount};
					getRoomType(postData);
					$('#hangOrder').modal('toggle');
				} else {
					layer.confirm('提示信息：下单失败,请检查所有参数是否完整=>' + data.message, {icon: 5});
					var startDate = $('#from_datepicker').val(), endDate = $('#to_datepicker').val(), tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
					var maiAccount = $(".maiAccount:checked").val();
					var postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'accountId': accountId,'maiAccount':maiAccount};
					getRoomType(postData);
					$('#hangOrder').modal('toggle');
				}
			}
		})
	}

	return false

})

// 表单验证
var formCheck = function () {
	var isComplete = true
	$tips.empty().hide()
	$('#hand-order-form input').each(function (i, el) {
		if (!$.trim($(this).val()) && $(this).attr('name') != 'tagId' && $(this).attr('name') != 'channelOrderCode') {
			var tips = $(this).prop('data-tips')
			$tips.html('请填写完相关信息').show()
			isComplete = false
			return false
		}
	})
	return isComplete
}

// 日期验证
var dateCheck = function () {
	var $inTime = $('#inTime').val(),
		$outTime = $('#outTime').val(),
		isDate = true

	if (new Date($inTime) > new Date($outTime)) {
		$tips.html('开始日期不得大于结束日期哦！').show()
		isDate = false
		return
	}
	if (new Date($inTime) == new Date($outTime)) {
		$tips.html('结束日期必须大于开始日期').show()
		isDate = false
		return
	}
	else {
		$tips.empty().hide()
	}
	if ($inTime == null || $inTime == "" || $outTime == null || $outTime == "") {
		return false;
	}
	return isDate
}
