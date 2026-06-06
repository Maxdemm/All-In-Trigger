package com.gambleknight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gambleknight.view.GameRenderer;

/** {@link ApplicationAdapter} implementation shared by all platforms. */
public class GambleKnightGame extends ApplicationAdapter {
    private GameRenderer gameRenderer;

    @Override
    public void create() {
        gameRenderer = new GameRenderer();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        gameRenderer.render();
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }
}
