package com.gambleknight.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.gambleknight.view.player.PlayerView;
import com.gambleknight.view.room.DoorView;
import com.gambleknight.view.room.RoomView;
import com.gambleknight.view.room.WallView;
import com.gambleknight.view.ui.HUD;
import com.gambleknight.view.ui.MinimapUI;
import com.gambleknight.view.ui.WeaponPanel;

public class GameRenderer {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Matrix4 uiMatrix;

    private RoomView roomView;
    private PlayerView playerView;
    private HUD hud;
    private MinimapUI minimapUI;
    private WeaponPanel weaponPanel;

    // Координати та розміри гравця
    private float playerX = 250f;
    private float playerY = 200f;
    private final float PLAYER_WIDTH = 32f;
    private final float PLAYER_HEIGHT = 36f;
    private final float PLAYER_SPEED = 300f;

    private float camTime = 0;

    public GameRenderer() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        uiMatrix = new Matrix4().setToOrtho2D(0, 0, 1280, 720);

        roomView = new RoomView();
        playerView = new PlayerView();
        hud = new HUD();
        minimapUI = new MinimapUI();
        weaponPanel = new WeaponPanel();
    }

    public void render() {
        camTime += Gdx.graphics.getDeltaTime();
        float speed = PLAYER_SPEED * Gdx.graphics.getDeltaTime();

        // 1. ОБРОБКА РУХУ ТА КОЛІЗІЙ
        float nextX = playerX;
        float nextY = playerY;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) nextY += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) nextY -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) nextX -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) nextX += speed;

        // Створюємо тимчасові прямокутники для перевірки колізій окремо по X та Y (щоб гравець міг "ковзати" вздовж стін)
        Rectangle playerBoundsX = new Rectangle(nextX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
        Rectangle playerBoundsY = new Rectangle(playerX, nextY, PLAYER_WIDTH, PLAYER_HEIGHT);

        // Перевірка колізії по осі X
        if (!isColliding(playerBoundsX)) {
            playerX = nextX;
        }
        // Перевірка колізії по осі Y
        if (!isColliding(playerBoundsY)) {
            playerY = nextY;
        }

        // 2. ВЗАЄМОДІЯ З ДВЕРИМА (Клавіша E)
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            interactWithDoors();
        }

        // 3. ОНОВЛЕННЯ КАМЕРИ
        camera.position.set(playerX + PLAYER_WIDTH / 2f, playerY + PLAYER_HEIGHT / 2f, 0);
        camera.update();

        // --- РЕНДЕР СВІТУ ---
        batch.setProjectionMatrix(camera.combined);
        roomView.render(batch, camTime);
        playerView.render(batch, playerX, playerY, camTime * 40f);

        // --- РЕНДЕР UI ---
        batch.setProjectionMatrix(uiMatrix);
        hud.render(batch);
        minimapUI.render(batch);
        weaponPanel.render(batch);
    }

    /**
     * Перевіряє, чи перетинається заданий прямокутник зі стінами або зачиненими дверима.
     */
    private boolean isColliding(Rectangle bounds) {
        // Перевірка стін
        for (WallView wall : roomView.getWalls()) {
            if (bounds.overlaps(wall.getBounds())) {
                return true; // Зіткнулися зі стіною
            }
        }

        // Перевірка зачинених дверей (крізь відчинені ходити можна)
        for (DoorView door : roomView.getDoors()) {
            if (!door.isOpen() && bounds.overlaps(door.getBounds())) {
                return true; // Зіткнулися із зачиненими дверима
            }
        }

        return false;
    }

    /**
     * Шукає найближчі двері в радіусі дії та змінює їх стан (відчинено/зачинено).
     */
    private void interactWithDoors() {
        // Створюємо зону взаємодії навколо гравця (трохи ширшу за самого гравця)
        Rectangle interactionArea = new Rectangle(
            playerX - 20, playerY - 20,
            PLAYER_WIDTH + 40, PLAYER_HEIGHT + 40
        );

        for (DoorView door : roomView.getDoors()) {
            if (interactionArea.overlaps(door.getBounds())) {
                // Міняємо стан дверей на протилежний
                door.setOpen(!door.isOpen());
                break; // Спрацьовує тільки на одні найближчі двері за раз
            }
        }
    }

    public void dispose() {
        batch.dispose();
        roomView.dispose();
        playerView.dispose();
        hud.dispose();
        minimapUI.dispose();
        weaponPanel.dispose();
    }
}
