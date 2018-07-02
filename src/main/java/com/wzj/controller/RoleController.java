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
	//@RequiresPermissions("role:create")//ӵ��roleȨ�޲������иķ���
	public String ToAddRoleJSP(){
		/*�ڳ������ж��û��Ƿ��ǽ�ɫ����ӵ��ĳһȨ��
		Subject cu = SecurityUtils.getSubject();
		if(cu.hasRole("rfindRoleByUserIdole")) {}else{}
		if(cu.isPermitted("role:create")){}else{}*/
		return "addRole";
	}
	
	@RequestMapping("/find")
	public String find(){//��ת����ɫ�б�
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
		AddLog.addLog(Log.QUERY,"��ѯ���еĽ�ɫ");
		List<SysRole> list=sysRoleService.findAll();
		return sysRoleService.findAll();
	}
	
	@RequestMapping("/findRoles")
	@ResponseBody
	public List<SysRole> findRoles(){//ע��ʱ�鿴���еĽ�ɫ
		return sysRoleService.findAll();
	}
	
	/*@RequestMapping("/register")
	@ResponseBody
	public Object add(SysRoleDTO sysRoleDTO){
		String name = sysRoleDTO.getName();
		//�ڽ�ɫ�������
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
		//�ڽ�ɫ���и��ݽ�ɫ����ѯ��ɫ
		SysRole role = sysRoleService.findByName(name);
		String[] permissions = sysRoleDTO.getPermissions();
		SysRolePermission srp = new SysRolePermission();
		srp.setRoleId(Integer.parseInt(role.getId()));
		//����Role��id��Ȩ�޵�idά����ϵ
		for(int i=0;i<permissions.length;i++){
			SysPermission pms = sysPermissionService.findByName(permissions[i]);
			srp.setPmsId(pms.getId());
			sysRoleService.addPermission(srp);
		}
		//�ڽ�ɫ-Ȩ�ޱ������
		if(result>0){
			AddLog.addLog(Log.ADD,"���\""+name+"\"��ɫ��Ϣ");
			return role;
		}
		return "error";
	}*/
	
	@RequestMapping("/register")
	@ResponseBody
	public Object add2(SysRoleDTO sysRoleDTO){
		String name = sysRoleDTO.getName();
		AddLog.addLog(Log.ADD,"����½�ɫ'"+name+"'");
		//�ڽ�ɫ�������
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
		//�ڽ�ɫ���и��ݽ�ɫ����ѯ��ɫ
		SysRole role = sysRoleService.findByName(name);
		int[] pmsid = sysRoleDTO.getPmsid();
		SysRolePermission srp = new SysRolePermission();
		srp.setRoleId(Integer.parseInt(role.getId()));
		//����Role��id��Ȩ�޵�idά����ϵ
		for(int i=0;i<pmsid.length;i++){
			srp.setPmsId(pmsid[i]);
			sysRoleService.addPermission(srp);
		}
		//�ڽ�ɫ-Ȩ�ޱ������
		if(result>0){
			AddLog.addLog(Log.ADD,"���\""+name+"\"��ɫ��Ϣ");
			return role;
		}
		return "error";
	}
	
	/*@RequestMapping("/update")
	@ResponseBody
	public String update(SysRoleDTO sysRoleDTO){
		String name = sysRoleDTO.getName();
		//�ڽ�ɫ�������
		SysRole sysRole = new SysRole();
		sysRole.setId(sysRoleDTO.getId());
		sysRole.setName(name);
		sysRole.setAvailable("1");
		int result = sysRoleService.update(sysRole);
		//�ڽ�ɫ���и��ݽ�ɫ����ѯ��ɫ
		SysRole role = sysRoleService.findByName(name);
		String[] permissions = sysRoleDTO.getPermissions();
		SysRolePermission srp = new SysRolePermission();
		srp.setRoleId(Integer.parseInt(role.getId()));
		//��ս�ɫ��Ȩ�޵Ĺ�ϵ
		sysRoleService.deleteAllPms(Integer.parseInt(role.getId()));
		//����Role��id��Ȩ�޵�idά����ϵ
		for(int i=0;i<permissions.length;i++){
			SysPermission pms = sysPermissionService.findByName(permissions[i]);
			srp.setPmsId(pms.getId());
			sysRoleService.addPermission(srp);
		}
		//�ڽ�ɫ-Ȩ�ޱ������
		if(result>0){
			AddLog.addLog(Log.MODIFY,"�޸�\""+name+"\"��ɫ��Ϣ");
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
		
		//�ڽ�ɫ���и���
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
		//��ս�ɫ��Ȩ�޵Ĺ�ϵ
		sysRoleService.deleteAllPms(Integer.parseInt(roleId));
		//����Role��id��Ȩ�޵�idά����ϵ
		for(int i=0;i<pmsid.length;i++){
			srp.setPmsId(pmsid[i]);
			sysRoleService.addPermission(srp);
		}
		//�ڽ�ɫ-Ȩ�ޱ������
		if(result>0){
			AddLog.addLog(Log.MODIFY,"�޸Ľ�ɫ'"+name+"'����Ϣ");
			return "succ";
		}
		return "error";
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	@Transactional
	public String deleteById(String id){
		SysRole findRoleById = sysRoleService.findRoleById(Integer.parseInt(id));
		AddLog.addLog(Log.DELETE,"ɾ����ɫ'"+findRoleById.getName()+"'����Ϣ");
		//ɾ����ɫ���еĽ�ɫ���
		int result = sysRoleService.deleteById(Integer.parseInt(id));
		//ɾ����ɫ-�û��м��Ľ�ɫ���
		//sysRoleService.deleteRoleInUserByRoleId(Integer.parseInt(id));
		//ɾ��Ȩ��-��ɫ�м��Ľ�ɫ���
		permissionService.deleteRolePerByRoleId(Integer.parseInt(id));
		return String.valueOf(result);
	}
	@RequestMapping("/findPmsByRoleId")
	@ResponseBody
	public List<SysPermission> findPmsByRoleId(Integer editId){
		SysRole findRoleById = sysRoleService.findRoleById(editId);
		AddLog.addLog(Log.QUERY,"�鿴��ɫΪ"+findRoleById.getName()+"������Ȩ��");
		return sysRoleService.findPmsByRoleId(editId);
	}
	
	@RequestMapping("/getPmsByRoleId")
	@ResponseBody
	public Object getPmsByRoleId(int roleId){//���ݽ�ɫ��ȡ��״�ṹ
		if(roleId==0){
			AddLog.addLog(Log.QUERY,"�鿴����Ȩ��");
		}else{
			SysRole findRoleById = sysRoleService.findRoleById(roleId);
			AddLog.addLog(Log.QUERY,"�鿴��ɫΪ'"+findRoleById.getName()+"'������Ȩ��");
		}
		//��ѯ����ɫ������Ȩ��
		List<SysPermission> rolePms=new ArrayList<>();
		if(roleId==0){
			rolePms=sysPermissionService.findAll();
		}else{
			rolePms=sysRoleService.findPmsByRoleId(roleId);
		}
		 List<Integer> ids = new ArrayList<>();
		 for(SysPermission sys:rolePms){//��ȡȨ�޵�id
			ids.add(sys.getId());
		 }
		 //��ѯһ���˵�
		 List<SysPermission> menus=sysPermissionService.getMenus();
		 List<Object> lists=new ArrayList<Object>();
		 for(SysPermission menu:menus){//ѭ��һ����ǩ
			 List<Object> list=new ArrayList<>();
			 boolean bool1=false;
			 List<SysPermission> pms1=sysPermissionService.getPmsByMenuId(menu.getId());
			 for(SysPermission pms:pms1){//ѭ��������ǩ
				 List<Object> list2=new ArrayList<>();
				 boolean bool2=false;
				 if(pms.getType().equals("menu1")){//�����������ǩ
					List<SysPermission> list3=sysPermissionService.getPmsByMenuId(pms.getId());
					for(SysPermission p:list3){//ѭ�������Ƿ�ѡ��
						if(ids.contains(p.getId())){
							p.setSelected(true);
							bool2=true;//�������ѡ�еģ��������ǩΪѡ��
						}else{
							p.setSelected(false);
						}
					}
					list2.add(list3);
				 }else{//���������ǩ��Ȩ�ޣ��жϸñ�ǩ�Ƿ�ѡ��
					 bool2=ids.contains(pms.getId());
				 }
				 if(bool2==true)//���������ǩ����ѡ�е�
						bool1=true;//��һ����ǩҲѡ��
				 pms.setSelected(bool2);
				 list2.add(pms);
				 list.add(list2);	 
			 }
			 if(ids.contains(menu.getId())){//���menu�д��ڸ�Ȩ��
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
		AddLog.addLog(Log.QUERY,"��ѯ��ɫ'"+role.getName()+"'����Ϣ");
		return role;
	}
	@RequestMapping("/findRoleByUserId")
	@ResponseBody
	public List<SysRole> findRoleByUserId(Integer userId){
		SysUser byUserId = sysUserService.getByUserId(userId);
		AddLog.addLog(Log.QUERY,"��ѯ'"+byUserId.getDept()+"':'"+byUserId.getUsername()+"'�Ľ�ɫ");
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
		AddLog.addLog(Log.EXPORT,"�������еĽ�ɫ��Ϣ");
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("export/role");
		String title = "��ɫ��Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		//��һ�������Ǳ������Ƽ�����
		boolean result = sysRoleService.exportRecord(title,filePath);
		if(!result){//�������
			return;
		}
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE������IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			InputStream in = new FileInputStream(new File(filePath));
			//�������ص���Ӧͷ
		    response.setHeader("content-disposition","attachment;fileName="+fileName);
		    response.setCharacterEncoding("UTF-8");
		    //��ȡresponse�ֽ���
		    OutputStream out = response.getOutputStream();
		    byte[] b = new byte[1024];
		    int len = -1;
		    while((len=in.read(b))!=-1){//��������������ѭ��д�뵽�����
		    	out.write(b,0,len);
		    }
			//�ر�
		    out.close();
		    in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
