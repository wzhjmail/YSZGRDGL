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
 * scorm �μ�������
 * 
 * @author mpc
 *
 */
public class ScormUtil {

	/**
	 * 
	 * @param filepathSon 
	 *            ѹ����Ŀ¼
	 * @param filePath
	 *            Ҫ��ѹ��Ŀ��·��
	 * @param fileName
	 *            �����ļ���
	 */
	public void unZipCourseFile(String filepathSon, String filePath, String fileName) {
		try {
			// Ҫ��ѹ��ѹ������·��
			String zipFileName = filepathSon + fileName;
			// ��ѹ��λ��
			String location = filePath + fileName.substring(0, fileName.lastIndexOf("."));
			// �ж��Ƿ���zip�ļ�
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
				// �������ѹ������ֱ�Ӹ���
				String dest = location + File.separator;
				copyFile(zipFileName, dest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * �����ļ�
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
	 * �����ṩɾ��������������ļ�����ɾ���ļ�����ȫ���ļ�������ǵ����ļ������ɾ��
	 * @param filePath
	 * @return
	 */
	public int deleteFolder(String filePath){
		File file=new File(filePath);
		//�ж�Ŀ¼���ļ��Ƿ����
		if(!file.exists()){
			return 0;
		}else{
			//�ж��Ƿ����ļ�
			if(file.isFile()){
				return deleteFile(filePath);//������ļ������ɾ���ļ��ķ���
			}else{
				return deleteDirectory(filePath);//�����Ŀ¼�����ɾ��Ŀ¼�ķ���
			}
		}
	}
	/**
	 * ɾ���ļ�
	 * @param filePath
	 */
	private  int deleteFile(String filePath){
		int flag=0;
		File file=new File(filePath);
		//·���ļ��Ҳ�Ϊ�������ɾ��
		if(file.isFile() && file.exists()){
			file.delete();
			flag=1;
		}
		return flag;
	}
	/**
	 * ɾ���ļ�����ȫ���ļ�
	 * @param filePath
	 * @return
	 */
	private int deleteDirectory(String filePath) {
		int flag=0;
		//���filepath�������ļ��ָ�����β�ģ��Զ�����ļ��ָ���
		if(!filePath.endsWith(File.pathSeparator)){
			filePath=filePath+File.separator;
		}
		File dirFile=new File(filePath);
		//���dir��Ӧ���ļ��в����ڣ����߲���һ��Ŀ¼�Ļ����Ƴ�
		if(!dirFile.exists() || !dirFile.isDirectory()){
			return flag;
		}
		flag=1;
		//ɾ���ļ����µ������ļ���������Ŀ¼
		File[] files=dirFile.listFiles();
		for(int i=0;i<files.length;i++){
			//ɾ�����ļ�
			if(files[i].isFile()){
				flag=deleteFile(files[i].getAbsolutePath());
			}else{
				//ɾ����Ŀ¼
				flag=deleteDirectory(files[i].getAbsolutePath());
				if(flag==0){
					break;
				}
			}
		}
		if(flag==0) return 0;
		//ɾ����ǰĿ¼
		if(dirFile.delete()){
			flag=1;
			return flag;
		}else{
			return flag;
		}
	}
	
}
