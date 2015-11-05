/**
 * Created by Administrator on 2015/7/6.
 */

$('.close-btn').click(function () {
    window.location.reload();
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
    var beginDate = $('.begin-date').val();
    var endDate = $('.end-date').val();
    var searchType = $('.search-type').val();
    if (beginDate == null || beginDate == '' || endDate == null || endDate == '' || searchType == null || searchType == '') {
        $('.begin-date').val(null);
        $('.end-date').val(null);
        $('.search-type').val(null);
    }
    $('.search-form').submit();
})
//渠道来源，订单状态联动
$('.channel-source,.order-status').change(function () {
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
})
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
                $('.room-type').html("预定房型：" + data.order.roomTypeName);
                $('.order-total').html("订单总额:" + data.order.totalPrice);
                $('.order-pre').html("预付金额:" + data.order.prepayPrice);
                var infoTime, priceInfo;
                if (data.order.dailyInfoses != null) {
                    for (var i = 0; i < data.order.dailyInfoses.length; i++) {
                        infoTime = infoTime + '<td class="del">' + data.order.dailyInfoses[i].dayDesc + '</td>';
                        priceInfo = priceInfo + '<td class="del">' + "成：" + data.order.dailyInfoses[i].costPrice + '<br/>售：' + data.order.dailyInfoses[i].price + '</td>'
                    }
                }
                $('.daily-info-time').append(infoTime);
                $('.daily-price').append(priceInfo);
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
//取消手动下单
$(".btn-cancel-order").on('click', function () {
    var url = $('.cancel-hand_order-url').val();
    var orderId = $('.cancel-order-id').val();
    layer.confirm('取消订单后，将无法恢复，确认要取消吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
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