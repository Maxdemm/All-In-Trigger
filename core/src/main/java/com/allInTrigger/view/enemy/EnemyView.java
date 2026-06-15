package com.allInTrigger.view.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class EnemyView {
    // Кольори для стилю "Казино/Гамблінг"
    private final Color COLOR_MELEE = new Color(0.8f, 0.1f, 0.1f, 1f); // Насичений червоний
    private final Color COLOR_RANGED = new Color(0.6f, 0.2f, 0.8f, 1f); // Фіолетовий (як фішки або сукно)
    private final Color COLOR_SHADOW = new Color(0, 0, 0, 0.3f);

    public void render(ShapeRenderer shapeRenderer, float x, float y, String type, float stateTime) {
        // Ефект плавного погойдування (дихання)
        float bobbing = MathUtils.sin(stateTime * 5f) * 3f;
        float pulse = MathUtils.sin(stateTime * 10f) * 1.5f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

        // 1. Тінь (стабільна на підлозі)
        shapeRenderer.setColor(COLOR_SHADOW);
        shapeRenderer.ellipse(x + 2, y - 5, 28, 10);

        if ("ranged".equalsIgnoreCase(type)) {
            drawRangedEnemy(shapeRenderer, x, y + bobbing, pulse);
        } else {
            drawMeleeEnemy(shapeRenderer, x, y + Math.abs(bobbing), pulse);
        }

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawMeleeEnemy(ShapeRenderer sr, float x, float y, float pulse) {
        // Тіло (Масивний броньований квадрат)
        sr.setColor(COLOR_MELEE);
        sr.rect(x, y, 32, 32);

        // Темна "маска" або шолом
        sr.setColor(0.1f, 0.1f, 0.15f, 1f);
        sr.rect(x + 2, y + 15, 28, 12);

        // Очі, що світяться (червоні)
        sr.setColor(Color.RED);
        sr.circle(x + 10, y + 21, 2 + pulse/2);
        sr.circle(x + 22, y + 21, 2 + pulse/2);

        // Деталі броні (лінії)
        sr.setColor(0.5f, 0f, 0f, 1f);
        sr.rect(x + 4, y, 4, 15);
        sr.rect(x + 24, y, 4, 15);
    }

    private void drawRangedEnemy(ShapeRenderer sr, float x, float y, float pulse) {
        // Тіло (більш витончене, літаюче)
        sr.setColor(COLOR_RANGED);
        // Малюємо ромб як основу
        sr.rect(x + 8, y, 16, 32);
        sr.triangle(x + 8, y + 32, x + 24, y + 32, x + 16, y + 40); // Капелюх/Верхівка

        // "Плащ"
        sr.setColor(0.3f, 0.1f, 0.4f, 1f);
        sr.rect(x + 4, y + 10, 24, 5);

        // Магічне око (одне велике)
        sr.setColor(Color.WHITE);
        sr.circle(x + 16, y + 24, 5 + pulse);
        sr.setColor(Color.CYAN);
        sr.circle(x + 16, y + 24, 2);

        // Магічні іскри знизу
        sr.setColor(0.8f, 0.4f, 1f, 0.6f);
        sr.circle(x + 8, y - 2 + pulse, 2);
        sr.circle(x + 24, y - 4 - pulse, 2);
    }
}
