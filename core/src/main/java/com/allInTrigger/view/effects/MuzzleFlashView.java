package com.allInTrigger.view.effects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MuzzleFlashView {
    private Vector2 position;
    private float size = 15;
    private float duration = 0.05f;
    private float elapsed = 0;
    private boolean isActive = true;
    private ShapeRenderer shapeRenderer;

    public MuzzleFlashView(float x, float y) {
        position = new Vector2(x, y);
        shapeRenderer = new ShapeRenderer();
    }

    public void update(float deltaTime) {
        elapsed += deltaTime;

        if (elapsed >= duration) {
            isActive = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (!isActive) return;

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(1, 1, 0, 0.8f);
        shapeRenderer.circle(position.x, position.y, size);

        shapeRenderer.end();
    }

    public boolean isActive() { return isActive; }

    public void dispose() {
        shapeRenderer.dispose();
    }
}

