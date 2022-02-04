package asteroid.entity;

import java.awt.Graphics2D;
import java.util.Random;

import asteroid.Game;
import asteroid.WorldPanel;
import asteroid.util.Vector2;

public class Asteroid extends Entity {

    private static final double mRotation = 0.0075;
    private static final double Rotation = 0.0175 - mRotation;
    private static final double mVelocity = 0.75;
    private static final double diffVelocity = 1.65 - mVelocity;
    private static final double maxDistance = WorldPanel.PlayBoard / 2.0;
    private static final double diffDistance = maxDistance - 200.0;
    private AsteroidSize size;
    private double rotationSpeed;

    public Asteroid(Random random) {
        super(calculatePosition(random), calculateVelocity(random), AsteroidSize.Large.radius, AsteroidSize.Large.killValue);
        this.rotationSpeed = -mRotation + (random.nextDouble() * Rotation);
        this.size = AsteroidSize.Large;
    }

    public Asteroid(Asteroid parent, AsteroidSize size, Random random) {
        super(new Vector2(parent.position), calculateVelocity(random), size.radius, size.killValue);
        this.rotationSpeed = mRotation + (random.nextDouble() * Rotation);
        this.size = size;
        for (int i = 0; i < 10; i++) {
            update(null);
        }
    }

    private static Vector2 calculatePosition(Random random) {
        Vector2 vec = new Vector2(WorldPanel.PlayBoard / 2.0, WorldPanel.PlayBoard / 2.0);
        return vec.add(new Vector2(random.nextDouble() * Math.PI * 2).scale(200.0 + random.nextDouble() * diffDistance));
    }

    private static Vector2 calculateVelocity(Random random) {
        return new Vector2(random.nextDouble() * Math.PI * 2).scale(mVelocity + random.nextDouble() * diffVelocity);
    }

    @Override
    public void update(Game game) {//Rotate the image each frame.
        super.update(game);
        rotate(rotationSpeed);
    }

    @Override
    public void draw(Graphics2D g, Game game) {//Draw the Asteroid.
        g.drawPolygon(size.polygon);
    }

    @Override
    public void handleCollision(Game game, Entity other) {
        if (other.getClass() != Asteroid.class) {
            if (size != AsteroidSize.Small) {
                AsteroidSize spawnSize = AsteroidSize.values()[size.ordinal() - 1];
                for (int i = 0; i < 2; i++) {
                    game.registerEntity(new Asteroid(this, spawnSize, game.getRandom()));
                }
            }
            flagForRemoval();
            game.addScore(getKillScore());
        }
    }
}