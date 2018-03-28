package com.archer.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class ValiImg {
	
		static int width = 220, height = 50;	
		static int FontSize=40;
		static int padding_x=20;
		static int charNum=4;
		static int char_gap=(width-2*padding_x-charNum*FontSize)/(charNum-1)+FontSize;
		static int char_y=(height+FontSize)/2-5;
	
		private InputStream in;
		private String authCode;
		public InputStream getIn() {
			try {
				BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
				
				Graphics g = image.getGraphics();
				Random random = new Random();
				g.setColor(getRandColor(200, 250));				
				g.fillRect(0, 0, width, height);
				g.setFont(new Font("Arial", Font.PLAIN, FontSize));		
				g.setColor(getRandColor(160, 200));
				for (int i = 0; i < 200; i++) {					
					int x = random.nextInt(width);
					int y = random.nextInt(height);
					int xl = random.nextInt(12);
					int yl = random.nextInt(12);
					g.drawLine(x, y, x + xl, y + yl);
				}
				char[] ch = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789".toCharArray();
				int index = 0;
				StringBuffer str = new StringBuffer();
				for (int j = 0; j < charNum; j++) {
					index = random.nextInt(ch.length);
					g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
					g.drawString(ch[index]+"",padding_x+char_gap*j, char_y);		
					str.append(ch[index]);
				}
				authCode=str.toString();
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(image, "jpeg", os);
				g.dispose();
				byte[] temp=os.toByteArray();
				in=new ByteArrayInputStream(temp);
		        os.close();
	        
			} catch (Exception e) {
					e.printStackTrace();
			}
			return in;
		}


		public String getAuthCode() {
			return authCode;
		}

		
		public Color getRandColor(int fc, int bc) {
		 	Random random = new Random();
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
