package com.gambleknight.view.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnemyView {
    private ShapeRenderer shapeRenderer;

    public EnemyView() {
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // --- ВОРОГ 1 (Ближній бій) ---
        float e1X = 250, e1Y = 180;
        shapeRenderer.setColor(0, 0, 0, 0.3f); // Тінь
        shapeRenderer.ellipse(e1X + 2, e1Y - 4, 28, 10);
        shapeRenderer.setColor(Color.RED); // Тіло
        shapeRenderer.rect(e1X, e1Y, 32, 32);
        shapeRenderer.setColor(Color.YELLOW); // Очі
        shapeRenderer.circle(e1X + 10, e1Y + 22, 3);
        shapeRenderer.circle(e1X + 22, e1Y + 22, 3);

        // --- ВОРОГ 2 (Дальній бій) ---
        float e2X = 500, e2Y = 220;
        shapeRenderer.setColor(0, 0, 0, 0.3f); // Тінь
        shapeRenderer.ellipse(e2X + 2, e2Y - 4, 28, 10);
        shapeRenderer.setColor(1f, 0.5f, 0f, 1f); // Тіло
        shapeRenderer.rect(e2X, e2Y, 32, 32);
        shapeRenderer.setColor(Color.WHITE); // Очі
        shapeRenderer.circle(e2X + 10, e2Y + 22, 2);
        shapeRenderer.circle(e2X + 22, e2Y + 22, 2);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
