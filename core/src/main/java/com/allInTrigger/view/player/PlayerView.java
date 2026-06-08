package com.allInTrigger.view.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerView {
    private ShapeRenderer shapeRenderer;

    public PlayerView() {
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch, float targetX, float targetY, float weaponAngle) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // тінь
        shapeRenderer.setColor(0, 0, 0, 0.3f);
        shapeRenderer.ellipse(targetX + 2, targetY - 4, 28, 10);
        // тіло
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(targetX, targetY, 32, 36);

        //маска
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(targetX, targetY + 16, 32, 14);

        //  очі
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(targetX + 6, targetY + 22, 6, 4);
        shapeRenderer.rect(targetX + 20, targetY + 22, 6, 4);
        //  зброя
        shapeRenderer.setColor(Color.GRAY);
        float centerX = targetX + 16;
        float centerY = targetY + 16;

        shapeRenderer.rect(centerX, centerY - 3, 0, 3, 22, 8, 1f, 1f, weaponAngle);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
