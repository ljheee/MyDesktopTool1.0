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
 * (��ͼ��ɺ�)�����ͼ
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
		
		//�����ļ�����
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter("GIF�ļ� (*.gif)",".gif"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG�ļ� (*.jpg;*.jpeg)",".jpg",".jpeg"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("BMP�ļ� (*.bmp)",".bmp"));
		
		
		JRootPane rootPane = new JRootPane(); //��panel����Ӳ˵�
		rootPane.setBackground(Color.gray);
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("�ļ�"); 
		JMenu editMenu = new JMenu("�༭"); 
		JMenu toolMenu = new JMenu("����"); 
		JMenu helpMenu = new JMenu("����"); 
		
		rootPane.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolMenu);
		menuBar.add(helpMenu);
		
		//���˵�  ��Ӳ˵���
		saveItem = new JMenuItem("����");
		exitItem = new JMenuItem("�˳�");
		copyItem = new JMenuItem("����");
		aboutItem = new JMenuItem("����");
		
		saveItem.addActionListener(handler);
		exitItem.addActionListener(handler);
		copyItem.addActionListener(handler);
		aboutItem.addActionListener(handler);
		
		fileMenu.add(new JMenuItem("��"));
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
	 * �˵���--�˵����¼���������
	 * @author ljheee
	 *
	 */
	class ItemListener implements ActionListener{

		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource()==saveItem){//�����ͼ
				
				int select = fileChooser.showSaveDialog(SaveCaptureFrame.this);
				if(select == JFileChooser.CANCEL_OPTION)   return;
				
//				fileChooser.setSelectedFile(new File("��ͼ.png"));
				
				File fileDir = null;
				String fileName = null;
				
				if(select==JFileChooser.APPROVE_OPTION){
					fileDir =fileChooser.getSelectedFile();//�ڴ�=��ѡһ��Ҫ������ļ��У������fileChooser.getName(file)���᷵����������ļ��� 
				}
				fileName = fileChooser.getName(fileDir);
				
				if(fileName==null|| fileName.trim().length()==0){
					JOptionPane.showMessageDialog(SaveCaptureFrame.this, "�ļ���Ϊ�գ�");
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
