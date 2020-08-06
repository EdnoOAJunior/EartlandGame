package eärtlandRPG.entities;

//import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import eärtlandRPG.main.Game;
import eärtlandRPG.world.Camera;
import eärtlandRPG.world.World;

public class Enemy extends Entity {
	
	private double speed = 1;
	
	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;
	
	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 3;
	private boolean moved = false;
	
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	
	private BufferedImage[] rightGoblin;
	private BufferedImage[] leftGoblin;
	private BufferedImage[] upGoblin;
	private BufferedImage[] downGoblin;
	

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		
		rightGoblin = new BufferedImage[4];
		for(int i = 0; i < 4; i++) {
			 rightGoblin[i] = Game.spritesheet.getSprite(32 + (i*16), 64, 16, 16);
		}
		leftGoblin = new BufferedImage[4];
		for(int i = 0; i < 4; i++) {
			 leftGoblin[i] = Game.spritesheet.getSprite(32 + (i*16), 80, 16, 16);
		 }
		downGoblin = new BufferedImage[4];
		for(int i = 0; i < 4; i++) {
			 downGoblin[i] = Game.spritesheet.getSprite(32 + (i*16), 96, 16, 16);
		 }
		upGoblin = new BufferedImage[4];
		for(int i = 0; i < 4; i++) {
			 upGoblin[i] = Game.spritesheet.getSprite(32 + (i*16), 112, 16, 16);
		 }
		

		
	}
	
	public void tick() {
		maskx = 8; 
		masky = 8; 
		maskw = 7; 
		maskh = 12;
		moved = false;
		//if(Game.rand.nextInt(100) < 50) {
			if(this.isCollidingWithPlayer() == false) {
			if(x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
					&& !isColliding((int)(x+speed), this.getY())){
				moved = true;
				dir = right_dir;
				x+=speed;
				if(x > Game.player.getX()) {
					x = Game.player.getX();
				}
				
			}else if(x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
					&& !isColliding((int)(x-speed), this.getY())){
				moved = true;
				dir = left_dir;
				x-=speed;
				if(x < Game.player.getX()) {
					x = Game.player.getX();
				}
			}
			if(y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
					&& !isColliding(this.getX(), (int)(y+speed))){
				moved = true;
				dir = down_dir;
				y+=speed;
				if(y > Game.player.getY()) {
					y = Game.player.getY();
				}
			}else if(y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))
					&& !isColliding(this.getX(), (int)(y-speed))){
				moved = true;
				dir = up_dir;
				y-=speed;
				if(y < Game.player.getY()) {
					y = Game.player.getY();
				}
			  }
			}
			
			else {
				//Estamos colidindo
				if(Game.rand.nextInt(100) < 10) {
				Game.player.life--;
				Game.player.isDamaged = true;
				System.out.println("Vida: " + Game.player.life);
				}
			}
			
			if(moved) {
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index > maxIndex) {
						index = 0;
					}
				}
			}
	}
	
	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle Player = new Rectangle(Game.player.getX(), Game.player.getY(),16,16);
		
		return enemyCurrent.intersects(Player);
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX()+ maskx, e.getY()+ masky, maskw, maskh);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		if(dir == right_dir) {
			g.drawImage(rightGoblin[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if(dir == left_dir) {
			g.drawImage(leftGoblin[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if(dir == down_dir) {
			g.drawImage(downGoblin[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else{
			g.drawImage(upGoblin[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}

}
