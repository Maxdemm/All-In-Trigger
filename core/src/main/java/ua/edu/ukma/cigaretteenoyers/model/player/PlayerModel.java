package ua.edu.ukma.cigaretteenoyers.model.player;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Intersector;
import ua.edu.ukma.cigaretteenoyers.shared.Constants;

public class PlayerModel {
    public Vector2 position = new Vector2(10f, 5.6f);
    public Vector2 velocity = new Vector2();
    public Circle  hitbox = new Circle(10f, 5.6f, Constants.PLAYER_RADIUS);
    public float   angle = 0f;
    public boolean invincible = false;
    public float   iframeTimer = 0f;

    public void update(float delta) {
        position.add(velocity);
        hitbox.setPosition(position);

        if (invincible) {
            iframeTimer -= delta;
            if (iframeTimer <= 0) invincible = false;
        }
    }

    public void triggerIframes() {
        invincible = true;
        iframeTimer = Constants.IFRAMES_TIME;
    }

    public void resolveWallCollision(Rectangle wall) {
        float overlapLeft = (position.x + hitbox.radius) - wall.x;
        float overlapRight = (wall.x + wall.width) - (position.x - hitbox.radius);
        float overlapTop = (wall.y + wall.height) - (position.y - hitbox.radius);
        float overlapBottom = (position.y + hitbox.radius) - wall.y;

        float minX = Math.min(overlapLeft, overlapRight);
        float minY = Math.min(overlapTop, overlapBottom);

        if (minX < minY) {
            position.x += (overlapLeft < overlapRight) ? -overlapLeft : overlapRight;
        } else {
            position.y += (overlapBottom < overlapTop) ? -overlapBottom : overlapTop;
        }
        hitbox.setPosition(position);
    }
}
