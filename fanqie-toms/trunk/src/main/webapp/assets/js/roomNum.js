/**
 * 房态房量
 */
function getRoomType(postData){
    var url = $("#dataUrlId").attr("data-url")+"?v="+new Date().getTime();
    $.ajax({
        type:'POST',
        data:postData,
        url:url,
        dataType:'html',
        success:function(data){
            $("#roomTypeContainerId").empty();
            $("#roomTypeContainerId").html(data);
        }
    })
}
$('#from_datepicker').change(function(){
    var url = $("#dataUrlId").attr("data-url")+"?v="+new Date().getTime();
    var date = $(this).val();
    $('#to_datepicker').val( TC.plusDate(date, '30', 'd', 'yyyy-MM-dd') );
    
    var postDate = {'startDay': $('#from_datepicker').val(), 'endDay': $('#to_datepicker').val()}

    $.ajax({
        type:'POST',
        data: postDate,
        url:url,
        dataType:'html',
        success:function(data){
            $("#roomTypeContainerId").empty();
            $("#roomTypeContainerId").html(data);
        }
    })
})
//上一月
$('#prevM').click(function(){
    var date = $('#from_datepicker').val();
    $('#from_datepicker').val( TC.plusDate(date, '-1', 'M', 'yyyy-MM-dd') ).change();
})
// 下一月
$('#nextM').click(function(){
    var date = $('#from_datepicker').val();
    $('#from_datepicker').val( TC.plusDate(date, '1', 'M', 'yyyy-MM-dd') ).change();
})

$('#myButton').on('click', function(){
    var startDate = $('#from_datepicker').val(),
        endDate = $('#to_datepicker').val(),
        tagId = $('#kz-tags').val(),
        innId = $('#kz_item').val(),
        postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'innId': innId};
    getRoomType( postData );
})



