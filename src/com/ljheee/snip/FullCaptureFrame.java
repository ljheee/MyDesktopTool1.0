package com.ljheee.snip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * ȫ����ͼ
 * 
 * @author ljheee
 *
 */
public class FullCaptureFrame extends JFrame {

	private static final long serialVersionUID = -8762875503137697117L;

	Image bImage = null;
	JMenuItem exitItem = null;
	JMenuItem saveItem = null;
	JMenuItem copyItem = null;
	JMenuItem aboutItem = null;

	ItemListener handler = new ItemListener();
	JFileChooser fileChooser = new JFileChooser();

	public FullCaptureFrame(BufferedImage image) {

		super("\u622A\u56FE\u5DE5\u51771.0");
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Image icon = Toolkit.getDefaultToolkit().getImage("/logo.png");
		this.setIconImage(icon);
		// �����ļ�����
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter("GIF�ļ� (*.gif)", ".gif"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG�ļ� (*.jpg;*.jpeg)", ".jpg", ".jpeg"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("BMP�ļ� (*.bmp)", ".bmp"));

		JRootPane rootPane = new JRootPane(); // ��panel����Ӳ˵�
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

		// ���˵� ��Ӳ˵���
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

		bImage = image.getScaledInstance(image.getWidth() - 10, image.getHeight() - 60,
				BufferedImage.SCALE_AREA_AVERAGING);
		this.add(new PaintImagePanel(bImage));

		this.setVisible(true);
	}
	
	/**
	 * Imageת����BufferedImage
	 * @param image
	 * @return
	 */
	public BufferedImage toBufferedImage(Image image) {

		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		image = new ImageIcon(image).getImage();

		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
			e.printStackTrace();
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			// int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
			/*
			 * if (hasAlpha) { type = BufferedImage.TYPE_INT_ARGB; }
			 */
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	/**
	 * ��Ҫ��JFrame��paint�����л���ͼƬ����Ϊ����JFrame��Ҫ����������ƣ�
	 * �������ͼƬ���ͻ���һЩ��˸������õķ��������¶���һ�����Component
	 * ����JComponent�̳У�Ȼ��ѻ��ƵĶ������ŵ����������,Ȼ����JFrame��� ���д���һ���´����Ļ����������OK�ˡ�
	 * 
	 * @author ljheee
	 *
	 */
	class PaintImagePanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private Image image;

		public PaintImagePanel(Image image) {
			this.image = image;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(image, 0, 0, this);
		}
	}

	/**
	 * �˵���--�˵����¼���������
	 * 
	 * @author ljheee
	 *
	 */
	class ItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == saveItem) {// �����ͼ

				int select = fileChooser.showSaveDialog(FullCaptureFrame.this);
				if (select == JFileChooser.CANCEL_OPTION)
					return;

				// fileChooser.setSelectedFile(new File("��ͼ.png"));

				File fileDir = null;
				String fileName = null;

				if (select == JFileChooser.APPROVE_OPTION) {
					fileDir = fileChooser.getSelectedFile();// �ڴ�=��ѡһ��Ҫ������ļ��У������fileChooser.getName(file)���᷵����������ļ���
				}
				fileName = fileChooser.getName(fileDir);

				if (fileName == null || fileName.trim().length() == 0) {
					JOptionPane.showMessageDialog(FullCaptureFrame.this, "�ļ���Ϊ�գ�");
				}
				try {
					ImageIO.write(toBufferedImage(bImage), "png", fileDir);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(FullCaptureFrame.this, "Save Sucess");

			}
			if (e.getSource() == exitItem) {
				System.exit(0);
			}
			if (e.getSource() == copyItem) {

			}
			if (e.getSource() == aboutItem) {
				JOptionPane.showMessageDialog(FullCaptureFrame.this, "Author:ljheee \nQQ554278334 \n2016-7-23");
			}

		}
	}

}
