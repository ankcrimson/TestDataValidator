package com.ui.launcher;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	public ImagePanel() {
		// TODO Auto-generated constructor stub
	}
	public ImagePanel(String path, int pos)
	{
		this.setLayout(new BorderLayout());
		try{
		BufferedImage myPicture = ImageIO.read(new File(path));
		JLabel picLabel = new JLabel(new ImageIcon( myPicture ));
		if(pos==1)
			add( picLabel , BorderLayout.EAST);
		else
			add( picLabel , BorderLayout.WEST);
		}catch(Exception e){JOptionPane.showMessageDialog(null, "Image not found at "+path);}
	}
@Override
protected void paintComponent(Graphics g) {
	// TODO Auto-generated method stub
	super.paintComponent(g);
	
}
}
