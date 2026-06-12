package com.allInTrigger.view.model;

import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Room {
    public int id;
    public Rectangle bounds;
    public List<Enemy> enemies;
    public boolean isCleared = false;
    public boolean isEntered = false;
    public int difficulty = 1; // 1, 2, 3 for different levels
    public Rectangle portalBounds; // Portal location when room is cleared

    public Room(int id, float x, float y, float width, float height, int difficulty) {
        this.id = id;
        this.bounds = new Rectangle(x, y, width, height);
        this.difficulty = difficulty;
        this.enemies = new ArrayList<>();

        // Portal is at the center of the room
        float centerX = x + width / 2 - 20;
        float centerY = y + height / 2 - 40;
        this.portalBounds = new Rectangle(centerX, centerY, 40, 80);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public boolean allEnemiesDefeated() {
        return enemies.isEmpty();
    }

    public void enter() {
        isEntered = true;
    }

    public void clear() {
        isCleared = true;
        isEntered = false;
    }

    public boolean playerIsInRoom(float playerX, float playerY, float playerWidth, float playerHeight) {
        Rectangle playerBounds = new Rectangle(playerX, playerY, playerWidth, playerHeight);
        return bounds.overlaps(playerBounds);
    }
}

