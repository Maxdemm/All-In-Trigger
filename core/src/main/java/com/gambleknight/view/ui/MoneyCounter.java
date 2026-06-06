package com.gambleknight.view.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MoneyCounter {
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    public MoneyCounter() {
        font = new BitmapFont();
        font.getData().setScale(1.2f);
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Background
        shapeRenderer.setColor(0, 0, 0, 0.5f);
        shapeRenderer.rect(550, 440, 130, 30);

        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.YELLOW);
        font.draw(batch, "$ 250", 560, 460);
        batch.end();
    }

    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
    }
}

