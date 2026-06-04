package ua.edu.ukma.cigaretteenoyers.level;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class RoomInstance {

    public enum RoomState {
        NOT_VISITED,
        ACTIVE,
        CLEARED
    }

    private final RoomTemplate template;
    private RoomState currentState;
    private int remainingEnemies;
    private boolean slotMachineSpawned;

    public RoomInstance(RoomTemplate template) {
        this.template = template;
        this.currentState = RoomState.NOT_VISITED;
        this.remainingEnemies = template.getEnemySpawnPoints().size;
        this.slotMachineSpawned = false;
    }

    public void enterRoom() {
        if (currentState == RoomState.NOT_VISITED) {
            currentState = RoomState.ACTIVE;
            System.out.println("Gamer enter into room, enemy count: " + remainingEnemies);

            if (remainingEnemies == 0) {
                clearRoom();
            }
        }
    }

    public void update(int currentAliveEnemiesCount) {
        if (currentState == RoomState.ACTIVE) {
            this.remainingEnemies = currentAliveEnemiesCount;

            if (remainingEnemies <= 0) {
                clearRoom();
            }
        }
    }

    private void clearRoom() {
        currentState = RoomState.CLEARED;
        System.out.println("Room is clear. the doors are open");

        if (!slotMachineSpawned) {
            triggerSlotMachineSpawn();
        }
    }

    private void triggerSlotMachineSpawn() {
        slotMachineSpawned = true;
        Vector2 spawnPos = template.getSlotMachineSpawnPoint();
        System.out.println("Slot machine xy: " + spawnPos);

    }


    public RoomState getCurrentState() { return currentState; }
    public RoomTemplate getTemplate() { return template; }
    public int getRemainingEnemies() { return remainingEnemies; }
    public boolean isSlotMachineSpawned() { return slotMachineSpawned; }

    public Array<Rectangle> getWallColliders() {
        return template.getWallColliders();
    }
}
