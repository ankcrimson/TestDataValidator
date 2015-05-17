package com.validator.qa.idoc;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.validator.log.LoggingClass;

public class IDOCHandeler extends DefaultHandler {

	boolean stat = false;
	static int i = 0;
	Stack<String> xpath = new Stack<String>();
	IDOCNode root;
	IDOCNode prev;
	boolean charsProcessed = false;
int elem=0;
	public IDOCNode getRoot() {
		return root;
	}

	List<ExcellRow> excellRows;
	IDOCNode n;
	String prevXPath = "";

	public List<ExcellRow> getExcellRows() {
		return excellRows;
	}

	public void setExcellRows(List<ExcellRow> excellRows) {
		this.excellRows = excellRows;
	}

	public IDOCHandeler() {
		// TODO Auto-generated constructor stub
		root = new IDOCNode();
		root.setParent(null);
		root.setXpath("//");
		root.setPk("");
		prev = root;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
elem++;//for ampercends
		charsProcessed = false;// for blank tags
		// System.out.println(qName.lastIndexOf(":"));
		if (qName.indexOf(":") > -1) {
			qName = qName.substring(qName.indexOf(":") + 1);
		}
		xpath.push(localName + qName);
		n = new IDOCNode();
		String xp = calculateXP(xpath);
		n.setXpath(xp);
		n.setParent(prev);
		prev.getChildren().add(n);
		prev = n;
		if (attributes.getLength() > 0) {
			// System.out.println("attr_len:"+attributes.getLength());

			for (int i = 0; i < attributes.getLength(); i++) {
				String nm = attributes.getQName(i);
				if (nm == null || nm.length() == 0)
					nm = attributes.getLocalName(i);
				String val = attributes.getValue(i);
				// System.out.println(nm+"--"+val);
				IDOCNode attrNode = new IDOCNode();
				String tmp_xp = xp + "/" + nm;
				attrNode.setXpath(tmp_xp);
				attrNode.setParent(prev);
				attrNode.setValue(val);
				prev.getChildren().add(attrNode);
				/*
				 * ExcellRow curr=null; //System.out.println("+++>"+tmp_xp);
				 * for(ExcellRow r:excellRows) if(r.getXpath().equals(tmp_xp)) {
				 * curr=r; attrNode.setParentXpath(curr.getParentXpath());
				 * attrNode.setPk(curr.getPk());
				 * attrNode.setRow(curr.getOccurances());
				 * 
				 * }
				 */

				for (ExcellRow r : excellRows) {
					// System.out.println(tmp_xp+"---"+r.getXpath());
					if (r.getXpath().equals(tmp_xp)) {
						r.setUsed(true);
						// System.out.println("rrrrrrrrrrr->"+r);
						ExcellRow newRow = new ExcellRow(r);
						newRow.setValue(val);
						newRow.setBakupVal(val);
						attrNode.addRow(newRow);
						newRow.setNode(attrNode);
						// prev.setParentXpath(r.getParentXpath());
						// prev.setPk(r.getIdocAttributes());

						// n.setRow(curr.getOccurances());
						// System.out.println("----"+xp+value);
					}
				}

			}
		}
	}
int elemPrev=0;
String value="";
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		// n=new Node();
		
		charsProcessed = true;// for blank tags

		if(elem==elemPrev)//ampercandCandidate
		{
			value = value+( new String(ch, start, length));
		}
		else
		value = new String(ch, start, length);
		
		
		elemPrev=elem;
		
	}

	public String calculateXP(Stack<String> xpath) {
		StringBuffer sb = new StringBuffer("//");
		// String xp="//";
		for (String curr : xpath)
			sb.append((sb.toString().equals("//")) ? curr : "/" + curr);
		return sb.toString();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);

		
		
		
	//	if(elem!=elemPrev)//ampercandCandidate
		{
				String xp = calculateXP(xpath);
				prev.setValue(value);
				prev.setXpath(xp);
				prev.setUid(++i);
				// System.out.println(prev.value+prev.getUid());
				// System.out.println(value);
				// System.out.println(xp);
				for (ExcellRow r : excellRows)
					if (r.getXpath().equals(xp)) {
						// System.out.println("rrrrrrrrrrr->"+r);
						r.setUsed(true);
						ExcellRow newRow = new ExcellRow(r);
						newRow.setValue(value);
						newRow.setBakupVal(value);
						prev.addRow(newRow);
						newRow.setNode(prev);
						// prev.setParentXpath(r.getParentXpath());
						// prev.setPk(r.getIdocAttributes());

						// n.setRow(curr.getOccurances());
						// System.out.println("----"+xp+value);
					}
				// nesting();

		}
		
		
		
		
		
		
		
		// for blank tags
		if (!charsProcessed) {
			{
				String nm = qName;
				if (nm == null || nm.length() == 0)
					nm = localName;
				String val = "";
				// System.out.println(nm+"--"+val);
				IDOCNode attrNode = new IDOCNode();
				String tmp_xp = calculateXP(xpath);
				attrNode.setXpath(tmp_xp);
				attrNode.setParent(prev);
				attrNode.setValue(val);
				prev.getChildren().add(attrNode);

				for (ExcellRow r : excellRows) {
					// System.out.println(tmp_xp+"---"+r.getXpath());
					if (r.getXpath().equals(tmp_xp)) {
						r.setUsed(true);
						// System.out.println("rrrrrrrrrrr->"+r);
						ExcellRow newRow = new ExcellRow(r);
						newRow.setValue(val);
						newRow.setBakupVal(val);
						attrNode.addRow(newRow);
						newRow.setNode(attrNode);

					}
				}
				charsProcessed = true;
			}
		}
		// for blank tags

		// String s=xpath.peek();//

		xpath.pop();
		// LoggingClass.logger.info(xpath.peek()+"-"+s+"\t"+calculateXP(xpath)+"/"+s);//
		/*
		 * String s2=""; try{ s2=xpath.peek(); }catch(Exception ex2){s2="";}
		 */// LoggingClass.logger.info(s2+"-"+s+"\t"+calculateXP(xpath)+"/"+s);//
		prev = prev.getParent();

	}

}
