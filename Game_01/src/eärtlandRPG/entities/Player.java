package eärtlandRPG.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import eärtlandRPG.graficos.Spritesheet;
import eärtlandRPG.main.Game;
import eärtlandRPG.world.Camera;
import eärtlandRPG.world.World;

public class Player extends Entity{
	
	public boolean right,left,up,down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 1.8;
	public double life = 100, maxlife = 100;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage damageRight;
	//private BufferedImage damageLeft;
	//private BufferedImage damageUpdown;
	
	private boolean hasBow = false;
	
	private int damageFrames = 0;
	
	public int ammo = 0;
	
	public boolean isDamaged = false;
	
	public boolean shoot = false;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		 
		 upPlayer = new BufferedImage[4];
		 downPlayer = new BufferedImage[4];
		 rightPlayer = new BufferedImage[4];
		 leftPlayer = new BufferedImage[4];
		 damageRight = Game.spritesheet.getSprite(0, 16, 16, 16);
		 //damageLeft = Game.spritesheet.getSprite(16, 16, 16, 16);
		// damageUpdown = Game.spritesheet.getSprite(0, 32, 16, 16);
		 for(int i = 0; i < 4; i++) {
			 rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		 }
		 for(int i = 0; i < 4; i++) {
			 leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
		 }
		 for(int i = 0; i < 4; i++) {
			 downPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 32, 16, 16);
		 }
		 for(int i = 0; i < 4; i++) {
			 upPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 48, 16, 16);
		 }
		 
		 
	}
	
	public void tick() {
		moved = false;
		if(up && World.isFree(this.getX(), (int)(y-speed))) {
			moved = true;
			dir = up_dir;
			y-=speed;
		}else if(down && World.isFree(this.getX(), (int)(y+speed))) {
			moved = true;
			dir = down_dir;
			y+=speed;
		}
		if(right && World.isFree((int)(x+speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}else if(left && World.isFree((int)(x-speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
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
		
		this.checkCollisionLifepack();
		this.checkCollisionAmmo();
		this.checkCollisionBow();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(life<=0) {
			Game.entities = new ArrayList<Entity>();
			Game.spritesheet = new Spritesheet("/spritesheet.png");
			Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16)); 
			Game.entities.add(Game.player);
			Game.enemies = new ArrayList<Enemy>();
			Game.world = new World ("/map.png");
			return;
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16- Game.HEIGHT);
	}
	
	
		public void checkCollisionBow() {
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity atual = Game.entities.get(i);
				if(atual instanceof Weapon) {
					if(Entity.isColliding(this, atual)) {
						hasBow = true;
						//System.out.println("Pegooo sem vergonha");
						Game.entities.remove(atual);
						}
					}	
				}
			}
	
		public void checkCollisionAmmo() {
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity atual = Game.entities.get(i);
				if(atual instanceof Ammo) {
					if(Entity.isColliding(this, atual)) {
						ammo++;
						Game.entities.remove(atual);
						}
					}	
				}
			}
	
		public void checkCollisionLifepack() {
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity atual = Game.entities.get(i);
				if(atual instanceof Lifepack) {
					if(Entity.isColliding(this, atual)) {
						life+=10;
						if(life > 100) {
							life = 100;
						}
						Game.entities.remove(atual);
						}
					}	
				}
			}


	
	public void render (Graphics g) {
		if(!isDamaged) {
			if(dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasBow) {
					g.drawImage(Entity.BOW_UP, this.getX()+4 - Camera.x, this.getY()+1 - Camera.y, null);
					//cima
				}
			}else if(dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasBow) {
					g.drawImage(Entity.BOW_DOWN, this.getX()+4 - Camera.x, this.getY()+2 - Camera.y, null);
					//baixo
				}
			}else if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasBow) {
					g.drawImage(Entity.BOW_RIGHT, this.getX()+2 - Camera.x, this.getY()+1 - Camera.y, null);
					//direita
				}
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasBow) {
					g.drawImage(Entity.BOW_LEFT, this.getX()-2 - Camera.x, this.getY()+1 - Camera.y, null);
					//esquerda
				}
			}
		}else {
			g.drawImage(damageRight, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
			
	}

}
