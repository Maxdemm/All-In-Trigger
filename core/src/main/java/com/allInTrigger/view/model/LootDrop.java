package com.allInTrigger.view.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class LootDrop {
    public float x, y;
    public String type; // "coin", "health", "weapon_drop"
    public boolean collected = false;
    private static final float SIZE = 12f;

    public LootDrop(float x, float y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Rectangle getBounds() {
        return new Rectangle(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (type.equals("coin")) {
            shapeRenderer.setColor(1f, 0.84f, 0f, 1f);
            shapeRenderer.circle(x, y, 6f);
            shapeRenderer.setColor(1f, 1f, 0.5f, 1f);
            shapeRenderer.circle(x, y, 3f);
        } else if (type.equals("health")) {
            shapeRenderer.setColor(0.2f, 1f, 0.2f, 1f);
            shapeRenderer.rect(x - 4, y - 8, 8, 16);
            shapeRenderer.rect(x - 8, y - 4, 16, 8);
            shapeRenderer.setColor(1f, 1f, 1f, 1f);
            shapeRenderer.rect(x - 2, y - 6, 4, 12);
            shapeRenderer.rect(x - 6, y - 2, 12, 4);
        }
    }
}

