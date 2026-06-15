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
        // shadow
        shapeRenderer.setColor(0, 0, 0, 0.25f);
        shapeRenderer.ellipse(x + width/2f, y - 6, width * 0.9f, 10);

        // body base
        if (type.equals("melee")) {
            // beefy rounded melee enemy
            shapeRenderer.setColor(0.8f, 0.15f, 0.15f, 1f);
            shapeRenderer.ellipse(x + width/2f, y + height/2f, width * 0.9f, height * 0.9f);
            // armor plate
            shapeRenderer.setColor(0.5f, 0.1f, 0.1f, 1f);
            shapeRenderer.rect(x + width*0.15f, y + height*0.55f, width*0.7f, height*0.25f);
            // eyes
            float eyeY = y + height*0.62f;
            float eyeCX = x + width*0.5f;
            float eyeOffset = width * 0.12f;
            shapeRenderer.setColor(1f, 1f, 0.2f, 1f);
            shapeRenderer.circle(eyeCX - eyeOffset, eyeY, 3.5f);
            shapeRenderer.circle(eyeCX + eyeOffset, eyeY, 3.5f);
        } else {
            // ranged — sleeker
            shapeRenderer.setColor(1f, 0.55f, 0f, 1f);
            shapeRenderer.ellipse(x + width/2f, y + height/2f, width * 0.8f, height * 0.6f);
            // headlight
            shapeRenderer.setColor(1f, 1f, 1f, 1f);
            shapeRenderer.circle(x + width*0.5f, y + height*0.62f, 2.5f);
            // backpack or weapon mount
            shapeRenderer.setColor(0.85f, 0.45f, 0f, 1f);
            shapeRenderer.rect(x + width*0.65f, y + height*0.35f, width*0.2f, height*0.35f);
        }
    }
}
