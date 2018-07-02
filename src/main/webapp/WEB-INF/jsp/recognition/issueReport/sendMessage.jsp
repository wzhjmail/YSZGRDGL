<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分中心审批</title>
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
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script>
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
	<style type="text/css">
		.label{
			line-height: unset;
		}
		a{
			cursor: pointer;
		}
	</style>
<script type="text/javascript">
window.onload=function(){//给表添加滚动条
	$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
	SetTableStyle();
};
$(function(){
	getTasks();
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		//alert($(this).children("td").eq(0).text());
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(0).text();
		taskId=$(this).children("td").eq(1).text();
		selectNo=$(this).children("td").eq(5).text();
		comName=$(this).children("td").eq(6).text();
		branchId=$(this).children("td").eq(9).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
});//初始化
function changeUser(){
	var username=$("#recivePerson option:selected").text();
	var id=$("#recivePerson option:selected").val();
	$.post(
		"${ctx}/sysUser/findUserById.action",
		"ID="+id,
		function(data){
			$("#td6").val(username);
			$("#td7").val(data.phone);
			$("#td8").val(data.salt);
		}
	);
}
function changeSend(){
	var sendType=$("#kd option:selected").val();
	$("#td1").val(sendType);
}
function SetTableStyle() {
	var aTable = $('#editable-sample').dataTable();
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
            aTable.$('td.td_selected').removeClass('td_selected');
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
        aTable.$('td.td_selected').removeClass('td_selected');
        if ($(e.target).hasClass('td_selected')) {
            $(e.target).removeClass('td_selected');
        } else {
            $(e.target).addClass('td_selected');
        }
        if ($(this).hasClass('row_selected')) {
            $(this).removeClass('row_selected');
        }
        else {
            aTable.$('tr.row_selected').removeClass('row_selected');
            $(this).addClass('row_selected');
        }
    });
}
function GetRightMenu(index,value) { 
    var imageMenuData = new Array();
    var imageMenuData2 = new Array(); 

    imageMenuData.push({
        "text": "申请表下载", "func": function () {
        	if(nRow==null){
				wzj.alert('温馨提示','请选择一个任务！');
			}else if(selectId=="数据为空"){
				wzj.alert('温馨提示','数据为空！');
				return false;
			}else{
				savePdfSQ();
			}
        }
    });
    imageMenuData.push({
        "text": "评审表下载", "func": function () {
        	if(nRow==null){
				wzj.alert('温馨提示','请选择一个任务！');
			}else if(selectId=="数据为空"){
				wzj.alert('温馨提示','数据为空！');
				return false;
			}else{
				savePdfPS();
			}
        }
    });
    imageMenuData2.push(imageMenuData);
    return imageMenuData2;
}
//以上是右键特效的js
var temptr = $();
var nRow = null;//选中的行
var selectId=null;
var selectNo=null;//选中的流水号
var branchId=null;
function getTasks(){
	$.ajaxSetup({async : false});
	$.post(
			"${ctx}/recognition/getPointTask.action",
			"pointId=fzxfh",
		function(data){
			$.each(data,function(i,item){
				var result="<tr ondblclick='viewInfo()'"
					if(item.status==30)
					result+="style='color:red'"
							result+="><td class='' style='display:none'>"+item.id+"</td>"
							+"<td class='' style='display:none'>"+item.taskId+"</td>"
							+"<td class=''><input type='checkbox' name='message' value='"+item.taskId+"' id='"+item.branchId+"'/></td>"
							+"<td class=''>"+(i+1)+"</td>"
							+"<td class=''>"+item.branchName+"</td>"
							+"<td class=''>"+exchangeNull(item.titleno)+"</td>"
							+"<td class=''>"+item.enterprisename+"</td>"
							+"<td class=''>"+item.enterprisekind+"</td>"
			             	+"<td class=''>"+exchangeNull(item.address)+"</td>"
			             	+"<td class='' style='display:none'>"+item.branchId+"</td>";
					$("#result").append(result);
					result="";
		});
});
}
function setRebackId(id,taskId){
	$("#company").val(id);
	$("#taskId").val(taskId);
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

function setId(){
	$("#send").attr("href","");
	$("#td1").val("");
	$("#td2").val("");
	$("#td5").val("");
	$("#td6").val("");
	$("#td7").val("");
	$("#td8").val("");
	var str=document.getElementsByName("message");
	var objarray=str.length;
	var chestr="";
	var cids1=[];
	var cids2=[];
	for (i=0;i<objarray;i++){
		if(str[i].checked == true) {  
			chestr+=str[i].value+",";
			cids1.push(str[i].id);
			}
	}
	for(j in cids1){
		if(cids1[0]==cids1[j]){
			cids2.push(cids1[j])
		}
	}
	if(cids1.length!=0){
		if(cids2.length==cids1.length){
			$("#send").attr("href","#Modal_show1");
		}else{
			 wzj.alert('温馨提示','请选择相同分中心！');
				return false;
		} 
	}
// 	$("#recivePerson").empty();
	$.post(
			"${ctx}/sysUser/getSendUser.action",
			"branchId=0000",
			function(data){
// 				if(data!=null&&data.length>0){
// 					for(var i=0;i<data.length;i++){
// 						var result="<option value="+data[i].id +">"+data[i].username+"</option>";
// 						$("#recivePerson").append(result);
// 					}
// 				}
				$("#td6").val(data.divisionlinkman);
				$("#td7").val(data.divisionphone1);
				$("#td8").val(data.divisionaddress);
			}
		);
	if(chestr == ""){
		wzj.alert('温馨提示', '请勾选任务！')
	}else{
		$("#taskId").val(chestr);
		$.post(
				"${ctx}/sysUser/getSendUser.action",
				"branchId="+cids1[0],
				function(data){
					$("#td3").val(data.divisionlinkman);
					$("#td4").val(data.divisionphone1);
					$("#td5").val(data.divisionaddress);
				}
			);
		document.getElementById("kd").options[0].selected = true; 
// 		document.getElementById("recivePerson").options[0].selected = true; 
	}
}
function view(id){
	if(id==null){
		wzj.alert('温馨提示', '请选择数据！')
		return false;
	}else if(id==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		//$("#group").attr("src","${ctx}/application/view.action?id="+id);
		location.href = "${ctx}/application/view.action?id="+id
	}
}

function getComment(element){
	var taskId=element.id;
	var companyName=element.name;
	$("#commentBody").empty();//清空
	$.post(
		"${ctx}/application/getComment",
		"taskId="+taskId+"&companyName="+companyName,
		function(data){
			$.each(data,function(i,item){
				var str=DateHandle(item.time);
				$("#commentBody").append(
					"<tr><td>"+str+"</td><td>"
					+item.userId+"</td><td>"
					+item.fullMessage+"</td></tr>");
			});
			$('#comment1').dataTable(oTable)
		}
	);
}
function checkDetail(id){
	$("#express").empty();//清空
	$("#express").append("<tr><td>分支机构</td><td>企业名称</td><td>快递名称</td><td>订单号</td><td>联系人</td><td>联系人电话</td></tr>");
	$.post(
		"${ctx}/application/getComment",//确定action
		"id="+id,
		function(data){
			$.each(data,function(i,item){
				$("#express").append(
					"<tr><td>"+item.branch+"</td><td>"
					+item.company+"</td><td>"+item.name+"</td><td>"+item.number+"</td><td>"+item.contact+"</td><td>"
					+item.phoneNumber+"</td></tr>");
			});
		}
	);
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
	  if(obj!=null){
	    var objDate=new Date(obj).Format("yyyy-MM-dd"); //创建一个日期对象表示当前时间     
	    return objDate; 
	  }else{
	    	return null;
	  }
	}
function savePdfSQ(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downPdf.action?type=2&titleNo='+selectNo);	
	}
}
function savePdfPS(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open('${ctx}/common/downPdf.action?type=5&titleNo='+selectNo);	
	}
}
function showSample(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		location.href ='${ctx}/application/report/toShowSample.action?comName='+comName+'&titleNo='+selectNo;
	}
}
function viewReview(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		$('#viewR').prop('href','../../viewPDF.jsp?code=xb_ps_'+selectId);
	}
}
//查看任务信息
function viewInfo(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId=="数据为空"){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		location.href='${ctx}/recognition/expressMessage/viewAll.action?titleNo='+selectNo+'&appId='+selectId+'&taskId='+taskId+'&comName='+comName;	
	}
}
//打印
function print(){
	if($('#msg').validationEngine('validate')){
		$("#msg").attr("action","${ctx}/application/printSendMsg.action?companyName="+comName);
		$("#msg").attr("target","nm_iframe");
		$("#msg").submit();
		$("#Modal_show1").show();
	}
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                        <div class="btn-group nopaddingleft">
                               <button onclick="viewInfo()" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	  审批<i class="fa fa-eye"></i>
                              </button>
                           </div>
                           <div class="btn-group">
                              <button onclick="setId()" class="btn btn-info" data-toggle='modal' href='' id="send">
                            	 寄送信息 <i class="fa fa-envelope"></i>
                              </button>
                           </div>
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" style="width:100%" id="editable-sample">
                           <input type="hidden" value="${activeUser.usercode}" id="usercode">  
                           <thead>
                              <tr>
                              	 <th class="" style="display:none">业务编号</th>
                              	 <th class="" style="display:none">任务编号</th>
                                 <th class="">操作</th>
                                 <th class="">编号</th>
                                 <th class="">分支机构</th>
                                 <th class="">流水号</th>
                                 <th class="">企业名称</th>
								 <th class="">企业性质</th>
                                 <th class="">详细地址</th>
                                 <th class="" style="display:none">分中心编号</th>
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
<div id="Modal_show1" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">材料寄送信息</h4>
              </div>
              <div class="modal-body">
              <iframe id="id_iframe" name="nm_iframe" style="display:none;">
				</iframe>
              <form id="msg" method="post">
              <div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">快递名称<span style="color: red">*</span></label>
            		<div class="col-sm-4">
                		<select id="kd" onchange="changeSend()" class="col-sm-12 validate[required]">
		                	<option value="EMS" selected="selected">EMS</option>
		                	<option value="中通">中通</option>
		                	<option value="申通">申通</option>
		                	<option value="圆通">圆通</option>
		                	<option value="顺丰">顺丰</option>
		                	<option value="韵达">韵达</option>
	                	</select>
                	</div>
                	<input id="taskId" value="" type="hidden"/>
                	<input class="form-control" id="td1" value="" name="name" style="width: 100%" type="hidden"/>
              	<label class="col-sm-2 labels nopaddingright" style="text-align: right;">订单号<b class="red">*</b></label>
                	<div class="col-sm-4">
                  		<input class="form-control validate[required]" id="td2" value="" name="number" style="width: 100%"/>
                	</div>
				</div>
              <div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">联系人</label>
            		<div class="col-sm-4">
            			<input class="form-control" id="td3" value="" name="contact" style="width: 100%"/>
            		</div>
              	<label class="col-sm-2 labels nopaddingright" style="text-align: right;">联系人电话</label>
                	<div class="col-sm-4">
                  		<input class="form-control validate[custom[telephone]]" id="td4" value="" name="phoneNumber" style="width: 100%"/>
                	</div>
				</div>
              <div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">联系人地址</label>
            		<div class="col-sm-10">
            			<input class="form-control" id="td5" value="" name="conAddress" style="width: 100%"/>
            		</div>
				</div>
<!-- 				<div class="row" style="width: 100%; margin: 5px 0px;"> -->
<!-- 				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人</label> -->
<!--             		<div class="col-sm-4"> -->
<!--             			<select id="recivePerson" onchange="changeUser()" style="margin: 5px 0" class="col-sm-12"></select> -->
<!--             		</div> -->
<!-- 				</div> -->
				<div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人电话<b class="red">*</b></label>
            		<div class="col-sm-4">
            			<input class="form-control validate[required,custom[telephone]]" id="td7" value="" name="recivePhoneNum" style="width: 100%"/>
            		</div>
              	<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人姓名<b class="red">*</b></label>
                	<div class="col-sm-4">
                  		<input class="form-control validate[required]" id="td6" value="" name="reciveName" style="width: 100%"/>
                	</div>
				</div>
				<div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人地址<b class="red">*</b></label>
                	<div class="col-sm-10">
                  		<input class="form-control validate[required]" id="td8" value="" name="reciveAddress" style="width: 100%"/>
                	</div>
				</div>
				</form>
	         </div>
	         <div class="modal-footer" style="margin-top:0px;">
	         	<!-- <button class="btn btn-success" type="button" onclick="print()">
                 	打印
                </button> -->
                <button class="btn btn-success" type="button" onclick="msgAdd()" id="yesbtn">
                 	确定
                </button>
              </div>
              </div>
            </div>
          </div>
          <div id="Modal_show2" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">退回原因</h4>
              </div>
              <div class="modal-body"  style="width:100%">
                <p><center>复审退回意见</center></p>
                <textarea id="comment" autocomplete="off" class="form-control placeholder-no-fix" placeholder="无"></textarea>
	         </div>
	         <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-success" type="button" onclick="back()">确定</button>
              </div>
              </div>
            </div>
          </div>
          <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">评审意见</h4>
              </div>
              <div class="modal-body" style="overflow-y: scroll;max-height: 300px;">
                <table id="comment1" class="table table-bordered" style="width:100%">
                	<thead><tr>
                  	<td>时间</td>
                  	<td>意见人</td>
                  	<td>意见</td>
                  </tr></thead>
                  <tbody id="commentBody">
                  </tbody>
                </table>
	         </div>
	         <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-success" type="button">确定</button>
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
	<script src="${ctx}/js/editable-table.js" ></script> <!--EDITABLE TABLE JS  -->
		<script src="${ctx}/js/commentTable.js" ></script>

     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
		    $("#msg").validationEngine({
		        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
		        promptPosition: 'topLeft',
		        autoHidePrompt: true,
		    });
        });
        function msgAdd(){
        	if($('#msg').validationEngine('validate')){
		        	var taskId = $("#taskId").val();
		        	$("#msg").attr("action","${ctx}/recognition/expressMessage/insertExpress.action?taskId="+taskId);
		        	$("#msg").submit();
		        	$('#yesbtn').attr("data-dismiss","modal")
	        }
        }
        function back(){
        	var taskId = $("#taskId").val();
        	var comment = $("#comment").val();
        	$.post(
               	"${ctx}/recognition/climeAndcompleteTask.action",
               	"taskId="+taskId+"&comment="+comment+"&role=分中心审核人员"
               		+"&result=false&status=17",
               	function(data){
               		window.location.reload();
               	}
            );
        }
        $(function(){
			 $("#msg").validationEngine({
	    	        scrollOffset: 98,//必须设置，因为Toolbar position为Fixed
	    	        promptPosition: 'topLeft',
	    	        autoHidePrompt: true,
	    	    });
        })
        	   $.validationEngineLanguage.allRules.telephone = {  
	    	      "regex": /^1[3|4|5|7|8][0-9]{9}$|^(0[0-9]{2})-\d{7,8}$|^(0[0-9]{3}-(\d{7,8}))$|^\d{8}$|^\d{7}$/,  
	    	      "alertText": "* 请输入正确的电话号码"  
	    	  };  
     </script>
  <!-- END JS --> 
    <!-- END JS -->
</body>
</html>