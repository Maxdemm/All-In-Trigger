package com.allInTrigger;

import com.badlogic.gdx.Game;
import com.allInTrigger.view.lobby.MainMenuScreen;

public class AllInTrigger extends Game {
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
