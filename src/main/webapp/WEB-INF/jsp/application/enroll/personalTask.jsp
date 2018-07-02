<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>录取样品信息</title>
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
<script type="text/javascript">
$(function(){
	getTasks();
	
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		//alert($(this).children("td").eq(0).text());
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(1).text();
		taskId1=$(this).children("td").eq(0).text();
		comName=$(this).children("td").eq(5).text();
		selectNo=$(this).children("td").eq(3).text();
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
});//初始化
function logout(){
	wzj.confirm('温馨提醒', '您确定退出本系统吗?', function(flag) {
		if(flag) {
			location.href='${ctx}/logout.action';
		}
	});
}

function logoutRedirect(){
	$.post(
		"${ctx}/logout.action",
		function(data){
			location.href="${ctx}/login.action"
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
//处理null值
function exchangeNull(data){
	if(data==null){
		data="";
	}else{
		data=data;
	}
	return data;
}
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
function GetRightMenu(index,value) { 
  
    var imageMenuData = new Array();
    var imageMenuData2 = new Array(); 
  
    imageMenuData.push({
        "text": "查看", "func": function () {
             $("#dialog").click();
        }
    }); 
    imageMenuData.push({
        "text": "样品", "func": function () {
            $("#yp").click();
        }
    });
    imageMenuData2.push(imageMenuData);
    return imageMenuData2;
}
//以上是右键特效的js
var temptr = $();
var nRow = null;//选中的行数
var selectId=null;//选中的业务编号
var taskId1=null;//选中的任务编号
var comName=null;//选中的企业名称
var selectNo=null;//选中的流水号
window.onload=function(){//给表添加滚动条
	$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
	SetTableStyle();
};

function getTasks(){
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/application/common/getPointTask.action",
		"pointId=cy",
		function(data){
			$.each(data,function(i,item){
				if(item.status==8){
				$("#result").append("<tr ondblclick='show()' style='color:red'>"
						+"<td style='display:none;'>"+item.taskId+"</td>"
						+"<td style='display:none;'>"+item.id+"</td>"
						+"<td>"+(i+1)+"</td>"
						+"<td>"+exchangeNull(item.titleno)+"</td>"
						+"<td>"+exchangeNull(item.branchName)+"</td>"
						+"<td>"+exchangeNull(item.enterprisename)+"</td>"
		             	+"<td>"+exchangeNull(item.enterprisekind)+"</td>"
		             	+"<td>"+exchangeNull(item.address)+"</td>"
		             	+"<td>"+exchangeNull(item.postalcode)+"</td>"
		             	+"<td>"+exchangeNull(item.syndicResult)+"</td>"
		             	+"<td>"+exchangeNull(item.businessno)+"</td>"
		             	+"<td>"+exchangeNull(item.registercapital)+"</td>"
		             	+"<td>"+exchangeNull(item.artificialperson)+"</td>"
		             	+"<td>"+exchangeNull(item.apjob)+"</td>"
		             	+"<td>"+exchangeNull(item.fax)+"</td>"
		             	+"<td>"+exchangeNull(item.aptel)+"</td>"
		             	+"<td>"+exchangeNull(item.linkman)+"</td>"
		             	+"<td>"+exchangeNull(item.ljob)+"</td>"
		             	+"<td>"+exchangeNull(item.ltel)+"</td>"
		             	+"<td>"+exchangeNull(item.mainbusiness)+"</td>"
		             	+"<td>"+exchangeNull(item.restbusiness)+"</td>"
		             	+"<td>"+exchangeNull(item.employeetotal)+"</td>"
		             	+"<td>"+exchangeNull(item.techniciantotal)+"</td>"
		             	+"<td>"+exchangeNull(item.bcprincipal)+"</td>"
		             	+"<td>"+exchangeNull(item.tpbusiness)+"</td>"
		             	+"<td>"+exchangeNull(item.tpopost)+"</td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.flat==true?"checked='true'":"")+" readonly='readonly'/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.gravure==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.webby==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.flexible==true?"checked='true'":"")+"/></td>"
		             	+"<td>"+exchangeNull(item.elsetype)+"</td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.papery==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.pastern==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.frilling==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.metal==true?"checked='true'":"")+"/></td>"
		             	+"<td><input disabled='disabled' type='checkbox' "+(item.plastic==true?"checked='true'":"")+"/></td>"
		             	+"<td>"+exchangeNull(item.elsematerial)+"</td>"
		             	+"<td>"+item.printEquipment+"</td>"
		             	+"<td>"+item.testEquipment+"</td>"
		             	+"<td>"+exchangeNull(item.remarks)+"</td>"
		             	+"<td>"+exchangeNull(DateHandle(item.createdate))+"</td>"
// 		             	+"<td></td>"
// 		             	+"<td></td>"
		             	+"</tr>");
				}else{
					$("#result").append("<tr ondblclick='show()'>"
							+"<td style='display:none;'>"+item.taskId+"</td>"
							+"<td style='display:none;'>"+item.id+"</td>"
							+"<td>"+(i+1)+"</td>"
							+"<td>"+exchangeNull(item.titleno)+"</td>"
							+"<td>"+exchangeNull(item.branchName)+"</td>"
							+"<td>"+exchangeNull(item.enterprisename)+"</td>"
			             	+"<td>"+exchangeNull(item.enterprisekind)+"</td>"
			             	+"<td>"+exchangeNull(item.address)+"</td>"
			             	+"<td>"+exchangeNull(item.postalcode)+"</td>"
			             	+"<td>"+exchangeNull(item.syndicResult)+"</td>"
			             	+"<td>"+exchangeNull(item.businessno)+"</td>"
			             	+"<td>"+exchangeNull(item.registercapital)+"</td>"
			             	+"<td>"+exchangeNull(item.artificialperson)+"</td>"
			             	+"<td>"+exchangeNull(item.apjob)+"</td>"
			             	+"<td>"+exchangeNull(item.fax)+"</td>"
			             	+"<td>"+exchangeNull(item.aptel)+"</td>"
			             	+"<td>"+exchangeNull(item.linkman)+"</td>"
			             	+"<td>"+exchangeNull(item.ljob)+"</td>"
			             	+"<td>"+exchangeNull(item.ltel)+"</td>"
			             	+"<td>"+exchangeNull(item.mainbusiness)+"</td>"
			             	+"<td>"+exchangeNull(item.restbusiness)+"</td>"
			             	+"<td>"+exchangeNull(item.employeetotal)+"</td>"
			             	+"<td>"+exchangeNull(item.techniciantotal)+"</td>"
			             	+"<td>"+exchangeNull(item.bcprincipal)+"</td>"
			             	+"<td>"+exchangeNull(item.tpbusiness)+"</td>"
			             	+"<td>"+exchangeNull(item.tpopost)+"</td>"
			             	+"<td><input disabled='disabled' type='checkbox' "+(item.flat==true?"checked='true'":"")+" readonly='readonly'/></td>"
			             	+"<td><input disabled='disabled' type='checkbox' "+(item.gravure==true?"checked='true'":"")+"/></td>"
			             	+"<td><input disabled='disabled' type='checkbox' "+(item.webby==true?"checked='true'":"")+"/></td>"
			             	+"<td><input disabled='disabled' type='checkbox' "+(item.flexible==true?"checked='true'":"")+"/></td>"
			             	+"<td>"+exchangeNull(item.elsetype)+"</td>"
			             	+"<td><input disabled='disabled' type='checkbox' "+(item.papery==true?"checked='true'":"")+"/></td>"
			             	+"<td><input disabled='disabled' type='checkbox' "+(item.pastern==true?"checked='true'":"")+"/></td>"
			             	+"<td><input disabled='disabled' type='checkbox' "+(item.frilling==true?"checked='true'":"")+"/></td>"
			             	+"<td><input disabled='disabled' type='checkbox' "+(item.metal==true?"checked='true'":"")+"/></td>"
			             	+"<td><input disabled='disabled' type='checkbox' "+(item.plastic==true?"checked='true'":"")+"/></td>"
			             	+"<td>"+exchangeNull(item.elsematerial)+"</td>"
			             	+"<td>"+exchangeNull(item.printEquipment)+"</td>"
			             	+"<td>"+exchangeNull(item.testEquipment)+"</td>"
			             	+"<td>"+exchangeNull(item.remarks)+"</td>"
			             	+"<td>"+exchangeNull(DateHandle(item.createdate))+"</td>"
//	 		             	+"<td></td>"
//	 		             	+"<td></td>"
			             	+"</tr>");
				}
			});
		}
	);
	
}
function view(){
	if(selectId==null){
		wzj.alert('温馨提示', '请选择一个任务！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		window.open("${ctx}/application/toView.action?titleNo="+selectNo+"&taskId="+taskId1+"&id="+selectId);
	}
}
function show(){
	if(selectId==null){
		wzj.alert('温馨提示', '请选择一个任务！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		location.href ="${ctx}/pbtSample/toSamples.action?comName="+comName
				+"&titleNo="+selectNo+"&taskId="+taskId1;
	}
}
function deleteRecord(nRow){
	var oTable = $('#editable-sample').dataTable();
	oTable.fnDeleteRow(nRow);
}
//查看意见
function getComment(){
	if(selectId==null){
		wzj.alert('温馨提示', '请选择一个任务！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		$("#suggest").attr("href","#Modal_show2");
		var taskId=taskId1;
		var companyName=comName;
		$("#commentBody").empty();//清空
		$.post(
			"${ctx}/application/getComment.action",
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
}

function viewReview(){
	if(nRow==null){
		wzj.alert('温馨提示','请选择一个任务！');
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		//location.href = "${ctx}/application/toViewPS.action?titleNo="+selectNo+"&taskId="+taskId1;
		window.open("${ctx}/application/toViewPS.action?titleNo="+selectNo+"&taskId="+taskId1);
	}
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                        <div class="btn-group  nopaddingleft">
                               <button onclick="show()" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	  样品 <i class="fa  fa-puzzle-piece" class="btn btn-info" id="yp"></i>
                              </button>
                           </div>
                        <div class="btn-group ">
                               <button onclick="view()" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	  查看申请表<i class="fa fa-eye"></i>
                              </button>
                           </div>
                            <div class="btn-group ">
                               <a id = "viewR" onclick="viewReview()" class="btn btn-info" target="_blank">
                            	查看评审表 <i class="fa fa-book"></i>
                          	    </a>
                           </div>
                           <div class="btn-group ">
                              <button id="suggest" class="btn btn-info" onClick='getComment()' data-toggle='modal' href=''>
                            	  意见 <i class="fa fa-comment"></i>
                              </button>
                           </div>
                            
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" style="width:460%" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="" style="display:none">任务编号</th>
                                 <th class="" style="display:none">申请编号</th>
                                 <th class="">编号</th>
                                 <th class="">流水号</th>
                                 <th class="">分支机构</th>
                                 <th class="">企业名称</th>
                                 <th class="">企业性质</th>
                                 <th class="">详细地址</th>
                                 <th class="">邮政编码</th>
                                 <th class="">评审结果</th>
                                 <th class="">营业执照号码</th>
                                 <th class="">注册资本</th>
                                 <th class="">法人代表</th>
                                 <th class="">法人职务</th>
                                 <th class="">传真</th>
                                 <th class="">法人电话</th>
                                 <th class="">联系人</th>
                                 <th class="">联系人职务</th>
                                 <th class="">联系人电话</th>
                                 <th class="">主营</th>
                                 <th class="">兼营</th>
                                 <th class="">职工人数</th>
                                 <th class="">技术人员数</th>
                                 <th class="">条码印刷技术负责人</th>
                                 <th class="">技术负责人职务</th>
                                 <th class="">技术负责人职称</th>
                                 <th class="">平板印刷</th>
                                 <th class="">凹版印刷</th>
                                 <th class="">丝网印刷</th>
                                 <th class="">柔性版印刷</th>
                                 <th class="">其他印刷</th>
                                 <th class="">纸质</th>
                                 <th class="">不干胶</th>
                                 <th class="">瓦楞纸</th>
                                 <th class="">金属</th>
                                 <th class="">塑料</th>
                                 <th class="">其他材料</th>
                                 <th class="">主要印刷设备</th>
                                 <th class="">条码检测设备</th>
                                 <th class="">备注</th>
                                 <th class="">创建日期</th>
<!--                                  <th class="">发证日期</th> -->
<!--                                  <th class="">到期日期</th> -->
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                        </div>
                     </div>
               </section>
                   <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">分支机构办理个人任务</h4>
              </div>
              <div class="modal-body">
                <table border="0" cellspacing="100px" align="center" style="height: 770px;width:100%">
					<tr id="trs"><td style="height: 100%"><iframe id="person" src="" frameborder="0" width="100%" scrolling="Yes" height="100%" leftmargin="0" topmargin="0"></iframe></td></tr>
				</table>
	         </div>
	         <div class="modal-footer" style="margin-top:0px;">
                <button data-dismiss="modal" class="btn btn-success" type="button">
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
                <h4 class="modal-title">审核意见</h4>
              </div>
              <div class="modal-body" style="max-height: 350px;">
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
    <script src="${ctx}/assets/fuelux/js/tree.min.js"></script><!-- TREE JS  -->
    <script src="${ctx}/js/editable-paging.js"></script>
    <script src="${ctx}/js/editable-table.js"></script>
		<script src="${ctx}/js/commentTable.js" ></script>
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
            $('.sorting').css('width','')
        });
        function processTask(){//初审任务的处理
        	var taskId=$("#result").children("tr").eq(nRow).children("td").eq(0).text();
            var comment=$("#comment").val();
            $.post(
            	"${ctx}/application/climeAndcompleteTask.action",
            	"taskId="+taskId+"&comment="+comment+"&role=分中心评审人员&candidate=fzjg"
            	+"&result=true&status=-1",
            	function(data){
            		window.location.reload();
            	}
            );
        }
     </script>
  <!-- END JS --> 
    <!-- END JS -->
</body>
</html>