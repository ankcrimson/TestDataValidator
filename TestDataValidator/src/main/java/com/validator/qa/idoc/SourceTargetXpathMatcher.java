package com.validator.qa.idoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.validator.log.LoggingClass;

public class SourceTargetXpathMatcher {

	/**
	 * @param args
	 */
	public boolean compareParentXpaths(Map<String,ExcellRow> sourceRows,Map<String,ExcellRow> targetRows,String srcXpath,String tXpath, String srcPXpath, String targPXpath)
	{
		ExcellRow src=sourceRows.get(srcXpath);
		ExcellRow targ=targetRows.get(tXpath);
		if(!(src.getParentXpath().equals(srcPXpath)&&targ.getParentXpath().equals(targPXpath)))
		{
			return false;
		}
		List<Integer> soccurances=src.getOccurances();
		List<Integer> toccurances=targ.getOccurances();
		for(Integer curr:soccurances)
		{
			if(toccurances.contains(curr))
				return true;
		}
		return false;
	}
	public boolean compareXpaths(Map<String,ExcellRow> sourceRows,Map<String,ExcellRow> targetRows,String srcXpath,String tXpath)
	{
		ExcellRow src=sourceRows.get(srcXpath);
		ExcellRow targ=targetRows.get(tXpath);
		if(src==null||targ==null)
		{
			return false;
		}
		List<Integer> soccurances=src.getOccurances();
		List<Integer> toccurances=targ.getOccurances();
		for(Integer curr:soccurances)
		{
			if(toccurances.contains(curr))
				return true;
		}
		return false;
	}
	public boolean compareParentXpaths(List<ExcellRow> sourceRows,List<ExcellRow> targetRows,String srcXpath,String tXpath, String srcPXpath, String targPXpath)
	{
		ExcellRow src=null;
		ExcellRow targ=null;
		for(ExcellRow s:sourceRows)
		{
			if(s.getXpath().equals(srcXpath))
			{
				src=s;
				break;
			}
		}
		for(ExcellRow t:targetRows)
		{
			if(t.getXpath().equals(tXpath))
			{
				targ=t;
				break;
			}
		}
		if(!(src.getParentXpath().equals(srcPXpath)&&targ.getParentXpath().equals(targPXpath)))
		{
			return false;
		}
		List<Integer> soccurances=src.getOccurances();
		List<Integer> toccurances=targ.getOccurances();
		for(Integer curr:soccurances)
		{
			if(toccurances.contains(curr))
				return true;
		}
		return false;
	}
	public boolean compareXpaths(List<ExcellRow> sourceRows,List<ExcellRow> targetRows,String srcXpath,String tXpath)
	{
		ExcellRow src=null;
		ExcellRow targ=null;
		for(ExcellRow s:sourceRows)
		{
			if(s.getXpath().equals(srcXpath))
			{
				src=s;
				break;
			}
		}
		for(ExcellRow t:targetRows)
		{
			if(t.getXpath().equals(tXpath))
			{
				targ=t;
				break;
			}
		}
		if(src==null||targ==null)
		{
			return false;
		}
		List<Integer> soccurances=src.getOccurances();
		List<Integer> toccurances=targ.getOccurances();
		for(Integer curr:soccurances)
		{
			if(toccurances.contains(curr))
				return true;
		}
		return false;
	}
////////////////////////////////////////////////////List Map and Map List remaining
	
	public Collection<ExcellRow> getCollectionFromMap(Map<String,ExcellRow> input)
	{
		return input.values();
	}
	
	public boolean compareRows(Collection<ExcellRow> sourceRows,Collection<ExcellRow> targetRows,ExcellRow src, ExcellRow targ)
	{
		//System.out.println(src);
		//System.out.println(targ);
		Iterator<ExcellRow> srcI=sourceRows.iterator();
		Iterator<ExcellRow> targI=targetRows.iterator();
		ExcellRow tempS=null;
		ExcellRow tempT=null;
		boolean status=false;
		while(srcI.hasNext())
		{
			status=false;
			ExcellRow csrc=srcI.next();
			if(csrc.getXpath().equals(src.getXpath()))//xpath match for src
			{//System.out.println("xpMatched"+src.getXpath()+"-"+csrc.getXpath());
				if(src.getParentXpath()!=null&&src.getParentXpath().length()>0)
				{
					//System.out.println("pxp Present");
					if( src.getParentXpath().equals(csrc.getParentXpath()))//parent match
					{//check for attribute nullness and then for line number
						//System.out.println("pxpMatched");
						if(src.getIdocAttributes()!=null && src.getIdocAttributes().length()>0)
						{
							//System.out.println("sidoca present");
							if(src.getIdocAttributes().equals(csrc.getIdocAttributes()))//idoc attributes match but no parrent present
							{//this is it as of source, now match the rownums and then go for value
							
								if(csrc.getOccurances().containsAll(src.getOccurances()))
								status=true;	
							}
							else
							{
								continue;
							}
						}
						else{//this is it as of source, now match the rownums and then go for value
							if(csrc.getOccurances().containsAll(src.getOccurances()))
							status=true;
						}
					}
					else//no parent xpath relation hence continue
					{
						continue;
					}
			
				}
				else
				{//System.out.println("PXP NA");
					if(src.getIdocAttributes()!=null && src.getIdocAttributes().length()>0)
					{	//System.out.println("SIDA A");
						if(src.getIdocAttributes().equals(csrc.getIdocAttributes()))//idoc attributes match but no parrent present
						{
							if(csrc.getOccurances().containsAll(src.getOccurances()))
								status=true;
						}
						else
						{
							continue;//idoc defined and not matched
						}
					}else {
						if(csrc.getOccurances().containsAll(src.getOccurances()))
							{
							status=true;
							//System.out.println("true for S CA "+csrc.getOccurances()+src.getOccurances());
							}
					}//no parent no idoc present match rownum now
					
				}
			}
		if(status)
			{
			break;
			}
		}
		
		boolean tstatus=false;
		if(status)
		while(targI.hasNext())
		{
			tstatus=false;
			ExcellRow ctarg=targI.next();
			if(ctarg.getXpath().equals(targ.getXpath()))//xpath match for src
			{//System.out.println("xpMatched T"+ctarg.getXpath()+"-"+targ.getXpath());
				if(targ.getParentXpath()!=null&&targ.getParentXpath().length()>0)
				{
					//System.out.println("TPXP A");
					if(src.getParentValue()!=null&&targ.getParentValue()!=null&&src.getParentValue().equals(targ.getParentValue()))
					if( targ.getParentXpath().equals(ctarg.getParentXpath()))//parent match
					{//check for attribute nullness and then for line number
					
						if(targ.getIdocAttributes()!=null && targ.getIdocAttributes().length()>0)
						{
							if(targ.getIdocAttributes().equals(ctarg.getIdocAttributes()))//idoc attributes match but no parrent present
							{//this is it as of source, now match the rownums and then go for value
								if(ctarg.getOccurances().containsAll(targ.getOccurances()))
									tstatus=true;	
							}
							else
							{
								continue;
							}
						}
						else{//this is it as of source, now match the rownums and then go for value
							if(ctarg.getOccurances().containsAll(targ.getOccurances()))
								tstatus=true;
						}
					}
					else//no parent xpath relation hence continue
					{
						continue;
					}
			
				}
				else
				{ //System.out.println("TPXP NA");
					if(src.getParentXpath()!=null && src.getParentXpath().length()>0)
					{
						//JOptionPane.showMessageDialog(null, "Unique XPath value present for source, but no matching value found for target for : "+targ.getXpath());
						LoggingClass.logger.info( "Unique XPath value present for source, but no matching value found for target for : "+targ.getXpath());
						continue;
					}
					if(targ.getIdocAttributes()!=null && targ.getIdocAttributes().length()>0)
					{	//System.out.println("TID A");
						if(targ.getIdocAttributes().equals(ctarg.getIdocAttributes()))//idoc attributes match but no parrent present
						{//System.out.println("TID Match");
							if(ctarg.getOccurances().containsAll(targ.getOccurances()))
								{
								//System.out.println(">>TM"+src.getValue()+targ.getValue());
								tstatus=true;
								
								}
						}
						else
						{
							continue;//idoc defined and not matched
						}
					}else {
						if(ctarg.getOccurances().containsAll(targ.getOccurances()))
							tstatus=true;
					}//no parent no idoc present match rownum now
					
				}
			}
		if(tstatus)break;
		}
		//if(!tstatus)
			//System.out.println(src.getOccurances().get(0));
		return tstatus;
	}
	/////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<ExcellRow> sourcePrintableRows=new ArrayList<ExcellRow>();
		sourcePrintableRows.add(new ExcellRow("//DigSales/Tran/BillDate","","2012-12-01",null,"",1));
		sourcePrintableRows.add(new ExcellRow("//DigSales/Tran/BillTime","","2012-12-01T17:55:59Z",null,"",2));
		sourcePrintableRows.add(new ExcellRow("//DigSales/Tran/OrderDate","","2012-12-01",null,"",3));

		List<ExcellRow> targetPrintableRows=new ArrayList<ExcellRow>();
		targetPrintableRows.add(new ExcellRow("//R/A/B/matlnbr","","66666666-888",null,"","//R/A/qualf=001,//R/A/B/tdid=002",1));
		targetPrintableRows.add(new ExcellRow("//R/A/C/size","","XL",null,"","//R/A/qualf=001,//R/A/C/qualf=003",2));
		targetPrintableRows.add(new ExcellRow("//R/A/C/size","","M",null,"","//R/A/qualf=001,//R/A/C/qualf=001",3));
		
		
		List<ExcellRow> sourceRows=new ArrayList<ExcellRow>();
		sourceRows.add(new ExcellRow("//DigSales/Tran/BillDate","","","","",1));
		sourceRows.add(new ExcellRow("//DigSales/Tran/BillTime","","","","",2));
		sourceRows.add(new ExcellRow("//DigSales/Tran/OrderDate","","","","",3));

		List<ExcellRow> targetRows=new ArrayList<ExcellRow>();
		targetRows.add(new ExcellRow("//R/A/B/matlnbr","","","","","//R/A/qualf=001,//R/A/B/tdid=002",1));
		targetRows.add(new ExcellRow("//R/A/C/size","","","","","//R/A/qualf=001,//R/A/C/qualf=003",2));
		targetRows.add(new ExcellRow("//R/A/C/size","","","","","//R/A/qualf=001,//R/A/C/qualf=001",3));
		
		
		
		
		SourceTargetXpathMatcher matcher=new SourceTargetXpathMatcher();
		Map<Integer,ExcellRow> srcAllVals=new HashMap<Integer,ExcellRow>();
		Map<Integer,ExcellRow> targAllVals=new HashMap<Integer,ExcellRow>();
		boolean matched=false;
		for(int excelrow=0;excelrow < sourcePrintableRows.size();excelrow++)
		{
//			log.info(".");
			int maprow=excelrow+1;
			System.out.println((maprow/sourcePrintableRows.size())*100+"% done");
		//source
		if(excelrow < sourcePrintableRows.size())
		{
			ExcellRow s=sourcePrintableRows.get(excelrow);
			//com.nike.aase.qa.RunExcl.writeToXL(MappingExcelFile,SourceParentXPathPos,SourceXPathPos,SourcePrimaryKeyPos,SourceParentValuePos,SourceValuePos,maprow,s,mappingwrksheetName)
			srcAllVals.put(maprow,s);
			 matched=false;
		for(ExcellRow t:targetPrintableRows)
		{
		if(s.getOccurances().get(0)==t.getOccurances().get(0))
		{
		//def boolean xpathMatched=matcher.compareXpaths(sourceRows,targetRows,s.getXpath(),t.getXpath())//commented on 29th Jan
		boolean xpathMatched=matcher.compareRows(sourceRows,targetRows,s,t);
		System.out.println();
		//log.info(xpathMatched);
		//log.info("xpathMatched="+xpathMatched);
			if(xpathMatched)
			{
				targAllVals.put(maprow,t);
				matched=true;
				break;
				
			}
			

		}
		}
		if(!matched)
		{
			//log.info("no match found");
			//com.nike.aase.qa.RunExcl.writeToXL(MappingExcelFile,TargetParentXPathPos,TargetXPathPos,TargetPrimaryKeyPos,TargetParentValuePos,TargetValuePos,maprow,new ExcellRow(),mappingwrksheetName)
			boolean tmpStat=false;
			for(ExcellRow r:targetRows)
			{
				if(r.getOccurances().containsAll(s.getOccurances()))
				{
					tmpStat=true;
					targAllVals.put(maprow,r);
				}
			}
			if(!tmpStat)
			{
			targAllVals.put(maprow,new ExcellRow());
			}
		}

		}}
		
		System.out.println(srcAllVals);
		System.out.println(targAllVals);
		
	}

}
