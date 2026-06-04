package ua.edu.ukma.cigaretteenoyers.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    private final Rectangle leftDoorCollider;
    private final Rectangle rightDoorCollider;

    public RoomInstance(RoomTemplate template) {
        this.template = template;
        this.currentState = RoomState.NOT_VISITED;
        this.remainingEnemies = template.getEnemySpawnPoints().size;
        this.slotMachineSpawned = false;

        float midY = (RoomTemplate.HEIGHT / 2) * RoomTemplate.TILE_SIZE;

        this.leftDoorCollider = new Rectangle(0, midY, RoomTemplate.TILE_SIZE, RoomTemplate.TILE_SIZE);
        this.rightDoorCollider = new Rectangle((RoomTemplate.WIDTH - 1) * RoomTemplate.TILE_SIZE, midY, RoomTemplate.TILE_SIZE, RoomTemplate.TILE_SIZE);
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

    public void render(ShapeRenderer shapeRenderer) {
        int[][] tileMap = template.getTileMap();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int y = 0; y < RoomTemplate.HEIGHT; y++) {
            for (int x = 0; x < RoomTemplate.WIDTH; x++) {
                int tileType = tileMap[y][x];
                float worldX = x * RoomTemplate.TILE_SIZE;
                float worldY = (RoomTemplate.HEIGHT - 1 - y) * RoomTemplate.TILE_SIZE;

                if (tileType == 1) {
                    shapeRenderer.setColor(Color.GRAY); // wall
                } else if (tileType == 2) {
                    shapeRenderer.setColor(Color.BROWN); // obstacle
                } else {
                    shapeRenderer.setColor(Color.DARK_GRAY); // floor
                }
                shapeRenderer.rect(worldX, worldY, RoomTemplate.TILE_SIZE, RoomTemplate.TILE_SIZE);
            }
        }

        if (currentState == RoomState.ACTIVE) {
            shapeRenderer.setColor(Color.RED); // door closed
        } else {
            shapeRenderer.setColor(Color.GREEN); // door open
        }

        shapeRenderer.rect(leftDoorCollider.x, leftDoorCollider.y, leftDoorCollider.width, leftDoorCollider.height);
        shapeRenderer.rect(rightDoorCollider.x, rightDoorCollider.y, rightDoorCollider.width, rightDoorCollider.height);

        shapeRenderer.end();
    }

    public Array<Rectangle> getWallColliders() {
        Array<Rectangle> colliders = template.getWallColliders();
        if (currentState == RoomState.ACTIVE) {
            colliders.add(leftDoorCollider);
            colliders.add(rightDoorCollider);
        }
        return colliders;
    }

    public Rectangle getLeftDoorCollider() { return leftDoorCollider; }
    public Rectangle getRightDoorCollider() { return rightDoorCollider; }
    public RoomState getCurrentState() { return currentState; }
    public RoomTemplate getTemplate() { return template; }
}
