package com.allInTrigger.view.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BulletView {
    private ShapeRenderer shapeRenderer;

    public BulletView() {
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Умовна позиція кулі для демонстрації
        float bulletX = 330;
        float bulletY = 140;

        // 1. Світіння навколо кулі (напівпрозорий великий радіус)
        shapeRenderer.setColor(1f, 1f, 0f, 0.25f); // Жовте світіння
        shapeRenderer.circle(bulletX, bulletY, 12);

        // 2. Тіло лазерної капсули (витягнута горизонтальна куля)
        shapeRenderer.setColor(1f, 0.3f, 0f, 1f); // Помаранчеве ядро
        shapeRenderer.rect(bulletX - 8, bulletY - 3, 16, 6);
        shapeRenderer.circle(bulletX - 8, bulletY, 3);
        shapeRenderer.circle(bulletX + 8, bulletY, 3);

        // 3. Яскрава серединка
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(bulletX - 4, bulletY - 1.5f, 8, 3);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
