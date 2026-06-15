package com.allInTrigger.map;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple representation of a level's map metadata used at runtime.
 * This is intentionally lightweight so it compiles without external map libs.
 */
public class LevelMap {
    private final int levelId;
    private final Map<Integer, Boolean> roomPortalEnabled = new HashMap<>();

    public LevelMap(int levelId) {
        this.levelId = levelId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setPortalEnabledForRoom(int roomId, boolean enabled) {
        roomPortalEnabled.put(roomId, enabled);
    }

    public boolean isPortalEnabledForRoom(int roomId) {
        return roomPortalEnabled.getOrDefault(roomId, true);
    }

    /** Returns true if any room in this level explicitly has portal enabled. */
    public boolean hasAnyPortalEnabled() {
        for (Boolean v : roomPortalEnabled.values()) {
            if (v != null && v) return true;
        }
        return false;
    }
}
