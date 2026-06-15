package com.allInTrigger.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.allInTrigger.view.model.Coin;
import com.allInTrigger.view.model.Enemy;

import java.util.List;

public class MinimapUI {
    private ShapeRenderer shapeRenderer;

    private final Color colorRoom = new Color(0.35f, 0.35f, 0.4f, 1f);
    private final Color colorCorridor = new Color(0.2f, 0.2f, 0.22f, 1f);
    private final Color colorLab = new Color(0.2f, 0.6f, 0.9f, 1f);
    private final Color colorTreasury = new Color(0.9f, 0.75f, 0.1f, 1f);
    private final Color colorCrypt = new Color(0.25f, 0.45f, 0.25f, 1f);
    private final Color colorForge = new Color(0.85f, 0.3f, 0.15f, 1f);

    public MinimapUI() {
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch, float playerX, float playerY, List<Coin> coins, List<Enemy> enemies) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float mapX = 1080;
        float mapY = 545;
        float mapW = 170;
        float mapH = 150;

        shapeRenderer.setColor(0, 0, 0, 0.75f);
        shapeRenderer.rect(mapX, mapY, mapW, mapH);

        shapeRenderer.setColor(0.25f, 0.25f, 0.3f, 1f);
        shapeRenderer.rect(mapX, mapY, mapW, 2);
        shapeRenderer.rect(mapX, mapY + mapH - 2, mapW, 2);
        shapeRenderer.rect(mapX, mapY, 2, mapH);
        shapeRenderer.rect(mapX + mapW - 2, mapY, 2, mapH);

        float minX = -550;
        float minY = -650;
        float worldW = 2850;
        float worldH = 1850;

        float scale = 0.046f;
        float offsetX = mapX + (mapW - worldW * scale) / 2f;
        float offsetY = mapY + (mapH - worldH * scale) / 2f;

        shapeRenderer.setColor(colorCorridor);
        drawScaledRect(450, 220, 200, 100, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(1100, 220, 200, 100, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(-150, -500, 200, 100, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(1700, -500, 200, 100, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(450, 950, 200, 100, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(1100, 950, 200, 100, minX, minY, scale, offsetX, offsetY);

        drawScaledRect(225, -300, 100, 400, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(825, 500, 100, 300, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(1450, -300, 100, 400, minX, minY, scale, offsetX, offsetY);

        shapeRenderer.setColor(colorRoom);
        drawScaledRect(100, 100, 350, 350, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(650, 50, 450, 450, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(1300, 100, 400, 400, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(50, -650, 450, 350, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(1300, -650, 400, 350, minX, minY, scale, offsetX, offsetY);
        drawScaledRect(650, 800, 450, 400, minX, minY, scale, offsetX, offsetY);

        shapeRenderer.setColor(colorLab);
        drawScaledRect(50, 800, 400, 400, minX, minY, scale, offsetX, offsetY);

        shapeRenderer.setColor(colorTreasury);
        drawScaledRect(1300, 800, 450, 400, minX, minY, scale, offsetX, offsetY);

        shapeRenderer.setColor(colorCrypt);
        drawScaledRect(-550, -650, 400, 350, minX, minY, scale, offsetX, offsetY);

        shapeRenderer.setColor(colorForge);
        drawScaledRect(1900, -650, 400, 350, minX, minY, scale, offsetX, offsetY);

        shapeRenderer.setColor(Color.YELLOW);
        for (Coin coin : coins) {
            if (!coin.isCollected) {
                float cx = offsetX + (coin.x - minX) * scale;
                float cy = offsetY + (coin.y - minY) * scale;
                if (cx >= mapX && cx <= mapX + mapW && cy >= mapY && cy <= mapY + mapH) {
                    shapeRenderer.circle(cx, cy, 2f); // Маленькі жовті крапки
                }
            }
        }

        shapeRenderer.setColor(Color.RED);
        for (Enemy enemy : enemies) {
            float ex = offsetX + (enemy.x - minX) * scale;
            float ey = offsetY + (enemy.y - minY) * scale;
            if (ex >= mapX && ex <= mapX + mapW && ey >= mapY && ey <= mapY + mapH) {
                shapeRenderer.circle(ex, ey, 2.5f); // Червоні крапки ворогів
            }
        }

        float playerMinimapX = offsetX + (playerX - minX) * scale;
        float playerMinimapY = offsetY + (playerY - minY) * scale;

        if (playerMinimapX >= mapX && playerMinimapX <= mapX + mapW &&
            playerMinimapY >= mapY && playerMinimapY <= mapY + mapH) {
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.circle(playerMinimapX, playerMinimapY, 3.5f);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawScaledRect(float rx, float ry, float rw, float rh,
                                float minX, float minY, float scale, float offsetX, float offsetY) {
        shapeRenderer.rect(offsetX + (rx - minX) * scale, offsetY + (ry - minY) * scale, rw * scale, rh * scale);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
