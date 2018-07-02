package com.wzj.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Log;
import com.wzj.pojo.SysPermission;
import com.wzj.service.impl.SysPermissionService;
import com.wzj.util.AddLog;

@Controller
public class FirstAction {
	@Autowired
	private SysPermissionService pmsService;
	// 返回系统首页 "/login"的意思是jsp页面，如果是.action 用重定向
	@RequestMapping("/first")
	//@RequiresPermissions("item:query")//拥有item:query权限才能运行改方法
	public String first() throws Exception {
		//认证成功，将activeUser对象存入session
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		Session session = subject.getSession();
		session.setAttribute("activeUser",activeUser);
		/*根据不同的角色跳转到不同的页面
		 * // shiro的session中取出存入的用户登录身份userrole
		String userType = (String) session.getAttribute("usertype");
		// 根据用户登录身份进行页面跳转
		if(userType.equals("企业用户")){
			
		}else if(userType.equals("分中心用户")){
			
		}else if(userType.equals("专家")){
			
		}else if(userType.equals("中心用户")){
			
		}*/
		return "succ";
	}
	@RequestMapping("/zjry")
	public String zjUser(HttpServletRequest request,Model model){
		AddLog.addLog(Log.QUERY,"查询所有专家用户信息");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId", branchId);
		return "userManage/zjManage";
	}
	@RequestMapping("/zxry")
	public String zxUser(HttpServletRequest request,Model model){
		AddLog.addLog(Log.QUERY,"查询所有中心用户信息");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId", branchId);
		return "userManage/user";
	}
	@RequestMapping("/second")
	public String second(HttpServletRequest request,Model model){
		AddLog.addLog(Log.QUERY,"查询所有分支机构用户信息");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId", branchId);
		return "userManage/fzjgUserManage";
	}
	@RequestMapping("/addFZXYH")
	public String addFZXYH(HttpServletRequest request,Model model){
		String name=request.getParameter("name");
		String cid=request.getParameter("cid");
		AddLog.addLog(Log.ADD,"添加人员'"+name+"'到分支机构用户信息中");
		model.addAttribute("name", name);
		model.addAttribute("cid", cid);
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId", branchId);
		return "userManage/fzjgUserManage";
	}
	@RequestMapping("/addZJ")
	public String addZj(HttpServletRequest request,Model model){
		String name=request.getParameter("name");
		String cid=request.getParameter("cid");
		AddLog.addLog(Log.ADD,"添加人员'"+name+"'到分支机构用户信息中");
		model.addAttribute("name", name);
		model.addAttribute("cid", cid);
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId", branchId);
		return "userManage/zjManage";
	}
	@RequestMapping("/addZXYH")
	public String addZXYH(HttpServletRequest request,Model model){
		String name=request.getParameter("name");
		String cid=request.getParameter("cid");
		AddLog.addLog(Log.ADD,"添加人员'"+name+"'到分支机构用户信息中");
		model.addAttribute("name", name);
		model.addAttribute("cid", cid);
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId", branchId);
		return "userManage/user";
	}
	@RequestMapping("/fzjgUserManage")
	public String fzjgUserManage(){
		AddLog.addLog(Log.QUERY,"查询所有分支机构人员信息");
		return "common/fzjgUserManage";
	}
	@RequestMapping("/third")
	public String third(){
		AddLog.addLog(Log.QUERY,"查询所有专家用户信息");
		return "userManage/zjManage";
	}
	@RequestMapping("/forth")
	public String forth(){
		return "userManage/qyUserManage";
	}
	
	@RequestMapping("/showMenu")
	@ResponseBody
	public Object showMenu(HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		int userId=activeUser.getUserid();
		//获取登录用户的权限
		List<SysPermission> pms=pmsService.getPermissionsByUserId(userId);
		List<Integer> ids = new ArrayList<>();
		for(SysPermission sys:pms){
			ids.add(sys.getId());
		}
		//根据权限Id查询Menu
		List<SysPermission> menus=pmsService.getMenusByIds(ids);
		//根据menu查询permission
		List<Object> lists=new ArrayList<Object>();
		
		for(SysPermission menu:menus){//循环一级标签
			if(menu.getType().equals("menu")){
				List<Object> list=new ArrayList<>();
				list.add(menu);
				List<SysPermission> syspms=pmsService.getPmsByMenuId(menu.getId());
				for(SysPermission sys:syspms){//循环二级标签
					if(ids.contains(sys.getId())){//如果sys的权限属于用户
						List<Object> list2=new ArrayList<>();
						list2.add(sys);
						if(sys.getType().equals("menu1"))
							list2.addAll(pmsService.getPmsByMenuId(sys.getId()));
						list.add(list2);
					}
				}
				lists.add(list);
			}
		}
		return lists;
	}
	@RequestMapping("/getPermission")
	@ResponseBody
	public SysPermission getPermission(String url){
		return pmsService.getPermissionByUrl(url);
	}
	@RequestMapping("first1.action")
	public String first1(){
		return "succ";
	}
	
}
