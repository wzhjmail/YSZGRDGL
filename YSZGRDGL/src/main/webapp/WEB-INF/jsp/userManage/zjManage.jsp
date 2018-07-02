<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询所有</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
    <link href="${ctx}/css/style_self.css" rel="stylesheet">
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js" ></script>
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<link rel="stylesheet" type="text/css" href="assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
<style type="text/css">
	.label{
		line-height: unset;
	}
</style>
<script type="text/javascript">
var oTable = {
		"bFilter":true,
	    "bDestroy" : true,
	    "bRetrieve":true,
		"bProcessing": true,
	    "bServerSide": true,	 
	    "sAjaxSource":"${ctx}/sysUser/findAll2.action?dept=专家用户&ramusCenter=${activeUser.ramusCenter}",
	    "aLengthMenu": [
	                    [5, 15, 20, -1],
	                    [5, 15, 20, "All"]
	                ],
	    "iDisplayLength": 15,
	    "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
	    "sPaginationType": "bootstrap",
	    "oLanguage": {
	        "sLengthMenu": "_MENU_ 条记录每页",
	        "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
	        "oPaginate": {
	            "sPrevious": "上一页",
	            "sNext": "下一页"
	        }
	    },
	    "aoColumnDefs": [{
	        'bSortable': false,
	        'aTargets': [0]
	    }],
	    "aoColumns": [
                      { "mData": null, 
                          "sClass": "center",
                          "fnRender": function(data) {
		                	  if(data.aData.usercode==null){
		                		  sReturn = "暂无"
			               		}else{
			               			sReturn = data.aData.usercode
								}
                          
                          return sReturn;
                      }},
                      { "mData": "username", 
                          "sClass": "center"},
	                  { "mData": "dept", "sClass": "center" },
	                  { "mData": "ramusCenter","sClass": "center"},
	                  { "mData": "unit", "sClass": "center" },
	                  { "mData": null, "sClass": "center",                    
		                  "fnRender": function(data) {
		                	  if(data.aData.locked==1){
		                		  sReturn = "<a href='javascript:'><span class='label label-info' id='"+data.aData.id+"' onclick='audit(0,this)'>激活</span></a>"
			               		}else{
			               			sReturn = "<a href='javascript:'><span class='label label-warning' id='"+data.aData.id+"' onclick='audit(1,this)'>禁用</span></a>"
								}
                          
                          return sReturn;
                      } },
	                  { "mData": null, "sClass": "center",                    
		                  "fnRender": function(data) {
                              sReturn = "<a data-toggle='modal' href='#Modal_show1' href='javascript:'><span class='label label-primary' onclick='viewRole("+data.aData.id+")'>查看</span></a></td>";
                              return sReturn;
                          } },
	                  { "mData": null, "sClass": "center",                    
			                  "fnRender": function(data) {
	                              sReturn = "<a data-toggle='modal' href='#Modal_show' onClick='toUpdateUser("+data.aData.id+")'><span class='label label-success'>修改</span></a>";
	                              return sReturn;
	                          } },
	                  { "mData": null, "sClass": "center",                    
				                  "fnRender": function(data) {
		                              sReturn = " <a href='javascript:;'><span class='label label-danger' onClick='deleteUser("+data.aData.id+")'>删除</span></a>";
		                              return sReturn;
		                          } },
	              ],
	}
	$(function(){
		var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
	      $('#editable-sample').dataTable(oTable)
			$('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium");
  	 	$('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall");
  	 	
		$('#birthday').datepicker({
			format:'yyyy-mm',
		});
		$('.labels').css('text-align','right')
		$('.col-sm-2').css('padding-right','0px')
		$('.row').css('margin','5px 0px')
		//userManage();
		/* var depts = new Array("分中心用户","中心用户","专家用户");
	    for(var i=0;i<depts.length;i++){
	    	var result='<option id="'+depts[i]+'"';
			result+='>'+depts[i]+'</option>';
			$("#dept").append(result);
	    } */
		
	  //分支机构下拉菜单
		 $.post(
			"${ctx}/divisionRegion/getAllDivision.action",
			function(data){
				if(data!=null&&data.length>0){
					for(var i=0;i<data.length;i++){
						if(data[i].divisioncode!="0000"){
							var result="<option value="+ data[i].divisioncode+" id="+ data[i].divisioncode +">"+data[i].divisionname+"</option>";
							$("#ramusCenter").append(result);
						}
					}
				}
			}
		);
		if($("#name").val()!=""){
			$("#Add").click();
		}
	    $.ajaxSetup({async : false});
        $.post(
    			"${ctx}/role/findAll.action",
    			function(data){
    				if(data!=null&&data.length>0){
    					var result="";
    					if($("#branchId").val()=="0000"){
    					for(var i=0;i<data.length;i++){
    						result='<label class="col-sm-4 checkbox-inline"><input type="checkbox" class="validate[required,minCheckbox[1]]" value='+data[i].id+' name="role"/>'+data[i].name+'</label>';
    						$("#rolebox").append(result);
    					}
    					}else{
    						for(var i=0;i<data.length;i++){
							if(data[i].belonged=="否"){
								result='<label class="col-sm-4 checkbox-inline"><input type="checkbox" class="validate[required,minCheckbox[1]]" value='+data[i].id+' name="role"/>'+data[i].name+'</label>';
								$("#rolebox").append(result);
							}
    						}
						}
    				}
    				
    			}
    		); 

    	$('.checkbox-inline').css('margin-left','0px')
	});//开始 
	var roles2=new Array();
	var roles3=new Array();
	var result="";
    function userManage(){
    	var branch=$("#branch").val();
    	param="dept=专家用户&ramusCenter="+branch;
    	$.ajaxSetup({async : false});
		$.post(
			"${ctx}/comUser/findUser.action",
			param,
			function(data){
				var name;
				if(data!=null){
					for(var i=0;i<data.length;i++){
						if(data[i].usercode==null){
							name="暂无"
						}else{
							name=data[i].usercode;
						}
						var result="<tr class='' id='"+data[i].id+"'><td>"+name+"</td><td class=''>"
                            +data[i].username+"</td><td class=''>"
 							+data[i].dept+"</td><td class=''>";
 							
 							result+=getDivName(data[i].ramusCenter)+"</td>";
 							result+="<td class=''>"
 	                            +exchangeNull(data[i].unit)+"</td>"+
 	                           "<td class='' style='display:none'>"
 	                            +data[i].post+"</td>";
 							if(data[i].locked==1){
 								result+="<td><a href='javascript:'><span class='label label-info' id='"+data[i].id+"' onclick='audit(0,this)'>激活</span></a></td>"+
 								"<td class=''style='display:none'>"
 	                            +data[i].locked+"</td>"+
 								"<td><a data-toggle='modal' href='#Modal_show1' href='javascript:'><span class='label label-primary' onclick='viewRole("+data[i].id+")'>查看</span></a></td>"+
 	                            "<td><a data-toggle='modal' href='#Modal_show' onClick='toUpdateUser("
			             			+data[i].id+")'><span class='label label-success'>修改</span></a></td>"+
 	                            "<td><a href='javascript:;'><span class='label label-danger' onClick='deleteUser("+data[i].id+")'>删除</span></a></td></tr>";
 							}else{
 								result+="<td><a href='javascript:'><span class='label label-warning' id='"+data[i].id+"' onclick='audit(1,this)'>禁用</span></a></td>"+
 								"<td class=''style='display:none'>"
 	                            +data[i].locked+"</td>"+
 	                           "<td><a data-toggle='modal' href='#Modal_show1' href='javascript:'><span class='label label-primary' onclick='viewRole("+data[i].id+")'>查看</span></a></td>"+
 	                            "<td><a data-toggle='modal' href='#Modal_show' onClick='toUpdateUser("
		             			+data[i].id+")'><span class='label label-success'>修改</span></a></td>"+
 	                            "<td><a href='javascript:;'><span class='label label-danger' onClick='deleteUser("+data[i].id+")'>删除</span></a></td></tr>";
 							}
						$("#result").append(result);
						$("#dept").val(data[i].dept);
					} 
				}
		});
    }
	
	function audit(locked,element){
		var id=element.id;
		var param = "id="+id+"&locked="+locked;
		$.post(
				"${ctx}/sysUser/audit.action",
				param,
				function(data){
					if(data==1){
						wzj.alert('温馨提示',"已激活！");
						setTimeout(function(){
							window.location.reload();
							},2000);          
					}else if(data==0){
						wzj.alert('温馨提示',"已禁用！");
						setTimeout(function(){
							window.location.reload();
							},2000);  
					}
				}
			);
		
		}
	function exportRecord(){
		window.open('${ctx}/sysUser/exportRecord.action?dept=专家用户');	
	}
	
	function deleteUser(id){
		wzj.confirm('温馨提醒','您确定要删除?',function(flag){
			if(flag){
		var param = "id="+id;
		$.post(//此处不能添加${pageContext.request.contextPath}
             	"${ctx}/sysUser/deleteById.action",
             	param,
             	function(data){//返回值是添加用的ID
             		if(data>0){
             			location.reload();
             		}else{
             			wzj.confirm('温馨提醒','删除失败！');
             		}
             		
             	}
             );
			}});
	}
	function toUpdateUser(uid){
		$('#uid').removeAttr("data-dismiss","modal");
		$(":checkbox").attr("checked",false);//角色清空
		$("#uid").val("");
		$("#usercode").val("");
		$("#username").val("");
		$("#password").val("");
		//$("#dept").val("");
		$("#locked").val("");
		$("#ramusCenter").val("");
		$("#gender").val("");
		$("#birthday").val("");
		$("#phone").val("");
		$("#mobile").val("");
		$("#email").val("");
		$("#unit").val("");
		$("#post").val("");
		var birthday="";
		if($("#name").val()!=""){
			$.post(
					"${ctx}/comUser/findUserById",
					"ID="+$("#cid").val(),
					function(data){
							$("#username").val(data.username);
							$("#dept").val(data.dept);
//								$("#locked").val(data.locked);
							var len= document.getElementById("ramusCenter");
							for(var i=0;i<len.options.length;i++){
								if(data.ramusCenter==len.options[i].value){
									len.options[i].selected=true;
								}
							}
							birthday=data.birthday;
							$("#gender").val(data.gender);
							if(birthday!=null){
								$("#birthday").val(birthday.substring(0,birthday.length-3));
							}else{
								$("#birthday").val(birthday);
							}
							
							$("#mobile").val(data.mobile);
							$("#email").val(data.email);
					});
//				$("#username").val($("#name").val());
		}
		if(uid!=""){
			$.post(
				"${ctx}/sysUser/findUserById.action",
				"ID="+uid,
				function(data){
					birthday=data.birthday;
						$("#uid").val(data.id);
						$("#usercode").val(data.usercode);
						$("#username").val(data.username);
						$("#password").val(data.password);
						//$("#dept").val(data.dept);
						$("#locked").val(data.locked);
						 $("#ramusCenter").find("option[value='"+data.ramusCenter+"']").attr("selected",true);
						 $("#gender").val(data.gender);
						if(birthday!=null){
							$("#birthday").val(birthday.substring(0,birthday.length-3));
						}else{
							$("#birthday").val(birthday);
						}
						$("#phone").val(data.phone);
						$("#mobile").val(data.mobile);
						$("#email").val(data.email);
						$("#unit").val(data.unit);
						$("#post").val(data.post);
						$.post(//查询角色名
							"${ctx}/role/findRole.action",
							"userId="+uid,
							function(dat){
								if(dat!=null){
									var	roleId=dat;
									var boxObj = document.getElementsByName("role");
									var roles=roleId.split(',');
									for(i=0;i<boxObj.length;i++){  
					                     for(j=0;j<roles.length;j++){
					                    if(boxObj[i].value == roles[j])  //如果值与修改前的值相等  
					                    {  
					                        boxObj[i].checked= true;  
					                         break;  
					                    }  
					                    }  
					                }        			
								}
							}
						);	   
				}
			);}
	}
	function updateUser(uid){
		if($('#userinfo').validationEngine('validate')){
		var usercode=$("#usercode").val();
		var username=$("#username").val();
		var password=$("#password").val();
		//var dept=$("#dept").val();
		var locked=$("#locked").val();
		var ramusCenterId=$("#ramusCenter option:selected").attr("id");
		var gender=$("#gender").val();
		var birthday=$("#birthday").val()+"-01";
		var phone=$("#phone").val();
		var mobile=$("#mobile").val();
		var email=$("#email").val();
		var unit=$("#unit").val();
		var post=$("#post").val();
		var role = document.getElementsByName('role');
		var roles='';
		if(birthday==""){
			wzj.confirm('温馨提醒','请选择日期！');
		}
		  var roles1 = new Array();
		  for(var i=0;i<role.length;i++){
			  if(role[i].checked){
				  roles1.push(role[i].value);
			  }
		  }
		  roles=roles1.toString();
		if(uid==""){
			var count="one";
			$.ajaxSetup({async : false});
			$.post(
				"${ctx}/sysUser/checkRegist.action",
				"usercode="+usercode,
				function(data){
					if("false"==data.result)
						count="two";
				}
			  );
			  if(count=="two"){
				  wzj.alert("温馨提示","用户名重复了！");
			  }else{
			var param = "usercode="+usercode+"&username="+username
			+"&password="+password+"&role="+roles+"&dept=专家用户&locked="+locked+"&ramusCenter="+ramusCenterId
			+"&gender="+gender+"&birthday="+birthday+"&phone="+phone+"&mobile="+mobile+"&email="+email+"&unit="+unit+"&post="+post+"&id="+$("#cid").val();
			$.post(//此处不能添加${pageContext.request.contextPath}
                 	"${ctx}/sysUser/insertZj.action",
                 	param,
                 	function(data){//返回值是添加用的ID
                 		if(data==0){
                 			wzj.confirm('温馨提醒','该用户已存在',function(){
                 				$("#name").val("");
                 			location.reload();
                 			});
                 		}else{
                 			wzj.confirm('温馨提醒','添加成功！',function(){
                 				if($("#branch").val()=="0000"){
                 					$("#name").val("");
                         			location.href="${ctx}/zjry.action";
                 				}else{
                 					$("#name").val("");
                         			location.href="${ctx}/fzjgUserManage.action";
                 				}
                 				
                     		});
                 		}
                 	}
                 );}
		}else{
			var count="two";
			$.ajaxSetup({async : false});
			$.post(
				"${ctx}/sysUser/checkUpdate.action",
				"userId="+uid+"&userCode="+usercode,
				function(data){
					if(data.result)
						count="one";
				}
			  );
			  if(count=="two"){
				  wzj.alert("温馨提示","用户名重复！");
			  }else{
				  var param = "id="+uid+"&usercode="+usercode+"&username="+username
					+"&password="+password+"&role="+roles+"&dept=专家用户&locked="+locked+"&ramusCenter="+ramusCenterId
					+"&gender="+gender+"&birthday="+birthday+"&phone="+phone+"&mobile="+mobile+"&email="+email+"&unit="+unit+"&post="+post;
					$.post(
							"${ctx}/sysUser/update.action",
							param,
							function(dat){
								location.reload();
							}
						);
					$('#uid').attr("data-dismiss","modal");
			  }
		}
		 //$('#uid').attr("data-dismiss","modal");
		}
	}
	function viewRole(uid){
		$("#role2").empty();//角色清空
		var result='';
		$.post(//查询角色名
			"${ctx}/role/findRoleByUserId.action",
			"userId="+uid,
			function(dat){
				if(dat!=""){
					for(var i=0;i<dat.length;i++){
							result='<div class="col-sm-4" id="'+dat[i].id+'"';
							result+='>'+dat[i].name+'</div>';
    						$("#role2").append(result);
						}
				}else{
					result='<div';
					result+=' class="col-sm-4">未分配</div>';
					$("#role2").append(result);
				}
				//$("#role2").prop('size',$("#role2 option").size())
			}
		);
	}
	
	function getDivName(code)
	{
		var divname
		$.post(
	        		"${ctx}/divisionRegion/getDivisionByCode.action",
	        		"code="+code,
	        		function(div){
	        			if(div!=null)
	        			divname=div.divisionname;
	        		}
	        	);
		return divname;
	}
	//处理null值
	function exchangeNull(data){
		if(data==null){
			data="";
		}else{
			data=data;
		}
		return data;
	}
</script>
</head>
<body style="overflow-x: hidden;overflow-y:hidden; ">
<input type="hidden" id="name" value="${name}">
<input type="hidden" id="cid" value="${cid}">
<input type="hidden" id="branchId" value="${branchId}">
 <input type="hidden" class="form-control search" value="${activeUser.ramusCenter}" id="branch">
					<section class="panel">
					<div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                           <div class="btn-group nopaddingleft" style="display:none">
                              <button class="btn btn-success" data-toggle='modal' href='#Modal_show' onClick='toUpdateUser(id)' id="Add">
                              	添加 <i class="fa fa-plus"></i>
                              </button>
                           </div>
                        <!--    <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                <!--  <li><a href="#">Print</a></li>
                                 <li><a href="#">Save as PDF</a></li> --
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <div class="space15"></div>
                        <table class="table table-striped table-hover table-bordered" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="">用户名</th>
                                 <th class="">真实姓名</th>
                                 <th class="">用户分类</th>
                                 <th class="">分支机构</th>
                                 <th class="">所属单位</th>
                                 <th class="" style="display: none">职务</th>
                                 <th class="">审核</th>
                                 <th class="" style="display: none">审核1</th>
                                 <th class="" style="width:100px">角色</th>
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
             <div id="Modal_show1" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content" style="width:100%">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">角色查看</h4>
              </div>
              <div class="modal-body">
             <div class="row" style="width:100%">
          <!--     <label class="col-sm-2">角色：</label> -->
              <div class="col-sm-12" id="role2">
           <!--    <select id="role2" name="role2" multiple="multiple" class="form-control" style="font-size:12px;background-color:#eee;width:150px"></select>-->
             </div>
             </div>
	         </div>
	         <div class="modal-footer">
                 <button data-dismiss="modal" class="btn btn-info" type="button">确定</button>
             </div>
              </div>
            </div>
          </div>
          <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content" style="width:100%">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">用户信息</h4>
              </div>
              <div class="modal-body">
<!--                 <table id="user" class="table table-bordered" style="width:100%"> -->
                	
<!--                 </table> -->
			<!-- 第一行 -->
			<form id="userinfo">
			<div class="row" style="width:100%">
<!-- 			<input type="hidden" id="uid" value=""> -->
				<label class="col-sm-2 labels">用户名<b class="red">*</b>：</label>
            		<div class="col-sm-4">
                		<input type="text" id="usercode" name="usercode" value="" class="form-control validate[required,custom[username]]">
                	</div>
              	<label class="col-sm-2 labels">密码<b class="red">*</b>：</label>
                	<div class="col-sm-4">
                  		<input type="password" id="password" name="password" value="" class="form-control validate[required]">
                	</div>
			</div> 
            <!-- 第二行 -->   
            <div class="row" style="width:100%">
            	<label class="col-sm-2 labels">真实姓名<b class="red">*</b>：</label>
					<div class="col-sm-4">
						<input type="text" id="username" name="username" value="" class="form-control validate[required,custom[username]]">
					</div>
				<label class="col-sm-2 labels">性别：</label>
					<div class="col-sm-4">
						<select id="gender" name="gender" style="width: 100%" class="form-control">
							<option value="男">男</option>
							<option value="女">女</option>
						</select>
					</div>
			</div> 
			<!-- 第三行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">电话：</label>
            		<div class="col-sm-4">
                		<input type="text" id="phone" name="phone" value="" class="form-control validate[custom[guPhone]]">
                	</div>
              	<label class="col-sm-2 labels">手机<b class="red">*</b>：</label>
                	<div class="col-sm-4">
                  		<input type="text" id="mobile" name="mobile" value="" class="form-control validate[groupRequired[grp],custom[mobilePhone]]">
                	</div>
			</div> 
			<!-- 第四行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">所属单位：</label>
            		<div class="col-sm-4">
                		<input type="text" id="unit" name="unit" value="" class="form-control">
                	</div>
              	<label class="col-sm-2 labels">职务：</label>
                	<div class="col-sm-4">
                  		<input type="text" id="post" name="post" value="" class="form-control">
                	</div>
			</div> 
			<!-- 第五行 -->
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">邮箱<b class="red">*</b>：</label>
            		<div class="col-sm-4">
                		<input type="text" id="email" name="email" value="" class="form-control validate[required,custom[email]]">
                	</div>
              	<label class="col-sm-2 labels">出生年月<b class="red">*</b>：</label>
           	<div class="col-sm-4 input-append date dpYears" data-date-viewmode="years" data-date-format="yyyy-mm" data-date="2012-02-12">
                  		<input type="text" id="birthday" name="birthday" value="" class="form-control validate[required,custom[date]]">
                		<!-- <span class="input-group-btn add-on">
            				<button class="btn btn-info" type="button" style="height: 34px;margin-left: -32px">
              				<i class="fa fa-calendar" style="bg-color:41CAC0;"></i>
            				</button>
          				</span> -->
			</div>
			</div>
            <!-- 第六行 -->
			<div class="row" style="width:100%">
				<!-- <label class="col-sm-2 labels">用户分类：</label>
					<div class="col-sm-4">
						<select id="dept" name="dept" style="width: 100%" class="form-control"></select>
					</div> -->
					<label class="col-sm-2 labels">所属分中心：</label>
					<div class="col-sm-4">
						<select id="ramusCenter" name="ramusCenter" class="form-control" style="width: 100%">
          				</select>
					</div>
				<label class="col-sm-2 labels">通过审核：</label>
					<div class="col-sm-4">
						<select id="locked" name="locked" style="width: 100%" class="form-control">
							<option value="0">是</option>
							<option value="1">否</option>
						</select>
					</div>
			</div>
			<!-- 第七行 
			<div class="row" style="width:100%">
				<label class="col-sm-2 labels">所属分中心：</label>
					<div class="col-sm-10">
						<select id="ramusCenter" name="ramusCenter" class="form-control" style="width: 100%">
          				</select>
					</div>
			</div>-->
			
			<!-- 第八行 -->
			<div class="row" style="width:100%" id="role">
				<label class="col-sm-2 labels">角色：</label>
				<div class="col-sm-10" id="rolebox"></div>
			</div>
			<div class="modal-footer">
				<button data-dismiss="modal" class="btn btn-info" type="button">取消</button>
				<button class="btn btn-success" type="button" onclick="updateUser(this.value)" id="uid">确定</button>
			</div>
			</form>
              </div>
            </div>
          </div>
	    <!-- END SECTION -->
	 <!-- BEGIN JS -->
   <!--datatable的汉化在assets/data-tables/jquery.dataTables.js中修改 -->
    <script src="${ctx}/js/jquery.sparkline.js"></script><!-- SPARKLINE JS -->
    <script src="${ctx}/js/sparkline-chart.js"></script><!-- SPARKLINE CHART JS -->
    <script src="${ctx}/js/count.js"></script><!-- COUNT JS -->
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<script src="${ctx}/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
	<script src="${ctx}/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
	<script src="${ctx}/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS  -->
	<script src="${ctx}/js/jquery.nicescroll.js" > </script><!-- NICESCROLL JS  -->
	<script src="${ctx}/assets/data-tables/jquery.dataTables.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/assets/data-tables/DT_bootstrap.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/js/respond.min.js" ></script><!-- RESPOND JS  -->
	<script src="${ctx}/js/common-scripts.js" ></script><!-- BASIC COMMON JS  -->
	<script src="${ctx}/js/editable-table-user.js" ></script><!-- EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            //EditableTable.init();
			 $("#userinfo").validationEngine({
	    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	    	        promptPosition: 'topLeft',
	    	        autoHidePrompt: true,
	    	    });

		    	$("#editable-sample thead tr th").css('width',"")
        });
        $.validationEngineLanguage.allRules.mobilePhone = {  
       	      "regex": /^1[3|4|5|7|8][0-9]{9}$/,  
       	      "alertText": "* 请输入正确的手机号码"  
        };  
        /* $.validationEngineLanguage.allRules.guPhone = {  
       	      "regex":/^(0[0-9]{2})-\d{8}$|^(0[0-9]{3}-(\d{7,8}))$|^\d{8}$|^\d{7}$/,  
       	      "alertText": "* 请输入正确的电话号码"  
        }; */
        $.validationEngineLanguage.allRules.guPhone = {  
         	      "regex":/^(0[0-9]{2})-\d{7,8}$|^(0[0-9]{3}-(\d{7,8}))$|^\d{8}$|^\d{7}$/,  
         	      "alertText": "* 请输入正确的电话号码"  
          };
        $.validationEngineLanguage.allRules.date = {  
    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])$/,
    	      "alertText": "* 请输入正确的日期格式"  
  	   	};  
        $.validationEngineLanguage.allRules.username = {  
    	      "regex": /^[\w\u4E00-\u9FA5]+$/,
    	      "alertText": "* 请输入正确的格式"  
  	   	};  
     </script>
  <!-- END JS --> 
</body>
</html>