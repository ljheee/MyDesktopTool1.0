package com.ljheee.paint.shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * ��ˢ--����Ͱ
 * ��һ�������Shape������Panel�ػ�ʱ�����ٴ����µ�[���������]
 * ֻ�ܰѴ���ʱ�Ĳ�����¼�������ڱ���Panel�ػ�ʱ����������ʱ��
 * @author ljheee
 *
 */
public class AirBrush extends Shape{
	/*���ñ�־���ڴ���ʱ��Ϊ1������Panel�ػ�ʱ��Ϊ2*/
	public int flag = 1;
	
	public int[] xx = new int[150];
	public int[] yy = new int[150];

	Color c = null;
	
	public AirBrush(int x1, int y1, int x2, int y2, Color color) {
		super(x1, y1, x2, y2, color);
	}

	@Override
	public void draw(Graphics2D g) {
		if(flag == 1){//����ʱ����̬���������
			g.setColor(color);
			c = g.getColor();
			Random random = new Random();
	        for (int i = 0; i < 150; i++)
	        {
	            int s;
	            double r;
	            s = random.nextInt(360); 
	            r =random.nextDouble();
	            int dx=(int) ( 50*Math.sqrt(r*r*r)*Math.sin((double)s)) ;
	            int dy=(int) ( 50*Math.sqrt(r*r*r)*Math.cos((double)s)) ;
	            xx[i] = x1+dx;
	            yy[i] = y1+dy;           
	            g.fillOval(xx[i], yy[i], 2, 2);
	        }
		}else{//����Panel�ػ�ʱ
			g.setColor(c);
			for (int i = 0; i < 150; i++) {
				g.fillOval(xx[i], yy[i], 2, 2);
			}
		}
	}
}
