package com.ljheee.app;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.ljheee.snip.MainUI;

/**
 * 
 * @author ljheee
 *
 */
public class App {

	public static boolean flag = true;

	/**
	 * ���ϵͳ����
	 * 
	 * @author ljheee
	 *
	 */
	static class SystemTrayDemo extends JFrame {

		private static final long serialVersionUID = 1L;

		private TrayIcon trayIcon = null;

		public SystemTrayDemo() {
			if (SystemTray.isSupported()) {// ��鵱ǰϵͳ�Ƿ�֧��ϵͳ����

				// ��ȡ��ʾ������������SystemTrayʵ����
				SystemTray tray = SystemTray.getSystemTray();

				Image image = this.getToolkit().getImage(this.getClass().getResource("/image/logo.png"));
				PopupMenu popupMenu = new PopupMenu();
				MenuItem exitItem = new MenuItem("�˳�");
				MenuItem captureItem = new MenuItem("��ͼ����");
				MenuItem paintItem = new MenuItem("��ͼ����");
				exitItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							System.exit(0);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				popupMenu.add(captureItem);
				popupMenu.add(paintItem);
				popupMenu.add(exitItem);
				trayIcon = new TrayIcon(image, "DesktopTool", popupMenu);
				while (flag) {
					flag = false;
					captureItem.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							// ������ý�ͼ����
							new Thread(new Runnable() {
								@Override
								public void run() {
									new MainUI();
								}
							}).start();
						}
					});
					
					paintItem.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							// ������û�ͼ����
							System.out.println("��ͼ����");
						}

					});
					try {
						tray.add(trayIcon);// �� TrayIcon ��ӵ� SystemTray��
					} catch (AWTException e) {
						System.err.println(e);
					}
				}
			} else {
				System.out.println("���ϵͳ��֧��ϵͳ����");
			}
			try {
			} catch (Exception e) {
			}

		}
	}

	public static void main(String[] args) {
		new SystemTrayDemo().setVisible(false);
	}

}
