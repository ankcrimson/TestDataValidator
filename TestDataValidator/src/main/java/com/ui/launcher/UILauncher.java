package com.ui.launcher;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.validator.VersionManager;
import com.validator.excel.ReadWriteExcel;
import com.validator.exceptions.CSVXPathException;
import com.validator.log.LoggingClass;
import com.validator.qa.idoc.ExcellRow;
import com.validator.qa.idoc.SplConditions;

public class UILauncher extends JFrame implements ActionListener,ItemListener,Runnable{

	/**
	 * @param args
	 */
	
	public void itemStateChanged(java.awt.event.ItemEvent ie) {
		if(ie.getSource().equals(sourceTypefld))
		{
			int i=(((JComboBox)ie.getSource()).getSelectedIndex());
			if(types[i].equals("Delimited")||types[i].equals("Delimited (TrimSpaces)"))
			{
				sdelfld.setVisible(true);
				sdelLab.setVisible(true);
			}
			else
			{
				sdelfld.setVisible(false);
				sdelLab.setVisible(false);
			}
			if(types[i].equals("Delimited")||types[i].equals("Delimited (TrimSpaces)")||types[i].equals("CSV")||types[i].equals("CSV (TrimSpaces)"))
			{
				sTQualLab.setVisible(true);
				sTQualfld.setVisible(true);
			}
			else
			{
				sTQualLab.setVisible(false);
				sTQualfld.setVisible(false);
			}
			if(types[i].equals("Delimited")||types[i].equals("Delimited (TrimSpaces)")||types[i].equals("FlatFile (TrimSpaces)")||types[i].equals("FlatFile")||types[i].equals("CSV")||types[i].equals("CSV (TrimSpaces)"))
			{
				srcHLab.setVisible(true);
				srcH.setVisible(true);
			}
			else
			{
				srcHLab.setVisible(false);
				srcH.setVisible(false);
			}
		}
		else if(ie.getSource().equals(targetTypefld))
		{
			int i=(((JComboBox)ie.getSource()).getSelectedIndex());
			if(types[i].equals("Delimited")||types[i].equals("Delimited (TrimSpaces)"))
			{
				tdelfld.setVisible(true);
				tdelLab.setVisible(true);
			}
			else
			{
				tdelfld.setVisible(false);
				tdelLab.setVisible(false);
			}
			if(types[i].equals("Delimited")||types[i].equals("Delimited (TrimSpaces)")||types[i].equals("CSV")||types[i].equals("CSV (TrimSpaces)"))
			{
				tTQualLab.setVisible(true);
				tTQualfld.setVisible(true);
			}
			else
			{
				tTQualLab.setVisible(false);
				tTQualfld.setVisible(false);
			}
			if(types[i].equals("Delimited")||types[i].equals("Delimited (TrimSpaces)")||types[i].equals("FlatFile (TrimSpaces)")||types[i].equals("FlatFile")||types[i].equals("CSV")||types[i].equals("CSV (TrimSpaces)"))
			{
				tgtHLab.setVisible(true);
				tgtH.setVisible(true);
			}
			else
			{
				tgtHLab.setVisible(false);
				tgtH.setVisible(false);
			}
		}
		
			
	}
	
	
	String[] types={"IDOC","XML","FlatFile","FlatFile (TrimSpaces)","CSV","CSV (TrimSpaces)","Delimited","Delimited (TrimSpaces)"};
	JPanel panel1=new JPanel();
	JLabel sourceTypeLab=new JLabel("Source Type");
	//JTextField sourceTypefld=new JTextField();
	JComboBox sourceTypefld=new JComboBox (types);
	
	JLabel targetTypeLab=new JLabel("Target Type");
	//JTextField targetTypefld=new JTextField();
	JComboBox targetTypefld=new JComboBox(types);
	
	JLabel srcFielLocLab=new JLabel("Source File Location");
	JTextField srcFielLocfldf=new JTextField();
	JFileChooser srcFielLocfld=new JFileChooser();
	
	JLabel tgtFielLocLab=new JLabel("Target File Location");
	JTextField tgtFielLocfldf=new JTextField();
	JFileChooser tgtFielLocfld=new JFileChooser();
	
	JLabel mapXLFileLab=new JLabel("Mapping Excel File Location");
	JTextField mapXLFilefldf=new JTextField();
	JFileChooser mapXLFilefld=new JFileChooser();
	JLabel mapWSNameLab=new JLabel("Mapping Worksheet Name");
	JTextField mapWSNamefld=new JTextField();
	JButton startButton=new JButton("Start");
	
	JButton openSourceBtn=new JButton("Open");
	JButton openTargetBtn=new JButton("Open");
	JButton openMappingBtn=new JButton("Open");
	ImagePanel cimg=new ImagePanel("src/main/resources/images/left.jpg",0);
	ImagePanel nimg=new ImagePanel("src/main/resources/images/right.jpg",1);
	
	JLabel sdelLab=new JLabel("SourceDelemiter");
	JTextField sdelfld=new JTextField();
	JLabel tdelLab=new JLabel("TargetDelemiter");
	JTextField tdelfld=new JTextField();
	
	JLabel sTQualLab=new JLabel("SourceTextQualifier");
	JTextField sTQualfld=new JTextField();
	JLabel tTQualLab=new JLabel("TargetTextQualifier");
	JTextField tTQualfld=new JTextField();
	
	JCheckBox srcH=new JCheckBox("Source First Row is Header Row");
	JCheckBox tgtH=new JCheckBox("Target First Row is Header Row");
	JLabel srcHLab=new JLabel("Source Has A Header Row");
	JLabel tgtHLab=new JLabel("Target Has A Header Row");
	
	JButton templateButton=new JButton("GenerateTemplate");
	
	
	public void actionPerformed(java.awt.event.ActionEvent evt) {
		//System.out.println(((JButton)evt.getSource()).getText()+"<<");
		
		try{
		JButton clickedBtn=null;
		Object src=evt.getSource();
		if(src instanceof JButton)
		{
			clickedBtn=(JButton)src;
		}
		//TODO
		if(clickedBtn==templateButton)
		{
			ReadWriteExcel rwe=new ReadWriteExcel();
			int resp=rwe.generateTemplateExcel();
			if(resp==0)
				JOptionPane.showMessageDialog(this, "Generated Template.xlsx with two sheets");
			else
				JOptionPane.showMessageDialog(this, "Failure Generating the File", "Failure", ERROR);
		}
		if(clickedBtn==openSourceBtn)
		{
			//LoggingClass.logger.info("Open Source Button Clicked");
			int i=srcFielLocfld.showOpenDialog(this);
			if(i==0)
			srcFielLocfldf.setText(srcFielLocfld.getSelectedFile().getPath());
		}
		if(clickedBtn==openTargetBtn)
		{
			//LoggingClass.logger.info("Open Target Button Clicked");
			int i=tgtFielLocfld.showOpenDialog(this);
			if(i==0)
			tgtFielLocfldf.setText(tgtFielLocfld.getSelectedFile().getPath());
		}
		if(clickedBtn==openMappingBtn)
		{
			//LoggingClass.logger.info("Open Mapping Button Clicked");
			int i=mapXLFilefld.showOpenDialog(this);
			if(i==0)
			mapXLFilefldf.setText(mapXLFilefld.getSelectedFile().getPath());
		}
		if(clickedBtn==startButton)
			{
			CoreLogic sui=new CoreLogic();
			boolean srcHRow=srcH.isSelected();
			boolean tgtHRow=tgtH.isSelected();
			String sourceType=(String)sourceTypefld.getSelectedItem();
			String targetType=(String)targetTypefld.getSelectedItem();
			String sourceFileLocation=(srcFielLocfld.getSelectedFile()!=null)?srcFielLocfld.getSelectedFile().getPath():"";
			String targetFileLocation=(tgtFielLocfld.getSelectedFile()!=null)?tgtFielLocfld.getSelectedFile().getPath():"";
			String MappingExcelFile=(mapXLFilefld.getSelectedFile()!=null)?mapXLFilefld.getSelectedFile().getPath():"";
			String mappingwrksheetName=mapWSNamefld.getText();
			String sdelimiter=sdelfld.getText();//",";
			String tdelimiter=tdelfld.getText();//",";
			
			LoggingClass.logger.info("Start Button Clicked");
			try{
				if(sourceFileLocation.equals("")||targetFileLocation.equals("")||MappingExcelFile.equals("")||mappingwrksheetName.equals(""))
				{
					if(sourceFileLocation.equals(""))
						JOptionPane.showMessageDialog(this, "Please choose a Source File");
					if(targetFileLocation.equals(""))
						JOptionPane.showMessageDialog(this, "Please choose a Target File");
					if(MappingExcelFile.equals(""))
						JOptionPane.showMessageDialog(this, "Please choose a Mapping File");
					if(mappingwrksheetName.equals(""))
						JOptionPane.showMessageDialog(this, "Please enter a Mapping Worksheet Name");

					//JOptionPane.showMessageDialog(this, "All fields are mandatory");
					return;
				}
				else
				{
					if((sourceType.equals("Delimited")||sourceType.equals("Delimited (TrimSpaces)"))&&sdelimiter.equals(""))
					{
						JOptionPane.showMessageDialog(this, "Please enter a Source Delemiter");
						return;
					}
					if((targetType.equals("Delimited")||targetType.equals("Delimited (TrimSpaces)"))&&tdelimiter.equals(""))
					{
						JOptionPane.showMessageDialog(this, "Please enter a Target Delemiter");
						return;
					}
				}
				if(sourceType.equals("CSV")||sourceType.equals("CSV (TrimSpaces)"))
				{
					sdelimiter=",";
				}
				if(targetType.equals("CSV")||targetType.equals("CSV (TrimSpaces)"))
				{
					tdelimiter=",";
				}
				String sTextQual=sTQualfld.getText();
				String tTextQual=tTQualfld.getText();
				sui.imitator(true,sourceType, targetType, sourceFileLocation, targetFileLocation, MappingExcelFile, mappingwrksheetName,this,sdelimiter,tdelimiter,sTextQual,tTextQual,srcHRow,tgtHRow);
				
				
			}catch(Exception e)
			{
				//System.out.println(e);
				e.printStackTrace();
				String msg=e.toString();
				if(e instanceof CSVXPathException)
				{msg="Error in CSV/Delimited XPath. Please see if you have any extra rows or you forgot to close the bracket anywhere in xpath";}
				LoggingClass.logger.info("Error "+msg);
				
				JOptionPane.showMessageDialog(this, "imitator:"+msg);
			}
			}
		}catch(Exception e){JOptionPane.showMessageDialog(this, "ActionPerformed:"+e);
		LoggingClass.logger.info("Error "+e);
		}
		}
	
	public UILauncher() {
		// TODO Auto-generated constructor stub
		super("Test Data Validator "+VersionManager.version);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//panel1.setLayout(new GridLayout(7,2,10,20));
		panel1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel1.setLayout(new GridBagLayout());
		setupFirst();
		this.add(panel1);
		setVisible(true);
		setSize(900, 550);
		for(;;)
		{
			Thread t=new Thread(this);
			t.start();
			try{Thread.sleep(1000);}catch(Exception e){}
		}
	}
	
	public void setupFirst()
	{
		
		double labWt=0.5;
		double compWt=1.5;
		double ifldWt=1.5;
		double ibtnWt=0.1;
		int y=0;
		Color bgColor=Color.white;
		
		panel1.setBackground(bgColor);
		srcH.setBackground(bgColor);
		tgtH.setBackground(bgColor);
		srcFielLocfldf.setEditable(false);
		tgtFielLocfldf.setEditable(false);
		mapXLFilefldf.setEditable(false);
		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.gridx=0;
		c1.weightx=labWt;
		c1.gridy=y;
		c1.insets = new Insets(10,0,0,0);
		panel1.add(sourceTypeLab,c1);
		
		
		c1.gridx=1;
		c1.weightx=compWt;
		c1.gridy=y;
		panel1.add(sourceTypefld,c1);
		
		y++;
		c1.gridy=y;
		c1.weightx=labWt;
		c1.gridx=0;
		panel1.add(sdelLab,c1);
		c1.gridx=1;
		c1.weightx=compWt;
		panel1.add(sdelfld,c1);
		
		
		y++;
		c1.gridy=y;
		c1.weightx=labWt;
		c1.gridx=0;
		panel1.add(sTQualLab,c1);
		c1.gridx=1;
		c1.weightx=compWt;
		panel1.add(sTQualfld,c1);
		
		y++;
		c1.gridy=y;
		c1.weightx=labWt;
		c1.gridx=0;
		panel1.add(srcHLab,c1);
		c1.gridx=1;
		c1.weightx=compWt;
		panel1.add(srcH,c1);
		
		
		
		
		y++;
		c1.gridx=0;
		c1.weightx=labWt;
		c1.gridy=y;
		panel1.add(targetTypeLab,c1);
		
		
		c1.gridx=1;
		c1.weightx=compWt;
		c1.gridy=y;
		panel1.add(targetTypefld,c1);
		
		y++;
		c1.gridy=y;
		c1.weightx=labWt;
		c1.gridx=0;
		panel1.add(tdelLab,c1);
		c1.weightx=compWt;
		c1.gridx=1;
		panel1.add(tdelfld,c1);
		
		
		y++;
		c1.gridy=y;
		c1.weightx=labWt;
		c1.gridx=0;
		panel1.add(tTQualLab,c1);
		c1.weightx=compWt;
		c1.gridx=1;
		panel1.add(tTQualfld,c1);
		
		
		y++;
		c1.gridy=y;
		c1.weightx=labWt;
		c1.gridx=0;
		panel1.add(tgtHLab,c1);
		c1.weightx=compWt;
		c1.gridx=1;
		panel1.add(tgtH,c1);
		
		
		////////////////////////////////////////
		sourceTypefld.addItemListener(this);
		targetTypefld.addItemListener(this);
		sdelfld.setVisible(false);
		sdelLab.setVisible(false);
		tdelfld.setVisible(false);
		tdelLab.setVisible(false);
		sTQualfld.setVisible(false);
		tTQualfld.setVisible(false);
		sTQualLab.setVisible(false);
		tTQualLab.setVisible(false);
		srcH.setVisible(false);
		
		tgtH.setVisible(false);
		srcHLab.setVisible(false);
		tgtHLab.setVisible(false);
		///////////////////////////////////////
		
		y++;
		c1.gridx=0;
		c1.weightx=labWt;
		c1.gridy=y;
		panel1.add(srcFielLocLab,c1);
		
		c1.gridx=1;
		c1.weightx=compWt;
		c1.gridy=y;
			JPanel srcPanel=new JPanel(new GridBagLayout());
			srcPanel.setBackground(bgColor);
			GridBagConstraints c2=new GridBagConstraints();
			c2.fill = GridBagConstraints.HORIZONTAL;
			c2.gridx=0;
			c2.weightx=ifldWt;
			c2.gridy=0;
			srcPanel.add(srcFielLocfldf,c2);
			c2.gridx=1;
			c2.weightx=ibtnWt;
			srcPanel.add(openSourceBtn,c2);
		panel1.add(srcPanel,c1);
		
		y++;
		c1.gridx=0;
		c1.weightx=labWt;
		c1.gridy=y;
		panel1.add(tgtFielLocLab,c1);
		
		c1.gridx=1;
		c1.weightx=compWt;
		c1.gridy=y;
			JPanel tgtPanel=new JPanel(new GridBagLayout());
			tgtPanel.setBackground(bgColor);
			c2.gridx=0;
			c2.weightx=ifldWt;
			tgtPanel.add(tgtFielLocfldf,c2);
			c2.gridx=1;
			c2.weightx=ibtnWt;
			tgtPanel.add(openTargetBtn,c2);
		panel1.add(tgtPanel,c1);
		
		y++;
		c1.gridx=0;
		c1.weightx=labWt;
		c1.gridy=y;
		panel1.add(mapXLFileLab,c1);
		//panel1.add(mapXLFilefld);
		c1.gridx=1;
		c1.weightx=compWt;
		c1.gridy=y;
			JPanel mapPanel=new JPanel(new GridBagLayout());
			mapPanel.setBackground(bgColor);
			c2.gridx=0;
			c2.weightx=ifldWt;
			mapPanel.add(mapXLFilefldf,c2);
			c2.gridx=1;
			c2.weightx=ibtnWt;
			mapPanel.add(openMappingBtn,c2);
		panel1.add(mapPanel,c1);
		
		y++;
		c1.gridx=0;
		c1.weightx=labWt;
		c1.gridy=y;
		panel1.add(mapWSNameLab,c1);
		
		
		c1.gridx=1;
		c1.weightx=compWt;
		c1.gridy=y;
		panel1.add(mapWSNamefld,c1);
		
		y++;
		c1.gridx=0;
		c1.weightx=labWt;
		c1.gridy=y;
		panel1.add(templateButton,c1);
		
		c1.gridx=1;
		c1.weightx=labWt;
		c1.gridy=y;
		panel1.add(startButton,c1);
		startButton.addActionListener(this);
		templateButton.addActionListener(this);
		openSourceBtn.addActionListener(this);
		openTargetBtn.addActionListener(this);
		openMappingBtn.addActionListener(this);
		
		
		y++;
		c1=new GridBagConstraints();
		c1.fill = GridBagConstraints.HORIZONTAL;     
		c1.ipady = 0;       //reset to default     
		c1.weighty = 1.0;   //request any extra vertical space     
     
		c1.insets = new Insets(10,0,0,0);  //top padding     
		c1.gridwidth = 1;   //2 columns wide     
		 
		
		
		c1.gridx=0;
		c1.weightx=labWt;
		c1.gridy=y;
		//c1.anchor=GridBagConstraints.FIRST_LINE_START;
		
		c1.anchor = GridBagConstraints.LAST_LINE_START;
		
		

		
		cimg.setBackground(bgColor);
		panel1.add(cimg,c1);
		c1.gridx=1;
		c1.weightx=labWt;
		c1.gridy=y;
		c1.anchor=GridBagConstraints.LAST_LINE_END;
		nimg.setBackground(bgColor);
		panel1.add(nimg,c1);
		
		
	}
	public void run() {
		// TODO Auto-generated method stub
		//this.setVisible(true);	
		panel1.repaint(500);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UILauncher mf=null;
		try{
			LoggingClass.logger.info("Start");
		mf=new UILauncher();
		}catch(Exception e)
		{JOptionPane.showMessageDialog(mf, ""+e);
		LoggingClass.logger.error("Exception", e);
		}
		
	}

}
