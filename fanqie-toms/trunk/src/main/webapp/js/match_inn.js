

//搜索房仓酒店
$(".search-btn").on("click",function(){
    var _this = $(this);
    var data_url = _this.attr("data-url");
    var searchValue = $("#search-id").val();
    $.ajax({
        type:'POST',
        url:data_url,
        dataType:'html',
        data:{'innName':searchValue},
        success:function(data){
            $("#contentId").html(data);
        },error:function(data){
            alert(data);
        }
    })
})
//绑定房仓酒店
$(".btn-primary").on("click",function(){
    var url = $(this).attr("data-url");
     var fcHotelId = $("input[name='fcHotelId']:checked").val();
     var innId = $("#innId").val();
    if(fcHotelId==undefined){
        alert("请选择房仓酒店id");
        return false;
    }
    $.ajax({
        type:'post',
        data:{'innId':innId,'fcHotelId':fcHotelId},
        dataType:'json',
        url:url,
        success:function(data){
            if(data.status!="200"){
                alert(data.message);
            }else{
                alert("绑定成功!");
            }
        },error:function(data){
            alert(data);
        }
    })
    console.log(fcHotelId+"-"+innId);
})
//保存价格计划
$("#userPlusBtn").on("click",function(){
    var currency = $("#currencyId").val();
    var payMethod = $("#payMethodId").val();
    var bedType = $("#bedTypeId").val();
    var url = $("#saveRatePlanUrlId").val();
    $.ajax({
        type:'post',
        data:{'currency':currency,'payMethod':payMethod,'bedType':bedType},
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
    var $innRooms = $('#innRoomtype .inn-rooms'),
        $sortable = $('#otaRoomType .inn-rooms-box'),
        arr = [],
        innCode = [],
        otaCode = []

    $innRooms.each(function(i){
        var innId = $(this).find('input').val()
        arr[i] = {}
        arr[i].innId = innId
        innCode.push($(this).html())
    })
    $sortable.each(function(i){
        var id = $(this).find('input').val()
        if(id){
            arr[i].id = id
            otaCode.push($(this).find('.inn-rooms').html())
        }
        else{
            arr[i].id = ''
            otaCode.push('')
        }
    })
    console.log(arr)

    var htmlCode = ''
    for(var i=0; i<arr.length; i++){
        htmlCode += '<tr><td>'+(i+1)+'</td><td>'+innCode[i]+'</td><td>'+otaCode[i]+'</td><td><span class="price-plan"></span><button class="btn btn-xs btn-primary edit-btn" data-toggle="modal" data-target="#editPopups">编辑</button></td><td><button class="btn btn-xs btn-success" data-toggle="modal" data-target="#roomTypeUp">上架</button></td></tr>'
    }
    $('#roomTypeData').html( htmlCode )

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
