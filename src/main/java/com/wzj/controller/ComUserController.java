package com.wzj.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzj.DTO.SysUserDTO;
import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.ComUser;
import com.wzj.pojo.Division;
import com.wzj.pojo.Log;
import com.wzj.pojo.SysUser;
import com.wzj.service.impl.ComUserService;
import com.wzj.service.impl.DivisionService;
import com.wzj.service.impl.SysUserService;
import com.wzj.util.AddLog;

@Controller
@RequestMapping("/comUser")
public class ComUserController {
	@Autowired
	private ComUserService comUserService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DivisionService divisionService;
	@RequestMapping("test")
	@ResponseBody
	public Object test(){
		return comUserService.getByUserIds("1,2");
	}
	@RequestMapping("/audit")
	@ResponseBody
	public int audit(int id,int locked){
		int flag=0;
		ComUser byUserId = comUserService.getByUserId(id);
		int result=comUserService.audit(id,locked);
		if(locked==0&&result==1){
			AddLog.addLog(Log.MODIFY,"激活'"+byUserId.getDept()+"':'"+byUserId.getUsername()+"'的信息");
			flag=1;//激活
		}else if(locked==1&&result==1){
			AddLog.addLog(Log.MODIFY,"禁用'"+byUserId.getDept()+"':'"+byUserId.getUsername()+"'的信息");
			flag=0;//禁用
		}
		return flag;
	}
	@RequestMapping("/findAll")
	@ResponseBody
	public Object findAll(String ramusCenter,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session){
		String code="";
		if(!StringUtils.isEmpty(sSearch)){
			Division division=divisionService.getDivisionLikeName(sSearch);
			if(division!=null)
				code=division.getDivisioncode();
		}
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength);
		List<ComUser> users;
		if(StringUtils.isEmpty(sSearch)){
			if("0000".equals(ramusCenter)){
				users = comUserService.findAll();
			}else{
				users = comUserService.findAllUser(ramusCenter);
			}
		}else{
			if(!code.equals(""))
				sSearch=code;
			users = comUserService.findByCondition(sSearch,ramusCenter);
		}
		int lable=1;
		for (ComUser comUser : users) {
			comUser.setUserLable(lable);
			Division byCode = divisionService.getDivisionByCode(comUser.getRamusCenter());
			if(byCode!=null){
				comUser.setRamusCenter(byCode.getDivisionname());
			}else{
				comUser.setRamusCenter("");
			}
			lable++;
		}
		//进行分页配置
        PageInfo<ComUser> pageInfo = new PageInfo<ComUser>(users);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	@RequestMapping("/findZj")
	@ResponseBody
	public Object findZj(int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session){
		String code="";
		if(!StringUtils.isEmpty(sSearch)){
			Division division=divisionService.getDivisionLikeName(sSearch);
			if(division!=null)
				code=division.getDivisioncode();
		}
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId =activeUser.getRamusCenter();
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        ComUser com=new ComUser();
        com.setDept("专家用户");
        com.setRamusCenter(branchId);
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength);
		List<ComUser> users;
		if(StringUtils.isEmpty(sSearch)){
			if("0000".equals(branchId)){
				users = comUserService.findZj(com);
			}else{
				users = comUserService.findZjByBranchId(com);
			}
		}else{
			if(!code.equals(""))
				sSearch=code;
			users = comUserService.findByCondition(sSearch,branchId);
		}
		
		for (ComUser comUser : users) {
			Division byCode = divisionService.getDivisionByCode(comUser.getRamusCenter());
			if(byCode!=null){
				comUser.setRamusCenter(byCode.getDivisionname());
			}else{
				comUser.setRamusCenter("");
			}
		}
		//进行分页配置
        PageInfo<ComUser> pageInfo = new PageInfo<ComUser>(users);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	@RequestMapping("/findAllByDept")
	@ResponseBody
	public Object findAllByDept(String ramusCenter,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session,String dept){
		String code="";
		if(!StringUtils.isEmpty(sSearch)){
			Division division=divisionService.getDivisionLikeName(sSearch);
			if(division!=null)
				code=division.getDivisioncode();
		}
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength);
		List<ComUser> users;
		if(StringUtils.isEmpty(sSearch)){
			if("0000".equals(ramusCenter)){
				if("all".equals(dept)){
					users = comUserService.findAll();
				}else{
					users=comUserService.findByDept(dept);
				}
				
			}else{
				if("all".equals(dept)){
					users = comUserService.findAllUser(ramusCenter);
				}else{
					users = comUserService.findAllUserByDept(ramusCenter,dept);
				}
			}
		}else{
			if(!code.equals("")){
				sSearch=code;
			}
			if("all".equals(dept)){
				users = comUserService.findByCondition(sSearch,ramusCenter);
			}else{
				users = comUserService.findByConditionAndDept(sSearch,ramusCenter,dept);
			}
			
		}
		int label=1;				
		for (ComUser comUser : users) {
			comUser.setUserLable(label);
			Division byCode = divisionService.getDivisionByCode(comUser.getRamusCenter());
			if(byCode!=null){
				comUser.setRamusCenter(byCode.getDivisionname());
			}else{
				comUser.setRamusCenter("");
			}
			label++;
		}
		//进行分页配置
        PageInfo<ComUser> pageInfo = new PageInfo<ComUser>(users);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	
	@RequestMapping("/findUser")
	@ResponseBody
	public Object findAll(String dept,String ramusCenter,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch){
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength);
		ComUser comUser=new ComUser();
		comUser.setDept(dept);
		comUser.setRamusCenter(ramusCenter);
		List<ComUser> users=new ArrayList<>();
		if(ramusCenter.equals("0000")){//如果所属分支机构是中心用户，则查询所有
			users = comUserService.findAllFZ(comUser);
		}else{//如果所属分支机构是分支机构，则返回：findAllByUser(comUser)
			users = comUserService.findAllByUser(comUser);
		}
		for (ComUser comUser2 : users) {
			Division byCode = divisionService.getDivisionByCode(comUser2.getRamusCenter());
			if(byCode!=null){
				comUser2.setRamusCenter(byCode.getDivisionname());
			}else{
				comUser2.setRamusCenter("");
			}
			
		}
		//进行分页配置
        PageInfo<ComUser> pageInfo = new PageInfo<ComUser>(users);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	@RequestMapping("/deleteById")
	@ResponseBody
	@Transactional
	public int deleteById(Integer id){
		//删除成功为1，删除失败为0.默认
		ComUser cUser=comUserService.getByUserId(id);
		SysUser user=sysUserService.findByCid(id);
		Division div=divisionService.getDivisionByCode(cUser.getRamusCenter());
		AddLog.addLog(Log.DELETE,"删除'"+div.getDivisionname()+"'人员：'"+cUser.getUsername()+"'的信息");
		comUserService.deleteById(id);
		if(user!=null){
			return sysUserService.deleteById(user.getId());
		}else{
			return 0;
		}
		
	} 
	
	@RequestMapping("update")
	@ResponseBody
	public int update(SysUserDTO sysUserDTO,HttpSession session){
		ComUser cUser=new ComUser();
		cUser.setId(sysUserDTO.getId());
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String brandhId =activeUser.getRamusCenter();
		String branchId1="";
		if(brandhId.equals("0000")){
			branchId1=sysUserDTO.getRamusCenter();
		}else{
			branchId1=brandhId;
		}
		Division div=divisionService.getDivisionByCode(branchId1);
		AddLog.addLog(Log.MODIFY,"修改'"+div.getDivisionname()+"'的人员"+sysUserDTO.getUsername()+"'的信息");
		cUser.setRamusCenter(branchId1);
		cUser.setUsername(sysUserDTO.getUsername());
		cUser.setGender(sysUserDTO.getGender());
		cUser.setBirthday(sysUserDTO.getBirthday());
		cUser.setMobile(sysUserDTO.getMobile());
		cUser.setEmail(sysUserDTO.getEmail());
		cUser.setPost(sysUserDTO.getPost());
		cUser.setUnit(sysUserDTO.getUnit());
		cUser.setDept(sysUserDTO.getDept());
		SysUser user=sysUserService.findByCid(sysUserDTO.getId());
		SysUser user1=new SysUser();
		if(user!=null){
//			user1.setUsercode(sysUserDTO.getUsername());
			user1.setCid(user.getCid());
			user1.setBirthday(sysUserDTO.getBirthday());
			user1.setId(user.getId());
			user1.setUsername(sysUserDTO.getUsername());
			user1.setDept(sysUserDTO.getDept());
			user1.setGender(sysUserDTO.getGender());
			user1.setEmail(sysUserDTO.getEmail());
			user1.setMobile(sysUserDTO.getMobile());
			user1.setPost(sysUserDTO.getPost());
			user1.setUnit(sysUserDTO.getUnit());
			user1.setRamusCenter(branchId1);
			sysUserService.update(user1);
		}
		return comUserService.updatefzjgUser(cUser);
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public int insert(SysUserDTO sysUserDTO,HttpSession session){//添加用户到数据库，返回的是用户ID
		ComUser cUser=new ComUser();
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String brandhId =activeUser.getRamusCenter();
		String branchId1="";
		if(brandhId.equals("0000")){
			branchId1=sysUserDTO.getRamusCenter();
		}else{
			branchId1=brandhId;
		}
		Division div=divisionService.getDivisionByCode(branchId1);
		AddLog.addLog(Log.ADD,"向'"+div.getDivisionname()+"'添加人员'"+sysUserDTO.getUsername()+"'的信息");
		cUser.setUsername(sysUserDTO.getUsername());
		cUser.setRamusCenter(branchId1);
		cUser.setGender(sysUserDTO.getGender());
		cUser.setBirthday(sysUserDTO.getBirthday());
		cUser.setMobile(sysUserDTO.getMobile());
		cUser.setEmail(sysUserDTO.getEmail());
		cUser.setPost(sysUserDTO.getPost());
		cUser.setUnit(sysUserDTO.getUnit());
		cUser.setDept(sysUserDTO.getDept());
		return comUserService.insert(cUser);//用户添加到数据库
	}
	
	@RequestMapping("findUserById")
	@ResponseBody
	public ComUser findUserById(int ID){
		ComUser cuser=comUserService.getByUserId(ID);
		AddLog.addLog(Log.QUERY,"查询'"+cuser.getUsername()+"'的用户信息");
		return cuser;
	}
	//获取当前用户所在机构的所有专家用户
	@RequestMapping("getZjUser")
	@ResponseBody
	public List<ComUser> getZjByBranchId(String branchId){
		List<ComUser> zjList=comUserService.getZjByBranchId(branchId);
		return zjList;
	}
	@RequestMapping("findUserByIds")
	@ResponseBody
	public Object findUserByIds(String ids){
		List<ComUser> users=comUserService.getByUserIds(ids);
		return users;
	}
	@RequestMapping("getZJUserByIds")
	@ResponseBody
	public Object getZJUserByIds(String ids){
		List<SysUser> users=null;
		String result="select * from com_user where id in("+ids+")";
		users=sysUserService.getZJUserByIds(result);
		return users;
	}
//	@RequestMapping("/exportRecord")
//	public void exportRecord(String dept,HttpServletRequest request, HttpServletResponse response) {
//		AddLog.addLog(Log.EXPORT,"导出所有的用户信息");
//		//获取导出的文件夹
//		String path = request.getSession().getServletContext().getRealPath("export/sysUser");
//		String title = "用户信息";
//		String fileName = title+".xlsx";
//		String filePath = path+"/"+fileName;
//		//第一个参数时sheet表格的名称及标题
//		//boolean result = sysUserService.exportRecord(title,filePath);
//		String ramusCenter=((ActiveUser)request.getSession().getAttribute("activeUser")).getRamusCenter();
//		boolean result = sysUserService.exportRecord(title,filePath,dept,ramusCenter);
//		if(!result){//如果出错
//			return;
//		}
//		try{
//			//根据不同的浏览器处理下载文件名乱码问题
//			String userAgent = request.getHeader("User-Agent");
//			//针对IE或者以IE为内核的浏览器
//			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
//				fileName = URLEncoder.encode(fileName,"UTF-8");
//			}else{//非IE浏览器的处理
//				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
//			}
//			//获取一个流
//			InputStream in = new FileInputStream(new File(filePath));
//			//设置下载的响应头
//			response.setHeader("content-disposition","attachment;fileName="+fileName);
//			response.setCharacterEncoding("UTF-8");
//			//获取response字节流
//			OutputStream out = response.getOutputStream();
//			byte[] b = new byte[1024];
//			int len = -1;
//			while((len=in.read(b))!=-1){//将输入流的内容循环写入到输出流
//				out.write(b,0,len);
//			}
//			//关闭
//			out.close();
//			in.close();
//		}catch(FileNotFoundException e){
//			e.printStackTrace();
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//	}
	
	//上传文件
//	@RequestMapping("/importFile")
//	@ResponseBody
//	public Object importFile(@RequestParam(value = "fileImport")MultipartFile file,
//			HttpServletRequest request, ModelMap model) {
//		AddLog.addLog(Log.IMPORT,"导入用户信息");
//		String path = request.getSession().getServletContext().getRealPath("upload");
//		System.out.println("路径是："+path);
//		String fileName = file.getOriginalFilename();
//		// 获取原始文件名（不包括扩展名）
//		String originalFileName = fileName.substring(0, fileName.lastIndexOf("."));
//		// 获取时间戳
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		String timeStamp = sdf.format(new Date());
//		// 新文件名
//		String newFileName = originalFileName + timeStamp
//				+ fileName.substring(fileName.lastIndexOf("."), fileName.length());
//		File targetFile = new File(path + "/excel", newFileName);
//		if (!targetFile.exists()) {
//			targetFile.mkdirs();
//		}
//		// 保存
//		try {
//			file.transferTo(targetFile);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		model.addAttribute("fileUrl", request.getContextPath() + "/upload/excel/" + newFileName);
//		// 绝对路径
//		String absolutePath = targetFile.getAbsolutePath();
//		// 调用将excel数据保存到数据库的方法
//		int result = sysUserService.importExcel(absolutePath);
//		return result;
//	}
	@RequestMapping("findByCid")
	@ResponseBody
	public ComUser findByCid(int cid){
		ComUser user=comUserService.getByUserId(cid);
		return user;
	}
}
