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
	<link rel="stylesheet" href="${ctx}/css/validationEngine.jquery.css">
	<link rel="stylesheet" href="${ctx}/css/jquery.mloading.css">
	<script src="${ctx}/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/js/jquery.validationEngine.js"></script>
	<script src="${ctx}/js/jquery.mloading.js"></script>
<style type="text/css">
	.searx{
		margin: 5px 0
	}
	.sdate, .fdate, .ddate{
		width: 45%;
		float: left;
		margin-right: 5px
	}
	.btn-group{
		padding-left: 0px;
		padding-right: 15px;
    	padding-bottom: 10px;
	}
</style>
<script type="text/javascript">
var reg = new RegExp(/^\d{6}$/); 
var oTable = {
		"bFilter":true,
	    "bDestroy" : true,
	    "bRetrieve":true,
		"bProcessing": true,
	    "bServerSide": true,	 
	    "sAjaxSource":"${ctx}/common/getAll.action",
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

                              console.log(data.oSettings._iRecordsTotal)
                              sReturn = "<input type='checkbox' name='taskId' titleno='"+data.aData.titleno+"' value='"+data.aData.id+"' id='"+data.aData.branchId+"'/><input type='hidden' value='"+data.aData.branchId+"'/>";
                              return sReturn;
                          } 
                        },
	                  { "mData": "id", "sClass": "hidden" },
	                  { "mData": "status", "sClass": "hidden" },
	                  { "mData":"status","sClass": "center",
	                	  "fnRender": function(data) {
// 	                		   var titleNo=data.aData.titleno;titleNo.indexOf("XB")==0&&
	                		   var ywStatus=data.aData.status;
	                		   var statuss="";
// 	                		   var certNo=data.aData.serial;
// 	                		   if(certNo!=""&&ywStatus<17){
// 		    						status="已发证";
// 		    					}else{
		    						if(ywStatus==37){
			    						statuss="复认中";
			    					}else if(ywStatus==34){
			    						statuss="复认完成";
			    					}else if(ywStatus==36){
			    						statuss="变更中";
			    					}else if(ywStatus==35){
			    						statuss="变更完成";
			    					}else if(ywStatus==17){
			    						statuss="申请完成";
			    					}
// 		    					} 
// 		      					if(titleNo.indexOf("XB")==0){
// 		    						status="申请完成";
// 		    					}else 
		      					/*var stat=data.aData.status;
		      					console.log(stat)
		      					switch(stat){
		      						case 11:status="申请完成";break;
		      						case (12<=stat<22):status="复认中";break;
		      						case 22:status="复认完成";break;
		      						case 24:status="变更中";break;
		      						case 23:status="变更中";break;
		      					}*/
	                          return statuss;
	                      } },
	                  { "mData": "branchName", "sClass": "center" },
	                  { "mData": "enterprisename", "sClass": "center" },
                      { "mData": "serial", 
	                    "sClass": "center",
	                      "fnRender": function(data) {
	                    	  var str = "";
							if(data.aData.serial!=null){
								str=data.aData.serial;
							var len = data.aData.serial.toString().length
		                      len = 6-len
		                      for(var i=0;i<len;i++){
		                    	  str = "0"+str
				              }
		                     }
	                          return str;
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
                        { "mData": "branchId", "sClass": "hidden" },
	              ],
	}

function changeSend(){
	var sendType=$("#kd option:selected").text();
	if(sendType=="快递名称"){
		$("#td1").val("");
	}else{
		$("#td1").val(sendType);
	}
	
}
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
		//加载地区
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
	            "d+" : this.getDate(),//day
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
			selectId=$(this).children("td").eq(1).text();
			comName=$(this).children("td").eq(5).text();
			selectStatus=$(this).children("td").eq(2).text();
			branchId=$(this).children("td").eq(10).text();
			companyName=$(this).children("td").eq(5).text();
// 			branchId=$(this).children("td:eq(0) input[type='hidden']").val();
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
	var selectStatus=null;
// 	var branch=null;
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
		}else if(selectId==""){
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
		var titleno = [];
		var path="";
		for (i=0;i<objarray;i++){
			if(str[i].checked == true) {  
				chestr+=str[i].value+",";
				path+=str[i].value+"_";
				titleno.push($(str[i]).attr("titleno"));
				}
		}
	path=path+"企业证书.pdf"
		console.log(chestr)
		console.log(titleno)
		if(chestr == ""){
			wzj.alert('温馨提示', '请选择数据！')
		}else{
			location.href = "${ctx}/common/exportPDFComCerts.action?type=1&ids="+chestr;
 			setTimeout(function(){
 				window.open('../viewPic.jsp?flag=5&path='+'upload/企业证书/'+path,"_blank")
 			},2000);
		}
	}
	function downCertFB(){
		var str=document.getElementsByName("taskId");
		var objarray=str.length;
		var chestr="";
		var path="";
		for (i=0;i<objarray;i++){
			if(str[i].checked == true) {  
				chestr+=str[i].value+",";
				path+=str[i].value+"_";
				}
		}
		path=path+"企业证书副本.pdf"
		if(chestr == ""){
			wzj.alert('温馨提示', '请选择数据！')
		}else{
			location.href = "${ctx}/common/exportPDFComCerts.action?type=2&ids="+chestr
			setTimeout(function(){
			window.open('../viewPic.jsp?flag=5&path='+'upload/企业证书副本/'+path,"_blank")
			},2000);
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
	function getTasks(){
		var status="";
		$.ajaxSetup({async : false});
		$.post(
			"${ctx}/common/getAll.action",
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
		var status="没注销";
		var param="branchId="+branchId+"&term1="+term1+"&appStartTime="+appStartTime+"&appEndTime="+appEndTime+"&term2="+term2+"&certStartTime="+certStartTime+"&certEndTime="+certEndTime+"&term3="+term3+"&expireStartTime="+expireStartTime+"&expireEndTime="+expireEndTime+"&term4="+term4+"&type="+type+"&term5="+term5+"&enterpriseName="+enterpriseName+"&status="+status+"&address="+address+"&term6="+term6;
		var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
	      oSettings.sAjaxSource = "${ctx}/common/supperSearch.action?"+param;//重新设置url
	      $('#editable-sample').dataTable(oTable).fnDraw()
	      $("#recent").val("");
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
								+"<td>"+exchangeNull(item.status)+"</td>"
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

	function sendMsg(){
        if(selectId==null){
            wzj.alert('温馨提示', '请选择一条数据！')
            return false;
        }else if(selectId==""){
            wzj.alert('温馨提示','数据不能为空！');
            return false;
        }else{
        	$("#dialog2").attr("href","#Modal_show3")
    		
        	if($("#userCenter").val()=="0000"){
        		$("#kd").val("EMS");
//         		$("#kd").attr("disabled","disabled");
        		$("#printbtn").show();
        		$("#msg input").removeAttr("disabled");
        		$("#okbtn").attr("onclick","updateMsg()");
        		if($("#td6").val()==""&&$("#td7").val()==""&&$("#td8").val()==""&&$("#td3").val()==""&&$("#td4").val()==""&&$("#td5").val()==""&&$("#td2").val()==""){
        			$.post(
            				"${ctx}/sysUser/getSendUser.action",
            				"branchId="+branchId,
            				function(data){
            					$("#td6").val(data.divisionlinkman);
            					$("#td7").val(data.divisionphone1);
            					$("#td8").val(data.divisionaddress);
            				}
            			);
            		$.post(
            				"${ctx}/sysUser/getSendUser.action",
            				"branchId=0000",
            				function(data){
            					$("#td3").val(data.divisionlinkman);
            					$("#td4").val(data.divisionphone1);
            					$("#td5").val(data.divisionaddress);
            				}
            			);
        		}else{
        			$.post(
        	                "${ctx}/application/expressMessage/getEnterpriseExpress.action",
        	                "companyId="+selectId,
        	                function(data){
        	                    $("#kd").val(data.name);
        	                    $("#td2").val(data.number);
        	                    $("#td3").val(data.contact);
        	                    $("#td4").val(data.phoneNumber);
        	                    $("#td5").val(data.conAddress);
        	                    $("#td6_se").val(data.reciveName);
        	                    $("#td7").val(data.recivePhoneNum);
        	                    $("#td6").val(data.reciveName);
        	                    $("#td8").val(data.reciveAddress);
        	                }
        	            );
        		}
        	}else{
        		$("#printbtn").hide();
        		$("#msg input").attr("disabled","disabled");
        		$("#okbtn").attr("onclick","");
        		$.post(
    	                "${ctx}/application/expressMessage/getEnterpriseExpress.action",
    	                "companyId="+selectId,
    	                function(data){
    	                    $("#kd").val(data.name);
    	                    $("#td2").val(data.number);
    	                    $("#td3").val(data.contact);
    	                    $("#td4").val(data.phoneNumber);
    	                    $("#td5").val(data.conAddress);
    	                    $("#td6_se").val(data.reciveName);
    	                    $("#td7").val(data.recivePhoneNum);
    	                    $("#td6").val(data.reciveName);
    	                    $("#td8").val(data.reciveAddress);
    	                }
    	            );
        	}
        	$("#td6").val("");
    		$("#td7").val("");
    		$("#td8").val("");
    		$("#td3").val("");
    		$("#td4").val("");
    		$("#td5").val("");
    		$("#td2").val("");
    		$("#td6_se").val("");
        }
		/*$("#dialog2").attr("href","");
        $("#td1").val("");
        $("#td2").val("");
        $("#td5").val("");
        $("#td6").val("");
        $("#td7").val("");
        $("#td8").val("");
		var id = document.getElementsByName('taskId');
		var check_val = [];
		var check_id1=[];
		var check_id2=[];
		    for(k in id){
		        if(id[k].checked){
		        	check_val.push(id[k].value);
		        	check_id1.push(id[k].id);
		        }
		    } 
		for(j in check_id1){
			if(check_id1[0]==check_id1[j]){
				check_id2.push(check_id1[j])
			}
		}  
		if(check_id1.length!=0){
			if(check_id2.length==check_id1.length){
				$("#dialog2").attr("href","#Modal_show3")
			}else{
				 wzj.alert('温馨提示','请选择相同分中心！');
					return false;
			}  
		}*/
		
		/*$("#recivePerson").empty();*/
			/*$.post(
					"${ctx}/sysUser/getAllUser.action",
					"branchId="+check_id1[0],
					function(data){
						if(data!=null&&data.length>0){
							for(var i=0;i<data.length;i++){
								var result="<option value="+data[i].id +">"+data[i].username+"</option>";
								$("#recivePerson").append(result);
							}
						}
					}
				);*/
		   /*if(selectId==""){
				wzj.alert('温馨提示','没有数据！');
				return false;
			}else{
				if(check_val.length==0){
					wzj.alert('温馨提示', '请勾选数据！')
					return false;
			}else{
					$.post(
						"${ctx}/application/expressMessage/getExpress.action",
						"companyId="+selectId,
						function(data){
						    $("#td2").val(data.number);
						    $("#td3").val(data.contact);
							$("#td4").val(data.phoneNumber);
							$("#td5").val(data.conAddress);
							$("#td7").val(data.recivePhoneNum);
							$("#td6").val(data.reciveName);
							$("#td8").val("123123123");
						}
					);
					/!*document.getElementById("kd").options[0].selected = true;
					document.getElementById("recivePerson").options[0].selected = true;*!/
			}
		   }*/
	}
	function updateMsg(){
		$('#okbtn').removeAttr('data-dismiss',"modal")
		if($('#msg').validationEngine('validate')){
			$('#okbtn').attr('data-dismiss',"modal")
			$("#msg").attr("action","${ctx}/application/check/updateMessage.action?comId="+selectId);
			$("#msg").attr("target","");
			$("#msg").submit();
		}
	}
	/*//寄送快递
	function msgClose(){
		var id = document.getElementsByName('taskId');
        var ids = new Array();
        for(var i = 0; i < id.length; i++){
         if(id[i].checked)
         ids.push(id[i].value);
        }
		if($('#msg').validationEngine('validate')){
			$("#okbtn").attr("data-dismiss","modal")
			$("#msg").attr("action","${ctx}/application/check/sendMessage.action?ids="+ids);
			$("#msg").attr("target","");
			$("#msg").submit();
			$('#yesbtn').attr("data-dismiss","modal");
		}
	}*/
	//打印
	function print(){
		if($('#msg').validationEngine('validate')){
			$("#msg").attr("action","${ctx}/application/comPrintSendMsg.action?companyName="+comName+"&comId="+selectId);
			$("#msg").attr("target","nm_iframe");
			$("#msg").submit();
			$("#Modal_show3").show();
		}
	}
	function misdeeds(){
		if(selectId==null){
			wzj.alert('温馨提示', '请选择一条数据！')
			return false;
		}else if(selectId==""){
			wzj.alert('温馨提示','数据不能为空！');
			return false;
		}else{
			location.href = "${ctx}/misdeeds/toMisdeeds.action?companyId="+selectId+"&branchId="+branchId+"&companyName="+companyName;
		}
	}
		var aTable={
					"bFilter":true,
				    "bDestroy" : true,
				    "bRetrieve":true,
				    "bServerSide": true,
				    "sAjaxSource":"${ctx}/recognition/getCount.action?companyId=",
				    "aLengthMenu": [
				                    [5, 15, 20, -1],
				                    [5, 15, 20, "All"]
				                ],
				    "iDisplayLength": 5,
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
		                  { "mData": "titleno","sClass": "center"},
		                  { "mData": "certappdate", "sClass": "center", "fnRender": function(data) {
			                        sReturn = (new Date(parseFloat(data.aData.certappdate))).format("yyyy-MM-dd");
			                        return sReturn;
			                  } },
		                  { "mData": "certtodate", "sClass": "center" , "fnRender": function(data) {
		                        sReturn = (new Date(parseFloat(data.aData.certtodate))).format("yyyy-MM-dd");
		                        return sReturn;
		                    } },
		                 
		              ],
			};
	function recognitionRecord(){//复认记录
		if(selectId==null){
			wzj.alert('温馨提示', '请选择一条数据！')
			return false;
		}else if(selectId==""){
			wzj.alert('温馨提示','数据不能为空！');
			return false;
		}else{
			$("#mtitle").html(companyName);
			var oSettings = $('#showRe').dataTable(aTable).fnSettings();
	        oSettings.sAjaxSource = "${ctx}/recognition/getCount.action?companyId="+selectId;//重新设置url
	        $('#showRe').dataTable(oTable).fnDraw()
			$("#dialog5").click();
			/* $.post(
				"${ctx}/recognition/getCount.action",
				"companyId="+selectId,
				function(data){
					$("#getSize").html("（共复认"+data.length+"次）")
					if(data!=null&&data.length>0){
						for(var i=0;i<data.length;i++){
							$("#showRe").append("<tr><td>"+(i+1)+"</td><td>"+data[i].titleno+"</td><td>"
								+(new Date(parseFloat(data[i].certappdate))).format("yyyy-MM-dd")+"</td><td>"
								+(new Date(parseFloat(data[i].certtodate))).format("yyyy-MM-dd")+"</td></tr>");
						}
					}
				}
			); */
		}
	}
	//复认激活
	function reActivate(){
		if(selectId==null){
			wzj.alert('温馨提示', '请选一条择数据！')
			return false;
		}else if(selectId==""){
			wzj.alert('温馨提示','数据不能为空！');
			return false;
		}else if(selectStatus==36){
			wzj.alert('温馨提示','变更业务进行中！');
			return false;
		}else if(selectStatus==37){
			wzj.alert('温馨提示','复认业务进行中！');
			return false;
		}else{
			wzj.confirm('温馨提示','您确定要复认该企业？',function(flag){
				$.post(
					"${ctx}/common/reActivate.action",
					"id="+selectId,
					function(data){
						wzj.alert('温馨提示','开始复认业务！');
						$("#menu7" , parent.document).click()
						$("#sub7" , parent.document).show()
						$("#sub64" , parent.document).hide()
						$("#frapp" , parent.document).click()
						location.href="${ctx}/recognition/toNewForms.action"
					}
				);
			});
		}
	}
	//开始变更	
	function reChange(){
		if(selectId==null){
			wzj.alert('温馨提示', '请选一条择数据！')
			return false;
		}else if(selectId==""){
			wzj.alert('温馨提示','数据不能为空！');
			return false;
		}else if(selectStatus==36){
			wzj.alert('温馨提示','变更业务进行中！');
			return false;
		}else if(selectStatus==37){
			wzj.alert('温馨提示','复认业务进行中！');
			return false;
		}else{
			wzj.confirm('温馨提示','您确定要变更该企业信息？',function(flag){
				$.post(
					"${ctx}/common/reChange.action",
					"id="+selectId,
					function(data){
						wzj.alert('温馨提示','开始变更业务！');
						$("#menu8" , parent.document).click()
						$("#sub8" , parent.document).show()
						$("#sub64" , parent.document).hide()
						$("#alterApp" , parent.document).click()
						location.href="${ctx}/alternation/toNewForms.action"
					}
				);
			});
		}
	}
	//修改企业信息
	function updateEnterpriseInfo(){
		if(selectId==null){
			wzj.alert('温馨提示', '请选一条择数据！')
			return false;
		}else if(selectId==""){
			wzj.alert('温馨提示','数据不能为空！');
			return false;
		}else{
			location.href="${ctx}/common/getEnterpriseInfo.action?id="+selectId;
		}
	}
	//检索最近三个月到期的企业
	function checkRecent(){
		$("#recent").val("recent");
	  var oSettings = $('#editable-sample').dataTable(oTable).fnSettings();
      oSettings.sAjaxSource = "${ctx}/common/recentSearch.action";//重新设置url
      $('#editable-sample').dataTable(oTable).fnDraw()
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
//       oSettings.sAjaxSource = "${ctx}/common/checkArea.action?address="+address+"&status=未注销";//重新设置url
//       $('#editable-sample').dataTable(oTable).fnDraw()
//     }
    //导出Excel
    function downloadExcel(){
    	if($("#recent").val()==""){
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
    		var status="没注销";
    		var param="branchId="+branchId+"&term1="+term1+"&appStartTime="+appStartTime+"&appEndTime="+appEndTime+"&term2="+term2+"&certStartTime="+certStartTime+"&certEndTime="+certEndTime+"&term3="+term3+"&expireStartTime="+expireStartTime+"&expireEndTime="+expireEndTime+"&term4="+term4+"&type="+type+"&term5="+term5+"&enterpriseName="+enterpriseName+"&status="+status+"&address="+address+"&term6="+term6;
    		var sSearch=$(".form-control.medium").val();
    		window.open("${ctx}/common/exportExcel.action?"+param+"&sSearch="+sSearch);
    	}else{
    		var sSearch=$(".form-control.medium").val();
    		window.open("${ctx}/common/exportExcelByRecent.action?sSearch="+sSearch);
    	}
		
    }
</script>
</head>
<body>
<input type="hidden" value="${activeUser.ramusCenter}" id="userCenter">
<input type="hidden" value="" id="recent">
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                        	<div class="btn-group  nopaddingleft" style="float: left;">
                             <button onclick="view()" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	  查看 <i class="fa fa-eye"></i>
                              </button>
                           </div>
                            <shiro:hasRole name="中心管理人员">
                           <div class="btn-group  left" style="float: left;">
                             <button onclick="updateEnterpriseInfo()" class="btn btn-info" data-toggle='modal' href='' id="">
                            	  修改企业信息 <i class="fa fa-edit"></i>
                              </button>
                           </div>
                           </shiro:hasRole>
                          <%--  </shiro:hasAnyRoles> --%>
                            <div class="btn-group  left" style="float: left;">
                             <button onclick="sendMsg()" class="btn btn-info" data-toggle='modal' href='' id="dialog2">
                            	  寄送信息 <i class="fa fa-cloud-download"></i>
                              </button>
                           </div>
                           <div class="btn-group  left" style="float: left;">
                             <button onclick="misdeeds()" class="btn btn-info">
                            	  质量记录 <i class="fa fa-bookmark"></i>
                              </button>
                           </div>
                           <div class="btn-group  left" style="float: left;">
                             <button onclick="recognitionRecord()" class="btn btn-info">
                            	  复认记录 <i class="fa fa-bookmark"></i>
                              </button>
                              <a href="#Modal_show5" style="display:none" data-toggle='modal' id="dialog5"></a>
                           </div>	
							<div id="reActivate" class="btn-group" style="float: left;">
                             <button onclick="reActivate()" class="btn btn-success">
                            	 复认申请<i class="fa fa-wrench"></i>
                              </button>
                           </div>
                           <div id="reChange" class="btn-group" style="float: left;">
                             <button onclick="reChange()" class="btn btn-success">
                            	 变更申请<i class="fa fa-wrench"></i>
                              </button>
                           </div>
                           <shiro:hasRole name="中心管理人员">
                           <div id="cancel" class="btn-group  left" style="float: left;">
                             <button onclick="cancel()" class="btn btn-danger">
                            	 注销 <i class="fa fa-trash-o"></i>
                              </button>
                           </div>
                           </shiro:hasRole>
                           <div class="btn-group  left" style="float: left;">
                             <button onclick="checkRecent()" class="btn btn-info" data-toggle='modal' href='' id="dialog">
                            	 近期到期企业<i class="fa fa-eye"></i>
                              </button>
                           </div>
                        </div>
                        <br/>
         			<div class="clearfix" >
<!--          			<button class="btn btn-primary " onclick="checkArea()"> -->
<!--                           	区域检索 <i class="fa fa-search"></i> -->
<!--                      </button> -->
                        <button class="btn btn-primary " data-toggle='modal' href='#Modal_show4'>
                          	高级检索 <i class="fa fa-search"></i>
                          </button>
                           <button class="btn btn-primary " data-toggle='' onclick="downloadExcel()">
                          	导出Excel<i class="fa fa-download"></i>
                          </button>
                          <div style="float: left;margin-right:5px">
<!--                     	<select id="province" onchange="changePro()" name="province"><option selected="true" disabled="true">全部</option></select> -->
<!--                     	<select id="city" onchange="changeCit()" name="city"></select> -->
<!--                     	<select id="area" onchange="changeAre()" name="area"></select> -->
                   	</div>
                   	                           <shiro:hasAnyRoles name="中心审核人员,中心复核人员,中心管理人员">
                           <div class="btn-group pull-right">
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
                              </ul>
                           </div>
                           </shiro:hasAnyRoles>		
                    </div>
<!--                    	<input type="hidden" id="address" name="address" value="" class="form-control" style="float: left;width: 50%"> -->
                   	
					<input type="hidden" value="${branchId}" id="branchId">   
                      <input type="hidden" value="${activeUser.usercode}" id="usercode">  
                        <div class="space15"></div>
                       <!--  <table class="table table-bordered" style="width:460%" id="editable-sample"> -->
                        <table class="table table-bordered" style="width:100%" id="editable-sample">
                           <thead>
                              <tr>
                               <!--  <th class="" style="display:none;">任务编号</th>
                                 <th class="" style="display:none;"></th> --> 
                                 <th class="">选项</th>
                                 <th class="" style="display: none;">编号</th>
                                 <th class="" style="display: none;">真状态</th>
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
                                 <th class="" style="display:none">分支机构编号</th>
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
          <div id="Modal_show3" class="modal fade">
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
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">快递名称</label>
            		<div class="col-sm-4">
						<input class="form-control" id="kd" value="EMS" name="name" style="width: 100%" readonly/>
                	</div>
                	<input class="form-control" id="td1" value="" style="width: 100%" type="hidden"/>
              	<label class="col-sm-2 labels nopaddingright" style="text-align: right;">订单号<b class="red">*</b></label>
                	<div class="col-sm-4">
                  		<input class="form-control validate[required]" id="td2" value="" name="number" style="width: 100%" disabled/>
                	</div>
				</div>
              <div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">联系人</label>
            		<div class="col-sm-4">
            			<input class="form-control" id="td3" value="" name="contact" style="width: 100%" disabled/>
            		</div>
              	<label class="col-sm-2 labels nopaddingright" style="text-align: right;">联系人电话</label>
                	<div class="col-sm-4">
                  		<input class="form-control" id="td4" value="" name="phoneNumber" style="width: 100%" disabled/>
                	</div>
				</div>
              <div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">联系人地址</label>
            		<div class="col-sm-10">
            			<input class="form-control" id="td5" value="" name="conAddress" style="width: 100%" disabled/>
            		</div>
				</div>
				<div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人</label>
            		<div class="col-sm-4">
            			<%--<select id="recivePerson" onchange="changeUser()" style="margin: 5px 0" class="col-sm-12" disabled></select>--%>
                            <input class="form-control" id="td6_se" value="" style="width: 100%" disabled/>
            		</div>
				</div>
				<div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人电话<b class="red">*</b></label>
            		<div class="col-sm-4">
            			<input class="form-control validate[required,custom[telephone]]" id="td7" value="" name="recivePhoneNum" style="width: 100%" disabled/>
            		</div>
              	<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人姓名<b class="red">*</b></label>
                	<div class="col-sm-4">
                  		<input class="form-control validate[required]" id="td6" value="" name="reciveName" style="width: 100%" disabled/>
                	</div>
				</div>
				<div class="row" style="width: 100%; margin: 5px 0px;">
				<label class="col-sm-2 labels nopaddingright" style="text-align: right;">收件人地址<b class="red">*</b></label>
                	<div class="col-sm-10">
                  		<input class="form-control validate[required]" id="td8" value="" name="reciveAddress" style="width: 100%" disabled/>
                	</div>
				</div>
				</form>
	         </div>
	         <div class="modal-footer" style="margin-top:0px;">
	         	<button class="btn btn-success" type="button" onclick="print()" id="printbtn" style="display:none">
                 	打印
                </button>
                <button class="btn btn-success" type="button" id="okbtn" onclick="">
                 	确定
                </button>
              </div>
              </div>
            </div>
          </div>
          <div id="Modal_show4" class="modal fade">
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
					<div class="col-sm-7"><input type="text" class="form_datetime form-control sdate" id="appStartDate"><input type="text" class="form_datetime form-control sdate validate[custom[date]]" id="appEndDate"></div>
					<div class="btn-group"></div>
					<div class="col-sm-2 nopaddingleft"><select class="form-control" id="term1" style="width: 120%"><option value="and">并且</option><option value="or">或者</option><option value="no">不含</option></select></div>
					</div>
                      <div class="clearfix searx sear" id="select2">
					<div class="col-sm-1 nopaddingleft">
                    <input type="checkbox" id="add3">
                    </div>
					<div class="col-sm-2 nopaddingleft">发证日期：</div>
					<div class="col-sm-7"><input type="text" class="form_datetime form-control fdate" id="certStartDate"><input type="text" class="form_datetime form-control fdate validate[custom[date]]" id="certEndDate"></div>
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
          <div id="Modal_show5" class="modal fade">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
                </button>
                <h4 class="modal-title" id="mtitle"></h4>
              </div>
              <div class="modal-body">
                <table class="table table-bordered" style="width:100%" id="showRe">
					<thead><tr><td>序号</td><td>流水号</td><td>复认时间</td><td>到期时间</td></tr></thead>
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
    <script src="${ctx}/assets/fuelux/js/tree.min.js"></script><!-- TREE JS  -->
    <!-- EDITABLE TABLE FUNCTION  -->
    <script>        
    	$(function(){
			setTimeout(function(){ 
				$('.odd').attr('ondblclick','view()')
				$('.even').attr('ondblclick','view()')
			}, 100);
			  //$("#appStartDate").on('click', function () { WdatePicker({el: "appStartDate"});});

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
        $.validationEngineLanguage.allRules.date = {  
	    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
	    	      "alertText": "* 请输入正确的日期格式"  
	   	}; 
   </script>
  <!-- END JS --> 
</body>
</html>