package com.validator.qa.idoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import java.io.*;

public class IDOCParser {

	/**
	 * @param args
	 */
	static IDOCParser p;
	static ArrayList<ExcellRow> printableRows;
	public static void init()
	{
		p=new IDOCParser();
		printableRows=new ArrayList<ExcellRow>();
	}
	public static ArrayList<ExcellRow> getPrintableRows() {
		return printableRows;
	}
	public static IDOCParser getParser()
	{
		if(p==null)
			p=new IDOCParser();
		return p;
	}
	
	public void loopAndPopulateParentVal(IDOCNode n)
	{
		n.setParentVal();
		if(n.getChildren().size()>0)
		for(IDOCNode c:n.getChildren())
			loopAndPopulateParentVal(c);
		
	}
	public void printAsPerExcellRows(IDOCNode n)
	{
		this.getPrintableRows().addAll(n.getMatchingRows());
		//print logic
		//System.out.println(n.getMatchingRows());
		//end print logic
		if(n.getChildren().size()>0)
		for(IDOCNode c:n.getChildren())
			printAsPerExcellRows(c);
		
	}
	public void reduce(IDOCNode n)
	{
		List<ExcellRow> toRemove=new ArrayList<ExcellRow>();
		for(ExcellRow r:n.getMatchingRows())
		{
			//System.out.println("==="+r.getIdocAttributes());
			try{
			if(r.getIdocAttributes()!=null&&r.getIdocAttributes().length()>1)
			{
				String[] attrs=r.getIdocAttributes().split(";");
				for(String attr:attrs)
			{
				String[] kvpairIdoc=attr.split("=");
				String v=n.SearchValByXPUPDOWN(kvpairIdoc[0]);
				try{
				if(v.equals(kvpairIdoc[1]))
				{
					//System.out.println("v="+v);
				}//do nothing if values match
				else
				{
					toRemove.add(r);
					//n.getMatchingRows().remove(r);//remove if values dont match
				}
				}catch(NullPointerException e)
				{
					toRemove.add(r);
					//System.out.println(e.getMessage()+" in "+r);
				}
			}
			}
			}catch(ArrayIndexOutOfBoundsException aiobe){
				JOptionPane.showMessageDialog(null, "Please check the IDoc Attributes for \""+r.getIdocAttributes()+"\" to be in \"XPath=ExpectedValue\" format");
				System.exit(1);
			}
		}
		n.getMatchingRows().removeAll(toRemove);
		if(n.getChildren().size()>0)
		{
			for(IDOCNode c:n.getChildren())
			reduce(c);
		}
	}
	public static void applySpecial(IDOCNode n, Map<Integer,SplConditions> allConditions)
	{
		Set<Integer> ks=allConditions.keySet();
		Iterator<Integer> ksi=ks.iterator();
		
		while(ksi.hasNext())
		{
			Integer curr=ksi.next();
			SplConditions c=allConditions.get(curr);
			if(c.getValidationSpCondition().equals("FirstOccurance"))
			{
				
			}
			else if(c.getValidationSpCondition().equals("Sum"))
			{
				
			}
			else if(c.getValidationSpCondition().equals("IfElse"))
			{
				HashSet<IDOCNode> currNodes= new HashSet<IDOCNode>();
				getNodes(currNodes,n,curr);
				//             a://a/b/c,//a/b/d
				String params=c.getValidationParameter();
				String[] pairs=params.split(",");
					for(IDOCNode cn:currNodes)
					{
						System.out.println("CurrNode="+cn);
						for(ExcellRow r:cn.getMatchingRows())
						{
							if(r.getOccurances().contains(curr))
							{
						//if(r.getValue()==null)r.setValue(cn.getValue());	
						//if(r.getValue()==null)r.setValue("");
							for(int i=0;i<pairs.length;i++)
							{
								String kv[]=pairs[i].split(":");
							
								System.out.println("OldVal="+r.getValue()+" - "+r.getXpath());
							
								if(kv.length==2)
								{
									if(r.getValue().equals(kv[0]))
									{
									String nv=cn.SearchValByXPUPDOWN(kv[1]);
									if(nv==null)
										nv=kv[1];
								System.out.println("New:"+nv+" - "+r.getXpath());
								r.setValue(nv);
									}
								}
								else if(r.getBakupVal().equals(r.getValue()))
								{
									String nv=cn.SearchValByXPUPDOWN(kv[0]);
									if(nv==null)
										nv=kv[0];
									System.out.println("OtherVal:"+nv);
									r.setValue(nv);
								}
								//continue;
						}
						}
					}
					
				}
			}
		}
	}
	public static void getNodes(HashSet<IDOCNode> currNodes,IDOCNode n,Integer i)
	{
		if(n.getMatchingRows().size()>0)
		{
			for(ExcellRow r:n.getMatchingRows())
			{
				if(r.getOccurances().contains(i))
					currNodes.add(n);
			}
		}
		if(n.getChildren()!=null && n.getChildren().size()>0)
		{
			for(IDOCNode cn:n.getChildren())
			{
				getNodes(currNodes, cn, i);
			}
		}
	}
	public static int addRows(List<ExcellRow> srcRows,Map<Integer,SplConditions> allConditions)
	{
		Map<Integer,SplConditions> allConditionsNew=new HashMap<Integer, SplConditions>();
		Set<Integer> keys=allConditions.keySet();
		//Iterator<Integer> i=keys.iterator();
		Iterator<Integer> it=keys.iterator();
		List<Integer> its=new ArrayList<Integer>();
		while(it.hasNext())
		{
			its.add(it.next());
		}
		List<ExcellRow> newRows=new ArrayList<ExcellRow>();
		int negative=-1;
		//while(i.hasNext())
		for(Integer i:its)
		{
			//Integer curr=i.next();
			Integer curr=i;
			SplConditions cond=allConditions.get(curr);
			ExcellRow r=srcRows.get(curr.intValue()-1);
			if(cond.getValidationSpCondition().equals("IfElse"))
			{
				String[] c1=cond.getValidationParameter().split(",");
				int ci=0;
				for(;ci<c1.length-1;ci++)
				{
					//add c1[i]
					ExcellRow newRow=new ExcellRow();
					String[] c2=c1[ci].split(">");
					newRow.setXpath(c2[1]);
					newRow.setParentXpath(r.getXpath());
					if(c2.length==3)
					{
						newRow.setIdocAttributes(c2[2]);
					}
					//allConditions.put(new Integer(negative), new SplConditions(negative, "", ""));
					allConditionsNew.put(new Integer(negative), new SplConditions(negative, "", ""));
					newRow.incrementOccurance(negative--);
					newRow.setPk("TemporaryRow");
					newRows.add(newRow);
				}
				
				///////////////////////////add c1[i]
				ExcellRow newRow=new ExcellRow();
				String[] c2=c1[ci].split(">");
				newRow.setXpath(c2[0]);
				newRow.setParentXpath(r.getXpath());
				if(c2.length==2)
				{
					newRow.setIdocAttributes(c2[1]);
				}
				//allConditions.put(new Integer(negative), new SplConditions(negative, "", ""));
				allConditionsNew.put(new Integer(negative), new SplConditions(negative, "", ""));
				newRow.incrementOccurance(negative--);
				newRow.setPk("TemporaryRow");
				newRows.add(newRow);
				/////////////////////////////
				Iterator<Integer> i2=allConditionsNew.keySet().iterator();
				while(i2.hasNext())
				{
					Integer ii=i2.next();
					allConditions.put(ii, allConditionsNew.get(ii));
					
				}
			}
			srcRows.addAll(newRows);
		}
		
	return negative;	
	}
	public static void start(String filename,List<ExcellRow> rows, Map<Integer,SplConditions> allConditions, boolean source)
	{
		//Step 0: Add rows for IfElse 
		if(source)
		addRows(rows,allConditions);
		//Step 1: Create Tree
		SAXParserFactory spf= SAXParserFactory.newInstance();
		IDOCHandeler h=new IDOCHandeler();
		IDOCNode root=h.getRoot();
		h.setExcellRows(rows);
		try{
		SAXParser sp=spf.newSAXParser();
		//
		File file = new File(filename);
		InputStream inputStream= new FileInputStream(file);
		Reader reader = new InputStreamReader(inputStream,"UTF-8");
		InputSource is = new InputSource(reader);
		is.setEncoding("UTF-8");
		
		//
		sp.parse(is, h);
		}catch(Exception e){JOptionPane.showMessageDialog(null, e);}
		IDOCNode n=h.getRoot();
		//Step 2: Loop through the tree and populate the excellrows
		IDOCParser p=new IDOCParser();
		p.loopAndPopulateParentVal(n);
		//Step 3: reduce the rows as per IDocAttributes
		p.reduce(n);
		//step 4: Apply super special conditions
		//if(allConditions!=null)
		//p.applySpecial(n,allConditions);
		//step 5: return the remaining rows using printAsPerExcellRows()
		p.printAsPerExcellRows(n);
		
		//for(ExcellRow tmpR:p.getPrintableRows())
		//	System.out.println(tmpR);
		//System.out.println(p.getPrintableRows());
		
		/*
		//idoc logic
		//Collection<ExcellRow> allExcellRows=rows.values();
		Iterator<ExcellRow> i=rows.iterator();//allExcellRows.iterator();
		while(i.hasNext())
		{
		ExcellRow r1=i.next();//=new ExcellRow("//A/B/matlnbr;qualf=001,tdid=002","","","","","//A/qualf=001,//A/B/tdid=002",1);//to convert to generic later
		p.setIdocXPaths(n.getChildren().get(0), r1);//24Jan
		}
		//p.setIdocXPaths(n.getChildren().get(0), r2);//24Jan
		//idoc logic
		//System.out.println(n);
		p.setSelector(n, rows);
		p.setSelectorsAsPK(n);
		p.recursiveWalk(n);
		//System.out.println(n);
		//p.recursivePrint(n);
		p.printAsPerExcellRows(n,rows);////
		//p.recursivePrint(n);
		//System.out.println(rows);
		 * 
		 */
	}
	public void setSelectorsAsPK(IDOCNode n)
	{
		if(n.getIdocSelectorXpath()!=null)
		{
		if(n.getParent().getPk()!=null&&n.getParent().getPk().length()>0)
		{
			n.setPk(n.getParent().getPk()+","+n.getIdocSelectorXpath()+"="+n.getIdocSelector());
			//System.out.println("++"+n.getPk());
			
		}
		else
		{
			n.setPk(n.getIdocSelectorXpath()+"="+n.getIdocSelector());
			//System.out.println("--"+n.getPk());
		}
		}
		for(IDOCNode cNode:n.getChildren())
		{
			setSelectorsAsPK(cNode);
		}
		
		return;
	}
	public void setSelector(IDOCNode n, List<ExcellRow> rows)
	{
		//System.out.println(n);
		for(ExcellRow r:rows)
		{
			String idas=r.getIdocAttributes();
			//if()
			//System.out.println(idas);
			String[] ida=idas.split(",");
			for(String kv:ida)
			{
				String[] kva=kv.split("=");
				try{
				if(n.getXpath()!=null&&n.getXpath().equals(kva[0])&&n.getValue().equals(kva[1]))
					{n.getParent().setIdocSelector(kva[1]);
					n.getParent().setIdocSelectorXpath(n.getXpath());
					}
				}catch(Exception e)
				{System.out.println(e);}
				//System.out.println(kva[0]+","+kva[1]);
			}
			//if(n.getXpath()!=null&&)
		}
		
		if(n.getChildren().size()==0)
			return;
		for(IDOCNode cNode:n.getChildren())
			setSelector(cNode, rows);
	}
	public void setIdocXPaths(IDOCNode node,ExcellRow r)
	{
		if(node==null)
			return;
		//System.out.println(node.getXpath()+","+node.getChildren().size());
		
		if(node.getXpath()!=null&&node.getXpath().equals(r.getXpath().split(";")[0]))
		{
			//node.setValue(value)
			System.out.println("settingValue"+r.getXpath());
			node.setIdocSplVal(r.getXpath());
			return;
		}
		if(node.getChildren().size()==0)
			return;
		String []xpathVals=r.getIdocAttributes().split(",");
		for(String xpathVal:xpathVals)
		{
			String[] xv=xpathVal.split("=");
			for(IDOCNode n:node.getChildren())
			{
				if(n.getXpath()!=null&&n.getXpath().equals(xv[0])&&n.getValue().equals(xv[1]))
					{
					for(IDOCNode n1:node.getChildren())
					setIdocXPaths(n1, r);
					break;
					}
			}
		}
	}
	public static void fill(List<ExcellRow> lr,String pathname)
	{
		try{
			File f1=new File(pathname);
			FileReader fr=new FileReader(f1);
			BufferedReader br=new BufferedReader(fr);
			String str=null;
			int i=0;
			while((str=br.readLine())!=null)
			{i++;
			//System.out.println(str);
			String[] xparg=str.split(",");
			ExcellRow r1=null;
			if(xparg.length>2)
				r1=new ExcellRow(xparg[1],xparg[0],"","","",xparg[2],i);
			else
				r1=new ExcellRow(xparg[1],xparg[0],"","","","",i);
				//JOptionPane.showMessageDialog(null, r1.getParentXpath());
				lr.add(r1);
			}
		}catch(Exception e){System.out.println(e);}
	}
/*	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		init();
		IDOCParser p=getParser();
		List<ExcellRow> rows=new ArrayList<ExcellRow>();
		fill(rows,"idoc_xp_attr.txt");
		p.start("C:/Users/319113/Documents/Mexico_RS_IO/3PL_ReturnStatus1.txt",rows,null,false);
		
		//System.out.println(p.getPrintableRows());
	}*/
	
	public void recursiveWalk(IDOCNode n)//A walk through the system setting parent node value
	{
		//System.out.println("xp="+n.getXpath()+",pxp="+n.getParentXpath()+",v="+n.getValue()+",pv="+n.getParentValue()+",pk="+n.getPk()+",uid="+n.getUid());
		n.setParentVal();
		for(IDOCNode curr:n.getChildren())
			recursiveWalk(curr);
	}
	public void recursivePrint(IDOCNode n)//For Printing
	{
		System.out.println("xp="+n.getXpath()+",pxp="+n.getParentXpath()+",v="+n.getValue()+",pv="+n.getParentValue()+",pk="+n.getPk()+",uid="+n.getUid()+"ispv="+n.getIdocSplVal());
		//System.out.print(n);
		//n.setParentVal();
		for(IDOCNode curr:n.getChildren())
			recursivePrint(curr);
	}
	public void recursivePrintUID(IDOCNode n)//For Printing
	{
		System.out.println(n.getUid()+";;"+n.getValue());
		//n.setParentVal();
		for(IDOCNode curr:n.getChildren())
			recursivePrintUID(curr);
	}
	public void printAsPerExcellRows(IDOCNode n,List<ExcellRow> excellRows)//Print as per foxy format
	{
		for(ExcellRow r:excellRows)
		{
			//System.out.println(r.getIdocAttributes()+n.getPk());
		
		//System.out.println(n.getParent().getPk()==null);
		boolean idc=true;//idoc attributes present
		if(r.getIdocAttributes()!=null&&r.getIdocAttributes().length()>0)
		{
			if(r.getIdocAttributes().equals(n.getPk()))
			{}
			else
				idc=false;
		}
		if(idc&&n.getXpath()!=null&&n.getXpath().length()>0&&n.getXpath().equals(r.getXpath()))
		{
			//System.out.println(r.getIdocAttributes()+"--"+n.getParent().getPk()+"--"+n.getXpath());
			n.setRow(r.getOccurances());
			for(int row:n.getRow())
			{//System.out.println(n.getUid());
				ExcellRow newRow=new ExcellRow(n.getXpath(), n.getParentXpath(), n.getValue(),n.getParentValue(), n.getPk(), n.getParent().getPk(),row);
				//newRow.setPk(n.getParent().getPk());
				printableRows.add(newRow);
				newRow.setPk(n.getParent().getPk());
			System.out.println(newRow);
		}
/*			if(r!=null&&r.getIdocAttributes()!=null&&
				n!=null&&n.getParent()!=null &&n.getParent().getPk()!=null
				&&n.getXpath()!=null
				&&n.getXpath().length()>0
				&&n.getXpath().equals(r.getXpath())
				&&n.getParent().getPk().equals(r.getIdocAttributes()))
		{
			//System.out.println(r.getIdocAttributes()+"--"+n.getParent().getPk()+"--"+n.getXpath());
			n.setRow(r.getOccurances());
			for(int row:n.getRow())
			{//System.out.println(n.getUid());
				ExcellRow newRow=new ExcellRow(n.getXpath(), n.getParentXpath(), n.getValue(),n.getParentValue(), n.getPk(), n.getParent().getPk(),row);
				//newRow.setPk(n.getParent().getPk());
				printableRows.add(newRow);
				newRow.setPk(n.getPk());
			System.out.println(newRow);
				
				
			}
		*/
			}

		}
	 	for(IDOCNode curr:n.getChildren())
			printAsPerExcellRows(curr,excellRows);
	}

}
