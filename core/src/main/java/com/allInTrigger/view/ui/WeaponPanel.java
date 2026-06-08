package com.allInTrigger.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class WeaponPanel {
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    public WeaponPanel() {
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(1.2f);
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        float x = 1100;
        float y = 30;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.05f, 0.05f, 0.08f, 0.75f);
        shapeRenderer.rect(x, y, 150, 70);

        // Силует бластера
        shapeRenderer.setColor(1f, 0.5f, 0f, 0.9f);
        shapeRenderer.rect(x + 40, y + 40, 70, 10);
        shapeRenderer.rect(x + 50, y + 25, 12, 15);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "30 / 120", x + 45, y + 20);
        batch.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }
}
