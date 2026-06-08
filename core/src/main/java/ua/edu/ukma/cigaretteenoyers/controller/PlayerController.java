package ua.edu.ukma.cigaretteenoyers.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ua.edu.ukma.cigaretteenoyers.model.player.PlayerModel;
import ua.edu.ukma.cigaretteenoyers.shared.Constants;

public class PlayerController {
    private final PlayerModel player;
    private final OrthographicCamera camera;

    public PlayerController(PlayerModel player, OrthographicCamera camera) {
        this.player = player;
        this.camera = camera;
    }

    public void update(float delta) {
        handleMovement(delta);
        handleAiming();
    }

    private void handleMovement(float delta) {
        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Keys.W)) dy += 1;
        if (Gdx.input.isKeyPressed(Keys.S)) dy -= 1;
        if (Gdx.input.isKeyPressed(Keys.A)) dx -= 1;
        if (Gdx.input.isKeyPressed(Keys.D)) dx += 1;

        Vector2 vel = new Vector2(dx, dy);
        if (vel.len2() > 1f) {
            vel.nor();
        }
        vel.scl(Constants.PLAYER_SPEED * delta);
        player.velocity.set(vel);
        player.update(delta);
    }

    private void handleAiming() {
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouse);
        player.angle = MathUtils.atan2(
            mouse.y - player.position.y,
            mouse.x - player.position.x
        ) * MathUtils.radiansToDegrees;
    }
}
