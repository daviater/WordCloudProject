package ie.gmit.sw;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

class WordNode{
	Rectangle r_rect = new Rectangle();
	WordNode[] aW_wordNodes = new WordNode[8];
	WordNode W_parent = null;
	int i_depth =0;
	
	WordNode(int i_width, int i_height) {
	 this.r_rect.width = i_width;
	 this.r_rect.height = i_height;
	}
	
	WordNode(Rectangle r_rect){
		this.r_rect = r_rect;
	}
	
	WordNode addNode(int i_width, int i_height) {
		System.out.println(i_depth);
		WordNode W_node = new WordNode(i_width, i_height);
		if(i_depth ==0) {
			for(int i=0;i<8;i++) {
				if(aW_wordNodes[i] == null) {
					aW_wordNodes[i] = W_node;
					W_node.W_parent = this;
					W_node.aW_wordNodes[parentNumber(i)] = this;
					Point p_point = createPoint(i, i_width, i_height);
					W_node.r_rect.x = p_point.x;
					W_node.r_rect.y = p_point.y;
					
					if(i == 7) {
						i_depth++;
						this.addDepth();
					}
					return W_node;
				}
			}
			i_depth++;
			this.addDepth();
			System.out.println("Problem");
			return findLowest().addNode(i_width, i_height);
		}
		else {
			return findLowest().addNode(i_width, i_height);
		}
	}
	
	WordNode findLowest() {
		WordNode w_temp = this.aW_wordNodes[0];
		
		for(int i = 1; i<8;i++) {
			if(this.aW_wordNodes[i].i_depth < w_temp.i_depth) {
				w_temp = this.aW_wordNodes[i];
			}
		}
		
		return w_temp;
	}
	
	void addDepth() {
		WordNode w_temp = this.W_parent;
		while(w_temp != null) {
			
			if(w_temp.aW_wordNodes[0] == w_temp.findLowest()) {
				w_temp.i_depth++;
			
				w_temp= w_temp.W_parent;
			}
			else {
				w_temp = null;
			}
		}
	}
	
	int parentNumber(int i_number) {
		int i_parentNumber = i_number - 4;
		if(i_parentNumber < 0) {
			i_parentNumber = 8+i_parentNumber;
		}
		return i_parentNumber;
	}



	Point createPoint(int i_direction, int i_width, int i_height) {
		Point p_point = new Point();;
		
		if(i_direction == 0) {
			p_point.x = this.r_rect.x;
			p_point.y = this.r_rect.y - i_height;
		}
		else if(i_direction == 1){
			p_point.y = this.r_rect.y - i_height;
			p_point.x = this.r_rect.x + this.r_rect.width/2;
		}
		else if(i_direction==2) {
			p_point.x = this.r_rect.x + this.r_rect.width/2;
			p_point.y = this.r_rect.y;
		}
		else if(i_direction == 3){
			p_point.x = this.r_rect.x+ this.r_rect.width/2;
			p_point.y = this.r_rect.y +this.r_rect.height;
		}
		else if(i_direction == 4){
			p_point.x = this.r_rect.x;
			p_point.y = this.r_rect.y + this.r_rect.height;
		}
		else if(i_direction == 5){
			p_point.y = this.r_rect.y + this.r_rect.height;
			p_point.x = this.r_rect.x - i_width;
		}
		else if(i_direction == 6){
			p_point.x = this.r_rect.x - i_width;
			p_point.y = this.r_rect.y;
		}
		else if(i_direction == 7){
			p_point.y = this.r_rect.y - i_height;
			p_point.x = this.r_rect.x - i_width;
		}
		
		return p_point;
	}
}


public class Artist {
	private static int i_MAX;
	private static int i_SIZE;
	private static float f_SCALER;
	private static int i_WIDTH = 1920;
	private static int i_HEIGHT = 1080;
	private static Rectangle[] aR_RECTANGLES;
	private static int i_rectangles = 0;
	private static int i_direction =0;
	//private static Point[] aP_ENDPOINTS;
	
	public static void init(Map<String, Integer> m_words, int i_maxWords) {
		i_MAX = (int) m_words.values().toArray()[0];
		i_SIZE = m_words.size();
		if(i_SIZE > i_maxWords) {
			i_SIZE = i_maxWords;
		}
		//System.out.println(i_MAX + " : " + i_SIZE);
		//aR_RECTANGLES = new Rectangle[i_SIZE];
		//aP_ENDPOINTS = new Point[i_SIZE];
		
		f_SCALER = (i_SIZE / i_HEIGHT)*15;
	}
	
	public static void draw(Map<String, Integer> m_words, int i_maxWords) throws IOException {
		
		BufferedImage image = new BufferedImage(i_WIDTH, i_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.red);
		
		AffineTransform affineTransform = new AffineTransform();
		
		List<String> l_keys = new ArrayList<>(m_words.keySet());
		List<Integer> l_values = new ArrayList<>(m_words.values());
		//int i_x = 20;
		//int i_y = 150;
		
		Font fo_font = new Font(Font.SANS_SERIF, Font.BOLD, (int)((float)l_values.get(0) / f_SCALER)).deriveFont(affineTransform);
		//FontMetrics fm_metrics = Toolkit.getDefaultToolkit().getFontMetrics(fo_font);
		graphics.setFont(fo_font);
		
		WordNode W_wordNode = new WordNode(new Rectangle(i_WIDTH/2, i_HEIGHT/2, l_keys.get(0).length() * fo_font.getSize(), fo_font.getSize()));
		//Rectangle r_rect = findSpace(l_keys.get(0), fo_font);
		
		graphics.drawString(l_keys.get(0),W_wordNode.r_rect.x, W_wordNode.r_rect.y+fo_font.getSize());
		
		for(int i = 1; (i < l_keys.size()) && (i < i_maxWords); i++) {
			fo_font = new Font(Font.SANS_SERIF, Font.BOLD, (int)((float)l_values.get(i) / f_SCALER)).deriveFont(affineTransform);
			//FontMetrics fm_metrics = Toolkit.getDefaultToolkit().getFontMetrics(fo_font);
			graphics.setFont(fo_font);
			graphics.setColor(new java.awt.Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
			//Rectangle r_rect = findSpace(l_keys.get(i), fo_font);
			WordNode w_temp = W_wordNode.addNode(l_keys.get(i).length() * fo_font.getSize(), fo_font.getSize());
			graphics.drawString(l_keys.get(i), w_temp.r_rect.x , w_temp.r_rect.y+fo_font.getSize());
			//i_y += (int)((float)l_values.get(i) / f_SCALER);
			//if(i_y > 1080) {
				//i_y = 50;
				//i_x += 250;
			//}
			
		}
		
		
		graphics.dispose();
		ImageIO.write(image, "png", new File("image.png"));
	}
	
	private static Rectangle findSpace(String s_word, Font f_font) {
		Rectangle r_rect = new Rectangle(i_WIDTH/2, i_HEIGHT/2, s_word.length() * f_font.getSize(), f_font.getSize());
		
		
		
		for(int i=0;i<i_rectangles;i++) {

			if(aR_RECTANGLES[i].contains(r_rect)) {
				if(i_direction == 0) {
					r_rect.y = aR_RECTANGLES[i].y - r_rect.height;
				}
				else if(i_direction == 1){
					r_rect.y = aR_RECTANGLES[i].y - r_rect.height;
					r_rect.x = aR_RECTANGLES[i].x + aR_RECTANGLES[i].width;
				}
				else if(i_direction==2) {
					r_rect.x = aR_RECTANGLES[i].x + aR_RECTANGLES[i].width;
				}
				else if(i_direction == 3){
					r_rect.x = aR_RECTANGLES[i].x + aR_RECTANGLES[i].width;
				}
				else if(i_direction == 4){
					r_rect.y = aR_RECTANGLES[i].y + aR_RECTANGLES[i].height;
				}
				else if(i_direction == 5){
					r_rect.y = aR_RECTANGLES[i].y + aR_RECTANGLES[i].height;
					r_rect.x = aR_RECTANGLES[i].x - r_rect.width;
				}
				else if(i_direction == 6){
					r_rect.x = aR_RECTANGLES[i].x - r_rect.width;
				}
				else if(i_direction == 7){
					r_rect.y = aR_RECTANGLES[i].y - r_rect.height;
					r_rect.x = aR_RECTANGLES[i].x - r_rect.width;
				}
			}
			
		}
		
		i_direction++;
		
		if(i_direction > 7) {
			i_direction =0;
		}
		aR_RECTANGLES[i_rectangles] = r_rect;
		i_rectangles++;
		
		return r_rect;
	}
}
