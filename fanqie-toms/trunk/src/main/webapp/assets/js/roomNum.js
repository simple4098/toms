/**
 * 房态房量
 */
function getRoomType(postData){
    $.ajax({
        type:'POST',
        data:postData,
        url:'../roomType.json',
        dataType:'json',
        success:function(data){

            // showRoomData(data);

        }
    })
}
$('#from_datepicker').change(function(){
    var date = $(this).val();
    $('#to_datepicker').val( TC.plusDate(date, '30', 'd', 'yyyy-MM-dd') );
    
    var postDate = {'startDay': $('#from_datepicker').val(), 'endDay': $('#to_datepicker').val()}
    console.log(postDate)
    $.ajax({
        type:'POST',
        data: postDate,
        url:'../roomType.json',
        dataType:'json',
        success:function(data){

            // todo

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



