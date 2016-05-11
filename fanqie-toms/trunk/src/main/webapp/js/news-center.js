$(function(){
    var CONST = {
        TIME : 1000*60,//轮循时间
        ROWS : 10//分页每页显示条数
    }
    var currentPage = 1,
        Pages = 1;
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
//        if(queryNotReadCountUrl){
//            $.ajax({
//                type:'GET',
//                url:queryNotReadCountUrl,
//                dataType:'html',
//                success:function(rs){
//                    rs = $.parseJSON(rs);
//                    if(rs && rs.count && rs.status==200) {
//                        $("#newsAccount").html(rs.count)
//                    }
//                },
//                error: function() {
//                    alert("查询所有的未读改价消息数量失败！")
//                }
//            })
//        }

    }
    function currentPageTab(i) {
        var data = {
            page : i,
            rows : CONST.ROWS
        }
        newsCenterFun(data)
    }
    $("#pagination .pagination").on("click","li",function() {
        var index = $(this).index()
        if($("#pagination .pagination li").length==2) {
            return;
        }
        var len = $("#pagination .pagination li").length-1
        if(index == 0){
            if(currentPage<=1){
                return;
            }
            currentPageTab(currentPage-1)
        } else if (index == Pages+1){
            if(currentPage>=Pages) {
                return;
            }
            currentPageTab(currentPage+1)
        } else {
            currentPageTab(index)
        }
        setReaded()
    })
    function newsCenterFun(data) {
        var url = $("#newsCenterUrl").attr("data-url")
        currentPage = data.page;
        $("#newsList").html("")
        if(url){
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
                        var pages = Math.ceil(newsData.pagination.rowsCount/newsData.pagination.rows)
                        Pages = pages;
                        $("#pagination .pagination").html("<li id='Previous'><a>&laquo;</a></li><li id='Next'><a>&raquo;</a></li>")
                        for(var i=0;i<pages;i++) {
                            if(i==currentPage-1) {
                                $("#Next").before("<li class='active'><a>"+(i+1)+"</a></li>")
                            } else {
                                $("#Next").before("<li><a>"+(i+1)+"</a></li>")
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

    }
    if(queryChangeMessageUrl){
//        setInterval(function(){
//            data = {
//                from : new Date((new Date().setMinutes(new Date().getMinutes() -1, new Date().getSeconds(), 0))).Format("yyyy-MM-dd hh:mm:ss"),
//                to : new Date().Format("yyyy-MM-dd hh:mm:ss")
//            }
//            $.ajax({
//                type:'GET',
//                data: data,
//                url:queryChangeMessageUrl,
//                dataType:'html',
//                success:function(rs){
//                    rs = $.parseJSON(rs);
//                    if(rs && rs.count && rs.status==200) {
//                        if(!rs.count) {
//                            return
//                        }
//                        $(".click-read-new-message").show();
//                        var MP = document.getElementById("MP");
//                        MP.play();
//                        queryNotReadCount()
//                    }
//                },
//                error: function() {
//                    alert("查询某一时间段的未读改价消息数量失败！")
//                }
//            })
//        },CONST.TIME)
    }

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
        setReaded();
    })
    function setReaded() {
        var changeMessageStatusUrl = $("#changeMessageStatus").attr("data-url")
        $("#newsAccount").html(0)
        $.ajax({
            type:'GET',
            url:changeMessageStatusUrl,
            dataType:'html',
            success:function(rs){
                if(!rs.status==200) {
                    alert("将消息置为已读失败")
                }
            },
            error: function() {
                alert("将消息置为已读失败")
            }
        })
    }
})