//分页方法
function page(page) {
    $("#pageId").attr("value", page);
    $("#form-page").submit();
}

//搜索房仓酒店
$(".search-btn").on("click",function(){
    var _this = $(this);
    var page = $(this).attr('data-value');
    if (page <= 1 || page == null) {
        page = 1;
    }
    var data_url = _this.attr("data-url");
    var searchValue = $("#search-id").val();
    $.ajax({
        type:'POST',
        url:data_url,
        dataType:'html',
        data: {'innName': searchValue, 'page': page},
        success:function(data){
            $("#contentId").html(data);
        },error:function(data){
            alert(data);
        }
    })
})
//上一页
$(".search-btn-up").on("click", function () {
    var _this = $(this);
    var maxPage = parseInt($('.max-page').val());
    var pageNow = parseInt($('.conment-page').val());
    if (pageNow <= 1 || pageNow == null) {
        pageNow = 1;
    }
    pageNow -= 1;
    var data_url = _this.attr("data-url");
    var searchValue = $("#search-id").val();
    $.ajax({
        type: 'POST',
        url: data_url,
        dataType: 'html',
        data: {'innName': searchValue, 'page': pageNow},
        success: function (data) {
            //$(".conment-page").val(data.page);
            $("#contentId").html(data);
        }, error: function (data) {

        }
    });
})
//下一页
$(".search-btn-down").on("click", function () {
    var _this = $(this);
    var maxPage = parseInt($('.max-page').val());
    var pageNow = parseInt($('.conment-page').val());
    if (pageNow <= 1 || pageNow == null) {
        pageNow = 1;
    }
    pageNow += 1;
    if (pageNow >= maxPage) {
        pageNow = maxPage;
    }
    var data_url = _this.attr("data-url");
    var searchValue = $("#search-id").val();
    $.ajax({
        type: 'POST',
        url: data_url,
        dataType: 'html',
        data: {'innName': searchValue, 'page': pageNow},
        success: function (data) {
            //$(".conment-page").val(data.page);
            $("#contentId").html(data);
        }, error: function (data) {

        }
    });
})
//绑定房仓酒店
$("#btn-primary-id").on("click",function(){
    var url = $(this).attr("data-url");
    var fcHotelId = $("input[name='fcHotelId']:checked").val();
    var innId = $("#innId").val();
    if(fcHotelId==undefined || fcHotelId.length==0){
        layer.msg("请选择房仓酒店id");
        return false;
    }
    $.ajax({
        type:'post',
        data:{'innId':innId,'fcHotelId':fcHotelId},
        dataType:'json',
        url:url,
        success:function(data){
            if(data.status!="200"){
                layer.msg(data.message);
            }else{
                layer.msg("绑定成功");
                window.location.href=window.location.href;
            }
        },error:function(data){
            layer.msg(data.message);
        }
    })
    console.log(fcHotelId+"-"+innId);
})
//重新绑定
$(".btn-primary-cx").on("click",function(){
    var _this = $(this);
    if(_this.hasClass("push")){
        var url = $(this).attr("data-url");
        var fcHotelId = $("input[name='fcHotelId']:checked").val();
        var innId = $("#innId").val();
        var oldFcHotelId = $("#matchSuccessId").find("input[type='radio']").val()
        if(fcHotelId==undefined || fcHotelId.length==0){
            layer.msg("请选择房仓酒店id");
            return false;
        }
        if(fcHotelId == oldFcHotelId){
            layer.msg("此客栈已经匹配过，不用重新匹配!");
            return false;
        }
        $.ajax({
            type:'post',
            data:{'innId':innId,'fcHotelId':fcHotelId},
            dataType:'json',
            url:url,
            success:function(data){
                if(data.status!="200"){
                    layer.msg(data.message);
                }else{
                    layer.msg("绑定成功");
                    window.location.href=window.location.href;
                }
            },error:function(data){
                layer.msg(data.message);
            }
        })
        console.log(fcHotelId+"-"+innId);
    }

})
//重新匹配
$("#cxInMatchId").on("click",function(){
    var _this = $(this);
    $("#matchSuccessId").css("display","none");
    $("#not-match-id").css("display","block");
    _this.addClass("push");
    _this.text("提交匹配");
})
//保存价格计划
$("#userPlusBtn").on("click",function(){
    var currency = $("#currencyId").val();
    var payMethod = $("#payMethodId").val();
    var bedType = $("#bedTypeId").val();
    var otaInfoId = $("#otaInfoId").val();
    var url = $("#saveRatePlanUrlId").val();
    $.ajax({
        type:'post',
        data:{'currency':currency,'payMethod':payMethod,'bedType':bedType,'otaInfoId':otaInfoId},
        dataType:'json',
        url:url,
        success:function(data){
            window.location.href = window.location.href;
        },error:function(data){
            alert(data);
        }
    })
    console.log(currency+"-"+payMethod+" - "+bedType);
})

$('.inn-rooms-box').sortable({
    connectWith: ".connectedSortable",
    over: function(event, ui){
        $(this).css('borderColor','#F4D611')
    },
    out: function(){
        $(this).css('borderColor','#ccc')
    },
    stop: function(event, ui){
        ui.item.siblings() && ui.item.siblings().appendTo($('#sortable'))
        ui.item.parent().css('borderWidth',0)
    },
    receive: function(evnet, ui){
        ui.sender.css('borderWidth',1)
    }
}).disableSelection()

$('#sortable').sortable({
    connectWith: ".connectedSortable",
    stop: function(event, ui){
        ui.item.siblings() && ui.item.siblings().appendTo($('#sortable'))
        ui.item.parent().css('borderWidth',0)
    },
    receive: function(evnet, ui){
        ui.sender.css('borderWidth',1)
    }
}).disableSelection()


$('#roomTypeBtn').click(function(){
    var i = layer.load(0);
    var _this = $(this);
    var url = _this.attr("data-url");
    var fcHotelId = $("input[name='fcHotelId']:checked").val();
    var innId = $("#innId").val();
    if(fcHotelId.length==0 || innId.length==0){
        layer.msg("客栈id ,房仓酒店id为空");
        return false;
    }
    var $innRooms = $('#innRoomtype .inn-rooms'),
        $sortable = $('#otaRoomType .inn-rooms-box'),
        arr = [],
        innCode = [],
        otaCode = []

    $innRooms.each(function(i){
        var obj = $(this).find('input');
        var innId = obj.attr("data-roomTypeId");
        var roomTypeName = obj.attr("data-roomTypeName");
        var roomTypeArea = obj.attr("data-area");
        var bedNum = obj.attr("bedNum");
        var bedLen = obj.attr("bedLen");
        var bedWid = obj.attr("bedWid");
        arr[i] = {}
        arr[i].roomTypeId = innId;
        arr[i].roomArea = roomTypeArea;
        arr[i].roomTypeName = roomTypeName;
        if(bedNum.length!=0){
            arr[i].bedNum = bedNum;
        }
        if(bedLen.length!=0){
            arr[i].bedLen = bedLen;
        }
        if(bedLen.length!=0){
            arr[i].bedWid = bedWid;
        }
        innCode.push($(this).html())
    })
    $sortable.each(function(i){
        var obj = $(this).find('input');
        var id =obj.attr("data-roomTypeId");
        var roomTypeName =obj.attr("data-roomTypeName");
        if(id){
            arr[i].fcRoomTypeId = id
            arr[i].fcRoomTypeName = roomTypeName
            otaCode.push($(this).find('.inn-rooms').html())
        }else{
            arr[i].fcRoomTypeId = ''
            arr[i].fcRoomTypeName = ''
            otaCode.push('')
        }
    })
    var json = JSON.stringify(arr);
    $.ajax({
        data:{"json":json,"innId":innId,"fcHotelId":fcHotelId},
        dataType:"json",
        type:'post',
        url:url,
        success:function(data){
            if(data.status==400){
                layer.msg("匹配失败:"+data.message);
                layer.close(i);
            }else{
                window.location.href=window.location.href;
                layer.close(i);
            }
        },error:function(data){
            layer.msg("匹配失败:"+data.message);
            layer.close(i);
        }
    })
    console.log(json)

    /*var htmlCode = ''
    for(var i=0; i<arr.length; i++){
        htmlCode += '<tr><td>'+(i+1)+'</td><td>'+innCode[i]+'</td><td>'+otaCode[i]+'</td><td><span class="price-plan"></span><button class="btn btn-xs btn-primary edit-btn" data-toggle="modal" data-target="#editPopups">编辑</button></td><td><button class="btn btn-xs btn-success" data-toggle="modal" data-target="#roomTypeUp">上架</button></td></tr>'
    }
    $('#roomTypeData').html( htmlCode )*/

    // 编辑按钮
    $('.edit-btn').on('click', function(){
        $(this).parent().addClass('active-row')
    })

    // 保存价格计划
    $('#editSave').click(function(){
        var price = $(this).parents('.modal-content').find('option:selected').text()
        $('.active-row').removeClass('active-row').children('.price-plan').text(price)
    })
})
