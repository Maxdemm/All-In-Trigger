package com.gambleknight.view.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class HealthBar {
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    public HealthBar() {
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Позиція HUD у верхньому лівому кутку (x=20, y=420)
        float startX = 20;
        float startY = 420;

        // 1. Смужка ХП (Червона)
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(startX, startY, 150, 14);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(startX + 2, startY + 2, 146 * 0.8f, 10); // 80% HP

        // 2. Смужка БРОНІ (Синя/Слабо-блакитна, як у Soul Knight)
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(startX, startY - 18, 150, 14);
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.rect(startX + 2, startY - 16, 146 * 0.5f, 10); // 50% Armor

        // 3. Смужка ЕНЕРГІЇ (Зелена)
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(startX, startY - 36, 150, 14);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(startX + 2, startY - 34, 146 * 1.0f, 10); // 100% Energy

        shapeRenderer.end();

        // Текстові показники поверх смужок
        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "HP", startX + 5, startY + 12);
        font.draw(batch, "ARM", startX + 5, startY - 6);
        font.draw(batch, "ENG", startX + 5, startY - 24);
        batch.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }
}
