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
	
	//����List<List<Object>>�е����ݵ�����excel
	public void exportRecord(String title,List<List<Object>> lists,String filePath) throws Exception{
		//����һ��������
		XSSFWorkbook workbook = new XSSFWorkbook();
		//���һ��sheet,sheet��
		XSSFSheet sheet = workbook.createSheet(title);
		//�����������ĵ�Ԫ���ʽ
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		
		//��������
		for(int i=0;i<lists.size();i++){
			XSSFRow row = sheet.createRow(i);
			XSSFCell cell;
			for(int j=0;j<lists.get(0).size();j++){
				cell = row.createCell(j);
				cell.setCellValue(lists.get(i).get(j).toString());
				cell.setCellStyle(style);
			}
		}
		//���ļ����浽ָ����λ��
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		workbook.close();
		out.close();
	}
	
	//����List<Object>�е�����󵼳����ݵ�excel
	//�ļ�����ʵ�����ȫ�޶�������ÿһ�е����ƣ�ʵ���࣬get���������صĵ�ַ
	public void exportRecord(String title,String objPath,String[] names,List<Object> lists,String[] methods,String filePath) throws Exception{
		//����һ��������
		XSSFWorkbook workbook = new XSSFWorkbook();
		//���һ��sheet,sheet��
		XSSFSheet sheet = workbook.createSheet(title);
		//�����������ĵ�Ԫ���ʽ
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		
		XSSFRow row;
		XSSFCell cell;
		//���ñ�ͷ
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("���");
		cell.setCellStyle(style);
		for(int i=0;i<names.length;i++){
			cell = row.createCell(i+1);
			cell.setCellValue(names[i]);
			cell.setCellStyle(style);
		}
		//��������
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
		//���ļ����浽ָ����λ��
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		workbook.close();
		out.close();
	}
	
	public void exportFile(HttpServletRequest request,HttpServletResponse response,String fileName,String filePath) throws Exception{
		//���ݲ�ͬ����������������ļ�����������
		String userAgent = request.getHeader("User-Agent");
		//���IE����IEΪ�ں˵������
		if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
			fileName = URLEncoder.encode(fileName,"UTF-8");
		}else{//��IE������Ĵ���
			fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
		}
		//��ȡһ����
		InputStream in = new FileInputStream(new File(filePath));
		//�������ڵ���Ӧͷ
		response.setHeader("content-disposition", "attachment;fileName="+fileName);
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
	}
	
	//ѹ���ļ���
	public boolean fileToZip(String sourcePath,String zipPath,String fileName){
		File sourceFile=new File(sourcePath);
		FileOutputStream fos=null;
		ZipOutputStream zos =null;
		boolean result=true;
		if(sourceFile.exists()==false){
			System.out.println("��ѹ�����ļ�Ŀ¼��"+sourcePath+"������.");
			result=false;
		}else{
			try {
				File zipFile=new File(zipPath+"/"+fileName+".zip");
				if(zipFile.exists()){ 
					System.out.println(zipPath + "Ŀ¼�´�������Ϊ:" + fileName +".zip" +"����ļ�����ɾ����");
					zipFile.delete();
				}
				File[] sourceFiles = sourceFile.listFiles();
				if(null == sourceFiles || sourceFiles.length<1){  
                    System.out.println("��ѹ�����ļ�Ŀ¼��" + sourcePath + "���治�����ļ�������ѹ��."); 
                    result=false;
                }else{ 
					fos=new FileOutputStream(zipFile);
					zos = new ZipOutputStream(fos);
					File sf = new File(zipPath);
					compress(sf,zos,sf.getName(),true);//boolean�����Ƿ���ԭ�е��ļ��ṹ
                }
			} catch (Exception e) {
				e.printStackTrace();
				result=false;
			}finally {//�ر���  
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
			// ��zip����������һ��zipʵ�壬��������nameΪzipʵ����ļ�������
			zos.putNextEntry(new ZipEntry(name));
			// copy�ļ���zip�������
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
				// ��Ҫ����ԭ�����ļ��ṹʱ,��Ҫ�Կ��ļ��н��д���
				if(KeepDirStructure){
					// ���ļ��еĴ���
					zos.putNextEntry(new ZipEntry(name + "/"));
					// û���ļ�������Ҫ�ļ���copy
					zos.closeEntry();
				}
			}else {
				for (File file : listFiles) {
					/*1������zip�ļ���˳���ǣ�����һ��zip�ļ�
					 *2��ѭ�������ļ������ļ����ݷ��뵽zip�ļ���
					 *��ô���������ˣ���ϵͳ�ǽ��ļ������뵽��ˮ�Ŷ�Ӧ���ļ�����
					 *���ԣ��ڶ�Ӧ���ļ����оͶ���һ��zip�ļ���
					 *������Ҫ���˵����ļ� 
					 */
					if(file.getName().equals(name+".zip"))
						continue;
					// �ж��Ƿ���Ҫ����ԭ�����ļ��ṹ
					if (KeepDirStructure) {
						// ע�⣺file.getName()ǰ����Ҫ���ϸ��ļ��е����ּ�һб��,
						// ��Ȼ���ѹ�����оͲ��ܱ���ԭ�����ļ��ṹ,���������ļ����ܵ�ѹ������Ŀ¼����
						compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
					} else {
						compress(file, zos, file.getName(),KeepDirStructure);
					}
					
				}
			}
		}
	}
	
	/*�����ļ��ĺ�����from��Դ�ļ���ַ��to��Ŀ���ַ��
	 *copyFolder�Ƿ����ļ���
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
