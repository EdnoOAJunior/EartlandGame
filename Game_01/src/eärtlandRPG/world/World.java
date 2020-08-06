package eärtlandRPG.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import eärtlandRPG.entities.*;
import eärtlandRPG.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	public static final int PINE_SIZE = 32;
	public static final int OAK_SIZE = 32;
	
	public World(String path) {
	
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16, Tile.TILE_FLOOR);
					if(pixelAtual == 0xFF000000/*Preto*/) {
						//Floor
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16,yy*16, Tile.TILE_FLOOR);
					}else if(pixelAtual == 0xFFFFFFFF/*Branco*/){
						//Wall
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*16,yy*16, Tile.TILE_WALL);
					}else if(pixelAtual == 0xFF007F0E/*Verde escuro*/) {
						//Pine Tree
						Pine pine = new Pine(xx*16, yy*16, 32, 32, Entity.PINE_EN);
						Game.entities.add(pine);
						pine.setMask(12, 23, 9, 8);
					}else if(pixelAtual == 0xFF00FF21/*Verde claro*/) {
						//Oak Tree
						Oak oak = new Oak(xx*16, yy*16, 32, 32, Entity.OAK_EN);
						Game.entities.add(oak);
						oak.setMask(12, 22, 9, 9);
					}else if(pixelAtual == 0xFF1928FF/*Azul*/) {
						//Player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if(pixelAtual == 0xFFFF1500/*Vermelho*/) {
						//Enemy
						Enemy en = new Enemy(xx*16, yy*16, 16, 16, Entity.GOBLIN_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
						
						
					}else if(pixelAtual == 0xFFFF6A00/*Laranja*/) {
						//Arco
						Weapon arco = new Weapon(xx*16, yy*16, 16, 16, Entity.WEAPON_EN);
						Game.entities.add(arco);
						arco.setMask(7, 3, 3, 8);
					}else if(pixelAtual == 0xFFFF59EB/*Magenta*/) {
						//Lifepack
						Lifepack pack = new Lifepack(xx*16, yy*16, 16, 16, Entity.LIFEPACK_EN);
						Game.entities.add(pack);
						pack.setMask(2, 6, 12, 10);
					}else if(pixelAtual == 0xFFFFEF16/*Amarelo*/) {
						//Ammo
						Ammo ammo = new Ammo(xx*16, yy*16, 16, 16, Entity.AMMO_EN);
						Game.entities.add(ammo);
						ammo.setMask(8, 5, 1, 8);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;
		
		return !(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile ||
				(tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}

}
