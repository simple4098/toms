function obt(tagId,innId,url){
	/*var tagId ='${paramDto.tagId}';
	var innId ='${paramDto.innId}';
	var url= $('.data-url').attr('data-url');*/
	$.ajax({
		type:'GET',
		url:url+"?v"+new Date().getTime(),
		dataType:'json',
		success:function(json){
			if(json.data.length==0){
				return false;
			}
			var aLabel = ""; //存放客栈标签
			var aList = "";  //存放客栈列表
			// 遍历获取客栈标签
			for(var attr in json.data){
				var k ='';
				if(tagId==json.data[attr].innLabelId){
					k ='selected';
				}
				aLabel += "<option "+k+" value='"+json.data[attr].innLabelId+"'>"+json.data[attr].innLabelName+"</option>";
			}
			// 遍历获取客栈列表
			function getInnName(num){
				aList = "<option value>--请选择客栈--</option>";
				for(var innList in json.data[num].bangInnList){
					var k ='';
					if(innId==json.data[num].bangInnList[innList].innId){
						k ='selected';
					}
					aList += "<option "+k+" value='"+json.data[num].bangInnList[innList].innId+"'>"+json.data[num].bangInnList[innList].innName+"</option>"
				};
			}
			// 写入DOM
			$('#kz-tags-ac').html(aLabel);
			// 默认加载第一个列表
			var num1 = $('#kz-tags-ac').children(':selected').index();
			getInnName(num1);
			$('#kz_item-ac').html(aList);
			// 联动刷新客栈列表
			$('#kz-tags-ac').change(function(){
				var num = $(this).children(':selected').index();
				getInnName(num);
				$('#kz_item-ac').html(aList);
			})

		}
	});
}