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
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/assets/fuelux/css/tree-style.css" rel="stylesheet">
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<script src="${ctx}/js/jquery-1.8.3.min.js" ></script><!-- BASIC JS LIABRARY 1.8.3 -->
	<script src="${ctx}/js/style_self.js"></script>
	<script src="${ctx}/js/echarts.js"></script>
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
	getFirst();
});
function getFirst(){
	var myChart = echarts.init(document.getElementById('main')); 
	myChart.showLoading({
		text:'加载中',
	});
	$.ajax({
		type: 'post',
		url: '${ctx}/count/getCount.action',
		cache: false,
		success: function (data) {
			var code;
			var name;
			console.log(data)
			branchName=data.names
			$.each(data.obj,function(i,item){
				$('#fzx').append('<option value="'+item.divisioncode+'">'+item.divisionname+'</option>')
				/*if($("#branchId").val()=="0000"){
					$('#fzx').append('<option value="'+item.divisioncode+'">'+item.divisionname+'</option>')
				}else{
					if(item.divisioncode==$("#branchId").val()){
						code=item.divisioncode;
						name=item.divisionname;
						$('#fzx').append('<option value="'+code+'">'+name+'</option>')
					}
				})*/
			})
		   	$('.selectpicker').selectpicker({
       			'selectedText': 'cat'
       		});
       		console.log(branchName)
       		console.log(yw)
			myChart.setOption(setData(branchName,yw));//载入设置
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

function setData(branchName,yw){
	option = {
	   		tooltip : {
	        	trigger: 'axis',
	        	formatter: function(datas){
					var res = datas[0].name + '<br/>', val;
					for(var i = 1; i < datas.length; i+=2) {
						res +='<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'+ color[i-1] +'"></span>'+ datas[i-1].seriesName + '：' + datas[i-1].value + '<br/>'+
						'<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'+ color[i] +'"></span>'+datas[i].seriesName + '：' + datas[i].value + '<br/>'+
						'<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:yellow"></span>'+datas[i].seriesName + '率：' + ((datas[i].value/datas[i-1].value).toFixed(4))*100 + '%<br/>';
					}
					return res;
	        	}
	    	},
		    legend: {
		        data:[],
		    },
		    toolbox: {
		        show : true,
		        feature : {
		        	mark : {show: true},
 			        dataZoom : {show: true},
		            dataView : {show: true,},
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

 	 	for(var i=0;i<yw.length;i++){
 	 	 	j=parseInt(i/2)
 			option.series.push({
	            name:yw[i],
	            type:'bar',
	            stack: 'stack'+j,
	            data:getRandom(branchName),
	        },)
 	 	 }
	return option;
}

function getRandom(branchName){
	var number=[]
	for(var i=0;i<branchName.length;i++){
		number.push(Math.floor(Math.random() * 10))
	}
	return number;
}

function getFzx(){	
	var sel=$("#fzx option:selected");
	console.log(sel)
	if(sel.length!=0){
		branchName=[]
		for(var i=0;i<sel.length;i++){
			branchName.push(sel[i].text);
		}
	}
	console.log(branchName)	
	if($("#ywType option:selected").text()=='-请选择-'){
		yw=['新办','新办退回','复认','复认退回','变更','变更退回']
	}else{
		yw=[$("#ywType option:selected").text(),$("#ywType option:selected").text()+'退回']
	}
	console.log(yw)	
	var myChart = echarts.init(document.getElementById('main')); 
	myChart.showLoading({
		text:'加载中',
	});
	/*var branchId= $("#fzx option:selected").val();
	var branchName= $("#fzx option:selected").text();
	var myDate = new Date();
	var year=myDate.getFullYear(); 
	var d1=$('#from').val();
	var d2=$('#to').val();
	if(branchId=="-请选择-"){
		if(d1!=""&&d2!=""){//按时间段查所有分中心
			var timestamp1 = Date.parse(d1);
			var timestamp2 = Date.parse(d2);
			if(timestamp2-timestamp1>=0){
				$.post(
						"${ctx}/count/getSearch.action",
						"d1="+d1+"&d2="+d2,
						function(data){
							myChart.setOption(setData(data,"全部"));//载入设置
							window.onresize=myChart.resize;
							myChart.hideLoading();//取消loading
						}
				);
			}else{
				wzj.alert('温馨提示','日期顺序有误!');
			}
		}else{//默认查询所有
			$.post(
					"${ctx}/count/getCount.action",
					function(data){
						myChart.setOption(setData(data,"总计"));//载入设置
						window.onresize=myChart.resize;
						myChart.hideLoading();//取消loading
					}
			);
		}
	}else{
		if(d1==""&&d2==""){//只按照分中心查找本年
			$.post(
			"${ctx}/count/search.action",
			"year="+year+"&branchId="+branchId,
			function(data){
				myChart.setOption(setData(data,branchName));//载入设置
				window.onresize=myChart.resize;
				myChart.hideLoading();//取消loading
			}
			);
		}else{//在规定时间段查找选中分中心的数据
			var timestamp1 = Date.parse(d1);
			var timestamp2 = Date.parse(d2);
			if(timestamp2-timestamp1>=0){
			$.post(
					"${ctx}/count/searchByTwo.action",
					"d1="+d1+"&d2="+d2+"&branchId="+branchId,
					function(data){
						myChart.setOption(setData(data,branchName));//载入设置
						window.onresize=myChart.resize;
						myChart.hideLoading();//取消loading
					}
					);
				}else{
					wzj.alert('温馨提示','日期顺序有误!');
				}
			}
		}*/
		myChart.setOption(setData(branchName,yw));//载入设置
		window.onresize=myChart.resize;
		myChart.hideLoading();//取消loading
}
</script>
</head>
<body>
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table ">
                        <div class="clearfix">
                        	<div class="col-sm-3">
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
								<select class="selectpicker bla bla bli" id="fzx" multiple data-live-search="true"></select>
						  	</div>
						  	</div>
                        	<div class="col-sm-3">
                        	<div class="col-sm-5 text-right" style="padding-top: 7px">业务类型：</div>
                        	<div class="col-sm-7">
                        		<select class="form-control" id="ywType">
									<option>-请选择-</option>
									<option>新办</option>
									<option>复认</option>
									<option>变更</option>
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
	<!-- BEGIN JS -->
    <!-- EDITABLE TABLE FUNCTION  -->
  	<!-- END JS --> 
  <script>
        $.validationEngineLanguage.allRules.date = {  
	    	      "regex": /^(19[0-9]{2}|20[0-9]{2})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/,
	    	      "alertText": "* 请输入正确的日期格式"  
	   	}; 
   </script>
</body>
</html>