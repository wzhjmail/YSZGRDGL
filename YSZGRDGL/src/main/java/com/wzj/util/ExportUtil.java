package com.wzj.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportUtil {
	
	//根据List<List<Object>>中的数据导出到excel
	public void exportRecord(String title,List<List<Object>> lists,String filePath) throws Exception{
		//创建一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		//添加一个sheet,sheet名
		XSSFSheet sheet = workbook.createSheet(title);
		//设置其他正文单元格格式
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		
		//设置内容
		for(int i=0;i<lists.size();i++){
			XSSFRow row = sheet.createRow(i);
			XSSFCell cell;
			for(int j=0;j<lists.get(0).size();j++){
				cell = row.createCell(j);
				cell.setCellValue(lists.get(i).get(j).toString());
				cell.setCellStyle(style);
			}
		}
		//将文件保存到指定的位置
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		workbook.close();
		out.close();
	}
	
	//根据List<Object>中的类对象导出数据到excel
	//文件名，实体类的全限定类名，每一列的名称，实体类，get方法，下载的地址
	public void exportRecord(String title,String objPath,String[] names,List<Object> lists,String[] methods,String filePath) throws Exception{
		//创建一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		//添加一个sheet,sheet名
		XSSFSheet sheet = workbook.createSheet(title);
		//设置其他正文单元格格式
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		
		XSSFRow row;
		XSSFCell cell;
		//设置表头
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		for(int i=0;i<names.length;i++){
			cell = row.createCell(i+1);
			cell.setCellValue(names[i]);
			cell.setCellStyle(style);
		}
		//设置内容
		Class clazz = Class.forName(objPath);
		for(int i=0;i<lists.size();i++){
			row = sheet.createRow(i+1);
			Object test = lists.get(i);
			cell = row.createCell(0);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
			for(int j=0;j<methods.length;j++){
				cell = row.createCell(j+1);
				Method method = clazz.getMethod(methods[j],null);
				String result = method.invoke(test,null).toString();
				cell.setCellValue(result);
				cell.setCellStyle(style);
			}
		}
		//将文件保存到指定的位置
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		workbook.close();
		out.close();
	}
	
	public void exportFile(HttpServletRequest request,HttpServletResponse response,String fileName,String filePath) throws Exception{
		//根据不同的浏览器处理下载文件名乱码问题
		String userAgent = request.getHeader("User-Agent");
		//针对IE或者IE为内核的浏览器
		if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
			fileName = URLEncoder.encode(fileName,"UTF-8");
		}else{//非IE浏览器的处理
			fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
		}
		//获取一个流
		InputStream in = new FileInputStream(new File(filePath));
		//设置现在的响应头
		response.setHeader("content-disposition", "attachment;fileName="+fileName);
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
	}
	
	//压缩文件夹
	public boolean fileToZip(String sourcePath,String zipPath,String fileName){
		File sourceFile=new File(sourcePath);
		FileOutputStream fos=null;
		ZipOutputStream zos =null;
		boolean result=true;
		if(sourceFile.exists()==false){
			System.out.println("待压缩的文件目录："+sourcePath+"不存在.");
			result=false;
		}else{
			try {
				File zipFile=new File(zipPath+"/"+fileName+".zip");
				if(zipFile.exists()){ 
					System.out.println(zipPath + "目录下存在名字为:" + fileName +".zip" +"打包文件，已删除！");
					zipFile.delete();
				}
				File[] sourceFiles = sourceFile.listFiles();
				if(null == sourceFiles || sourceFiles.length<1){  
                    System.out.println("待压缩的文件目录：" + sourcePath + "里面不存在文件，无需压缩."); 
                    result=false;
                }else{ 
					fos=new FileOutputStream(zipFile);
					zos = new ZipOutputStream(fos);
					File sf = new File(zipPath);
					compress(sf,zos,sf.getName(),true);//boolean类型是否保留原有的文件结构
                }
			} catch (Exception e) {
				e.printStackTrace();
				result=false;
			}finally {//关闭流  
                try {  
                    if(null != zos) zos.close();
                    if(null != fos) fos.close();
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
			}
		}
		return result;
	}
	private static final int  BUFFER_SIZE = 2 * 1024;
	private static void compress(File sourceFile, ZipOutputStream zos, String name,
		boolean KeepDirStructure) throws Exception{
		byte[] buf = new byte[BUFFER_SIZE];
		if(sourceFile.isFile()){
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1){
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if(listFiles == null || listFiles.length == 0){
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				if(KeepDirStructure){
					// 空文件夹的处理
					zos.putNextEntry(new ZipEntry(name + "/"));
					// 没有文件，不需要文件的copy
					zos.closeEntry();
				}
			}else {
				for (File file : listFiles) {
					/*1、生成zip文件的顺序是：创建一个zip文件
					 *2、循环所有文件，将文件内容放入到zip文件中
					 *那么，问题来了：本系统是将文件都放入到流水号对应的文件夹下
					 *所以，在对应的文件夹中就多了一个zip文件，
					 *所以需要过滤掉该文件 
					 */
					if(file.getName().equals(name+".zip"))
						continue;
					// 判断是否需要保留原来的文件结构
					if (KeepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
					} else {
						compress(file, zos, file.getName(),KeepDirStructure);
					}
					
				}
			}
		}
	}
	
	/*复制文件的函数。from是源文件地址，to是目标地址，
	 *copyFolder是否复制文件夹
	 */
	public static void copy(String from, String to,boolean copyFolder){
		File f1=new File(from);
		if(f1.exists()){
			File f2=new File(to);
			if(!f2.exists()){
				f2.mkdirs();
			}
			File[] files=f1.listFiles();
			for(File f:files){
				if(f.isFile()){
					copyFile(from+"//"+f.getName(),to+"//"+f.getName());
				}else if(copyFolder){
					 copy(from+"//"+f.getName(),to+"//"+f.getName(),copyFolder);
				}
			}
		}
	}
	private static void copyFile(String from, String to) {   
        try {   
            InputStream in = new FileInputStream(from);   
            OutputStream out = new FileOutputStream(to);   
   
            byte[] buff = new byte[1024];   
            int len = 0;   
            while ((len = in.read(buff)) != -1) {   
                 out.write(buff, 0, len);   
             }   
             in.close();   
             out.close();   
         } catch (FileNotFoundException e) {   
             e.printStackTrace();   
         } catch (IOException e) {   
             e.printStackTrace();   
         }   
     }   
}
