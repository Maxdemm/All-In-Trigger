package com.allInTrigger.view.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.allInTrigger.AllInTrigger;

public class LobbyScreen implements Screen {
    private final AllInTrigger game;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    private Vector2 playerPos;
    private float playerSize = 40f;
    private float playerSpeed = 300f;

    private Rectangle roomBounds;
    private Rectangle portalBounds;
    private Rectangle carpetBounds;

    private Rectangle[] walls;
    private Rectangle[] crates;
    private Vector2[] torches;
    private Vector2[] trees;

    public LobbyScreen(AllInTrigger game) {
        this.game = game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        playerPos = new Vector2(200, 200);
        roomBounds = new Rectangle(100, 100, 1400, 1000);
        carpetBounds = new Rectangle(600, 400, 400, 300);
        portalBounds = new Rectangle(1300, 550, 80, 120);

        walls = new Rectangle[] {
            new Rectangle(100, 100, 1400, 40),
            new Rectangle(100, 1060, 1400, 40),
            new Rectangle(100, 100, 40, 1000),
            new Rectangle(1460, 100, 40, 1000)
        };

        crates = new Rectangle[] {
            new Rectangle(300, 300, 50, 50),
            new Rectangle(350, 300, 50, 50),
            new Rectangle(300, 350, 50, 50),
            new Rectangle(1100, 800, 50, 50),
            new Rectangle(1150, 850, 50, 50)
        };

        torches = new Vector2[] {
            new Vector2(200, 1040),
            new Vector2(500, 1040),
            new Vector2(800, 1040),
            new Vector2(1100, 1040),
            new Vector2(200, 160),
            new Vector2(500, 160),
            new Vector2(800, 160),
            new Vector2(1100, 160)
        };

        trees = new Vector2[] {
            new Vector2(450, 700),
            new Vector2(520, 750),
            new Vector2(950, 300),
            new Vector2(250, 850)
        };
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput(delta);

        camera.position.set(playerPos.x, playerPos.y, 0);
        camera.update();

        shapeRenderer.setProjectionMatrix(camera.combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0.18f, 0.18f, 0.22f, 1f);
        shapeRenderer.rect(roomBounds.x, roomBounds.y, roomBounds.width, roomBounds.height);

        shapeRenderer.setColor(0.5f, 0.1f, 0.1f, 0.6f);
        shapeRenderer.rect(carpetBounds.x, carpetBounds.y, carpetBounds.width, carpetBounds.height);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.25f, 0.25f, 0.3f, 0.5f);
        for (float x = roomBounds.x; x < roomBounds.x + roomBounds.width; x += 50) {
            shapeRenderer.line(x, roomBounds.y, x, roomBounds.y + roomBounds.height);
        }
        for (float y = roomBounds.y; y < roomBounds.y + roomBounds.height; y += 50) {
            shapeRenderer.line(roomBounds.x, y, roomBounds.x + roomBounds.width, y);
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0.3f, 0.3f, 0.35f, 1f);
        for (Rectangle wall : walls) {
            shapeRenderer.rect(wall.x, wall.y, wall.width, wall.height);
        }

        shapeRenderer.setColor(0.5f, 0.35f, 0.2f, 1f);
        for (Rectangle crate : crates) {
            shapeRenderer.rect(crate.x, crate.y, crate.width, crate.height);
            shapeRenderer.setColor(0.4f, 0.28f, 0.15f, 1f);
            shapeRenderer.rect(crate.x + 5, crate.y + 5, crate.width - 10, crate.height - 10);
            shapeRenderer.setColor(0.5f, 0.35f, 0.2f, 1f);
        }

        for (Vector2 tree : trees) {
            shapeRenderer.setColor(0.1f, 0.4f, 0.1f, 1f);
            shapeRenderer.circle(tree.x, tree.y, 45);
            shapeRenderer.setColor(0.15f, 0.5f, 0.15f, 1f);
            shapeRenderer.circle(tree.x - 10, tree.y + 10, 30);
        }

        for (Vector2 torch : torches) {
            shapeRenderer.setColor(0.3f, 0.2f, 0.1f, 1f);
            shapeRenderer.rect(torch.x - 4, torch.y - 15, 8, 20);
            shapeRenderer.setColor(1f, 0.4f, 0f, 1f);
            shapeRenderer.circle(torch.x, torch.y + 10, 8);
            shapeRenderer.setColor(1f, 0.8f, 0f, 1f);
            shapeRenderer.circle(torch.x, torch.y + 8, 4);
        }

        shapeRenderer.setColor(0.2f, 0.6f, 1f, 0.8f);
        shapeRenderer.rect(portalBounds.x, portalBounds.y, portalBounds.width, portalBounds.height);
        shapeRenderer.setColor(0.5f, 0.8f, 1f, 0.9f);
        shapeRenderer.rect(portalBounds.x + 10, portalBounds.y + 10, portalBounds.width - 20, portalBounds.height - 20);

        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.rect(playerPos.x - playerSize / 2, playerPos.y - playerSize / 2, playerSize, playerSize);

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "LOBBY", roomBounds.x + 50, roomBounds.y + roomBounds.height - 80);
        font.draw(batch, "ENTER PORTAL", portalBounds.x - 30, portalBounds.y + portalBounds.height + 30);
        batch.end();

        checkPortalCollision();
    }

    private void handleInput(float delta) {
        float nextX = playerPos.x;
        float nextY = playerPos.y;
        float moveDist = playerSpeed * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) nextY += moveDist;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) nextY -= moveDist;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) nextX -= moveDist;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) nextX += moveDist;

        Rectangle nextPlayerBounds = new Rectangle(nextX - playerSize / 2, nextY - playerSize / 2, playerSize, playerSize);
        boolean collision = false;

        for (Rectangle wall : walls) {
            if (nextPlayerBounds.overlaps(wall)) {
                collision = true;
                break;
            }
        }
        for (Rectangle crate : crates) {
            if (nextPlayerBounds.overlaps(crate)) {
                collision = true;
                break;
            }
        }

        if (!collision) {
            playerPos.set(nextX, nextY);
        }
    }

    private void checkPortalCollision() {
        Rectangle pBounds = new Rectangle(playerPos.x - playerSize / 2, playerPos.y - playerSize / 2, playerSize, playerSize);
        if (pBounds.overlaps(portalBounds)) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
