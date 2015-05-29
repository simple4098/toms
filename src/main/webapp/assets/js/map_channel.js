
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


var order_url= $('.order-url').attr('data-url');
var orderD_url= $('.orderD-url').attr('data-url');
var postData = $("#orderId").serialize();

function getData(postData){
    $.ajax({
        type:'POST',
        data:postData,
        url:orderD_url,
        dataType:'json',
        success:function(data){
            channel_data = data;
            channelName = [];
            for(var attr in channel_data.data){
                channelName.push( channel_data.data[attr].name )
            }
            init_channel();
            orderSource(data.list)
        }
    })
}
function orderSource(obj){
    $("#orderSourceId").empty();
    var source="";
    for (var i=0;i<obj.length;i++){
        var o = obj[i];
        source += "<tr> <td><span class='label label-default'>"+ o.fromName+"</span></td>"+
            "<td>订单："+ o.orderNum+"</td>"+
            "<td>实住间夜："+ o.liveNum+"</td>"+
            "<td>营业收入："+ o.income+"元</td>"+
            "</tr>"
    }
    $("#orderSourceId").html(source);
}
$('#myButton').on('click', function(){
    postData = $("#orderId").serialize();
    opt(postData);
    getData( postData );
})

var timer = setInterval(function(){
    if($('#kz_item').html()){
        opt(postData);
        getData(postData);
        clearInterval(timer);
    }
},500);
function opt(obj){
    $.ajax({
        type:'post',
        url:order_url,
        dataType:'json',
        data:obj,
        success:function(json) {
            var order = json.orderSource;
            $("#orderNum").html(order.orderNum);
            $("#realLiveNum").html(order.liveNum);
            $("#emptyAndTotalRoom").html("总数"+order.totalRoomNum+"间夜;空置"+order.emptyRoomNum+"间夜");
            $("#incomeId").html(order.income);
        }
    });
}



