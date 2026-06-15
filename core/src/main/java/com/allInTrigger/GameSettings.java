package com.allInTrigger;

public class GameSettings {
    private Difficulty difficulty;

    public GameSettings() {
        this.difficulty = Difficulty.NORMAL;
    }

    public GameSettings(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void cycleDifficulty() {
        this.difficulty = this.difficulty.next();
    }

    public float getHpMultiplier() {
        return difficulty.getHpMultiplier();
    }

    public float getSpeedMultiplier() {
        return difficulty.getSpeedMultiplier();
    }
}

