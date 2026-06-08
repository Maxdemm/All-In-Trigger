package com.gambleknight.view.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class CoinEffectView {
    private Vector2 position;
    private Vector2 velocity;
    private float size = 4;
    private float duration = 0.5f;
    private float elapsed = 0;
    private boolean isActive = true;
    private ShapeRenderer shapeRenderer;

    public CoinEffectView(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(
            (float) (Math.random() - 0.5f) * 100,
            (float) (Math.random() + 0.5f) * 150
        );
        shapeRenderer = new ShapeRenderer();
    }

    public void update(float deltaTime) {
        elapsed += deltaTime;
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        if (elapsed >= duration) {
            isActive = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (!isActive) return;

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float alpha = 1 - (elapsed / duration);
        shapeRenderer.setColor(1, 0.84f, 0, alpha);
        shapeRenderer.circle(position.x, position.y, size);

        shapeRenderer.end();
    }

    public boolean isActive() { return isActive; }

    public void dispose() {
        shapeRenderer.dispose();
    }
}

