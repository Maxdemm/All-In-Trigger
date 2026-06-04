package ua.edu.ukma.cigaretteenoyers.level;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Static blueprint for a game room. Parses a raw integer matrix layout
 * and translates it into LibGDX world coordinates.
 */
public class RoomTemplate {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 11;
    public static final float TILE_SIZE = 32f;

    private final int[][] tileMap;
    private final Array<Vector2> enemySpawnPoints;
    private final Vector2 slotMachineSpawnPoint;

    /**
     * Constructs a new RoomTemplate and processes the layout matrix.
     * Maps top-left array indices to LibGDX bottom-left world space.
     *
     * @param matrix 2D array defining the room structure
     */
    public RoomTemplate(int[][] matrix) {
        this.tileMap = matrix;
        this.enemySpawnPoints = new Array<>();
        this.slotMachineSpawnPoint = new Vector2(WIDTH * TILE_SIZE / 2, HEIGHT * TILE_SIZE / 2);
        parseMatrix();
    }

    /**
     * Scans the layout matrix to locate enemy and slot machine markers.
     * Automatically inverts the Y-axis for OpenGL coordinate system.
     */
    private void parseMatrix() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int tileType = tileMap[y][x];

                float worldX = x * TILE_SIZE;
                float worldY = (HEIGHT - 1 - y) * TILE_SIZE;

                if (tileType == 3) {
                    enemySpawnPoints.add(new Vector2(worldX, worldY));
                } else if (tileType == 4) {
                    slotMachineSpawnPoint.set(worldX, worldY);
                }
            }
        }
    }

    public int[][] getTileMap() { return tileMap; }
    public Array<Vector2> getEnemySpawnPoints() { return enemySpawnPoints; }
    public Vector2 getSlotMachineSpawnPoint() { return slotMachineSpawnPoint; }

    /**
     * Generates bounding boxes for solid objects like walls and obstacles.
     * Used by Developer 1 for physics and collision detection.
     *
     * @return array of rectangles representing solid walls
     */
    public Array<Rectangle> getWallColliders() {
        Array<Rectangle> colliders = new Array<>();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {

                if (tileMap[y][x] == 1 || tileMap[y][x] == 2) {
                    float worldX = x * TILE_SIZE;
                    float worldY = (HEIGHT - 1 - y) * TILE_SIZE;
                    colliders.add(new Rectangle(worldX, worldY, TILE_SIZE, TILE_SIZE));
                }
            }
        }
        return colliders;
    }
}
