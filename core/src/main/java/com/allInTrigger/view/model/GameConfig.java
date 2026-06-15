package com.allInTrigger.view.model;

public class GameConfig {
    // Level 1: "Трущоби" (Easy)
    public static class Level1 {
        public static final int LEVEL_NUMBER = 1;
        public static final String LEVEL_NAME = "Трущоби";
        public static final int INITIAL_MONEY = 100;
        public static final float INFLATION = 0f; // No inflation
        public static final int NUM_ROOMS = 3;
        public static final float ENEMY_DAMAGE = 1f;
        public static final float ENEMY_SPEED_MULT = 1f;
    }

    // Level 2: "Казино" (Medium)
    public static class Level2 {
        public static final int LEVEL_NUMBER = 2;
        public static final String LEVEL_NAME = "Казино";
        public static final int INITIAL_MONEY = 150;
        public static final float INFLATION = 0f; // No inflation yet
        public static final int NUM_ROOMS = 3;
        public static final float ENEMY_DAMAGE = 1.5f;
        public static final float ENEMY_SPEED_MULT = 1.3f;
    }

    // Level 3: "Волл-Стріт" (Hard)
    public static class Level3 {
        public static final int LEVEL_NUMBER = 3;
        public static final String LEVEL_NAME = "Волл-Стріт";
        public static final int INITIAL_MONEY = 200;
        public static final float INFLATION = 1f / 2f; // -1 монета кожні 2 секунди
        public static final int NUM_ROOMS = 4;
        public static final float ENEMY_DAMAGE = 2.5f;
        public static final float ENEMY_SPEED_MULT = 1.8f;
        public static final boolean HAS_BOSS = true;
    }

    // Money system
    public static final int MONEY_FROM_BASIC_ENEMY = 50;
    public static final float MONEY_SHAKE_AMOUNT = 5f;
}

