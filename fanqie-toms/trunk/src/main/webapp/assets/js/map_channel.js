
// 各渠道房费所占比例 饼状图
var channel_data = "",
    channelName = [],
    myChart_channel = echarts.init(document.getElementById('map_channel'));
var init_channel = function(){
    myChart_channel = echarts.init(document.getElementById('map_channel'));
    myChart_channel.showLoading({
        text: '正在努力的读取数据中...',    //loading
    });
                
    myChart_channel.hideLoading();
    option = {
        title : {
            text: '各渠道房费所占比例',
            subtext: '来源：fanqiele.com',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data: channelName
        },
        toolbox: {
            show : false
        },
        calculable : false,
        series : [
            {
                name:'房费所占比例',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data: channel_data.data
            }
        ]
    };
    // 为echarts对象加载数据 
    myChart_channel.setOption(option);    
}




function getData(postData){
    var orderD_url= $('.orderD-url').attr('data-url');
    $.ajax({
        type:'POST',
        data:postData,
        url:orderD_url+"?v"+new Date().getTime(),
        dataType:'json',
        success:function(data){
            channel_data = data;
            channelName = [];
            for(var attr in channel_data.data){
                channelName.push( channel_data.data[attr].name )
            }
            var order = data.orderSource;
            if(order){
                $("#orderNum").html(order.orderNum);
                $("#realLiveNum").html(order.liveNum);
                $("#emptyAndTotalRoom").html("总数"+order.totalRoomNum+"间夜;空置"+order.emptyRoomNum+"间夜");
                $("#incomeId").html(order.income);
            }else{
                $("#orderNum").html(0);
                $("#realLiveNum").html(0);
                $("#emptyAndTotalRoom").html("总数"+0+"间夜;空置"+0+"间夜");
                $("#incomeId").html(0);
            }
            init_channel();
            orderSource(data.list)
        }
    })
}
function orderSource(obj){
    $("#orderSourceId").empty();
    var source="";
    if(obj!=undefined){
        for (var i=0;i<obj.length;i++){
            var o = obj[i];
            source += "<tr> <td><span class='label label-default'>"+ o.fromName+"</span></td>"+
            "<td>订单："+ o.orderNum+"</td>"+
            "<td>实住间夜："+ o.liveNum+"</td>"+
            "<td>营业收入："+ o.income+"元</td>"+
            "</tr>"
        }
        $("#orderSourceId").html(source);
    }else{
        $("#orderSourceId").html("<div class=\"alert alert-danger center\">没有数据,请重新筛选</div>");
    }
}
$('#myButton').on('click', function(){
    var postData = $("#orderId").serialize();
    //opt(postData);
    getData( postData );
})

var timer = setInterval(function(){
    if($('#kz_item').html()){
        var postData = $("#orderId").serialize();
        //opt(postData);
        getData(postData);
        clearInterval(timer);
    }
},500);




