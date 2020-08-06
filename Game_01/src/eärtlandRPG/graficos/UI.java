package eärtlandRPG.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

//import eärtlandRPG.entities.Player;
import eärtlandRPG.main.Game;

public class UI {
	
	
	public void render(Graphics g){
		
		g.setColor(Color.black);
		g.fillRect(5, 5, 50, 7);
		g.setColor(Color.red);
		g.fillRect(5, 5,(int)((Game.player.life/Game.player.maxlife)*50), 7);
		//g.setColor(Color.green);
		//g.setFont(new Font("arial", Font.BOLD, 7));
		//g.drawString("Life: " + (int)Player.life + "/" + (int)Player.maxlife, 8, 11);
		
		if(Game.player.life <= 0) {
			g.setColor(Color.red);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("GAME OVER", 29, 90);
			g.drawString("YOU MF!!", 53, 115);
		}
		
		
	}

}
