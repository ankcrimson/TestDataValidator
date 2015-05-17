package com.ui.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HelpButtonPanel extends JPanel implements ActionListener{
JButton helpButton;
public HelpButtonPanel(JFrame f, Color c) {
	// TODO Auto-generated constructor stub
	ImageIcon i=new ImageIcon("images/help.bmp");
	helpButton=new JButton(i);
	setLayout(new BorderLayout());
	add(helpButton,BorderLayout.WEST);
	helpButton.addActionListener(this);
	this.setBackground(c);
	helpButton.setBackground(c);
	helpButton.setBorder(BorderFactory.createEmptyBorder());
}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("hi");
	}

}
