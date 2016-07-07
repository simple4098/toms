//验证自定义渠道名称必填
$('.save-myself-channel, .update-myself-channel').on('click', function () {
    var channelName = $('.myself-channel-name').val();
    if (channelName == null || channelName == '') {
        layer.alert('提示信息：渠道自定义名称必填', {icon: 5})
    }
});

//验证自定义渠道名称必填
$('.save-myself-channel').on('click', function () {
    var channelName = $('.myself-channel-name').val();
    if (channelName == null || channelName == '') {
        layer.alert('提示信息：渠道自定义名称必填', {icon: 5})
    }
    var url = $(this).attr("data-url");
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        data: 'channelName='+channelName,
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
})
//更新自定义渠道
$('.update-myself-channel').on('click', function () {
    var channelName = $('.myself-channel-name-update').val();
    if (channelName == null || channelName == '') {
        layer.alert('提示信息：渠道自定义名称必填', {icon: 5})
    }
    var url = $('.update-myself-channel-url').attr("data-url");
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        data: 'channelName='+channelName,
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
})
//删除自定义渠道
$('.delete-myself-channel').on('click', function () {
    var url = $('.delete-myself-channel-url').attr("data-url");
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
})

//创建pms渠道名
$('.save-pms-channel-name').on('click', function () {
    var pmsChannelName = $('.pms-channel-name').val();
    if (pmsChannelName == null || pmsChannelName == '') {
        layer.alert('提示信息:PMS渠道名称必填', {icon: 5})
    }
    var url = $(this).attr("data-url");
    layer.msg('加载中', {icon: 16});
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        data: 'pmsChannelName='+pmsChannelName,
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
})

