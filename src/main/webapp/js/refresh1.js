function refresh(){
	if(window.sessionStorage.getItem("obj1")==null){
		window.sessionStorage.setItem("obj1","1");
		var button = document.getElementById('qyxx');
	    button.click();//执行执行点击按钮
	}else{
		var role =window.sessionStorage.getItem("role"); 
		if(role==$('.username').text().replace(/(^\s*)|(\s*$)/g, "")){
			var obj =window.sessionStorage.getItem("obj"); 
		}else{
			var obj ='/YSZGRDGL/common/toCancelledEnterpriseInfo.action'; 
		}
	    var li=document.getElementsByName("menu");
// 	    角色管理、权限管理、业务查询与管理、查看日志、数据字典、设定证书编号/
	    for(var i=0;i<li.length;i++){
	    	if("/YSZGRDGL/count/toCount.action"==obj||"/YSZGRDGL/role/find.action"==obj || "/YSZGRDGL/permission/toPermissionsJSP.action"==obj||"/YSZGRDGL/log/find.action"==obj||"/YSZGRDGL/dictionary/find.action"==obj||"/YSZGRDGL/application/toCertificate.action"==obj||"/YSZGRDGL/certposition/toSetting.action"==obj){
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
	    	$("#sub64").attr("style","display:block")
	    	for(var i=0;i<li.length;i++){
	    		if(obj==li[i].getAttribute("value")){
	    			$(li[i]).find("a").eq(0).addClass("active");
	    			li[i].setAttribute("class","sub-menu active");
	    		}else{
	    			li[i].setAttribute("class","sub-menu");
	    			$(li[i]).find("a").eq(0).removeClass("active");
	    		}
		    	$("#menu64").addClass("active");
	    	}
	    }else{
	    	$("#sub64").attr("style","display:none")
	    	$("#menu64").removeClass("active");
	    }
// 	    用户管理
	    	if("/YSZGRDGL/zxry.action"==obj || "/YSZGRDGL/second.action"==obj||"/YSZGRDGL/zjry.action"==obj){

	    		$("#sub2").attr("style","display:block")
	    		for(var i=0;i<li.length;i++){
	    		if(obj==li[i].getAttribute("value")){
	    			$(li[i]).find("a").eq(0).addClass("active");
		    		li[i].setAttribute("class","sub-menu active");
	        	}else{
	        		li[i].setAttribute("class","sub-menu");
		    		$(li[i]).find("a").eq(0).removeClass("active");
	        	}
	    		$("#menu2").addClass("active");
	    	}
	    	}else{
	    		$("#sub2").attr("style","display:none")
	    		$("#menu2").removeClass("active");
	    		$("#sub2 li").each(function(){
	    			$(this).removeClass("active");
	    		})
	    	}
// 	   分支机构管理

	    	if("/YSZGRDGL/branchCenter/getBranchCenter.action"==obj||"/YSZGRDGL/equipment/toEquip.action"==obj||"/YSZGRDGL/fzjgUserManage.action"==obj){
	    		$("#sub5").attr("style","display:block")
	    		
	    		for(var i=0;i<li.length;i++){
	    		if(obj==li[i].getAttribute("value")){
	    			$(li[i]).find("a").eq(0).addClass("active");
		    		li[i].setAttribute("class","sub-menu active");
	        	}else{
	        		li[i].setAttribute("class","sub-menu");
		    		$(li[i]).find("a").eq(0).removeClass("active");
	        	}
	    		$("#menu5").addClass("active");
	    	}
	    	}else{
	    		$("#sub5").attr("style","display:none")
	    		$("#menu5").removeClass("active");
	    	}
	    	if("/YSZGRDGL/evaluation/toReviewInfo.action"==obj||"/YSZGRDGL/certposition/toSetting.action"==obj){
	    		$("#sub72").attr("style","display:block")
	    		for(var i=0;i<li.length;i++){
	    			if(obj==li[i].getAttribute("value")){
	    				$(li[i]).find("a").eq(0).addClass("active");
	    				li[i].setAttribute("class","sub-menu active");
	    			}else{
	    				li[i].setAttribute("class","sub-menu");
	    				$(li[i]).find("a").eq(0).removeClass("active");
	    			}
	    			$("#menu72").addClass("active");
	    		}
	    	}else{
	    		$("#sub72").attr("style","display:none")
	    		$("#menu72").removeClass("active");
	    	}
// 新申请业务办理document.getElementById('sub4').getElementsByTagName('li')
 var sub6 =$("#sub6 li.a1");
	for(var j=0;j<sub6.length;j++){
		if(obj==sub6[j].getAttribute("value")||$(sub6[j]).find("a").eq(0).attr("class")=="dcjq-parent active"){
			$("#menu6").addClass("active");
			$("#sub6").attr("style","display:block");
			$(sub6[j]).find("a").eq(0).addClass("active");
			sub6[j].setAttribute("class","sub-menu active");
		}else{
			$(sub6[j]).find("a").eq(0).removeClass("active");
			sub6[j].setAttribute("class","sub-menu");
			$(sub6[j]).find("a").eq(0).removeClass("active");
		}
	}
// 	业务申请
	var sub16 =$("#sub16 li");
	for(var i=0;i<sub16.length;i++){
		if(obj==sub16[i].getAttribute("value")){
			$("#menu6").addClass("active");
			$("#ywsq").addClass("active");
			$("#sub6").attr("style","display:block");
			$("#sub16").attr("style","display:block");
			$(sub16[i]).find("a").eq(0).addClass("active");
			sub16[i].setAttribute("class","sub-menu active");
		}else{
			$(sub16[i]).find("a").eq(0).removeClass("active");
			sub16[i].setAttribute("class","sub-menu");
		}
	}
// 	初审
	var sub17 =$("#sub17 li");
	for(var i=0;i<sub17.length;i++){
		if(obj==sub17[i].getAttribute("value")){
			$("#menu6").addClass("active");
			$("#fzaud1").addClass("active");
			$("#sub6").attr("style","display:block");
			$("#sub17").attr("style","display:block");
			$(sub17[i]).find("a").eq(0).addClass("active");
			sub17[i].setAttribute("class","sub-menu active");
		}else{
			$(sub17[i]).find("a").eq(0).removeClass("active");
			sub17[i].setAttribute("class","sub-menu");
		}
	}
// 	评审
	var fzrev1 =$("#fzrev1 li");
	for(var i=0;i<fzrev1.length;i++){
		if(obj==fzrev1[i].getAttribute("value")){
			$("#menu6").addClass("active");
			$("#sub4").attr("style","display:block");
			$("#sub6").addClass("active");
			$("#fzrev1").attr("style","display:block");
			$(fzrev1[i]).find("a").eq(0).addClass("active");
			fzrev1[i].setAttribute("class","sub-menu active");
		}else{
			$(fzrev1[i]).find("a").eq(0).removeClass("active");
			fzrev1[i].setAttribute("class","sub-menu");
		}
	} 
//	录取信息登记
	var admiss1 =$("#admiss1 li");
	for(var i=0;i<admiss1.length;i++){
		if(obj==admiss1[i].getAttribute("value")){
			$("#menu6").addClass("active");
			$("#sub4").attr("style","display:block");
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
	var sub20 =$("#sub20 li");
	for(var i=0;i<sub20.length;i++){
		if(obj==sub20[i].getAttribute("value")){
			$("#menu6").addClass("active");
			$("#codeaud1").addClass("active");
			$("#sub6").attr("style","display:block");
			$("#sub20").attr("style","display:block");
			$(sub20[i]).find("a").eq(0).addClass("active");
			sub20[i].setAttribute("class","sub-menu active");
		}else{
			$(sub20[i]).find("a").eq(0).removeClass("active");
			sub20[i].setAttribute("class","sub-menu");
		}
	}  	  	 	
// 	中心出具检验报告
	var report1 =$("#report1 li");
	for(var i=0;i<report1.length;i++){
		if(obj==report1[i].getAttribute("value")){
			$("#menu6").addClass("active");
			$("#sub4").attr("style","display:block");
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
			$("#menu6").addClass("active");
			$("#sub4").attr("style","display:block");
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
	var sub7 =$("#sub7 li.a1");
	for(var j=0;j<sub7.length;j++){
		if(obj==sub7[j].getAttribute("value")||$(sub7[j]).find("a").eq(0).attr("class")=="dcjq-parent active"){
			$("#menu7").addClass("active");
			$("#sub7").attr("style","display:block");
			$(sub7[j]).find("a").eq(0).addClass("active");
			sub7[j].setAttribute("class","sub-menu active");
		}else{
			$(sub7[j]).find("a").eq(0).removeClass("active");
			sub7[j].setAttribute("class","sub-menu");
			$(sub7[j]).find("a").eq(0).removeClass("active");
		}
	}
// 复认业务申请
	var sub25 =$("#sub25 li");
	for(var i=0;i<sub25.length;i++){
		if(obj==sub25[i].getAttribute("value")){
			$("#menu7").addClass("active");
			$("#frapp").addClass("active");
			$("#sub7").attr("style","display:block");
			$("#sub25").attr("style","display:block");
			$(sub25[i]).find("a").eq(0).addClass("active");
			sub25[i].setAttribute("class","sub-menu active");
		}else{
			$(sub25[i]).find("a").eq(0).removeClass("active");
			sub25[i].setAttribute("class","sub-menu");
		}
	}  	  	 
// 初审
	var sub26 =$("#sub26 li");
	for(var i=0;i<sub26.length;i++){
		if(obj==sub26[i].getAttribute("value")){
			$("#menu7").addClass("active");
			$("#fzaud2").addClass("active");
			$("#sub7").attr("style","display:block");
			$("#sub26").attr("style","display:block");
			$(sub26[i]).find("a").eq(0).addClass("active");
			sub26[i].setAttribute("class","sub-menu active");
		}else{
			$(sub26[i]).find("a").eq(0).removeClass("active");
			sub26[i].setAttribute("class","sub-menu");
		}
	}  	  	 
// 编码中心审核
	var sub29 =$("#sub29 li");
	for(var i=0;i<sub29.length;i++){
		if(obj==sub29[i].getAttribute("value")){
			$("#menu7").addClass("active");
			$("#codeaud2").addClass("active");
			$("#sub7").attr("style","display:block");
			$("#sub29").attr("style","display:block");
			$(sub29[i]).find("a").eq(0).addClass("active");
			sub29[i].setAttribute("class","sub-menu active");
		}else{
			$(sub29[i]).find("a").eq(0).removeClass("active");
			sub29[i].setAttribute("class","sub-menu");
		}
	}  	  	
// 变更业务办理
	var sub8 =$("#sub8 li.a1");
	for(var j=0;j<sub8.length;j++){
		if(obj==sub8[j].getAttribute("value")||$(sub8[j]).find("a").eq(0).attr("class")=="dcjq-parent active"){
			$("#menu8").addClass("active");
			$("#sub8").attr("style","display:block");
			$(sub8[j]).find("a").eq(0).addClass("active");
			sub8[j].setAttribute("class","sub-menu active");
		}else{
			$(sub8[j]).find("a").eq(0).removeClass("active");
			sub8[j].setAttribute("class","sub-menu");
			$(sub8[j]).find("a").eq(0).removeClass("active");
		}
	}
// 变更业务申请
	var sub34 =$("#sub34 li");
	for(var i=0;i<sub34.length;i++){
		if(obj==sub34[i].getAttribute("value")){
			$("#menu8").addClass("active");
			$("#chaapp").addClass("active");
			$("#sub8").attr("style","display:block");
			$("#sub34").attr("style","display:block");
			$(sub34[i]).find("a").eq(0).addClass("active");
			sub34[i].setAttribute("class","sub-menu active");
		}else{
			$(sub34[i]).find("a").eq(0).removeClass("active");
			sub34[i].setAttribute("class","sub-menu");
		}
	}  	  	
// 	初审
	var sub35 =$("#sub35 li");
	for(var i=0;i<sub35.length;i++){
		if(obj==sub35[i].getAttribute("value")){
			$("#menu8").addClass("active");
			$("#branaud").addClass("active");
			$("#sub8").attr("style","display:block");
			$("#sub35").attr("style","display:block");
			$(sub35[i]).find("a").eq(0).addClass("active");
			sub35[i].setAttribute("class","sub-menu active");
		}else{
			$(sub35[i]).find("a").eq(0).removeClass("active");
			sub35[i].setAttribute("class","sub-menu");
		}
	}  	  	
// 	编码中心复核
	var recheck =$("#recheck li");
	for(var i=0;i<recheck.length;i++){
		if(obj==recheck[i].getAttribute("value")){
			$("#menu8").addClass("active");
			$("#sub6").attr("style","display:block");
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

};