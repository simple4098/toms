$(function(){
    $(".mai-fang").bind("click",function(){
        var _this = $(this);
        var innId = _this.attr("inn_id");
        var otaInfoId = _this.attr("ota_info_id");
        var url = _this.attr("data-url");
        var fan_hui_url = _this.attr("data-url-fan-hui");
        var data =  $(".table-right tr td[class='room-fc']");
        if(data.length>0) {
            var arr = [];
            data.each(function (i) {
                var obj = $(this).find("input[name='startDateStr']");
                var endDateStrValue = $(this).find("input[name='endDateStr']").val();
                var roomValue = $(this).find("input[name='roomValue']").val();
                arr[i] = {}
                arr[i].roomTypeId = obj.attr("roomtypeid");
                arr[i].startDateStr = obj.val();
                arr[i].endDateStr = endDateStrValue;
                arr[i].priceChange = roomValue;
                //console.log(obj.attr("roomtypeid") + " " + obj.val() + "endDate:" + endDateStrValue + " roomValue:" + roomValue);
            })
            var json = JSON.stringify(arr);
            console.log(json);
            //console.log("innId:"+innId+" otaInfoId:"+otaInfoId);
            var i = layer.load(0);

            $.ajax({
                data:{"innId":innId,"otaInfoId":otaInfoId,"json":json},
                type:'post',
                dataType:'json',
                url:url,
                success:function(data){
                    if(data.status=='200'){
                        layer.msg("同步成功!");
                        window.location.href = fan_hui_url;

                    }else{
                        layer.close(i);
                        layer.msg("同步失败:"+data.message);
                    }

                },error:function(data){
                    layer.close(i);
                    layer.msg("同步失败:"+data.message);
                }
            })
        }

    });

});