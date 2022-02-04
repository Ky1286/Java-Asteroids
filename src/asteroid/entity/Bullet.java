package asteroid.entity;

import java.awt.Graphics2D;
import asteroid.Game;
import asteroid.util.Vector2;

public class Bullet extends Entity {

    public Bullet(Entity shot, double angle) {
        super(new Vector2(shot.position), new Vector2(angle).scale(6.75), 2.0, 0);
    }
    
    @Override
    public void handleCollision(Game game, Entity other) {
        if (other.getClass() != Player.class) {
            flagForRemoval();
        }
    }

    @Override
    public void draw(Graphics2D g, Game game) {
        g.drawOval(-1, -1, 2, 2);
    }
}