/**
 * Created by Administrator on 2015/6/26.
 */
//分页方法
function page(page) {
    $("#pageId").attr("value", page);
    $("#form-page").submit();
}
var span = '<span class="middle" name="middle" disabled="false" style="color: red">此项必填</span>';
/*删除员工弹出层把userid传入*/
$('.del-btn').on('click', function () {
    var userId = $(this).attr('data-whatever');
    $('.user-id').val(userId);
    var dataUrl = $(this).attr('data-url');
    $('.data-url').val(dataUrl);
    var url = $(this).attr('json-url');
    var delUrl = $(this).attr('del-url');
    $('.del-url').val(delUrl);
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.status) {
                $('.user-list option').remove();
                for (var i = 0; i < data.data.length; i++) {
                    $('.user-list').append('<option value="' + data.data[i].id + '">' + data.data[i].userName + '</option>')
                }
            }
        }
    })
});
//删除form提交
$('.btn-del-1').on('click', function () {
    $('.delete-user').submit();
});
/*删除员工*/
$('.btn-del').on('click', function () {
    var url = $('.del-url').val();
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (!data.status) {
                window.location.reload();
            } else {
                window.location.reload();
            }
        },
        error: function () {
            //do same thing!
        }
    });
});
$("#ckAll").click(function () {
    $("input[name='permissionIds']").prop("checked", this.checked);
});
$("#ckAll-new").click(function () {
    $("input[name='permissionIds']").prop("checked", this.checked);
});
$("input[name='permissionIds']").click(function () {
    var $subs = $("input[name='permissionIds']");
    $("#ckAll").prop("checked", $subs.length == $subs.filter(":checked").length ? true : false);
    $("#ckAll-new").prop("checked", $subs.length == $subs.filter(":checked").length ? true : false);
});
/*修改权限*/
$('.permission-btn').on('click', function () {
    var url = $(this).attr('data-url');
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.status) {
                $('.permission-user-id').val(data.user.id);
                if (data.user.dataPermission == 0) {
                    $('.dataPermission0').attr('checked', true);
                } else {
                    $('.dataPermission1').attr('checked', true);
                }
                if (data.data.length != 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        var checkClass = data.data[i].id;
                        $('.' + checkClass).attr('checked', true);
                    }
                }
                $('#jurisdiction').modal();
            }
        }
    })
});
/*修改权限*/
$('.btn-update-permission').on('click', function () {
//        $('.update-permission-form').submit();
    var url = $(this).attr('data-url');
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        data: $("#permission-form").serialize(),
        success: function (data) {
            if (data.status) {
                layer.alert('提示信息：修改成功', {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：修改失败', {icon: 5});
            }
        }, error: function () {
            layer.msg('系统错误');
        }
    })
});
/*新增员工验证*/
$('.btn-info').on('click', function () {
    $('.help-login-name .middle').remove();
    $('.help-password .middle').remove();
    $('.help-tel .middle').remove();
    $('.help-name .middle').remove();
    if ($('.login-name').val() == null || $('.login-name').val() == '') {
        $('.help-login-name').append(span);
        return false;
    }
    if ($('.pawd').val() == null || $('.pawd').val() == '') {
        $('.help-password').append(span);
        return false;
    }
    if ($('.tel').val() == null || $('.tel').val() == '') {
        $('.help-tel').append(span);
        return false;
    }
    if ($('.user-name').val() == null || $('.user-name').val() == '') {
        $('.help-name').append(span);
        return false;
    }
    //验证通过跳到第二部
    $('#jurisdictionnew').modal();
    //将一步的值传递过去
    $('.login-name-permission').val($('.login-name').val());
    $('.password-permission').val($('.pawd').val());
    $('.telephone-permission').val($('.tel').val());
    $('.user-name-permission').val($('.user-name').val());
//        return false;
});
/*验证登陆名*/
$('.login-name').on('blur', function () {
    $('.help-login-name .middle').remove();
    var value = $(this).val();
    var name = $(this).attr('name');
    var url = $(this).attr('data-url');
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        data: name + '=' + value,
        success: function (data) {
            var span = '<span class="middle" name="middle" disabled="false" style="color: red">登录名已经存在</span>';
            if (!data.status) {
                $('.help-login-name').append(span);
                $('.btn-sub').attr('disabled', true);
            } else {
                $('.help-login-name .middle').remove();
                $('.btn-sub').attr('disabled', false);

            }
        },
        error: function () {
            layer.msg('系统错误');
        }
    });
});
/*新增权限*/
$('.btn-new-permission').on('click', function () {
    var url = $(this).attr('data-url');
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        data: $("#permission-form-new").serialize(),
        success: function (data) {
            if (data.status) {
                layer.alert('提示信息：新增员工成功', {icon: 6}, function () {
                    window.location.reload();
                });
            } else {
                layer.alert('提示信息：新增员工失败', {icon: 5}, function () {
                    window.location.reload();
                });
            }
        }, error: function () {
            layer.msg('系统错误');
        }
    })
});
$('.close').on('click', function () {
    window.location.reload();
});
