package com.allInTrigger.view.ui;

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
        font.getData().setScale(1.2f);
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        if (!isVisible) return;

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float x = 440;
        float y = 380;
        float width = 400;
        float height = 230;

        shapeRenderer.setColor(0.12f, 0.12f, 0.18f, 0.95f);
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.rect(x, y, width, 2);
        shapeRenderer.rect(x, y + height - 2, width, 2);
        shapeRenderer.rect(x, y, 2, height);
        shapeRenderer.rect(x + width - 2, y, 2, height);

        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "CHOOSE YOUR UPGRADE:", x + 30, y + height - 30);
        font.setColor(Color.GRAY);
        font.draw(batch, "[1] +20% Damage", x + 50, y + height - 80);
        font.draw(batch, "[2] +15% Speed", x + 50, y + height - 120);
        font.draw(batch, "[3] Max Armor", x + 50, y + height - 160);
        batch.end();
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
    }
}
