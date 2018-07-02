<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编码中心出具检验报告</title>
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
	$("#psb").click(
		function(){
			if(nRow==null){
				wzj.alert('温馨提示','请选择一个任务！');
			}else if(selectId==""){
				wzj.alert('温馨提示','数据为空！');
				return false;
			}else{
					var taskId=$("#result").children("tr").eq(nRow).children("td").eq(0).text();
						$.post(
							"${ctx}/application/pickupTask.action",
							"taskId="+taskId,
							function(data){//刷新页面
								window.location.reload();
							}
						);
		}}	
	);
	
	$("#editable-sample tbody tr").dblclick(function(e){
		view();
	});
	
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		//alert($(this).children("td").eq(0).text());
		nRow = $(this).parent().find("tr").index($(this)[0]);
		selectId=$(this).children("td").eq(1).text();
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
    /* if (index == 5 || index == 6 || index == 7) {
    imageMenuData.push({
        "text": "添加到", "func": function () {
            var href = "?" + dd + "&Mobile=" + value;
            OpenPageByTab('添加', href);
        }
    });
    } */
  
    imageMenuData.push({
        "text": "查看", "func": function () {
            $("#dialog").click();
        }
    }); 
    imageMenuData.push({
        "text": "拾取", "func": function () {
        	if(nRow==null){
				wzj.alert('温馨提示','请选择一个任务！');
			}else if(selectId==""){
				wzj.alert('温馨提示','数据为空！');
				return false;
			}else{
        		    var taskId=$("#result").children("tr").eq(nRow).children("td").eq(0).text();
        				 $.post(
        					"${ctx}/application/pickupTask.action",
        					"taskId="+taskId,
        					function(data){
        						//刷新页面
        						window.location.reload();
        					}
        				);
			}
        }
    });
       imageMenuData2.push(imageMenuData);      
       return imageMenuData2;
}
//以上是右键特效的js
var temptr = $();
var nRow = null;
var selectId=null;
window.onload=function(){//给表添加滚动条
	$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
	SetTableStyle();
};
function getTasks(){
	$.ajaxSetup({async : false});
	$.post(
		"${ctx}/application/expressMessage/getAllTask.action",
		"pointName=出具检验报告&status=7",
		function(data){
			$.each(data,function(i,item){
				$("#result").append("<tr>"
						+"<td style='display:none;'>"+item.taskId+"</td>"
						+"<td>"+item.id+"</td>"
						+"<td>初次申请</td>"
						+"<td>"+item.branchName+"</td>"
		             	+"<td>"+item.enterprisename+"</td>"
		             	+"<td>"+item.enterprisekind+"</td>"
		             	+"<td>"+item.address+"</td>"
		             	+"<td>"+item.postalcode+"</td>"
		             	+"<td></td>"
		             	+"<td></td>"
		             	+"<td></td>"
		             	+"<td>"+item.businessno+"</td>"
		             	+"<td>"+item.registercapital+"</td>"
		             	+"<td>"+item.artificialperson+"</td>"
		             	+"<td>"+item.apjob+"</td>"
		             	+"<td>"+item.fax+"</td>"
		             	+"<td>"+item.aptel+"</td>"
		             	+"<td>"+item.linkman+"</td>"
		             	+"<td>"+item.ljob+"</td>"
		             	+"<td>"+item.ltel+"</td>"
		             	+"<td>"+item.mainbusiness+"</td>"
		             	+"<td>"+item.restbusiness+"</td>"
		             	+"<td>"+item.employeetotal+"</td>"
		             	+"<td>"+item.techniciantotal+"</td>"
		             	+"<td>"+item.bcprincipal+"</td>"
		             	+"<td>"+item.tpbusiness+"</td>"
		             	+"<td>"+item.tpopost+"</td>"
		             	+"<td><input type='checkbox' "+(item.flat==true?"checked='true'":"")+"/></td>"
		             	+"<td><input type='checkbox' "+(item.gravure==true?"checked='true'":"")+"/></td>"
		             	+"<td><input type='checkbox' "+(item.webby==true?"checked='true'":"")+"/></td>"
		             	+"<td><input type='checkbox' "+(item.flexible==true?"checked='true'":"")+"/></td>"
		             	+"<td>"+item.elsetype+"</td>"
		             	+"<td><input type='checkbox' "+(item.papery==true?"checked='true'":"")+"/></td>"
		             	+"<td><input type='checkbox' "+(item.pastern==true?"checked='true'":"")+"/></td>"
		             	+"<td><input type='checkbox' "+(item.frilling==true?"checked='true'":"")+"/></td>"
		             	+"<td><input type='checkbox' "+(item.metal==true?"checked='true'":"")+"/></td>"
		             	+"<td><input type='checkbox' "+(item.plastic==true?"checked='true'":"")+"/></td>"
		             	+"<td>"+item.elsematerial+"</td>"
		             	+"<td>"+item.printEquipment+"</td>"
		             	+"<td>"+item.testEquipment+"</td>"
		             	+"<td>"+item.remarks+"</td>"
		             	+"<td></td>"
		             	+"<td></td>"
		             	+"<td></td></tr>");
			});
		}
	);	
}
function view(){
	if(selectId==null){
		wzj.alert('温馨提示', '请选择数据！')
		return false;
	}else if(selectId==""){
		wzj.alert('温馨提示','数据为空！');
		return false;
	}else{
		//$("#dialog").attr("href","#Modal_show");
		//$("#group").attr("src","${ctx}/application/view.action?id="+selectId);
		location.href = "${ctx}/application/view.action?id="+selectId
	}
}

function deleteRecord(nRow){
	var aTable = $('#editable-sample').dataTable();
	aTable.fnDeleteRow(nRow);
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                        <div class="btn-group col-lg-1 nopaddingleft">
                              <button onclick="view();" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	  查看 <i class="fa fa-eye"></i>
                              </button>
                           </div>
                           <div class="btn-group col-lg-1">
                              <button id="psb" class="btn btn-info" >
                            	  拾取 <i class="fa fa-gavel"></i>
                              </button>
                           </div>
                           
                        <!--    <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a href="#">Print</a></li>
                                 <li><a href="#">Save as PDF</a></li>
                                 <li><a href="">export Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" style="width:460%" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="" style="display:none;">任务编号</th>
                                 <th class="">编号</th>
                                 <th class="">状态</th>
                                 <th class="">分支机构</th>
                                 <th class="">企业名称</th>
                                 <th class="">企业性质</th>
                                 <th class="">详细地址</th>
                                 <th class="">邮政编码</th>
                                 <th class="">证书编号</th>
                                 <th class="">测评结果</th>
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
                                 <th class="">发证日期</th>
                                 <th class="">到期日期</th>
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
                <h4 class="modal-title">分支机构组任务</h4>
              </div>
              <div class="modal-body">
                <table border="0" cellspacing="100px" align="center" style="height: 770px;width:100%">
					<tr id="trs"><td style="height: 100%"><iframe id="group" src="" frameborder="0" width="100%" scrolling="Yes" height="100%" leftmargin="0" topmargin="0"></iframe></td></tr>
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
	<script src="${ctx}/js/editable-table.js" ></script><!-- EDITABLE TABLE JS  -->
     <!-- EDITABLE TABLE FUNCTION  -->
     <script>
        jQuery(document).ready(function() {
            EditableTable.init();
        });
     </script>
  <!-- END JS --> 
</body>
</html>