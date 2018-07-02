<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业信息查询</title>
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
<style type="text/css">
	.searx{
		margin: 5px 0
	}
	.sdate, .fdate, .ddate{
		width: 45%;
		float: left;
		margin-right: 5px
	}
</style>
<script type="text/javascript">
var oTable = {
		"bFilter":true,
	    "bDestroy" : true,
	    "bRetrieve":true,
		"bProcessing": true,
	    "bServerSide": true,	 
	    "sAjaxSource":"${ctx}/common/getCancel.action",
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
//                       { "mData": null, 
//                           "sClass": "center",
//                           "fnRender": function(data) {
//                               sReturn = "<input type='checkbox' name='taskId' value='"+data.aData.id+"'/><input type='hidden' value='"+data.aData.branchId+"'/>";
// 							return sReturn;
//                           } 
//                         },
	                  { "mData": "id", "sClass": "hidden" },
	                  { "mData": "titleno","sClass": "center",
	                	  "fnRender": function(data) {
	                		  var titleNo=data.aData.titleno;
		      					if(titleNo.indexOf("XB")==0){
		      						statuss="申请完成";
		      					}else if(titleNo.indexOf("FR")==0){
		      						statuss="复认完成";
		      					}else if(titleNo.indexOf("BG")==0){
		      						statuss="变更完成";
		      					}
	                          return statuss;
	                      } },
	                  { "mData": "branchName", "sClass": "center" },
	                  { "mData": "enterprisename", "sClass": "center" },
                      { "mData": "serial", 
	                  	"sClass": "center",
	                  	"fnRender": function(data) {
		                      var str = data.aData.serial
		                      if(str!=null){
		                    	  var len = str.toString().length
			                      len = 6-len
			                      for(var i=0;i<len;i++){
			                    	  str = "0"+str
					              }
		                          return str;
		                      }else{
		                    	 return ""; 
		                      }
	                      }  
                      },
	                  { "mData": "certappdate", 
	                      "sClass": "center",
	                      "fnRender": function(data) {
	                    	  if(data.aData.certappdate==null||data.aData.certappdate==""){
	                    		  sReturn ="";
	                    	  }else{
	                    		  sReturn = (new Date(parseFloat(data.aData.certappdate))).format("yyyy-MM-dd");
	                    	  }
	                          return sReturn;
	                      } 
	                    },
	                    { "mData": "certtodate", 
	                        "sClass": "center",
	                        "fnRender": function(data) {
	                        	if(data.aData.certtodate==null||data.aData.certtodate==""){
		                    		  sReturn ="";
		                    	  }else{
		                    		  sReturn = (new Date(parseFloat(data.aData.certtodate))).format("yyyy-MM-dd");
		                    	  }
		                          return sReturn;
	                        } 
	                      },
                         { "mData": "createdate", 
                          "sClass": "center",
                          "fnRender": function(data) {
                        	  if(data.aData.createdate==null||data.aData.createdate==""){
	                    		  sReturn ="";
	                    	  }else{
	                    		  sReturn = (new Date(parseFloat(data.aData.createdate))).format("yyyy-MM-dd");
	                    	  }
	                          return sReturn;
                          } 
                        },
	              ],
	}


	$(function(){
		var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
	      $('#editable-sample').dataTable(oTable)
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
		$('.sdate').datepicker({
			format:'yyyy-mm-dd',
		});
		$('.fdate').datepicker({
			format:'yyyy-mm-dd',
		});
		$('.ddate').datepicker({
			format:'yyyy-mm-dd',
		});
		//获取所有地区
		getProvince();
		//获取分中心
		$.ajax({
		type: 'post',
		url: '${ctx}/count/getFZX.action',
		cache: false,
		success: function (data) {
			var code;
			var name;
			$('#fzx').append('<option value="全部">全部</option>');
			$.each(data,function(i,item){
				if($("#branchId").val()=="0000"){
					$('#fzx').append('<option value="'+item.divisioncode+'">'+item.divisionname+'</option>')
					document.getElementById("fzx").options[0].selected = true; 
				}else{
				if(item.divisioncode==$("#branchId").val()){
					code=item.divisioncode;
					name=item.divisionname;
					$('#fzx').append('<option value="'+code+'">'+name+'</option>')
					document.getElementById("fzx").options.remove(0); 
				}
				}
			})
		}
	});
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
	$('#ywlx').append('<option value="">请选择</option><option value="XB">新办</option><option value="FR">复认</option><option value="BG">变更</option>');
		
		$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
			//获取选中行的第一列的值
			nRow = $(this).parent().find("tr").index($(this)[0]);
			selectId=$(this).children("td").eq(0).text();
			branchId=$(this).children("td:eq(0) input[type='hidden']").val();
			temptr.css("background-color","");
	        temptr = $(this);
	        temptr.css("background-color","#99ffff");
		});
	});
	//处理null值
	function exchangeNull(data){
		if(data==null){
			data="";
		}else{
			data=data;
		}
		return data;
	}
	function GetRightMenu(index,value) { 
	    var imageMenuData = new Array();
	    var imageMenuData2 = new Array(); 
	    imageMenuData.push({
	        "text": "查看", "func": function () {
	        	if(selectId==null){
	        		wzj.alert('温馨提示', '请选择数据！')
	        		return false;
	        	}else if(selectId==""){
	        		wzj.alert('温馨提示','数据为空！');
	        		return false;
	        	}else{
		            $("#dialog").click();
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
	var branchId=null;
	window.onload=function(){//给表添加滚动条
		$("#editable-sample").wrap('<div style="overflow-x:auto;width:100%"></div>');
		SetTableStyle();
		$("body").delegate("#editable-sample tbody tr","click",function(){
			SetTableStyle();
		});
	};
	
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
	        } else {
	            aTable.$('tr.row_selected').removeClass('row_selected');
	            $(this).addClass('row_selected');
	        }
	    });
	}
	function view(){
		if(selectId==null){
			wzj.alert('温馨提示', '请选择一条数据！')
			return false;
		}else if(selectId=="数据为空"){
			wzj.alert('温馨提示','数据不能为空！');
			return false;
		}else{
			location.href = "${ctx}/common/view.action?id="+selectId
		}
	}
	function cancel(){
		if(selectId==null){
			wzj.alert('温馨提示', '请选一条择数据！')
			return false;
		}else if(selectId==""){
			wzj.alert('温馨提示','数据不能为空！');
			return false;
		}else{
			wzj.confirm('温馨提示','您确定要注销该企业？',function(flag){
				$.post(
					"${ctx}/common/cancel.action",
					"id="+selectId,
					function(data){
						window.location.reload();
					}
				);
			});
		}
	}
	function downCert(){
		var str=document.getElementsByName("taskId");
		var objarray=str.length;
		var chestr="";
		for (i=0;i<objarray;i++){
			if(str[i].checked == true) {  
				chestr+=str[i].value+",";
				}
		}
		if(chestr == ""){
			wzj.alert('温馨提示', '请选择数据！')
		}else{
			location.href = "${ctx}/common/exportPDFComCerts.action?type=1&ids="+chestr;
		}
	}
	function downCertFB(){
		var str=document.getElementsByName("taskId");
		var objarray=str.length;
		var chestr="";
		for (i=0;i<objarray;i++){
			if(str[i].checked == true) {  
				chestr+=str[i].value+",";
				}
		}
		if(chestr == ""){
			wzj.alert('温馨提示', '请选择数据！')
		}else{
			location.href = "${ctx}/common/exportPDFComCerts.action?type=2&ids="+chestr
		}
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
	
	function superSearch(){
		var branchId;
		var appStartTime="1000/01/01";
		var appEndTime="1000/01/01";
		var certStartTime="1000/01/01";
		var certEndTime="1000/01/01";
		var expireStartTime="1000/01/01";
		var expireEndTime="1000/01/01";
		var term1;
		var term2;
		var term3;
		var term4;
		var term5;
		var term6;
		var type;
		var enterpriseName;
		var address;
		if($("#add1").attr('checked')){
			branchId=$("#fzx option:selected").val();
		}else{
			branchId="";
		}
		if($("#add2").attr('checked')){
			term1=$("#term1 option:selected").val();
			if($('#appStartDate').val()!=""){
				var appStartTime1=$('#appStartDate').val().split("-");
				appStartTime=appStartTime1[0]+'/'+appStartTime1[1]+'/'+appStartTime1[2];
			}
			if($('#appEndDate').val()!=""){
				var appEndTime1=$('#appEndDate').val().split("-");
				appEndTime=appEndTime1[0]+'/'+appEndTime1[1]+'/'+appEndTime1[2];
			}
		}else{
			term1="xx";
		}
		if($("#add3").attr('checked')){
			term2=$("#term2 option:selected").val();
			if($('#certStartDate').val()!=""){
				var certStartTime1=$('#certStartDate').val().split("-");
				certStartTime=certStartTime1[0]+'/'+certStartTime1[1]+'/'+certStartTime1[2];
			}
			if($('#certEndDate').val()!=""){
				var certEndTime1=$('#certEndDate').val().split("-");
				certEndTime=certEndTime1[0]+'/'+certEndTime1[1]+'/'+certEndTime1[2];
			}
		}else{
			term2="xx";
		}
		if($("#add4").attr('checked')){
			term3=$("#term3 option:selected").val();
			if($('#expireStartDate').val()!=""){
				var expireStartTime1=$('#expireStartDate').val().split("-");
				expireStartTime=expireStartTime1[0]+'/'+expireStartTime1[1]+'/'+expireStartTime1[2];
			}
			if($('#expireEndDate').val()!=""){
				var expireEndTime1=$('#expireEndDate').val().split("-");
				expireEndTime=expireEndTime1[0]+'/'+expireEndTime1[1]+'/'+expireEndTime1[2];
			}
		}else{
			term3="xx";
		}
		if($("#add5").attr('checked')){
			term4=$("#term4 option:selected").val();
			type=$("#ywlx option:selected").val();
		}else{
			term4="xx";
			type="";
		}
		if($("#add6").attr('checked')){
			term5=$("#term5 option:selected").val();
			enterpriseName=$("#qymc").val();
		}else{
			term5="xx";
			enterpriseName="";
		}
		if($("#add7").attr('checked')){
			term6=$("#term6 option:selected").val();
			address=$("#address").val();
		}else{
			term6="xx";
			address="";
		}
		var status="已注销";
		var param="branchId="+branchId+"&term1="+term1+"&appStartTime="+appStartTime+"&appEndTime="+appEndTime+"&term2="+term2+"&certStartTime="+certStartTime+"&certEndTime="+certEndTime+"&term3="+term3+"&expireStartTime="+expireStartTime+"&expireEndTime="+expireEndTime+"&term4="+term4+"&type="+type+"&term5="+term5+"&enterpriseName="+enterpriseName+"&status="+status+"&address="+address+"&term6="+term6;
		  var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
	      oSettings.sAjaxSource = "${ctx}/common/supperSearch.action?"+param;//重新设置url
	      $('#editable-sample').dataTable(oTable).fnDraw()
	}
	
	function getTime(){
		var status="";
		var sTime = $("#from").val();
		var eTime = $("#to").val();
		var timestamp1 = Date.parse(sTime);
		var timestamp2 = Date.parse(eTime);
		var time="sTime="+sTime+"&eTime="+eTime;
		if(timestamp2-timestamp1>=0){
		$("#result").empty();
		$.post(
				"${ctx}/common/getInTime.action",
				time,
				function(data){
					$.each(data,function(i,item){
						var titleNo=item.titleno;
						if(titleNo.indexOf("XB")==0){
							status="申请完成";
						}else if(titleNo.indexOf("FR")==0){
							status="复认完成";
						}else if(titleNo.indexOf("BG")==0){
							status="变更完成";
						}
						var result="<tr>"
								+"<td style='display:none;'>"+item.id+"</td>"
								+"<td style='display:none;'>"+item.branchId+"</td>"
								+"<td>"+(i+1)+"</td>"
								+"<td>"+exchangeNull(status)+"</td>"
				             	+"<td>"+exchangeNull(item.branchName)+"</td>"
				             	+"<td>"+exchangeNull(item.enterprisename)+"</td>"
				             	+"<td>"+DateHandle(item.certappdate)+"</td>"
				             	+"<td>"+DateHandle(item.certtodate)+"</td>"
				             	/* +"<td>"+item.enterprisekind+"</td>"
				             	+"<td>"+exchangeNull(item.address)+"</td>"
				             	+"<td>"+exchangeNull(item.postalcode)+"</td>"
				             	+"<td>"+exchangeNull(item.certificateno)+"</td>"
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
				             	+"<td>"+(item.zhuxiao==null?"":item.zhuxiao)+"</td>"
				             	+"<td>"+(item.zhuxiaodate==null?"":DateHandle(item.zhuxiaodate))+"</td>"
				             	+"<td>"+exchangeNull(item.remarks)+"</td>" */
				             	+"<td>"+DateHandle(item.createdate)+"</td>"
				             	+"<td><input type='checkbox' name='taskId' value='"+item.id+"'/></td></tr>";
								$("#result").append(result);
								result="";
					});
				}
			);
		}else{
				wzj.alert('温馨提示','日期顺序有误!');
			}
	}
	function exportRecord(){
		window.open('${ctx}/common/exportRecord.action?zhuxiao=false');	
	}
	//新申请激活
	function activate(){
		if(selectId==null){
			wzj.alert('温馨提示', '请选一条择数据！')
			return false;
		}else if(selectId=="数据为空"){
			wzj.alert('温馨提示','数据不能为空！');
			return false;
		}else{
			wzj.confirm('温馨提示','您确定要激活该企业？',function(flag){
				$.post(
					"${ctx}/common/activate.action",
					"id="+selectId,
					function(data){
						window.location.reload();
					}
				);
			});
		}
	}
	//复认激活
	function reActivate(){
		if(selectId==null){
			wzj.alert('温馨提示', '请选一条择数据！')
			return false;
		}else if(selectId=="数据为空"){
			wzj.alert('温馨提示','数据不能为空！');
			return false;
		}else{
			wzj.confirm('温馨提示','您确定要激活该企业？',function(flag){
				$.post(
					"${ctx}/common/reAactivate.action",
					"id="+selectId,
					function(data){
						window.location.reload();
					}
				);
			});
		}
	}
	function getProvince(){
		$.post(
			"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
			"regioncode=000000000000",
			function(data){
				if(data!=null&&data.length>0){
					for(var i=0;i<data.length;i++){
						var result='<option id="'+ data[i].regioncode +'" value="'+data[i].regioncode+'">'+data[i].regionname+"</option>";
						$("#province").append(result);
					}
				}
			}
		);
	}
    
    function changePro(){
		var pro = $("#province option:selected").attr("id");
		//市
		$.post(
    		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
    		"regioncode="+pro,
    		function(data){
    			$("#city").empty();
    			$("#area").empty();
    			$("#city").append("<option value='' id='shi'> 请选择市 </option>");
    			if(data!=null&&data.length>0){
    				for(var i=0;i<data.length;i++){
    					var result='<option name="cityName" id="'+ data[i].regioncode +'" value="'+data[i].regioncode+'">'+data[i].regionname+"</option>";
    					$("#city").append(result);
    				}
    			}
    		}
    	);
		var proname = $("#province option:selected").text();
		$("#address").val(proname);
	}
    function changeCit(){
		var city = $("#city option:selected").attr("id");
		$("#area").empty();
		var proname = "";
		var cityname ="";
		if($("#province option:selected").text()!="全部"){
			proname =$("#province option:selected").text();	
		}
		if(city!="shi"){
			//区
			$.post(
	    		"${pageContext.request.contextPath}/divisionRegion/getRegionChildren.action",
	    		"regioncode="+city,
	    		function(data){
					$("#area").append("<option value=''> 请选择地区 </option>");
	    			if(data!=null&&data.length>0){
	    				for(var i=0;i<data.length;i++){
	    					var result='<option name="areaName" id="'+ data[i].regioncode +'" value="'+data[i].regioncode+'">'+data[i].regionname+"</option>";
	    					$("#area").append(result);
	    				}
	    			}
	    		}
	    	);
			if($("#city option[name='cityName']:selected").text()!=" 请选择市"&&$("#city option[name='cityName']:selected").text()!="市辖区"&&$("#city option[name='cityName']:selected").text()!="市辖县"&&$("#city option[name='cityName']:selected").text()!="县"){
				cityname=$("#city option[name='cityName']:selected").text();
			}
		}
		$("#address").val(proname+cityname);
	}
    function changeAre(){
    	var pro = "";
    	var cit = "";
    	var are = "";
    	if($("#province option:selected").text()!="全部"){
    		pro =$("#province option:selected").text();
    	}
    	if($("#city option[name='cityName']:selected").text()!=" 请选择市"&&$("#city option[name='cityName']:selected").text()!="市辖区"&&$("#city option[name='cityName']:selected").text()!="市辖县"&&$("#city option[name='cityName']:selected").text()!="县"){
    		cit=$("#city option[name='cityName']:selected").text()
    	}
    	if($("#area option[name='areaName']:selected").text()!=" 请选择地区"){
    		are = $("#area option[name='areaName']:selected").text()
    	}
    	$("#address").val(pro+cit+are);
    }
    //区域检索
//     function checkArea(){
//    	var address=$("#address").val();
//    	var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
//       oSettings.sAjaxSource = "${ctx}/common/checkArea.action?address="+address+"&status=已注销";//重新设置url
//       $('#editable-sample').dataTable(oTable).fnDraw()
//     }
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                        	<div class="btn-group  nopaddingleft" style="float: left;">
                             <button onclick="view()" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	  查看 <i class="fa fa-eye"></i>
                              </button>
                           </div>
                           
                           <shiro:hasAnyRoles name="中心管理人员">
                           <div id="activate" class="btn-group">
                             <button onclick="activate()" class="btn btn-success">
                            	 新申请激活 <i class="fa fa-wrench"></i>
                              </button>
                           </div>
<!--                            <div id="reActivate" class="btn-group"> -->
<!--                              <button onclick="reActivate()" class="btn btn-success"> -->
<!--                             	 复认激活 <i class="fa fa-wrench"></i> -->
<!--                               </button> -->
<!--                            </div> -->
                           </shiro:hasAnyRoles>        

                        <!--    <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                              <li>
							  <a onclick="downCert()">
                            	 证书下载</i>
                              </a>
                              </li>
                              <li>
							  <a onclick="downCertFB()">
                            	 证书副本下载</i>
                              </a>
                              </li>
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div>	 -->		
                        </div>
                        <br/>
                        <!-- <div class="clearfix">
                           <div class="col-sm-4 nopaddingleft">
	                           	<div class="input-group">
								  <span class="input-group-addon">从</span>
								  <input type="text" class="form_datetime form-control" id="from">
								  <span class="input-group-addon">至</span>
								  <input type="text" class="form_datetime form-control" id="to">
								</div>
							</div>

                           <div class="btn-group nopaddingleft">
                              <button class="btn btn-primary " onclick="getTime();">
                              	到期查找 <i class="fa fa-search"></i>
                              </button>
                           </div>
                        </div> -->
                        <br/>
                      <div class="clearfix" >
<!--          			<button class="btn btn-primary " onclick="checkArea()"> -->
<!--                           	区域检索 <i class="fa fa-search"></i> -->
<!--                      </button> -->
                        <button class="btn btn-primary " data-toggle='modal' href='#Modal_show1'>
                          	高级检索 <i class="fa fa-search"></i>
                          </button>
                          <div style="float: left;margin-right:5px">
<!--                     	<select id="province" onchange="changePro()" name="province"><option selected="true" disabled="true">全部</option></select> -->
<!--                     	<select id="city" onchange="changeCit()" name="city"></select> -->
<!--                     	<select id="area" onchange="changeAre()" name="area"></select> -->
                   	</div>
                   	
                    </div>
<!--                    	<input type="hidden" id="address" name="address" value="" class="form-control" style="float: left;width: 50%"> -->
					<input type="hidden" value="${branchId}" id="branchId">  
                        <div class="space15"></div>
                       <!--  <table class="table table-bordered" style="width:460%" id="editable-sample"> -->
                        <table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
                               <!--  <th class="" style="display:none;">任务编号</th>
                                 <th class="" style="display:none;"></th> --> 
<!--                              	 <th class="">选项</th> -->
                                 <th class="" style="display: none;">编号</th>
                                 <th class="">状态</th>
                                 <th class="">分支机构</th>
                                 <th class="">企业名称</th>
                                 <th class="">证书编号</th>
                                 <th class="">发证日期</th>
                                 <th class="">到期日期</th>
                                 <!-- <th class="">企业性质</th>
                                 <th class="">详细地址</th>
                                 <th class="">邮政编码</th>
                                 <th class="">证书编号</th>
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
                                 <th class="">注销</th>
                                 <th class="">注销时间</th>
                                 <th class="">备注</th> -->
                                 <th class="">更新日期</th>
                              </tr>
                           </thead>
                           <tbody id="result">
                              <!-- 循环显示查询出来的结果 -->
                             
                           </tbody>
                        </table>
                  </div>
               </section>
        <div id="Modal_show" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">企业详细信息</h4>
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
                <h4 class="modal-title">证书寄送信息</h4>
              </div>
              <div class="modal-body">
                <table border="0" cellspacing="100px" align="center" style="height: 100px;width:100%">
                	<tr><td style="height: 40%">时间：</td><td style="height: 60%" id="td1"></td></tr>
					<tr><td style="height: 40%">快递名称：</td><td style="height: 60%" id="td2"></td></tr>
					<tr><td style="height: 40%">订单号：</td><td style="height: 60%" id="td3"></td></tr>
					<tr><td style="height: 40%">联系人：</td><td style="height: 60%" id="td4"></td></tr>
					<tr><td style="height: 40%">联系人电话：</td><td style="height: 60%" id="td5"></td></tr>
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
                    <div id="Modal_show1" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content" style="width:100%">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title">高级检索</h4>
              </div>
              <div class="modal-body">
             <div class="clearfix" >
                    <div class="col-sm-1 nopaddingleft">
                    <input type="checkbox" id="add1">
                    </div>
                        <div class="sear" id="sear">
                    <div class="col-sm-2">分中心：</div>
                    <div class="col-sm-7"><select class="form-control" id="fzx"></select></div>
					<div class="col-sm-2" id="branch" style="display:none">
					</div>
                       </div>  
                    </div>
                    <div class="clearfix searx sear" id="select1">
					<div class="col-sm-1 nopaddingleft">
                    <input type="checkbox" id="add2">
                    </div>
					<div class="col-sm-2 nopaddingleft">申请日期：</div>
					<div class="col-sm-7"><input type="text" class="form_datetime form-control sdate validate[custom[date]]" id="appStartDate"><input type="text" class="form_datetime form-control sdate " id="appEndDate"></div>
					<div class="btn-group"></div>
					<div class="col-sm-2 nopaddingleft"><select class="form-control" id="term1" style="width: 120%"><option value="and">并且</option><option value="or">或者</option><option value="no">不含</option></select></div>
					</div>
                      <div class="clearfix searx sear" id="select2">
					<div class="col-sm-1 nopaddingleft">
                    <input type="checkbox" id="add3">
                    </div>
					<div class="col-sm-2 nopaddingleft">发证日期：</div>
					<div class="col-sm-7"><input type="text" class="form_datetime form-control fdate validate[custom[date]]" id="certStartDate"><input type="text" class="form_datetime form-control fdate" id="certEndDate"></div>
					<div class="btn-group"></div>
					<div class="col-sm-2 nopaddingleft"><select class="form-control" id="term2" style="width: 120%"><option value="and">并且</option><option value="or">或者</option><option value="no">不含</option></select></div>
					</div>  
                    <div class="clearfix searx sear" id="select3">
                    <div class="col-sm-1 nopaddingleft">
                    <input type="checkbox" id="add4">
                    </div>
					<div class="col-sm-2 nopaddingleft">到期日期：</div>
					<div class="col-sm-7"><input type="text" class="form_datetime form-control ddate validate[custom[date]]" id="expireStartDate"><input type="text" class="form_datetime form-control ddate" id="expireEndDate"></div>
					<div class="btn-group"></div>
					<div class="col-sm-2 nopaddingleft"><select class="form-control" id="term3" style="width: 120%"><option value="and">并且</option><option value="or">或者</option><option value="no">不含</option></select></div>
					</div>      
                     <div class="clearfix searx sear" id="select4">
                     <div class="col-sm-1 nopaddingleft">
                    <input type="checkbox" id="add5">
                    </div>
					
					<div class="col-sm-2 nopaddingleft">业务类型：</div>
					<div class="col-sm-7"><select class="form-control" id="ywlx"></select></div>
					<div class="btn-group"></div>
					<div class="col-sm-2 nopaddingleft"><select class="form-control" id="term4" style="width: 120%"><option value="and">并且</option><option value="or">或者</option><option value="no">不含</option></select></div>
					</div>       
                    <div class="clearfix searx sear" id="select5">
<!-- 	            	<div class="col-sm-1 nopaddingleft"><select class="form-control" id="term5"><option value="xx" style="display:none">请选择</option><option value="or">或者</option><option value="and">并且</option><option value="no">不含</option></select></div> -->
					<div class="col-sm-1 nopaddingleft">
                    <input type="checkbox" id="add6">
                    </div>
					<div class="col-sm-2 nopaddingleft">企业名称：</div>
					<div class="col-sm-7"><input class="form-control" id="qymc"></div>
					<div class="btn-group"></div>
					<div class="col-sm-2 nopaddingleft"><select class="form-control" id="term5" style="width: 120%"><option value="and">并且</option><option value="or">或者</option><option value="no">不含</option></select></div>
					</div> 
					<div class="clearfix searx sear" id="select6">
<!-- 	            	<div class="col-sm-1 nopaddingleft"><select class="form-control" id="term5"><option value="xx" style="display:none">请选择</option><option value="or">或者</option><option value="and">并且</option><option value="no">不含</option></select></div> -->
					<div class="col-sm-1 nopaddingleft">
                    <input type="checkbox" id="add7">
                    </div>
					<div class="col-sm-2 nopaddingleft">所在地区：</div>
					<div class="col-sm-7">
						<select id="province" onchange="changePro()" name="province"><option selected="true" disabled="true">全部</option></select>
                    	<select id="city" onchange="changeCit()" name="city"></select>
                    	<select id="area" onchange="changeAre()" name="area"></select>
					<input type="hidden" id="address" name="address" value="" class="form-control" style="float: left;width: 50%">
					</div>
					<div class="btn-group"></div>
					<div class="col-sm-2 nopaddingleft"><select class="form-control" id="term6" style="width: 120%"><option value="and">并且</option><option value="or">或者</option><option value="no">不含</option></select></div>
					</div> 
	         </div>
	         <div class="modal-footer">
                 <button data-dismiss="modal" class="btn btn-info" type="button" onclick="superSearch()">检索</button>
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
    <!-- EDITABLE TABLE FUNCTION  -->
    <script>        
        $(function(){
			setTimeout(function(){ 
				$('.odd').attr('ondblclick','view()')
				$('.even').attr('ondblclick','view()')
			}, 100);
        })
        	$.validationEngineLanguage.allRules.date = {  
		    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
		    	      "alertText": "* 请输入正确的日期格式"  
		   	}; 
   </script>
  <!-- END JS --> 
</body>
</html>