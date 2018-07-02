document.write('<script src="../js/editable-paging.js"></script>'); 
$.validationEngineLanguage.allRules.path = {  
	      "regex": /^[\w\\\/\.]+$/,
	      "alertText": "* 请输入正确的路径"  
};
$.validationEngineLanguage.allRules.name = {  
	      "regex": /^[\w\u4E00-\u9FA5\(\)]+$/,
	      "alertText": "* 请输入正确的权限名称"  
};  
var EditableTable = function () {
	
'use strict';
    return {
        init: function () {
        	var status="修改";//用来否区分是添加还是修改
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }
                oTable.fnDraw();
            }
           
            function editRow(oTable, nRow,flag) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                if(flag==0){
                	jqTds[0].innerHTML = '<input type="text" style="width:40px" class="form-control small validate[required,custom[number]]" value="' + aData[0] + '">';
                }
                jqTds[1].innerHTML = '<input type="text" style="width:75px" class="form-control small validate[required,custom[name]]" value="' + aData[1] + '">';
               
                jqTds[2].innerHTML = '<select id="type" style="height:30px;font-size:12px;background-color:#eee;width:80px" ><option id="menu">menu</option><option id="pms">permission</option></select>';
                if("menu"==aData[2]){
                	$("#menu").attr("selected", true);
                	jqTds[3].innerHTML = '<input type="text" style="width:235px" class="form-control small validate[custom[path]]" value="' + aData[3] + '">';
                }else{
                	$("#pms").attr("selected", true);
                	jqTds[3].innerHTML = '<input type="text" style="width:235px" class="form-control small validate[required,custom[path]]" value="' + aData[3] + '">';
                }
                //jqTds[4].innerHTML = '<input type="text" style="width:75px" class="form-control small" value="' + aData[4] + '">';
                jqTds[4].innerHTML = '<input type="text" style="width:40px" class="form-control small validate[required,custom[number]]" value="' + aData[4] + '">';
                //jqTds[6].innerHTML = '<input type="text" style="width:40px" class="form-control small" value="' + aData[6] + '">';
                jqTds[5].innerHTML = '<input type="text" style="width:40px" class="form-control small validate[required,custom[number]]" value="' + aData[5] + '">';              
                jqTds[6].innerHTML = '<select id="avlb" style="height:30px;background-color:#eee;width:45px"><option id="avl">1</option><option id="unavl">0</option></select>';
                if("1"==aData[7]){
                	$("#avl").attr("selected", true);
                }else{
                	$("#unavl").attr("selected", true);
                }
                jqTds[7].innerHTML = '<a class="edit" href=""><span class="label label-success">保存</span></a>';
                jqTds[8].innerHTML = '<a class="cancel" href=""><span class="label label-danger">取消</span></a>';
                $('#type').change(function(){
                	console.log($('#type').parent().parent())
                	console.log($('#type').parent().parent().find('td:nth-child(4)'))
                	if($('#type').val()=="menu"){
                		$('#type').parent().parent().find('td:nth-child(4) input').removeClass('validate[required,custom[path]]').addClass('validate[custom[path]]')
                	}else{
                		$('#type').parent().parent().find('td:nth-child(4) input').removeClass('validate[custom[path]]').addClass('validate[required,custom[path]]')
                	}
                })
            }

            function saveRow(oTable, nRow) {
            	var success = false
            	if($('#info').validationEngine('validate')){
            		success = true
	                var jqInputs = $('input', nRow);
            		var jqTds = $('td', nRow);
            		if(status=="新建"){
            			var userId = jqInputs[0].value;
    	                var userName = jqInputs[1].value;
    	                var userType = $('#type option:selected').text();
    	                var userUrl = jqInputs[2].value;
    	                //var percode = jqInputs[3].value;
    	                var parentid = jqInputs[3].value;
    	                //var parentids = jqInputs[5].value;
    	                var sortstring = jqInputs[4].value;
    	                var available = $('#avlb option:selected').text();
            		}else{
            			var userId = jqTds[0].innerText;
    	                var userName = jqInputs[0].value;
    	                var userType = $('#type option:selected').text();
    	                var userUrl = jqInputs[1].value;
    	                //var percode = jqInputs[3].value;
    	                var parentid = jqInputs[2].value;
    	                //var parentids = jqInputs[5].value;
    	                var sortstring = jqInputs[3].value;
    	                var available = $('#avlb option:selected').text();
            		}
	                
	                oTable.fnUpdate(userId, nRow, 0, false);
	                oTable.fnUpdate(userName, nRow, 1, false);
	                oTable.fnUpdate(userType, nRow, 2, false);
	                oTable.fnUpdate(userUrl, nRow, 3, false);
	                //oTable.fnUpdate(percode, nRow, 4, false);
	                oTable.fnUpdate(parentid, nRow, 4, false);
	                //oTable.fnUpdate(parentids, nRow, 6, false);
	                oTable.fnUpdate(sortstring, nRow, 5, false);
	                oTable.fnUpdate(available, nRow, 6, false);
	                oTable.fnUpdate('<a class="edit" href=""><span class="label label-success">修改</span></a>', nRow, 7, false);
	                oTable.fnUpdate('<a class="delete" href=""><span class="label label-danger">删除</span></a>', nRow, 8, false);
	                oTable.fnDraw();
	                //将数据保存到数据库
	                var param = "id="+userId+"&name="+userName
	        		+"&type="+userType+"&url="+userUrl+"&parentid="+parentid+"&sortstring="+sortstring+"&available="+available;
	                var ids="";
	                $.ajaxSetup({async : false});
	                $.post(
	                	"findIds.action",
	                	function(data){
	                		ids=data.toString();
	                	}
	                );
	                if(ids.indexOf(userId)>-1){//update操作
	                	if(status=="新建"){//如果添加的时候id重复
	                		wzj.confirm('温馨提醒','ID冲突，请更换！');
	                		 editRow(oTable, nRow,0);
	                		return;
	                	}else{
	                		$.post(//此处不能添加${pageContext.request.contextPath},也不能要permission
	                            	"update.action",
	                            	param,
	                            	function(data){
	                        		wzj.confirm('温馨提醒','修改成功！');
	                            	}
	                            );
	                	}
	                	
	                }else{//add操作
	                	if(userName=="" || userName==null){
	                		wzj.confirm('温馨提醒','name是必填项!');
	                		return;
	                	}
	                	$.post(
	                		"add.action",
	                		param,
	                		function(data){
	            			wzj.confirm('温馨提醒','添加成功！');
	                		}
	                	);
	                }
	               status="修改"; 
            	}
            	return success
            }
            
            function cancelEditRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
                oTable.fnUpdate(jqInputs[5].value, nRow, 5, false);
                oTable.fnUpdate(jqInputs[6].value, nRow, 6, false);
                oTable.fnUpdate(jqInputs[7].value, nRow, 7, false);
                oTable.fnUpdate(jqInputs[8].value, nRow, 8, false);
                oTable.fnUpdate('<a class="edit" href="">修改</a>', nRow, 9, false);
                oTable.fnDraw();
                $('.sorting').css('width','')
                $('.sorting_disabled').css('width','')
            }
            /*var oTable = $('#editable-sample').dataTable({
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
                if(status=="修改"){
                    var aiNew = oTable.fnAddData(['', '', '', '','','', '',  '<a class="edit" href="">修改</a>', '<a class="cancel" data-mode="new" href="">取消</a>']);
                    var nRow = oTable.fnGetNodes(aiNew[0]);
                    editRow(oTable, nRow,0);
                    nEditing = nRow;
                }
                status="新建";
            });
            
            $('#editable-sample a.delete').live('click', function (e) {
                var that=this
            	e.preventDefault();
                wzj.confirm('温馨提醒', '您确定删除该行吗?', function() {
                	 var nRow = $(that).parents('tr')[0];
                     var userId = nRow.cells[0].innerHTML;//根据一行获取其中一列的值
        			$.post(
        				"deleteById",
        				"id="+userId,
        				function(data){
        					if(data>0){//如果删除成功为1，失败为0
        					   oTable.fnDeleteRow(nRow);
        					   wzj.confirm('温馨提醒','删除成功！');
        					}
        			});
        		});
            });
            $('#editable-sample a.cancel').live('click', function (e) {
            	console.log($(this))
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                    $('.sorting').css('width','')
                    $('.sorting_disabled').css('width','')
                } else {
                	console.log(oTable)
                	console.log(nEditing)
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                    if("新建"==status){
                    	 status="修改";
                    	$('#editable-sample a.cancel').click();
                    }
                }
                status="修改";
            });
            
            $('#editable-sample a.edit').live('click', function (e) {
                e.preventDefault();
                var nRow = $(this).parents('tr')[0];
                if (nEditing != null && nEditing != nRow) {
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow,0);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.childNodes[0].innerHTML == "保存") {
                    var success = saveRow(oTable, nEditing);
                    if(success == true){
                    	nEditing = null;
                    }
                    
                    //alert("Updated! Do not forget to do some ajax to sync with backend :)");
                } else {
                    editRow(oTable, nRow,1);
                    nEditing = nRow;
                }
            });
        }
    };
}();
