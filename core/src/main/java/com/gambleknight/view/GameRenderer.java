package com.gambleknight.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.gambleknight.view.player.PlayerView;
import com.gambleknight.view.room.RoomView;
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

    private float camTime = 0;

    public GameRenderer() {
        batch = new SpriteBatch();

        // Камера для ігрового світу
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        // Матриця для інтерфейсу (завжди статична поверх екрана)
        uiMatrix = new Matrix4().setToOrtho2D(0, 0, 1280, 720);

        roomView = new RoomView();
        playerView = new PlayerView();
        hud = new HUD();
        minimapUI = new MinimapUI();
        weaponPanel = new WeaponPanel();
    }

    public void render() {

        float speed = 500 * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            camera.position.y += speed;

        if (Gdx.input.isKeyPressed(Input.Keys.S))
            camera.position.y -= speed;

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            camera.position.x -= speed;

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            camera.position.x += speed;

        camera.update();

        // --- ШАР 1: ІГРОВИЙ СВІТ (Камера рухається) ---
        batch.setProjectionMatrix(camera.combined);
        roomView.render(batch, camTime); // Передаємо час для анімації порталу

        // Малюємо нашого лицаря в першій кімнаті
        playerView.render(batch, 250, 200, camTime * 40f);

        // --- ШАР 2: ІНТЕРФЕЙС (Зафіксований на екрані 1280x720) ---
        batch.setProjectionMatrix(uiMatrix);
        hud.render(batch);
        minimapUI.render(batch);
        weaponPanel.render(batch);
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
