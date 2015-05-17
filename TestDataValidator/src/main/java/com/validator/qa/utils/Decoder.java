package com.validator.qa.utils;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JOptionPane;

import com.validator.log.LoggingClass;
import com.validator.qa.idoc.ExcellRow;

public class Decoder {
/*public static String decode(String sourceValues,String parameter)
{
String retval="";
String[] parameters=parameter.split(",");
for(String sourceValue:sourceValues.split("\n"))
{
	boolean found=false;
	for(int i=0;i<(parameters.length-1);i++)
	{
		String[] keyvalue=parameters[i].split(":");
		if(sourceValue.equals(keyvalue[0]))
			{
			found=true;
			retval=(retval.equals(""))?keyvalue[1]:(retval+"\n"+keyvalue[1]);
			}
	}
	if(!found)
		retval=(retval.equals(""))?(parameters[parameters.length-1]):retval+"\n"+(parameters[parameters.length-1]);
}
		return retval;
}
*/
public static String decode(String sourceValues,String parameter)
{
String retval="";
List<String> allVals=new ArrayList<String>();

char[] cha=parameter.toCharArray();
String tmp="";
for(char c:cha)
{
	if(c==',')
	{
		allVals.add(tmp);
		tmp="";
	}
	else tmp+=c;
}
allVals.add(tmp);
boolean found=false;
//System.out.println(allVals);
for(int i=0;i<(allVals.size()-1);i++)
{
	String[] keyvalue=allVals.get(i).split(":");
	if(sourceValues.equals(keyvalue[0]))
		{
		found=true;
		retval=(retval.equals(""))?keyvalue[1]:(retval+"\n"+keyvalue[1]);
		}

}
if(!found)
	retval=allVals.get(allVals.size()-1);
		return retval;
}


public static String date_converter(String sourceValues,String parameter)
{
	String retVal="";
	
	//sourceValue="12/11-2012";
	//parameter="dd/mm-yyyy,yyyy:mm-dd";
	String[] srctargp=parameter.split(",");//source and target parameters
	String[] srcp=srctargp[0].split("");//source parameter splitted
	String[] targp=srctargp[1].split("");//target parameter splitted
	
	for(String sourceValue:sourceValues.split("\n"))
	{
	String[] src=sourceValue.split("");//source value splitted
	String convDate="";// to hold converted date
	HashSet<String> valgen=new HashSet<String>();//used to get different names for different characters based on position of occurance
	Map<String,String> src_srcp=new HashMap<String,String>(); //will have mapping of source and Source parameter	
	for(int i=0;i<srcp.length;i++) //getting different names for different characters for source
	{
		int x=0;
		while(!valgen.add(srcp[i]+""+x))
		{x++;}
		srcp[i]=srcp[i]+""+(x);
	}
	valgen.clear();//clear the Set
	for(int i=0;i<targp.length;i++) //getting different names for different characters for target
	{
		int x=0;
		while(!valgen.add(targp[i]+""+x))
		{x++;}
		targp[i]=targp[i]+""+(x);
	}
	for(int i=0;i<src.length;i++)
	{
		src_srcp.put(srcp[i], src[i]);
	}
	for(String s:targp)
	{
		if(src_srcp.containsKey(s))
			{
			convDate+=(src_srcp.get(s));
			}
		else {
			//System.out.println(s);
			String spl_chr=s.split("[0-9]+")[0]; //remove the extra numbers
			try{Integer.parseInt(spl_chr);}catch(Exception e){convDate+=(spl_chr);} //display if it is not a number
			}
	}
	//System.out.println(retVal);
	retVal=(retVal.equals(""))?convDate:retVal+"\n"+convDate;
	}
	return retVal;
}
public static String extractor(String sourceValues,String parameter)
{
	//String sourceValue="112233445566-999";
	//String parameter="-,2";
	String retVal="";
	String[] params=parameter.split(",");
	for(String sourceValue:sourceValues.split("\n"))
	{
	String[] vals=sourceValue.split(params[0]);
	retVal=(retVal.equals(""))? (vals[Integer.parseInt(params[1])-1]):retVal+"\n"+(vals[Integer.parseInt(params[1])-1]);//return the index-1'th string
	}
	return retVal;
}
public static String trim(String sourceValues,String parameter)
{
	if(parameter.equals(""))
		return sourceValues;
	String[] params=parameter.split(",");
	parameter=params[0];
	String direction="b";
	if(params.length==2)
	{
		direction=params[1];
	}
	String retVal="";
	for(String sourceValue:sourceValues.split("\n"))
	{
	while(sourceValue.indexOf(parameter)==0&&(direction.equalsIgnoreCase("b")||direction.equalsIgnoreCase("l")))
	{
		sourceValue=sourceValue.substring(parameter.length());
	}
	while(sourceValue.lastIndexOf(parameter)==(sourceValue.length()-parameter.length())&&(direction.equalsIgnoreCase("b")||direction.equalsIgnoreCase("r")))
	{
		if(sourceValue.length()-parameter.length()==-1)
			break;
		sourceValue=sourceValue.substring(0,(sourceValue.length()-parameter.length()));
		
	}
	retVal=(retVal.equals(""))?sourceValue:retVal+"\n"+sourceValue;
	}
	return(retVal);

}

public static String hardcode(String sourceValues,String parameter)
{
	return parameter;

}


public static String substring(String sourceValues,String parameter)
{
	String[] params=parameter.split(":");
	String retval="";
	int start=0;
	int end=0;
	boolean right=false;
	if(params.length==1)
	{
		start=Integer.parseInt(params[0].trim());
		end=sourceValues.length();
	}
	else
	{
		start=Integer.parseInt(params[0].trim());
		end=Integer.parseInt(params[1].trim());
		end++;
		
		
	}
	if(params.length==3)
	{
		if(params[2].trim().equals("R"))
		{
			right=true;
		}
	}
	if(start>sourceValues.length())
		start=sourceValues.length();
	if(end>sourceValues.length())
		end=sourceValues.length();
	if(end<start)
		end=start;
	//start--;
	System.out.println(start+", "+end);
	if(right)
	{
		StringBuffer sb=new StringBuffer(sourceValues);
		sb.reverse();
		sourceValues=sb.toString();
		retval=sourceValues.substring(start, end);
		sb=new StringBuffer(retval);
		sb.reverse();
		retval=sb.toString();
	}
	else
	retval=sourceValues.substring(start, end);
	return retval;
}

public static String crop(String sourceValues,String parameter)
{
	String[] params=parameter.split(":");
	String retval="";
	if(params.length<2)
		return retval;
	
	if(params[0].trim().equals("L"))
	{
		retval=substring(sourceValues, params[1]);
	}
	else if(params[0].trim().equals("R"))
	{
		StringBuffer sb=new StringBuffer(sourceValues);
		sb.reverse();
		StringBuffer sb2=new StringBuffer(substring(sb.toString(), params[1]));
		sb2.reverse();
		retval=sb2.toString();
	}
	else if(params[0].trim().equals("B"))
	{
		StringBuffer sb=new StringBuffer(substring(sourceValues, params[1]));
		sb.reverse();
		StringBuffer sb2=new StringBuffer(substring(sb.toString(), params[1]));
		sb2.reverse();
		retval=sb2.toString();
	}
	return retval;
}


public static synchronized ArrayList<Integer> getProcessVector(String sourceValues,String parameter)
{
	ArrayList<Integer> values=new ArrayList<Integer>();
	if(parameter.equals(""))
		return values;
	int i=0;
	for(String sourceValue:sourceValues.split("\n"))//get the parameters
	{
	if(sourceValue.equals(parameter))
		values.add(i);//this record is to be processed
	i++;
	}
	return values;

}
public static String reduce(String sourceValues,ArrayList<Integer> validValues)
{
	
	String retVal="";
	String[] splitted=sourceValues.split("\n");
	
	for(int i:validValues)
	{
		try{
			retVal=(retVal.equals(""))?splitted[i]:retVal+"\n"+splitted[i];
		}catch(Exception e){}
		
	}
	return retVal;
}
public static String padding(String sourceValues,String parameter)
{
	//String sourceValues="abc";
	//String parameter="L,10,0";
	String retVal=sourceValues;
	String[] params=parameter.split(",");
	String finalLen=params[1];
	String direction=params[0];
	int l=Integer.parseInt(finalLen);
	int i=retVal.length();
	while(i<l)
	{
		if(direction.equals("L"))
		{
			retVal=params[2]+retVal;
		}
		else if(direction.equals("R"))
		{
			retVal=retVal+params[2];
		}
		i=retVal.length();
	}
	return retVal;
}
public static String ifelse(String value, String parameter, List<ExcellRow> srcPrintableRows,int currentRow)
{
	String[] params=parameter.split(",");
	int i=0;
	String currxp=srcPrintableRows.get(currentRow).getXpath();
	String currMatchVal=srcPrintableRows.get(currentRow).getBakupVal();
	String pxp=srcPrintableRows.get(currentRow).getParentXpath();
	String pMatchVal=srcPrintableRows.get(currentRow).getParentValue();
	
	
	for(;i<(params.length-1);i++)
	{
		//loop through each xpath and palce value
		for(ExcellRow r:srcPrintableRows)
		{
			String[] val_xp=params[i].split(">");
			if(value.equals(val_xp[0]))
			{
				//return val of this xpath: val_xp[1]
				
				String[] valArr=val_xp[1].split("\"");
				//for(int ii=0;ii<valArr.length;ii++)
					//System.out.println(ii+"====="+valArr[ii]);
				if(valArr.length>1)
				{
					//System.out.println(">>>>>>>>>"+val_xp[1]);
					return valArr[valArr.length-1];
				}
				else
				for(ExcellRow er:srcPrintableRows)
				{
					if(er.getXpath().equals(val_xp[1])&&er.getParentXpath()!=null&&er.getParentXpath().equals(pxp)&&er.getParentValue().equals(pMatchVal))
					{
						return er.getValue();
					}
				}
			}
		}
	}
	//return the value of last xpath: params[i]
	if(currxp.equals(params[i]))
	{return currMatchVal;}
	else
	{
		for(ExcellRow er:srcPrintableRows)
		{
			if(er.getXpath().equals(params[i])&&er.getParentXpath()!=null&&er.getParentXpath().equals(currxp)&&er.getParentValue().equals(currMatchVal))
			{
				return er.getValue();
			}
		}

	}
	return "No Value Found";
}
public static String concat(String value, String parameter, List<ExcellRow> srcPrintableRows,int currentRow)
{
	String[] params=parameter.split(",");
	int i=0;
	String currxp=srcPrintableRows.get(currentRow).getXpath();
	String pxp=srcPrintableRows.get(currentRow).getParentXpath();
	String currMatchVal=srcPrintableRows.get(currentRow).getBakupVal();
	String pMatchVal=srcPrintableRows.get(currentRow).getParentValue();
	String rv="";
	
	outer: for(;i<(params.length);i++)
	{
		if(params[i].equals("this"))
			{
			rv+=value;continue;
			}
		
		String tmp=params[i];
		System.out.println(">>>>>>"+tmp.charAt(0)+tmp.charAt(tmp.length()-1));
		if(tmp.charAt(0)==tmp.charAt(tmp.length()-1)&&tmp.charAt(0)=='\"')
		{
			rv+= tmp.substring(1,tmp.length()-1);
			System.out.println("================"+tmp.substring(1,tmp.length()-1));
			continue;
		}
		//char[] chars=params[i].toCharArray();
		//if(chars[0]=='\"'&&chars[chars.length-1]=='\"')
		//	System.out.println();
		//String[] valArr=params[i].split("\"");
		//System.out.println("-------------------"+params[i]);
		//System.out.println("----------------"+valArr.length);
		/*if(valArr.length>1)
		{
			rv+= valArr[valArr.length-1];
			System.out.println(">>>>>>>>>>>>"+rv);
			continue;
		}
		*/
		//loop through each xpath and palce value
		for(ExcellRow r:srcPrintableRows)
		{
			//String[] val_xp=params[i].split(">");
			String xp=params[i];
			//System.out.println(xp);
			//if(true)
			//{
				//return val of this xpath: val_xp[1]
				
				//for(ExcellRow er:srcPrintableRows)
				
					//System.out.println("matching "+r.getXpath()+" with "+xp +r.getXpath().equals(xp) + (r.getParentXpath()!=null)+r.getParentXpath().equals(pxp)+r.getParentValue().equals(pMatchVal)+" -- "+r.getParentValue()+" &&&&&&& "+pMatchVal);
					if(r.getXpath().equals(xp)&&r.getParentXpath()!=null&&r.getParentXpath().equals(pxp)&&r.getParentValue().equals(pMatchVal))
					{
						rv+= r.getValue();
						//continue outer;
					}
					else if(r.getXpath().equals(xp)&&r.getParentXpath()==null)
					{
						rv+= r.getValue();
						//continue outer;
					}
				
			//}
		}
	}
	//return the value of last xpath: params[i]
	/*if(currxp.equals(params[i]))
	{return currMatchVal;}
	else
	{
		for(ExcellRow er:srcPrintableRows)
		{
			if(er.getXpath().equals(params[i])&&er.getParentXpath()!=null&&er.getParentXpath().equals(currxp)&&er.getParentValue().equals(currMatchVal))
			{
				return er.getValue();
			}
		}

	}*/
	return rv;
}

public static String occurance(String value, String parameter, List<ExcellRow> srcPrintableRows,int currentRow)
{
	String[] params=parameter.split(",");
	int i=0;
	String currxp=srcPrintableRows.get(currentRow).getXpath();
	String pxp=srcPrintableRows.get(currentRow).getParentXpath();
	String currMatchVal=srcPrintableRows.get(currentRow).getBakupVal();
	String pMatchVal=srcPrintableRows.get(currentRow).getParentValue();
	String rv="";
	
	for(;i<(params.length);i++)
	{
		
		//String tmp=params[i];
		//System.out.println(">>>>>>"+tmp.charAt(0)+tmp.charAt(tmp.length()-1));
		/*if(tmp.charAt(0)==tmp.charAt(tmp.length()-1)&&tmp.charAt(0)=='\"')
		{
			rv+= tmp.substring(1,tmp.length()-1);
			System.out.println("================"+tmp.substring(1,tmp.length()-1));
			continue;
		}
		*///char[] chars=params[i].toCharArray();
		//if(chars[0]=='\"'&&chars[chars.length-1]=='\"')
		//	System.out.println();
		//String[] valArr=params[i].split("\"");
		//System.out.println("-------------------"+params[i]);
		//System.out.println("----------------"+valArr.length);
		/*if(valArr.length>1)
		{
			rv+= valArr[valArr.length-1];
			System.out.println(">>>>>>>>>>>>"+rv);
			continue;
		}
		*/
		//loop through each xpath and palce value
		int occur=1;
		//String xp=params[i];
		String[] prms=params[i].split(":");
		String xp=prms[0].trim();
		String occ=prms[1].trim();
		for(ExcellRow r:srcPrintableRows)
		{
			//String[] val_xp=params[i].split(">");
			
			//System.out.println(xp);
			//if(true)
			//{
				//return val of this xpath: val_xp[1]
				
				//for(ExcellRow er:srcPrintableRows)
				
					//System.out.println("matching "+r.getXpath()+" with "+xp +r.getXpath().equals(xp) + (r.getParentXpath()!=null)+r.getParentXpath().equals(pxp)+r.getParentValue().equals(pMatchVal)+" -- "+r.getParentValue()+" &&&&&&& "+pMatchVal);
					if(r.getXpath().equals(xp)&&r.getParentXpath()!=null&&r.getParentXpath().equals(pxp)&&r.getParentValue().equals(pMatchVal))
					{
						if(occ.equals(occur+""))
						rv= r.getBakupVal();
						occur++;
					}
					else if(r.getXpath().equals(xp)&&r.getParentXpath()==null)
					{
						if(occ.equals(occur+""))
						rv+= r.getBakupVal();
						occur++;
					}
				
			//}
		}
	}
	//return the value of last xpath: params[i]
	/*if(currxp.equals(params[i]))
	{return currMatchVal;}
	else
	{
		for(ExcellRow er:srcPrintableRows)
		{
			if(er.getXpath().equals(params[i])&&er.getParentXpath()!=null&&er.getParentXpath().equals(currxp)&&er.getParentValue().equals(currMatchVal))
			{
				return er.getValue();
			}
		}

	}*/
	return rv;
}

public static String multiply(String value, String parameter, List<ExcellRow> srcPrintableRows,int currentRow)
{
	String[] params=parameter.split(",");
	int i=0;
	String currxp=srcPrintableRows.get(currentRow).getXpath();
	String pxp=srcPrintableRows.get(currentRow).getParentXpath();
	String currMatchVal=srcPrintableRows.get(currentRow).getBakupVal();
	String pMatchVal=srcPrintableRows.get(currentRow).getParentValue();
	
	String rv="1";
	
	for(;i<(params.length);i++)
	{
		if(params[i].equals("this"))
			{
			rv=(Double.parseDouble(rv)*Double.parseDouble(currMatchVal))+"";continue;
			}
		
		String tmp=params[i];
		//System.out.println(">>>>>>"+tmp.charAt(0)+tmp.charAt(tmp.length()-1));
		if(tmp.charAt(0)==tmp.charAt(tmp.length()-1)&&tmp.charAt(0)=='\"')
		{
			String tmpstr=tmp.substring(1,tmp.length()-1);
			rv=(Double.parseDouble(rv.trim())*Double.parseDouble(tmpstr.trim()))+"";
			//rv+= tmp.substring(1,tmp.length()-1);
			//System.out.println("================"+tmp.substring(1,tmp.length()-1));
			continue;
		}
		//char[] chars=params[i].toCharArray();
		//if(chars[0]=='\"'&&chars[chars.length-1]=='\"')
		//	System.out.println();
		//String[] valArr=params[i].split("\"");
		//System.out.println("-------------------"+params[i]);
		//System.out.println("----------------"+valArr.length);
		/*if(valArr.length>1)
		{
			rv+= valArr[valArr.length-1];
			System.out.println(">>>>>>>>>>>>"+rv);
			continue;
		}
		*/
		//loop through each xpath and palce value
		for(ExcellRow r:srcPrintableRows)
		{
			//String[] val_xp=params[i].split(">");
			String xp=params[i];
			//System.out.println(xp);
			//if(true)
			//{
				//return val of this xpath: val_xp[1]
				
				//for(ExcellRow er:srcPrintableRows)
				
					//System.out.println("matching "+r.getXpath()+" with "+xp +r.getXpath().equals(xp) + (r.getParentXpath()!=null)+r.getParentXpath().equals(pxp)+r.getParentValue().equals(pMatchVal)+" -- "+r.getParentValue()+" &&&&&&& "+pMatchVal);
					if(r.getXpath().equals(xp)&&r.getParentXpath()!=null&&r.getParentXpath().equals(pxp)&&r.getParentValue().equals(pMatchVal))
					{
						
						rv=(Double.parseDouble(rv.trim())*Double.parseDouble(r.getBakupVal().trim()))+"";
						//rv+= r.getValue();
					}
					else if(r.getXpath().equals(xp)&&r.getParentXpath()==null)
					{
						//rv+= r.getValue();
						rv=(Double.parseDouble(rv.trim())*Double.parseDouble(r.getBakupVal().trim()))+"";
					}
				
			//}
		}
	}
	//return the value of last xpath: params[i]
	/*if(currxp.equals(params[i]))
	{return currMatchVal;}
	else
	{
		for(ExcellRow er:srcPrintableRows)
		{
			if(er.getXpath().equals(params[i])&&er.getParentXpath()!=null&&er.getParentXpath().equals(currxp)&&er.getParentValue().equals(currMatchVal))
			{
				return er.getValue();
			}
		}

	}*/
	return rv;
}

public static String date2(String val, String param)
{
String rval="";
String[] params=param.split(",");
String src=params[0];
String tgt=params[1];
DateFormat sdf=null;
DateFormat tdf=null;
try{
sdf = new SimpleDateFormat(src);
tdf = new SimpleDateFormat(tgt);
}catch(Exception ee){
	JOptionPane.showMessageDialog(null, "Date format not specified correctly: "+src+" to "+tgt+" for"+val);
	LoggingClass.logger.info("Date format not specified correctly: "+src+" to "+tgt+" for"+val);
}
try{
Date d1 = sdf.parse(val);
rval=tdf.format(d1);
}catch(Exception e){System.out.println(e);}
return rval;
}
public static String encode(String data, String fmt)
{
		// TODO Auto-generated method stub
//String s="Canada Apparel - Operations";
String uec=data;
try{
uec=URLEncoder.encode(data,fmt);
}catch(Exception e){}
return uec;
}
	
public static String getPlusToPercentage(String str)
{
		return str.replaceAll("[\\+]|[\\ ]", "%20");
}
public static void main(String args[])
{
/*	ArrayList<Integer> i=new ArrayList<Integer>();
	i.add(0);
	i.add(2);
	
	String s="==X=";
	System.out.println(">"+trim(s,"=,B")+"<");
	String sourceValues="3";
	String parameter="1:002,2:008,4:004,xxxxxxxx";
	//System.out.println(">"+padding(sourceValues,parameter)+"<");
	//System.out.println(">"+date_converter(sourceValues,parameter)+"<");*/
	String sourceValue="1234567890";
	String parameter="0:1:R";
	System.out.println(">"+substring(sourceValue,parameter)+"<");
	/*String sourceValue="2013-01-11";
	String parameter="yyyy-MM-dd,dd-MMM-yyyy";
	System.out.println(">"+date2(sourceValue,parameter)+"<");

	String sourceValue="1234567890";
	String parameter="B:2";
	System.out.println(">"+crop(sourceValue,parameter)+"<");
	*/
	//System.out.println(">"+date2(sourceValue,parameter)+"<");
}
}
