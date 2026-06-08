package com.gambleknight.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class HUD {
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private String levelText = "LEVEL: 1-1"; // Змінна для динамічного відображення поточного рівня

    public HUD() {
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(1.2f); // Чистий, читаємий розмір
    }

    // Метод для оновлення тексту рівня (викликається з GameRenderer)
    public void setCurrentLevel(int level) {
        if (level == 1) {
            this.levelText = "LEVEL: 1-1";
        } else {
            this.levelText = "LEVEL: 1-2"; // Новий крижаний біом
        }
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Верхня плашка
        shapeRenderer.setColor(0.07f, 0.07f, 0.09f, 0.95f);
        shapeRenderer.rect(0, 640, 1280, 80);
        shapeRenderer.setColor(0.18f, 0.18f, 0.22f, 1f);
        shapeRenderer.rect(0, 638, 1280, 2);

        // --- ІДЕАЛЬНО РІВНІ СМУЖКИ ЗДОРОВ'Я ТА БРОНІ ---
        float barX = 90;
        float barWidth = 200;
        float barHeight = 14;

        // HP (Червона)
        drawSleekBar(barX, 690, barWidth, barHeight, Color.RED, 0.75f);
        // ARM (Блакитна)
        drawSleekBar(barX, 668, barWidth, barHeight, Color.CYAN, 0.60f);
        // ENG (Зелена)
        drawSleekBar(barX, 646, barWidth, barHeight, Color.GREEN, 0.85f);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // --- ВИТОНЧЕНИЙ ТЕКСТ ПО ЦЕНТРУ ТА КРАЯХ ---
        batch.begin();
        font.setColor(Color.LIGHT_GRAY);
        // Точне центрування тексту навпроти смужок
        font.draw(batch, "HP", 30, 703);
        font.draw(batch, "ARM", 30, 681);
        font.draw(batch, "ENG", 30, 659);

        // Рівень (Тепер рядок береться динамічно)
        font.setColor(Color.WHITE);
        font.draw(batch, levelText, 590, 688);

        // Золото
        font.setColor(Color.GOLD);
        font.draw(batch, "$ 250", 1160, 688);
        batch.end();
    }

    private void drawSleekBar(float x, float y, float width, float height, Color color, float progress) {
        // Задній темний слот
        shapeRenderer.setColor(0.15f, 0.15f, 0.18f, 1f);
        shapeRenderer.rect(x, y, width, height);
        // Кольорове заповнення
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width * progress, height);
        // Сучасний внутрішній неоновий блік (світла смужка зверху для об'єму кута)
        shapeRenderer.setColor(1, 1, 1, 0.25f);
        shapeRenderer.rect(x, y + height - 3, width * progress, 3);
    }

    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }
}
