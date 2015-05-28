<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Examples</title>
    <link rel="stylesheet" type="text/css" href="/assets/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/ace.min.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/jquery-ui-1.10.3.full.min.css">
    <!-- <link rel="stylesheet" type="text/css" href="../assets/css/datepicker.css" /> -->
    <link rel="stylesheet" type="text/css" href="/assets/css/pages.css">

</head>
<body>

<div class="container">
    <div class="clearfix select-area">
        <div class="pull-right">
            <select class="selectpicker">
                <option>快捷日期</option>
                <option>昨日</option>
                <option>本月</option>
                <option>近7天</option>
                <option>近30天</option>
            </select>
            <input readonly class="date-input" type="text" id="datepicker" placeholder="2015-05-26">&nbsp;至
            <input readonly class="date-input" type="text" placeholder="2015-05-27">
            <select class="selectpicker">
                <option>客栈标签</option>
                <option>丽江</option>
                <option>大理</option>
                <option>乌镇</option>
                <option>海南</option>
            </select>
            <select class="selectpicker">
                <option>请选择客栈</option>
                <option>哪里那里客栈</option>
                <option>走走停停客栈</option>
                <option>双廊海中月湾客栈</option>
                <option>十步海岸国际青年旅舍</option>
            </select>
            <button type="button" id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">
                搜索
                <i class="icon-search icon-on-right bigger-110"></i>
            </button>
        </div>
    </div>
    <div class="container color-green">
        <p>所选时间段内，共接待 <span>${totalNum}</span> 位客人，分布在 <span>${totalCityNum}</span> 个城市。</p>
    </div>
    <div class="container table-wrap">
        <table class="table">
            <thead>
            <tr>
                <th>城市</th>
                <th>用户数</th>
                <th></th>
                <th>占比</th>
            </tr>
            </thead>
            <tbody>
             <c:forEach var="c" items="${innCustomerList}">
                 <tr>
                     <td>${c.city}</td>
                     <td>${c.num}</td>
                     <td>
                         <div class="progress">
                             <div class="progress-bar progress-bar progress-bar-success" style="width:${c.percent*100}%;"></div>
                         </div>
                     </td>
                     <td>
                         <div class="easy-pie-chart percentage" data-percent="66" data-color="#87CEEB">
                             <span class="percent">${c.percent*100}</span>%
                         </div>
                     </td>
                 </tr>
             </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="container">
        <div>
            <ul class="pagination">

                <li class="disabled">${total}   ${row} ${page}
                    <a href="#">
                        <i class="icon-double-angle-left"></i>
                    </a>
                </li>

                <li class="active">
                    <a href="#">1</a>
                </li>

                <li>
                    <a href="#">2</a>
                </li>

                <li>
                    <a href="#">3</a>
                </li>

                <li>
                    <a href="#">4</a>
                </li>

                <li>
                    <a href="#">5</a>
                </li>

                <li>
                    <a href="#">
                        <i class="icon-double-angle-right"></i>
                    </a>
                </li>
            </ul>
        </div>

        <p></p>
    </div>
</div>

<script src="/assets/js/jquery-2.0.3.min.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="/assets/js/jquery.easy-pie-chart.min.js"></script>

<script type="text/javascript">
    $(function(){
        // loading
        $('#myButton').on('click', function () {
            var $btn = $(this).button('loading')
            setTimeout(function(){
                $btn.button('reset');
            },1000)
        });

        // datepicker
        $( "#datepicker" ).datepicker({
            showOtherMonths: true,
            selectOtherMonths: true,
            //isRTL:true,


            /*
             changeMonth: true,
             changeYear: true,

             showButtonPanel: true,
             beforeShow: function() {
             //change button colors
             var datepicker = $(this).datepicker( "widget" );
             setTimeout(function(){
             var buttons = datepicker.find('.ui-datepicker-buttonpane')
             .find('button');
             buttons.eq(0).addClass('btn btn-xs');
             buttons.eq(1).addClass('btn btn-xs btn-success');
             buttons.wrapInner('<span class="bigger-110" />');
             }, 0);
             }
             */
        });

        var oldie = /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase());
        $('.easy-pie-chart.percentage').each(function(){
            $(this).easyPieChart({
                barColor: $(this).data('color'),
                trackColor: '#EEEEEE',
                scaleColor: false,
                lineCap: 'butt',
                lineWidth: 8,
                animate: oldie ? false : 1000,
                size:75
            }).css('color', $(this).data('color'));
        });

    })





</script>
</body>
</html>