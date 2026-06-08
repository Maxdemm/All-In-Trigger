package com.gambleknight.view.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class SlotMachineUI {
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private boolean isVisible = false;

    public SlotMachineUI() {
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        if (!isVisible) return;

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Slot machine background
        shapeRenderer.setColor(0.3f, 0.2f, 0.1f, 0.9f);
        shapeRenderer.rect(250, 200, 300, 150);

        // Slot windows
        shapeRenderer.setColor(0, 0, 0, 1f);
        shapeRenderer.rect(270, 270, 50, 50);
        shapeRenderer.rect(350, 270, 50, 50);
        shapeRenderer.rect(430, 270, 50, 50);

        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.YELLOW);
        font.draw(batch, "SPIN!", 350, 230);
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

