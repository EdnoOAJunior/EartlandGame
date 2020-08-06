package eärtlandRPG.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import eärtlandRPG.world.Camera;

public class ArrowShoot extends Entity{
	
	private int dx;
	private int dy;
	private double spd;

	public ArrowShoot(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 2, 2);
	}
	
}
