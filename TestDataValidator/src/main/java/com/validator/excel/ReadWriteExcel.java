package com.validator.excel;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Color;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import java.util.Hashtable;


//import com.eviware.soapui.config.JMSPropertyConfig;







import javax.xml.parsers.*;
import javax.xml.xpath.*;
import javax.xml.namespace.NamespaceContext;





import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontCharset;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.charts.ChartData;
import org.apache.poi.ss.usermodel.charts.ChartDataFactory;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ScatterChartData;
import org.apache.poi.xslf.usermodel.XSLFColor;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.validator.VersionManager;
import com.validator.log.LoggingClass;
import com.validator.qa.idoc.*;



public class ReadWriteExcel{


/*public static void writeToXL(java.lang.String fileName, java.lang.Integer columnN, java.lang.Integer rowN, java.lang.String valuesToPass, java.lang.String sheetName)
throws BiffException, RowsExceededException, WriteException
{
	try
	{
		Workbook workbook = Workbook.getWorkbook(new File(fileName));
		WritableWorkbook copy = Workbook.createWorkbook(new File(fileName), workbook);
		WritableSheet sheetN = copy.getSheet(sheetName);
		jxl.write.WritableCell cell = sheetN.getWritableCell(1, 2);
		Label label1 = new Label(columnN, rowN, valuesToPass);
		sheetN.addCell(label1);
		copy.write();
		copy.close();
		workbook.close();
	}
catch(IOException e)
	{
		e.printStackTrace();
	}
}

public static void writeToXL(java.lang.String fileName, java.lang.Integer parentXpathPos, java.lang.Integer xpathPos, java.lang.Integer pkPos, java.lang.Integer parentValPos, java.lang.Integer targetValPos, java.lang.Integer rowN, ExcellRow row, java.lang.String sheetName)

throws BiffException, RowsExceededException, WriteException
{
	try
	{
		Workbook workbook = Workbook.getWorkbook(new File(fileName));
		WritableWorkbook copy = Workbook.createWorkbook(new File(fileName), workbook);
		WritableSheet sheetN = copy.getSheet(sheetName);
//		jxl.write.WritableCell cell = sheetN.getWritableCell(1, 2);
		Label label1 = new Label(parentXpathPos, rowN, row.getParentXpath());
		Label label2 = new Label(xpathPos, rowN, row.getXpath());
		Label label3 = new Label(pkPos, rowN, row.getPk());
		Label label4 = new Label(parentValPos, rowN, row.getParentValue());
		Label label5 = new Label(targetValPos, rowN, row.getValue());
		sheetN.addCell(label1);
		sheetN.addCell(label2);
		sheetN.addCell(label3);
		sheetN.addCell(label4);
		sheetN.addCell(label5);
		copy.write();
		copy.close();
		workbook.close();
	}
catch(IOException e)
	{
		e.printStackTrace();
	}
}

public static void writeAllToXL(java.lang.String fileName, java.lang.Integer sparentXpathPos, java.lang.Integer sxpathPos, java.lang.Integer spkPos, java.lang.Integer sparentValPos, java.lang.Integer sValPos,java.lang.Integer tparentXpathPos, java.lang.Integer txpathPos, java.lang.Integer tpkPos, java.lang.Integer tparentValPos, java.lang.Integer tValPos, Map<java.lang.Integer,ExcellRow> s,  Map<java.lang.Integer,ExcellRow> t, java.lang.String sheetName)

throws BiffException, RowsExceededException, WriteException
{
	try
	{
		Workbook workbook = Workbook.getWorkbook(new File(fileName));
		WritableWorkbook copy = Workbook.createWorkbook(new File(fileName), workbook);
		WritableSheet sheetN = copy.getSheet(sheetName);
//		jxl.write.WritableCell cell = sheetN.getWritableCell(1, 2);
		Set<Integer> setS=s.keySet();
		Integer maxS=Collections.max(setS);
		Set<Integer> setT=t.keySet();
		Integer maxT=Collections.max(setT);
		for(int rowN=1;rowN<=maxS||rowN<=maxT;rowN++)
		{
		ExcellRow srow=s.get(rowN);
		ExcellRow trow=t.get(rowN);
		Label label1 = new Label(sparentXpathPos, rowN, srow.getParentXpath());
		Label label2 = new Label(sxpathPos, rowN, srow.getXpath());
		Label label3 = new Label(spkPos, rowN, srow.getPk());
		Label label4 = new Label(sparentValPos, rowN, srow.getParentValue());
		Label label5 = new Label(sValPos, rowN, srow.getValue());
		Label tlabel1 = new Label(tparentXpathPos, rowN, trow.getParentXpath());
		Label tlabel2 = new Label(txpathPos, rowN, trow.getXpath());
		Label tlabel3 = new Label(tpkPos, rowN, trow.getPk());
		Label tlabel4 = new Label(tparentValPos, rowN, trow.getParentValue());
		Label tlabel5 = new Label(tValPos, rowN, trow.getValue());
		sheetN.addCell(label1);
		sheetN.addCell(label2);
		sheetN.addCell(label3);
		sheetN.addCell(label4);
		sheetN.addCell(label5);
		sheetN.addCell(tlabel1);
		sheetN.addCell(tlabel2);
		sheetN.addCell(tlabel3);
		sheetN.addCell(tlabel4);
		sheetN.addCell(tlabel5);
		}
		copy.write();
		copy.close();
		workbook.close();
	}
catch(IOException e)
	{
		e.printStackTrace();
	}
}


public static void writeAllToXLWithPassFail(java.lang.String fileName, java.lang.Integer sparentXpathPos, java.lang.Integer sxpathPos, java.lang.Integer spkPos, java.lang.Integer sparentValPos, java.lang.Integer sValPos,java.lang.Integer tparentXpathPos, java.lang.Integer txpathPos, java.lang.Integer tpkPos, java.lang.Integer tparentValPos, java.lang.Integer tValPos,java.lang.Integer statusPos,java.lang.Integer splConditionPos,java.lang.Integer splConditionParam, Map<java.lang.Integer,ExcellRow> s,  Map<java.lang.Integer,ExcellRow> t,List<SplConditions> conditions, java.lang.String sheetName)

throws BiffException, RowsExceededException, WriteException
{
	try
	{
		Workbook workbook = Workbook.getWorkbook(new File(fileName));
		WritableWorkbook copy = Workbook.createWorkbook(new File(fileName), workbook);
		WritableSheet sheetN = copy.getSheet(sheetName);
//		jxl.write.WritableCell cell = sheetN.getWritableCell(1, 2);
		Set<Integer> setS=s.keySet();
		Integer maxS=Collections.max(setS);
		Set<Integer> setT=t.keySet();
		Integer maxT=Collections.max(setT);
		for(int rowN=1;rowN<=maxS||rowN<=maxT;rowN++)
		{
		ExcellRow srow=s.get(rowN);
		ExcellRow trow=t.get(rowN);
		SplConditions scondition=conditions.get(rowN-1);
		Label label1 = new Label(sparentXpathPos, rowN, srow.getParentXpath());
		Label label2 = new Label(sxpathPos, rowN, srow.getXpath());
		Label label3 = new Label(spkPos, rowN, srow.getPk());
		Label label4 = new Label(sparentValPos, rowN, srow.getParentValue());
		Label label5 = new Label(sValPos, rowN, srow.getValue());
		Label tlabel1 = new Label(tparentXpathPos, rowN, trow.getParentXpath());
		Label tlabel2 = new Label(txpathPos, rowN, trow.getXpath());
		Label tlabel3 = new Label(tpkPos, rowN, trow.getPk());
		Label tlabel4 = new Label(tparentValPos, rowN, trow.getParentValue());
		Label tlabel5 = new Label(tValPos, rowN, trow.getValue());
		String passFail=(srow.getValue().equals(trow.getValue()))?"Pass":"Fail";
		Label labelPassFail = new Label(statusPos, rowN, passFail);
		Label scondC=new Label(splConditionPos,rowN,scondition.getValidationSpCondition());//Spl condition condition
		Label scondP=new Label(splConditionParam,rowN,scondition.getValidationParameter());//Spl condition param
		sheetN.addCell(label1);
		sheetN.addCell(label2);
		sheetN.addCell(label3);
		sheetN.addCell(label4);
		sheetN.addCell(label5);
		sheetN.addCell(tlabel1);
		sheetN.addCell(tlabel2);
		sheetN.addCell(tlabel3);
		sheetN.addCell(tlabel4);
		sheetN.addCell(tlabel5);
		sheetN.addCell(labelPassFail);
		sheetN.addCell(scondC);
		sheetN.addCell(scondP);
		}
		copy.write();
		copy.close();
		workbook.close();
	}
catch(IOException e)
	{
		e.printStackTrace();
	}
}

public static void writeAllToXLWithPassFail2(java.lang.String fileName, java.lang.Integer sparentXpathPos, java.lang.Integer sxpathPos, java.lang.Integer spkPos, java.lang.Integer sparentValPos, java.lang.Integer sValPos,java.lang.Integer tparentXpathPos, java.lang.Integer txpathPos, java.lang.Integer tpkPos, java.lang.Integer tparentValPos, java.lang.Integer tValPos,java.lang.Integer sIDAPos,java.lang.Integer tIDAPos,java.lang.Integer statusPos,java.lang.Integer splConditionPos,java.lang.Integer splConditionParam, Map<java.lang.Integer,ExcellRow> s,  Map<java.lang.Integer,ExcellRow> t,List<SplConditions> conditions, java.lang.String sheetName)

throws BiffException, RowsExceededException, WriteException
{
	try
	{
		Workbook workbook = Workbook.getWorkbook(new File(fileName));
		WritableWorkbook copy = Workbook.createWorkbook(new File(fileName), workbook);
		WritableSheet sheetN = copy.getSheet(sheetName);
//		jxl.write.WritableCell cell = sheetN.getWritableCell(1, 2);
		Set<Integer> setS=s.keySet();
		Integer maxS=Collections.max(setS);
		Set<Integer> setT=t.keySet();
		Integer maxT=Collections.max(setT);
		for(int rowN=1;rowN<=maxS||rowN<=maxT;rowN++)
		{
		ExcellRow srow=s.get(rowN);
		ExcellRow trow=t.get(rowN);
		SplConditions scondition=conditions.get(rowN-1);
		Label label1 = new Label(sparentXpathPos, rowN, srow.getParentXpath());
		Label label2 = new Label(sxpathPos, rowN, srow.getXpath());
		Label label3 = new Label(spkPos, rowN, srow.getPk());
		Label label4 = new Label(sparentValPos, rowN, srow.getParentValue());
		Label label5 =null;
		if(scondition.getValidationSpCondition()!=null&&scondition.getValidationSpCondition().length()>0)
		label5 = new Label(sValPos, rowN, srow.getBakupVal()+" => "+srow.getValue());
		else
		label5 = new Label(sValPos, rowN, srow.getValue());
		Label label6 = new Label(sIDAPos, rowN, srow.getIdocAttributes());
		Label tlabel1 = new Label(tparentXpathPos, rowN, trow.getParentXpath());
		Label tlabel2 = new Label(txpathPos, rowN, trow.getXpath());
		Label tlabel3 = new Label(tpkPos, rowN, trow.getPk());
		Label tlabel4 = new Label(tparentValPos, rowN, trow.getParentValue());
		Label tlabel5 = new Label(tValPos, rowN, trow.getValue());
		Label tlabel6 = new Label(tIDAPos, rowN, trow.getIdocAttributes());
		String passFail=(srow.getValue().equals(trow.getValue()))?"Pass":"Fail";
		Label labelPassFail = new Label(statusPos, rowN, passFail);
		Label scondC=new Label(splConditionPos,rowN,scondition.getValidationSpCondition());//Spl condition condition
		Label scondP=new Label(splConditionParam,rowN,scondition.getValidationParameter());//Spl condition param
		sheetN.addCell(label1);
		sheetN.addCell(label2);
		sheetN.addCell(label3);
		sheetN.addCell(label4);
		sheetN.addCell(label5);
		sheetN.addCell(label6);
		sheetN.addCell(tlabel1);
		sheetN.addCell(tlabel2);
		sheetN.addCell(tlabel3);
		sheetN.addCell(tlabel4);
		sheetN.addCell(tlabel5);
		sheetN.addCell(tlabel6);
		sheetN.addCell(labelPassFail);
		sheetN.addCell(scondC);
		sheetN.addCell(scondP);
		}
		copy.write();
		copy.close();
		workbook.close();
	}
catch(IOException e)
	{
		e.printStackTrace();
	}
}

public static String writeAllToXLWithPassFail_newFile(java.lang.String fileName, java.lang.Integer sparentXpathPos, java.lang.Integer sxpathPos, java.lang.Integer spkPos, java.lang.Integer sparentValPos, java.lang.Integer sValPos,java.lang.Integer tparentXpathPos, java.lang.Integer txpathPos, java.lang.Integer tpkPos, java.lang.Integer tparentValPos, java.lang.Integer tValPos,java.lang.Integer sIDAPos,java.lang.Integer tIDAPos,java.lang.Integer statusPos,java.lang.Integer splConditionPos,java.lang.Integer splConditionParam, Map<java.lang.Integer,ExcellRow> s,  Map<java.lang.Integer,ExcellRow> t,List<SplConditions> conditions, java.lang.String sheetName,List<String> headerCells,java.lang.Integer commentPos, Map<Integer,String> comments)
throws BiffException, RowsExceededException, WriteException
{
	
	//Date d=new Date();
	Date dt=new Date();
	Calendar c=Calendar.getInstance();
	c.setTime(dt);
	DateFormat df=DateFormat.getDateTimeInstance();
	String d=df.format(dt);
	d=d.replaceAll(":", "_");
	System.out.println(d);
	String newName =(fileName.substring(0, fileName.lastIndexOf(".")))+d+fileName.substring(fileName.lastIndexOf("."));
	
	try
	{
		File newFile=new File(newName);
		newFile.createNewFile();
		Workbook workbook = Workbook.getWorkbook(new File(fileName));
		WorkbookSettings wbSettings = new WorkbookSettings();
	    wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook copy=Workbook.createWorkbook(newFile, wbSettings);
		//WritableWorkbook copy = Workbook.createWorkbook(new File(newName), workbook);
		
		WritableSheet sheetN = copy.createSheet("Results",0 );
		//WritableSheet sheetN = copy.getSheet(sheetName);
//		jxl.write.WritableCell cell = sheetN.getWritableCell(1, 2);
		Set<Integer> setS=s.keySet();
		Integer maxS=Collections.max(setS);
		Set<Integer> setT=t.keySet();
		Integer maxT=Collections.max(setT);
		//System.out.println(headerCells);
		for(int col=0;col<headerCells.size();col++)
			{
			String hc=headerCells.get(col);
			Label l=new Label(col, 0, hc);
			WritableFont wf=new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			WritableCellFormat wcf=new WritableCellFormat();
			wcf.setBackground(Colour.GRAY_25);
			wcf.setFont(wf);			
			wcf.setBorder(Border.ALL, BorderLineStyle.THICK);
			l.setCellFormat(wcf);
			sheetN.addCell(l);
			
			}
			
		int sheetnum=0;
		
		int opRow=0;
		for(int rowN=1;rowN<=maxS||rowN<=maxT;rowN++)
		{
		//rowN++;
			////int rowN=rowN1%65531;
			//if(rowN==0)rowN=1;
			if(opRow>=60000)
			{
				
				System.gc();
				opRow=0;
				sheetnum++;
				sheetN = copy.createSheet("Results_"+sheetnum,sheetnum );
				System.out.println("Current Sheet:"+sheetnum);
			}
		ExcellRow srow=s.get(rowN);
		ExcellRow trow=t.get(rowN);
		if(trow==null||srow.getPk().equals("TemporaryRow"))
		{
			continue;
		}
		opRow++;//row to write
		SplConditions scondition=conditions.get(rowN-1);
		Label label1 = new Label(sparentXpathPos, opRow, srow.getParentXpath());
		Label label2 = new Label(sxpathPos, opRow, srow.getXpath());
		Label label3 = new Label(spkPos, opRow, srow.getPk());
		Label label4 = new Label(sparentValPos, opRow, srow.getParentValue());
		Label label5 =null;
		if(scondition.getValidationSpCondition()!=null&&scondition.getValidationSpCondition().length()>0)
		label5 = new Label(sValPos, opRow, srow.getBakupVal()+" => "+srow.getValue());
		else
		label5 = new Label(sValPos, opRow, srow.getValue());
		Label label6 = new Label(sIDAPos, opRow, srow.getIdocAttributes());
		Label tlabel1 = new Label(tparentXpathPos, opRow, trow.getParentXpath());
		Label tlabel2 = new Label(txpathPos, opRow, trow.getXpath());
		Label tlabel3 = new Label(tpkPos, opRow, trow.getPk());
		Label tlabel4 = new Label(tparentValPos, opRow, trow.getParentValue());
		Label tlabel5 = new Label(tValPos, opRow, trow.getValue());
		Label tlabel6 = new Label(tIDAPos, opRow, trow.getIdocAttributes());
		String passFail=(srow.getValue().equals(trow.getValue()))?"Pass":"Fail";
		Label labelPassFail = new Label(statusPos, opRow, passFail);
		Label scondC=new Label(splConditionPos,opRow,scondition.getValidationSpCondition());//Spl condition condition
		Label scondP=new Label(splConditionParam,opRow,scondition.getValidationParameter());//Spl condition param
		String comment=comments.get(srow.getOccurances().get(0));
		Label commentLab=new Label(commentPos,opRow,comment);
		
		WritableFont wf=new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
		WritableCellFormat wcf=new WritableCellFormat();
		wcf.setBackground(Colour.LIGHT_TURQUOISE);
		wcf.setFont(wf);			
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		WritableCellFormat passf=new WritableCellFormat();
		WritableCellFormat yellowf=new WritableCellFormat();
		WritableCellFormat failf=new WritableCellFormat();
		passf.setBackground(Colour.LIGHT_GREEN);
		yellowf.setBackground(Colour.YELLOW);
		failf.setBackground(Colour.LIGHT_ORANGE);
		passf.setBorder(Border.ALL, BorderLineStyle.THIN);
		failf.setBorder(Border.ALL, BorderLineStyle.THIN);
		yellowf.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		label1.setCellFormat(wcf);
		label2.setCellFormat(wcf);
		label3.setCellFormat(wcf);
		label4.setCellFormat(wcf);
		label5.setCellFormat(wcf);
		label6.setCellFormat(wcf);
		
		if(comment!=null && comment.length()>0)
		commentLab.setCellFormat(yellowf);
		else
			commentLab.setCellFormat(wcf);
		
		tlabel1.setCellFormat(wcf);
		tlabel2.setCellFormat(wcf);
		tlabel3.setCellFormat(wcf);
		tlabel4.setCellFormat(wcf);
		tlabel5.setCellFormat(wcf);
		tlabel6.setCellFormat(wcf);
		
		if(trow==null||trow.getPk()==null||trow.getPk().equals("Target Not Found"))
		{
			labelPassFail.setString("Target Not Found");
			passFail="Not Found";
			labelPassFail.setCellFormat(yellowf);
			tlabel3.setString("");
		}
		else if(passFail.equals("Pass"))
			labelPassFail.setCellFormat(passf);
		else
			labelPassFail.setCellFormat(failf);
		
		if(scondition.getValidationSpCondition()!=null && scondition.getValidationSpCondition().length()>0)
		{
			scondC.setCellFormat(passf);
			scondP.setCellFormat(passf);
		}
		else{
			scondC.setCellFormat(wcf);
			scondP.setCellFormat(wcf);
		}
		
		sheetN.addCell(label1);
		sheetN.addCell(label2);
		sheetN.addCell(label3);
		sheetN.addCell(label4);
		sheetN.addCell(label5);
		sheetN.addCell(label6);
		sheetN.addCell(tlabel1);
		sheetN.addCell(tlabel2);
		sheetN.addCell(tlabel3);
		sheetN.addCell(tlabel4);
		sheetN.addCell(tlabel5);
		sheetN.addCell(tlabel6);
		sheetN.addCell(labelPassFail);
		sheetN.addCell(scondC);
		sheetN.addCell(scondP);
		sheetN.addCell(commentLab);
		}
		copy.write();
		copy.close();
		workbook.close();
	}
catch(IOException e)
	{
	LoggingClass.logger.info("Error="+e);	
	//e.printStackTrace();
	}
return newName;
}
*/
public static String writeAllToXLWithPassFail_newFile2(java.lang.String fileName, java.lang.Integer sparentXpathPos, java.lang.Integer sxpathPos, java.lang.Integer spkPos, java.lang.Integer sparentValPos, java.lang.Integer sValPos,java.lang.Integer tparentXpathPos, java.lang.Integer txpathPos, java.lang.Integer tpkPos, java.lang.Integer tparentValPos, java.lang.Integer tValPos,java.lang.Integer sIDAPos,java.lang.Integer tIDAPos,java.lang.Integer statusPos,java.lang.Integer splConditionPos,java.lang.Integer splConditionParam, Map<java.lang.Integer,ExcellRow> s,  Map<java.lang.Integer,ExcellRow> t,List<SplConditions> conditions, java.lang.String sheetName,List<String> headerCells,java.lang.Integer commentPos, Map<Integer,String> comments)
//throws BiffException, RowsExceededException, WriteException
{
	//System.out.println(s);
	//System.out.println(t);
	//Date d=new Date();
	Date dt=new Date();
	Calendar c=Calendar.getInstance();
	c.setTime(dt);
	DateFormat df=DateFormat.getDateTimeInstance();
	String d=df.format(dt);
	d=d.replaceAll(":", "_");
	//System.out.println(d);
	String newName =(fileName.substring(0, fileName.lastIndexOf(".")))+d+".xlsx";
	headerCells.add("XPathRowNumber");
	try
	{
		File newFile=new File(newName);
		newFile.createNewFile();
		//Workbook workbook = WorkbookFactory.create(new File(fileName));
		//WorkbookSettings wbSettings = new WorkbookSettings();
	    //wbSettings.setLocale(new Locale("en", "EN"));
		SXSSFWorkbook copy=new SXSSFWorkbook(200);
		
		//WritableWorkbook copy = Workbook.createWorkbook(new File(newName), workbook);
		
		Sheet sheetN = copy.createSheet("Results" );
		//WritableSheet sheetN = copy.getSheet(sheetName);
//		jxl.write.WritableCell cell = sheetN.getWritableCell(1, 2);
		Set<Integer> setS=s.keySet();
		Integer maxS=Collections.max(setS)+1;
		Set<Integer> setT=t.keySet();
		Integer maxT=Collections.max(setT)+1;
		//System.out.println(maxS+","+maxT);
		Row headerRow=sheetN.createRow(0);
		CellStyle headStyle=copy.createCellStyle();
		Font headFont=copy.createFont();
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		//headStyle.setFillBackgroundColor(IndexedColors.AQUA.index);
		headStyle.setFillForegroundColor(IndexedColors.AQUA.index);
		headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headStyle.setFont(headFont);
		
		headStyle.setBorderTop(CellStyle.BORDER_THICK);
		headStyle.setBorderBottom(CellStyle.BORDER_THICK);
		headStyle.setBorderLeft(CellStyle.BORDER_THICK);
		headStyle.setBorderRight(CellStyle.BORDER_THICK);
		
		
		Font textFont=copy.createFont();
		textFont.setFontName("Arial Unicode MS");
		textFont.setCharSet(FontCharset.MAC.getValue());
		CellStyle textStyle=copy.createCellStyle();
		textStyle.setFont(textFont);
		textStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
		textStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		CellStyle passF=copy.createCellStyle();
		CellStyle failF=copy.createCellStyle();
		CellStyle yellowF=copy.createCellStyle();
		passF.setFont(textFont);
		failF.setFont(textFont);
		yellowF.setFont(textFont);
		passF.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		failF.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
		yellowF.setFillForegroundColor(IndexedColors.YELLOW.index);
		passF.setFillPattern(CellStyle.SOLID_FOREGROUND);
		failF.setFillPattern(CellStyle.SOLID_FOREGROUND);
		yellowF.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		
		
		textStyle.setBorderTop(CellStyle.BORDER_THIN);
		textStyle.setBorderBottom(CellStyle.BORDER_THIN);
		textStyle.setBorderLeft(CellStyle.BORDER_THIN);
		textStyle.setBorderRight(CellStyle.BORDER_THIN);
		
		passF.setBorderTop(CellStyle.BORDER_THIN);
		passF.setBorderBottom(CellStyle.BORDER_THIN);
		passF.setBorderLeft(CellStyle.BORDER_THIN);
		passF.setBorderRight(CellStyle.BORDER_THIN);
		
		failF.setBorderTop(CellStyle.BORDER_THIN);
		failF.setBorderBottom(CellStyle.BORDER_THIN);
		failF.setBorderLeft(CellStyle.BORDER_THIN);
		failF.setBorderRight(CellStyle.BORDER_THIN);
		
		yellowF.setBorderTop(CellStyle.BORDER_THIN);
		yellowF.setBorderBottom(CellStyle.BORDER_THIN);
		yellowF.setBorderLeft(CellStyle.BORDER_THIN);
		yellowF.setBorderRight(CellStyle.BORDER_THIN);
		
		
		for(int col=0;col<headerCells.size();col++)
			{
			String hc=headerCells.get(col);
			//Label l=new Label(col, 0, hc);
			//WritableFont wf=new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			//WritableCellFormat wcf=new WritableCellFormat();
			//wcf.setBackground(Colour.GRAY_25);
			//wcf.setFont(wf);			
			//wcf.setBorder(Border.ALL, BorderLineStyle.THICK);
			//l.setCellFormat(wcf);
			//sheetN.addCell(l);
			Cell cell=headerRow.createCell(col);
			cell.setCellValue(hc);
			cell.setCellStyle(headStyle);
			}
			
		int sheetnum=0;
		
		int opRow=0;
		for(int rowN=1;rowN<=maxS||rowN<=maxT;rowN++)
		{
			if(rowN%200==0)
			System.out.println(rowN);
		//rowN++;
			////int rowN=rowN1%65531;
			//if(rowN==0)rowN=1;
			if(opRow>=1048570)
			{
				
				System.gc();
				opRow=0;
				sheetnum++;
				sheetN = copy.createSheet("Results_"+sheetnum );
				System.out.println("Current Sheet:"+sheetnum);
			}
		ExcellRow srow=s.get(rowN);
		ExcellRow trow=t.get(rowN);
		if(trow==null||srow.getPk().equals("TemporaryRow"))
		{
			continue;
		}
		
		
		
		opRow++;//row to write
		Row r=sheetN.createRow(opRow);
		SplConditions scondition=conditions.get(rowN-1);
		
		
		Cell label1 = r.createCell(sparentXpathPos); label1.setCellValue(srow.getParentXpath());
		Cell label2 = r.createCell(sxpathPos); label2.setCellValue(srow.getXpath());
		Cell label3 = r.createCell(spkPos);label3.setCellValue(srow.getPk());
		Cell label4 = r.createCell(sparentValPos);label4.setCellValue(srow.getParentValue());
		Cell label5 =r.createCell(sValPos);
		if(scondition.getValidationSpCondition()!=null&&scondition.getValidationSpCondition().length()>0)
			label5.setCellValue(srow.getBakupVal()+" => "+srow.getValue());
		else
			label5.setCellValue(srow.getValue());
		Cell label6 = r.createCell(sIDAPos); label6.setCellValue(srow.getIdocAttributes());
		Cell tlabel1 = r.createCell(tparentXpathPos);tlabel1.setCellValue(trow.getParentXpath());
		Cell tlabel2 = r.createCell(txpathPos); tlabel2.setCellValue( trow.getXpath());
		Cell tlabel3 = r.createCell(tpkPos); tlabel3.setCellValue(trow.getPk());
		Cell tlabel4 = r.createCell(tparentValPos); tlabel4.setCellValue(trow.getParentValue());
		Cell tlabel5 = r.createCell(tValPos); tlabel5.setCellValue( trow.getValue());
		Cell tlabel6 = r.createCell(tIDAPos); tlabel6.setCellValue( trow.getIdocAttributes());
		String passFail=(srow.getValue().equals(trow.getValue()))?"Pass":"Fail";
		Cell labelPassFail = r.createCell(statusPos);labelPassFail.setCellValue( passFail);
		Cell scondC= r.createCell(splConditionPos);scondC.setCellValue(scondition.getValidationSpCondition());//Spl condition condition
		Cell scondP= r.createCell(splConditionParam);scondP.setCellValue(scondition.getValidationParameter());//Spl condition param
		String comment=comments.get(srow.getOccurances().get(0));
		Cell commentLab= r.createCell(commentPos);commentLab.setCellValue(comment);
		Cell xpathRowLab= r.createCell(commentPos+1);xpathRowLab.setCellValue(srow.getOccurances().get(0)+1);
		
		//System.out.println(Charset.forName("UTF-8").encode(trow.getValue()));
		
		label1.setCellStyle(textStyle);
		label2.setCellStyle(textStyle);
		label3.setCellStyle(textStyle);
		label4.setCellStyle(textStyle);
		label5.setCellStyle(textStyle);
		label6.setCellStyle(textStyle);
		
		if(comment!=null && comment.length()>0)
		commentLab.setCellStyle(yellowF);
		else
			commentLab.setCellStyle(textStyle);
		
		tlabel1.setCellStyle(textStyle);
		tlabel2.setCellStyle(textStyle);
		tlabel3.setCellStyle(textStyle);
		tlabel4.setCellStyle(textStyle);
		tlabel5.setCellStyle(textStyle);
		tlabel6.setCellStyle(textStyle);
		xpathRowLab.setCellStyle(textStyle);
		if(trow==null||trow.getPk()==null||trow.getPk().equals("Target Not Found"))
		{
			labelPassFail.setCellValue("Target Not Found");
			passFail="Not Found";
			labelPassFail.setCellStyle(yellowF);
			tlabel3.setCellValue("");
		}
		else if(passFail.equals("Pass"))
			labelPassFail.setCellStyle(passF);
		else
			labelPassFail.setCellStyle(failF);
		
		if(scondition.getValidationSpCondition()!=null && scondition.getValidationSpCondition().length()>0)
		{
			scondC.setCellStyle(passF);
			scondP.setCellStyle(passF);
		}
		else{
			scondC.setCellStyle(textStyle);
			scondP.setCellStyle(textStyle);
		}
		
		}
		
		writeMoreInfo("KeyInfo", "Help/KeyInfo.txt", copy);
		FileOutputStream fileOut = new FileOutputStream(newName);
		
	    copy.write(fileOut);
	    fileOut.close();
	    System.out.println("File \""+newName+"\" written");
	}
catch(IOException e)
	{
	LoggingClass.logger.info("Error="+e);	
	//e.printStackTrace();
	}
	/*catch(InvalidFormatException ife)
	{
		LoggingClass.logger.info("Error Writing Output File due to file format");
		ife.printStackTrace();
	}*/
return newName;
}


public int generateTemplateExcel()
{
	int retVal=0;
	String newName ="Template.xlsx";
	try
	{
		File newFile=new File(newName);
		newFile.createNewFile();
		//Workbook workbook = WorkbookFactory.create(new File(fileName));
		//WorkbookSettings wbSettings = new WorkbookSettings();
	    //wbSettings.setLocale(new Locale("en", "EN"));
		SXSSFWorkbook template=new SXSSFWorkbook(200);
		Sheet sheet = template.createSheet("Results" );
		String fields="SourceUniqueXPath	SourceXPath	SourcePrimaryKey	SourceParentValue	SourceValue	SourceIDocAttributes	Validation_SPCondition	ConditionalParameter	TargetUniqueXPath	TargetXPath	TargetPrimaryKey	TargetParentValue	TargetValue	TargetIDocAttributes	Status	Comments";
		Row row=sheet.createRow(0);
		String[] farray=fields.split("\t");
		
		
		CellStyle headStyle=template.createCellStyle();
		Font headFont=template.createFont();
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		//headStyle.setFillBackgroundColor(IndexedColors.AQUA.index);
		headStyle.setFillForegroundColor(IndexedColors.AQUA.index);
		headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headStyle.setFont(headFont);
		
		headStyle.setBorderTop(CellStyle.BORDER_THICK);
		headStyle.setBorderBottom(CellStyle.BORDER_THICK);
		headStyle.setBorderLeft(CellStyle.BORDER_THICK);
		headStyle.setBorderRight(CellStyle.BORDER_THICK);
		
		for(int i=0;i<farray.length;i++)
		{
			Cell cell=row.createCell(i);
			cell.setCellValue(farray[i]);
			cell.setCellStyle(headStyle);
		}
	
		writeMoreInfo("KeyInfo", "src/main/resources/Help/KeyInfo.txt", template);
		FileOutputStream fileOut = new FileOutputStream(newName);
		
	    template.write(fileOut);
	    fileOut.close();
	    System.out.println("File \""+newName+"\" written");
		
	}catch(Exception e)
	{
		e.printStackTrace();
		retVal=1;
	}
	return retVal;
}

public static void writeMoreInfo(String sheetName, String KeyInfoFile, SXSSFWorkbook book)
{
	 

	
	CellStyle headStyle=book.createCellStyle();
	Font headFont=book.createFont();
	headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	headFont.setColor(IndexedColors.WHITE.index);
	//headStyle.setFillBackgroundColor(IndexedColors.AQUA.index);
	headStyle.setFillForegroundColor(IndexedColors.INDIGO.index);
	headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	headStyle.setFont(headFont);
	
	headStyle.setBorderTop(CellStyle.BORDER_THICK);
	headStyle.setBorderBottom(CellStyle.BORDER_THICK);
	headStyle.setBorderLeft(CellStyle.BORDER_THICK);
	headStyle.setBorderRight(CellStyle.BORDER_THICK);
	headStyle.setIndention(CellStyle.ALIGN_JUSTIFY);

	Font textFont=book.createFont();
	textFont.setFontName("Arial Unicode MS");
	textFont.setCharSet(FontCharset.MAC.getValue());
	CellStyle textStyle=book.createCellStyle();
	textStyle.setFont(textFont);
	textStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
	textStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	textStyle.setBorderTop(CellStyle.BORDER_THIN);
	textStyle.setBorderBottom(CellStyle.BORDER_THIN);
	textStyle.setBorderLeft(CellStyle.BORDER_THIN);
	textStyle.setBorderRight(CellStyle.BORDER_THIN);
	textStyle.setWrapText(true);
	
	
	try{
		Sheet sheet=book.createSheet(sheetName);
		
		FileReader fr=new FileReader(KeyInfoFile);
		BufferedReader br=new BufferedReader(fr);
		
		String rowVal="";
		int i=0;
		while((rowVal=br.readLine())!=null)
		{
			String[] cols=rowVal.split("########");
			Row r=sheet.createRow(i);
			
			if(cols.length>=2)
			{
				Cell c1=r.createCell(0);
				
				Cell c2=r.createCell(1);
				c1.setCellValue(cols[0]);
				c2.setCellValue(cols[1].replaceAll("<newline>", "\n"));
				//System.out.println(i+","+cols[0]+","+cols[1]);
				if(cols.length>=3)
				{
					
					if(cols[2].equalsIgnoreCase("Heading"))
					{
						c1.setCellStyle(headStyle);
						c2.setCellStyle(headStyle);
					}
					else
					{
						c1.setCellStyle(textStyle);
						c2.setCellStyle(textStyle);
					}
				}else
				{
					c1.setCellStyle(textStyle);
					c2.setCellStyle(textStyle);
				}
				
			}
			i++;
		}
	Row versRow=sheet.createRow(i+2);
	Cell vh=versRow.createCell(0);
	vh.setCellValue("Version");
	Cell vv=versRow.createCell(1);
	vv.setCellValue("This Sheet was created using Mapper "+VersionManager.version);
	vh.setCellStyle(headStyle);
	vv.setCellStyle(textStyle);
	
	
	sheet.autoSizeColumn(0);
	sheet.autoSizeColumn(1);
	}catch(Exception e){e.printStackTrace();}
}


/*
public static String getXpathNodeListValue(String FILE_NAME_STRING,boolean flag, String exp)
{
	String nodeValue = "";
	try{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		//domFactory.setNamespaceAware(false);
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document xmlDocument;
		if (flag == true)
		{
			InputSource is = new InputSource(new StringReader(FILE_NAME_STRING));
			xmlDocument = builder.parse(is);
		}
		else
		{
			xmlDocument = builder.parse(FILE_NAME_STRING);
		}		
		//NamespaceContext context = new NamespaceContextMap("xn", "http://www.xmlns.nike.com");


		XPath xPath = XPathFactory.newInstance().newXPath();
		xPath.setNamespaceContext(new MyNamespaceContext()); 
 		//xPath.addNamespace("xn", "http://www.xmlns.nike.com"); 

		try {
			XPathExpression xPathExpression = xPath.compile(exp);
			NodeList nodes = (NodeList) xPathExpression.evaluate(xmlDocument,XPathConstants.NODESET);
			
			for (int i = 0; i < nodes.getLength(); i++) {
				nodeValue = (nodeValue.equals(""))?nodes.item(i).getTextContent():nodeValue +"\n"+ nodes.item(i).getTextContent();
			}
			//return nodeValue;
		}
		catch(NullPointerException npe)
		{
			// handle the error as child object does not exist
			String NodeNotExist = "The Node Does Not Exist";
			nodeValue = NodeNotExist;
		}
	  }
	  catch(Exception ex)
	  {
		ex.printStackTrace();
	  }
  return nodeValue;
}

private static class MyNamespaceContext implements NamespaceContext {          
	
	public String getNamespaceURI(String prefix) {             
		if("xn".equals(prefix)) { 
			return "http://www.xmlns.nike.com";             
		   } 
            	return null;         
	}          

	public String getPrefix(String namespaceURI) {             
		return null;         
	}          
	
	public Iterator getPrefixes(String namespaceURI) {             
		return null;         
	} 

}*/
//public static void main(String args[])
//{
/*String srcxml="<r><s><a>a1</a><b>2</b></s><s><a>a1</a><b>2</b></s></r>";
String exp="//s/a";
String val=getXpathNodeListValue(srcxml,true,exp);
System.out.println("val_main="+val);
System.out.println("aaa");
*/

//}
}
