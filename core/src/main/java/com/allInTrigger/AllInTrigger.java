package com.allInTrigger;

import com.badlogic.gdx.Game;
import com.allInTrigger.view.lobby.MainMenuScreen;
import com.allInTrigger.map.LevelMap;
import com.allInTrigger.map.MapRegistry;

public class AllInTrigger extends Game {
    @Override
    public void create() {
        // Register LevelMaps for levels 1..7. For each level only one room will have the portal enabled.
        // Room ids used by GameRenderer: -1 (spawn), 0, 1, 2

        // Level 1 - easy: exit in room 0
        LevelMap l1 = new LevelMap(1);
        l1.setPortalEnabledForRoom(-1, false);
        l1.setPortalEnabledForRoom(0, true);
        l1.setPortalEnabledForRoom(1, false);
        l1.setPortalEnabledForRoom(2, false);
        MapRegistry.register(l1);

        // Level 2 - normal: exit in room 1
        LevelMap l2 = new LevelMap(2);
        l2.setPortalEnabledForRoom(-1, false);
        l2.setPortalEnabledForRoom(0, false);
        l2.setPortalEnabledForRoom(1, true);
        l2.setPortalEnabledForRoom(2, false);
        MapRegistry.register(l2);

        // Level 3 - special: central room (1) should NOT show portal; choose room 2 as exit
        LevelMap l3 = new LevelMap(3);
        l3.setPortalEnabledForRoom(-1, false);
        l3.setPortalEnabledForRoom(0, false);
        l3.setPortalEnabledForRoom(1, false); // central room no portal
        l3.setPortalEnabledForRoom(2, true);
        MapRegistry.register(l3);

        // Levels 4..7 — Very hard: only one exit room each (choose various rooms)
        LevelMap l4 = new LevelMap(4);
        l4.setPortalEnabledForRoom(-1, false);
        l4.setPortalEnabledForRoom(0, false);
        l4.setPortalEnabledForRoom(1, false);
        l4.setPortalEnabledForRoom(2, true);
        MapRegistry.register(l4);

        LevelMap l5 = new LevelMap(5);
        l5.setPortalEnabledForRoom(-1, false);
        l5.setPortalEnabledForRoom(0, true);
        l5.setPortalEnabledForRoom(1, false);
        l5.setPortalEnabledForRoom(2, false);
        MapRegistry.register(l5);

        LevelMap l6 = new LevelMap(6);
        l6.setPortalEnabledForRoom(-1, false);
        l6.setPortalEnabledForRoom(0, false);
        l6.setPortalEnabledForRoom(1, false);
        l6.setPortalEnabledForRoom(2, true);
        MapRegistry.register(l6);

        LevelMap l7 = new LevelMap(7);
        l7.setPortalEnabledForRoom(-1, false);
        l7.setPortalEnabledForRoom(0, false);
        l7.setPortalEnabledForRoom(1, true);
        l7.setPortalEnabledForRoom(2, false);
        MapRegistry.register(l7);

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
