package com.gambleknight.view.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class HealthPack {
    public float x, y;
    public final float size = 20f;
    public boolean isCollected = false;
    private Rectangle bounds;

    public HealthPack(float x, float y) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, size, size);
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (isCollected) return;

        // Малюємо білий квадрат-основу
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x, y, size, size);

        // Малюємо червоний медичний хрест всередині
        shapeRenderer.setColor(Color.RED);
        // Горизонтальна лінія хреста
        shapeRenderer.rect(x + 3, y + 8, size - 6, 4);
        // Вертикальна лінія хреста
        shapeRenderer.rect(x + 8, y + 3, 4, size - 6);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
