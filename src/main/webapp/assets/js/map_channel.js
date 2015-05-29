
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
    $.ajax({
        type:'POST',
        data:postData,
        url:'../json3.json',
        dataType:'json',
        success:function(data){
            channel_data = data;
            for(var attr in channel_data.data){
               channelName.push( channel_data.data[attr].name )
            }

            init_channel();
        }
    })
}
$('#myButton').on('click', function(){
    var startDate = $('#from_datepicker').val(),
        endDate = $('#to_datepicker').val(),
        tagId = $('#kz-tags').val(),
        innId = $('#kz_item').val(),
        postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'innId': innId};
    getData( postData );
})



