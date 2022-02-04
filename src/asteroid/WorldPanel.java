package asteroid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import javax.swing.JPanel;

import asteroid.entity.Entity;
import asteroid.util.Vector2;

public class WorldPanel extends JPanel {
	public static final int PlayBoard = 550;
	private static final Font mainFont = new Font("Dialog", Font.PLAIN, 25);
	private static final Font subFont = new Font("Dialog", Font.PLAIN, 15);
	private Game game;
	
	public WorldPanel(Game game) {
		this.game = game;

		setPreferredSize(new Dimension(PlayBoard, PlayBoard));
		setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		g2d.setColor(Color.WHITE); 
		AffineTransform identity = g2d.getTransform();
		Iterator<Entity> iter = game.getEntities().iterator();
		while(iter.hasNext()) {
			Entity entity = iter.next();
			if(entity != game.getPlayer() || game.canDrawPlayer()) {
				Vector2 pos = entity.getPosition();
				drawEntity(g2d, entity, pos.x, pos.y);
				g2d.setTransform(identity);
				double radius = entity.getCollisionRadius();
				double x = (pos.x < radius) ? pos.x + PlayBoard
						: (pos.x > PlayBoard - radius) ? pos.x - PlayBoard : pos.x;
				double y = (pos.y < radius) ? pos.y + PlayBoard
						: (pos.y > PlayBoard - radius) ? pos.y - PlayBoard : pos.y;
				if(x != pos.x || y != pos.y) {
					drawEntity(g2d, entity, x, y);
					g2d.setTransform(identity);
				}
			}	
		}
		if(!game.isGameOver()) {
			g.drawString("Score: " + game.getScore(), 10, 15);
		}
		if(game.isGameOver()) {
			drawTextCentered("Game Over", mainFont, g2d, -25);
			drawTextCentered("Final Score: " + game.getScore(), subFont, g2d, 10);
		} else if(game.isPaused()) {
			drawTextCentered("Paused", mainFont, g2d, -25);
		} else if(game.isShowingLevel()) {
			drawTextCentered("Level: " + game.getLevel(), mainFont, g2d, -25);
		}
		
		//Draw a ship for each life the player has remaining.
		g2d.translate(15, 30);
		g2d.scale(0.85, 0.85);
		for(int i = 0; i < game.getLives(); i++) {
			g2d.drawLine(-8, 10, 0, -10);
			g2d.drawLine(8, 10, 0, -10);
			g2d.drawLine(-6, 6, 6, 6);
			g2d.translate(30, 0);
		}
	}

	private void drawTextCentered(String text, Font font, Graphics2D g, int y) {
		g.setFont(font);
		g.drawString(text, PlayBoard / 2 - g.getFontMetrics().stringWidth(text) / 2, PlayBoard / 2 + y);
	}
	
	private void drawEntity(Graphics2D g2d, Entity entity, double x, double y) {
		g2d.translate(x, y);
		double rotation = entity.getRotation();
		if(rotation != 0.0f) {
			g2d.rotate(entity.getRotation());
		}
		entity.draw(g2d, game);
	}

}