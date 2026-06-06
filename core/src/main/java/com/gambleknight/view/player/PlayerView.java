package com.gambleknight.view.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerView {
    private ShapeRenderer shapeRenderer;

    public PlayerView() {
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch, float targetX, float targetY, float weaponAngle) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // 1. Малюємо ТІНЬ (тепер вона точно з'явиться)
        shapeRenderer.setColor(0, 0, 0, 0.3f);
        shapeRenderer.ellipse(targetX + 2, targetY - 4, 28, 10);

        // 2. Малюємо ТІЛО ГРАВЦЯ
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(targetX, targetY, 32, 36);

        // Шолом / Маска
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(targetX, targetY + 16, 32, 14);

        // Очі
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(targetX + 6, targetY + 22, 6, 4);
        shapeRenderer.rect(targetX + 20, targetY + 22, 6, 4);

        // 3. Малюємо ЗБРОЮ
        // Використовуємо розширений метод rect(), який вміє крутитися навколо своєї осі (origin)
        // без ручного переписування матриць всього екрана.
        shapeRenderer.setColor(Color.GRAY);
        float centerX = targetX + 16;
        float centerY = targetY + 16;

        shapeRenderer.rect(
            centerX, centerY - 3,   // Позиція зброї на екрані
            0, 3,                   // Точка обертання (лівий бік ствола, центр)
            22, 8,                  // Довжина та ширина ствола
            1f, 1f,                 // Масштаб по X та Y
            weaponAngle             // Кут повороту в градусах
        );

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
