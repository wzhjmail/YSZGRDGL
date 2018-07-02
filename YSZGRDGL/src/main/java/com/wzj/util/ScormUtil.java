package com.wzj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * scorm 课件工具类
 * 
 * @author mpc
 *
 */
public class ScormUtil {

	/**
	 * 
	 * @param filepathSon 
	 *            压缩包目录
	 * @param filePath
	 *            要解压的目标路径
	 * @param fileName
	 *            还有文件名
	 */
	public void unZipCourseFile(String filepathSon, String filePath, String fileName) {
		try {
			// 要解压的压缩包的路径
			String zipFileName = filepathSon + fileName;
			// 解压的位置
			String location = filePath + fileName.substring(0, fileName.lastIndexOf("."));
			// 判断是否是zip文件
			if (zipFileName.substring(zipFileName.lastIndexOf(".") + 1).equals("zip")) {
				ZipFile archive = new ZipFile(zipFileName);
				byte[] buffer = new byte[16384];
				for (Enumeration e = archive.getEntries(); e.hasMoreElements();) {
					ZipEntry entry = (ZipEntry) e.nextElement();
					if (!entry.isDirectory()) {
						String entryFilename = entry.getName();
						entryFilename = entryFilename.replace('/', File.separatorChar);
						entryFilename = location + File.separator + entryFilename;
						System.out.println(entryFilename);
						File destFile = new File(entryFilename);
						String parent = destFile.getParent();
						if (parent != null) {
							File parentFile = new File(parent);
							if (!parentFile.exists()) {
								parentFile.mkdirs();
							}
						}
						InputStream in = archive.getInputStream(entry);
						OutputStream outputStream = new FileOutputStream(entryFilename);
						int count;
						while ((count = in.read(buffer)) != -1) {
							outputStream.write(buffer, 0, count);
						}
						in.close();
						outputStream.close();
					}
				}
				archive.close();
			} else {
				// 如果不是压缩包则直接复制
				String dest = location + File.separator;
				copyFile(zipFileName, dest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 复制文件
	 * @param oldPathFile
	 * @param newPathFile
	 */
	public void copyFile(String oldPathFile, String newPathFile) {
		File destFile = new File(newPathFile);

		String parent = destFile.getParent();
		if (parent != null) {
			File parentFile = new File(parent);
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
		}
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPathFile);
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 对外提供删除方法，如果是文件夹则删除文件夹下全部文件，如果是单个文件则进行删除
	 * @param filePath
	 * @return
	 */
	public int deleteFolder(String filePath){
		File file=new File(filePath);
		//判断目录或文件是否存在
		if(!file.exists()){
			return 0;
		}else{
			//判断是否是文件
			if(file.isFile()){
				return deleteFile(filePath);//如果是文件则调用删除文件的方法
			}else{
				return deleteDirectory(filePath);//如果是目录则调用删除目录的方法
			}
		}
	}
	/**
	 * 删除文件
	 * @param filePath
	 */
	private  int deleteFile(String filePath){
		int flag=0;
		File file=new File(filePath);
		//路径文件且不为空则进行删除
		if(file.isFile() && file.exists()){
			file.delete();
			flag=1;
		}
		return flag;
	}
	/**
	 * 删除文件加下全部文件
	 * @param filePath
	 * @return
	 */
	private int deleteDirectory(String filePath) {
		int flag=0;
		//如果filepath不是以文件分隔符结尾的，自动添加文件分隔符
		if(!filePath.endsWith(File.pathSeparator)){
			filePath=filePath+File.separator;
		}
		File dirFile=new File(filePath);
		//如果dir对应的文件夹不存在，或者不是一个目录的话则推出
		if(!dirFile.exists() || !dirFile.isDirectory()){
			return flag;
		}
		flag=1;
		//删除文件夹下的所有文件，包括子目录
		File[] files=dirFile.listFiles();
		for(int i=0;i<files.length;i++){
			//删除子文件
			if(files[i].isFile()){
				flag=deleteFile(files[i].getAbsolutePath());
			}else{
				//删除子目录
				flag=deleteDirectory(files[i].getAbsolutePath());
				if(flag==0){
					break;
				}
			}
		}
		if(flag==0) return 0;
		//删除当前目录
		if(dirFile.delete()){
			flag=1;
			return flag;
		}else{
			return flag;
		}
	}
	
}
