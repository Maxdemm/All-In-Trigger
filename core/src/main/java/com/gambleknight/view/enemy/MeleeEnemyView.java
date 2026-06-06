package com.gambleknight.view.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MeleeEnemyView {
    private ShapeRenderer shapeRenderer;

    public MeleeEnemyView() {
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw melee enemy - solid red
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(200, 300, 32, 32);

        // Draw aggressive eyes
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(210, 322, 3);
        shapeRenderer.circle(222, 322, 3);

        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
