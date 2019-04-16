package ie.gmit.sw;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class Artist {
	private static int i_MAX;
	private static int i_SIZE;
	private static float f_SCALER;
	private static int i_WIDTH = 1920;
	private static int i_HEIGHT = 1080;
	private static Point[] aP_STARTPOINTS;
	private static Point[] aP_ENDPOINTS;
	
	public static void init(Map<String, Integer> m_words) {
		i_MAX = (int) m_words.values().toArray()[0];
		i_SIZE = m_words.size();
		System.out.println(i_MAX + " : " + i_SIZE);
		aP_STARTPOINTS = new Point[i_SIZE];
		aP_ENDPOINTS = new Point[i_SIZE];
		
		f_SCALER = (i_SIZE / i_HEIGHT);
	}
	
	public static void draw(Map<String, Integer> m_words) throws IOException {
		//Font font = new Font(Font.SANS_SERIF, Font.BOLD, 62);
		BufferedImage image = new BufferedImage(i_WIDTH, i_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.red);
		
		AffineTransform affineTransform = new AffineTransform();
		
		List<String> l_keys = new ArrayList<>(m_words.keySet());
		List<Integer> l_values = new ArrayList<>(m_words.values());
		int i_x = 20;
		int i_y = 150;
		for(int i = 0; i < l_keys.size(); i++) {
			Font fo_font = new Font(Font.SANS_SERIF, Font.BOLD, (int)((float)l_values.get(i) / f_SCALER)).deriveFont(affineTransform);
			//FontMetrics fm_metrics = Toolkit.getDefaultToolkit().getFontMetrics(fo_font);
			graphics.setFont(fo_font);
			graphics.drawString(l_keys.get(i), i_x, i_y);
			i_y += (int)((float)l_values.get(i) / f_SCALER);
			if(i_y > 1080) {
				i_y = 50;
				i_x += 250;
			}
			//if(i==0) {
				//graphics.drawString(l_keys.get(i), i * 5, 0);
				//aP_STARTPOINTS[i].x = 960;
				//aP_STARTPOINTS[i].y = 540;
				//aP_ENDPOINTS[i].x = (fo_font.getStringBounds((l_keys.get(i), fm_metrics.getFontRenderContext())).OUT_RIGHT;
			//}
		}
		
		/*m_words.forEach((String s, Integer i)->{
			graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (int)((float)i / f_SCALER)).deriveFont(affineTransform));
			
			//graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
			
			//graphics.drawString(s, 960, 540);
			/*if(i_iter ==0) {
				graphics.drawString(s, 960, 540);
				aP_STARTPOINTS[i_iter].x = 960;
				/aP_STARTPOINTS[i_iter].y = 540;
			}
			else {
				graphics.drawString(s, aP_ENDPOINTS[i_iter-1].x, aP_STARTPOINTS[i_iter-1].y);
				}
				
			graphics.drawString(s, 960, 540);
			affineTransform.rotate(Math.toRadians(90), 0, 0);
		});*/
		/*
		graphics.setFont(font);
		graphics.drawString("Data Structures", 0, 100);
		font = new Font(Font.SANS_SERIF, Font.ITALIC, 42);
		graphics.setFont(font);
		graphics.setColor(Color.yellow);
		graphics.drawString("and Algorithms", 10, 150);
		font = new Font(Font.MONOSPACED, Font.PLAIN, 22);
		graphics.setFont(font);
		graphics.setColor(Color.blue);
		graphics.drawString("2019Assignment", 40, 180);*/
		graphics.dispose();
		ImageIO.write(image, "png", new File("image.png"));
	}
}
