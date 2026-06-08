package com.allInTrigger.view.ui;

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
        font.getData().setScale(1.3f);
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        if (!isVisible) return;

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float x = 470;
        float y = 420;
        float width = 340;
        float height = 180;

        shapeRenderer.setColor(0.25f, 0.15f, 0.1f, 0.95f);
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.rect(x, y, width, 3);
        shapeRenderer.rect(x, y + height - 3, width, 3);
        shapeRenderer.rect(x, y, 3, height);
        shapeRenderer.rect(x + width - 3, y, 3, height);

        shapeRenderer.setColor(0, 0, 0, 1f);
        shapeRenderer.rect(x + 30, y + 80, 60, 60);
        shapeRenderer.rect(x + 140, y + 80, 60, 60);
        shapeRenderer.rect(x + 250, y + 80, 60, 60);

        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.YELLOW);
        font.draw(batch, "PRESS [ENTER] TO SPIN!", x + 45, y + 45);

        font.setColor(Color.WHITE);
        font.draw(batch, "7", x + 53, y + 118);
        font.draw(batch, "7", x + 163, y + 118);
        font.draw(batch, "7", x + 273, y + 118);
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
