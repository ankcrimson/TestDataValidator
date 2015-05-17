package com.validator.qa.utils;

import java.util.ArrayList;
import java.util.List;
class STProperties{
	String tagName="";
	String parentTagName="";
	String xPathValue="";
	String ActualTagValue="";
	String ParentTagValue="";
	String pk="";
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getParentTagName() {
		return parentTagName;
	}
	public void setParentTagName(String parentTagName) {
		this.parentTagName = parentTagName;
	}
	public String getxPathValue() {
		return xPathValue;
	}
	public void setxPathValue(String xPathValue) {
		this.xPathValue = xPathValue;
	}
	public String getActualTagValue() {
		return ActualTagValue;
	}
	public void setActualTagValue(String actualTagValue) {
		ActualTagValue = actualTagValue;
	}
	public String getParentTagValue() {
		return ParentTagValue;
	}
	public void setParentTagValue(String parentTagValue) {
		ParentTagValue = parentTagValue;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	
}

public class ValueHolderTools {
	
	static List<STProperties> allRecs;
	
	public static List<STProperties> initHolderList()
	{
		ArrayList<STProperties> allRecs2=new ArrayList<STProperties>();
		allRecs=allRecs2;
		return allRecs2;
	}
	
	public static List<STProperties> getAllRecs() {
		return allRecs;
	}
	
	public static STProperties getSTProperties(ArrayList<STProperties>stprop,int i)
	{
		return stprop.get(i);
	}
	public static void setSTProperties(ArrayList<STProperties>stprop,STProperties prop,int i)
	{
		//stprop.set(i, stprop);
		stprop.set(i, prop);
	}
}
