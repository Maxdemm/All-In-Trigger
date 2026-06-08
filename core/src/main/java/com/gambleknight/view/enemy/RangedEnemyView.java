package com.gambleknight.view.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RangedEnemyView {
    private ShapeRenderer shapeRenderer;

    public RangedEnemyView() {
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw ranged enemy - orange
        shapeRenderer.setColor(1f, 0.5f, 0f, 1f);
        shapeRenderer.rect(500, 300, 32, 32);

        // Draw calm eyes
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(510, 322, 2);
        shapeRenderer.circle(522, 322, 2);

        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
