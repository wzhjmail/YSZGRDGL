<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询所有</title>
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
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
<script type="text/javascript">
var oTable = {
	"bFilter":true,
    "bDestroy" : true,
    "bRetrieve":true,
    "bServerSide": true,
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

    "aoColumns": [
                  { "mData": "id", "sClass": "center" },
                  { "mData": "time", 
                    "sClass": "center",
                    "fnRender": function(data) {
                        sReturn = (new Date(parseFloat(data.aData.time))).format("yyyy-MM-dd hh:mm:ss");
                        return sReturn;
                    } 
                  },
                  { "mData": "person", "sClass": "center" },
                  { "mData": "department", "sClass": "center" },
                  { "mData": "operationType", "sClass": "center" },
                  { "mData": "operation", "sClass": "center" },
              ],
}
	$(function(){
	      var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
	      oSettings.sAjaxSource = "${ctx}/log/findAll.action";//重新设置url
	      $('#editable-sample').dataTable(oTable).fnDraw()
 			$('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium");
      	 	$('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall");
			$('#from').datepicker({
				format:'yyyy-mm-dd',
			});
			$('#to').datepicker({
				format:'yyyy-mm-dd',
			});
			$('#timePoint').datepicker({
				format:'yyyy-mm-dd',
			});
			$.ajax({
				type: 'post',
				url: '${ctx}/log/findAll.action?sEcho=1&iDisplayStart=0&iDisplayLength=15&sSearch=',
				cache: false,
				success: function (data) {
					console.log(data)
				}
			});
	});
	
	function deleteLog(){
		var timePoint = $("#timePoint").val();
		if(timePoint==""){
			wzj.alert('温馨提醒',"请选择时间点！");
			return;
		}
		$.post(
			"${ctx}/log/deleteBefore.action",
			"timePoint="+timePoint,
			function(data){
				if(data!=null){
					var table = $('#editable-sample').DataTable();
					table.fnClearTable();//清空表
					for(var i=0;i<data.length;i++){//在框架的表中添加记录
						table.fnAddData([i+1,
							(new Date(parseFloat(data[i].time))).format("yyyy-MM-dd hh:mm:ss"),
						    data[i].person,
                            data[i].department,
                            data[i].operationType,
                            data[i].operation]);
					}
				}
			}
		);
	}
	
	function getTime(){
		var sTime = $("#from").val();
		var eTime = $("#to").val();
		var time="sTime="+sTime+"&eTime="+eTime;
	      var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
	      oSettings.sAjaxSource = "${ctx}/log/findByTime.action?"+time;//重新设置url
	      $('#editable-sample').dataTable(oTable).fnDraw()
	}
	Date.prototype.format = function(format) {
        var o = {
            "M+" : this.getMonth() + 1,// month
            "d+" : this.getDate(),// day
            "h+" : this.getHours(),// hour
            "m+" : this.getMinutes(),// minute
            "s+" : this.getSeconds(),// second
            "q+" : Math.floor((this.getMonth() + 3) / 3),// quarter
            "S" : this.getMilliseconds()
        };
        if (/(y+)/.test(format) || /(Y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for ( var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    };
    
	function exportRecord(){
		window.open('${ctx}/log/exportRecord.action');	
	}

	//定时删除记录
	/* function exportLog(){
		var date = new Date();
		var time1=date.getMonth()+1;
		var time="time="+time1;
		$.ajaxSetup({async : false});
		$.post(
			"${ctx}/log/exportLog.action",
			time,
			function(data){
				location.reload;
			}
		);
	}
	setTimeout("exportLog()", 3000); */
</script>
</head>
<body>
               <section class="panel">
                 
                  <div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                        <!-- <div class="col-md-5 nopaddingleft">
							<div class="input-group input-large" data-date="2013-07-13" data-date-format="yyyy/mm/dd">
							  <span class="input-group-addon">从</span>
							  <input type="text" class="form_datetime form-control validate[custom[date]]" id="from">
							  <span class="input-group-addon">至</span>
							  <input type="text" class="form_datetime form-control validate[custom[date]]" id="to">
							</div>		   
						  </div>
                           <div class="btn-group pull-left ">
                              <button class="btn btn-primary" onclick="getTime();">
                              查找	<i class="fa fa-search"></i> 
                              </button>
                           </div> -->
                           

						  <div class="col-md-1"> </div>
						  <!-- <div class="btn-group col-md-1">
                              <button class="btn btn-primary" onclick="deleteLog();">
                              	删除
                              </button>
                           </div>
						  <div class="col-md-3">
							<div class="input-group input-large" data-date-format="yyyy/mm/dd">
							  <input type="text" class="form_datetime form-control" id="timePoint">
							  <span class="input-group-addon">前的日志</span>
							</div>		   
						  </div> -->
                           
                        <!--    <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a href="#">Print</a></li>
                                 <li><a href="#">Save as PDF</a></li>
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        
                        <div class="space15"></div>
                        <table class="table table-striped table-hover table-bordered" id="editable-sample">
                           <thead>
                              <tr>
                                 <th class="">编号</th>
                                 <th class="">时间</th>
                                 <th class="">操作人</th>
                                 <th class="">所属中心</th>
                                 <th class="">操作类型</th>
                                 <th class="">操作内容</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                     </div>
                  </div>
               </section>
	 <!-- BEGIN JS -->
	  	<script src="${ctx}/js/jquery.sparkline.js"></script><!-- SPARKLINE JS -->
    	<script src="${ctx}/js/sparkline-chart.js"></script><!-- SPARKLINE CHART JS -->
    	<script src="${ctx}/js/count.js"></script><!-- COUNT JS -->
		<script src="${ctx}/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
		<script src="${ctx}/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
		<script src="${ctx}/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS  -->
		<script src="${ctx}/js/jquery.nicescroll.js" > </script><!-- NICESCROLL JS  -->
		<script src="${ctx}/js/respond.min.js" ></script><!-- RESPOND JS  -->
		<script src="${ctx}/js/common-scripts.js" ></script><!-- BASIC COMMON JS  -->
    <script src="${ctx}/assets/fuelux/js/tree.min.js"></script><!-- TREE JS  -->
	<script src="${ctx}/assets/data-tables/jquery.dataTables.js"></script><!-- DATATABLE JS  -->
	<script src="${ctx}/assets/data-tables/DT_bootstrap.js"></script><!-- DATATABLE JS  -->
  <!-- END JS --> 
    <script>
        $.validationEngineLanguage.allRules.date = {  
	    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
	    	      "alertText": "* 请输入正确的日期格式"  
	   	}; 
   </script>
</body>
</html>