var _data = {"date":["05-1","05-2","05-3","05-4","05-5","05-6","05-7","05-8","05-9","05-10","05-11","05-12","05-13","05-13","05-14","05-15","05-15","05-16","05-17","05-17","05-18","05-18","05-19","05-19","05-20","05-20","05-21","05-21","05-22","05-23","05-24","05-25","05-25","05-25","05-26","05-26","05-27","05-28","05-29","05-30","05-31"],"visit":[0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,1,0,0,3,0,3,0,8,0,20,0,11,0,0,0,0,10,10,0,3,0,0,0,0,0,0]}

// 营业收入
var init_yingYeShouRu = function(){
    var myChart_yingYeShouRu = echarts.init(document.getElementById('yingYeShouRu'));
    myChart_yingYeShouRu.showLoading({
        text: '正在努力的读取数据中...',    //loading
    });
                
    myChart_yingYeShouRu.hideLoading();
    option = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['营业收入']
        },
        toolbox: {
            show : false,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : false,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : _data.date
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'营业收入',
                type:'line',
                stack: '总量',
                data:_data.visit
            }
        ]
    };
    // 为echarts对象加载数据 
    myChart_yingYeShouRu.setOption(option);    
}

// 间夜数
var init_jianYeShu = function(){
    var myChart_jianYeShu = echarts.init(document.getElementById('jianYeShu'));
    myChart_jianYeShu.showLoading({
        text: '正在努力的读取数据中...',    //loading
    });
                
    myChart_jianYeShu.hideLoading();
    option = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['间夜总数','实住间夜数','空置间夜数']
        },
        toolbox: {
            show : false,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : false,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : _data.date
            }
        ],
        yAxis : [
            {
                type : 'value'
                /*min : '-1'*/
            }
        ],
        series : [
            {
                name:'间夜总数',
                type:'line',
                stack: '总量',
                data:_data.visit
            },
            {
                name:'实住间夜数',
                type:'line',
                stack: '总量',
                data:_data.visit
            },
            {
                name:'空置间夜数',
                type:'line',
                stack: '总量',
                data:_data.visit
            }
        ]
    };
    // 为echarts对象加载数据 
    myChart_jianYeShu.setOption(option);    
}

// 入住率
var init_ruZhuLv = function(){
    var myChart_ruZhuLv = echarts.init(document.getElementById('ruZhuLv'));
    myChart_ruZhuLv.showLoading({
        text: '正在努力的读取数据中...',    //loading
    });
                
    myChart_ruZhuLv.hideLoading();
    option = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['入住率']
        },
        toolbox: {
            show : false,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : false,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : _data.date
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'入住率',
                type:'line',
                stack: '总量',
                data:_data.visit
            }
        ]
    };
    // 为echarts对象加载数据 
    myChart_ruZhuLv.setOption(option);    
}

// 间夜均价
var init_jianYeJunJia = function(){
    var myChart_jianYeJunJia = echarts.init(document.getElementById('jianYeJunJia'));
    myChart_jianYeJunJia.showLoading({
        text: '正在努力的读取数据中...',    //loading
    });
                
    myChart_jianYeJunJia.hideLoading();
    option = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['间夜均价']
        },
        toolbox: {
            show : false,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : false,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : _data.date
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'间夜均价',
                type:'line',
                stack: '总量',
                data:_data.visit
            }
        ]
    };
    // 为echarts对象加载数据 
    myChart_jianYeJunJia.setOption(option);    
}

// 营业收入
init_yingYeShouRu();
// 间夜数
$('a[href="#tab_jianYeShu"]').on('shown.bs.tab', function (e) { 
init_jianYeShu();
})
// 入住率
$('a[href="#tab_ruZhuLv"]').on('shown.bs.tab', function (e) { 
init_ruZhuLv();
})
// 间夜均价
$('a[href="#tab_jianYeJunJia"]').on('shown.bs.tab', function (e) { 
init_jianYeJunJia();
})

/*function getData(_month){
    $.ajax({
        type:'GET',
        data:{key:_month},
        url:visit,
        dataType:'text',
        success:function(data){
            // console.log(data);
            //var _data = $.parseJSON(data);
            var _data = {"date":["05-1","05-2","05-3","05-4","05-5","05-6","05-7","05-8","05-9","05-10","05-11","05-12","05-13","05-13","05-14","05-15","05-15","05-16","05-17","05-17","05-18","05-18","05-19","05-19","05-20","05-20","05-21","05-21","05-22","05-23","05-24","05-25","05-25","05-25","05-26","05-26","05-27","05-28","05-29","05-30","05-31"],"visit":[0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,1,0,0,3,0,3,0,8,0,20,0,11,0,0,0,0,10,10,0,3,0,0,0,0,0,0]}
            
        }
    })
}*/





