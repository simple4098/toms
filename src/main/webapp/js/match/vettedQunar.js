
$("#sub-id").on("click",function(){
    var url= $(this).attr("data-url");
    var furl= $(this).attr("f-url");
    var otaInfoId= $("#otaInfoId").val();
    var otaType= $("#otaTypeId").val();
    var appKey= $("#appKey").val();
    var appSecret= $("#appSecret").val();
    var sessionKey= $("#sessionKey").val();
    var xcUserName= $("#xc_user_name").val();
    var xcPassword= $("#xc_password").val();
    var userId= $("#userId").val();
    var usedPriceModel = $("#usedPriceModel option:selected").val();
    if(sessionKey.length==0){
        $("#error").html("sessionKey 不能为空")
        return false;
    }

    if(usedPriceModel.length==0){
        $("#error").html("价格模式不能为空")
        return false;
    }
    $("#error").html("");
    var i = layer.load(0);
    $.ajax({
        url:url,
        type:'post',
        dataType:'json',
        data:{"appKey":appKey,"appSecret":appSecret,"sessionKey":sessionKey,"otaInfoId":otaInfoId,"otaType":otaType,"usedPriceModel":usedPriceModel,"xcUserName":xcUserName,"xcPassword":xcPassword,"userId":userId},
        success:function(data){
            if(data.status=='200'){
                layer.msg("保存成功!");
                layer.close(i);
                window.location.href=furl;
            }else{
                layer.close(i);
                $("#error").html(data.message)
            }

        },error:function(data){
            layer.close(i);
            $("#error").html(data.message)
        }
    })
});

