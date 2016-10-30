package com.ljheee.snip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * (截图完成后)保存截图
 * @author ljheee
 *
 */
public class SaveCaptureFrame extends JFrame{

	private static final long serialVersionUID = 5372620218928936963L;
	
	BufferedImage bImage = null;
	JMenuItem exitItem = null;
	JMenuItem saveItem = null;
	JMenuItem copyItem = null;
	JMenuItem aboutItem = null;
	
	ItemListener handler = new ItemListener();
	JFileChooser fileChooser = new JFileChooser();
	
	public SaveCaptureFrame(Point p0 , int width, int height, BufferedImage saveImage) {
		
		super("\u622A\u56FE\u5DE5\u51771.0");
		this.setSize(width+200, height+200);
//		this.setLocation(p0.x-50, p0.y-50);
		this.setLocation(p0);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		bImage = saveImage;
		
		Image icon = Toolkit.getDefaultToolkit().getImage("/logo.png");
		this.setIconImage(icon);
		
		//设置文件过滤
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter("GIF文件 (*.gif)",".gif"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG文件 (*.jpg;*.jpeg)",".jpg",".jpeg"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("BMP文件 (*.bmp)",".bmp"));
		
		
		JRootPane rootPane = new JRootPane(); //此panel，添加菜单
		rootPane.setBackground(Color.gray);
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("文件"); 
		JMenu editMenu = new JMenu("编辑"); 
		JMenu toolMenu = new JMenu("工具"); 
		JMenu helpMenu = new JMenu("帮助"); 
		
		rootPane.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolMenu);
		menuBar.add(helpMenu);
		
		//给菜单  添加菜单项
		saveItem = new JMenuItem("保存");
		exitItem = new JMenuItem("退出");
		copyItem = new JMenuItem("复制");
		aboutItem = new JMenuItem("关于");
		
		saveItem.addActionListener(handler);
		exitItem.addActionListener(handler);
		copyItem.addActionListener(handler);
		aboutItem.addActionListener(handler);
		
		fileMenu.add(new JMenuItem("打开"));
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		
		editMenu.add(copyItem);
		
		helpMenu.add(aboutItem);
		
		this.add(rootPane, BorderLayout.NORTH);
		
		
		this.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(bImage,50 , 80, this);
	}
	
	/**
	 * 菜单栏--菜单项事件监听处理
	 * @author ljheee
	 *
	 */
	class ItemListener implements ActionListener{

		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource()==saveItem){//保存截图
				
				int select = fileChooser.showSaveDialog(SaveCaptureFrame.this);
				if(select == JFileChooser.CANCEL_OPTION)   return;
				
//				fileChooser.setSelectedFile(new File("截图.png"));
				
				File fileDir = null;
				String fileName = null;
				
				if(select==JFileChooser.APPROVE_OPTION){
					fileDir =fileChooser.getSelectedFile();//在此=是选一个要保存的文件夹，下面的fileChooser.getName(file)将会返回手输入的文件名 
				}
				fileName = fileChooser.getName(fileDir);
				
				if(fileName==null|| fileName.trim().length()==0){
					JOptionPane.showMessageDialog(SaveCaptureFrame.this, "文件名为空！");
				}
				
					try {
						ImageIO.write(bImage, "png", fileDir);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				JOptionPane.showMessageDialog(SaveCaptureFrame.this, "Save Sucess");
			
			}
			if(e.getSource()==exitItem){
				System.exit(0);
			}
			if(e.getSource()==copyItem){
				
			}
			if(e.getSource()==aboutItem){
				JOptionPane.showMessageDialog(SaveCaptureFrame.this, "Author:ljheee \nQQ554278334 \n2016-7-23");
			}
			
		}
	}
	
	//test
	public static void main(String[] args) {
		new SaveCaptureFrame(new Point(50, 100), 200, 100, null);
	}

}
