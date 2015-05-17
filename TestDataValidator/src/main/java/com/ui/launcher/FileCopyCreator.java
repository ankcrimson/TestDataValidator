package com.ui.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import com.validator.log.LoggingClass;

public class FileCopyCreator {

	public String createCopy(String oldName)
	{
		String newName=oldName.substring(0, oldName.lastIndexOf('.'))+"tmp1"+oldName.substring(oldName.lastIndexOf('.'));
		try{
		FileInputStream fis=new FileInputStream(oldName);
		//String newName=oldName.substring(0, oldName.lastIndexOf('.'))+1+oldName.substring(oldName.lastIndexOf('.'));
		FileOutputStream fos=new FileOutputStream(newName);
		int ch=0;
		while((ch=fis.read())!=-1)
		{
			fos.write(ch);
		}
		fis.close();
		fos.close();
		
		}catch(Exception e)
		{
			e.printStackTrace();
			LoggingClass.logger.info(e);
		}
	return newName;
	}
	
	public boolean deleteCopy(String newName)
	{
		boolean deleted=false;
		try{
		File f=new File(newName);
		deleted=f.delete();
		
		}catch(Exception e){
			e.printStackTrace();
			LoggingClass.logger.info(e);
		}
		return deleted;
	}
	
	public static void main(String[] args) {
		FileCopyCreator fcc=new FileCopyCreator();
		
		System.out.println(fcc.createCopy("D:\\MapperU8_2_5\\sample_scenarios\\delevery notes\\Delivery notes SAP-IDM.xls"));
	}
}
