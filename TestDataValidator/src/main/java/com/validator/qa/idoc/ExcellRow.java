package com.validator.qa.idoc;

import java.util.ArrayList;
import java.util.List;

public class ExcellRow {
String xpath;
String parentXpath;
String value;
String parentValue;
String pk;
String idocAttributes;//24Jan
List<Integer> occurances=new ArrayList<Integer>(); 
String bakupVal;
IDOCNode node;
boolean used=false;

public boolean isUsed() {
	return used;
}
public void setUsed(boolean used) {
	this.used = used;
}

public ExcellRow(ExcellRow r)
{
	this.xpath=r.getXpath();
	this.parentXpath=r.getParentXpath();
	this.value=r.getValue();
	this.parentValue=r.getParentValue();
	this.pk=r.getPk();
	this.idocAttributes=r.getIdocAttributes();
	this.occurances.addAll(r.getOccurances());
	this.bakupVal=r.getBakupVal();
	this.node=r.getNode();
}

public IDOCNode getNode() {
	return node;
}
public void setNode(IDOCNode node) {
	this.node = node;
}
public void incrementOccurance(int i)
{
	this.occurances.add(i);
}
public String getBakupVal() {
	return bakupVal;
}
public void setBakupVal(String bakupVal) {
	this.bakupVal = bakupVal;
}
//for normal xml
public ExcellRow(String xpath,String parentXpath,String value,String parentValue,String pk,int rownum) 
{
	// TODO Auto-generated constructor stub
	this.xpath=xpath;
	this.parentXpath=parentXpath;
	this.parentValue=parentValue;
	this.value=value;
	this.pk=pk;
	this.occurances.add(rownum);
	this.bakupVal=value;
}
//for idoc xml
public ExcellRow(String xpath,String parentXpath,String value,String parentValue,String pk,String idocAttributes,int rownum) 
{
	// TODO Auto-generated constructor stub
	this.xpath=xpath;
	this.parentXpath=parentXpath;
	this.parentValue=parentValue;
	this.value=value;
	this.pk=pk;
	this.idocAttributes=idocAttributes;
	this.occurances.add(rownum);
	this.bakupVal=value;
}

public String getIdocAttributes() {
	return idocAttributes;
}
public void setIdocAttributes(String idocAttributes) {
	this.idocAttributes = idocAttributes;
}
public List<Integer> getOccurances() {
	return occurances;
}
@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ExcellRow:xp="+xpath+",pxp="+parentXpath+",v="+value+",pv="+parentValue+",pk="+pk+",occ="+occurances+"ida="+idocAttributes;
	}

public String getXpath() {
	return xpath;
}
public void setXpath(String xpath) {
	this.xpath = xpath;
}
public String getParentXpath() {
	return parentXpath;
}
public void setParentXpath(String parentXpath) {
	this.parentXpath = parentXpath;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public String getParentValue() {
	return parentValue;
}
public void setParentValue(String parentValue) {
	this.parentValue = parentValue;
}
public String getPk() {
	return pk;
}
public void setPk(String pk) {
	this.pk = pk;
}
public ExcellRow() {
	// TODO Auto-generated constructor stub
}

}
