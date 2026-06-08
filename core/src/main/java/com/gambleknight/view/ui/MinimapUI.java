package com.gambleknight.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MinimapUI {
    private ShapeRenderer shapeRenderer;

    // Стильні кольори для різних зон замку
    private final Color colorRoom = new Color(0.35f, 0.35f, 0.4f, 1f);       // Звичайні кімнати
    private final Color colorCorridor = new Color(0.2f, 0.2f, 0.22f, 1f);    // Коридори та тунелі
    private final Color colorLab = new Color(0.2f, 0.6f, 0.9f, 1f);          // Блакитна магічна лабораторія (К7)
    private final Color colorTreasury = new Color(0.9f, 0.75f, 0.1f, 1f);    // Золота скарбниця (К8)
    private final Color colorCrypt = new Color(0.25f, 0.45f, 0.25f, 1f);     // Зелений стародавній склеп (К9)
    private final Color colorForge = new Color(0.85f, 0.3f, 0.15f, 1f);      // Вогняна лавова кузня (К10)

    public MinimapUI() {
        shapeRenderer = new ShapeRenderer();
    }

    /**
     * Рендеринг мінімапи
     * @param batch потік малювання
     * @param playerX поточна X координата гравця у світі
     * @param playerY поточна Y координата гравця у світі
     */
    public void render(SpriteBatch batch, float playerX, float playerY) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // --- 1. НАЛАШТУВАННЯ СТАТИЧНОЇ РАМКИ МІНІМАПИ ---
        float mapX = 1080;
        float mapY = 470;
        float mapW = 170;
        float mapH = 150;

        // Напівпрозорий фон мапи
        shapeRenderer.setColor(0, 0, 0, 0.75f);
        shapeRenderer.rect(mapX, mapY, mapW, mapH);

        // Кам'яна обводка рамки мінімапи
        shapeRenderer.setColor(0.25f, 0.25f, 0.3f, 1f);
        shapeRenderer.rect(mapX, mapY, mapW, 2);
        shapeRenderer.rect(mapX, mapY + mapH - 2, mapW, 2);
        shapeRenderer.rect(mapX, mapY, 2, mapH);
        shapeRenderer.rect(mapX + mapW - 2, mapY, 2, mapH);

        // --- 2. МАТЕМАТИКА МАСШТАБУВАННЯ (Зсув під RoomView) ---
        // Вираховуємо межі всього твого світу з RoomView: X(-550 до 2300), Y(-650 до 1200)
        float minX = -550;
        float minY = -650;
        float worldW = 2850;
        float worldH = 1850;

        // Коефіцієнт стиснення та центрування, щоб усе сіло ідеально всередині 170x150
        float scale = 0.046f;
        float offsetX = mapX + (mapW - worldW * scale) / 2f;
        float offsetY = mapY + (mapH - worldH * scale) / 2f;

        // --- 3. МАЛЮЄМО КОРИДОРИ ТА ТУНЕЛІ (Нижній шар мапи) ---
        shapeRenderer.setColor(colorCorridor);

        // Горизонтальні проходи
        drawScaledRect(450, 220, 200, 100, minX, minY, scale, offsetX, offsetY);   // Коридор 1
        drawScaledRect(1100, 220, 200, 100, minX, minY, scale, offsetX, offsetY);  // Коридор 2
        drawScaledRect(-150, -500, 200, 100, minX, minY, scale, offsetX, offsetY); // Коридор 7 (до Склепу)
        drawScaledRect(1700, -500, 200, 100, minX, minY, scale, offsetX, offsetY); // Коридор 8 (до Кузні)
        drawScaledRect(450, 950, 200, 100, minX, minY, scale, offsetX, offsetY);   // Коридор 5 (до Лабораторії)
        drawScaledRect(1100, 950, 200, 100, minX, minY, scale, offsetX, offsetY);  // Коридор 6 (до Скарбниці)

        // Вертикальні шахти/переходи
        drawScaledRect(225, -300, 100, 400, minX, minY, scale, offsetX, offsetY);  // Тунель 1 (Вниз від К1)
        drawScaledRect(825, 500, 100, 300, minX, minY, scale, offsetX, offsetY);   // Тунель 2 (Вгору від К2)
        drawScaledRect(1450, -300, 100, 400, minX, minY, scale, offsetX, offsetY); // Тунель 3 (Вниз від К3)

        // --- 4. МАЛЮЄМО КІМНАТИ (Верхній шар мапи) ---

        // Стандартні кімнати замку (Сірі)
        shapeRenderer.setColor(colorRoom);
        drawScaledRect(100, 100, 350, 350, minX, minY, scale, offsetX, offsetY);   // Кімната 1 (Старт)
        drawScaledRect(650, 50, 450, 450, minX, minY, scale, offsetX, offsetY);    // Кімната 2 (Центр)
        drawScaledRect(1300, 100, 400, 400, minX, minY, scale, offsetX, offsetY);  // Кімната 3 (Правий фланг)
        drawScaledRect(50, -650, 450, 350, minX, minY, scale, offsetX, offsetY);   // Кімната 4 (Підземелля ліво)
        drawScaledRect(1300, -650, 400, 350, minX, minY, scale, offsetX, offsetY); // Кімната 6 (Підземелля право)
        drawScaledRect(650, 800, 450, 400, minX, minY, scale, offsetX, offsetY);   // Кімната 5 (Небесний зал центр)

        // Тематичні унікальні кімнати (Кольорові)
        shapeRenderer.setColor(colorLab);
        drawScaledRect(50, 800, 400, 400, minX, minY, scale, offsetX, offsetY);    // Кімната 7 (Магічна Лабораторія)

        shapeRenderer.setColor(colorTreasury);
        drawScaledRect(1300, 800, 450, 400, minX, minY, scale, offsetX, offsetY);  // Кімната 8 (Королівська Скарбниця)

        shapeRenderer.setColor(colorCrypt);
        drawScaledRect(-550, -650, 400, 350, minX, minY, scale, offsetX, offsetY); // Кімната 9 (Стародавній Склеп)

        shapeRenderer.setColor(colorForge);
        drawScaledRect(1900, -650, 400, 350, minX, minY, scale, offsetX, offsetY); // Кімната 10 (Лавова Кузня)

        // --- 5. ЖИВА МАРКЕР-КРАПКА ГРАВЦЯ ---
        // Перетворюємо ігрові координати гравця у координати екрану мінімапи
        float playerMinimapX = offsetX + (playerX - minX) * scale;
        float playerMinimapY = offsetY + (playerY - minY) * scale;

        // Малюємо гравця (яскраву синю крапку), якщо він у межах вікна мінімапи
        if (playerMinimapX >= mapX && playerMinimapX <= mapX + mapW &&
            playerMinimapY >= mapY && playerMinimapY <= mapY + mapH) {
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.circle(playerMinimapX, playerMinimapY, 3.5f);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    // Допоміжний метод автоматичного розрахунку розміру та позиції кімнати на мінімапі
    private void drawScaledRect(float rx, float ry, float rw, float rh,
                                float minX, float minY, float scale, float offsetX, float offsetY) {
        shapeRenderer.rect(offsetX + (rx - minX) * scale,
            offsetY + (ry - minY) * scale,
            rw * scale,
            rh * scale);
    }
    // Цей метод дозволить викликати мапу як раніше — з 1 аргументом,
    // і підставить "за замовчуванням" координати стартової кімнати (100, 100)
    public void render(SpriteBatch batch) {
        render(batch, 100f, 100f);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
