package com.ljheee.snip;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.JFrame;

/**
 * 矩形截屏
 *  Singleton
 * @author ljheee
 *
 */
public class RectCaptureFrame extends JFrame {

	private static final long serialVersionUID = -7757266057337551153L;

	private static RectCaptureFrame thisInstance = null;

	int orgx = 0, orgy = 0, endx = 0, endy = 0,mouseXNow = 0,mouseYNow = 0;
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	BufferedImage fullScreenImage = null;// 当前屏幕--全景图
	BufferedImage tempImage = null;//
	BufferedImage saveImage = null;
	Graphics g = null;
	Point p0 = null;
	int width = 0, height = 0;

	@Override
	public void paint(Graphics g) {
		RescaleOp ro = new RescaleOp(0.8f, 0, null); // 对源图像中数据进行逐像素重缩放
		tempImage = ro.filter(fullScreenImage, null); // 对源 srcImage 进行重缩放
		g.drawImage(tempImage, 0, 0, this);
	}
	
	/**
	 * 单例模式：全局静态访问点
	 * @param jf
	 * @return
	 */
	public static RectCaptureFrame getSnippingFrame(JFrame jf) {
		
		if (thisInstance == null) {
			thisInstance = new RectCaptureFrame(jf);
		}
		
		//当矩形截图实例不为空，又一次“新建矩形截图时”，返回旧实例前，需刷新Frame，清除上一次的截图矩形痕迹
		thisInstance.paint(thisInstance.getGraphics());
		return thisInstance;
	}

	/**
	 * 私有构造方法
	 * @param jf
	 */
	private RectCaptureFrame(JFrame jf) {
		
		snapshot();//截取当前屏幕的满屏图片
		this.setSize(d);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setUndecorated(true);
		
		Image icon = Toolkit.getDefaultToolkit().getImage("/logo.png");
		this.setIconImage(icon);
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				new SaveCaptureFrame(p0, width, height,saveImage);
				thisInstance.desory();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				jf.setVisible(false);
				jf.dispose();
				orgx = e.getX();
				orgy = e.getY();
				p0 = new Point(orgx, orgy);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				RectCaptureFrame.this.setCursor(CROSSHAIR_CURSOR);//十字型鼠标
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});

		/**
		 * 鼠标圈定区域， 对圈定的区域截屏
		 */
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				endx = e.getX();
				endy = e.getY();
				mouseXNow = e.getXOnScreen();
				mouseYNow = e.getYOnScreen();
				g = RectCaptureFrame.this.getGraphics();
				g.drawImage(tempImage, 0, 0, RectCaptureFrame.this);
				int x = Math.min(orgx, endx);
				int y = Math.min(orgy, endy);

				width = Math.abs(endx - orgx) + 1;
				height = Math.abs(endy - orgy) + 1;
				// 加上1，防止width或height为0

				g.setColor(Color.BLUE);
				g.drawRect(x - 1, y - 1, width + 1, height + 1);// 截图所拉出的矩形框
				// 减1，加1都是为了防止图片将矩形框覆盖掉

				saveImage = fullScreenImage.getSubimage(x, y, width, height);
				g.drawImage(saveImage, x, y, RectCaptureFrame.this);
				String sizeTip = Math.abs(orgx-mouseXNow)+"x"+Math.abs(orgy-mouseYNow);
				g.setColor(Color.BLACK);
				g.fillRect((orgx>mouseXNow?mouseXNow:orgx), (orgy>mouseYNow?mouseYNow:orgy)-20, sizeTip.length()*7, 18);
				g.setColor(Color.WHITE);
				g.drawString(sizeTip, (orgx>mouseXNow?mouseXNow:orgx), (orgy>mouseYNow?mouseYNow:orgy)-7);
//				new SaveCaptureFrame(p0, width, height);
			}
		});

		this.setVisible(true);
	}

	/**
	 * (小界面点击“取消”时)销毁当前矩形截图实例
	 */
	public void desory() {
		if(thisInstance!=null){
			try {
				thisInstance.dispose();
				thisInstance.finalize();
				thisInstance = null;
				System.gc();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 截取当前屏幕的满屏图片
	 */
	public void snapshot() {
		try {
			Robot robot = new Robot();
			
			// 创建包含从屏幕中读取的像素的图像。
			fullScreenImage = robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height));
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
