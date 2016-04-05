$(function(){
    $("#showNewsList").on("click",function(){
        $("#newsList").html("")
        var newsData = {
            data : [
                {
                    createDate : "201502-03",
                    innName : "客栈1",
                    message : "客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1",
                    status : 0
                },
                {
                    createDate : "201502-04",
                    innName : "客栈2",
                    message : "客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1",
                    status : 0
                },
                {
                    createDate : "201502-05",
                    innName : "客栈3",
                    message : "客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1",
                    status : 0
                },
                {
                    createDate : "201502-06",
                    innName : "客栈4",
                    message : "客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1",
                    status : 1
                },
                {
                    createDate : "201502-07",
                    innName : "客栈5",
                    message : "客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1客栈1",
                    status : 1
                }
            ],
            pagination : {
                page : 1,
                pageCount : 1,
                rows : 10,
                rowsCount : 5
            }
        }
        $.each(newsData.data,function(){
            if(this.status==0){
                $("#newsList").append("<li><h4><i class='no-read'></i>"+this.innName+"改价提醒<span class='fr'>"+this.createDate+"</span></h4>"+"<p>"+this.message+"</p></li>")
            }else{
                $("#newsList").append("<li><h4>"+this.innName+"改价提醒<span class='fr'>"+this.createDate+"</span></h4>"+"<p>"+this.message+"</p></li>")
            }
        })
        $("#newsCenter").show();
    })
    $("#packUp").on("click",function(){
        $("#newsCenter").hide();
    })
})