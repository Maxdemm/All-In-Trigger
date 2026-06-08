package com.allInTrigger.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class HUD {
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private String levelText = "LEVEL: 1-1";

    public HUD() {
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(1.2f);
    }

    public void setCurrentLevel(int level) {
        if (level == 1) {
            this.levelText = "LEVEL: 1-1";
        } else {
            this.levelText = "LEVEL: 1-2";
        }
    }

    // ОНОВЛЕНО: Тепер метод приймає також броню (ARM) та стаміну (ENG)
    public void render(SpriteBatch batch, float currentHp, float maxHp, float currentArm, float maxArm, float currentEng, float maxEng, int currentMoney) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Верхня плашка
        shapeRenderer.setColor(0.07f, 0.07f, 0.09f, 0.95f);
        shapeRenderer.rect(0, 640, 1280, 80);
        shapeRenderer.setColor(0.18f, 0.18f, 0.22f, 1f);
        shapeRenderer.rect(0, 638, 1280, 2);

        float barX = 90;
        float barWidth = 200;
        float barHeight = 14;

        // Розрахунок відсотків заповнення смужок
        float hpProgress = Math.max(0f, currentHp / maxHp);
        float armProgress = Math.max(0f, currentArm / maxArm);
        float engProgress = Math.max(0f, currentEng / maxEng);

        // HP (Червона) — Динамічна
        drawSleekBar(barX, 690, barWidth, barHeight, Color.RED, hpProgress);
        // ARM (Блакитна) — Динамічна
        drawSleekBar(barX, 668, barWidth, barHeight, Color.CYAN, armProgress);
        // ENG (Зелена) — Динамічна
        drawSleekBar(barX, 646, barWidth, barHeight, Color.GREEN, engProgress);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "HP", 30, 703);
        font.draw(batch, "ARM", 30, 681);
        font.draw(batch, "ENG", 30, 659);

        // Рівень
        font.setColor(Color.WHITE);
        font.draw(batch, levelText, 590, 688);

        // Золото — Динамічне
        font.setColor(Color.GOLD);
        font.draw(batch, "$ " + currentMoney, 1160, 688);
        batch.end();
    }

    private void drawSleekBar(float x, float y, float width, float height, Color color, float progress) {
        shapeRenderer.setColor(0.15f, 0.15f, 0.18f, 1f);
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width * progress, height);

        shapeRenderer.setColor(1, 1, 1, 0.25f);
        shapeRenderer.rect(x, y + height - 3, width * progress, 3);
    }

    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }
}
