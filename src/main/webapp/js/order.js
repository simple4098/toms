/**
 * Created by Administrator on 2015/7/6.
 */
//$("#startDate").val(formatDate(new Date(),"1"))
//$("#endDate").val(formatDate(new Date())) //1代表显示日期的1号
function formatDate(date,oneday) {
    var year = date.getFullYear();
    var mouth = date.getMonth()+1;
    var day = date.getDate();
    if(parseInt(mouth)<10) {
        mouth="0"+mouth;
    }
    if(parseInt(day+1)<10) {
        day="0"+day;
    }
    if(oneday) {
        day = "01"
    }
    var Date = year+"-"+mouth+'-'+day;
    return Date;
}
$("#selectOperator").on("focus",function() {
     $("#operatorList").show();
})
$("#orderStatus").on("focus",function() {
	$("#orderStatusList").show();
})
/*$("#selectOperator").on("blur",function() {
    $("#operatorList").hide();
})*/
$('.close-btn').click(function () {
    window.location.reload();
})
$("#enterOperators").on("click",function() {
    var operator = [],
        str = ''
    $.each($("input[name='name']"),function(key,value) {
        var json = {
            id	: $("input[name='userid']").eq(key).val(),
            userName : $(this).next().text()
        }
        if($(this).is(":checked")) {
           json.selected = true;
            if(str) {
                str+=(","+$(this).next().html())
            }else {
                str+=$(this).next().html()
            }

        }else {
           json.selected = false;
        }
        $("#selectOperator").val(str);
        operator[key] = json;
    })
     $("#operatorList").hide();
    $("#operatorsJson").val(JSON.stringify(operator));
//    alert($("#operatorsJson").val());
   
})
$("#enterOrderStatus").on("click",function() {
	var operator = [],
	str = ''
		$.each($("input[name='status-name']"),function(key,value) {
			
			if($(this).is(":checked")) {
				if(str) {
					str+=(","+$(this).next().html())
				}else {
					str+=$(this).next().html()
				}
				
			}
			$("#orderStatus").val(str);
		})
		$("#orderStatusList").hide();
//    alert($("#operatorsJson").val());
	
})
//分页方法
function page(page) {
    $("#pageId").attr("value", page);
    $("#form-page").submit();
}
/**
 * 查询
 */
$('.btn-search').on('click', function () {
    $('.search-form').submit();
})
//渠道来源，订单状态联动
/*$('.order-status').change(function () {
    $('#channelSource').val($('.channel-source').val());
    $('#orderStatus').val($('.order-status').val());
    $('.channel-source-text').val($('.channel-source').val());
    $('.order-status-text').val($('.order-status').val());
    var beginDate = $('.begin-date').val();
    var endDate = $('.end-date').val();
    var searchType = $('.search-type').val();
    if (beginDate == null || beginDate == '' || endDate == null || endDate == '' || searchType == null || searchType == '') {
        $('.begin-date').val(null);
        $('.end-date').val(null);
        $('.search-type').val(null);
    }
    $('.search-form').submit();
})*/
/*查询订单*/
$('.btn-order').on('click', function () {
    var url = $(this).attr('data-url');
    $('.del').remove();
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.status) {
                $('.cancel-order-id').val(data.order.id);
                $('.order-status').html("订单状态：" + data.order.orderStatusDesc);
                $('.inn-name').html("客栈名称:" + data.order.innName);
                $('.guest-name').html("客人姓名:" + data.order.guestName);
                $('.guest-mobile').html("客人手机号码：" + data.order.guestMobile);
                if(data.order.refundStatus) {
                	$('.cancel-remark').html("备注：扣款");
                } else {
                	$('.cancel-remark').html("备注：不扣款");
                }
                //$('.room-type').html("预定房型：" + data.order.roomTypeName);
                var tatalPrice = ""
                if (data.order.channelSource == 'HAND_ORDER') {
                    tatalPrice =  data.order.prepayPrice;
                } else {
                    tatalPrice =  data.order.totalPrice;
                }
                $("#orderPrice").html(tatalPrice+"/"+data.order.prepayPrice)
                $("#profit").html(data.profit);
                if(data.order.operator == null){
                    $("#operator").html("系统");
                }else{
                    $("#operator").html(data.order.operator);
                }
                $("#costPrice").html(data.order.costPrice)
                if(data.otherTotalCost && data.otherTotalCost.length) {
                    $.each(data.otherTotalCost,function() {
                        $("#otherTotalCost").append("<dd>"+this.consumerProjectName+"总成本："+this.totalCost)
                    })
                }
                $.each(data.order.dailyInfoses,function() {
                    $("#roomTypesInfo").append("<ul class='room-types-info'><li>房型："+this.roomTypeName+"</li><li>日期："+this.dayDesc+"</li><li><dl><dd>成："+this.costPrice+"<dd>售："+this.price+"</dd></dl></li></ul>")
                })
                if(data.order.orderOtherPriceList.length) {
                    $("#orderOtherPriceList").show()
                    $.each(data.order.orderOtherPriceList,function() {
                        $("#orderOtherPriceList").append("<ul class='room-types-info'><li>"+this.consumerProjectName+this.priceName+"</li><li><dl><dd>单价："+this.price+"<dd>数量："+this.nums+"</dd></dl></li></ul>")
                    })
                }else {
                    $("#orderOtherPriceList").hide();
                }
                if (data.order.channelSource == "HAND_ORDER" && data.order.orderStatus == "CONFIM_AND_ORDER") {
                    $('.btn-cancel-order').attr('disabled', false);
                } else {
                    $('.btn-cancel-order').attr('disabled', true);

                }
                $('#jurisdiction').modal();
            }
        },
        error: function () {
            layer.msg('系统错误');
        }
    })
});
/*确认并执行下单*/
$('.btn-confirm').on('click', function () {
    var url = $(this).attr('data-url');
    $('.confirm-url').val(url);
    $('#makeOrder').modal();
});
/*确认下单按钮*/
$('.btn-confirm-sure').on('click', function () {
    var url = $('.confirm-url').val();
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    window.location.reload();
                });
            }
        }
        ,
        error: function () {
            layer.closeAll('loading');
            layer.msg('系统错误');
        }
    })
});
/*直接拒绝*/
$('.btn-refues').on('click', function () {
    var url = $(this).attr('data-url');
    $('.refues-url').val(url);
    $('#myRefuesOrder').modal();
});
/*直接拒绝订单确认*/
$('.btn-refues-sure').on('click', function () {
    var url = $('.refues-url').val();
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    window.location.reload();
                });
            }
        },
        error: function () {
            layer.closeAll('loaidng');
            layer.msg("系统错误");
        }
    })
});

/*确认但不执行下单*/
$('.btn-confirm-no').on('click', function () {
    var url = $(this).attr('data-url');
    $('.confirm-no-url').val(url);
    $('#myConfirmNoOrder').modal();
});

$('.btn-confirm-no-sure').on('click', function () {
    var url = $('.confirm-no-url').val();
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    window.location.reload();
                });
            }
        },
        error: function () {
            layer.closeAll('loading');
            layer.msg("系统错误");
        }
    })
});
$('.btn-default').on('click', function () {
    $(".modal-backdrop").remove();
})
//同意退款
$('.pay-back-sure').on('click', function () {
    var url = $(this).attr('data-url');
    $('.pay-back-sure-url').val(url);
    $("#payBackSure").modal();
});
$('.btn-pay-back-sure').on('click', function () {
    var url = $('.pay-back-sure-url').val();
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    window.location.reload();
                });
            }
        },
        error: function () {
            layer.closeAll('loading');
            layer.msg("系统错误");
        }
    })
});
//拒绝退款
$('.refuse-pay-back').on('click', function () {
    var url = $(this).attr('data-url');
    $('.refuse-pay-back-url').val(url);
    $("#refusePayBack").modal();
});
$('.btn-refuse-pay-back-sure').on('click', function () {
    var url = $('.refuse-pay-back-url').val();
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    window.location.reload();
                });
            }
        },
        error: function () {
            layer.closeAll('loading');
            layer.msg("系统错误");
        }
    })
});


//信用住同意退款
$('.apply-back-sure').on('click', function () {	
    var url = $(this).attr('data-url');
    $('.apply-back-sure-url').val(url);
    $("#applyBackSure").modal();
});
//信用住同意退款
$('.btn-apply-back-sure').on('click', function () {
    var url = $('.apply-back-sure-url').val();
    var refundStatus=$("input[name='refundStatus']:checked").val()
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url+'&refundStatus='+refundStatus,
        type: 'get',
        dataType: 'json',
        success: function (data) {
            layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    window.location.reload();
                });
            }
        },
        error: function () {
            layer.closeAll('loading');
            layer.msg("系统错误");
        }
    })
});

//信用住拒绝退款
$('.refuse-apply-back').on('click', function () {
    var url = $(this).attr('data-url');
    $('.refuse-apply-back-url').val(url);
    $("#refusePayBack").modal();
});
$('.btn-refuse-apply-back-sure').on('click', function () {
    var url = $('.refuse-apply-back-url').val();
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        success: function (data) {
        	layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    window.location.reload();
                });
            }
        },
        error: function () {
            layer.closeAll('loading');
            layer.msg("系统错误");
        }
    })
});




//取消手动下单
$(".btn-cancel-order").on('click', function () {
    var url = $('.cancel-hand_order-url').val();
    var orderId = $('.cancel-order-id').val();
    layer.confirm('取消订单后，将无法恢复，确认要取消吗？', {
        btn: ['确定', '取消'],
        offset: ['240px' , '40%']
    },function () {
        layer.msg('加载中', {icon: 16});
        $.ajax({
            url: url + "?orderId=" + orderId,
            type: 'post',
            dataType: 'json',
            success: function (data) {
                layer.closeAll('loading');
                if (data.status) {
                    layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                        window.location.reload();
                    });
                } else {
                    layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                        window.location.reload();
                    });
                }
            },
            error: function () {
                layer.closeAll('loading');
                layer.msg("系统错误");
            }
        })
        //layer.msg('的确很重要', {icon: 1});
    }, function () {
    });
});

//导出订单
$('.btn-export-form').on('click', function () {
    var beginDateStr = $('.begin-date').val();
    var endDateStr = $('.end-date').val();
    if (null == beginDateStr || '' == beginDateStr || null == endDateStr || '' == endDateStr) {
        layer.alert("导出订单时，必须写入时间段，开始时间和结束时间之差必须在31天之内", {icon: 5});
        return false;
    } else {
        var beginDate = new Date(beginDateStr);
        var endDate = new Date(endDateStr);
        if ((endDate - beginDate) / 86400000 > 30) {
            layer.alert("请检查输入的时间，开始时间和结束时间只差必须在31天之内", {icon: 5});
            return false;
        } else {
            var searchType = $('.search-type').val();
            var channelOrderCode = $('.keyword').val();
            var channelSource = $('.channel-source').val();
            var operatorsJson = $('#operatorsJson').val();
            var selectOrderStatus = $('#orderStatus').val();
            var selectOperator = $('#selectOperator').val();
            var innName = $('.select-hotel').val();
            $('.search-type-form').val(searchType);
            $('.channel-order-code-form').val(channelOrderCode);
            $('.channel-source-form').val(channelSource);
            $('.begin-date-form').val(beginDateStr);
            $('.end-date-form').val(endDateStr);
            $('.operatorsJson-form').val(operatorsJson);
            $('.selectedOperators-form').val(selectOperator);
            $('.selectStatusString-form').val(selectOrderStatus);
            $('.innName-form').val(innName);
            var objForm = $('#export-order-form');
            objForm.submit();
        }
    }
});
//下单到oms
$('.create-order-oms').on('click', function () {
    var url = $(this).attr('data-url');
    $('.create-order-oms-url').val(url);
    $("#createOrderOms").modal();
});
//取消订单同步oms
$('.cancel-order-oms').on('click', function () {
    var url = $(this).attr('data-url');
    $('.cancel-order-oms-url').val(url);
    $("#cancelOrderOms").modal();
});
//确认下单到oms
$('.btn-create-order-oms').on('click', function () {
    var url = $('.create-order-oms-url').val();
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    window.location.reload();
                });
            }
        },
        error: function () {
            layer.closeAll('loading');
            layer.msg("系统错误");
        }
    })
});
//确认取消订单同步oms
$('.btn-cancel-order-oms').on('click', function () {
    var url = $('.cancel-order-oms-url').val();
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    window.location.reload();
                });
            }
        },
        error: function () {
            layer.closeAll('loading');
            layer.msg("系统错误");
        }
    })
});
//点击关闭订单
$('.close-order').on('click', function () {
    var url = $(this).attr('data-url');
    $('.close-order-url').val(url);
    $("#closeOrder").modal();
})
//确认关闭订单
$('.btn-close-order').on('click', function () {
    var url = $('.close-order-url').val();
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    window.location.reload();
                });
            }
        },
        error: function () {
            layer.closeAll('loading');
            layer.msg("系统错误");
        }
    })
});

$('.oms-order-status').on('click', function () {
    var url = $(this).attr('data-url');
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    layer.closeAll();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    layer.closeAll();
                });
            }
        },
        error: function () {
            layer.closeAll('loading');
            layer.msg("系统错误");
        }
    });

});
$('.pms-order-status').on('click', function () {
    var url = $(this).attr('data-url');
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            layer.closeAll('loading');
            if (data.status) {
                layer.alert('提示信息：' + data.message, {icon: 6}, function () {
                    layer.closeAll();
                });
            } else {
                layer.alert('提示信息：' + data.message, {icon: 5}, function () {
                    layer.closeAll();
                });
            }
        },
        error: function () {
            layer.closeAll('loading');
            layer.msg("系统错误");
        }
    });
});