var vm = avalon.define({
    $id : "newsCenter",
    displayDiv : "none",
    UnreadNewsAcount : 0,
    init : function(){
        console.log(11);
    },
    showDialogFun : function() {
        vm.displayDiv = "block"
    },
    packUp : function() {
        vm.displayDiv = "none"
    }
})
vm.init();