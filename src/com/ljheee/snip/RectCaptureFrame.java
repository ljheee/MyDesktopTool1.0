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
 * ���ν���
 *  Singleton
 * @author ljheee
 *
 */
public class RectCaptureFrame extends JFrame {

	private static final long serialVersionUID = -7757266057337551153L;

	private static RectCaptureFrame thisInstance = null;

	int orgx = 0, orgy = 0, endx = 0, endy = 0,mouseXNow = 0,mouseYNow = 0;
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	BufferedImage fullScreenImage = null;// ��ǰ��Ļ--ȫ��ͼ
	BufferedImage tempImage = null;//
	BufferedImage saveImage = null;
	Graphics g = null;
	Point p0 = null;
	int width = 0, height = 0;

	@Override
	public void paint(Graphics g) {
		RescaleOp ro = new RescaleOp(0.8f, 0, null); // ��Դͼ�������ݽ���������������
		tempImage = ro.filter(fullScreenImage, null); // ��Դ srcImage ����������
		g.drawImage(tempImage, 0, 0, this);
	}
	
	/**
	 * ����ģʽ��ȫ�־�̬���ʵ�
	 * @param jf
	 * @return
	 */
	public static RectCaptureFrame getSnippingFrame(JFrame jf) {
		
		if (thisInstance == null) {
			thisInstance = new RectCaptureFrame(jf);
		}
		
		//�����ν�ͼʵ����Ϊ�գ���һ�Ρ��½����ν�ͼʱ�������ؾ�ʵ��ǰ����ˢ��Frame�������һ�εĽ�ͼ���κۼ�
		thisInstance.paint(thisInstance.getGraphics());
		return thisInstance;
	}

	/**
	 * ˽�й��췽��
	 * @param jf
	 */
	private RectCaptureFrame(JFrame jf) {
		
		snapshot();//��ȡ��ǰ��Ļ������ͼƬ
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
				RectCaptureFrame.this.setCursor(CROSSHAIR_CURSOR);//ʮ�������
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});

		/**
		 * ���Ȧ������ ��Ȧ�����������
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
				// ����1����ֹwidth��heightΪ0

				g.setColor(Color.BLUE);
				g.drawRect(x - 1, y - 1, width + 1, height + 1);// ��ͼ�������ľ��ο�
				// ��1����1����Ϊ�˷�ֹͼƬ�����ο򸲸ǵ�

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
	 * (С��������ȡ����ʱ)���ٵ�ǰ���ν�ͼʵ��
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
	 * ��ȡ��ǰ��Ļ������ͼƬ
	 */
	public void snapshot() {
		try {
			Robot robot = new Robot();
			
			// ������������Ļ�ж�ȡ�����ص�ͼ��
			fullScreenImage = robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height));
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
