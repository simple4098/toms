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
            vm.addOtherPayItem.otherList.push(json)
        }else if(type == 'edit'){
            vm.eidtPayItem.otherList.push(json)
        }

    },
    saveOtherPayItem : function() {
        var addOtherPayItem = vm.addOtherPayItem.$model;
        vm.saveValid(addOtherPayItem)
    },
    saveValid : function(otherPayItem) {
        if(!otherPayItem.consumerProjectName){
            alert("消费项目名称不能为空！")
            return;
        }
        var isValid = false;
        $.each(otherPayItem.otherList,function(){
            if(!this.consumerProjectName || !this.price){
                alert("新增价格类型不能为空！")
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
        vm.eidtPayItem = {
            "consumerProjectName":"门票",
            "consumerFunId": "",
            "parentId" : "",
            "otherList" : [
                {
                    "consumerProjectName": "全票",
                    "priceName": "",
                    "price": "100",
                    "status": false
                },
                {
                    "consumerProjectName": "半票",
                    "priceName": "",
                    "price": "50",
                    "status": true
                }
            ]
        }
        $("#editPayItems").modal('show');
    },
    saveEditPayItem : function() {
        var eidtPayItem = vm.eidtPayItem.$model;
        if(vm.eidtPayItem.otherList.length==0){
          alert("消费项目至少为一条！")
          return;
        }
        vm.saveValid(eidtPayItem);
    }
})
avalon.scan();