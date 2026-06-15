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
import com.allInTrigger.view.ui.SoundManager; // ← ЗВУК

public class LobbyScreen implements Screen {
    private final AllInTrigger game;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont labelFont;
    private OrthographicCamera camera;

    private Vector2 playerPos;
    private final float PLAYER_WIDTH = 32f;
    private final float PLAYER_HEIGHT = 36f;
    private final float PLAYER_SPEED = 300f;
    private float camTime = 0f;

    private Rectangle roomBounds;
    private Rectangle portalBounds;
    private Rectangle[] walls;
    private Rectangle[] crates;
    private Vector2[] torches;
    private Vector2[] trees;
    private Vector2[] bushes;

    public LobbyScreen(AllInTrigger game) {
        this.game = game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        labelFont = new BitmapFont();
        labelFont.getData().setScale(1.1f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        playerPos = new Vector2(700, 550);

        roomBounds = new Rectangle(100, 100, 1400, 1000);
        portalBounds = new Rectangle(1340, 530, 80, 120);

        walls = new Rectangle[]{
            new Rectangle(100,  100,  1400, 40),
            new Rectangle(100,  1060, 1400, 40),
            new Rectangle(100,  100,  40,  1000),
            new Rectangle(1460, 100,  40,  1000)
        };

        crates = new Rectangle[]{
            new Rectangle(300,  300, 50, 50),
            new Rectangle(350,  300, 50, 50),
            new Rectangle(300,  350, 50, 50),
            new Rectangle(1100, 800, 50, 50),
            new Rectangle(1150, 850, 50, 50)
        };

        torches = new Vector2[]{
            new Vector2(200,  1040), new Vector2(500,  1040),
            new Vector2(800,  1040), new Vector2(1100, 1040),
            new Vector2(1400, 1040), new Vector2(200,  160),
            new Vector2(500,  160),  new Vector2(800,  160),
            new Vector2(1100, 160),  new Vector2(1400, 160),
            new Vector2(140,  350),  new Vector2(140,  700),
            new Vector2(1460, 350),  new Vector2(1460, 700),
        };

        trees = new Vector2[]{
            new Vector2(450, 700), new Vector2(520, 750),
            new Vector2(950, 300), new Vector2(250, 850),
            new Vector2(700, 500), new Vector2(1200, 600),
        };

        bushes = new Vector2[]{
            new Vector2(600, 800),  new Vector2(350, 600),
            new Vector2(900, 850),  new Vector2(1050, 450),
            new Vector2(180, 500),
        };

        // ── Музика лобі (та ж, що меню) ───────────────────────────────────
        SoundManager.getInstance().playMenuMusic();
    }

    @Override
    public void render(float delta) {
        camTime += delta;

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput(delta);

        camera.position.set(playerPos.x, playerPos.y, 0);
        camera.update();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0.11f, 0.11f, 0.14f, 1f);
        shapeRenderer.rect(roomBounds.x, roomBounds.y, roomBounds.width, roomBounds.height);

        shapeRenderer.setColor(0.15f, 0.15f, 0.19f, 0.6f);
        for (float x = roomBounds.x; x <= roomBounds.x + roomBounds.width; x += 50)
            shapeRenderer.rect(x, roomBounds.y, 1.5f, roomBounds.height);
        for (float y = roomBounds.y; y <= roomBounds.y + roomBounds.height; y += 50)
            shapeRenderer.rect(roomBounds.x, y, roomBounds.width, 1.5f);

        shapeRenderer.setColor(0.28f, 0.08f, 0.08f, 0.55f);
        shapeRenderer.rect(550, 380, 500, 380);
        shapeRenderer.setColor(0.38f, 0.1f, 0.1f, 0.3f);
        shapeRenderer.rect(570, 400, 460, 340);

        for (Rectangle wall : walls) {
            shapeRenderer.setColor(0.32f, 0.32f, 0.38f, 1f);
            shapeRenderer.rect(wall.x, wall.y + wall.height - 6, wall.width, 6);
            shapeRenderer.setColor(0.22f, 0.22f, 0.27f, 1f);
            shapeRenderer.rect(wall.x, wall.y, wall.width, wall.height - 6);
        }

        for (Vector2 t : torches) drawTorch(t.x, t.y, camTime);
        for (Rectangle c : crates) drawCrate(c.x, c.y);
        for (Vector2 t : trees)   drawPineTree(t.x, t.y);
        for (Vector2 b : bushes)  drawBush(b.x, b.y);

        drawPortal(portalBounds.x, portalBounds.y, camTime);
        drawPlayer(playerPos.x, playerPos.y, camTime * 40f);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "LOBBY", roomBounds.x + 60, roomBounds.y + roomBounds.height - 55);
        labelFont.setColor(Color.CYAN);
        labelFont.draw(batch, "ENTER PORTAL", portalBounds.x - 20, portalBounds.y + portalBounds.height + 28);
        batch.end();

        checkPortalCollision();
    }

    private void handleInput(float delta) {
        float speed = PLAYER_SPEED * delta;

        float nextX = playerPos.x;
        float nextY = playerPos.y;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) nextY += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) nextY -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) nextX -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) nextX += speed;

        Rectangle bx = new Rectangle(nextX, playerPos.y, PLAYER_WIDTH, PLAYER_HEIGHT);
        Rectangle by = new Rectangle(playerPos.x, nextY, PLAYER_WIDTH, PLAYER_HEIGHT);

        boolean cx = false, cy = false;
        for (Rectangle w : walls)  { if (bx.overlaps(w)) cx = true; if (by.overlaps(w)) cy = true; }
        for (Rectangle c : crates) { if (bx.overlaps(c)) cx = true; if (by.overlaps(c)) cy = true; }

        if (!cx) playerPos.x = nextX;
        if (!cy) playerPos.y = nextY;
    }

    private void checkPortalCollision() {
        Rectangle p = new Rectangle(playerPos.x, playerPos.y, PLAYER_WIDTH, PLAYER_HEIGHT);
        if (p.overlaps(portalBounds)) {
            SoundManager.getInstance().playClick(); // ← ЗВУК входу в портал
            game.setScreen(new GameScreen(game));
        }
    }

    // ── малювання (без змін) ───────────────────────────────────────────────

    private void drawTorch(float x, float y, float time) {
        float flicker = (float) Math.sin(time * 6.5f + x * 0.01f) * 4f;
        shapeRenderer.setColor(1f, 0.55f, 0.1f, 0.035f);
        shapeRenderer.circle(x, y + 15, 70 + flicker);
        shapeRenderer.setColor(1f, 0.4f, 0.05f, 0.08f);
        shapeRenderer.circle(x, y + 15, 42 + flicker * 0.6f);
        shapeRenderer.setColor(1f, 0.25f, 0f, 0.14f);
        shapeRenderer.circle(x, y + 15, 22 + flicker * 0.3f);
        shapeRenderer.setColor(0.22f, 0.22f, 0.26f, 1f);
        shapeRenderer.rect(x - 6, y, 12, 22);
        shapeRenderer.setColor(0.35f, 0.35f, 0.4f, 1f);
        shapeRenderer.rect(x - 8, y + 22, 16, 4);
        shapeRenderer.setColor(0.9f, 0.25f, 0f, 1f);
        shapeRenderer.triangle(x - 5, y + 26, x + 5, y + 26, x, y + 38 + flicker);
        shapeRenderer.setColor(1f, 0.85f, 0.2f, 1f);
        shapeRenderer.triangle(x - 2.5f, y + 26, x + 2.5f, y + 26, x, y + 31 + flicker * 0.5f);
    }

    private void drawCrate(float x, float y) {
        shapeRenderer.setColor(0f, 0f, 0f, 0.35f);
        shapeRenderer.ellipse(x - 2, y - 4, 56, 14);
        shapeRenderer.setColor(0.4f, 0.22f, 0.08f, 1f);
        shapeRenderer.rect(x, y, 50, 40);
        shapeRenderer.setColor(0.58f, 0.34f, 0.15f, 1f);
        shapeRenderer.rect(x, y + 34, 50, 8);
        shapeRenderer.setColor(0.28f, 0.15f, 0.05f, 1f);
        shapeRenderer.rect(x + 5, y, 4, 34);
        shapeRenderer.rect(x + 41, y, 4, 34);
    }

    private void drawPineTree(float x, float y) {
        shapeRenderer.setColor(0f, 0f, 0f, 0.35f);
        shapeRenderer.ellipse(x - 15, y - 4, 40, 12);
        shapeRenderer.setColor(0.22f, 0.1f, 0.02f, 1f);
        shapeRenderer.rect(x - 4, y, 14, 14);
        shapeRenderer.setColor(0.35f, 0.18f, 0.05f, 1f);
        shapeRenderer.rect(x, y, 4, 14);
        shapeRenderer.setColor(0.08f, 0.32f, 0.25f, 1f);
        shapeRenderer.triangle(x - 24, y + 12, x, y + 12, x, y + 38);
        shapeRenderer.setColor(0.14f, 0.48f, 0.38f, 1f);
        shapeRenderer.triangle(x, y + 12, x + 24, y + 12, x, y + 38);
        shapeRenderer.setColor(0.11f, 0.4f, 0.32f, 1f);
        shapeRenderer.triangle(x - 18, y + 28, x, y + 28, x, y + 54);
        shapeRenderer.setColor(0.18f, 0.58f, 0.46f, 1f);
        shapeRenderer.triangle(x, y + 28, x + 18, y + 28, x, y + 54);
        shapeRenderer.setColor(0.15f, 0.48f, 0.38f, 1f);
        shapeRenderer.triangle(x - 12, y + 44, x, y + 44, x, y + 68);
        shapeRenderer.setColor(0.22f, 0.68f, 0.54f, 1f);
        shapeRenderer.triangle(x, y + 44, x + 12, y + 44, x, y + 68);
    }

    private void drawBush(float x, float y) {
        shapeRenderer.setColor(0f, 0f, 0f, 0.35f);
        shapeRenderer.ellipse(x - 18, y - 6, 52, 16);
        shapeRenderer.setColor(0.04f, 0.18f, 0.06f, 1f);
        shapeRenderer.circle(x - 12, y + 10, 18);
        shapeRenderer.circle(x + 12, y + 10, 18);
        shapeRenderer.circle(x, y + 20, 22);
        shapeRenderer.setColor(0.09f, 0.35f, 0.12f, 1f);
        shapeRenderer.circle(x - 6, y + 14, 15);
        shapeRenderer.circle(x,     y + 24, 16);
        shapeRenderer.setColor(0.16f, 0.52f, 0.2f, 1f);
        shapeRenderer.circle(x + 8, y + 14, 14);
        shapeRenderer.setColor(0.24f, 0.68f, 0.28f, 1f);
        shapeRenderer.circle(x, y + 28, 10);
    }

    private void drawPortal(float x, float y, float time) {
        shapeRenderer.setColor(0f, 0f, 0f, 0.4f);
        shapeRenderer.ellipse(x - 20, y - 8, 90, 20);
        float pulse = (float) Math.sin(time * 5f) * 4f;
        shapeRenderer.setColor(0.3f, 0f, 0.6f, 0.35f);
        shapeRenderer.ellipse(x + 10, y, 30 + pulse, 75);
        shapeRenderer.setColor(0.1f, 0.6f, 1f, 0.7f);
        shapeRenderer.ellipse(x + 17, y + 10, 16 - pulse, 55);
        shapeRenderer.setColor(0.2f, 0.2f, 0.25f, 1f);
        shapeRenderer.rect(x - 10, y, 16, 80);
        shapeRenderer.setColor(0.35f, 0.35f, 0.4f, 1f);
        shapeRenderer.rect(x - 10, y + 80, 16, 6);
        shapeRenderer.setColor(0.2f, 0.2f, 0.25f, 1f);
        shapeRenderer.rect(x + 44, y, 16, 80);
        shapeRenderer.setColor(0.35f, 0.35f, 0.4f, 1f);
        shapeRenderer.rect(x + 44, y + 80, 16, 6);
        shapeRenderer.setColor(0.25f, 0.25f, 0.3f, 1f);
        shapeRenderer.rect(x - 14, y + 70, 78, 12);
    }

    private void drawPlayer(float px, float py, float weaponAngle) {
        shapeRenderer.setColor(0f, 0f, 0f, 0.3f);
        shapeRenderer.ellipse(px + 2, py - 4, 28, 10);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(px, py, PLAYER_WIDTH, PLAYER_HEIGHT);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(px, py + 16, PLAYER_WIDTH, 14);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(px + 6,  py + 22, 6, 4);
        shapeRenderer.rect(px + 20, py + 22, 6, 4);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(px + 16, py + 13, 0, 3, 22, 8, 1f, 1f, weaponAngle);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause()  { SoundManager.getInstance().pauseMusic(); }
    @Override public void resume() { SoundManager.getInstance().resumeMusic(); }
    @Override public void hide()   {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        labelFont.dispose();
    }
}
