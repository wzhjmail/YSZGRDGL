/*智能上下文菜单插 */
(function($) {
	var D = $(document).data("func", {});	
	$.smartMenu = $.noop;
	$.fn.smartMenu = function(data, options) {
		var B = $("body"), defaults = {
			name: "",
			offsetX: 2,
			offsetY: 2,
			textLimit: 6,
			beforeShow: $.noop,
			afterShow: $.noop
		};
		var params = $.extend(defaults, options || {});
		
		var htmlCreateMenu = function(datum) {
			var dataMenu = datum || data, nameMenu = datum? Math.random().toString(): params.name, htmlMenu = "", htmlCorner = "", clKey = "smart_menu_";
			if ($.isArray(dataMenu) && dataMenu.length) {
				htmlMenu = '<div id="smartMenu_'+ nameMenu +'" class="'+ clKey +'box">' +
								'<div class="'+ clKey +'body">' +
									'<ul class="'+ clKey +'ul">';
									
				$.each(dataMenu, function(i, arr) {
					if (i) {
						htmlMenu = htmlMenu + '<li class="'+ clKey +'li_separate">&nbsp;</li>';	
					}
					if ($.isArray(arr)) {
						$.each(arr, function(j, obj) {
							var text = obj.text, htmlMenuLi = "", strTitle = "", rand = Math.random().toString().replace(".", "");
							if (text) {
								if (text.length > params.textLimit) {
									text = text.slice(0, params.textLimit)	+ "�?";
									strTitle = ' title="'+ obj.text +'"';
								}
								if ($.isArray(obj.data) && obj.data.length) {
									htmlMenuLi = '<li class="'+ clKey +'li" data-hover="true">' + htmlCreateMenu(obj.data) +
										'<a href="javascript:" class="'+ clKey +'a"'+ strTitle +' data-key="'+ rand +'"><i class="'+ clKey +'triangle"></i>'+ text +'</a>' + 
									'</li>';
								} else {
									htmlMenuLi = '<li class="'+ clKey +'li">' +
										'<a href="javascript:" class="'+ clKey +'a"'+ strTitle +' data-key="'+ rand +'">'+ text +'</a>' + 
									'</li>';
								}
								
								htmlMenu += htmlMenuLi;
								
								var objFunc = D.data("func");
								objFunc[rand] = obj.func;
								D.data("func", objFunc);
							}
						});	
					}
				});
				
				htmlMenu = htmlMenu + '</ul>' +
									'</div>' +
								'</div>';
			}
			return htmlMenu;
		}, funSmartMenu = function() {
			var idKey = "#smartMenu_", clKey = "smart_menu_", jqueryMenu = $(idKey + params.name);
			if (!jqueryMenu.size()) {
				$("body").append(htmlCreateMenu());
				
				//事件
				$(idKey + params.name +" a").bind("click", function() {
					var key = $(this).attr("data-key"),
						callback = D.data("func")[key];
					if ($.isFunction(callback)) {
						callback.call(D.data("trigger"));	
					}
					$.smartMenu.hide();
					return false;
				});
				$(idKey + params.name +" li").each(function() {
					var isHover = $(this).attr("data-hover"), clHover = clKey + "li_hover";
					
					$(this).hover(function() {
						var jqueryHover = $(this).siblings("." + clHover);
						jqueryHover.removeClass(clHover).children("."+ clKey +"box").hide();
						jqueryHover.children("."+ clKey +"a").removeClass(clKey +"a_hover");
						
						if (isHover) {					
							$(this).addClass(clHover).children("."+ clKey +"box").show();
							$(this).children("."+ clKey +"a").addClass(clKey +"a_hover");	
						}
						
					});
					
				});
				return $(idKey + params.name);
			} 
			return jqueryMenu;
		};
		
		$(this).each(function() {
			this.oncontextmenu = function(e) {
				//回调
				if ($.isFunction(params.beforeShow)) {
					params.beforeShow.call(this);	
				}
				e = e || window.event;
				//阻止冒泡
				e.cancelBubble = true;
				if (e.stopPropagation) {
					e.stopPropagation();
				}
				//隐藏当前上下文菜单，确保页面上一次只有一个上下文菜单
				$.smartMenu.hide();
				var st = D.scrollTop();
				var jqueryMenu = funSmartMenu();
				if (jqueryMenu) {
					jqueryMenu.css({
						display: "block",
						left: e.clientX + params.offsetX,
						top: e.clientY + st + params.offsetY
					});
					D.data("target", jqueryMenu);
					D.data("trigger", this);
					//回调
					if ($.isFunction(params.afterShow)) {
						params.afterShow.call(this);	
					}
					return false;
				}
			};
		});
		if (!B.data("bind")) {
			B.bind("click", $.smartMenu.hide).data("bind", true);
		}
	};
	$.extend($.smartMenu, {
		hide: function() {
			var target = D.data("target");
			if (target && target.css("display") === "block") {
				target.hide();
			}		
		},
		remove: function() {
			var target = D.data("target");
			if (target) {
				target.remove();
			}
		}
	});
})(jQuery);


/*alert效果*/
var wzj = new function() {
	this.width = $(window).width() * 0.3;
	this.height = 172;

	this.close = function() {
		$('.win iframe').fadeOut();
		$('.win').fadeOut("fast");
		setTimeout(function() {
			$('.win iframe').remove();
			$('.win').remove();
		}, 200);
	};
	function messageBox(html, title, message, type) {
		var jq = $(html);
		if(type == "toast") {
			jq.find(".window-panel").width(message.length * 20).css("margin-left", -message.length * 20 / 2).css("margin-top", -wzj.height / 2);
		} else {
			jq.find(".window-panel").width(wzj.width).css("margin-left", -wzj.width / 2).css("margin-top", -wzj.height / 2 - 36);
		}
		if(valempty(title)) {
			jq.find(".title").remove();
			jq.find(".window-panel .body-panel").css("border-radius", "4px");
		} else {
			jq.find(".title").find(":header").html(title);
		}
		jq.find(".content").html(message.replace('\r\n', '<br/>'));
		jq.appendTo('body').fadeIn("fast");
		$(".win .w-btn:first").focus();
	}

	this.confirm = function(title, message, selected) {
		this._close = function(flag) {
			if(flag) {
				$(".win").remove();
				selected(flag);
			} else {
				this.close();
			};
		};

		var html = '<div class="win"><div class="mask-layer"></div><div class="window-panel"><iframe class="title-panel" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe><div class="title"><h3 style="margin-left: 10px;margin-top: 10px;"></h3></div><div class="body-panel"><p class="content"></p><p class="btns"><button class="w-btn" tabindex="1" onclick="wzj._close(false);">取消</button><button class="w-btn" onclick="wzj._close(true);">确定</button></p></div></div></div>';
		messageBox(html, title, message);
	};

	this.alert = function(title, message, ico) {
		var icon = "";
		if(!valempty(ico)) {
			icon = '<p class="btns" style="margin-bottom:-15px;"><img width="70px" height="70px" src="images/' + ico + '.png"></p>';
		}
		var html = '<div class="win"><div class="mask-layer"></div><div class="window-panel"><iframe class="title-panel" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe><div class="title"><h3 style="margin-left: 10px;margin-top: 10px;"></h3></div><div class="body-panel">' + icon + '<p class="content"></p><p class="btns"><button class="w-btn" tabindex="1" onclick="wzj.close();">确定</button></p></div></div></div>';
		messageBox(html, title, message);
	}
};

function valempty(str) {
	if(str == "null" || str == null || str == "" || str == "undefined" || str == undefined || str == 0) {
		return true;
	} else {
		return false;
	}
}