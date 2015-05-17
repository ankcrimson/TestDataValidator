package com.validatorcsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.validator.exceptions.CSVXPathException;
import com.validator.log.LoggingClass;
import com.validator.qa.idoc.ExcellRow;
import com.validator.qa.idoc.SplConditions;

public class CSVReader_New {
	ArrayList<ExcellRow> printableRows=new ArrayList<ExcellRow>();
	Map<String,String> parentVals=new HashMap<String, String>();
	public ArrayList<ExcellRow> getPrintableRows() {
		return printableRows;
	}
	public void setPrintableRows(ArrayList<ExcellRow> printableRows) {
		this.printableRows = printableRows;
	}

	public String[] getElements(String s, String d, String q)
	{
		ArrayList<String> al=new ArrayList<String>();
		int start=0;
		int end=0;
		//System.out.println(q.length());
		while(start<s.length())
		{
			
		if(q.length()!=0&&s.substring(start, (start+q.length())).equals(q))
		{
			if(s.substring(start, (start+d.length())).equals(d))
				start+=d.length();	
			start+=q.length();
		    end=s.indexOf(q, start);
		    al.add(s.substring(start, end));
		    start=end+q.length()+d.length();
		}
		else
		{
			end=s.indexOf(d,start);
			if(end<0)
				end=s.length();
			al.add(s.substring(start, end));
			start=end+d.length();
		}
		}
	al.add("");
String[] arr=new String[al.size()];
		for(int i=0;i<al.size();i++)
		{
			arr[i]=al.get(i);
		}
		return arr;
	}
	
public void read(boolean hRow,String filename,List<ExcellRow> rows,String delimiter,String delimiter2,Map<Integer,SplConditions> allConditions,boolean source,String tQual,boolean ...trim)
{
	try{
	//File f=new File(filename);
	//FileReader fr=new FileReader(f);
	//Scanner fileRead=new Scanner(new FileInputStream(filename), "UTF-8");//new read mech
		FileInputStream fis = new FileInputStream(filename);
		InputStreamReader isr = new InputStreamReader(fis, "UTF8");
	BufferedReader br=new BufferedReader(isr);
	//Scanner br=fileRead;
	if(hRow)
		br.readLine();
		//br.nextLine();
	String frow="";
	while((frow=br.readLine())!=null)
	//while(br.hasNext()&&(frow=br.nextLine())!=null)
	{
		/*frow+=delimiter2+"Last Element";
		if(tQual.length()>0)
		{
			frow=frow.replaceAll(tQual, "");
		}
		String[] elements=frow.split(delimiter);*/
		String[] elements=getElements(frow, delimiter, tQual);
		List<ExcellRow> currRowRows=new ArrayList<ExcellRow>();
		for(ExcellRow r:rows)
		{
			String xp=r.getXpath();
			String ida=r.getIdocAttributes();
			String pxp=r.getParentXpath();
			String[] idas=ida.split(";");
			
			boolean selected=true;
			if(ida.length()>1)
			for(String cida:idas)
			{
				String[] keyVal=cida.split("=");
				int index=Integer.parseInt(keyVal[0].split("[(]|[)]")[1]);
				if(!(elements[index].equals(keyVal[1])))
				{
					selected=false;
					String val=null;
					for(ExcellRow row:printableRows)
					{
						if(row.getXpath().equals(keyVal[0]))
							val=row.getValue();
					}
					if(val!=null&&val.equals(keyVal[1]))
						selected=true;
					if(!selected)
					break;
				}
			}
			try{
			if(selected)
			{
				
				String val=elements[Integer.parseInt(xp.split("[(]|[)]")[1])];
				if(trim!=null&&trim.length>0&&trim[0]){
					val=val.trim();
				}
				parentVals.put(xp, val);
				//System.out.println(xp+"-"+parentVals.get(xp));
				ExcellRow newRow=new ExcellRow(xp, pxp, val, "", r.getPk(), ida, r.getOccurances().get(0));
				printableRows.add(newRow);
				currRowRows.add(newRow);
				r.setUsed(true);
			}
			}catch(Exception e){throw new CSVXPathException();}
		}
		//adding parent vals
		for(ExcellRow row:currRowRows)
		{	
			String pxp=row.getParentXpath();
			String[] pxps=pxp.split(";");
			String pxpval="Not Found";
			for(String cpxp:pxps)
			{
				String pval=parentVals.get(cpxp);
				if(pval==null)pval="";
				if(trim!=null&&trim.length>0&&trim[0]){
					pval=pval.trim();
				}
				pxpval=(pxpval.equals("Not Found"))?pval:pxpval+";"+pval;
			}
			row.setParentValue(pxpval);
		}
	}
	
	}catch(Exception e){
		//JOptionPane.showMessageDialog(null, e.getMessage());e.printStackTrace();
		//System.out.println(e);
		e.printStackTrace();
		String msg=e.toString();
		if(e instanceof CSVXPathException)
		{msg="Error in CSV/Delimited XPath. Please see if you have any extra rows or you forgot to close the bracket anywhere in xpath";}
		LoggingClass.logger.info(msg);
		
		JOptionPane.showMessageDialog(null, msg);
		}
		
}
}
