package com.allInTrigger.view.model;

import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;
import com.allInTrigger.map.MapRegistry;
import com.allInTrigger.map.LevelMap;

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

    /**
     * Checks whether this room should show a portal when cleared. Default true if no level map registered.
     */
    public boolean isPortalEnabledForCurrentLevel(int levelId) {
        try {
            LevelMap map = MapRegistry.get(levelId).orElse(null);
            if (map == null) return true; // no map -> keep old behaviour
            // If the level explicitly enables portals for any room, respect the per-room flag.
            if (map.hasAnyPortalEnabled()) {
                return map.isPortalEnabledForRoom(this.id);
            }
            // If no portals are explicitly enabled for the level, default to true (old behaviour)
            return true;
        } catch (Throwable t) {
            return true; // fail-safe: show portal if anything goes wrong
        }
    }

    public boolean playerIsInRoom(float playerX, float playerY, float playerWidth, float playerHeight) {
        Rectangle playerBounds = new Rectangle(playerX, playerY, playerWidth, playerHeight);
        return bounds.overlaps(playerBounds);
    }
}
