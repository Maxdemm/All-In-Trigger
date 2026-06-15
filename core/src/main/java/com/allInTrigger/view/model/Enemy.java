package com.allInTrigger.view.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    public float x, y;
    public float width = 32f;
    public float height = 32f;
    public float speed;
    public float hp = 20f;
    public String type;

    public Enemy(float x, float y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.speed = type.equals("melee") ? 110f : 80f;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void update(float delta, float playerX, float playerY) {
        Vector2 direction = new Vector2(playerX - this.x, playerY - this.y);
        float distance = direction.len();

        // Enemies will follow player only when relatively close
        if (distance < 450f && distance > 5f) {
            direction.nor();
            this.x += direction.x * speed * delta;
            this.y += direction.y * speed * delta;
        }
    }

    public void updateInRoom(float delta, float playerX, float playerY, Rectangle roomBounds) {
        Vector2 direction = new Vector2(playerX - this.x, playerY - this.y);
        float distance = direction.len();

        // Only chase if player is in room and reasonably close
        if (distance < 450f && distance > 5f) {
            direction.nor();
            float newX = this.x + direction.x * speed * delta;
            float newY = this.y + direction.y * speed * delta;

            // Keep enemy within room bounds
            if (roomBounds.contains(newX + width / 2, newY + height / 2)) {
                this.x = newX;
                this.y = newY;
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        //  тінь
        shapeRenderer.setColor(0, 0, 0, 0.3f);
        shapeRenderer.ellipse(x + 2, y - 4, 28, 10);

        if (type.equals("melee")) {
            // ближній ворог
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.circle(x + 10, y + 22, 3);
            shapeRenderer.circle(x + 22, y + 22, 3);
        } else {
            // дальній
            shapeRenderer.setColor(1f, 0.5f, 0f, 1f);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(x + 10, y + 22, 2.5f);
            shapeRenderer.circle(x + 22, y + 22, 2.5f);
        }
    }
}
