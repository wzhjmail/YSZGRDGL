window.onload = function(){
	if(window.sessionStorage.getItem("obj1")==null){
		window.sessionStorage.setItem("obj1","1");
		var button = document.getElementById('qyxx');
	    button.click();//执行执行点击按钮
	}else{
		var role =window.sessionStorage.getItem("role"); 
		console.log(window.sessionStorage.getItem("role"))
		console.log($('.username').text().replace(/(^\s*)|(\s*$)/g, ""))
		if(role==$('.username').text().replace(/(^\s*)|(\s*$)/g, "")){
			var obj =window.sessionStorage.getItem("obj"); 
		}else{
			var obj ='/YSZGRDGL/common/toEnterpriseInfo.action'; 
		}
	    var li=document.getElementsByName("menu");
// 	    角色管理、权限管理、业务查询与管理、查看日志、数据字典、设定证书编号/
	    for(var i=0;i<li.length;i++){
	    	if("/YSZGRDGL/count/toCount.action"==obj||"/YSZGRDGL/role/find.action"==obj || "/YSZGRDGL/permission/toPermissionsJSP.action"==obj||"/YSZGRDGL/log/find.action"==obj||"/YSZGRDGL/dictionary/find.action"==obj||"/YSZGRDGL/application/toCertificate.action"==obj){
	    		if(obj==li[i].getAttribute("value")){
	    		$(li[i]).find("a").eq(0).addClass("active");
	    		li[i].setAttribute("class","sub-menu active");
	    		}else{
    			li[i].setAttribute("class","sub-menu");
	    		$(li[i]).find("a").eq(0).removeClass("active");
	    		}
        	}
	    }
// 	    企业信息
	    if("/YSZGRDGL/common/toEnterpriseInfo.action"==obj || "/YSZGRDGL/common/toCancelledEnterpriseInfo.action"==obj){
	    	$("#sub1x").attr("style","display:block")
	    	$("#enterprise").addClass("active");
	    	for(var i=0;i<li.length;i++){
	    		if(obj==li[i].getAttribute("value")){
	    			$(li[i]).find("a").eq(0).addClass("active");
	    			li[i].setAttribute("class","sub-menu active");
	    		}else{
	    			li[i].setAttribute("class","sub-menu");
	    			$(li[i]).find("a").eq(0).removeClass("active");
	    		}
	    	}
	    }else{
	    	$("#sub1x").attr("style","display:none")
	    	$("#enterprise").removeClass("active");
	    }
// 	    用户管理
	    	if("/YSZGRDGL/zxry.action"==obj || "/YSZGRDGL/second.action"==obj||"/YSZGRDGL/zjry.action"==obj){
	    		$("#sub1").attr("style","display:block")
	    		$("#userManage").addClass("active");
	    		for(var i=0;i<li.length;i++){
	    		if(obj==li[i].getAttribute("value")){
	    			$(li[i]).find("a").eq(0).addClass("active");
		    		li[i].setAttribute("class","sub-menu active");
	        	}else{
	        		li[i].setAttribute("class","sub-menu");
		    		$(li[i]).find("a").eq(0).removeClass("active");
	        	}
	    	}
	    	}else{
	    		$("#sub1").attr("style","display:none")
	    		$("#userManage").removeClass("active");
	    	}
// 	   分支机构管理

	    	if("/YSZGRDGL/branchCenter/getBranchCenter.action"==obj||"/YSZGRDGL/equipment/toEquip.action"==obj||"/YSZGRDGL/fzjgUserManage.action"==obj){
	    		$("#sub20").attr("style","display:block")
	    		$("#fzjgManage").addClass("active");
	    		for(var i=0;i<li.length;i++){
	    		if(obj==li[i].getAttribute("value")){
	    			$(li[i]).find("a").eq(0).addClass("active");
		    		li[i].setAttribute("class","sub-menu active");
	        	}else{
	        		li[i].setAttribute("class","sub-menu");
		    		$(li[i]).find("a").eq(0).removeClass("active");
	        	}
	    	}
	    	}else{
	    		$("#sub20").attr("style","display:none")
	    		$("#fzjgManage").removeClass("active");
	    	}
// 新申请业务办理document.getElementById('sub2').getElementsByTagName('li')
 var sub2 =$("#sub2 li.a");
	for(var j=0;j<sub2.length;j++){
		if(obj==sub2[j].getAttribute("value")||$(sub2[j]).find("a").eq(0).attr("class")=="dcjq-parent active"){
			$("#sub3").addClass("active");
			$("#sub2").attr("style","display:block");
			$(sub2[j]).find("a").eq(0).addClass("active");
			sub2[j].setAttribute("class","sub-menu active");
		}else{
			$(sub2[j]).find("a").eq(0).removeClass("active");
			sub2[j].setAttribute("class","sub-menu");
			$(sub2[j]).find("a").eq(0).removeClass("active");
		}
	}
// 	业务申请
	var app1 =$("#app1 li");
	for(var i=0;i<app1.length;i++){
		if(obj==app1[i].getAttribute("value")){
			$("#sub3").addClass("active");
			$("#sub2").attr("style","display:block");
			$("#sub4").addClass("active");
			$("#app1").attr("style","display:block");
			$(app1[i]).find("a").eq(0).addClass("active");
			app1[i].setAttribute("class","sub-menu active");
		}else{
			$(app1[i]).find("a").eq(0).removeClass("active");
			app1[i].setAttribute("class","sub-menu");
		}
	}
// 	初审
	var firstrail1 =$("#firstrail1 li");
	for(var i=0;i<firstrail1.length;i++){
		if(obj==firstrail1[i].getAttribute("value")){
			$("#sub3").addClass("active");
			$("#sub2").attr("style","display:block");
			$("#sub5").addClass("active");
			$("#firstrail1").attr("style","display:block");
			$(firstrail1[i]).find("a").eq(0).addClass("active");
			firstrail1[i].setAttribute("class","sub-menu active");
		}else{
			$(firstrail1[i]).find("a").eq(0).removeClass("active");
			firstrail1[i].setAttribute("class","sub-menu");
		}
	}
// 	评审
	var rev1 =$("#rev1 li");
	for(var i=0;i<rev1.length;i++){
		if(obj==rev1[i].getAttribute("value")){
			$("#sub3").addClass("active");
			$("#sub2").attr("style","display:block");
			$("#sub6").addClass("active");
			$("#rev1").attr("style","display:block");
			$(rev1[i]).find("a").eq(0).addClass("active");
			rev1[i].setAttribute("class","sub-menu active");
		}else{
			$(rev1[i]).find("a").eq(0).removeClass("active");
			rev1[i].setAttribute("class","sub-menu");
		}
	} 
//	录取信息登记
	var admiss1 =$("#admiss1 li");
	for(var i=0;i<admiss1.length;i++){
		if(obj==admiss1[i].getAttribute("value")){
			$("#sub3").addClass("active");
			$("#sub2").attr("style","display:block");
			$("#sub21").addClass("active");
			$("#admiss1").attr("style","display:block");
			$(admiss1[i]).find("a").eq(0).addClass("active");
			admiss1[i].setAttribute("class","sub-menu active");
		}else{
			$(admiss1[i]).find("a").eq(0).removeClass("active");
			admiss1[i].setAttribute("class","sub-menu");
		}
	} 
// 	编码中心审核    	
	var express1 =$("#express1 li");
	for(var i=0;i<express1.length;i++){
		if(obj==express1[i].getAttribute("value")){
			$("#sub3").addClass("active");
			$("#sub2").attr("style","display:block");
			$("#sub7").addClass("active");
			$("#express1").attr("style","display:block");
			$(express1[i]).find("a").eq(0).addClass("active");
			express1[i].setAttribute("class","sub-menu active");
		}else{
			$(express1[i]).find("a").eq(0).removeClass("active");
			express1[i].setAttribute("class","sub-menu");
		}
	}  	  	 	
// 	中心出具检验报告
	var report1 =$("#report1 li");
	for(var i=0;i<report1.length;i++){
		if(obj==report1[i].getAttribute("value")){
			$("#sub3").addClass("active");
			$("#sub2").attr("style","display:block");
			$("#sub8").addClass("active");
			$("#report1").attr("style","display:block");
			$(report1[i]).find("a").eq(0).addClass("active");
			report1[i].setAttribute("class","sub-menu active");
		}else{
			$(report1[i]).find("a").eq(0).removeClass("active");
			report1[i].setAttribute("class","sub-menu");
		}
	}  	  	 
// 	 编码中心复核   	
	var check1 =$("#check1 li");
	for(var i=0;i<check1.length;i++){
		if(obj==check1[i].getAttribute("value")){
			$("#sub3").addClass("active");
			$("#sub2").attr("style","display:block");
			$("#sub9").addClass("active");
			$("#check1").attr("style","display:block");
			$(check1[i]).find("a").eq(0).addClass("active");
			check1[i].setAttribute("class","sub-menu active");
		}else{
			$(check1[i]).find("a").eq(0).removeClass("active");
			check1[i].setAttribute("class","sub-menu");
		}
	}  	  	  
// 复认业务办理
	var sub10 =$("#sub10 li.b");
	for(var j=0;j<sub10.length;j++){
		if(obj==sub10[j].getAttribute("value")||$(sub10[j]).find("a").eq(0).attr("class")=="dcjq-parent active"){
			$("#sub11").addClass("active");
			$("#sub10").attr("style","display:block");
			$(sub10[j]).find("a").eq(0).addClass("active");
			sub10[j].setAttribute("class","sub-menu active");
		}else{
			$(sub10[j]).find("a").eq(0).removeClass("active");
			sub10[j].setAttribute("class","sub-menu");
			$(sub10[j]).find("a").eq(0).removeClass("active");
		}
	}
// 复认业务申请
	var app2 =$("#app2 li");
	for(var i=0;i<app2.length;i++){
		if(obj==app2[i].getAttribute("value")){
			$("#sub11").addClass("active");
			$("#sub10").attr("style","display:block");
			$("#sub12").addClass("active");
			$("#app2").attr("style","display:block");
			$(app2[i]).find("a").eq(0).addClass("active");
			app2[i].setAttribute("class","sub-menu active");
		}else{
			$(app2[i]).find("a").eq(0).removeClass("active");
			app2[i].setAttribute("class","sub-menu");
		}
	}  	  	 
// 初审
	var firstrail2 =$("#firstrail2 li");
	for(var i=0;i<firstrail2.length;i++){
		if(obj==firstrail2[i].getAttribute("value")){
			$("#sub11").addClass("active");
			$("#sub10").attr("style","display:block");
			$("#sub13").addClass("active");
			$("#firstrail2").attr("style","display:block");
			$(firstrail2[i]).find("a").eq(0).addClass("active");
			firstrail2[i].setAttribute("class","sub-menu active");
		}else{
			$(firstrail2[i]).find("a").eq(0).removeClass("active");
			firstrail2[i].setAttribute("class","sub-menu");
		}
	}  	  	 
// 编码中心审核
	var express2 =$("#express2 li");
	for(var i=0;i<express2.length;i++){
		if(obj==express2[i].getAttribute("value")){
			$("#sub11").addClass("active");
			$("#sub10").attr("style","display:block");
			$("#sub14").addClass("active");
			$("#express2").attr("style","display:block");
			$(express2[i]).find("a").eq(0).addClass("active");
			express2[i].setAttribute("class","sub-menu active");
		}else{
			$(express2[i]).find("a").eq(0).removeClass("active");
			express2[i].setAttribute("class","sub-menu");
		}
	}  	  	
// 变更业务办理
	var sub15 =$("#sub15 li.c");
	for(var j=0;j<sub15.length;j++){
		if(obj==sub15[j].getAttribute("value")||$(sub15[j]).find("a").eq(0).attr("class")=="dcjq-parent active"){
			$("#sub16").addClass("active");
			$("#sub15").attr("style","display:block");
			$(sub15[j]).find("a").eq(0).addClass("active");
			sub15[j].setAttribute("class","sub-menu active");
		}else{
			$(sub15[j]).find("a").eq(0).removeClass("active");
			sub15[j].setAttribute("class","sub-menu");
			$(sub15[j]).find("a").eq(0).removeClass("active");
		}
	}
// 变更业务申请
	var app3 =$("#app3 li");
	for(var i=0;i<app3.length;i++){
		if(obj==app3[i].getAttribute("value")){
			$("#sub16").addClass("active");
			$("#sub15").attr("style","display:block");
			$("#sub17").addClass("active");
			$("#app3").attr("style","display:block");
			$(app3[i]).find("a").eq(0).addClass("active");
			app3[i].setAttribute("class","sub-menu active");
		}else{
			$(app3[i]).find("a").eq(0).removeClass("active");
			app3[i].setAttribute("class","sub-menu");
		}
	}  	  	
// 	初审
	var firstrail3 =$("#firstrail3 li");
	for(var i=0;i<firstrail3.length;i++){
		if(obj==firstrail3[i].getAttribute("value")){
			$("#sub16").addClass("active");
			$("#sub15").attr("style","display:block");
			$("#sub18").addClass("active");
			$("#firstrail3").attr("style","display:block");
			$(firstrail3[i]).find("a").eq(0).addClass("active");
			firstrail3[i].setAttribute("class","sub-menu active");
		}else{
			$(firstrail3[i]).find("a").eq(0).removeClass("active");
			firstrail3[i].setAttribute("class","sub-menu");
		}
	}  	  	
// 	编码中心复核
	var recheck =$("#recheck li");
	for(var i=0;i<recheck.length;i++){
		if(obj==recheck[i].getAttribute("value")){
			$("#sub16").addClass("active");
			$("#sub15").attr("style","display:block");
			$("#sub19").addClass("active");
			$("#recheck").attr("style","display:block");
			$(recheck[i]).find("a").eq(0).addClass("active");
			recheck[i].setAttribute("class","sub-menu active");
		}else{
			$(recheck[i]).find("a").eq(0).removeClass("active");
			recheck[i].setAttribute("class","sub-menu");
		}
	}  	  
	    	$("#content").attr("src",obj);
 }
	   
//标签数量显示
//	用户管理
	var sub1=$("#sub1 li");
	$("#lable1").html(sub1.length);
//	分支机构管理
	var sub20=$("#sub20 li");
	$("#lable5").html(sub20.length);
//	新申请业务办理
	var ul = document.getElementById('sub2');
	var n = ul.firstChild.nodeType == 1?ul.firstChild:ul.firstChild.nextSibling;
	var sub2 = [];
	for(;n;n = n.nextSibling){
	    if(n.nodeType == 1){
	        sub2.push(n);
	    }
	};
	$("#lable2").html(sub2.length);
//	复认业务办理
	var ul = document.getElementById('sub10');
	var n = ul.firstChild.nodeType == 1?ul.firstChild:ul.firstChild.nextSibling;
	var sub10 = [];
	for(;n;n = n.nextSibling){
	    if(n.nodeType == 1){
	        sub10.push(n);
	    }
	};
	$("#lable3").html(sub10.length);
//变更业务办理
	var ul = document.getElementById('sub15');
	var n = ul.firstChild.nodeType == 1?ul.firstChild:ul.firstChild.nextSibling;
	var sub15 = [];
	for(;n;n = n.nextSibling){
	    if(n.nodeType == 1){
	        sub15.push(n);
	    }
	};
	$("#lable4").html(sub15.length);
};