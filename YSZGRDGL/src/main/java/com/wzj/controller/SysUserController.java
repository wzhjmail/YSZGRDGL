package com.wzj.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzj.DTO.SysUserDTO;
import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.ComUser;
import com.wzj.pojo.Division;
import com.wzj.pojo.Log;
import com.wzj.pojo.Stu;
import com.wzj.pojo.SysUser;
import com.wzj.service.impl.BranchCenterService;
import com.wzj.service.impl.ComUserService;
import com.wzj.service.impl.DivisionService;
import com.wzj.service.impl.SysUserService;
import com.wzj.util.AddLog;
import com.wzj.util.CommonUtil;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sysUser")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private BranchCenterService branchCenterService;
	@Autowired
	private DivisionService divisionService;
	@Autowired
	private ComUserService comUserService;
	
	@RequestMapping(value="checkRegist")
	@ResponseBody
	public JSONObject checkRegist(HttpServletRequest request){
		System.out.println("ִ�з�����checkRegist");
		String usercode = request.getParameter("usercode");
		JSONObject jsonObj = new JSONObject();
		int countExist=sysUserService.getSysUserCountByUsername(usercode);
		if(countExist>=1){
			jsonObj.put("result", "false");
		}else{
			jsonObj.put("result", "true");
		}
		return jsonObj;
	}
	
	@RequestMapping(value="checkUpdate")
	@ResponseBody
	public Object checkUpdate(int userId,String userCode){
		JSONObject jsonObj=new JSONObject();
		int countExist=sysUserService.getSysUserCount(userId,userCode);
		if(countExist>=1){
			jsonObj.put("result", false);
		}else{
			jsonObj.put("result", true);
		}
		return jsonObj;
	}
	
	@RequestMapping(value="register",produces="text/html;charset=utf-8")
	@ResponseBody
	public String register(SysUserDTO sysUserDTO){
		String pwd = sysUserDTO.getPassword();
		String pwd5= CommonUtil.getInstance().bytesToMD5(pwd.getBytes());
		int locked=0;
		SysUser sysUser = new SysUser();
		sysUser.setUsercode(sysUserDTO.getUsercode());
		sysUser.setUsername(sysUserDTO.getUsername());
		sysUser.setDept(sysUserDTO.getDept());
		sysUser.setPassword(pwd5);
		sysUser.setLocked(sysUserDTO.getLocked());
		//��ȡ�û��ķ���������
		String code=sysUserDTO.getRamusCenter();
		Division divisionByCode = branchCenterService.getDivisionByCode(code);
		String branchName=divisionByCode.getDivisionname();
		int result = sysUserService.insert(sysUser);//ע��ɹ�
		SysUser user = sysUserService.findUser(sysUserDTO.getUsercode(),pwd5,locked);
		sysUserService.setRoleId(user.getId(),sysUserDTO.getRole());
		if(result==1){//�����ӳɹ�������succ����ֱ��д���¼
			AddLog.addLog(branchName,sysUserDTO.getUsercode(),Log.ADD,"����û�\""+sysUserDTO.getUsername()+"����Ϣ");
			return "username=" + sysUserDTO.getUsercode() +
					"&password=" + sysUserDTO.getPassword()+"&rememberMe=false&dept="+sysUserDTO.getDept();
		}else{
			return "error";
		}
	}
	
	@RequestMapping("/audit")
	@ResponseBody
	public int audit(int id,int locked){
		int flag=0;
		SysUser byUserId = sysUserService.getByUserId(id);
		int result=sysUserService.audit(id,locked);
		if(locked==0&&result==1){
			AddLog.addLog(Log.MODIFY,"����'"+byUserId.getDept()+"':'"+byUserId.getUsername()+"'����Ϣ");
			flag=1;//����
		}else if(locked==1&&result==1){
			AddLog.addLog(Log.MODIFY,"����'"+byUserId.getDept()+"':'"+byUserId.getUsername()+"'����Ϣ");
			flag=0;//����
		}
		return flag;
	}
	
	@RequestMapping("/toEdit")
	public String toEdit(){
		return "editUser";
	}
	@RequestMapping("/findAll")
	@ResponseBody
	public Object findAll(String dept,String ramusCenter){
		SysUser sysuser=new SysUser();
		sysuser.setDept(dept);
		sysuser.setRamusCenter(ramusCenter);
		if(ramusCenter.equals("0000")){//���������֧�����������û������ѯ����
			return sysUserService.findAllFZ(sysuser);
		}else{//���������֧�����Ƿ�֧�������򷵻أ�
			return sysUserService.findAllByUser(sysuser);
		}
	}
	@RequestMapping("/findAll2")
	@ResponseBody
	public Object findAll2(String dept,String ramusCenter,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch,HttpSession session){
		String code="";
		if(!StringUtils.isEmpty(sSearch)){
			Division division=divisionService.getDivisionLikeName(sSearch);
			if(division!=null)
				code=division.getDivisioncode();
		}
		
		//ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength);
		SysUser sysuser=new SysUser();
		sysuser.setDept(dept);
		sysuser.setRamusCenter(ramusCenter);
		List<SysUser> users;
		if(StringUtils.isEmpty(sSearch)){//�ǲ��ҹ���
			if(ramusCenter.equals("0000")){//���������֧�����������û������ѯ����
				users = sysUserService.findAllFZ(sysuser);
			}else{//���������֧�����Ƿ�֧�������򷵻أ�
				users = sysUserService.findAllByUser(sysuser);
			}
		}else{//���ҹ���
			users=sysUserService.findLike(dept,code,sSearch);
		}
		
		for (SysUser sysUser2 : users) {
			Division byCode = divisionService.getDivisionByCode(sysUser2.getRamusCenter());
			if(byCode!=null){
				sysUser2.setRamusCenter(byCode.getDivisionname());
			}else{
				sysUser2.setRamusCenter("");
			}
			
		}
      //���з�ҳ����
        PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(users);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	
	@RequestMapping("/getAllUser")
	@ResponseBody
	public Object getAllUser(String branchId){
		List<SysUser> findAll = sysUserService.findAllByBranchId(branchId);
		return findAll;
	}
	
	@RequestMapping("test")
	@ResponseBody
	public void test(){//�����л����ݿ�
		String i=sysUserService.test();
		System.out.println(i);
	}
	@RequestMapping("testFile")
	@ResponseBody
	public void testFile() throws Exception{//�����ļ����ϴ�
		File file=new File("F:/pdfʾ���ļ�.pdf");
		FileInputStream fileInputStream=new FileInputStream(file);
		int flength=(int)file.length();
		byte[] fbate=new byte[flength];
		fileInputStream.read(fbate, 0, flength);
		fileInputStream.close();
		int i=sysUserService.testFile(fbate);
		System.out.println(i);
	}
	@RequestMapping("getFile")
	@ResponseBody
	public void getFile() throws Exception{//��ȡ���ݿ��е��ļ�
		Stu stu=sysUserService.getName();
		String name=stu.getName();
		System.out.println("+++++++++++++++"+name);
		File file=new File("F:/abc.pdf");
		if(!file.exists()){
			file.createNewFile();
		}
		OutputStream outputStream=new FileOutputStream(file);
		InputStream inputStream=new ByteArrayInputStream(stu.getMfile()); 
		byte[] buffer=new byte[1024];
		int len=0;
		while((len=inputStream.read(buffer))!=-1){
			outputStream.write(buffer,0,len);
		}
		inputStream.close();
		outputStream.close();
		
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	@Transactional
	public int deleteById(Integer id){
		//ɾ���ɹ�Ϊ1��ɾ��ʧ��Ϊ0.Ĭ��
		SysUser user=sysUserService.getByUserId(id);
		AddLog.addLog(Log.DELETE,"ɾ��'"+user.getDept()+"':'"+user.getUsername()+"'����Ϣ");
		sysUserService.deleteUserRole(id);//ɾ���û�֮ǰɾ���û��ͽ�ɫ�Ĺ�ϵ
		return sysUserService.deleteById(id);
	} 
	
	@RequestMapping("update")
	@ResponseBody
	public int update(SysUserDTO sysUserDTO){
		AddLog.addLog(Log.MODIFY,"�޸�'"+sysUserDTO.getDept()+"':'"+sysUserDTO.getUsername()+"'����Ϣ");
		Boolean flag=false;
		String pwd = sysUserDTO.getPassword();
		String pwd5 = pwd;
		if(pwd.length()!=32){
			pwd5 = CommonUtil.getInstance().bytesToMD5(pwd.getBytes());
		}
		SysUser sysUser = new SysUser();
		sysUser.setId(sysUserDTO.getId());
		sysUser.setUsercode(sysUserDTO.getUsercode());
		sysUser.setUsername(sysUserDTO.getUsername());
		sysUser.setPassword(pwd5);
		sysUser.setDept(sysUserDTO.getDept());
		sysUser.setLocked(sysUserDTO.getLocked());
		sysUser.setRamusCenter(sysUserDTO.getRamusCenter());
		sysUser.setGender(sysUserDTO.getGender());
		sysUser.setBirthday(sysUserDTO.getBirthday());
		sysUser.setPhone(sysUserDTO.getPhone());
		sysUser.setMobile(sysUserDTO.getMobile());
		sysUser.setEmail(sysUserDTO.getEmail());
		sysUser.setUnit(sysUserDTO.getUnit());
		sysUser.setPost(sysUserDTO.getPost());;
		SysUser su=sysUserService.getByUserId(sysUserDTO.getId());
		sysUser.setCid(su.getCid());
		int count=sysUserService.getSysUserCountByUsername(sysUserDTO.getUsercode());
		if(!sysUserDTO.getUsercode().equals(su.getUsercode())&&count>0){
			flag=false;
		}else{
			flag=true;
		}
		if(flag){
			String role=sysUserDTO.getRole();
			if(!StringUtils.isEmpty(role)){
				int re = sysUserService.updateRoleId(sysUser.getId(),role);
				if(re==0){//���sys_user_role��û�и��û�����Ϣ����ֱ�Ӳ���
					sysUserService.setRoleId(sysUser.getId(),sysUserDTO.getRole());
				}
			}
			return sysUserService.update(sysUser);
		}else{
			return 0;
		}
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public int insert(SysUserDTO sysUserDTO,HttpSession session){//����û������ݿ⣬���ص����û�ID
		AddLog.addLog(Log.ADD,"���'"+sysUserDTO.getDept()+"':'"+sysUserDTO.getUsername()+"'����Ϣ");
		Boolean flag=false;
		SysUser user=null;
		String pwd = sysUserDTO.getPassword();
		String pwd5 = CommonUtil.getInstance().bytesToMD5(pwd.getBytes());
		SysUser sysUser = new SysUser();
		sysUser.setUsercode(sysUserDTO.getUsercode());
		sysUser.setUsername(sysUserDTO.getUsername());
		sysUser.setPassword(pwd5);
		sysUser.setRole(sysUserDTO.getRole());
		sysUser.setDept(sysUserDTO.getDept());
		sysUser.setLocked(sysUserDTO.getLocked());
		
		sysUser.setCid(sysUserDTO.getId());
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String brandhId =activeUser.getRamusCenter();
		String branchId1="";
		if(brandhId.equals("0000")){
			branchId1=sysUserDTO.getRamusCenter();
		}else{
			branchId1=brandhId;
		}
		sysUser.setRamusCenter(branchId1);
		sysUser.setGender(sysUserDTO.getGender());
		sysUser.setBirthday(sysUserDTO.getBirthday());
		sysUser.setPhone(sysUserDTO.getPhone());
		sysUser.setMobile(sysUserDTO.getMobile());
		sysUser.setEmail(sysUserDTO.getEmail());
		sysUser.setUnit(sysUserDTO.getUnit());;
		sysUser.setPost(sysUserDTO.getPost());;
		int count=sysUserService.getSysUserCountByUsername(sysUserDTO.getUsercode());
		if(count!=0){
			flag=false;
		}else{
			flag=true;
		}
		if(flag){
			sysUserService.insert(sysUser);//�û���ӵ����ݿ�
			user = sysUserService.getByUserName(sysUserDTO.getUsercode());
			sysUserService.setRoleId(user.getId(), sysUserDTO.getRole());
//			return user.getId();
			ComUser cUser=new ComUser();
			cUser.setLocked(sysUserDTO.getLocked());
			cUser.setUsername(sysUserDTO.getUsername());
			cUser.setDept(sysUserDTO.getDept());
			cUser.setEmail(sysUserDTO.getEmail());
			cUser.setId(sysUserDTO.getId());
			cUser.setPhone(sysUserDTO.getPhone());
//			cUser.setPost(sysUserDTO.getPost());
//			cUser.setUnit(sysUserDTO.getUnit());
			cUser.setGender(sysUserDTO.getGender());
			cUser.setUsercode(sysUserDTO.getUsercode());
			cUser.setPassword(pwd5);
			cUser.setMobile(sysUserDTO.getMobile());
			cUser.setBirthday(sysUserDTO.getBirthday());
			cUser.setRamusCenter(sysUserDTO.getRamusCenter());
			return comUserService.update(cUser);
		}else{
			return 0;
		}
		
	}
	
	@RequestMapping("insertZj")
	@ResponseBody
	public int insertZj(SysUserDTO sysUserDTO,HttpSession session){//����û������ݿ⣬���ص����û�ID
		AddLog.addLog(Log.ADD,"���'"+sysUserDTO.getDept()+"':'"+sysUserDTO.getUsername()+"'����Ϣ");
		Boolean flag=false;
		SysUser user=null;
		String pwd = sysUserDTO.getPassword();
		String pwd5 = CommonUtil.getInstance().bytesToMD5(pwd.getBytes());
		SysUser sysUser = new SysUser();
		sysUser.setUsercode(sysUserDTO.getUsercode());
		sysUser.setUsername(sysUserDTO.getUsername());
		sysUser.setPassword(pwd5);
		sysUser.setRole(sysUserDTO.getRole());
		sysUser.setDept(sysUserDTO.getDept());
		sysUser.setLocked(sysUserDTO.getLocked());
		
		sysUser.setCid(sysUserDTO.getId());
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String brandhId =activeUser.getRamusCenter();
		String branchId1="";
		if(brandhId.equals("0000")){
			branchId1=sysUserDTO.getRamusCenter();
		}else{
			branchId1=brandhId;
		}
		sysUser.setRamusCenter(branchId1);
		sysUser.setGender(sysUserDTO.getGender());
		sysUser.setBirthday(sysUserDTO.getBirthday());
		sysUser.setPhone(sysUserDTO.getPhone());
		sysUser.setMobile(sysUserDTO.getMobile());
		sysUser.setEmail(sysUserDTO.getEmail());
		sysUser.setUnit(sysUserDTO.getUnit());;
		sysUser.setPost(sysUserDTO.getPost());;
		int count=sysUserService.getSysUserCountByUsername(sysUserDTO.getUsercode());
		if(count!=0){
			flag=false;
		}else{
			flag=true;
		}
		if(flag){
			sysUserService.insert(sysUser);//�û���ӵ����ݿ�
			user = sysUserService.getByUserName(sysUserDTO.getUsercode());
			sysUserService.setRoleId(user.getId(), sysUserDTO.getRole());
//			return user.getId();
			ComUser cUser=new ComUser();
			cUser.setLocked(sysUserDTO.getLocked());
			cUser.setUsername(sysUserDTO.getUsername());
			cUser.setDept(sysUserDTO.getDept());
			cUser.setEmail(sysUserDTO.getEmail());
			cUser.setId(sysUserDTO.getId());
			cUser.setPhone(sysUserDTO.getPhone());
			cUser.setPost(sysUserDTO.getPost());
			cUser.setUnit(sysUserDTO.getUnit());
			cUser.setGender(sysUserDTO.getGender());
			cUser.setUsercode(sysUserDTO.getUsercode());
			cUser.setPassword(pwd5);
			cUser.setMobile(sysUserDTO.getMobile());
			cUser.setBirthday(sysUserDTO.getBirthday());
			cUser.setRamusCenter(sysUserDTO.getRamusCenter());
			return comUserService.updatezj(cUser);
		}else{
			return 0;
		}
		
	}
	@RequestMapping("findUserById")
	@ResponseBody
	public SysUser findUserById(int ID){
		SysUser user=sysUserService.getByUserId(ID);
		AddLog.addLog(Log.QUERY,"��ѯ'"+user.getDept()+"':'"+user.getUsername()+"'����Ϣ");
		String branchNo=user.getRamusCenter();
		Division allDivision = divisionService.getDivisionByCode(branchNo);
		String address = allDivision.getDivisionaddress();
		user.setSalt(address);
		return user;
	}
	@RequestMapping("findUserByIds")
	@ResponseBody
	public Object findUserByIds(String ids){
		List<SysUser> users=sysUserService.getByUserIds(ids);
		return users;
	}
	@RequestMapping("getZJUserByIds")
	@ResponseBody
	public Object getZJUserByIds(String ids){
		List<SysUser> users=null;
		String result="select * from sys_user where id in("+ids+")";
		users=sysUserService.getZJUserByIds(result);
		return users;
	}
	@RequestMapping("findByCid")
	@ResponseBody
	public SysUser findByCid(int cid){
		SysUser user=sysUserService.findByCid(cid);
		return user;
	}
	//��ȡ��ǰ�û����ڻ���������ר���û�
	@RequestMapping("getZjUser")
	@ResponseBody
	public List<SysUser> getZjByBranchId(String branchId){
		List<SysUser> zjList=sysUserService.getZjByBranchId(branchId);
		return zjList;
	}
	@RequestMapping("/exportRecord")
	public void exportRecord(String dept,HttpServletRequest request, HttpServletResponse response) {
		AddLog.addLog(Log.EXPORT,"�������е��û���Ϣ");
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("export/sysUser");
		String title = "�û���Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		//��һ������ʱsheet�������Ƽ�����
		//boolean result = sysUserService.exportRecord(title,filePath);
		String ramusCenter=((ActiveUser)request.getSession().getAttribute("activeUser")).getRamusCenter();
		boolean result = sysUserService.exportRecord(title,filePath,dept,ramusCenter);
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
	
	//�ϴ��ļ�
	@RequestMapping("/importFile")
	@ResponseBody
	public Object importFile(@RequestParam(value = "fileImport")MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		AddLog.addLog(Log.IMPORT,"�����û���Ϣ");
		String path = request.getSession().getServletContext().getRealPath("upload");
		System.out.println("·���ǣ�"+path);
		String fileName = file.getOriginalFilename();
		// ��ȡԭʼ�ļ�������������չ����
		String originalFileName = fileName.substring(0, fileName.lastIndexOf("."));
		// ��ȡʱ���
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStamp = sdf.format(new Date());
		// ���ļ���
		String newFileName = originalFileName + timeStamp
				+ fileName.substring(fileName.lastIndexOf("."), fileName.length());
		File targetFile = new File(path + "/excel", newFileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// ����
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("fileUrl", request.getContextPath() + "/upload/excel/" + newFileName);
		// ����·��
		String absolutePath = targetFile.getAbsolutePath();
		// ���ý�excel���ݱ��浽���ݿ�ķ���
		int result = sysUserService.importExcel(absolutePath);
		return result;
	}
	@RequestMapping("getSendUser")
	@ResponseBody
	public Division getSendUser(String branchId){
		Division div=divisionService.getDivisionByCode(branchId);
		AddLog.addLog(Log.QUERY,"��ѯ'"+div.getDivisionname()+"'����ϵ����Ϣ");
		if("".equals(div.getDivisionphone1())&&"".equals(div.getDivisionphone2())){
			div.setDivisonphone1("");
		}else if("".equals(div.getDivisionphone1())&&!"".equals(div.getDivisionphone2())){
			div.setDivisonphone1(div.getDivisionphone2());
		}
		return div;
	}
}
