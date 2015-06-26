/**
 * Created by Administrator on 2015/6/26.
 */
$('.btn-send').on('click', function () {
    var url = $(this).attr('data-url');
    var flag = false;
    var checkGroup = $(".checkUnit input[type='checkbox']");
    for (var i = 0; i < checkGroup.length; i++) {
        if (checkGroup.eq(i).prop("checked")) {
            flag = true;
            break;
        }
    }
    var flag1 = false;
    if ($('.send-type1').prop('checked') || $('.send-type2').prop('checked')) {
        flag1 = true;
    }
    if ($('.notice-content').val() == null || $('.notice-content').val() == '') {
        layer.alert('提示信息：请填写通知内容', {icon: 5});
    } else if (!flag) {
        layer.alert('提示信息：请选择接收的客栈', {icon: 5});
    } else if (!flag1) {
        layer.alert('提示信息：请选择发送方式', {icon: 5});
    }
    if ($('.notice-content').val() != null && $('.notice-content').val() != '' && flag && flag1) {
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            data: $('.data-form').serialize(),
            success: function (data) {
                if (data.status) {
                    layer.alert('提示信息：发送成功！', {icon: 6}, function () {
                        window.location.reload();
                    });

                } else {
                    layer.alert('提示信息：发送失败！', {icon: 5});
                }
            },
            error: function () {
                layer.alert('系统错误', {icaon: 5})
            }
        });
    }

});
$(function () {
    // 全选&&反选 所有客栈
    $("#ckAll").click(function () {
        $("input.checkbox").prop("checked", this.checked);
    });

    $("input.checkbox").click(function () {
        var $subs = $("input.checkbox");
        $("#ckAll").prop("checked", $subs.length == $subs.filter(":checked").length ? true : false);
    });
    // 全选&&反选 分组客栈
    $("input.checkbox").click(function () {
        if (!$(this).attr('id')) {
            var keywords = $(this).attr('data-name'),
                $subs = $("input[data-name=" + keywords + "]"),
                $parent = $("#" + keywords);
            $parent.prop("checked", $subs.length == $subs.filter(":checked").length ? true : false);
        }
        else {
            var id = $(this).attr('id');
            $("input[data-name=" + id + "]").prop("checked", this.checked);
        }
    });
});
