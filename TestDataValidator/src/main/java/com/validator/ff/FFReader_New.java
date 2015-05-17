package com.validator.ff;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.validator.log.LoggingClass;
import com.validator.qa.idoc.ExcellRow;
import com.validator.qa.idoc.SplConditions;

public class FFReader_New {
	ArrayList<ExcellRow> printableRows=new ArrayList<ExcellRow>();
	Map<String,String> parentVals=new HashMap<String, String>();
	public ArrayList<ExcellRow> getPrintableRows() {
		return printableRows;
	}
	public void setPrintableRows(ArrayList<ExcellRow> printableRows) {
		this.printableRows = printableRows;
	}
public void read(boolean hRow,String filename,List<ExcellRow> rows,Map<Integer,SplConditions> allConditions,boolean source,boolean ...trim)
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
		
		//String[] elements=frow.split(delimiter);
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
				try{
				String[] keyVal=cida.split("=");
				String[] index=keyVal[0].split(":");
				int start=Integer.parseInt(index[0]);
				int len=Integer.parseInt(index[1]);
				String idaVal=frow.substring(start,start+len);
				if(trim.length>0&&trim[0])
					idaVal=idaVal.trim();
				if(!((idaVal).equals(keyVal[1])))
				{
					selected=false;
					break;
				}
				}catch(Exception e){e.printStackTrace();LoggingClass.logger.info("Error in IdocArrtibutePath"+ida+" - "+e.getLocalizedMessage());}
			}
			if(selected)
			{
				try{
				String[] index=xp.split(":");
				//System.out.println(index[0]+"____"+index[1]);
				int start=Integer.parseInt(index[0]);
				int len=Integer.parseInt(index[1]);
				
				if(index.length==5)//very very old xpath
				{
					start=Integer.parseInt(index[3]);
					len=Integer.parseInt(index[4]);
					String idattr=frow.substring(Integer.parseInt(index[0]), Integer.parseInt(index[1]));
					if(trim.length>0&&trim[0])
						idattr=idattr.trim();
					if(!idattr.equals(index[2]))
					{continue;}
				}
				
				String val=frow.substring(start,start+len);//elements[Integer.parseInt(xp.split("[(]|[)]")[1])];
				if(trim.length>0&&trim[0])
					val=val.trim();
				parentVals.put(xp, val);
				ExcellRow newRow=new ExcellRow(xp, pxp, val, "", "", ida, r.getOccurances().get(0));
				printableRows.add(newRow);
				currRowRows.add(newRow);
				r.setUsed(true);
				}catch(Exception e){e.printStackTrace();LoggingClass.logger.info("Error in Xpath"+xp+" - "+e.getLocalizedMessage());}
			}
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
				pxpval=(pxpval.equals("Not Found"))?pval:pxpval+";"+pval;
			}
			row.setParentValue(pxpval);
		}
	}
	
	}catch(Exception e){JOptionPane.showMessageDialog(null, e.getMessage());e.printStackTrace();}
}
}
