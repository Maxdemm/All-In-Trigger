package com.allInTrigger.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapRegistry {
    private static final Map<Integer, LevelMap> registry = new HashMap<>();

    public static void register(LevelMap map) {
        registry.put(map.getLevelId(), map);
    }

    public static Optional<LevelMap> get(int levelId) {
        return Optional.ofNullable(registry.get(levelId));
    }
}

