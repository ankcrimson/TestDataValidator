package com.ui.launcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.validator.VersionManager;
import com.validator.log.LoggingClass;

public class CLLauncher {

	/**
	 * @param args
	 * D:\remit_adv_in\ID_16_Output_FusionOut.txt
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CoreLogic sui=new CoreLogic();
		System.out.println("Test Data Validator "+VersionManager.version);
		Properties prop = new Properties();
		String sourceType="";
		String sourceDelimiter="";
		String sourceQualifier="";
		String sourceHeader="";
		String targetType="";
		String targetDelimiter="";
		String targetQualifier="";
		String targetHeader="";
		try {
               //load a properties file
    		prop.load(new FileInputStream("Properties.txt"));
 
               //get the property value and print it out
    		sourceType=(prop.getProperty("SourceType"));
    		sourceDelimiter=(prop.getProperty("SourceDelimiter"));
    		sourceQualifier=(prop.getProperty("SourceQualifier"));
    		sourceHeader=(prop.getProperty("SourceHeader"));
    		targetType=(prop.getProperty("TargetType"));
    		targetDelimiter=(prop.getProperty("TargetDelimiter"));
    		targetQualifier=(prop.getProperty("TargetQualifier"));
    		targetHeader=(prop.getProperty("TargetHeader"));
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
		
		
		if(args.length<4)
		{
			System.out.println("usage: xxx SrcFileLoc TgtFileLoc MapFileLoc SheetName");
			System.exit(1);
		}
		String sourceFileLocation=args[0];
		//String sourceFileLocation="C:/Users/319113/Documents/returnsRecipt/idm.txt";
		String targetFileLocation=args[1];
		//String targetFileLocation="C:/Users/319113/Documents/Mexico_RS_IO/970_ReturnStatus_1.txt";
		
		String MappingExcelFile=args[2];
		
		String mappingwrksheetName=args[3];
		
		boolean shead=false;
		if(sourceHeader!=null&&sourceHeader.equalsIgnoreCase("true"))
			shead=true;
		
		boolean thead=false;
		if(targetHeader!=null&&targetHeader.equalsIgnoreCase("true"))
			thead=true;
		/*String sourceType="FlatFile (TrimSpaces)";
		String targetType="IDOC";
		String sourceFileLocation="d:/dmr/jCapsInput_IDOC_Number_0000001836655486_1.txt";
		String targetFileLocation="d:/dmr/Idoc -0000001836655877_1.txt";
		String MappingExcelFile="d:/dmr/DMR1.xls";
		*/
		//String mappingwrksheetName="X-Path";
		try{
		sui.imitator(false,sourceType, targetType, sourceFileLocation, targetFileLocation, MappingExcelFile, mappingwrksheetName,null,sourceDelimiter,targetDelimiter,sourceQualifier,targetQualifier,shead,thead);
		}catch(Exception e)
		{
			e.printStackTrace();
			//System.out.println(e);
			LoggingClass.logger.error("Exception", e);
		}
	}

}
