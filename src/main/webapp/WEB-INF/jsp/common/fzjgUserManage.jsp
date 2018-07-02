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
	    "sAjaxSource":"${ctx}/comUser/findAll.action?ramusCenter=${activeUser.ramusCenter}",
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
	        'aTargets': [8,9,10]
	    }],
	    "aoColumns": [
	                  { "mData": "userLable", "sClass": "center"},
	                  { "mData": "username", "sClass": "center"},
	                  { "mData": "dept", "sClass": "center"},
	                  { "mData": "gender", "sClass": "center"},
	                  { "mData": "birthday", "sClass": "center", 
	                	  "fnRender": function(data) {
               		   var birthday=data.aData.birthday;
               		   if(birthday!=null){
               			return birthday.substring(0,birthday.length-3);
               		   }else{
               			return birthday;
               		   }
                	 }},
	                  { "mData": "ramusCenter", "sClass": "center"},
	                  { "mData": "email", "sClass": "center"},
	                  { "mData": "post", "sClass": "center"},
	                  { "mData": "mobile", "sClass": "center"},
	                  { "mData": null, "sClass": "center",                    
		                  "fnRender": function(data) {
		                	  if(data.aData.usercode!=null&&data.aData.password!=null){
		                		  sReturn = "<a href='javascript:;'><span class='label label-info'>已启用</span></a>";
		                	  }else{
		                		  sReturn = "<a href='javascript:;' onclick='start(this)' id='"+data.aData.id+"' name='"+data.aData.username+"'><span class='label label-success'>启用</span></a>";
		                	  }
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
	        format: 'yyyy-mm',  
		});
		$('.labels').css('text-align','right')
		$('.col-sm-2').css('padding-right','0px')
		$('.row').css('margin','5px 0px')
		//userManage();
	    //分支机构下拉菜单
		$.post(
			"${ctx}/divisionRegion/getAllDivision.action",
			function(data){
				if(data!=null&&data.length>0){
					for(var i=0;i<data.length;i++){
						var result="<option id="+ data[i].divisioncode +">"+data[i].divisionname+"</option>";
						$("#ramusCenter").append(result);
					}
				}
			}
		);
        $('.checkbox-inline').css('margin-left','0px')
        var depts1 = new Array("分中心用户","中心用户","专家用户");
        var depts2 = new Array("分中心用户","专家用户");
        if($("#branch").val()=="0000"){
        	for(var i=0;i<depts1.length;i++){
        		var result='<option id="'+depts1[i]+'"';
        		result+='>'+depts1[i]+'</option>';
        		 $("#dept").append(result);
       		 }
        }else{
        	for(var i=0;i<depts2.length;i++){
        	var	result='<option id="'+depts2[i]+'"';
        		result+='>'+depts2[i]+'</option>';
        		 $("#dept").append(result);
       		 }
        }
        userClass();
     var all=document.getElementById("all");
     all.onchange=function(){
     	 dept=all.options[all.selectedIndex].value;
     	 var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
          oSettings.sAjaxSource = "${ctx}/comUser/findAllByDept.action?dept="+dept+"&ramusCenter="+$("#branch").val();//重新设置url
          $('#editable-sample').dataTable(oTable).fnDraw();
     }
	});//开始 
	//处理null值
	function exchangeNull(data){
		if(data==null){
			data="";
		}else{
			data=data;
		}
		return data;
	}
	var roles2=new Array();
	var roles3=new Array();
	var result="";
    function userManage(){
    	var branch=$("#branch").val();
    	param="ramusCenter="+branch;
    	$.ajaxSetup({async : false});
		$.post(
			"${ctx}/comUser/findAll.action",
			param,
			function(data){
				if(data!=null){
					for(var i=0;i<data.length;i++){
						var result="<tr class='' id='"+data[i].id+"'><td>"+(i+1)+"</td><td class=''>"
                            +exchangeNull(data[i].username)+"</td><td class=''>"
 							+exchangeNull(data[i].gender)+"</td><td class=''>"
 							+exchangeNull(data[i].birthday)+"</td><td class=''>"
 							+getDivName(data[i].ramusCenter)+"</td><td class=''>"
 							+exchangeNull(data[i].email)+"</td><td class=''>"
 							+exchangeNull(data[i].post)+"</td><td class=''>"
 							+exchangeNull(data[i].mobile)+"</td><td class=''><a href='javascript:;' onclick='start(this)' id='"+data[i].id+"' name='"+data[i].username+"'><span class='label label-success'>启用</span></a></td>"+
	             			"<td class=''><a data-toggle='modal' href='#Modal_show' onClick='toUpdateUser("
	             			+data[i].id+")'><span class='label label-success'>修改</span></a></td>"+"<td class=''><a href='javascript:;'><span class='label label-danger' onClick='deleteUser("
	             			+data[i].id+")'>删除</span></a></td></tr>"
						$("#result").append(result);
					} 
				}
		});
    }
    function start(element){
    	var id=element.id;
    	var name=element.name;
    	$.post(
             	"${ctx}/comUser/findByCid.action",
             	"cid="+id,
             	function(data){
             			if(data.dept=="专家用户"){
             				$("#menu2" , parent.document).click()
    						$("#sub2" , parent.document).show()
    						$("#sub5" , parent.document).hide()
    						$("#zjyh" , parent.document).click()
    						location.href="${ctx}/addZJ.action?name="+name+"&cid="+id;
             			}else if(data.dept=="分中心用户"){
             				$("#menu2" , parent.document).click()
    						$("#sub2" , parent.document).show()
    						$("#sub5" , parent.document).hide()
    						$("#fzxyh" , parent.document).click()
    						location.href="${ctx}/addFZXYH.action?cid="+id+"&name="+name;
             			}else if(data.dept=="中心用户"){
             				$("#menu2" , parent.document).click()
    						$("#sub2" , parent.document).show()
    						$("#sub5" , parent.document).hide()
    						$("#zxyh" , parent.document).click()
    						location.href="${ctx}/addZXYH.action?name="+name+"&cid="+id;
             			}
//              			window.location.href="${ctx}/secondForAdd.action?name="+name;
             		}
             );
    }
    function deleteUser(id){
    	wzj.confirm('温馨提醒','您确定要删除?',function(flag){
    		if(flag){
    		var param = "id="+id;
    		$.post(//此处不能添加${pageContext.request.contextPath}
                 	"${ctx}/comUser/deleteById.action",
                 	param,
                 	function(data){//返回值是添加用的ID
//                  		if(data>0){
                 			location.reload();
//                  		}else{
//                  			wzj.confirm('温馨提醒','删除失败！');
//                  		}
                 		
                 	}
                 );
    		}});
    	}
    
	function exportRecord(){
		window.open('${ctx}/sysUser/exportRecord.action?dept=分中心用户');	
	}
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	function DateHandle(obj) { 
		var objDate=null;
		  if(obj!=null){
			objDate=new Date(obj).Format("yyyy-MM-dd"); //创建一个日期对象表示当前时间     
		  }else{
			  objDate="";
		  }
		  return objDate; 
		}  
	function toUpdateUser(uid){
		var branch=$("#branch").val();
		if(branch=="0000"){
			$("#toAdd").attr("style","width:100%;display:block;margin:5px 0px");
		}else{
// 			$("#toAdd").attr("style","width:100%;display:none;margin:5px 0px");
		$("#toAdd").remove();
		}
		$("#uid").val("");
		$("#username").val("");
		$("#post").val("");
		$("#email").val("");
		$("#mobile").val("");
		$("#gender").val("");
		$("#ramusCenter").val("");
		$("#birthday").val("");
		$("#unit").val("");
		var birthday="";
			$.post(
				"${ctx}/comUser/findUserById",
				"ID="+uid,
				function(data){
					birthday=data.birthday;
					$("#uid").val(data.id);
					$("#username").val(data.username);
					$("#post").val(data.post);
					$("#email").val(data.email);
					$("#mobile").val(data.mobile);
					$("#gender").val(data.gender);
					$("#dept").val(data.dept);
// 					$("#dept").find("option[value='"+data.dept+"']").attr("selected",true);
					$("#unit").val(data.unit);
					$("#ramusCenter").find("option[id='"+data.ramusCenter+"']").attr("selected",true);
					if(birthday!=null){
						$("#birthday").val(birthday.substring(0,birthday.length-3));
					}else{
						$("#birthday").val(birthday);
					}
					
				}
			);
	}
	function updateUser(uid){
		$('#uid').removeAttr("data-dismiss","modal");
		if($('#userInfo').validationEngine('validate')){
			var branch=$("#branch").val();
			if(branch=="0000"){
				$("#toAdd").attr("style","width:100%;display:block;");
			}else{
				$("#toAdd").attr("style","width:100%;display:none;");
			}
			var dept=$("#dept").val();
			var username=$("#username").val();
			var post=$("#post").val();
			var email=$("#email").val();
			var mobile=$("#mobile").val();
			var gender=$("#gender option:selected").attr("value");
			var ramusCenter=$("#ramusCenter option:selected").attr("id");
			var unit=$("#unit").val();
	// 		var myDate = new Date();myDate.getFullYear()+"-"+myDate.getMonth()+"-"+myDate.getDate();
			var birthday=$("#birthday").val()+"-01";
			if(ramusCenter=="0000"&&dept=="分中心用户"){
				wzj.confirm('温馨提醒','分中心用户只能选分支机构！');
				return false;
			}else if(dept=="中心用户"&&ramusCenter!="0000"){
				wzj.confirm('温馨提醒','中心用户只能选中国物品编码中心！');
				return false;
			}else{
				  if(uid==""){
						var param = "username="+username+"&post="+post
						+"&email="+email+"&mobile="+mobile+"&gender="+gender+"&ramusCenter="+ramusCenter+"&birthday="+birthday+"&dept="+dept+"&unit="+unit;
						$.post(//此处不能添加${pageContext.request.contextPath}
		                 	"${ctx}/comUser/insert.action",
		                 	param,
		                 	function(data){//返回值是添加用的ID
		                 		location.reload();
		                 	}
			                 );
					}else{
						var param = "username="+username+"&post="+post
						+"&email="+email+"&mobile="+mobile+"&gender="+gender+"&ramusCenter="+ramusCenter+"&birthday="+birthday+"&id="+uid+"&dept="+dept+"&unit="+unit;
						$.post(
								"${ctx}/comUser/update.action",
								param,
								function(dat){
									location.reload();
								}
							);
					}
			}
			  $('#uid').attr("data-dismiss","modal");
		}
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
function userClass(){
       var str1 = '<lable> 用户分类检索：</lable><select id="all" >'+
	'<option value="all">全部</option><option value="中心用户">中心用户</option>'+
	'<option value="专家用户">专家用户</option><option value="分中心用户">分中心用户</option>'+
	'</select>'
	var str2='<lable> 用户分类检索：</lable><select id="all">'+
	'<option value="all">全部</option>'+
	'<option value="专家用户">专家用户</option><option value="分中心用户">分中心用户</option>'+
	'</select>'
	if($("#branch").val()=="0000"){
// 		$('#editable-sample_wrapper .col-sm-4:eq(0)').after(str1)
		$("#userclass").append(str1);
	}else{
		$("#userclass").append(str2);
// 		$('#editable-sample_wrapper .col-sm-4:eq(0)').after(str2)
	}
	}
</script>
</head>
<body style="overflow-x: hidden;overflow-y:hidden; ">
<input type="hidden" class="form-control search" value="${activeUser.ramusCenter}" id="branch">
					<section class="panel">
					<div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                           <div class="btn-group nopaddingleft">
                              <button class="btn btn-success" data-toggle='modal' href='#Modal_show' onClick='toUpdateUser(id)'>
                              	添加 <i class="fa fa-plus"></i>
                              </button>
                           </div>
                            <div class="btn-group" id="userclass">
                           </div>
                         <!--   <div class="btn-group pull-right">
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
                                 <th class="">编号</th>
                                 <th class="">姓名</th>
                                 <th class="">用户分类</th>
                                 <th class="">性别</th>
                                 <th class="">出生年月</th>
                                 <th class="">所属分中心</th>
                                 <th class="">邮箱</th>
                                 <th class="">职务</th>
                                 <th class="">手机</th>
                                 <th>启用登录</th>
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
              <div class="col-sm-12" id="role2">
              
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
              <form action="" id="userInfo">
<!--                 </table> -->
				<div class="row" style="width:100%">
	<!-- 			<input type="hidden" id="uid" value=""> -->
				<label class="col-sm-2 labels">姓名<b class="red">*</b>：</label>
	                                 <div class="col-sm-4">
	                                    <input type="text" id="username" name="username" value="" class="form-control validate[required,custom[username]]">
	                                 </div>
				<label class="col-sm-2 labels">职务：</label>
	                                 <div class="col-sm-4">
	                                    <input type="text" id="post" name="post" value="" class="form-control">
	                                 </div>
	                                  </div>
	             <div class="row" style="width:100%">
	             <label class="col-sm-2 labels">邮箱<b class="red">*</b>：</label>
	                                 <div class="col-sm-4">
	                                    <input id="email" name="email" class="form-control validate[required,custom[email]]">
	                                 </div>
	              <label class="col-sm-2 labels">手机<b class="red">*</b>：</label>
	              <div class="col-sm-4">
	                 <input type="text" id="mobile" name="mobile" value="" class="form-control validate[required,custom[telePhone]]">
	              </div>
	               </div>
	             <div class="row" style="width:100%">
	              <label class="col-sm-2 labels">性别：</label>
	              <div class="col-sm-4">
	                 <select id="gender" name="gender" style="width: 100%" class="form-control">
	                 <option value="男">男</option>
	                 <option value="女">女</option>
	                 </select>
	              </div>
	              <label class="col-sm-2 labels">所属单位：</label>
            		<div class="col-sm-4">
                		<input type="text" id="unit" name="unit" value="" class="form-control">
                	</div>
	               </div>
	               <!-- 第六行 -->
	               <div class="row" style="width:100%">
	             <label class="col-sm-2 labels">用户分类：</label>
					<div class="col-sm-4">
						<select id="dept" name="dept" style="width: 100%" class="form-control"></select>
					</div>
	                            <label class="col-sm-2 labels">出生年月<b class="red">*</b>：</label>
	              <div class="col-sm-4">
	                  		<input type="text" id="birthday" name="birthday" class="form-control validate[required,custom[date]]">
	               </div>
	               </div>
	                <!-- 第七行 -->
				<div class="row" style="width:100%;" id="toAdd">
					<label class="col-sm-2 labels nopaddingright nopaddingleft">所属分中心<b class="red">*</b>：</label>
						<div class="col-sm-4">
							<select id="ramusCenter" name="ramusCenter" class="form-control" style="width: 100%">
	          				</select>
						</div>
				</div>
			</form>
	         </div>
	         <div class="modal-footer">
	            <button data-dismiss="modal" class="btn btn-danger" type="button">取消</button>
	         	<button data-dismiss="modal" class="btn btn-success" type="button" onclick="updateUser(this.value)" id="uid">确定</button>
              </div>
              </div>
            </div>
          </div>
	    <!-- END SECTION -->
	 <!-- BEGIN JS -->
   <!--datatable的汉化在assets/data-tables/jquery.dataTables.js中修改 -->
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
	<script src="${ctx}/js/editable-table-user.js" ></script><!-- EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
           // EditableTable.init();
			 $("#userInfo").validationEngine({
	    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	    	        promptPosition: 'topLeft',
	    	        autoHidePrompt: true,
	    	 });
        });
        $.validationEngineLanguage.allRules.telePhone = {  
	    	      "regex": /^1[3|4|5|7|8][0-9]{9}$/,  
	    	      "alertText": "* 请输入正确的手机号码"  
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