<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet">
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/assets/fuelux/css/tree-style.css" rel="stylesheet">
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script>
   	<link rel="stylesheet" href="${ctx}/css/jquery.treeview.css"/>
	<script src="${ctx}/js/jquery.treeview.js" type="text/javascript"></script>
	<script src="${ctx}/js/jquery.cookie.js" type="text/javascript"></script>
<style type="text/css">
.label-successs {
    background-color: #A9D86E;
    background-color: #5cb85c;
}
.labels {
    padding: 0.5em 0.8em;
    display: inline;
    padding: .2em .6em .3em;
    font-size: 75%;
    font-weight: 700;
    line-height: unset;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;
    }
	.label{
		line-height: unset;
	}
</style>
<script type="text/javascript">
var avlb="否";
var status="添加";
var changedId=0;
var MainSel = null;
var SlaveSel = null;
var Item_org = new Array();
var reg = new RegExp("[`%~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]"); 

	$(function(){
		findAll();//显示当前的所有角色
		$("#editable-new").click(function(){//添加角色按钮触发的函数
			registRole();
		});//添加按钮
		$("#change").click(function(){//点击提交按钮
			if(status=="修改"){//修改功能
				editRoleSubmit();
			}else{//添加功能
				registRoleSubmit();
			}
		});//regis
	});//开始 
	
	function findAll(){//查询所有的角色,并显示在页面上
		$.ajaxSetup({async : false});
		$.post(
			"${ctx}/role/findAll.action",
			function(data){
				if(data!=null){
					console.log(data);
					for(var i=0;i<data.length;i++){
						/* if(data[i].available==1){
							avlb="是";
						}else{
							avlb="否";
						} */
						var result="<tr class='' id='"+data[i].id+"'>"+
						"<td style='display:none'>"+data[i].id+"</td><td>"+(i+1)+"</td><td class=''>"+data[i].name+"</td><td class=''>"+data[i].belonged+"</td>"+
							/* "<td class=''>"+avlb+"</td>"+ */
							"<td><a data-toggle='modal' href='#Modal_show'><span onclick='showPms("+data[i].id+
									")' class='label label-primary'>查 看</span></a></td>"+
                            "<td><a data-toggle='modal' href='#Modal_edit'><span onclick='editRole("+data[i].id+
                            		")' class='label label-success'>修 改</span></a></td>"+
                            "<td><a class='delete' href='#'><span class='label label-danger'>删 除</span></a></td></tr>";
						$("#result").append(result); 
					} 
				}
		});
	}

function drawTree(data,id){
	var len = data.length 
	$('#'+id).empty()
	for(var i=0;i<len;i++){
		if(data[i][data[i].length-1].selected){
			result = '<li><span><input type="checkbox" value="'+data[i][data[i].length-1].id+'" onclick="selectAll(this)" checked>'+data[i][data[i].length-1].name+'</span>'
		}else{
			result = '<li><span><input type="checkbox" value="'+data[i][data[i].length-1].id+'" onclick="selectAll(this)">'+data[i][data[i].length-1].name+'</span>'
		}
		
		if(len>1){
        	result = result + '<ul style="display:none">'
        	var length = data[i].length
			for(var j=0;j<length-1;j++){
				if(data[i][j][data[i][j].length-1].selected){
					result = result + '<li><span><input type="checkbox" onclick="selectM(this)"  value="'+data[i][j][data[i][j].length-1].id+'" checked>'+data[i][j][data[i][j].length-1].name+'</span>'
				}else{
					result = result + '<li><span><input type="checkbox" onclick="selectM(this)" value="'+data[i][j][data[i][j].length-1].id+'">'+data[i][j][data[i][j].length-1].name+'</span>'
				}
				if(data[i][j].length>1){
					result = result + '<ul>'
					for(var m=0;m<data[i][j].length-1;m++){
						if(data[i][j][m].length>0){
							for(var n=0;n<data[i][j][m].length;n++){
								if(data[i][j][m][n].selected){
									result = result + '<li><span><input type="checkbox" onclick="selectS(this)" value="'+data[i][j][m][n].id+'" checked>'+data[i][j][m][n].name+'</span>'
								}else{
									result = result + '<li><span><input type="checkbox" onclick="selectS(this)" value="'+data[i][j][m][n].id+'">'+data[i][j][m][n].name+'</span>'
								}
								
							}
						}
					}
					result = result + '</ul></li>'
				}
			}
        	result = result + '</ul></li>'
		}
		$('#'+id).append(result)
	}
}
	
	function registRole(){//点击添加按钮
		$('#titles').text('添加角色')
		status="添加";
		$("#rolename").val("");
		$.post(//查询所有权限
			"${ctx}/role/getPmsByRoleId.action",
			"roleId="+0,
			function(data){
				drawTree(data,"tree")
				$("#tree").treeview();
				$("#tree input[type='checkbox']").removeAttr('checked')
		}); 
	}

	function editRole(id){//点击修改按钮
		$('#titles').text('修改角色')
		changedId=id;
		status="修改";
		$.ajaxSetup({async : false});
		$.post(
			"${ctx}/role/findRoleById.action",
			"id="+id,
			function(data){
				$("#rolename").val(data.name);
				$("#oldrolename").val(data.name);
				$("#belonged").val(data.belonged);
			}
		);
		$("#roleId").val(id);
		$.post(
			"${ctx}/role/getPmsByRoleId.action",
			"roleId="+id,
			function(data){
				drawTree(data,"tree")
				$("#tree").treeview();
			}
		);
	}
	
	function registRoleSubmit(){//角色添加,提交按钮
		var pmsid = []
		$($('#tree input[type="checkbox"]')).each(function(){
			if($(this)[0].checked==true){
				pmsid.push($(this).val())
			}
		});
		var param = "name="+$("#rolename").val()+"&pmsid="+pmsid+"&belonged="+$("#belonged").val();
		if($("#rolename").val()==""){
			 wzj.alert('温馨提示',"角色名不能为空");
			return false;
		}else{
			if(reg.test($("#rolename").val())){
				wzj.alert('温馨提示',"角色名不能包含特殊字符");
				return false;
			}else{
				$('#change').attr('data-dismiss','modal')
				$.post(
						"${ctx}/role/register.action",
						param,
						function(data){
							if(data=="rename"){
								 wzj.alert('温馨提示',"角色名重复！");
							}else if("error"==data){
								 wzj.alert('温馨提示',"未知错误！");
							}else{
								window.location.reload()
							}
				});//post
			}
		}
	}
	
	function showPms(id){
		$.post(
				"${ctx}/role/getPmsByRoleId.action",
				"roleId="+id,
				function(data){
					drawTree(data,"trs")
					$("#trs").treeview();
					$($('#trs input[type="checkbox"]')).each(function(){
						$(this).attr('disabled','disabled')
						if($(this)[0].checked==false){
							$(this).parent().parent().hide()
						}
					});
				}
			);
	}
	function editRoleSubmit(){//角色修改,提交按钮
		var pmsid = []
		$($('#tree input[type="checkbox"]')).each(function(){
			if($(this)[0].checked==true){
				pmsid.push($(this).val())
			}
		});
		var param = "id="+$("#roleId").val()+"&name="+$("#rolename").val()+"&pmsid="+pmsid
			+"&belonged="+$("#belonged").val()+"&oldrolename="+$("#oldrolename").val();
		if($("#rolename").val()==""){
			 wzj.alert('温馨提示',"角色名不能为空");
			return false;
		}else{
			if(reg.test($("#rolename").val())){
				wzj.alert('温馨提示',"角色名不能包含特殊字符");
				return false;
			}else{
				$('#change').attr('data-dismiss','modal')
				$.post(
						"${ctx}/role/update.action",
						param,
						function(data){
							if(data.toString()=="succ"){
	// 							$("#"+$("#roleId").val()).find("td").eq(1).html($("#rolename").val());
						location.reload();
							}else if("error"==data){
								 wzj.alert('温馨提示',"未知错误！")
							}else if(data=="rename"){
								wzj.alert('温馨提示',"角色名重复！");
							}
				});//post
			}
		}
	}
	
	function exportRecord(){
		window.open("${ctx}/role/exportRecord.action");	
	}
	function cancel(){
		if(status=="添加"){//如果是添加时点击了取消按钮，则将页面中的第一行去掉
			$("#result tr:first").find("td").eq(5).find("a").click();
		}
	}
	
	function selectAll(data){
		$(data).parent().parent().find('ul input[type="checkbox"]').each(function(){
			$(this)[0].checked=$(data)[0].checked
		})
	}
	function selectM(data){
		selectAll(data)
		var flagM=false
		$(data).parent().parent().parent().find('input[type="checkbox"]').each(function(){
			if($(this)[0].checked==true){
				flagM=true
			}
		})
		$(data).parent().parent().parent().parent().find('input[type="checkbox"]:eq(0)')[0].checked=flagM
	}
	function selectS(data){
		var flag=false
		var flagS=false
		$(data).parent().parent().parent().find('input[type="checkbox"]').each(function(){
			if($(this)[0].checked==true){
				flag=true
			}
		})
		$(data).parent().parent().parent().parent().find('input[type="checkbox"]:eq(0)')[0].checked=flag
		$(data).parent().parent().parent().parent().parent().find('input[type="checkbox"]').each(function(){
			if($(this)[0].checked==true){
				flagS=true
			}
		})
		$(data).parent().parent().parent().parent().parent().parent().find('input[type="checkbox"]:eq(0)')[0].checked=flagS
	}
</script>
</head>
<body>

	
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                           <div class="btn-group nopaddingleft">
                             <a data-toggle="modal" href="#Modal_edit">
                              <button id="editable-new" class="btn btn-success green">
                              	添加 <i class="fa fa-plus"></i>
                              </button></a>
                           </div>
                       <!--     <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                               <!--   <li><a href="#">Print</a></li>
                                 <li><a href="#">Save as PDF</a></li> 
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <div class="space15"></div>
                        <table class="table table-striped table-hover table-bordered" id="editable-sample">
                           <thead>
                              <tr>
                              	 <th class="" style="display:none">角色编号</th>
                                 <th class="">编号</th>
                                 <th class="">角色名</th>
                                 <th class="">中心专属</th>
                                 <!-- <th class="">是否可用</th> -->
                                 <th class="">权限</th>
                                 <th>修改</th>
                                 <th>删除</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                     </div>
                  </div>
               </section>
           
	  <!-- END SECTION -->
		<div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">角色权限</h4>
              </div>
              <div class="modal-body">
                <ul id="trs" class="filetree">

			    </ul>
	         </div>
	         <div class="modal-footer" style="margin-top:0px;">
                <button data-dismiss="modal" class="btn btn-success" type="button">
                 	确定
                </button>
              </div>
              </div>
            </div>
          </div>

 		<div id="Modal_edit" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button"  class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title" id="titles"></h4>
              </div>
              <div class="modal-body">
              <div class="row" style="width:100%">
              <div class="col-sm-6">
               <input type="text"  placeholder="角色名称" id="rolename" class="form-control placeholder-no-fix">
               <input type="hidden" id="oldrolename" class="form-control placeholder-no-fix">
              <input type="hidden" id="roleId">
              </div>
              <div class="col-sm-3">中心专属：</div>
              <div class="col-sm-3">
              <select id="belonged" name="belonged" style="width: 100%" class="form-control">
							<option value="否">否</option>
							<option value="是">是</option>
						</select>
              </div>
              </div>
               
                
              	<table width="300" border="0" cellspacing="0" cellpDoAdding="0" align="center" style="display: none;">
					<tr>
						<td width="45%" align="center">
						<select id="select1" size="8" multiple>
						</select></td>
						<td width="10%" align="center" style="padding: 10px">
							<input name="DoAdd" type="button" value=">>" onClick="DoAdds()"><br> 
							<input name="DoDel" type="button" value="<<"" onClick="DoDels()"></td>
						<td width="45%" align="center">
						<select id="select2" size="8" multiple>
						</select></td>
					</tr>
				</table>
			    <ul id="tree" class="filetree">

			    </ul>
<!-- 			     id="cancel" onclick="cancel();" -->
         <div class="modal-footer" style="margin-top:0px;">
                <button data-dismiss="modal" class="btn btn-info" type="button">
                  	取消
                </button>
                <button  class="btn btn-success" type="button" id="change">
                 	提交
                </button>
              </div>
              </div>
            </div>
          </div>
	
	 <!-- BEGIN JS -->
    <script src="${ctx}/js/jquery.sparkline.js"></script><!-- SPARKLINE JS -->
    <script src="${ctx}/js/sparkline-chart.js"></script><!-- SPARKLINE CHART JS -->
    <script src="${ctx}/js/count.js"></script><!-- COUNT JS -->
  
		<script src="${ctx}/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
		<script src="${ctx}/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
		<script src="${ctx}/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS  -->
		<script src="${ctx}/js/jquery.nicescroll.js" > </script><!-- NICESCROLL JS  -->
		<script src="${ctx}/assets/data-tables/jquery.dataTables.js"></script><!-- DATATABLE JS  -->
		<script src="${ctx}/assets/data-tables/DT_bootstrap.js"></script><!-- DATATABLE JS  -->
		<script src="${ctx}/js/respond.min.js" ></script><!-- RESPOND JS  -->
		<script src="${ctx}/js/common-scripts.js" ></script><!-- BASIC COMMON JS  -->
		<script src="${ctx}/js/editable-table-role.js" ></script>
    <script src="${ctx}/assets/fuelux/js/tree.min.js"></script><!-- TREE JS  -->
    <script >
        jQuery(document).ready(function() {
            EditableTable.init();
        });            

     </script>
  <!-- END JS --> 
</body>
</html>