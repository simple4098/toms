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

            showRoomData(data);

        }
    })
}

$('#myButton').on('click', function(){
    var startDate = $('#from_datepicker').val(),
        endDate = $('#to_datepicker').val(),
        tagId = $('#kz-tags').val(),
        innId = $('#kz_item').val(),
        postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'innId': innId};
        //console.log(postData)
	
    getRoomType( postData );
})


function showRoomData(roomData){
	var typeNum = roomData.list.length; // 房型数量
	var daysNum = roomData.list[0].roomDetail.length;
	var tableHead = '';
	for(var i=0; i<=daysNum; i++){
		if(!i){
			tableHead += "<td colspan='2'></td>";
		}
		else{
			tableHead +='<td>'+ roomData.list[0].roomDetail[i-1].roomDate.slice(5) +'</td>';
		}
	}
	// console.log( roomData.list[0].roomDetail[0].roomDate )
	$('<tr>').appendTo($('#table'));
	$('#table tr').eq(0).html( tableHead );
}
