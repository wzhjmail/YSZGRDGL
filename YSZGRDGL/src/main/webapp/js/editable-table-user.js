document.write('<script src="js/md5-min.js"></script>');
document.write('<script src="js/editable-paging.js"></script>'); 
var EditableTable = function () {
	
'use strict';

    return {
        init: function () {
        	status="修改";
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }
                oTable.fnDraw();
            }

            function editRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                jqTds[0].innerHTML = '<input type="text" class="form-control small" style="width:100px" value="' + aData[0] + '">';
                jqTds[1].innerHTML = '<input type="text" class="form-control small" style="width:100px" value="' + aData[1] + '">';
                jqTds[2].innerHTML = '<select id="dept" name="dept" class="form-control" style="height:30px;font-size:12px;background-color:#eee;width:120px"></select>';
                jqTds[3].innerHTML = '<input type="password" class="form-control small" value="' + aData[3] + '">';
//                jqTds[4].innerHTML = '<input type="text" class="form-control small" style="width:100px" value="' + aData[5] + '">';
                jqTds[4].innerHTML = '<select id="locked" name="locked" class="form-control" style="height:30px;font-size:12px;background-color:#eee;width:100px"></select>';
                jqTds[5].innerHTML = '<select id="role" name="role" class="form-control" style="height:30px;font-size:12px;background-color:#eee;width:120px"></select>';
                if(aData[0]==""){
                	jqTds[6].innerHTML = '<a class="edit" href=""><span class="label label-primary">保存</span></a>';
                    jqTds[7].innerHTML = '<a class="cancel" href=""><span class="label label-info">取消</span></a>';
                }else{
                	jqTds[6].innerHTML = '<select id="role1" name="role1" class="form-control" style="height:30px;font-size:12px;background-color:#eee;width:120px"></select>';
                	jqTds[7].innerHTML = '<a class="edit" href=""><span class="label label-primary">保存</span></a>';
                    jqTds[8].innerHTML = '<a class="cancel" href=""><span class="label label-info">取消</span></a>';
                }
                $.ajaxSetup({async : false});
                $.post(
            			"role/findAll.action",
            			function(data){
            				if(data!=null&&data.length>0){
            					for(var i=0;i<data.length;i++){
            						var result='<option id="'+data[i].id+'"';
            						if(aData[0]==""){
            							result+='>'+data[i].name+'</option>';
                						$("#role").append(result);
            						}else{
            							if(data[i].name==aData[6]){
                							result+=' selected = "selected"'
                						}
            							result+='>'+data[i].name+'</option>';
                						$("#role1").append(result);

            						}
            						            					}
            				}
            			}
            		);
                var depts = new Array("企业用户","分中心用户","中心用户","专家用户");
                for(var i=0;i<depts.length;i++){
                	var result='<option id="'+depts[i]+'"';
					if(depts[i]==aData[2]){
						result+=' selected = "selected"'
					}
					result+='>'+depts[i]+'</option>';
					$("#dept").append(result);
                }
                
                var lock=new Array("0","1");
                for(var i=0;i<lock.length;i++){
                	var result='<option id="'+lock[i]+'"';
					if(lock[i]==aData[5]){
						result+=' selected = "selected"'
					}
					result+='>'+lock[i]+'</option>';
					$("#locked").append(result);
                }
            }

            function saveRow(oTable, nRow) {
            	var rd = nRow.id;//获取添加或者修改行的ID值
            	var userId=0;
                if(rd==null||rd==""){
                	userId=0;
                }else{
                	userId=rd;
                }
                var jqInputs = $('input', nRow);
                var usercode = jqInputs[0].value;
                var username = jqInputs[1].value;
                var dept = $('#dept option:selected').val();
                var password = jqInputs[2].value;
//                jqInputs[3].value
                var locked =$('#locked option:selected').val();;
                var roleId = "";
                var role = "";
                if(userId!=0){
                	roleId = $('#role1 option:selected').attr("id");
                	role =  $('#role1 option:selected').val();
                }else{
                	roleId = $('#role option:selected').attr("id");
                	role =  $('#role option:selected').val();
                }
                oTable.fnUpdate(usercode, nRow, 0, false);
                oTable.fnUpdate(username, nRow, 1, false);
                oTable.fnUpdate(dept, nRow, 2, false);
                oTable.fnUpdate(hex_md5(password), nRow, 3, false);
                if(locked==0){
                	oTable.fnUpdate('<a href=""><span class="label label-success">已审核</span></a>', nRow, 4, false);
                }else{
                	oTable.fnUpdate('<a href=""><span class="label label-info" onclick="audit('+rd+')">待审核</span></a>', nRow, 4, false);
                }
                oTable.fnUpdate(role, nRow, 5, false);
                oTable.fnUpdate('<a class="edit" href=""><span class="label label-success">修改</span></a>', nRow, 7, false);
                oTable.fnUpdate('<a class="delete" href=""><span class="label label-danger">删除</span></a>', nRow, 8, false);
                oTable.fnDraw();
               
                //将数据保存到数据库
                if(userId==0){//添加用户
                	var param = "usercode="+usercode+"&username="+username
            		+"&password="+password+"&role="+roleId+"&dept="+dept+"&locked="+locked;
                	$.post(//此处不能添加${pageContext.request.contextPath}
                         	"sysUser/insert.action",
                         	param,
                         	function(data){//返回值是添加用的ID
                        		if(data>0){
                         			nRow.setAttribute("id",data);
                         			alert("添加成功！");
                         		}else{
                         			alert("添加失败！");
                         		}
                         		$("#result").innerHTML = "" ;
                         		oTable.fnDraw();
                         		location.reload();
                         	}
                         );
                }else{//update操作
                	var param = "id="+rd+"&usercode="+usercode+"&username="+username
            		+"&password="+password+"&role="+roleId+"&dept="+dept+"&locked="+locked;
                	$.post(//此处不能添加${pageContext.request.contextPath}
                    	"sysUser/update.action",
                    	param,
                    	function(data){
                    		if(data>0){
                    			alert("修改成功！");
                    		}else{
                    			alert("修改失败！");
                    		}
                    		location.reload();
                    	}
                    );
                }
                status="修改";
            }

            function cancelEditRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
                oTable.fnUpdate(jqInputs[5].value, nRow, 5, false);
                oTable.fnUpdate('<a class="edit" href="">修改</a>', nRow, 5, false);
                oTable.fnDraw();
            }
            /*var oTable = $('#editable-sample').dataTable({
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"]
                ],
                "iDisplayLength": 5,
                "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
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
            //var oTable = oTable();
            var nEditing = null;
            
            $('#editable-sample_new').click(function (e) {
                e.preventDefault();
                var aiNew = oTable.fnAddData(['', '', '', '', '','', '<a class="edit" href=""><span class="label label-success">修改</span></a>', '<a class="cancel" data-mode="new" href=""><span class="label label-info">取消</span></a>']);
                var nRow = oTable.fnGetNodes(aiNew[0]);
                editRow(oTable, nRow);
                nEditing = nRow;
                status="新建";
            });
            
            $('#editable-sample a.delete').live('click', function (e) {
                e.preventDefault();
				wzj.confirm('温馨提示','您确定删除？',function(flag){
					if(flag){
	                	 var nRow = $(this).parents('tr')[0];
	                     var id=nRow.id
	        			$.post(
	        				"sysUser/deleteById",
	        				"id="+id,
	        				function(data){
	        					if(data>0){//如果删除成功为1，失败为0
	        					   oTable.fnDeleteRow(nRow);
	        					}
	        			});
					}
				});	
            });
            $('#editable-sample a.cancel').live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                } else {
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
                    editRow(oTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.childNodes[0].innerHTML == "保存") {
                	saveRow(oTable, nEditing);
                    nEditing = null;
                    //alert("Updated! Do not forget to do some ajax to sync with backend :)");
                } else {
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
            });
        }
    };//return
}();//function