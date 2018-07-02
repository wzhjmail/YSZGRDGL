/**
 * js 工具类  通用
 * @author xyc
 * @param percent
 * @returns {Number}jQuery
 */
(function($) {
		//禁用鼠标右键
	    document.oncontextmenu=function(e){return false;};
})();
//适应高度
function fixHeight(percent)     
{     
    return (document.body.clientHeight) * percent ;      
}  
function fixWidth(percent)
{
//适应宽度
    return ($(".mdiv").width() - 5-20)*0.98*percent ;      
}  


//显示loading框
function ajaxLoading(info){
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html(info).appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
}
//隐藏loading框
function ajaxLoadEnd(){
     $(".datagrid-mask").remove();
     $(".datagrid-mask-msg").remove();            
}
