package eärtlandRPG.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet2 {
	
	private BufferedImage spritesheet2;
	
	public Spritesheet2(String path) {
		try {
			spritesheet2 = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet2.getSubimage(x, y, width, height); 
		}

}
