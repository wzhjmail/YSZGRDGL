package com.wzj.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import com.wzj.pojo.UploadFile;

public class UploadFileUtil {
	
	//保存文件
	public Map<String,String> uploadFile(MultipartFile file,String titleNo,HttpServletRequest request){
		Map<String,String> map = new HashMap<>();
		
		String fileName = file.getOriginalFilename();
		//获取原始文件名
		String originalFileName = fileName.substring(0,fileName.lastIndexOf("."));
		//获取时间戳
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStamp = sdf.format(new Date());
		int random = (int)(Math.random()*10);
		//新文件名
		String newFileName = originalFileName+timeStamp+random+
		fileName.substring(fileName.lastIndexOf("."),fileName.length());*/
		String newFileName = originalFileName+
				fileName.substring(fileName.lastIndexOf("."),fileName.length());
		
		String type=titleNo.substring(0,2);//获取XB/FR/BG/MB
		//String path="src/main/webapp/upload/"+type+"/"+titleNo+"/";
		String path = request.getSession().getServletContext().getRealPath("/upload");
		path+="/"+type+"/"+titleNo+"/";
		File dir=new File(path);
		if(!dir.exists()&&!dir.isDirectory()){//如果文件不存在则创建文件
			dir.mkdirs();
		}
		File targetFile = new File(path,newFileName);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		//保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		map.put("name", newFileName);
		map.put("size",file.getSize()/1024+"kb");
		map.put("path", "upload/"+type+"/"+titleNo+"/"+newFileName);
		return map;
	}
	
	//保存文件,修改新的名称
	public Map<String,String> uploadFile(MultipartFile file,String titleNo,String newName,HttpServletRequest request){
		Map<String,String> map = new HashMap<>();
		String fname=file.getOriginalFilename();
		String fileName = newName;
		String type=titleNo.substring(0,2);//获取XB/FR/BG/MB
		String path = request.getSession().getServletContext().getRealPath("/upload");
		path+="/"+type+"/"+titleNo+"/";
		File dir=new File(path);
		if(!dir.exists()&&!dir.isDirectory()){//如果文件不存在则创建文件
			dir.mkdirs();
		}
		File targetFile = new File(path,fileName);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		//保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		map.put("name", fileName);
		map.put("size",file.getSize()/1024+"kb");
		map.put("path", "upload/"+type+"/"+titleNo+"/"+fileName);
		return map;
	}
		
	//保存样品、检测报告
	public Map<String,String> uploadYPFile(MultipartFile file,String titleNo,HttpServletRequest request,List<UploadFile>  uf,String describe,String sampleId){
		Map<String,String> map = new HashMap<>();
		String fileName;
		String[] split;
		String saveName = "";
		String newFileName = "";
		int i=1;
		fileName=file.getOriginalFilename();
		if(uf.size()!=0){
			for (UploadFile uploadFile : uf) {
				split=uploadFile.getUprul().split("/");
				saveName=split[split.length-1];
				int before=saveName.lastIndexOf(".");
				if(fileName.equals(saveName)){
					newFileName=fileName.substring(0,before)+"(1)"+fileName.substring(before,fileName.length());
				}else if((before-3>0)&&fileName.equals(saveName.substring(0,before-3)+saveName.substring(before,saveName.length()))){
					i=Integer.parseInt(saveName.substring(before-2,saveName.lastIndexOf(".")-1))+1;	
					newFileName=fileName.substring(0,before)+"("+i+")"+fileName.substring(before,fileName.length());
				}else{
					newFileName=fileName;
				}
				
			}
		}else{
			newFileName=fileName;
		}
		String type=titleNo.substring(0,2);//获取XB/FR/BG/MB
		//String path="src/main/webapp/upload/"+type+"/"+titleNo+"/";
		String path = request.getSession().getServletContext().getRealPath("/upload");
		path+="/"+type+"/"+titleNo+"/"+sampleId+"/"+describe+"/";
		File dir=new File(path);
		if(!dir.exists()&&!dir.isDirectory()){//如果文件不存在则创建文件
			dir.mkdirs();
		}
		File targetFile = new File(path,newFileName);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		//保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("name", newFileName);
		map.put("size",file.getSize()/1024+"kb");
		map.put("path", "upload/"+type+"/"+titleNo+"/"+sampleId+"/"+describe+"/"+newFileName);
		return map;
	}
	//保存文件New
		public Map<String,String> uploadFileNew(MultipartFile file,String titleNo,String name){
			Map<String,String> map = new HashMap<>();
			
			String type=titleNo.substring(0,2);//获取XB/FR/BG/MB
			String path="src/main/webapp/upload/"+type+"/"+titleNo+"/";
			File dir=new File(path);
			if(!dir.exists()&&!dir.isDirectory()){//如果文件不存在则创建文件
				dir.mkdirs();
			}
			File targetFile = new File(path,name);
			if(!targetFile.exists()){
				targetFile.mkdirs();
			}
			//保存
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("name", name);
			map.put("size",file.getSize()/1024+"kb");
			map.put("path", "upload/"+type+"/"+titleNo+"/"+name);
			return map;
		}
	public Map<String,String> uploadFile(MultipartFile file){
		Map<String,String> map = new HashMap<String,String>();
		
		String fileName = file.getOriginalFilename();
		//获取原始文件名
		String originalFileName = fileName.substring(0,fileName.lastIndexOf("."));
		//获取时间戳
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStamp = sdf.format(new Date());
		int random = (int)(Math.random()*10);
		//新文件名
		String newFileName = originalFileName+timeStamp+random+
		fileName.substring(fileName.lastIndexOf("."),fileName.length());
		String path="D:\\upload\\";
		File targetFile = new File(path,newFileName);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		//保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("name", newFileName);
		map.put("path", path+newFileName);
		return map;
	}
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
          //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
