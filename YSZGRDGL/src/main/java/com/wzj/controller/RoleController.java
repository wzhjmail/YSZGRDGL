package com.wzj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.DTO.SysRoleDTO;
import com.wzj.pojo.Log;
import com.wzj.pojo.SysPermission;
import com.wzj.pojo.SysRole;
import com.wzj.pojo.SysRolePermission;
import com.wzj.pojo.SysUser;
import com.wzj.service.impl.PermissionService;
import com.wzj.service.impl.SysPermissionService;
import com.wzj.service.impl.SysRoleService;
import com.wzj.service.impl.SysUserService;
import com.wzj.util.AddLog;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysPermissionService sysPermissionService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private PermissionService permissionService;
	@RequestMapping("/toAddRoleJSP")
	//@RequiresPermissions("role:create")//拥有role权限才能运行改方法
	public String ToAddRoleJSP(){
		/*在程序中判断用户是否是角色或者拥有某一权限
		Subject cu = SecurityUtils.getSubject();
		if(cu.hasRole("rfindRoleByUserIdole")) {}else{}
		if(cu.isPermitted("role:create")){}else{}*/
		return "addRole";
	}
	
	@RequestMapping("/find")
	public String find(){//跳转到角色列表
		return "role";
	}
	@RequestMapping("toEditJsp")
	public String toEditJsp(String id){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute("editId",id);
		return "editRole";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public List<SysRole> findAll(){
		AddLog.addLog(Log.QUERY,"查询所有的角色");
		List<SysRole> list=sysRoleService.findAll();
		return sysRoleService.findAll();
	}
	
	@RequestMapping("/findRoles")
	@ResponseBody
	public List<SysRole> findRoles(){//注册时查看所有的角色
		return sysRoleService.findAll();
	}
	
	/*@RequestMapping("/register")
	@ResponseBody
	public Object add(SysRoleDTO sysRoleDTO){
		String name = sysRoleDTO.getName();
		//在角色表中添加
		SysRole sysRole = new SysRole();
		sysRole.setName(name);
		sysRole.setAvailable("1");
		List<SysRole> roles = findAll();
		for(SysRole role:roles){
			if(name.equals(role.getName())){
				return "rename";
			}
		}
		int result = sysRoleService.add(sysRole);
		//在角色表中根据角色名查询角色
		SysRole role = sysRoleService.findByName(name);
		String[] permissions = sysRoleDTO.getPermissions();
		SysRolePermission srp = new SysRolePermission();
		srp.setRoleId(Integer.parseInt(role.getId()));
		//根据Role的id和权限的id维护关系
		for(int i=0;i<permissions.length;i++){
			SysPermission pms = sysPermissionService.findByName(permissions[i]);
			srp.setPmsId(pms.getId());
			sysRoleService.addPermission(srp);
		}
		//在角色-权限表中添加
		if(result>0){
			AddLog.addLog(Log.ADD,"添加\""+name+"\"角色信息");
			return role;
		}
		return "error";
	}*/
	
	@RequestMapping("/register")
	@ResponseBody
	public Object add2(SysRoleDTO sysRoleDTO){
		String name = sysRoleDTO.getName();
		AddLog.addLog(Log.ADD,"添加新角色'"+name+"'");
		//在角色表中添加
		SysRole sysRole = new SysRole();
		sysRole.setName(name);
		sysRole.setAvailable("1");
		List<SysRole> roles = findAll();
		for(SysRole role:roles){
			if(name.equals(role.getName())){
				return "rename";
			}
		}
		int result = sysRoleService.add(sysRole);
		//在角色表中根据角色名查询角色
		SysRole role = sysRoleService.findByName(name);
		int[] pmsid = sysRoleDTO.getPmsid();
		SysRolePermission srp = new SysRolePermission();
		srp.setRoleId(Integer.parseInt(role.getId()));
		//根据Role的id和权限的id维护关系
		for(int i=0;i<pmsid.length;i++){
			srp.setPmsId(pmsid[i]);
			sysRoleService.addPermission(srp);
		}
		//在角色-权限表中添加
		if(result>0){
			AddLog.addLog(Log.ADD,"添加\""+name+"\"角色信息");
			return role;
		}
		return "error";
	}
	
	/*@RequestMapping("/update")
	@ResponseBody
	public String update(SysRoleDTO sysRoleDTO){
		String name = sysRoleDTO.getName();
		//在角色表中添加
		SysRole sysRole = new SysRole();
		sysRole.setId(sysRoleDTO.getId());
		sysRole.setName(name);
		sysRole.setAvailable("1");
		int result = sysRoleService.update(sysRole);
		//在角色表中根据角色名查询角色
		SysRole role = sysRoleService.findByName(name);
		String[] permissions = sysRoleDTO.getPermissions();
		SysRolePermission srp = new SysRolePermission();
		srp.setRoleId(Integer.parseInt(role.getId()));
		//清空角色和权限的关系
		sysRoleService.deleteAllPms(Integer.parseInt(role.getId()));
		//根据Role的id和权限的id维护关系
		for(int i=0;i<permissions.length;i++){
			SysPermission pms = sysPermissionService.findByName(permissions[i]);
			srp.setPmsId(pms.getId());
			sysRoleService.addPermission(srp);
		}
		//在角色-权限表中添加
		if(result>0){
			AddLog.addLog(Log.MODIFY,"修改\""+name+"\"角色信息");
			return "succ";
		}
		return "error";
	}*/
	
	@RequestMapping("/update")
	@ResponseBody
	public String update2(SysRoleDTO sysRoleDTO){
		String name = sysRoleDTO.getName();
		String oldrolename=sysRoleDTO.getOldrolename();
		String roleId=sysRoleDTO.getId();
		String belonged=sysRoleDTO.getBelonged();
		
		//在角色表中更新
		SysRole sysRole = new SysRole();
		sysRole.setId(roleId);
		sysRole.setName(name);
		sysRole.setBelonged(belonged);
		sysRole.setAvailable("1");
		List<SysRole> roles = findAll();
		for(SysRole role:roles){
			if(!name.equals(oldrolename)){
				if(name.equals(role.getName())){
					return "rename";
				}
			}
		}
		int result = sysRoleService.update(sysRole);
		int[] pmsid = sysRoleDTO.getPmsid();
		SysRolePermission srp = new SysRolePermission();
		srp.setRoleId(Integer.parseInt(roleId));
		//清空角色和权限的关系
		sysRoleService.deleteAllPms(Integer.parseInt(roleId));
		//根据Role的id和权限的id维护关系
		for(int i=0;i<pmsid.length;i++){
			srp.setPmsId(pmsid[i]);
			sysRoleService.addPermission(srp);
		}
		//在角色-权限表中添加
		if(result>0){
			AddLog.addLog(Log.MODIFY,"修改角色'"+name+"'的信息");
			return "succ";
		}
		return "error";
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	@Transactional
	public String deleteById(String id){
		SysRole findRoleById = sysRoleService.findRoleById(Integer.parseInt(id));
		AddLog.addLog(Log.DELETE,"删除角色'"+findRoleById.getName()+"'的信息");
		//删除角色表中的角色编号
		int result = sysRoleService.deleteById(Integer.parseInt(id));
		//删除角色-用户中间表的角色编号
		//sysRoleService.deleteRoleInUserByRoleId(Integer.parseInt(id));
		//删除权限-角色中间表的角色编号
		permissionService.deleteRolePerByRoleId(Integer.parseInt(id));
		return String.valueOf(result);
	}
	@RequestMapping("/findPmsByRoleId")
	@ResponseBody
	public List<SysPermission> findPmsByRoleId(Integer editId){
		SysRole findRoleById = sysRoleService.findRoleById(editId);
		AddLog.addLog(Log.QUERY,"查看角色为"+findRoleById.getName()+"的所有权限");
		return sysRoleService.findPmsByRoleId(editId);
	}
	
	@RequestMapping("/getPmsByRoleId")
	@ResponseBody
	public Object getPmsByRoleId(int roleId){//根据角色获取树状结构
		if(roleId==0){
			AddLog.addLog(Log.QUERY,"查看所有权限");
		}else{
			SysRole findRoleById = sysRoleService.findRoleById(roleId);
			AddLog.addLog(Log.QUERY,"查看角色为'"+findRoleById.getName()+"'的所有权限");
		}
		//查询本角色的所有权限
		List<SysPermission> rolePms=new ArrayList<>();
		if(roleId==0){
			rolePms=sysPermissionService.findAll();
		}else{
			rolePms=sysRoleService.findPmsByRoleId(roleId);
		}
		 List<Integer> ids = new ArrayList<>();
		 for(SysPermission sys:rolePms){//获取权限的id
			ids.add(sys.getId());
		 }
		 //查询一级菜单
		 List<SysPermission> menus=sysPermissionService.getMenus();
		 List<Object> lists=new ArrayList<Object>();
		 for(SysPermission menu:menus){//循环一级标签
			 List<Object> list=new ArrayList<>();
			 boolean bool1=false;
			 List<SysPermission> pms1=sysPermissionService.getPmsByMenuId(menu.getId());
			 for(SysPermission pms:pms1){//循环二级标签
				 List<Object> list2=new ArrayList<>();
				 boolean bool2=false;
				 if(pms.getType().equals("menu1")){//如果是三级标签
					List<SysPermission> list3=sysPermissionService.getPmsByMenuId(pms.getId());
					for(SysPermission p:list3){//循环三级是否被选中
						if(ids.contains(p.getId())){
							p.setSelected(true);
							bool2=true;//如果存在选中的，则二级标签为选中
						}else{
							p.setSelected(false);
						}
					}
					list2.add(list3);
				 }else{//如果二级标签是权限，判断该标签是否被选中
					 bool2=ids.contains(pms.getId());
				 }
				 if(bool2==true)//如果二级标签存在选中的
						bool1=true;//则一级标签也选中
				 pms.setSelected(bool2);
				 list2.add(pms);
				 list.add(list2);	 
			 }
			 if(ids.contains(menu.getId())){//如果menu中存在该权限
				 bool1=true;
			 }
			menu.setSelected(bool1);
			list.add(menu);
			lists.add(list);
		 }
		 return lists;
	}
	
	@RequestMapping("/findRoleById")
	@ResponseBody
	public SysRole findRoleById(Integer id){
		SysRole role=sysRoleService.findRoleById(id);
		AddLog.addLog(Log.QUERY,"查询角色'"+role.getName()+"'的信息");
		return role;
	}
	@RequestMapping("/findRoleByUserId")
	@ResponseBody
	public List<SysRole> findRoleByUserId(Integer userId){
		SysUser byUserId = sysUserService.getByUserId(userId);
		AddLog.addLog(Log.QUERY,"查询'"+byUserId.getDept()+"':'"+byUserId.getUsername()+"'的角色");
		String roleIds=sysRoleService.findRoleIdByUserId(userId);
		if("".equals(roleIds)){
			roleIds="010";
		}
		SysRole sysrole=new SysRole();
		sysrole.setId(roleIds);
		List<SysRole> findRoleByUserId = sysRoleService.findRoleByRoleIds(sysrole);
		return findRoleByUserId;
	}
	
	@RequestMapping("/findRole")
	@ResponseBody
	public String findRole(Integer userId){
		return sysRoleService.findRole(userId);
	}
	
	@RequestMapping("/exportRecord")
	public void exportRecord(HttpServletRequest request,HttpServletResponse response){
		AddLog.addLog(Log.EXPORT,"导出所有的角色信息");
		//获取导出的文件夹
		String path = request.getSession().getServletContext().getRealPath("export/role");
		String title = "角色信息";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		//次一个参数是表格的名称及标题
		boolean result = sysRoleService.exportRecord(title,filePath);
		if(!result){//如果出错
			return;
		}
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者以IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的响应头
		    response.setHeader("content-disposition","attachment;fileName="+fileName);
		    response.setCharacterEncoding("UTF-8");
		    //获取response字节流
		    OutputStream out = response.getOutputStream();
		    byte[] b = new byte[1024];
		    int len = -1;
		    while((len=in.read(b))!=-1){//将输入流的内容循环写入到输出流
		    	out.write(b,0,len);
		    }
			//关闭
		    out.close();
		    in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
