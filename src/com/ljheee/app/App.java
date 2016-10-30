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
	 * 添加系统托盘
	 * 
	 * @author ljheee
	 *
	 */
	static class SystemTrayDemo extends JFrame {

		private static final long serialVersionUID = 1L;

		private TrayIcon trayIcon = null;

		public SystemTrayDemo() {
			if (SystemTray.isSupported()) {// 检查当前系统是否支持系统托盘

				// 获取表示桌面托盘区的SystemTray实例。
				SystemTray tray = SystemTray.getSystemTray();

				Image image = this.getToolkit().getImage(this.getClass().getResource("/image/logo.png"));
				PopupMenu popupMenu = new PopupMenu();
				MenuItem exitItem = new MenuItem("退出");
				MenuItem captureItem = new MenuItem("截图工具");
				MenuItem paintItem = new MenuItem("画图工具");
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
							// 这里调用截图功能
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
							// 这里调用画图工具
							System.out.println("画图工具");
						}

					});
					try {
						tray.add(trayIcon);// 将 TrayIcon 添加到 SystemTray。
					} catch (AWTException e) {
						System.err.println(e);
					}
				}
			} else {
				System.out.println("你的系统不支持系统托盘");
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
