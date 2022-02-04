package asteroid.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import asteroid.Game;
import asteroid.WorldPanel;
import asteroid.util.Vector2;

public class Player extends Entity {

    private static final double Rotation = -Math.PI / 2.0;
    private static final double maxVelocity = 0.9;
    private static final double ROTATION_SPEED = 0.052;
        private static final int FIRE_RATE = 5;
    private boolean thrustPressed;
    private boolean rotateLeftPressed;
    private boolean rotateRightPressed;
    private boolean firePressed;
    private boolean firingEnabled;
        private int consecutiveShots;
        private int fireCooldown;
    private int animationFrame;
    private List<Bullet> bullets;

    public Player() {
        super(new Vector2(WorldPanel.PlayBoard / 2.0, WorldPanel.PlayBoard / 2.0), new Vector2(0.0, 0.0), 10.0, 0);
        this.bullets = new ArrayList<>();
        this.rotation = Rotation;
        this.thrustPressed = false;
        this.rotateLeftPressed = false;
        this.rotateRightPressed = false;
        this.firePressed = false;
        this.firingEnabled = true;
        this.fireCooldown = 0;
        this.animationFrame = 0;
    }

    public void setThrusting(boolean state) {
        this.thrustPressed = state;
    }

    public void setRotateLeft(boolean state) {
        this.rotateLeftPressed = state;
    }

    public void setRotateRight(boolean state) {
        this.rotateRightPressed = state;
    }

    public void setFiring(boolean state) {
        this.firePressed = state;
    }

    public void setFiringEnabled(boolean state) {
        this.firingEnabled = state;
    }

    public void reset() {
        this.rotation = Rotation;
        position.set(WorldPanel.PlayBoard / 2.0, WorldPanel.PlayBoard / 2.0);
        velocity.set(0.0, 0.0);
        bullets.clear();
    }

    @Override
    public void update(Game game) {
        super.update(game);
        this.animationFrame++;
        if (rotateLeftPressed != rotateRightPressed) {
            rotate(rotateLeftPressed ? -ROTATION_SPEED : ROTATION_SPEED);
        }
        if (thrustPressed) {
            velocity.add(new Vector2(rotation).scale(0.0405));
            if (velocity.getLengthSquared() >= maxVelocity * maxVelocity) {
                velocity.normalize().scale(maxVelocity);
            }
        }
        if (velocity.getLengthSquared() != 0.0) {
            velocity.scale(0.950);
        }
        Iterator<Bullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            if (bullet.needsRemoval()) {
                iter.remove();
            }
        }

        this.fireCooldown--;
        if (firingEnabled && firePressed && fireCooldown <= 0) {
            this.fireCooldown = FIRE_RATE;
            Bullet bullet = new Bullet(this, rotation);
            bullets.add(bullet);
            game.registerEntity(bullet);
            this.consecutiveShots++;
        }
        if (firePressed == true) {
            Sound.playSoundEffect(Sound.laser);
        }
    }

    @Override
    public void handleCollision(Game game, Entity other) {
        if (other.getClass() == Asteroid.class) {
            game.die();
        }
    }

    @Override
    public void draw(Graphics2D g, Game game) {
        if (!game.isPlayerInvulnerable() || game.isPaused() || animationFrame % 20 < 10) {
            g.drawLine(-10, -8, 10, 0);
            g.drawLine(-10, 8, 10, 0);
            g.drawLine(-6, -6, -6, 6);
            if (!game.isPaused() && thrustPressed) {
                g.drawLine(-6, -6, -12, 0);
                g.drawLine(-6, 6, -12, 0);
            }
        }
    }
}