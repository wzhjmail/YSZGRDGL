
document.write('<script src="../js/editable-paging.js"></script>'); 
var EditableTable = function () {

'use strict';

    return {
        init: function () {
           /* var oTable = $('#editable-sample').dataTable({
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"]
                ],
                "iDisplayLength": 5,
                "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ 条记录每页",
                    "oPaginate": {
                        "sPrevious": "上一页",
                        "sNext": "下一页"
                    }
                },
                "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': [0]
                }]
            });*/
        	var oTable = getTable();
            jQuery('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium");
            jQuery('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall");
            var nEditing = null;
             
            $('#editable-sample_new').click(function (e) {
                e.preventDefault();
                var aiNew = oTable.fnAddData(['', '', '', '', '<a class="edit"><span class="label label-success">修改</span></a>', 
                                              '<a class="delete" href="javascript:;"><span class="label label-danger">删除</span></a>']);
                var nRow = oTable.fnGetNodes(aiNew[0]);
            });
            
            $('#editable-sample a.delete').live('click', function (e) {
                e.preventDefault();
                var nRow = $(this).parents('tr')[0];
                var id=nRow.childNodes[0].innerHTML;
                if(id!=null&&id!=""){//删除数据库中的值
                	wzj.confirm('温馨提醒', '您确定删除该行吗?', function() {
	                     $.post(
	             				"deleteById.action",
	             				"id="+id,
	             				function(data){
	             					if(data>0){//如果删除成功为1，失败为0
	             					   oTable.fnDeleteRow(nRow);
	             					}
	             			});
	        		});
               }else{//添加时取消，删除页面的值
            	   oTable.fnDeleteRow(nRow);
               }
            });//delete按钮
        }
    };
}();