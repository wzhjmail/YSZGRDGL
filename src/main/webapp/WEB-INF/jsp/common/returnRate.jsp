<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退回率统计</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet">
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-select.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/assets/fuelux/css/tree-style.css" rel="stylesheet">
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script>
	<script src="${ctx}/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
	<script src="${ctx}/js/bootstrap-select.min.js" ></script><!-- BOOTSTRAP JS  -->
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
	<style type="text/css">
		.btn-group{
			padding-left: 0px
		}
	</style>
<script type="text/javascript">

console.log(fix(1,13))
var color=['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074',
           '#546570', '#c4ccd3']
var branchName=[]
var yw=['新办','新办退回','复认','复认退回','变更','变更退回'] 
var number=[]
$(function(){
	$('#from').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#to').datepicker({
		format:'yyyy-mm-dd',
	});
	$.ajax({
		type: 'post',
		url: '${ctx}/count/getCount.action',
		cache: false,
		success: function (data) {
			$('#fzx').empty()
			
				if($("#branchId").val()=="0000"){
					$.each(data.obj,function(i,item){
						if(item.divisioncode!="0000"){
							$('#fzx').append('<option value="'+item.divisioncode+'">'+item.divisionname+'</option>')
						}
					})
				   	$('.selectpicker').selectpicker({
		       			'selectedText': 'cat'
		       		});
				}else{
					$.each(data.obj,function(i,item){
						if(item.divisioncode==$("#branchId").val()){
							code=item.divisioncode;
							name=item.divisionname;
							$('#fzx').append('<option value="'+code+'" selected="selected">'+name+'</option>')
						}
					})
				}
			

			getFzx();
		}
	});
// 	getFirst();
});
function getFirst(){
	var myChart = echarts.init(document.getElementById('main')); 
	myChart.showLoading({
		text:'加载中',
	});
	$.ajax({
		type: 'post',
		url: '${ctx}/count/getReturnRate.action',
		cache: false,
		success: function (data) {
			branchName=data.names
			myChart.setOption(setData(branchName,yw,data));//载入设置
			window.onresize=myChart.resize;
			myChart.hideLoading();//取消loading
		}
	});
}
function exportRecord(){
	var branchId= $("#fzx option:selected").val();
	var myDate = new Date();
	var year=myDate.getFullYear(); 
	var d1=$('#from').val();
	var d2=$('#to').val();
	var timestamp1 = Date.parse(d1);
	var timestamp2 = Date.parse(d2);
	if(branchId=="-请选择-"){//没选分中心
		if(d1==""&&d2==""){//没选时间段
			window.open("${ctx}/count/exportAll.action");
		}else{//选择了时间段
			if(timestamp2-timestamp1>=0){
				window.open("${ctx}/count/exportByTime.action?d1="+d1+"&d2="+d2);
			}else{
				wzj.alert('温馨提示','日期顺序有误!');
			}
		}
	}else{//选择了分中心
		if(d1==""&&d2==""){//没选时间段
			window.open("${ctx}/count/exportByBranch.action?branchId="+branchId+"&year="+year);
		}else{//选择了时间段
			if(timestamp2-timestamp1>=0){
		window.open("${ctx}/count/exportByTwo.action?branchId="+branchId+"&d1="+d1+"&d2="+d2);
			}else{
				wzj.alert('温馨提示','日期顺序有误!');
			}
			}
	}
}

function setData(branchName,yw,data){
	getOption(branchName)
	option.series.push({
          name:"新办",
          type:'bar',
          data:data.xb,
      })
	option.series.push({
          name:"新办退回",
          type:'bar',
          data:data.xbReturn,
      })
	option.series.push({
          name:"复认",
          type:'bar',
          data:data.fr,
      })
	option.series.push({
          name:"复认退回",
          type:'bar',
          data:data.frReturn,
      })
	option.series.push({
          name:"变更",
          type:'bar',
          data:data.bg,
      })
	option.series.push({
          name:"变更退回",
          type:'bar',
          data:data.bgReturn,
      })
	return option;
}
function setSome(branchName,yw,data){
	if($("#ywType option:selected").val()=="XB"){
		datas=data.xb
		datas1=data.xbReturn
	}else if($("#ywType option:selected").val()=="FR"){
		datas=data.fr
		datas1=data.frReturn
	}else if($("#ywType option:selected").val()=="BG"){
		datas=data.bg
		datas1=data.bgReturn
	}
	getOption(branchName)
	option.series.push({
          name:yw[0],
          type:'bar',
          stack: 'stack0',
          data:datas,
      })
	option.series.push({
          name:yw[1],
          type:'bar',
          stack: 'stack0',
          data:datas1,
      })
	return option;
}

function getOption(branchName){
	option = {
	   		tooltip : {
	        	trigger: 'axis',
	        	formatter: function(datas){
					var res = datas[0].name + '<br/>', val;
					for(var i = 1; i < datas.length; i+=2) {
						var count=datas[i-1].value;
		        		if(count==0){
		        			count=1;
		        		}
						res +='<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'+ color[i-1] +'"></span>'+ datas[i-1].seriesName + '：' + datas[i-1].value + '<br/>'+
						'<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'+ color[i] +'"></span>'+datas[i].seriesName + '：' + datas[i].value + '<br/>'+
						'<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:yellow"></span>'+datas[i].seriesName + '率：' + fix(datas[i].value,count) + '%<br/>';
					}
					return res;
	        	}
	    	},
		    legend: {
		        data:[],
		    },
		    toolbox: {
			    right:'4%',
		        show : true,
		        feature : {
		        	mark : {show: true},
 			        dataZoom : {show: true},
		            dataView : {show: true,
		            	readOnly: true,
 			           	optionToContent:function(opt) {
	 			               var axisData = branchName;
	 			               var table ='<table id="test" class="table-bordered table-striped" style="width:90%;text-align:center;margin-left:8%">',
	 			               table = table + '<tbody><tr><td></td><td>新办</td><td>新办退回</td><td>复认</td><td>复认退回</td><td>变更</td><td>变更退回</td></tr>';
	 			                for (var i = 0, l = axisData.length; i < l; i++) {
	 			                    table += '<tr>' + '<td>' + axisData[i] + '</td>' + '<td>' + opt.series[0].data[i] + '</td>'+ '<td>' + opt.series[1].data[i] + '</td>'+ 
	 			                    		 '<td>' + opt.series[2].data[i] + '</td>' + '<td>' + opt.series[3].data[i] + '</td>'+
	 			                    		 '<td>' + opt.series[4].data[i] + '</td>' + '<td>' + opt.series[5].data[i] + '</td>'  + '</tr>';
	 			                    }
	 			                      table += '</tbody>';
	 			                      return table;
	 			                }},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        } 
		    },
	    	calculable : true,
	    	grid: {
  	            	bottom: '20%',
  	         	},
		    xAxis : [
		        {
		            type : 'category',
		            data : branchName,
         			axisLabel:{
						interval:0,
						formatter:function(a){
							return a.split("").join("\n")
						},
                 	}
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [],
		};
}

function fix(num1,num2){
	var str = (num1/num2)*100 + ""
	if(str.split(".").length==1){
		return str
	}else{
		if(str.split(".")[1].length<=2){
			return str.split(".")[0]+"."+str.split(".")[1]
		}else{
			return str.split(".")[0]+"."+str.split(".")[1].substr(0,2)
		}
	}
}

function getRandom(branchName){
	var number=[]
	for(var i=0;i<branchName.length;i++){
		number.push(Math.floor(Math.random() * 10))
	}
	return number;
}

function getFzx(){
	var fzx=$("#fzx option:selected");
	var fzxid=$("#fzx option:selected").val();
	var ywType=$("#ywType option:selected");
	var d1 = $("#from").val()
	var d2 = $("#to").val()
	if(fzx.length!=0){
		fzxid=[]
		branchName=[]
		for(var i=0;i<fzx.length;i++){
			branchName.push(fzx[i].text);
			fzxid.push(fzx[i].value);
		}
	}
	if($("#ywType option:selected").text()=='-请选择-'){
		yw=['新办','新办退回','复认','复认退回','变更','变更退回']
	}else{
		yw=[$("#ywType option:selected").text(),$("#ywType option:selected").text()+'退回']
	}
	if(d1!=""&&d2!=""&&fzx.length!=0&&yw.length!=6){
		var timestamp1 = Date.parse(d1);
		var timestamp2 = Date.parse(d2);
		if(timestamp2-timestamp1>=0){
			var myChart = echarts.init(document.getElementById('main')); 
			myChart.showLoading({
				text:'加载中',
			});
			$.post(
					"${ctx}/count/getByTBT.action",
					"d1="+d1+"&d2="+d2+"&branchIds="+fzxid.toString()+"&type="+ywType[0].value,
					function(data){
						myChart.setOption(setData(branchName,yw,data));//载入设置
						window.onresize=myChart.resize;
						myChart.hideLoading();//取消loading
					}
			);
		}
	}else if(d1==""&&d2==""&&fzx.length==0&&yw.length==6){
		var myChart = echarts.init(document.getElementById('main')); 
		myChart.showLoading({
			text:'加载中',
		});
		$.ajax({
			type: 'post',
			url: '${ctx}/count/getReturnRate.action',
			cache: false,
			success: function (data) {
				branchName=data.names
				myChart.setOption(setData(branchName,yw,data));//载入设置
				window.onresize=myChart.resize;
				myChart.hideLoading();//取消loading
			}
		});
	}else{
		if(d1!=""&&d2!=""){//按时间段查所有分中心
			var timestamp1 = Date.parse(d1);
			var timestamp2 = Date.parse(d2);
			if(timestamp2-timestamp1>=0){
				var myChart = echarts.init(document.getElementById('main')); 
				myChart.showLoading({
					text:'加载中',
				});
				if(fzx.length!=0){
					$.post(
						"${ctx}/count/getByTimeAndBranch.action",
						"d1="+d1+"&d2="+d2+"&branchIds="+fzxid.toString(),
						function(data){
							myChart.setOption(setData(branchName,yw,data));//载入设置
							window.onresize=myChart.resize;
							myChart.hideLoading();//取消loading
						}
					);
				}else if(yw.length!=6){
					$.post(
						"${ctx}/count/getByTimeAndtype.action",
						"d1="+d1+"&d2="+d2+"&type="+ywType[0].value,
						function(data){
							branchName=data.names
							myChart.setOption(setSome(branchName,yw,data));//载入设置
							window.onresize=myChart.resize;
							myChart.hideLoading();//取消loading
						}
					);
				}else{
					$.post(
						"${ctx}/count/getReturnRateByTime.action",
						"d1="+d1+"&d2="+d2,
						function(data){
							branchName=data.names
							myChart.setOption(setData(branchName,yw,data));//载入设置
							window.onresize=myChart.resize;
							myChart.hideLoading();//取消loading
						}
					);
				}
			}else{
				wzj.alert('温馨提示','日期顺序有误!');
			}
		}else if(fzx.length!=0){
			var myChart = echarts.init(document.getElementById('main')); 
			myChart.showLoading({
				text:'加载中',
			});
			if(yw.length!=6){
				$.post(
					"${ctx}/count/getByTypeAndBranch.action",
					"type="+ywType[0].value+"&branchIds="+fzxid.toString(),
					function(data){
						myChart.setOption(setSome(branchName,yw,data));//载入设置
						window.onresize=myChart.resize;
						myChart.hideLoading();//取消loading
					}
				);
			}else{
				$.post(
						"${ctx}/count/getReturnRateByBranch.action",
						"branchIds="+fzxid.toString(),
						function(data){
							myChart.setOption(setData(branchName,yw,data));//载入设置
							window.onresize=myChart.resize;
							myChart.hideLoading();//取消loading
						}
				);
			}
		}else{
			var myChart = echarts.init(document.getElementById('main')); 
			myChart.showLoading({
				text:'加载中',
			});
			$.post(
				"${ctx}/count/getReturnRateByType.action",
				"type="+ywType[0].value,
				function(data){
					myChart.setOption(setSome(branchName,yw,data));//载入设置
					window.onresize=myChart.resize;
					myChart.hideLoading();//取消loading
				}
			);
		}
	}
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                        	<div class="col-sm-4">
								<div class="input-group input-large" data-date="2013-07-13" data-date-format="yyyy/mm/dd">
								  <span class="input-group-addon">从</span>
								  <input type="text" class="form_datetime form-control validate[custom[date]]" id="from">
								  <span class="input-group-addon">至</span>
								  <input type="text" class="form_datetime form-control validate[custom[date]]" id="to">
								</div>		   
						  	</div>
<!--                            <div class="btn-group" style="float: left;"> -->
<!--                               <button class="btn btn-primary" onclick="getTime();"> -->
<!--                               	查找 -->
<!--                               </button> -->
<!--                            </div> -->
                        	<div class="col-sm-3">
                        	<div class="col-sm-5 text-right" style="padding-top: 7px">分中心：</div>
                        	<div class="col-sm-7">
								<select class="selectpicker bla bla bli" id="fzx" multiple data-live-search="true" size="1" style="width: 150px">
									<option>-请选择-</option>
								</select>
						  	</div>
						  	</div>
                        	<div class="col-sm-4">
                        	<div class="col-sm-5 text-right" style="padding-top: 7px">业务类型：</div>
                        	<div class="col-sm-7">
                        		<select class="form-control" id="ywType">
									<option>-请选择-</option>
									<option value="XB">新办</option>
									<option value="FR">复认</option>
									<option value="BG">变更</option>
								</select></div>
						  	</div>
                           <div class="btn-group">
                              <button class="btn btn-primary" onclick="getFzx();">
                              	查找 	<i class="fa fa-search"></i> 
                              </button>
                           </div>
                           
					<!-- 	  <div class="btn-group col-md-1">
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
                           
                            <!--  <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        <!--    <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div> -->
                        </div>
                        <input type="hidden" value="${branchId}" id="branchId">
                        <div class="space15"></div>
						    <div id="main" style="height:600px;margin-top: 2%"></div>
                     </div>
                  </div>
               </section>
            <script type="text/javascript">
		        $.validationEngineLanguage.allRules.date = {  
			   	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
			   	      "alertText": "* 请输入正确的日期格式"  
			  	}; 
            </script>
            <script src="${ctx}/js/echarts.js"></script>
	<!-- BEGIN JS -->
    <!-- EDITABLE TABLE FUNCTION  -->
  	<!-- END JS --> 
</body>
</html>