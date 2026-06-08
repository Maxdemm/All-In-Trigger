package com.allInTrigger.view.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PauseMenuUI {
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private boolean isVisible = false;

    public PauseMenuUI() {
        font = new BitmapFont();
        font.getData().setScale(2f);
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        if (!isVisible) return;

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Semi-transparent overlay
        shapeRenderer.setColor(0, 0, 0, 0.7f);
        shapeRenderer.rect(0, 0, 800, 480);

        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "PAUSED", 350, 300);

        font.getData().setScale(1f);
        font.draw(batch, "Press P to resume", 300, 250);
        batch.end();
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
    }
}

