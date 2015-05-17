package com.validator.qa.utils;
import java.util.*;



public class ArrayListTools {

	static List<String> valueHolder;//=new ArrayList<String>();
	public static void init()
	{
		valueHolder=new ArrayList<String>();
	}
	
	
	
	static public List<String> getArrayList()
	{
		if(valueHolder==null)
			init();
		return valueHolder;
	}
	static public void add(String value)
	{
		valueHolder.add(value);
	}
	static public String get(int index)
	{
		
		return valueHolder.get(index);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
