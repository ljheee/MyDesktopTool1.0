package com.ljheee.snip;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * 初始化--小界面
 * @author ljheee
 *
 */
public class MainUI {

	JButton newRectCapture, newFullCapture, cancleBtn, aboutBtn;
	JFrame jf = new JFrame("\u622A\u56FE\u5DE5\u51771.0");
	UIListener handler = new UIListener();
	
	public MainUI() {
		
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setSize(280, 110);
		jf.setResizable(false);
//		jf.setIconImage(new ImageIcon("logo.png").getImage());
		Image icon = Toolkit.getDefaultToolkit().getImage("/logo.png");
		jf.setIconImage(icon);
		
		newRectCapture = new JButton("\u77E9\u5F62\u622A\u56FE");

		newFullCapture = new JButton("\u5168\u5C4F\u622A\u56FE");

		cancleBtn = new JButton("\u53D6\u6D88");
		cancleBtn.setEnabled(false);
		
		aboutBtn = new JButton();//about.png==35*35px
//		aboutBtn.setMargin(new Insets(75, 245, 0, 0));
		aboutBtn.setIcon(new ImageIcon(MainUI.class.getResource("/about.png")));
		aboutBtn.setToolTipText("\u5173\u4E8E");
		
		newRectCapture.addActionListener(handler);
		newFullCapture.addActionListener(handler);
		cancleBtn.addActionListener(handler);
		aboutBtn.addActionListener(handler);

		JLabel lblAuthorljheee = new JLabel("QQ: 554278334\r\n");

		GroupLayout groupLayout = new GroupLayout(jf.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(newRectCapture)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(newFullCapture)
							.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
							.addComponent(cancleBtn))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAuthorljheee, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(231, Short.MAX_VALUE)
					.addComponent(aboutBtn, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(newRectCapture)
						.addComponent(newFullCapture)
						.addComponent(cancleBtn))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblAuthorljheee, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(26)
							.addComponent(aboutBtn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(85, Short.MAX_VALUE))
		);
		jf.getContentPane().setLayout(groupLayout);

		jf.setVisible(true);

	}
	
	
	/**
	 * 内部类
	 * 监听处理--“初始化小界面”按钮功能
	 * @author ljheee
	 *
	 */
	class UIListener implements ActionListener {
		
		RectCaptureFrame rectCapture = null;
		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == aboutBtn) {
//				JOptionPane.showMessageDialog(null, "Author:ljheee \nQQ554278334 \n2016-7-23");
				new com.ljheee.snip.ui.about.About(jf,"关于",true);
			}
			
			if (e.getSource() == newRectCapture) {
				jf.setVisible(false);//先设置为不可见
				cancleBtn.setEnabled(true);
				rectCapture = RectCaptureFrame.getSnippingFrame(jf);
				jf.setVisible(true);//再设为可见，“小界面”会显示在上面
			}
			
			if (e.getSource() == newFullCapture) {
				jf.setVisible(false);
				jf.dispose();
				BufferedImage image = null;
				Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
				try {
					Robot robot = new Robot();
					image = robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height));
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
				new FullCaptureFrame(image);
			}
			
			if (e.getSource() == cancleBtn) {
				rectCapture.desory();
				cancleBtn.setEnabled(false);
			}

		}
	}

	
	
	public static void main(String[] args) {
		new MainUI();
	}
}
