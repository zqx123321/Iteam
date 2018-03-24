$(document).ready(function(){
	  //取出有clear类的input域
            //(注: "clear once" 是两个class clear 和 once)
            $('.txtfield').each(function () {
                //使用data方法存储数据
                $(this).data("txt", $.trim($(this).val()));
            }).focus(function () {
                // 获得焦点时判断域内的值是否和默认值相同，如果相同则清空
                if ($.trim($(this).val()) === $(this).data("txt")) {
                    $(this).val("");
                }
            }).blur(function () {
                // 为有class clear的域添加blur时间来恢复默认值
                // 但如果class是once则忽略
                if ($.trim($(this).val()) === "" && !$(this).hasClass("once")) {
                    //Restore saved data
                    $(this).val($(this).data("txt"));
                }
            });

          
});
function adjust(obj){
	 var hight=document.documentElement.clientHeight;
     var mainHight =$(".logIn-main").height();
     var endhight = (hight-mainHight)/2 + "px";

      $(".logIn-cont").css("padding-top",endhight);
}
    window.onload=function(){  
  			window.onresize = adjust;  
  			adjust();  
		}

