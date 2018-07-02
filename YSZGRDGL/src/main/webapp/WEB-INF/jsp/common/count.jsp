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
	<script src="${ctx}/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
	<script src="${ctx}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- 日期显示 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/bootstrap-datepicker/css/datepicker.css"><!-- BOOTSTRAP DATEPICKER PLUGIN CSS -->
	<script src="${ctx}/js/bootstrap-select.min.js" ></script><!-- BOOTSTRAP JS  -->
	<link href="${ctx}/css/bootstrap-select.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
<script type="text/javascript">
$(function(){
	$('#from').datepicker({
		format:'yyyy-mm-dd',
	});
	$('#to').datepicker({
		format:'yyyy-mm-dd',
	});
// 	getFirst();
	$.ajax({
		type: 'post',
		url: '${ctx}/count/getCount.action',
		cache: false,
		success: function (data) {
			$('#fzx').empty()
				console.log(data)
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
					fzxid=[];
					$.each(data.obj,function(i,item){
						if(item.divisioncode==$("#branchId").val()){
							code=item.divisioncode;
							name=item.divisionname;
							$('#fzx').append('<option value="'+code+'" selected="selected">'+name+'</option>')
							fzxid.push(code);
							var myDate = new Date();
							var year=myDate.getFullYear(); 
							//加载分中心页面
							$.post(
							"${ctx}/count/search.action",
							"year="+year+"&branchId="+fzxid.toString(),
							function(data){
								var length = Object.getOwnPropertyNames(data.returnData).length
								myChart.setOption(setmutiData(data.returnData,length));//载入设置
								window.onresize=myChart.resize;
								myChart.hideLoading();//取消loading
							}
							);
						}
					})
				}

			var myChart = echarts.init(document.getElementById('main')); 
			myChart.showLoading({
				text:'加载中',
			});
			sort(data)
			myChart.setOption(setData(sort(data),"总计"));//载入设置
			window.onresize=myChart.resize;
			myChart.hideLoading();//取消loading
		}
	});
});
var bg=[]
var xb=[]
var fr=[]
var names=[]
var datas=[]
function sort(data){
	var datax = data
	if(data.names.length!=0){
		datas=[]
		var max = data.xb[0] + data.fr[0]
		var index = 0
		for(var i=0;i<data.names.length;i++){
			num = data.xb[i+1] + data.fr[i+1]
			if(max<num){
				max=num
				index = i+1
			}
		}
		xb.push(data.xb[index])
		bg.push(data.bg[index])
		fr.push(data.fr[index])
		names.push(data.names[index])
		datax.xb.splice(index,1)
		datax.bg.splice(index,1)
		datax.fr.splice(index,1)
		datax.names.splice(index,1)
		sort(datax)
	}else{
		datas.push({"xb":xb,"fr":fr,"bg":bg,"names":names})
		bg=[]
		xb=[]
		fr=[]
		names=[]
		return datas[0]
	}
}

function exportRecord(){
// 	var branchId= $("#fzx option:selected").val();
	var fzx=$("#fzx option:selected");
	if(fzx.length!=0){
		fzxid=[]
		branchName=[]
		for(var i=0;i<fzx.length;i++){
			branchName.push(fzx[i].text);
			fzxid.push(fzx[i].value);
		}
	}
	var myDate = new Date();
	var year=myDate.getFullYear(); 
	var d1=$('#from').val();
	var d2=$('#to').val();
	var timestamp1 = Date.parse(d1);
	var timestamp2 = Date.parse(d2);
	if(fzx.length==0){//没选分中心
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
			window.open("${ctx}/count/exportByBranch.action?branchId="+fzxid.toString()+"&year="+year);
		}else{//选择了时间段
			if(timestamp2-timestamp1>=0){
		window.open("${ctx}/count/exportByTwo.action?branchId="+fzxid.toString()+"&d1="+d1+"&d2="+d2);
			}else{
				wzj.alert('温馨提示','日期顺序有误!');
			}
			}
	}
}

function setData(data,branchName){
	console.log(data)
    var xb=0
    var bg=0
    var fr=0
	for(var i=0;i<data.xb.length;i++){
		xb=xb+data.xb[i];
	}
    for(var j=0;j<data.bg.length;j++){
    	bg=bg+data.bg[j];
    }
    for(var k=0;k<data.fr.length;k++){
    	fr=fr+data.fr[k]
    }
	option = {
			title : {
				text: branchName,
	            left:'8%',
	            subtext:"新办："+xb+"，"+
	            		"变更："+bg+"，"+
	            		"复认："+fr,
	            subtextStyle:{
		            color:'#000'
		            }
       			},
	   		tooltip : {
	        	trigger: 'axis',	            
	        	formatter:function(params){
		               var res = params[0].name
		               for(var i=0;i<params.length;i++){
		            	   	if(params[i].seriesName=="变更"){
		            	   		
				           	}else{
				           		res+='<br/><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'+params[i].color+'"></span>'+params[i].seriesName+':'+params[i].data+''
					        }
			           }
		               return res
			        }
	    	},
		    legend: {
		        data:['新办','复认','变更'],
                selected: {  
                    '新办': false,  
                    '复认': false,  
                    '变更': false, 
                }  
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
	    	 			               var axisData = data.names;
	    	 			               var table ='<table id="test" class="table-bordered table-striped" style="width:90%;text-align:center;margin-left:8%">',
	    	 			               table = table + '<tbody><tr><td></td><td>新办</td><td>复认</td><td>全部（含新办与复认）</td><td>变更</td></tr>';
	    	 			                for (var i = 0, l = axisData.length; i < l; i++) {
	    	 			                    table += '<tr>' + '<td>' + axisData[i] + '</td>' + '<td>' + data.xb[i] + '</td>' + '<td>' + data.fr[i] + '</td>'+ '<td>' + (data.xb[i] + data.fr[i]) + '</td>'+'<td>' + data.bg[i] + '</td>'+ '</tr>';
	    	 			                    }
	    	 			                      table += '</tbody>';
	    	 			                      return table;
	    	 			                }
			             		},
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
		            data : data.names,
         			axisLabel:{
						interval:0,
						formatter:function(a){
							if(a.substr(a.length-1,1)=="月"){
								return a
							}else{
								return a.split("").join("\n")
							}
						},
                 	}
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'新办',
		            type:'bar',
		            stack: 'stack1',
		            data:data.xb,
		        },
		        {
		            name:'变更',
		            type:'bar',
		            stack: 'stack1',
		            data:data.bg,
		        },
		        {
		            name:'复认',
		            type:'bar',
		            stack: 'stack1',
		            data:data.fr,
		        },
		    ]
		};
	switch($('#ywType').val()){
		case "XB":
			option.legend.selected["新办"]=true
			break;
		case "FR":
			option.legend.selected["复认"]=true
			break;
		case "BG":
			option.legend.selected["变更"]=true
			break;
		default:
			option.legend.selected["新办"]=true
			option.legend.selected["复认"]=true
			option.legend.selected["变更"]=true
			break;
	}
	return option;
}
function setmutiData(data,length){
	console.log(data)
	console.log(length)
	option = {
			baseOption: {
		        timeline: {
			        bottom:'0%',
		            axisType: 'category',
		            autoPlay: false,
		            playInterval: 5000,
		            data: [],
		            label: {
		                formatter : '{value}'
		            }
		        },
		   		tooltip : {
		        	trigger: 'axis',	            
		        	formatter:function(params){
			        	console.log(params)
			               var res = params.name
			               return res
				        }
		    	},
			    legend: {
			        data:['新办','复认','变更'],
	                selected: {  
	                    '新办': false,  
	                    '复认': false,  
	                    '变更': false, 
	                }  
			    },
		    	calculable : true,
		    	grid: {
	  	            	bottom: '20%',
	  	         	},

			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
		    },
		    options: []
		};
	for(var i=0;i<length;i++){
	    var xb=0
	    var bg=0
	    var fr=0
		for(var m=0;m<data[i].xb.length;m++){
			xb=xb+parseInt(data[i].xb[m]);
		}
	    for(var j=0;j<data[i].bg.length;j++){
	    	bg=bg+parseInt(data[i].bg[j]);
	    }
	    for(var k=0;k<data[i].fr.length;k++){
	    	fr=fr+parseInt(data[i].fr[k])
	    }
	    var branchName = data[i].branchName
		 option.baseOption.timeline.data.push(data[i].branchName)
		 option.options.push({  
				 title : {
						text: data[i].branchName,
			            left:'8%',
			            subtext:"新办："+xb+"，"+
			            		"变更："+bg+"，"+
			            		"复认："+fr,
			            subtextStyle:{
				            color:'#000'
				            }
		       			},
				   		tooltip : {
				        	trigger: 'axis',	            
				        	formatter:function(params){
					               var res = params[0].name
					               for(var i=0;i<params.length;i++){
					            	   	if(params[i].seriesName=="变更"){
					            	   		
							           	}else{
							           		res+='<br/><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'+params[i].color+'"></span>'+params[i].seriesName+':'+params[i].data+''
								        }
						           }
					               return res
						        }
				    	},
					    xAxis : [
							        {
							            type : 'category',
							            data : data[i].names,
					         			axisLabel:{
											interval:0,
					                 	}
							        }
							    ],
					    toolbox: {
					        show : true,
					        feature : {
					        	mark : {show: true},
			 			        dataZoom : {show: true},
					            dataView : {show: true,
						             		readOnly: false,
			    	 			           	optionToContent:function(opt) {
 				    	 			               var axisData = ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];
				    	 			               var table ='<table id="test" class="table-bordered table-striped" style="width:90%;text-align:center;margin-left:8%">',
				    	 			               table = table + '<tbody><tr><td></td><td>新办</td><td>复认</td><td>全部（含新办与复认）</td><td>变更</td></tr>';
				    	 			                for (var n = 0, l = axisData.length; n < l; n++) {
				    	 			                    table += '<tr>' + '<td>' + axisData[n] + '</td>' + '<td>' + opt.series[0].data[n] + '</td>' + '<td>' + opt.series[2].data[n] + '</td>'+ '<td>' + (opt.series[0].data[n] + opt.series[2].data[n]) + '</td>'+'<td>' + opt.series[0].data[n] + '</td>'+ '</tr>';
				    	 			                    }
				    	 			                      table += '</tbody>';
				    	 			                      return table;
				    	 			                }
						             		},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        } 
					    },
					series: [		        
					{
			            name:'新办',
			            type:'bar',
			            barMaxWidth:30,//最大宽度
			            stack: 'stack1',
			            data:data[i].xb,
			        },
			        {
			            name:'变更',
			            type:'bar',
			            barMaxWidth:30,//最大宽度
			            stack: 'stack1',
			            data:data[i].bg,
			        },
			        {
			            name:'复认',
			            type:'bar',
			            barMaxWidth:30,//最大宽度
			            stack: 'stack1',
			            data:data[i].fr,
			        },
	            ]
		})
	}
	
	switch($('#ywType').val()){
		case "XB":
			option.baseOption.legend.selected["新办"]=true
			break;
		case "FR":
			option.baseOption.legend.selected["复认"]=true
			break;
		case "BG":
			option.baseOption.legend.selected["变更"]=true
			break;
		default:
			option.baseOption.legend.selected["新办"]=true
			option.baseOption.legend.selected["复认"]=true
			option.baseOption.legend.selected["变更"]=true
			break;
	}
	return option;
}
function getFzx(){
	var myChart = echarts.init(document.getElementById('main')); 
	myChart.showLoading({
		text:'加载中',
	});
	var fzx=$("#fzx option:selected");
	var branchId= $("#fzx option:selected").val();
	var branchName= $("#fzx option:selected").text();
	var myDate = new Date();
	var year=myDate.getFullYear(); 
	var d1=$('#from').val();
	var d2=$('#to').val();
	if(fzx.length!=0){
		fzxid=[]
		branchName=[]
		for(var i=0;i<fzx.length;i++){
			branchName.push(fzx[i].text);
			fzxid.push(fzx[i].value);
		}
	}
	if(fzx.length==0){
		console.log(666)
		if(d1!=""&&d2!=""){//按时间段查所有分中心
			var timestamp1 = Date.parse(d1);
			var timestamp2 = Date.parse(d2);
			if(timestamp2-timestamp1>=0){
				$.post(
						"${ctx}/count/getSearch.action",
						"d1="+d1+"&d2="+d2,
						function(data){
							sort(data)
							myChart.setOption(setData(sort(data),"全部"));//载入设置
							
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
						sort(data)
						myChart.setOption(setData(sort(data),"总计"));//载入设置
						window.onresize=myChart.resize;
						myChart.hideLoading();//取消loading
					}
			);
		}
	}else{
		if(d1==""&&d2==""){//只按照分中心查找本年
			$.post(
			"${ctx}/count/search.action",
			"year="+year+"&branchId="+fzxid.toString(),
			function(data){
				console.log(data)
				var length = Object.getOwnPropertyNames(data.returnData).length
				myChart.setOption(setmutiData(data.returnData,length));//载入设置
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
					"d1="+d1+"&d2="+d2+"&branchId="+fzxid.toString(),
					function(data){
						sort(data)
						myChart.setOption(setData(sort(data),"总计"));//载入设置
						window.onresize=myChart.resize;
						myChart.hideLoading();//取消loading
					}
					);
				}else{
					wzj.alert('温馨提示','日期顺序有误!');
				}
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
                        	<div class="col-sm-4 text-right nopaddingleft nopaddingright" style="padding-top: 7px">分中心：</div>
                        	<div class="col-sm-8">
                        		<select class="selectpicker bla bla bli" id="fzx" multiple data-live-search="true" size="1" style="width: 150px">
									
								</select>
							</div>
						  	</div>
						  	<div class="col-sm-3">
                        	<div class="col-sm-4 text-right nopaddingleft nopaddingright" style="padding-top: 7px">业务类型：</div>
                        	<div class="col-sm-8">
                        		<select class="form-control" id="ywType">
									<option>-请选择-</option>
									<option value="XB">新办</option>
									<option value="FR">复认</option>
									<option value="BG">变更</option>
								</select></div>
						  	</div>
                           <div class="btn-group nopaddingleft">
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
                           <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">下载<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a href="javascript:exportRecord();">导出到Excel</a></li>
                              </ul>
                           </div> 
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