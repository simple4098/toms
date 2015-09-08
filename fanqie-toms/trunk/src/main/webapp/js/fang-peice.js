$('.room-price-class').on('click', function () {
    var $t = $(this);
    if(!$t.hasClass("push")) {
    $t.addClass("push");
    var otaInfoId = $(this).attr('ota_info_id');
    var roomTypeId = $(this).attr('room_type_id');
    var innId = $(this).attr('inn_id');
    var roomTypeName = $(this).attr('type_name');
    var data_url  = $(this).attr('data-url');
    var url =data_url+"?innId="+innId+"&otaInfoId="+otaInfoId+"&roomTypeId="+roomTypeId+"&roomTypeName="+roomTypeName;
        $.ajax({
            type:'POST',
            url:url,
            dataType:'html',
            success:function(data){
                layer.open({
                    title:"房型特殊价格设置",
                    type: 1,
                    shade: 0.6,
                    area: ['516px', '400px'],
                    shadeClose: true, //开启遮罩关闭
                    content: data
                });
                $t.removeClass("push");
            },error:function(data){
                $t.removeClass("push");
                alert(data);
            }
        })
    }

});

$('.tp-price').on('click', function () {
    var $this = $(this);
    if( !$this.hasClass('push')){
        layer.load(0, {time: 3*1000});
        $this.addClass("push");
        var otaInfoId = $(this).attr('ota_info_id');
        var innId = $(this).attr('inn_id');
        var accountId = $(this).attr('account_id');
        var url = '<c:url value="/distribution/ajax/tpPrice.json"/>'+"?innId="+innId+"&otaInfoId="+otaInfoId+"&accountId="+accountId
        $.ajax({
            type:'POST',
            url:url,
            dataType:'json',
            success:function(data){
                $this.removeClass("push");
                if(!data.status){
                    layer.alert("推送失败,检查是否房价信息存在!");
                }else{
                    layer.msg('同步完成');
                }
            },error:function(data){
                $this.removeClass("push");
                layer.alert("同步失败!")
            }
        })
    }else{
        layer.alert("正在同步请稍后...")
    }

});