package com.wzj.service.application.impl;

import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.spi.CalendarDataProvider;

import org.apache.ibatis.annotations.Update;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.CompanyInfoMapper;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.Division;
import com.wzj.service.application.ICompanyInfoService;

@Service("companyInfoServices")
public class CompanyInfoService implements ICompanyInfoService {
	@Autowired
	private CompanyInfoMapper companyInfoMapper;
	
	@Override
	public int insertById(int titleId) {
		return companyInfoMapper.insertById(titleId);
	}
	public int delete(int id) {
		return companyInfoMapper.delete(id);
	}
	public int setStatus(int id, int status) {
		return companyInfoMapper.setStatus(id,status);
	}
	public void changeZhuxiao(int id, String zhuxiao) {
		companyInfoMapper.changeZhuxiao(id,zhuxiao);
	}
	public List<CompanyInfo> getComByStatus(int status, String branchId) {
		return companyInfoMapper.getComByStatus(status,branchId);
	}
	@Override
	public int insert(CompanyInfo com) {
		return companyInfoMapper.insert(com);
	}
	public int update(CompanyInfo com) {
		return companyInfoMapper.update(com);
	}
	public List<CompanyInfo> getAll(String branchId) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getAll();
		}else{
			return companyInfoMapper.getByBranchId(branchId);
		}
	}
	@Override
	public int certificateExpired() {
		List<CompanyInfo> coms = companyInfoMapper.getAll();
		int result=0;
		Calendar calendar = Calendar.getInstance();
		for(CompanyInfo com:coms){
			Date toDate=com.getCerttodate();
			int over=toDate.compareTo(new Date());
			//boolean bool=((com.getStatus()==11)||(com.getStatus()==12)||com.getStatus()==5));
			boolean bool=(com.getStatus()==17)||(com.getStatus()==34)||(com.getStatus()==5);
			if(over<0&&bool){//ʱ����ˣ�����״̬���ڣ�������ɹ������ϳɹ�������ɹ��ģ�
				companyInfoMapper.setStatus(com.getId(),18);
				result++;
			}
			//������ڰ������Ȼû�и��ϣ���ע��
			calendar.add(Calendar.MONTH, -6);
			Date half=calendar.getTime();
			if((toDate.compareTo(half)<0)&&bool){//�������ǰ�Ľ����ڹ���ʱ��ǰ,�Զ�ע��
				companyInfoMapper.setZhuxiao(com.getId());
			}
		}
		return result;
	}
	public int cancel(int id) {
		return companyInfoMapper.cancel(id);
	}
	public int activate(int id) {
		return companyInfoMapper.activate(id);
		
	}
	public CompanyInfo getComById(int id) {
		return companyInfoMapper.getComById(id);
	}
	public List<CompanyInfo> getInTime(String sTime, String eTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//��ת��Date��
		Date sDate;
		Date eDate;
		Timestamp sTamp=null;
		Timestamp eTamp=null;
		try {
			sDate = df.parse(sTime);
			eDate = df.parse(eTime);
			sTamp = new Timestamp(sDate.getTime());
			eTamp = new Timestamp(eDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(sTamp==null){//�����ʼʱ��Ϊ�գ�����2017-07-09Ϊ��ʼʱ��
			try {
				Date myDate1 = df.parse("2017-07-09");
				sTamp = new Timestamp(myDate1.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(eTamp==null)//�������ʱ��Ϊ�գ����õ�ǰʱ��Ϊ����ʱ��
			eTamp = new Timestamp(new Date().getTime());
		
		if(eTamp.before(sTamp)){//�������ʱ��С�ڿ�ʼʱ�䣬�Ե�
			Timestamp ts = eTamp;
			eTamp=sTamp;
			sTamp=ts;
		}
		return companyInfoMapper.getInTime(sTamp,eTamp);
	}
	public CompanyInfo getComByBussino(String xbId) {
		return companyInfoMapper.getComByBussino(xbId);
	}
	public List<CompanyInfo> getCancel(String branchId) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getAllCancel();
		}else{
			return companyInfoMapper.getCancelByBranchId(branchId);
		}
	}
	public int reActivate(int id) {
		return companyInfoMapper.reActivate(id);
	}
	public List<CompanyInfo> getAllBySql(String result) {
		return companyInfoMapper.getAllBySql(result);
	}
	public int reChange(int id) {
		return companyInfoMapper.reChange(id);
		
	}
	public CompanyInfo getComByName(String companyName) {
		return companyInfoMapper.getComByName(companyName);
	}
	public List<CompanyInfo> getRecentCompany(String defaultStartDate, String defaultEndDate, String branchId, String sSearch) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getRecentCompany(defaultStartDate,defaultEndDate,sSearch);
		}else{
			return companyInfoMapper.getRecentCompany2(defaultStartDate,defaultEndDate,branchId,sSearch);
		}
		
	}
	public List<CompanyInfo> getByAddress(String address, String branchId) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getByAddress("%"+address+"%");
		}else{
			return companyInfoMapper.getByaddress2("%"+address+"%",branchId);
		}
		
	}
	public List<CompanyInfo> getByAddress2(String address, String branchId) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getByAddress3("%"+address+"%");
		}else{
			return companyInfoMapper.getByaddress4("%"+address+"%",branchId);
		}
	}
	public List<CompanyInfo> getAllByLike(String branchId, String sSearch) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getAll2(sSearch);
		}else{
			return companyInfoMapper.getAllByLike(branchId,sSearch);
		}
	}
	public List<CompanyInfo> getCancel2(String branchId, String sSearch) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getAllCancel1(sSearch);
		}else{
			return companyInfoMapper.getCancelByBranchId1(branchId,sSearch);
		}
	}
	public List<CompanyInfo> getRecentCompany1(String defaultStartDate, String defaultEndDate, String branchId) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getRecentCompany3(defaultStartDate,defaultEndDate);
		}else{
			return companyInfoMapper.getRecentCompany4(defaultStartDate,defaultEndDate,branchId);
		}
	}
	public boolean exportExcel(String title, String filePath, List<CompanyInfo> coms) {
	//����excel����д������
	try{
		//����һ��������
		XSSFWorkbook workbook = new XSSFWorkbook();
		//���һ��sheet,sheet��
		XSSFSheet sheet = workbook.createSheet(title);
		//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,18));
		//������һ��
		XSSFRow titleRow = sheet.createRow(0);
		//�������ⵥԪ���ʽ
		XSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		//����һ������
		XSSFFont titlefont = workbook.createFont();
		titlefont.setFontHeight(16);//��������16��
		//������Ӧ�õ���ǰ�ĸ�ʽ��
		titleStyle.setFont(titlefont);
		//�ڵ�һ���д���һ����Ԫ��
		XSSFCell titleCell = titleRow.createCell(0);
		//����ֵ����ʽ�ͱ���
		titleCell.setCellValue(title);
		titleCell.setCellStyle(titleStyle);
		//---------------�����ǵ�һ��-----------------
//		sheet.setColumnWidth(0,25 * 256);
		sheet.setColumnWidth(0,25 * 256);
		sheet.setColumnWidth(1,25 * 256);
		sheet.setColumnWidth(2,25 * 512);
		sheet.autoSizeColumn(3);
		sheet.setColumnWidth(4,25 * 256);
		sheet.setColumnWidth(5,25 * 256);
		sheet.setColumnWidth(6,25 * 256);
		sheet.setColumnWidth(7,25 * 256);
		sheet.setColumnWidth(8,25 * 256);
		sheet.setColumnWidth(9,25 * 256);
		sheet.setColumnWidth(10,25 * 256);
		sheet.setColumnWidth(11,25 * 256);
		sheet.setColumnWidth(12,25 * 256);
		sheet.setColumnWidth(13,25 * 256);
		sheet.setColumnWidth(14,25 * 256);
		sheet.setColumnWidth(15,25 * 512);
		sheet.setColumnWidth(16,25 * 512);
		sheet.setColumnWidth(17,25 * 512);
		sheet.setColumnWidth(18,25 * 512);
		//�����������ĵ�Ԫ���ʽ
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		//���õڶ��б�ͷ
		XSSFRow rowHeader = sheet.createRow(1);
		XSSFCell cell = rowHeader.createCell(0);
		cell.setCellValue("״̬");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(1);
		cell.setCellValue("��֧����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(2);
		cell.setCellValue("��ҵ����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(3);
		cell.setCellValue("֤����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(4);
		cell.setCellValue("��֤����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(5);
		cell.setCellValue("��������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(6);
		cell.setCellValue("��������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(7);
		cell.setCellValue("��ҵ����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(8);
		cell.setCellValue("ע���ʱ�");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(9);
		cell.setCellValue("��������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(10);
		cell.setCellValue("����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(11);
		cell.setCellValue("���˴���");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(12);
		cell.setCellValue("���˵绰");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(13);
		cell.setCellValue("��ϵ��");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(14);
		cell.setCellValue("��ϵ�˵绰");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(15);
		cell.setCellValue("����ӡˢ����������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(16);
		cell.setCellValue("����ӡˢ��������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(17);
		cell.setCellValue("ӡˢ�������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(18);
		cell.setCellValue("��ע");
		cell.setCellStyle(style);
		//-------------��ͷ��------------
		//���õ�һ��Ϊ״̬
		int index = 1;
		String status="";
		Integer ywStatus;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String certappdate ="";
		String certtodate="";
		String createdate="";

			for(int i=0;i<coms.size();i++){
				String Typ="";
				String Mat="";
					index++;//�к�++����2��ʼ
					//����һ������
					XSSFRow row = sheet.createRow(index);
					//��һ��  ״̬
					XSSFCell rowCell = row.createCell(0);
					ywStatus=coms.get(i).getStatus();
					if(ywStatus!=null){
						if(ywStatus==37){
							status="������";
						}else if(ywStatus==34){
							status="�������";
						}else if(ywStatus==36){
							status="�����";
						}else if(ywStatus==35){
							status="������";
						}else if(ywStatus==17){
							status="�������";
						}
					}
					rowCell.setCellValue(status);
					rowCell.setCellStyle(style);
					//�ڶ��� ��֧��������
					rowCell = row.createCell(1);
					rowCell.setCellValue(coms.get(i).getBranchName());
					rowCell.setCellStyle(style);
					//������  ��ҵ����
					rowCell = row.createCell(2);
					rowCell.setCellValue(coms.get(i).getEnterprisename());
					rowCell.setCellStyle(style);
					//������  ֤����
					rowCell = row.createCell(3);
					String str = "";
					if(coms.get(i).getSerial()!=null){
						str=coms.get(i).getSerial()+"";
					int len = coms.get(i).getSerial().toString().length();
                      len = 6-len;
                      for(int j=0;j<len;j++){
                    	  str = "0"+str;
		              }
                     }
					rowCell.setCellValue(str);
					rowCell.setCellStyle(style);
					//������  ��֤����
					rowCell = row.createCell(4);
					if(coms.get(i).getCertappdate()!=null){
						certappdate = formatter.format(coms.get(i).getCertappdate());
						rowCell.setCellValue(certappdate);
					}else{
						rowCell.setCellValue("");
					}
					rowCell.setCellStyle(style);
					//������  ��������
					rowCell = row.createCell(5);
					if(coms.get(i).getCerttodate()!=null){
						certtodate=formatter.format(coms.get(i).getCerttodate());
						rowCell.setCellValue(certtodate);
					}else{
						rowCell.setCellValue("");
					}
					rowCell.setCellStyle(style);
					//������  ��������
					rowCell = row.createCell(6);
					if(coms.get(i).getCreatedate()!=null){
						createdate=formatter.format(coms.get(i).getCreatedate());
						rowCell.setCellValue(createdate);
					}else{
						rowCell.setCellValue("");
					}
					rowCell.setCellStyle(style);
					//�ڰ���  ��ҵ����
					rowCell = row.createCell(7);
					rowCell.setCellValue(coms.get(i).getEnterprisekind());
					rowCell.setCellStyle(style);
					//�ھ���  ע���ʱ�
					rowCell = row.createCell(8);
					rowCell.setCellValue(coms.get(i).getRegistercapital());
					rowCell.setCellStyle(style);
					//��ʮ��  ��������
					rowCell = row.createCell(9);
					rowCell.setCellValue(coms.get(i).getPostalcode());
					rowCell.setCellStyle(style);
					//��ʮһ�� ����
					rowCell = row.createCell(10);
					rowCell.setCellValue(coms.get(i).getFax());
					rowCell.setCellStyle(style);
					//ʮ����  ���˴���
					rowCell = row.createCell(11);
					rowCell.setCellValue(coms.get(i).getArtificialperson());
					rowCell.setCellStyle(style);
					//��ʮ����  ���˵绰
					rowCell = row.createCell(12);
					rowCell.setCellValue(coms.get(i).getAptel());
					rowCell.setCellStyle(style);
					//��ʮ����  ��ϵ��
					rowCell = row.createCell(13);
					rowCell.setCellValue(coms.get(i).getLinkman());
					rowCell.setCellStyle(style);
					//��ʮ����  ��ϵ�˵绰
					rowCell = row.createCell(14);
					rowCell.setCellValue(coms.get(i).getLtel());
					rowCell.setCellStyle(style);
					//��ʮ����  ����ӡˢ����������
					rowCell = row.createCell(15);
					rowCell.setCellValue(coms.get(i).getBcprincipal());
					rowCell.setCellStyle(style);
					//��ʮ����  ����ӡˢ��������
					rowCell = row.createCell(16);
					if(coms.get(i).getFlat())Typ+="��ƽ�潺ӡ";
					if(coms.get(i).getGravure())Typ+="������ӡˢ";
					if(coms.get(i).getWebby())Typ+="��˿��ӡˢ";
					if(coms.get(i).getFlexible())Typ+="�����԰�ӡˢ";
					if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="��"+coms.get(i).getElsetype();
//					Typ=Typ.substring(1, Typ.length());
					if(Typ.length()==0){
						rowCell.setCellValue("");
					}else{
						rowCell.setCellValue(Typ.substring(1, Typ.length()));
					}
					rowCell.setCellStyle(style);
					//��ʮ����  ӡˢ�������
					rowCell = row.createCell(17);
					if(coms.get(i).getPapery())Mat+="��ֽ��";
					if(coms.get(i).getPastern())Mat+="�����ɽ�";
					if(coms.get(i).getFrilling())Mat+="������ֽ";
					if(coms.get(i).getMetal())Mat+="������";
					if(coms.get(i).getPlastic()!=null)Mat+="������";
					if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="��"+coms.get(i).getElsematerial();
					if(Mat.length()==0){
						rowCell.setCellValue("");
					}else{
						rowCell.setCellValue(Mat.substring(1, Mat.length()));
					}
					rowCell.setCellStyle(style);
					//��ʮ����  ��ע
					rowCell = row.createCell(18);
					rowCell.setCellValue(coms.get(i).getRemarks());
					rowCell.setCellStyle(style);
			}
		//���ļ����浽ָ����λ��
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		workbook.close();
		return true;
	}catch(Exception e){
		e.printStackTrace();
		return false;
	}
	
	}
}
