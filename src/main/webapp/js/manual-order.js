$(function(){
    var CONST = {
        CHANNELID : {
            SALECHANNELID : 1, //卖价Id
            TAGCHANNELID : 0 //底价Id
        },
        IDNEED : { //其它消费是否必须
            NEED : true, //必须
            NOTNEED : false
        }
    }
    var $manualOrder = $("#manualOrder"),
        $liveTimeString = $("#liveTimeString"),
        $leaveTimeString = $("#leaveTimeString"),
        $todayCheckin = $("#todayCheckin"),
        $tomarowCheckin = $("#tomarowCheckin"),
        $oneNight = $("#oneNight"),
        $twoNight = $("#twoNight"),
        $threeNight= $("#threeNight"),
        $guestMobile = $("#guestMobile"),
        $selectRoomType = $(".selectRoomType"),
        $roomNumber = $(".roomNumber"),
        $plus = $(".plus-icon"),
        $reduce = $(".reduce-icon"),
        $number = $(".number"),
        $otherList = $("#otherList"),
        $notNeedList = $("#notNeedList"),
        $guestName = $("#guestName"),
        $otherPaynumber = $("#otherPaynumber"),
        $comment = $("#comment"),
        $roomTypeNumPlus = $(".plus-icon"),
        $roomTypeNumReduce = $(".reduce-icon"),
        $payNumberReduce = $("#payNumberReduce"),
        $payNumberPlug = $("#payNumberPlug"),
        $bangInnId = $("#bangInnId"),
        $channelOrderCode = $("#channelOrderCode"),
        $payment = $("#payment"),
        $saveManualOrder = $("#saveManualOrder"),
        bangInnId = $('#kz_item-r').val(),//客栈Id
        maiAccount,//卖价或者底价
        roomTypedata = [] //存放房型数据,
    $.each($("input[name='maiAccount']"),function() {
        if($(this).is(":checked"))
        {
            maiAccount = $(this).val();
        }
    })
    $manualOrder.on("click",function() {
        //清空数据
        $("#otherList,#notNeedList").html("")
        $("#channelOrderCode,#guestName,#guestMobile,#liveTimeString,#leaveTimeString,.roomNumber,.number,#otherPaynumber,#payment,#comment").val("")
        $selectRoomType.html("<option>请选择房型</option>")
        if($(".room-type-operate").length!==1){
            var len = $(".room-type-operate").length-1
            $.each($(".room-type-operate"),function(key,val) {
                if(key==len) {
                    return false
                }
                $(this).remove()
            })
        }
        //请求其它消费数据
        var data = {
            "otherList" : [{
                "consumerProjectName":"门票",
                "priceName": "全价",
                "price": 100,
                "status": true
            },
            {
                "consumerProjectName":"车票",
                "priceName": "可乐",
                "price": 50,
                "status": false
            },
            {
                "consumerProjectName":"门票",
                "priceName": "半价",
                "price": 30,
                "status": true
            },
            {
                "consumerProjectName":"车票",
                "priceName": "咖啡",
                "price": 20,
                "status": false
            },
            {
                "consumerProjectName":"门票",
                "priceName": "矿泉水",
                "price": 50,
                "status": false
            },
            {
                "consumerProjectName":"车票",
                "priceName": "半价",
                "price": 30,
                "status": true
            }]
        }
        $.each(data.otherList,function(){
            if(this.status==CONST.IDNEED.NEED){
                $otherList.append("<label class='col-sm-3 control-label no-padding-right'><span class='red'>*</span>"+this.consumerProjectName+"("+this.priceName+")</label><div class='col-sm-3'><input type='text' placeholder='填写消费数量' class='ipt number'></div>")
            }else {
                $notNeedList.append("<option>"+this.priceName+"</option>")
            }
        })
    })
    $liveTimeString.datepicker({
        onClose : function( selectedDate ) {
            $leaveTimeString.datepicker("option","minDate", selectedDate)
        },
        onSelect : function() {
            if($leaveTimeString.val()){
                getRoomType()
            }
        }
    })
    $leaveTimeString.datepicker({
        onClose : function( selectedDate ) {
            $liveTimeString.datepicker("option","maxDate", selectedDate)
        },
        onSelect : function() {
            if($liveTimeString.val()){
                getRoomType()
            }
        }
    })
    $todayCheckin.on("click",function() {
        $liveTimeString.val(formatDate(new Date()));
        $leaveTimeString.datepicker("option","minDate", $liveTimeString.val())
        if($leaveTimeString.val()){
            getRoomType()
        }
    })
    $tomarowCheckin.on("click",function() {
        $liveTimeString.val(TC.plusDate(new Date(), '1', 'd', 'yyyy-MM-dd'));
        $leaveTimeString.datepicker("option","minDate", $liveTimeString.val())
        if($leaveTimeString.val()){
            getRoomType()
        }
    })
    $oneNight.on("click",function() {
        if($liveTimeString.val()!="") {
            $leaveTimeString.val(TC.plusDate(new Date($liveTimeString.val()),'1','d','yyyy-MM-dd'))
            getRoomType()
        }else {
            alert("请先选择入住时间！")
        }

    })
    $twoNight.on("click",function() {
        if($liveTimeString.val()!="") {
            $leaveTimeString.val(TC.plusDate(new Date($liveTimeString.val()),'2','d','yyyy-MM-dd'))
            getRoomType()
        }else {
            alert("请先选择入住时间！")
        }
    })
    $threeNight.on("click",function() {
        if($liveTimeString.val()!="") {
            $leaveTimeString.val(TC.plusDate(new Date($liveTimeString.val()),'3','d','yyyy-MM-dd'))
            getRoomType()
        }else {
            alert("请先选择入住时间！")
        }
    })
    selectRoomTypeChange($selectRoomType.eq(0),0)
    removeRoomType($(".remove-room-type").eq(0))
    function selectRoomTypeChange($selectRoomType,i) {
       /* var oldRoomtypeid
        $("#roomOperate").off().on("click",$selectRoomType,function() {
            oldRoomtypeid = $selectRoomType.find("option:selected").attr("data-roomtypeid")
        })*/
        $("#roomOperate").off().on("change",$selectRoomType,function() {
            var newRoomtypeid = $selectRoomType.find("option:selected").attr("data-roomtypeid")
            var url = $("#roomNumUrl").attr("data-url")
            $roomTypeNumPlus = $(".plus-icon")
            $roomTypeNumReduce = $(".reduce-icon")
            $roomNumber = $(".roomNumber")
            $(this).next().val(1)
            var json = {
                bangInnId : $('#kz_item-r').val(),
                leaveTimeString : $leaveTimeString.val(),
                liveTimeString : $liveTimeString.val(),
                maiAccount : maiAccount,
                roomTypeId : $(this).find("option:selected").attr("data-roomtypeid")
            }
            var maxRoomNumber;
            $.ajax({
                type:'GET',
                data: json,
                url:url,
                dataType:'html',
                success:function(rs){
                    rs = $.parseJSON(rs)
                    if( rs.data) {
                        maxRoomNumber = rs.data
                        $roomNumber.eq(i).val(1)
                        numberPlugReduce($roomTypeNumPlus.eq(i),$roomTypeNumReduce.eq(i),$roomNumber.eq(i),maxRoomNumber);
                    }
                },
                error: function() {
                    alert("获取该房型最大房量失败，请重试！")
                }
            })
        })
    }
    function removeRoomType(obj) {
        obj.on("click",function() {
            if($(".room-type-operate").length!==1){
                var obj = $(this).prevAll(".selectRoomType").find("option:checked")
                var roomtypeid = obj.attr("data-roomtypeid")
                var val = obj.val()
                $(this).parent().remove();
                var Lool = false
                $.each($(".selectRoomType").find("option"),function() {
                    if(roomtypeid == $(this).attr("data-roomtypeid")) {
                        Lool = true
                    }
                })
                if(!Lool) {
                    $.each($(".selectRoomType"),function(){
                        $(this).append("<option data-roomtypeid="+roomtypeid+">"+val+"</option>")
                    })
                }
            }
        })
    }
    $("#otherList").on("blur",".number",function() {
        if(!isPInt($(this).val())){
           alert("请输入正整数！")
        }
    })
    $("#otherPaynumber").on("blur",function() {
        if(!isPInt($(this).val())){
            alert("请输入正整数！")
        }
    })
    $("#addRoomType").on("click",function() {
        var selectRoomType  = $selectRoomType.eq($(".room-type-operate").length-1)
        if(selectRoomType.find("option:selected").attr("data-roomtypeid")==undefined){
            alert("请先选择房型再继续新增房型！")
            return;
        }
        if(selectRoomType.find("option").length == 2) {
            alert("所有房型已被选择！")
            return
        }
        var html = $(".room-type-operate:last").html()
        $(".add-room-type").before("<div class='mgb-10 room-operate room-type-operate'>"+html+"</div>")
        $selectRoomType = $(".selectRoomType")
        var i = $(".room-type-operate").length-1
        selectRoomTypeChange($selectRoomType.eq(i),i)
        removeRoomType($(".remove-room-type").eq(i))
        //removeRoomType($(".remove-room-type").eq(i),i)
        $.each($(".selectRoomType:last").find("option"),function() {
            if($(this).attr("data-roomtypeid") !== undefined) {
                if($(this).attr("data-roomtypeid") == $selectRoomType.eq(i-1).find("option:selected").attr("data-roomtypeid")) {
                    $(this).remove();
                }
            }
        })
    })
    function isPInt(str) {//判断是否为正整数
        var g = /^[1-9]*[1-9][0-9]*$/;
        return g.test(str);
    }
    numberPlugReduce($payNumberPlug,$payNumberReduce,$otherPaynumber);
    function numberPlugReduce($plus,$reduce,obj,maxRoomNumber) {//加减
        $plus.off().on("click",function() {
            if(maxRoomNumber && obj.val()==maxRoomNumber.toString() || obj.val()==""){
                return false;
            }
            obj.val(parseInt(obj.val())+1)
        })
        $reduce.off().on("click",function() {
            if(parseInt(obj.val())<=0 || obj.val()==""){
                return false
            }else{
                obj.val(parseInt(obj.val())-1)
            }

        })
    }
    $saveManualOrder.on("click",function() {
        if(!$guestName.val()) {
            alert("客人姓名必填！")
            return;
        }
        if(!$guestMobile.val()) {
            alert("手机号必填！")
            return;
        }
        ValidTelNum();//验证手机号
        if(!$liveTimeString.val()) {
            alert("入住时间必填！")
            return;
        }
        if(!$leaveTimeString.val()) {
            alert("离店时间必填！")
            return;
        }
        var Lool = false
        $.each($roomNumber,function() {
            if(!$(this).val()) {
                alert("房间数量必填！")
                Lool = true;
                return false;
            }
        })
        if(Lool) {
            return;
        }
        $number = $(".number")

        $.each($number,function(){
            if(!$(this).val()) {
                alert("消费数量必填！")
                Lool = true;
                return false;
            }
            if(!isPInt($(this).val())){//验证是否为正整数
                alert("请输入正整数！")
                Lool = true;
                return false;
            }
        })
        if(Lool){
           return;
        }
        if($selectRoomType.val()=="请选择房型"){
            alert("请选择房型！")
            return;
        }
        if(!$payment.val()) {
            alert("订单总销售额必填！")
            return;
        }
        if(!isPInt($payment.val())){//验证是否为正整数
            alert("请输入正整数！")
            Lool = true;
            return false;
        }
        var homeAmount,roomTypeId,roomTypeName;
        //请求保存接口传递参数
        var json = {
            bangInnId : $('#kz_item-r').val(),
            channelOrderCode : $channelOrderCode.val(),
            comment : $comment.val(),
            guestMobile : $guestMobile.val(),
            guestName : $guestName.val(),
            leaveTimeString : $leaveTimeString.val(),
            liveTimeString : $liveTimeString.val(),
            maiAccount : maiAccount,
            payment : $payment.val()
        }
        $.each($(".room-type-operate"),function(key,val) {
            $selectedObj = $(this).find(".selectRoomType").find("option:checked")
            $roomNumber = $(this).find(".roomNumber")
            homeAmount = "dailyInfoses" + "[" + key + "]" + ".roomTypeNums"
            roomTypeId = "dailyInfoses"+"["+key+"]"+".roomTypeId"
            roomTypeName = "dailyInfoses"+"["+key+"]"+".roomTypeName"
            json[homeAmount] = $roomNumber.val()
            json[roomTypeId] = $selectedObj.attr("data-roomtypeid")
            json[roomTypeName] = $selectedObj.val()
        })

        //请求保存接口
        var url = $saveManualOrder.attr("data-url");
        $.ajax({
            type:'POST',
            data: json,
            url:url,
            dataType:'html',
            success:function(rs){
                if (rs.status) {
                    alert("手动下单成功！");
                } else {
                    alert("手动下单失败！");
                }
            },
            error: function() {
                alert("手动下单失败，请重试！")
            }
        })

    })
    function formatDate(date) {
        var year = date.getFullYear();
        var mouth = date.getMonth()+1;
        var day = date.getDate();
        if(parseInt(mouth)<10) {
            mouth="0"+mouth;
        }
        if(parseInt(day+1)<10) {
            day="0"+day;
        }
        var Date = year+"-"+mouth+'-'+day;
        return Date;
    }
    function getRoomType() {
        $selectRoomType.html("<option>请选择房型</option>")
        var url = $("#roomTypeUrl").attr("data-url");
        var json = {
            bangInnId : $('#kz_item-r').val(),
            leaveTimeString : $leaveTimeString.val(),
            liveTimeString : $liveTimeString.val(),
            maiAccount : maiAccount
        }
        $.ajax({
            type:'GET',
            data: json,
            url:url,
            dataType:'html',
            success:function(rs){
                rs = $.parseJSON(rs)
                if(rs.data && rs.data.length==0){
                    alert("获取房型数据为空，请重新选择时间段！")
                    return
                }
                if( rs.data && rs.data.length ) {
                    roomTypedata = rs.data;
                    $.each(roomTypedata,function() {
                        $selectRoomType.append("<option data-roomTypeId="+this.roomTypeId+">"+this.roomTypeName+"</option>")
                    })
                }
            },
            error: function() {
                alert("获取房型数据失败，请重试！")
            }
        })
    }
    function ValidTelNum() {
        var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
        var isMob = /^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
        var value = $guestMobile.val().trim();
        if (!(isMob.test(value) || isPhone.test(value))) {
            alert("请输入正确的手机号码！")
            return;
        }
    }
    // 验证手机号
    $guestMobile.blur(function () {
        ValidTelNum();
    })
})