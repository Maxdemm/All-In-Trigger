package ua.edu.ukma.cigaretteenoyers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import ua.edu.ukma.cigaretteenoyers.controller.PlayerController;
import ua.edu.ukma.cigaretteenoyers.model.player.PlayerModel;
import ua.edu.ukma.cigaretteenoyers.shared.Constants;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GambleKnightGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private PlayerModel player;
    private PlayerController playerController;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        shapeRenderer = new ShapeRenderer();
        player = new PlayerModel();
        playerController = new PlayerController(player, camera);
    }

    @Override
    public void render() {
        playerController.update(Gdx.graphics.getDeltaTime());

        camera.update();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(
            player.position.x - Constants.PLAYER_RADIUS,
            player.position.y - Constants.PLAYER_RADIUS,
            Constants.PLAYER_RADIUS * 2f,
            Constants.PLAYER_RADIUS * 2f
        );
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
