package com.wzj.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class Img {
	Random random = new Random();
	// 添加噪点的方法
	public int getRandomIntColor() {
		int[] rgb = this.getRandomRgb();
		int color = 0;
		for (int c : rgb) {
			color = color << 8;
			color = color | c;
		}
		return color;
	}

	public int[] getRandomRgb() {
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = random.nextInt(255);
		}
		return rgb;
	}

	// 扭曲方法
	public void shear(Graphics g, int w1, int h1, Color color) {
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	public void shearX(Graphics g, int w1, int h1, Color color) {

		int period = random.nextInt(2);

		boolean borderGap = true;
		int frames = 1;
		int phase = random.nextInt(2);

		for (int i = 0; i < h1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period
							+ (6.2831853071795862D * (double) phase)
							/ (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}

	}

	public void shearY(Graphics g, int w1, int h1, Color color) {

		int period = random.nextInt(40) + 10; // 50;

		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period
							+ (6.2831853071795862D * (double) phase)
							/ (double) frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}

		}

	}

	// 画一道粗线的方法
	public void drawThickLine(Graphics g, int x1, int y1, int x2, int y2,
			int thickness, Color c) {

		// The thick line is in fact a filled polygon
		g.setColor(c);
		int dX = x2 - x1;
		int dY = y2 - y1;
		// line length
		double lineLength = Math.sqrt(dX * dX + dY * dY);

		double scale = (double) (thickness) / (2 * lineLength);

		// The x and y increments from an endpoint needed to create a
		// rectangle...
		double ddx = -scale * (double) dY;
		double ddy = scale * (double) dX;
		ddx += (ddx > 0) ? 0.5 : -0.5;
		ddy += (ddy > 0) ? 0.5 : -0.5;
		int dx = (int) ddx;
		int dy = (int) ddy;

		// Now we can compute the corner points...
		int xPoints[] = new int[4];
		int yPoints[] = new int[4];

		xPoints[0] = x1 + dx;
		yPoints[0] = y1 + dy;
		xPoints[1] = x1 - dx;
		yPoints[1] = y1 - dy;
		xPoints[2] = x2 - dx;
		yPoints[2] = y2 - dy;
		xPoints[3] = x2 + dx;
		yPoints[3] = y2 + dy;

		g.fillPolygon(xPoints, yPoints, 4);
	}

	// 随机得到一个汉字的方法
	public char getRandomChar() {
		final int minFirstByte = 176;
		final int maxFirstByte = 215;
		final int minSecondByte = 161;
		final int maxSecondByte = 249;

		byte[] b = new byte[2];
		b[0] = (byte) (getRandomIntBetween(minFirstByte, maxFirstByte));
		b[1] = (byte) (getRandomIntBetween(minSecondByte, maxSecondByte));
		try {
			String s = new String(b, "gb2312");
			assert s.length() == 1;
			return s.charAt(0);
		} catch (UnsupportedEncodingException uee) {
			// 重试
			return getRandomChar();
		}
	}

	/**
	 * 获取两个整数之间的某个随机数，包含较小的整数，不包含较大的整数
	 */
	public int getRandomIntBetween(int first, int second) {
		if (second < first) {
			int tmp = first;
			first = second;
			second = tmp;
		}
		return random.nextInt(second - first) + first;
	}

	/**
	 * 产生随机字体
	 */
	public Font getFont() {
		Random random = new Random();
		Font font[] = new Font[5];
		font[0] = new Font("Ravie", Font.PLAIN, 40);
		font[1] = new Font("Antique Olive Compact", Font.PLAIN, 40);
		font[2] = new Font("Fixedsys", Font.PLAIN, 40);
		font[3] = new Font("Wide Latin", Font.PLAIN, 40);
		font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, 40);
		return font[random.nextInt(5)];
	}

	/**
	 * 随机产生定义的颜色
	 */
	public Color getRandColor() {
		Random random = new Random();
		Color color[] = new Color[10];
		color[0] = new Color(32, 158, 25);
		color[1] = new Color(218, 42, 19);
		color[2] = new Color(31, 75, 208);
		return color[random.nextInt(3)];
	}

	public Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
