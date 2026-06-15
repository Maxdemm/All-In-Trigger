package com.allInTrigger.view.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    public float x, y;
    public float vx, vy;
    public float speed;
    public float damage;
    public float lifetime = 5f;
    public float age = 0;
    public String weaponType;
    private static final float WIDTH = 4f;
    private static final float HEIGHT = 4f;

    public Bullet(float x, float y, float angle, float speed, float damage, String weaponType) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;
        this.weaponType = weaponType;

        // Convert angle to radians and calculate velocity
        float rad = (float) Math.toRadians(angle);
        this.vx = (float) Math.cos(rad) * speed;
        this.vy = (float) Math.sin(rad) * speed;
    }

    public void update(float delta) {
        x += vx * delta;
        y += vy * delta;
        age += delta;
    }

    public Rectangle getBounds() {
        return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    }

    public boolean isAlive() {
        return age < lifetime;
    }

    public void render(ShapeRenderer shapeRenderer) {
        Color color = Color.YELLOW;
        float alpha = 1f;

        if (weaponType.equals("shotgun")) {
            color = new Color(1f, 0.6f, 0f, alpha);
        } else if (weaponType.equals("rifle")) {
            color = new Color(0f, 1f, 0.5f, alpha);
        } else if (weaponType.equals("minigun")) {
            color = new Color(1f, 0.2f, 0.2f, alpha);
        }

        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, 3f);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(x, y, 1.5f);
    }
}

