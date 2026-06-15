package com.allInTrigger.view.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.allInTrigger.AllInTrigger;
import com.allInTrigger.view.GameRenderer;

public class GameScreen implements Screen {
    private final AllInTrigger game;
    private GameRenderer gameRenderer;

    public GameScreen(AllInTrigger game) {
        this.game = game;
    }

    @Override
    public void show() {
        gameRenderer = new GameRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameRenderer.render();
    }

    @Override
    public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (gameRenderer != null) {
            gameRenderer.dispose();
        }
    }
}
