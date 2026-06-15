package com.allInTrigger.view.lobby;

import com.badlogic.gdx.Gdx;
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

public class MainMenuScreen implements Screen {

    private final AllInTrigger game;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch   batch;
    private BitmapFont    titleFont;
    private BitmapFont    btnFont;
    private BitmapFont    subtitleFont;
    private OrthographicCamera uiCamera;

    private Rectangle startBtn;
    private Rectangle settingsBtn;
    private Rectangle exitBtn;

    private float time = 0f;

    private float heroX = 300f, heroY = 340f;
    private float heroVX = 90f, heroVY = 55f;
    private float heroWeaponAngle = 0f;

    private static final float[][] WAYPOINTS = {
        {200, 220}, {700, 180}, {1100, 300}, {950, 550},
        {600, 620}, {280, 500}, {160, 340}, {200, 220}
    };
    private int   waypointIdx = 0;
    private float heroSpeed   = 130f;

    private static final float[][] TORCHES = {
        {150, 650}, {450, 680}, {750, 650}, {1050, 670}, {1220, 650},
        {150, 120}, {450, 100}, {800, 120}, {1100, 100},
        {130, 380}, {1230, 380},
    };

    private static final float[][] TREES = {
        {340, 480}, {560, 520}, {820, 200}, {990, 440},
        {170, 280}, {1100, 520}, {650, 370},
    };

    private static final float[][] BUSHES = {
        {430, 300}, {700, 540}, {920, 350}, {260, 580}, {1050, 220},
    };

    private static final float[][] CRATES = {
        {500, 420}, {850, 500}, {300, 180}, {1000, 150},
    };

    private static final float[][] ROOMS = {
        {80,  80,  450, 380},
        {580, 80,  480, 380},
        {80,  520, 300, 220},
        {800, 490, 420, 250},
        {420, 380, 200, 160},
    };

    private static final float[][] CORRIDORS = {
        {530, 220, 50,  120},
        {580, 560, 220, 60},
        {380, 500, 100, 50},
        {560, 350, 90,  80},
    };

    // ── Hover-трекінг для click-звуку ──────────────────────────────────────
    private boolean wasStartHovered    = false;
    private boolean wasSettingsHovered = false;
    private boolean wasExitHovered     = false;

    public MainMenuScreen(AllInTrigger game) {
        this.game = game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        batch         = new SpriteBatch();

        titleFont = new BitmapFont();
        titleFont.getData().setScale(4.5f);

        subtitleFont = new BitmapFont();
        subtitleFont.getData().setScale(1.3f);

        btnFont = new BitmapFont();
        btnFont.getData().setScale(1.8f);

        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, 1280, 720);

        float btnW = 260f, btnH = 58f;
        float btnX = 1280 - 80 - btnW;
        startBtn    = new Rectangle(btnX, 400, btnW, btnH);
        settingsBtn = new Rectangle(btnX, 310, btnW, btnH);
        exitBtn     = new Rectangle(btnX, 220, btnW, btnH);

        // ── Запускаємо музику меню ─────────────────────────────────────────
        SoundManager.getInstance().playMenuMusic();
    }

    @Override
    public void render(float delta) {
        time += delta;

        Gdx.gl.glClearColor(0.04f, 0.04f, 0.06f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateHero(delta);

        float mouseX = Gdx.input.getX();
        float mouseY = 720 - Gdx.input.getY();

        // ── Hover-звук: один клік при наведенні ───────────────────────────
        checkHoverSound(startBtn,    wasStartHovered,    mouseX, mouseY);
        wasStartHovered    = startBtn.contains(mouseX, mouseY);
        checkHoverSound(settingsBtn, wasSettingsHovered, mouseX, mouseY);
        wasSettingsHovered = settingsBtn.contains(mouseX, mouseY);
        checkHoverSound(exitBtn,     wasExitHovered,     mouseX, mouseY);
        wasExitHovered     = exitBtn.contains(mouseX, mouseY);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(uiCamera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0.04f, 0.04f, 0.07f, 1f);
        shapeRenderer.rect(0, 0, 1280, 720);

        for (float[] c : CORRIDORS) {
            shapeRenderer.setColor(0.09f, 0.09f, 0.12f, 0.55f);
            shapeRenderer.rect(c[0], c[1], c[2], c[3]);
            drawGridAlpha(c[0], c[1], c[2], c[3], 0.06f);
        }
        for (float[] r : ROOMS) {
            shapeRenderer.setColor(0.11f, 0.11f, 0.15f, 0.6f);
            shapeRenderer.rect(r[0], r[1], r[2], r[3]);
            drawGridAlpha(r[0], r[1], r[2], r[3], 0.08f);
        }

        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.22f, 0.22f, 0.28f, 0.35f);
        for (float[] r : ROOMS) {
            shapeRenderer.rect(r[0], r[1], r[2], r[3]);
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (float[] t : TORCHES) drawTorchBg(t[0], t[1], time);
        for (float[] t : TREES)  drawTreeBg(t[0], t[1]);
        for (float[] b : BUSHES) drawBushBg(b[0], b[1]);
        for (float[] c : CRATES) drawCrateBg(c[0], c[1]);

        drawHeroBg(heroX, heroY, heroWeaponAngle);

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0f, 0f, 0f, 0.55f);
        shapeRenderer.rect(0, 0, 1280, 720);

        shapeRenderer.setColor(0.06f, 0.06f, 0.09f, 0.92f);
        shapeRenderer.rect(0, 0, 420, 720);

        shapeRenderer.setColor(0.35f, 0.2f, 0.6f, 0.9f);
        shapeRenderer.rect(418, 0, 3, 720);
        shapeRenderer.setColor(0.1f, 0.5f, 1f, 0.5f);
        shapeRenderer.rect(416, 0, 2, 720);

        float glowPulse = (float)(Math.sin(time * 2.0f) * 0.08f + 0.18f);
        shapeRenderer.setColor(0.3f, 0.05f, 0.55f, glowPulse);
        shapeRenderer.rect(20, 555, 380, 110);

        drawButton(startBtn,    time, true);
        drawButton(settingsBtn, time, false);
        drawButton(exitBtn,     time, false);

        shapeRenderer.setColor(0.18f, 0.08f, 0.28f, 0.7f);
        shapeRenderer.rect(0, 0, 420, 6);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        titleFont.setColor(0.15f, 0.05f, 0.3f, 1f);
        titleFont.draw(batch, "ALL IN", 32, 672);
        titleFont.draw(batch, "TRIGGER", 32, 618);

        titleFont.setColor(0.85f, 0.75f, 1.0f, 1f);
        titleFont.draw(batch, "ALL IN", 30, 674);
        titleFont.setColor(0.45f, 0.8f, 1.0f, 1f);
        titleFont.draw(batch, "TRIGGER", 30, 620);

        subtitleFont.setColor(0.5f, 0.5f, 0.65f, 1f);
        subtitleFont.draw(batch, "A dungeon shooter", 30, 555);

        drawBtnLabel("START GAME", startBtn,    mouseX, mouseY);
        drawBtnLabel("SETTINGS",   settingsBtn, mouseX, mouseY);
        drawBtnLabel("EXIT",        exitBtn,     mouseX, mouseY);

        subtitleFont.getData().setScale(0.8f);
        subtitleFont.setColor(0.3f, 0.3f, 0.4f, 1f);
        subtitleFont.draw(batch, "v0.1 alpha", 18, 30);
        subtitleFont.getData().setScale(1.3f);

        batch.end();

        // ── INPUT ─────────────────────────────────────────────────────────
        if (Gdx.input.justTouched()) {
            if (startBtn.contains(mouseX, mouseY)) {
                SoundManager.getInstance().playClick(); // ← ЗВУК кнопки
                game.setScreen(new LobbyScreen(game));
            } else if (exitBtn.contains(mouseX, mouseY)) {
                SoundManager.getInstance().playClick(); // ← ЗВУК кнопки
                Gdx.app.exit();
            }
        }
    }

    /** Грає tick-звук один раз при наведенні курсора на кнопку. */
    private void checkHoverSound(Rectangle btn, boolean wasHovered, float mx, float my) {
        if (!wasHovered && btn.contains(mx, my)) {
            SoundManager.getInstance().playClick();
        }
    }

    private void updateHero(float delta) {
        float[] target = WAYPOINTS[waypointIdx];
        float tx = target[0], ty = target[1];
        float dx = tx - heroX, dy = ty - heroY;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist < 8f) {
            waypointIdx = (waypointIdx + 1) % (WAYPOINTS.length - 1);
        } else {
            float nx = dx / dist, ny = dy / dist;
            heroX += nx * heroSpeed * delta;
            heroY += ny * heroSpeed * delta;
            heroWeaponAngle = (float) Math.toDegrees(Math.atan2(ny, nx));
        }
    }

    private void drawGridAlpha(float x, float y, float w, float h, float alpha) {
        shapeRenderer.setColor(0.2f, 0.2f, 0.26f, alpha);
        for (float gx = x; gx <= x + w; gx += 50)
            shapeRenderer.rect(gx, y, 1f, h);
        for (float gy = y; gy <= y + h; gy += 50)
            shapeRenderer.rect(x, gy, w, 1f);
    }

    private void drawTorchBg(float x, float y, float t) {
        float fl = (float) Math.sin(t * 6.5f + x * 0.02f) * 3f;
        shapeRenderer.setColor(1f, 0.55f, 0.1f, 0.025f);
        shapeRenderer.circle(x, y + 14, 68 + fl);
        shapeRenderer.setColor(1f, 0.4f, 0.05f, 0.06f);
        shapeRenderer.circle(x, y + 14, 38 + fl * 0.6f);
        shapeRenderer.setColor(1f, 0.2f, 0f, 0.11f);
        shapeRenderer.circle(x, y + 14, 18 + fl * 0.3f);
        shapeRenderer.setColor(0.22f, 0.22f, 0.26f, 0.8f);
        shapeRenderer.rect(x - 5, y, 10, 18);
        shapeRenderer.setColor(0.35f, 0.35f, 0.4f, 0.8f);
        shapeRenderer.rect(x - 7, y + 18, 14, 4);
        shapeRenderer.setColor(0.9f, 0.25f, 0f, 0.85f);
        shapeRenderer.triangle(x - 4, y + 22, x + 4, y + 22, x, y + 32 + fl);
        shapeRenderer.setColor(1f, 0.85f, 0.2f, 0.85f);
        shapeRenderer.triangle(x - 2f, y + 22, x + 2f, y + 22, x, y + 26 + fl * 0.5f);
    }

    private void drawTreeBg(float x, float y) {
        shapeRenderer.setColor(0f, 0f, 0f, 0.2f);
        shapeRenderer.ellipse(x - 12, y - 3, 34, 10);
        shapeRenderer.setColor(0.18f, 0.08f, 0.02f, 0.7f);
        shapeRenderer.rect(x - 3, y, 11, 11);
        shapeRenderer.setColor(0.06f, 0.28f, 0.22f, 0.75f);
        shapeRenderer.triangle(x - 20, y + 10, x, y + 10, x, y + 33);
        shapeRenderer.setColor(0.11f, 0.42f, 0.33f, 0.75f);
        shapeRenderer.triangle(x, y + 10, x + 20, y + 10, x, y + 33);
        shapeRenderer.setColor(0.09f, 0.36f, 0.28f, 0.75f);
        shapeRenderer.triangle(x - 15, y + 25, x, y + 25, x, y + 48);
        shapeRenderer.setColor(0.15f, 0.52f, 0.42f, 0.75f);
        shapeRenderer.triangle(x, y + 25, x + 15, y + 25, x, y + 48);
    }

    private void drawBushBg(float x, float y) {
        shapeRenderer.setColor(0f, 0f, 0f, 0.2f);
        shapeRenderer.ellipse(x - 15, y - 5, 44, 12);
        shapeRenderer.setColor(0.04f, 0.16f, 0.05f, 0.65f);
        shapeRenderer.circle(x - 10, y + 8, 15);
        shapeRenderer.circle(x + 10, y + 8, 15);
        shapeRenderer.circle(x, y + 17, 18);
        shapeRenderer.setColor(0.08f, 0.3f, 0.1f, 0.65f);
        shapeRenderer.circle(x, y + 20, 13);
    }

    private void drawCrateBg(float x, float y) {
        shapeRenderer.setColor(0f, 0f, 0f, 0.25f);
        shapeRenderer.ellipse(x - 2, y - 3, 44, 11);
        shapeRenderer.setColor(0.36f, 0.2f, 0.07f, 0.7f);
        shapeRenderer.rect(x, y, 40, 32);
        shapeRenderer.setColor(0.52f, 0.3f, 0.12f, 0.7f);
        shapeRenderer.rect(x, y + 27, 40, 6);
        shapeRenderer.setColor(0.24f, 0.13f, 0.04f, 0.7f);
        shapeRenderer.rect(x + 4, y, 3, 27);
        shapeRenderer.rect(x + 33, y, 3, 27);
    }

    private void drawHeroBg(float px, float py, float angle) {
        shapeRenderer.setColor(0f, 0f, 0f, 0.25f);
        shapeRenderer.ellipse(px + 1, py - 3, 26, 8);
        shapeRenderer.setColor(0.2f, 0.2f, 0.9f, 0.85f);
        shapeRenderer.rect(px, py, 26, 30);
        shapeRenderer.setColor(0.18f, 0.18f, 0.22f, 0.85f);
        shapeRenderer.rect(px, py + 14, 26, 11);
        shapeRenderer.setColor(0.9f, 0.9f, 1f, 0.9f);
        shapeRenderer.rect(px + 4,  py + 18, 5, 3);
        shapeRenderer.rect(px + 17, py + 18, 5, 3);
        shapeRenderer.setColor(0.5f, 0.5f, 0.55f, 0.85f);
        shapeRenderer.rect(px + 13, py + 11, 0, 2, 20, 6, 1f, 1f, angle);
    }

    private void drawButton(Rectangle btn, float t, boolean isStart) {
        float mouseX = Gdx.input.getX();
        float mouseY = 720 - Gdx.input.getY();
        boolean hovered = btn.contains(mouseX, mouseY);

        if (hovered) {
            shapeRenderer.setColor(0.4f, 0.2f, 0.7f, 0.6f);
            shapeRenderer.rect(btn.x - 2, btn.y - 2, btn.width + 4, btn.height + 4);
            shapeRenderer.setColor(0.22f, 0.1f, 0.38f, 0.95f);
            shapeRenderer.rect(btn.x, btn.y, btn.width, btn.height);
            shapeRenderer.setColor(0.6f, 0.4f, 1f, 0.3f);
            shapeRenderer.rect(btn.x, btn.y + btn.height - 4, btn.width, 4);
        } else {
            shapeRenderer.setColor(0.12f, 0.12f, 0.17f, 0.9f);
            shapeRenderer.rect(btn.x, btn.y, btn.width, btn.height);
            shapeRenderer.setColor(0.25f, 0.15f, 0.4f, 0.6f);
            shapeRenderer.rect(btn.x, btn.y + btn.height - 3, btn.width, 3);
        }

        if (isStart || hovered) {
            shapeRenderer.setColor(0.5f, 0.3f, 1f, hovered ? 1f : 0.5f);
            shapeRenderer.rect(btn.x, btn.y, 4, btn.height);
        }
    }

    private void drawBtnLabel(String label, Rectangle btn, float mouseX, float mouseY) {
        boolean hovered = btn.contains(mouseX, mouseY);
        if (hovered) btnFont.setColor(Color.WHITE);
        else         btnFont.setColor(0.65f, 0.65f, 0.75f, 1f);
        float tw = label.length() * 13f;
        btnFont.draw(batch, label, btn.x + (btn.width - tw) / 2f + 8, btn.y + 38);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause()  { SoundManager.getInstance().pauseMusic(); }
    @Override public void resume() { SoundManager.getInstance().resumeMusic(); }
    @Override public void hide()   {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        titleFont.dispose();
        subtitleFont.dispose();
        btnFont.dispose();
        // Музику НЕ зупиняємо тут — LobbyScreen / GameScreen зробить це через playMenuMusic/playLevelMusic
    }
}
