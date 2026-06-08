package com.gambleknight.view.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Coin {
    public float x, y;
    public final float RADIUS = 8f;
    public boolean isCollected = false;

    public Coin(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (isCollected) return;
        // Тінь монети
        shapeRenderer.setColor(0, 0, 0, 0.3f);
        shapeRenderer.ellipse(x - 4, y - 6, 10, 4);
        // Сама золота монета
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(x, y, RADIUS);
        shapeRenderer.setColor(0.9f, 0.7f, 0f, 1f); // Внутрішнє кільце декор
        shapeRenderer.circle(x, y, RADIUS - 2.5f);
    }
}
