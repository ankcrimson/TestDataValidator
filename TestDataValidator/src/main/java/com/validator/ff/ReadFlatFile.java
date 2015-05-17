package com.validator.ff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import com.validator.qa.idoc.ExcellRow;
import com.validator.qa.idoc.IDOCNode;

public class ReadFlatFile {

	List<ExcellRow> matchingRows=new ArrayList<ExcellRow>();
	List<ExcellRow> srcRows=new ArrayList<ExcellRow>();
	IDOCNode structure=null;
	
	public void setSrcRows(List<ExcellRow> srcRows) {
		this.srcRows = srcRows;
	}
	public List<ExcellRow> getSrcRows() {
		return srcRows;
	}
	

	public int getUpdown(String newRowXP, String currRowXP, IDOCNode root)
	{
		
		if(newRowXP.equals(currRowXP))
			return 0;
		//System.out.println(currRowXP+"=="+newRowXP);
		int xlevel=0;
		int ylevel=0;
		
		xlevel=findLevel(root, currRowXP, xlevel);
		ylevel=findLevel(root, newRowXP, ylevel);
		System.out.println(currRowXP+"=="+newRowXP+"-----"+"xlevel="+xlevel+"ylevel="+ylevel);
		if(xlevel==ylevel)return 0;
		if(xlevel>ylevel||currRowXP.length()<=1) return 1;
		else return -1;
				
	}
	public int findLevel(IDOCNode root,String xpath,int level)
	{
		//System.out.println(xpath+"--"+root.getXpath());
		if(xpath.equals(root.getXpath()))
		{
			//level=0;
			return level+1;
		}
		
		for(IDOCNode n:root.getChildren())
		{
			int x=findLevel(n, xpath, level+1);
			//System.out.println(x);
			if(x>-1)
				return x;
		}
		return -1;
	}
	
	public void readFile(boolean hRow,List<ExcellRow> srcRows, String filename,IDOCNode root,boolean ...trim)
	{
		int i=0;
		structure=makeStructure(srcRows);
		System.out.println(structure+"str");
		IDOCNode curr=root;
		try{
			File ipf=new File(filename);
			FileReader ipfr=new FileReader(ipf);
			BufferedReader ipbr=new BufferedReader(ipfr);
			String currLine="";
			if(hRow)
				ipbr.readLine();//remove header row
			while((currLine=ipbr.readLine())!=null)
			{
				List<ExcellRow> currentRowMatches=new ArrayList<ExcellRow>();
				
				for(ExcellRow sr: srcRows)
				{	try{
					
									String[] props=sr.getXpath().split(":");
									System.out.println(sr.getXpath());
									int qualStart=Integer.parseInt(props[0]);
									int qualLen=Integer.parseInt(props[1]);
									if(props.length==5){
										//=================================================
										String qual=currLine.substring(qualStart, qualStart+qualLen);
										if(props[2].equals(qual))
										{	int updown=getUpdown(sr.getXpath(),curr.getXpath(),structure);
							//System.out.println(sr.getXpath()+curr.getXpath()+"Updown=>"+updown+"<");
										
										ExcellRow newRow=new ExcellRow(sr);
										sr.setUsed(true);
										int valStart=Integer.parseInt(props[3]);
										int valLen=Integer.parseInt(props[4]);
										String val=currLine.substring(valStart,valStart+valLen);
										if(trim[0])
										{
											val=val.trim();
										}
										newRow.setValue(val);
										newRow.setBakupVal(val);
							//matchingRows.add(newRow);
							//currentRowMatches.add(newRow);
										IDOCNode newNode=new IDOCNode();
										newNode.addRow(newRow);
										newNode.setParent(curr);
										newNode.setXpath(sr.getXpath());
							
										newNode.getMatchingRows().addAll(currentRowMatches);
										newNode.setUid(++i);
										newNode.setValue(val);//tmp action
							
										if(updown==1)
										{
											newNode.setParent(curr);//9Mar
											curr.getChildren().add(newNode);
											//curr=newNode;
										}
										if(updown==-1)
										{
											newNode.setParent(curr.getParent().getParent());//9Mar
											
											curr.getParent().getParent().getChildren().add(newNode);
											//curr=newNode.getParent();
										}
										if(updown==0)
										{
											newNode.setParent(curr.getParent());
											curr.getParent().getChildren().add(newNode);							
										}
										curr=newNode;
										
							//System.out.println("root="+root);
										
										//For Qualifier 9 Mar
										if(sr.getIdocAttributes()!=null &&sr.getIdocAttributes().length()>1)
										try{
											IDOCNode attrNode=new IDOCNode();
											String[] aquals=sr.getIdocAttributes().split(":");
											int aqualStart=Integer.parseInt(aquals[0]);
											String[] aquals2=aquals[1].split("=");
											int aqualLen=Integer.parseInt(aquals2[0]);
											String aval=currLine.substring(aqualStart, aqualStart+aqualLen);
											attrNode.setValue(aval);
											attrNode.setXpath(""+aqualStart+":"+aqualLen);
											attrNode.setUid(++i);
											newNode.getChildren().add(attrNode);
											//System.out.println(">>>>>>>>>>>"+newNode.getParent().getChildren());
										}catch(Exception e){JOptionPane.showMessageDialog(null, "Please provide flat file IDoc Qualifier properly for "+sr.getIdocAttributes()+"eg. 146:35=01");}
										
										}
	//=================================================

									}
									else if(props.length==2){
										//=================================================
										String qual=currLine.substring(qualStart, qualStart+qualLen);
										if(true)
										{	int updown=getUpdown(sr.getXpath(),curr.getXpath(),structure);
							//System.out.println("Updown=>"+updown+"<");
										//System.out.println("5555555555555555555-"+root);
										ExcellRow newRow=new ExcellRow(sr);
										sr.setUsed(true);
										//int valStart=Integer.parseInt(props[3]);
										//int valLen=Integer.parseInt(props[4]);
										String val=qual;//currLine.substring(valStart,valStart+valLen);
										if(trim[0])
										{
											val=val.trim();
										}
										newRow.setValue(val);
										newRow.setBakupVal(val);
							//matchingRows.add(newRow);
							//currentRowMatches.add(newRow);
										IDOCNode newNode=new IDOCNode();
										newNode.addRow(newRow);
										newNode.setParent(curr);
										newNode.setXpath(sr.getXpath());
							
										newNode.getMatchingRows().addAll(currentRowMatches);
										newNode.setUid(++i);
										newNode.setValue(val);//tmp action
										
										if(updown==1)
										{
											newNode.setParent(curr);//9Mar
											curr.getChildren().add(newNode);
											//curr=newNode;
										}
										if(updown==-1)
										{
											newNode.setParent(curr.getParent().getParent());//9Mar
											curr.getParent().getParent().getChildren().add(newNode);
											//curr=newNode.getParent();
										}
										if(updown==0)
										{
											newNode.setParent(curr.getParent());
											curr.getParent().getChildren().add(newNode);							
										}
										curr=newNode;
										
							//System.out.println("root="+root);
										
										//For Qualifier 9 Mar
										if(sr.getIdocAttributes()!=null &&sr.getIdocAttributes().length()>1)
										try{
											IDOCNode attrNode=new IDOCNode();
											String[] aquals=sr.getIdocAttributes().split(":");
											int aqualStart=Integer.parseInt(aquals[0]);
											String[] aquals2=aquals[1].split("=");
											int aqualLen=Integer.parseInt(aquals2[0]);
											String aval=currLine.substring(aqualStart, aqualStart+aqualLen);
											attrNode.setValue(aval);
											attrNode.setXpath(""+aqualStart+":"+aqualLen);
											attrNode.setUid(++i);
											newNode.getChildren().add(attrNode);
											//System.out.println(">>>>>>>>>>>"+newNode.getParent().getChildren());
										}catch(Exception e){JOptionPane.showMessageDialog(null, "Please provide flat file IDoc Qualifier properly for "+sr.getIdocAttributes()+"eg. 146:35=01");}
										
										}
	//=================================================

									}
/*//=================================================
									String qual=currLine.substring(qualStart, qualStart+qualLen);
									if(props[2].equals(qual))
									{	int updown=getUpdown(sr.getXpath(),curr.getXpath(),structure);
						//System.out.println("Updown=>"+updown+"<");
									ExcellRow newRow=new ExcellRow(sr);
									int valStart=Integer.parseInt(props[3]);
									int valLen=Integer.parseInt(props[4]);
									String val=currLine.substring(valStart,valStart+valLen);
									if(trim[0])
									{
										val=val.trim();
									}
									newRow.setValue(val);
									newRow.setBakupVal(val);
						//matchingRows.add(newRow);
						//currentRowMatches.add(newRow);
									IDOCNode newNode=new IDOCNode();
									newNode.addRow(newRow);
									newNode.setParent(curr);
									newNode.setXpath(sr.getXpath());
						
									newNode.getMatchingRows().addAll(currentRowMatches);
									newNode.setUid(++i);
									newNode.setValue(val);//tmp action
						
									if(updown==1)
									{
										newNode.setParent(curr);//9Mar
										curr.getChildren().add(newNode);
										//curr=newNode;
									}
									if(updown==-1)
									{
										newNode.setParent(curr.getParent().getParent());//9Mar
										curr.getParent().getParent().getChildren().add(newNode);
										//curr=newNode.getParent();
									}
									if(updown==0)
									{
										newNode.setParent(curr.getParent());
										curr.getParent().getChildren().add(newNode);							
									}
									curr=newNode;
									
						//System.out.println("root="+root);
									
									//For Qualifier 9 Mar
									if(sr.getIdocAttributes()!=null &&sr.getIdocAttributes().length()>1)
									try{
										IDOCNode attrNode=new IDOCNode();
										String[] aquals=sr.getIdocAttributes().split(":");
										int aqualStart=Integer.parseInt(aquals[0]);
										String[] aquals2=aquals[1].split("=");
										int aqualLen=Integer.parseInt(aquals2[0]);
										String aval=currLine.substring(aqualStart, aqualStart+aqualLen);
										attrNode.setValue(aval);
										attrNode.setXpath(""+aqualStart+":"+aqualLen);
										attrNode.setUid(++i);
										newNode.getChildren().add(attrNode);
										//System.out.println(">>>>>>>>>>>"+newNode.getParent().getChildren());
									}catch(Exception e){JOptionPane.showMessageDialog(null, "Please provide flat file IDoc Qualifier properly for "+sr.getIdocAttributes()+"eg. 146:35=01");}
									
									}
//=================================================
*/									
									
				}catch(ArrayIndexOutOfBoundsException ae){JOptionPane.showMessageDialog(null, "Please provide flat file XPath properly for "+sr.getXpath()+"eg. 0:7:E2EDK01:146:35   or   146:35");}
				}
			}
			ipbr.close();
			ipfr.close();
		}catch(Exception e){e.printStackTrace();JOptionPane.showMessageDialog(null, "Error in reader, please provide the xpath properly");}
	}
	public IDOCNode makeStructure(List<ExcellRow> srcRows)
	{
		
		int i=0;
		IDOCNode root=new IDOCNode();
		root.setParent(null);
		//structure.add(root);
		for(ExcellRow r:srcRows)
		{
			if(r.getParentXpath()==null||r.getParentXpath().length()<=1||r.getParentXpath().equals(r.getXpath()))
			{
				IDOCNode n=new IDOCNode();
				n.setUid(++i);
				n.setValue(r.getXpath());
				n.setXpath(r.getXpath());
				n.setParent(root);
				root.getChildren().add(n);
			}
		}
		System.out.println(root+"----1");
		//System.out.println(root);
		for(ExcellRow r:srcRows)
		{
			if(r.getParentXpath()==null||r.getParentXpath().length()<=1)
			{}
			else
			{	
				//IDOCNode n=getNode(root, r.getParentXpath());
				String[] pxps=r.getParentXpath().split(";");
				IDOCNode in=root;
				for(String s:pxps)
				{
					IDOCNode n=getNode(in, s);
					if(n!=null)
						{
						in=n;
						//System.out.println("////////////////////////"+s);
						}
				}
				IDOCNode n=in;//getNode(root, r.getParentXpath());
				//
				IDOCNode newNode=new IDOCNode();
				newNode.setUid(++i);
				newNode.setValue(r.getXpath());
				//newNode.setXpath(r.getXpath());
				System.out.println(n+"   -   "+r.getParentXpath()+"  -  "+r.getXpath());
				newNode.setParent(n);
				if(n!=null)
				{
					n.getChildren().add(newNode);
					System.out.println("added in structure"+newNode.getXpath());
				}
				else
				{
					System.out.println("Not found"+r.getParentXpath());
				}
			}
			
		}
		root.setXpath("");
		//System.out.println(root);
		System.out.println(root+"----2");
		return root;
	}
	public IDOCNode getNode(IDOCNode n,String xpath)
	{
		if(n==null)
		{return null;}
		if(n.getXpath()!=null&&n.getXpath().equals(xpath))
		{
			return n;
		}
		for(IDOCNode cn:n.getChildren())
		{
			IDOCNode node=getNode(cn, xpath);
			if(node!=null)
			{
				return node;
			}
		}
		return null;
	}
	public void printRows(IDOCNode n)
	{
		System.out.println(n.getMatchingRows());
		for(IDOCNode cn:n.getChildren())
		{
			printRows(cn);
		}
	}
}
