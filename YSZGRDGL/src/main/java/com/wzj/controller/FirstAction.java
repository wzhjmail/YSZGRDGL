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
	// ����ϵͳ��ҳ "/login"����˼��jspҳ�棬�����.action ���ض���
	@RequestMapping("/first")
	//@RequiresPermissions("item:query")//ӵ��item:queryȨ�޲������иķ���
	public String first() throws Exception {
		//��֤�ɹ�����activeUser�������session
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		Session session = subject.getSession();
		session.setAttribute("activeUser",activeUser);
		/*���ݲ�ͬ�Ľ�ɫ��ת����ͬ��ҳ��
		 * // shiro��session��ȡ��������û���¼���userrole
		String userType = (String) session.getAttribute("usertype");
		// �����û���¼��ݽ���ҳ����ת
		if(userType.equals("��ҵ�û�")){
			
		}else if(userType.equals("�������û�")){
			
		}else if(userType.equals("ר��")){
			
		}else if(userType.equals("�����û�")){
			
		}*/
		return "succ";
	}
	@RequestMapping("/zjry")
	public String zjUser(HttpServletRequest request,Model model){
		AddLog.addLog(Log.QUERY,"��ѯ����ר���û���Ϣ");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId", branchId);
		return "userManage/zjManage";
	}
	@RequestMapping("/zxry")
	public String zxUser(HttpServletRequest request,Model model){
		AddLog.addLog(Log.QUERY,"��ѯ���������û���Ϣ");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId", branchId);
		return "userManage/user";
	}
	@RequestMapping("/second")
	public String second(HttpServletRequest request,Model model){
		AddLog.addLog(Log.QUERY,"��ѯ���з�֧�����û���Ϣ");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId", branchId);
		return "userManage/fzjgUserManage";
	}
	@RequestMapping("/addFZXYH")
	public String addFZXYH(HttpServletRequest request,Model model){
		String name=request.getParameter("name");
		String cid=request.getParameter("cid");
		AddLog.addLog(Log.ADD,"�����Ա'"+name+"'����֧�����û���Ϣ��");
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
		AddLog.addLog(Log.ADD,"�����Ա'"+name+"'����֧�����û���Ϣ��");
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
		AddLog.addLog(Log.ADD,"�����Ա'"+name+"'����֧�����û���Ϣ��");
		model.addAttribute("name", name);
		model.addAttribute("cid", cid);
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId", branchId);
		return "userManage/user";
	}
	@RequestMapping("/fzjgUserManage")
	public String fzjgUserManage(){
		AddLog.addLog(Log.QUERY,"��ѯ���з�֧������Ա��Ϣ");
		return "common/fzjgUserManage";
	}
	@RequestMapping("/third")
	public String third(){
		AddLog.addLog(Log.QUERY,"��ѯ����ר���û���Ϣ");
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
		//��ȡ��¼�û���Ȩ��
		List<SysPermission> pms=pmsService.getPermissionsByUserId(userId);
		List<Integer> ids = new ArrayList<>();
		for(SysPermission sys:pms){
			ids.add(sys.getId());
		}
		//����Ȩ��Id��ѯMenu
		List<SysPermission> menus=pmsService.getMenusByIds(ids);
		//����menu��ѯpermission
		List<Object> lists=new ArrayList<Object>();
		
		for(SysPermission menu:menus){//ѭ��һ����ǩ
			if(menu.getType().equals("menu")){
				List<Object> list=new ArrayList<>();
				list.add(menu);
				List<SysPermission> syspms=pmsService.getPmsByMenuId(menu.getId());
				for(SysPermission sys:syspms){//ѭ��������ǩ
					if(ids.contains(sys.getId())){//���sys��Ȩ�������û�
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
