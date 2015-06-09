var span = '<span class="middle" name="middle" disabled="false" style="color: red">此项必填</span>';

/*修改密码-header*/
$('.btn-sub-head').on('click', function () {
    $('.help-password-head .middle').remove();
    if ($('.paswd').val() == null || $('.paswd').val() == '') {
        $('.help-password-head').append(span);
        return false;
    }
    if ($('.paswd').val() != null && $('.paswd').val() != '') {
        $('.update-password').submit();
    }
});
/*新增员工*/
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
            //do same thing!
        }
    });
});
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
/*删除模板弹出层把noticeId传入*/
$('.delete-notice').on('click', function () {
    var noticeId = $(this).attr('data-whatever');
    $('.notice-id').val(noticeId);
    var url = $(this).attr('data-url');
    $('.data-url').val(url);
});
/*删除模板*/
$('.btn-submit').on('click', function () {
    var url = $('.data-url').val();
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (!data.status) {

            } else {
                window.location.reload();
            }
        },
        error: function () {
            //do same thing!
        }
    });
});
/*必填验证*/
var span = '<span class="middle" name="middle" disabled="false" style="color: red">此项必填</span>';
$('.btn-sub').on('click', function () {
    $('.help-notice-title .middle').remove();
    $('.help-notice-content .middle').remove();
    $('.help-label-name .middle').remove();
    if ($('.notice-title').val() == null || $('.notice-title').val() == '') {
        $('.help-notice-title').append(span);
        return false;
    }
    if ($('.notice-content').val() == null || $('.notice-content').val() == '') {
        $('.help-notice-content').append(span);
        return false;
    }
    if ($('.notice-content').val() != null && $('.notice-content').val() != '' && $('.notice-title').val() != null && $('.notice-title').val() != '') {
        $('.notice-form').submit();
    }

});
//新增分类
$('.btn-new-label').on('click', function () {
    $('.help-label-name .middle').remove();
    if ($('.label-name').val() == null || $('.label-name').val() == '') {
        $('.help-label-name').append(span);
        return false;
    }
    if ($('.label-name').val() != null && $('.label-name').val() != '') {
        $('.new-label').submit();
    }
});

/*删除分类传入labelId*/
$('.delete-label').on('click', function () {
    var labelId = $(this).attr('data-whatever');
    $('.label-id').val(labelId);
    var url = $(this).attr('data-url');
    $('.data-url').val(url);
});
/*删除分类*/
$('.btn-submit').on('click', function () {
    var url = $('.data-url').val();
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (!data.status) {
                layer.msg(data.message);
            } else {
                window.location.reload();
            }
        },
        error: function () {
            //do same thing!
        }
    });
});

/*编辑分类*/
$('.update-label').on('click', function () {
    $('.help-label-name .middle').remove();
    var data = $(this).attr('data-whatever');
    var url = $(this).attr('data-url');
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.status) {
                $('.label-name').val(data.data.labelName);
                $('.labelId').val(data.data.id);
            } else {
                layer.msg(data.message);
            }
        }
    });
});
//编辑分类
$('.btn-update-label').on('click', function () {
    $('.help-label-name .middle').remove();
    if ($('.label-name').val() == null || $('.label-name').val() == '') {
        $('.help-label-name').append(span);
        return false;
    }
    if ($('.label-name').val() != null && $('.label-name').val() != '') {
        $('.update-label-form').submit();
    }
});

/*通知消息联动*/
$('.notice').on('change', function () {
    var noticeId = $(this).val();
    var url = $(this).attr('data-url');
    if (noticeId != null && noticeId != '') {
        $.ajax({
            url: url + noticeId,
            type: 'post',
            dataType: 'json',
            success: function (data) {
                $('.notice-content').val(data.data.noticeContent);
            }
        })
    } else {
        $('.notice-content').val('');
    }
});

/*选择接收的客栈*/
$('.all-inn').on('click', function () {
    if ($(this).is(':checked')) {
        $('.inn-label').attr('checked', true);
        $('.inn').attr('checked', true);
    }
    if (!$(this).is(':checked')) {
        $('.inn-label').attr('checked', false);
        $('.inn').attr('checked', false);
    }
});
/*绑定客栈更新*/
$('.bang-inn-update').on('click', function () {
    $('.update-inn').submit();
});

/*加盟编号*/
$('.code').on('blur', function () {
    var span = '<span class="middle" name="middle" disabled="false" style="color: red">该加盟编号已经存在，请重新填写</span>';
    var data = $(this).val();
    var url = $(this).attr('data-url');
    $('.help-code .middle').remove();
    $('.bang-inn-update').attr('disabled', false);
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        data: '&code=' + data,
        success: function (data) {
            if (!data.status) {
                $('.help-code').append(span);
                $('.bang-inn-update').attr('disabled', true);
            } else {
                $('.help-code .middle').remove();
                $('.bang-inn-update').attr('disabled', false);
            }
        }
    });
})
