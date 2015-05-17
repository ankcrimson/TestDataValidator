package com.validator.ff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




import com.validator.qa.idoc.ExcellRow;
import com.validator.qa.idoc.SplConditions;
import com.validatorcsv.CSVReader_New;

public class FFParser_New {

	/**
	 * @param args
	 */
	static FFParser_New p;
	public static FFParser_New getParser()
	{
		return p;
	}
	static ArrayList<ExcellRow> printableRows;
	public static void init()
	{
		p=new FFParser_New();
		printableRows=new ArrayList<ExcellRow>();
	}
	public static ArrayList<ExcellRow> getPrintableRows() {
		return printableRows;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static void start(boolean hRow,String filename,List<ExcellRow> rows,Map<Integer,SplConditions> allConditions,boolean source,boolean ...trim)
	{
		FFReader_New crn=new FFReader_New();
		try{
		crn.read(hRow, filename, rows, allConditions, source, trim);
		printableRows=crn.getPrintableRows();
		}catch(Exception e){e.printStackTrace();}
	}

}
