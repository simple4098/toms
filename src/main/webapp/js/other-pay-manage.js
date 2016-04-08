var vm = avalon.define({
    $id : "otherPayManage",
    divDisplayIsopened : ['block','none'],
    addOtherPayItem : {},
    eidtPayItem : {},
    init : function() {
        var status = $("#onoffButton").attr("data-status");
        if(status=="true") {
            vm.divDisplayIsopened = ['none','block']
        }else {
            vm.divDisplayIsopened = ['block','none']

        }
    },
    divDisplayFun : function() {
       // vm.divDisplay = ['none','block']
    },
    divDisplayIsopenedFun : function() {
        var $onOffButton = $("#onoffButton");
        var url = $onOffButton.attr("data-url");
        var status = $onOffButton.attr("data-status");
        if(status=="true") {
            status = "false";
        }else {
            status = "true";
        }
        $.ajax({
            url:url+"?status="+status,
            type:'get',
            dataType:'json',
            success:function(data){
                if(data.status){
                    $onOffButton.attr("data-status",status);
                    if(status=="true"){
                        vm.divDisplayIsopened = ['none','block']
                        $onOffButton.attr("class",'on_button');
                    }else{
                        vm.divDisplayIsopened = ['block','none']
                        $onOffButton.attr("class",'off_button');

                    }
                }
            },error:function(data){

            }
        })
    },
    addOtherPayItemFun : function() {
        vm.addOtherPayItem = {};
        vm.isEmpty = [false,false];
        vm.addOtherPayItem = {
            "consumerProjectName":"",
            "consumerFunId": "",
            "parentId" : "",
            "otherList" : [
                {
                    "consumerProjectName": "",
                    "priceName": "",
                    "price": "",
                    "status": false
                }]
        }
        if($('.opened-con-ul').length>=5){
            layer.msg("最多只能新增5条消费项目");
        }else{
            $("#addPayItems").modal("show");
        }
    },
    addPriceType : function(type) {

        var json = {
            "consumerProjectName": "",
            "priceName": "",
            "price": "",
            "status": false
        };
        if(type=='add') {
            if(vm.addOtherPayItem.otherList.length>4) {
                return;
            }
            vm.addOtherPayItem.otherList.push(json)
        }else if(type == 'edit'){
            if(vm.eidtPayItem.otherList.length>4) {
                return;
            }
            vm.eidtPayItem.otherList.push(json)
        }

    },
    saveOtherPayItem : function() {
        var addOtherPayItem = vm.addOtherPayItem.$model;
        if(!vm.saveValid(addOtherPayItem)){
            return;
        }
        var json = JSON.stringify(addOtherPayItem);
        var url = $(this).attr("data-url");
        $.ajax({
            url:url+"?json="+json,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                if(data.status==200){
                    window.location.href='/personality/info/otherConsumer';
                }else{
                    layer.msg(data.message);
                }
            },
            error: function () {
                layer.msg('系统错误');
            }
        })
    },
    saveValid : function(otherPayItem) {
        if(!otherPayItem.consumerProjectName || otherPayItem.consumerProjectName.length>5){
            layer.alert("消费项目名称不能为空,并且长度最大为5个字符!")
            return false;
        }
        var isValid = false;
        $.each(otherPayItem.otherList,function(){
            if(!this.priceName || this.priceName.length>5){
                layer.alert("价格名称不能为空,并且不能超过5个字符!")
                isValid = true;
                return false;
            }
            if(!this.price){
                layer.alert("价格不能为空!")
                isValid = true;
                return false;
            }
            var re = /^\d{1,4}$/;
            if(!re.test(this.price)){
                layer.alert("价格请填写不大于4位的正整数!");
                isValid = true;
                return false;
            }
        })
        if(isValid){
            return false;
        }
        if(!isValid){
            return true;
        }
        $('#addPayItems').modal('hide');
        $('#editPayItems').modal('hide');
    },
    editPayItemFun : function() {
        var url = $(this).attr("data-url");
        $.ajax({
            url: url,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                vm.eidtPayItem = data.data;
            },
            error: function () {
                layer.msg('系统错误');
            }
        })
        $("#editPayItems").modal('show');
    },
    saveEditPayItem : function() {
        var editPayItem = vm.eidtPayItem.$model;
        if(vm.eidtPayItem.otherList.length==0){
          alert("消费项目至少为一条！")
          return;
        }
        if(!vm.saveValid(editPayItem)){
            return;
        }
        var json = JSON.stringify(editPayItem);
        var url = $(this).attr("data-url");
        $.ajax({
            url:url+"?json="+json,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                if(data.status){
                    window.location.href='/personality/info/otherConsumer';
                }else{
                    layer.msg(data.message);
                }
            },
            error: function () {
                layer.msg('系统错误');
            }
        })
    }
})
vm.init()