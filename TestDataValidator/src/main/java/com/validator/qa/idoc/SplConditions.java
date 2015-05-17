package com.validator.qa.idoc;

public class SplConditions {
int rownumber;
String validationSpCondition;
String validationParameter;
public SplConditions(int rownumber,String validationSpCondition,String validationParameter) 
{
	// TODO Auto-generated constructor stub
	this.rownumber=rownumber;
	this.validationSpCondition=validationSpCondition;
	this.validationParameter=validationParameter;
}
public SplConditions() {
	// TODO Auto-generated constructor stub
}

public int getRownumber() {
	return rownumber;
}
public void setRownumber(int rownumber) {
	this.rownumber = rownumber;
}
public String getValidationSpCondition() {
	return validationSpCondition;
}
public void setValidationSpCondition(String validationSpCondition) {
	this.validationSpCondition = validationSpCondition;
}
public String getValidationParameter() {
	return validationParameter;
}
public void setValidationParameter(String validationParameter) {
	this.validationParameter = validationParameter;
}

}
