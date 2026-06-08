package com.gambleknight.view.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class UpgradeUI {
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private boolean isVisible = false;

    public UpgradeUI() {
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        if (!isVisible) return;

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Background
        shapeRenderer.setColor(0.2f, 0.2f, 0.3f, 0.9f);
        shapeRenderer.rect(200, 150, 400, 250);

        // Border
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(200, 150, 400, 250);

        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "Choose Upgrade:", 220, 370);
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

