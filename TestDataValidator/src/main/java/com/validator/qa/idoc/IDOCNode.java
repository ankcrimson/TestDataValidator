package com.validator.qa.idoc;

import java.util.*;

public class IDOCNode {
String xpath;
String value;
String parentValue;
String parentXpath;
String pk;
String idocSelector;
String idocSelectorXpath;
static int pnodeCount=0;
List<ExcellRow> matchingRows=new ArrayList<ExcellRow>();
List<Integer> row=new ArrayList<Integer>();
int uid;
List<IDOCNode> children=new ArrayList<IDOCNode>();
IDOCNode parent;
String idocSplVal;



public void addRow(ExcellRow r)
{
	this.getMatchingRows().add(r);
}
public List<ExcellRow> getMatchingRows() {
	return matchingRows;
}
public void setMatchingRows(List<ExcellRow> matchingRows) {
	this.matchingRows = matchingRows;
}
public String getIdocSelectorXpath() {
	return idocSelectorXpath;
}
public void setIdocSelectorXpath(String idocSelectorXpath) {
	this.idocSelectorXpath = idocSelectorXpath;
}
public String getIdocSelector() {
	return idocSelector;
}
public void setIdocSelector(String idocSelector) {
	this.idocSelector = idocSelector;
}
public String getIdocSplVal() {
	return idocSplVal;
}
public void setIdocSplVal(String idocSplVal) {
	this.idocSplVal = idocSplVal;
}
@Override
	public String toString() {
		// TODO Auto-generated method stub
		//return "xp-"+xpath+","+value+","+children+","+idocSelector;
	String retVal= "<"+xpath+" value=\""+value+"\" idocA=\""+idocSelector+"\" >";
	for(IDOCNode n:children)
		retVal+=n;
	retVal+="</"+xpath+">";
	return retVal;
	}
public List<Integer> getRow() {
	return row;
}
public void setRow(List<Integer> row) {
	this.row = row;
}
/*public void setRow(int row) {
	this.row = row;
}
public int getRow() {
	return row;
}
*/
public String getPk() {
	return pk;
}
public void setPk(String pk) {
	this.pk = pk;
}
public List<IDOCNode> getChildren() {
	return children;
}
public void setChildren(List<IDOCNode> children) {
	this.children = children;
}
public IDOCNode getParent() {
	return parent;
}
public void setParent(IDOCNode parent) {
	this.parent = parent;
}
public String getParentValue() {
	return parentValue;
}
public void setParentValue(String parentValue) {
	this.parentValue = parentValue;
}
public String getParentXpath() {
	return parentXpath;
}
public void setParentXpath(String parentXpath) {
	this.parentXpath = parentXpath;
}
public String getXpath() {
	return xpath;
}
public void setXpath(String xpath) {
	this.xpath = xpath;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public int getUid() {
	return uid;
}
public void setUid(int uid) {
	this.uid = uid;
}
public void setParentVal()
{
	
	for(ExcellRow r:getMatchingRows())
	{
		
	
	String parentXP=r.getParentXpath();
	String finalVal="";
	for(String pXpath:parentXP.split(";"))
	{
	//String pXpath=r.getParentXpath();
	String parentVal=null;
	//System.out.println("PXP="+getParentXpath());
	parentVal=searchChildrenByXPath(pXpath);
	IDOCNode tmp=this;
	for(int i=1;i<=99;i++)
	{
	if((parentVal==null||parentVal.length()==0)&&tmp.getParent()!=null&&pXpath!=null)
	{
		parentVal=tmp.getParent().searchChildrenByXPath(pXpath);
		if(parentVal!=null&&parentVal.length()>0)
			break;
		else tmp=tmp.getParent();
		//System.out.println(tmp);
	}
	else break;
	}
	finalVal+=(finalVal.length()>0)?";"+parentVal:parentVal;
	}
	
	
	
	
	
	//System.out.println("xpath="+xpath+"parentValSetted="+finalVal);
	if(finalVal!=null)
	{
	r.setParentValue(finalVal);
	}
	}
}
public String SearchValByXPUPDOWN(String xpath)
{
	
//	if()
	
	String parentVal=null;
	//System.out.println("PXP="+getParentXpath());
	parentVal=searchChildrenByXPath(xpath);
	//System.out.println("++++++++++++++++++++"+parentVal);
	IDOCNode tmp=this;
	for(int i=1;i<=99;i++)
		if((parentVal==null||parentVal.length()==0)&&tmp.getParent()!=null)//9MAR
		//if((parentVal==null||parentVal.length()>0)&&tmp.getParent()!=null)
	{
		//System.out.println("looking in\n"+getParent().getXpath()+" from "+getXpath()+" for "+getParentXpath());
		parentVal=tmp.getParent().searchChildrenByXPath(xpath);
		if(parentVal!=null&&parentVal.length()>0)
			break;
		else tmp=tmp.getParent();
		//System.out.println(tmp.getXpath()+"--"+parentVal);
		
	}
	else break;
	

	return parentVal;
	
}

public String oneStepUp(String xp)
{
	String s=xp.substring(0,xp.lastIndexOf("/"));
	//System.out.println("--"+s+"--");
	return s;
}

public String  searchChildrenByXPath(String xpath)
{
	if(xpath==null||this.getXpath()==null||this.getXpath().equals("//"))
		return null;
	if(xpath.equals(this.getXpath()))
	{
		//System.out.println("found "+this.getXpath()+"*******"+this.getValue());
		return getValue();
	}
	String val=null;
	List<IDOCNode> children=getChildren();
	if(children.size()==0)
		return null;
	for(IDOCNode curr:children)
	{
		val=curr.searchChildrenByXPath(xpath);
		if(val!=null)return val;
	}
	return val;
}
}
