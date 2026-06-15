package com.allInTrigger;

public enum Difficulty {
    EASY(0.7f, 0.85f),
    NORMAL(1.0f, 1.0f),
    HARD(1.5f, 1.2f);

    private final float hpMultiplier;
    private final float speedMultiplier;

    Difficulty(float hpMultiplier, float speedMultiplier) {
        this.hpMultiplier = hpMultiplier;
        this.speedMultiplier = speedMultiplier;
    }

    public float getHpMultiplier() {
        return hpMultiplier;
    }

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }

    public Difficulty next() {
        int ord = this.ordinal();
        Difficulty[] vals = Difficulty.values();
        return vals[(ord + 1) % vals.length];
    }
}

