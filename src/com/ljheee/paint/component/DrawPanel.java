package com.ljheee.paint.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.ljheee.paint.shape.AirBrush;
import com.ljheee.paint.shape.Shape;
import com.ljheee.paint.shape.ShapeList;

public class DrawPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	ShapeList list = null;
	Shape shape;
	public String filename;
	
	public Image img = null;
	/**
	 * ��һ�ִ�����ʽ
	 * ��drawPanel �ϻ��Ƹ���Shape
	 * @param lists
	 */
	public DrawPanel(ShapeList lists) {
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		//����һ�����а���б���Ե�ı߿�
		setBorder(BorderFactory.createLoweredBevelBorder());
		setBackground(Color.white);
		this.list = lists;
	}
	/**
	 * �ڶ��ִ�����ʽ
	 * ��drawPanel �ϻ���image
	 * @param img
	 */
	public DrawPanel(Image img) {
		this.img = img;
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g; // ���廭��
		
		if(img != null){
			g2d.drawImage(img, 0, 0, null);
		}
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				shape = list.getShape(i);
				if(shape instanceof AirBrush){
					AirBrush airBrush = (AirBrush) shape;
					airBrush.flag = 2;
				}
				shape.draw(g2d);
			}
		}
	}
	
}
