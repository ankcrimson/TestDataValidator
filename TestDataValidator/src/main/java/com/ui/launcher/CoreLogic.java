package com.ui.launcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;







import com.validator.ff.FFParser_New;
import com.validator.ff.FFReader_New;
import com.validator.log.LoggingClass;
import com.validator.qa.idoc.ExcellRow;
import com.validator.qa.idoc.IDOCParser;
import com.validator.qa.idoc.SourceTargetXpathMatcher;
import com.validator.qa.idoc.SplConditions;
import com.validatorcsv.CSVParser_New;

public class CoreLogic {

	/**
	 * @param args
	 */

	public void imitator(boolean popups,String sourceType,String targetType,String sourceFileLocation,String targetFileLocation,String MappingExcelFile,String mappingwrksheetName, JFrame parent,String sdelimiter,String tdelimiter,String sTextQual, String tTextQual,boolean srcHRow, boolean tgtHRow) throws Exception
	{
		LoggingClass.logger.info("\n=================START================\nSource File ("+sourceType+") ="+sourceFileLocation+"\nTarget File ("+targetType+") ="+targetFileLocation+"\nMapping Sheet="+MappingExcelFile+" - "+mappingwrksheetName+"\n========================================");
		String sdelimiter2=sdelimiter;
		String tdelimiter2=tdelimiter;
		Hashtable dict1= new Hashtable();
		LoggingClass.logger.info("Source Delemiter" +sdelimiter);
		LoggingClass.logger.info("Target Delemiter" +tdelimiter);
		LoggingClass.logger.info("Source Text Qualifier" +sTextQual);
		LoggingClass.logger.info("Target Text Qualifier" +tTextQual);
		// Initialize
		Workbook mappingwrkbook = null;
		
		//Work on a copy not the original file so that the file may be modified while tool is working
		FileCopyCreator fcc=new FileCopyCreator();
		String tmpName=fcc.createCopy(MappingExcelFile);
		//MappingExcelFile=fcc.createCopy(MappingExcelFile);
		
		
		FileInputStream fis=null;
		try{
			
			fis=new FileInputStream(tmpName);
			 mappingwrkbook = WorkbookFactory.create(fis);
		}catch(Exception be){
			String msg= "Please check if xls/xlsx file for mapping workbook exists";
			if(popups)
			JOptionPane.showMessageDialog(null,msg);
			else
				System.out.println(msg);
			//System.exit(0);
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			PrintStream ps=new PrintStream(bos);
			be.printStackTrace(ps);
			LoggingClass.logger.info(bos);
			return;
		}
		Sheet mappingwrksheet = mappingwrkbook.getSheet(mappingwrksheetName);
		
		int maprows = mappingwrksheet.getLastRowNum()+1;
		int mapcolumns = mappingwrksheet.getRow(mappingwrksheet.getFirstRowNum()).getLastCellNum();
		LoggingClass.logger.info("mapcolumns" +mapcolumns);
		LoggingClass.logger.info("maprows" + maprows);
		//System.out.println("mapcolumns" +mapcolumns);
		//System.out.println("maprows" + maprows);
		// Loop through each record to map a dictionary
		List<String> cellArray=new ArrayList<String>();
		for(int mapcol=0;mapcol < mapcolumns;mapcol++)
		{
			if(mappingwrksheet.isColumnHidden(mapcol))
				mappingwrksheet.setColumnHidden(mapcol, false);
			Row r=mappingwrksheet.getRow(0);
			Cell c=r.getCell(mapcol);
			dict1.put(c.getStringCellValue(), mapcol);
			cellArray.add(c.getStringCellValue());
			//System.out.println(mapcol+" - "+c.getStringCellValue());
		}
		//get header positions
		int SourceParentXPathPos=0;
		int SourceXPathPos=0;
		int SourcePrimaryKeyPos=0;
		int SourceParentValuePos=0;
		int SourceValuePos=0;
		int TargetParentXPathPos=0;
		int TargetXPathPos=0;
		int TargetPrimaryKeyPos=0;
		int TargetParentValuePos=0;
		int TargetValuePos=0;
		int SourceIDocAttributesPos=0;
		int TargetIDocAttributesPos=0;
		int Validation_SPConditionPos=0;
		int ConditionalParameterPos=0;
		int StatusPos=0;
		int CommentsPos=0;
		try{
		SourceParentXPathPos=(((Integer)dict1.get("SourceParentXPath"))!=null)?((Integer)dict1.get("SourceParentXPath")).intValue():((Integer)dict1.get("SourceUniqueXPath")).intValue();
		SourceXPathPos=((Integer)dict1.get("SourceXPath")).intValue();
		SourcePrimaryKeyPos=((Integer)dict1.get("SourcePrimaryKey")).intValue();
		SourceParentValuePos=((Integer)dict1.get("SourceParentValue")).intValue();
		SourceValuePos=((Integer)dict1.get("SourceValue")).intValue();
		TargetParentXPathPos=(((Integer)dict1.get("TargetParentXPath"))!=null)?((Integer)dict1.get("TargetParentXPath")).intValue():((Integer)dict1.get("TargetUniqueXPath"));
		TargetXPathPos=((Integer)dict1.get("TargetXPath")).intValue();
		TargetPrimaryKeyPos=((Integer)dict1.get("TargetPrimaryKey")).intValue();
		TargetParentValuePos=((Integer)dict1.get("TargetParentValue")).intValue();
		TargetValuePos=((Integer)dict1.get("TargetValue")).intValue();
		SourceIDocAttributesPos=((Integer)dict1.get("SourceIDocAttributes")).intValue();
		TargetIDocAttributesPos=((Integer)dict1.get("TargetIDocAttributes")).intValue();
		Validation_SPConditionPos=((Integer)dict1.get("Validation_SPCondition")).intValue();
		ConditionalParameterPos=((Integer)dict1.get("ConditionalParameter")).intValue();
		StatusPos=((Integer)dict1.get("Status")).intValue();
		CommentsPos=((Integer)dict1.get("Comments")).intValue();
		}catch(Exception e){
			if(popups)
			JOptionPane.showMessageDialog(null, "Please make sure we have all the columns present in the Mapping Sheet");
			else
				System.out.println("Please make sure we have all the columns present in the Mapping Sheet");
			System.exit(1);
		}
		//end getting positions
		List<ExcellRow> sourceRows=null;
		List<ExcellRow> targetRows=null;
		List<ExcellRow> sourcePrintableRows=null;
		List<ExcellRow> targetPrintableRows=null;
		Map<Integer,String> comments=new HashMap<Integer, String>();
		List<SplConditions> finalConditionsToPrint=null;
		/*if(sourceType=="XML")
		{
			System.out.println(">>>>>>>>>>>>>>>>>>>>src:xml");
			sourceRows=new HashMap<String,ExcellRow>();
		}
		*/
		if(sourceType.equalsIgnoreCase("IDOC")||sourceType.equalsIgnoreCase("XML")||sourceType.equalsIgnoreCase("FlatFile")||sourceType.equalsIgnoreCase("FlatFile (TrimSpaces)")||sourceType.equalsIgnoreCase("CSV")||sourceType.equalsIgnoreCase("CSV (TrimSpaces)")||sourceType.equalsIgnoreCase("Delimited")||sourceType.equalsIgnoreCase("Delimited (TrimSpaces)"))
		{
			sourceRows=new ArrayList<ExcellRow>();
		}
		if(targetType.equalsIgnoreCase("IDOC")||targetType.equalsIgnoreCase("XML")||targetType.equalsIgnoreCase("FlatFile")||targetType.equalsIgnoreCase("FlatFile (TrimSpaces)")||targetType.equalsIgnoreCase("CSV")||targetType.equalsIgnoreCase("CSV (TrimSpaces)")||targetType.equalsIgnoreCase("Delimited")||targetType.equalsIgnoreCase("Delimited (TrimSpaces)"))
		{
			targetRows=new ArrayList<ExcellRow>();
		}
		Map<Integer,SplConditions> allConditions=new HashMap<Integer,SplConditions>();
		//get XPATH sheet data in structures
		for(int maprow=1;maprow < maprows;maprow++)
		{
		//	System.out.println(maprow);
			Row r=mappingwrksheet.getRow(maprow);
			//System.out.println(r.getCell(SourceParentXPathPos+1));
			r.getCell(SourceParentXPathPos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String SourceParentXPath= r.getCell(SourceParentXPathPos).getStringCellValue();
			r.getCell(SourceXPathPos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String SourceXPath= r.getCell(SourceXPathPos).getStringCellValue();
			r.getCell(SourcePrimaryKeyPos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String SourcePrimaryKey= r.getCell(SourcePrimaryKeyPos).getStringCellValue();
			r.getCell(SourceParentValuePos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String SourceParentValue= r.getCell(SourceParentValuePos).getStringCellValue();
			r.getCell(SourceValuePos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String SourceValue= r.getCell(SourceValuePos).getStringCellValue();
			r.getCell(TargetParentXPathPos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String TargetParentXPath= r.getCell(TargetParentXPathPos).getStringCellValue();
			r.getCell(TargetXPathPos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String TargetXPath= r.getCell(TargetXPathPos,Row.CREATE_NULL_AS_BLANK).getStringCellValue();
			r.getCell(TargetPrimaryKeyPos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String TargetPrimaryKey= r.getCell(TargetPrimaryKeyPos).getStringCellValue();
			r.getCell(TargetParentValuePos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String TargetParentValue= r.getCell(TargetParentValuePos).getStringCellValue();
			r.getCell(TargetValuePos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String TargetValue= r.getCell(TargetValuePos).getStringCellValue();
			r.getCell(Validation_SPConditionPos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String Validation_SPCondition= r.getCell(Validation_SPConditionPos).getStringCellValue();
			r.getCell(ConditionalParameterPos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String ConditionalParameter= r.getCell(ConditionalParameterPos).getStringCellValue();
			r.getCell(SourceIDocAttributesPos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String SourceIDocAttributes= r.getCell(SourceIDocAttributesPos).getStringCellValue();
			r.getCell(TargetIDocAttributesPos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String TargetIDocAttributes= r.getCell(TargetIDocAttributesPos).getStringCellValue();
			r.getCell(CommentsPos,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
			String comment=r.getCell(CommentsPos).getStringCellValue();
			comments.put(new Integer(maprow), comment);
		
		if(sourceType.equalsIgnoreCase("IDOC")||sourceType.equalsIgnoreCase("XML")||sourceType.equalsIgnoreCase("FlatFile")||sourceType.equalsIgnoreCase("FlatFile (TrimSpaces)")||sourceType.equalsIgnoreCase("CSV")||sourceType.equalsIgnoreCase("CSV (TrimSpaces)")||sourceType.equalsIgnoreCase("Delimited")||sourceType.equalsIgnoreCase("Delimited (TrimSpaces)"))
		{
			ExcellRow sourceRow=new ExcellRow(SourceXPath,SourceParentXPath,SourceValue,SourceParentValue,SourcePrimaryKey,SourceIDocAttributes,maprow);
			sourceRows.add(sourceRow);
		}//done
	
		if(targetType.equalsIgnoreCase("IDOC")||targetType.equalsIgnoreCase("XML")||targetType.equalsIgnoreCase("FlatFile")||targetType.equalsIgnoreCase("FlatFile (TrimSpaces)")||targetType.equalsIgnoreCase("CSV")||targetType.equalsIgnoreCase("CSV (TrimSpaces)")||targetType.equalsIgnoreCase("Delimited")||targetType.equalsIgnoreCase("Delimited (TrimSpaces)"))
		{
			ExcellRow targetRow=new ExcellRow(TargetXPath,TargetParentXPath,TargetValue,TargetParentValue,TargetPrimaryKey,TargetIDocAttributes,maprow);
			targetRows.add(targetRow);
		}
			
			allConditions.put(maprow,new SplConditions(maprow,Validation_SPCondition,ConditionalParameter));
		}//done taking the xpath sheet data
		fis.close();//8 june 2014
		mappingwrkbook=null;
		mappingwrksheet=null;
		System.gc();
		fcc.deleteCopy(tmpName);
		
		LoggingClass.logger.info("src rows="+sourceRows);//add to log the source rows from xpath
		
		if(sourceType.equalsIgnoreCase("IDOC")||sourceType.equalsIgnoreCase("XML"))
		{
		IDOCParser.init();
		IDOCParser p=IDOCParser.getParser();
		p.start(sourceFileLocation,sourceRows,allConditions,true);
		//System.out.println("src rows="+sourceRows);
		
		sourcePrintableRows=p.getPrintableRows();
		finalConditionsToPrint=new ArrayList<SplConditions>();//final conditions to print
		}
		else if(sourceType.equalsIgnoreCase("FlatFile")||sourceType.equalsIgnoreCase("FlatFile (TrimSpaces)"))
		{
			//FFReader_New.init();
			//FFReader_New p=FFReader_New.getParser();
			FFParser_New p=new FFParser_New();
			
			if(sourceType.equalsIgnoreCase("FlatFile (TrimSpaces)"))
				p.start(srcHRow,sourceFileLocation,sourceRows,allConditions,true,true);
			else
				p.start(srcHRow,sourceFileLocation,sourceRows,allConditions,true,false);
			//System.out.println("src rows="+sourceRows);
			sourcePrintableRows=p.getPrintableRows();
			finalConditionsToPrint=new ArrayList<SplConditions>();//final conditions to print
		}
		else if(sourceType.equalsIgnoreCase("CSV")||sourceType.equalsIgnoreCase("CSV (TrimSpaces)")||sourceType.equalsIgnoreCase("Delimited")||sourceType.equalsIgnoreCase("Delimited (TrimSpaces)"))
		{
			/*CSVIDOCParser.init();
			CSVIDOCParser p=CSVIDOCParser.getParser();
			*/
			CSVParser_New.init();
			CSVParser_New p=CSVParser_New.getParser();
			
		
			if(sTextQual!=null&&sTextQual.length()>0)
			{
				String[] regexes="!,$,(,),*,+,-,?,[,\\,],^,|".split(",");
				String[] delimiterArr=sTextQual.split("");
				String x="";
				outer: for(String sd:delimiterArr)
				{
				for(String s:regexes)
				{
					if(sd.equals(s))
					{
						//sd="\\"+sd;
						x=x+"\\"+s;
						continue outer;
					}
					
				}
				x+=sd;
				}
				sTextQual=x;
				}
			if(sourceType.equalsIgnoreCase("CSV (TrimSpaces)")||sourceType.equals("Delimited (TrimSpaces)"))
				p.start(srcHRow,sourceFileLocation,sourceRows,sdelimiter,sdelimiter2,allConditions,true,sTextQual,true);
			else
				p.start(srcHRow,sourceFileLocation,sourceRows,sdelimiter,sdelimiter2,allConditions,true,sTextQual,false);
			//System.out.println("src rows="+sourceRows);
			sourcePrintableRows=p.getPrintableRows();
			finalConditionsToPrint=new ArrayList<SplConditions>();//final conditions to print
		}
		LoggingClass.logger.info("src printable rows="+sourcePrintableRows);
		//System.out.println("SPR: "+sourcePrintableRows);//src printable rows
		for(int excelrow=0;excelrow < sourcePrintableRows.size();excelrow++)
		{
			int rownumber=sourcePrintableRows.get(excelrow).getOccurances().get(0);
//			System.out.println(">>>>"+rownumber)
			SplConditions condition=(allConditions.get(rownumber));
			LoggingClass.logger.info("Found Row: "+rownumber);
			finalConditionsToPrint.add(condition);
			String ValidationCondition1=condition.getValidationSpCondition();
			String ConditionalParameter1=condition.getValidationParameter();
			String SourceAfterConversion=sourcePrintableRows.get(excelrow).getValue();
						if(ValidationCondition1!="") //conditional check --Ankur
						{
							//condition present so transform_write --Ankur
							//split and loop at each element for recursive processing Ankur 8Jan
							String[] conditions=ValidationCondition1.split(">>");
							String[] parameters=ConditionalParameter1.split(">>");
					
							//System.out.println("Cond,Param="+conditions+","+parameters);
							for(int paramNo=0;paramNo<conditions.length;paramNo++)
							{//System.out.println(conditions[paramNo]);
							//loop--Ankur 8Jan
							String ValidationCondition=conditions[paramNo];
							String ConditionalParameter=parameters[paramNo];
							//System.out.println("Src Before Conversion:"+SourceAfterConversion);
							if(ValidationCondition.equalsIgnoreCase("Hardcoded"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.hardcode(SourceAfterConversion,ConditionalParameter);
							}
							else if(ValidationCondition.equalsIgnoreCase("Decode"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.decode(SourceAfterConversion,ConditionalParameter);
							}
							else if(ValidationCondition.equalsIgnoreCase("DateTransform"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.date_converter(SourceAfterConversion,ConditionalParameter);
							}
							else if(ValidationCondition.equalsIgnoreCase("Extract"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.extractor(SourceAfterConversion,ConditionalParameter);
							}
							else if(ValidationCondition.equalsIgnoreCase("Trim"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.trim(SourceAfterConversion,ConditionalParameter);
							}
							else if(ValidationCondition.equalsIgnoreCase("Padding"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.padding(SourceAfterConversion,ConditionalParameter);
							}
							else if(ValidationCondition.equalsIgnoreCase("Date2"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.date2(SourceAfterConversion,ConditionalParameter);
							}
							else if(ValidationCondition.equalsIgnoreCase("IfElse"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.ifelse(SourceAfterConversion,ConditionalParameter,sourcePrintableRows,excelrow);
							}
							else if(ValidationCondition.equalsIgnoreCase("Concatenate"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.concat(SourceAfterConversion,ConditionalParameter,sourcePrintableRows,excelrow);
							}
							else if(ValidationCondition.equalsIgnoreCase("Occurrence"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.occurance(SourceAfterConversion,ConditionalParameter,sourcePrintableRows,excelrow);
							}
							else if(ValidationCondition.equalsIgnoreCase("Multiply"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.multiply(SourceAfterConversion,ConditionalParameter,sourcePrintableRows,excelrow);
							}
							else if(ValidationCondition.equalsIgnoreCase("Encode"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.encode(SourceAfterConversion,ConditionalParameter);
							}
							else if(ValidationCondition.equalsIgnoreCase("PlusToPercent"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.getPlusToPercentage(SourceAfterConversion);
							}
							else if(ValidationCondition.equalsIgnoreCase("Crop"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.crop(SourceAfterConversion,ConditionalParameter);
							}
							else if(ValidationCondition.equalsIgnoreCase("Substring"))
							{
								SourceAfterConversion=com.validator.qa.utils.Decoder.substring(SourceAfterConversion,ConditionalParameter);
							}
							else{
								//System.out.println(ValidationCondition+" _Default");
							}
							}
							//end split and loop at each element for recursive processing Ankur 8Jan
							//System.out.println("Src After Conversion:"+SourceAfterConversion);
						}
						else
						{
						//do nothing as of now
						}	// End conditional check --Ankur
						sourcePrintableRows.get(excelrow).setValue(SourceAfterConversion);
			
		}

System.gc();
	
		LoggingClass.logger.info("Target rows="+targetRows);//add to log file, target rows from xpath sheet

		if(targetType.equalsIgnoreCase("IDOC")||targetType.equalsIgnoreCase("XML"))
		{
		IDOCParser.init();
		IDOCParser p2=IDOCParser.getParser();
		p2.start(targetFileLocation,targetRows,null,false);
		//System.out.println("TargetRows:"+targetRows);
		targetPrintableRows=p2.getPrintableRows();
		}
		else if(targetType.equalsIgnoreCase("FlatFile")||targetType.equalsIgnoreCase("FlatFile (TrimSpaces)"))
		{
		//FFIDOCParser.init();
		//FFIDOCParser p2=FFIDOCParser.getParser();
			FFParser_New p2=new FFParser_New();
		if(targetType.equalsIgnoreCase("FlatFile (TrimSpaces)"))
			p2.start(tgtHRow,targetFileLocation,targetRows,allConditions,false,true);
		else
			p2.start(tgtHRow,targetFileLocation,targetRows,allConditions,false,false);
		//System.out.println("TargetRows:"+targetRows);
		targetPrintableRows=p2.getPrintableRows();
		}
		else if(targetType.equalsIgnoreCase("CSV")||targetType.equalsIgnoreCase("CSV (TrimSpaces)")||targetType.equalsIgnoreCase("Delimited")||targetType.equalsIgnoreCase("Delimited (TrimSpaces)"))//Delimited (TrimSpaces)
		{
		/*CSVIDOCParser.init();
		CSVIDOCParser p2=CSVIDOCParser.getParser();
		*/
			CSVParser_New.init();
			CSVParser_New p2=CSVParser_New.getParser();
		
		if(tTextQual!=null&&tTextQual.length()>0)
		{
			String[] regexes="!,$,(,),*,+,-,?,[,\\,],^,|".split(",");
		//
		String[] delimiterArr=tTextQual.split("");
		String x="";
		outer: for(String td:delimiterArr)
		{
		for(String s:regexes)
		{
			if(td.equals(s))
			{
				//sd="\\"+sd;
				x=x+"\\"+s;
				continue outer;
			}
			
		}
		x+=td;
		}
		tTextQual=x;
		}

		if(targetType.equalsIgnoreCase("CSV (TrimSpaces)")||targetType.equalsIgnoreCase("Delimited (TrimSpaces)"))
			p2.start(tgtHRow,targetFileLocation,targetRows,tdelimiter,tdelimiter2,allConditions,false,tTextQual,true);
		else
			p2.start(tgtHRow,targetFileLocation,targetRows,tdelimiter,tdelimiter2,allConditions,false,tTextQual,false);
		//System.out.println("TargetRows:"+targetRows);
		targetPrintableRows=p2.getPrintableRows();
		}
		
		LoggingClass.logger.info("Target Printable rows Original="+targetPrintableRows);
		//System.out.println("TargetPrintableRowsOriginal"+targetPrintableRows);//
		//Simple write without any logic
		LoggingClass.logger.info("Now Matching");
		
		mappingwrksheetName="Sheet1";
	
 System.gc();
				SourceTargetXpathMatcher matcher=new SourceTargetXpathMatcher();
				Map<Integer,ExcellRow> srcAllVals=new HashMap<Integer,ExcellRow>();
				Map<Integer,ExcellRow> targAllVals=new HashMap<Integer,ExcellRow>();
				boolean matched=false;
				int maprow=0;
				for(int excelrow=0;excelrow < sourcePrintableRows.size();excelrow++)
				{
//					System.out.println(".");
					maprow=excelrow+1;
					//System.out.println((maprow/sourcePrintableRows.size())*100+"% done");
				//source
				if(excelrow < sourcePrintableRows.size())
				{
					ExcellRow s=sourcePrintableRows.get(excelrow);
					//com.nike.aase.qa.RunExcl.writeToXL(MappingExcelFile,SourceParentXPathPos,SourceXPathPos,SourcePrimaryKeyPos,SourceParentValuePos,SourceValuePos,maprow,s,mappingwrksheetName)
					srcAllVals.put(maprow,s);
					 matched=false;
				for(ExcellRow t:targetPrintableRows)
				{
					//System.out.println("cps->"+s.getOccurances().get(0)+"cpt->"+t.getOccurances().get(0));
				if(s.getOccurances().get(0).equals(t.getOccurances().get(0)))
				{
			
					
				boolean xpathMatched=matcher.compareRows(sourceRows,targetRows,s,t);
			
					if(xpathMatched)
					{
						targAllVals.put(maprow,t);
						matched=true;
						targetPrintableRows.remove(t);
						break;
						
					}
					

				}
				}
				if(!matched)
				{
					boolean tmpStat=false;
					for(ExcellRow r:targetRows)
					{
						if(r.getOccurances().containsAll(s.getOccurances()))
						{
							tmpStat=true;
							ExcellRow notFoundRow= new ExcellRow();
							notFoundRow.setXpath(r.getXpath());
							notFoundRow.setParentXpath(r.getParentXpath());
							notFoundRow.setIdocAttributes(r.getIdocAttributes());
							//notFoundRow.setPk("");
							notFoundRow.setPk("Target Not Found");
							targAllVals.put(maprow,notFoundRow);
							//System.out.println("))))))))))))))))"+r.getXpath());
						}
					}
					if(!tmpStat)
					{
					targAllVals.put(maprow,new ExcellRow());
					}
				}

				}}
				LoggingClass.logger.info("---total_rows----"+maprow+"---spr_size---"+sourcePrintableRows.size());
				//System.out.println("---total_rows----"+maprow+"---spr_size---"+sourcePrintableRows.size());
		//System.out.println(sourceRows);
		//System.out.println(targetRows);
		LoggingClass.logger.info("src rows="+srcAllVals+"\ntarg rows="+targAllVals+"\nWriting");
		//System.out.println(srcAllVals);
		//System.out.println(targAllVals);
		//System.out.println("Writing");
		LoggingClass.logger.info("\n========Src Rows Mentioned in STM but not present in output due to lack/mismatch of input data for them:========");
		String missedRs="\n";
		for(ExcellRow sr:sourceRows)
		{
			if(!sr.isUsed())
				missedRs+="RowNum="+sr.getOccurances()+",\tUniqueXpath="+sr.getParentXpath()+",\tXpath="+sr.getXpath()+",\tIdocAttributes="+sr.getIdocAttributes()+"\n";
		}
		LoggingClass.logger.info(missedRs+"\n");
		LoggingClass.logger.info("\n========Target Rows Mentioned in STM but not present in output due to lack/mismatch of input data for them:========");
		missedRs="\n";
		for(ExcellRow tr:targetRows)
		{
			if(!tr.isUsed())
				missedRs+="RowNum="+tr.getOccurances()+",\tUniqueXpath="+tr.getParentXpath()+",\tXpath="+tr.getXpath()+",\tIdocAttributes="+tr.getIdocAttributes()+"\n";
		}
		LoggingClass.logger.info(missedRs+"\n");
System.gc();
		//com.nike.aase.qa.RunExcl.writeAllToXLWithPassFail2(MappingExcelFile,SourceParentXPathPos,SourceXPathPos,SourcePrimaryKeyPos,SourceParentValuePos,SourceValuePos,TargetParentXPathPos,TargetXPathPos,TargetPrimaryKeyPos,TargetParentValuePos,TargetValuePos,SourceIDocAttributesPos,TargetIDocAttributesPos,StatusPos,Validation_SPConditionPos,ConditionalParameterPos,srcAllVals,targAllVals,finalConditionsToPrint,mappingwrksheetName);
		String res=com.validator.excel.ReadWriteExcel.writeAllToXLWithPassFail_newFile2(MappingExcelFile,SourceParentXPathPos,SourceXPathPos,SourcePrimaryKeyPos,SourceParentValuePos,SourceValuePos,TargetParentXPathPos,TargetXPathPos,TargetPrimaryKeyPos,TargetParentValuePos,TargetValuePos,SourceIDocAttributesPos,TargetIDocAttributesPos,StatusPos,Validation_SPConditionPos,ConditionalParameterPos,srcAllVals,targAllVals,finalConditionsToPrint,mappingwrksheetName,cellArray,CommentsPos,comments);
		//System.out.println("Done");
		if(popups)
		JOptionPane.showMessageDialog(parent, "Output Saved at "+res);
		LoggingClass.logger.info("Output Saved at ="+res+"\n================================================================END===============================================================");
		//"Done"
System.gc();
	}
}
