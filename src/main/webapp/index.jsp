<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="myshiro" uri="/customize-tags" %>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
	<link href="${ctx}/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
	<link href="${ctx}/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
	<!-- 查询页面 -->
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="${ctx}/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="${ctx}/assets/data-tables/DT_bootstrap.css" rel="stylesheet" ><!-- DATATABLE CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="${ctx}/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
	<link href="${ctx}/css/style_self.css" rel="stylesheet"><!-- 右键特效的css -->
	
<script src="${ctx}/js/jquery-1.8.3.min.js"></script><!-- BASIC JS LIABRARY 1.8.3 -->
<script src="${ctx}/js/style_self.js"></script><!-- 右键特效的js -->
<script type="text/javascript">
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
        }
        else {
            oTable.$('tr.row_selected').removeClass('row_selected');
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
             //window.clipboardData.setData('text', value); 
            wzj.alert('温馨提示', '查看')
        }
    }); 
    imageMenuData.push({
        "text": "评审", "func": function () {
        	 wzj.alert('温馨提示','评审');
        }
    });
    imageMenuData.push({
        "text": "修改", "func": function () {
        	 wzj.alert('温馨提示','修改');
        }
    });
    imageMenuData.push({
        "text": "<font color='red'>删除</font>", "func": function () {
        	 wzj.alert('温馨提示',"删除");
        }
    });
       imageMenuData2.push(imageMenuData);
       
       return imageMenuData2;
}
//以上是右键特效的js
var temptr = $();
var nRow = null;
$(function(){
	for(var i=0;i<10;i++){
		$("#result").append("<tr>"
				+"<td>"+i+1+"</td>"
				+"<td>初次申请</td>"
             	+"<td>郑州办事处</td>"
             	+"<td>河南真彩印刷有限公司</td>"
             	+"<td>股份有限责任公司</td>"
             	+"<td>河南省郑州市文化路97号</td>"
             	+"<td>450000</td>"
             	+"<td>110120</td>"
             	+"<td></td>"
             	+"<td>0C,2B,15A</td>"
             	+"<td>12306</td>"
             	+"<td>5000万</td>"
             	+"<td>郑金</td>"
             	+"<td>总经理</td>"
             	+"<td>110-120119</td>"
             	+"<td>1819282435</td>"
             	+"<td>刘飞</td>"
             	+"<td>经理</td>"
             	+"<td>1234563435</td>"
             	+"<td>印刷用品</td>"
             	+"<td></td>"
             	+"<td>300</td>"
             	+"<td>90</td>"
             	+"<td>王维</td>"
             	+"<td>厂长</td>"
             	+"<td>工程师</td>"
             	+"<td><input type='checkbox' checked='true'/></td>"
             	+"<td><input type='checkbox' checked='true'/></td>"
             	+"<td><input type='checkbox'/></td>"
             	+"<td><input type='checkbox' checked='true'/></td>"
             	+"<td></td>"
             	+"<td><input type='checkbox' checked='true'/></td>"
             	+"<td><input type='checkbox'/></td>"
             	+"<td><input type='checkbox' checked='true'/></td>"
             	+"<td><input type='checkbox'/></td>"
             	+"<td><input type='checkbox' checked='true'/></td>"
             	+"<td></td>"
             	+"<td>日本四色胶印</td>"
             	+"<td>委托检验</td>"
             	+"<td></td>"
             	+"<td></td>"
             	+"<td>2017/04/23</td>"
             	+"<td>2017/04/25</td>"
             	+"<td>2027/04/23</td></tr>");
	}
	
	$("#psb").click(
		function(){
			window.open("${ctx}/review_form.jsp");
		}		
	);
	
	$("#editable-sample tbody tr").dblclick(function(e){
		alert($(this).children("td").eq(0).text());
		view();
	});
	
	$("#deleteRow").click(function(){
		wzj.confirm('温馨提醒', "确定删除"+nRow+"行？", function(flag) {
			if(flag) {
				deleteRecord(nRow);
			}
		});
	});
	
	$('#editable-sample tbody').on('click', 'tr', function () {//选中的行
		//获取选中行的第一列的值
		//alert($(this).children("td").eq(0).text());
		nRow = $(this).parent().find("tr").index($(this)[0]);
		temptr.css("background-color","");
        temptr = $(this);
        temptr.css("background-color","#99ffff");
	});
});//初始化
window.onload=function(){//给表添加滚动条
	$("#editable-sample").wrap('<div style="overflow-x:auto"></div>');
	SetTableStyle();
};
function view(){
	window.open("${ctx}/application_form.jsp");
}
function ifm_change(){
	window.open("${ctx}/ifm_change.jsp");
}
function deleteRecord(nRow){
	var oTable = $('#editable-sample').dataTable();
	oTable.fnDeleteRow(nRow);
}
</script>
</head>
<body>
	<section id="container">
		<header class="header white-bg">
			<div class="sidebar-toggle-box">
			  <div  data-placement="right" class="fa fa-bars tooltips">
			  </div>
			</div>
			<a href="index.html" class="logo">
	          <!--  -->
	          <span>商品条码印刷资格认定管理系统</span>
            </a>
            <nav class="nav notify-row" id="top_menu">
             	 <ul class="nav top-menu">
             	 	<li class="dropdown">
             	 		<a data-toggle="dropdown" class="dropdown-toggle" href="#">
			                <i class="fa fa-tasks">
			                </i>
			                <span class="badge bg-success">
			                  6
			                </span>
			             </a>
			              <ul class="dropdown-menu extended tasks-bar">
			                <li class="notify-arrow notify-arrow-blue">
			                </li>
			                <li>
			                  <p class="blue">
			                    You have 6 pending tasks
			                  </p>
			                </li>
			                 <li>
                  <a href="#">
                    <div class="task-info">
                      <div class="desc">
                        Dashboard v1.3
                      </div>
                      <div class="percent">
                        40%
                      </div>
                    </div>
                    <div class="progress progress-striped">
                      <div class="progress-bar progress-bar-success set-40" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" >
                        <span class="sr-only">
                          40% Complete (success)
                        </span>
                      </div>
                    </div>
                  </a>
                </li>
                <li class="external">
                  <a href="#">
                    See All Tasks
                  </a>
                </li>
			          </ul>
             	 	</li>
             	 	<li id="header_inbox_bar" class="dropdown">
             	 		<a data-toggle="dropdown" class="dropdown-toggle" href="#">
			                <i class="fa fa-envelope-o">
			                </i>
			                <span class="badge bg-important">
			                  5
			                </span>
			             </a>
			             <ul class="dropdown-menu extended inbox">
			             	<li class="notify-arrow notify-arrow-blue">
				            </li>
				            <li>
			                  <p class="blue">
			                    You have 5 new messages
			                  </p>
				            </li>
				            <li>
                  <a href="#">
                    <span class="photo">
                      <img alt="avatar" src="./img/avatar-mini.jpg">
                    </span>
                    <span class="subject">
                      <span class="from">
                        Chintan Pandya
                      </span>
                      <span class="time">
                        Just now
                      </span>
                    </span>
                    <span class="message">
                      Hello, this is an example msg.
                    </span>
                  </a>
                </li>
                <li>
                  <a href="#">
                    See all messages
                  </a>
                </li>
			             </ul>
             	 	</li>
             	 	 <li id="header_notification_bar" class="dropdown">
		              <a data-toggle="dropdown" class="dropdown-toggle" href="#">
		                <i class="fa fa-bell-o">
		                </i>
		                <span class="badge bg-warning">
		                  7
		                </span>
		              </a>
              		  <ul class="dropdown-menu extended notification">
              		  	 <li class="notify-arrow notify-arrow-blue">
			                </li>
			                <li>
			                  <p class="blue">
			                    You have 7 new notifications
			                  </p>
                			</li>
                			<li>
			                  <a href="#">
			                    <span class="label label-danger">
			                      <i class="fa fa-bolt">
			                      </i>
			                    </span>
			                    Server #3 overloaded.
			                    <span class="small italic">
			                      34 mins
			                    </span>
			                  </a>
			                </li>
			                <li>
			                  <a href="#">
			                    See all notifications
			                  </a>
			                </li>
              		  </ul>
              		</li>
             	 </ul>
            </nav>
            <div class="top-nav ">
            	 <ul class="nav pull-right top-menu">
            	 	<li>
		              <input type="text" class="form-control search" placeholder="Search">
		            </li>
		             <li class="dropdown">
		             	<a data-toggle="dropdown" class="dropdown-toggle" href="#">
			                <img alt="" src="img/avatar1_small.jpg">
			                <span class="username">
			                  ${activeUser.realname}
			                </span>
			                <b class="caret">
			                </b>
		                </a>
		                <ul class="dropdown-menu extended logout">
                <li class="log-arrow-up">
                </li>
                <li>
                  <a href="#">
                    <i class=" fa fa-suitcase">
                    </i>
                    Profile
                  </a>
                </li>
                <li>
                  <a href="${ctx}/sysUser/toEdit.action">
                    <i class="fa fa-cog">
                    </i>
                    	修改信息
                  </a>
                </li>
                <li>
                  <a href="javascript:logoutRedirect()">
                    <i class="fa fa-bell-o">
                    </i>
                   	 注销登录
                  </a>
                </li>
                <li>
                  <a href="javascript:logout()">
                    <i class="fa fa-key">
                    </i>
                    	退出登录
                  </a>
                </li>
              </ul>
		             </li>
            	 </ul>
            </div>
		</header>
		<aside>
			<div id="sidebar" class="nav-collapse" style="z-index:10">
          	<ul class="sidebar-menu" id="nav-accordion">
	            <li>
	              <a href="${ctx}/index.jsp" class="active">
	                <i class="fa fa-dashboard">
	                </i>
	                <span>Dashboard</span>
	              </a>
	            </li>
	            <%-- <li>
	                <a href="${ctx}/toLoginJSP.action">
		                <i class="fa fa-rocket"></i>
	                	<span>登录页面</span>
              		</a>
            	</li> --%>
            <myshiro:hasAnyPermission name="qyyh,fzjg,zjyh,zxyh"> 
	            <li class="sub-menu">
	              <a href="javascript:;">
	                <i class="fa fa-laptop">
	                </i>
	                <span>用户管理</span>
	                <span class="label label-success span-sidebar">4</span>
	              </a>
	               <ul class="sub">
	               <shiro:hasPermission name="qyyh">
	               		<li>
		                  <a href="${ctx}/first.action">中心用户管理</a>
		                </li>
				   </shiro:hasPermission>	
				   <shiro:hasPermission name="fzjg">	               
		           <li>
                    <a href="#">分支机构用户管理</a>
                  </li>
                </shiro:hasPermission>
                 <shiro:hasPermission name="zjyh">
                <li>
                  <a href="#">专家管理</a>
                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="zxyh">
                <li>
                  <a href="${ctx}/toFirmMng.action">企业用户管理</a>
                </li>
                </shiro:hasPermission>
	               </ul>
	            </li>
	        </myshiro:hasAnyPermission>
            <shiro:hasPermission name="role">
            <li>
	             <a href="${ctx}/role/find.action">
	                <i class="fa fa-user"></i>
	                <span>角色管理</span>
		         </a>
	        </li>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="pms">
            <li>
              <a href="${ctx}/permission/toPermissionsJSP.action">
                <i class="fa fa-key"></i>
                <span>权限管理</span>
              </a>
            </li>
            </shiro:hasPermission>
            <li class="sub-menu">
              <a href="">
                <i class="fa fa-sitemap"></i>
                <span>分支机构管理</span>
              	<span class="label label-primary span-sidebar">3</span>
              </a>
              <ul class="sub">
               		<li>
	                  <a href="#">分支机构信息管理</a>
	                </li>
	                <li>
	                  <a href="#">设备信息管理</a>
	                </li>
	                <li>
	                  <a href="#">人员信息管理</a>
	                </li>
			 </ul>
            </li>
            <li class="sub-menu">
	              <a href="javascript:;">
	                <i class="fa fa-suitcase"></i>
	                <span>业务管理</span>
	                <span class="label label-info span-sidebar">4</span>
	              </a>
	          <ul class="sub">
                <li>
                  <a href="${ctx}/application_form.jsp" target="_blank">新申请业务办理</a>
                </li>
		        <li>
                   <a href="${ctx}/reApplication_form.jsp" target="_blank">复认业务办理</a>
                </li>
                <li>
                  <a href="${ctx}/ifm_change2.jsp">变更业务办理</a>
                </li>
                <li>
                  <a href="${ctx}/review_form2.jsp">评审业务办理</a>
                </li>
                <li>
                  <a href="">业务查询与统计</a>
                </li>
	         </ul>
	       </li>
           <shiro:hasRole name="系统管理员">
            <li>
                <a href="${ctx}/log/find.action">
	                <i class="fa fa-book"></i>
                	<span>查看日志</span>
             	</a>
            </li>
            </shiro:hasRole>
            <shiro:hasRole name="系统管理员">
            <li>
                <a href="${ctx}/dictionary/find.action">
	                <i class="fa fa-book"></i>
                	<span>数据字典</span>
             	</a>
            </li>
            </shiro:hasRole>
	        </ul>
	        </div>
		</aside>
		<section id="container" class="">
	    <!-- BEGIN HEADER --> 
         
         <section id="main-content">
		    <!-- BEGIN WRAPPER  -->
            <section class="wrapper site-min-height">
               <section class="panel">
                  <div class="panel-body">
                     <div class="adv-table editable-table">
                        <div class="clearfix">
                        <div class="btn-group col-lg-1">
                              <button onclick="view();" class="btn btn-info" >
                            	  查看<i class="fa fa-eye"></i>
                              </button>
                           </div>
                           <div class="btn-group col-lg-1">
                              <button id="psb" class="btn btn-info" >
                            	  评审<i class="fa fa-gavel"></i>
                              </button>
                           </div>
                           <div class="btn-group col-lg-1">
                              <button onclick="view();" class="btn btn-info" >
                            	  添加<i class="fa fa-plus"></i>
                              </button>
                           </div>
                           <div class="btn-group col-lg-1">
                              <button onclick="ifm_change();" class="btn btn-info" >
                            	  修改<i class="fa fa-pencil"></i>
                              </button>
                           </div>
                           <div class="btn-group col-lg-1">
                              <button id="deleteRow" class="btn btn-warning" >
                            	  删除<i class="fa fa-times"></i>
                              </button>
                           </div>
                           
                           <div class="btn-group pull-right">
                              <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">工具<i class="fa fa-angle-down"></i>
                              </button>
                              <ul class="dropdown-menu pull-right">
                                 <li><a href="#">Print</a></li>
                                 <li><a href="#">Save as PDF</a></li>
                                 <li><a href="">export Excel</a></li>
                              </ul>
                           </div>
                        </div>
                        <div class="space15"></div>
                        <table class="table table-bordered" style="width:460%" id="editable-sample">
                           <thead>
                              <tr>
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
                                 <th class="">技术负责人</th>
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
                             
                             <!-- <tr>
                             	<td>123</td>
                             	<td>初次申请</td>
                             	<td>郑州办事处</td>
                             	<td>河南真彩印刷有限公司</td>
                             	<td>股份有限责任公司</td>
                             	<td>河南省郑州市文化路97号</td>
                             	<td>450000</td>
                             	<td>110120</td>
                             	<td></td>
                             	<td>0C,2B,15A</td>
                             	<td>12306</td>
                             	<td>5000万</td>
                             	<td>郑金</td>
                             	<td>总经理</td>
                             	<td>110-120119</td>
                             	<td>1819282435</td>
                             	<td>刘飞</td>
                             	<td>经理</td>
                             	<td>1234563435</td>
                             	<td>印刷用品</td>
                             	<td></td>
                             	<td>300</td>
                             	<td>90</td>
                             	<td>王维</td>
                             	<td>厂长</td>
                             	<td>工程师</td>
                             	<td><checkbox selected="true"/></td>
                             	<td><checkbox selected="true"/></td>
                             	<td><checkbox selected="false"/></td>
                             	<td><checkbox selected="false"/></td>
                             	<td></td>
                             	<td><checkbox selected="true"/></td>
                             	<td><checkbox selected="false"/></td>
                             	<td><checkbox selected="true"/></td>
                             	<td><checkbox selected="true"/></td>
                             	<td><checkbox selected="true"/></td>
                             	<td></td>
                             	<td>日本四色胶印</td>
                             	<td>委托检验</td>
                             	<td></td>
                             	<td></td>
                             	<td>2017/04/23</td>
                             	<td>2017/04/25</td>
                             	<td>2027/04/23</td>
                             </tr> -->
                           </tbody>
                        </table>
                        </div>
                     </div>
               </section>
            </section>
			<!-- END WRAPPER  -->
         </section>
        </section>
	    <!-- END SECTION -->
		
		<footer class="site-footer">
        <div class="text-center">
           版权所有：中国物品编码中心|京ICP备11036137号-10
          <a href="" target="_blank">技术支持：海丰通航</a>
          <a href="#" class="go-top">
            <i class="fa fa-angle-up">
            </i>
          </a>
        </div>
      </footer>
	</section>
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
            EditableTable.init();
          	//^待匹配串的开始位置，$待匹配串的结束位置
            var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
            if(myreg.test("18236768239")){ 
            	 alert('有效的手机号码！'); 
            } else {
            	 alert('请输入有效的手机号码！'); 
            }
            
        });
     </script>
  <!-- END JS --> 
    <!-- END JS -->
</body>
</html>