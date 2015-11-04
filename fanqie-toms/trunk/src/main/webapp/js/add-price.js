$(function(){
    $(".mai-fang").bind("click",function(){
        var _this = $(this);
        var innId = _this.attr("inn_id");
        var otaInfoId = _this.attr("ota_info_id");
        var url = _this.attr("data-url");
        var fan_hui_url = _this.attr("data-url-fan-hui");
        var data =  $(".table-right tr td[class='room-fc']");
        var reg = new RegExp("^[+-]?[0-9]+(.[0-9]{1,3})?$");
        var f = true;
        var f1 = true;
        if(data.length>0) {
            var arr = [];
            data.each(function (i) {
                var obj = $(this).find("input[name='startDateStr']");
                var startDateStr = obj.val();
                var roomTypeName = obj.attr("roomTypeName");
                var endDateStrValue = $(this).find("input[name='endDateStr']").val();
                var roomValue = $(this).find("input[name='roomValue']").val();
                arr[i] = {}
                arr[i].roomTypeId = obj.attr("roomtypeid");
                arr[i].startDateStr = startDateStr;
                arr[i].endDateStr = endDateStrValue;
                arr[i].priceChange = roomValue;
                arr[i].roomTypeName = roomTypeName;
                if(endDateStrValue.length!=0 || startDateStr.length!=0){
                    var dateS = new Date(startDateStr);
                    var dateE = new Date(endDateStrValue);
                    if(dateS - dateE >0){
                        f1 = false;
                        return false;
                    }
                }

                if(roomValue.length>0){
                    if(!reg.test(roomValue)){
                       f = false;
                       return false;
                    }
                }
                //console.log(obj.attr("roomtypeid") + " " + obj.val() + "endDate:" + endDateStrValue + " roomValue:" + roomValue);
            })
            if(!f){
                layer.alert("请输入正确的数字");
                return false;
            }if(!f1){
                layer.alert("日期有误,开始时间不能大于结束时间!");
                return false;
            }
            var json = JSON.stringify(arr);
            var price ='';
            $.each(arr, function(i, o){
                price+=o.priceChange;
            });
            if(price.length==0){
                layer.alert("特殊价格不能为空!");
                return false;
            }
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