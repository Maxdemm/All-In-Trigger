package com.gambleknight.view.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ExplosionEffect {
    private Vector2 position;
    private float radius;
    private float maxRadius = 50;
    private float duration = 0.3f;
    private float elapsed = 0;
    private boolean isActive = true;
    private ShapeRenderer shapeRenderer;

    public ExplosionEffect(float x, float y) {
        position = new Vector2(x, y);
        radius = 0;
        shapeRenderer = new ShapeRenderer();
    }

    public void update(float deltaTime) {
        elapsed += deltaTime;
        radius = (elapsed / duration) * maxRadius;

        if (elapsed >= duration) {
            isActive = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (!isActive) return;

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        float alpha = 1 - (elapsed / duration);
        shapeRenderer.setColor(1, 0.5f, 0, alpha);
        shapeRenderer.circle(position.x, position.y, radius);

        shapeRenderer.end();
    }

    public boolean isActive() { return isActive; }
    public Vector2 getPosition() { return position; }
    public float getRadius() { return radius; }

    public void dispose() {
        shapeRenderer.dispose();
    }
}

