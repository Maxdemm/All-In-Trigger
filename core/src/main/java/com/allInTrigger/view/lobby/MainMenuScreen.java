package com.allInTrigger.view.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.allInTrigger.AllInTrigger;

public class MainMenuScreen implements Screen {
    private final AllInTrigger game;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;

    private Rectangle startBtn;
    private Rectangle settingsBtn;
    private Rectangle exitBtn;

    public MainMenuScreen(AllInTrigger game) {
        this.game = game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2f);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float btnWidth = 200;
        float btnHeight = 60;
        float startX = (screenWidth - btnWidth) / 2;

        startBtn = new Rectangle(startX, screenHeight / 2 + 60, btnWidth, btnHeight);
        settingsBtn = new Rectangle(startX, screenHeight / 2 - 20, btnWidth, btnHeight);
        exitBtn = new Rectangle(startX, screenHeight / 2 - 100, btnWidth, btnHeight);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (Gdx.input.justTouched()) {
            if (startBtn.contains(mouseX, mouseY)) {
                game.setScreen(new LobbyScreen(game));
                return;
            } else if (exitBtn.contains(mouseX, mouseY)) {
                Gdx.app.exit();
                return;
            }
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (startBtn.contains(mouseX, mouseY)) shapeRenderer.setColor(Color.GOLD);
        else shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(startBtn.x, startBtn.y, startBtn.width, startBtn.height);

        if (settingsBtn.contains(mouseX, mouseY)) shapeRenderer.setColor(Color.GOLD);
        else shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(settingsBtn.x, settingsBtn.y, settingsBtn.width, settingsBtn.height);

        if (exitBtn.contains(mouseX, mouseY)) shapeRenderer.setColor(Color.GOLD);
        else shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(exitBtn.x, exitBtn.y, exitBtn.width, exitBtn.height);

        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "GAMBLE KNIGHT", Gdx.graphics.getWidth() / 2 - 130, Gdx.graphics.getHeight() - 100);

        font.setColor(Color.BLACK);
        font.draw(batch, "START", startBtn.x + 55, startBtn.y + 40);
        font.draw(batch, "SETTINGS", settingsBtn.x + 35, settingsBtn.y + 40);
        font.draw(batch, "EXIT", exitBtn.x + 65, exitBtn.y + 40);
        batch.end();
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
