$(function(){
    var CONST = {
        TIME : 60*1000,//轮循时间
        ROWS : 1
    }
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    var queryNotReadCountUrl = $("#queryNotReadCountUrl").attr("data-url"),
        queryChangeMessageUrl = $("#queryChangeMessageUrl").attr("data-url")
    queryNotReadCount()
    function queryNotReadCount() {
        $.ajax({
            type:'GET',
            url:queryNotReadCountUrl,
            dataType:'html',
            success:function(rs){
                rs = $.parseJSON(rs);
                if(rs && rs.count && rs.status==200) {
                    $("#newsAccount").html(rs.count)
                }
            },
            error: function() {
                alert("查询所有的未读改价消息数量失败！")
            }
        })
    }
    function currentPageTab(i) {
        var data = {
            page : i,
            rows : CONST.ROWS
        }
        newsCenterFun(data)
    }
    $(".pagination").on("click","li",function() {
        var index = $(this).index()
        currentPageTab(index)
        $(".pagination li").find("a").removeClass('currentacitve')
        $(this).find("a").addClass('currentacitve');
    })
    function newsCenterFun(data) {
        var url = $("#newsCenterUrl").attr("data-url")
        $("#newsList").html("")
        $.ajax({
            type:'GET',
            data: data,
            url:url,
            dataType:'html',
            success:function(rs){
                rs = $.parseJSON(rs);
                if(rs && rs.data && rs.pagination && rs.data.length) {
                    var newsData =  rs;
                    $.each(newsData.data,function(){
                        if(!this.status){
                            $("#newsList").append("<li><h4><i class='no-read'></i>"+this.innName+"改价提醒<span class='fr'>"+this.createDate+"</span></h4>"+"<p>"+this.context+"</p></li>")
                        }else{
                            $("#newsList").append("<li><h4>"+this.innName+"改价提醒<span class='fr'>"+this.createDate+"</span></h4>"+"<p>"+this.context+"</p></li>")
                        }
                    })
                    var pages = Math.ceil(newsData.pagination.pageCount/newsData.pagination.rows)
                    $(".pagination").html("<li id='Previous'><a>&laquo;</a></li><li id='Next'><a>&raquo;</a></li>")
                    for(var i=0;i<pages;i++) {
                        if(i==0) {
                            $("#Next").before("<li><a  class='currentacitve' onclick=currentPageTab("+(i+1)+")>"+(i+1)+"</a>")
                        } else {
                            $("#Next").before("<li><a onclick=currentPageTab("+(i+1)+")>"+(i+1)+"</a>")
                        }

                    }
                    $("#newsCenter").show();
                }
            },
            error: function() {
                alert("获取消息列表失败，请重试！")
            }
        })
    }
    setInterval(function(){
            data = {
                from : new Date().Format("yyyy-MM-dd hh:mm:ss"),
                to : new Date((new Date().setMinutes(new Date().getMinutes() -1, new Date().getSeconds(), 0))).Format("yyyy-MM-dd hh:mm:ss")
            }
            $.ajax({
                type:'GET',
                data: data,
                url:queryChangeMessageUrl,
                dataType:'html',
                success:function(rs){
                    rs = $.parseJSON(rs);
                    if(rs && rs.count && rs.status==200) {
                        if(!rs.count) {
                            return
                        }
                        $(".click-read-new-message").show();
                        var MP = document.getElementById("MP");
                        MP.play();
                        queryNotReadCount()
                    }
                },
                error: function() {
                    alert("查询某一时间段的未读改价消息数量失败！")
                }
            })
    },5000)
    $(".click-read-new-message a").on("click",function() {
        var data = {
            page : 1,
            rows : CONST.ROWS
        }
        newsCenterFun(data)
        $(".click-read-new-message").hide();
    })
    $("#showNewsList").on("click",function(){
        $("#newsList").html("")
        $(".click-read-new-message").hide();
        var data = {
            page : 1,
            rows : CONST.ROWS
        }
        newsCenterFun(data)
    })
    $("#packUp").on("click",function(){
        $("#newsCenter").hide();
        $.ajax({
            type:'GET',
            url:queryNotReadCountUrl,
            dataType:'html',
            success:function(rs){
                rs = $.parseJSON(rs);
                if(rs && rs.count && rs.status==200) {
                    $("#newsAccount").html(rs.count)
                }
            },
            error: function() {
                alert("查询所有的未读改价消息数量失败！")
            }
        })
    })
})