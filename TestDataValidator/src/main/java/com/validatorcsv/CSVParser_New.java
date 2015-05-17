package com.validatorcsv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.validator.qa.idoc.ExcellRow;
import com.validator.qa.idoc.SplConditions;

public class CSVParser_New {

	/**
	 * @param args
	 */
	static CSVParser_New p;
	public static CSVParser_New getParser()
	{
		return p;
	}
	static ArrayList<ExcellRow> printableRows;
	public static void init()
	{
		p=new CSVParser_New();
		printableRows=new ArrayList<ExcellRow>();
	}
	public static ArrayList<ExcellRow> getPrintableRows() {
		return printableRows;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static void start(boolean hRow,String filename,List<ExcellRow> rows,String delimiter,String delimiter2,Map<Integer,SplConditions> allConditions,boolean source,String tQual,boolean ...trim)
	{
		CSVReader_New crn=new CSVReader_New();
		try{
		crn.read(hRow, filename, rows, delimiter,delimiter, allConditions, source, tQual, trim);
		printableRows=crn.getPrintableRows();
		}catch(Exception e){e.printStackTrace();}
	}

}
