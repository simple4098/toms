var vm = avalon.define({
    $id : "otherPayManage",
    divDisplayIsopened : ['block','none'],
    isopenStatus : false,
    addOtherPayItem : {},
    eidtPayItem : {},
    init : function() {

    },
    divDisplayFun : function() {
       // vm.divDisplay = ['none','block']
    },
    divDisplayIsopenedFun : function() {
        if(!vm.isopenStatus) {
            vm.divDisplayIsopened = ['none','block']
        }else {
            vm.divDisplayIsopened = ['block','none']
        }
        vm.isopenStatus = !vm.isopenStatus;

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
        $("#addPayItems").modal("show");
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
        vm.saveValid(addOtherPayItem);
        var json = JSON.stringify(eidtPayItem);
        var url = $(this).attr("data-url");
        $.ajax({
            url:url+"?json="+json,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                console.log(data.data)
            },
            error: function () {
                layer.msg('系统错误');
            }
        })
        //todo
    },
    saveValid : function(otherPayItem) {
        if(!otherPayItem.consumerProjectName){
            alert("消费项目名称不能为空！")
            return;
        }
        var isValid = false;
        $.each(otherPayItem.otherList,function(){
            if(!this.price){
                alert("价格不能为空！")
                isValid = true;
                return false;
            }
            var re = /^[0-9]*[1-9][0-9]*$/;
            if(!re.test(this.price)){
                alert("价格请填写正整数1");
                isValid = true;
                return false;
            }
        })
        if(isValid){
            return;
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
              console.log(data.data)
                vm.eidtPayItem = data.data;
            },
            error: function () {
                layer.msg('系统错误');
            }
        })
        $("#editPayItems").modal('show');
    },
    saveEditPayItem : function() {
        var eidtPayItem = vm.eidtPayItem.$model;
        if(vm.eidtPayItem.otherList.length==0){
          alert("消费项目至少为一条！")
          return;
        }
        vm.saveValid(eidtPayItem);
        console.log(eidtPayItem);
        var json = JSON.stringify(eidtPayItem);
        var url = $(this).attr("data-url");
        $.ajax({
            url:url+"?json="+json,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                console.log(data.data)
            },
            error: function () {
                layer.msg('系统错误');
            }
        })
        //todo
    }
})
avalon.scan();