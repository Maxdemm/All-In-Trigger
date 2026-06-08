package com.gambleknight.view.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    public float x, y;
    public float width = 32f;
    public float height = 32f;
    public float speed;
    public float hp = 20f;
    public String type; // "melee" або "ranged"

    public Enemy(float x, float y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.speed = type.equals("melee") ? 110f : 80f; // Ближній бій бігає швидше
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Простий ШІ: розраховує вектор до гравця та рухається в його сторону
    public void update(float delta, float playerX, float playerY) {
        Vector2 direction = new Vector2(playerX - this.x, playerY - this.y);
        float distance = direction.len();

        // Починає переслідування, якщо гравець підійшов ближче ніж на 450 пікселів
        if (distance < 450f && distance > 5f) {
            direction.nor(); // Нормалізуємо вектор (робимо довжину одиничною)
            this.x += direction.x * speed * delta;
            this.y += direction.y * speed * delta;
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        // Тінь
        shapeRenderer.setColor(0, 0, 0, 0.3f);
        shapeRenderer.ellipse(x + 2, y - 4, 28, 10);

        if (type.equals("melee")) {
            // Червоний боєць ближнього бою
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.setColor(Color.YELLOW); // Агресивні жовті очі
            shapeRenderer.circle(x + 10, y + 22, 3);
            shapeRenderer.circle(x + 22, y + 22, 3);
        } else {
            // Помаранчевий стрілець
            shapeRenderer.setColor(1f, 0.5f, 0f, 1f);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(x + 10, y + 22, 2.5f);
            shapeRenderer.circle(x + 22, y + 22, 2.5f);
        }
    }
}
