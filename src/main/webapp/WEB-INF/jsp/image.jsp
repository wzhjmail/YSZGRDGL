<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>查看当前的流程图</title>
<script src="${ctx}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$(function(){
		$.ajaxSetup({async : false});
		$.post(
			"${ctx}/getDeps.action",
			function(data){
				$.each(data,function(key,val){
					var result="<tr><td>"+key+"</td><td>"+val+
					"</td><td></td><td><a href='${ctx}/delDep.action?id="+key+"'>删除</a></td></tr>";
					$("#tab").append(result);
				});
			}
		);
	});

	function deployment(){
		$.ajaxSetup({ async : false });
		$.post(
			"${ctx}/deployment.action",
			function(data){
				alert("部署流程定义成功，Id:"+data.toString());
			}
		);
	}
	
	function startProcess(){
		var name = $("#form1name").val();
		$.ajaxSetup({ async : false });
		$.post(
			"${ctx}/start.action",
			"name="+name,
			function(data){
				alert("启动流程定义成功");
			}
		);
	}
	function findPersonalTaskId(){
		var name = $("#form2name").val();
		$.ajaxSetup({ async : false });
		$.post(
			"${ctx}/findPersonalTaskId.action",
			"name="+name,
			function(data){
				alert("任务Id："+data.result);
			}
		);
	}
	function findGroupTaskId(){
		var name = $("#formgname").val();
		$.ajaxSetup({ async : false });
		$.post(
			"${ctx}/findGroupTaskId.action",
			"name="+name,
			function(data){
				alert("任务Id："+data.result);
				alert("comment:"+data.comment);
			}
		);
	}
	function completePersonlTask(){
		var taskId = $("#form3taskId").val();
		var money = $("#money").val();
		var comment = $("#comm").val();
		var role = $("#role").val();
		var param = "taskId="+taskId+"&role="+role+"&money="+money+"&comment="+comment;
		$.ajaxSetup({ async : false });
		$.post(
			"${ctx}/completePersonlTask.action",
			param,
			function(data){
				alert("完成任务！");
			}
		);
	}
	function showProcess(){
		var taskId = $("#form4taskId").val();
		$.ajaxSetup({ async : false });
		$.post(
			"${ctx}/showProcess.action",
			"taskId="+taskId,
			function(data){
				/* 方式一：
				var imgFrame=document.getElementById("imgFrame");
				或者var imgFrame = $("#imgFrame")[0];
				imgFrame.style.top=data.y+"px";
				imgFrame.style.left=data.x+"px";
				imgFrame.style.width=data.width+"px";
				imgFrame.style.height=data.height+"px";
				方式二：*/
				var imgFrame = $("#imgFrame");
				imgFrame.offset({top:data.y,left:data.x});
				imgFrame.height(data.height+"px");
				imgFrame.width(data.width+"px");
			}
		);
	}
	function climTask(){
		var taskId = $("#ti").val();
		var userId = $("#ui").val();
		$.post(
			"${ctx}/climTask.action",
			"taskId="+taskId+"&userId="+userId,
			function(data){
				alert("完成拾取任务！");
			}	
		);
	}
</script>
</head>
<body>
<img style="position:absolute;top:0px;left:0px;" src="upload/zzrd2.png"/>
<div style="z-index:9;position:absolute;border:1px solid red;top:0px;left:0px;width:0px;height:0px;" id="imgFrame"></div>
<br>
<center style="position:relative;margin-top:10px;">
	<form>
		<button type="button" onclick="deployment()">部署流程实例</button>
	</form>
	<form>
		<input type="text" name="name" id="form1name" placeholder="用户名称"><br>
		<button type="button" onclick="startProcess()">启动流程定义</button>
	</form>
	<br>
	<form>
		<input type="text" name="name" id="form2name" placeholder="个人名称"><br>
		<button type="button" onclick="findPersonalTaskId()">查看任务Id</button>
	</form><br> 
	<form>
		<input type="text" name="name" id="formgname" placeholder="组成员名称"><br>
		<button type="button" onclick="findGroupTaskId()">查看任务Id</button>
	</form><br> 
	<form>
		<input type="text" name="name" id="ti" placeholder="任务Id"><br>
		<input type="text" name="name" id="ui" placeholder="拾取人"><br>
		<button type="button" onclick="climTask()">拾取任务</button>
	</form><br> 
	<form>
		<input type="text" name="taskId" id="form3taskId" placeholder="任务Id"><br>
		<input type="text" name="role" id="role" placeholder="角色"><br>
		<input type="text" name="money" id="money" placeholder="资金"><br>
		<input type="text" name="comm" id="comm" placeholder="评论"><br>
		<button type="button" onclick="completePersonlTask()">完成任务</button>
	</form>
	<br>
	<form>
		<input type="text" name="taskId" id="form4taskId" placeholder="任务Id"><br>
		<button type="button" onclick="showProcess()">查看流程图</button>
	</form><br>
	<form>
	   <table id="tab">
	     <tr><td>Id</td><td>流程名称</td><td>发布时间</td><td>操作</td></tr>
	   </table>
	</form>
</center>
</body>
</html>