$(function(){
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		//alert($(this).children("td").eq(0).text());
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(0).text();
		selectNo=$(this).children("td").eq(2).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
})

var temptr = $();
var nRow = null;
var selectId=null;
window.onload=function(){//给表添加滚动条
	$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
	SetTableStyle();
};
function SetTableStyle() {
	var oTable = $('#editable-sample').dataTable();
    $("#editable-sample tbody tr").unbind("mouserover")
    $("#editable-sample tbody tr").bind("mousedown", (function (e) {
        if (e.which == 3) {
            var opertionn = {
                name: "",
                offsetX: 2,
                offsetY: 2,
                textLimit: 30,
                beforeShow: $.noop,
                afterShow: $.noop
            };
              
            var menudata = GetRightMenu(e.target.cellIndex, e.target.innerHTML);
            oTable.$('td.td_selected').removeClass('td_selected');
            if ($(e.target).hasClass('td_selected')) {
                $(e.target).removeClass('td_selected');
            } else {
                $(e.target).addClass('td_selected');
            }
            $.smartMenu.remove();
            $(this).smartMenu(menudata, opertionn);
        }
         
    }));
    $("#editable-sample tbody tr").mouseover(function (e) { 
        oTable.$('td.td_selected').removeClass('td_selected');
        if ($(e.target).hasClass('td_selected')) {
            $(e.target).removeClass('td_selected');
        } else {
            $(e.target).addClass('td_selected');
        }
        if ($(this).hasClass('row_selected')) {
            $(this).removeClass('row_selected');
        }else {
            oTable.$('tr.row_selected').removeClass('row_selected');
            $(this).addClass('row_selected');
        }
    });
}